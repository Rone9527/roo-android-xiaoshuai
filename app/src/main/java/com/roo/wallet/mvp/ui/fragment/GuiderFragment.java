package com.roo.wallet.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aries.ui.view.radius.RadiusTextView;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.roo.wallet.R;
import com.roo.wallet.mvp.model.bean.GuiderBean;
import com.roo.wallet.mvp.ui.activity.GuiderActivity;

import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/8 10:03
 *     desc        : 描述--//GuiderFragment
 * </pre>
 */

public class GuiderFragment extends BaseFragment {

    private static final String KEY_GUIDER = "KEY_GUIDER";

    public static GuiderFragment newInstance(GuiderBean.DataBean dataBean) {
        Bundle args = new Bundle();
        GuiderFragment fragment = new GuiderFragment();
        args.putParcelable(KEY_GUIDER, dataBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_guider, container, false);


        TextView tvTitle = inflate.findViewById(R.id.tvTitle);
        TextView tvTitleSub = inflate.findViewById(R.id.tvTitleSub);
        RadiusTextView tvJump = inflate.findViewById(R.id.tvJump);
        ImageView ivGuider = inflate.findViewById(R.id.ivGuider);

        GuiderBean.DataBean dataBean = getArguments().getParcelable(KEY_GUIDER);
        tvJump.setVisibility(dataBean.getIndex() == 2 ? View.VISIBLE : View.INVISIBLE);
        tvTitle.setText(dataBean.getTitle());
        tvTitleSub.setText(dataBean.getSubTitle());
        Glide.with(requireActivity()).load(dataBean.getDrawable()).into(ivGuider);

        RxView.clicks(tvJump).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> ((GuiderActivity) getActivity()).gotoMainActivity());

        return inflate;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }
}
