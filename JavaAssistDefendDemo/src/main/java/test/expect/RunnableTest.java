package test.expect;

public class RunnableTest {

    public void testDefend(){
            new Thread(new Runnable() {
                public void run() {
                    System.out.println("DefendTest DefendTest");
                }
            }).start();
    }
}
