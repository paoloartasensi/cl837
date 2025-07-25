package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$drawable;
import androidx.appcompat.R$id;
import androidx.appcompat.R$string;
import androidx.appcompat.R$styleable;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.m;
import androidx.appcompat.widget.Toolbar;
import androidx.core.h.a0;
import androidx.core.h.b0;
import androidx.core.h.v;
import androidx.core.h.z;

/* compiled from: ToolbarWidgetWrapper */
public class h0 implements p {
    Toolbar a;
    private int b;
    private View c;
    private View d;
    private Drawable e;

    /* renamed from: f  reason: collision with root package name */
    private Drawable f288f;

    /* renamed from: g  reason: collision with root package name */
    private Drawable f289g;

    /* renamed from: h  reason: collision with root package name */
    private boolean f290h;

    /* renamed from: i  reason: collision with root package name */
    CharSequence f291i;

    /* renamed from: j  reason: collision with root package name */
    private CharSequence f292j;
    private CharSequence k;
    Window.Callback l;
    boolean m;
    private ActionMenuPresenter n;
    private int o;
    private int p;
    private Drawable q;

    /* compiled from: ToolbarWidgetWrapper */
    class a implements View.OnClickListener {
        final androidx.appcompat.view.menu.a e = new androidx.appcompat.view.menu.a(h0.this.a.getContext(), 0, 16908332, 0, 0, h0.this.f291i);

        a() {
        }

        public void onClick(View view) {
            h0 h0Var = h0.this;
            Window.Callback callback = h0Var.l;
            if (callback != null && h0Var.m) {
                callback.onMenuItemSelected(0, this.e);
            }
        }
    }

    /* compiled from: ToolbarWidgetWrapper */
    class b extends b0 {
        private boolean a = false;
        final /* synthetic */ int b;

        b(int i2) {
            this.b = i2;
        }

        public void a(View view) {
            if (!this.a) {
                h0.this.a.setVisibility(this.b);
            }
        }

        public void b(View view) {
            h0.this.a.setVisibility(0);
        }

        public void c(View view) {
            this.a = true;
        }
    }

    public h0(Toolbar toolbar, boolean z) {
        this(toolbar, z, R$string.abc_action_bar_up_description, R$drawable.abc_ic_ab_back_material);
    }

    private void c(CharSequence charSequence) {
        this.f291i = charSequence;
        if ((this.b & 8) != 0) {
            this.a.setTitle(charSequence);
        }
    }

    private int o() {
        if (this.a.getNavigationIcon() == null) {
            return 11;
        }
        this.q = this.a.getNavigationIcon();
        return 15;
    }

    private void p() {
        if ((this.b & 4) == 0) {
            return;
        }
        if (TextUtils.isEmpty(this.k)) {
            this.a.setNavigationContentDescription(this.p);
        } else {
            this.a.setNavigationContentDescription(this.k);
        }
    }

    private void q() {
        if ((this.b & 4) != 0) {
            Toolbar toolbar = this.a;
            Drawable drawable = this.f289g;
            if (drawable == null) {
                drawable = this.q;
            }
            toolbar.setNavigationIcon(drawable);
            return;
        }
        this.a.setNavigationIcon((Drawable) null);
    }

    private void r() {
        Drawable drawable;
        int i2 = this.b;
        if ((i2 & 2) == 0) {
            drawable = null;
        } else if ((i2 & 1) != 0) {
            drawable = this.f288f;
            if (drawable == null) {
                drawable = this.e;
            }
        } else {
            drawable = this.e;
        }
        this.a.setLogo(drawable);
    }

    public void a(boolean z) {
    }

    public boolean a() {
        return this.a.h();
    }

    public void b(CharSequence charSequence) {
        this.f292j = charSequence;
        if ((this.b & 8) != 0) {
            this.a.setSubtitle(charSequence);
        }
    }

    public void collapseActionView() {
        this.a.c();
    }

    public boolean d() {
        return this.a.k();
    }

    public void e(int i2) {
        if (i2 != this.p) {
            this.p = i2;
            if (TextUtils.isEmpty(this.a.getNavigationContentDescription())) {
                c(this.p);
            }
        }
    }

    public boolean f() {
        return this.a.b();
    }

    public void g() {
        this.a.d();
    }

    public Context getContext() {
        return this.a.getContext();
    }

    public CharSequence getTitle() {
        return this.a.getTitle();
    }

    public int h() {
        return this.b;
    }

    public Menu i() {
        return this.a.getMenu();
    }

    public ViewGroup j() {
        return this.a;
    }

    public int k() {
        return this.o;
    }

    public void l() {
        Log.i("ToolbarWidgetWrapper", "Progress display unsupported");
    }

    public boolean m() {
        return this.a.f();
    }

    public void n() {
        Log.i("ToolbarWidgetWrapper", "Progress display unsupported");
    }

    public void setIcon(int i2) {
        setIcon(i2 != 0 ? androidx.appcompat.a.a.a.c(getContext(), i2) : null);
    }

    public void setTitle(CharSequence charSequence) {
        this.f290h = true;
        c(charSequence);
    }

    public void setWindowCallback(Window.Callback callback) {
        this.l = callback;
    }

    public void setWindowTitle(CharSequence charSequence) {
        if (!this.f290h) {
            c(charSequence);
        }
    }

    public h0(Toolbar toolbar, boolean z, int i2, int i3) {
        Drawable drawable;
        this.o = 0;
        this.p = 0;
        this.a = toolbar;
        this.f291i = toolbar.getTitle();
        this.f292j = toolbar.getSubtitle();
        this.f290h = this.f291i != null;
        this.f289g = toolbar.getNavigationIcon();
        g0 a2 = g0.a(toolbar.getContext(), (AttributeSet) null, R$styleable.ActionBar, R$attr.actionBarStyle, 0);
        this.q = a2.b(R$styleable.ActionBar_homeAsUpIndicator);
        if (z) {
            CharSequence e2 = a2.e(R$styleable.ActionBar_title);
            if (!TextUtils.isEmpty(e2)) {
                setTitle(e2);
            }
            CharSequence e3 = a2.e(R$styleable.ActionBar_subtitle);
            if (!TextUtils.isEmpty(e3)) {
                b(e3);
            }
            Drawable b2 = a2.b(R$styleable.ActionBar_logo);
            if (b2 != null) {
                b(b2);
            }
            Drawable b3 = a2.b(R$styleable.ActionBar_icon);
            if (b3 != null) {
                setIcon(b3);
            }
            if (this.f289g == null && (drawable = this.q) != null) {
                a(drawable);
            }
            d(a2.d(R$styleable.ActionBar_displayOptions, 0));
            int g2 = a2.g(R$styleable.ActionBar_customNavigationLayout, 0);
            if (g2 != 0) {
                a(LayoutInflater.from(this.a.getContext()).inflate(g2, this.a, false));
                d(this.b | 16);
            }
            int f2 = a2.f(R$styleable.ActionBar_height, 0);
            if (f2 > 0) {
                ViewGroup.LayoutParams layoutParams = this.a.getLayoutParams();
                layoutParams.height = f2;
                this.a.setLayoutParams(layoutParams);
            }
            int b4 = a2.b(R$styleable.ActionBar_contentInsetStart, -1);
            int b5 = a2.b(R$styleable.ActionBar_contentInsetEnd, -1);
            if (b4 >= 0 || b5 >= 0) {
                this.a.a(Math.max(b4, 0), Math.max(b5, 0));
            }
            int g3 = a2.g(R$styleable.ActionBar_titleTextStyle, 0);
            if (g3 != 0) {
                Toolbar toolbar2 = this.a;
                toolbar2.b(toolbar2.getContext(), g3);
            }
            int g4 = a2.g(R$styleable.ActionBar_subtitleTextStyle, 0);
            if (g4 != 0) {
                Toolbar toolbar3 = this.a;
                toolbar3.a(toolbar3.getContext(), g4);
            }
            int g5 = a2.g(R$styleable.ActionBar_popupTheme, 0);
            if (g5 != 0) {
                this.a.setPopupTheme(g5);
            }
        } else {
            this.b = o();
        }
        a2.a();
        e(i2);
        this.k = this.a.getNavigationContentDescription();
        this.a.setNavigationOnClickListener(new a());
    }

    public void a(Menu menu, m.a aVar) {
        if (this.n == null) {
            ActionMenuPresenter actionMenuPresenter = new ActionMenuPresenter(this.a.getContext());
            this.n = actionMenuPresenter;
            actionMenuPresenter.a(R$id.action_menu_presenter);
        }
        this.n.a(aVar);
        this.a.a((g) menu, this.n);
    }

    public void d(int i2) {
        View view;
        int i3 = this.b ^ i2;
        this.b = i2;
        if (i3 != 0) {
            if ((i3 & 4) != 0) {
                if ((i2 & 4) != 0) {
                    p();
                }
                q();
            }
            if ((i3 & 3) != 0) {
                r();
            }
            if ((i3 & 8) != 0) {
                if ((i2 & 8) != 0) {
                    this.a.setTitle(this.f291i);
                    this.a.setSubtitle(this.f292j);
                } else {
                    this.a.setTitle((CharSequence) null);
                    this.a.setSubtitle((CharSequence) null);
                }
            }
            if ((i3 & 16) != 0 && (view = this.d) != null) {
                if ((i2 & 16) != 0) {
                    this.a.addView(view);
                } else {
                    this.a.removeView(view);
                }
            }
        }
    }

    public void setIcon(Drawable drawable) {
        this.e = drawable;
        r();
    }

    public void b(int i2) {
        b(i2 != 0 ? androidx.appcompat.a.a.a.c(getContext(), i2) : null);
    }

    public boolean c() {
        return this.a.g();
    }

    public void b(Drawable drawable) {
        this.f288f = drawable;
        r();
    }

    public void c(int i2) {
        a((CharSequence) i2 == 0 ? null : getContext().getString(i2));
    }

    public void e() {
        this.m = true;
    }

    public void a(ScrollingTabContainerView scrollingTabContainerView) {
        Toolbar toolbar;
        View view = this.c;
        if (view != null && view.getParent() == (toolbar = this.a)) {
            toolbar.removeView(this.c);
        }
        this.c = scrollingTabContainerView;
        if (scrollingTabContainerView != null && this.o == 2) {
            this.a.addView(scrollingTabContainerView, 0);
            Toolbar.e eVar = (Toolbar.e) this.c.getLayoutParams();
            eVar.width = -2;
            eVar.height = -2;
            eVar.a = 8388691;
            scrollingTabContainerView.setAllowCollapse(true);
        }
    }

    public boolean b() {
        return this.a.i();
    }

    public void b(boolean z) {
        this.a.setCollapsible(z);
    }

    public void a(View view) {
        View view2 = this.d;
        if (!(view2 == null || (this.b & 16) == 0)) {
            this.a.removeView(view2);
        }
        this.d = view;
        if (view != null && (this.b & 16) != 0) {
            this.a.addView(view);
        }
    }

    public z a(int i2, long j2) {
        z a2 = v.a(this.a);
        a2.a(i2 == 0 ? 1.0f : 0.0f);
        a2.a(j2);
        a2.a((a0) new b(i2));
        return a2;
    }

    public void a(Drawable drawable) {
        this.f289g = drawable;
        q();
    }

    public void a(CharSequence charSequence) {
        this.k = charSequence;
        p();
    }

    public void a(int i2) {
        this.a.setVisibility(i2);
    }

    public void a(m.a aVar, g.a aVar2) {
        this.a.a(aVar, aVar2);
    }
}
