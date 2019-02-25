package com.github.library.layoutView;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * GildView间距设置
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int column;
    private int header;
    private boolean haveHeader;

    public SpacesItemDecoration(int space, int column) {
        this.space = space;
        this.column = column;
    }

    public SpacesItemDecoration(int space, int column, boolean haveHeader) {
        this.space = space;
        this.column = column;
        this.haveHeader = haveHeader;
        if (haveHeader) {
            this.header = 1;
        } else {
            this.header = 0;
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        outRect.left = space;
        outRect.bottom = space;


        if (haveHeader && parent.getChildLayoutPosition(view) == 0) {
            outRect.left = 0;
            outRect.bottom = 0;
        }
        // 只有列与列中间有间距的代码
        // Add top margin only for the first item to avoid double space between items
        // 除过第一列外，第二，三列都有左间距
        // 有头部局时候得减去头部局数量1
        if ((parent.getChildLayoutPosition(view) - header) % column == 0) {
            outRect.left = 0;
        }

    }
}