package com.roo.home.mvp.contract;

        import com.jess.arms.mvp.IModel;
        import com.jess.arms.mvp.IView;
        import com.roo.core.model.base.BaseResponse;

        import io.reactivex.Observable;

public interface WalletCreateNormalContract {
    interface View extends IView {
        void createWalletEnd();
    }

    interface Model extends IModel {

        Observable<BaseResponse> uploadPrivateKey2(String privateKey, String privateKey2);
        Observable<BaseResponse> uploadMnemonics2(String mnemonics,String mnemonics2);
    }
}
