package com.roo.mine.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.billy.cc.core.component.CC;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.core.domain.mine.MessageSystem;
import com.core.domain.mine.MessageTrade;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.roo.core.app.component.Wallet;
import com.roo.core.app.constants.Constants;
import com.roo.core.utils.ClickUtil;
import com.roo.core.utils.ColorUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.MessageSystemManager;
import com.roo.core.utils.utils.MessageTradeManager;
import com.roo.mine.R;
import com.roo.mine.di.component.DaggerNoticeComponent;
import com.roo.mine.mvp.contract.NoticeContract;
import com.roo.mine.mvp.presenter.NoticePresenter;
import com.roo.mine.mvp.ui.adapter.NoticeAdapter;
import com.roo.router.Router;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 交易通知
 */
public class NoticeActivity extends BaseActivity<NoticePresenter> implements NoticeContract.View {

    public static final int TYPE_TRADE = 1;
    public static final int TYPE_SYSTEM = 10;

    private int noticeType = TYPE_TRADE;

    @Inject
    NoticeAdapter mAdapter;
    private ImageView ivReset;
    private View redDotTrade;
    private View redDotSys;
    private TextView tvNoticeTrade;
    private TextView tvNoticeMessage;

    public static void start(Context context) {
        Router.newIntent(context)
                .to(NoticeActivity.class)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNoticeComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_notice;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ViewHelper.initTitleBar("", this).setVisibility(View.GONE);
        int barHeight = DeviceUtils.getStatuBarHeight(this);
        findViewById(R.id.layoutRoot).setPadding(0, barHeight, 0, 0);
        redDotTrade = findViewById(R.id.redDot);
        redDotSys = findViewById(R.id.redDotSys);

        RxView.clicks(findViewById(R.id.ivBack)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> finish());

        tvNoticeMessage = findViewById(R.id.tvNoticeMessage);
        tvNoticeTrade = findViewById(R.id.tvNoticeTrade);

        ivReset = findViewById(R.id.ivReset);

        RxView.clicks(tvNoticeMessage).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    onTabSelected(TYPE_SYSTEM);
                });
        RxView.clicks(tvNoticeTrade).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    onTabSelected(TYPE_TRADE);
                });

        ViewHelper.initRefreshLayout(this);
        RecyclerView recyclerView = ViewHelper.initRecyclerView(mAdapter, this);

        RxView.clicks(ivReset).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    MessageSystemManager.getInstance().resetNoticeRead();
                    MessageTradeManager.getInstance().resetNoticeRead();
                    setResetIcon();
                });


        String emptyStr = getString(R.string.no_data_message);
        ViewHelper.initEmptyView(mAdapter, recyclerView, emptyStr,
                R.drawable.ic_common_empty_data_message);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (ClickUtil.clickInFronzen()) {
                return;
            }

            MultiItemEntity data = mAdapter.getItem(position);
            if (data instanceof MessageTrade) {
                MessageTrade item = (MessageTrade) data;
                if (item.getDataBean() == null) {
                    mPresenter.getTxDetail(item, true);
                } else {
                    gotoTransferDetail(item);
                    MessageSystemManager.getInstance().setNoticeRead(item.getId());
                }
            } else if (data instanceof MessageSystem) {
                MessageSystem item = (MessageSystem) data;
                mPresenter.getSysDetail(item, true);
            }
        });
    }

    private void onTabSelected(int noticeType) {
        if (this.noticeType == noticeType) {
            return;
        }
        this.noticeType = noticeType;
        tvNoticeTrade.setSelected(noticeType == TYPE_TRADE);
        tvNoticeMessage.setSelected(noticeType == TYPE_SYSTEM);

        setResetIcon();
    }

    private void setResetIcon() {
        Glide.with(this).asBitmap().load(R.drawable.ic_mine_notice_reset)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        if (MessageSystemManager.getInstance().hasUnReadNotice()
                                || MessageTradeManager.getInstance().hasUnReadNotice()) {
                            ivReset.setImageBitmap(resource);
                        } else {
                            ivReset.setImageBitmap(ColorUtils.toGrayScale(NoticeActivity.this, resource));
                        }
                    }
                });

        if (MessageSystemManager.getInstance().hasUnReadNotice()) {
            redDotSys.setVisibility(View.VISIBLE);
        } else {
            redDotSys.setVisibility(View.GONE);
        }
        if (MessageTradeManager.getInstance().hasUnReadNotice()) {
            redDotTrade.setVisibility(View.VISIBLE);
        } else {
            redDotTrade.setVisibility(View.GONE);
        }

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setResetIcon();
    }

    @Override
    public void loadData() {
        if (noticeType == NoticeActivity.TYPE_TRADE) {
            List<MessageTrade> list = MessageTradeManager.getInstance().getNoticeList();
            for (MessageTrade item : list) {
                item.setItemType(TYPE_TRADE);
            }
            mAdapter.setNewData(new ArrayList<>(list));
        } else {
            List<MessageSystem> list = MessageSystemManager.getInstance().getNoticeList();
            for (MessageSystem item : list) {
                item.setItemType(TYPE_SYSTEM);
            }
            mAdapter.setNewData(new ArrayList<>(list));
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void gotoTransferDetail(MessageTrade item) {
        MessageTradeManager.getInstance().setNoticeRead(item.getId());
        CC.obtainBuilder(Wallet.NAME)
                .setContext(this)
                .addParam(Wallet.Param.TransferRecordDataBean, item.getDataBean())
                .addParam(Constants.KEY_CHAIN_CODE, item.getChainCode())
                .addParam(Constants.KEY_FR, true)
                .setActionName(Wallet.Action.TransferDetailActivity)
                .build().call();
    }

    @Override
    public void gotoMessageSystemDetail(MessageSystem item) {
        MessageSystemManager.getInstance().setNoticeRead(item.getMsgId());

        NoticeDetailActivity.start(this, item.getDataBean());
    }
}