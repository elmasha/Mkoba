package com.el.mkoba;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    private int numofTabs;


    public PagerAdapter(FragmentManager supportFragmentManager, int numofTabs) {
        super(supportFragmentManager);
        this.numofTabs = numofTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new Create_payFragment();
            case 1:
                return new PendingPay_Fragment();
            case 2:
                return new Past_payFragment();
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return 3;
    }
}
