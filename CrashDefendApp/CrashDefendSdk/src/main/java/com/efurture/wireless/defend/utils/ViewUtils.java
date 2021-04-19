package com.efurture.wireless.defend.utils;

import android.view.View;
import com.efurture.wireless.defend.lang.RunnableSafeWrapper;

public class ViewUtils {


    /**
     * 在主线程安全执行线程回调, 即使action执行过程中发生异常，也能保障执行的顺畅。
     * */
    public static final void safePost(View view,  Runnable action){
        if(view == null || action == null){
            return;
        }
        view.post(RunnableSafeWrapper.safeWrap(action));
    }


    /**
     * 在主线程安全执行线程回调, 即使action执行过程中发生异常，也能保障执行的顺畅。
     * */
    public static final void safePostDelayed(View view, Runnable action, long delayMillis){
        if(view == null || action == null){
            return;
        }
        view.postDelayed(RunnableSafeWrapper.safeWrap(action), delayMillis);
    }



    /**
     * 在主线程安全执行线程回调, 即使action执行过程中发生异常，也能保障执行的顺畅。
     * */
    public static final void safePostOnAnimation(View view, Runnable action, long delayMillis){
        if(view == null || action == null){
            return;
        }
        view.postOnAnimation(RunnableSafeWrapper.safeWrap(action));
    }

    /**
     * 在主线程安全执行线程回调, 即使action执行过程中发生异常，也能保障执行的顺畅。
     * */
    public static final void safePostOnAnimationDelayed(View view, Runnable action, long delayMillis){
        if(view == null || action == null){
            return;
        }
        view.postOnAnimationDelayed(RunnableSafeWrapper.safeWrap(action), delayMillis);
    }
}
