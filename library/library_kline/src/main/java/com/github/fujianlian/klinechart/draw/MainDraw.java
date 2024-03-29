package com.github.fujianlian.klinechart.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.github.fujianlian.klinechart.BaseKLineChartView;
import com.github.fujianlian.klinechart.KLineChartView;
import com.github.fujianlian.klinechart.KLineEntity;
import com.github.fujianlian.klinechart.R;
import com.github.fujianlian.klinechart.base.IChartDraw;
import com.github.fujianlian.klinechart.base.IValueFormatter;
import com.github.fujianlian.klinechart.entity.ICandle;
import com.github.fujianlian.klinechart.entity.IKLine;
import com.github.fujianlian.klinechart.formatter.ValueFormatter;
import com.github.fujianlian.klinechart.utils.ViewUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 主图的实现类
 * Created by tifezh on 2016/6/14.
 */
public class MainDraw implements IChartDraw<ICandle> {

    private boolean isDrawM30 = true;
    private boolean isDrawM50 = false;

    private float mCandleWidth = 0;
    private float mCandleLineWidth = 0;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mRedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mGreenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint ma5Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint ma10Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint ma30Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint ma50Paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Paint mSelectorTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mSelectorBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Context mContext;

    private boolean mCandleSolid = true;
    // 是否分时
    private boolean isLine = false;
    private @Status
    int status = Status.MA;
    private KLineChartView kChartView;

    private Paint bgStokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint valuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Context context;

    public Paint getLinePaint() {
        return mLinePaint;
    }

    private IValueFormatter mIValueFormatter;
    /**
     * draw选择器
     *
     * @param view
     * @param canvas
     */
    private String[] selectTitle;

    public MainDraw(BaseKLineChartView view) {
        context = view.getContext();

        selectTitle = context.getResources().getStringArray(R.array.kline_select_show);

        kChartView = (KLineChartView) view;
        mContext = context;


        mRedPaint.setColor(ViewUtil.getIncreaseColor(context));
        mGreenPaint.setColor(ViewUtil.getDecreaseColor(context));

        mLinePaint.setColor(ContextCompat.getColor(context, R.color.chart_line));
        paint.setColor(ContextCompat.getColor(context, R.color.chart_line_background));

        bgStokePaint.setStrokeWidth(1);
        bgStokePaint.setStyle(Paint.Style.STROKE);
        bgStokePaint.setColor(Color.parseColor("#7C8586"));
        setSelectorTextColor(Color.parseColor("#656F80"));

        valuePaint.setTextSize(ViewUtil.dp2px(view.getContext(), 9));
        resetViewPaintColor();

    }

    private void resetViewPaintColor() {
        valuePaint.setColor(Color.parseColor("#A1B2CC"));
    }

    public @Status
    int getStatus() {
        return status;
    }

    public void setStatus(@Status int status) {
        this.status = status;
    }

    @Override
    public void drawTranslated(@Nullable ICandle lastPoint, @NonNull ICandle curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseKLineChartView view, int position) {
        try {
            if (isLine) {
                view.drawMainLine(canvas, mLinePaint, lastX, lastPoint.getClosePrice(), curX, curPoint.getClosePrice());
                view.drawMainMinuteLine(canvas, paint, lastX, lastPoint.getClosePrice(), curX, curPoint.getClosePrice());
                if (status == Status.MA) {
                    //画ma60
                    if (lastPoint.getMA60Price() != 0) {
                        view.drawMainLine(canvas, ma10Paint, lastX, lastPoint.getMA60Price(), curX, curPoint.getMA60Price());
                    }
                } else if (status == Status.BOLL) {
                    //画boll
                    if (lastPoint.getMb() != 0) {
                        view.drawMainLine(canvas, ma10Paint, lastX, lastPoint.getMb(), curX, curPoint.getMb());
                    }
                }
            } else {
                drawCandle(view, canvas, curX, curPoint.getHighPrice(), curPoint.getLowPrice(), curPoint.getOpenPrice(), curPoint.getClosePrice());
                if (status == Status.MA) {
                    //画ma5
                    if (lastPoint.getMA5Price() != 0) {
                        view.drawMainLine(canvas, ma5Paint, lastX, lastPoint.getMA5Price(), curX, curPoint.getMA5Price());
                    }
                    //画ma10
                    if (lastPoint.getMA10Price() != 0) {
                        view.drawMainLine(canvas, ma10Paint, lastX, lastPoint.getMA10Price(), curX, curPoint.getMA10Price());
                    }
                    //画ma30
                    if (lastPoint.getMA30Price() != 0 && isDrawM30) {
                        view.drawMainLine(canvas, ma30Paint, lastX, lastPoint.getMA30Price(), curX, curPoint.getMA30Price());
                    }
                    //画ma50
                    if (lastPoint.getMA50Price() != 0 && isDrawM50) {
                        view.drawMainLine(canvas, ma50Paint, lastX, lastPoint.getMA50Price(), curX, curPoint.getMA50Price());
                    }
                } else if (status == Status.BOLL) {
                    //画boll
                    if (lastPoint.getUp() != 0) {
                        view.drawMainLine(canvas, ma5Paint, lastX, lastPoint.getUp(), curX, curPoint.getUp());
                    }
                    if (lastPoint.getMb() != 0) {
                        view.drawMainLine(canvas, ma10Paint, lastX, lastPoint.getMb(), curX, curPoint.getMb());
                    }
                    if (lastPoint.getDn() != 0) {
                        view.drawMainLine(canvas, ma30Paint, lastX, lastPoint.getDn(), curX, curPoint.getDn());
                    }
                }
            }

        } catch (Exception ignore) {

        }
    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseKLineChartView view, int position, float x, float y) {
        ICandle point = (IKLine) view.getItem(position);
        y = y - 5;
        if (isLine) {
            if (status == Status.MA) {
                if (point.getMA60Price() != 0) {
                    String text = "MA60:" + view.formatValue(point.getMA60Price()) + "  ";
                    canvas.drawText(text, x, y, ma10Paint);
                }
            } else if (status == Status.BOLL) {
                if (point.getMb() != 0) {
                    String text = "BOLL:" + view.formatValue(point.getMb()) + "  ";
                    canvas.drawText(text, x, y, ma10Paint);
                }
            }
        } else {
            if (status == Status.MA) {
                String text;
                if (point.getMA5Price() != 0) {
                    text = "MA5:" + view.formatValue(point.getMA5Price()) + "  ";
                    canvas.drawText(text, x, y, ma5Paint);
                    x += ma5Paint.measureText(text);
                }
                if (point.getMA10Price() != 0) {
                    text = "MA10:" + view.formatValue(point.getMA10Price()) + "  ";
                    canvas.drawText(text, x, y, ma10Paint);
                    x += ma10Paint.measureText(text);
                }
                if (point.getMA30Price() != 0 && isDrawM30) {
                    text = "MA30:" + view.formatValue(point.getMA30Price());
                    canvas.drawText(text, x, y, ma30Paint);
                }
                if (point.getMA50Price() != 0 && isDrawM50) {
                    text = "MA50:" + view.formatValue(point.getMA50Price());
                    canvas.drawText(text, x, y, ma50Paint);
                }
            } else if (status == Status.BOLL) {
                if (point.getMb() != 0) {
                    String text = "BOLL:" + view.formatValue(point.getMb()) + "  ";
                    canvas.drawText(text, x, y, ma10Paint);
                    x += ma5Paint.measureText(text);
                    text = "UB:" + view.formatValue(point.getUp()) + "  ";
                    canvas.drawText(text, x, y, ma5Paint);
                    x += ma10Paint.measureText(text);
                    text = "LB:" + view.formatValue(point.getDn());
                    canvas.drawText(text, x, y, ma30Paint);
                }
            }
        }
        if (view.isLongPress()) {
            drawSelector(view, canvas);
        }
    }

    @Override
    public float getMaxValue(ICandle point) {
        if (status == Status.BOLL) {
            if (Float.isNaN(point.getUp())) {
                if (point.getMb() == 0) {
                    return point.getHighPrice();
                } else {
                    return point.getMb();
                }
            } else if (point.getUp() == 0) {
                return point.getHighPrice();
            } else {
                return point.getUp();
            }
        } else {
            return Math.max(point.getHighPrice(), point.getMA30Price());
        }
    }

    @Override
    public float getMinValue(ICandle point) {
        if (status == Status.BOLL) {
            if (point.getDn() == 0) {
                return point.getLowPrice();
            } else {
                return point.getDn();
            }
        } else {
            if (point.getMA30Price() == 0f) {
                return point.getLowPrice();
            } else {
                return Math.min(point.getMA30Price(), point.getLowPrice());
            }
        }
    }

    @Override
    public IValueFormatter getValueFormatter() {
        if (mIValueFormatter == null) {
            mIValueFormatter = new ValueFormatter();
        }
        return mIValueFormatter;
    }

    public void setValueFormatter(IValueFormatter valueFormatter) {
        this.mIValueFormatter = valueFormatter;
    }

    /**
     * 画Candle
     *
     * @param canvas
     * @param x      x轴坐标
     * @param high   最高价
     * @param low    最低价
     * @param open   开盘价
     * @param close  收盘价
     */
    private void drawCandle(BaseKLineChartView view, Canvas canvas, float x, float high, float low, float open, float close) {
        high = view.getMainY(high);
        low = view.getMainY(low);
        open = view.getMainY(open);
        close = view.getMainY(close);
        float r = mCandleWidth / 2;
        float lineR = mCandleLineWidth / 2;
        if (open > close) {
            //实心
            if (mCandleSolid) {
                canvas.drawRect(x - r, close, x + r, open, mRedPaint);
                canvas.drawRect(x - lineR, high, x + lineR, low, mRedPaint);
            } else {
                mRedPaint.setStrokeWidth(mCandleLineWidth);
                canvas.drawLine(x, high, x, close, mRedPaint);
                canvas.drawLine(x, open, x, low, mRedPaint);
                canvas.drawLine(x - r + lineR, open, x - r + lineR, close, mRedPaint);
                canvas.drawLine(x + r - lineR, open, x + r - lineR, close, mRedPaint);
                mRedPaint.setStrokeWidth(mCandleLineWidth * view.getScaleX());
                canvas.drawLine(x - r, open, x + r, open, mRedPaint);
                canvas.drawLine(x - r, close, x + r, close, mRedPaint);
            }

        } else if (open < close) {
            canvas.drawRect(x - r, open, x + r, close, mGreenPaint);
            canvas.drawRect(x - lineR, high, x + lineR, low, mGreenPaint);
        } else {
            canvas.drawRect(x - r, open, x + r, close + 1, mRedPaint);
            canvas.drawRect(x - lineR, high, x + lineR, low, mRedPaint);
        }
    }

    private void drawSelector(BaseKLineChartView view, Canvas canvas) {
        Paint.FontMetrics metrics = mSelectorTextPaint.getFontMetrics();
        float textHeight = metrics.descent - metrics.ascent;

        int index = view.getSelectedIndex();
        float padding = ViewUtil.dp2px(mContext, 5);
        float margin = ViewUtil.dp2px(mContext, 5);
        float width = 0;
        float left;
        float top = margin + view.getTopPadding();
        float height = padding * 9 + textHeight * 8;

        ICandle point = (ICandle) view.getItem(index);
        List<String> strings = new ArrayList<>(12);
        strings.add(view.getAdapter().getDate(index));
        strings.add(getValueFormatter().format(point.getOpenPrice()));
        strings.add(getValueFormatter().format(point.getHighPrice()));
        strings.add(getValueFormatter().format(point.getLowPrice()));
        strings.add(getValueFormatter().format(point.getClosePrice()));

        String format = new ValueFormatter().format((point.getClosePrice() - point.getOpenPrice()) / point.getOpenPrice() * 100);
        strings.add(format + "%");

        String format1 = getValueFormatter().format(point.getClosePrice() - point.getOpenPrice());
        strings.add(format1);

        if (point instanceof KLineEntity) {
            strings.add(new BigDecimal(((KLineEntity) point).Volume).setScale(4, RoundingMode.FLOOR).stripTrailingZeros().toPlainString());
        }

        width += ViewUtil.dp2px(mContext, 101);

        float x = view.translateXtoX(view.getX(index));
        if (x > view.getChartWidth() / 2f) {
            left = margin;
        } else {
            left = view.getChartWidth() - width - margin;
        }

        RectF r = new RectF(left, top, left + width, top + height);
        canvas.drawRoundRect(r, padding, padding, mSelectorBackgroundPaint);
        canvas.drawRoundRect(r, padding, padding, bgStokePaint);
        float y = top + padding + (textHeight - metrics.bottom - metrics.top) / 2;

        for (int i = 0; i < selectTitle.length; i++) {
            canvas.drawText(selectTitle[i], left + padding, y, mSelectorTextPaint);

            String value = strings.get(i);
            float startX = left + width - padding - valuePaint.measureText(value);

            if (i == 5) {
                if (point.getClosePrice() > point.getOpenPrice()) {
                    valuePaint.setColor(ViewUtil.getIncreaseColor(context));
                } else if (point.getClosePrice() < point.getOpenPrice()) {
                    valuePaint.setColor(ViewUtil.getDecreaseColor(context));
                }
            } else if (i == 7) {
                resetViewPaintColor();
            }
            canvas.drawText(value, startX, y, valuePaint);
            y += textHeight + padding;
        }

    }

    /**
     * 设置蜡烛宽度
     *
     * @param candleWidth
     */
    public void setCandleWidth(float candleWidth) {
        mCandleWidth = candleWidth;
    }

    /**
     * 设置蜡烛线宽度
     *
     * @param candleLineWidth
     */
    public void setCandleLineWidth(float candleLineWidth) {
        mCandleLineWidth = candleLineWidth;
    }

    /**
     * 设置ma5颜色
     *
     * @param color
     */
    public void setMa5Color(int color) {
        this.ma5Paint.setColor(color);
    }

    /**
     * 设置ma10颜色
     *
     * @param color
     */
    public void setMa10Color(int color) {
        this.ma10Paint.setColor(color);
    }

    /**
     * 设置ma30颜色
     *
     * @param color
     */
    public void setMa30Color(int color) {
        this.ma30Paint.setColor(color);
    }

    /**
     * 设置ma50颜色
     *
     * @param color
     */
    public void setMa50Color(int color) {
        this.ma50Paint.setColor(color);
    }

    /**
     * 设置选择器文字颜色
     *
     * @param color
     */
    public void setSelectorTextColor(int color) {
        mSelectorTextPaint.setColor(color);
    }

    /**
     * 设置选择器文字大小
     *
     * @param textSize
     */
    public void setSelectorTextSize(float textSize) {
        mSelectorTextPaint.setTextSize(textSize);
    }

    /**
     * 设置选择器背景
     *
     * @param color
     */
    public void setSelectorBackgroundColor(int color) {
        mSelectorBackgroundPaint.setColor(color);
    }

    /**
     * 设置曲线宽度
     */
    public void setLineWidth(float width) {
        ma5Paint.setStrokeWidth(width);
        ma10Paint.setStrokeWidth(width);
        ma30Paint.setStrokeWidth(width);
        ma50Paint.setStrokeWidth(width);
        mLinePaint.setStrokeWidth(width);
    }

    /**
     * 设置文字大小
     */
    public void setTextSize(float textSize) {
        ma5Paint.setTextSize(textSize);
        ma10Paint.setTextSize(textSize);
        ma30Paint.setTextSize(textSize);
        ma50Paint.setTextSize(textSize);
    }

    /**
     * 蜡烛是否实心
     */
    public void setCandleSolid(boolean candleSolid) {
        mCandleSolid = candleSolid;
    }

    public boolean isLine() {
        return isLine;
    }

    public void setLine(boolean line) {
        if (isLine != line) {
            isLine = line;
            if (isLine) {
                kChartView.setCandleWidth(kChartView.dp2px(7f));
            } else {
                kChartView.setCandleWidth(kChartView.dp2px(6f));
            }
        }
    }
}
