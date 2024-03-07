package com.github.fujianlian.klinechart.formatter;

import com.github.fujianlian.klinechart.base.IValueFormatter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Value格式化类
 * Created by tifezh on 2016/6/21.
 */

public class ValueFormatter implements IValueFormatter {
    @Override
    public String format(float value) {
        return new BigDecimal(value).setScale(2, RoundingMode.FLOOR).stripTrailingZeros().toPlainString();
    }
}
