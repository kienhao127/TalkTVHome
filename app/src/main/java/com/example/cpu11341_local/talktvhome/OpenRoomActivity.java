package com.example.cpu11341_local.talktvhome;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class OpenRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_room);

        TextView textView = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        int roomID = intent.getIntExtra("RoomID", 0);
        textView.setText("Room ID = " + String.valueOf(roomID));
    }

}
