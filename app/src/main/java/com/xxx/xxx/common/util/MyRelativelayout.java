package com.xxx.xxx.common.util;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MyRelativelayout extends RelativeLayout {
    public MyRelativelayout(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if (child.getVisibility() != GONE) {
                child.layout(listX.get(i), listY.get(i), childWidth + listX.get(i), childHeight + listY.get(i));
            }
        }
    }

    List<Integer> listX = new ArrayList<>();
    List<Integer> listY = new ArrayList<>();

    public void setChildPosition(int posx, int posy) {
        listX.add(posx);
        listY.add(posy);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }
}
