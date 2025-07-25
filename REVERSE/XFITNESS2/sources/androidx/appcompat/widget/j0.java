package androidx.appcompat.widget;

import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityManager;
import androidx.core.h.v;
import androidx.core.h.w;

/* compiled from: TooltipCompatHandler */
class j0 implements View.OnLongClickListener, View.OnHoverListener, View.OnAttachStateChangeListener {
    private static j0 n;
    private static j0 o;
    private final View e;

    /* renamed from: f  reason: collision with root package name */
    private final CharSequence f294f;

    /* renamed from: g  reason: collision with root package name */
    private final int f295g;

    /* renamed from: h  reason: collision with root package name */
    private final Runnable f296h = new a();

    /* renamed from: i  reason: collision with root package name */
    private final Runnable f297i = new b();

    /* renamed from: j  reason: collision with root package name */
    private int f298j;
    private int k;
    private k0 l;
    private boolean m;

    /* compiled from: TooltipCompatHandler */
    class a implements Runnable {
        a() {
        }

        public void run() {
            j0.this.a(false);
        }
    }

    /* compiled from: TooltipCompatHandler */
    class b implements Runnable {
        b() {
        }

        public void run() {
            j0.this.a();
        }
    }

    private j0(View view, CharSequence charSequence) {
        this.e = view;
        this.f294f = charSequence;
        this.f295g = w.a(ViewConfiguration.get(view.getContext()));
        c();
        this.e.setOnLongClickListener(this);
        this.e.setOnHoverListener(this);
    }

    public static void a(View view, CharSequence charSequence) {
        j0 j0Var = n;
        if (j0Var != null && j0Var.e == view) {
            a((j0) null);
        }
        if (TextUtils.isEmpty(charSequence)) {
            j0 j0Var2 = o;
            if (j0Var2 != null && j0Var2.e == view) {
                j0Var2.a();
            }
            view.setOnLongClickListener((View.OnLongClickListener) null);
            view.setLongClickable(false);
            view.setOnHoverListener((View.OnHoverListener) null);
            return;
        }
        new j0(view, charSequence);
    }

    private void b() {
        this.e.removeCallbacks(this.f296h);
    }

    private void c() {
        this.f298j = Integer.MAX_VALUE;
        this.k = Integer.MAX_VALUE;
    }

    private void d() {
        this.e.postDelayed(this.f296h, (long) ViewConfiguration.getLongPressTimeout());
    }

    public boolean onHover(View view, MotionEvent motionEvent) {
        if (this.l != null && this.m) {
            return false;
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.e.getContext().getSystemService("accessibility");
        if (accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action != 7) {
            if (action == 10) {
                c();
                a();
            }
        } else if (this.e.isEnabled() && this.l == null && a(motionEvent)) {
            a(this);
        }
        return false;
    }

    public boolean onLongClick(View view) {
        this.f298j = view.getWidth() / 2;
        this.k = view.getHeight() / 2;
        a(true);
        return true;
    }

    public void onViewAttachedToWindow(View view) {
    }

    public void onViewDetachedFromWindow(View view) {
        a();
    }

    /* access modifiers changed from: package-private */
    public void a(boolean z) {
        long j2;
        int i2;
        long j3;
        if (v.C(this.e)) {
            a((j0) null);
            j0 j0Var = o;
            if (j0Var != null) {
                j0Var.a();
            }
            o = this;
            this.m = z;
            k0 k0Var = new k0(this.e.getContext());
            this.l = k0Var;
            k0Var.a(this.e, this.f298j, this.k, this.m, this.f294f);
            this.e.addOnAttachStateChangeListener(this);
            if (this.m) {
                j2 = 2500;
            } else {
                if ((v.w(this.e) & 1) == 1) {
                    j3 = 3000;
                    i2 = ViewConfiguration.getLongPressTimeout();
                } else {
                    j3 = 15000;
                    i2 = ViewConfiguration.getLongPressTimeout();
                }
                j2 = j3 - ((long) i2);
            }
            this.e.removeCallbacks(this.f297i);
            this.e.postDelayed(this.f297i, j2);
        }
    }

    /* access modifiers changed from: package-private */
    public void a() {
        if (o == this) {
            o = null;
            k0 k0Var = this.l;
            if (k0Var != null) {
                k0Var.a();
                this.l = null;
                c();
                this.e.removeOnAttachStateChangeListener(this);
            } else {
                Log.e("TooltipCompatHandler", "sActiveHandler.mPopup == null");
            }
        }
        if (n == this) {
            a((j0) null);
        }
        this.e.removeCallbacks(this.f297i);
    }

    private static void a(j0 j0Var) {
        j0 j0Var2 = n;
        if (j0Var2 != null) {
            j0Var2.b();
        }
        n = j0Var;
        if (j0Var != null) {
            j0Var.d();
        }
    }

    private boolean a(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (Math.abs(x - this.f298j) <= this.f295g && Math.abs(y - this.k) <= this.f295g) {
            return false;
        }
        this.f298j = x;
        this.k = y;
        return true;
    }
}
