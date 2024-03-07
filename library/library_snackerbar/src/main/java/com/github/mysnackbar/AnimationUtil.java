package com.github.mysnackbar;


import android.content.Context;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

public class AnimationUtil {
    public static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    public static final Interpolator FAST_OUT_SLOW_IN_INTERPOLATOR = new FastOutSlowInInterpolator();
    public static final Interpolator FAST_OUT_LINEAR_IN_INTERPOLATOR = new FastOutLinearInInterpolator();
    public static final Interpolator LINEAR_OUT_SLOW_IN_INTERPOLATOR = new LinearOutSlowInInterpolator();
    public static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();

    /**
     * Linear interpolation between {@code startValue} and {@code endValue} by {@code fraction}.
     */
    static float lerp(float startValue, float endValue, float fraction) {
        return startValue + (fraction * (endValue - startValue));
    }

    static int lerp(int startValue, int endValue, float fraction) {
        return startValue + Math.round(fraction * (endValue - startValue));
    }

    public static Animation getAnimationInFromTopToBottom(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.top_in);
    }

    public static Animation getAnimationInFromBottomToTop(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.design_snackbar_in);
    }

    public static Animation getAnimationOutFromTopToBottom(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.top_out);
    }

    public static Animation getAnimationOutFromBottomToTop(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.design_snackbar_out);
    }

}