package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.R$dimen;
import androidx.appcompat.R$layout;
import androidx.appcompat.view.menu.m;
import androidx.appcompat.widget.MenuPopupWindow;
import androidx.core.h.v;

/* compiled from: StandardMenuPopup */
final class q extends k implements PopupWindow.OnDismissListener, AdapterView.OnItemClickListener, m, View.OnKeyListener {
    private static final int z = R$layout.abc_popup_menu_item_layout;

    /* renamed from: f  reason: collision with root package name */
    private final Context f169f;

    /* renamed from: g  reason: collision with root package name */
    private final g f170g;

    /* renamed from: h  reason: collision with root package name */
    private final f f171h;

    /* renamed from: i  reason: collision with root package name */
    private final boolean f172i;

    /* renamed from: j  reason: collision with root package name */
    private final int f173j;
    private final int k;
    private final int l;
    final MenuPopupWindow m;
    final ViewTreeObserver.OnGlobalLayoutListener n = new a();
    private final View.OnAttachStateChangeListener o = new b();
    private PopupWindow.OnDismissListener p;
    private View q;
    View r;
    private m.a s;
    ViewTreeObserver t;
    private boolean u;
    private boolean v;
    private int w;
    private int x = 0;
    private boolean y;

    /* compiled from: StandardMenuPopup */
    class a implements ViewTreeObserver.OnGlobalLayoutListener {
        a() {
        }

        public void onGlobalLayout() {
            if (q.this.a() && !q.this.m.l()) {
                View view = q.this.r;
                if (view == null || !view.isShown()) {
                    q.this.dismiss();
                } else {
                    q.this.m.c();
                }
            }
        }
    }

    /* compiled from: StandardMenuPopup */
    class b implements View.OnAttachStateChangeListener {
        b() {
        }

        public void onViewAttachedToWindow(View view) {
        }

        public void onViewDetachedFromWindow(View view) {
            ViewTreeObserver viewTreeObserver = q.this.t;
            if (viewTreeObserver != null) {
                if (!viewTreeObserver.isAlive()) {
                    q.this.t = view.getViewTreeObserver();
                }
                q qVar = q.this;
                qVar.t.removeGlobalOnLayoutListener(qVar.n);
            }
            view.removeOnAttachStateChangeListener(this);
        }
    }

    public q(Context context, g gVar, View view, int i2, int i3, boolean z2) {
        this.f169f = context;
        this.f170g = gVar;
        this.f172i = z2;
        this.f171h = new f(gVar, LayoutInflater.from(context), this.f172i, z);
        this.k = i2;
        this.l = i3;
        Resources resources = context.getResources();
        this.f173j = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R$dimen.abc_config_prefDialogWidth));
        this.q = view;
        this.m = new MenuPopupWindow(this.f169f, (AttributeSet) null, this.k, this.l);
        gVar.a((m) this, context);
    }

    private boolean i() {
        View view;
        if (a()) {
            return true;
        }
        if (this.u || (view = this.q) == null) {
            return false;
        }
        this.r = view;
        this.m.setOnDismissListener(this);
        this.m.setOnItemClickListener(this);
        this.m.a(true);
        View view2 = this.r;
        boolean z2 = this.t == null;
        ViewTreeObserver viewTreeObserver = view2.getViewTreeObserver();
        this.t = viewTreeObserver;
        if (z2) {
            viewTreeObserver.addOnGlobalLayoutListener(this.n);
        }
        view2.addOnAttachStateChangeListener(this.o);
        this.m.a(view2);
        this.m.f(this.x);
        if (!this.v) {
            this.w = k.a(this.f171h, (ViewGroup) null, this.f169f, this.f173j);
            this.v = true;
        }
        this.m.e(this.w);
        this.m.g(2);
        this.m.a(h());
        this.m.c();
        ListView g2 = this.m.g();
        g2.setOnKeyListener(this);
        if (this.y && this.f170g.h() != null) {
            FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(this.f169f).inflate(R$layout.abc_popup_menu_header_item_layout, g2, false);
            TextView textView = (TextView) frameLayout.findViewById(16908310);
            if (textView != null) {
                textView.setText(this.f170g.h());
            }
            frameLayout.setEnabled(false);
            g2.addHeaderView(frameLayout, (Object) null, false);
        }
        this.m.a((ListAdapter) this.f171h);
        this.m.c();
        return true;
    }

    public void a(int i2) {
        this.x = i2;
    }

    public void a(Parcelable parcelable) {
    }

    public void a(g gVar) {
    }

    public void b(boolean z2) {
        this.f171h.a(z2);
    }

    public void c() {
        if (!i()) {
            throw new IllegalStateException("StandardMenuPopup cannot be used without an anchor");
        }
    }

    public boolean d() {
        return false;
    }

    public void dismiss() {
        if (a()) {
            this.m.dismiss();
        }
    }

    public Parcelable e() {
        return null;
    }

    public ListView g() {
        return this.m.g();
    }

    public void onDismiss() {
        this.u = true;
        this.f170g.close();
        ViewTreeObserver viewTreeObserver = this.t;
        if (viewTreeObserver != null) {
            if (!viewTreeObserver.isAlive()) {
                this.t = this.r.getViewTreeObserver();
            }
            this.t.removeGlobalOnLayoutListener(this.n);
            this.t = null;
        }
        this.r.removeOnAttachStateChangeListener(this.o);
        PopupWindow.OnDismissListener onDismissListener = this.p;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public boolean onKey(View view, int i2, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i2 != 82) {
            return false;
        }
        dismiss();
        return true;
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.p = onDismissListener;
    }

    public boolean a() {
        return !this.u && this.m.a();
    }

    public void b(int i2) {
        this.m.a(i2);
    }

    public void a(boolean z2) {
        this.v = false;
        f fVar = this.f171h;
        if (fVar != null) {
            fVar.notifyDataSetChanged();
        }
    }

    public void c(int i2) {
        this.m.b(i2);
    }

    public void c(boolean z2) {
        this.y = z2;
    }

    public void a(m.a aVar) {
        this.s = aVar;
    }

    public boolean a(r rVar) {
        if (rVar.hasVisibleItems()) {
            l lVar = new l(this.f169f, rVar, this.r, this.f172i, this.k, this.l);
            lVar.a(this.s);
            lVar.a(k.b((g) rVar));
            lVar.setOnDismissListener(this.p);
            this.p = null;
            this.f170g.a(false);
            int b2 = this.m.b();
            int d = this.m.d();
            if ((Gravity.getAbsoluteGravity(this.x, v.o(this.q)) & 7) == 5) {
                b2 += this.q.getWidth();
            }
            if (lVar.a(b2, d)) {
                m.a aVar = this.s;
                if (aVar == null) {
                    return true;
                }
                aVar.a(rVar);
                return true;
            }
        }
        return false;
    }

    public void a(g gVar, boolean z2) {
        if (gVar == this.f170g) {
            dismiss();
            m.a aVar = this.s;
            if (aVar != null) {
                aVar.a(gVar, z2);
            }
        }
    }

    public void a(View view) {
        this.q = view;
    }
}
