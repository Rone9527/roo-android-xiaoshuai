package com.roo.dapp.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.core.domain.dapp.DappBean;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.core.utils.LogManage;
import com.roo.dapp.R;
import com.roo.view.swipe.EasySwipeMenuLayout;
import com.roo.view.swipe.SwipeState;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/13 10:26
 *     desc        : 描述--//BackupWordsAdapter 备份助记词
 * </pre>
 */

public class DappFavoriteAdapter extends BaseItemDraggableAdapter<DappBean, BaseViewHolderImpl> {
    private boolean enableEdit = false;

    @Inject
    public DappFavoriteAdapter() {
        super(R.layout.item_dapp_favorite, new ArrayList<>());
    }


    private OnSwipeListener onSwipeListener;

    public interface OnSwipeListener {
        void onSwipe(@SwipeState int state, DappBean item, int position);
    }

    public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
        this.onSwipeListener = onSwipeListener;
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, DappBean item) {
        EasySwipeMenuLayout swipeMenuLayout = helper.getView(R.id.swipeMenuLayout);
        swipeMenuLayout.setOnSwipeListener(state -> {
            if (onSwipeListener != null) {
                onSwipeListener.onSwipe(state, item, helper.getAdapterPosition());
            }
            LogManage.d(state == SwipeState.LEFTOPEN ? "SwipeState.LEFTOPEN" : "SwipeState.RIGHTOPEN");
        });
        helper.setText(R.id.tvDappName, item.getName());
        helper.setText(R.id.tvDappInfo, item.getDiscription());
        helper.setGone(R.id.ivDappDelete, enableEdit);
        helper.setGone(R.id.ivDappDrag, enableEdit);

        helper.addOnClickListener(R.id.ivDappDelete, R.id.content, R.id.tvDelete);

        helper.setCircleImageUrl(R.id.ivDapp, item.getIcon());

    }

    public boolean getEnableEdit() {
        return enableEdit;
    }

    public void switchEnableEdit() {
        enableEdit = !enableEdit;
        notifyDataSetChanged();
    }
}
