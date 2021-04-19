package com.efurture.wireless.defend.assist;

import com.efurture.wireless.defend.asm.BizCallTest;
import com.efurture.wireless.defend.plugin.CrashDefendCoder;

import org.junit.Test;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CodeConverter;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class JavaAssistTest {


    @Test
    public void testAssist() throws IOException, NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException {
        String className = "com.efurture.wireless.defend.asm.BizCallTest";
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.getCtClass(className);
        for (CtBehavior ctBehavior : ctClass.getDeclaredBehaviors()) {
            if(ctBehavior instanceof CtMethod){
                CtMethod ctMethod = (CtMethod)ctBehavior;

                ctMethod.instrument(new ExprEditor(){
                    @Override
                    public void edit(MethodCall m) throws CannotCompileException {


                        if("com.efurture.wireless.defend.asm.ViewUtils".equals(m.getClassName())
                           && (m.getMethodName().equals("post") || m.getMethodName().equals("staticPost") )){


                            try {
                                CtMethod method = m.getMethod();
                                System.out.println(Modifier.isStatic(method.getModifiers()));
                                if(Modifier.isStatic(method.getModifiers())){
                                    System.out.println(m.getMethodName()
                                            + " " + m.getClassName());
                                    System.out.println("ddd" + m.where()
                                            + " " + m.getSignature());
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("if(com.efurture.wireless.defend.asm.RedirectViewUtils.needRedirect(" +
                                            "" + m.getClassName() + ".class"+
                                            "," +
                                            "\"" + m.getMethodName() + "\"" +
                                            ",$args)){");
                                    sb.append("$_ = ");
                                    sb.append("com.efurture.wireless.defend.asm.RedirectViewUtils.redirect(" +
                                            "" + m.getClassName() + ".class"+
                                             ",");
                                    sb.append("\"" + m.getMethodName() + "\"");
                                    sb.append(",$args);}else{$_=$proceed($$);}");
                                    System.out.println(sb.toString());
                                    m.replace(sb.toString());
                                }else{
                                    System.out.println(m.getMethodName()
                                            + " " + m.getClassName());
                                    System.out.println("ddd" + m.where()
                                            + " " + m.getSignature());
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("if(com.efurture.wireless.defend.asm.RedirectViewUtils.needRedirect($0," +
                                            "\"" + m.getMethodName() + "\"" +
                                            ",$args)){");
                                    sb.append("$_ = ");
                                    sb.append("com.efurture.wireless.defend.asm.RedirectViewUtils.redirect($0,");
                                    sb.append("\"" + m.getMethodName() + "\"");
                                    sb.append(",$args);}else{$_=$proceed($$);}");
                                    System.out.println(sb.toString());
                                    m.replace(sb.toString());
                                }
                            } catch (NotFoundException e) {
                                e.printStackTrace();
                            }



                        }

                    }
                });

                /**
                 * 方法调用加上try catch
                 * */
                ctMethod.instrument(new ExprEditor(){
                    @Override
                    public void edit(MethodCall m) throws CannotCompileException {
                        if("com.efurture.wireless.defend.asm.ViewUtils".equals(m.getClassName())
                                && m.getMethodName().equals("doExp")){
                            StringBuilder sb = new StringBuilder();
                            sb.append("try{$_=$proceed($$);}catch(Exception e){}");
                            System.out.println(sb.toString());
                            m.replace(sb.toString());
                        }
                    }
                });
            }
        }

        ctClass.writeFile();

        Class targetClass = ctClass.toClass();
        BizCallTest bizCallTest = (BizCallTest) targetClass.newInstance();
        bizCallTest.doBiz();

    }
}
