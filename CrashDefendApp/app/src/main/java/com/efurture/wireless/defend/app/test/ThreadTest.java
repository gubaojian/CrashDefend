package com.efurture.wireless.defend.app.test;

public class ThreadTest extends Thread{

    @Override
    public void run(){
        super.run();
        throw new RuntimeException("ThreadTest");
    }
}
