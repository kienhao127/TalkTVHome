package com.example.cpu11341_local.talktvhome.adapter;

import android.content.Context;
import android.provider.ContactsContract;
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
            default:{
                return new MessageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_detail_item_layout,parent,false));
            }
        }
    }

    @Override
    public void onBindViewHolder(EventHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case 1:
                EventHolder eventHolder = (EventHolder) holder;
                eventHolder.textViewDateTime.setText(arrMessDetail.get(position).getDatetime());
                eventHolder.textViewTitle.setText(arrMessDetail.get(position).getTitle());
                eventHolder.textViewDate.setText(arrMessDetail.get(position).getDatetime());
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
                remindHolder.textViewDateTime.setText(arrMessDetail.get(position).getDatetime());
                remindHolder.textViewTitle.setText(arrMessDetail.get(position).getTitle());
                remindHolder.textViewDate.setText(arrMessDetail.get(position).getDatetime());
                remindHolder.textViewDes.setText(arrMessDetail.get(position).getText());
                remindHolder.textViewViewDetail.setText(arrMessDetail.get(position).getAction_title());
                break;
            default:
                MessageHolder messageHolder = (MessageHolder) holder;
                Glide.with(context)
                        .load(arrMessDetail.get(position).getUser().getAvatar())
                        .apply(RequestOptions.placeholderOf(R.drawable.grid_item))
                        .apply(RequestOptions.errorOf(R.drawable.grid_item))
                        .apply(RequestOptions.circleCropTransform())
                        .into(messageHolder.imageViewAvatar);
                messageHolder.textViewMessDetail.setText(arrMessDetail.get(position).getText());
                messageHolder.textViewDate.setText(arrMessDetail.get(position).getDatetime());
                if (arrMessDetail.get(position).isWarning()){
                    messageHolder.imageViewWarningDot.setImageResource(R.drawable.warning_dot);
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
        public MessageHolder(View view){
            super(view);
            imageViewAvatar = (ImageView) view.findViewById(R.id.imageViewAvatar);
            textViewMessDetail = (TextView) view.findViewById(R.id.textViewMessDetail);
            textViewDate = (TextView) view.findViewById(R.id.textViewDateTime);
            imageViewWarningDot = (ImageView) view.findViewById(R.id.imageViewWarningDot);
        }
    }
}