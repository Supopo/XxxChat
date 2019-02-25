package com.hongyu.zorelib.utils;

import android.content.Context;

/**
 * Created by Administrator on 2017/5/4.
 */

public class SaveMain {

    private static Context mContext;

    public static void save(Context context){
        mContext = context;
    }

    public static Context getmContext() {
        return mContext;
    }
}
