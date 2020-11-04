package com.cainiao.wireless.crashdefend.plugin.config.domain;

import java.io.Serializable;
import java.util.Map;

public class DefendAuto implements Serializable {
    private static final long serialVersionUID = -3714160097375512785L;

    private String scope;
    private Map<String,String> attributes;

    public DefendAuto(String scope, Map<String, String> attributes) {
        this.scope = scope;
        this.attributes = attributes;
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
}
