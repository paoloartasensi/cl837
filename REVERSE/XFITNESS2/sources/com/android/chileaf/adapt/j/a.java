package com.android.chileaf.adapt.j;

import android.app.Application;
import java.lang.reflect.InvocationTargetException;

/* compiled from: AutoSizeUtils */
public class a {
    public static Application a() {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Object invoke = cls.getMethod("getApplication", new Class[0]).invoke(cls.getMethod("currentActivityThread", new Class[0]).invoke((Object) null, new Object[0]), new Object[0]);
            if (invoke != null) {
                return (Application) invoke;
            }
            throw new NullPointerException("you should init first");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new NullPointerException("you should init first");
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            throw new NullPointerException("you should init first");
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
            throw new NullPointerException("you should init first");
        } catch (ClassNotFoundException e4) {
            e4.printStackTrace();
            throw new NullPointerException("you should init first");
        }
    }
}
