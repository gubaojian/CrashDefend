package com.efurture.wireless.defend.plugin.instrument;

import com.efurture.wireless.defend.plugin.CrashDefendConfig;
import com.efurture.wireless.defend.plugin.WeaverCode;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

/**
 * 添加try catch的method call功能
 * */
public class TryCatchMethodCallExprEditor extends ExprEditor {

    private ClassPool mPool;

    public TryCatchMethodCallExprEditor(ClassPool pool) {
        this.mPool = pool;
    }


    @Override
    public void edit(MethodCall m) throws CannotCompileException {
        String className = m.getClassName();
        String methodName = m.getMethodName();
        try {
            CtClass ctClass = mPool.get(className);
            if(!CrashDefendConfig.isTryCatchMethodCall(ctClass, methodName)){
                return;
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        if(isVoidMethodCall(m)){
            StringBuilder sb = new StringBuilder();
            sb.append("try{$proceed($$);}catch(Exception e){"+ WeaverCode.REPORTER_CODE + "}");
            m.replace(sb.toString());
        }else{
            StringBuilder sb = new StringBuilder();
            sb.append("try{$_=$proceed($$);}catch(Exception e){"+ WeaverCode.REPORTER_CODE + "}");
            m.replace(sb.toString());
        }
    }


    private boolean isVoidMethodCall(MethodCall m){
        try {
            CtMethod ctMethod = m.getMethod();
            if(VOID_NAME.equals(ctMethod.getReturnType().getName())){
                return true;
            }
            return false;
        } catch (NotFoundException e) {
            return  false;
        }
    }

    private static final String VOID_NAME = "void";
}
