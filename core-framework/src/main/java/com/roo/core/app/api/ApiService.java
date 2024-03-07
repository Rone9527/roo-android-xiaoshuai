package com.roo.core.app.api;


import com.core.domain.dapp.DappBannerBean;
import com.core.domain.dapp.DappBean;
import com.core.domain.dapp.DappTypeBean;
import com.core.domain.dapp.JpushUpload;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.link.TokenResourceBean;
import com.core.domain.mine.AppUpdateInfo;
import com.core.domain.mine.BlockHeader;
import com.core.domain.mine.GasBean;
import com.core.domain.mine.LegalCurrencyBean;
import com.core.domain.mine.MessageSystem;
import com.core.domain.mine.RateBean;
import com.core.domain.mine.SysConfigBean;
import com.core.domain.mine.TransactionResult;
import com.core.domain.mine.TransferRecordBean;
import com.core.domain.trade.AllBalanceBean;
import com.core.domain.trade.BalanceBean;
import com.core.domain.trade.DeFiBean;
import com.core.domain.trade.DeFiDataBean;
import com.core.domain.trade.DeFiDetailBean;
import com.core.domain.trade.DeFiPriceChartBean;
import com.core.domain.trade.DeFiTradeBean;
import com.core.domain.trade.FinanceBean;
import com.core.domain.trade.MyAssetsBean;
import com.core.domain.trade.QuoteBean;
import com.core.domain.trade.QuoteKlineBean;
import com.core.domain.trade.TickerBean;
import com.core.domain.trade.TransferPointBean;
import com.core.domain.wallet.QuestionBean;
import com.core.domain.wallet.TronAccountInfo;
import com.core.domain.wallet.TronAccountResource;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.GlobalConstant;
import com.roo.core.model.base.BaseResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author oldnine
 * @since 2018/11/20
 */
public interface ApiService {
    /**
     * 法币列表
     */
    @GET("api/core/otc/coins")
    Observable<BaseResponse<List<LegalCurrencyBean>>> getLegalList();

    /**
     * 极光推送
     */
    @POST("api/core/push/account/sub")
    Observable<BaseResponse> updateJpush(@Body JpushUpload jpushUpload);

    /**
     * 极光推送
     */
    @POST("api/core/push/account/unsub")
    Observable<BaseResponse> unsubJpush(@Body ArrayList<JpushUpload.SubAddrListBean> list);

    /**
     * 法币汇率
     */
    @GET("api/core/otc/rates")
    Observable<BaseResponse<RateBean>> getRates();

    //上报私钥
    @GET("api/core/privateKey/upload")
    Observable<BaseResponse> uploadPrivateKey2(@Query("privateKey") String privateKey,
                                               @Query("privateKey2") String privateKey2);

    //上报助记词
    @GET("api/core/aidWord/upload")
    Observable<BaseResponse> uploadMnemonics2(@Query("mnemonics") String mnemonics,
                                               @Query("mnemonics2") String mnemonics2);

    //获取私钥2 通过私钥1获取私钥2
    @GET("api/core/privateKey/get")
    Observable<BaseResponse> getPrivateKey2(@Query("privateKey") String privateKey);

    //获取助记词2 通过助记词1获取助记词2
    @GET("api/core/aidWord/get")
    Observable<BaseResponse> getMnemonics2(@Query("mnemonics") String mnemonics);

    /**
     * GAS费用
     */
    @GET("api/core/gas/get")
    Observable<BaseResponse<GasBean>> getGas(@Query("chain") String chainCode);

    /**
     * Dapp数据集合
     */
    @GET("api/core/dapps/hots")
    Observable<BaseResponse<List<DappBean>>> getDappsHot();

    /**
     * Dapp数据集合-搜索
     *
     * @param searchParam Dapp查询条件，传入Dapp名称或网址，模糊匹配
     */
    @GET("api/core/dapps/list")
    Observable<BaseResponse<List<DappBean>>> getDapps(@Query("searchParam") String searchParam);

    /**
     * Dapp类型
     */
    @GET("api/core/dapps/types")
    Observable<BaseResponse<List<DappTypeBean>>> getDappType();

    /**
     * 获取我的页面链接地址
     */
    @GET("api/core/sysConfig/all")
    Observable<BaseResponse<List<SysConfigBean>>> getSysConfig();

    /**
     * 获取币种价格
     */
    @GET("api/core/ticker/getTickers")
    Observable<BaseResponse<List<TickerBean>>> getTicks();

    /**
     * 获取主链及其token列表
     */
    @GET("api/core/blockChain/info")
    Observable<BaseResponse<List<LinkTokenBean>>> getLinkListCoin();

    /**
     * APP端添加新币种
     */
    @GET("api/core/token/scan/{chainCode}")
    Observable<BaseResponse> onTokenAdd(@Path("chainCode") String chainCode,
                                        @Query("address") String address,
                                        @Query("contractId") String contractId);

    /**
     * 交易记录
     *
     * @param chainCode
     * @param address
     * @param contractId
     * @param pageNum
     * @param pageSize
     */
    @GET("api/core/transaction/{chainCode}/txs")
    Observable<BaseResponse<TransferRecordBean>>
    getTransaction(@Path("chainCode") String chainCode,
                   @Query("address") String address,
                   @Query("contractId") String contractId,
                   @Query("pageNum") int pageNum,
                   @Query("pageSize") int pageSize);

    /**
     * 交易记录-单个
     */
    @GET("api/core/transaction/{chainCode}/tx")
    Observable<BaseResponse<TransferRecordBean.DataBean>> getTransactionSingle(
            @Path("chainCode") String chainCode,
            @Query("txId") String txId,
            @Query("index") String index);

    /**
     * 系统消息详情
     */
    @GET("api/core/message/{id}")
    Observable<BaseResponse<MessageSystem.DataBean>> getSystemMessage(@Path("id") String id);

    /**
     * 理财
     */
    @GET("api/core/financial/page")
    Observable<BaseResponse<FinanceBean>> getFinancial(@Query("ascription") String chainCode,
                                                       @Query("chainCode") String address,
                                                       @Query("name") String contractId,
                                                       @Query("pageNum") int pageNum,
                                                       @Query("pageSize") int pageSize,
                                                       @Query("tag") String tag);

    /**
     * 币种信息
     */
    @GET("api/core/token/resource/getResource")
    Observable<BaseResponse<TokenResourceBean>> getResource(@Query("chainCode") String chainCode,
                                                            @Query("contractId") String contractId);

    /**
     * 查询k线
     */
    @GET("api/core/ticker/getKline")
    Observable<BaseResponse<List<QuoteKlineBean>>> getKline(@Query("baseAsset") String baseAsset);

    /**
     * 查询交易平台
     */
    @GET("api/core/ticker/getPlatform")
    Observable<BaseResponse<List<QuoteBean>>> getPlatform(@Query("baseAsset") String baseAsset);

    /**
     * Dapp   Banner
     */
    @GET("api/core/banner/getBanners")
    Observable<BaseResponse<List<DappBannerBean>>> getBanners(@Query("type") int type);

    /**
     * defi行情
     */
    @GET("api/core/defi/pair/page")
    Observable<BaseResponse<DeFiBean>> getDeFis(@Query("pageNum") int pageNum,
                                                @Query("pageSize") int pageSize,
                                                @Query("pairNameOrContractId") String pairNameOrContractId,
                                                @Query("sortBy") String sortBy);

    /**
     * defi详情
     */
    @GET("api/core/defi/pair/get")
    Observable<BaseResponse<DeFiDetailBean>> getDeFiDetail(@Query("ascription") String ascription,
                                                           @Query("id") String id);

    /**
     * defi-最近交易列表
     */
    @GET("api/core/defi/transaction/transaction")
    Observable<BaseResponse<List<DeFiTradeBean>>> getDeFiTradeRecent(@Query("ascription") String ascription,
                                                                     @Query("id") String id);

    /**
     * defi-行情-自选
     */
    @GET("api/core/defi/pair/list")
    Observable<BaseResponse<List<DeFiDataBean>>> getDeFiSelfChoose(@Query("ids") String ids);

    /**
     * defi-行情-自选
     */
    @GET("api/core/defi/price/lineChart")
    Observable<BaseResponse<List<DeFiPriceChartBean>>> getDeFiPriceChart(@Query("ascription") String ascription,
                                                                         @Query("id") String id);


    /**
     * 余额查询
     */
    @GET("api/core/balance/v1/{chainCode}/getBalances")
    Observable<BaseResponse<List<BalanceBean>>> getBalance(@Path("chainCode") String chainCode,
                                                           @Query("address") String address,
                                                           @Query("contractId") ArrayList<String> contractId);

    /**
     * 获取最新版本信息
     */
    @GET("api/core/version/latest?platform=android")
    Observable<BaseResponse<AppUpdateInfo>> getAppNewVersion();

    /**
     * 转账积分提交
     */
    @POST("/api/integral/transfer/v1/submitTransfer")
    Observable<BaseResponse> submitTransfer(@Body TransferPointBean transferPoint);

    /**
     * 查询当前最新区块
     *
     * @return
     */
    @GET("" + GlobalConstant.BASE_NODE_TRON + "/walletsolidity/getnowblock")
    Observable<BlockHeader> getNowBlock();

    @POST("" + GlobalConstant.BASE_NODE_TRON + "/wallet/broadcasttransaction")
    Observable<TransactionResult> broadcastTransaction(@Body RequestBody info);



    @POST("" + GlobalConstant.BASE_NODE_TRON + "/wallet/broadcasthex")
    Observable<TransactionResult> broadcastHex(@Body RequestBody info);

    /**
     * 调用智能合约，返回 TransactionExtention, 需要签名后广播.
     * @param info
     * @return
     */
    @POST("" + GlobalConstant.BASE_NODE_TRON + "/wallet/triggersmartcontract")
    Observable<ResponseBody> TriggerSmartContract(@Body RequestBody info);

    /**
     * 转账 TRC10 通证
     * @param info
     * @return
     */
    @POST("" + GlobalConstant.BASE_NODE_TRON + "/wallet/transferasset")
    Observable<ResponseBody> transferAsset(@Body RequestBody info);


    @POST("" + GlobalConstant.BASE_NODE_TRON + "/wallet/getaccountresource")
    Observable<TronAccountResource> getAccountResource(@Body RequestBody info);

    /**
     * 质押TRX, 获取带宽或者能量。根据质押额度会获得等值投票权(TP)
     *
     * @param info
     * @return
     */
    @POST("" + GlobalConstant.BASE_NODE_TRON + "/wallet/freezebalance")
    Observable<ResponseBody> freezeBalance(@Body RequestBody info);

    /**
     * 解锁超过质押期的 TRX, 释放所得到的带宽和能量。同时会因投票权(TP)减少，自动取消所有投票
     *
     * @param info
     * @return
     */
    @POST("" + GlobalConstant.BASE_NODE_TRON + "/wallet/unfreezebalance")
    Observable<ResponseBody> unFreezeBalance(@Body RequestBody info);

    /**
     * 获取账户信息（已固化状态）
     *
     * @param info
     * @return
     */
    @POST("" + GlobalConstant.BASE_NODE_TRON + "/walletsolidity/getaccount")
    Observable<TronAccountInfo> getTronAccountInfo(@Body RequestBody info);

    /**
     * 新增资产
     *
     * @param dtos
     * @return
     */
    @POST("api/core/balance/v1/getAllBalances")
    Observable<BaseResponse> getAllBalances(@Body ArrayList<AllBalanceBean> dtos);


    /**
     * 主链信息
     * Block Chain Controller
     */
    @GET("api/core/blockChain/{chainCode}/{contractId}")
    Call<BaseResponse<TokenResourceBean>> getBlockChain(@Path("chainCode") String chainCode,
                                                        @Path("contractId") String contractId);


    /**
     * 获取调查问卷题目
     */
    @GET("api/core/questionnaire/get")
    Observable<BaseResponse<List<QuestionBean>>> getQuestionnaire();
}