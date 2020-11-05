package test.defendinterfaceimpl;

import com.cainiao.wireless.crashdefend.Defend;
import com.cainiao.wireless.crashdefend.DefendIgnore;

public class RunnableTest {

    public void testDefend(){
            new Thread(new Runnable() {
                public void run() {
                    throw new RuntimeException("Hello World Crash");
                }
            }).start();
    }
}
