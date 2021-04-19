package com.cainiao.wireless.crashdefend;

import com.cainiao.wireless.crashdefend.plugin.CrashDefendCoder;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.IOException;

public class CrashDefendUtils {

    public static  CtClass  addTryCatch(String className) throws NotFoundException, CannotCompileException, IOException, ClassNotFoundException {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.getCtClass(className);
        CrashDefendCoder.addTryCatch(ctClass, pool);
        ctClass.writeFile();
        return  ctClass;
    }
}
