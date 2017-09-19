package com.example.cpu11341_local.talktvhome.adapter;

import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.ParseException;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cpu11341_local.talktvhome.ElapsedTime;
import com.example.cpu11341_local.talktvhome.MessageDataManager;
import com.example.cpu11341_local.talktvhome.R;
import com.example.cpu11341_local.talktvhome.data.MessageDetail;
import com.example.cpu11341_local.talktvhome.data.User;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by CPU11341-local on 9/5/2017.
 */

public class MessageDetailRecyclerAdapter extends RecyclerView.Adapter<MessageDetailRecyclerAdapter.EventHolder> {

    private Context context;
    private OnItemClickListener mItemClickListener;
    ArrayList<MessageDetail> arrMessDetail = new ArrayList<>();

    public MessageDetailRecyclerAdapter(Context context, ArrayList<MessageDetail> arrMessDetail){
        this.context = context;
        this.arrMessDetail = arrMessDetail;
    }

    @Override
    public int getItemViewType(int position) {
        return arrMessDetail.get(position).getType();
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
            default:{
                return new MyMessageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_message_detail_item_layout,parent,false));
            }
        }
    }

    @Override
    public void onBindViewHolder(EventHolder holder, final int position) {
        DateFormat dateFormat = new SimpleDateFormat("d/MM/yy HH:mm:ss");
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
                MessageHolder messageHolder = (MessageHolder) holder;
                if (position == 0 || arrMessDetail.get(position - 1).getType() != arrMessDetail.get(position).getType()){
                    messageHolder.textViewDate.setVisibility(View.VISIBLE);
                    messageHolder.textViewDate.setText(ElapsedTime.getRelativeTimeSpanString(arrMessDetail.get(position).getDatetime()));
                    messageHolder.imageViewAvatar.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(arrMessDetail.get(position).getUser().getAvatar())
                            .apply(RequestOptions.placeholderOf(R.drawable.grid_item))
                            .apply(RequestOptions.errorOf(R.drawable.grid_item))
                            .apply(RequestOptions.circleCropTransform())
                            .into(messageHolder.imageViewAvatar);
                    messageHolder.imageViewMsgArrow.setVisibility(View.VISIBLE);
                }else {
                    messageHolder.textViewDate.setVisibility(View.GONE);
                    messageHolder.imageViewAvatar.setVisibility(View.INVISIBLE);
                    messageHolder.imageViewMsgArrow.setVisibility(View.INVISIBLE);
                }
                messageHolder.textViewMessDetail.setText(arrMessDetail.get(position).getText());
                break;
            default:
                MyMessageHolder myMessageHolder = (MyMessageHolder) holder;
                if (position == 0 || arrMessDetail.get(position - 1).getType() != arrMessDetail.get(position).getType()){
                    myMessageHolder.textViewDate.setVisibility(View.VISIBLE);
                    myMessageHolder.textViewDate.setText(ElapsedTime.getRelativeTimeSpanString(arrMessDetail.get(position).getDatetime()));
                    myMessageHolder.imageViewAvatar.setVisibility(View.VISIBLE);
                    User curUser = MessageDataManager.getInstance().getCurrentUser();
                    Glide.with(context)
                            .load(curUser.getAvatar())
                            .apply(RequestOptions.placeholderOf(R.drawable.grid_item))
                            .apply(RequestOptions.errorOf(R.drawable.grid_item))
                            .apply(RequestOptions.circleCropTransform())
                            .into(myMessageHolder.imageViewAvatar);
                    myMessageHolder.imageViewMsgArrow.setVisibility(View.VISIBLE);
                }else {
                    myMessageHolder.textViewDate.setVisibility(View.GONE);
                    myMessageHolder.imageViewAvatar.setVisibility(View.INVISIBLE);
                    myMessageHolder.imageViewMsgArrow.setVisibility(View.INVISIBLE);
                }
                myMessageHolder.textViewMessDetail.setText(arrMessDetail.get(position).getText());
                if (arrMessDetail.get(position).isWarning()) {
                    myMessageHolder.imageViewWarningDot.setVisibility(View.VISIBLE);
                } else {
                    myMessageHolder.imageViewWarningDot.setVisibility(View.INVISIBLE);
                }
                break;

        }
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

    public class RemindHolder extends EventHolder {
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

    public class MessageHolder extends EventHolder {
        ImageView imageViewAvatar;
        TextView textViewMessDetail;
        TextView textViewDate;
        ImageView imageViewWarningDot;
        ImageView imageViewMsgArrow;
        public MessageHolder(View view){
            super(view);
            imageViewAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            textViewMessDetail = (TextView) view.findViewById(R.id.textViewMessDetail);
            textViewDate = (TextView) view.findViewById(R.id.textViewDateTime);
            imageViewWarningDot = (ImageView) view.findViewById(R.id.imageViewWarningDot);
            imageViewMsgArrow = (ImageView) view.findViewById(R.id.imageViewMessageboxArrow);
        }
    }

    public class MyMessageHolder extends EventHolder {
        ImageView imageViewAvatar;
        TextView textViewMessDetail;
        TextView textViewDate;
        ImageView imageViewWarningDot;
        ImageView imageViewMsgArrow;
        public MyMessageHolder(View view){
            super(view);
            imageViewAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            textViewMessDetail = (TextView) view.findViewById(R.id.textViewMessDetail);
            textViewDate = (TextView) view.findViewById(R.id.textViewDateTime);
            imageViewWarningDot = (ImageView) view.findViewById(R.id.imageViewWarningDot);
            imageViewMsgArrow = (ImageView) view.findViewById(R.id.imageViewMessageboxArrow);
        }
    }
}