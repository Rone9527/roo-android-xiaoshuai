package com.roo.core.ui.base;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.finddreams.languagelib.MultiLanguageUtil;
import com.roo.core.utils.KeyboardUtil;
import com.roo.core.utils.TouchOutsideHelper;
import com.jess.arms.mvp.IPresenter;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

/**
 * Created: AriesHoo on 2017/7/3 16:04
 * Function: title 基类
 * Desc:
 */

public abstract class InputActivity<P extends IPresenter> extends com.jess.arms.base.BaseActivity<P> {

    private TouchOutsideHelper touchOutsideHelper = new TouchOutsideHelper();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MultiLanguageUtil.attachBaseContext(ViewPumpContextWrapper.wrap(newBase)));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {//触摸
            if (touchOutsideHelper.isTouchView(hideSoftByEditViewIds(), ev)) {
                return super.dispatchTouchEvent(ev);
            }
            if (hideSoftByEditViewIds() == null ||
                    hideSoftByEditViewIds().length == 0) {
                return super.dispatchTouchEvent(ev);
            }
            View v = getCurrentFocus();
            if (touchOutsideHelper.isFocusEditText(v, hideSoftByEditViewIds())) {
                KeyboardUtil.hideKeyboard(this);//隐藏键盘
                touchOutsideHelper.clearViewFocus(v, hideSoftByEditViewIds());
            }
        }
        return super.dispatchTouchEvent(ev);

    }

    /**
     * 传入EditText的Id
     * 没有传入的EditText不做处理
     *
     * @return id 数组
     */
    public abstract View[] hideSoftByEditViewIds(View... views);

}
