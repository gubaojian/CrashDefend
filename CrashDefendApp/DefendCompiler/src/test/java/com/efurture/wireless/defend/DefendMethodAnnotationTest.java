package com.efurture.wireless.defend;

import com.efurture.wireless.defend.plugin.CrashDefendConfig;
import com.efurture.wireless.defend.plugin.config.domain.DefendConfig;

import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.junit.Test;

import java.io.IOException;

public class DefendMethodAnnotationTest {


    @Test
    public void testDefendInterfaceImpl() throws ClassNotFoundException, CannotCompileException, NotFoundException, IOException {
        com.efurture.wireless.defend.plugin.config.domain.DefendConfig config = new DefendConfig();
        CrashDefendConfig.defendConfig  = config;

        CrashDefendUtils.addTryCatch("test.defendmethodannotation.AnnotationTest");
    }
}
