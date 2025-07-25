package com.afollestad.materialdialogs.lifecycle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.i;
import kotlin.l;

/* compiled from: DialogLifecycleObserver.kt */
public final class DialogLifecycleObserver implements LifecycleObserver {
    private final a<l> e;

    public DialogLifecycleObserver(a<l> aVar) {
        i.b(aVar, "dismiss");
        this.e = aVar;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public final void onDestroy() {
        this.e.invoke();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public final void onPause() {
        this.e.invoke();
    }
}
