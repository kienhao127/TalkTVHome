package com.example.cpu11341_local.talktvhome;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
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
import com.example.cpu11341_local.talktvhome.data.Topic;
import com.example.cpu11341_local.talktvhome.data.User;
import com.example.cpu11341_local.talktvhome.data.Wrapper;
import com.example.cpu11341_local.talktvhome.fragment.ChatFragment;
import com.example.cpu11341_local.talktvhome.fragment.HomeFragment;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

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

//        MessageDataManager.getInstance().insertUser( new User(0, "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV"), getApplicationContext());
//        MessageDataManager.getInstance().insertUser( new User(1, "http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Thúy Chi"), getApplicationContext());
//        MessageDataManager.getInstance().insertUser( new User(2, "http://avatar1.cctalk.vn/csmtalk_user3/450425623?t=1502078349", "Trang Lady"), getApplicationContext());
//        MessageDataManager.getInstance().insertUser( new User(5, "http://is2.mzstatic.com/image/thumb/Purple127/v4/95/75/d9/9575d99b-8854-11cc-25ef-4aa4b4bb6dc3/source/1200x630bb.jpg", "Tui"), getApplicationContext());

        final Random r = new Random();
        final Handler handler = new Handler();
        final int delay = 10000; //milliseconds


//        handler.postDelayed(new Runnable(){
//            int i=0;
//            public void run(){
//                DateFormat df = new SimpleDateFormat("d/MM/yyyy HH:mm:ss");
//                String date = df.format(Calendar.getInstance().getTime());
//
//                MessageDetail messageDetail = new MessageDetail(3, MessageDataManager.getInstance().getUser("3", getBaseContext()),
//                        Calendar.getInstance().getTimeInMillis(), String.valueOf(i), false);
//                messageDetail.setTopicID(messageDetail.getUser().getId() +"_"+MessageDataManager.getInstance().getCurrentUser(getApplicationContext()).getId());
//                i++;
//
//                InsertMessageTask insertMessageTask = new InsertMessageTask();
//                insertMessageTask.execute(messageDetail);
//
//                handler.postDelayed(this, delay);
//            }
//        }, delay);
//
//        handler.postDelayed(new Runnable(){
//            int i=0;
//            public void run(){
//                DateFormat df = new SimpleDateFormat("d/MM/yyyy HH:mm:ss");
//                String date = df.format(Calendar.getInstance().getTime());
//
//                MessageDetail messageDetail = new MessageDetail(3, MessageDataManager.getInstance().getUser("2", getBaseContext()),
//                        Calendar.getInstance().getTimeInMillis(), String.valueOf(i), false);
//                messageDetail.setTopicID(messageDetail.getUser().getId() +"_"+MessageDataManager.getInstance().getCurrentUser(getApplicationContext()).getId());
//                i++;
//
//                InsertMessageTask insertMessageTask = new InsertMessageTask();
//                insertMessageTask.execute(messageDetail);
//
//                handler.postDelayed(this, delay);
//            }
//        }, delay);
//
//        MessageDetail messageDetail = new MessageDetail(1, new User("0", "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV"),
//                "Tên event 1", Calendar.getInstance().getTimeInMillis(), "http://talktv.vcdn.vn/talk/mobile/banner/ad_banner_75.jpg", "Mô tả", 1, "Xem chi tiết", "action_extra");
//        messageDetail.setTopicID(messageDetail.getUser().getId() +"_"+MessageDataManager.getInstance().getCurrentUser(getApplicationContext()).getId());
//        InsertMessageTask insertMessageTask = new InsertMessageTask();
//        insertMessageTask.execute(messageDetail);
//
//        messageDetail = new MessageDetail(2, new User("0", "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV"),
//                "Nhắc nhở 1",  Calendar.getInstance().getTimeInMillis(), "Nội dung nhắc nhở", 1, "Xem chi tiết", "action_extra");
//        messageDetail.setTopicID(messageDetail.getUser().getId() +"_"+MessageDataManager.getInstance().getCurrentUser(getApplicationContext()).getId());
//        InsertMessageTask insertMessageTask2 = new InsertMessageTask();
//        insertMessageTask2.execute(messageDetail);
//
//        messageDetail = new MessageDetail(3, new User("0", "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV"),
//                Calendar.getInstance().getTimeInMillis(), "Nội dung thông báo", false);
//        messageDetail.setTopicID(messageDetail.getUser().getId() +"_"+MessageDataManager.getInstance().getCurrentUser(getApplicationContext()).getId());
//        InsertMessageTask insertMessageTask3 = new InsertMessageTask();
//        insertMessageTask3.execute(messageDetail);
//
//        for (int i=0; i<5; i++) {
//            messageDetail = new MessageDetail(3, new User("1", "http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Thúy Chi"),
//                    Calendar.getInstance().getTimeInMillis(), String.valueOf(i), false);
//            messageDetail.setTopicID(messageDetail.getUser().getId() +"_"+MessageDataManager.getInstance().getCurrentUser(getApplicationContext()).getId());
//            InsertMessageTask insertMessageTask4 = new InsertMessageTask();
//            insertMessageTask4.execute(messageDetail);
//        }
//
//        for (int i=0; i<5; i++) {
//            messageDetail = new MessageDetail(3, new User("2", "http://avatar1.cctalk.vn/csmtalk_user3/450425623?t=1502078349", "Trang Lady"),
//                    Calendar.getInstance().getTimeInMillis(), String.valueOf(i), false);
//            messageDetail.setTopicID(messageDetail.getUser().getId() +"_"+MessageDataManager.getInstance().getCurrentUser(getApplicationContext()).getId());
//            InsertMessageTask insertMessageTask4 = new InsertMessageTask();
//            insertMessageTask4.execute(messageDetail);
//        }
//
//        for (int i=0; i<5; i++) {
//            messageDetail = new MessageDetail(3, new User("3", "http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Lady Gaga"),
//                    Calendar.getInstance().getTimeInMillis(), String.valueOf(i), false);
//            messageDetail.setTopicID(messageDetail.getUser().getId() +"_"+MessageDataManager.getInstance().getCurrentUser(getApplicationContext()).getId());
//            InsertMessageTask insertMessageTask4 = new InsertMessageTask();
//            insertMessageTask4.execute(messageDetail);
//        }

    }

    private class InsertMessageTask extends AsyncTask<MessageDetail, Void, Wrapper> {
        @Override
        protected Wrapper doInBackground(MessageDetail... messageDetail) {
            Wrapper wrapper = new Wrapper(MessageDataManager.getInstance().insertMessage(messageDetail[0], getApplicationContext()), messageDetail[0]);
            return wrapper;
        }

        @Override
        protected void onPostExecute(Wrapper wrapper) {
            super.onPostExecute(wrapper);
            if (MessageDataManager.getInstance().dataListener != null){
                MessageDataManager.getInstance().dataListener.onDataChanged(wrapper.getTopic(), wrapper.getMessageDetail());
            }
        }
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
    }
}
