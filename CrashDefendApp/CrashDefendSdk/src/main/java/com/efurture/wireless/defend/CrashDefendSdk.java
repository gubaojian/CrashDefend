package com.efurture.wireless.defend;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;


import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * 有了字节码编译处理防护技术，这些hook基本不需要，仅做参考
 * */
/**
 * CrashDefendSDK初始化总入口
 * */
public class CrashDefendSdk {

    private Context mContext;
    private OnCatchCrashListener mOnCatchCrashListener;
    private static CrashDefendSdk mCrashDefendSdk;
    private Handler mHander;

    public CrashDefendSdk(){
        mHander = new Handler(Looper.getMainLooper());
    }


    public static CrashDefendSdk getInstance(){
        if(mCrashDefendSdk == null){
            synchronized (CrashDefendSdk.class){
                if(mCrashDefendSdk == null){
                    mCrashDefendSdk = new CrashDefendSdk();
                }
            }
        }
        return mCrashDefendSdk;
    }

    public Context getContext() {
        return mContext;
    }

    public CrashDefendSdk setContext(Context mContext) {
        this.mContext = mContext;
        return this;
    }

    public OnCatchCrashListener getOnCatchCrashListener() {
        return mOnCatchCrashListener;
    }

    public CrashDefendSdk setOnCatchCrashListener(OnCatchCrashListener mOnCatchCrashListener) {
        this.mOnCatchCrashListener = mOnCatchCrashListener;
        return this;
    }

    /**
     * CrashDefendSDK和CrashDefendKit初始化总入口
     * */
    public void init(){
        if(mContext == null){
            return;
        }
        mContext = mContext.getApplicationContext();
        OnCatchCrashListener onCatchCrashListenerWrapper = new OnCatchCrashListener() {
            @Override
            public void onCatch(Throwable throwable) {
                if(throwable != null && isDebug() && mContext != null){
                    final StringWriter stringWriter = new StringWriter();
                    PrintWriter  messageWriter = new PrintWriter(stringWriter);
                    throwable.printStackTrace(messageWriter);
                    messageWriter.flush();
                    doToast(stringWriter.toString());
                    messageWriter.close();
                }
                if(mOnCatchCrashListener != null){
                    mOnCatchCrashListener.onCatch(throwable);
                }
            }
        };
        DefendReporter.getInstance().setOnCatchCatchListener(onCatchCrashListenerWrapper);
    }

    private  boolean isDebug() {
        try {
            if(mContext == null){
                return false;
            }
            ApplicationInfo info = mContext.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    private void doToast(String content){
        mHander.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
