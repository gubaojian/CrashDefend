package test.defendauto;

public class RunnableTest {

    public void testDefend(){
            new Thread(new Runnable() {
                public void run() {
                    throw new RuntimeException("Hello World Crash");
                }
            }).start();
    }
}
