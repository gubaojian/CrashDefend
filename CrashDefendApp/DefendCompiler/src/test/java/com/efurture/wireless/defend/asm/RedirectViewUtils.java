package com.efurture.wireless.defend.asm;

public class RedirectViewUtils {




    public static boolean needRedirect(Object thisObject, String method, Object... args){
        return  false;
    }

    public static Object redirect(Object thisObject, String method, Object... args){
        System.out.println("RedirectViewUtils redirect view utils " + method);
        return null;
    }
}
