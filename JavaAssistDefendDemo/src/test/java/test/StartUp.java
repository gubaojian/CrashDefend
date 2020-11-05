package test;


import com.cainiao.wireless.crashdefend.Defend;

@Defend
public class StartUp {


    @Defend
    public static void doTaskOne(){
        // some worker code
        throw new RuntimeException("Hello World Defend");
    }

    public static void doTaskTwo(){
        // some worker code
        throw new RuntimeException("Hello Defend");
    }

    public static void doTaskThree(){
        // some worker code
        throw new RuntimeException("Hello Defend");
    }

}
