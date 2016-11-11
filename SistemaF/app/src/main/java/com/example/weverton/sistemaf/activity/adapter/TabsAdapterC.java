package com.example.weverton.sistemaf.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.weverton.sistemaf.activity.fragments.categoria.CategoriaFragmentsC;
import com.example.weverton.sistemaf.activity.fragments.categoria.CategoriaFragmentsT;
import com.example.weverton.sistemaf.activity.fragments.despesa.DespesaFragmentsC;
import com.example.weverton.sistemaf.activity.fragments.despesa.DespesaFragmentsT;

public class TabsAdapterC extends FragmentPagerAdapter {
    public TabsAdapterC(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int idx) {
        if (idx == 0) {
            return new CategoriaFragmentsC();
        }
        return new CategoriaFragmentsT();
    }

}
