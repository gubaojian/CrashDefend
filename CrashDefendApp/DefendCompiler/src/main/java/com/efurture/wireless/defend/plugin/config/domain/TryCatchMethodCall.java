package com.efurture.wireless.defend.plugin.config.domain;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

import javassist.CtClass;
import javassist.CtMethod;


/**
 * Try Catch Method Call
 * */
public class TryCatchMethodCall implements Serializable{

    private String className;
    private boolean includeSubClass;
    private List<DefendMethod> defendMethodList;

    public TryCatchMethodCall(String className,   boolean includeSubClass, List<DefendMethod> defendMethodList) {
        this.className = className;
        this.includeSubClass = includeSubClass;
        this.defendMethodList = defendMethodList;
    }

    public boolean isDefend(CtClass ctClass, String methodName) {
        if(includeSubClass) {
            if(!SubClassUtils.isSubClass(ctClass, className)){
                return false;
            }
        }else{
            if (!ctClass.getName().equals(className)) {
                return false;
            }
        }
        if(defendMethodList != null){
            for(DefendMethod defendMethod : defendMethodList){
                if(StringUtils.equals(defendMethod.getMethodName(), methodName)){
                    return true;
                }
            }
        }
        return false;
    }
}
