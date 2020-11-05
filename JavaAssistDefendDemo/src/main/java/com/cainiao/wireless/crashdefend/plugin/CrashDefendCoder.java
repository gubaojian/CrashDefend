package com.cainiao.wireless.crashdefend.plugin;

import javassist.*;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.MethodInfo;

import java.io.IOException;

/**
 * 安全主动防护处理类
 * */
public class CrashDefendCoder {

    /**
     * 根据配置的白名单插入防护代码
     * */
    public static  void addTryCatch(CtClass ctClass, ClassPool pool) throws NotFoundException, CannotCompileException, IOException {
        //进行正常的安全防御处理
        for (CtBehavior ctBehavior : ctClass.getDeclaredBehaviors()) {
            //空方法不做处理
            if(ctBehavior.isEmpty()){
                continue;
            }

            if(ctBehavior instanceof  CtMethod){
                CtMethod ctMethod = (CtMethod)ctBehavior;
                boolean  isDefendMethod = CrashDefendConfig.isDefendMethod(ctClass, ctMethod);
                if(!isDefendMethod){
                    continue;
                }
                //返回值类型, 无返回值类型的处理
                CtClass returnType =  ctMethod.getReturnType();
                if(!isMethodReturnVoid(returnType.getName())){
                    StringBuilder code = new StringBuilder("{com.cainiao.wireless.crashdefend.CrashDefendSdk.onCatch($e);");
                    code.append(getReturnStatement(returnType.getName()));
                    code.append("}");
                    ctBehavior.addCatch(code.toString(), pool.get(CrashDefendConfig.defendConfig.getDefendCatchClass()));
                }else{
                    ctBehavior.addCatch("{com.cainiao.wireless.crashdefend.CrashDefendSdk.onCatch(exp);return;}", pool.get(CrashDefendConfig.defendConfig.getDefendCatchClass()), "exp");
                }
                System.out.println("defend class " + ctClass.getName() + " method " + ctMethod.getName());
            }
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
            return "return 0;";
        } else if (Constants.LANG_INT.equals(type)) {
            return "return 0;";
        } else if (Constants.LONG.equals(type)) {
            return "return 0L;";
        } else if (Constants.LANG_LONG.equals(type)) {
            return "return 0L;";
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






}
