package com.example.cpu11341_local.talktvhome.fragment;

/**
 * Created by CPU11341-local on 8/17/2017.
 */
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cpu11341_local.talktvhome.R;
import com.example.cpu11341_local.talktvhome.bannerview.Banner;
import com.example.cpu11341_local.talktvhome.adapter.HomeRecyclerAdapter;
import com.example.cpu11341_local.talktvhome.data.DocGrid;
import com.example.cpu11341_local.talktvhome.data.DocGridWithTitle;
import com.example.cpu11341_local.talktvhome.data.DocHorizon;
import com.example.cpu11341_local.talktvhome.data.DocTitle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    int tabID = 0;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<DocHorizon> arrHorList;
    ArrayList<DocGrid> arrDocGrid;
    ArrayList<DocGridWithTitle> arrDocGridWithTitle;
    ArrayList<Banner> arrBannerItems;
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

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("homeTest.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);

        arrHorList = new ArrayList<>();
        arrBannerItems = new ArrayList<>();
        arrDocGridWithTitle = new ArrayList<>();
        arrDocGrid = new ArrayList<>();

        String json = loadJSONFromAsset();
        JSONObject jsonResponse;
        try {
            jsonResponse = new JSONObject(json);
            JSONObject home = jsonResponse.getJSONObject("doc");
            JSONArray homeItems = home.getJSONArray("child");
            for(int i=0;i<homeItems.length();i++){
                JSONObject homeItem = homeItems.getJSONObject(i);
                switch (homeItem.getInt("type")){
                    case 1:{
                        JSONArray banners = homeItem.getJSONArray("child");
                        for (int iBanner = 0; iBanner < banners.length(); iBanner++){
                            JSONObject banner = banners.getJSONObject(iBanner);
                            arrBannerItems.add(new Banner(banner.getString("thumbnail"), banner.getInt("roomId"), banner.getInt("adId"), banner.getInt("actionType")));
                        }
                        break;
                    }
                    case 2:{
                        JSONArray HoriListItems = homeItem.getJSONArray("child");
                        for (int iHori = 0; iHori < HoriListItems.length(); iHori++){
                            JSONObject HoriListItem = HoriListItems.getJSONObject(iHori);
                            if (HoriListItem.getInt("actionType") == 1){
                                arrHorList.add(new DocHorizon(HoriListItem.getInt("actionType"), HoriListItem.getInt("backendId"), HoriListItem.getString("avatar"),
                                        HoriListItem.getString("title"), HoriListItem.getInt("type"), HoriListItem.getInt("roomType"), HoriListItem.getInt("roomId")));
                            } else {
                                arrHorList.add(new DocHorizon(HoriListItem.getInt("actionType"),HoriListItem.getInt("backendId"), HoriListItem.getString("pageUrl"),
                                        HoriListItem.getString("avatar"), HoriListItem.getString("title"), HoriListItem.getInt("type")));
                            }
                        }
                        break;
                    }
                    case 3:{
                        DocTitle docTitle = new DocTitle(homeItem.getInt("actionType"), homeItem.getInt("backendId"), homeItem.getString("pageUrl"),
                                homeItem.getInt("type"), homeItem.getString("title"));
                        i++;
                        homeItem = homeItems.getJSONObject(i);

                        while (homeItem.getInt("type") != 3 && i < homeItems.length()){
                            arrDocGrid.add(new DocGrid(homeItem.getInt("actionType"), homeItem.getString("thumbnail"), homeItem.getInt("viewers"), homeItem.getString("name"),
                                    homeItem.getInt("backendId"), homeItem.getString("title"), homeItem.getInt("type"), homeItem.getInt("roomType"),
                                    homeItem.getInt("roomId")));
                            i++;
                            if (i != homeItems.length()){
                                homeItem = homeItems.getJSONObject(i);
                            }
                        }
                        arrDocGridWithTitle.add(new DocGridWithTitle(docTitle, new ArrayList<DocGrid>(arrDocGrid)));
                        arrDocGrid.clear();
                        i--;
                        break;
                    }
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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