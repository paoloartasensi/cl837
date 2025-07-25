package com.android.chileaf.adapt;

import android.app.Activity;

/* compiled from: WrapperAutoAdaptStrategy */
public class g implements b {
    private final b a;

    public g(b bVar) {
        this.a = bVar;
    }

    public void a(Object obj, Activity activity) {
        i k = d.u().k();
        if (k != null) {
            k.b(obj, activity);
        }
        b bVar = this.a;
        if (bVar != null) {
            bVar.a(obj, activity);
        }
        if (k != null) {
            k.a(obj, activity);
        }
    }
}
