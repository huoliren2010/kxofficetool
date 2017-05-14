package com.kx.officetool.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kx.officetool.AppConfig;
import com.kx.officetool.BroadActivity;
import com.kx.officetool.CompanyDetailsActivity;
import com.kx.officetool.DailySignActivity;
import com.kx.officetool.JoinCompanyActivity;
import com.kx.officetool.R;
import com.kx.officetool.infos.CompanyInfo;
import com.kx.officetool.infos.LoadUserAvatar;
import com.kx.officetool.infos.UserInfo;
import com.kx.officetool.utils.SharedPreferencesUtil;
import com.kx.officetool.utils.ToastUtil;

import static com.kx.officetool.infos.Actions.ACTION_ON_COMPANY_ATTACHED;

public class WorkFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    private UserInfo mUsrInfo = null;
    private CompanyInfo mCompanyInfo = null;
    private View mViewRoot_NotJoinCompany, mViewRoot_JoinedCompany;

    private ImageView mIvCompanyAvatar;
    private TextView mTvCompanyName, mTvCompanyFormatId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_work, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadUserInfo();
        initControls();
    }


    private void initControls() {
        View viewRoot = getView();
        mViewRoot_JoinedCompany = viewRoot.findViewById(R.id.viewroot_joinedcompany);
        viewRoot.findViewById(R.id.viewroot_companyinfo_item).setOnClickListener(this);
        mIvCompanyAvatar = (ImageView) viewRoot.findViewById(R.id.iv_company_avatar);
        mTvCompanyName = (TextView) viewRoot.findViewById(R.id.tv_companename);
        mTvCompanyFormatId = (TextView) viewRoot.findViewById(R.id.tv_companeid);
        viewRoot.findViewById(R.id.tv_daily_sign).setOnClickListener(this);
        viewRoot.findViewById(R.id.tv_post_hols).setOnClickListener(this);
        viewRoot.findViewById(R.id.tv_order_meetingroom).setOnClickListener(this);
        viewRoot.findViewById(R.id.tv_post_board).setOnClickListener(this);
        if (mUsrInfo != null && mCompanyInfo != null) {
            fillCompanyInfo(mCompanyInfo);
        } else {
            ViewStub viewStubNotjoinCompany = (ViewStub) viewRoot.findViewById(R.id.viewstub_notjoin_anycompany);
            mViewRoot_NotJoinCompany = viewStubNotjoinCompany.inflate();
            mViewRoot_NotJoinCompany.setVisibility(View.VISIBLE);
            mViewRoot_JoinedCompany.setVisibility(View.GONE);
            Button btnJoin = (Button) mViewRoot_NotJoinCompany.findViewById(R.id.btn_join_company);
            Button btnCreate = (Button) mViewRoot_NotJoinCompany.findViewById(R.id.btn_create_company);
            btnJoin.setOnClickListener(this);
            btnCreate.setOnClickListener(this);
        }
    }

    private void fillCompanyInfo(CompanyInfo companyInfo) {
        if(companyInfo == null)return;
        if (mViewRoot_NotJoinCompany != null) mViewRoot_NotJoinCompany.setVisibility(View.GONE);
        mViewRoot_JoinedCompany.setVisibility(View.VISIBLE);
//        Glide.with(this).load(companyInfo.getCompanAvatarUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.ic_personal_default).error(R.mipmap.ic_personal_default).into(mIvCompanyAvatar);
        Bitmap bmp = LoadUserAvatar.getInstance(getActivity()).loadImage(mIvCompanyAvatar, companyInfo.getCompanAvatarUrl(), new LoadUserAvatar.ImageDownloadedCallBack() {
            @Override
            public void onImageDownloaded(ImageView imageView, Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        });
        if(bmp != null && !bmp.isRecycled()){
            mIvCompanyAvatar.setImageBitmap(bmp);
        }else mIvCompanyAvatar.setImageResource(R.mipmap.ic_personal_default);
        mTvCompanyName.setText(companyInfo.getCompanyName());
        mTvCompanyFormatId.setText(String.format(getResources().getString(R.string.string_format_company_id), String.valueOf(companyInfo.getCompanyId())));
    }

    private void loadUserInfo() {
        if (mUsrInfo == null)
            mUsrInfo = SharedPreferencesUtil.getObject(getActivity(), UserInfo.KEY_USERINFO_OBJ, UserInfo.class);
        mCompanyInfo = SharedPreferencesUtil.getObject(getActivity(), CompanyInfo.KEY_COMPANYINFO_OBJ, CompanyInfo.class);
        AppConfig.getInstance().mCompanyInfo = mCompanyInfo;
        if(mCompanyInfo != null){
            mUsrInfo.setCompanyId(mCompanyInfo.getCompanyId());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_join_company:
                startActivity(new Intent(getActivity(), JoinCompanyActivity.class));
                break;
            case R.id.btn_create_company:
                startActivity(new Intent(getActivity(), CreateCompanyActivity.class));
                break;
            //跳转到公司信息界面
            case R.id.viewroot_companyinfo_item:
                startActivity(new Intent(getActivity(), CompanyDetailsActivity.class));
                break;
            case R.id.tv_daily_sign:
                startActivity(new Intent(getActivity(), DailySignActivity.class));
                break;
            case R.id.tv_post_hols:
                break;
            case R.id.tv_order_meetingroom:
                break;
            case R.id.tv_post_board:
                if(AppConfig.getInstance().isManager()){
                    startActivity(new Intent(getActivity(), BroadActivity.class));
                }else ToastUtil.showShort(getActivity(), "只有管理员才能发布公告");
                break;
        }
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter(ACTION_ON_COMPANY_ATTACHED));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            loadUserInfo();
            fillCompanyInfo(mCompanyInfo);
        }
    };
}
