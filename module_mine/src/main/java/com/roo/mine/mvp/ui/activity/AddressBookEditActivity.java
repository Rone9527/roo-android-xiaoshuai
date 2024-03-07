package com.roo.mine.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.aries.ui.view.radius.RadiusEditText;
import com.aries.ui.view.title.TitleBarView;
import com.billy.cc.core.component.CC;
import com.bumptech.glide.Glide;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.manager.ChainCode;
import com.core.domain.mine.AddressBookBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.ComponentApplication;
import com.roo.core.app.component.Qrcode;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.PrimaryKey;
import com.roo.core.utils.GlideManger;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.Kits;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.mine.R;
import com.roo.mine.di.component.DaggerAddressBookEditComponent;
import com.roo.mine.mvp.contract.AddressBookEditContract;
import com.roo.mine.mvp.presenter.AddressBookEditPresenter;
import com.roo.mine.mvp.ui.dialog.ChooseLinkDialog;
import com.roo.mine.mvp.ui.utils.AddressBookManager;
import com.roo.router.Router;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 编辑地址本
 */
public class AddressBookEditActivity extends BaseActivity<AddressBookEditPresenter> implements AddressBookEditContract.View {
    public static final int DEFAULT = -1;
    private boolean enable = false;
    private TitleBarView mTitleBarView;
    private AddressBookBean addressBook;
    private int icon;
    private TextView tvLink;
    private ImageView ivLink;

    public static void start(Context context, long id) {
        Router.newIntent(context)
                .to(AddressBookEditActivity.class)
                .putLong(Constants.KEY_ID, id)
                .launch();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddressBookEditComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_address_book_edit;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        long id = getIntent().getLongExtra(Constants.KEY_ID, DEFAULT);
        RadiusEditText edAddress = findViewById(R.id.edAddress);
        RadiusEditText edRemark = findViewById(R.id.edRemark);
        RadiusEditText edDesc = findViewById(R.id.edDesc);

        tvLink = findViewById(R.id.tvLink);
        ivLink = findViewById(R.id.ivLink);

        String mainTitleText;
        if (id == DEFAULT) {//新增
            mainTitleText = getString(R.string.title_add_address);


            String json = SharedPref.getString(Constants.KEY_OBJ_LINK_TOKEN);
            if (!TextUtils.isEmpty(json)) {
                List<LinkTokenBean> dataSet = new Gson().fromJson(json, new TypeToken<List<LinkTokenBean>>() {
                }.getType());
                for (LinkTokenBean linkBean : dataSet) {
                    if (linkBean.getCode().equals(ChainCode.ETH)) {
                        setLinkInfo(linkBean.getCode(), GlobalUtils.getLinkImage(linkBean.getCode()));
                        break;
                    }
                }
            }

        } else {//编辑
            mainTitleText = getString(R.string.title_edit_books);
            addressBook = AddressBookManager.getInstance().getAddressById(id);

            edRemark.setText(addressBook.getRemark());
            edDesc.setText(addressBook.getDescrible());
            edAddress.setText(addressBook.getAddress());
            setLinkInfo(addressBook.getChainCode(), addressBook.getIcon());
        }
        mTitleBarView = ViewHelper.initTitleBar(mainTitleText, this);
        RxView.clicks(findViewById(R.id.layoutLink))
                .throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> {
            ChooseLinkDialog.newInstance(Kits.Text.getText(tvLink))
                    .setOnClickListener((linkBean) -> {
                        setLinkInfo(linkBean.getCode(), GlobalUtils.getLinkImage(linkBean.getCode()));
                    }).show(getSupportFragmentManager(), getClass().getSimpleName());
        });

        RxView.clicks(findViewById(R.id.ivScan)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    CC.obtainBuilder(Qrcode.NAME)
                            .setContext(this)
                            .setActionName(Qrcode.Action.QrcodeScanActivity)
                            .build().callAsyncCallbackOnMainThread((cc, result) -> {
                        if (result.isSuccess()) {
                            String resultDataItem = result.getDataItem(Qrcode.Result.RESULT);
                            edAddress.setText(resultDataItem);
                        } else {
                            ToastUtils.show(getString(R.string.toast_not_support_scan_type));
                        }
                    });
                });
        mTitleBarView.setRightText(getString(R.string.save));
        mTitleBarView.getTextView(Gravity.END).setOnClickListener(
                v -> {
                    if (enable) {
                        if (id == DEFAULT) {
                            addressBook = new AddressBookBean();
                            addressBook.setId(PrimaryKey.getIndexNext(PrimaryKey.ADDRESS));
                        }
                        addressBook.setIcon(icon);
                        addressBook.setChainCode(Kits.Text.getText(tvLink));
                        addressBook.setAddress(Kits.Text.getText(edAddress));
                        addressBook.setRemark(Kits.Text.getText(edRemark));
                        addressBook.setDescrible(Kits.Text.getText(edDesc));

                        AddressBookManager.getInstance().addAddress(addressBook);
                        if (id == DEFAULT) {
                            PrimaryKey.addIndex(PrimaryKey.ADDRESS);
                        }
                        setResult(RESULT_OK);
                        finish();
                    }
                });

        InitialValueObservable<CharSequence> observableAddress = RxTextView.textChanges(edAddress);
        InitialValueObservable<CharSequence> observableRemark = RxTextView.textChanges(edRemark);
        InitialValueObservable<CharSequence> observableLink = RxTextView.textChanges(tvLink);

        Observable.combineLatest(observableAddress, observableRemark, observableLink, (s1, s2, s3) ->
                !(Kits.Empty.check(s1) || Kits.Empty.check(s2) || Kits.Empty.check(s3)))
                .subscribe(new ErrorHandleSubscriber<Boolean>(ComponentApplication.getRxErrorHandler()) {
                    @Override
                    public void onNext(@NotNull Boolean verify) {
                        AddressBookEditActivity.this.enable = verify;
                        setTextActionColor();
                    }
                });
        setTextActionColor();
    }

    private void setLinkInfo(String chainCode, int icon) {
        this.icon = icon;
        tvLink.setText(chainCode);

        Glide.with(this).load(this.icon)
                .apply(GlideManger.OPTIONS_IMAGE_CIRCLE)
                .apply(GlideManger.optionsCircle(this)).into(ivLink);
    }

    private void setTextActionColor() {
        mTitleBarView.setRightTextColor(ContextCompat.getColor(this,
                enable ? R.color.blue_color : R.color.white_color_assist_2));
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }
}