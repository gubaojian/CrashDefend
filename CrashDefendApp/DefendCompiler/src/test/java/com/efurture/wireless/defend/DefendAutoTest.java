package com.efurture.wireless.defend;


import com.efurture.wireless.defend.plugin.CrashDefendConfig;
import com.efurture.wireless.defend.plugin.config.DefendConfigReader;
import com.efurture.wireless.defend.plugin.config.domain.DefendConfig;

import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.junit.Test;

import java.io.IOException;

public class DefendAutoTest {


    @Test
    public void testDefendClass() throws ClassNotFoundException, CannotCompileException, NotFoundException, IOException {
        DefendConfig config = new DefendConfig();
        DefendConfigReader.parseConfig("src/test/resources/test-defend-auto.xml", config);
        CrashDefendConfig.defendConfig  = config;

        CrashDefendUtils.addTryCatch("test.defendauto.RunnableTest");
        CrashDefendUtils.addTryCatch("test.defendauto.RunnableTest$1");
    }
}
