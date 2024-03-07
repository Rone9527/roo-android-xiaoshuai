package com.roo.mine.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.mine.LanguageBean;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.mine.R;

import javax.inject.Inject;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/7 14:19
 *     desc        : 描述--//LanguageAdapter 多语言
 * </pre>
 */

public class LanguageAdapter extends BaseQuickAdapter<LanguageBean, BaseViewHolderImpl> {

    @Inject
    public LanguageAdapter() {
        super(R.layout.item_language);
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, LanguageBean item) {
//        helper.setText(R.id.tvLanguage, item.get)
//                .setChecked(R.id.checkbox, item.get);
    }
}
