package com.xyzlf.custom.keyboardlib;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class KeyboardDialog extends Dialog {

    private PasswordView mPasswordView;
    private KeyboardGridAdapter mKeyboardAdapter;

    private IKeyboardListener mOnListener;

    private TextView keyBordTitle;
    private LinearLayout layoutTips;
    private TextView keyBordTitleSub;
    private TextView keyBordTitleTips;

    public static KeyboardDialog newInstance(Context context) {
        return new KeyboardDialog(context);
    }

    protected CancellickedListener onCancelClickedListener;

    public void setOnCancelClickListener(CancellickedListener OnCancellickedListener) {
        this.onCancelClickedListener = OnCancellickedListener;

    }

    private KeyboardDialog(Context context) {
        super(context, R.style.KeyboardDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keyboard_dialog);
        if (this.getWindow() != null) {
            WindowManager.LayoutParams lp = this.getWindow().getAttributes();
            lp.width = getContext().getResources().getDisplayMetrics().widthPixels;
            this.getWindow().setAttributes(lp);
        }
        //setCanceledOnTouchOutside(false);
        //setCancelable(false);
//        findViewById(R.id.keybordOutSide).setOnClickListener(v -> dismiss());
        findViewById(R.id.keybordOutSide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCancelClickedListener != null) {
                    onCancelClickedListener.onClick();
                }
                dismiss();
            }
        });
        findViewById(R.id.keybordClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCancelClickedListener != null) {
                    onCancelClickedListener.onClick();
                }
                dismiss();
            }
        });
        keyBordTitleSub = findViewById(R.id.keyBordTitleSub);
        keyBordTitleTips = findViewById(R.id.keyBordTitleTips);
        keyBordTitle = findViewById(R.id.keyBordTitle);
        layoutTips = findViewById(R.id.layoutTips);
        mPasswordView = findViewById(R.id.dialog_password);
        mPasswordView.setOnPasswordListener(new PasswordView.OnPasswordListener() {
            @Override
            public void onPasswordChange(String text) {
                if (null != mOnListener) {
                    mOnListener.onPasswordChange(text);
                }
            }

            @Override
            public void onPasswordComplete(String text) {
                if (null != mOnListener) {
                    mOnListener.onPasswordComplete(text);
                    mKeyboardAdapter.setDataSet(getDataSet());
                    mKeyboardAdapter.notifyDataSetChanged();
                }
            }

        });
        GridView keyboardGrid = findViewById(R.id.dialog_grid);

        mKeyboardAdapter = new KeyboardGridAdapter(getDataSet());
        keyboardGrid.setAdapter(mKeyboardAdapter);
        keyboardGrid.setOnItemClickListener((parent, view, position, id) -> {
            if (null == mKeyboardAdapter) {
                return;
            }
            if (position == (mKeyboardAdapter.getCount() - 3)) {
                return;
            }
            if (position == (mKeyboardAdapter.getCount() - 1)) {
                mPasswordView.deletePassword();
            } else {
                int value = mKeyboardAdapter.getItem(position);
                mPasswordView.addPassword(value);
            }
        });
        keyboardGrid.setOnItemLongClickListener((parent, view, position, id) -> {
            if (position == mKeyboardAdapter.getCount() - 1) {
                mPasswordView.clearPassword();
                return true;
            }
            return false;
        });
    }

    private List<Integer> getDataSet() {
        List<Integer> dataSet = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataSet.add(i);
        }
        Collections.shuffle(dataSet);
        dataSet.add(9, KeyboardGridAdapter.TYPE_VALUE_BANK);
        dataSet.add(KeyboardGridAdapter.TYPE_VALUE_DELETE);
        return dataSet;
    }

    @Override
    public void show() {
        try {
            Context context = getContext();
            if (context instanceof Activity) {
                if (((Activity) context).isFinishing()) {
                    return;
                }
            }
            if (isShowing()) {
                return;
            }
            super.show();
        } catch (Throwable ignore) {
        }
    }

    @Override
    public void dismiss() {
        try {
            Context context = getContext();
            if (context instanceof Activity) {
                if (((Activity) context).isFinishing()) {
                    return;
                }
            }
            if (isShowing()) {
                super.dismiss();
            }
        } catch (Throwable ignore) {
        }
    }

    public KeyboardDialog setKeyboardLintener(IKeyboardListener listener) {
        this.mOnListener = listener;
        return this;
    }

    public TextView getTitle() {
        return keyBordTitle;
    }

    public LinearLayout getTipsLayout() {
        return layoutTips;
    }

    public void closeGone() {
        findViewById(R.id.keybordClose).setVisibility(View.GONE);
    }

    public TextView getTitleSub() {
        return keyBordTitleSub;
    }

    public TextView getTitleTips() {
        return keyBordTitleTips;
    }

    public void clearPassword() {
        mPasswordView.clearPassword();
    }
}
