package com.roo.core.ui.anim;

import android.animation.Animator;

/**
 * desc
 * Created by Mango
 * on 2017/9/1 0001.
 */

public class AnimatorFuture implements AnimFuture {

    private Animator animator;

    private AnimatorFuture(Animator animator) {
        this.animator = animator;
    }

    public static AnimatorFuture valueOf(Animator animator) {
        return new AnimatorFuture(animator);
    }

    @Override
    public void cancel() {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
    }
}
