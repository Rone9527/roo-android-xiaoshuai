package com.roo.home.mvp.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.home.R;
import com.roo.home.mvp.model.bean.BackupWord;

import javax.inject.Inject;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/13 10:26
 *     desc        : 描述--//BackupWordsAdapter 备份助记词
 * </pre>
 */

public class BackupWordsVerifyAdapter extends
        BaseQuickAdapter<BackupWord, BaseViewHolderImpl> {

    @Inject
    public BackupWordsVerifyAdapter() {
        super(R.layout.item_backup_words_verify);
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, BackupWord item) {
        TextView tvBackupWord = helper.getView(R.id.tvBackupWord);
        tvBackupWord.setSelected(item.isSelect());
        helper.setText(R.id.tvBackupWord, item.getText());

    }
}
