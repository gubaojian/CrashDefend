package com.efurture.wireless.defend.utils;

import android.app.ActivityManager;
import android.content.Context;

public class ProcessUtils {

    public static boolean isMainProcess(Context context) {
        String procName = getCurrentProcessName(context);
        return procName == null || procName.equalsIgnoreCase(context.getPackageName());
    }

    public static String getCurrentProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
