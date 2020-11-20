package com.cainiao.wireless.crashdefendkit.lang;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.cainiao.wireless.crashdefendkit.os.SafeHandler;

/**
 * Helper class for managing the background thread used to perform io operations
 * and handle async broadcasts.
 */
public final class AsyncHandler {
    private static final HandlerThread sHandlerThread = new HandlerThread("AsyncHandler");
    private static final Handler sHandler;
    static {
        sHandlerThread.start();
        sHandler = new SafeHandler(sHandlerThread.getLooper());
    }

    public static void post(Runnable r) {
        sHandler.post(r);
    }

    public static   HandlerThread getAsyncHandlerThread(){
        return sHandlerThread;
    }


    public static Looper getAsycLooper(){
        return sHandlerThread.getLooper();
    }


    private AsyncHandler() {}
}