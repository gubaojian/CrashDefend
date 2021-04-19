package com.efurture.wireless.defend.app;

import android.os.Handler;

import com.efurture.wireless.defend.DefendReporter;

import java.lang.reflect.Field;


/**
 * 有了字节码编译处理防护技术，这些hook基本不需要，仅做参考
 * */
public class ActivityThreadHook {

    public static void hook(){
        try{
            Class activityThreadClassName = ActivityThreadHook.class.getClassLoader().loadClass("android.app.ActivityThread");
            Field sCurrentActivityThreadField = activityThreadClassName.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            Object activityThreadThis = sCurrentActivityThreadField.get(null);

            Field mHField = activityThreadClassName.getDeclaredField("mH");
            mHField.setAccessible(true);
            Handler mH  = (Handler) mHField.get(activityThreadThis);
            Field callbackField = Handler.class.getDeclaredField("mCallback");
            callbackField.setAccessible(true);
            Handler.Callback  callback = (Handler.Callback) callbackField.get(mH);
            if(!(callback instanceof HCallback)) {
                HCallback hCallback = new HCallback(callback, mH);
                callbackField.set(mH, hCallback);
            }
        }catch (Exception e){
            DefendReporter.onCatch(e);
        }

    }
}
