package com.codingbaby;

import android.content.Context;
import android.util.TypedValue;

public class Unit {

    /**
     * dp转px
     *
     * @param dp dp值
     * @return px值
     */
    public static int dp2px(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
}
