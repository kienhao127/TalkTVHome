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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cpu11341_local.talktvhome.ElapsedTime;
import com.example.cpu11341_local.talktvhome.MessageDataManager;
import com.example.cpu11341_local.talktvhome.R;
import com.example.cpu11341_local.talktvhome.data.MessageDetail;
import com.example.cpu11341_local.talktvhome.data.User;
import com.example.cpu11341_local.talktvhome.myview.TalkTextView;

import org.w3c.dom.Text;

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
                    if (!isLoading && firstVisibleItem < 5) {
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
                eventHolder.textViewTitle.setText(arrMessDetail.get(position).getTitle());
                eventHolder.textViewDateTime.setText(ElapsedTime.getRelativeTimeSpanString(arrMessDetail.get(position).getDatetime()));
                eventHolder.textViewEventDatetime.setText(ElapsedTime.getRelativeTimeSpanString(arrMessDetail.get(position).getEventDatetime()));
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
                remindHolder.textViewEventDatetime.setText(ElapsedTime.getRelativeTimeSpanString(arrMessDetail.get(position).getEventDatetime()));
                remindHolder.textViewTitle.setText(arrMessDetail.get(position).getTitle());
                remindHolder.textViewDes.setText(arrMessDetail.get(position).getText());
                remindHolder.textViewViewDetail.setText(arrMessDetail.get(position).getAction_title());
                break;
            case 3:
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
                    } else {
                        messageHolder.imageViewAvatar.setVisibility(View.VISIBLE);
                        Glide.with(context)
                                .load(arrMessDetail.get(position).getUser().getAvatar())
                                .apply(RequestOptions.placeholderOf(R.drawable.grid_item))
                                .apply(RequestOptions.errorOf(R.drawable.grid_item))
                                .apply(RequestOptions.circleCropTransform())
                                .into(messageHolder.imageViewAvatar);
                        messageHolder.imageViewAvatar.setVisibility(View.VISIBLE);
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
                }
                messageHolder.textViewMessDetail.setText(arrMessDetail.get(position).getText());
                messageHolder.textViewMessDetail.setBackgroundResource(R.drawable.rounded_corner);
                if (messageHolder.textViewMessDetail.getHeight() < (int) pxFromDp(context, 50)){
                    messageHolder.textViewMessDetail.setHeight((int) pxFromDp(context, 50));
                }
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
                    } else {
                        myMessageHolder.imageViewAvatar.setVisibility(View.VISIBLE);
                        Glide.with(context)
                                .load(MessageDataManager.getInstance().getCurrentUser(context).getAvatar())
                                .apply(RequestOptions.placeholderOf(R.drawable.grid_item))
                                .apply(RequestOptions.errorOf(R.drawable.grid_item))
                                .apply(RequestOptions.circleCropTransform())
                                .into(myMessageHolder.imageViewAvatar);
                        myMessageHolder.imageViewAvatar.setVisibility(View.VISIBLE);
                    }
                } else {
                    myMessageHolder.imageViewAvatar.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(MessageDataManager.getInstance().getCurrentUser(context).getAvatar())
                            .apply(RequestOptions.placeholderOf(R.drawable.grid_item))
                            .apply(RequestOptions.errorOf(R.drawable.grid_item))
                            .apply(RequestOptions.circleCropTransform())
                            .into(myMessageHolder.imageViewAvatar);
                    myMessageHolder.imageViewAvatar.setVisibility(View.VISIBLE);
                }
                myMessageHolder.textViewMessDetail.setText(arrMessDetail.get(position).getText());
                myMessageHolder.textViewMessDetail.setBackgroundResource(R.drawable.my_message_box);

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

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
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
        ImageView imageViewEvent;
        TextView textViewDes;
        TextView textViewViewDetail;
        TextView textViewEventDatetime;

        public EventHolder(final View view){
            super(view);
            textViewDateTime = (TextView) view.findViewById(R.id.textViewDateTime);
            textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
            imageViewEvent = (ImageView) view.findViewById(R.id.imageViewEvent);
            textViewDes = (TextView) view.findViewById(R.id.textViewDes);
            textViewViewDetail = (TextView) view.findViewById(R.id.textViewViewDetail);
            textViewEventDatetime = (TextView) view.findViewById(R.id.textViewEventDateTime);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemLongClickListener!=null){
                        mItemLongClickListener.onItemLongClick(view, getAdapterPosition());
                    }
                    return true;
                }
            });
            textViewViewDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null){
                        mItemClickListener.onItemClick(view, getAdapterPosition());
                    }
                }
            });
        }
    }

    public class RemindHolder extends RecyclerView.ViewHolder {
        TextView textViewDateTime;
        TextView textViewTitle;
        TextView textViewDes;
        TextView textViewViewDetail;
        TextView textViewEventDatetime;

        public RemindHolder(final View view){
            super(view);
            textViewDateTime = (TextView) view.findViewById(R.id.textViewDateTime);
            textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
            textViewDes = (TextView) view.findViewById(R.id.textViewDes);
            textViewViewDetail = (TextView) view.findViewById(R.id.textViewViewDetail);
            textViewEventDatetime = (TextView) view.findViewById(R.id.textViewEventDateTime);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemLongClickListener!=null){
                        mItemLongClickListener.onItemLongClick(view, getAdapterPosition());
                    }
                    return true;
                }
            });
            textViewViewDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null){
                        mItemClickListener.onItemClick(view, getAdapterPosition());
                    }
                }
            });
        }
    }

    public class MessageHolder extends RecyclerView.ViewHolder {
        ImageView imageViewAvatar;
        TextView textViewMessDetail;
        TextView textViewDate;
        ImageView imageViewWarningDot;
        public MessageHolder(final View view){
            super(view);
            imageViewAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            textViewMessDetail = (TextView) view.findViewById(R.id.textViewMessDetail);
            textViewDate = (TextView) view.findViewById(R.id.textViewDateTime);
            imageViewWarningDot = (ImageView) view.findViewById(R.id.imageViewWarningDot);
            textViewMessDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null){
                        mItemClickListener.onItemClick(view, getAdapterPosition());
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
        public MyMessageHolder(final View view){
            super(view);
            imageViewAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            textViewMessDetail = (TextView) view.findViewById(R.id.textViewMessDetail);
            textViewDate = (TextView) view.findViewById(R.id.textViewDateTime);
            imageViewWarningDot = (ImageView) view.findViewById(R.id.imageViewWarningDot);
            textViewMessDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null){
                        mItemClickListener.onItemClick(view, getAdapterPosition());
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

    public void setData(ArrayList<MessageDetail> arrMessDetail){
        this.arrMessDetail = arrMessDetail;
        notifyDataSetChanged();
    }
}