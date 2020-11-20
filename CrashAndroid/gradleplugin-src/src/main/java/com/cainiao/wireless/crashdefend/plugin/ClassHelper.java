package com.cainiao.wireless.crashdefend.plugin;

public class ClassHelper {

    private static  Class<?> DefendClass = null;
    private static  Class<?> DefendIgnoreClass = null;

    public static Class getDefendAnnotationClass(){
        if(DefendClass == null){
            try {
                DefendClass =   Thread.currentThread().getContextClassLoader().loadClass("com.cainiao.wireless.crashdefend.annotation.Defend");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        return DefendClass;
    }


    public static  Class getDefendIgnoreAnnotationClass(){
        if(DefendIgnoreClass == null){
            try {
                DefendIgnoreClass = Thread.currentThread().getContextClassLoader().loadClass("com.cainiao.wireless.crashdefend.annotation.DefendIgnore");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        return DefendIgnoreClass;
    }
}
