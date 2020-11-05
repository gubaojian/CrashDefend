package com.cainiao.wireless.crashdefend.plugin.config.domain;

import com.cainiao.wireless.crashdefend.Defend;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefendInterfaceImpl implements Serializable, MatchDefend {
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

    public boolean isDefend(CtClass ctClass, CtMethod ctMethod) {
        if(!ctClass.getName().startsWith(scope)){
            return false;
        }
        CtClass[] interfaces = null;
        try {
            interfaces = ctClass.getInterfaces();
        } catch (NotFoundException e) {
            return false;
        }
        if(interfaces == null){
            return false;
        }
        CtClass matchInterface = null;
        for(CtClass interfaceClass : interfaces){
            if(StringUtils.equals(interfaceName, interfaceClass.getName())){
                matchInterface = interfaceClass;
                break;
            }
        }
        if(matchInterface == null){
            return false;
        }
        if(defendMethodList == null || defendMethodList.size() == 0){
            defendMethodList = new ArrayList<DefendMethod>();
            CtMethod[]  methods = matchInterface.getMethods();
            for(CtMethod  method : methods){
                DefendMethod defendMethod = new DefendMethod(method.getName(), new HashMap<String, String>());
                defendMethodList.add(defendMethod);
            }
        }
        for(DefendMethod defendMethod : defendMethodList){
            if(defendMethod.isDefend(ctClass, ctMethod)){
                return true;
            }
        }
        return false;
    }
}
