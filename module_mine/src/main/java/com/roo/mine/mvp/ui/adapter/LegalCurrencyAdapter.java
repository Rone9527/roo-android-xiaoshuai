package com.roo.mine.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.mine.LegalCurrencyBean;
import com.roo.core.ui.adapter.BaseViewHolderImpl;
import com.roo.core.utils.utils.TickerManager;
import com.roo.mine.R;

import javax.inject.Inject;

/**
 * <pre>
 *     project name: client-android-wallet
 *     author      : 李琼
 *     create time : 2021/6/7 14:19
 *     desc        : 描述--//LegalCurrencyAdapter 计价货币
 * </pre>
 */

public class LegalCurrencyAdapter extends BaseQuickAdapter<LegalCurrencyBean, BaseViewHolderImpl> {

    private LegalCurrencyBean selected;

    public LegalCurrencyBean getSelected() {
        return selected;
    }


    @Inject
    public LegalCurrencyAdapter() {
        super(R.layout.item_legal_currency);
        selected = TickerManager.getInstance().getLegal();
    }

    public void setSelected(LegalCurrencyBean selected) {
        this.selected = selected;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolderImpl helper, LegalCurrencyBean item) {
        helper.setText(R.id.tvLegalCurrency, item.getSymbol())
                .setVisible(R.id.ivCheck, selected != null
                        && item.getSymbol().equals(selected.getSymbol()));
    }
}
