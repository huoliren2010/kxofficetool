package com.kx.officetool;

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

import com.kx.officetool.infos.CompanyInfo;
import com.kx.officetool.infos.LoadUserAvatar;
import com.kx.officetool.infos.MeetingRoom;
import com.kx.officetool.infos.UserInfo;
import com.kx.officetool.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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
        if (mUsrInfo != null && mUsrInfo.isJoinedInCompany()) {
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
        if(mCompanyInfo != null){
            //for test set some infos
            List<UserInfo> mList = new ArrayList<>();
            mList.add(new UserInfo().setUserAvatar("http://imgsrc.baidu.com/forum/w%3D580/sign=22e7d635bd8f8c54e3d3c5270a2b2dee/3f10b3de9c82d1584109496a890a19d8bd3e427f.jpg").setUserGender(UserInfo.GENDER.BOY).setUserNickName("陈海").setUserPhoneNumber("114").setCompanyId(mCompanyInfo.getCompanyId()).setLevel(UserInfo.Level.NORMAL));
            mList.add(new UserInfo().setUserAvatar("http://imgsrc.baidu.com/forum/w%3D580/sign=9017ac60af1ea8d38a22740ca70830cf/54b8cd11728b47101affe617cacec3fdfd032336.jpg").setUserGender(UserInfo.GENDER.BOY).setUserNickName("沙瑞金").setUserPhoneNumber("114").setCompanyId(mCompanyInfo.getCompanyId()).setLevel(UserInfo.Level.NORMAL));
            mList.add(new UserInfo().setUserAvatar("http://imgsrc.baidu.com/forum/w%3D580/sign=a86449cc6bd0f703e6b295d438fb5148/9c170cf3d7ca7bcb2831d8f4b7096b63f724a8b8.jpg").setUserGender(UserInfo.GENDER.BOY).setUserNickName("丁义珍").setUserPhoneNumber("114").setCompanyId(mCompanyInfo.getCompanyId()).setLevel(UserInfo.Level.NORMAL));
            mList.add(new UserInfo().setUserAvatar("http://imgsrc.baidu.com/forum/w%3D580/sign=3340a1bf9deef01f4d1418cdd0fc99e0/729bd158ccbf6c8107c53433b53eb13532fa4010.jpg").setUserGender(UserInfo.GENDER.BOY).setUserNickName("祁同伟").setUserPhoneNumber("114").setCompanyId(mCompanyInfo.getCompanyId()).setLevel(UserInfo.Level.MIDDLE));
            mList.add(new UserInfo().setUserAvatar("http://imgsrc.baidu.com/forum/w%3D580/sign=b819870701fa513d51aa6cd60d6c554c/e45ff21fbe096b63dfb24c8b05338744eaf8ac5f.jpg").setUserGender(UserInfo.GENDER.BOY).setUserNickName("季昌明").setUserPhoneNumber("114").setCompanyId(mCompanyInfo.getCompanyId()).setLevel(UserInfo.Level.ADVANCED));
            mList.add(new UserInfo().setUserAvatar("http://imgsrc.baidu.com/forum/w%3D580/sign=2807a73fba1c8701d6b6b2ee177e9e6e/8cc0f2d3572c11dfaf98a6f86a2762d0f603c2bd.jpg").setUserGender(UserInfo.GENDER.BOY).setUserNickName("高育良").setUserPhoneNumber("114").setCompanyId(mCompanyInfo.getCompanyId()).setLevel(UserInfo.Level.ADVANCED));
            mList.add(new UserInfo().setUserAvatar("http://www.people.com.cn/mediafile/pic/20170409/63/16943124615050023627.jpg").setUserGender(UserInfo.GENDER.BOY).setUserNickName("侯亮平").setUserPhoneNumber("114").setCompanyId(mCompanyInfo.getCompanyId()).setLevel(UserInfo.Level.ADVANCED));
            mList.add(new UserInfo().setUserAvatar("http://imgsrc.baidu.com/forum/w%3D580/sign=5671fd63dd3f8794d3ff4826e21a0ead/dbe3e6cd7b899e51cb8724cf4ba7d933c9950dfd.jpg").setUserGender(UserInfo.GENDER.BOY).setUserNickName("李达康书记").setUserPhoneNumber("114").setCompanyId(mCompanyInfo.getCompanyId()).setLevel(UserInfo.Level.BOSS));
            mCompanyInfo.setCompanyMembers(mList);
            List<MeetingRoom> mList2 = new ArrayList<>();
            mList2.add(new MeetingRoom().setRoomNumber("1001").setStatus(MeetingRoom.Status.IDLE));
            mList2.add(new MeetingRoom().setRoomNumber("1002").setTime(new Date(2017, 3, 16, 11, 0).getTime(), new Date(2017, 3, 16, 12, 0).getTime()).setStatus(MeetingRoom.Status.USED));
            mList2.add(new MeetingRoom().setRoomNumber("1003").setStatus(MeetingRoom.Status.IDLE));
            mList2.add(new MeetingRoom().setRoomNumber("1004").setTime(new Date(2017, 3, 16, 11, 0).getTime(), new Date(2017, 3, 16, 12, 0).getTime()).setStatus(MeetingRoom.Status.USED));
            mList2.add(new MeetingRoom().setRoomNumber("1005").setTime(new Date(2017, 3, 16, 11, 0).getTime(), new Date(2017, 3, 16, 12, 0).getTime()).setStatus(MeetingRoom.Status.ORDERING));
            mList2.add(new MeetingRoom().setRoomNumber("1007").setTime(new Date(2017, 3, 16, 11, 0).getTime(), new Date(2017, 3, 16, 12, 0).getTime()).setStatus(MeetingRoom.Status.USED));
            mList2.add(new MeetingRoom().setRoomNumber("1008").setTime(new Date(2017, 3, 16, 11, 0).getTime(), new Date(2017, 3, 16, 12, 0).getTime()).setStatus(MeetingRoom.Status.ORDERING));
            mList2.add(new MeetingRoom().setRoomNumber("1006").setStatus(MeetingRoom.Status.IDLE));
            Collections.sort(mList2, new Comparator<MeetingRoom>() {
                @Override
                public int compare(MeetingRoom o1, MeetingRoom o2) {
                    return o1.getRoomStatus() - o2.getRoomStatus();
                }
            });
            mCompanyInfo.setCompanyMeetingRooms(mList2);
            SharedPreferencesUtil.putObject(getActivity(), CompanyInfo.KEY_COMPANYINFO_OBJ, mCompanyInfo);
            //for test set some infos
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
