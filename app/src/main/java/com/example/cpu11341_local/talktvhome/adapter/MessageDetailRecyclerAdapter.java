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
import com.example.cpu11341_local.talktvhome.data.MessageDetail;

import java.util.ArrayList;

/**
 * Created by CPU11341-local on 9/5/2017.
 */

public class MessageDetailRecyclerAdapter extends RecyclerView.Adapter<MessageDetailRecyclerAdapter.RecyclerViewHolderEvent> {

    private Context context;
    private OnItemClickListener mItemClickListener;
    ArrayList<MessageDetail> arrMessDetail = new ArrayList<>();

    public MessageDetailRecyclerAdapter(Context context, ArrayList<MessageDetail> arrMessDetail){
        this.context = context;
        this.arrMessDetail = arrMessDetail;
    }


    @Override
    public RecyclerViewHolderEvent onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolderEvent(LayoutInflater.from(parent.getContext()).inflate(R.layout.system_event_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderEvent holder, final int position) {
        holder.textViewDateTime.setText(arrMessDetail.get(position).getDatetime());
        holder.textViewTitle.setText(arrMessDetail.get(position).getTitle());
        holder.textViewDate.setText(arrMessDetail.get(position).getDatetime());
        Glide.with(context)
                .load(arrMessDetail.get(position).getImageURL())
                .apply(RequestOptions.placeholderOf(R.drawable.grid_item))
                .apply(RequestOptions.errorOf(R.drawable.grid_item
                ))
                .into(holder.imageViewEvent);
        holder.textViewDes.setText(arrMessDetail.get(position).getDescription());
        holder.textViewViewDetail.setText(arrMessDetail.get(position).getAction_title());
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final MessageDetailRecyclerAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public int getItemCount() {
        return arrMessDetail.size();
    }

    public class RecyclerViewHolderEvent extends RecyclerView.ViewHolder {
        TextView textViewDateTime;
        TextView textViewTitle;
        TextView textViewDate;
        ImageView imageViewEvent;
        TextView textViewDes;
        TextView textViewViewDetail;

        public RecyclerViewHolderEvent(View view){
            super(view);
            textViewDateTime = (TextView) view.findViewById(R.id.textViewDateTime);
            textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
            textViewDate = (TextView) view.findViewById(R.id.textViewDate);
            imageViewEvent = (ImageView) view.findViewById(R.id.imageViewEvent);
            textViewDes = (TextView) view.findViewById(R.id.textViewDes);
            textViewViewDetail = (TextView) view.findViewById(R.id.textViewViewDetail);
        }
    }

    public class RecyclerViewHolderRemind extends RecyclerView.ViewHolder {
        ImageView imageViewAvatar;
        TextView textViewName;
        TextView textViewLastMess;
        TextView textViewDate;

        public RecyclerViewHolderRemind(View view){
            super(view);
            imageViewAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            textViewName = (TextView) view.findViewById(R.id.textViewName);
            textViewLastMess = (TextView) view.findViewById(R.id.textViewLastMess);
            textViewDate = (TextView) view.findViewById(R.id.textViewDate);
        }
    }
}