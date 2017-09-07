package com.example.cpu11341_local.talktvhome;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cpu11341_local.talktvhome.data.Topic;
import com.example.cpu11341_local.talktvhome.fragment.MessageFragment;

import java.util.ArrayList;

public class OpenRoomActivity extends AppCompatActivity {
    ImageView imageView;
    boolean isLayoutGone = true;

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

                    // Create a new Fragment to be placed in the activity layout
                    MessageFragment messFragment = new MessageFragment("Tin nhắn", true);

                    // In case this activity was started with special instructions from an
                    // Intent, pass the Intent's extras to the fragment as arguments
                    messFragment.setArguments(getIntent().getExtras());

                    // Add the fragment to the 'fragment_container' FrameLayout
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, messFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

}
