package com.github.mysnackbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * TSnackbar provides lightweight feedback about an operation. They show a brief message at the
 * top of the screen on mobile. TSnackbar appear above all other
 * elements on screen and only one can be displayed at a time.
 * <p>
 * They automatically disappear after a timeout or after user interaction elsewhere on the screen,
 * particularly after interactions that summon a new surface or activity. Snackbars can be swiped
 * off screen.
 * <p>
 * Snackbars can contain an action which is set via
 * {@link #setAction(CharSequence, View.OnClickListener)}.
 * <p>
 * To be notified when a snackbar has been shown or dismissed, you can provide a {@link Callback}
 * via {@link #setCallback(Callback)}.</p>
 */
public final class TSnackbar {

    /**
     * Show the TSnackbar from top to down.
     */
    public static final int APPEAR_FROM_TOP_TO_DOWN = 0;
    /**
     * Show the TSnackbar from top to down.
     */
    public static final int APPEAR_FROM_BOTTOM_TO_TOP = 1;
    /**
     * Show the TSnackbar indefinitely. This means that the TSnackbar will be displayed from the time
     * that is {@link #show() shown} until either it is dismissed, or another TSnackbar is shown.
     *
     * @see #setDuration
     */
    public static final int LENGTH_INDEFINITE = -2;
    /**
     * Show the TSnackbar for a short period of time.
     *
     * @see #setDuration
     */
    public static final int LENGTH_SHORT = -1;
    /**
     * Show the TSnackbar for a long period of time.
     *
     * @see #setDuration
     */
    public static final int LENGTH_LONG = 0;
    private static final int ANIMATION_DURATION = 550;
    private static final int ANIMATION_FADE_DURATION = 180;
    private static final Handler sHandler;
    private static final int MSG_SHOW = 0;
    private static final int MSG_DISMISS = 1;

    static {
        sHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case MSG_SHOW:
                        ((TSnackbar) message.obj).showView();
                        return true;
                    case MSG_DISMISS:
                        ((TSnackbar) message.obj).hideView(message.arg1);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private final ViewGroup mParent;
    private final Context mContext;
    private final SnackbarLayout mView;
    private final AccessibilityManager mAccessibilityManager;
    private final SnackbarManager.Callback mManagerCallback = new SnackbarManager.Callback() {
        @Override
        public void show() {
            sHandler.sendMessage(sHandler.obtainMessage(MSG_SHOW, TSnackbar.this));
        }

        @Override
        public void dismiss(int event) {
            sHandler.sendMessage(sHandler.obtainMessage(MSG_DISMISS, event, 0, TSnackbar.this));
        }
    };
    public boolean isTranslucentStatusbar = true;//是否是沉浸式状态栏
    private
    @OverSnackAppearDirection
    int appearDirection = APPEAR_FROM_TOP_TO_DOWN;
    private int mDuration;
    private Callback mCallback;

    private TSnackbar(ViewGroup parent) {
        mParent = parent;
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (appearDirection == APPEAR_FROM_BOTTOM_TO_TOP) {
            mView = (SnackbarLayout) inflater.inflate(R.layout.view_bsnackbar_layout, mParent, false);
        } else {
            mView = (SnackbarLayout) inflater.inflate(R.layout.view_tsnackbar_layout, mParent, false);
        }
        mView.setOnGestureDetectorListener(new SnackbarLayout.OnGestureDetectorListener() {
            @Override
            public boolean onFlingUp() {
                sHandler.sendMessage(sHandler.obtainMessage(MSG_DISMISS, TSnackbar.this));
                return false;
            }

            @Override
            public boolean onFlingDown() {
                sHandler.sendMessage(sHandler.obtainMessage(MSG_DISMISS, TSnackbar.this));
                return false;
            }
        });
        mAccessibilityManager = (AccessibilityManager)
                mContext.getSystemService(Context.ACCESSIBILITY_SERVICE);

    }

    private TSnackbar(ViewGroup parent, @OverSnackAppearDirection int appearDirection) {
        this.appearDirection = appearDirection;
        mParent = parent;
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (appearDirection == APPEAR_FROM_BOTTOM_TO_TOP) {
            mView = (SnackbarLayout) inflater.inflate(R.layout.view_bsnackbar_layout, mParent, false);
        } else {
            mView = (SnackbarLayout) inflater.inflate(R.layout.view_tsnackbar_layout, mParent, false);
        }
        mView.setOnGestureDetectorListener(new SnackbarLayout.OnGestureDetectorListener() {
            @Override
            public boolean onFlingUp() {
                sHandler.sendMessage(sHandler.obtainMessage(MSG_DISMISS, TSnackbar.this));
                return false;
            }

            @Override
            public boolean onFlingDown() {
                return false;
            }
        });
        mAccessibilityManager = (AccessibilityManager)
                mContext.getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (appearDirection == APPEAR_FROM_TOP_TO_DOWN) {
            setMinHeight(0, 0);
        }
    }

    @NonNull
    public static TSnackbar make(@NonNull View view, @StringRes int resId) {
        return make(view, view.getResources().getText(resId), LENGTH_SHORT);
    }

    @NonNull
    public static TSnackbar make(@NonNull View view, @StringRes int resId, @Duration int duration) {
        return make(view, view.getResources().getText(resId), duration);
    }

    @NonNull
    public static TSnackbar make(@NonNull View view, @NonNull CharSequence text, @Duration int duration) {
        return make(view, text, duration, APPEAR_FROM_TOP_TO_DOWN);
    }

    @NonNull
    public static TSnackbar make(@NonNull Activity activity, @NonNull CharSequence text) {
        return make(activity.findViewById(android.R.id.content).getRootView(), text, LENGTH_SHORT, APPEAR_FROM_TOP_TO_DOWN);
    }

    @NonNull
    public static TSnackbar make(@NonNull Activity activity, @NonNull CharSequence text, @Duration int duration, @OverSnackAppearDirection int appearDirection) {
        return make(activity.findViewById(android.R.id.content).getRootView(), text, duration, appearDirection);
    }

    /**
     * Make a TSnackbar to display a message.
     * <p/>
     * <p>TSnackbar will try and find a parent view to hold TSnackbar's view from the value given
     * to {@code view}. TSnackbar will walk up the view tree trying to find a suitable parent,
     * which is defined as a {@link CoordinatorLayout} or the window decor's content view,
     * whichever comes first.
     * <p/>
     * <p>Having a {@link CoordinatorLayout} in your view hierarchy allows TSnackbar to enable
     * certain features, such as swipe-to-dismiss and automatically moving of widgets like
     * {@link FloatingActionButton}.
     *
     * @param view     The view to find a parent from.
     * @param text     The string resource to use
     * @param duration How long to display the message.  Either {@link #LENGTH_SHORT} or {@link
     *                 #LENGTH_LONG}
     */
    @NonNull
    public static TSnackbar make(@NonNull View view, @NonNull CharSequence text, @Duration int duration, @OverSnackAppearDirection int appearDirection) {
        return new TSnackbar(findSuitableParent(view), appearDirection)
                .setText(text)
                .setDuration(duration);
    }

    private static ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;
        do {
            if (view instanceof CoordinatorLayout) {
                // We've found a CoordinatorLayout, use it
                return (ViewGroup) view;
            } else if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    // If we've hit the decor content view, then we didn't find a CoL in the
                    // hierarchy, so use it.
                    return (ViewGroup) view;
                } else {
                    // It's not the content view but we'll use it as our fallback
                    fallback = (ViewGroup) view;
                }
            }

            if (view != null) {
                // Else, we will loop and crawl up the view hierarchy and try to find a parent
                final ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);

        // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
        return fallback;
        //return (ViewGroup) view;
    }

    /**
     * @param stateBarHeight
     * @param actionBarHeight
     * @return
     */
    public TSnackbar setMinHeight(int stateBarHeight, int actionBarHeight) {
        if (appearDirection == APPEAR_FROM_TOP_TO_DOWN) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//SDK >= 4.4
                if (stateBarHeight > 0 || actionBarHeight > 0) {
                    mView.setPadding(0, stateBarHeight, 0, 0);
                    mView.setMinimumHeight(stateBarHeight + actionBarHeight);
                } else {
                    mView.setPadding(0, ScreenUtil.getStatusHeight(mContext), 0, 0);
                    mView.setMinimumHeight(ScreenUtil.getActionBarHeight(mContext) + ScreenUtil.getStatusHeight(mContext));
                }
            } else {
                if (stateBarHeight > 0 || actionBarHeight > 0) {
                    mView.setMinimumHeight(actionBarHeight);
                    ScreenUtil.setMargins(mView, 0, stateBarHeight, 0, 0);
                } else {
                    mView.setMinimumHeight(ScreenUtil.getActionBarHeight(mContext));
                    ScreenUtil.setMargins(mView, 0, ScreenUtil.getStatusHeight(mContext), 0, 0);
                }
            }
        }
        return this;
    }

    /**
     * @param resource_id
     * @return
     */
    public TSnackbar addIcon(int resource_id) {
        final TextView tv = mView.getMessageView();
        tv.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(resource_id), null, null, null);
        return this;
    }

    /**
     * @param isTranslucentStatusbar
     * @return
     */
    public TSnackbar isTranslucentStatusbar(boolean isTranslucentStatusbar) {
        this.isTranslucentStatusbar = isTranslucentStatusbar;
        return this;
    }

    /**
     * @param resource_id image id
     * @param width       image width
     * @param height      image height
     * @return
     */
    public TSnackbar addIcon(int resource_id, int width, int height) {
        final TextView tv = mView.getMessageView();
        if (width > 0 || height > 0) {
            tv.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(mContext.getResources(), Bitmap.createScaledBitmap(((BitmapDrawable) (mContext.getResources().getDrawable(resource_id))).getBitmap(), width, height, true)), null, null, null);
        } else {
            addIcon(resource_id);
        }
        return this;
    }

    /**
     * show loading progressBar
     *
     * @param resource_id image id
     * @param left        show textview left
     * @param right       show textview right
     * @return TSnackbar
     */
    public TSnackbar addIconProgressLoading(int resource_id, boolean left, boolean right) {
        Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.rotate);
        if (resource_id > 0) {
            drawable = ContextCompat.getDrawable(mContext, resource_id);
        }
        addIconProgressLoading(drawable, left, right);
        return this;
    }

    /**
     * @param drawable
     * @param left
     * @param right
     * @return
     */
    public TSnackbar addIconProgressLoading(Drawable drawable, boolean left, boolean right) {
        final ObjectAnimator animator = ObjectAnimator.ofInt(drawable, "level", 0, 10000);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        mView.setBackgroundColor(ContextCompat.getColor(mContext, Prompt.SUCCESS.getBackgroundColor()));
        if (left) {
            mView.getMessageView().setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        }
        if (right) {
            mView.getMessageView().setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        }
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mCallback != null) {
                    mCallback.onShown(TSnackbar.this);
                }
                SnackbarManager.getInstance().onShown(mManagerCallback);
            }
        });
        animator.start();
        return this;
    }

    /**
     * default style {ERROR , WARNING , SUCCESS}
     *
     * @param prompt
     * @return
     */
    public TSnackbar setPromptThemBackground(Prompt prompt) {
        if (prompt == Prompt.SUCCESS) {
            setBackgroundColor(ContextCompat.getColor(mContext, Prompt.SUCCESS.getBackgroundColor()));
            addIcon(Prompt.SUCCESS.getResIcon(), 0, 0);
        } else if (prompt == Prompt.ERROR) {
            setBackgroundColor(ContextCompat.getColor(mContext, Prompt.ERROR.getBackgroundColor()));
            addIcon(Prompt.ERROR.getResIcon(), 0, 0);
        } else if (prompt == Prompt.WARNING) {
            setBackgroundColor(ContextCompat.getColor(mContext, Prompt.WARNING.getBackgroundColor()));
            addIcon(Prompt.WARNING.getResIcon(), 0, 0);
        } else if (prompt == Prompt.FAILED) {
            setBackgroundColor(ContextCompat.getColor(mContext, Prompt.FAILED.getBackgroundColor()));
            addIcon(Prompt.FAILED.getResIcon(), 0, 0);
        }
        return this;
    }

    public TSnackbar setBackgroundColor(int colorId) {
        mView.setBackgroundColor(colorId);
        return this;
    }

    @NonNull
    public TSnackbar setAction(@StringRes int resId, View.OnClickListener listener) {
        return setAction(mContext.getText(resId), listener);
    }

    @NonNull
    public TSnackbar setAction(CharSequence text, final View.OnClickListener listener) {
        final TextView tv = mView.getActionView();
        if (TextUtils.isEmpty(text) || listener == null) {
            tv.setVisibility(View.GONE);
            tv.setOnClickListener(null);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view);

                    dispatchDismiss(Callback.DISMISS_EVENT_ACTION);
                }
            });
        }
        return this;
    }

    /**
     * Sets the text color of the action specified in
     */
    @NonNull
    public TSnackbar setActionTextColor(ColorStateList colors) {
        final Button btn = mView.getActionView();
        btn.setTextColor(colors);
        return this;
    }

    /**
     * Sets the text color of the action specified in
     */
    @NonNull
    public TSnackbar setActionTextSize(int size) {
        final Button btn = mView.getActionView();
        btn.setTextSize(size);
        return this;
    }

    /**
     * Sets the text color of the action specified in
     */
    @NonNull
    public TSnackbar setMessageTextSize(int size) {
        final TextView tv = mView.getMessageView();
        tv.setTextSize(size);
        return this;
    }

    /**
     * Sets the text color of the action specified in
     */
    @NonNull
    public TSnackbar setActionTextColor(@ColorInt int color) {
        final Button btn = mView.getActionView();
        btn.setTextColor(color);
        return this;
    }

    /**
     * Sets the text color of the action specified in
     */
    @NonNull
    public TSnackbar setTextColor(@ColorInt int color) {
        setActionTextColor(color);
        setMessageTextColor(color);
        return this;
    }

    /**
     * Sets the text color of the action specified in
     */
    @NonNull
    public TSnackbar setColor(ColorStateList colors) {
        setActionTextColor(colors);
        setMessageTextColor(colors);
        return this;
    }

    /**
     * Sets the text color of the action specified in
     */
    @NonNull
    public TSnackbar setMessageTextColor(@ColorInt int color) {
        final TextView tv = mView.getMessageView();
        tv.setTextColor(color);
        return this;
    }

    /**
     * Sets the text color of the action specified in
     */
    @NonNull
    public TSnackbar setMessageTextColor(ColorStateList colors) {
        final TextView tv = mView.getMessageView();
        tv.setTextColor(colors);
        return this;
    }

    /**
     * Update the text in this {@link TSnackbar}.
     *
     * @param message The new text for the Toast.
     */
    @NonNull
    public TSnackbar setText(@NonNull CharSequence message) {
        final TextView tv = mView.getMessageView();
        tv.setText(message);
        return this;
    }

    /**
     * Update the text in this {@link TSnackbar}.
     *
     * @param resId The new text for the Toast.
     */
    @NonNull
    public TSnackbar setText(@StringRes int resId) {
        return setText(mContext.getText(resId));
    }

    /**
     * Return the duration.
     *
     * @see #setDuration
     */
    @Duration
    public int getDuration() {
        return mDuration;
    }

    /**
     * Set how long to show the view for.
     *
     * @param duration either be one of the predefined lengths:
     *                 {@link #LENGTH_SHORT}, {@link #LENGTH_LONG}, or a custom duration
     *                 in milliseconds.
     */
    @NonNull
    public TSnackbar setDuration(@Duration int duration) {
        mDuration = duration;
        return this;
    }

    /**
     * Returns the {@link TSnackbar}'s view.
     */

    @NonNull
    public View getView() {
        return mView;
    }

    /**
     * Show the {@link TSnackbar}.
     */
    public void show() {
        SnackbarManager.getInstance().show(mDuration, mManagerCallback);
    }

    /**
     * Dismiss the {@link TSnackbar}.
     */
    public void dismiss() {
        dispatchDismiss(Callback.DISMISS_EVENT_MANUAL);
    }

    private void dispatchDismiss(@Callback.DismissEvent int event) {
        SnackbarManager.getInstance().dismiss(mManagerCallback, event);
    }

    /**
     * Set a callback to be called when this the visibility of this {@link TSnackbar} changes.
     */
    @NonNull
    public TSnackbar setCallback(Callback callback) {
        mCallback = callback;
        return this;
    }

    /**
     * Return whether this {@link TSnackbar} is currently being shown.
     */
    public boolean isShown() {
        return SnackbarManager.getInstance().isCurrent(mManagerCallback);

    }

    /**
     * Returns whether this {@link TSnackbar} is currently being shown, or is queued to be
     * shown next.
     */
    public boolean isShownOrQueued() {
        return SnackbarManager.getInstance().isCurrentOrNext(mManagerCallback);
    }

    private void showView() {
        if (mView.getParent() == null) {
            final ViewGroup.LayoutParams lp = mView.getLayoutParams();
            if (lp instanceof CoordinatorLayout.LayoutParams) {
                // If our LayoutParams are from a CoordinatorLayout, we'll setup our Behavior
                final Behavior behavior = new Behavior();
                behavior.setStartAlphaSwipeDistance(0.1f);
                behavior.setEndAlphaSwipeDistance(0.6f);
                behavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END);
                behavior.setListener(new SwipeDismissBehavior.OnDismissListener() {
                    @Override
                    public void onDismiss(View view) {
                        view.setVisibility(View.GONE);
                        dispatchDismiss(Callback.DISMISS_EVENT_SWIPE);
                    }

                    @Override
                    public void onDragStateChanged(int state) {
                        switch (state) {
                            case SwipeDismissBehavior.STATE_DRAGGING:
                            case SwipeDismissBehavior.STATE_SETTLING:

                                SnackbarManager.getInstance().cancelTimeout(mManagerCallback);
                                break;
                            case SwipeDismissBehavior.STATE_IDLE:

                                SnackbarManager.getInstance().restoreTimeout(mManagerCallback);
                                break;
                        }
                    }
                });
                ((CoordinatorLayout.LayoutParams) lp).setBehavior(behavior);

                ((CoordinatorLayout.LayoutParams) lp).setMargins(0, (int) mContext.getResources().getDimension(R.dimen.dim_30), 0, 0);
            }
            mParent.addView(mView);
        }
        if (ViewCompat.isLaidOut(mView)) {
            animateViewIn();
        } else {
            mView.setOnLayoutChangeListener(new SnackbarLayout.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View view, int left, int top, int right, int bottom) {
                    animateViewIn();
                    mView.setOnLayoutChangeListener(null);
                }
            });
        }

        mView.setOnAttachStateChangeListener(new SnackbarLayout.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                if (isShownOrQueued()) {
                    // If we haven't already been dismissed then this event is coming from a
                    // non-user initiated action. Hence we need to make sure that we callback
                    // and keep our state up to date. We need to post the call since removeView()
                    // will call through to onDetachedFromWindow and thus overflow.
                    sHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onViewHidden(Callback.DISMISS_EVENT_MANUAL);
                        }
                    });
                }
            }
        });

        if (ViewCompat.isLaidOut(mView)) {
            if (shouldAnimate()) {
                // If animations are enabled, animate it in
                animateViewIn();
            } else {
                // Else if anims are disabled just call back now
                onViewShown();
            }
        } else {
            // Otherwise, add one of our layout change listeners and show it in when laid out
            mView.setOnLayoutChangeListener(new SnackbarLayout.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View view, int left, int top, int right, int bottom) {
                    mView.setOnLayoutChangeListener(null);

                    if (shouldAnimate()) {
                        // If animations are enabled, animate it in
                        animateViewIn();
                    } else {
                        // Else if anims are disabled just call back now
                        onViewShown();
                    }
                }
            });
        }
    }

    private void animateViewIn() {
        Animation anim;
        if (appearDirection == APPEAR_FROM_TOP_TO_DOWN) {
            anim = AnimationUtil.getAnimationInFromTopToBottom(mView.getContext());
        } else {
            anim = AnimationUtil.getAnimationInFromBottomToTop(mView.getContext());
        }
        anim.setInterpolator(AnimationUtil.FAST_OUT_SLOW_IN_INTERPOLATOR);
        anim.setDuration(ANIMATION_DURATION);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                onViewShown();
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

    private void animateViewOut(final int event) {
        Animation anim;
        if (appearDirection == APPEAR_FROM_TOP_TO_DOWN) {
            anim = AnimationUtil.getAnimationOutFromTopToBottom(mView.getContext());
        } else {
            anim = AnimationUtil.getAnimationOutFromBottomToTop(mView.getContext());
        }
        anim.setInterpolator(AnimationUtil.FAST_OUT_SLOW_IN_INTERPOLATOR);
        anim.setDuration(ANIMATION_DURATION);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                onViewHidden(event);
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

    final void hideView(@Callback.DismissEvent final int event) {
        if (shouldAnimate() && mView.getVisibility() == View.VISIBLE) {
            animateViewOut(event);
        } else {
            // If anims are disabled or the view isn't visible, just call back now
            onViewHidden(event);
        }
    }

    private void onViewShown() {
        SnackbarManager.getInstance().onShown(mManagerCallback);
        if (mCallback != null) {
            mCallback.onShown(this);
        }
    }

    private void onViewHidden(int event) {
        // First tell the SnackbarManager that it has been dismissed
        SnackbarManager.getInstance().onDismissed(mManagerCallback);
        // Now call the dismiss listener (if available)
        if (mCallback != null) {
            mCallback.onDismissed(this, event);
        }
        // Lastly, remove the view from the parent (if attached)
        final ViewParent parent = mView.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(mView);
        }
    }

    /**
     * Returns true if we should animate the TSnackbar view in/out.
     */
    private boolean shouldAnimate() {
        return !mAccessibilityManager.isEnabled();
    }

    /**
     * @return if the view is being being dragged or settled by {@link SwipeDismissBehavior}.
     */
    private boolean isBeingDragged() {
        final ViewGroup.LayoutParams lp = mView.getLayoutParams();
        if (lp instanceof CoordinatorLayout.LayoutParams) {
            final CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) lp;
            final CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
            if (behavior instanceof SwipeDismissBehavior) {
                return ((SwipeDismissBehavior) behavior).getDragState() != SwipeDismissBehavior.STATE_IDLE;
            }
        }
        return false;
    }

    @IntDef({APPEAR_FROM_TOP_TO_DOWN, APPEAR_FROM_BOTTOM_TO_TOP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OverSnackAppearDirection {
    }

    @IntDef({LENGTH_INDEFINITE, LENGTH_SHORT, LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    /**
     * Callback class for {@link TSnackbar} instances.
     *
     * @see TSnackbar#setCallback(Callback)
     */
    public static abstract class Callback {
        /**
         * Indicates that the TSnackbar was dismissed via a swipe.
         */
        public static final int DISMISS_EVENT_SWIPE = 0;
        /**
         * Indicates that the TSnackbar was dismissed via an action click.
         */
        public static final int DISMISS_EVENT_ACTION = 1;
        /**
         * Indicates that the TSnackbar was dismissed via a timeout.
         */
        public static final int DISMISS_EVENT_TIMEOUT = 2;
        /**
         * Indicates that the TSnackbar was dismissed via a call to {@link #dismiss()}.
         */
        public static final int DISMISS_EVENT_MANUAL = 3;
        /**
         * Indicates that the TSnackbar was dismissed from a new TSnackbar being shown.
         */
        public static final int DISMISS_EVENT_CONSECUTIVE = 4;

        /**
         * Called when the given {@link TSnackbar} has been dismissed, either through a time-out,
         * having been manually dismissed, or an action being clicked.
         *
         * @param tSnackbar The snackbar which has been dismissed.
         * @param event     The event which caused the dismissal. One of either:
         *                  {@link #DISMISS_EVENT_SWIPE}, {@link #DISMISS_EVENT_ACTION},
         *                  {@link #DISMISS_EVENT_TIMEOUT}, {@link #DISMISS_EVENT_MANUAL} or
         *                  {@link #DISMISS_EVENT_CONSECUTIVE}.
         * @see TSnackbar#dismiss()
         */
        public void onDismissed(TSnackbar tSnackbar, @DismissEvent int event) {
        }

        /**
         * Called when the given {@link TSnackbar} is visible.
         *
         * @param tSnackbar The snackbar which is now visible.
         * @see TSnackbar#show()
         */
        public void onShown(TSnackbar tSnackbar) {

        }

        @IntDef({DISMISS_EVENT_SWIPE, DISMISS_EVENT_ACTION, DISMISS_EVENT_TIMEOUT,
                DISMISS_EVENT_MANUAL, DISMISS_EVENT_CONSECUTIVE})
        @Retention(RetentionPolicy.SOURCE)
        public @interface DismissEvent {
        }
    }

    final class Behavior extends SwipeDismissBehavior<SnackbarLayout> {
        @Override
        public boolean canSwipeDismissView(View child) {
            return child instanceof SnackbarLayout;
        }

        @Override
        public boolean onInterceptTouchEvent(CoordinatorLayout parent, SnackbarLayout child, MotionEvent event) {
            // We want to make sure that we disable any TSnackbar timeouts if the user is
            // currently touching the TSnackbar. We restore the timeout when complete
            if (parent.isPointInChildBounds(child, (int) event.getX(), (int) event.getY())) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        SnackbarManager.getInstance().cancelTimeout(mManagerCallback);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        SnackbarManager.getInstance().restoreTimeout(mManagerCallback);
                        break;
                }
            }

            return super.onInterceptTouchEvent(parent, child, event);
        }
    }
}