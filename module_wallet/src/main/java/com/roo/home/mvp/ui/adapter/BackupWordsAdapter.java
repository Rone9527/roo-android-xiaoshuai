package com.roo.home.mvp.ui.adapter;

import com.aries.ui.view.radius.RadiusLinearLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.home.R;

import javax.inject.Inject;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/13 10:26
 *     desc        : 描述--//BackupWordsAdapter 备份助记词
 * </pre>
 */

public class BackupWordsAdapter extends BaseQuickAdapter<String, BaseViewHolderImpl> {

    @Inject
    public BackupWordsAdapter() {
        super(R.layout.item_backup_words);
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, String item) {
        int index = helper.getAdapterPosition();
        int radius = ArmsUtils.dip2px(mContext, 20);

        RadiusLinearLayout layoutBackupWords = helper.getView(R.id.layoutBackupWords);
        if (index == 0) {
            layoutBackupWords.getDelegate().setTopLeftRadius(radius).init();
        } else if (index == 2) {
            layoutBackupWords.getDelegate().setTopRightRadius(radius).init();
        } else if (index == 9) {
            layoutBackupWords.getDelegate().setBottomLeftRadius(radius).init();
        } else if (index == 11) {
            layoutBackupWords.getDelegate().setBottomRightRadius(radius).init();
        }

        helper.setText(R.id.tvBackupWord, item)
                .setText(R.id.tvIndex, String.valueOf(index + 1));

    }
}
