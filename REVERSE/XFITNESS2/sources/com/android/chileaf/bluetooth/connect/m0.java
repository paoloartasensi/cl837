package com.android.chileaf.bluetooth.connect;

import android.util.Log;

/* compiled from: ConditionalWaitRequest */
public final class m0<T> extends f0 {
    private final a<T> u;
    private final T v;
    private boolean w;

    /* compiled from: ConditionalWaitRequest */
    public interface a<T> {
        boolean a(T t);
    }

    /* access modifiers changed from: package-private */
    public boolean l() {
        try {
            return this.u.a(this.v) == this.w;
        } catch (Exception e) {
            Log.e("ConditionalWaitRequest", "Error while checking predicate", e);
            return true;
        }
    }

    /* access modifiers changed from: package-private */
    public m0<T> a(u0 u0Var) {
        super.a(u0Var);
        return this;
    }
}
