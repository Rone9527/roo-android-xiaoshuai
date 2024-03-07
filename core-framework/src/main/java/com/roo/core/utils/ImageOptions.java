package com.roo.core.utils;

import androidx.annotation.DrawableRes;
import android.widget.ImageView;

import com.roo.core.R;


public class ImageOptions {

    private static final int RES_PLACE_HOLDER = R.drawable.ic_place_holder_circle;
    private static final int RES_ERROR = R.drawable.ic_common_error_pic_circle;
    public int placeHolderResId = RES_PLACE_HOLDER;        //加载中的资源id
    public int errorResId = RES_ERROR;      //加载失败的资源id
    public ImageView.ScaleType scaleType = null;
    public int roundSize;
    public int roundType;

    public ImageOptions() {
    }

    public ImageOptions(@DrawableRes int placeHolderResId, @DrawableRes int errorResId) {
        this.placeHolderResId = placeHolderResId;
        this.errorResId = errorResId;
        this.roundSize = 4;
        this.roundType = TransformRoundCornersCenterCrop.ALL;
    }

    public static ImageOptions defaultOptions() {
        return new ImageOptions(R.drawable.ic_place_holder_circle, R.drawable.ic_common_error_pic_circle);
    }

    public static ImageOptions defaultError(@DrawableRes int errorResId) {
        return new ImageOptions(R.drawable.ic_place_holder_circle, errorResId);
    }

    public static ImageOptions defaultPlaceHolder(@DrawableRes int placeHolderResId) {
        return new ImageOptions(placeHolderResId, R.drawable.ic_common_error_pic_circle);
    }

    public static ImageOptions defaultOptionsNone() {
        return new ImageOptions(RES_PLACE_HOLDER, RES_PLACE_HOLDER);
    }

    public static ImageOptions defaultOptionsCircle() {
        return new ImageOptions(R.drawable.ic_common_error_pic_circle, R.drawable.ic_common_error_pic_circle);
    }

    public static ImageOptions defaultOptionsRound() {
        return new ImageOptions(R.drawable.ic_place_holder_square, R.drawable.ic_common_error_pic_circle);
    }

    public static ImageOptions defaultOptionsSquare() {
        return new ImageOptions(R.drawable.ic_place_holder_square, R.drawable.ic_common_error_pic_circle);
    }

    public ImageOptions scaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        return this;
    }


    public ImageOptions placeHolderResId(int loadingResId) {
        this.placeHolderResId = loadingResId;
        return this;
    }


    public ImageOptions errorResId(int errorResId) {
        this.errorResId = errorResId;
        return this;
    }

    public ImageOptions roundSize(int roundSizedp) {
        this.roundSize = roundSizedp;
        return this;
    }

    public ImageOptions roundType(int roundType) {
        this.roundType = roundType;
        return this;
    }
}