package com.roo.home.mvp.contract;

import com.core.domain.mine.BlockHeader;
import com.core.domain.mine.TransactionResult;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public interface TransferTronContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void getNowBlockDataSet(BlockHeader t);

        void broadcastTransactionSuccess(TransactionResult t);

        void getTransactionSuccess(ResponseBody t);

        void createTransactionTrc10Success(ResponseBody t);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BlockHeader> getNowBlock();

        Observable<TransactionResult> broadcastTransaction(RequestBody body);

        Observable<ResponseBody> createTransactionTre20(RequestBody body);

        Observable<ResponseBody> createTransactionTre10(RequestBody body);
    }
}