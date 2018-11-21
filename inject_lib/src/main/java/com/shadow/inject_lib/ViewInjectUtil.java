package com.shadow.inject_lib;

import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by shadowwalker on 18-11-21.
 */

public class ViewInjectUtil {
    private static final String TAG = "ViewInjectUtil";

    public static void register(Object obj) {
        injectView(obj);
        injectEvent(obj);
    }

    private static void injectView(Object obj) {
        Class<?> clazz = obj.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        Method findViewByIdMethod = null;
        try {
            findViewByIdMethod = clazz.getMethod("findViewById", int.class);
            findViewByIdMethod.setAccessible(true);
            if (findViewByIdMethod != null) {
                for (Field field : declaredFields) {
                    if (field.isAnnotationPresent(ViewInject.class)) {
                        try {
                            ViewInject viewInject = field.getAnnotation(ViewInject.class);
                            int viewId = viewInject.value();
                            View view = (View) findViewByIdMethod.invoke(obj, viewId);
                            field.setAccessible(true);
                            field.set(obj, view);
                        } catch (InvocationTargetException e) {
                            System.out.print(TAG + e.getMessage());
                        }
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            System.out.print(TAG + e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.print(TAG + e.getMessage());
        } catch (ClassCastException e) {
            System.out.print(TAG + e.getMessage());
        }
    }

    private static void injectEvent(Object obj) {
        Class<?> clazz = obj.getClass();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        Method findViewByIdMethod = null;
        try {
            findViewByIdMethod = clazz.getMethod("findViewById", int.class);
            findViewByIdMethod.setAccessible(true);
            if (findViewByIdMethod != null) {
                ProxyHandler proxyHandler = new ProxyHandler(obj);
                for (Method method : declaredMethods) {
                    if (method.isAnnotationPresent(EventInject.class)) {
                        EventInject eventInject = method.getAnnotation(EventInject.class);
                        int[] viewIds = eventInject.values();
                        if (viewIds.length > 0) {
                            EventType eventType = eventInject.annotationType().getAnnotation(EventType.class);
                            proxyHandler.map(eventType.methodName(), method);
                            Object proxy = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{eventType.listenerClass()}, proxyHandler);
                            for (int viewId : viewIds) {
                                try {
                                    View view = (View) findViewByIdMethod.invoke(obj, viewId);
                                    if (view != null) {
                                        Method setOnClickListenerMethod = view.getClass().getMethod(eventType.className(), eventType.listenerClass());
                                        setOnClickListenerMethod.setAccessible(true);
                                        setOnClickListenerMethod.invoke(view, proxy);
                                    }
                                } catch (InvocationTargetException e) {
                                    System.out.print(TAG + e.getMessage());
                                } catch (ClassCastException e) {
                                    System.out.print(TAG + e.getMessage());
                                }
                            }
                        }


                    }
                }
            }
        } catch (NoSuchMethodException e) {
            System.out.print(TAG + e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.print(TAG + e.getMessage());
        } catch (NullPointerException e) {
            System.out.print(TAG + e.getMessage());
        }
    }
}
