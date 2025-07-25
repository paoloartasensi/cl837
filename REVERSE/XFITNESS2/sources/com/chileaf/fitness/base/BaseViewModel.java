package com.chileaf.fitness.base;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import kotlin.jvm.internal.i;

/* compiled from: BaseViewModel.kt */
public class BaseViewModel extends AndroidViewModel implements LifecycleObserver {
    private final Application e;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BaseViewModel(Application application) {
        super(application);
        i.b(application, "application");
        this.e = application;
    }

    public final Application b() {
        return this.e;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate(LifecycleOwner lifecycleOwner) {
        i.b(lifecycleOwner, "owner");
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(LifecycleOwner lifecycleOwner) {
        i.b(lifecycleOwner, "owner");
        lifecycleOwner.getLifecycle().removeObserver(this);
    }
}
