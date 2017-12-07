package com.example.cpu11341_local.talktvhome;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cpu11341_local.talktvhome.data.Topic;
import com.example.cpu11341_local.talktvhome.data.User;
import com.example.cpu11341_local.talktvhome.fragment.ChatFragment;
import com.example.cpu11341_local.talktvhome.fragment.TopicFragment;

public class OpenRoomActivity extends AppCompatActivity {
    ImageView imageView;
    boolean isLayoutGone = true;
    boolean isMessShowed = false;
    TopicFragment messFragment;
    ImageView imageViewEmoticon;
    String idolID;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_room);

        TextView textView = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        int roomID = intent.getIntExtra("RoomID", -1);
        idolID = "2";
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
                    MessageDataManager.getInstance().setDataListener(null);
                    isMessShowed = false;
                }
            }
        });
        frameLayout = (FrameLayout) findViewById(R.id.fragment_container);

        imageView = (ImageView) findViewById(R.id.messageShow);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLayoutGone = false;

                frameLayout.getLayoutParams().height = Resources.getSystem().getDisplayMetrics().heightPixels / 2;

                if (findViewById(R.id.fragment_container) != null) {

                    // However, if we're being restored from a previous state,
                    // then we don't need to do anything and should return or else
                    // we could end up with overlapping fragments.
                    if (savedInstanceState != null) {
                        return;
                    }

                    // Add the fragment to the 'fragment_container' FrameLayout
                    User user = new User("2", "http://avatar1.cctalk.vn/csmtalk_user3/450425623?t=1502078349", "Trang Lady");
                    String topidID = user.getId() + "_" + MessageDataManager.getInstance().getCurrentUser(getApplicationContext()).getId();
                    Topic topic = MessageDataManager.getInstance().getTopic(topidID, getApplicationContext());
                    if (topic.getUser() == null) {
                        topic = new Topic(user, "", 0, 3, topidID, false, false);
                    }
                    ChatFragment chatFragment = new ChatFragment(topic);
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_bottom, 0, 0, R.anim.exit_to_bottom)
                            .add(R.id.fragment_container, chatFragment)
                            .addToBackStack(null)
                            .commit();
                    isMessShowed = true;
                }
            }
        });
    }

    protected OnBackPressedListener onBackPressedListener;

    public interface OnBackPressedListener {
        void doBack();
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null) {
            onBackPressedListener.doBack();
//            super.onBackPressed();
        } else {
            if (isMessShowed) {
                getSupportFragmentManager().popBackStack();

                messFragment = new TopicFragment("Tin nhắn", true, getClass().getName());
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_bottom, 0, 0, R.anim.exit_to_bottom)
                        .add(R.id.fragment_container, messFragment, "TopicFrag")
                        .addToBackStack(null)
                        .commit();
                isMessShowed = false;
            }
            else {
                super.onBackPressed();
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
