package com.cainiao.wireless.crashdefend.plugin.config.domain;

import com.cainiao.wireless.crashdefend.plugin.config.domain.DefendAuto;
import com.cainiao.wireless.crashdefend.plugin.config.domain.DefendClass;
import com.cainiao.wireless.crashdefend.plugin.config.domain.DefendInterfaceImpl;
import com.cainiao.wireless.crashdefend.plugin.config.domain.DefendSubClass;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DefendConfig implements Serializable, MatchDefend{

    private static final long serialVersionUID = 1399644052227571261L;

    private static final String DEFAULT_CATCH_CLASS = "java.lang.Throwable";
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
        return false;
    }
}
