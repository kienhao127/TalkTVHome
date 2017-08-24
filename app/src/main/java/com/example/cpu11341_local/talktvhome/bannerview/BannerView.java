package com.example.cpu11341_local.talktvhome.bannerview;

/**
 * Created by CPU11341-local on 8/7/2017.
 */

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.cpu11341_local.talktvhome.R;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;



public class BannerView extends LinearLayout {
    CustomViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private int currentPage;
    ViewPagerAdapter viewPagerAdapter;
    boolean isInfinite;

    public BannerView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.banner_layout, this);

        viewPager = (CustomViewPager) view.findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) view.findViewById(R.id.sliderDots);

        viewPager.addOnPageChangeListener(new CustomViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPage = viewPager.getCurrentItem();
                int pageCount = viewPagerAdapter.getCount();
                int pos = 1;
                if (currentPage == pageCount - 1){
                    pos = 1;
                } else if (currentPage == 0){
                    pos = pageCount - 2;
                } else {
                    pos = currentPage;
                }

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageResource(R.drawable.nonactive_dot);
                }
                dots[pos-1].setImageResource(R.drawable.active_dot);

            }

            @Override
            public void onPageSelected(int position) {
                if (!viewPagerAdapter.getInfinite() && position==viewPagerAdapter.getCount() - 2){
                    viewPager.setAllowedSwipeDirection(SwipeDirection.left2right);
                } else {
                    if (!viewPagerAdapter.getInfinite() && position == 1) {
                        viewPager.setAllowedSwipeDirection(SwipeDirection.right2left);
                    } else {
                        viewPager.setAllowedSwipeDirection(SwipeDirection.all);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (viewPagerAdapter.getInfinite()){
                    int pageCount = viewPagerAdapter.getCount();
                    if (state == ViewPager.SCROLL_STATE_DRAGGING){
                        if (currentPage == pageCount - 1){
                            currentPage = 1;
                            viewPager.setCurrentItem(1, false);

                        } else if (currentPage == 0){
                            currentPage = pageCount - 2;
                            viewPager.setCurrentItem(pageCount - 2, false);
                        }
                    }
                    if (state == ViewPager.SCROLL_STATE_IDLE){
                        if (currentPage == pageCount - 1){
                            currentPage = 1;
                            viewPager.setCurrentItem(1, false);

                        } else if (currentPage == 0){

                            currentPage = pageCount - 2;
                            viewPager.setCurrentItem(pageCount - 2, false);
                        }
                    }
                }
            }
        });

        autoScroll();
    }

    public void autoScroll(){
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                viewPager.setCurrentItem(currentPage++, true);
                if (currentPage == viewPagerAdapter.getCount()){
                    currentPage = 1;
//                    viewPager.setCurrentItem(currentPage, false);
                }
            }
        };
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 500, 2000);

    }

    public void addBannerItems(ArrayList<Banner> arrBannerItems){
        viewPagerAdapter = new ViewPagerAdapter(getContext(), arrBannerItems);

        viewPager.setOffscreenPageLimit(viewPagerAdapter.getCount() - 2);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(1, true);

        updateDots();
    }

    public void setInfiniteSlide(boolean isInfinite){
        viewPagerAdapter.setInfinite(isInfinite);
        if (viewPagerAdapter.getInfinite()) {
            viewPager.setAllowedSwipeDirection(SwipeDirection.all);
        }else {
            if (viewPager.getCurrentItem() == viewPagerAdapter.getCount() - 1){
                viewPager.setCurrentItem(1, false);
            }
            if (viewPager.getCurrentItem() == 0){
                viewPager.setCurrentItem(viewPagerAdapter.getCount() - 2, false);
            }
        }
        int position = viewPager.getCurrentItem();
        if (!viewPagerAdapter.getInfinite() && position== viewPagerAdapter.getCount() - 2){
            viewPager.setAllowedSwipeDirection(SwipeDirection.left2right);
        } else {
            if (!viewPagerAdapter.getInfinite() && position == 1) {
                viewPager.setAllowedSwipeDirection(SwipeDirection.right2left);
            } else {
                viewPager.setAllowedSwipeDirection(SwipeDirection.all);
            }
        }
    }

    public int getCurPosition(){
        return viewPager.getCurrentItem();
    }

    public void addBannerItem(Banner banner){
        viewPagerAdapter.addBannerItem(banner);
        updateDots();
    }

    public void removeBannerItemAt(int position){
        viewPagerAdapter.removeBannerItemAt(position);
        updateDots();
    }

    public void replaceAllBannerItems(ArrayList<Banner> arrBannerItems){
        viewPagerAdapter.replaceAllBannerItems(arrBannerItems);
        updateDots();
    }

    public void updateDots(){
        sliderDotspanel.removeAllViews();
        dotscount = viewPagerAdapter.getCount() - 2;
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageResource(R.drawable.nonactive_dot);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }
        dots[0].setImageResource(R.drawable.active_dot);
    }
}
