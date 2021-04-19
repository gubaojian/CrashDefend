package com.cainiao.wireless.crashdefendkit.lang;

public class SafeRunnableWrapper {


    /**
     * 包装Runnable 对象，返回安全执行的Runnale对象。
     * */
    public static SafeRunnable safeWrap(final Runnable action){
        if(action instanceof SafeRunnable) {
            return (SafeRunnable) action;
        }else{
            SafeRunnable safeRunnable = new SafeRunnable() {
                @Override
                public void safeRun() {
                    action.run();
                }
            };
            return  safeRunnable;
        }
    }
}
