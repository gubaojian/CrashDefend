package com.efurture.wireless.defend.asm;

public class BizCallTest {

    public  void doBiz(){
        ViewUtils viewUtils = new ViewUtils();
        Runnable a = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello world");
            }
        };
        Object c = viewUtils.post(a);
        System.out.println(c);

        ViewUtils.staticPost(a);
    }


    public  void doBiz2(){
        System.out.println("hello world");
        ViewUtils viewUtils = new ViewUtils();
        Runnable a = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello world");
            }
        };
        viewUtils.post(a);
    }


    public  void doBiz3(){
        System.out.println("hello world");
        ViewUtils viewUtils = new ViewUtils();
        Runnable a = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello world");
            }
        };
        viewUtils.doExp(a);
    }


}
