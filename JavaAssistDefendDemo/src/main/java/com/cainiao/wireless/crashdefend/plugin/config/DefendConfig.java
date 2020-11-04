package com.cainiao.wireless.crashdefend.plugin.config;

import com.cainiao.wireless.crashdefend.plugin.config.domain.DefendAuto;
import com.cainiao.wireless.crashdefend.plugin.config.domain.DefendClass;
import com.cainiao.wireless.crashdefend.plugin.config.domain.DefendInterfaceImpl;
import com.cainiao.wireless.crashdefend.plugin.config.domain.DefendSubClass;

import java.util.List;

public class DefendConfig {

    /**
     * debug包是否插入代码
     * */
    private boolean defendOnDebug;

    /**
     * 代码防护是否打开
     * */
    private boolean defendOff;

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
        return defendClassList;
    }

    public void setDefendClassList(List<DefendClass> defendClassList) {
        this.defendClassList = defendClassList;
    }

    public List<DefendSubClass> getDefendSubClassList() {
        return defendSubClassList;
    }

    public void setDefendSubClassList(List<DefendSubClass> defendSubClassList) {
        this.defendSubClassList = defendSubClassList;
    }

    public List<DefendInterfaceImpl> getDefendInterfaceList() {
        return defendInterfaceList;
    }

    public void setDefendInterfaceList(List<DefendInterfaceImpl> defendInterfaceList) {
        this.defendInterfaceList = defendInterfaceList;
    }

    public List<DefendAuto> getDefendAutoList() {
        return defendAutoList;
    }

    public void setDefendAutoList(List<DefendAuto> defendAutoList) {
        this.defendAutoList = defendAutoList;
    }
}
