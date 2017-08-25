package com.example.cpu11341_local.talktvhome.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cpu11341_local.talktvhome.CategoryDetailActivity;
import com.example.cpu11341_local.talktvhome.HorilistImageLoader;
import com.example.cpu11341_local.talktvhome.OpenRoomActivity;
import com.example.cpu11341_local.talktvhome.R;
import com.example.cpu11341_local.talktvhome.data.DocHorizon;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

import java.util.ArrayList;

/**
 * Created by CPU11341-local on 8/18/2017.
 */

public class DocHorlistRecyclerAdapter extends RecyclerView.Adapter<DocHorlistRecyclerAdapter.RecyclerViewHolder> {
    private static ArrayList<DocHorizon> arrHorList = new ArrayList<DocHorizon>();
    private Context context;

    public DocHorlistRecyclerAdapter(Context context){
        this.context = context;
    }

    public DocHorlistRecyclerAdapter(Context context, ArrayList<DocHorizon> arrHorList){
        this.arrHorList = arrHorList;
        this.context = context;
    }

    @Override
    public DocHorlistRecyclerAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horlist_item_layout, parent,false);

        DocHorlistRecyclerAdapter.RecyclerViewHolder recyclerViewHolder = new DocHorlistRecyclerAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    @Override
    public void onBindViewHolder(DocHorlistRecyclerAdapter.RecyclerViewHolder holder, int position) {
        final DocHorizon docHorizon = arrHorList.get(position);

        HorilistImageLoader imageLoader = HorilistImageLoader.getInstance();

        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .displayer(new CircleBitmapDisplayer())
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(displayImageOptions).build();
        imageLoader.init(config);

        imageLoader.displayImage(docHorizon.getImgURL(), holder.imageViewIcon);

        holder.textViewTitle.setText(docHorizon.getTitle());
    }

    @Override
    public int getItemCount() {
        return arrHorList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewIcon;
        TextView textViewTitle;

        public RecyclerViewHolder(View view) {
            super(view);
            imageViewIcon = (ImageView) view.findViewById(R.id.imageView);
            textViewTitle = (TextView) view.findViewById(R.id.textView);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Context context = v.getContext();
            Intent intent = new Intent();
            if (arrHorList.get(pos).getAction_type() == 1){
                intent = new Intent(context, OpenRoomActivity.class);
                intent.putExtra("RoomID", arrHorList.get(pos).getRoomID());
            } else {
                intent = new Intent(context, CategoryDetailActivity.class);
                intent.putExtra("CategoryID", arrHorList.get(pos).getCategoryID());
            }
            context.startActivity(intent);
        }
    }

    public void setData(ArrayList<DocHorizon> data) {
        if (arrHorList != data) {
            arrHorList = data;
            notifyDataSetChanged();
        }
    }
}