package com.roo.home.mvp.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.roo.core.R;

/**
 * //  ┏┓　　　┏┓
 * //┏┛┻━━━┛┻┓
 * //┃　　　　　　　┃
 * //┃　　　━　　　┃
 * //┃　┳┛　┗┳　┃
 * //┃　　　　　　　┃
 * //┃　　　┻　　　┃
 * //┃　　　　　　　┃
 * //┗━┓　　　┏━┛
 * //    ┃　　　┃   神兽保佑
 * //    ┃　　　┃   代码无BUG！
 * //    ┃　　　┗━━━┓
 * //    ┃　　　　　　　┣┓
 * //    ┃　　　　　　　┏┛
 * //    ┗┓┓┏━┳┓┏┛
 * //      ┃┫┫　┃┫┫
 * //      ┗┻┛　┗┻┛
 * Created by : Arvin
 * Created on : 2021/9/22
 * PackageName : com.roo.core.ui.dialog
 * Description :
 */
public class DialogUtils {
    private static Dialog dialog;
    /**
     * Dialog提示框消失方法
     */
    public static void dialogDismiss() {
        if (isDialogShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * Dialog提示框是否正在运行
     *
     * @return Dialog提示框是否正在运行
     */
    public static boolean isDialogShowing() {
        return dialog != null && dialog.isShowing();
    }

    public static Dialog showConfirmDialog(Context context) {
        dialog = new Dialog(context, R.style.dialog);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.CENTER);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //填充对话框的布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_test_success, null);
        //将自定义布局加载到dialog上
        dialog.setContentView(inflate);
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();

        return dialog;
    }
}
