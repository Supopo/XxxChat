package com.hongyu.zorelib.utils;

import android.app.Activity;

import java.util.Stack;

public class ActivityManage {
    /**
     * add Activity 添加Activity到栈
     */
    // 存放activity
    private static Stack<Activity> activityStack;

    public static void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 退出应用程序
     */
    public static void AppExit() {
        try {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
        } catch (Exception ignored) {
        }
    }

    public static void finishSingleActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        if (activityStack == null) {
            return;
        }
        if (activityStack.size() == 0) {
            return;
        }
        if (activityStack.contains(activity)) {
            activityStack.remove(activity);
        }
    }
}
