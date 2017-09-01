package com.example.cpu11341_local.talktvhome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cpu11341_local.talktvhome.R;

import java.util.ArrayList;

/**
 * Created by CPU11341-local on 9/1/2017.
 */

public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageRecyclerAdapter.RecyclerViewHolder> {

    private Context context;
    private OnItemClickListener mItemClickListener;

    public MessageRecyclerAdapter(Context context){
        this.context = context;
    }


    @Override
    public MessageRecyclerAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_layout,parent,false);

        MessageRecyclerAdapter.RecyclerViewHolder recyclerViewHolder = new MessageRecyclerAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(MessageRecyclerAdapter.RecyclerViewHolder holder, final int position) {

        Glide.with(context)
                .load("http://avatar1.cctalk.vn/csmtalk_room/88")
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.grid_item))
                .apply(RequestOptions.errorOf(R.drawable.grid_item
                ))
                .into(holder.imageViewAvatar);
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final MessageRecyclerAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewAvatar;
        TextView textViewName;
        TextView textViewMessage;
        TextView textViewDate;

        public RecyclerViewHolder(View view){
            super(view);
            imageViewAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            textViewName = (TextView) view.findViewById(R.id.textViewName);
            textViewMessage = (TextView) view.findViewById(R.id.textViewMessage);
            textViewDate = (TextView) view.findViewById(R.id.textViewDate);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }
    }
}
