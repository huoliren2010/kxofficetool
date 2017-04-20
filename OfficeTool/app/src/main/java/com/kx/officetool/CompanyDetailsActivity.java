package com.kx.officetool;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CompanyDetailsActivity extends AppCompatActivity {
    FragmentManager mSupportFragmentManager = null;

    CompanyDetailFragment mCompanyDetailFragment;
    CompanyManagerFragment mCompanyManagerFragment;
    CompanyMeetingRoomFragment mCompanyMeetingRoomFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);
        mSupportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
        mCompanyDetailFragment = new CompanyDetailFragment();
        mCompanyManagerFragment = new CompanyManagerFragment();
        mCompanyMeetingRoomFragment = new CompanyMeetingRoomFragment();
        fragmentTransaction.add(R.id.activity_company_details, mCompanyDetailFragment)
                .add(R.id.activity_company_details, mCompanyManagerFragment)
                .add(R.id.activity_company_details, mCompanyMeetingRoomFragment)
        ;
        fragmentTransaction.hide(mCompanyManagerFragment).hide(mCompanyMeetingRoomFragment).show(mCompanyDetailFragment).commit();
    }

    public void showCompanyManagerFragment() {
        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
        fragmentTransaction.hide(mCompanyDetailFragment).hide(mCompanyMeetingRoomFragment).show(mCompanyManagerFragment).commit();
    }

    public void showCompanyMeetingRoomFragment(){
        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
        fragmentTransaction.hide(mCompanyDetailFragment).hide(mCompanyManagerFragment).show(mCompanyMeetingRoomFragment).commit();
    }
}
