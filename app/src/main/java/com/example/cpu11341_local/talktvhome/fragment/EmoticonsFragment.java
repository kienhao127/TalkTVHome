package com.example.cpu11341_local.talktvhome.fragment;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cpu11341_local.talktvhome.EmoticonUtil;
import com.example.cpu11341_local.talktvhome.R;
import com.example.cpu11341_local.talktvhome.adapter.EmoticonsRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by CPU11341-local on 11/13/2017.
 */

public class EmoticonsFragment extends Fragment {
    int emoticonsCategory;
    RecyclerView recyclerView;
    EmoticonsRecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    LinkedHashMap<String, Integer> emotions;
    EmoticonsRecyclerAdapter.EmoticonClickListener emoticonClickListener;

    public EmoticonsFragment (int emoticonsCategory, EmoticonsRecyclerAdapter.EmoticonClickListener emoticonClickListener){
        Log.d("EmoticonsFragment", "EmoticonsFragment");
        this.emoticonsCategory = emoticonsCategory;
        this.emoticonClickListener = emoticonClickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("EmoticonsFragment", "onCreateView");
        View view = inflater.inflate(R.layout.emoticons_fragment, container, false);

        emotions = EmoticonUtil.getInstance().getEmoticons(emoticonsCategory);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        adapter = new EmoticonsRecyclerAdapter(getContext(), emoticonClickListener, emotions);
        layoutManager = new GridLayoutManager(getContext(), 7);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }
}