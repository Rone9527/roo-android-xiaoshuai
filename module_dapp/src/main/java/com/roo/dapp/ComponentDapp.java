package com.roo.dapp;

import android.text.TextUtils;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.core.domain.dapp.DappBean;
import com.core.domain.link.LinkTokenBean;
import com.roo.core.app.component.Dapp;
import com.roo.dapp.mvp.ui.activity.DappActivity;
import com.roo.dapp.mvp.ui.activity.DappHostActivity;
import com.roo.dapp.mvp.ui.fragment.DappBrowserFragmentNew;
import com.roo.dapp.mvp.ui.fragment.DappFragment;

public class ComponentDapp implements IComponent {

    @Override
    public String getName() {
        return Dapp.NAME;
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName) {
            case Dapp.Action.DappActivity: {
                DappActivity.start(cc.getContext());
            }
            return false;
            case Dapp.Action.DappFragment: {
                DappFragment webFragment = DappFragment.newInstance();
                CC.sendCCResult(cc.getCallId(), CCResult.successWithNoKey(webFragment));
            }
            return false;

            case Dapp.Action.DappBrowserFragment: {

                LinkTokenBean.TokensBean tokensBean = cc.getParamItem(Dapp.Param.TOKENS_BEAN);
                String url = cc.getParamItem(Dapp.Param.KEY_URL);
                DappBrowserFragmentNew browserFragment;
                if (TextUtils.isEmpty(url)) {
                    DappBean dappBean = cc.getParamItem(Dapp.Param.DAPP_BEAN);
                    browserFragment = DappBrowserFragmentNew.newInstance(tokensBean, dappBean, false);
                } else {
                    browserFragment = DappBrowserFragmentNew.newInstance(tokensBean, url, false);
                }
                CC.sendCCResult(cc.getCallId(), CCResult.successWithNoKey(browserFragment));
            }
            return false;

            case Dapp.Action.DappHostActivity: {

                LinkTokenBean.TokensBean tokensBean = cc.getParamItem(Dapp.Param.TOKENS_BEAN);
                String url = cc.getParamItem(Dapp.Param.KEY_URL);
                if (TextUtils.isEmpty(url)) {
                    DappBean dappBean = cc.getParamItem(Dapp.Param.DAPP_BEAN);
                    DappHostActivity.start(cc.getContext(), tokensBean, dappBean);
                } else {
                    DappHostActivity.start(cc.getContext(), tokensBean, url);
                }
                CC.sendCCResult(cc.getCallId(), CCResult.success());
            }
            return false;

            default:
                CC.sendCCResult(cc.getCallId(), CCResult.error("没有找到对应的Action"));
                return false;
        }
    }

}
