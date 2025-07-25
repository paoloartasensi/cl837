package org.koin.core.logger;

import kotlin.jvm.internal.i;

/* compiled from: EmptyLogger.kt */
public final class a extends b {
    public a() {
        super(Level.ERROR);
    }

    public void a(Level level, String str) {
        i.b(level, "level");
        i.b(str, "msg");
    }
}
