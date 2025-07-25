package androidx.customview.a;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import androidx.core.h.e0.d;
import androidx.core.h.e0.e;
import androidx.core.h.e0.f;
import androidx.core.h.v;
import androidx.core.h.y;
import androidx.customview.a.b;
import g.a.h;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ExploreByTouchHelper */
public abstract class a extends androidx.core.h.a {
    private static final Rect n = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    private static final b.a<d> o = new C0028a();
    private static final b.C0029b<h<d>, d> p = new b();
    private final Rect d = new Rect();
    private final Rect e = new Rect();

    /* renamed from: f  reason: collision with root package name */
    private final Rect f544f = new Rect();

    /* renamed from: g  reason: collision with root package name */
    private final int[] f545g = new int[2];

    /* renamed from: h  reason: collision with root package name */
    private final AccessibilityManager f546h;

    /* renamed from: i  reason: collision with root package name */
    private final View f547i;

    /* renamed from: j  reason: collision with root package name */
    private c f548j;
    int k = Integer.MIN_VALUE;
    int l = Integer.MIN_VALUE;
    private int m = Integer.MIN_VALUE;

    /* renamed from: androidx.customview.a.a$a  reason: collision with other inner class name */
    /* compiled from: ExploreByTouchHelper */
    static class C0028a implements b.a<d> {
        C0028a() {
        }

        public void a(d dVar, Rect rect) {
            dVar.a(rect);
        }
    }

    public a(View view) {
        if (view != null) {
            this.f547i = view;
            this.f546h = (AccessibilityManager) view.getContext().getSystemService("accessibility");
            view.setFocusable(true);
            if (v.m(view) == 0) {
                v.h(view, 1);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("View may not be null");
    }

    private boolean b(int i2, Rect rect) {
        d dVar;
        d dVar2;
        h<d> d2 = d();
        int i3 = this.l;
        int i4 = Integer.MIN_VALUE;
        if (i3 == Integer.MIN_VALUE) {
            dVar = null;
        } else {
            dVar = d2.a(i3);
        }
        d dVar3 = dVar;
        if (i2 == 1 || i2 == 2) {
            dVar2 = (d) b.a(d2, p, o, dVar3, i2, v.o(this.f547i) == 1, false);
        } else if (i2 == 17 || i2 == 33 || i2 == 66 || i2 == 130) {
            Rect rect2 = new Rect();
            int i5 = this.l;
            if (i5 != Integer.MIN_VALUE) {
                a(i5, rect2);
            } else if (rect != null) {
                rect2.set(rect);
            } else {
                a(this.f547i, i2, rect2);
            }
            dVar2 = (d) b.a(d2, p, o, dVar3, rect2, i2);
        } else {
            throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD, FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
        if (dVar2 != null) {
            i4 = d2.c(d2.a(dVar2));
        }
        return c(i4);
    }

    private AccessibilityEvent c(int i2, int i3) {
        AccessibilityEvent obtain = AccessibilityEvent.obtain(i3);
        d b2 = b(i2);
        obtain.getText().add(b2.i());
        obtain.setContentDescription(b2.e());
        obtain.setScrollable(b2.t());
        obtain.setPassword(b2.s());
        obtain.setEnabled(b2.o());
        obtain.setChecked(b2.m());
        a(i2, obtain);
        if (!obtain.getText().isEmpty() || obtain.getContentDescription() != null) {
            obtain.setClassName(b2.c());
            f.a(obtain, this.f547i, i2);
            obtain.setPackageName(this.f547i.getContext().getPackageName());
            return obtain;
        }
        throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
    }

    private h<d> d() {
        ArrayList arrayList = new ArrayList();
        a((List<Integer>) arrayList);
        h<d> hVar = new h<>();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            hVar.c(i2, f(i2));
        }
        return hVar;
    }

    private AccessibilityEvent e(int i2) {
        AccessibilityEvent obtain = AccessibilityEvent.obtain(i2);
        this.f547i.onInitializeAccessibilityEvent(obtain);
        return obtain;
    }

    private d f(int i2) {
        d A = d.A();
        A.h(true);
        A.i(true);
        A.a((CharSequence) "android.view.View");
        A.c(n);
        A.d(n);
        A.b(this.f547i);
        a(i2, A);
        if (A.i() == null && A.e() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }
        A.a(this.e);
        if (!this.e.equals(n)) {
            int a = A.a();
            if ((a & 64) != 0) {
                throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
            } else if ((a & 128) == 0) {
                A.e((CharSequence) this.f547i.getContext().getPackageName());
                A.c(this.f547i, i2);
                if (this.k == i2) {
                    A.a(true);
                    A.a(128);
                } else {
                    A.a(false);
                    A.a(64);
                }
                boolean z = this.l == i2;
                if (z) {
                    A.a(2);
                } else if (A.p()) {
                    A.a(1);
                }
                A.j(z);
                this.f547i.getLocationOnScreen(this.f545g);
                A.b(this.d);
                if (this.d.equals(n)) {
                    A.a(this.d);
                    if (A.b != -1) {
                        d A2 = d.A();
                        for (int i3 = A.b; i3 != -1; i3 = A2.b) {
                            A2.b(this.f547i, -1);
                            A2.c(n);
                            a(i3, A2);
                            A2.a(this.e);
                            Rect rect = this.d;
                            Rect rect2 = this.e;
                            rect.offset(rect2.left, rect2.top);
                        }
                        A2.w();
                    }
                    this.d.offset(this.f545g[0] - this.f547i.getScrollX(), this.f545g[1] - this.f547i.getScrollY());
                }
                if (this.f547i.getLocalVisibleRect(this.f544f)) {
                    this.f544f.offset(this.f545g[0] - this.f547i.getScrollX(), this.f545g[1] - this.f547i.getScrollY());
                    if (this.d.intersect(this.f544f)) {
                        A.d(this.d);
                        if (a(this.d)) {
                            A.q(true);
                        }
                    }
                }
                return A;
            } else {
                throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
            }
        } else {
            throw new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
        }
    }

    private static int g(int i2) {
        if (i2 == 19) {
            return 33;
        }
        if (i2 != 21) {
            return i2 != 22 ? 130 : 66;
        }
        return 17;
    }

    private boolean h(int i2) {
        int i3;
        if (!this.f546h.isEnabled() || !this.f546h.isTouchExplorationEnabled() || (i3 = this.k) == i2) {
            return false;
        }
        if (i3 != Integer.MIN_VALUE) {
            d(i3);
        }
        this.k = i2;
        this.f547i.invalidate();
        a(i2, 32768);
        return true;
    }

    private void i(int i2) {
        int i3 = this.m;
        if (i3 != i2) {
            this.m = i2;
            a(i2, 128);
            a(i3, 256);
        }
    }

    /* access modifiers changed from: protected */
    public abstract int a(float f2, float f3);

    public e a(View view) {
        if (this.f548j == null) {
            this.f548j = new c();
        }
        return this.f548j;
    }

    /* access modifiers changed from: protected */
    public void a(int i2, AccessibilityEvent accessibilityEvent) {
    }

    /* access modifiers changed from: protected */
    public abstract void a(int i2, d dVar);

    /* access modifiers changed from: protected */
    public void a(int i2, boolean z) {
    }

    /* access modifiers changed from: protected */
    public void a(AccessibilityEvent accessibilityEvent) {
    }

    /* access modifiers changed from: protected */
    public void a(d dVar) {
    }

    /* access modifiers changed from: protected */
    public abstract void a(List<Integer> list);

    /* access modifiers changed from: protected */
    public abstract boolean a(int i2, int i3, Bundle bundle);

    /* compiled from: ExploreByTouchHelper */
    static class b implements b.C0029b<h<d>, d> {
        b() {
        }

        public d a(h<d> hVar, int i2) {
            return hVar.f(i2);
        }

        public int a(h<d> hVar) {
            return hVar.d();
        }
    }

    /* compiled from: ExploreByTouchHelper */
    private class c extends e {
        c() {
        }

        public d a(int i2) {
            return d.a(a.this.b(i2));
        }

        public d b(int i2) {
            int i3 = i2 == 2 ? a.this.k : a.this.l;
            if (i3 == Integer.MIN_VALUE) {
                return null;
            }
            return a(i3);
        }

        public boolean a(int i2, int i3, Bundle bundle) {
            return a.this.b(i2, i3, bundle);
        }
    }

    public final boolean a(MotionEvent motionEvent) {
        if (!this.f546h.isEnabled() || !this.f546h.isTouchExplorationEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 7 || action == 9) {
            int a = a(motionEvent.getX(), motionEvent.getY());
            i(a);
            if (a != Integer.MIN_VALUE) {
                return true;
            }
            return false;
        } else if (action != 10 || this.m == Integer.MIN_VALUE) {
            return false;
        } else {
            i(Integer.MIN_VALUE);
            return true;
        }
    }

    private boolean d(int i2) {
        if (this.k != i2) {
            return false;
        }
        this.k = Integer.MIN_VALUE;
        this.f547i.invalidate();
        a(i2, 65536);
        return true;
    }

    public final boolean a(KeyEvent keyEvent) {
        int i2 = 0;
        if (keyEvent.getAction() == 1) {
            return false;
        }
        int keyCode = keyEvent.getKeyCode();
        if (keyCode != 61) {
            if (keyCode != 66) {
                switch (keyCode) {
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                        if (!keyEvent.hasNoModifiers()) {
                            return false;
                        }
                        int g2 = g(keyCode);
                        int repeatCount = keyEvent.getRepeatCount() + 1;
                        boolean z = false;
                        while (i2 < repeatCount && b(g2, (Rect) null)) {
                            i2++;
                            z = true;
                        }
                        return z;
                    case 23:
                        break;
                    default:
                        return false;
                }
            }
            if (!keyEvent.hasNoModifiers() || keyEvent.getRepeatCount() != 0) {
                return false;
            }
            b();
            return true;
        } else if (keyEvent.hasNoModifiers()) {
            return b(2, (Rect) null);
        } else {
            if (keyEvent.hasModifiers(1)) {
                return b(1, (Rect) null);
            }
            return false;
        }
    }

    private d c() {
        d f2 = d.f(this.f547i);
        v.a(this.f547i, f2);
        ArrayList arrayList = new ArrayList();
        a((List<Integer>) arrayList);
        if (f2.b() <= 0 || arrayList.size() <= 0) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                f2.a(this.f547i, ((Integer) arrayList.get(i2)).intValue());
            }
            return f2;
        }
        throw new RuntimeException("Views cannot have both real and virtual children");
    }

    private boolean b() {
        int i2 = this.l;
        return i2 != Integer.MIN_VALUE && a(i2, 16, (Bundle) null);
    }

    private AccessibilityEvent b(int i2, int i3) {
        if (i2 != -1) {
            return c(i2, i3);
        }
        return e(i3);
    }

    public void b(View view, AccessibilityEvent accessibilityEvent) {
        super.b(view, accessibilityEvent);
        a(accessibilityEvent);
    }

    /* access modifiers changed from: package-private */
    public d b(int i2) {
        if (i2 == -1) {
            return c();
        }
        return f(i2);
    }

    public final void a(boolean z, int i2, Rect rect) {
        int i3 = this.l;
        if (i3 != Integer.MIN_VALUE) {
            a(i3);
        }
        if (z) {
            b(i2, rect);
        }
    }

    private boolean c(int i2, int i3, Bundle bundle) {
        if (i3 == 1) {
            return c(i2);
        }
        if (i3 == 2) {
            return a(i2);
        }
        if (i3 == 64) {
            return h(i2);
        }
        if (i3 != 128) {
            return a(i2, i3, bundle);
        }
        return d(i2);
    }

    /* access modifiers changed from: package-private */
    public boolean b(int i2, int i3, Bundle bundle) {
        if (i2 != -1) {
            return c(i2, i3, bundle);
        }
        return a(i3, bundle);
    }

    private void a(int i2, Rect rect) {
        b(i2).a(rect);
    }

    private static Rect a(View view, int i2, Rect rect) {
        int width = view.getWidth();
        int height = view.getHeight();
        if (i2 == 17) {
            rect.set(width, 0, width, height);
        } else if (i2 == 33) {
            rect.set(0, height, width, height);
        } else if (i2 == 66) {
            rect.set(-1, 0, -1, height);
        } else if (i2 == 130) {
            rect.set(0, -1, width, -1);
        } else {
            throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
        return rect;
    }

    public final boolean c(int i2) {
        int i3;
        if ((!this.f547i.isFocused() && !this.f547i.requestFocus()) || (i3 = this.l) == i2) {
            return false;
        }
        if (i3 != Integer.MIN_VALUE) {
            a(i3);
        }
        this.l = i2;
        a(i2, true);
        a(i2, 8);
        return true;
    }

    public final boolean a(int i2, int i3) {
        ViewParent parent;
        if (i2 == Integer.MIN_VALUE || !this.f546h.isEnabled() || (parent = this.f547i.getParent()) == null) {
            return false;
        }
        return y.a(parent, this.f547i, b(i2, i3));
    }

    public void a(View view, d dVar) {
        super.a(view, dVar);
        a(dVar);
    }

    private boolean a(int i2, Bundle bundle) {
        return v.a(this.f547i, i2, bundle);
    }

    private boolean a(Rect rect) {
        if (rect == null || rect.isEmpty() || this.f547i.getWindowVisibility() != 0) {
            return false;
        }
        ViewParent parent = this.f547i.getParent();
        while (parent instanceof View) {
            View view = (View) parent;
            if (view.getAlpha() <= 0.0f || view.getVisibility() != 0) {
                return false;
            }
            parent = view.getParent();
        }
        if (parent != null) {
            return true;
        }
        return false;
    }

    public final boolean a(int i2) {
        if (this.l != i2) {
            return false;
        }
        this.l = Integer.MIN_VALUE;
        a(i2, false);
        a(i2, 8);
        return true;
    }
}
