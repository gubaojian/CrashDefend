package com.cainiao.wireless.crashdefendkit;

import android.content.Context;

/**
 * CrashDefendKit 防护初始化SDK入口，
 * 内置多种安全防护解决方案。
 * */
public class CrashDefendKit {


    private Context mContext;
    private OnCatchCrashListener mOnCrashListener;


    /**
     * CrashDefendSdk 单例对象
     * */
    private static CrashDefendKit mCrashDefendSdk;

    /**
     * 返回 CrashDefendSdk 单例对象
     * */
    public static CrashDefendKit getInstance(){
        if(mCrashDefendSdk == null){
            synchronized (CrashDefendKit.class){
                if(mCrashDefendSdk == null){
                    mCrashDefendSdk = new CrashDefendKit();
                }
            }
        }
        return mCrashDefendSdk;
    }

    public Context getContext() {
        return mContext;
    }

    public CrashDefendKit setContext(Context context) {
        this.mContext = context;
        return this;
    }

    public OnCatchCrashListener getOnCrashListener() {
        return mOnCrashListener;
    }

    public CrashDefendKit setOnCatchCrashListener(OnCatchCrashListener mOnCrashListener) {
        this.mOnCrashListener = mOnCrashListener;
        return this;
    }


    public static final void  onCrash(Class category, Exception e){
        if(category == null){
            return;
        }
        onCrash(category.getName(), e);
    }


    /**
     * 所有crash回调的入口
     * */
    public static final void  onCrash(String category, Exception e){
        synchronized (CrashDefendKit.class){
            OnCatchCrashListener onCrashListener = getInstance().getOnCrashListener();
            if(onCrashListener != null){
                onCrashListener.onCatch(category, e);
            }
        }
    }
}
