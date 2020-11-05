package com.cainiao.wireless.crashdefend.plugin.config;

import com.cainiao.wireless.crashdefend.plugin.config.domain.*;
import org.apache.commons.lang3.BooleanUtils;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.*;

public class DefendConfigReader {

    /**
    * 解析配置文件，将配置文件添加到配置中
    * */
    public static final void parseConfig(String fileName, DefendConfig config){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(fileName);
            NodeList nodeList = document.getChildNodes();
            if(nodeList == null
                    || nodeList.getLength() == 0){
                return;
            }
            for(int i=0; i<nodeList.getLength(); i++){
                Node resources = nodeList.item(i);
                NodeList resourcesChilds = resources.getChildNodes();
                for(int m=0; m<resourcesChilds.getLength(); m++){
                    Node element = resourcesChilds.item(m);
                    if(DefendConfigElements.TAG_DEFEND_ON_DEBUG.equals(element.getNodeName())){
                        config.setDefendOnDebug(BooleanUtils.toBoolean(element.getTextContent()));
                    }else if(DefendConfigElements.TAG_DEFEND_OFF.equals(element.getNodeName())){
                        config.setDefendOff(BooleanUtils.toBoolean(element.getTextContent()));
                    }else if(DefendConfigElements.TAG_DEFEND_INTERFACE_IMPL.equals(element.getNodeName())){
                        DefendInterfaceImpl defendInterface = parseInterfaceImpl(element);
                        config.getDefendInterfaceList().add(defendInterface);
                    }else if(DefendConfigElements.TAG_DEFEND_SUB_CLASS.equals(element.getNodeName())){
                        DefendSubClass defendSubClass = parseDefendSubClass(element);
                        config.getDefendSubClassList().add(defendSubClass);
                    }else if(DefendConfigElements.TAG_DEFEND_CLASS.equals(element.getNodeName())){
                        DefendClass defendClass = parseDefendClass(element);
                        config.getDefendClassList().add(defendClass);
                    }else if(DefendConfigElements.TAG_DEFEND_AUTO.equals(element.getNodeName())){
                        DefendAuto defendAuto = parseDefendAuto(element);
                        config.getDefendAutoList().add(defendAuto);
                    }
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析 DefendClass 接口防护类
     * */
    private static DefendAuto parseDefendAuto(Node element){
        if(element.getAttributes() == null || element.getAttributes().getLength() == 0){
            throw new RuntimeException("defendAuto should have class attribute, " + element.toString());
        }
        Map<String,String> attrs = parseAttrs(element);
        String scope = attrs.get(DefendConfigElements.ATTR_SCOPE);
        if(scope == null){
            throw new RuntimeException("defendAuto should have class attribute, " + attrs);
        }
        DefendAuto defendAuto = new DefendAuto(scope, attrs);
        return defendAuto;
    }

    /**
     * 解析 DefendClass 接口防护类
     * */
    private static DefendClass parseDefendClass(Node element){
        if(element.getAttributes() == null || element.getAttributes().getLength() == 0){
            throw new RuntimeException("defendClass should have class attribute, " + element.toString());
        }
        Map<String,String> attrs = parseAttrs(element);
        String className = attrs.get(DefendConfigElements.ATTR_CLASS);
        if(className == null){
            throw new RuntimeException("defendClass should have class attribute, " + attrs);
        }
        List<DefendMethod> defendMethods = parseDefendMethods(element);
        if(defendMethods.size() == 0){
            throw new RuntimeException("please config defendClass's defendMethod element " + element.toString());
        }
        DefendClass defendClass = new DefendClass(className, attrs, defendMethods);
        return defendClass;
    }

    /**
     * 解析 DefendSubClass 接口防护类
     * */
    private static DefendSubClass parseDefendSubClass(Node element){
        if(element.getAttributes() == null || element.getAttributes().getLength() == 0){
            throw new RuntimeException("defendSubClass should have scope and parent attribute, " + element.toString());
        }
        Map<String,String> attrs = parseAttrs(element);
        String scope = attrs.get(DefendConfigElements.ATTR_SCOPE);
        String parentName = attrs.get(DefendConfigElements.ATTR_PARENT);
        if(scope == null){
            throw new RuntimeException("defendSubClass should have scope and parent attribute, " + attrs);
        }
        if(parentName == null){
            throw new RuntimeException("defendSubClass should have scope and parent attribute, " + attrs);
        }
        List<DefendMethod> defendMethods = parseDefendMethods(element);

        if(defendMethods.size() == 0){
            throw new RuntimeException("please config defendSubClass's defendMethod element " + element.toString());
        }
        DefendSubClass defendSubClass = new DefendSubClass(parentName, scope, defendMethods);
        return defendSubClass;
    }

    /**
     * 解析 DefendInterfaceImpl 接口防护类
     * */
    private static DefendInterfaceImpl parseInterfaceImpl(Node element){
        if(element.getAttributes() == null || element.getAttributes().getLength() == 0){
            throw new RuntimeException("defendInterfaceImpl should have scope and interface, " + element.toString());
        }
        Map<String,String> attrs = parseAttrs(element);
        String scope = attrs.get(DefendConfigElements.ATTR_SCOPE);
        String interfaceNode = attrs.get(DefendConfigElements.ATTR_INTERFACE);
        if(scope == null){
            throw new RuntimeException("defendInterfaceImpl should have scope and interface, " + attrs);
        }
        if(interfaceNode == null){
            throw new RuntimeException("defendInterfaceImpl should have scope and interface, " + attrs);
        }
        DefendInterfaceImpl defendInterface = new DefendInterfaceImpl(interfaceNode, scope, attrs);
        defendInterface.setDefendMethodList(parseDefendMethods(element));
        return defendInterface;
    }

    /**
     * 解析DefendMethod XML的属性
     * */
    private static List<DefendMethod> parseDefendMethods(Node element){
        List<DefendMethod> defendMethods = new ArrayList<DefendMethod>();
        NodeList nodeList = element.getChildNodes();
        if(nodeList != null) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                Map<String, String> attrs = parseAttrs(node);
                String name = attrs.get(DefendConfigElements.ATTR_NAME);
                String returnValue = attrs.get(DefendConfigElements.ATTR_RETURN_VALUE);
                DefendMethod defendMethod = new DefendMethod(name, attrs);
                defendMethod.setReturnValue(returnValue);
                defendMethods.add(defendMethod);
            }
        }
        return defendMethods;
    }

    /**
     * 解析XML的属性
     * */
    private static  Map<String,String>  parseAttrs(Node element){
        Map<String,String> attrs = new HashMap<String, String>();
        NamedNodeMap namedNodeMap = element.getAttributes();
        if(namedNodeMap != null) {
            for (int i = 0; i < namedNodeMap.getLength(); i++) {
                Node node = namedNodeMap.item(i);
                attrs.put(node.getNodeName(), node.getNodeValue());
            }
        }
        return attrs;
    }





}
