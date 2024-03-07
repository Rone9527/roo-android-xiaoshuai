package com.roo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义星级评定控件
 */
public class RatingBarView extends View {
    private int starDistance = 0; //星星间距
    private int starCount = 5;  //星星个数
    private int starSize;     //星星高度大小，星星一般正方形，宽度等于高度
    private float starMark = 0.0F;   //评分星星
    private Bitmap starFillBitmap; //亮星星
    private Drawable starEmptyDrawable; //暗星星
    private OnStarChangeListener onStarChangeListener;//监听星星变化接口
    private Paint paint;         //绘制星星画笔
    private Paint frameAnimaPaint;//绘制帧动画每一帧画笔
    private boolean integerMark = false;
    private Handler animaHandler = new Handler(Looper.getMainLooper());
    private int animaResListIndex = 0;
    private int frameAnimaDuration = 60;//帧动画每一帧时长
    private boolean isCanClick = false;//是否可以点击
    private int[] animaResList = null;

    public RatingBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RatingBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化UI组件
     *
     * @param context 当前视图
     * @param attrs   参数
     */
    private void init(Context context, AttributeSet attrs) {
        setClickable(true);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBarView);
        starDistance = (int) mTypedArray.getDimension(R.styleable.RatingBarView_starDistance, 6);
        starSize = (int) mTypedArray.getDimension(R.styleable.RatingBarView_starSize, 20);
        starCount = mTypedArray.getInteger(R.styleable.RatingBarView_starCount, 0);
        starEmptyDrawable = mTypedArray.getDrawable(R.styleable.RatingBarView_starEmpty);
        starFillBitmap = drawableToBitmap(mTypedArray.getDrawable(R.styleable.RatingBarView_starFill));
        mTypedArray.recycle();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(starFillBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));

        frameAnimaPaint = new Paint();
        frameAnimaPaint.setAntiAlias(true);
    }

    /**
     * 设置是否需要整数评分
     *
     * @param integerMark 星星是否为整数还是小数
     */
    public void setIntegerMark(boolean integerMark) {
        this.integerMark = integerMark;
    }

    /**
     * 设置帧动画每一帧的时长
     *
     * @param animaDuration
     */
    public void setFrameAnimaDuration(int animaDuration) {
        this.frameAnimaDuration = animaDuration;
    }

    public void setAnimaResList(int[] animaResList) {
        this.animaResList = animaResList;
    }

    /**
     * 获取显示星星的数目
     *
     * @return starMark 星星的个数
     */
    public float getStarMark() {
        return starMark;
    }

    /**
     * 设置显示的星星的分数
     *
     * @param mark 分数
     */
    public void setStarMark(float mark) {
        if (integerMark) {
            starMark = (int) Math.ceil(mark);
        } else {
            starMark = Math.round(mark * 10) * 1.0f / 10;
        }

        if ((starMark - (int) starMark) >= 0.5) {
            starMark = (int) starMark + 0.5f;
        } else {
            starMark = (int) starMark;
        }

        if (this.onStarChangeListener != null) {
            this.onStarChangeListener.onStarChange(starMark);  //调用监听接口
        }
        animaResListIndex = 0;
        if (animaResList != null && animaResList.length > 1) {
            animaHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (animaResList != null && animaResListIndex < animaResList.length) {
                        Bitmap tempBitamp = drawableToBitmap(getContext().getResources().getDrawable(animaResList[animaResListIndex]));
                        frameAnimaPaint.setShader(new BitmapShader(tempBitamp, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
                        animaHandler.postDelayed(this, frameAnimaDuration);
                        invalidate();
                        animaResListIndex++;
                    }
                }
            }, 0);
        } else {
            invalidate();
        }
    }

    /**
     * 设置监听
     *
     * @param onStarChangeListener 点击星星图标后触发
     */
    public void setOnStarChangeListener(OnStarChangeListener onStarChangeListener) {
        this.onStarChangeListener = onStarChangeListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(starSize * starCount + starDistance * (starCount - 1), starSize);
    }

    //绘制星星
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (starFillBitmap == null || starEmptyDrawable == null) {
            return;
        }
        for (int i = (starCount - 1); i >= (int) starMark; i--) {
            starEmptyDrawable.setBounds((starDistance + starSize) * i, 0, (starDistance + starSize) * i + starSize, starSize);
            starEmptyDrawable.draw(canvas);
        }
        if (starMark > 1) {
            canvas.drawRect(0, 0, starSize, starSize, paint);
            if (starMark - (int) (starMark) == 0) {
                for (int i = 1; i < starMark; i++) {
                    canvas.translate(starDistance + starSize, 0);
                    if (i == (starMark - 1) && animaResList != null && animaResList.length > 1) {//最后一个星星并且有帧动画资源超过两个的时候用帧动画画笔绘制
                        canvas.drawRect(0, 0, starSize, starSize, frameAnimaPaint);
                    } else {
                        canvas.drawRect(0, 0, starSize, starSize, paint);
                    }
                }
            } else {
                for (int i = 0; i < (int) starMark - 1; i++) {
                    canvas.translate(starDistance + starSize, 0);
                    canvas.drawRect(0, 0, starSize, starSize, paint);
                }

                canvas.translate(starDistance + starSize, 0);
                if (animaResList != null && animaResList.length > 1) {
                    canvas.drawRect(0, 0, starSize * (Math.round((starMark - (int) (starMark)) * 10) * 1.0f / 10), starSize, frameAnimaPaint);
                } else {
                    canvas.drawRect(0, 0, starSize * (Math.round((starMark - (int) (starMark)) * 10) * 1.0f / 10), starSize, paint);
                }
            }
        } else {
            if (animaResList != null && animaResList.length > 1) {
                canvas.drawRect(0, 0, starSize * starMark, starSize, frameAnimaPaint);
            } else {
                canvas.drawRect(0, 0, starSize * starMark, starSize, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isCanClick) {
            return super.onTouchEvent(event);
        }
        int x = (int) event.getX();
        if (x < 0) x = 0;
        if (x > getMeasuredWidth()) x = getMeasuredWidth();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                setStarMark(x * 1.0f / (getMeasuredWidth() * 1.0f / starCount));
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (animaResList == null || animaResList.length <= 1) {
                    setStarMark(x * 1.0f / (getMeasuredWidth() * 1.0f / starCount));
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
        }
        invalidate();
        return super.onTouchEvent(event);
    }

    /**
     * 设置是否可以点击
     *
     * @param canClick
     */
    public void setCanClick(boolean canClick) {
        isCanClick = canClick;
    }

    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return bitmap
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) return null;
        Bitmap bitmap = Bitmap.createBitmap(starSize, starSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, starSize, starSize);
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animaHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 定义星星点击的监听接口 点击星星点亮或者空心
     */
    public interface OnStarChangeListener {
        void onStarChange(float mark);
    }
}