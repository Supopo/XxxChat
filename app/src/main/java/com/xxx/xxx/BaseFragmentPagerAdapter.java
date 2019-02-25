package com.xxx.xxx;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list = new ArrayList<>();
    private String[] titles;


    public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titles) {
        super(fm);
        this.list = fragmentList;
        this.titles = titles;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null || titles.length > position) {
            return titles[position];
        }
        return "";
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
