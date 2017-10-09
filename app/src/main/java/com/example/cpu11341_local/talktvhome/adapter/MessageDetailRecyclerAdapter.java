package com.example.cpu11341_local.talktvhome.adapter;

import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.ParseException;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cpu11341_local.talktvhome.ElapsedTime;
import com.example.cpu11341_local.talktvhome.MessageDataManager;
import com.example.cpu11341_local.talktvhome.R;
import com.example.cpu11341_local.talktvhome.data.MessageDetail;
import com.example.cpu11341_local.talktvhome.data.User;
import com.example.cpu11341_local.talktvhome.myview.TalkTextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by CPU11341-local on 9/5/2017.
 */

public class MessageDetailRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;
    ArrayList<MessageDetail> arrMessDetail = new ArrayList<>();
    boolean isLoading;
    private int firstVisibleItem;
    RecyclerView recyclerView;

    public MessageDetailRecyclerAdapter(Context context, ArrayList<MessageDetail> arrMessDetail, RecyclerView recyclerView){
        this.context = context;
        this.arrMessDetail = arrMessDetail;
        this.recyclerView = recyclerView;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (linearLayoutManager != null){
                    firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                    if (!isLoading && firstVisibleItem == 0) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return arrMessDetail.get(position) == null ? -1 : arrMessDetail.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("onCreateViewHolder", "Message Detail Recycler Adapter");
        switch (viewType) {
            case 1:{
                return new EventHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.system_event_layout,parent,false));
            }
            case 2:{
                return new RemindHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.system_remind_layout,parent,false));
            }
            case 3:{
                return new MessageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_detail_item_layout,parent,false));
            }
            case 4:{
                return new MyMessageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_message_detail_item_layout,parent,false));
            }
            default:{
                return new LoadingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_layout,parent,false));
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case 1:
                EventHolder eventHolder = (EventHolder) holder;
                eventHolder.textViewDateTime.setText(ElapsedTime.getRelativeTimeSpanString(arrMessDetail.get(position).getDatetime()));
                eventHolder.textViewTitle.setText(arrMessDetail.get(position).getTitle());
                eventHolder.textViewDate.setText(ElapsedTime.getRelativeTimeSpanString(arrMessDetail.get(position).getDatetime()));
                Glide.with(context)
                        .load(arrMessDetail.get(position).getImageURL())
                        .apply(RequestOptions.placeholderOf(R.drawable.grid_item))
                        .apply(RequestOptions.errorOf(R.drawable.grid_item
                        ))
                        .into(eventHolder.imageViewEvent);
                eventHolder.textViewDes.setText(arrMessDetail.get(position).getText());
                eventHolder.textViewViewDetail.setText(arrMessDetail.get(position).getAction_title());
                break;
            case 2:
                RemindHolder remindHolder = (RemindHolder) holder;
                remindHolder.textViewDateTime.setText(ElapsedTime.getRelativeTimeSpanString(arrMessDetail.get(position).getDatetime()));
                remindHolder.textViewTitle.setText(arrMessDetail.get(position).getTitle());
                remindHolder.textViewDate.setText(ElapsedTime.getRelativeTimeSpanString(arrMessDetail.get(position).getDatetime()));
                remindHolder.textViewDes.setText(arrMessDetail.get(position).getText());
                remindHolder.textViewViewDetail.setText(arrMessDetail.get(position).getAction_title());
                break;
            case 3:
                Log.i("onBindViewHolder", "Message Detail Recycler Adapter");
                MessageHolder messageHolder = (MessageHolder) holder;
                if (position == 0){
                    messageHolder.textViewDate.setVisibility(View.VISIBLE);
                    messageHolder.textViewDate.setText(ElapsedTime.getRelativeTimeSpanString(arrMessDetail.get(position).getDatetime()));
                } else {
                    if (arrMessDetail.get(position - 1) != null){
                        if (ElapsedTime.getDayOfDate(arrMessDetail.get(position).getDatetime()) != ElapsedTime.getDayOfDate(arrMessDetail.get(position - 1).getDatetime())) {
                            messageHolder.textViewDate.setVisibility(View.VISIBLE);
                            messageHolder.textViewDate.setText(ElapsedTime.getRelativeTimeSpanString(arrMessDetail.get(position).getDatetime()));
                        } else {
                            messageHolder.textViewDate.setText(ElapsedTime.getRelativeTimeSpanString(arrMessDetail.get(position).getDatetime()));
                            messageHolder.textViewDate.setVisibility(View.GONE);
                        }
                    }
                }

                if (position < arrMessDetail.size() - 1){
                    if (arrMessDetail.get(position).getType() == arrMessDetail.get(position + 1).getType()){
                        messageHolder.imageViewAvatar.setVisibility(View.INVISIBLE);
                        messageHolder.imageViewMsgArrow.setVisibility(View.INVISIBLE);
                    } else {
                        messageHolder.imageViewAvatar.setVisibility(View.VISIBLE);
                        Glide.with(context)
                                .load(arrMessDetail.get(position).getUser().getAvatar())
                                .apply(RequestOptions.placeholderOf(R.drawable.grid_item))
                                .apply(RequestOptions.errorOf(R.drawable.grid_item))
                                .apply(RequestOptions.circleCropTransform())
                                .into(messageHolder.imageViewAvatar);
                        messageHolder.imageViewAvatar.setVisibility(View.VISIBLE);
                        messageHolder.imageViewMsgArrow.setVisibility(View.VISIBLE);
                    }
                } else {
                    messageHolder.imageViewAvatar.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(arrMessDetail.get(position).getUser().getAvatar())
                            .apply(RequestOptions.placeholderOf(R.drawable.grid_item))
                            .apply(RequestOptions.errorOf(R.drawable.grid_item))
                            .apply(RequestOptions.circleCropTransform())
                            .into(messageHolder.imageViewAvatar);
                    messageHolder.imageViewAvatar.setVisibility(View.VISIBLE);
                    messageHolder.imageViewMsgArrow.setVisibility(View.VISIBLE);
                }
                messageHolder.textViewMessDetail.setText(arrMessDetail.get(position).getText());
                break;
            case 4:
                MyMessageHolder myMessageHolder = (MyMessageHolder) holder;
                if (position == 0){
                    myMessageHolder.textViewDate.setVisibility(View.VISIBLE);
                    myMessageHolder.textViewDate.setText(ElapsedTime.getRelativeTimeSpanString(arrMessDetail.get(position).getDatetime()));
                } else {
                    if (arrMessDetail.get(position - 1) != null){
                        if (ElapsedTime.getDayOfDate(arrMessDetail.get(position).getDatetime()) != ElapsedTime.getDayOfDate(arrMessDetail.get(position - 1).getDatetime())) {
                            myMessageHolder.textViewDate.setVisibility(View.VISIBLE);
                            myMessageHolder.textViewDate.setText(ElapsedTime.getRelativeTimeSpanString(arrMessDetail.get(position).getDatetime()));
                        } else {
                            myMessageHolder.textViewDate.setText(ElapsedTime.getRelativeTimeSpanString(arrMessDetail.get(position).getDatetime()));
                            myMessageHolder.textViewDate.setVisibility(View.GONE);
                        }
                    }
                }

                if (position < arrMessDetail.size() - 1){
                    if (arrMessDetail.get(position).getType() == arrMessDetail.get(position + 1).getType()){
                        myMessageHolder.imageViewAvatar.setVisibility(View.INVISIBLE);
                        myMessageHolder.imageViewMsgArrow.setVisibility(View.INVISIBLE);
                    } else {
                        myMessageHolder.imageViewAvatar.setVisibility(View.VISIBLE);
                        Glide.with(context)
                                .load(MessageDataManager.getInstance().getCurrentUser().getAvatar())
                                .apply(RequestOptions.placeholderOf(R.drawable.grid_item))
                                .apply(RequestOptions.errorOf(R.drawable.grid_item))
                                .apply(RequestOptions.circleCropTransform())
                                .into(myMessageHolder.imageViewAvatar);
                        myMessageHolder.imageViewAvatar.setVisibility(View.VISIBLE);
                        myMessageHolder.imageViewMsgArrow.setVisibility(View.VISIBLE);
                    }
                } else {
                    myMessageHolder.imageViewAvatar.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(MessageDataManager.getInstance().getCurrentUser().getAvatar())
                            .apply(RequestOptions.placeholderOf(R.drawable.grid_item))
                            .apply(RequestOptions.errorOf(R.drawable.grid_item))
                            .apply(RequestOptions.circleCropTransform())
                            .into(myMessageHolder.imageViewAvatar);
                    myMessageHolder.imageViewAvatar.setVisibility(View.VISIBLE);
                    myMessageHolder.imageViewMsgArrow.setVisibility(View.VISIBLE);
                }
                myMessageHolder.textViewMessDetail.setText(arrMessDetail.get(position).getText());
                if (arrMessDetail.get(position).isWarning()) {
                    myMessageHolder.imageViewWarningDot.setVisibility(View.VISIBLE);
                } else {
                    myMessageHolder.imageViewWarningDot.setVisibility(View.INVISIBLE);
                }
                break;
            default:{
                LoadingHolder loadingHolder = (LoadingHolder) holder;
                loadingHolder.progressBar.setIndeterminate(true);
                break;
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemLongClickListener {
        public void onItemLongClick(View view, int position);
    }

    public void SetOnItemLongClickListener(final OnItemLongClickListener mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
    }

    @Override
    public int getItemCount() {
        return arrMessDetail.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        TextView textViewDateTime;
        TextView textViewTitle;
        TextView textViewDate;
        ImageView imageViewEvent;
        TextView textViewDes;
        TextView textViewViewDetail;

        public EventHolder(View view){
            super(view);
            textViewDateTime = (TextView) view.findViewById(R.id.textViewDateTime);
            textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
            textViewDate = (TextView) view.findViewById(R.id.textViewDate);
            imageViewEvent = (ImageView) view.findViewById(R.id.imageViewEvent);
            textViewDes = (TextView) view.findViewById(R.id.textViewDes);
            textViewViewDetail = (TextView) view.findViewById(R.id.textViewViewDetail);
        }
    }

    public class RemindHolder extends RecyclerView.ViewHolder {
        TextView textViewDateTime;
        TextView textViewTitle;
        TextView textViewDate;
        TextView textViewDes;
        TextView textViewViewDetail;

        public RemindHolder(View view){
            super(view);
            textViewDateTime = (TextView) view.findViewById(R.id.textViewDateTime);
            textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
            textViewDate = (TextView) view.findViewById(R.id.textViewDate);
            textViewDes = (TextView) view.findViewById(R.id.textViewDes);
            textViewViewDetail = (TextView) view.findViewById(R.id.textViewViewDetail);
        }
    }

    public class MessageHolder extends RecyclerView.ViewHolder {
        ImageView imageViewAvatar;
        TextView textViewMessDetail;
        TextView textViewDate;
        ImageView imageViewWarningDot;
        ImageView imageViewMsgArrow;
        public MessageHolder(final View view){
            super(view);
            imageViewAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            textViewMessDetail = (TextView) view.findViewById(R.id.textViewMessDetail);
            textViewDate = (TextView) view.findViewById(R.id.textViewDateTime);
            imageViewWarningDot = (ImageView) view.findViewById(R.id.imageViewWarningDot);
            imageViewMsgArrow = (ImageView) view.findViewById(R.id.imageViewMessageboxArrow);
            textViewMessDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (textViewDate.getVisibility() == View.VISIBLE) {
                        Animation showOff = AnimationUtils.loadAnimation(context, R.anim.show_off);
                        textViewDate.setVisibility(View.GONE);
                        textViewDate.startAnimation(showOff);
                        textViewMessDetail.setBackgroundResource(R.drawable.rounded_corner);
                    } else {
                        Animation showUp = AnimationUtils.loadAnimation(context, R.anim.show_up);
                        Animation slide_down = AnimationUtils.loadAnimation(context, R.anim.slide_down);
                        textViewDate.setVisibility(View.VISIBLE);
                        textViewDate.startAnimation(showUp);
                        textViewMessDetail.startAnimation(slide_down);
                        textViewMessDetail.setBackgroundResource(R.drawable.selected_msg_box);
                    }
                }
            });
            textViewMessDetail.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemLongClickListener!=null){
                        mItemLongClickListener.onItemLongClick(view, getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }

    public class MyMessageHolder extends RecyclerView.ViewHolder {
        ImageView imageViewAvatar;
        TextView textViewMessDetail;
        TextView textViewDate;
        ImageView imageViewWarningDot;
        ImageView imageViewMsgArrow;
        public MyMessageHolder(final View view){
            super(view);
            imageViewAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            textViewMessDetail = (TextView) view.findViewById(R.id.textViewMessDetail);
            textViewDate = (TextView) view.findViewById(R.id.textViewDateTime);
            imageViewWarningDot = (ImageView) view.findViewById(R.id.imageViewWarningDot);
            imageViewMsgArrow = (ImageView) view.findViewById(R.id.imageViewMessageboxArrow);
            textViewMessDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (textViewDate.getVisibility() == View.VISIBLE) {
                        Animation showOff = AnimationUtils.loadAnimation(context, R.anim.show_off);
                        textViewDate.setVisibility(View.GONE);
                        textViewDate.startAnimation(showOff);
                        textViewMessDetail.setBackgroundResource(R.drawable.my_message_box);
                    } else {
                        Animation showUp = AnimationUtils.loadAnimation(context, R.anim.show_up);
                        Animation slide_down = AnimationUtils.loadAnimation(context, R.anim.slide_down);
                        textViewDate.setVisibility(View.VISIBLE);
                        textViewDate.startAnimation(showUp);
                        textViewMessDetail.startAnimation(slide_down);
                        textViewMessDetail.setBackgroundResource(R.drawable.selected_my_msg_box);
                    }
                }
            });
            textViewMessDetail.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemLongClickListener!=null){
                        mItemLongClickListener.onItemLongClick(view, getAdapterPosition());
                    }
                    return true;
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