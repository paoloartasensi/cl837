package androidx.fragment.app;

import android.util.Log;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: FragmentManagerViewModel */
final class l extends ViewModel {

    /* renamed from: g  reason: collision with root package name */
    private static final ViewModelProvider.Factory f624g = new a();
    private final HashMap<String, Fragment> a = new HashMap<>();
    private final HashMap<String, l> b = new HashMap<>();
    private final HashMap<String, ViewModelStore> c = new HashMap<>();
    private final boolean d;
    private boolean e = false;

    /* renamed from: f  reason: collision with root package name */
    private boolean f625f = false;

    /* compiled from: FragmentManagerViewModel */
    static class a implements ViewModelProvider.Factory {
        a() {
        }

        public <T extends ViewModel> T create(Class<T> cls) {
            return new l(true);
        }
    }

    l(boolean z) {
        this.d = z;
    }

    static l a(ViewModelStore viewModelStore) {
        return (l) new ViewModelProvider(viewModelStore, f624g).get(l.class);
    }

    /* access modifiers changed from: package-private */
    public Collection<Fragment> b() {
        return this.a.values();
    }

    /* access modifiers changed from: package-private */
    public boolean c() {
        return this.e;
    }

    /* access modifiers changed from: package-private */
    public ViewModelStore d(Fragment fragment) {
        ViewModelStore viewModelStore = this.c.get(fragment.f588i);
        if (viewModelStore != null) {
            return viewModelStore;
        }
        ViewModelStore viewModelStore2 = new ViewModelStore();
        this.c.put(fragment.f588i, viewModelStore2);
        return viewModelStore2;
    }

    /* access modifiers changed from: package-private */
    public boolean e(Fragment fragment) {
        return this.a.remove(fragment.f588i) != null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || l.class != obj.getClass()) {
            return false;
        }
        l lVar = (l) obj;
        if (!this.a.equals(lVar.a) || !this.b.equals(lVar.b) || !this.c.equals(lVar.c)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean f(Fragment fragment) {
        if (!this.a.containsKey(fragment.f588i)) {
            return true;
        }
        if (this.d) {
            return this.e;
        }
        return !this.f625f;
    }

    public int hashCode() {
        return (((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode();
    }

    /* access modifiers changed from: protected */
    public void onCleared() {
        if (j.d(3)) {
            Log.d("FragmentManager", "onCleared called for " + this);
        }
        this.e = true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("FragmentManagerViewModel{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append("} Fragments (");
        Iterator<Fragment> it = this.a.values().iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(") Child Non Config (");
        Iterator<String> it2 = this.b.keySet().iterator();
        while (it2.hasNext()) {
            sb.append(it2.next());
            if (it2.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(") ViewModelStores (");
        Iterator<String> it3 = this.c.keySet().iterator();
        while (it3.hasNext()) {
            sb.append(it3.next());
            if (it3.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(')');
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public void b(Fragment fragment) {
        if (j.d(3)) {
            Log.d("FragmentManager", "Clearing non-config state for " + fragment);
        }
        l lVar = this.b.get(fragment.f588i);
        if (lVar != null) {
            lVar.onCleared();
            this.b.remove(fragment.f588i);
        }
        ViewModelStore viewModelStore = this.c.get(fragment.f588i);
        if (viewModelStore != null) {
            viewModelStore.clear();
            this.c.remove(fragment.f588i);
        }
    }

    /* access modifiers changed from: package-private */
    public l c(Fragment fragment) {
        l lVar = this.b.get(fragment.f588i);
        if (lVar != null) {
            return lVar;
        }
        l lVar2 = new l(this.d);
        this.b.put(fragment.f588i, lVar2);
        return lVar2;
    }

    /* access modifiers changed from: package-private */
    public boolean a(Fragment fragment) {
        if (this.a.containsKey(fragment.f588i)) {
            return false;
        }
        this.a.put(fragment.f588i, fragment);
        return true;
    }

    /* access modifiers changed from: package-private */
    public Fragment a(String str) {
        return this.a.get(str);
    }
}
