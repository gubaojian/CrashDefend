package com.cainiao.wireless.crashdefend.plugin;

import com.cainiao.wireless.crashdefend.plugin.config.domain.DefendConfig;
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
}
