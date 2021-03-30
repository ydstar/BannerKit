package com.banner.kit.banner;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.banner.kit.R;
import com.banner.kit.core.adapter.BannerAdapter;
import com.banner.kit.core.adapter.BannerModel;
import com.banner.kit.core.pager.MyViewPager;
import com.banner.kit.indicator.CircleIndicator;
import com.banner.kit.indicator.BannerIndicator;

import java.util.List;


/**
 * Author: 信仰年轻
 * Date: 2020-09-22 18:38
 * Email: hydznsqk@163.com
 * Des:辅助iBanner完成各种功能的控制
 * 将iBanner的一些逻辑内聚在这，保证暴露给使用者的iBanner干净整洁
 */
public class BannerDelegate implements IBannerInter, ViewPager.OnPageChangeListener {

    private Context mContext;
    private BannerKit mBanner;

    private List<? extends BannerModel> mDataList;
    private BannerAdapter mAdapter;
    private MyViewPager mViewPager;

    //指示器
    private BannerIndicator mIndicator;
    //是否自动播放
    private boolean mAutoPlay;
    //播放间隔时间
    private int mIntervalTime = 3000;
    //是否循环
    private boolean mLoop;
    //滚动的时长
    private int mScrollDuration = -1;
    //pagerChange的监听
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    //轮播图点击的回调接口
    private BannerKit.OnBannerClickListener mOnBannerClickListener;

    public BannerDelegate(Context context, BannerKit iBanner) {
        this.mContext = context;
        this.mBanner = iBanner;
    }

    @Override
    public void setBannerData(@NonNull List<? extends BannerModel> data) {
        setBannerData(R.layout.i_banner_item_image, data);

    }

    @Override
    public void setBannerData(int layoutResId, @NonNull List<? extends BannerModel> data) {
        if(data==null){
            throw new RuntimeException("轮播图数据不能为空");
        }
        this.mDataList = data;
        init(layoutResId);
    }

    @Override
    public void setIndicator(BannerIndicator indicator) {
        if(indicator==null){
            throw new RuntimeException("指示器不能为空");
        }
        this.mIndicator = indicator;
    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
    }

    @Override
    public void setLoop(boolean loop) {
        this.mLoop = loop;
    }

    @Override
    public void setIntervalTime(int intervalTime) {
        if(intervalTime>0){
            this.mIntervalTime = intervalTime;
        }
    }

    @Override
    public void setBindAdapter(IBindAdapter bindAdapter) {
        if (mAdapter == null) {
            mAdapter = new BannerAdapter(mContext);
        }
        mAdapter.setBindAdapter(bindAdapter);
    }

    @Override
    public void setScrollDuration(int duration) {
        this.mScrollDuration = duration;
        if (mViewPager != null && duration > 0) {
            mViewPager.setScrollDuration(duration);
        }
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    @Override
    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.mOnBannerClickListener = onBannerClickListener;
        if (mAdapter == null) {
            mAdapter = new BannerAdapter(mContext);
        }
        mAdapter.setBannerClickListener(mOnBannerClickListener);
    }

    /**
     * @param layoutResId
     */
    private void init(int layoutResId) {
        if (mAdapter == null) {
            mAdapter = new BannerAdapter(mContext);
        }
        if (mIndicator == null) {
            mIndicator = new CircleIndicator(mContext);
        }
        mIndicator.onInflate(mDataList.size());

        mAdapter.setLayoutResId(layoutResId);
        mAdapter.setBannerData(mDataList);
        mAdapter.setAutoPlay(mAutoPlay);
        mAdapter.setLoop(mLoop);
        if(mOnBannerClickListener!=null){
            mAdapter.setBannerClickListener(mOnBannerClickListener);
        }

        mViewPager = new MyViewPager(mContext);
        mViewPager.setIntervalTime(mIntervalTime);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAutoPlay(mAutoPlay);
        if (mScrollDuration > 0) {
            mViewPager.setScrollDuration(mScrollDuration);
        }
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        mViewPager.setAdapter(mAdapter);

        if ((mLoop || mAutoPlay) && mAdapter.getRealCount() != 0) {
            //无限轮播关键点：使第一张能反向滑动到最后一张，已达到无限滚动的效果
            int firstItem = mAdapter.getFirstItem();
            mViewPager.setCurrentItem(firstItem, false);
        }

        //清除缓存view
        mBanner.removeAllViews();
        mBanner.addView(mViewPager, layoutParams);
        mBanner.addView(mIndicator.get(), layoutParams);

    }

    ///////////////////////viewpager的监听////////////////////////////////////////////////
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (null != mOnPageChangeListener && mAdapter.getRealCount() != 0) {
            mOnPageChangeListener.onPageScrolled(position % mAdapter.getRealCount(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mAdapter.getRealCount() == 0) {
            return;
        }
        position = position % mAdapter.getRealCount();
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
        if (mIndicator != null) {
            mIndicator.onPointChange(position, mAdapter.getRealCount());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }
}
