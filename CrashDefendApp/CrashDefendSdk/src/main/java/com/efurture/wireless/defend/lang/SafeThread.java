package com.efurture.wireless.defend.lang;


import androidx.annotation.NonNull;

import com.efurture.wireless.defend.DefendReporter;


/***
 * 通用Thread线程异常处理类
 * @author  剑白
 * @date  2020/08/14
 * */
public class SafeThread extends Thread{

    public SafeThread(Runnable target) {
        super(target);
    }

    public SafeThread(ThreadGroup group, Runnable target) {
        super(group, target);
    }

    public SafeThread(Runnable target, String name) {
        super(target, name);
    }

    public SafeThread(ThreadGroup group, Runnable target, @NonNull String name) {
        super(group, target, name);
    }

    public SafeThread(ThreadGroup group, Runnable target, @NonNull String name, long stackSize) {
        super(group, target, name, stackSize);
    }


    @Override
    public void run() {
        try {
            super.run();
        }catch (Exception e){
            DefendReporter.onCrash(e);
        }
    }
}
