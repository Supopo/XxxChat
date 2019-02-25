package com.hongyu.zorelib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by isle on 2017/7/31.
 */

public class AllGridView extends GridView {
    public AllGridView(Context context) {
        super(context);
    }

    public AllGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AllGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
