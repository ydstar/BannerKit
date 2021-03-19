package com.banner.kit.core.pager;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;


import com.banner.kit.core.adapter.IBannerAdapter;
import com.banner.kit.core.scroller.IBannerScroller;

import java.lang.reflect.Field;

/**
 * Author: 信仰年轻
 * Date: 2020-09-22 15:03
 * Email: hydznsqk@163.com
 * Des: 实现了自动翻页的ViewPager
 */
public class IViewPager extends ViewPager {

    //轮播的间隔时间
    private int mIntervalTime;

    //是否开启自动轮播
    private boolean mAutoPlay = true;

    private boolean isLayout;
    private Handler mHandler = new Handler();

    public IViewPager(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        isLayout=true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            //手指抬起的时候开启自动轮播(如果开了自动轮播的话)
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                start();
                break;

            default:
                stop();
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 开始轮播
     */
    private void start() {
        mHandler.removeCallbacksAndMessages(null);
        //如果开启了自动轮播才会发送消息去进行轮播
        if(mAutoPlay){
            mHandler.postDelayed(mRunnable,mIntervalTime);
        }
    }

    /**
     * 停止轮播
     */
    private void stop() {
        //停止Timer
        mHandler.removeCallbacksAndMessages(null);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            next();
            //延时一定时间执行下一次
            mHandler.postDelayed(this,mIntervalTime);
        }
    };

    /**
     * 显示下一个Item
     */
    private int next() {
        int nextPosition = -1;
        if(getAdapter()==null || getAdapter().getCount()<=1){
            stop();
            return nextPosition;
        }

        nextPosition=getCurrentItem()+1;
        //下一个索引大于adapter的view的最大数量时重新开始
        if(nextPosition>=getAdapter().getCount()){
            nextPosition = ((IBannerAdapter) getAdapter()).getFirstItem();
        }

        setCurrentItem(nextPosition,true);
        return nextPosition;
    }


    /**
     * 设置轮播间隔的时间
     * @param intervalTime
     */
    public void setIntervalTime(int intervalTime) {
        this.mIntervalTime = intervalTime;
    }

    /**
     * 设置是否自动播放
     * @param autoPlay
     */
    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
        if (!mAutoPlay) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    /**
     * 黑科技
     * 设置ViewPager的滚动速度
     *
     * @param duration page切换的时间长度
     */
    public void setScrollDuration(int duration) {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            scrollerField.set(this, new IBannerScroller(getContext(), duration));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

///////////////////fix bug///////////////////////////////////////////
    //viewpager可见就会重新走下面的方法
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isLayout && getAdapter() != null && getAdapter().getCount() > 0) {
            try {
                //fix 使用RecyclerView + ViewPager bug https://blog.csdn.net/u011002668/article/details/72884893
                Field mScroller = ViewPager.class.getDeclaredField("mFirstLayout");
                mScroller.setAccessible(true);
                mScroller.set(this, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        start();
    }

    //viewpager 消失不可见就会回收
    @Override
    protected void onDetachedFromWindow() {
        //fix 使用RecyclerView + ViewPager bug
        if (((Activity) getContext()).isFinishing()) {
            super.onDetachedFromWindow();
        }
        stop();
    }

}
