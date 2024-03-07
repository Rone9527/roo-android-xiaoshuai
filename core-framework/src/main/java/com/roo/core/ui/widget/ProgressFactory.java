package com.roo.core.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.roo.core.R;

/**
 * Created by xiaoys on 2016/11/15.
 */

public class ProgressFactory {

    private ProgressFactory() {

    }

    public static Dialog newLoading(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);// 得到加载view
        LinearLayout layout = inflate.findViewById(R.id.dialog_loading_view);// 加载布局

        Dialog loadingDialog = new Dialog(context, R.style.DialogLoadingStyle);// 创建自定义样式dialog
        loadingDialog.setCancelable(true); // 是否可以按“返回键”消失
        loadingDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        /**将显示Dialog的方法封装在这里面  */
        Window window = loadingDialog.getWindow();
        window.setDimAmount(0f);
        WindowManager.LayoutParams lp = window.getAttributes();

        window.setAttributes(lp);

        //为Window设置动画
        window.setWindowAnimations(R.style.PopWindowAnimStyle);
        loadingDialog.show();
        return loadingDialog;
    }

    /**
     * 关闭dialog
     */
    public static Dialog closeDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            return dialog;
        }
        return null;
    }
}