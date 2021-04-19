package com.efurture.wireless.defend.plugin.config.domain;

import com.efurture.wireless.defend.plugin.ClassHelper;

import javassist.CtClass;
import javassist.CtMethod;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DefendConfig implements Serializable, com.efurture.wireless.defend.plugin.config.domain.MatchDefend {

    private static final long serialVersionUID = 1399644052227571261L;

    private static final String DEFAULT_CATCH_CLASS = "java.lang.Exception";
    /**
     * debug包是否插入代码
     * */
    private boolean defendOnDebug;

    /**
     * 代码防护是否打开
     * */
    private boolean defendOff;

    /**
     * defendConfig类型
     * */
    private String defendCatchClass;

    /**
     * 防护Class列表
     * */
    private List<DefendClass> defendClassList;


    /**
     * 防护Class实现的列表
     * */
    private List<DefendSubClass> defendSubClassList;


    /**
     * 防护Interface的实现列表
     * */
    private List<DefendInterfaceImpl> defendInterfaceList;

    /**
     * 防护自动防护包列表
     * */
    private List<DefendAuto> defendAutoList;

    /**
     * 注解防护列表
     * */
    private List<com.efurture.wireless.defend.plugin.config.domain.MatchDefend> defendMethodAnnotations;


    /**
     * try catch method call
     * */
    private List<TryCatchMethodCall> tryCatchMethodCallList;


    /**
     * redirect method call
     * */
    private List<RedirectMethodCall> redirectMethodCallList;


    public DefendConfig() {
        defendMethodAnnotations = new ArrayList<com.efurture.wireless.defend.plugin.config.domain.MatchDefend>();
        if(ClassHelper.getDefendAnnotationClass() != null) {
            defendMethodAnnotations.add(new DefendMethodAnnotation(ClassHelper.getDefendAnnotationClass()));
            defendMethodAnnotations.add(new DefendClassAnnotation(ClassHelper.getDefendAnnotationClass()));
        }
    }

    public boolean isDefendOnDebug() {
        return defendOnDebug;
    }

    public void setDefendOnDebug(boolean defendOnDebug) {
        this.defendOnDebug = defendOnDebug;
    }

    public boolean isDefendOff() {
        return defendOff;
    }

    public void setDefendOff(boolean defendOff) {
        this.defendOff = defendOff;
    }

    public List<DefendClass> getDefendClassList() {
        if(defendClassList == null){
            defendClassList = new ArrayList<DefendClass>();
        }
        return defendClassList;
    }

    public void setDefendClassList(List<DefendClass> defendClassList) {
        this.defendClassList = defendClassList;
    }

    public List<DefendSubClass> getDefendSubClassList() {
        if(defendSubClassList == null){
            defendSubClassList = new ArrayList<DefendSubClass>();
        }
        return defendSubClassList;
    }

    public void setDefendSubClassList(List<DefendSubClass> defendSubClassList) {
        this.defendSubClassList = defendSubClassList;
    }

    public List<DefendInterfaceImpl> getDefendInterfaceList() {
        if(defendInterfaceList == null){
            defendInterfaceList = new ArrayList<DefendInterfaceImpl>();
        }
        return defendInterfaceList;
    }

    public void setDefendInterfaceList(List<DefendInterfaceImpl> defendInterfaceList) {
        this.defendInterfaceList = defendInterfaceList;
    }

    public List<DefendAuto> getDefendAutoList() {
        if(defendAutoList == null){
            defendAutoList = new ArrayList<DefendAuto>();
        }
        return defendAutoList;
    }

    public void setDefendAutoList(List<DefendAuto> defendAutoList) {
        this.defendAutoList = defendAutoList;
    }

    public String getDefendCatchClass() {
        if(defendCatchClass == null){
            defendCatchClass = DEFAULT_CATCH_CLASS;
        }
        return defendCatchClass;
    }

    public void setDefendCatchClass(String defendCatchClass) {
        this.defendCatchClass = defendCatchClass;
    }

    public List<TryCatchMethodCall> getTryCatchMethodCallList() {
        if(tryCatchMethodCallList == null){
            tryCatchMethodCallList = new ArrayList<>();
        }
        return tryCatchMethodCallList;
    }

    public void setTryCatchMethodCallList(List<TryCatchMethodCall> tryCatchMethodCallList) {
        this.tryCatchMethodCallList = tryCatchMethodCallList;
    }

    public List<RedirectMethodCall> getRedirectMethodCallList() {
        if(redirectMethodCallList == null){
            redirectMethodCallList = new ArrayList<>();
        }
        return redirectMethodCallList;
    }

    public void setRedirectMethodCallList(List<RedirectMethodCall> redirectMethodCallList) {
        this.redirectMethodCallList = redirectMethodCallList;
    }

    public boolean isDefend(CtClass ctClass, CtMethod ctMethod) {
        if(isDefendOff()) {
            return false;
        }
        //是否匹配 defendInterfaceList
        if(defendInterfaceList != null){
            for(DefendInterfaceImpl defendInterface : defendInterfaceList){
                if(defendInterface.isDefend(ctClass, ctMethod)){
                    return true;
                }
            }
        }

        //是否匹配 defendSubClassList
        if(defendSubClassList != null){
            for(DefendSubClass defendSubClass : defendSubClassList){
                if(defendSubClass.isDefend(ctClass, ctMethod)){
                    return true;
                }
            }
        }

        //是否匹配 defendClassList
        if(defendClassList != null){
            for(DefendClass defendClass : defendClassList){
                if(defendClass.isDefend(ctClass, ctMethod)){
                    return true;
                }
            }
        }

        //注解扫描
        if(defendMethodAnnotations != null){
            for(MatchDefend defendMethodAnnotation : defendMethodAnnotations){
                if(defendMethodAnnotation.isDefend(ctClass, ctMethod)){
                    return true;
                }
            }
        }

        //自动扫描
        if(defendAutoList != null){
            for(DefendAuto defendAuto : defendAutoList){
                if(defendAuto.isDefend(ctClass, ctMethod)){
                    return true;
                }
            }
        }

        return false;
    }
}
