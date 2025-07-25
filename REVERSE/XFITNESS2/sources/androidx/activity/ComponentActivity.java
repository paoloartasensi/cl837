package androidx.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ReportFragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.savedstate.SavedStateRegistry;

public class ComponentActivity extends androidx.core.app.ComponentActivity implements LifecycleOwner, ViewModelStoreOwner, HasDefaultViewModelProviderFactory, androidx.savedstate.b, c {

    /* renamed from: g  reason: collision with root package name */
    private final LifecycleRegistry f12g;

    /* renamed from: h  reason: collision with root package name */
    private final androidx.savedstate.a f13h;

    /* renamed from: i  reason: collision with root package name */
    private ViewModelStore f14i;

    /* renamed from: j  reason: collision with root package name */
    private ViewModelProvider.Factory f15j;
    private final OnBackPressedDispatcher k;
    private int l;

    class a implements Runnable {
        a() {
        }

        public void run() {
            ComponentActivity.super.onBackPressed();
        }
    }

    static final class b {
        Object a;
        ViewModelStore b;

        b() {
        }
    }

    public ComponentActivity() {
        this.f12g = new LifecycleRegistry(this);
        this.f13h = androidx.savedstate.a.a((androidx.savedstate.b) this);
        this.k = new OnBackPressedDispatcher(new a());
        if (getLifecycle() != null) {
            if (Build.VERSION.SDK_INT >= 19) {
                getLifecycle().addObserver(new LifecycleEventObserver() {
                    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                        if (event == Lifecycle.Event.ON_STOP) {
                            Window window = ComponentActivity.this.getWindow();
                            View peekDecorView = window != null ? window.peekDecorView() : null;
                            if (peekDecorView != null) {
                                peekDecorView.cancelPendingInputEvents();
                            }
                        }
                    }
                });
            }
            getLifecycle().addObserver(new LifecycleEventObserver() {
                public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                    if (event == Lifecycle.Event.ON_DESTROY && !ComponentActivity.this.isChangingConfigurations()) {
                        ComponentActivity.this.getViewModelStore().clear();
                    }
                }
            });
            int i2 = Build.VERSION.SDK_INT;
            if (19 <= i2 && i2 <= 23) {
                getLifecycle().addObserver(new ImmLeaksCleaner(this));
                return;
            }
            return;
        }
        throw new IllegalStateException("getLifecycle() returned null in ComponentActivity's constructor. Please make sure you are lazily constructing your Lifecycle in the first call to getLifecycle() rather than relying on field initialization.");
    }

    public final SavedStateRegistry b() {
        return this.f13h.a();
    }

    @Deprecated
    public Object d() {
        return null;
    }

    public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        if (getApplication() != null) {
            if (this.f15j == null) {
                this.f15j = new SavedStateViewModelFactory(getApplication(), this, getIntent() != null ? getIntent().getExtras() : null);
            }
            return this.f15j;
        }
        throw new IllegalStateException("Your activity is not yet attached to the Application instance. You can't request ViewModel before onCreate call.");
    }

    public Lifecycle getLifecycle() {
        return this.f12g;
    }

    public ViewModelStore getViewModelStore() {
        if (getApplication() != null) {
            if (this.f14i == null) {
                b bVar = (b) getLastNonConfigurationInstance();
                if (bVar != null) {
                    this.f14i = bVar.b;
                }
                if (this.f14i == null) {
                    this.f14i = new ViewModelStore();
                }
            }
            return this.f14i;
        }
        throw new IllegalStateException("Your activity is not yet attached to the Application instance. You can't request ViewModel before onCreate call.");
    }

    public void onBackPressed() {
        this.k.a();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f13h.a(bundle);
        ReportFragment.injectIfNeededIn(this);
        int i2 = this.l;
        if (i2 != 0) {
            setContentView(i2);
        }
    }

    public final Object onRetainNonConfigurationInstance() {
        b bVar;
        Object d = d();
        ViewModelStore viewModelStore = this.f14i;
        if (viewModelStore == null && (bVar = (b) getLastNonConfigurationInstance()) != null) {
            viewModelStore = bVar.b;
        }
        if (viewModelStore == null && d == null) {
            return null;
        }
        b bVar2 = new b();
        bVar2.a = d;
        bVar2.b = viewModelStore;
        return bVar2;
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        Lifecycle lifecycle = getLifecycle();
        if (lifecycle instanceof LifecycleRegistry) {
            ((LifecycleRegistry) lifecycle).setCurrentState(Lifecycle.State.CREATED);
        }
        super.onSaveInstanceState(bundle);
        this.f13h.b(bundle);
    }

    public final OnBackPressedDispatcher a() {
        return this.k;
    }

    public ComponentActivity(int i2) {
        this();
        this.l = i2;
    }
}
