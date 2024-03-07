package com.roo.home;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.core.domain.mine.TransferRecordBean;
import com.roo.core.app.component.Wallet;
import com.roo.core.app.constants.Constants;
import com.roo.core.utils.LogManage;
import com.roo.home.mvp.ui.activity.AssetSelectActivity;
import com.roo.home.mvp.ui.activity.TransferDetailActivity;
import com.roo.home.mvp.ui.activity.WalletCreateActivity;
import com.roo.home.mvp.ui.activity.WalletCreateNormalActivity;
import com.roo.home.mvp.ui.activity.WalletListActivity;
import com.roo.home.mvp.ui.activity.WalletManagerActivity;
import com.roo.home.mvp.ui.fragment.WalletFragment;

public class ComponentWallet implements IComponent {

    @Override
    public String getName() {
        return Wallet.NAME;
    }
    /**
     * 组件被调用时的入口
     * 要确保每个逻辑分支都会调用到CC.sendCCResult，
     * 包括try-catch,if-else,switch-case-default,startActivity
     * @param cc 组件调用对象，可从此对象中获取相关信息
     * @return true:将异步调用CC.sendCCResult(...),用于异步实现相关功能，例如：文件加载、网络请求等
     *          false:会同步调用CC.sendCCResult(...),即在onCall方法return之前调用，否则将被视为不合法的实现
     */
    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName) {

            case Wallet.Action.WalletListActivity:
                WalletListActivity.start(cc.getContext());

                return false;
            case Wallet.Action.WalletManagerActivity:
                String remark = cc.getParamItem(Constants.KEY_DEFAULT);
                WalletManagerActivity.start(cc.getContext(), remark);
                return false;
            case Wallet.Action.AssetSelectActivity:
                AssetSelectActivity.start(cc.getContext());
                return false;
            case Wallet.Action.WalletCreateActivity:
                WalletCreateActivity.start(cc.getContext());
                return false;
            case Wallet.Action.TransferDetailActivity:
                TransferRecordBean.DataBean recordBean = cc.getParamItem(Wallet.Param.TransferRecordDataBean);
                String chainCode = cc.getParamItem(Constants.KEY_CHAIN_CODE);
                boolean isSideIn = cc.getParamItem(Constants.KEY_FR, false);

                TransferDetailActivity.start(cc.getContext(), recordBean, chainCode,isSideIn);
                return false;
            case Wallet.Action.WalletCreateNormalActivity:
                WalletCreateNormalActivity.start(cc.getContext(), true);
                return false;
            case Wallet.Action.WalletFragment:
                WalletFragment walletFragment = WalletFragment.newInstance();
                CC.sendCCResult(cc.getCallId(), CCResult.successWithNoKey(walletFragment));
                return false;
        }
        return false;
    }

}
