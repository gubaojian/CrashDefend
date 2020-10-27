package com.cainiao.wireless.crashdefend;

import javassist.*;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.MethodInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 安全主动防护处理类
 * */
public class CrashDefendCompiler {

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
        defendClasses.add("android.support.v7.widget.RecyclerView");
        defendClasses.add("android.support.v7.widget.LinearLayoutManager");


        excludeDefendPackages.add("test.exclude");
        defendPackages.add("test");
    }


    public static  void addTryCatch(CtClass ctClass, ClassPool pool, Class<?> sourceClass) throws NotFoundException, CannotCompileException, IOException {
        //是否是普通的JavaBean类，如果是则不做处理
        if(isExcludeSubClass(sourceClass)){
            return;
        }

        //跳过处理的包, 跳过处理的包
        String excludePackage = isExcludePackage(ctClass);
        if(excludePackage != null){
            String includePackage = isIncludePackage(ctClass);
            if(includePackage == null){
                if(!isIncludeClass(ctClass)){
                    return;
                }
                //默认包含类
            }else{

                //排除包优先级高于包含包
                if(includePackage.length() <= excludePackage.length()){
                    return;
                }
            }
        }


        //配置包名字的采用白名单机制，没有配置, 默认全部加
        if(defendPackages != null && !defendPackages.isEmpty()){
            //默认处理模式，根据包名字来处理
            String includePackage = isIncludePackage(ctClass);
            if(includePackage == null){
                if(!isIncludeClass(ctClass)){
                    return;
                }
            }
        }

        //进行正常的安全防御处理
        for (CtBehavior ctBehavior : ctClass.getDeclaredBehaviors()) {
            MethodInfo methodInfo = ctBehavior.getMethodInfo();

            //空方法不做处理
            if(ctBehavior.isEmpty()){
                continue;
            }

            //匿名内部类做处理
            if(methodInfo.isConstructor() && isAnonymousClass(ctClass)){
                continue;
            }

            //私有方法和构造方法不做处理
            if(methodInfo.isConstructor()){
                CtConstructor ctConstructor = (CtConstructor)ctBehavior;
                if(isPrivateConstructor(ctConstructor)){
                    continue;
                }
            }

            //私有方法不做处理, 带返回值的方法不做处理。
            if(methodInfo.isMethod()){
                CtMethod ctMethod = (CtMethod)ctBehavior;
                if(isPrivateMethod(ctMethod)){
                    continue;
                }
                //返回值类型, 无返回值类型的处理
                CtClass returnType =  ctMethod.getReturnType();
                try{
                    Object annotation = ctMethod.getAnnotation(DefendIgnore.class);
                    if(annotation != null){
                        continue;
                    }
                }catch (Exception e){}
                if(!isMethodReturnVoid(returnType.getName())){
                    if(configOnlyVoid){
                        continue;
                    }
                    StringBuilder code = new StringBuilder("{com.cainiao.wireless.crashdefend.CrashDefendSdk.onCatch($e);");
                    code.append(getReturnStatement(returnType.getName()));
                    code.append("}");
                    ctBehavior.addCatch(code.toString(), pool.get("java.lang.Throwable"));
                    continue;
                }
            }
            ctBehavior.addCatch("{com.cainiao.wireless.crashdefend.CrashDefendSdk.onCatch($e);}", pool.get("java.lang.Throwable"));
        }
        ctClass.writeFile();
    }


    private static boolean isAnonymousClass(CtClass ctClass){
        String anonyClassChars = "$";
        return  ctClass.getName().indexOf(anonyClassChars) >= 0;
    }


    private static boolean isPrivateMethod(CtMethod ctMethod){
        if(AccessFlag.isPrivate(ctMethod.getModifiers())){
            return true;
        }else{
            return false;
        }
    }

    private static boolean isMethodReturnVoid(String returnType){
        if(Constants.VOID.equals(returnType)){
            return true;
        }else if(Constants.LANG_VOID.equals(returnType)){
            return true;
        }
        return  false;
    }

    private static boolean isPrivateConstructor(CtConstructor constructor){
        if(AccessFlag.isPrivate(constructor.getModifiers())){
            return true;
        }else{
            return false;
        }
    }


    /**
     * 根据传入类型来生成返回语句
     *
     * @param type 返回类型
     * @return 返回return语句
     */
    private static String getReturnStatement(String type) {
        if (Constants.CONSTRUCTOR.equals(type)) {
            return "return;";
        } else if (Constants.LANG_VOID.equals(type)) {
            return "return null;";
        } else if (Constants.VOID.equals(type)) {
            return "return;";
        } else if (Constants.LANG_BOOLEAN.equals(type)) {
            return "return false;";
        } else if (Constants.BOOLEAN.equals(type)) {
            return "return false;";
        } else if (Constants.INT.equals(type)) {
            return "return -1;";
        } else if (Constants.LANG_INT.equals(type)) {
            return "return -1;";
        } else if (Constants.LONG.equals(type)) {
            return "return -1;";
        } else if (Constants.LANG_LONG.equals(type)) {
            return "return -1;";
        } else if (Constants.DOUBLE.equals(type)) {
            return "return 0;";
        } else if (Constants.LANG_DOUBLE.equals(type)) {
            return "return 0;";
        } else if (Constants.FLOAT.equals(type)) {
            return "return 0;";
        } else if (Constants.LANG_FLOAT.equals(type)) {
            return "return 0;";
        } else if (Constants.SHORT.equals(type)) {
            return "return 0;";
        } else if (Constants.LANG_SHORT.equals(type)) {
            return "return 0;";
        } else if (Constants.BYTE.equals(type)) {
            return "return 0;";
        } else if (Constants.LANG_BYTE.equals(type)) {
            return "return 0;";
        } else if (Constants.CHAR.equals(type)) {
            return "return 0;";
        } else if (Constants.LANG_CHARACTER.equals(type)) {
            return "return 0;";
        }
        return "return null;";
    }


    /**
     * 是否是排除类
     * */
    private static boolean isExcludeSubClass(Class<?> sourceClass){
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



    private static String isExcludePackage(CtClass ctClass){
        for(String excludePackage  : excludeDefendPackages){
            if(ctClass.getName().startsWith(excludePackage)){
                return excludePackage;
            }
        }
        return null;
    }


    private static String isIncludePackage(CtClass ctClass){
        for(String includePackage  : defendPackages){
            if(ctClass.getName().startsWith(includePackage)){
                return includePackage;
            }
        }
        return null;
    }

    private static boolean isIncludeClass(CtClass ctClass){
        for(String includeClass  : defendClasses){
            if(ctClass.getName().equals(includeClass)){
                return true;
            }
        }
        return false;
    }



}
