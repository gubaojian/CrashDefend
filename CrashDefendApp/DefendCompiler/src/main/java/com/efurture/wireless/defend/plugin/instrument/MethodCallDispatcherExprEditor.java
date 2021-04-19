package com.efurture.wireless.defend.plugin.instrument;

import com.efurture.wireless.defend.plugin.CrashDefendConfig;


import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class MethodCallDispatcherExprEditor extends ExprEditor {

    private ClassPool mPool;
    private  boolean needTryCatch = false;

    public MethodCallDispatcherExprEditor(ClassPool pool) {
        this.mPool = pool;
    }

    @Override
    public void edit(MethodCall m) throws CannotCompileException {
        String className = m.getClassName();
        String methodName = m.getMethodName();
        try {
            CtClass ctClass = mPool.get(className);
            if(!CrashDefendConfig.isRedirectMethodCall(ctClass, methodName)){
                return;
            }
        }catch (NotFoundException e) {
            return;
        }

        try {
            CtMethod method = m.getMethod();
            CtClass[] parameterTypes =  method.getParameterTypes();
            StringBuilder parameterSB = new StringBuilder();
            if(parameterTypes.length == 0){
                parameterSB.append("new Class[0]");
            }else{
                parameterSB.append("new Class[]{");
                for(CtClass parameterType : parameterTypes){
                    parameterSB.append(parameterType.getName() + ".class");
                    parameterSB.append(",");
                }
                parameterSB.deleteCharAt(parameterSB.length()-1);
                parameterSB.append("}");
            }
            boolean isStatic = Modifier.isStatic(method.getModifiers());
            StringBuilder sb = new StringBuilder();
            if(isStatic){
                sb.append("$_ = ");
                sb.append("($r)com.efurture.wireless.defend.redirect.MethodCallDispatcher.dispatch(");
                sb.append(m.getClassName() + ".class");
                sb.append(",");
                sb.append('\"'  + m.getMethodName() + '\"');
                sb.append(",");
                sb.append("true");
                sb.append(",");
                sb.append(parameterSB.toString());
                sb.append(",");
                sb.append("$args");
                sb.append(");");
            }else{
                sb.append("$_ = ");
                sb.append("($r)com.efurture.wireless.defend.redirect.MethodCallDispatcher.dispatch(");
                sb.append("$0");
                sb.append(",");
                sb.append('\"'  + m.getMethodName() + '\"');
                sb.append(",");
                sb.append("false");
                sb.append(",");
                sb.append(parameterSB.toString());
                sb.append(",");
                sb.append("$args");
                sb.append(");");
            }
            m.replace(sb.toString());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        needTryCatch = true;
        System.out.println("redirectMethodCall " + className + " " + methodName);
    }

    public boolean isNeedTryCatch() {
        return needTryCatch;
    }
}
