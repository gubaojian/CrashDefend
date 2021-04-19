package com.efurture.wireless.defend.app;


import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.efurture.wireless.defend.DefendReporter;

import java.util.HashSet;
import java.util.Set;

/**
 * 防护Activity生命周期异常
 * */
public class HCallback implements Handler.Callback {

    Handler.Callback originCallback;
    Handler mH;

    public HCallback(Handler.Callback originCallback, Handler mH) {
        this.originCallback = originCallback;
        this.mH = mH;
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        try{
            if(originCallback != null){
                return originCallback.handleMessage(msg);
            }else{
                if(mH != null){
                    mH.handleMessage(msg);
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            if(isIgnoreException(e)) {
               DefendReporter.onCrash(e);
            }
            return true;
        }
    }

    /**
     * 忽略的异常列表
     * */
    public static Set<String> ignoreExceptions = new HashSet<>();
    static {
        ignoreExceptions.add(NullPointerException.class.getName());
        ignoreExceptions.add("android.app.RemoteServiceException");
        ignoreExceptions.add("android.os.DeadObjectException");
        ignoreExceptions.add(IllegalStateException.class.getName());
    }

    public static boolean isIgnoreException(Exception e){
        if(ignoreExceptions.contains(e.getClass().getName())){
            return true;
        }
        return false;
    }
}
