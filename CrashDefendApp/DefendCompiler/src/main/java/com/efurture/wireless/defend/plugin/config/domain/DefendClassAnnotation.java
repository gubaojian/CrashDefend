package com.efurture.wireless.defend.plugin.config.domain;

import javassist.CtClass;
import javassist.CtMethod;

import java.io.Serializable;

public class DefendClassAnnotation implements Serializable, MatchDefend {

    private static final long serialVersionUID = -3898558416502153500L;

    private Class annotationClass;

    public DefendClassAnnotation(Class annotationClass) {
        this.annotationClass = annotationClass;
    }

    public boolean isDefend(CtClass ctClass, CtMethod ctMethod) {
        try {
            Object annotation =  ctClass.getAnnotation(annotationClass);
            if(annotation == null){
                return false;
            }
            return DefendAutoConfig.isDefend(ctClass, ctMethod);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
