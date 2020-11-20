package com.cainiao.wireless.crashdefendkit.lang;

import com.cainiao.wireless.crashdefendkit.CrashDefendKit;


/***
 * 通用Runnable任务预防类
 * @author  剑白
 * @date  2020/08/14
 * */
public abstract class SafeRunnable implements Runnable {

    @Override
    public  final void run() {
        try{
            safeRun();
        }catch (Exception e){
            CrashDefendKit.onCrash(SafeRunnable.class.getName(), e);
        }
    }

    public abstract void safeRun();
}
