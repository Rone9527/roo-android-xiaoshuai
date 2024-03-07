package com.github.fujianlian.klinechart.draw;

/**
 * @author fujianlian Created on 2018/8/20 11:09
 * @descripe MainDraw当前子视图
 */

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        Status.MA,
        Status.NONE,
        Status.BOLL,
})
@Retention(RetentionPolicy.SOURCE)
public @interface Status {
    int MA = 1;
    int BOLL = 2;
    int NONE = 3;
}
