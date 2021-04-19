package com.efurture.wireless.defend.plugin.config.domain;

import javassist.CtClass;
import javassist.CtMethod;

import java.io.Serializable;
import java.util.List;

public class DefendSubClass implements Serializable, MatchDefend {
    private static final long serialVersionUID = 888335789839024129L;

    private String className;
    private String scope;
    private List<com.efurture.wireless.defend.plugin.config.domain.DefendMethod> defendMethodList;

    public DefendSubClass(String className, String scope, List<com.efurture.wireless.defend.plugin.config.domain.DefendMethod> defendMethodList) {
        this.className = className;
        this.scope = scope;
        this.defendMethodList = defendMethodList;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<com.efurture.wireless.defend.plugin.config.domain.DefendMethod> getDefendMethodList() {
        return defendMethodList;
    }

    public void setDefendMethodList(List<com.efurture.wireless.defend.plugin.config.domain.DefendMethod> defendMethodList) {
        this.defendMethodList = defendMethodList;
    }

    public boolean isDefend(CtClass ctClass, CtMethod ctMethod) {
        if(!ctClass.getName().startsWith(scope)){
            return false;
        }
        if(SubClassUtils.isSubClass(ctClass, className)){
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
