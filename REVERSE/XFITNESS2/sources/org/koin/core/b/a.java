package org.koin.core.b;

import kotlin.jvm.internal.i;
import org.koin.core.KoinApplication;
import org.koin.core.error.KoinAppAlreadyStartedException;

/* compiled from: GlobalContext.kt */
public final class a {
    private static KoinApplication a;

    static {
        new a();
    }

    private a() {
    }

    public static final void a(KoinApplication koinApplication) {
        i.b(koinApplication, "koinApplication");
        if (a == null) {
            a = koinApplication;
            return;
        }
        throw new KoinAppAlreadyStartedException("A Koin Application has already been started");
    }
}
