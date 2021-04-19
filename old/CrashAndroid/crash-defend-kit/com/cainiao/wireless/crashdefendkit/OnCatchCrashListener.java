package com.cainiao.wireless.crashdefendkit;


/**
 * Crash回调监听器。
 * */
public interface OnCatchCrashListener {

    public void onCatch(String category, Exception e);
}
