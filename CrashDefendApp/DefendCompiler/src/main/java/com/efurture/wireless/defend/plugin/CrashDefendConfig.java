package com.efurture.wireless.defend.plugin;

import com.efurture.wireless.defend.plugin.config.domain.DefendConfig;
import com.efurture.wireless.defend.plugin.config.domain.RedirectMethodCall;
import com.efurture.wireless.defend.plugin.config.domain.TryCatchMethodCall;

import java.util.List;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;

public class CrashDefendConfig {


    /**
     * 默认配置代码
     * */
    public static DefendConfig defendConfig = new DefendConfig();

    /**
     * DefendAuto实现处理。
     * */
    public static boolean isDefendMethod(CtClass ctClass, CtMethod ctMethod){
        /**
         * 特殊方法过滤
         * */
        if(ctClass.isAnnotation()){
            return false;
        }
        if(ctClass.isArray()){
            return false;
        }
        if(Modifier.isNative(ctMethod.getModifiers())){
            return false;
        }
        if(Modifier.isAbstract(ctMethod.getModifiers())){
            return false;
        }
        try {
           Class<?> defendIgnoreClass =  ClassHelper.getDefendIgnoreAnnotationClass();
           if(defendIgnoreClass != null){
               Object defendIgnore = ctMethod.getAnnotation(defendIgnoreClass);
               if(defendIgnore != null){
                   return  false;
               }
           }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return defendConfig.isDefend(ctClass, ctMethod);
    }


    public static boolean hasTryCatchMethodCall(){
        List<TryCatchMethodCall> tryCatchMethodCallList = defendConfig.getTryCatchMethodCallList();
        if(tryCatchMethodCallList == null){
            return false;
        }
        if(tryCatchMethodCallList.size() == 0){
            return false;
        }
        return  true;
    }

    public static boolean hasRedirectMethodCall(){
        List<RedirectMethodCall> redirectMethodCallList = defendConfig.getRedirectMethodCallList();
        if(redirectMethodCallList == null || redirectMethodCallList.size() == 0){
            return false;
        }
        return true;
    }


    public static boolean isTryCatchMethodCall(CtClass ctClass, String methodName){
        List<TryCatchMethodCall> tryCatchMethodCallList =  defendConfig.getTryCatchMethodCallList();
        if(tryCatchMethodCallList == null){
            return false;
        }
        for(TryCatchMethodCall tryCatchMethodCall : tryCatchMethodCallList){
            if(tryCatchMethodCall.isDefend(ctClass, methodName)){
                return true;
            }
        }
        return false;
    }

    public static boolean isRedirectMethodCall(CtClass ctClass, String methodName){
        List<RedirectMethodCall> redirectMethodCallList = defendConfig.getRedirectMethodCallList();
        if(redirectMethodCallList == null || redirectMethodCallList.size() == 0){
            return false;
        }
        for(RedirectMethodCall redirectMethodCall : redirectMethodCallList){
            if(redirectMethodCall.isDefend(ctClass, methodName)){
                return true;
            }
        }
        return false;
    }
}
