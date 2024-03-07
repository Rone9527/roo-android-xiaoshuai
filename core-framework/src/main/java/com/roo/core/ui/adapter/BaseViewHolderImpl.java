
package com.roo.core.ui.adapter;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.utils.GlideManger;
import com.roo.core.utils.ImageOptions;

public class BaseViewHolderImpl extends com.chad.library.adapter.base.BaseViewHolder {

    public BaseViewHolderImpl(View view) {
        super(view);
    }

    public BaseViewHolderImpl setImageUrl(ImageView view, Object imgUrl, @NonNull ImageOptions options) {
        Glide.with(itemView.getContext()).load(imgUrl)
                .apply(RequestOptions.placeholderOf(options.placeHolderResId).error(options.errorResId))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL).centerCrop())
                .into(view);
        return this;
    }

    public BaseViewHolderImpl setImageUrl(ImageView view, Object imgUrl) {
        return setImageUrl(view, imgUrl, ImageOptions.defaultOptions());
    }

    public BaseViewHolderImpl setImageUrl(int viewId, Object imgUrl) {
        return setImageUrl(viewId, imgUrl, ImageOptions.defaultOptions());
    }

    public BaseViewHolderImpl setImageUrl(int viewId, Object imgUrl, @NonNull ImageOptions options) {
        ImageView view = getView(viewId);
        return setImageUrl(view, imgUrl, options);
    }

    public BaseViewHolderImpl setCircleImageUrl(ImageView view, Object imgUrl, @NonNull ImageOptions options) {
        Glide.with(itemView.getContext()).load(imgUrl)
                .apply(RequestOptions.placeholderOf(options.placeHolderResId)
                        .error(options.errorResId))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(GlideManger.optionsCircle(itemView.getContext()))
                .apply(RequestOptions.circleCropTransform()).into(view);
        return this;
    }

    public BaseViewHolderImpl setCircleImageUrl(ImageView view, Object imgUrl) {
        return setCircleImageUrl(view, imgUrl, ImageOptions.defaultOptionsCircle());
    }

    public BaseViewHolderImpl setCircleImageUrl(int viewId, Object imgUrl) {
        return setCircleImageUrl(viewId, imgUrl, ImageOptions.defaultOptionsCircle());
    }

    public BaseViewHolderImpl setCircleImageUrl(int viewId, Object imgUrl, @NonNull ImageOptions options) {
        ImageView view = getView(viewId);
        return setCircleImageUrl(view, imgUrl, options);
    }

    public BaseViewHolderImpl setRoundImageUrl(ImageView view, Object imgUrl, @NonNull ImageOptions options) {
        Glide.with(itemView.getContext()).load(imgUrl)
                .apply(RequestOptions.placeholderOf(options.placeHolderResId)
                        .error(options.errorResId))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(GlideManger.optionsRoundCornerCenterCrop(itemView.getContext(),
                        ArmsUtils.dip2px(itemView.getContext(), options.roundSize), options.roundType))
                .into(view);
        return this;
    }

    public BaseViewHolderImpl setRoundImageUrl(ImageView view, Object imgUrl) {
        return setRoundImageUrl(view, imgUrl, ImageOptions.defaultOptionsRound());
    }

    public BaseViewHolderImpl setRoundImageUrl(int viewId, Object imgUrl) {
        return setRoundImageUrl(viewId, imgUrl, ImageOptions.defaultOptionsRound());
    }

    public BaseViewHolderImpl setRoundImageUrl(int viewId, Object imgUrl, @NonNull ImageOptions options) {
        ImageView view = getView(viewId);
        return setRoundImageUrl(view, imgUrl, options);
    }

}