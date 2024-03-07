package com.roo.dapp.mvp.ui.activity;

import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;

import com.core.domain.dapp.DappBean;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.manager.ChainCode;
import com.roo.core.app.constants.Constants;
import com.roo.core.ui.base.IBaseSingleFragmentActivity;
import com.roo.dapp.mvp.ui.fragment.DappBrowserFragmentNew;
import com.roo.dapp.mvp.ui.fragment.DappBrowserTronFragment;
import com.roo.router.Router;

public class DappHostActivity extends IBaseSingleFragmentActivity {

    private LinkTokenBean.TokensBean tokensBean;

    public static void start(Context context, LinkTokenBean.TokensBean tokensBean, DappBean dappBean) {
        Router.newIntent(context)
                .to(DappHostActivity.class)
                .putParcelable(Constants.KEY_DEFAULT, tokensBean)
                .putParcelable(Constants.KEY_MSG, dappBean)
                .launch();
    }

    public static void start(Context context, LinkTokenBean.TokensBean tokensBean, String url) {
        Router.newIntent(context)
                .to(DappHostActivity.class)
                .putParcelable(Constants.KEY_DEFAULT, tokensBean)
                .putString(Constants.KEY_URL, url)
                .launch();
    }

    @Override
    protected Fragment createFragment() {
        tokensBean = getIntent().getParcelableExtra(Constants.KEY_DEFAULT);
        String url = getIntent().getStringExtra(Constants.KEY_URL);
        String chainCode = tokensBean.getChainCode();
        if (chainCode.equalsIgnoreCase(ChainCode.TRON)) {

            if (TextUtils.isEmpty(url)) {
                DappBean dappBean = getIntent().getParcelableExtra(Constants.KEY_MSG);
                return DappBrowserTronFragment.newInstance(tokensBean, dappBean, true);
            } else {
                return DappBrowserTronFragment.newInstance(tokensBean, url, true);
            }
        } else {
            if (TextUtils.isEmpty(url)) {
                DappBean dappBean = getIntent().getParcelableExtra(Constants.KEY_MSG);
                return DappBrowserFragmentNew.newInstance(tokensBean, dappBean, true);
            } else {
                return DappBrowserFragmentNew.newInstance(tokensBean, url, true);
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        String chainCode = tokensBean.getChainCode();
        if (chainCode.equalsIgnoreCase(ChainCode.TRON)){
            boolean onKeyDown = ((DappBrowserTronFragment) getFragment()).onKeyDown(keyCode);
            if (onKeyDown) {
                return onKeyDown;
            }
        }else {
            boolean onKeyDown = ((DappBrowserFragmentNew) getFragment()).onKeyDown(keyCode);
            if (onKeyDown) {
                return onKeyDown;
            }
        }

        return super.onKeyDown(keyCode, event);
    }
}