package com.roo.core.ui.dialog;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.jakewharton.rxbinding2.view.RxView;
import com.roo.core.R;
import com.roo.core.app.constants.Constants;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;
import com.roo.core.utils.Kits;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.intentshare.NativeShareTool;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class ShareDialog extends FullScreenDialogFragment {

    public static ShareDialog newInstance(Bitmap bitmap) {
        ShareDialog fragment = new ShareDialog();
        Bundle args = new Bundle();
        args.putParcelable(Constants.KEY_DEFAULT, bitmap);
        fragment.setArguments(args);
        return fragment;
    }
    private OnClickedListener onClickedListener;

    public ShareDialog setOnClickListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public interface OnClickedListener {
        void onShare();
    }
    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_share, container, false);
        Bitmap bitmap = getArguments().getParcelable(Constants.KEY_DEFAULT);
        String fileName = "cachier-" + Kits.Date.getYmdhms(System.currentTimeMillis());

        RxView.clicks(inflate.findViewById(R.id.tvCancel)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> dismiss());


        RxView.clicks(inflate.findViewById(R.id.layoutWechat)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    //微信好友-图片
                    File file = Kits.File.savePhoto(fileName, bitmap);
                    Kits.File.flushMedia(requireActivity(), file);
                    NativeShareTool.getInstance().shareWechatFriend(file, true, requireActivity());
                    onShare();
                });

        RxView.clicks(inflate.findViewById(R.id.layoutWechatTimeLine)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    //微信朋友圈-单图
                    File file = Kits.File.savePhoto(fileName, bitmap);
                    Kits.File.flushMedia(requireActivity(), file);
                    NativeShareTool.getInstance().shareWechatMoment(file, requireActivity());
                    onShare();
                });
        RxView.clicks(inflate.findViewById(R.id.layoutQQ)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    //QQ好友-图片
                    File file = Kits.File.savePhoto(fileName, bitmap);
                    Kits.File.flushMedia(requireActivity(), file);
                    NativeShareTool.getInstance().shareImageToQQ(file, requireActivity());
                    onShare();
                });

        RxView.clicks(inflate.findViewById(R.id.layoutSaveFile)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    //保存图片
                    File file = Kits.File.savePhoto(fileName, bitmap);
                    Kits.File.flushMedia(requireActivity(), file);
                    ToastUtils.show(getString(R.string.toast_save_pic_success));
                    dismiss();
                });

        return inflate;
    }

    private void onShare() {
        if (onClickedListener != null) {
            onClickedListener.onShare();
        }
        dismiss();
    }

}
