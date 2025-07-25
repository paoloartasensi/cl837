package com.chileaf.fitness.config;

import com.chileaf.fitness.App;
import java.util.Arrays;
import kotlin.jvm.internal.i;

/* compiled from: FitnessEx.kt */
public final class a {
    public static final String a(int i2) {
        String string = App.f1144g.a().getString(i2);
        i.a((Object) string, "App.instance.getString(resId)");
        return string;
    }

    public static final String a(int i2, Object... objArr) {
        i.b(objArr, "formatArgs");
        String string = App.f1144g.a().getString(i2, Arrays.copyOf(objArr, objArr.length));
        i.a((Object) string, "App.instance.getString(resId, *formatArgs)");
        return string;
    }
}
