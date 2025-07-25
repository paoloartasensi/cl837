package androidx.lifecycle;

import kotlin.jvm.b.l;

/* compiled from: LiveData.kt */
public final class LiveDataKt$observe$wrappedObserver$1<T> implements Observer<T> {
    final /* synthetic */ l $onChanged;

    public LiveDataKt$observe$wrappedObserver$1(l lVar) {
        this.$onChanged = lVar;
    }

    public final void onChanged(T t) {
        this.$onChanged.invoke(t);
    }
}
