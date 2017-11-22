package com.example.cpu11341_local.talktvhome.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cpu11341_local.talktvhome.EmoticonUtil;
import com.example.cpu11341_local.talktvhome.R;
import com.example.cpu11341_local.talktvhome.fragment.EmoticonsFragment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CPU11341-local on 11/13/2017.
 */

public class EmoticonsRecyclerAdapter extends RecyclerView.Adapter<EmoticonsRecyclerAdapter.RecyclerViewHolder> {
    private Context context;
    private EmoticonClickListener emoticonClickListener;
    private ArrayList<String> emoticonString = new ArrayList<>();
    TypedArray emotions;
    public EmoticonsRecyclerAdapter(Context context, EmoticonClickListener emoticonClickListener){
        this.context = context;
    }

    public EmoticonsRecyclerAdapter(Context context, EmoticonClickListener emoticonClickListener, TypedArray emotions){
        this.context = context;
        this.emoticonClickListener = emoticonClickListener;
        this.emoticonString = new ArrayList<>(EmoticonUtil.getSmiley().keySet());
        this.emotions = emotions;
        Log.d("Emoticon size", String.valueOf(emoticonString.size()));
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emoticons_item,parent,false);

        final EmoticonsRecyclerAdapter.RecyclerViewHolder recyclerViewHolder = new EmoticonsRecyclerAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(EmoticonsRecyclerAdapter.RecyclerViewHolder holder, final int position) {
        Log.d("emoticonString", String.valueOf(emoticonString.size()));
        holder.emoticon.setImageResource(EmoticonUtil.getSmiley().get(emoticonString.get(position)));
    }

    public interface EmoticonClickListener {
        public void onEmoticonItemClick(int id, String index);
    }

    @Override
    public int getItemCount() {
        return emoticonString.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView emoticon;
        public RecyclerViewHolder(View view){
            super(view);
            emoticon = (ImageView) view.findViewById(R.id.emoticonsItem);
            emoticon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (emoticonClickListener != null) {
                emoticonClickListener.onEmoticonItemClick(EmoticonUtil.getSmiley().get(emoticonString.get(getAdapterPosition())), emoticonString.get(getAdapterPosition()));
            }
        }
    }

}
