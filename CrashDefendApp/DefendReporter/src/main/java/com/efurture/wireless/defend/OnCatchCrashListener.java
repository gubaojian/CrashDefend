package com.efurture.wireless.defend;


/**
 * 捕获列表
 * */
public interface OnCatchCrashListener {
    
    /**
     * 捕获异常列表
     * */
    public void onCatch(Throwable throwable);
}
