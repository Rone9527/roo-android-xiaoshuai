package com.roo.trade.mvp.ui.dialog;

import android.os.Bundle;
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
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.handler.HandlerUtil;
import com.roo.core.utils.utils.TokenManager;
import com.roo.trade.R;

import java.util.concurrent.TimeUnit;

public class TradeChooseLinkDialog extends FullScreenDialogFragment {

    private OnClickedListener onClickedListener;

    public static TradeChooseLinkDialog newInstance(String link) {
        TradeChooseLinkDialog fragment = new TradeChooseLinkDialog();
        Bundle args = new Bundle();
        args.putString(Constants.KEY_DEFAULT, link);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_choose_link, container, false);

        View content = inflate.findViewById(R.id.content);
        ViewGroup.LayoutParams layoutParams = content.getLayoutParams();
        layoutParams.height = ArmsUtils.getScreenHeidth(requireActivity()) * 7 / 8;
        content.setLayoutParams(layoutParams);

        ChooseLinkAdapter mAdapter = new ChooseLinkAdapter();
        mAdapter.setLink(getArguments().getString(Constants.KEY_DEFAULT));
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

        mAdapter.setNewData(TokenManager.getInstance().getLink());
        return inflate;
    }

    public TradeChooseLinkDialog setOnClickListener(OnClickedListener onClickedListener) {
        this.onClickedListener = onClickedListener;
        return this;
    }

    public interface OnClickedListener {
        void onClick(LinkTokenBean linkBean);
    }

    private static class ChooseLinkAdapter extends BaseQuickAdapter<LinkTokenBean, BaseViewHolderImpl> {
        private String link;

        public ChooseLinkAdapter() {
            super(R.layout.item_choose_link);
        }

        @Override
        protected void convert(BaseViewHolderImpl helper, LinkTokenBean item) {
            helper.setText(R.id.tvLink, item.getCode());
            helper.setCircleImageUrl(R.id.ivLink, GlobalUtils.getLinkImage(item.getCode()));
            helper.setGone(R.id.ivCheck, item.getCode().equals(link));
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getLink() {
            return link;
        }
    }
}
