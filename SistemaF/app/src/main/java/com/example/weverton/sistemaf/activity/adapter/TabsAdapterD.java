package com.example.weverton.sistemaf.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.weverton.sistemaf.activity.fragments.despesa.DespesaFragmentsC;
import com.example.weverton.sistemaf.activity.fragments.despesa.DespesaFragmentsT;

public class TabsAdapterD extends FragmentPagerAdapter {
    public TabsAdapterD(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int idx) {
        if (idx == 0) {
            return new DespesaFragmentsC();
        }
        return new DespesaFragmentsT();
    }

}
