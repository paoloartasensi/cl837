package androidx.navigation;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.b;
import java.util.UUID;

/* compiled from: NavBackStackEntry */
public final class f implements LifecycleOwner, ViewModelStoreOwner, HasDefaultViewModelProviderFactory, b {
    private final Context e;

    /* renamed from: f  reason: collision with root package name */
    private final j f727f;

    /* renamed from: g  reason: collision with root package name */
    private final Bundle f728g;

    /* renamed from: h  reason: collision with root package name */
    private final LifecycleRegistry f729h;

    /* renamed from: i  reason: collision with root package name */
    private final androidx.savedstate.a f730i;

    /* renamed from: j  reason: collision with root package name */
    final UUID f731j;
    private Lifecycle.State k;
    private Lifecycle.State l;
    private g m;
    private ViewModelProvider.Factory n;

    /* compiled from: NavBackStackEntry */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|4|5|6|7|8|9|10|11|12|(3:13|14|16)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(16:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|16) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                androidx.lifecycle.Lifecycle$Event[] r0 = androidx.lifecycle.Lifecycle.Event.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                androidx.lifecycle.Lifecycle$Event r1 = androidx.lifecycle.Lifecycle.Event.ON_CREATE     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001d }
                androidx.lifecycle.Lifecycle$Event r1 = androidx.lifecycle.Lifecycle.Event.ON_STOP     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0028 }
                androidx.lifecycle.Lifecycle$Event r1 = androidx.lifecycle.Lifecycle.Event.ON_START     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0033 }
                androidx.lifecycle.Lifecycle$Event r1 = androidx.lifecycle.Lifecycle.Event.ON_PAUSE     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x003e }
                androidx.lifecycle.Lifecycle$Event r1 = androidx.lifecycle.Lifecycle.Event.ON_RESUME     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0049 }
                androidx.lifecycle.Lifecycle$Event r1 = androidx.lifecycle.Lifecycle.Event.ON_DESTROY     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0054 }
                androidx.lifecycle.Lifecycle$Event r1 = androidx.lifecycle.Lifecycle.Event.ON_ANY     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.f.a.<clinit>():void");
        }
    }

    f(Context context, j jVar, Bundle bundle, LifecycleOwner lifecycleOwner, g gVar) {
        this(context, jVar, bundle, lifecycleOwner, gVar, UUID.randomUUID(), (Bundle) null);
    }

    private void f() {
        if (this.k.ordinal() < this.l.ordinal()) {
            this.f729h.setCurrentState(this.k);
        } else {
            this.f729h.setCurrentState(this.l);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Lifecycle.State state) {
        this.l = state;
        f();
    }

    public SavedStateRegistry b() {
        return this.f730i.a();
    }

    public Bundle c() {
        return this.f728g;
    }

    public j d() {
        return this.f727f;
    }

    /* access modifiers changed from: package-private */
    public Lifecycle.State e() {
        return this.l;
    }

    public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        if (this.n == null) {
            this.n = new SavedStateViewModelFactory((Application) this.e.getApplicationContext(), this, this.f728g);
        }
        return this.n;
    }

    public Lifecycle getLifecycle() {
        return this.f729h;
    }

    public ViewModelStore getViewModelStore() {
        g gVar = this.m;
        if (gVar != null) {
            return gVar.b(this.f731j);
        }
        throw new IllegalStateException("You must call setViewModelStore() on your NavHostController before accessing the ViewModelStore of a navigation graph.");
    }

    private static Lifecycle.State b(Lifecycle.Event event) {
        switch (a.a[event.ordinal()]) {
            case 1:
            case 2:
                return Lifecycle.State.CREATED;
            case 3:
            case 4:
                return Lifecycle.State.STARTED;
            case 5:
                return Lifecycle.State.RESUMED;
            case 6:
                return Lifecycle.State.DESTROYED;
            default:
                throw new IllegalArgumentException("Unexpected event value " + event);
        }
    }

    f(Context context, j jVar, Bundle bundle, LifecycleOwner lifecycleOwner, g gVar, UUID uuid, Bundle bundle2) {
        this.f729h = new LifecycleRegistry(this);
        androidx.savedstate.a a2 = androidx.savedstate.a.a((b) this);
        this.f730i = a2;
        this.k = Lifecycle.State.CREATED;
        this.l = Lifecycle.State.RESUMED;
        this.e = context;
        this.f731j = uuid;
        this.f727f = jVar;
        this.f728g = bundle;
        this.m = gVar;
        a2.a(bundle2);
        if (lifecycleOwner != null) {
            this.k = lifecycleOwner.getLifecycle().getCurrentState();
        }
        f();
    }

    /* access modifiers changed from: package-private */
    public void a(Lifecycle.Event event) {
        this.k = b(event);
        f();
    }

    /* access modifiers changed from: package-private */
    public void a(Bundle bundle) {
        this.f730i.b(bundle);
    }
}
