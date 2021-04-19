package test;

public class DefendTestDemo {

    public void testDefend(){
        new Thread(new Runnable() {
            public void run() {
                throw new RuntimeException("Hello World Crash Defend Demo");
            }
        }).start();
    }
}
