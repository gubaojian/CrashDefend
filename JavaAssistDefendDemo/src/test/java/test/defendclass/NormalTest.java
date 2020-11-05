package test.defendclass;

public class NormalTest {

    public void testDefend(){
        throw new RuntimeException("Hello World Crash");
    }


    public void helloDefend(){
        throw new RuntimeException("Hello World Crash");
    }

    public void noneDefend(){
        throw new RuntimeException("Hello World Crash");
    }
}
