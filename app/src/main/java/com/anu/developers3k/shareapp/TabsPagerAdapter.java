package com.anu.developers3k.shareapp;

/**
 * Created by Anuraj R(a4anurajr@gmail.com) on 8/8/2017.
 */


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new UserAppsFragment();
            case 1:
                return new SystemAppsFragment();
            case 2:
                return new AboutUs();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}
