package com.efurture.wireless.defend.plugin;

public class ClassHelper {


    private static  Class<?> DefendClass = null;

    private static  Class<?> DefendIgnoreClass = null;


    public static Class getDefendAnnotationClass(){
        if(DefendClass == null){
            try {
                DefendClass =   Thread.currentThread().getContextClassLoader().loadClass("com.efurture.wireless.defend.annotation.Defend");
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
                DefendIgnoreClass = Thread.currentThread().getContextClassLoader().loadClass("com.efurture.wireless.defend.annotation.DefendIgnore");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        return DefendIgnoreClass;
    }
}
