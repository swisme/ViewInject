package com.shadow.inject_lib;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shadowwalker on 18-11-21.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@EventType(className = "setOnClickListener", listenerClass = View.OnClickListener.class, methodName = "onClick")
public @interface EventInject {
    int[] values();
}
