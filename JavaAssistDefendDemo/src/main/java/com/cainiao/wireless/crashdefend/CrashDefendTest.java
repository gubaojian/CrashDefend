package com.cainiao.wireless.crashdefend;

import javassist.*;
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
            if(ctBehavior.isEmpty()){
                continue;
            }
            if(methodInfo.isConstructor() && isAnonymousClass(ctClass)){
                continue;
            }

            System.out.println("love " + methodInfo.getName() + " " + ctBehavior.getSignature()
            +"  " + ctClass.getName());
            if(methodInfo.isConstructor()){
                //continue;
            }
            ctBehavior.addCatch("{}", pool.get("java.lang.Throwable"));
            /**
            if(methodInfo.isMethod()){
                CtMethod method = (CtMethod)ctBehavior;

                // method.insertBefore("try {");
                //method.insertAt(method, )
                //method.insertAfter(" }catch (Exception e){}");

            }*/
            System.out.println(ctBehavior.getMethodInfo().getName());
        }
        ctClass.writeFile();
    }


    private static boolean isAnonymousClass(CtClass ctClass){
        String anonyClassChars = "$";
        return  ctClass.getName().indexOf(anonyClassChars) >= 0;
    }
}
