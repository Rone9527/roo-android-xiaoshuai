package com.roo.trade.mvp.ui.activity;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.roo.core.ui.base.IBaseSingleFragmentActivity;
import com.roo.router.Router;
import com.roo.trade.mvp.ui.fragment.MarketFragment;

public class TradeActivity extends IBaseSingleFragmentActivity {

    public static void start(Context context) {
        Router.newIntent(context)
                .to(TradeActivity.class)
                .launch();
    }

    @Override
    protected Fragment createFragment() {
        return MarketFragment.newInstance();
    }

}
