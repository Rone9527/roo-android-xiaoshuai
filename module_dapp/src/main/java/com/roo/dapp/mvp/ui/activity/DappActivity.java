package com.roo.dapp.mvp.ui.activity;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.roo.core.ui.base.IBaseSingleFragmentActivity;
import com.roo.dapp.mvp.ui.fragment.DappFragment;
import com.roo.router.Router;

public class DappActivity extends IBaseSingleFragmentActivity {

    public static void start(Context context) {
        Router.newIntent(context)
                .to(DappActivity.class)
                .launch();
    }

    @Override
    protected Fragment createFragment() {
        return DappFragment.newInstance();
    }

}
