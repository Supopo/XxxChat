package com.xxx.xxx.common.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.Random;


public class AnimationTools {
    public static void cancleAnim(View view) {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", -10, 10);
        translationX.setDuration(50);
        translationX.setRepeatMode(ValueAnimator.REVERSE);
        translationX.setRepeatCount(4);
        translationX.start();

    }


    public static void breathAnimote(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.06f, 1f);
        scaleX.setRepeatCount(ValueAnimator.INFINITE);
        scaleX.setRepeatMode(ObjectAnimator.REVERSE);
        scaleX.setInterpolator(new LinearInterpolator());
        scaleX.setDuration(4000);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.2f, 1f);
        scaleY.setRepeatCount(ValueAnimator.INFINITE);
        scaleY.setRepeatMode(ObjectAnimator.REVERSE);
        scaleY.setInterpolator(new LinearInterpolator());
        scaleY.setDuration(4000);
        scaleY.start();
        scaleX.start();
    }

    public static void obtainAnimo(View view, View vv, Animator.AnimatorListener animatorListener) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int[] location2 = new int[2];
        vv.getLocationOnScreen(location2);
        AnimatorSet animationSet = new AnimatorSet();
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", -(location[0] - location2[0]));
        translationX.setInterpolator(new LinearInterpolator());
        translationX.setDuration(700);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", -(location[1] - location2[1]));
        translationY.setInterpolator(new LinearInterpolator());
        translationY.setDuration(700);
        animationSet.playTogether(translationX, translationY);
        animationSet.addListener(animatorListener);
        animationSet.start();
    }

    public static void upDownAnimation(View view) {
        final ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", view.getPivotY() + 10, view.getPivotY() - 10);
        translationY.setInterpolator(new LinearInterpolator());
        translationY.setDuration(1000);
        translationY.setRepeatMode(ValueAnimator.REVERSE);
        translationY.setRepeatCount(ValueAnimator.INFINITE);
        //        new Handler().postDelayed(()-> translationY.start(),(long) (1000*getRandom()));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                translationY.start();
            }
        }, (long) (1000 * getRandom()));
    }

    private static float getRandom() {
        Random random = new Random();
        return random.nextFloat();
    }
}
