package com.cainiao.wireless.crashdefend.plugin.config.domain;

import com.cainiao.wireless.crashdefend.Defend;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DefendInterfaceImpl implements Serializable {
    private static final long serialVersionUID = 8379367412064079430L;

    public String interfaceName;
    public String scope;
    public Map<String,String> attributes;
    public List<DefendMethod> defendMethodList;

    public DefendInterfaceImpl(String interfaceName, String scope, Map<String, String> attributes) {
        this.interfaceName = interfaceName;
        this.scope = scope;
        this.attributes = attributes;
    }

    public DefendInterfaceImpl(String interfaceName, String scope, Map<String, String> attributes, List<DefendMethod> defendMethodList) {
        this.interfaceName = interfaceName;
        this.scope = scope;
        this.attributes = attributes;
        this.defendMethodList = defendMethodList;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
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
