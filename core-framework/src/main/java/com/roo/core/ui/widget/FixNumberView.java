package com.roo.core.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.roo.core.R;
import com.roo.core.utils.ResourceUtil;

import java.util.ArrayList;

public class FixNumberView extends LinearLayout {

    private int maxSize;
    private final ArrayList<View> mViewList = new ArrayList<>();
    private final int mIntervalSize;

    public FixNumberView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FixNumberView);
        mIntervalSize = typedArray.getDimensionPixelSize(R.styleable.FixNumberView_intervalSize, ResourceUtil.dp2px(3));
        int fixHeight = typedArray.getDimensionPixelSize(R.styleable.FixNumberView_fixHeight, -2);
        maxSize = typedArray.getInt(R.styleable.FixNumberView_maxSize, 21);
        int layoutIdType = typedArray.getResourceId(R.styleable.FixNumberView_itemLayoutId, -1);
        int intervalColor = typedArray.getColor(R.styleable.FixNumberView_intervalColor, 0);

        typedArray.recycle();
        for (int i = 0; i < maxSize; i++) {
            LayoutParams layoutParams = new LayoutParams(-1, fixHeight);
            View view =  LayoutInflater.from(context).inflate(layoutIdType, null);

            view.setLayoutParams(layoutParams);
            mViewList.add(view);
            addView(view);
            if (mIntervalSize > 0 && i != (maxSize - 1)) {
                if (intervalColor != 1) {
                    layoutParams = new LayoutParams(-1, mIntervalSize);
                    View viewInterval = new View(context);
                    viewInterval.setBackgroundColor(intervalColor);
                    viewInterval.setLayoutParams(layoutParams);
                    addView(viewInterval);
                }
            }
        }
    }

    public int getIntervalSize() {
        return mIntervalSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public View getViewByIndex(int position) {
        return mViewList.get(position);
    }

}
