package com.shadow.inject_lib;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by shadowwalker on 18-11-21.
 */

public class ProxyHandler implements InvocationHandler {
    private WeakReference<Object> mWeakRef;
    private HashMap<String, Method> mMethodMap;

    public ProxyHandler(Object obj) {
        mWeakRef = new WeakReference<Object>(obj);
        mMethodMap = new HashMap<>();
    }

    public void map(String methodName, Method method) {
        mMethodMap.put(methodName, method);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (mWeakRef != null) {
            Object realObj = mWeakRef.get();
            if (realObj != null) {
                Method realMethod = mMethodMap.get(method.getName());
                if (realMethod != null) {
                    realMethod.invoke(realObj, args);
                }
            }
        }
        return null;
    }
}
