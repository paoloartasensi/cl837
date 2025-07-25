package org.koin.core.error;

import kotlin.jvm.internal.i;

/* compiled from: NoBeanDefFoundException.kt */
public final class NoBeanDefFoundException extends Exception {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NoBeanDefFoundException(String str) {
        super(str);
        i.b(str, "msg");
    }
}
