package com.example.cpu11341_local.talktvhome.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cpu11341_local.talktvhome.bannerview.Banner;
import com.example.cpu11341_local.talktvhome.bannerview.BannerView;
import com.example.cpu11341_local.talktvhome.CategoryDetailActivity;
import com.example.cpu11341_local.talktvhome.OpenRoomActivity;
import com.example.cpu11341_local.talktvhome.R;
import com.example.cpu11341_local.talktvhome.data.DocGridWithTitle;
import com.example.cpu11341_local.talktvhome.data.DocHorizon;

import java.util.ArrayList;

/**
 * Created by CPU11341-local on 8/21/2017.
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.FirstRVHolder> {
    private ArrayList<DocGridWithTitle> arrDocGrid = new ArrayList<>();
    private ArrayList<DocHorizon> arrDocHori = new ArrayList<>();
    private ArrayList<Banner> arrBannerItems = new ArrayList<>();
    private int tabID;

    public HomeRecyclerAdapter(int tabID, ArrayList<Banner> arrBannerItems, ArrayList<DocHorizon> arrDocHori, ArrayList<DocGridWithTitle> arrDocGrid){
        this.arrDocGrid = arrDocGrid;
        this.arrDocHori = arrDocHori;
        this.arrBannerItems = arrBannerItems;
        this.tabID = tabID;
    }

    public HomeRecyclerAdapter(int tabID, ArrayList<DocHorizon> arrDocHori, ArrayList<DocGridWithTitle> arrDocGrid){
        this.arrDocGrid = arrDocGrid;
        this.arrDocHori = arrDocHori;
        this.tabID = tabID;
    }

    @Override
    public int getItemViewType(int position) {
        return tabID;
    }

    @Override
    public HomeRecyclerAdapter.FirstRVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:{
                return new FirstRVHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.first_tab_layout,parent,false));
            }
            default:{
                return new OtherRVHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.other_tab_layout,parent,false));
            }
        }
    }

    @Override
    public void onBindViewHolder(HomeRecyclerAdapter.FirstRVHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case 0:
                final FirstRVHolder firstRVHolder = (FirstRVHolder) holder;
                firstRVHolder.bannerView.addBannerItems(arrBannerItems);
                firstRVHolder.bannerView.setInfiniteSlide(true);
                firstRVHolder.docHorlistRecyclerAdapter.setData(arrDocHori);
                firstRVHolder.docGridRecyclerAdapter.setData(arrDocGrid);
                break;
            default:
                OtherRVHolder otherRVHolder = (OtherRVHolder) holder;
                otherRVHolder.docHorlistRecyclerAdapter.setData(arrDocHori);
                otherRVHolder.docGridRecyclerAdapter.setData(arrDocGrid);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class FirstRVHolder extends RecyclerView.ViewHolder{
        private BannerView bannerView;
        private RecyclerView horlistRecyclerView;
        private RecyclerView gridRecyclerView;
        private DocHorlistRecyclerAdapter docHorlistRecyclerAdapter;
        private DocGridRecyclerAdapter docGridRecyclerAdapter;

        public FirstRVHolder(View view) {
            super(view);
            Context context = itemView.getContext();

            bannerView = (BannerView) itemView.findViewById(R.id.bannerView);
            horlistRecyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerViewHorList);
            gridRecyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerViewDocGrid);

            horlistRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            docHorlistRecyclerAdapter = new DocHorlistRecyclerAdapter(context);
            horlistRecyclerView.setAdapter(docHorlistRecyclerAdapter);

            gridRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            docGridRecyclerAdapter = new DocGridRecyclerAdapter();
            gridRecyclerView.setAdapter(docGridRecyclerAdapter);

        }
    }

    public static class OtherRVHolder extends FirstRVHolder{
        private RecyclerView horlistRecyclerView;
        private RecyclerView gridRecyclerView;
        private DocHorlistRecyclerAdapter docHorlistRecyclerAdapter;
        private DocGridRecyclerAdapter docGridRecyclerAdapter;

        public OtherRVHolder(View view){
            super(view);
            Context context = itemView.getContext();

            horlistRecyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerViewHorList);
            gridRecyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerViewDocGrid);

            horlistRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            docHorlistRecyclerAdapter = new DocHorlistRecyclerAdapter(context);
            horlistRecyclerView.setAdapter(docHorlistRecyclerAdapter);

            gridRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            gridRecyclerView.setHasFixedSize(true);
            docGridRecyclerAdapter = new DocGridRecyclerAdapter();
            gridRecyclerView.setAdapter(docGridRecyclerAdapter);
        }
    }
}
