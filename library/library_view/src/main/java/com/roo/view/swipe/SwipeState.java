package com.roo.view.swipe;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by guanaj on 2017/6/6.
 */
@IntDef({SwipeState.LEFTOPEN, SwipeState.RIGHTOPEN, SwipeState.CLOSE})
@Retention(RetentionPolicy.SOURCE)
public @interface SwipeState {
    int LEFTOPEN = 100;
    int RIGHTOPEN = 101;
    int CLOSE = 102;
    int NULL = 0;
}