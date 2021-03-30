package com.banner.kit.core.scroller;

import android.content.Context;
import android.widget.Scroller;

/**
 * Author: 信仰年轻
 * Date: 2020-09-22 17:41
 * Email: hydznsqk@163.com
 * Des: 黑科技用于设置滚动的时长
 */
public class BannerScroller extends Scroller {
    /**
     * 值越大，滑动越慢
     */
    private int mDuration = 1000;

    public BannerScroller(Context context, int duration) {
        super(context);
        mDuration = duration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}
