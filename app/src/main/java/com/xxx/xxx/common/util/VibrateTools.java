package com.xxx.xxx.common.util;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;

public class VibrateTools {

    public  static void shortVibrate(Activity context){
        Vibrator mVibrator = (Vibrator) context.getApplication().getSystemService(Service.VIBRATOR_SERVICE);
        mVibrator.vibrate(new long[]{100,10,100,100},-1);
    }
}
