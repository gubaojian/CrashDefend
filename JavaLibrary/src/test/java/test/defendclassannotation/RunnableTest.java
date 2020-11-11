package test.defendclassannotation;

import com.cainiao.wireless.crashdefend.annotation.Defend;

@Defend
public class RunnableTest  implements  Runnable{


    public void run() {
        throw new RuntimeException("Hello world Test");
    }
}
