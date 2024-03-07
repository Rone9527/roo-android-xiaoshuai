package com.roo.mine.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.core.domain.mine.MessageSystem;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.constants.Constants;
import com.roo.core.utils.Kits;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.MessageSystemManager;
import com.roo.mine.R;
import com.roo.mine.di.component.DaggerNoticeDetailComponent;
import com.roo.mine.mvp.contract.NoticeDetailContract;
import com.roo.mine.mvp.presenter.NoticeDetailPresenter;
import com.roo.router.Router;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 系统消息详情
 */
public class NoticeDetailActivity extends BaseActivity<NoticeDetailPresenter> implements NoticeDetailContract.View {

    public static void start(Context context, MessageSystem.DataBean dataBean) {
        Router.newIntent(context)
                .to(NoticeDetailActivity.class)
                .putParcelable(Constants.KEY_DEFAULT, dataBean)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNoticeDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_noticedetail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ViewHelper.initTitleBar(getString(R.string.title_notice_detail), this);
        MessageSystem.DataBean dataBean = getIntent().getParcelableExtra(Constants.KEY_DEFAULT);
        NestedScrollView scrollView = findViewById(R.id.scrollView);
        View emptyView = findViewById(R.id.emptyView);

        if (dataBean == null) {
            scrollView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);

            ImageView emptyImg = findViewById(R.id.empty_img);
            emptyImg.setImageResource(R.drawable.ic_empty_notice);
            TextView emptyString = findViewById(R.id.empty_string);
            emptyString.setText(R.string.empty_notice_tip);

        } else {
            scrollView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            TextView tvTitle = findViewById(R.id.tvTitle);
            TextView tvContent = findViewById(R.id.tvContent);
            TextView tvTime = findViewById(R.id.tvTime);

            tvTitle.setText(dataBean.getMsgTitle());
            tvContent.setText(dataBean.getMsgContent());
            tvTime.setText(Kits.Date.getYmdhmCh(dataBean.getPublishTime() * 1000));
            MessageSystemManager.getInstance().setNoticeRead(dataBean.getId());

        }

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }
}