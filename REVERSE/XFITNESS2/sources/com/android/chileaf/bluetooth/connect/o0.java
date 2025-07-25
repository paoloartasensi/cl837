package com.android.chileaf.bluetooth.connect;

import com.android.chileaf.bluetooth.connect.Request;

/* compiled from: ConnectionPriorityRequest */
public final class o0 extends y0<Object> {
    private final int q;

    o0(Request.Type type, int i2) {
        super(type);
        this.q = (i2 < 0 || i2 > 2) ? 0 : i2;
    }

    /* access modifiers changed from: package-private */
    public int i() {
        return this.q;
    }

    /* access modifiers changed from: package-private */
    public o0 a(u0 u0Var) {
        super.a(u0Var);
        return this;
    }
}
