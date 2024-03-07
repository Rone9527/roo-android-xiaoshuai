package com.roo.core.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.R;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        int padding = ArmsUtils.dip2px(context, 15);
        imageView.setPadding(padding, 0, padding, 0);

        RequestOptions options =
                GlideManger.optionsRoundCornerCenterCrop(context, ArmsUtils.dip2px(context, 8),
                        TransformRoundCornersCenterCrop.ALL);
        Glide.with(context).load(path)
                .apply(RequestOptions.errorOf(R.drawable.ic_common_error_pic_rec))
                .apply(options)
                .into(imageView);
    }
}