package com.roo.core.ui.anim;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;


/**
 * desc
 * Created by Mango
 * on 2017/8/2 0002.
 */

public class AnimUtils {

    private AnimUtils() {
    }

    public static void rotateMenu(View view, boolean front) {
        ObjectAnimator objectAnimator;
        if (front) {
            objectAnimator = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        } else {
            objectAnimator = ObjectAnimator.ofFloat(view, "rotation", 360f, 0f);
        }
        objectAnimator.setDuration(300);
        AnimManager.getInstance().executeAnim(view.getContext(), objectAnimator);
    }

    public static void getAlphaAnimEnter(int duration) {
        Animation enterAnimation = new AlphaAnimation(0f, 1f);
        enterAnimation.setDuration(duration);
        enterAnimation.setFillAfter(true);
    }

    public static void getAlphaAnimExit(int duration) {
        Animation exitAnimation = new AlphaAnimation(1f, 0f);
        exitAnimation.setDuration(duration);
        exitAnimation.setFillAfter(true);
    }
}
