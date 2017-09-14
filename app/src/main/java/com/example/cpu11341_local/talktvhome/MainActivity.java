package com.example.cpu11341_local.talktvhome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.cpu11341_local.talktvhome.data.MessageDetail;
import com.example.cpu11341_local.talktvhome.data.TabData;
import com.example.cpu11341_local.talktvhome.data.User;
import com.example.cpu11341_local.talktvhome.fragment.ChatFragment;
import com.example.cpu11341_local.talktvhome.fragment.HomeFragment;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    TextView mTitle;
    TabData tab[] = {new TabData(0, "Hot", new int[]{1, 2, 3}),
            new TabData(1, "Mobile", new int[]{2, 3}),
            new TabData(2, "PC", new int[]{2, 3}),
            new TabData(3, "Show", new int[]{2, 3}),
            new TabData(4, "...", new int[]{2, 3})};
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(tab.length);
        viewPager.setAdapter(new CustomAdapter(getSupportFragmentManager(), getApplicationContext()));

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);

        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final Random r = new Random();


        final Handler handler1 = new Handler();
        final Handler handler2 = new Handler();
        final int delay = 5000; //milliseconds

//        handler.postDelayed(new Runnable(){
//            public void run(){
//                DateFormat df = new SimpleDateFormat("d/MM/yyyy HH:mm:ss");
//                String date = df.format(Calendar.getInstance().getTime());
//
//                MessageDetail messageDetail = new MessageDetail(3, 2, new User(2, "http://avatar1.cctalk.vn/csmtalk_user3/450425623?t=1502078349", "Trang Lady"),
//                        date, String.valueOf(r.nextInt(100)), false);
//                MessageDataManager.getInstance().insertMessage(messageDetail, isSameChatFragment(2));
//                handler.postDelayed(this, delay);
//            }
//        }, delay);
//
//        handler1.postDelayed(new Runnable(){
//            public void run(){
//                DateFormat df = new SimpleDateFormat("d/MM/yyyy HH:mm:ss");
//                String date = df.format(Calendar.getInstance().getTime());
//
//                MessageDetail messageDetail = new MessageDetail(3, 2, new User(1, "http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Thúy Chi"),
//                        date, String.valueOf(r.nextInt(100)), false);
//                MessageDataManager.getInstance().insertMessage(messageDetail, isSameChatFragment(1));
//                handler1.postDelayed(this, delay);
//            }
//        }, 15000);

        handler2.postDelayed(new Runnable(){
            public void run(){
                DateFormat df = new SimpleDateFormat("d/MM/yyyy HH:mm:ss");
                df.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
                String date = df.format(Calendar.getInstance().getTime());

                MessageDetail messageDetail = new MessageDetail(3, 2, new User(4, "http://avatar1.cctalk.vn/csmtalk_user3/453014912?t=1502427610", "LiLaa ❤"),
                        date, String.valueOf(r.nextInt(100)), false);
                MessageDataManager.getInstance().insertMessage(messageDetail, isSameChatFragment(1));
                handler2.postDelayed(this, delay);
            }
        }, 5000);
    }

    boolean isSameChatFragment(int senderID){
        ChatFragment chatFragment =  (ChatFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (chatFragment != null){
            if (chatFragment.getSenderID() == senderID){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_message:
                Intent intent = new Intent(this, MessageActivity.class);
                this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        MessageDataManager.getInstance().setDataListener(null);
    }
}
