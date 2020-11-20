package com.cainiao.wireless.crashdefendkit.utils;

import android.view.View;
import com.cainiao.wireless.crashdefendkit.lang.SafeRunnableWrapper;

public class DViewUtils {


    /**
     * 在主线程安全执行线程回调, 即使action执行过程中发生异常，也能保障执行的顺畅。
     * */
    public static final void safePost(View view,  Runnable action){
        if(view == null || action == null){
            return;
        }
        view.post(SafeRunnableWrapper.safeWrap(action));
    }


    /**
     * 在主线程安全执行线程回调, 即使action执行过程中发生异常，也能保障执行的顺畅。
     * */
    public static final void safePostDelayed(View view, Runnable action, long delayMillis){
        if(view == null || action == null){
            return;
        }
        view.postDelayed(SafeRunnableWrapper.safeWrap(action), delayMillis);
    }



    /**
     * 在主线程安全执行线程回调, 即使action执行过程中发生异常，也能保障执行的顺畅。
     * */
    public static final void safePostOnAnimation(View view, Runnable action, long delayMillis){
        if(view == null || action == null){
            return;
        }
        view.postOnAnimation(SafeRunnableWrapper.safeWrap(action));
    }

    /**
     * 在主线程安全执行线程回调, 即使action执行过程中发生异常，也能保障执行的顺畅。
     * */
    public static final void safePostOnAnimationDelayed(View view, Runnable action, long delayMillis){
        if(view == null || action == null){
            return;
        }
        view.postOnAnimationDelayed(SafeRunnableWrapper.safeWrap(action), delayMillis);
    }
}
