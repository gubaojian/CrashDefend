package com.cainiao.wireless.crashdefend.plugin.config.domain;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.io.Serializable;
import java.util.List;

public class DefendSubClass implements Serializable, MatchDefend {
    private static final long serialVersionUID = 888335789839024129L;

    private String className;
    private String scope;
    private List<DefendMethod> defendMethodList;

    public DefendSubClass(String className, String scope, List<DefendMethod> defendMethodList) {
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

    public List<DefendMethod> getDefendMethodList() {
        return defendMethodList;
    }

    public void setDefendMethodList(List<DefendMethod> defendMethodList) {
        this.defendMethodList = defendMethodList;
    }

    public boolean isDefend(CtClass ctClass, CtMethod ctMethod) {
        if(!ctClass.getName().startsWith(scope)){
            return false;
        }
        if(isSubClass(ctClass)){
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

    /**
     * 是否是子类的排除类。
     * */
    public boolean isSubClass(CtClass sourceClass){
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
