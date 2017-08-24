package com.example.cpu11341_local.talktvhome.bannerview;;

/**
 * Created by CPU11341-local on 8/7/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cpu11341_local.talktvhome.OpenRoomActivity;
import com.example.cpu11341_local.talktvhome.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Banner> arrBannerItems;
    private boolean isInfinite;

    public ViewPagerAdapter(Context context, ArrayList<Banner> arrBannerItem) {
        this.context = context;
        this.isInfinite = false;
        this.arrBannerItems = arrBannerItem;
        notifyDataSetChanged();
    }

    public void setInfinite(boolean infinite) {
        isInfinite = infinite;
        notifyDataSetChanged();
    }

    public boolean getInfinite(){
        return isInfinite;
    }

    @Override
    public int getCount() {
        return arrBannerItems.size() + 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container,final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_banner_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        if (position == 0) {
            ImageLoader.getInstance().displayImage(arrBannerItems.get(arrBannerItems.size() - 1).getImageURL(), imageView);
        } else {
            if (position == (arrBannerItems.size() + 1)) {
                ImageLoader.getInstance().displayImage(arrBannerItems.get(0).getImageURL(), imageView);
            } else {
                ImageLoader.getInstance().displayImage(arrBannerItems.get(position - 1).getImageURL(), imageView);
            }
        }

        view.setOnTouchListener(new View.OnTouchListener() {
            float x1, x2;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        return true;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        if (x2==x1) {
                            int currentPositon = position - 1;
                            if (arrBannerItems.get(currentPositon).getAction_type() == 1){
                                Intent intent = new Intent(context, OpenRoomActivity.class);
                                intent.putExtra("RoomID", arrBannerItems.get(currentPositon).getRoomID());
                                context.startActivity(intent);
                            } else {
                                String url = arrBannerItems.get(currentPositon).getLink();
                                if (!url.startsWith("http://") && !url.startsWith("https://"))
                                    url = "http://" + url;
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                context.startActivity(browserIntent);
                            }
                        }
                        break;
                }
                return false;
            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void addBannerItem(Banner banner){
        arrBannerItems.add(banner);
        notifyDataSetChanged();
    }

    public void removeBannerItemAt(int position){
        arrBannerItems.remove(position);
        notifyDataSetChanged();
    }

    public void replaceAllBannerItems(ArrayList<Banner> arrBannerItems){
        this.arrBannerItems = arrBannerItems;
        notifyDataSetChanged();
    }

    public ArrayList<Banner> getArrBannerItems(){
        return arrBannerItems;
    }
}
