package com.roo.home.mvp.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.roo.home.R;

public class ConfirmDialog extends BaseDialogFragment {
 


    @Override
    protected View setView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.dialog_confirm, container, false);
        return inflate;
    }

    public static class Builder extends BaseDialogFragment.Builder<Builder, ConfirmDialog> {

        private String mTitle;
        private String mMessage;
        private String leftText;
        private String rightText;

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setMessage(String message) {
            mMessage = message;
            return this;
        }

        public Builder setLeftText(String leftText) {
            this.leftText = leftText;
            return this;
        }

        public Builder setRightText(String rightText) {
            this.rightText = rightText;
            return this;
        }

        @Override
        public ConfirmDialog build() {
            return ConfirmDialog.newInstance(this);
        }
    }
    private static ConfirmDialog newInstance(Builder builder) {
        ConfirmDialog dialog = new ConfirmDialog();
        Bundle bundle = getArgumentBundle(builder);
        bundle.putString(LEFT_TEXT, builder.leftText);
        bundle.putString(RIGHT_TEXT, builder.rightText);
        bundle.putString(PARAM_TITLE, builder.mTitle);
        bundle.putString(PARAM_MESSAGE, builder.mMessage);
        dialog.setArguments(bundle);
        return dialog;
    }
    private static final String LEFT_TEXT = "left_text";
    private static final String RIGHT_TEXT = "right_text";
    private static final String PARAM_TITLE = "title";
    private static final String PARAM_MESSAGE = "message";

}
