package com.example.cpu11341_local.talktvhome.fragment;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cpu11341_local.talktvhome.R;
import com.example.cpu11341_local.talktvhome.adapter.EmoticonsRecyclerAdapter;

import java.util.ArrayList;

/**
 * Created by CPU11341-local on 11/13/2017.
 */

public class EmoticonsFragment extends Fragment {
    int emoticonsCategory;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    TypedArray emotions;

    public EmoticonsFragment (int emoticonsCategory){
        this.emoticonsCategory = emoticonsCategory;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.emoticons_fragment, container, false);
        emotions = getResources().obtainTypedArray(emoticonsCategory);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        ArrayList<String> paths = new ArrayList<String>();
        for (short i = 0; i < emotions.length(); i++) {
            paths.add(emotions.getString(i) + ".png");
        }
        adapter = new EmoticonsRecyclerAdapter(getContext(), paths);
        layoutManager = new GridLayoutManager(getContext(), 7);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
