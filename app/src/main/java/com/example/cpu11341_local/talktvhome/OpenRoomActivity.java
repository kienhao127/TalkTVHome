package com.example.cpu11341_local.talktvhome;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.example.cpu11341_local.talktvhome.data.Topic;
import com.example.cpu11341_local.talktvhome.fragment.ChatFragment;
import com.example.cpu11341_local.talktvhome.fragment.MessageFragment;

import java.util.ArrayList;

public class OpenRoomActivity extends AppCompatActivity {
    ImageView imageView;
    boolean isLayoutGone = true;
    boolean isMessShowed = false;
    MessageFragment messFragment;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_room);

        TextView textView = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        int roomID = intent.getIntExtra("RoomID", -1);
        if (roomID != -1){
            textView.setText("Room ID = " + String.valueOf(roomID));
        } else {
            int offlineVideoID = intent.getIntExtra("OfflineVideoID", -1);
            textView.setText("Offline Video ID = " + String.valueOf(offlineVideoID));
        }

        findViewById(R.id.relativeLayout).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                    isMessShowed = true;
                }
            }
        });

        imageView = (ImageView) findViewById(R.id.messageShow);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLayoutGone = false;
                FrameLayout fl = (FrameLayout) findViewById(R.id.fragment_container);
                fl.getLayoutParams().height = Resources.getSystem().getDisplayMetrics().heightPixels / 2;
                fl.setVisibility(fl.VISIBLE);

                if (findViewById(R.id.fragment_container) != null) {

                    // However, if we're being restored from a previous state,
                    // then we don't need to do anything and should return or else
                    // we could end up with overlapping fragments.
                    if (savedInstanceState != null) {
                        return;
                    }

                    // Add the fragment to the 'fragment_container' FrameLayout
                    ChatFragment chatFragment = new ChatFragment("Tên user", 1);
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_bottom, 0, 0, R.anim.exit_to_bottom)
                            .add(R.id.fragment_container, chatFragment)
                            .addToBackStack(null)
                            .commit();
                    isMessShowed = false;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!isMessShowed) {
            getSupportFragmentManager().popBackStack();

            messFragment = new MessageFragment("Tin nhắn", true, getClass().getName());
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_bottom, 0, 0, R.anim.exit_to_bottom)
                    .add(R.id.fragment_container, messFragment)
                    .commit();
            isMessShowed = true;
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0){
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
            if (getSupportFragmentManager().getBackStackEntryCount() > 1){
                MessageFragment messageFragment =  (MessageFragment) getSupportFragmentManager().findFragmentByTag("MessFrag");
                if (messageFragment != null){
                    messageFragment.onResume();
                }
            } else {
                this.onResume();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (messFragment != null){
            messFragment.onResume();
        }
    }
}
