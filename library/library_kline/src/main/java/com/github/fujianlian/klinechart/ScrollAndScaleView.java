package com.github.fujianlian.klinechart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.OverScroller;
import android.widget.RelativeLayout;

import androidx.core.view.GestureDetectorCompat;

/**
 * 可以滑动和放大的view
 * Created by tian on 2016/5/3.
 */
public abstract class ScrollAndScaleView extends RelativeLayout implements
        GestureDetector.OnGestureListener,
        ScaleGestureDetector.OnScaleGestureListener {
    protected int mScrollX = 0;
    protected GestureDetectorCompat mDetector;
    protected ScaleGestureDetector mScaleDetector;

    protected boolean isLongPress = false;
    protected boolean touch = false;
    protected float mScaleX = 1;
    protected float mScaleXMax = 2f;
    protected float mScaleXMin = 0.5f;
    float x;
    float y;
    private OverScroller mScroller;
    private boolean mMultipleTouch = false;
    private boolean mScrollEnable = true;
    private boolean mScaleEnable = true;
    private boolean isHorizontalSlide = false;
    private boolean isDoubleTouch = false;

    public ScrollAndScaleView(Context context) {
        this(context,null);
    }

    public ScrollAndScaleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScrollAndScaleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        mDetector = new GestureDetectorCompat(getContext(), this);
        mScaleDetector = new ScaleGestureDetector(getContext(), this);
        mScroller = new OverScroller(getContext());
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (!isLongPress && !isMultipleTouch()) {
            scrollBy(Math.round(distanceX), 0);
            return true;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        isLongPress = true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (!isTouch() && isScrollEnable()) {
            mScroller.fling(mScrollX, 0
                    , Math.round(velocityX / mScaleX), 0,
                    Integer.MIN_VALUE, Integer.MAX_VALUE,
                    0, 0);
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (!isTouch()) {
                scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            } else {
                mScroller.forceFinished(true);
            }
        }
    }

    @Override
    public void scrollBy(int x, int y) {
        scrollTo(mScrollX - Math.round(x / mScaleX), 0);
    }

    @Override
    public void scrollTo(int x, int y) {
        if (!isScrollEnable()) {
            mScroller.forceFinished(true);
            return;
        }
        int oldX = mScrollX;
        mScrollX = x;
        if (mScrollX < getMinScrollX()) {
            mScrollX = getMinScrollX();
            onRightSide();
            mScroller.forceFinished(true);
        } else if (mScrollX > getMaxScrollX()) {
            mScrollX = getMaxScrollX();
            onLeftSide();
            mScroller.forceFinished(true);
        }
        onScrollChanged(mScrollX, 0, oldX, 0);
        invalidate();
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        if (!isScaleEnable()) {
            return false;
        }
        float oldScale = mScaleX;
        mScaleX *= detector.getScaleFactor();
        if (mScaleX < mScaleXMin) {
            mScaleX = mScaleXMin;
        } else if (mScaleX > mScaleXMax) {
            mScaleX = mScaleXMax;
        } else {
            onScaleChanged(mScaleX, oldScale);
        }
        return true;
    }

    protected void onScaleChanged(float scale, float oldScale) {
        invalidate();
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    public boolean isHorizontalSlide() {
        return isHorizontalSlide;
    }

    public void setHorizontalSlide(boolean horizontalSlide) {
        isHorizontalSlide = horizontalSlide;
    }

    public boolean isDoubleTouch() {
        return isDoubleTouch;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 按压手指超过1个
        if (event.getPointerCount() > 1) {
            isLongPress = false;
            isDoubleTouch = true;
        } else {
            isDoubleTouch = false;
        }
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                touch = true;
                x = event.getX();
                y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                float dX = (event.getX() - x);
                float dY = (event.getY() - y);

                //左右滑动
                //上下滑动
                isHorizontalSlide = Math.abs(dX) >= Math.abs(dY);

                if (isLongPress) {
                    onLongPress(event);
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (x == event.getX()) {
                    if (isLongPress) {
                        isLongPress = false;
                    }
                }
                touch = false;
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                isLongPress = false;
                touch = false;
                invalidate();
                break;
            default:
                break;
        }
        mMultipleTouch = event.getPointerCount() > 1;
        this.mDetector.onTouchEvent(event);
        this.mScaleDetector.onTouchEvent(event);
        return true;
    }


    /**
     * 滑到了最左边
     */
    abstract public void onLeftSide();

    /**
     * 滑到了最右边
     */
    abstract public void onRightSide();

    /**
     * 是否在触摸中
     *
     * @return
     */
    public boolean isTouch() {
        return touch;
    }

    /**
     * 获取位移的最小值
     *
     * @return
     */
    public abstract int getMinScrollX();

    /**
     * 获取位移的最大值
     *
     * @return
     */
    public abstract int getMaxScrollX();

    /**
     * 设置ScrollX
     *
     * @param scrollX
     */
    public void setScrollX(int scrollX) {
        this.mScrollX = scrollX;
        scrollTo(scrollX, 0);
    }

    /**
     * 是否是多指触控
     *
     * @return
     */
    public boolean isMultipleTouch() {
        return mMultipleTouch;
    }

    protected void checkAndFixScrollX() {
        if (mScrollX < getMinScrollX()) {
            mScrollX = getMinScrollX();
            mScroller.forceFinished(true);
        } else if (mScrollX > getMaxScrollX()) {
            mScrollX = getMaxScrollX();
            mScroller.forceFinished(true);
        }
    }

    public float getScaleXMax() {
        return mScaleXMax;
    }

    /**
     * 设置缩放的最大值
     */
    public void setScaleXMax(float scaleXMax) {
        mScaleXMax = scaleXMax;
    }

    public float getScaleXMin() {
        return mScaleXMin;
    }

    /**
     * 设置缩放的最小值
     */
    public void setScaleXMin(float scaleXMin) {
        mScaleXMin = scaleXMin;
    }

    public boolean isScrollEnable() {
        return mScrollEnable;
    }

    /**
     * 设置是否可以滑动
     */
    public void setScrollEnable(boolean scrollEnable) {
        mScrollEnable = scrollEnable;
    }

    public boolean isScaleEnable() {
        return mScaleEnable;
    }

    /**
     * 设置是否可以缩放
     */
    public void setScaleEnable(boolean scaleEnable) {
        mScaleEnable = scaleEnable;
    }

    @Override
    public float getScaleX() {
        return mScaleX;
    }
}
