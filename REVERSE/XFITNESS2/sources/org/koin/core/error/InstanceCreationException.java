package org.koin.core.error;

import kotlin.jvm.internal.i;

/* compiled from: InstanceCreationException.kt */
public final class InstanceCreationException extends Exception {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public InstanceCreationException(String str, Exception exc) {
        super(str, exc);
        i.b(str, "msg");
        i.b(exc, "parent");
    }
}
