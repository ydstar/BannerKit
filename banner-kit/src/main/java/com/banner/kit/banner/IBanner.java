package com.banner.kit.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.banner.kit.R;
import com.banner.kit.core.adapter.IBannerModel;
import com.banner.kit.indicator.IIndicator;

import java.util.List;


/**
 * Author: 信仰年轻
 * Date: 2020-09-22 17:05
 * Email: hydznsqk@163.com
 * Des: 轮播图
 * 思路:
 *     1.创建自动翻页的ViewPager类IViewPager
 *     2.创建适配器IBannerAdapter,为IViewPager页面填充数据
 *     3.创建指示器IIndicator的实现类
 *     4.添加IViewPager到该容器
 *     5.添加IIndicator到该容器
 */
public class IBanner extends FrameLayout implements IBannerInter{

    private IBannerDelegate mDelegate;

    public IBanner(@NonNull Context context) {
        this(context,null);
    }

    public IBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDelegate = new IBannerDelegate(context,this);
        initCustomAttrs(context, attrs);
    }
    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.iBanner);
        boolean autoPlay = typedArray.getBoolean(R.styleable.iBanner_autoPlay, true);
        boolean loop = typedArray.getBoolean(R.styleable.iBanner_loop, false);
        int intervalTime = typedArray.getInteger(R.styleable.iBanner_intervalTime, -1);
        setAutoPlay(autoPlay);
        setLoop(loop);
        setIntervalTime(intervalTime);
        typedArray.recycle();
    }

    @Override
    public void setBannerData(int layoutResId, @NonNull List<? extends IBannerModel> data) {
        mDelegate.setBannerData(layoutResId,data);
    }

    @Override
    public void setBannerData(@NonNull List<? extends IBannerModel> data) {
        mDelegate.setBannerData(data);
    }

    @Override
    public void setIndicator(IIndicator indicator) {
        mDelegate.setIndicator(indicator);
    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        mDelegate.setAutoPlay(autoPlay);
    }

    @Override
    public void setLoop(boolean loop) {
        mDelegate.setLoop(loop);
    }

    @Override
    public void setIntervalTime(int intervalTime) {
        mDelegate.setIntervalTime(intervalTime);
    }

    @Override
    public void setBindAdapter(IBindAdapter bindAdapter) {
        mDelegate.setBindAdapter(bindAdapter);
    }

    @Override
    public void setScrollDuration(int duration) {
        mDelegate.setScrollDuration(duration);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mDelegate.setOnPageChangeListener(onPageChangeListener);
    }

    @Override
    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        mDelegate.setOnBannerClickListener(onBannerClickListener);
    }
}
