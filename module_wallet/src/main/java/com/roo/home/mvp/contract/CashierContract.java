package com.roo.home.mvp.contract;



import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

public interface CashierContract {

    interface View extends IView {

        FragmentActivity getActivity();

        FragmentManager getSupportFragmentManager();


    }

    interface Model extends IModel {

    }
}