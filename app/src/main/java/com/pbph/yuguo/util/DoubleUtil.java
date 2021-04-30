package com.pbph.yuguo.util;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2018/8/7 0007.
 */

public class DoubleUtil {
    public static String doubleToString(double num){
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }
}
