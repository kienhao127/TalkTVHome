package com.example.cpu11341_local.talktvhome.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cpu11341_local.talktvhome.ElapsedTime;
import com.example.cpu11341_local.talktvhome.R;
import com.example.cpu11341_local.talktvhome.data.Topic;

import java.util.ArrayList;

/**
 * Created by CPU11341-local on 9/1/2017.
 */

public class TopicRecyclerAdapter extends RecyclerView.Adapter<TopicRecyclerAdapter.RecyclerViewHolder> {

    private Context context;
    private OnItemClickListener mItemClickListener;
    ArrayList<Topic> arrTopic = new ArrayList<>();

    public TopicRecyclerAdapter(Context context, ArrayList<Topic> arrTopic){
        this.context = context;
        this.arrTopic = arrTopic;
    }


    @Override
    public TopicRecyclerAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_layout,parent,false);

        TopicRecyclerAdapter.RecyclerViewHolder recyclerViewHolder = new TopicRecyclerAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(TopicRecyclerAdapter.RecyclerViewHolder holder, final int position) {
        Glide.with(context)
                .load(arrTopic.get(position).getAvatar())
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.grid_item))
                .apply(RequestOptions.errorOf(R.drawable.grid_item
                ))
                .into(holder.imageViewAvatar);
        holder.textViewName.setText(arrTopic.get(position).getName());
        holder.textViewLastMess.setText(arrTopic.get(position).getLastMess());
        holder.textViewDate.setText(ElapsedTime.getRelativeTimeSpanString(arrTopic.get(position).getDate()));
        if (arrTopic.get(position).isHasNewMessage()){
            holder.imageViewUnreadDot.setImageResource(R.drawable.unread_dot);
            holder.textViewDate.setTypeface(null, Typeface.BOLD);
            holder.textViewLastMess.setTypeface(null, Typeface.BOLD);
            holder.textViewName.setTypeface(null, Typeface.BOLD);
        }
        holder.setIsRecyclable(false);
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final TopicRecyclerAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public int getItemCount() {
        return arrTopic.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewAvatar;
        TextView textViewName;
        TextView textViewLastMess;
        TextView textViewDate;
        ImageView imageViewUnreadDot;

        public RecyclerViewHolder(View view){
            super(view);
            imageViewAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            textViewName = (TextView) view.findViewById(R.id.textViewName);
            textViewLastMess = (TextView) view.findViewById(R.id.textViewLastMess);
            textViewDate = (TextView) view.findViewById(R.id.textViewDate);
            imageViewUnreadDot = (ImageView) view.findViewById(R.id.imageViewUnreadDot);
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
