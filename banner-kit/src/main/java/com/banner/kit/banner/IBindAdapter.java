package com.banner.kit.banner;


import com.banner.kit.core.adapter.IBannerAdapter;
import com.banner.kit.core.adapter.IBannerModel;

/**
 * Author: 信仰年轻
 * Date: 2020-09-22 18:07
 * Email: hydznsqk@163.com
 * Des: 数据的对外接口,用来赋值和解耦的
 */
public interface IBindAdapter {
    void onBind(IBannerAdapter.IBannerViewHolder viewHolder, IBannerModel model, int position);

}
