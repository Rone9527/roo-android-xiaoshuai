package com.roo.core.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

public class BottomBarBehavior extends CoordinatorLayout.Behavior<View> {

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    private float viewY;//控件距离coordinatorLayout底部距离
    private boolean isAnimate;//动画是否在进行

    public BottomBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 有嵌套滑动到来了，问下该Behavior是否接受嵌套滑动
     *
     * @param coordinatorLayout 当前的CoordinatorLayout
     * @param child             该Behavior对应的View
     * @param directTargetChild 我的理解是在CoordinateLayout下作为父View,
     *                          而该View的子类是Tager的那个View,也就是Target的父View),
     *                          因为我测试用ViewPager包裹了RecycleView后该参数返回Viewpager,
     *                          如果没有包裹参数返回的是RecycleView
     * @param target            具体嵌套滑动的那个子类
     * @param nestedScrollAxes  支持嵌套滚动轴。水平方向，垂直方向，或者不指定
     * @param type              导致此滚动事件的输入类型
     * @return 是否接受该嵌套滑动
     */

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int nestedScrollAxes, @ViewCompat.NestedScrollType int type) {
        if (child.getVisibility() == View.VISIBLE && viewY == 0) {
            //获取控件距离父布局（coordinatorLayout）底部距离
            viewY = coordinatorLayout.getHeight() - child.getY();
        }

        //ViewCompat是一个兼容类,在android5.0之前的API为了实现新的效果
        //避免出错使用ViewCompat.xxxx方法可以解决出现低版本错误的问题
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;//判断是否竖直滚动
    }


    /**
     * 在嵌套滑动的子View未滑动之前准备滑动的情况
     *
     * @param coordinatorLayout 此行为与关联的视图的父级CoordinatorLayout
     * @param child             该Behavior对应的View
     * @param target            具体嵌套滑动的那个子类
     * @param dx                水平方向嵌套滑动的子View想要变化的距离
     * @param dy                垂直方向嵌套滑动的子View想要变化的距离
     * @param consumed          这个参数要我们在实现这个函数的时候指定，回头告诉子View当前父View消耗的距离 consumed[0] 水平消耗的距离，consumed[1] 垂直消耗的距离 好让子view做出相应的调整
     * @param type              导致此滚动事件的输入类型
     */
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        //dy大于0是向上滚动 小于0是向下滚动，判断的时候尽量不要判断是否大于等于或者小于等于0，否则可能会影响点击事件
        //System.out.println(dy);
        if (type == ViewCompat.TYPE_TOUCH) {
            if (dy > 20 && !isAnimate && child.getVisibility() == View.VISIBLE) {
                hide(child);
            } else if (dy < 20 && !isAnimate && child.getVisibility() == View.INVISIBLE) {
                show(child);
            }
        }
    }

    //隐藏时的动画
    private void hide(View view) {
        ViewPropertyAnimator animator = view.animate().translationY(viewY).setInterpolator(INTERPOLATOR).setDuration(500);

        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {
                isAnimate = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.INVISIBLE);
                isAnimate = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                show(view);
            }
        });
        animator.start();
    }

    //显示时的动画
    private void show(final View view) {
        ViewPropertyAnimator animator = view.animate().translationY(0).setInterpolator(INTERPOLATOR).setDuration(500);
        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {
                view.setVisibility(View.VISIBLE);
                isAnimate = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isAnimate = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                hide(view);
            }
        });
        animator.start();
    }

}
