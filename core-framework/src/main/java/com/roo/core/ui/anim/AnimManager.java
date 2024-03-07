package com.roo.core.ui.anim;

import android.animation.Animator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * desc 动画管理
 * Created by XYS
 * on 2017/9/1 0001.
 */

public class AnimManager {

    private Map<Object, Set<AnimFuture>> animHolderMap = new HashMap<>();

    public static AnimManager getInstance() {
        return Singleton.INSTANCE;
    }

    /**
     * 动画执行
     */
    public void executeAnim(Object tag, Animator animator) {
        addAnimHolderToTag(tag, animator);
        animator.start();
    }

    /**
     * 添加动画
     *
     * @param tag
     * @param animator
     */
    private void addAnimHolderToTag(Object tag, Animator animator) {
        Set<AnimFuture> animFutures;
        if (animHolderMap.get(tag) == null) {
            animFutures = new HashSet<>();
            animHolderMap.put(tag, animFutures);
        } else {
            animFutures = animHolderMap.get(tag);
        }
        animFutures.add(AnimatorFuture.valueOf(animator));
    }

    /**
     * 取消动画
     *
     * @param tag
     */
    public void cancelAnimByTag(Object tag) {
        Set<AnimFuture> animFutures = animHolderMap.get(tag);
        if (animFutures == null || animFutures.isEmpty()) {
            return;
        }
        for (AnimFuture animFuture : animFutures) {
            animFuture.cancel();
        }
        animHolderMap.remove(tag);
    }

    private static class Singleton {
        private static final AnimManager INSTANCE = new AnimManager();
    }
}
