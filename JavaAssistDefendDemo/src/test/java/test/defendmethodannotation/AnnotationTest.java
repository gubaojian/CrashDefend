package test.defendmethodannotation;

import com.cainiao.wireless.crashdefend.Defend;

public class AnnotationTest {

    @Defend
    public void testDefend(){
        throw new RuntimeException("Hello World Crash");
    }


    @Defend
    public void helloDefend(){
        throw new RuntimeException("Hello World Crash");
    }

    public void noneDefend(){
        throw new RuntimeException("Hello World Crash");
    }
}
