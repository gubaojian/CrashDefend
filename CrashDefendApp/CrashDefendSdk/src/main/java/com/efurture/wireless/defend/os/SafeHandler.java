package com.efurture.wireless.defend.os;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.efurture.wireless.defend.DefendReporter;


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
            DefendReporter.onCrash(e);
        }
    }
}
