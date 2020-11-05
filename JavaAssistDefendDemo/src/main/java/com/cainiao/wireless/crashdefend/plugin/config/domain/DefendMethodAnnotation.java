package com.cainiao.wireless.crashdefend.plugin.config.domain;

import com.cainiao.wireless.crashdefend.Defend;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.Serializable;

public class DefendMethodAnnotation implements Serializable, MatchDefend {

    private static final long serialVersionUID = -3898558416502153500L;

    private Class annotationClass;

    public DefendMethodAnnotation(Class annotationClass) {
        this.annotationClass = annotationClass;
    }

    public boolean isDefend(CtClass ctClass, CtMethod ctMethod) {
        try {
            Object annotation = ctMethod.getAnnotation(annotationClass);
            if(annotation != null){
                return true;
            }
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
