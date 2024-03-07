package com.roo.trade.mvp.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.link.LinkTokenBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.roo.core.app.constants.Constants;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;
import com.roo.core.utils.Kits;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.handler.HandlerUtil;
import com.roo.core.utils.utils.TokenManager;
import com.roo.trade.R;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ChooseDexDialog extends FullScreenDialogFragment {
    public static final String KEY_SHOW_ALL = "KEY_SHOW_ALL";

    private OnClickedListener onClickedListener;

    public static ChooseDexDialog newInstance(String link) {
        ChooseDexDialog fragment = new ChooseDexDialog();
        Bundle args = new Bundle();
        args.putString(Constants.KEY_DEFAULT, link);
        args.putBoolean(KEY_SHOW_ALL, true);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_choose_dex, container, false);
        ChooseLinkAdapter mAdapter = new ChooseLinkAdapter();
        mAdapter.setDex(getArguments().getString(Constants.KEY_DEFAULT));
        boolean showAll = getArguments().getBoolean(KEY_SHOW_ALL);
        ViewHelper.initRecyclerView(inflate, mAdapter);
        RxView.clicks(inflate.findViewById(R.id.tvCancel)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> dismiss());
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            LinkTokenBean item = mAdapter.getItem(position);
            mAdapter.setDex(item.getCode());
            mAdapter.notifyDataSetChanged();

            if (onClickedListener != null) {
                onClickedListener.onClick(item);
            }
            HandlerUtil.runOnUiThreadDelay(this::dismiss, 200);
        });

        ArrayList<LinkTokenBean> links = TokenManager.getInstance().getLink();
        if (!Kits.Empty.check(links)) {
            ArrayList<LinkTokenBean> dataSet = new ArrayList<>(links);
            if (showAll) {
                dataSet.add(0, new LinkTokenBean());
            }
            mAdapter.setNewData(dataSet);
        }
        return inflate;
    }

    public ChooseDexDialog setOnClickListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public interface OnClickedListener {
        void onClick(LinkTokenBean linkBean);
    }

    private static class ChooseLinkAdapter extends BaseQuickAdapter<LinkTokenBean, BaseViewHolderImpl> {
        private String dex;

        public ChooseLinkAdapter() {
            super(R.layout.item_choose_dex);
        }

        @Override
        protected void convert(BaseViewHolderImpl helper, LinkTokenBean item) {
        }

        public void setDex(String dex) {
            this.dex = dex;
        }

        public String getDex() {
            return dex;
        }
    }
}
