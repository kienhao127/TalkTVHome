package com.example.cpu11341_local.talktvhome;

/**
 * Created by CPU11341-local on 9/1/2017.
 */

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.cpu11341_local.talktvhome.data.DocGrid;

import java.util.ArrayList;

public class CategoryDetailActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<DocGrid> arrDocGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        Intent intent = getIntent();
        String pageUrl = intent.getStringExtra("PageUrl");

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(pageUrl);
    }

}
