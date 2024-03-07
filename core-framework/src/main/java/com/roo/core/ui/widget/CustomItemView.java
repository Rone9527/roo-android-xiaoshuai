package com.roo.core.ui.widget;


import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.roo.core.R;

import me.majiajie.pagerbottomtabstrip.internal.RoundMessageView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;

public class CustomItemView extends BaseTabItem {

    private ImageView mIcon;
    private LottieAnimationView animationView;
    private final TextView mTitle;
    private final RoundMessageView mMessages;

    private Drawable mDefaultDrawable;
    private Drawable mCheckedDrawable;

    private int mDefaultTextColor = 0x56000000;
    private int mCheckedTextColor = 0x56000000;

    private boolean mChecked;

    public CustomItemView(Context context) {
        this(context, null);
    }

    public CustomItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.item_custom, this, true);

        mIcon = findViewById(R.id.icon);
        animationView = findViewById(R.id.animationView);
        mTitle = findViewById(R.id.title);
        mMessages = findViewById(R.id.messages);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return CustomItemView.class.getName();
    }

    /**
     * 方便初始化的方法
     *
     * @param drawableRes        默认状态的图标
     * @param checkedDrawableRes 选中状态的图标
     * @param title              标题
     * @param json
     */
    public void initialize(@DrawableRes int drawableRes, @DrawableRes int checkedDrawableRes, String title, String json) {
        mDefaultDrawable = ContextCompat.getDrawable(getContext(), drawableRes);
        mCheckedDrawable = ContextCompat.getDrawable(getContext(), checkedDrawableRes);
        animationView.setAnimation(json);
        animationView.addAnimatorListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animationView.setVisibility(GONE);
                mIcon.setVisibility(VISIBLE);
                mIcon.setImageDrawable(mCheckedDrawable);
            }
        });
        mTitle.setText(title);
    }

    @Override
    public void setChecked(boolean checked) {
        if (checked) {
            animationView.playAnimation();
            mIcon.setVisibility(GONE);
            animationView.setVisibility(VISIBLE);
            mTitle.setTextColor(mCheckedTextColor);
        } else {
            mIcon.setVisibility(VISIBLE);
            mIcon.setImageDrawable(mDefaultDrawable);
            animationView.setVisibility(GONE);
            mTitle.setTextColor(mDefaultTextColor);
            animationView.cancelAnimation();
        }
        mChecked = checked;
    }

    @Override
    public void setMessageNumber(int number) {
        mMessages.setMessageNumber(number);
    }

    @Override
    public void setHasMessage(boolean hasMessage) {
        mMessages.setHasMessage(hasMessage);
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setDefaultDrawable(Drawable drawable) {
        mDefaultDrawable = drawable;
        if (!mChecked) {
            mIcon.setImageDrawable(drawable);
        }
    }

    @Override
    public void setSelectedDrawable(Drawable drawable) {
        mCheckedDrawable = drawable;
        if (mChecked) {
            mIcon.setImageDrawable(drawable);
        }
    }

    @Override
    public String getTitle() {
        return mTitle.getText().toString();
    }

    public void setTextDefaultColor(@ColorInt int color) {
        mDefaultTextColor = color;
    }

    public void setTextCheckedColor(@ColorInt int color) {
        mCheckedTextColor = color;
    }
}
