package com.kx.officetool.fragment;

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
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kx.officetool.LoginActivity;
import com.kx.officetool.R;
import com.kx.officetool.infos.LoadUserAvatar;
import com.kx.officetool.infos.UserInfo;
import com.kx.officetool.service.WebService;
import com.kx.officetool.utils.BitmapUtil;
import com.kx.officetool.utils.IntentUtil;
import com.kx.officetool.utils.SharedPreferencesUtil;

import static android.app.Activity.RESULT_OK;

public class PersonalFragment extends Fragment implements View.OnClickListener {
    private static final int REQUEST_CROP = 0x1025;
    UserInfo mUserInfo;
    ImageView mIvAvatar;
    TextView mTvNickName;
    TextView mTvGender;
    TextView mTvPhoneNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View viewRoot = getView();
        mUserInfo = SharedPreferencesUtil.getObject(getActivity(), UserInfo.KEY_USERINFO_OBJ, UserInfo.class);
        viewRoot.findViewById(R.id.viewroot_usr_avatar).setOnClickListener(this);
        viewRoot.findViewById(R.id.viewroot_usr_gender).setOnClickListener(this);
        viewRoot.findViewById(R.id.btn_logout).setOnClickListener(this);
        mIvAvatar = (ImageView) viewRoot.findViewById(R.id.iv_avatar);
        mTvNickName = (TextView) viewRoot.findViewById(R.id.tv_nickname);
        mTvNickName.setText(mUserInfo.getUserName());
        mTvGender = (TextView) viewRoot.findViewById(R.id.tv_gender);
        mTvGender.setText(mUserInfo.getGender());
        mTvPhoneNumber = (TextView)viewRoot.findViewById(R.id.tv_phonenumber);
        mTvPhoneNumber.setText(mUserInfo.getPhoneNumber());
        LoadUserAvatar.getInstance(getActivity()).loadAvatar(mIvAvatar, mUserInfo.getUserAvatar());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewroot_usr_avatar:
                startActivityForResult(IntentUtil.getCropIntent(), REQUEST_CROP);
                break;
            case R.id.viewroot_usr_gender:
                final PopupWindow popupWindow = new PopupWindow(getActivity());
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_select_gender, null);
                popupWindow.setContentView(view);
                popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setOutsideTouchable(true);
                RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radiogroup);
                radioGroup.check(mUserInfo.getUserGender() == UserInfo.GENDER.BOY ? R.id.radiobutton_boy : R.id.radiobutton_girl);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        mUserInfo.setUserGender(checkedId == R.id.radiobutton_boy ? UserInfo.GENDER.BOY : UserInfo.GENDER.GIRL);
                        popupWindow.dismiss();
                        mTvGender.setText(mUserInfo.getGender());
                        SharedPreferencesUtil.putObject(getActivity(), UserInfo.KEY_USERINFO_OBJ, mUserInfo);
                    }
                });
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                WebService.getInstance().updateUserInfo(mUserInfo);
                            }
                        }).start();
                    }
                });
                popupWindow.showAsDropDown(mTvGender);
                break;
            case R.id.btn_logout:
                SharedPreferencesUtil.clear(getActivity());
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CROP && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                final Bitmap bitmap = BitmapUtil.getBitmap(BitmapUtil.getPath(getActivity(), data.getData()), 120, 120);
                if (bitmap != null && !bitmap.isRecycled())
                    LoadUserAvatar.getInstance(getActivity()).put(UserInfo.KEY_USERINFO_AVATAR, bitmap);
                mUserInfo.setUserAvatar(UserInfo.KEY_USERINFO_AVATAR);
                SharedPreferencesUtil.putObject(getActivity(), UserInfo.KEY_USERINFO_OBJ, mUserInfo);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        mIvAvatar.setImageBitmap(bitmap);
                    }
                });
            } else {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    final Bitmap bitmap = extras.getParcelable("data");
                    if (bitmap != null && !bitmap.isRecycled()) {
                        LoadUserAvatar.getInstance(getActivity()).put(UserInfo.KEY_USERINFO_AVATAR, bitmap);
                        mUserInfo.setUserAvatar(UserInfo.KEY_USERINFO_AVATAR);
                        SharedPreferencesUtil.putObject(getActivity(), UserInfo.KEY_USERINFO_OBJ, mUserInfo);
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                mIvAvatar.setImageBitmap(bitmap);
                            }
                        });
                    }
                }
            }
        }
    }
}
