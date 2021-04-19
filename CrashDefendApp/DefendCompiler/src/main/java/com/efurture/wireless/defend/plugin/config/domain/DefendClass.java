package com.efurture.wireless.defend.plugin.config.domain;

import javassist.CtClass;
import javassist.CtMethod;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DefendClass implements Serializable, MatchDefend {
    private static final long serialVersionUID = 6816190703499881745L;

    private String className;
    private Map<String,String> attributes;
    private List<com.efurture.wireless.defend.plugin.config.domain.DefendMethod> defendMethodList;

    public DefendClass(String className, Map<String, String> attributes, List<com.efurture.wireless.defend.plugin.config.domain.DefendMethod> defendMethodList) {
        this.className = className;
        this.attributes = attributes;
        this.defendMethodList = defendMethodList;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public List<com.efurture.wireless.defend.plugin.config.domain.DefendMethod> getDefendMethodList() {
        return defendMethodList;
    }

    public void setDefendMethodList(List<com.efurture.wireless.defend.plugin.config.domain.DefendMethod> defendMethodList) {
        this.defendMethodList = defendMethodList;
    }

    public boolean isDefend(CtClass ctClass, CtMethod ctMethod) {
        if(StringUtils.equals(ctClass.getName(), className)){
            if(defendMethodList != null){
                for(DefendMethod defendMethod : defendMethodList){
                    if(defendMethod.isDefend(ctClass, ctMethod)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
