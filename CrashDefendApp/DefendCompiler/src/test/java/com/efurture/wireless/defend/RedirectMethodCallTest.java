package com.efurture.wireless.defend;

import com.efurture.wireless.defend.plugin.CrashDefendCoder;
import com.efurture.wireless.defend.plugin.CrashDefendConfig;
import com.efurture.wireless.defend.plugin.config.DefendConfigReader;
import com.efurture.wireless.defend.plugin.config.domain.DefendConfig;
import com.efurture.wireless.defend.plugin.config.domain.RedirectMethodCall;
import com.efurture.wireless.defend.plugin.config.domain.TryCatchMethodCall;
import com.efurture.wireless.defend.redirect.MethodCallDispatcher;
import com.efurture.wireless.defend.redirect.MethodDispatcherAdapter;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;


/**
 * 方法拦截调用
 * */
public class RedirectMethodCallTest {

    @Test
    public void testRedirectMethod() throws ClassNotFoundException, CannotCompileException, NotFoundException, IOException, IllegalAccessException, InstantiationException {
        DefendConfig config = new DefendConfig();
        DefendConfigReader.parseConfig("src/test/resources/defend.xml", config);
        CrashDefendConfig.defendConfig = config;

        List<RedirectMethodCall> redirectMethodCallList = config.getRedirectMethodCallList();
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.getCtClass("test.redirectmethodcall.RedirectMethodCallTest");


        CrashDefendCoder.addCoder(ctClass, pool);

        ctClass.writeFile();

        Class targetClass = ctClass.toClass();

        test.redirectmethodcall.RedirectMethodCallTest redirectMethodCallTest = (test.redirectmethodcall.RedirectMethodCallTest) targetClass.newInstance();
        redirectMethodCallTest.callRedirectMethod();
    }

    @Test
    public void testSubRedirectMethod() throws ClassNotFoundException, CannotCompileException, NotFoundException, IOException, IllegalAccessException, InstantiationException {
        DefendConfig config = new DefendConfig();
        DefendConfigReader.parseConfig("src/test/resources/defend.xml", config);
        CrashDefendConfig.defendConfig  = config;

        List<RedirectMethodCall>  redirectMethodCallList = config.getRedirectMethodCallList();
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.getCtClass("test.redirectmethodcall.RedirectMethodCallSubTest");


        CrashDefendCoder.addCoder(ctClass, pool);

        ctClass.writeFile();

        Class targetClass = ctClass.toClass();

        MethodCallDispatcher.getInstance().setMethodDispatcherAdapter(new MethodDispatcherAdapter(){
            @Override
            public Object dispatchMethodCall(Object target, String methodName, boolean isStatic, Class<?>[] parameterTypes, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
                System.out.println("dispatchMethodCall " + methodName);
                return super.dispatchMethodCall(target, methodName, isStatic, parameterTypes, args);
            }
        });

        test.redirectmethodcall.RedirectMethodCallSubTest redirectMethodCallTest =  (test.redirectmethodcall.RedirectMethodCallSubTest) targetClass.newInstance();
        redirectMethodCallTest.callRedirectMethod();

        Assert.assertEquals(redirectMethodCallTest.testIntMethod(20), 50);
    }

}