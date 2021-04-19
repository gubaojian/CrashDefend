package com.efurture.wireless.defend.plugin.config.domain;

import javassist.CtClass;
import javassist.CtMethod;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;

public class DefendMethod implements Serializable, MatchDefend {
    private static final long serialVersionUID = 4120604044647504370L;

    private String methodName;
    private String returnValue;
    private Map<String,String> attributes;

    public DefendMethod(String methodName, Map<String, String> attributes) {
        this.methodName = methodName;
        this.attributes = attributes;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    /**
     * 判断方法是否在保护方法之内
     * */
    public boolean isDefend(CtClass ctClass, CtMethod ctMethod) {
         return StringUtils.equals(ctMethod.getName(), methodName);
    }
}
