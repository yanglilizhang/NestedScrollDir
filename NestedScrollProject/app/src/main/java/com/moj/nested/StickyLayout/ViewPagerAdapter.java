package com.moj.nested.StickyLayout;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGER_COUNT = 5;
    private String tabTitles[] = new String[]{"我的", "分享", "收藏", "关注", "关注者"};
    private Context mContext;

    public ViewPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
