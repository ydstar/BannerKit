package com.banner.kit.banner;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.banner.kit.core.adapter.IBannerAdapter;
import com.banner.kit.core.adapter.IBannerModel;
import com.banner.kit.indicator.IIndicator;

import java.util.List;


/**
 * Author: 信仰年轻
 * Date: 2020-09-22 18:10
 * Email: hydznsqk@163.com
 * Des:
 */
public interface IBannerInter {
    /**
     * 设置轮播图的数据
     * @param data 数据
     */
    void setBannerData(@NonNull List<? extends IBannerModel> data);

    /**
     * 设置轮播图的数据
     * @param layoutResId 轮播图布局
     * @param data 数据
     */
    void setBannerData(@LayoutRes int layoutResId, @NonNull List<? extends IBannerModel> data);


    /**
     * 设置指示器
     * @param iIndicator
     */
    void setIndicator(IIndicator iIndicator);

    /**
     * 设置自动轮播
     * @param autoPlay
     */
    void setAutoPlay(boolean autoPlay);

    /**
     * 设置是否可以往复循环
     * @param loop
     */
    void setLoop(boolean loop);

    /**
     * 设置轮播间隔时间
     * @param intervalTime
     */
    void setIntervalTime(int intervalTime);

    /**
     * 设置绑定adapter的数据
     * @param bindAdapter
     */
    void setBindAdapter(IBindAdapter bindAdapter);


    /**
     * 设置滚动的时间
     * @param duration
     */
    void setScrollDuration(int duration);

    /**
     * 设置pagerChange的监听
     * @param onPageChangeListener
     */
    void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener);

    /**
     * 设置轮播图点击的回调接口
     * @param onBannerClickListener
     */
    void setOnBannerClickListener(OnBannerClickListener onBannerClickListener);


    interface OnBannerClickListener {
        void onBannerClick(@NonNull IBannerAdapter.IBannerViewHolder viewHolder, @NonNull IBannerModel model, int position);
    }
}
