package com.example.cpu11341_local.talktvhome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.cpu11341_local.talktvhome.adapter.GridRecyclerAdapter;
import com.example.cpu11341_local.talktvhome.data.DocGrid;
import com.example.cpu11341_local.talktvhome.data.DocGridWithTitle;
import com.example.cpu11341_local.talktvhome.data.DocHorizon;

import java.util.ArrayList;

public class CategoryDetailActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<DocGrid> arrDocGrid;
    ArrayList<DocGridWithTitle> arrDocGridWithTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        String ImgURL = "http://i.imgur.com/XMhtBg0.png";
        arrDocGrid = new ArrayList<>();
        arrDocGrid.add(new DocGrid(ImgURL, "TalkTV 47", 1));
        arrDocGrid.add(new DocGrid(ImgURL, "TalkTV 48", 1));
        arrDocGrid.add(new DocGrid(ImgURL, "TalkTV 49", 1));
        arrDocGrid.add(new DocGrid(ImgURL, "TalkTV 50", 1));
        arrDocGrid.add(new DocGrid(ImgURL, "TalkTV 51", 1));
        arrDocGrid.add(new DocGrid(ImgURL, "TalkTV 52", 1));

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCatDetail);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new GridRecyclerAdapter(this, arrDocGrid);
        recyclerView.setAdapter(adapter);
    }
}
