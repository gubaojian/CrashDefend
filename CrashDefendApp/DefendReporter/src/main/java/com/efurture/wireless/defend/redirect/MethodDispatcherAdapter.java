package com.efurture.wireless.defend.redirect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * 方法分发容器实现
 * */
public class MethodDispatcherAdapter {

    /**
     * 方法分发容器实现
     * */
    public Object dispatchMethodCall(Object target, String methodName, boolean isStatic, Class<?>[] parameterTypes, Object ...args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = null;
        if(isStatic){
            method = ((Class)target).getDeclaredMethod(methodName, parameterTypes);
        }else {
            method = target.getClass().getDeclaredMethod(methodName, parameterTypes);
        }
        if(isStatic){
            return method.invoke(null, args);
        }else{
            return method.invoke(target, args);
        }
    }

    /**
     * 分发方法
     * */
    public final Object dispatchMethodCallSafe(Object target, String methodName, boolean isStatic, Class<?>[] parameterTypes, Object ...args){
        try {
            return dispatchMethodCall(target, methodName, isStatic, parameterTypes, args);
        }catch (Exception e){
            return null;
        }
    }
}
