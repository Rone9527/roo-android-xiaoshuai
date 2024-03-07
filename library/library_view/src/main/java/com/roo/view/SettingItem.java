package com.roo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.aries.ui.view.radius.RadiusTextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class SettingItem extends RelativeLayout {
    /*根布局*/
    private LinearLayout mRootLayout;
    /*左侧文本控件*/
    private TextView mTvLeftText;
    /*右侧文本控件*/
    private TextView mTvRightText;
    /*右侧文本控件的标签*/
    private RadiusTextView mTvRightTag;
    /*上面分割线*/
    private View mTopline;
    /*下面分割线*/
    private View mUnderLine;
    /*左侧图标控件*/
    private ImageView mIvLeftIcon;
    /*右侧图标控件,默认展示图标*/
    private ImageView mIvRightIcon;
    /*右侧图标控件,选择样式图标*/
    private AppCompatCheckBox mRightIconCheck;
    /*右侧图标控件,开关样式图标*/
    private SwitchButton mRightIconSwitch;
    /*右侧图标展示风格*/
    private @RightStyle
    int mRightStyle;
    private OnItemClick onItemClick;
    private int mLeftDefValue = 0xFF616161;
    private int mRightDefValue = 0XFF9E9E9E;

    public SettingItem(Context context) {
        this(context, null);
    }

    public SettingItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initStyle(context, attrs);
        //获取到右侧展示风格，进行样式切换
        setRightStyle(mRightStyle);
    }
    //===================对外API===================START

    public TextView getLeftText() {
        return mTvLeftText;
    }

    public TextView getRightText() {
        return mTvRightText;
    }

    public RadiusTextView getRightTag() {
        return mTvRightTag;
    }

    public ImageView getLeftIcon() {
        return mIvLeftIcon;
    }

    public ImageView getRightIcon() {
        return mIvRightIcon;
    }

    public SettingItem setOnItemClickListener(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
        setOnClickListener(v -> {
            if (this.onItemClick != null) {
                this.onItemClick.onItemClick(this);
            }
        });
        return this;
    }

    @Override
    public void setOnClickListener(View.OnClickListener onClick) {
        mRootLayout.setOnClickListener(onClick);
    }

    public SettingItem setRightCheckBoxDewable(@DrawableRes int drawableResId) {
        mRightIconCheck.setButtonDrawable(drawableResId);
        return this;
    }

    public AppCompatCheckBox getCheckBox() {
        if (mRightStyle == RightStyle.mRightIconCheck) {
            return mRightIconCheck;
        }
        return null;
    }

    public SwitchButton getRightSwitch() {
        return mRightIconSwitch;
    }

    public SettingItem setRightSwitch(boolean checked) {
        mRightIconSwitch.setChecked(checked);
        return this;
    }

    //===================对外API===================END

    /**
     * 初始化自定义属性
     */
    private void initStyle(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingItem);

        //Item Background
        int itemBackground = a.getResourceId(R.styleable.SettingItem_itemBackground, android.R.color.white);
        if (itemBackground == android.R.color.transparent) {
            mLeftDefValue = 0xFFD9D9D9;
            mRightDefValue = 0XFF9E9E9E;
        }
        mRootLayout.setBackgroundResource(itemBackground);

        //LeftText
        mTvLeftText.setText(a.getString(R.styleable.SettingItem_leftText));
        mTvLeftText.setVisibility(TextUtils.isEmpty(mTvLeftText.getText().toString()) ? View.GONE : View.VISIBLE);
        int mTextSize = a.getDimensionPixelSize(R.styleable.SettingItem_textSizeLeft, dip2px(getContext(), 16));
        mTvLeftText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        int mTextColor = a.getColor(R.styleable.SettingItem_textColorLeft, mLeftDefValue);
        mTvLeftText.setTextColor(mTextColor);

        //RightText
        mTvRightText.setText(a.getString(R.styleable.SettingItem_rightText));
        mTextSize = a.getDimensionPixelSize(R.styleable.SettingItem_textSizeRight, dip2px(getContext(), 14));
        mTvRightText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);

        mTextColor = a.getColor(R.styleable.SettingItem_textColorRight, mRightDefValue);
        mTvRightText.setTextColor(mTextColor);

        //LeftIcon
        int resourceId = a.getResourceId(R.styleable.SettingItem_leftIcon, 0);
        if (resourceId == 0) {
            mIvLeftIcon.setVisibility(GONE);
        } else {
            mIvLeftIcon.setVisibility(VISIBLE);
            mIvLeftIcon.setImageResource(resourceId);
        }

        //RightIcon
        int rightResourceId = a.getResourceId(R.styleable.SettingItem_rightIcon, 0);
        if (rightResourceId == 0) {
            mIvRightIcon.setVisibility(GONE);
        } else {
            mIvRightIcon.setVisibility(VISIBLE);
            mIvRightIcon.setImageResource(rightResourceId);
        }

        mRightStyle = a.getInt(R.styleable.SettingItem_rightStyle, RightStyle.mRightIconShow);

        //UnderLine
        if (a.getBoolean(R.styleable.SettingItem_showUnderLine, false)) {
            mUnderLine.setVisibility(View.VISIBLE);
        } else {
            mUnderLine.setVisibility(View.INVISIBLE);
        }
        switchLineStyle(a.getInt(R.styleable.SettingItem_bottomLineStyle, 0), mUnderLine);

        //Topline
        if (a.getBoolean(R.styleable.SettingItem_showTopLine, false)) {
            mTopline.setVisibility(View.VISIBLE);
        } else {
            mTopline.setVisibility(View.INVISIBLE);
        }
        switchLineStyle(a.getInt(R.styleable.SettingItem_topLineStyle, 0), mTopline);

        //selectableItemBackground
        if (a.getBoolean(R.styleable.SettingItem_showClickFeedback, true)) {
            TypedValue typedValue = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
            int[] attribute = new int[]{android.R.attr.selectableItemBackground};
            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(typedValue.resourceId, attribute);
            setBackground(typedArray.getDrawable(0));
        }
        a.recycle();
    }

    /**
     * 根据设定切换右侧展示样式，同时更新点击事件处理方式
     *
     * @param mRightStyle
     */
    private void setRightStyle(@RightStyle int mRightStyle) {
        switch (mRightStyle) {
            case RightStyle.mRightIconShow: {
                mIvRightIcon.setVisibility(View.VISIBLE);
                mRightIconCheck.setVisibility(View.GONE);
                mRightIconSwitch.setVisibility(View.GONE);
                LayoutParams layoutParams = (LayoutParams) mTvRightText.getLayoutParams();
                layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_END);
                layoutParams.addRule(RelativeLayout.START_OF, R.id.iv_righticon);
                layoutParams.setMarginEnd(dip2px(getContext(), 6));
                mTvRightText.setLayoutParams(layoutParams);
            }
            break;
            case RightStyle.mRightIconHide: {
                mIvRightIcon.setVisibility(View.GONE);
                mRightIconCheck.setVisibility(View.GONE);
                mRightIconSwitch.setVisibility(View.GONE);
            }
            break;
            case RightStyle.mRightIconCheck: {
                //显示选择框样式
                mIvRightIcon.setVisibility(View.GONE);
                mRightIconCheck.setVisibility(View.VISIBLE);
                mRightIconSwitch.setVisibility(View.GONE);

                LayoutParams layoutParams = (LayoutParams) mTvRightText.getLayoutParams();
                layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_END);
                layoutParams.addRule(RelativeLayout.START_OF, R.id.rightcheck);
                layoutParams.setMarginEnd(dip2px(getContext(), 6));
                mTvRightText.setLayoutParams(layoutParams);
            }
            break;
            case RightStyle.mRightIconSwitch: {
                //显示开关切换样式
                mIvRightIcon.setVisibility(View.GONE);
                mRightIconCheck.setVisibility(View.GONE);
                mRightIconSwitch.setVisibility(View.VISIBLE);

                LayoutParams layoutParams = (LayoutParams) mTvRightText.getLayoutParams();
                layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_END);
                layoutParams.addRule(RelativeLayout.START_OF, R.id.rightswitch);
                layoutParams.setMarginEnd(dip2px(getContext(), 6));
                mTvRightText.setLayoutParams(layoutParams);
            }
            break;
            default:
                break;
        }
        this.mRightStyle = mRightStyle;
    }

    /**
     * @param mLineStyle
     */
    private void switchLineStyle(int mLineStyle, View view) {
        LinearLayout.LayoutParams rlParam = (LinearLayout.LayoutParams) view.getLayoutParams();
        switch (mLineStyle) {
            case 0://center
                rlParam.setMargins(dip2px(getContext(), 16), 0, dip2px(getContext(), 16), 0);
                break;
            case 1://right
                rlParam.setMargins(dip2px(getContext(), 16), 0, 0, 0);
                break;
            case 2://left
                rlParam.setMargins(0, 0, dip2px(getContext(), 16), 0);
                break;
            case 3://full
                rlParam.setMargins(0, 0, 0, 0);
                break;
            default:
                rlParam.setMargins(dip2px(getContext(), 16), 0, dip2px(getContext(), 16), 0);
                break;
        }
        view.setLayoutParams(rlParam);
    }

    private void initView(Context context) {
        View inflate = View.inflate(context, R.layout.item_setting, this);
        mRootLayout = inflate.findViewById(R.id.layoutRoot);
        mTopline = inflate.findViewById(R.id.topline);
        mUnderLine = inflate.findViewById(R.id.underline);
        mTvLeftText = inflate.findViewById(R.id.tv_lefttext);
        mTvRightText = inflate.findViewById(R.id.tv_righttext);
        mTvRightTag = inflate.findViewById(R.id.tv_righttag);
        mIvLeftIcon = inflate.findViewById(R.id.iv_lefticon);
        mIvRightIcon = inflate.findViewById(R.id.iv_righticon);
        mRightIconCheck = inflate.findViewById(R.id.rightcheck);
        mRightIconSwitch = inflate.findViewById(R.id.rightswitch);
    }

    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @IntDef({RightStyle.mRightIconShow,
            RightStyle.mRightIconHide,
            RightStyle.mRightIconCheck,
            RightStyle.mRightIconSwitch})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RightStyle {
        int mRightIconShow = 0;
        int mRightIconHide = 1;
        int mRightIconCheck = 2;
        int mRightIconSwitch = 3;
    }

    public interface OnItemClick {
        void onItemClick(View view);
    }
}