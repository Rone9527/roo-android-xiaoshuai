package com.roo.dapp.mvp.ui.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.link.LinkTokenBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.constants.Constants;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.core.ui.dialog.base.FullScreenDialogFragment;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.Kits;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.handler.HandlerUtil;
import com.roo.core.utils.utils.TokenManager;
import com.roo.dapp.R;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DappChooseLinkDialog extends FullScreenDialogFragment {
    public static final String KEY_SHOW_ALL = "KEY_SHOW_ALL";

    private OnClickedListener onClickedListener;

    public static DappChooseLinkDialog newInstance(String link) {
        DappChooseLinkDialog fragment = new DappChooseLinkDialog();
        Bundle args = new Bundle();
        args.putString(Constants.KEY_DEFAULT, link);
        args.putBoolean(KEY_SHOW_ALL, true);
        fragment.setArguments(args);
        return fragment;
    }

    public static DappChooseLinkDialog newInstance() {
        DappChooseLinkDialog fragment = new DappChooseLinkDialog();
        Bundle args = new Bundle();
        args.putBoolean(KEY_SHOW_ALL, false);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_choose_link_dapp, container, false);

        View content = inflate.findViewById(R.id.content);
        ViewGroup.LayoutParams layoutParams = content.getLayoutParams();
        layoutParams.height = ArmsUtils.getScreenHeidth(requireActivity()) * 7 / 8;
        content.setLayoutParams(layoutParams);

        ChooseLinkAdapter mAdapter = new ChooseLinkAdapter();
        mAdapter.setLink(getArguments().getString(Constants.KEY_DEFAULT));
        boolean showAll = getArguments().getBoolean(KEY_SHOW_ALL);
        ViewHelper.initRecyclerView(inflate, mAdapter);
        RxView.clicks(inflate.findViewById(R.id.ivClose)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> dismiss());
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            LinkTokenBean item = mAdapter.getItem(position);
            mAdapter.setLink(item.getCode());
            mAdapter.notifyDataSetChanged();

            if (onClickedListener != null) {
                onClickedListener.onClick(item);
            }
            HandlerUtil.runOnUiThreadDelay(this::dismiss, 200);
        });

        ArrayList<LinkTokenBean> links = TokenManager.getInstance().getLink();
        if (!Kits.Empty.check(links)) {//全部资产
            ArrayList<LinkTokenBean> dataSet = new ArrayList<>(links);
            if (showAll) {
                dataSet.add(0, new LinkTokenBean());
            }
            mAdapter.setNewData(dataSet);
        }
        return inflate;
    }

    public DappChooseLinkDialog setOnClickListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public interface OnClickedListener {
        void onClick(LinkTokenBean linkBean);
    }

    private static class ChooseLinkAdapter extends BaseQuickAdapter<LinkTokenBean, BaseViewHolderImpl> {
        private String link;

        public ChooseLinkAdapter() {
            super(R.layout.item_choose_link_dapp);
        }

        @Override
        protected void convert(BaseViewHolderImpl helper, LinkTokenBean item) {
            if (TextUtils.isEmpty(item.getCode()) &&
                    TextUtils.isEmpty(item.getName()) &&
                    TextUtils.isEmpty(item.getNameEn())) {
                helper.setGone(R.id.ivCheck, item.getCode() == null && link == null);
            } else {
                helper.setText(R.id.tvLink, item.getCode());
                helper.setCircleImageUrl(R.id.ivLink, GlobalUtils.getLinkImage(item.getCode()));
                helper.setGone(R.id.ivCheck, item.getCode().equals(link));
            }
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getLink() {
            return link;
        }
    }
}
