package test;

import com.cainiao.wireless.crashdefend.annotation.Defend;
import com.cainiao.wireless.crashdefend.annotation.DefendIgnore;

public class RunnableTest {

    @Defend
    public void testDefend(){
            new Thread(new Runnable() {
                @Defend
                public void run() {
                    throw new RuntimeException("Hello World Crash");
                }
            }).start();
    }


    public Object testReturnObject(){
        return  null;//
    }


    @DefendIgnore
    public Object testDefendIgnore(){
        return  null;//
    }


    public Object testExcludePackages(){
        return  null;//
    }


    public native void testNative();



    private void noInsertCode(){

    }
}
