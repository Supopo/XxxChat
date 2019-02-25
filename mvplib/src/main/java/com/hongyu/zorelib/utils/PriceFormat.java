package com.hongyu.zorelib.utils;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/4/8.
 */

public class PriceFormat {
    private static DecimalFormat df = new DecimalFormat("##0.00");

    public static String format(double price) {
       return df.format(price);
    }
}
