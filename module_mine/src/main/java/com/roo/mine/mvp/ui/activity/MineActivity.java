package com.roo.mine.mvp.ui.activity;

import android.content.Context;


import androidx.fragment.app.Fragment;

import com.roo.core.ui.base.IBaseSingleFragmentActivity;
import com.roo.mine.mvp.ui.fragment.MineFragment;
import com.roo.router.Router;

public class MineActivity extends IBaseSingleFragmentActivity {

    public static void start(Context context) {
        Router.newIntent(context)
                .to(MineActivity.class)
                .launch();
    }

    @Override
    protected Fragment createFragment() {
        return MineFragment.newInstance();
    }

}
