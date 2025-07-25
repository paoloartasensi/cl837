package org.koin.core.error;

import kotlin.jvm.internal.i;

/* compiled from: KoinAppAlreadyStartedException.kt */
public final class KoinAppAlreadyStartedException extends Exception {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public KoinAppAlreadyStartedException(String str) {
        super(str);
        i.b(str, "msg");
    }
}
