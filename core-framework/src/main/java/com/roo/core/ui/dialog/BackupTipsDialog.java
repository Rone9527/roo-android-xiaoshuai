package com.roo.core.ui.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jakewharton.rxbinding2.view.RxView;
import com.roo.core.R;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;

import java.util.concurrent.TimeUnit;

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
 * Description :备份助记词提醒
 */
public class BackupTipsDialog extends FullScreenDialogFragment {
    private BackupTipsDialog.OnClickedListener onClickedListener;

    public static BackupTipsDialog newInstance() {
        BackupTipsDialog fragment = new BackupTipsDialog();

        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        setCancelable(false);
    }

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_backup_tips, container, false);
        RxView.clicks(inflate.findViewById(R.id.tv_cancel)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onCancel();
                        dismiss();
                    }
                });
        TextView tvBackup = inflate.findViewById(R.id.tv_backup);
        RxView.clicks(tvBackup).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (onClickedListener != null) {
                        onClickedListener.onClick();
                    }
                });
        return inflate;
    }

    public BackupTipsDialog setOnClickListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public interface OnClickedListener {
        void onClick();

        void onCancel();
    }
}
