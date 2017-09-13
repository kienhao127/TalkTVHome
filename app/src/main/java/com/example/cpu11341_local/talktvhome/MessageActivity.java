package com.example.cpu11341_local.talktvhome;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;

import com.example.cpu11341_local.talktvhome.data.Topic;
import com.example.cpu11341_local.talktvhome.fragment.MessageFragment;

import java.util.ArrayList;
import java.util.Date;

public class MessageActivity extends AppCompatActivity {
    MessageFragment messFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        overridePendingTransition(R.anim.enter_from_right, R.anim.hold);

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            messFragment = new MessageFragment("Tin nhắn", true, getClass().getName());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, messFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
        overridePendingTransition(0, R.anim.exit_to_right);
        if (getSupportFragmentManager().getBackStackEntryCount() > 1){
            MessageFragment messageFragment =  (MessageFragment) getSupportFragmentManager().findFragmentByTag("MessFrag");
            if (messageFragment != null){
                messageFragment.onResume();
            }
        } else {
            this.onResume();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        messFragment.onResume();
    }
}
