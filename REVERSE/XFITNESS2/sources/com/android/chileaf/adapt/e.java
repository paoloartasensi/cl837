package com.android.chileaf.adapt;

import android.app.Activity;
import com.android.chileaf.adapt.external.ExternalAdaptInfo;
import com.android.chileaf.adapt.h.b;
import j.a.a;
import java.util.Locale;

/* compiled from: DefaultAutoAdaptStrategy */
public class e implements b {
    public void a(Object obj, Activity activity) {
        if (d.u().d().a()) {
            if (d.u().d().b(obj.getClass())) {
                a.d(String.format(Locale.ENGLISH, "%s canceled the adaptation!", new Object[]{obj.getClass().getName()}), new Object[0]);
                c.b(activity);
                return;
            }
            ExternalAdaptInfo a = d.u().d().a(obj.getClass());
            if (a != null) {
                a.a(String.format(Locale.ENGLISH, "%s used %s for adaptation!", new Object[]{obj.getClass().getName(), ExternalAdaptInfo.class.getName()}), new Object[0]);
                c.a(activity, a);
                return;
            }
        }
        if (obj instanceof com.android.chileaf.adapt.h.a) {
            a.d(String.format(Locale.ENGLISH, "%s canceled the adaptation!", new Object[]{obj.getClass().getName()}), new Object[0]);
            c.b(activity);
        } else if (obj instanceof b) {
            a.a(String.format(Locale.ENGLISH, "%s implemented by %s!", new Object[]{obj.getClass().getName(), b.class.getName()}), new Object[0]);
            c.a(activity, (b) obj);
        } else {
            a.a(String.format(Locale.ENGLISH, "%s used the global configuration.", new Object[]{obj.getClass().getName()}), new Object[0]);
            c.a(activity);
        }
    }
}
