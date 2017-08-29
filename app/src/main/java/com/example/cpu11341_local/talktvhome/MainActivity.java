package com.example.cpu11341_local.talktvhome;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.cpu11341_local.talktvhome.bannerview.Banner;
import com.example.cpu11341_local.talktvhome.bannerview.BannerView;
import com.example.cpu11341_local.talktvhome.data.TabData;
import com.example.cpu11341_local.talktvhome.data.doctype;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    TabData tab[] = {new TabData(0, "Hot", new doctype[]{doctype.BANNER, doctype.HORIZONLIST, doctype.GRIDLIST}),
            new TabData(1, "Mobile", new doctype[]{doctype.HORIZONLIST, doctype.GRIDLIST}),
            new TabData(2, "PC", new doctype[]{doctype.HORIZONLIST, doctype.GRIDLIST}),
            new TabData(3, "Show", new doctype[]{doctype.HORIZONLIST, doctype.GRIDLIST}),
            new TabData(4, "...", new doctype[]{doctype.HORIZONLIST, doctype.GRIDLIST})};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(tab.length);
        viewPager.setAdapter(new CustomAdapter(getSupportFragmentManager(), getApplicationContext()));

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);


//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//        });

    }


    private class CustomAdapter extends FragmentPagerAdapter {

        public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return new HomeFragment().newInstance(position);
        }

        @Override
        public int getCount() {
            return tab.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tab[position].getTitle();
        }
    }
}
