package com.efurture.wireless.defend.lang;


import android.os.AsyncTask;

/**
 * 执行异步任务
 * */
public class BgThread {

    public static void post(final Runnable runnable){
        if(runnable instanceof  SafeRunnable){
            AsyncTask.THREAD_POOL_EXECUTOR.execute(runnable);
        }else{
            SafeRunnable safeRunnable = new SafeRunnable() {
                @Override
                public void safeRun() {
                    if(runnable != null){
                        runnable.run();
                    }
                }
            };
            AsyncTask.THREAD_POOL_EXECUTOR.execute(safeRunnable);
        }
    }

}
