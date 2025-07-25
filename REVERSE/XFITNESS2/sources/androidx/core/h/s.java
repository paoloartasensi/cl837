package androidx.core.h;

import android.view.View;
import android.view.ViewTreeObserver;

/* compiled from: OneShotPreDrawListener */
public final class s implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {
    private final View e;

    /* renamed from: f  reason: collision with root package name */
    private ViewTreeObserver f516f;

    /* renamed from: g  reason: collision with root package name */
    private final Runnable f517g;

    private s(View view, Runnable runnable) {
        this.e = view;
        this.f516f = view.getViewTreeObserver();
        this.f517g = runnable;
    }

    public static s a(View view, Runnable runnable) {
        if (view == null) {
            throw new NullPointerException("view == null");
        } else if (runnable != null) {
            s sVar = new s(view, runnable);
            view.getViewTreeObserver().addOnPreDrawListener(sVar);
            view.addOnAttachStateChangeListener(sVar);
            return sVar;
        } else {
            throw new NullPointerException("runnable == null");
        }
    }

    public boolean onPreDraw() {
        a();
        this.f517g.run();
        return true;
    }

    public void onViewAttachedToWindow(View view) {
        this.f516f = view.getViewTreeObserver();
    }

    public void onViewDetachedFromWindow(View view) {
        a();
    }

    public void a() {
        if (this.f516f.isAlive()) {
            this.f516f.removeOnPreDrawListener(this);
        } else {
            this.e.getViewTreeObserver().removeOnPreDrawListener(this);
        }
        this.e.removeOnAttachStateChangeListener(this);
    }
}
