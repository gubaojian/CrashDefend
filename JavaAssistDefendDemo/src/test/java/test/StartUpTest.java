package test;


import com.cainiao.wireless.crashdefend.Defend;

@Defend
public class StartUpTest {

    @Defend
    public static void doTaskOne(){
        // some worker code
        throw new RuntimeException("Hello World Defend");
    }
}
