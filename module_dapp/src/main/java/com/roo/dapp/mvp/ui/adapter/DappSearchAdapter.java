package com.roo.dapp.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.core.domain.dapp.DappBean;
import com.core.domain.dapp.DappHistoryBean;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.dapp.R;

import javax.inject.Inject;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/13 10:26
 *     desc        : 描述--//BackupWordsAdapter 备份助记词
 * </pre>
 */

public class DappSearchAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolderImpl> {

    public static final int TYPE_HISTORY_TITLE = 1001;
    public static final int TYPE_HISTORY_CONTENT = 1002;

    public static final int TYPE_SEARCH_CONTENT = 1003;


    @Inject
    public DappSearchAdapter() {
        super(null);
        addItemType(TYPE_HISTORY_TITLE, R.layout.item_search_history_top);
        addItemType(TYPE_HISTORY_CONTENT, R.layout.item_search_history_item);

        addItemType(TYPE_SEARCH_CONTENT, R.layout.item_search_result);
    }


    @Override
    protected void convert(BaseViewHolderImpl helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_HISTORY_TITLE: {
                helper.addOnClickListener(R.id.ivDeleteAll);
            }
            break;
            case TYPE_HISTORY_CONTENT: {
                DappHistoryBean historyBean = (DappHistoryBean) item;
                helper.setText(R.id.tvText, historyBean.getName());
                helper.addOnClickListener(R.id.ivDelete, R.id.layoutRootHistory);
            }
            break;
            case TYPE_SEARCH_CONTENT: {
                helper.addOnClickListener(R.id.layoutRoot);

                DappBean dappBean = (DappBean) item;

                helper.setText(R.id.tvDappName, dappBean.getName());
                helper.setText(R.id.tvDappInfo, dappBean.getDiscription());
                helper.setCircleImageUrl(R.id.ivDapp, dappBean.getIcon());
            }
            break;
        }
    }
}
