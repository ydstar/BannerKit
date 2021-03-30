package com.banner.kit.core.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import com.banner.kit.banner.IBannerInter;
import com.banner.kit.banner.IBindAdapter;

import java.util.List;

/**
 * Author: 信仰年轻
 * Date: 2020-09-22 17:38
 * Email: hydznsqk@163.com
 * Des: iViewPager的适配器，为页面填充数据
 */
public class BannerAdapter extends PagerAdapter {

    private Context mContext;
    private SparseArray<IBannerViewHolder> mCachedViews = new SparseArray<>();
    private List<? extends BannerModel> mDataList;

    //布局资源ID
    private int mLayoutResId = -1;

    //非自动轮播状态下是否可以循环切换
    private boolean mLoop = false;

    //是否开启自动轮
    private boolean mAutoPlay = true;

    //对外的绑定数据的接口
    private IBindAdapter mBindAdapter;

    //轮播图点击事件的回调接口
    private IBannerInter.OnBannerClickListener mBannerClickListener;

    public BannerAdapter(@NonNull Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 入口
     * 设置轮播图的数据
     *
     * @param list
     */
    public void setBannerData(List<? extends BannerModel> list) {
        this.mDataList = list;
        initCachedView();
        notifyDataSetChanged();
    }

    /**
     * 初始化缓存的viewHolder,并存到mCachedViews中
     */
    private void initCachedView() {
        mCachedViews = new SparseArray<>();
        for (int i = 0; i < mDataList.size(); i++) {
            IBannerViewHolder viewHolder = new IBannerViewHolder(createView(LayoutInflater.from(mContext), null));
            mCachedViews.put(i, viewHolder);
        }
    }

    /**
     * 根据传入的布局资源ID去创建对应的view
     */
    private View createView(LayoutInflater layoutInflater, ViewGroup parent) {
        if (mLayoutResId == -1) {
            throw new IllegalArgumentException("还未设置轮播图的布局");
        }
        return layoutInflater.inflate(mLayoutResId, parent, false);
    }

    /**
     * 设置布局资源ID
     *
     * @param layoutResId
     */
    public void setLayoutResId(@LayoutRes int layoutResId) {
        this.mLayoutResId = layoutResId;
    }

    /**
     * 设置是否循环
     *
     * @param loop
     */
    public void setLoop(boolean loop) {
        this.mLoop = loop;
    }

    /**
     * 设置自动播放
     *
     * @param autoPlay
     */
    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
    }


    /**
     * 设置对外的绑定数据接口
     *
     * @param bindAdapter
     */
    public void setBindAdapter(IBindAdapter bindAdapter) {
        this.mBindAdapter = bindAdapter;
    }

    /**
     * 设置轮播图点击的对外接口b
     *
     * @param bannerClickListener
     */
    public void setBannerClickListener(IBannerInter.OnBannerClickListener bannerClickListener) {
        this.mBannerClickListener = bannerClickListener;
    }

///////////////////////////////count///////////////////////////////////////////
    /**
     * 获取初次展示的item位置
     */
    public int getFirstItem() {
        int i = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % getRealCount();
        return i;
    }

    /**
     * 得到真正的数量
     */
    public int getRealCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    ///////////////////////////////adapter的方法///////////////////////////////////////////
    @Override
    public int getCount() {
        return mAutoPlay ? Integer.MAX_VALUE : (mLoop ? Integer.MAX_VALUE : getRealCount());
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition = position;
        if (getRealCount() > 0) {
            realPosition = position % getRealCount();
        }
        //清空
        IBannerViewHolder viewHolder = mCachedViews.get(realPosition);
        if (container.equals(viewHolder.mRootView.getParent())) {
            container.removeView(viewHolder.mRootView);
        }

        onBind(viewHolder, mDataList.get(realPosition), realPosition);
        if (viewHolder.mRootView.getParent() != null) {
            ((ViewGroup) viewHolder.mRootView.getParent()).removeView(viewHolder.mRootView);
        }
        container.addView(viewHolder.mRootView);
        return viewHolder.mRootView;
    }

    private void onBind(final IBannerViewHolder viewHolder, final BannerModel model, final int realPosition) {
        if (mBindAdapter != null) {
            //让外界去绑定数据
            mBindAdapter.onBind(viewHolder, model, realPosition);
        }

        //条目的点击事件
        viewHolder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBannerClickListener != null) {
                    mBannerClickListener.onBannerClick(viewHolder, model, realPosition);
                }
            }
        });
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        //让item每次都会刷新
        return POSITION_NONE;
    }

    public static class IBannerViewHolder {
        private SparseArray<View> viewHolderSparseArr;
        private View mRootView;

        /**
         * 根据ID找到指定的view
         * @param id
         * @param <V>
         * @return
         */
        public <V extends View> V findViewById(int id) {
            if (!(mRootView instanceof ViewGroup)) {
                return (V) mRootView;
            }
            if (this.viewHolderSparseArr == null) {
                this.viewHolderSparseArr = new SparseArray<>(1);
            }

            V childView = (V) viewHolderSparseArr.get(id);
            if (childView == null) {
                childView = mRootView.findViewById(id);
                this.viewHolderSparseArr.put(id, childView);
            }
            return childView;
        }

        public IBannerViewHolder(View rootView) {
            this.mRootView = rootView;
        }

        public View getRootView() {
            return mRootView;
        }
    }
}
