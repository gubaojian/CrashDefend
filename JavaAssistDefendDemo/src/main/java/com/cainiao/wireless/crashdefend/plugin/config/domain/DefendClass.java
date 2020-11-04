package com.cainiao.wireless.crashdefend.plugin.config.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DefendClass implements Serializable {
    private static final long serialVersionUID = 6816190703499881745L;

    private String className;
    private Map<String,String> attributes;
    private List<DefendMethod> defendMethodList;

    public DefendClass(String className, Map<String, String> attributes, List<DefendMethod> defendMethodList) {
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

    public List<DefendMethod> getDefendMethodList() {
        return defendMethodList;
    }

    public void setDefendMethodList(List<DefendMethod> defendMethodList) {
        this.defendMethodList = defendMethodList;
    }
}
