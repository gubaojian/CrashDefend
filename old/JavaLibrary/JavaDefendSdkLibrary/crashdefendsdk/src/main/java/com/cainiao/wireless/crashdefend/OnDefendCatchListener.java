package com.cainiao.wireless.crashdefend;


/**
 * 捕获列表
 * */
public interface OnDefendCatchListener {
    
    /**
     * 捕获异常列表
     * */
    public void onCatch(Throwable throwable);
}
