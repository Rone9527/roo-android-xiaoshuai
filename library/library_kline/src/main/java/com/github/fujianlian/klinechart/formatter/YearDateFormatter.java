package com.github.fujianlian.klinechart.formatter;

import com.github.fujianlian.klinechart.base.IDateTimeFormatter;
import com.github.fujianlian.klinechart.utils.DateUtil;

import java.util.Date;

/**
 * 时间格式化器
 * Created by tifezh on 2016/6/21.
 */

public class YearDateFormatter implements IDateTimeFormatter {
    @Override
    public String format(Date date) {
        if (date != null) {
            return DateUtil.YEAR_DATE_FORMAT.format(date);
        } else {
            return "";
        }
    }
}
