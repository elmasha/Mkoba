package com.el.mkoba;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MytransPagerAdapter extends FragmentPagerAdapter {

    private int numofTabs;


    public MytransPagerAdapter(FragmentManager supportFragmentManager, int numofTabs) {
        super(supportFragmentManager);
        this.numofTabs = numofTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new MyTransactionFragment();
            case 1:
                return new ConnectFragment();
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return 2;
    }
}
