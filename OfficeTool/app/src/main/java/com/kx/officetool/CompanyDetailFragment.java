package com.kx.officetool;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kx.officetool.infos.Actions;
import com.kx.officetool.infos.CompanyInfo;
import com.kx.officetool.infos.LoadUserAvatar;
import com.kx.officetool.utils.BitmapUtil;
import com.kx.officetool.utils.IntentUtil;
import com.kx.officetool.utils.SharedPreferencesUtil;

import static android.app.Activity.RESULT_OK;


public class CompanyDetailFragment extends Fragment implements View.OnClickListener {
    private static final int REQUEST_CROP = 0x1024;
    ImageView mIvCompanyAvatar;
    TextView mTvCompanyName, mTvCompanyId;
    CompanyInfo mCompanyInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_company_details, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View viewRoot = getView();
        viewRoot.findViewById(R.id.viewroot_company_avatar).setOnClickListener(this);
        viewRoot.findViewById(R.id.viewroot_company_manager).setOnClickListener(this);
        viewRoot.findViewById(R.id.viewroot_company_meetingroom).setOnClickListener(this);
        mIvCompanyAvatar = (ImageView) viewRoot.findViewById(R.id.iv_company_avatar);
        mTvCompanyName = (TextView) viewRoot.findViewById(R.id.tv_company_name);
        mTvCompanyId = (TextView) viewRoot.findViewById(R.id.tv_company_id);
        fillCompanyInfo();
    }

    private void fillCompanyInfo() {
        if (mCompanyInfo == null)
            mCompanyInfo = SharedPreferencesUtil.getObject(getActivity(), CompanyInfo.KEY_COMPANYINFO_OBJ, CompanyInfo.class);
        Bitmap bmp = LoadUserAvatar.getInstance(getActivity()).loadImage(mIvCompanyAvatar, mCompanyInfo.getCompanAvatarUrl(), new LoadUserAvatar.ImageDownloadedCallBack() {
            @Override
            public void onImageDownloaded(ImageView imageView, Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        });
        if(bmp != null && !bmp.isRecycled()){
            mIvCompanyAvatar.setImageBitmap(bmp);
        }else mIvCompanyAvatar.setImageResource(R.mipmap.ic_personal_default);
//        Glide.with(this).load(mCompanyInfo.getCompanAvatarUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.ic_personal_default).error(R.mipmap.ic_personal_default).into(mIvCompanyAvatar);
        mTvCompanyName.setText(mCompanyInfo.getCompanyName());
        mTvCompanyId.setText(String.valueOf(mCompanyInfo.getCompanyId()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewroot_company_avatar:
                startActivityForResult(IntentUtil.getCropIntent(), REQUEST_CROP);
                break;
            case R.id.viewroot_company_manager:
                ((CompanyDetailsActivity)getActivity()).showCompanyManagerFragment();
                break;
            case R.id.viewroot_company_meetingroom:
                ((CompanyDetailsActivity)getActivity()).showCompanyMeetingRoomFragment();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CROP && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                final Bitmap bitmap = BitmapUtil.getBitmap(BitmapUtil.getPath(getActivity(), data.getData()), 120, 120);
                if (bitmap != null && !bitmap.isRecycled()) {
                    LoadUserAvatar.getInstance(getActivity()).put(CompanyInfo.KEY_COMPANYINFO_AVATAR, bitmap);
                    mCompanyInfo.setCompanAvatarUrl(CompanyInfo.KEY_COMPANYINFO_AVATAR);
                    SharedPreferencesUtil.putObject(getActivity(), CompanyInfo.KEY_COMPANYINFO_OBJ, mCompanyInfo);
                    getActivity().sendBroadcast(new Intent(Actions.ACTION_ON_COMPANY_ATTACHED));
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mIvCompanyAvatar.setImageBitmap(bitmap);
                        }
                    });
                }
            }else {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    final Bitmap bitmap = extras.getParcelable("data");
                    if (bitmap != null && !bitmap.isRecycled()) {
                        LoadUserAvatar.getInstance(getActivity()).put(CompanyInfo.KEY_COMPANYINFO_AVATAR, bitmap);
                        mCompanyInfo.setCompanAvatarUrl(CompanyInfo.KEY_COMPANYINFO_AVATAR);
                        SharedPreferencesUtil.putObject(getActivity(), CompanyInfo.KEY_COMPANYINFO_OBJ, mCompanyInfo);
                        getActivity().sendBroadcast(new Intent(Actions.ACTION_ON_COMPANY_ATTACHED));
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                mIvCompanyAvatar.setImageBitmap(bitmap);
                            }
                        });
                    }
                }
            }
        }
    }


}
