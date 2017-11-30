package com.example.cpu11341_local.talktvhome.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cpu11341_local.talktvhome.CategoryDetailActivity;
import com.example.cpu11341_local.talktvhome.OpenRoomActivity;
import com.example.cpu11341_local.talktvhome.R;
import com.example.cpu11341_local.talktvhome.data.DocGridWithTitle;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by CPU11341-local on 8/18/2017.
 */

public class DocGridRecyclerAdapter extends RecyclerView.Adapter<DocGridRecyclerAdapter.RVHolder> implements Serializable {
    private static ArrayList<DocGridWithTitle> arrDocGridWithTitle = new ArrayList<DocGridWithTitle>();
    private static RecyclerView gridList;

    public DocGridRecyclerAdapter(ArrayList<DocGridWithTitle> arrDocGrid){
        this.arrDocGridWithTitle = arrDocGrid;
    }

    public DocGridRecyclerAdapter() {

    }

    @Override
    public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_grid_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(DocGridRecyclerAdapter.RVHolder holder, final int position) {
        holder.gridRecyclerAdapter.setData(arrDocGridWithTitle.get(position).getarrDocGrid());
        holder.textView.setText(arrDocGridWithTitle.get(position).getDocTitle().getTitle());
        if (!arrDocGridWithTitle.get(position).getDocTitle().getTitle().equals("Nổi bật")){
            holder.imageView.setImageResource(R.drawable.ic_chevron_right);
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, CategoryDetailActivity.class);
                    intent.putExtra("PageUrl", arrDocGridWithTitle.get(position).getDocTitle().getPageUrl());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrDocGridWithTitle.size();
    }


    public static class RVHolder extends RecyclerView.ViewHolder {
        private GridRecyclerAdapter gridRecyclerAdapter;
        private TextView textView;
        private ImageView imageView;

        public RVHolder(View view) {
            super(view);
            Context context = itemView.getContext();

            imageView = (ImageView) itemView.findViewById(R.id.imageViewChervonRight);
            textView = (TextView) itemView.findViewById(R.id.textViewTitle);
            gridList = (RecyclerView) itemView.findViewById(R.id.recyclerViewGrid);
            gridList.setLayoutManager(new GridLayoutManager(context, 2));
            gridRecyclerAdapter = new GridRecyclerAdapter(context);
            gridList.setAdapter(gridRecyclerAdapter);

            gridRecyclerAdapter.SetOnItemClickListener(new GridRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Context context = itemView.getContext();
                    Intent intent;
                    if (arrDocGridWithTitle.get(getAdapterPosition()).getarrDocGrid().get(position).getActionType() == 1) {
                        intent = new Intent(context, OpenRoomActivity.class);
                        intent.putExtra("RoomID", arrDocGridWithTitle.get(getAdapterPosition()).getarrDocGrid().get(position).getRoomId());
                    } else {
                        intent = new Intent(context, OpenRoomActivity.class);
                        intent.putExtra("OfflineVideoID", arrDocGridWithTitle.get(getAdapterPosition()).getarrDocGrid().get(position).getRoomId());
                    }
                    context.startActivity(intent);
                }
            });
        }
    }

    public void setData(ArrayList<DocGridWithTitle> data) {
        if (arrDocGridWithTitle != data) {
            arrDocGridWithTitle = data;
            notifyDataSetChanged();
        }
    }
}