package androidx.lifecycle;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import kotlin.d;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.i;
import kotlin.reflect.c;

/* compiled from: ViewModelProvider.kt */
public final class ViewModelLazy<VM extends ViewModel> implements d<VM> {
    private VM cached;
    private final a<ViewModelProvider.Factory> factoryProducer;
    private final a<ViewModelStore> storeProducer;
    private final c<VM> viewModelClass;

    public ViewModelLazy(c<VM> cVar, a<? extends ViewModelStore> aVar, a<? extends ViewModelProvider.Factory> aVar2) {
        i.b(cVar, "viewModelClass");
        i.b(aVar, "storeProducer");
        i.b(aVar2, "factoryProducer");
        this.viewModelClass = cVar;
        this.storeProducer = aVar;
        this.factoryProducer = aVar2;
    }

    public boolean isInitialized() {
        return this.cached != null;
    }

    public VM getValue() {
        VM vm = this.cached;
        if (vm != null) {
            return vm;
        }
        VM vm2 = new ViewModelProvider(this.storeProducer.invoke(), this.factoryProducer.invoke()).get(kotlin.jvm.a.a(this.viewModelClass));
        this.cached = vm2;
        i.a((Object) vm2, "ViewModelProvider(store,…ed = it\n                }");
        return vm2;
    }
}
