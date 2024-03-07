package com.roo.home.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.core.domain.mine.LegalCurrencyBean;
import com.core.domain.trade.QuoteBean;
import com.core.domain.trade.QuoteKlineBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.constants.Constants;
import com.roo.core.utils.Kits;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.TickerManager;
import com.roo.home.R;
import com.roo.home.di.component.DaggerExchangeQuotesComponent;
import com.roo.home.mvp.contract.ExchangeQuotesContract;
import com.roo.home.mvp.presenter.ExchangeQuotesPresenter;
import com.roo.home.mvp.ui.adapter.ExchangeQuotesAdapter;
import com.roo.view.chart.Axis;
import com.roo.view.chart.AxisValue;
import com.roo.view.chart.Line;
import com.roo.view.chart.OnChartSelectedListener;
import com.roo.view.chart.OnPointSelectListener;
import com.roo.view.chart.PointValue;
import com.roo.view.chart.SlideSelectLineChart;
import com.roo.view.chart.SlidingLine;
import com.roo.router.Router;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * token交易所详情
 */
public class ExchangeQuotesActivity extends BaseActivity<ExchangeQuotesPresenter> implements ExchangeQuotesContract.View {
    private String tokenSymbol;

    ExchangeQuotesAdapter adapter;
    private SlideSelectLineChart slideSelectLineChart;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerExchangeQuotesComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    public static void start(Context context, String chainCode) {
        Router.newIntent(context)
                .to(ExchangeQuotesActivity.class)
                .putString(Constants.KEY_CHAIN_CODE, chainCode)
                .launch();
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_exchangequotes;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tokenSymbol = getIntent().getStringExtra(Constants.KEY_CHAIN_CODE);
        ViewHelper.initTitleBar(tokenSymbol.concat(getString(R.string.trade_platform_quote)), this);
        adapter = new ExchangeQuotesAdapter();
        adapter.setSymbol(tokenSymbol);
        RecyclerView recyclerView = ViewHelper.initRecyclerView(adapter, this);
        ViewHelper.initEmptyView(adapter, recyclerView);

        LegalCurrencyBean selectedLegal = TickerManager.getInstance().getLegal();
        ((TextView) (findViewById(R.id.tvNewPrice))).setText(MessageFormat.format(getString(R.string.format_quote_new_price), selectedLegal.getIcon()));
        requestData();
        slideSelectLineChart = findViewById(R.id.move_select_chart);
//        initLineChart();
        slideSelectLineChart.setOnPointSelectListener(new OnPointSelectListener() {
            @Override
            public void onPointSelect(int position, String xLabel, String value) {
                String point = xLabel + ":" + value;
//                tvSelectPoint.setText(point);
//                LogManage.e(Constants.LOG_STRING + "--position--" + position + "---xLabel----" + xLabel + "------value----" + value);
            }
        });

        slideSelectLineChart.setOnChartSelectedListener(new OnChartSelectedListener() {
            @Override
            public void onChartSelected(boolean isChartSelected) {
//                Log.e("====", "isChartSelected: " + isChartSelected);
            }
        });
    }


    private void requestData() {
        mPresenter.getKline(tokenSymbol);
        mPresenter.getPlatform(tokenSymbol);
    }


    private SlidingLine getSlideingLine() {
        SlidingLine slidingLine = new SlidingLine();
        slidingLine.setSlideLineColor(Color.parseColor("#376AFF"))
                .setSlideLineWidth(30)
                .setSlidePointColor(Color.parseColor("#376AFF"))
                .setSlidePointRadius(8)
                .setDash(false);
        return slidingLine;
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }


    public String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time * 1000));//换成毫秒
    }

    @Override
    public void loadData(List<QuoteKlineBean> list) {
        List<AxisValue> axisValueListX = new ArrayList<>();
        List<AxisValue> axisValueListY = new ArrayList<>();

        @SuppressLint({"NewApi", "LocalSuppress"})
        Double minDD = list.stream().mapToDouble(quoteKlineBean -> Float.parseFloat(quoteKlineBean.getClosePrice())).min().getAsDouble();
//        minDD = minDD * 0.95;
        for (QuoteKlineBean quoteKlineBean : list) {
            AxisValue axisValueX = new AxisValue();
            AxisValue axisValueY = new AxisValue();
            axisValueX.setLabel(getTime(quoteKlineBean.getTime()));
            axisValueY.setLabel((Double.parseDouble(quoteKlineBean.getClosePrice()) - minDD) + "");//使用差值画线
            axisValueListX.add(axisValueX);
            axisValueListY.add(axisValueY);
        }
        Axis axisX = new Axis(axisValueListX);
//        axisX.setAxisColor(Color.parseColor("#376AFF")).setTextColor(Color.GREEN).setHasLines(false).setShowText(false);
        axisX.setShowText(false);
        axisX.setHasLines(false);
        axisX.setAxisColor(Color.TRANSPARENT);

        Axis axisY = new Axis(axisValueListY);
//        axisY.setAxisColor(Color.parseColor("#376AFF")).setTextColor(Color.GREEN).setHasLines(false).setShowText(false);
        axisY.setShowText(false);
        axisY.setHasLines(false);
        axisY.setAxisColor(Color.TRANSPARENT);

        @SuppressLint({"NewApi", "LocalSuppress"})
        Double max = axisValueListY.stream().mapToDouble(axisValue -> Float.parseFloat(axisValue.getLabel())).max().getAsDouble();
//        @SuppressLint({"NewApi", "LocalSuppress"})
//        Double min = axisValueListY.stream().mapToDouble(axisValue -> Float.parseFloat(axisValue.getLabel())).min().getAsDouble();
//
////        axisY.setStartY(min.floatValue());
////        axisY.setStopY(max.floatValue());
////        LogManage.e(Constants.LOG_STRING + "小" + min + "大" + max);


        slideSelectLineChart.setAxisX(axisX);
        slideSelectLineChart.setAxisY(axisY);

        slideSelectLineChart.setSlideLine(getSlideingLine());

        Line line = getFoldLineNew(axisValueListY, max, minDD);
        slideSelectLineChart.setChartData(line);
        slideSelectLineChart.show();


    }

    private Line getFoldLineNew(List<AxisValue> listY, Double max, Double minDD) {

        List<PointValue> pointValues = new ArrayList<>();
        for (int i = 0; i < listY.size(); i++) {
            PointValue pointValue = new PointValue();
            pointValue.setX((i) / (listY.size() - 1f));
            pointValue.setLabel((Double.parseDouble(listY.get(i).getLabel()) + minDD) + "");
            pointValue.setY(Float.parseFloat(listY.get(i).getLabel()) / max.floatValue());
            pointValue.setShowLabel(false);
            pointValues.add(pointValue);
        }
        Line line = new Line(pointValues);
        line.setLineColor(Color.parseColor("#376AFF"))
                .setLineWidth(1.5f)
                .setPointColor(Color.parseColor("#376AFF"))
                .setCubic(true)
                .setPointRadius(2)
                .setFill(true)
                .setHasPoints(false)
                .setFillColor(Color.parseColor("#376AFF"))
                .setLabelColor(Color.parseColor("#376AFF"))
                .setHasLabels(true);
        return line;
    }


    @Override
    public void loadQuotes(List<QuoteBean> list) {
        if (Kits.Empty.check(list)) {
            findViewById(R.id.ll_top).setVisibility(View.GONE);
        } else {
            adapter.setNewData(list);
        }
    }
}