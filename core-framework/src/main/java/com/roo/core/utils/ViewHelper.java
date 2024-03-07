package com.roo.core.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.viewpager.widget.ViewPager;

import com.aries.ui.view.title.TitleBarView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.R;
import com.roo.core.ui.adapter.FragmentPageAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * <pre>
 *     project name: JMDroid
 *     author      : 李琼
 *     create time : 2018/8/2 下午5:33
 *     desc        : 描述--//ViewHelper
 *     Reference   :
 *     modifier               :
 *     modification time      :
 *     modify remarks         :
 *     @version: --//1.0
 * </pre>
 */

public class ViewHelper {

    //==========================状态栏=================================

    public static ImmersionBar initStatusBarTrans(Activity activity) {
        return initStatusBarTrans(activity, 0, null);
    }

    public static ImmersionBar initStatusBarTrans(Activity activity, View view) {
        return initStatusBarTrans(activity, view, null);
    }

    public static ImmersionBar initStatusBarTrans(Activity activity, BarHide hide) {
        return initStatusBarTrans(activity, 0, hide);
    }

    public static ImmersionBar initStatusBarTrans(Activity activity, int viewIdRes, BarHide hide) {
        if (viewIdRes != 0) {
            return initStatusBarTrans(activity, activity.findViewById(viewIdRes), hide);
        } else {
            return initStatusBarTrans(activity, null, hide);
        }
    }

    public static ImmersionBar initStatusBarTrans(Activity activity, View view, BarHide hide) {
        ImmersionBar immersionBar = ImmersionBar.with(activity);
        if (hide != null) {
            immersionBar.hideBar(hide);
        }
        if (view != null) {
            immersionBar.statusBarView(view);
        }
        return immersionBar;
    }

    /**
     * 设置状态栏背景
     */
    public static ImmersionBar initStatusBar(Activity mActivity, View view) {
        ImmersionBar immersionBar = ImmersionBar.with(mActivity);
        ImmersionBar.setTitleBar(mActivity, view);
        return immersionBar;
    }

    //==========================标题栏 TitleBarView =================================

    public static TitleBarView initTitleBar(@StringRes int stringRes, AppCompatActivity mActivity) {
        return initTitleBar(true, mActivity.getString(stringRes), mActivity, null, 0, R.drawable.ic_common_back_black, true);

    }

    public static TitleBarView initTitleBar(@StringRes int stringRes, AppCompatActivity mActivity,
                                            boolean whiteMode) {
        return initTitleBar(true, mActivity.getString(stringRes), mActivity, null, 0,
                whiteMode ? R.drawable.ic_common_back_black : R.drawable.ic_common_back_white, whiteMode);
    }

    public static TitleBarView initTitleBar(String text, AppCompatActivity mActivity) {
        return initTitleBar(true, text, mActivity, null, 0, R.drawable.ic_common_back_black, true);

    }

    public static TitleBarView initTitleBar(AppCompatActivity mActivity,
                                            boolean whiteMode) {
        return initTitleBar(true, "", mActivity, null, 0,
                whiteMode ? R.drawable.ic_common_back_black : R.drawable.ic_common_back_white, whiteMode);
    }

    public static TitleBarView initTitleBar(String text, AppCompatActivity mActivity,
                                            boolean whiteMode) {
        return initTitleBar(true, text, mActivity, null, 0,
                whiteMode ? R.drawable.ic_common_back_black : R.drawable.ic_common_back_white, whiteMode);
    }

    public static TitleBarView initTitleBar(String text, AppCompatActivity mActivity,
                                            int navigationIcon) {
        return initTitleBar(true, text, mActivity, null, 0, navigationIcon, true);
    }

    public static TitleBarView initTitleBar(String text, AppCompatActivity mActivity,
                                            int navigationIcon, boolean whiteMode) {
        return initTitleBar(true, text, mActivity, null, 0, navigationIcon, whiteMode);
    }

    public static TitleBarView initTitleBar(boolean isShowBack,
                                            @StringRes int stringRes, AppCompatActivity mActivity,
                                            boolean whiteMode) {
        return initTitleBar(isShowBack, mActivity.getString(stringRes), mActivity, null, 0,
                whiteMode ? R.drawable.ic_common_back_black : R.drawable.ic_common_back_white, whiteMode);
    }

    public static TitleBarView initTitleBar(boolean isShowBack,
                                            @StringRes int stringRes, AppCompatActivity mActivity) {
        return initTitleBar(isShowBack, mActivity.getString(stringRes), mActivity, null, 0, R.drawable.ic_common_back_black, true);
    }

    public static TitleBarView initTitleBar(boolean isShowBack, String text, AppCompatActivity mActivity,
                                            boolean whiteMode) {
        return initTitleBar(isShowBack, text, mActivity, null, 0,
                whiteMode ? R.drawable.ic_common_back_black : R.drawable.ic_common_back_white, whiteMode);
    }

    public static TitleBarView initTitleBar(boolean isShowBack,
                                            String text, AppCompatActivity mActivity) {
        return initTitleBar(isShowBack, text, mActivity, null, 0, R.drawable.ic_common_back_black, true);
    }

    public static TitleBarView initTitleBar(boolean isShowBack,
                                            String text, AppCompatActivity mActivity,
                                            Drawable drawable,
                                            @ColorInt int titleColor,
                                            @DrawableRes int navigationIcon, boolean whiteMode) {
        TitleBarView titleBarView = mActivity.findViewById(R.id.titleBar);
        if (drawable != null) {
            titleBarView.setBackground(drawable);
        }

        if (whiteMode) {
            ImmersionBar.with(mActivity).statusBarDarkFont(true).init();
        }
        if (!TextUtils.isEmpty(text)) {
            if (titleColor != 0) {
                titleBarView.setTitleMainTextColor(titleColor);
            }
            titleBarView.setTitleMainText(text);
        }
        if (isShowBack) {
            if (navigationIcon != 0) {
                titleBarView.addLeftAction(titleBarView.
                        new ImageAction(navigationIcon, v -> mActivity.finish()));
            }
        }
        return titleBarView;
    }

    public static TitleBarView initTitleBar(@StringRes int stringRes, View inflate, Fragment fragment) {
        return initTitleBar(fragment.getActivity().getString(stringRes), inflate, null, 0, 0, true, fragment);
    }

    public static TitleBarView initTitleBar(String text, View inflate, Fragment fragment) {
        return initTitleBar(text, inflate, null, 0, 0, true, fragment);
    }

    public static TitleBarView initTitleBar(String text, View inflate, boolean whiteMode, Fragment fragment) {
        return initTitleBar(text, inflate, null, 0, 0, whiteMode, fragment);
    }

    public static TitleBarView initTitleBar(String text, View inflate, int navigationIcon, Fragment fragment) {
        return initTitleBar(text, inflate, null, 0, navigationIcon, true, fragment);
    }

    public static TitleBarView initTitleBar(String text, View inflate, int navigationIcon, boolean whiteMode, Fragment fragment) {
        return initTitleBar(text, inflate, null, 0, navigationIcon, whiteMode, fragment);
    }

    public static TitleBarView initTitleBar(@StringRes int stringRes, View inflate, boolean whiteMode, Fragment fragment) {
        return initTitleBar(fragment.getActivity().getString(stringRes), inflate, null, 0, 0, whiteMode, fragment);
    }

    public static TitleBarView initTitleBar(String text,
                                            View inflate,
                                            Drawable drawable,
                                            @ColorRes int titleColor,
                                            @DrawableRes int navigationIcon,
                                            boolean whiteMode,
                                            Fragment fragment) {
        TitleBarView titleBarView = inflate.findViewById(R.id.titleBar);
        if (drawable != null) {
            titleBarView.setBackground(drawable);
        } else if (whiteMode) {
            titleBarView.setBackgroundColor(ContextCompat.getColor(fragment.getActivity(),
                    android.R.color.white));
        }

        if (!TextUtils.isEmpty(text)) {
            if (titleColor != 0) {
                titleBarView.setTitleMainTextColor(fragment.getActivity()
                        .getResources().getColor(titleColor));
            } else if (whiteMode) {
                titleBarView.setTitleMainTextColor(fragment.getActivity()
                        .getResources().getColor(android.R.color.black));
            }
            titleBarView.setTitleMainText(text);
        }
        if (navigationIcon != 0) {
            titleBarView.addLeftAction(titleBarView.
                    new ImageAction(navigationIcon, v -> fragment.getActivity().finish()));
        }
        if (whiteMode) {
            ImmersionBar.with(fragment).statusBarDarkFont(true).init();
        }
        return titleBarView;
    }
    //==========================标题栏 TitleBarView END=================================

    public static RecyclerView initRecyclerView(@NonNull View mRootView,
                                                RecyclerView.Adapter adapter) {
        return initRecyclerView(mRootView, adapter, R.id.recycler_view);
    }

    public static RecyclerView initRecyclerView(@NonNull View mRootView,
                                                RecyclerView.Adapter adapter,
                                                @IdRes int recyclerIdRes) {
        RecyclerView mRecyclerView = mRootView.findViewById(recyclerIdRes);
        return initRecyclerView(mRecyclerView, adapter, new LinearLayoutManager(mRecyclerView.getContext()));
    }

    public static RecyclerView initRecyclerView(@NonNull View mRootView,
                                                RecyclerView.Adapter adapter,
                                                @NonNull RecyclerView.LayoutManager mLayoutManager) {
        RecyclerView mRecyclerView = mRootView.findViewById(R.id.recycler_view);
        return initRecyclerView(mRecyclerView, adapter, mLayoutManager);
    }


    public static RecyclerView initRecyclerView(@NonNull View mRootView,
                                                RecyclerView.Adapter adapter,
                                                @IdRes int recyclerIdRes,
                                                @NonNull RecyclerView.LayoutManager mLayoutManager) {
        RecyclerView mRecyclerView = mRootView.findViewById(recyclerIdRes);
        return initRecyclerView(mRecyclerView, adapter, mLayoutManager);
    }


    public static RecyclerView initRecyclerView(@NonNull RecyclerView mRecyclerView,
                                                RecyclerView.Adapter adapter) {
        return initRecyclerView(mRecyclerView, adapter, new LinearLayoutManager(mRecyclerView.getContext()));
    }

    public static RecyclerView initRecyclerView(@NonNull RecyclerView mRecyclerView,
                                                RecyclerView.Adapter adapter,
                                                @NonNull RecyclerView.LayoutManager mLayoutManager) {
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (adapter == null) {
            throw new RuntimeException("adapter can not be null !");
        }
        mRecyclerView.setAdapter(adapter);
        if (mRecyclerView.getItemAnimator() instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        }
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        return mRecyclerView;
    }

    public static RecyclerView initRecyclerView(RecyclerView.Adapter adapter,
                                                @NonNull Activity activity) {
        return initRecyclerView(adapter, activity, new LinearLayoutManager(activity));
    }

    public static RecyclerView initRecyclerView(RecyclerView.Adapter adapter,
                                                @NonNull Activity activity,
                                                @NonNull RecyclerView.LayoutManager mLayoutManager) {
        return initRecyclerView(adapter, activity, mLayoutManager, R.id.recycler_view);
    }

    public static RecyclerView initRecyclerView(RecyclerView.Adapter adapter,
                                                @NonNull Activity activity,
                                                @NonNull RecyclerView.LayoutManager mLayoutManager,
                                                @IdRes int idRes) {
        RecyclerView mRecyclerView = activity.findViewById(idRes);
        return initRecyclerView(mRecyclerView, adapter, mLayoutManager);
    }

    public static SmartRefreshLayout initRefreshLayout(@NonNull Activity mActivity,
                                                       @Nullable OnRefreshListener refreshListener,
                                                       @Nullable OnLoadMoreListener loadMoreListener) {
        SmartRefreshLayout mRefreshLayout = mActivity.findViewById(R.id.refreshLayout);
        return initRefreshLayout(mRefreshLayout, refreshListener, loadMoreListener);
    }

    public static SmartRefreshLayout initRefreshLayout(@NonNull Activity mActivity,
                                                       @Nullable OnRefreshListener refreshListener) {
        SmartRefreshLayout mRefreshLayout = mActivity.findViewById(R.id.refreshLayout);
        return initRefreshLayout(mRefreshLayout, refreshListener, null);
    }

    public static SmartRefreshLayout initRefreshLayout(@NonNull Activity mActivity,
                                                       @Nullable OnLoadMoreListener laodMoreListener) {
        SmartRefreshLayout mRefreshLayout = mActivity.findViewById(R.id.refreshLayout);
        return initRefreshLayout(mRefreshLayout, null, laodMoreListener);
    }

    public static SmartRefreshLayout initRefreshLayout(@NonNull Activity mActivity) {
        SmartRefreshLayout mRefreshLayout = mActivity.findViewById(R.id.refreshLayout);
        return initRefreshLayout(mRefreshLayout, null, null);
    }

    public static SmartRefreshLayout initRefreshLayout(@NonNull View inflate) {
        SmartRefreshLayout mRefreshLayout = inflate.findViewById(R.id.refreshLayout);
        return initRefreshLayout(mRefreshLayout, null, null);
    }

    public static SmartRefreshLayout initRefreshLayout(@NonNull Activity mActivity, @IdRes int refreshId,
                                                       @Nullable OnRefreshListener refreshListener,
                                                       @Nullable OnLoadMoreListener loadMoreListener) {
        SmartRefreshLayout mRefreshLayout = mActivity.findViewById(refreshId);
        return initRefreshLayout(mRefreshLayout, refreshListener, loadMoreListener);
    }

    public static SmartRefreshLayout initRefreshLayout(@NonNull View mRootView, @Nullable OnRefreshListener refreshListener) {
        return initRefreshLayout(mRootView, refreshListener, null);
    }

    public static SmartRefreshLayout initRefreshLayout(@NonNull View mRootView,
                                                       @Nullable OnRefreshListener refreshListener,
                                                       @Nullable OnLoadMoreListener loadMoreListener) {
        SmartRefreshLayout mRefreshLayout = mRootView.findViewById(R.id.refreshLayout);
        return initRefreshLayout(mRefreshLayout, refreshListener, loadMoreListener);
    }

    public static SmartRefreshLayout initRefreshLayout(@NonNull View mRootView, @IdRes int refreshId,
                                                       @Nullable OnRefreshListener refreshListener,
                                                       @Nullable OnLoadMoreListener loadMoreListener) {
        SmartRefreshLayout mRefreshLayout = mRootView.findViewById(refreshId);
        return initRefreshLayout(mRefreshLayout, refreshListener, loadMoreListener);
    }

    public static SmartRefreshLayout initRefreshLayout(@NonNull SmartRefreshLayout mRefreshLayout,
                                                       @Nullable OnRefreshListener refreshListener,
                                                       @Nullable OnLoadMoreListener loadMoreListener) {
        mRefreshLayout.setOnRefreshListener(refreshListener);
        mRefreshLayout.setEnableRefresh(refreshListener != null);

        mRefreshLayout.setOnLoadMoreListener(loadMoreListener);
        mRefreshLayout.setEnableLoadMore(loadMoreListener != null);
        return mRefreshLayout;

    }

    public static View initEmptyView(BaseQuickAdapter adapter, RecyclerView mRecyclerView) {
        return initEmptyView(adapter, mRecyclerView, mRecyclerView.getContext().getString(R.string.no_data), 0, 0, 0, null);
    }

    public static View initEmptyView(BaseQuickAdapter adapter, RecyclerView mRecyclerView,
                                     View.OnClickListener clickListener) {
        return initEmptyView(adapter, mRecyclerView, mRecyclerView.getContext().getString(R.string.no_data), 0, 0, 0, clickListener);
    }

    public static View initEmptyView(BaseQuickAdapter adapter, RecyclerView mRecyclerView,
                                     @DrawableRes int resId) {
        return initEmptyView(adapter, mRecyclerView, "", 0, resId, 0, null);
    }

    public static View initEmptyView(BaseQuickAdapter adapter, @LayoutRes int layoutRes, RecyclerView mRecyclerView) {
        return initEmptyView(adapter, mRecyclerView, "", 0, 0, layoutRes, null);
    }

    public static View initEmptyView(BaseQuickAdapter adapter, RecyclerView mRecyclerView,
                                     @DrawableRes int resId,
                                     View.OnClickListener clickListener) {
        return initEmptyView(adapter, mRecyclerView, "", 0, resId, 0, clickListener);

    }

    public static View initEmptyView(BaseQuickAdapter adapter, RecyclerView mRecyclerView,
                                     String strHint) {
        return initEmptyView(adapter, mRecyclerView, strHint, 0, 0, 0, null);

    }

    public static View initEmptyView(BaseQuickAdapter adapter, RecyclerView mRecyclerView,
                                     String strHint,
                                     View.OnClickListener clickListener) {
        return initEmptyView(adapter, mRecyclerView, strHint, 0, 0, 0, clickListener);
    }

    public static View initEmptyView(BaseQuickAdapter adapter, RecyclerView mRecyclerView,
                                     String strHint,
                                     @ColorInt int textColor, @DrawableRes int resId) {
        return initEmptyView(adapter, mRecyclerView, strHint, textColor, resId, 0, null);
    }

    public static View initEmptyView(BaseQuickAdapter adapter, RecyclerView mRecyclerView,
                                     String strHint,
                                     @DrawableRes int resId) {
        return initEmptyView(adapter, mRecyclerView, strHint, 0, resId, 0, null);
    }

    public static View initEmptyView(@LayoutRes int layoutRes,
                                     BaseQuickAdapter adapter, RecyclerView mRecyclerView) {
        return initEmptyView(adapter, mRecyclerView, mRecyclerView.getContext().getString(R.string.no_data), 0, 0, layoutRes, null);
    }

    public static View initEmptyView(@LayoutRes int layoutRes,
                                     BaseQuickAdapter adapter, RecyclerView mRecyclerView,
                                     String strHint) {
        return initEmptyView(adapter, mRecyclerView, strHint, 0, 0, layoutRes, null);
    }

    public static View initEmptyView(@LayoutRes int layoutRes,
                                     BaseQuickAdapter adapter, RecyclerView mRecyclerView,
                                     String strHint,
                                     @DrawableRes int resId) {
        return initEmptyView(adapter, mRecyclerView, strHint, 0, resId, layoutRes, null);
    }

    public static View initEmptyView(@LayoutRes int layoutRes, BaseQuickAdapter adapter, RecyclerView mRecyclerView,
                                     String strHint,
                                     @ColorInt int textColor, @DrawableRes int resId) {
        return initEmptyView(adapter, mRecyclerView, strHint, textColor, resId, layoutRes, null);
    }

    public static View initEmptyView(BaseQuickAdapter adapter, RecyclerView mRecyclerView,
                                     String strHint,
                                     @ColorInt int textColor, @DrawableRes int resId,
                                     View.OnClickListener clickListener) {
        return initEmptyView(adapter, mRecyclerView, strHint, textColor, resId, 0, clickListener);
    }

    public static View initEmptyView(BaseQuickAdapter adapter, RecyclerView mRecyclerView,
                                     String strHint,
                                     @ColorInt int textColor, @DrawableRes int resId, @LayoutRes int layoutRes,
                                     View.OnClickListener clickListener) {
        Context ctx = mRecyclerView.getContext();
        View emptyRecView = LayoutInflater.from(ctx)
                .inflate(layoutRes == 0 ? R.layout.default_empty_view : layoutRes,
                        (ViewGroup) mRecyclerView.getParent(), false);

        ImageView emptyRecImg = emptyRecView.findViewById(R.id.empty_img);
        emptyRecImg.setImageResource(resId == 0 ? R.drawable.ic_common_empty_data : resId);
        if (!TextUtils.isEmpty(strHint)) {
            TextView emptyRecString = emptyRecView.findViewById(R.id.empty_string);
            emptyRecString.setText(strHint);
            emptyRecString.setTextColor(textColor == 0 ?
                    ContextCompat.getColor(ctx, R.color.white_color_assist_1) : textColor);
        }
        emptyRecView.setOnClickListener(clickListener);
        adapter.setEmptyView(emptyRecView);
        return emptyRecView;
    }

    public static AVLoadingIndicatorView initLoadingView(BaseQuickAdapter adapter, RecyclerView mRecyclerView) {
        return initLoadingView(adapter, mRecyclerView, "", ContextCompat.getColor(mRecyclerView.getContext(), R.color.colorAccent));
    }

    public static AVLoadingIndicatorView initLoadingView(BaseQuickAdapter adapter, RecyclerView mRecyclerView, String strHint) {
        return initLoadingView(adapter, mRecyclerView, strHint, ContextCompat.getColor(mRecyclerView.getContext(), R.color.colorAccent));
    }

    public static AVLoadingIndicatorView initLoadingView(BaseQuickAdapter adapter, RecyclerView mRecyclerView, String strHint, @ColorInt int textColor) {

        View loadingView = LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.default_loading_full,
                (ViewGroup) mRecyclerView.getParent(), false);
        AVLoadingIndicatorView mAviLoading = loadingView.findViewById(R.id.avi_loading);
        mAviLoading.setIndicatorColor(textColor);
        TextView loadingString = loadingView.findViewById(R.id.loading_string);
        if (TextUtils.isEmpty(strHint)) {
            loadingString.setVisibility(View.GONE);
        } else {
            loadingString.setVisibility(View.VISIBLE);
            loadingString.setText(strHint);
            if (textColor != 0) {
                loadingString.setTextColor(textColor);
            }
        }
        adapter.setEmptyView(loadingView);
        return mAviLoading;
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

    /**
     * 滚动root，使scrollToView在root可视区域的底部
     * eg:解决输入账号密码被遮挡的情况
     *
     * @param rootView     最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的View
     * @param srollHeight  单位 dp
     */
    public static void solveKeyboard(View rootView, View scrollToView, int srollHeight) {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect rect = new Rect();
            //获取root在窗体的可视区域
            rootView.getWindowVisibleDisplayFrame(rect);
            //获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
            int rootInvisibleHeight = rootView.getRootView().getHeight() - rect.bottom;
            //若不可视区域高度大于一定数值，则说明键盘显示
            if (rootInvisibleHeight > ArmsUtils.getScreenHeidth(rootView.getContext()) * 0.20) {
                int[] location = new int[2];
                //获取scrollToView在窗体的坐标
                scrollToView.getLocationInWindow(location);
                //计算root滚动高度，使scrollToView在可见区域
                rootView.scrollTo(0, srollHeight);
            } else {
                //键盘隐藏
                rootView.scrollTo(0, 0);
            }
        });
    }

    public static void initSlieTabLayout(SlidingTabLayout tabLayout, ViewPager viewPager,
                                         FragmentManager fragmentManager, ArrayList<? extends Fragment> fragments, String[] titles) {
        viewPager.setAdapter(new FragmentPageAdapter<>(fragmentManager, fragments, titles));
        tabLayout.setViewPager(viewPager, titles);
    }

    /**
     * 判断是否是双击操作
     */

    public static void doubleClickDetect(View view, int timeMillis, Consumer<List<Object>> event) {
        Observable<Object> observable = RxView.clicks(view).share();
        observable.buffer(observable.debounce(timeMillis == 0 ? 300 : timeMillis, TimeUnit.MILLISECONDS))
                .observeOn(AndroidSchedulers.mainThread())
                .filter(objects -> objects.size() >= 2)
                .subscribe(event);
    }

    public static View initHeaderView(@LayoutRes int layoutRes, BaseQuickAdapter mAdapter, RecyclerView mRecyclerView) {
        View headView = LayoutInflater.from(mRecyclerView.getContext())
                .inflate(layoutRes, (ViewGroup) mRecyclerView.getParent(), false);
        mAdapter.setHeaderView(headView);
        return headView;
    }

    public static View addHeaderView(int layoutRes, BaseQuickAdapter mAdapter, RecyclerView mRecyclerView) {
        View headView = LayoutInflater.from(mRecyclerView.getContext())
                .inflate(layoutRes, (ViewGroup) mRecyclerView.getParent(), false);
        mAdapter.addHeaderView(headView);
        return headView;
    }

    public static View initFooterView(@LayoutRes int layoutRes, BaseQuickAdapter mAdapter, RecyclerView mRecyclerView) {
        View footView = LayoutInflater.from(mRecyclerView.getContext())
                .inflate(layoutRes, (ViewGroup) mRecyclerView.getParent(), false);
        mAdapter.setFooterView(footView);
        return footView;
    }

    public static View addFooterView(int layoutRes, BaseQuickAdapter mAdapter, RecyclerView mRecyclerView) {
        View footView = LayoutInflater.from(mRecyclerView.getContext())
                .inflate(layoutRes, (ViewGroup) mRecyclerView.getParent(), false);
        mAdapter.addFooterView(footView);
        return footView;
    }
}
