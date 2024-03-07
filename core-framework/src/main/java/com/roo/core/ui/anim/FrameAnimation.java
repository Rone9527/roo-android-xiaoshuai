package com.roo.core.ui.anim;

import android.widget.ImageView;

import com.roo.core.utils.handler.HandlerUtil;

/**
 * Created by Ansen on 2015/5/14 23:30.
 */
public class FrameAnimation {

    private ImageView mImageView;

    private int[] mFrameRess;

    /**
     * 每帧动画的播放间隔
     */
    private int mDuration;

    private int repeatCount = 1;
    private boolean isPlaying = false;
    private AnimationListener animationListener;

    /**
     * @param iv       播放动画的控件
     * @param frameRes 播放的图片数组
     * @param duration 播放的时间间隔
     * @param isRepeat 是否重复,如果设置为重复,则默认重复三次: this.repeatCount = isRepeat ? 3 : 1
     */
    public FrameAnimation(ImageView iv, int[] frameRes, int duration, boolean isRepeat) {
        this.mImageView = iv;
        this.mFrameRess = frameRes;
        this.mDuration = duration;
        this.repeatCount = isRepeat ? 3 : 1;
    }

    public void start() {
        if (!isPlaying) {
            play(0);
        }
    }

    private void play(int i) {
        if (i == 0 && !isPlaying) {
            if (animationListener != null) {
                animationListener.onAnimationStart();
            }
            isPlaying = true;
        }
        HandlerUtil.runOnUiThreadDelay(() -> {
            if (i > mFrameRess.length - 1) {
                if (--repeatCount > 0) {
                    play(0);
                } else {
                    if (animationListener != null) {
                        animationListener.onAnimationEnd();
                        isPlaying = false;
                    }
                }
            } else {
                mImageView.setBackgroundResource(mFrameRess[i]);
                play(i + 1);
            }
        }, mDuration);
    }

    public FrameAnimation setAnimationListener(AnimationListener animationListener) {
        this.animationListener = animationListener;
        return this;
    }

    public interface AnimationListener {
        void onAnimationEnd();

        void onAnimationStart();
    }

}
