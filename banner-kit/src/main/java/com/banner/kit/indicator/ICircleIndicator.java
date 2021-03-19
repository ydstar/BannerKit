package com.banner.kit.indicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.banner.kit.R;
import com.banner.kit.util.IDisplayUtil;


/**
 * Author: 信仰年轻
 * Date: 2020-09-26 16:56
 * Email: hydznsqk@163.com
 * Des: 圆形指示器 java版本
 */
public class ICircleIndicator extends FrameLayout
        implements IIndicator<FrameLayout> {


    private static final int VWC = ViewGroup.LayoutParams.WRAP_CONTENT;

    /**
     * 正常状态下的指示点
     */
    @DrawableRes
    private int mPointNormal = R.drawable.shape_point_normal;

    /**
     * 选中状态下的指示点
     */
    @DrawableRes
    private int mPointSelected = R.drawable.shape_point_select;

    /**
     * 指示点左右内间距
     */
    private int mPointLeftRightPadding;

    /**
     * 指示点上下内间距
     */
    private int mPointTopBottomPadding;


    public ICircleIndicator(@NonNull Context context) {
        this(context, null);
    }

    public ICircleIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ICircleIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPointLeftRightPadding = IDisplayUtil.dp2px(5f, context.getResources());
        mPointTopBottomPadding = IDisplayUtil.dp2px(15f, context.getResources());
    }


    @Override
    public FrameLayout get() {
        return this;
    }

    @Override
    public void onInflate(int count) {
        removeAllViews();
        if (count <= 0) {
            return;
        }
        LinearLayout groupView = new LinearLayout(getContext());
        groupView.setOrientation(LinearLayout.HORIZONTAL);

        ImageView imageView;
        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(VWC, VWC);
        imageViewParams.gravity = Gravity.CENTER_VERTICAL;
        imageViewParams.setMargins(mPointLeftRightPadding, mPointTopBottomPadding, mPointLeftRightPadding, mPointTopBottomPadding);

        for (int x = 0; x < count; x++) {
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(imageViewParams);
            if (x == 0) {
                imageView.setImageResource(mPointSelected);
            } else {
                imageView.setImageResource(mPointNormal);
            }
            groupView.addView(imageView);

        }
        LayoutParams groupViewParams = new LayoutParams(VWC, VWC);
        groupViewParams.gravity = Gravity.CENTER | Gravity.BOTTOM;
        addView(groupView, groupViewParams);
    }

    @Override
    public void onPointChange(int current, int count) {
        ViewGroup viewGroup = (ViewGroup) getChildAt(0);

        for (int x = 0; x < viewGroup.getChildCount(); x++) {
            ImageView imageView = (ImageView) viewGroup.getChildAt(x);
            if (x == current) {
                imageView.setImageResource(mPointSelected);
            } else {
                imageView.setImageResource(mPointNormal);
            }
            imageView.requestLayout();
        }
    }
}
