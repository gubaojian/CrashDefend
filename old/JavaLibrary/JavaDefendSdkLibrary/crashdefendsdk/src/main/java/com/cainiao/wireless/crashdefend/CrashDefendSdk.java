package com.cainiao.wireless.crashdefend;

public class CrashDefendSdk {

    private static CrashDefendSdk mCrashDefendSdk;

    public static CrashDefendSdk getInstance(){
        if(mCrashDefendSdk == null) {
            synchronized (CrashDefendSdk.class) {
                if(mCrashDefendSdk == null) {
                    mCrashDefendSdk = new CrashDefendSdk();
                }
            }
        }
        return mCrashDefendSdk;
    }

    /**
     * 捕获异常的回调
     * */
    public static void onCatch(Throwable e){
        CrashDefendSdk crashDefendSdk = getInstance();
        OnDefendCatchListener onDefendCatchListener = crashDefendSdk.getOnDefendCatchListener();
        if(onDefendCatchListener != null){
            onDefendCatchListener.onCatch(e);
        }
    }

    public OnDefendCatchListener getOnDefendCatchListener() {
        return mOnDefendCatchListener;
    }

    public void setOnDefendCatchListener(OnDefendCatchListener mOnDefendCatchListener) {
        this.mOnDefendCatchListener = mOnDefendCatchListener;
    }

    private OnDefendCatchListener mOnDefendCatchListener;
}
