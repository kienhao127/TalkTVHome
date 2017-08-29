package com.example.cpu11341_local.talktvhome.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cpu11341_local.talktvhome.CategoryDetailActivity;
import com.example.cpu11341_local.talktvhome.OpenRoomActivity;
import com.example.cpu11341_local.talktvhome.R;
import com.example.cpu11341_local.talktvhome.data.DocGrid;
import com.example.cpu11341_local.talktvhome.data.DocHorizon;

import java.util.ArrayList;

/**
 * Created by CPU11341-local on 8/18/2017.
 */

public class GridRecyclerAdapter extends RecyclerView.Adapter<GridRecyclerAdapter.RecyclerViewHolder> {
    private static ArrayList<DocGrid> arrGridItem = new ArrayList<DocGrid>();
    private Context context;
    private OnItemClickListener mItemClickListener;

    public GridRecyclerAdapter(Context context){
        this.context = context;
    }

    public GridRecyclerAdapter(Context context, ArrayList<DocGrid> arrHorList){
        this.arrGridItem = arrHorList;
        this.context = context;
    }

    @Override
    public GridRecyclerAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_layout,parent,false);

        final GridRecyclerAdapter.RecyclerViewHolder recyclerViewHolder = new GridRecyclerAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(GridRecyclerAdapter.RecyclerViewHolder holder, final int position) {
        final DocGrid docGrid = arrGridItem.get(position);

        Glide.with(context)
                .load(docGrid.getThumbnail())
                .apply(RequestOptions.placeholderOf(R.drawable.grid_item))
                .apply(RequestOptions.errorOf(R.drawable.grid_item
                ))
                .into(holder.imageViewThumb);
        holder.textViewChannelName.setText(docGrid.getTitle());
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public int getItemCount() {
        return arrGridItem.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewThumb;
        TextView textViewChannelName;
        public RecyclerViewHolder(View view){
            super(view);
            imageViewThumb = (ImageView) view.findViewById(R.id.imageViewThumb);
            textViewChannelName = (TextView) view.findViewById(R.id.textViewChannelName);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    public void setData(ArrayList<DocGrid> data) {
        if (arrGridItem != data) {
            arrGridItem = data;
//            notifyDataSetChanged();
        }
    }
}