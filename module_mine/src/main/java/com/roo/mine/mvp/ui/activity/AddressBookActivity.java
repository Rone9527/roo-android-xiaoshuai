package com.roo.mine.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.View;

import com.aries.ui.view.title.TitleBarView;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.mine.AddressBookBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.component.Mine;
import com.roo.core.app.constants.Constants;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.mine.R;
import com.roo.mine.di.component.DaggerAddressBookComponent;
import com.roo.mine.mvp.contract.AddressBookContract;
import com.roo.mine.mvp.presenter.AddressBookPresenter;
import com.roo.mine.mvp.ui.adapter.AddressBookAdapter;
import com.roo.mine.mvp.ui.dialog.AddressBookOperateDialog;
import com.roo.mine.mvp.ui.utils.AddressBookManager;
import com.roo.router.Router;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 地址本
 */
public class AddressBookActivity extends BaseActivity<AddressBookPresenter> implements AddressBookContract.View {

    @Inject
    AddressBookAdapter mAdapter;
    private String callId;
    private String chainCode;

    public static void start(Context context) {
        start(context, false, "", "");
    }

    /**
     * @param selectItem 选择条目 or 编辑条目
     */
    public static void start(Context context, boolean selectItem, String callId, String chainCode) {
        Router.newIntent(context)
                .to(AddressBookActivity.class)
                .putBoolean(Constants.KEY_DEFAULT, selectItem)
                .putString(Constants.KEY_ID, callId)
                .putString(Constants.KEY_CHAIN_CODE, chainCode)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddressBookComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_address_book;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        boolean selectItem = getIntent().getBooleanExtra(Constants.KEY_DEFAULT, false);
        chainCode = getIntent().getStringExtra(Constants.KEY_CHAIN_CODE);
        callId = getIntent().getStringExtra(Constants.KEY_ID);

        TitleBarView mTitleBarView = ViewHelper.initTitleBar(getString(R.string.title_address_books), this);
        mTitleBarView.addRightAction(mTitleBarView.new ImageAction(R.drawable.ic_common_add,
                v -> AddressBookEditActivity.start(this, -1)));

        RecyclerView recyclerView = ViewHelper.initRecyclerView(mAdapter, this);

        String emptyStr = getString(R.string.no_data_address);
        ViewHelper.initEmptyView(mAdapter, recyclerView, emptyStr,
                R.drawable.ic_common_empty_data_address);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AddressBookBean item = mAdapter.getItem(position);
                if (item == null) {
                    return;
                }
                if (selectItem) {
                    CC.sendCCResult(callId, CCResult.success(Mine.Result.RESULT, item));
                    finish();
                    return;
                }
                AddressBookOperateDialog.newInstance().setOnClickedListener(new AddressBookOperateDialog.OnClickedListener() {
                    @Override
                    public void onClickRemove() {
                        AddressBookManager.getInstance().removeAddress(item.getId());
                        ToastUtils.show(R.string.toast_address_book_deleted);
                        setNewData();
                    }

                    @Override
                    public void onClickCopy() {
                        GlobalUtils.copyToClipboard(item.getAddress(), AddressBookActivity.this);
                        ToastUtils.show(getString(R.string.title_address_books) + getString(R.string.copy_success));
                    }

                    @Override
                    public void onClickEdit() {
                        AddressBookEditActivity.start(AddressBookActivity.this, item.getId());
                    }
                }).show(getSupportFragmentManager(), getClass().getSimpleName());
            }
        });
    }

    private void setNewData() {
        List<AddressBookBean> addressList = AddressBookManager.getInstance().getAddressList();
        if (TextUtils.isEmpty(chainCode)) {
            mAdapter.setNewData(addressList);
        } else {
            List<AddressBookBean> result = new ArrayList<>();
            for (AddressBookBean item : addressList) {
                if (item.getChainCode().equals(chainCode)) {
                    result.add(item);
                }
            }
            mAdapter.setNewData(result);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setNewData();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CC.sendCCResult(callId, CCResult.success());
    }

}