package com.cainiao.wireless.crashdefend.plugin.config.domain;

import java.io.Serializable;
import java.util.List;

public class DefendSubClass implements Serializable {
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
}
