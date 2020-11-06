package com.cainiao.wireless.crashdefend.plugin.config.domain;

import javassist.CtClass;
import javassist.CtMethod;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class DefendAutoConfig {

    public static final Map<String, ArrayList<String>> autoDefendClassMethods = new HashMap<String, ArrayList<String>>();
    static {
        initAutoDefend();
    }


    /**
     * 初始化自动防护方法和列表
     * */
    private static void initAutoDefend(){
        addJavaLangRunnable();
        addOnViewClickListener();
        addActivity();
        addFragment();
        addBroadcastReceiver();
        addService();
        addIntentService();
        addHandler();
        addHandlerCallback();
        addApplication();
    }

    /**
     * Runnable任务防护
     * */
    private static void addJavaLangRunnable(){
        String defendClass = "java.lang.Runnable";
        ArrayList<String> methods = new ArrayList<String>();
        methods.add("run");
        autoDefendClassMethods.put(defendClass, methods);
    }


    /**
     * 点击事件防护
     * */
    private static void addOnViewClickListener(){
        String defendClass = "android.view.View.OnClickListener";
        ArrayList<String> methods = new ArrayList<String>();
        methods.add("onClick");
        autoDefendClassMethods.put(defendClass, methods);
    }


    /**
     * Activity生命周期防护
     * */
    private static void addActivity(){
        String defendClass = "android.app.Activity";
        ArrayList<String> methods = new ArrayList<String>();
        methods.add("onCreate");
        methods.add("onStart");
        methods.add("onResume");
        methods.add("onPause");
        methods.add("onStop");
        methods.add("onDestroy");
        methods.add("onNewIntent");
        methods.add("onSaveInstanceState");
        autoDefendClassMethods.put(defendClass, methods);
    }


    /**
     * Fragment生命周期防护
     * */
    private static void addFragment(){
        String defendClass = "android.support.v4.app.Fragment";
        ArrayList<String> methods = new ArrayList<String>();
        methods.add("onAttach");
        methods.add("onCreate");
        methods.add("onStart");
        methods.add("onResume");
        methods.add("onPause");
        methods.add("onStop");
        methods.add("onDestroyView");
        methods.add("onDestroy");
        methods.add("onDetach");
        autoDefendClassMethods.put(defendClass, methods);
    }


    /**
     * Fragment生命周期防护
     * */
    private static void addBroadcastReceiver(){
        String defendClass = "android.content.BroadcastReceiver";
        ArrayList<String> methods = new ArrayList<String>();
        methods.add("onReceive");
        autoDefendClassMethods.put(defendClass, methods);
    }

    /**
     * Service生命周期防护
     * */
    private static void addService(){
        String defendClass = "android.app.Service";
        ArrayList<String> methods = new ArrayList<String>();
        methods.add("onCreate");
        methods.add("onStart");
        methods.add("onDestroy");
        autoDefendClassMethods.put(defendClass, methods);
    }


    /**
     * IntentService生命周期防护
     * */
    private static void addIntentService(){
        String defendClass = "android.app.IntentService";
        ArrayList<String> methods = new ArrayList<String>();
        methods.add("onHandleIntent");
        autoDefendClassMethods.put(defendClass, methods);
    }


    /**
     * Handler生命周期防护
     * */
    private static void addHandler(){
        String defendClass = "android.os.Handler";
        ArrayList<String> methods = new ArrayList<String>();
        methods.add("handleMessage");
        autoDefendClassMethods.put(defendClass, methods);
    }

    /**
     * Handler Callback 生命周期防护
     * */
    private static void addHandlerCallback(){
        String defendClass = "android.os.Handler.Callback";
        ArrayList<String> methods = new ArrayList<String>();
        methods.add("handleMessage");
        autoDefendClassMethods.put(defendClass, methods);
    }



    /**
     * Handler Callback 生命周期防护
     * */
    private static void addApplication(){
        String defendClass = "android.app.Application";
        ArrayList<String> methods = new ArrayList<String>();
        methods.add("onCreate");
        methods.add("onTerminate");
        methods.add("onConfigurationChanged");
        autoDefendClassMethods.put(defendClass, methods);
    }


    /**
     * 主动防护方法类列表和方法列表
     * */
    public static  boolean isDefend(CtClass ctClass, CtMethod ctMethod){
        Map<String, ArrayList<String>> autoDefendClassMethods = DefendAutoConfig.autoDefendClassMethods;
        if(autoDefendClassMethods == null){
            return false;
        }
        Set<Map.Entry<String, ArrayList<String>>> entrySets =  autoDefendClassMethods.entrySet();
        for(Map.Entry<String, ArrayList<String>> entry : entrySets){
            if(SubClassUtils.isSubClass(ctClass, entry.getKey())){
                ArrayList<String> methods = entry.getValue();
                for(String method : methods){
                    if(StringUtils.equals(method, ctMethod.getName())){
                        return true;
                    }
                }
            }
        }
        return false;
    }




}
