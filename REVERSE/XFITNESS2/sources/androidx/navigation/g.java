package androidx.navigation;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

/* compiled from: NavControllerViewModel */
class g extends ViewModel {
    private static final ViewModelProvider.Factory b = new a();
    private final HashMap<UUID, ViewModelStore> a = new HashMap<>();

    /* compiled from: NavControllerViewModel */
    static class a implements ViewModelProvider.Factory {
        a() {
        }

        public <T extends ViewModel> T create(Class<T> cls) {
            return new g();
        }
    }

    g() {
    }

    static g a(ViewModelStore viewModelStore) {
        return (g) new ViewModelProvider(viewModelStore, b).get(g.class);
    }

    /* access modifiers changed from: package-private */
    public ViewModelStore b(UUID uuid) {
        ViewModelStore viewModelStore = this.a.get(uuid);
        if (viewModelStore != null) {
            return viewModelStore;
        }
        ViewModelStore viewModelStore2 = new ViewModelStore();
        this.a.put(uuid, viewModelStore2);
        return viewModelStore2;
    }

    /* access modifiers changed from: protected */
    public void onCleared() {
        for (ViewModelStore clear : this.a.values()) {
            clear.clear();
        }
        this.a.clear();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("NavControllerViewModel{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append("} ViewModelStores (");
        Iterator<UUID> it = this.a.keySet().iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(')');
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public void a(UUID uuid) {
        ViewModelStore remove = this.a.remove(uuid);
        if (remove != null) {
            remove.clear();
        }
    }
}
