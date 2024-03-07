package com.xyzlf.custom.keyboardlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class PasswordView extends AppCompatEditText {

    private static final int DEFAULT_BOARD_SIZE = 0; //默认边框大小
    private static final int DEFAULT_BOARD_RADIUS = 0; //默认边框圆角大小
    private static final int DEFAULT_PWD_CIRCLE_COUNT = 6; //默认密码个数
    private static final int DEFAULT_PWD_CIRCLE_RADIUS = 7; //默认密码半径
    private static final int DEFAULT_PWD_RING_SIZE = 1; //默认空心圆密码大小

    private Paint mPaint;
    private Paint mPaintEmpty;
    private RectF mRectF;

    private int mBgColor;

    private int mBoardColor;
    private int mBoardSize;
    private int mBoardCornerSize;

    private int mDividerColor;
    private int mDividerSize;

    private int mPwdCircleColor;
    private int mPwdRingColor;
    private int mPwdCircleRadius;
    private int mPwdRingSize;

    private int mPwdCircleCount;
    private int mPwdItemWidth;

    private final StringBuilder mPasswordStr;

    public PasswordView(Context context) {
        this(context, null);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initAttributeSet(context, attrs);

        mPasswordStr = new StringBuilder();

        setCursorVisible(false);
        setFocusable(false);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mRectF = new RectF();

        mPaintEmpty = new Paint();
        mPaintEmpty.setAntiAlias(true);

    }

    private void initAttributeSet(Context context, AttributeSet attrs) {
        int boardColor = Color.parseColor("#00FFFFFF");
        int dividerColor = Color.parseColor("#00FFFFFF");
        int circleColor = Color.parseColor("#FF376AFF");
        int ringColor = Color.parseColor("#FFA8B0BC");

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PasswordView);
        mBgColor = array.getColor(R.styleable.PasswordView_pwd_bg_color, Color.WHITE);

        mBoardColor = array.getColor(R.styleable.PasswordView_pwd_board_color, boardColor);
        mBoardSize = (int) array.getDimension(R.styleable.PasswordView_pwd_board_size, ViewUtil.dp2px(context, DEFAULT_BOARD_SIZE));
        mBoardCornerSize = (int) array.getDimension(R.styleable.PasswordView_pwd_board_corner_size, ViewUtil.dp2px(context, DEFAULT_BOARD_RADIUS));

        mDividerColor = array.getColor(R.styleable.PasswordView_pwd_divider_color, dividerColor);
        mDividerSize = (int) array.getDimension(R.styleable.PasswordView_pwd_divider_size, DEFAULT_BOARD_SIZE);

        mPwdRingColor = array.getColor(R.styleable.PasswordView_pwd_ring_color, ringColor);

        mPwdCircleColor = array.getColor(R.styleable.PasswordView_pwd_circle_color, circleColor);
        mPwdCircleRadius = (int) array.getDimension(R.styleable.PasswordView_pwd_circle_radius, ViewUtil.dp2px(context, DEFAULT_PWD_CIRCLE_RADIUS));
        mPwdRingSize = mPwdCircleRadius - (int) array.getDimension(R.styleable.PasswordView_pwd_ring_size, ViewUtil.dp2px(context, DEFAULT_PWD_RING_SIZE));

        mPwdCircleCount = (int) array.getDimension(R.styleable.PasswordView_pwd_circle_count, DEFAULT_PWD_CIRCLE_COUNT);

        array.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPwdItemWidth = (w - (mPwdCircleCount - 1) * mDividerSize) / mPwdCircleCount;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackgroud(canvas);

        drawBoard(canvas);

        drawBoardDivider(canvas);

        drawPasswordEmpty(canvas);

        drawPassword(canvas);
    }

    private void drawPasswordEmpty(Canvas canvas) {
        mPaint.setColor(mPwdRingColor);
        mPaint.setStyle(Paint.Style.FILL);

        mPaintEmpty.setStyle(Paint.Style.FILL);
        mPaintEmpty.setColor(Color.WHITE);

        for (int i = 0; i < mPwdCircleCount; i++) {
            int cx = i * mDividerSize + i * mPwdItemWidth + mPwdItemWidth / 2 + mBoardSize;
            canvas.drawCircle(cx, getHeight() / 2f, mPwdCircleRadius, mPaint);
            canvas.drawCircle(cx, getHeight() / 2f, mPwdRingSize, mPaintEmpty);
        }
    }


    private void drawBackgroud(Canvas canvas) {
        mPaint.setColor(mBgColor);
        mPaint.setStyle(Paint.Style.FILL);
        mRectF.set(0, 0, getWidth(), getHeight());

        if (mBoardCornerSize > 0) {
            canvas.drawRoundRect(mRectF, mBoardCornerSize, mBoardCornerSize, mPaint);
        } else {
            canvas.drawRect(mRectF, mPaint);
        }
    }

    private void drawBoard(Canvas canvas) {
        mPaint.setColor(mBoardColor);
        mPaint.setStrokeWidth(mBoardSize);
        mPaint.setStyle(Paint.Style.STROKE);

        mRectF.set(mBoardSize, mBoardSize, getWidth() - mBoardSize, getHeight() - mBoardSize);

        if (mBoardCornerSize > 0) {
            canvas.drawRoundRect(mRectF, mBoardCornerSize, mBoardCornerSize, mPaint);
        } else {
            canvas.drawRect(mRectF, mPaint);
        }
    }

    private void drawBoardDivider(Canvas canvas) {
        mPaint.setColor(mDividerColor);
        mPaint.setStrokeWidth(mDividerSize);
        mPaint.setStyle(Paint.Style.FILL);

        for (int i = 0; i < mPwdCircleCount - 1; i++) {
            int startX = (i + 1) * mDividerSize + (i + 1) * mPwdItemWidth + mBoardSize;
            canvas.drawLine(startX, mBoardSize, startX, getHeight() - mBoardSize, mPaint);
        }
    }

    private void drawPassword(Canvas canvas) {
        mPaint.setColor(mPwdCircleColor);
        mPaint.setStyle(Paint.Style.FILL);

        int pwdTextLength = getText() == null ? 0 : getText().length();
        for (int i = 0; i < pwdTextLength; i++) {
            int cx = i * mDividerSize + i * mPwdItemWidth + mPwdItemWidth / 2 + mBoardSize;
            canvas.drawCircle(cx, getHeight() / 2f, mPwdCircleRadius, mPaint);
        }
    }

    public void addPassword(int number) {
        if (mPasswordStr.length() >= mPwdCircleCount) {
            return;
        }
        mPasswordStr.append(number);

        String text = mPasswordStr.toString();
        setText(text);

        if (mPasswordStr.length() == mPwdCircleCount) {
            if (onPasswordListener != null) {
                postDelayed(() -> onPasswordListener.onPasswordComplete(text), 100);
            }
        } else {
            if (onPasswordListener != null) {
                onPasswordListener.onPasswordChange(text);
            }
        }
    }

    public void deletePassword() {
        int len = mPasswordStr.length();
        if (len <= 0) {
            return;
        }
        mPasswordStr.deleteCharAt(len - 1);
        String text = mPasswordStr.toString();
        setText(text);

        if (onPasswordListener != null) {
            onPasswordListener.onPasswordChange(text);
        }
    }

    public void clearPassword() {
        mPasswordStr.delete(0, mPasswordStr.length());
        setText("");
    }

    private OnPasswordListener onPasswordListener;

    public void setOnPasswordListener(OnPasswordListener onPasswordListener) {
        this.onPasswordListener = onPasswordListener;
    }


    public interface OnPasswordListener {

        void onPasswordChange(String text);

        void onPasswordComplete(String text);
    }

}
