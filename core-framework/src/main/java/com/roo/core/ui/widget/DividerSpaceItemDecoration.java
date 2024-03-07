package com.roo.core.ui.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView设置item上下间距
 */
public class DividerSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public DividerSpaceItemDecoration(int spacePx) {
        this.space = spacePx;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildPosition(view) != 0)
            outRect.top = space;
    }
}
