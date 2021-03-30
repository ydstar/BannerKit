package com.banner.kit.indicator;

import android.view.View;

/**
 * Author: 信仰年轻
 * Date: 2020-09-22 17:06
 * Email: hydznsqk@163.com
 * Des: 指示器的顶层接口,实现该接口来定义你需要样式的指示器
 */
public interface BannerIndicator<T extends View> {
    T get();

    /**
     * 初始化Indicator
     *
     * @param count 幻灯片数量
     */
    void onInflate(int count);

    /**
     * 幻灯片切换回调
     *
     * @param current 切换到的幻灯片位置
     * @param count   幻灯片数量
     */
    void onPointChange(int current, int count);
}
