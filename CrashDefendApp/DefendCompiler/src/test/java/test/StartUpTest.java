package test;


import com.efurture.wireless.defend.annotation.Defend;

@Defend
public class StartUpTest {

    @Defend
    public static void doTaskOne(){
        // some worker code
        throw new RuntimeException("Hello World Defend");
    }
}
