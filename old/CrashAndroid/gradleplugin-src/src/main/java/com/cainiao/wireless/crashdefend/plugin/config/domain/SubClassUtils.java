package com.cainiao.wireless.crashdefend.plugin.config.domain;

import javassist.CtClass;
import javassist.NotFoundException;

/**
 * 是否是subclass的实现
 * */
public class SubClassUtils {

    /**
     * 是否是子类或者接口实现。
     * */
    public static boolean isSubClass(CtClass sourceClass, String className){
        while (sourceClass != null){
            if(sourceClass.getName().equals(className)){
                return true;
            }
            CtClass[] interfaceClasses = null;
            try {
                interfaceClasses = sourceClass.getInterfaces();
                if(interfaceClasses != null) {
                    for (CtClass interfaceClass : interfaceClasses) {
                        if (interfaceClass.getName().equals(className)) {
                            return true;
                        }
                    }
                }
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
            try {
                sourceClass = sourceClass.getSuperclass();
            } catch (NotFoundException e) {
                e.printStackTrace();
                sourceClass = null;
            }
        }
        return false;
    }
}
