package com.roo.home.mvp.ui.activity;

import android.content.Context;


import androidx.fragment.app.Fragment;

import com.roo.core.ui.base.IBaseSingleFragmentActivity;
import com.roo.home.mvp.ui.fragment.WalletFragment;
import com.roo.router.Router;

public class WalletActivity extends IBaseSingleFragmentActivity {

    public static void start(Context context) {
        Router.newIntent(context)
                .to(WalletActivity.class)
                .launch();
    }

    @Override
    protected Fragment createFragment() {
        return WalletFragment.newInstance();
    }

}
