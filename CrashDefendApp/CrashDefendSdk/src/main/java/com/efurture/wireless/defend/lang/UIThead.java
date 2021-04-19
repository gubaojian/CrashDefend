package com.efurture.wireless.defend.lang;

import android.os.Looper;

import com.efurture.wireless.defend.os.SafeHandler;


/**
 * 主线程安全执行容器
 * */
public class UIThead {


    /**
     * 安全执行容器
     * */
    private static SafeHandler mSafeHandler = new SafeHandler(Looper.getMainLooper());

    /**
     * 抛出异常
     * */
    public static void post(Runnable run){
        mSafeHandler.post(run);
    }
}
