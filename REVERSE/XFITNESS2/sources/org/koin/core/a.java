package org.koin.core;

import org.koin.core.g.b;
import org.koin.core.g.c;
import org.koin.core.scope.Scope;

/* compiled from: Koin.kt */
public final class a {
    private final c a = new c();
    private final Scope b;

    public a() {
        new b();
        this.b = new Scope("-Root-", true, this);
    }

    public final void a() {
        this.b.a();
    }

    public final Scope b() {
        return this.b;
    }

    public final c c() {
        return this.a;
    }
}
