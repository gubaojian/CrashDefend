package com.efurture.wireless.defend;

import com.efurture.wireless.defend.plugin.CrashDefendCoder;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.IOException;

public class CrashDefendUtils {

    public static  CtClass  addTryCatch(String className) throws NotFoundException, CannotCompileException, IOException, ClassNotFoundException {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.getCtClass(className);
        CrashDefendCoder.addCoder(ctClass, pool);
        ctClass.writeFile();
        return  ctClass;
    }
}
