package com.example.cpu11341_local.talktvhome.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cpu11341_local.talktvhome.ElapsedTime;
import com.example.cpu11341_local.talktvhome.R;
import com.example.cpu11341_local.talktvhome.data.Topic;

import java.util.ArrayList;

/**
 * Created by CPU11341-local on 9/1/2017.
 */

public class TopicRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;
    ArrayList<Topic> arrTopic = new ArrayList<>();
    private int position;
    boolean isLoading;
    private int lastVisibleItem;
    RecyclerView recyclerView;

    public TopicRecyclerAdapter(Context context, final ArrayList<Topic> arrTopic, RecyclerView recyclerView){
        this.context = context;
        this.arrTopic = arrTopic;
        this.recyclerView = recyclerView;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (linearLayoutManager != null){
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!isLoading && lastVisibleItem == arrTopic.size()-1) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }
            }
        });
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getItemViewType(int position) {
        return arrTopic.get(position) == null ? -1 : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:{
                return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_layout,parent,false));
            }
            default:{
                return new LoadingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_layout,parent,false));
            }
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case 0:
                RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
                Glide.with(context)
                        .load(arrTopic.get(position).getAvatar())
                        .apply(RequestOptions.circleCropTransform())
                        .apply(RequestOptions.placeholderOf(R.drawable.grid_item))
                        .apply(RequestOptions.errorOf(R.drawable.grid_item
                        ))
                        .into(recyclerViewHolder.imageViewAvatar);
                recyclerViewHolder.textViewName.setText(arrTopic.get(position).getName());
                recyclerViewHolder.textViewLastMess.setText(arrTopic.get(position).getLastMess());
                recyclerViewHolder.textViewDate.setText(ElapsedTime.getRelativeTimeSpanString(arrTopic.get(position).getDate()));
                if (arrTopic.get(position).isHasNewMessage()) {
                    recyclerViewHolder.imageViewUnreadDot.setImageResource(R.drawable.unread_dot);
                    recyclerViewHolder.textViewDate.setTypeface(null, Typeface.BOLD);
                    recyclerViewHolder.textViewLastMess.setTypeface(null, Typeface.BOLD);
                    recyclerViewHolder.textViewName.setTypeface(null, Typeface.BOLD);
                }
                break;
            default: {
                LoadingHolder loadingHolder = (LoadingHolder) holder;
                loadingHolder.progressBar.setIndeterminate(true);
                break;
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final TopicRecyclerAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemLongClickListener {
        public void onItemLongClick(View view, int position);
    }

    public void SetOnItemLongClickListener(final TopicRecyclerAdapter.OnItemLongClickListener mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
    }

    @Override
    public int getItemCount() {
        return arrTopic.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
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
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    setPosition(getAdapterPosition());
                    return false;
                }
            });
        }
    }

    public class LoadingHolder extends RecyclerView.ViewHolder{
        public ProgressBar progressBar;
        public LoadingHolder(View view){
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBarLoadMoreMsg);
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
    private OnLoadMoreListener onLoadMoreListener;
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }
}
