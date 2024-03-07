package com.xyzlf.custom.keyboardlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import androidx.core.content.ContextCompat;

public class KeyboardGirdView extends GridView {

    private Paint localPaint;
    private int strokeWidth = 1;
    private int color;
    private int defValueLineColor;

    public KeyboardGirdView(Context context) {
        this(context, null);
    }

    public KeyboardGirdView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyboardGirdView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        defValueLineColor = ContextCompat.getColor(context, android.R.color.transparent);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KeyboardGridView);
        strokeWidth = (int) typedArray.getDimension(R.styleable.KeyboardGridView_grid_line_size, strokeWidth);
        color = typedArray.getColor(R.styleable.KeyboardGridView_grid_line_color, defValueLineColor);
        typedArray.recycle();

        init();
    }

    private void init() {
        localPaint = new Paint();
        localPaint.setStrokeWidth(strokeWidth);
        localPaint.setStyle(Paint.Style.STROKE);
        localPaint.setColor(color);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        View localView1 = getChildAt(0);
        int column = getWidth() / localView1.getWidth();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View cellView = getChildAt(i);
            if ((i + 1) % column == 0) {
                canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(), localPaint);
            } else if ((i + 1) > (childCount - (childCount % column))) {
                canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(), localPaint);
            } else {
                canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(), localPaint);
                canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(), localPaint);
            }
        }
        if (childCount % column != 0) {
            for (int j = 0; j < (column - childCount % column); j++) {
                View lastView = getChildAt(childCount - 1);
                canvas.drawLine(lastView.getRight() + lastView.getWidth() * j, lastView.getTop(), lastView.getRight() + lastView.getWidth() * j, lastView.getBottom(), localPaint);
            }
        }
    }


}
