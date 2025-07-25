package com.chileaf.fitness.device.cdn;

import androidx.activity.ComponentActivity;
import androidx.lifecycle.ViewModelStore;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.i;

/* compiled from: ActivityViewModelLazy.kt */
public final class CDNActivity$$special$$inlined$viewModels$2 extends Lambda implements a<ViewModelStore> {
    final /* synthetic */ ComponentActivity $this_viewModels;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public CDNActivity$$special$$inlined$viewModels$2(ComponentActivity componentActivity) {
        super(0);
        this.$this_viewModels = componentActivity;
    }

    public final ViewModelStore invoke() {
        ViewModelStore viewModelStore = this.$this_viewModels.getViewModelStore();
        i.a((Object) viewModelStore, "viewModelStore");
        return viewModelStore;
    }
}
