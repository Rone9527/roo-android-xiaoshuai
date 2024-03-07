package com.roo.trade.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.core.domain.trade.TickerBean;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.TickerManager;
import com.roo.trade.R;
import com.roo.trade.di.component.DaggerTradeComponent;
import com.roo.trade.di.module.TradeModule;
import com.roo.trade.mvp.contract.TradeContract;
import com.roo.trade.mvp.presenter.TradePresenter;
import com.roo.trade.mvp.ui.adapter.TradeAdapter;

import org.jetbrains.annotations.NotNull;
import org.simple.eventbus.Subscriber;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 行情
 */
public class QuotesFragment extends BaseFragment<TradePresenter> implements TradeContract.View {

    @Inject
    TradeAdapter mAdapter;

    private int baseSort = ORDER_PERCENT;
    public static final int ORDER_PRICE = 1;
    public static final int ORDER_PERCENT = 2;

    private int sortType = ORDER_DEFAULT;
    //1默认，2降序，3升序
    public static final int ORDER_ASC = 3;
    public static final int ORDER_DESC = 2;
    public static final int ORDER_DEFAULT = 1;

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public static QuotesFragment newInstance() {
        QuotesFragment fragment = new QuotesFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTradeComponent
                .builder()
                .appComponent(appComponent)
                .tradeModule(new TradeModule(this))
                .build()
                .inject(this);

    }

    @Subscriber(tag = EventBusTag.DATA_TICKER)
    public void onTicker(String event) {
        doRefresh();
    }

    @Subscriber(tag = EventBusTag.LEGAL_CHANGED)
    public void onLegalChanged(String event) {
        doRefresh();
    }


    private void doRefresh() {
        Observable.fromIterable(TickerManager.getInstance().getTickerList())
                .filter(new Predicate<TickerBean>() {
                    @Override
                    public boolean test(@NotNull TickerBean t) {
                        return t.isShow();
                    }
                }).toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TickerBean>>() {
                    @Override
                    public void accept(List<TickerBean> tokenList) {
                        switch (sortType) {
                            case ORDER_ASC:
                                Collections.sort(tokenList, (TickerBean b1, TickerBean b2) -> {
                                    if (baseSort == ORDER_PRICE) {
                                        return b1.getPrice().compareTo(b2.getPrice());
                                    } else {
                                        return b1.getPriceChangePercent().compareTo(b2.getPriceChangePercent());
                                    }
                                });
                                break;
                            case ORDER_DESC:
                                Collections.sort(tokenList, (TickerBean b1, TickerBean b2) -> {
                                    if (baseSort == ORDER_PRICE) {
                                        return b2.getPrice().compareTo(b1.getPrice());
                                    } else {
                                        return b2.getPriceChangePercent().compareTo(b1.getPriceChangePercent());
                                    }
                                });
                                break;
                        }
                        mAdapter.setNewData(tokenList);
                    }
                });
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_quotes, container, false);
        RecyclerView recyclerView = ViewHelper.initRecyclerView(inflate, mAdapter);
        ViewHelper.initRefreshLayout(inflate, refreshLayout -> {
            doRefresh();
            refreshLayout.finishRefresh(2000);
        });
        ViewHelper.initEmptyView(mAdapter, recyclerView);

        ViewHelper.addFooterView(R.layout.layout_quote_foot, mAdapter, recyclerView);

        ImageView ivPrice = inflate.findViewById(R.id.ivPrice);
        ImageView ivQuote = inflate.findViewById(R.id.ivQuote);

        inflate.findViewById(R.id.layoutQuoteChange).setOnClickListener(v -> {
            if (baseSort != ORDER_PERCENT) {
                setSortType(ORDER_DEFAULT);
            }
            baseSort = ORDER_PERCENT;
            ivPrice.setImageResource(R.drawable.ic_quote_change_common);
            setSortTypeImage(ivQuote);
        });
        //inflate.findViewById(R.id.layoutPriceChange).setOnClickListener(v -> {
        //    if (baseSort != ORDER_PRICE) {
        //        setSortType(ORDER_DEFAULT);
        //    }
        //    baseSort = ORDER_PRICE;
        //    ivQuote.setImageResource(R.drawable.ic_quote_change_common);
        //    setSortTypeImage(ivPrice);
        //});
        return inflate;
    }

    private void setSortTypeImage(ImageView imageView) {
        switch (sortType) {
            case ORDER_DEFAULT:
                setSortType(ORDER_DESC);
                imageView.setImageResource(R.drawable.ic_quote_change_down);
                break;
            case ORDER_DESC:
                setSortType(ORDER_ASC);
                imageView.setImageResource(R.drawable.ic_quote_change_up);
                break;
            case ORDER_ASC:
                setSortType(ORDER_DEFAULT);
                imageView.setImageResource(R.drawable.ic_quote_change_common);
                break;
        }
        doRefresh();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }
}