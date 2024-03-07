package com.roo.dapp.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.aries.ui.view.title.TitleBarView;
import com.billy.cc.core.component.CC;
import com.core.domain.dapp.DappBean;
import com.core.domain.link.LinkTokenBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.component.Wallet;
import com.roo.core.app.constants.Constants;
import com.roo.core.daoManagers.DappDaoManager;
import com.roo.core.model.UserWallet;
import com.roo.core.ui.dialog.AddLinkHintDialog;
import com.roo.core.ui.dialog.DappTipDialog;
import com.roo.core.ui.widget.DividerItemDecoration;
import com.roo.core.utils.ClickUtil;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.core.utils.utils.TimeUtils;
import com.roo.dapp.R;
import com.roo.dapp.di.component.DaggerDappFavoriteComponent;
import com.roo.dapp.mvp.contract.DappFavoriteContract;
import com.roo.dapp.mvp.presenter.DappFavoritePresenter;
import com.roo.dapp.mvp.ui.adapter.DappFavoriteAdapter;
import com.roo.dapp.mvp.ui.dialog.DappFavoriteModifyHintDialog;
import com.roo.router.Router;
import com.roo.view.swipe.EasySwipeMenuLayout;
import com.roo.view.swipe.SwipeState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class DappFavoriteActivity extends BaseActivity<DappFavoritePresenter> implements DappFavoriteContract.View {
    private boolean changedSort = false;
    private boolean changedDel = false;
    @Inject
    DappFavoriteAdapter mAdapter;
    private final List<DappBean> delList = new ArrayList<>();
    private TitleBarView titleBarView;

    public static void start(Context context, int selectType) {
        Router.newIntent(context)
                .to(DappFavoriteActivity.class)
                .putInt(Constants.KEY_DEFAULT, selectType)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDappFavoriteComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.default_rv_refresh_loadmore_titlebar;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        titleBarView = ViewHelper.initTitleBar(getString(R.string.favorites), this);
        titleBarView.getLinearLayout(Gravity.START).removeAllViews();
        titleBarView.addLeftAction(titleBarView.
                new ImageAction(R.drawable.ic_common_back_black, v -> onBackPressed()));

        titleBarView.setOnRightTextClickListener(v -> {
            mAdapter.switchEnableEdit();
            setRightText(titleBarView);
            if (!mAdapter.getEnableEdit()) {//保存

                if (changedSort) {
                    for (DappBean item : mAdapter.getData()) {
                        DappDaoManager.getDappDao().update(item);
                    }
                    changedSort = false;
                }
                postDelete();
            }
        });

        setRightText(titleBarView);

        RecyclerView mRecyclerView = ViewHelper.initRecyclerView(mAdapter, this);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        ViewHelper.initEmptyView(mAdapter, mRecyclerView);
        ViewHelper.initRefreshLayout(this);
        int selectType = getIntent().getIntExtra(Constants.KEY_DEFAULT, Constants.FAVORITE);
        mPresenter.getDappTop(selectType);



//        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(mAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        // 开启拖拽
        mAdapter.enableDragItem(itemTouchHelper, R.id.ivDappDrag, false);

//        mAdapter.setOnItemDragListener(onItemDragListener);

        mAdapter.setOnSwipeListener((state, item, position) -> {
            if (state == SwipeState.RIGHTOPEN)
                if (mAdapter.getEnableEdit()) {
                    for (int i = 0; i < mAdapter.getData().size(); i++) {
                        EasySwipeMenuLayout swipeMenuLayout = (EasySwipeMenuLayout) mAdapter
                                .getViewByPosition(mRecyclerView, i, R.id.swipeMenuLayout);
                        if (swipeMenuLayout != null) {
                            if (position != i) {
                                swipeMenuLayout.handlerSwipeMenu(SwipeState.NULL);
                            }
                        }
                    }
                } else {
                    delList.add(mAdapter.getItem(position));
                    mAdapter.remove(position);
                }
        });

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (ClickUtil.clickInFronzen(200)) {
                return;
            }
            DappBean item = mAdapter.getItem(position);
            int id = view.getId();
            if (id == R.id.content) {
                if (mAdapter.getEnableEdit()) {
                    return;
                }
                onItemClick(item);
            } else if (id == R.id.tvDelete) {
                delList.add(item);
                changedDel = true;
                mAdapter.remove(position);
            } else if (id == R.id.ivDappDelete) {
                EasySwipeMenuLayout swipeMenuLayout = (EasySwipeMenuLayout) mAdapter
                        .getViewByPosition(mRecyclerView, position, R.id.swipeMenuLayout);
                if (swipeMenuLayout != null) {
                    swipeMenuLayout.handlerSwipeMenu(SwipeState.RIGHTOPEN);
                }
            }
        });
    }

    public class MyItemTouchCallback extends ItemTouchHelper.Callback {

        private final DappFavoriteAdapter adapter;

        public MyItemTouchCallback(DappFavoriteAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlag;
            int swipeFlag;
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                dragFlag = ItemTouchHelper.DOWN | ItemTouchHelper.UP
                        | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
                swipeFlag = 0;
            } else {
                dragFlag = ItemTouchHelper.DOWN | ItemTouchHelper.UP;
                swipeFlag = ItemTouchHelper.END;
            }
            return makeMovementFlags(dragFlag, swipeFlag);

        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            changedSort = true;
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    //注意：这里不仅要交换位置，还要交换时间，因为收藏是按照时间先后顺序收藏的
                    long dataBaseCreateTime = adapter.getData().get(i).getDataBaseCreateTime();
                    long dataBaseCreateTime1 = adapter.getData().get(i+1).getDataBaseCreateTime();
                    adapter.getData().get(i).setDataBaseCreateTime(dataBaseCreateTime1);
                    adapter.getData().get(i+1).setDataBaseCreateTime(dataBaseCreateTime);

                    Collections.swap(adapter.getData(), i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    long dataBaseCreateTime = adapter.getData().get(i).getDataBaseCreateTime();
                    long dataBaseCreateTime1 = adapter.getData().get(i-1).getDataBaseCreateTime();
                    adapter.getData().get(i).setDataBaseCreateTime(dataBaseCreateTime1);
                    adapter.getData().get(i-1).setDataBaseCreateTime(dataBaseCreateTime);
                    Collections.swap(adapter.getData(), i, i - 1);
                }
            }
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            LogManage.e("从位置：" + fromPosition + "移动到：" + toPosition);

            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            if (direction == ItemTouchHelper.END) {
                adapter.getData().remove(position);
                adapter.notifyItemRemoved(position);
            }

        }

    }

    private void postDelete() {
        if (!Kits.Empty.check(delList)) {
            for (DappBean del : delList) {
                DappDaoManager.delete(del.getName(), del.getDataBaseType());
            }
            delList.clear();
            changedDel = false;
        }
    }

    @Override
    public void onBackPressed() {
        if (changedSort || changedDel) {
            DappFavoriteModifyHintDialog.newInstance()
                    .setOnClickListener(super::onBackPressed)
                    .show(getSupportFragmentManager(), getClass().getSimpleName());
        } else if (mAdapter.getEnableEdit()) {
            mAdapter.switchEnableEdit();
            setRightText(titleBarView);
        } else {
            postDelete();
            super.onBackPressed();
        }
    }

    private void onItemClick(DappBean item) {
        UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(this);
        if (null == userWallet) {
            ToastUtils.show(getString(R.string.tip_no_wallet_here));
            return;
        }
        ArrayList<LinkTokenBean.TokensBean> tokenList = userWallet.getTokenList();
        if (!userWallet.getListMainChainCode().contains(item.getChain())) {
            AddLinkHintDialog.newInstance(item.getChain()).setOnClickListener(() -> {
                CC.obtainBuilder(Wallet.NAME)
                        .setContext(this)
                        .setActionName(Wallet.Action.AssetSelectActivity)
                        .build().call();
            }).show(getSupportFragmentManager(), getClass().getSimpleName());
            return;
        }
        for (LinkTokenBean.TokensBean tokensBean : tokenList) {
            if (tokensBean.getChainCode().equalsIgnoreCase(item.getChain())) {
                if (SharedPref.getBoolean(Constants.KEY_SHOW_DAPP_TIPS, false)) {
                    DappHostActivity.start(this, tokensBean, item);
                } else {
                    DappTipDialog.newInstance(item.getName(), item.getIcon())
                            .setOnClickListener(() ->
                                    DappHostActivity.start(DappFavoriteActivity.this, tokensBean, item))
                            .show(getSupportFragmentManager(), getClass().getSimpleName());
                }
                break;
            }
        }

    }

    private void setRightText(TitleBarView titleBarView) {
        if (mAdapter.getEnableEdit()) {
            titleBarView.setRightText(R.string.save);
            titleBarView.setRightTextColor(ContextCompat.getColor(
                    DappFavoriteActivity.this, R.color.blue_color));
        } else {
            titleBarView.setRightText(R.string.edit);
            titleBarView.setRightTextColor(ContextCompat.getColor(
                    DappFavoriteActivity.this, R.color.white_color_assist_2));
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }
}