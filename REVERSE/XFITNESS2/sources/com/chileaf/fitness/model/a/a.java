package com.chileaf.fitness.model.a;

import android.os.Looper;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.internal.i;

/* compiled from: AutoLiveData.kt */
public final class a<T> extends MutableLiveData<T> {
    /* access modifiers changed from: private */
    public final AtomicBoolean a = new AtomicBoolean(false);

    /* renamed from: com.chileaf.fitness.model.a.a$a  reason: collision with other inner class name */
    /* compiled from: AutoLiveData.kt */
    static final class C0066a<T> implements Observer<T> {
        final /* synthetic */ a a;
        final /* synthetic */ Observer b;

        C0066a(a aVar, Observer observer) {
            this.a = aVar;
            this.b = observer;
        }

        public final void onChanged(T t) {
            if (this.a.a.compareAndSet(true, false)) {
                this.b.onChanged(t);
            }
        }
    }

    public void observe(LifecycleOwner lifecycleOwner, Observer<? super T> observer) {
        i.b(lifecycleOwner, "owner");
        i.b(observer, "observer");
        if (hasActiveObservers()) {
            j.a.a.d("Multiple observers registered but only one will be notified of changes.", new Object[0]);
        }
        super.observe(lifecycleOwner, new C0066a(this, observer));
    }

    public void postValue(T t) {
        this.a.set(true);
        super.postValue(t);
    }

    public void setValue(T t) {
        this.a.set(true);
        super.setValue(t);
    }

    public final void a(T t) {
        if (i.a((Object) Looper.getMainLooper(), (Object) Looper.myLooper())) {
            setValue(t);
        } else {
            postValue(t);
        }
    }
}
