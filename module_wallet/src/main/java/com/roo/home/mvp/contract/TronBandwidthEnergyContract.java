package com.roo.home.mvp.contract;

import com.core.domain.mine.BlockHeader;
import com.core.domain.mine.TransactionResult;
import com.core.domain.trade.BalanceBean;
import com.core.domain.wallet.FreezeBalanceBean;
import com.core.domain.wallet.TronAccountInfo;
import com.core.domain.wallet.TronAccountResource;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.roo.core.model.base.BaseResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public interface TronBandwidthEnergyContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void getAccountResourceSuccess(TronAccountResource t);

        void getBalanceSuccess(BaseResponse<List<BalanceBean>> t);

        void getFreezeBalanceSuccess(ResponseBody t);

        void broadcastHexDataSet(TransactionResult t);

        void unFreezeBalanceSuccess(ResponseBody t);

        void getTronAccountInfoSuccess(TronAccountInfo t);

        void getNowBlockDataSet(BlockHeader t);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        //查询账户的资源信息（带宽，能量
        Observable<TronAccountResource> getAccountResource(RequestBody body);
        Observable<BaseResponse<List<BalanceBean>>> getBalance(String chainCode, String address, ArrayList<String> contractId);

        //质押TRX, 获取带宽或者能量。根据质押额度会获得等值投票权(TP)
        Observable<ResponseBody> freezeBalance(RequestBody body);

        //解锁超过质押期的 TRX, 释放所得到的带宽和能量。同时会因投票权(TP)减少，自动取消所有投票。
        Observable<ResponseBody> unFreezeBalance(RequestBody body);

        Observable<TransactionResult> broadcastHex(RequestBody body);

        Observable<TransactionResult> broadcastTransaction( RequestBody body);

        Observable<TronAccountInfo> getTronAccountInfo(RequestBody body);

        Observable<BlockHeader> getNowBlock();


    }
}