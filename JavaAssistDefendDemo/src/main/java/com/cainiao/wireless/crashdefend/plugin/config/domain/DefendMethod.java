package com.cainiao.wireless.crashdefend.plugin.config.domain;

import java.io.Serializable;
import java.util.Map;

public class DefendMethod implements Serializable {
    private static final long serialVersionUID = 4120604044647504370L;

    public String methodName;
    public Map<String,String> attributes;

    public DefendMethod(String methodName, Map<String, String> attributes) {
        this.methodName = methodName;
        this.attributes = attributes;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
