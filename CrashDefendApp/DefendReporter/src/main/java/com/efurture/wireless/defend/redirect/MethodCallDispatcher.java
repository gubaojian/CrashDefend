package com.efurture.wireless.defend.redirect;


/**
 * 分发方法实现入口
 * */
public class MethodCallDispatcher {

    private static MethodCallDispatcher mMethodCallDispatcher = new MethodCallDispatcher();

    private MethodDispatcherAdapter mMethodDispatcherAdapter = new MethodDispatcherAdapter();

    /**
     * 分发方法实现入口
     * */
    public static Object dispatch(Object target, String methodName, boolean isStatic, Class<?>[] parameterTypes, Object ...args){
        if(mMethodCallDispatcher != null && mMethodCallDispatcher.getMethodDispatcherAdapter() != null){
            return mMethodCallDispatcher.getMethodDispatcherAdapter().dispatchMethodCallSafe(target,
                    methodName, isStatic, parameterTypes, args);
        }
        return null;
    }

    public static MethodCallDispatcher getInstance(){
        return mMethodCallDispatcher;
    }


    public MethodDispatcherAdapter getMethodDispatcherAdapter() {
        if(mMethodDispatcherAdapter == null){
            mMethodDispatcherAdapter = new MethodDispatcherAdapter();
        }
        return mMethodDispatcherAdapter;
    }

    public void setMethodDispatcherAdapter(MethodDispatcherAdapter methodDispatcherAdapter) {
        this.mMethodDispatcherAdapter = methodDispatcherAdapter;
    }
}
