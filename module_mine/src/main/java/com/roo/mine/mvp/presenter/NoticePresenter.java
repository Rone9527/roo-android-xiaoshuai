package com.roo.mine.mvp.presenter;



import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.core.domain.mine.MessageSystem;
import com.core.domain.mine.MessageTrade;
import com.core.domain.mine.TransferRecordBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.utils.MessageSystemManager;
import com.roo.core.utils.utils.MessageTradeManager;
import com.roo.mine.mvp.contract.NoticeContract;
import com.roo.mine.mvp.ui.adapter.NoticeAdapter;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class NoticePresenter extends BasePresenter<NoticeContract.Model, NoticeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    NoticeAdapter mAdapter;

    @Inject
    public NoticePresenter(NoticeContract.Model model, NoticeContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void checkNotice() {
        for (MessageTrade item : MessageTradeManager.getInstance().getNoticeList()) {
            if (item.getDataBean() == null) {
                getTxDetail(item, false);
            }
        }
        for (MessageSystem item : MessageSystemManager.getInstance().getNoticeList()) {
            getSysDetail(item, false);
        }
    }

    public void getTxDetail(MessageTrade item, boolean index) {
        mModel.getTxDetail(item.getChainCode(), item.getTxId(), item.getIndex())
                .compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<TransferRecordBean.DataBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<TransferRecordBean.DataBean> t) {
                        item.setDataBean(t.getData());
                        MessageTradeManager.getInstance().replaceNotice(item);
                        if (index) {
                            mRootView.gotoTransferDetail(item);
                        }
                    }
                });
    }

    public void getSysDetail(MessageSystem item, boolean index) {
        mModel.getSystemMessage(item.getMsgId()).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<MessageSystem.DataBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<MessageSystem.DataBean> t) {
                        item.setDataBean(t.getData());

                        if (index) {
                            mRootView.gotoMessageSystemDetail(item);
                        }
                    }
                });
    }

}