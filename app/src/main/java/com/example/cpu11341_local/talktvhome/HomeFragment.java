package com.example.cpu11341_local.talktvhome;

/**
 * Created by CPU11341-local on 8/17/2017.
 */
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cpu11341_local.talktvhome.bannerview.Banner;
import com.example.cpu11341_local.talktvhome.bannerview.BannerView;
import com.example.cpu11341_local.talktvhome.adapter.DocGridRecyclerAdapter;
import com.example.cpu11341_local.talktvhome.adapter.DocHorlistRecyclerAdapter;
import com.example.cpu11341_local.talktvhome.adapter.HomeRecyclerAdapter;
import com.example.cpu11341_local.talktvhome.data.DocGrid;
import com.example.cpu11341_local.talktvhome.data.DocGridWithTitle;
import com.example.cpu11341_local.talktvhome.data.DocHorizon;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    int tabID = 0;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<DocHorizon> arrHorList;
    ArrayList<DocGrid> arrDocGrid;
    ArrayList<DocGridWithTitle> arrDocGridWithTitle;
    ArrayList<Banner> arrBannerItems = new ArrayList<>();
    Parcelable state;

    public static HomeFragment newInstance(int someInt) {
        HomeFragment myFragment = new HomeFragment();

        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabID = getArguments().getInt("someInt");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);
        String ImgURL = "http://i.imgur.com/BLwlT6v.png";
        arrHorList = new ArrayList<>();
        arrHorList.add(new DocHorizon(ImgURL, "LOL", null, 1));
        arrHorList.add(new DocHorizon(ImgURL, "88", 88, null));
        arrHorList.add(new DocHorizon(ImgURL, "CFM", null, 2));
        arrHorList.add(new DocHorizon(ImgURL, "99", 99, null));
        arrHorList.add(new DocHorizon(ImgURL, "Liên Quân", null, 3));
        arrHorList.add(new DocHorizon(ImgURL, "69", 69, null));
        arrHorList.add(new DocHorizon(ImgURL, "Khác", null, 4));

        arrDocGrid = new ArrayList<>();
        arrDocGrid.add(new DocGrid(ImgURL, "TalkTV 47", 47, null));
        arrDocGrid.add(new DocGrid(ImgURL, "TalkTV 48", 48, null));
        arrDocGrid.add(new DocGrid(ImgURL, "TalkTV 49", 49, null));
        arrDocGrid.add(new DocGrid(ImgURL, "Offine video 1", null, 1));
        arrDocGrid.add(new DocGrid(ImgURL, "TalkTV 51", 51, null));
        arrDocGrid.add(new DocGrid(ImgURL, "Offine video 2", null, 2));

        arrDocGridWithTitle = new ArrayList<>();
        arrDocGridWithTitle.add(new DocGridWithTitle("Nổi bật", arrDocGrid));
        arrDocGridWithTitle.add(new DocGridWithTitle("Liên Quân", arrDocGrid));
        arrDocGridWithTitle.add(new DocGridWithTitle("Liên Minh Huyền thoại", arrDocGrid));

        arrBannerItems.add(new Banner("http://i.imgur.com/A8jcSkq.png", 69));
        arrBannerItems.add(new Banner("http://i.imgur.com/hNZLvPT.png", "www.google.com"));
        arrBannerItems.add(new Banner("http://i.imgur.com/XfoVEXv.png", 96));
        arrBannerItems.add(new Banner("http://i.imgur.com/0IUuV4e.png", "www.facebook.com"));

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewHome);
        if (tabID == 0){
            adapter = new HomeRecyclerAdapter(tabID, arrBannerItems, arrHorList, arrDocGridWithTitle);
        } else {
            adapter = new HomeRecyclerAdapter(tabID, arrHorList, arrDocGridWithTitle);
        }

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (state != null) {
            layoutManager.onRestoreInstanceState(state);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        state = layoutManager.onSaveInstanceState();

    }
}