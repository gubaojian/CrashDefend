package com.cainiao.wireless.crashdefend;

import com.cainiao.wireless.crashdefend.plugin.CrashDefendCoder;
import javassist.*;

import java.io.IOException;

public class CrashDefendTest {

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException, ClassNotFoundException {
        ClassPool pool = ClassPool.getDefault();
        addTryCatch("test.RunnableTest", pool);
        addTryCatch("test.RunnableTest$1", pool);
        addTryCatch("test.JavaBeanTest", pool);
        addTryCatch("test.exclude.ExcludeTest", pool);
        addTryCatch("test.StartUp", pool);
        addTryCatch("test.DefendTestDemo", pool);
        addTryCatch("test.DefendTestDemo$1", pool);
    }


    public static  void addTryCatch(String className, ClassPool pool) throws NotFoundException, CannotCompileException, IOException, ClassNotFoundException {
        CtClass ctClass = pool.getCtClass(className);
        CrashDefendCoder.addTryCatch(ctClass, pool);
    }




}
