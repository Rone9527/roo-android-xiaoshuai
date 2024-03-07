package com.roo.trade.mvp.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.link.DexBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;
import com.roo.core.utils.ViewHelper;
import com.roo.trade.R;

import java.util.concurrent.TimeUnit;

public class ChooseDexRecorderDialog extends FullScreenDialogFragment {

    private OnClickedListener onClickedListener;

    public static ChooseDexRecorderDialog newInstance() {
        ChooseDexRecorderDialog fragment = new ChooseDexRecorderDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_dex_recorder, container, false);
        ChooseLinkAdapter mAdapter = new ChooseLinkAdapter();
        ViewHelper.initRecyclerView(inflate, mAdapter);
        RxView.clicks(inflate.findViewById(R.id.tvCancel)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> dismiss());
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            DexBean item = mAdapter.getItem(position);
            if (onClickedListener != null) {
                onClickedListener.onClick(item);
            }
        });

        return inflate;
    }

    public ChooseDexRecorderDialog setOnClickListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public interface OnClickedListener {
        void onClick(DexBean linkBean);
    }

    private static class ChooseLinkAdapter extends BaseQuickAdapter<DexBean, BaseViewHolderImpl> {

        public ChooseLinkAdapter() {
            super(R.layout.item_choose_dex);
        }

        @Override
        protected void convert(BaseViewHolderImpl helper, DexBean item) {
        }

    }
}
