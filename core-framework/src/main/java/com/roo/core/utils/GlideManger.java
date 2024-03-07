package com.roo.core.utils;

import android.content.Context;


import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.roo.core.R;

public class GlideManger {

    public static final RequestOptions OPTIONS_IMAGE_CIRCLE = RequestOptions
            .placeholderOf(R.drawable.ic_common_error_pic_circle)
            .error(R.drawable.ic_common_error_pic_circle)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.ALL);

    public static final RequestOptions OPTIONS_IMAGE_SQUARE = RequestOptions
            .placeholderOf(R.drawable.ic_place_holder_square)
            .error(R.drawable.ic_place_holder_square)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.ALL);

    public static RequestOptions optionsRoundCorner(Context context, int radiusPx,
                                                    @TransformRoundCorners.CornerType int type) {
        return RequestOptions.bitmapTransform(new TransformRoundCorners(context, radiusPx, type));
    }

    public static RequestOptions optionsRoundCornerCenterCrop(Context context, int radius,
                                                              @TransformRoundCornersCenterCrop.CornerType int type) {
        return RequestOptions.bitmapTransform(new TransformRoundCornersCenterCrop(context, radius, type));
    }

    public static RequestOptions optionsCircleBorder(Context context) {
        return optionsCircleBorder(context, 2, ContextCompat.getColor(context, android.R.color.white));
    }

    public static RequestOptions optionsCircle(Context context) {
        return optionsCircleBorder(context, 2, ContextCompat.getColor(context, android.R.color.transparent));
    }

    public static RequestOptions optionsCircleBorder(Context context, int borderWidth, @ColorInt int borderColor) {
        return RequestOptions.bitmapTransform(new TransformCircleBorder(context, borderWidth, borderColor));
    }

    public static RequestOptions optionsCircleBorderCenterCrop(Context context) {
        return optionsCircleBorderCenterCrop(context, 2, ContextCompat.getColor(context, android.R.color.white));
    }

    public static RequestOptions optionsCircleBorderCenterCrop(Context context, int borderWidth, @ColorInt int borderColor) {
        return RequestOptions.bitmapTransform(new TransformCircleBorderCenterCrop(context, borderWidth, borderColor));
    }
}
