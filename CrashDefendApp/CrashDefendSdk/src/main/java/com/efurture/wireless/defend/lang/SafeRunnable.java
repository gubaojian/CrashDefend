package com.efurture.wireless.defend.lang;

import com.efurture.wireless.defend.DefendReporter;


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
            DefendReporter.onCrash(e);
        }
    }

    public abstract void safeRun();
}
