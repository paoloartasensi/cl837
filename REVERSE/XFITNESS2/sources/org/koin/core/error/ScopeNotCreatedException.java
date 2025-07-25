package org.koin.core.error;

import kotlin.jvm.internal.i;

/* compiled from: ScopeNotCreatedException.kt */
public final class ScopeNotCreatedException extends Exception {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ScopeNotCreatedException(String str) {
        super(str);
        i.b(str, "msg");
    }
}
