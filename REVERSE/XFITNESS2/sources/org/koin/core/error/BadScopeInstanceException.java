package org.koin.core.error;

import kotlin.jvm.internal.i;

/* compiled from: BadScopeInstanceException.kt */
public final class BadScopeInstanceException extends Exception {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BadScopeInstanceException(String str) {
        super(str);
        i.b(str, "s");
    }
}
