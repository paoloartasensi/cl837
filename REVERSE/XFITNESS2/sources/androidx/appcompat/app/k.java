package androidx.appcompat.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$id;
import androidx.appcompat.R$styleable;
import androidx.appcompat.app.a;
import androidx.appcompat.d.b;
import androidx.appcompat.d.h;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.widget.ActionBarContainer;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.ScrollingTabContainerView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.p;
import androidx.core.h.a0;
import androidx.core.h.b0;
import androidx.core.h.c0;
import androidx.core.h.v;
import androidx.core.h.z;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* compiled from: WindowDecorActionBar */
public class k extends a implements ActionBarOverlayLayout.d {
    private static final Interpolator B = new AccelerateInterpolator();
    private static final Interpolator C = new DecelerateInterpolator();
    final c0 A = new c();
    Context a;
    private Context b;
    ActionBarOverlayLayout c;
    ActionBarContainer d;
    p e;

    /* renamed from: f  reason: collision with root package name */
    ActionBarContextView f74f;

    /* renamed from: g  reason: collision with root package name */
    View f75g;

    /* renamed from: h  reason: collision with root package name */
    ScrollingTabContainerView f76h;

    /* renamed from: i  reason: collision with root package name */
    private boolean f77i;

    /* renamed from: j  reason: collision with root package name */
    d f78j;
    androidx.appcompat.d.b k;
    b.a l;
    private boolean m;
    private ArrayList<a.b> n = new ArrayList<>();
    private boolean o;
    private int p = 0;
    boolean q = true;
    boolean r;
    boolean s;
    private boolean t;
    private boolean u = true;
    h v;
    private boolean w;
    boolean x;
    final a0 y = new a();
    final a0 z = new b();

    /* compiled from: WindowDecorActionBar */
    class a extends b0 {
        a() {
        }

        public void a(View view) {
            View view2;
            k kVar = k.this;
            if (kVar.q && (view2 = kVar.f75g) != null) {
                view2.setTranslationY(0.0f);
                k.this.d.setTranslationY(0.0f);
            }
            k.this.d.setVisibility(8);
            k.this.d.setTransitioning(false);
            k kVar2 = k.this;
            kVar2.v = null;
            kVar2.l();
            ActionBarOverlayLayout actionBarOverlayLayout = k.this.c;
            if (actionBarOverlayLayout != null) {
                v.I(actionBarOverlayLayout);
            }
        }
    }

    /* compiled from: WindowDecorActionBar */
    class b extends b0 {
        b() {
        }

        public void a(View view) {
            k kVar = k.this;
            kVar.v = null;
            kVar.d.requestLayout();
        }
    }

    /* compiled from: WindowDecorActionBar */
    class c implements c0 {
        c() {
        }

        public void a(View view) {
            ((View) k.this.d.getParent()).invalidate();
        }
    }

    /* compiled from: WindowDecorActionBar */
    public class d extends androidx.appcompat.d.b implements g.a {

        /* renamed from: g  reason: collision with root package name */
        private final Context f79g;

        /* renamed from: h  reason: collision with root package name */
        private final g f80h;

        /* renamed from: i  reason: collision with root package name */
        private b.a f81i;

        /* renamed from: j  reason: collision with root package name */
        private WeakReference<View> f82j;

        public d(Context context, b.a aVar) {
            this.f79g = context;
            this.f81i = aVar;
            g gVar = new g(context);
            gVar.c(1);
            this.f80h = gVar;
            gVar.a((g.a) this);
        }

        public void a() {
            k kVar = k.this;
            if (kVar.f78j == this) {
                if (!k.a(kVar.r, kVar.s, false)) {
                    k kVar2 = k.this;
                    kVar2.k = this;
                    kVar2.l = this.f81i;
                } else {
                    this.f81i.a(this);
                }
                this.f81i = null;
                k.this.f(false);
                k.this.f74f.a();
                k.this.e.j().sendAccessibilityEvent(32);
                k kVar3 = k.this;
                kVar3.c.setHideOnContentScrollEnabled(kVar3.x);
                k.this.f78j = null;
            }
        }

        public void b(CharSequence charSequence) {
            k.this.f74f.setTitle(charSequence);
        }

        public Menu c() {
            return this.f80h;
        }

        public MenuInflater d() {
            return new androidx.appcompat.d.g(this.f79g);
        }

        public CharSequence e() {
            return k.this.f74f.getSubtitle();
        }

        public CharSequence g() {
            return k.this.f74f.getTitle();
        }

        public void i() {
            if (k.this.f78j == this) {
                this.f80h.s();
                try {
                    this.f81i.a((androidx.appcompat.d.b) this, (Menu) this.f80h);
                } finally {
                    this.f80h.r();
                }
            }
        }

        public boolean j() {
            return k.this.f74f.b();
        }

        public boolean k() {
            this.f80h.s();
            try {
                return this.f81i.b(this, this.f80h);
            } finally {
                this.f80h.r();
            }
        }

        public void b(int i2) {
            b((CharSequence) k.this.a.getResources().getString(i2));
        }

        public View b() {
            WeakReference<View> weakReference = this.f82j;
            if (weakReference != null) {
                return (View) weakReference.get();
            }
            return null;
        }

        public void a(View view) {
            k.this.f74f.setCustomView(view);
            this.f82j = new WeakReference<>(view);
        }

        public void a(CharSequence charSequence) {
            k.this.f74f.setSubtitle(charSequence);
        }

        public void a(int i2) {
            a((CharSequence) k.this.a.getResources().getString(i2));
        }

        public void a(boolean z) {
            super.a(z);
            k.this.f74f.setTitleOptional(z);
        }

        public boolean a(g gVar, MenuItem menuItem) {
            b.a aVar = this.f81i;
            if (aVar != null) {
                return aVar.a((androidx.appcompat.d.b) this, menuItem);
            }
            return false;
        }

        public void a(g gVar) {
            if (this.f81i != null) {
                i();
                k.this.f74f.d();
            }
        }
    }

    public k(Activity activity, boolean z2) {
        new ArrayList();
        View decorView = activity.getWindow().getDecorView();
        b(decorView);
        if (!z2) {
            this.f75g = decorView.findViewById(16908290);
        }
    }

    private p a(View view) {
        if (view instanceof p) {
            return (p) view;
        }
        if (view instanceof Toolbar) {
            return ((Toolbar) view).getWrapper();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Can't make a decor toolbar out of ");
        sb.append(view != null ? view.getClass().getSimpleName() : "null");
        throw new IllegalStateException(sb.toString());
    }

    static boolean a(boolean z2, boolean z3, boolean z4) {
        if (z4) {
            return true;
        }
        return !z2 && !z3;
    }

    private void b(View view) {
        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) view.findViewById(R$id.decor_content_parent);
        this.c = actionBarOverlayLayout;
        if (actionBarOverlayLayout != null) {
            actionBarOverlayLayout.setActionBarVisibilityCallback(this);
        }
        this.e = a(view.findViewById(R$id.action_bar));
        this.f74f = (ActionBarContextView) view.findViewById(R$id.action_context_bar);
        ActionBarContainer actionBarContainer = (ActionBarContainer) view.findViewById(R$id.action_bar_container);
        this.d = actionBarContainer;
        p pVar = this.e;
        if (pVar == null || this.f74f == null || actionBarContainer == null) {
            throw new IllegalStateException(k.class.getSimpleName() + " can only be used with a compatible window decor layout");
        }
        this.a = pVar.getContext();
        boolean z2 = (this.e.h() & 4) != 0;
        if (z2) {
            this.f77i = true;
        }
        androidx.appcompat.d.a a2 = androidx.appcompat.d.a.a(this.a);
        j(a2.a() || z2);
        k(a2.f());
        TypedArray obtainStyledAttributes = this.a.obtainStyledAttributes((AttributeSet) null, R$styleable.ActionBar, R$attr.actionBarStyle, 0);
        if (obtainStyledAttributes.getBoolean(R$styleable.ActionBar_hideOnContentScroll, false)) {
            i(true);
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R$styleable.ActionBar_elevation, 0);
        if (dimensionPixelSize != 0) {
            a((float) dimensionPixelSize);
        }
        obtainStyledAttributes.recycle();
    }

    private void k(boolean z2) {
        this.o = z2;
        if (!z2) {
            this.e.a((ScrollingTabContainerView) null);
            this.d.setTabContainer(this.f76h);
        } else {
            this.d.setTabContainer((ScrollingTabContainerView) null);
            this.e.a(this.f76h);
        }
        boolean z3 = true;
        boolean z4 = m() == 2;
        ScrollingTabContainerView scrollingTabContainerView = this.f76h;
        if (scrollingTabContainerView != null) {
            if (z4) {
                scrollingTabContainerView.setVisibility(0);
                ActionBarOverlayLayout actionBarOverlayLayout = this.c;
                if (actionBarOverlayLayout != null) {
                    v.I(actionBarOverlayLayout);
                }
            } else {
                scrollingTabContainerView.setVisibility(8);
            }
        }
        this.e.b(!this.o && z4);
        ActionBarOverlayLayout actionBarOverlayLayout2 = this.c;
        if (this.o || !z4) {
            z3 = false;
        }
        actionBarOverlayLayout2.setHasNonEmbeddedTabs(z3);
    }

    private void n() {
        if (this.t) {
            this.t = false;
            ActionBarOverlayLayout actionBarOverlayLayout = this.c;
            if (actionBarOverlayLayout != null) {
                actionBarOverlayLayout.setShowingForActionMode(false);
            }
            l(false);
        }
    }

    private boolean o() {
        return v.D(this.d);
    }

    private void p() {
        if (!this.t) {
            this.t = true;
            ActionBarOverlayLayout actionBarOverlayLayout = this.c;
            if (actionBarOverlayLayout != null) {
                actionBarOverlayLayout.setShowingForActionMode(true);
            }
            l(false);
        }
    }

    public void addOnMenuVisibilityListener(a.b bVar) {
        this.n.add(bVar);
    }

    public void c() {
        if (!this.s) {
            this.s = true;
            l(true);
        }
    }

    public void d() {
    }

    public void d(boolean z2) {
        a(z2 ? 4 : 0, 4);
    }

    public void e(boolean z2) {
        h hVar;
        this.w = z2;
        if (!z2 && (hVar = this.v) != null) {
            hVar.a();
        }
    }

    public void f(boolean z2) {
        z zVar;
        z zVar2;
        if (z2) {
            p();
        } else {
            n();
        }
        if (o()) {
            if (z2) {
                zVar = this.e.a(4, 100);
                zVar2 = this.f74f.a(0, 200);
            } else {
                zVar2 = this.e.a(0, 200);
                zVar = this.f74f.a(8, 100);
            }
            h hVar = new h();
            hVar.a(zVar, zVar2);
            hVar.c();
        } else if (z2) {
            this.e.a(4);
            this.f74f.setVisibility(0);
        } else {
            this.e.a(0);
            this.f74f.setVisibility(8);
        }
    }

    public int g() {
        return this.e.h();
    }

    public void h(boolean z2) {
        View view;
        View view2;
        h hVar = this.v;
        if (hVar != null) {
            hVar.a();
        }
        this.d.setVisibility(0);
        if (this.p != 0 || (!this.w && !z2)) {
            this.d.setAlpha(1.0f);
            this.d.setTranslationY(0.0f);
            if (this.q && (view = this.f75g) != null) {
                view.setTranslationY(0.0f);
            }
            this.z.a((View) null);
        } else {
            this.d.setTranslationY(0.0f);
            float f2 = (float) (-this.d.getHeight());
            if (z2) {
                int[] iArr = {0, 0};
                this.d.getLocationInWindow(iArr);
                f2 -= (float) iArr[1];
            }
            this.d.setTranslationY(f2);
            h hVar2 = new h();
            z a2 = v.a(this.d);
            a2.b(0.0f);
            a2.a(this.A);
            hVar2.a(a2);
            if (this.q && (view2 = this.f75g) != null) {
                view2.setTranslationY(f2);
                z a3 = v.a(this.f75g);
                a3.b(0.0f);
                hVar2.a(a3);
            }
            hVar2.a(C);
            hVar2.a(250);
            hVar2.a(this.z);
            this.v = hVar2;
            hVar2.c();
        }
        ActionBarOverlayLayout actionBarOverlayLayout = this.c;
        if (actionBarOverlayLayout != null) {
            v.I(actionBarOverlayLayout);
        }
    }

    public void i(boolean z2) {
        if (!z2 || this.c.i()) {
            this.x = z2;
            this.c.setHideOnContentScrollEnabled(z2);
            return;
        }
        throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
    }

    public void j(boolean z2) {
        this.e.a(z2);
    }

    /* access modifiers changed from: package-private */
    public void l() {
        b.a aVar = this.l;
        if (aVar != null) {
            aVar.a(this.k);
            this.k = null;
            this.l = null;
        }
    }

    public int m() {
        return this.e.k();
    }

    public void removeOnMenuVisibilityListener(a.b bVar) {
        this.n.remove(bVar);
    }

    public void g(boolean z2) {
        View view;
        h hVar = this.v;
        if (hVar != null) {
            hVar.a();
        }
        if (this.p != 0 || (!this.w && !z2)) {
            this.y.a((View) null);
            return;
        }
        this.d.setAlpha(1.0f);
        this.d.setTransitioning(true);
        h hVar2 = new h();
        float f2 = (float) (-this.d.getHeight());
        if (z2) {
            int[] iArr = {0, 0};
            this.d.getLocationInWindow(iArr);
            f2 -= (float) iArr[1];
        }
        z a2 = v.a(this.d);
        a2.b(f2);
        a2.a(this.A);
        hVar2.a(a2);
        if (this.q && (view = this.f75g) != null) {
            z a3 = v.a(view);
            a3.b(f2);
            hVar2.a(a3);
        }
        hVar2.a(B);
        hVar2.a(250);
        hVar2.a(this.y);
        this.v = hVar2;
        hVar2.c();
    }

    public void c(boolean z2) {
        if (!this.f77i) {
            d(z2);
        }
    }

    private void l(boolean z2) {
        if (a(this.r, this.s, this.t)) {
            if (!this.u) {
                this.u = true;
                h(z2);
            }
        } else if (this.u) {
            this.u = false;
            g(z2);
        }
    }

    public void a(float f2) {
        v.a((View) this.d, f2);
    }

    public void a(Configuration configuration) {
        k(androidx.appcompat.d.a.a(this.a).f());
    }

    public void a(int i2) {
        this.p = i2;
    }

    public void a(CharSequence charSequence) {
        this.e.setTitle(charSequence);
    }

    public void a(int i2, int i3) {
        int h2 = this.e.h();
        if ((i3 & 4) != 0) {
            this.f77i = true;
        }
        this.e.d((i2 & i3) | ((i3 ^ -1) & h2));
    }

    public k(Dialog dialog) {
        new ArrayList();
        b(dialog.getWindow().getDecorView());
    }

    public androidx.appcompat.d.b a(b.a aVar) {
        d dVar = this.f78j;
        if (dVar != null) {
            dVar.a();
        }
        this.c.setHideOnContentScrollEnabled(false);
        this.f74f.c();
        d dVar2 = new d(this.f74f.getContext(), aVar);
        if (!dVar2.k()) {
            return null;
        }
        this.f78j = dVar2;
        dVar2.i();
        this.f74f.a(dVar2);
        f(true);
        this.f74f.sendAccessibilityEvent(32);
        return dVar2;
    }

    public boolean f() {
        p pVar = this.e;
        if (pVar == null || !pVar.m()) {
            return false;
        }
        this.e.collapseActionView();
        return true;
    }

    public void b(boolean z2) {
        if (z2 != this.m) {
            this.m = z2;
            int size = this.n.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.n.get(i2).a(z2);
            }
        }
    }

    public void b(CharSequence charSequence) {
        this.e.setWindowTitle(charSequence);
    }

    public void a(boolean z2) {
        this.q = z2;
    }

    public void b(int i2) {
        this.e.c(i2);
    }

    public void a() {
        if (this.s) {
            this.s = false;
            l(true);
        }
    }

    public void b() {
        h hVar = this.v;
        if (hVar != null) {
            hVar.a();
            this.v = null;
        }
    }

    public void a(Drawable drawable) {
        this.e.a(drawable);
    }

    public boolean a(int i2, KeyEvent keyEvent) {
        Menu c2;
        d dVar = this.f78j;
        if (dVar == null || (c2 = dVar.c()) == null) {
            return false;
        }
        boolean z2 = true;
        if (KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() == 1) {
            z2 = false;
        }
        c2.setQwertyMode(z2);
        return c2.performShortcut(i2, keyEvent, 0);
    }

    public Context h() {
        if (this.b == null) {
            TypedValue typedValue = new TypedValue();
            this.a.getTheme().resolveAttribute(R$attr.actionBarWidgetTheme, typedValue, true);
            int i2 = typedValue.resourceId;
            if (i2 != 0) {
                this.b = new ContextThemeWrapper(this.a, i2);
            } else {
                this.b = this.a;
            }
        }
        return this.b;
    }
}
