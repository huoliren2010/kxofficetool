package com.kx.officetool;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.kx.officetool.fragment.CompanyDepartMentFragment;
import com.kx.officetool.fragment.CompanyDetailFragment;
import com.kx.officetool.fragment.CompanyManagerFragment;
import com.kx.officetool.fragment.CompanyMeetingRoomFragment;

public class CompanyDetailsActivity extends BaseActivity {
    FragmentManager mSupportFragmentManager = null;

    CompanyDetailFragment mCompanyDetailFragment;
    CompanyManagerFragment mCompanyManagerFragment;
    CompanyMeetingRoomFragment mCompanyMeetingRoomFragment;
    CompanyDepartMentFragment mCompanyDepartMentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);
        mSupportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
        mCompanyDetailFragment = new CompanyDetailFragment();
        mCompanyManagerFragment = new CompanyManagerFragment();
        mCompanyMeetingRoomFragment = new CompanyMeetingRoomFragment();
        mCompanyDepartMentFragment = new CompanyDepartMentFragment();
        fragmentTransaction.add(R.id.activity_company_details, mCompanyDetailFragment)
                .add(R.id.activity_company_details, mCompanyDepartMentFragment)
                .add(R.id.activity_company_details, mCompanyManagerFragment)
                .add(R.id.activity_company_details, mCompanyMeetingRoomFragment)
        ;
        fragmentTransaction.hide(mCompanyManagerFragment).hide(mCompanyMeetingRoomFragment).hide(mCompanyDepartMentFragment).show(mCompanyDetailFragment).commit();
    }

    public void showCompanyManagerFragment() {
        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
        fragmentTransaction.hide(mCompanyDetailFragment).hide(mCompanyMeetingRoomFragment).hide(mCompanyDepartMentFragment).show(mCompanyManagerFragment).commit();
    }

    public void showCompanyMeetingRoomFragment(){
        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
        fragmentTransaction.hide(mCompanyDetailFragment).hide(mCompanyManagerFragment).hide(mCompanyDepartMentFragment).show(mCompanyMeetingRoomFragment).commit();
    }

    public void showCompanyDepartmentFragment() {
        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
        fragmentTransaction.hide(mCompanyDetailFragment).hide(mCompanyManagerFragment).hide(mCompanyMeetingRoomFragment).show(mCompanyDepartMentFragment).commit();
    }
}
