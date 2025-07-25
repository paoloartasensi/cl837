package androidx.lifecycle;

import kotlin.jvm.b.l;
import kotlin.jvm.internal.i;

/* compiled from: LiveData.kt */
public final class LiveDataKt {
    public static final <T> Observer<T> observe(LiveData<T> liveData, LifecycleOwner lifecycleOwner, l<? super T, kotlin.l> lVar) {
        i.b(liveData, "$this$observe");
        i.b(lifecycleOwner, "owner");
        i.b(lVar, "onChanged");
        LiveDataKt$observe$wrappedObserver$1 liveDataKt$observe$wrappedObserver$1 = new LiveDataKt$observe$wrappedObserver$1(lVar);
        liveData.observe(lifecycleOwner, liveDataKt$observe$wrappedObserver$1);
        return liveDataKt$observe$wrappedObserver$1;
    }
}
