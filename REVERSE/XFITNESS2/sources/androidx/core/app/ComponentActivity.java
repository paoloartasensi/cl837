package androidx.core.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import androidx.core.h.e;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ReportFragment;
import g.a.g;

public class ComponentActivity extends Activity implements LifecycleOwner, e.a {
    private g<Class<? extends a>, a> e = new g<>();

    /* renamed from: f  reason: collision with root package name */
    private LifecycleRegistry f437f = new LifecycleRegistry(this);

    public static class a {
    }

    public void a(a aVar) {
        this.e.put(aVar.getClass(), aVar);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        View decorView = getWindow().getDecorView();
        if (decorView == null || !e.a(decorView, keyEvent)) {
            return e.a(this, decorView, this, keyEvent);
        }
        return true;
    }

    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        View decorView = getWindow().getDecorView();
        if (decorView == null || !e.a(decorView, keyEvent)) {
            return super.dispatchKeyShortcutEvent(keyEvent);
        }
        return true;
    }

    public Lifecycle getLifecycle() {
        return this.f437f;
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"RestrictedApi"})
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ReportFragment.injectIfNeededIn(this);
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        this.f437f.markState(Lifecycle.State.CREATED);
        super.onSaveInstanceState(bundle);
    }

    public <T extends a> T a(Class<T> cls) {
        return (a) this.e.get(cls);
    }

    public boolean a(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }
}
