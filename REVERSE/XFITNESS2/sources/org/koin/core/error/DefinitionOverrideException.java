package org.koin.core.error;

import kotlin.jvm.internal.i;

/* compiled from: DefinitionOverrideException.kt */
public final class DefinitionOverrideException extends Exception {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DefinitionOverrideException(String str) {
        super(str);
        i.b(str, "msg");
    }
}
