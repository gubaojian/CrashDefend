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



    
    public static void onCatch(Throwable e){

    }
}
