package org.koin.core.logger;

import kotlin.jvm.internal.i;

/* compiled from: Logger.kt */
public abstract class b {
    private Level a;

    public b(Level level) {
        i.b(level, "level");
        this.a = level;
    }

    public final Level a() {
        return this.a;
    }

    public abstract void a(Level level, String str);

    public final void b(String str) {
        i.b(str, "msg");
        a(Level.ERROR, str);
    }

    public final void c(String str) {
        i.b(str, "msg");
        a(Level.INFO, str);
    }

    public final void a(String str) {
        i.b(str, "msg");
        a(Level.DEBUG, str);
    }

    public final boolean a(Level level) {
        i.b(level, "lvl");
        return this.a.compareTo(level) <= 0;
    }
}
