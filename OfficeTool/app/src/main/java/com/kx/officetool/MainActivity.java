package com.kx.officetool;

import android.content.res.TypedArray;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kx.officetool.fragment.ContactFragment;
import com.kx.officetool.fragment.MessageFragment;
import com.kx.officetool.fragment.PersonalFragment;
import com.kx.officetool.fragment.WorkFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
        final List<Fragment> fragmentList = new ArrayList<>();
        final String[] textArrays = getResources().getStringArray(R.array.mainpage_indicates_str);
        final int[] mDrawableArrays = new int[textArrays.length];
        TypedArray typedArray = getResources().obtainTypedArray(R.array.mainpage_indicates_icon);
        getResources().getIntArray(R.array.mainpage_indicates_icon);
        fragmentList.add(new MessageFragment());
        fragmentList.add(new ContactFragment());
        fragmentList.add(new WorkFragment());
        fragmentList.add(new PersonalFragment());
        for (int i = 0; i < textArrays.length; i++) {
            mDrawableArrays[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
        PagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return textArrays[position];
            }
        };
        viewpager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewpager);
        for (int i = 0; i < textArrays.length; i++) {
            tabLayout.getTabAt(i).setIcon(mDrawableArrays[i]);
        }

    }

}
