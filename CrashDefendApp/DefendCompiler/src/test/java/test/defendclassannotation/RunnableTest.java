package test.defendclassannotation;

import com.efurture.wireless.defend.annotation.Defend;

@Defend
public class RunnableTest  implements  Runnable{


    public void run() {
        throw new RuntimeException("Hello world Test");
    }
}
