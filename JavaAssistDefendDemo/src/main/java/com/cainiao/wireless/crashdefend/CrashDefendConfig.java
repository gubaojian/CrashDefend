package com.cainiao.wireless.crashdefend;

import javassist.CtClass;

import java.util.ArrayList;
import java.util.List;

public class CrashDefendConfig {

    public static List<String> excludeDefendSubClasses = new ArrayList<String>();
    public static List<String> excludeDefendPackages = new ArrayList<String>();
    public static List<String> defendPackages = new ArrayList<String>();
    public static List<String> defendClasses = new ArrayList<String>();


    public static boolean configOnlyVoid = false;

    //初始化默认配置
    static {
        excludeDefendSubClasses.add("java.io.Serializable");
        excludeDefendSubClasses.add("java.io.Externalizable");
        excludeDefendSubClasses.add("android.os.Parcelable");
        excludeDefendSubClasses.add("mtopsdk.mtop.domain.IMTOPDataObject");
        excludeDefendPackages.add("android.");
        excludeDefendPackages.add("androidx.");
        excludeDefendPackages.add("org.greenrobot");
        excludeDefendPackages.add("org.ifaa.android");
        excludeDefendPackages.add("de.greenrobot");
        defendClasses.add("android.support.v7.widget.RecyclerView");
        defendClasses.add("android.support.v7.widget.LinearLayoutManager");


        excludeDefendPackages.add("test.exclude");
        defendPackages.add("test");
    }


    /**
     * 是否是排除类
     * */
    public static boolean isExcludeSubClass(Class<?> sourceClass){
        while (sourceClass != null){
            Class<?>[] interfaceClasses = sourceClass.getInterfaces();
            for(String excludeSubClass  : excludeDefendSubClasses){
                if(sourceClass.getName()  .equals(excludeSubClass)){
                    return true;
                }
                for(Class<?> interfaceClass : interfaceClasses){
                    if(interfaceClass.getName().equals(excludeSubClass)){
                        return true;
                    }
                }
            }
            sourceClass = sourceClass.getSuperclass();
        }
        return false;
    }



    public static String isExcludePackage(CtClass ctClass){
        for(String excludePackage  : excludeDefendPackages){
            if(ctClass.getName().startsWith(excludePackage)){
                return excludePackage;
            }
        }
        return null;
    }


    public static String isIncludePackage(CtClass ctClass){
        for(String includePackage  : defendPackages){
            if(ctClass.getName().startsWith(includePackage)){
                return includePackage;
            }
        }
        return null;
    }

    public static boolean isIncludeClass(CtClass ctClass){
        for(String includeClass  : defendClasses){
            if(ctClass.getName().equals(includeClass)){
                return true;
            }
        }
        return false;
    }
}
