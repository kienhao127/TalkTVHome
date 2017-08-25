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

import com.example.cpu11341_local.talktvhome.CategoryDetailActivity;
import com.example.cpu11341_local.talktvhome.OpenRoomActivity;
import com.example.cpu11341_local.talktvhome.R;
import com.example.cpu11341_local.talktvhome.data.DocGrid;
import com.example.cpu11341_local.talktvhome.data.DocHorizon;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

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

        GridRecyclerAdapter.RecyclerViewHolder recyclerViewHolder = new GridRecyclerAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(GridRecyclerAdapter.RecyclerViewHolder holder, int position) {
        final DocGrid docGrid = arrGridItem.get(position);

        ImageLoader.getInstance().displayImage(docGrid.getImageURL(), holder.imageViewThumb);

        holder.textViewChannelName.setText(docGrid.getChannelName());
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position, String id);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public int getItemCount() {
        return arrGridItem.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            int pos = getAdapterPosition();
            Context context = v.getContext();
            Intent intent = new Intent();
            if (arrGridItem.get(pos).getAction_type() == 1){
                intent = new Intent(context, OpenRoomActivity.class);
                intent.putExtra("RoomID", arrGridItem.get(pos).getRoomID());
            } else {
                intent = new Intent(context, OpenRoomActivity.class);
                intent.putExtra("OfflineVideoID", arrGridItem.get(pos).getOfflineVideoID());
            }

            context.startActivity(intent);
        }
    }

    public void setData(ArrayList<DocGrid> data) {
        if (arrGridItem != data) {
            arrGridItem = data;
            notifyDataSetChanged();
        }
    }
}