package com.hongyu.zorelib.utils;

/**
 * Created by Administrator on 2017/5/4.
 */

public class StartTools {
    public static int limited(int grade){
        grade=grade/5;
        if (grade>10){
            limited(grade);
        }
        return grade;
    }
}
