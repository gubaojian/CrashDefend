package com.cainiao.wireless.crashdefend;

import javassist.*;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.MethodInfo;

import java.io.IOException;

public class CrashDefendTest {

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("test.RunnableTest");
        addTryCatch(ctClass, pool);



        addTryCatch(pool.get("test.RunnableTest$1"), pool);


    }

    public static  void addTryCatch(CtClass ctClass, ClassPool pool) throws NotFoundException, CannotCompileException, IOException {
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
            //私有方法不做处理
            if(methodInfo.isMethod()){
                CtMethod ctMethod = (CtMethod)ctBehavior;
                if(isPrivateMethod(ctMethod)){
                    continue;
                }
            }
            ctBehavior.addCatch("{com.cainiao.wireless.crashdefend.CrashDefendSdk.getInstance().onCatch($e);}", pool.get("java.lang.Throwable"));
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

    private static boolean isPrivateConstructor(CtConstructor constructor){
        if(AccessFlag.isPrivate(constructor.getModifiers())){
            return true;
        }else{
            return false;
        }
    }


}
