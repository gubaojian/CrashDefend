package com.efurture.wireless.defend;

import com.efurture.wireless.defend.plugin.CrashDefendCoder;
import com.efurture.wireless.defend.plugin.CrashDefendConfig;
import com.efurture.wireless.defend.plugin.config.DefendConfigReader;
import com.efurture.wireless.defend.plugin.config.domain.DefendConfig;
import com.efurture.wireless.defend.plugin.config.domain.TryCatchMethodCall;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

public class TryCatchMethodCallTest {

    @Test
    public void testTryCatch() throws ClassNotFoundException, CannotCompileException, NotFoundException, IOException {
        DefendConfig config = new DefendConfig();
        DefendConfigReader.parseConfig("src/test/resources/defend.xml", config);
        CrashDefendConfig.defendConfig  = config;

        List<TryCatchMethodCall>  tryCatchMethodCallList = config.getTryCatchMethodCallList();
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.getCtClass("test.trycatchmethodcall.TryCatchMethodCallTest");

        CrashDefendCoder.addCoder(ctClass, pool);

        ctClass.writeFile();

        System.out.println(tryCatchMethodCallList.get(0).toString());
    }
}
