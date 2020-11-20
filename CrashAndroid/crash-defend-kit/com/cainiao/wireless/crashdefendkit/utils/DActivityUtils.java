package com.cainiao.wireless.crashdefendkit.utils;

import android.app.Activity;
import android.os.Build;

import com.cainiao.wireless.crashdefendkit.CrashDefendKit;
import com.cainiao.wireless.crashdefendkit.lang.SafeRunnable;

public class DActivityUtils {



    /**
     * 判断Activity是否有效。
     * */
    public static final boolean isActivityValid(Activity activity){
        if(activity == null){
            return false;
        }
        if(activity.isFinishing()){
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if(activity.isDestroyed()){
                return false;
            }
        }
        return true;
    }


    /**
     * 在主线程安全执行线程回调, 即使action执行过程中发生异常，也能保障执行的顺畅。
     * */
    public static final void safeRunOnUiThread(Activity activity, final Runnable action){
        if(activity == null || action == null){
            return;
        }
        if(action instanceof SafeRunnable) {
            activity.runOnUiThread(action);
        }else{
            Runnable safeRunnable = new Runnable() {
                @Override
                public void run() {
                    try{
                        action.run();
                    }catch (Exception e){
                        CrashDefendKit.onCrash(DActivityUtils.class.getName(), e);
                    }
                }
            };
            activity.runOnUiThread(safeRunnable);
        }
    }


    /**
     *
     * */
}
