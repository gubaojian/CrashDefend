package com.efurture.wireless.defend.utils;

import android.os.StrictMode;

public class StrictModeUtils {


    /**
     * enable strict mode
     * */
    public static void enableAllStrictMode(){
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().build());
    }

}
