package com.efurture.wireless.defend;

/**
 * 拦截异常上报接口
 * */
public class DefendReporter {

    private static DefendReporter mDefendReporter;

    public static DefendReporter getInstance(){
        if(mDefendReporter == null) {
            synchronized (DefendReporter.class) {
                if(mDefendReporter == null) {
                    mDefendReporter = new DefendReporter();
                }
            }
        }
        return mDefendReporter;
    }

    /**
     * 捕获异常的回调
     * */
    public static void onCatch(Throwable e){
        DefendReporter defendReporter = getInstance();
        OnCatchCrashListener onCatchCrashListener = defendReporter.getOnCatchCatchListener();
        if(onCatchCrashListener != null){
            onCatchCrashListener.onCatch(e);
        }
    }

    public static void onCrash(Throwable e){
        onCatch(e);
    }


    public OnCatchCrashListener getOnCatchCatchListener() {
        return mOnCatchCrashListener;
    }

    public void setOnCatchCatchListener(OnCatchCrashListener mOnCatchCrashListener) {
        this.mOnCatchCrashListener = mOnCatchCrashListener;
    }

    private OnCatchCrashListener mOnCatchCrashListener;
}
