package com.cainiao.wireless.crashdefendkit.os;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.cainiao.wireless.crashdefendkit.CrashDefendKit;

public class SafeHandler extends Handler {


    public SafeHandler() {
        super();
    }

    public SafeHandler(Callback callback) {
        super(callback);
    }

    public SafeHandler(Looper looper) {
        super(looper);
    }

    public SafeHandler(Looper looper, Callback callback) {
        super(looper, callback);
    }

    @Override
    public final void dispatchMessage(Message msg) {
        try{
            super.dispatchMessage(msg);
        }catch (Exception e){
            CrashDefendKit.onCrash(SafeHandler.class.getName(), e);
        }
    }
}
