package com.example.cpu11341_local.talktvhome.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cpu11341_local.talktvhome.R;
import com.example.cpu11341_local.talktvhome.fragment.EmoticonsFragment;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by CPU11341-local on 11/13/2017.
 */

public class EmoticonsRecyclerAdapter extends RecyclerView.Adapter<EmoticonsRecyclerAdapter.RecyclerViewHolder> {
    private static ArrayList<String> arrEmoticons = new ArrayList<>();
    private Context context;
    private EmoticonClickListener emoticonClickListener;

    public EmoticonsRecyclerAdapter(Context context){
        this.context = context;
    }

    public EmoticonsRecyclerAdapter(Context context, ArrayList<String> arrEmoticons, EmoticonClickListener emoticonClickListener){
        this.arrEmoticons = arrEmoticons;
        this.context = context;
        this.emoticonClickListener = emoticonClickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emoticons_item,parent,false);

        final EmoticonsRecyclerAdapter.RecyclerViewHolder recyclerViewHolder = new EmoticonsRecyclerAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(EmoticonsRecyclerAdapter.RecyclerViewHolder holder, final int position) {
        holder.emoticon.setImageBitmap(getImage(arrEmoticons.get(position)));
    }

    private Bitmap getImage (String path) {
        AssetManager mngr = context.getAssets();
        InputStream in = null;

        try {
            in = mngr.open("emoticons/" + path);
        } catch (Exception e){
            e.printStackTrace();
        }

        Bitmap temp = BitmapFactory.decodeStream(in ,null ,null);
        return temp;
    }

    public interface EmoticonClickListener {
        public void onEmoticonItemClick(View view, final String index);
    }

    @Override
    public int getItemCount() {
        return arrEmoticons.size();
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
                emoticonClickListener.onEmoticonItemClick(v, arrEmoticons.get(getAdapterPosition()));
            }
        }
    }

}