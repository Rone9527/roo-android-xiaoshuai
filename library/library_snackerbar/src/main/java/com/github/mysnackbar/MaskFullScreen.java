package com.github.mysnackbar;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;

import java.util.HashMap;


/**
 * 蒙版
 */
public final class MaskFullScreen {

    private static final HashMap<String, MaskFullScreen> MASK_NIGHT = new HashMap<>();
    private static final int ANIMATION_DURATION = 500;
    private final ViewGroup mParent;
    private final Context mContext;
    private final MaskLayout mView;
    private boolean isMaskAdd = false;

    private MaskFullScreen(ViewGroup parent) {
        mParent = parent;
        mContext = parent.getContext();
        mView = (MaskLayout) LayoutInflater.from(mContext).inflate(R.layout.view_mask_layout, mParent, false);
    }

    @NonNull
    public static MaskFullScreen make(@NonNull Activity activity) {
        if (MASK_NIGHT.containsKey(activity.toString())) {
            return MASK_NIGHT.get(activity.toString());
        } else {
            MaskFullScreen mask = new MaskFullScreen(findSuitableParent(activity.findViewById(android.R.id.content).getRootView()));
            MASK_NIGHT.put(activity.toString(), mask);
            return mask;
        }
    }

    private static ViewGroup findSuitableParent(View view) {
        if (view != null) {
            return (ViewGroup) view;
        } else {
            throw new RuntimeException("view can not be null or must be the view root of activity !");
        }
    }

    public final void showView() {
        showView(false);
    }

    public final void showView(boolean showAnim) {
        if (!isMaskAdd) {
            isMaskAdd = true;
            if (showAnim) {
                if (ViewCompat.isLaidOut(mView)) {
                    mParent.addView(mView);
                    animateViewIn();
                } else {
                    mView.setOnLayoutChangeListener((view, left, top, right, bottom) -> {
                        animateViewIn();
                        mView.setOnLayoutChangeListener(null);
                    });
                    mParent.addView(mView);
                }
            } else {
                mParent.addView(mView);
            }
        }
    }

    private void animateViewIn() {
        Animation anim = AnimationUtil.getAnimationInFromBottomToTop(mContext);
        anim.setInterpolator(AnimationUtil.FAST_OUT_SLOW_IN_INTERPOLATOR);
        anim.setDuration(ANIMATION_DURATION);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mView.startAnimation(anim);
    }

    public final void hideView() {
        if (isMaskAdd) {
            Animation anim = AnimationUtil.getAnimationOutFromBottomToTop(mContext);
            anim.setInterpolator(AnimationUtil.FAST_OUT_SLOW_IN_INTERPOLATOR);
            anim.setDuration(ANIMATION_DURATION);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    onViewHidden();
                }

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            mView.startAnimation(anim);
        } else {
            onViewHidden();
        }
    }

    private void onViewHidden() {
        mParent.removeView(mView);
        isMaskAdd = false;
    }

}