package com.example.cpu11341_local.talktvhome;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cpu11341_local.talktvhome.adapter.MessageRecyclerAdapter;

/**
 * Created by CPU11341-local on 9/1/2017.
 */

public class MessageFragment extends android.support.v4.app.Fragment {

    RecyclerView messRecyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Toolbar toolbar;
    TextView mTitle;
    boolean isActivity;

    public MessageFragment(boolean isActivity) {
        this.isActivity = isActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragment,container,false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        mTitle.setText(R.string.toolbar_title_mess);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (isActivity){
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        messRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMessage);
        adapter = new MessageRecyclerAdapter(getContext());

        layoutManager = new LinearLayoutManager(getContext());
        messRecyclerView.setLayoutManager(layoutManager);
        messRecyclerView.setAdapter(adapter);
        return view;
    }
}
