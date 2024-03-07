package com.roo.shine;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

/**
 * author : Eric
 * e-mail : yuanshuai@bertadata.com
 * time   : 2018/01/08
 * desc   : xxxx描述
 * version: 1.0
 */

public class LinearGradientView extends FrameLayout {

    private Bitmap mMaskBitmap;

    public LinearGradientView(Context context) {
        this(context, null);
    }

    public LinearGradientView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public LinearGradientView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#FFFFFFFF"));
        if (mMaskBitmap == null && (w > 0 && h > 0)) {
            mMaskBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Bitmap temp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas maskCanvas = new Canvas(mMaskBitmap);
            Canvas tempCanvas = new Canvas(temp);
            tempCanvas.save();
            tempCanvas.rotate(45, w / 2f, h / 2f);
            int padding = (int) (Math.sqrt(2) * Math.max(w, h)) / 2;
            Paint tempPaint = new Paint();
            tempPaint.setColor(Color.parseColor("#80FFFFFF"));
            tempCanvas.drawRect(w / 2f, -padding, w / 2f + 100, h + padding, tempPaint);
            tempCanvas.drawRect(w / 2f + 45, -padding, w / 2f + 45, h + padding, tempPaint);
            tempCanvas.restore();
            maskCanvas.drawBitmap(temp, 0, 0, mPaint);
            mPaint.setXfermode(null);
        }
        x0ff = -w;
    }

    float x0ff;

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawShineMask(canvas);
    }

    protected void drawShineMask(Canvas canvas) {
        canvas.drawBitmap(mMaskBitmap, -x0ff, 0, null);
    }

    public void startAnim() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "xoff", getWidth(), 0, -getWidth());
        objectAnimator.setDuration(1500);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.setStartDelay(2000);
        objectAnimator.start();
    }

    private void setXoff(float off) {
        x0ff = off;
        invalidate();
    }
}
