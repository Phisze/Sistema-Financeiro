package com.example.weverton.sistemaf.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.weverton.sistemaf.R;
import com.example.weverton.sistemaf.activity.adapter.MyTabListener;
import com.example.weverton.sistemaf.activity.adapter.TabsAdapterD;

public class DespesaActivity  extends AppCompatActivity {


    private ViewPager viewPager;//usado para fazer o evento com efeito ao clicar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa);

        // ViewPager
        viewPager = (ViewPager) findViewById(R.id.view);
        viewPager.setAdapter(new TabsAdapterD(getSupportFragmentManager()));

        // Configura as Tabs
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText("Cadastro").setTabListener(new MyTabListener(viewPager, 0)));
        actionBar.addTab(actionBar.newTab().setText("Suas Despesas").setTabListener(new MyTabListener(viewPager, 1)));

        // Se o ViewPager troca de página, atualiza a Tab.
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int idx) {
                // Se fizer swipe no ViewPager, atualiza a tab
                actionBar.setSelectedNavigationItem(idx);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
    }
}
