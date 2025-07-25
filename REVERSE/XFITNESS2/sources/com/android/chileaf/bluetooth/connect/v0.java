package com.android.chileaf.bluetooth.connect;

import com.android.chileaf.bluetooth.connect.Request;
import java.util.LinkedList;
import java.util.Queue;

/* compiled from: RequestQueue */
public class v0 extends x0 {
    private final Queue<Request> p = new LinkedList();

    v0() {
        super(Request.Type.SET);
    }

    /* access modifiers changed from: package-private */
    public Request i() {
        try {
            return this.p.remove();
        } catch (Exception unused) {
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean j() {
        return !this.p.isEmpty();
    }

    /* access modifiers changed from: package-private */
    public v0 a(u0 u0Var) {
        super.a(u0Var);
        return this;
    }
}
