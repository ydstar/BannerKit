package com.banner.kit.indicator;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.banner.kit.util.DisplayUtil;


/**
 * Author: 信仰年轻
 * Date: 2020-09-22 19:10
 * Email: hydznsqk@163.com
 * Des: 数字指示器
 */
public class NumIndicator extends FrameLayout implements BannerIndicator<FrameLayout> {
    private static final int VWC = ViewGroup.LayoutParams.WRAP_CONTENT;
    /**
     * 指示点左右内间距
     */
    private int mPointLeftRightPadding;

    /**
     * 指示点上下内间距
     */
    private int mPointTopBottomPadding;


    public NumIndicator(Context context) {
        this(context, null);
    }

    public NumIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPointLeftRightPadding = DisplayUtil.dp2px(10, getContext().getResources());
        mPointTopBottomPadding = DisplayUtil.dp2px(10, getContext().getResources());
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
        groupView.setPadding(0, 0, mPointLeftRightPadding, mPointTopBottomPadding);

        TextView indexTv = new TextView(getContext());
        indexTv.setText("1");
        indexTv.setTextColor(Color.WHITE);
        groupView.addView(indexTv);

        TextView symbolTv = new TextView(getContext());
        symbolTv.setText(" / ");
        symbolTv.setTextColor(Color.WHITE);
        groupView.addView(symbolTv);

        TextView countTv = new TextView(getContext());
        countTv.setText(String.valueOf(count));
        countTv.setTextColor(Color.WHITE);
        groupView.addView(countTv);

        LayoutParams groupViewParams = new LayoutParams(VWC, VWC);
        groupViewParams.gravity = Gravity.END | Gravity.BOTTOM;
        addView(groupView, groupViewParams);
    }

    @Override
    public void onPointChange(int current, int count) {
        ViewGroup viewGroup = (ViewGroup) getChildAt(0);
        TextView indexTv = (TextView) viewGroup.getChildAt(0);
        TextView countTv = (TextView) viewGroup.getChildAt(2);
        indexTv.setText(String.valueOf(current + 1));
        countTv.setText(String.valueOf(count));
    }

}
