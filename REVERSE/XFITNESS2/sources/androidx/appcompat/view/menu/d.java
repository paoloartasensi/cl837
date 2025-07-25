package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.R$dimen;
import androidx.appcompat.R$layout;
import androidx.appcompat.view.menu.m;
import androidx.appcompat.widget.MenuPopupWindow;
import androidx.appcompat.widget.v;
import java.util.ArrayList;
import java.util.List;

/* compiled from: CascadingMenuPopup */
final class d extends k implements m, View.OnKeyListener, PopupWindow.OnDismissListener {
    private static final int F = R$layout.abc_cascading_menu_item_layout;
    private boolean A;
    private m.a B;
    ViewTreeObserver C;
    private PopupWindow.OnDismissListener D;
    boolean E;

    /* renamed from: f  reason: collision with root package name */
    private final Context f131f;

    /* renamed from: g  reason: collision with root package name */
    private final int f132g;

    /* renamed from: h  reason: collision with root package name */
    private final int f133h;

    /* renamed from: i  reason: collision with root package name */
    private final int f134i;

    /* renamed from: j  reason: collision with root package name */
    private final boolean f135j;
    final Handler k;
    private final List<g> l = new ArrayList();
    final List<C0005d> m = new ArrayList();
    final ViewTreeObserver.OnGlobalLayoutListener n = new a();
    private final View.OnAttachStateChangeListener o = new b();
    private final v p = new c();
    private int q = 0;
    private int r = 0;
    private View s;
    View t;
    private int u;
    private boolean v;
    private boolean w;
    private int x;
    private int y;
    private boolean z;

    /* compiled from: CascadingMenuPopup */
    class a implements ViewTreeObserver.OnGlobalLayoutListener {
        a() {
        }

        public void onGlobalLayout() {
            if (d.this.a() && d.this.m.size() > 0 && !d.this.m.get(0).a.l()) {
                View view = d.this.t;
                if (view == null || !view.isShown()) {
                    d.this.dismiss();
                    return;
                }
                for (C0005d dVar : d.this.m) {
                    dVar.a.c();
                }
            }
        }
    }

    /* compiled from: CascadingMenuPopup */
    class b implements View.OnAttachStateChangeListener {
        b() {
        }

        public void onViewAttachedToWindow(View view) {
        }

        public void onViewDetachedFromWindow(View view) {
            ViewTreeObserver viewTreeObserver = d.this.C;
            if (viewTreeObserver != null) {
                if (!viewTreeObserver.isAlive()) {
                    d.this.C = view.getViewTreeObserver();
                }
                d dVar = d.this;
                dVar.C.removeGlobalOnLayoutListener(dVar.n);
            }
            view.removeOnAttachStateChangeListener(this);
        }
    }

    /* compiled from: CascadingMenuPopup */
    class c implements v {

        /* compiled from: CascadingMenuPopup */
        class a implements Runnable {
            final /* synthetic */ C0005d e;

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ MenuItem f136f;

            /* renamed from: g  reason: collision with root package name */
            final /* synthetic */ g f137g;

            a(C0005d dVar, MenuItem menuItem, g gVar) {
                this.e = dVar;
                this.f136f = menuItem;
                this.f137g = gVar;
            }

            public void run() {
                C0005d dVar = this.e;
                if (dVar != null) {
                    d.this.E = true;
                    dVar.b.a(false);
                    d.this.E = false;
                }
                if (this.f136f.isEnabled() && this.f136f.hasSubMenu()) {
                    this.f137g.a(this.f136f, 4);
                }
            }
        }

        c() {
        }

        public void a(g gVar, MenuItem menuItem) {
            C0005d dVar = null;
            d.this.k.removeCallbacksAndMessages((Object) null);
            int size = d.this.m.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    i2 = -1;
                    break;
                } else if (gVar == d.this.m.get(i2).b) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 != -1) {
                int i3 = i2 + 1;
                if (i3 < d.this.m.size()) {
                    dVar = d.this.m.get(i3);
                }
                d.this.k.postAtTime(new a(dVar, menuItem, gVar), gVar, SystemClock.uptimeMillis() + 200);
            }
        }

        public void b(g gVar, MenuItem menuItem) {
            d.this.k.removeCallbacksAndMessages(gVar);
        }
    }

    /* renamed from: androidx.appcompat.view.menu.d$d  reason: collision with other inner class name */
    /* compiled from: CascadingMenuPopup */
    private static class C0005d {
        public final MenuPopupWindow a;
        public final g b;
        public final int c;

        public C0005d(MenuPopupWindow menuPopupWindow, g gVar, int i2) {
            this.a = menuPopupWindow;
            this.b = gVar;
            this.c = i2;
        }

        public ListView a() {
            return this.a.g();
        }
    }

    public d(Context context, View view, int i2, int i3, boolean z2) {
        this.f131f = context;
        this.s = view;
        this.f133h = i2;
        this.f134i = i3;
        this.f135j = z2;
        this.z = false;
        this.u = j();
        Resources resources = context.getResources();
        this.f132g = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R$dimen.abc_config_prefDialogWidth));
        this.k = new Handler();
    }

    private int d(int i2) {
        List<C0005d> list = this.m;
        ListView a2 = list.get(list.size() - 1).a();
        int[] iArr = new int[2];
        a2.getLocationOnScreen(iArr);
        Rect rect = new Rect();
        this.t.getWindowVisibleDisplayFrame(rect);
        if (this.u == 1) {
            if (iArr[0] + a2.getWidth() + i2 > rect.right) {
                return 0;
            }
            return 1;
        } else if (iArr[0] - i2 < 0) {
            return 1;
        } else {
            return 0;
        }
    }

    private MenuPopupWindow i() {
        MenuPopupWindow menuPopupWindow = new MenuPopupWindow(this.f131f, (AttributeSet) null, this.f133h, this.f134i);
        menuPopupWindow.a(this.p);
        menuPopupWindow.setOnItemClickListener(this);
        menuPopupWindow.setOnDismissListener(this);
        menuPopupWindow.a(this.s);
        menuPopupWindow.f(this.r);
        menuPopupWindow.a(true);
        menuPopupWindow.g(2);
        return menuPopupWindow;
    }

    private int j() {
        return androidx.core.h.v.o(this.s) == 1 ? 0 : 1;
    }

    public void a(Parcelable parcelable) {
    }

    public void a(g gVar) {
        gVar.a((m) this, this.f131f);
        if (a()) {
            d(gVar);
        } else {
            this.l.add(gVar);
        }
    }

    public void b(boolean z2) {
        this.z = z2;
    }

    public void c() {
        if (!a()) {
            for (g d : this.l) {
                d(d);
            }
            this.l.clear();
            View view = this.s;
            this.t = view;
            if (view != null) {
                boolean z2 = this.C == null;
                ViewTreeObserver viewTreeObserver = this.t.getViewTreeObserver();
                this.C = viewTreeObserver;
                if (z2) {
                    viewTreeObserver.addOnGlobalLayoutListener(this.n);
                }
                this.t.addOnAttachStateChangeListener(this.o);
            }
        }
    }

    public boolean d() {
        return false;
    }

    public void dismiss() {
        int size = this.m.size();
        if (size > 0) {
            C0005d[] dVarArr = (C0005d[]) this.m.toArray(new C0005d[size]);
            for (int i2 = size - 1; i2 >= 0; i2--) {
                C0005d dVar = dVarArr[i2];
                if (dVar.a.a()) {
                    dVar.a.dismiss();
                }
            }
        }
    }

    public Parcelable e() {
        return null;
    }

    /* access modifiers changed from: protected */
    public boolean f() {
        return false;
    }

    public ListView g() {
        if (this.m.isEmpty()) {
            return null;
        }
        List<C0005d> list = this.m;
        return list.get(list.size() - 1).a();
    }

    public void onDismiss() {
        C0005d dVar;
        int size = this.m.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                dVar = null;
                break;
            }
            dVar = this.m.get(i2);
            if (!dVar.a.a()) {
                break;
            }
            i2++;
        }
        if (dVar != null) {
            dVar.b.a(false);
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
        this.D = onDismissListener;
    }

    public void b(int i2) {
        this.v = true;
        this.x = i2;
    }

    private MenuItem a(g gVar, g gVar2) {
        int size = gVar.size();
        for (int i2 = 0; i2 < size; i2++) {
            MenuItem item = gVar.getItem(i2);
            if (item.hasSubMenu() && gVar2 == item.getSubMenu()) {
                return item;
            }
        }
        return null;
    }

    private View a(C0005d dVar, g gVar) {
        int i2;
        f fVar;
        int firstVisiblePosition;
        MenuItem a2 = a(dVar.b, gVar);
        if (a2 == null) {
            return null;
        }
        ListView a3 = dVar.a();
        ListAdapter adapter = a3.getAdapter();
        int i3 = 0;
        if (adapter instanceof HeaderViewListAdapter) {
            HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
            i2 = headerViewListAdapter.getHeadersCount();
            fVar = (f) headerViewListAdapter.getWrappedAdapter();
        } else {
            fVar = (f) adapter;
            i2 = 0;
        }
        int count = fVar.getCount();
        while (true) {
            if (i3 >= count) {
                i3 = -1;
                break;
            } else if (a2 == fVar.getItem(i3)) {
                break;
            } else {
                i3++;
            }
        }
        if (i3 != -1 && (firstVisiblePosition = (i3 + i2) - a3.getFirstVisiblePosition()) >= 0 && firstVisiblePosition < a3.getChildCount()) {
            return a3.getChildAt(firstVisiblePosition);
        }
        return null;
    }

    private void d(g gVar) {
        View view;
        C0005d dVar;
        int i2;
        int i3;
        int i4;
        LayoutInflater from = LayoutInflater.from(this.f131f);
        f fVar = new f(gVar, from, this.f135j, F);
        if (!a() && this.z) {
            fVar.a(true);
        } else if (a()) {
            fVar.a(k.b(gVar));
        }
        int a2 = k.a(fVar, (ViewGroup) null, this.f131f, this.f132g);
        MenuPopupWindow i5 = i();
        i5.a((ListAdapter) fVar);
        i5.e(a2);
        i5.f(this.r);
        if (this.m.size() > 0) {
            List<C0005d> list = this.m;
            dVar = list.get(list.size() - 1);
            view = a(dVar, gVar);
        } else {
            dVar = null;
            view = null;
        }
        if (view != null) {
            i5.c(false);
            i5.a((Object) null);
            int d = d(a2);
            boolean z2 = d == 1;
            this.u = d;
            if (Build.VERSION.SDK_INT >= 26) {
                i5.a(view);
                i3 = 0;
                i2 = 0;
            } else {
                int[] iArr = new int[2];
                this.s.getLocationOnScreen(iArr);
                int[] iArr2 = new int[2];
                view.getLocationOnScreen(iArr2);
                if ((this.r & 7) == 5) {
                    iArr[0] = iArr[0] + this.s.getWidth();
                    iArr2[0] = iArr2[0] + view.getWidth();
                }
                i2 = iArr2[0] - iArr[0];
                i3 = iArr2[1] - iArr[1];
            }
            if ((this.r & 5) != 5) {
                if (z2) {
                    a2 = view.getWidth();
                }
                i4 = i2 - a2;
                i5.a(i4);
                i5.b(true);
                i5.b(i3);
            } else if (!z2) {
                a2 = view.getWidth();
                i4 = i2 - a2;
                i5.a(i4);
                i5.b(true);
                i5.b(i3);
            }
            i4 = i2 + a2;
            i5.a(i4);
            i5.b(true);
            i5.b(i3);
        } else {
            if (this.v) {
                i5.a(this.x);
            }
            if (this.w) {
                i5.b(this.y);
            }
            i5.a(h());
        }
        this.m.add(new C0005d(i5, gVar, this.u));
        i5.c();
        ListView g2 = i5.g();
        g2.setOnKeyListener(this);
        if (dVar == null && this.A && gVar.h() != null) {
            FrameLayout frameLayout = (FrameLayout) from.inflate(R$layout.abc_popup_menu_header_item_layout, g2, false);
            frameLayout.setEnabled(false);
            ((TextView) frameLayout.findViewById(16908310)).setText(gVar.h());
            g2.addHeaderView(frameLayout, (Object) null, false);
            i5.c();
        }
    }

    private int c(g gVar) {
        int size = this.m.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (gVar == this.m.get(i2).b) {
                return i2;
            }
        }
        return -1;
    }

    public void c(int i2) {
        this.w = true;
        this.y = i2;
    }

    public void c(boolean z2) {
        this.A = z2;
    }

    public boolean a() {
        return this.m.size() > 0 && this.m.get(0).a.a();
    }

    public void a(boolean z2) {
        for (C0005d a2 : this.m) {
            k.a(a2.a().getAdapter()).notifyDataSetChanged();
        }
    }

    public void a(m.a aVar) {
        this.B = aVar;
    }

    public boolean a(r rVar) {
        for (C0005d next : this.m) {
            if (rVar == next.b) {
                next.a().requestFocus();
                return true;
            }
        }
        if (!rVar.hasVisibleItems()) {
            return false;
        }
        a((g) rVar);
        m.a aVar = this.B;
        if (aVar != null) {
            aVar.a(rVar);
        }
        return true;
    }

    public void a(g gVar, boolean z2) {
        int c2 = c(gVar);
        if (c2 >= 0) {
            int i2 = c2 + 1;
            if (i2 < this.m.size()) {
                this.m.get(i2).b.a(false);
            }
            C0005d remove = this.m.remove(c2);
            remove.b.b((m) this);
            if (this.E) {
                remove.a.b((Object) null);
                remove.a.d(0);
            }
            remove.a.dismiss();
            int size = this.m.size();
            if (size > 0) {
                this.u = this.m.get(size - 1).c;
            } else {
                this.u = j();
            }
            if (size == 0) {
                dismiss();
                m.a aVar = this.B;
                if (aVar != null) {
                    aVar.a(gVar, true);
                }
                ViewTreeObserver viewTreeObserver = this.C;
                if (viewTreeObserver != null) {
                    if (viewTreeObserver.isAlive()) {
                        this.C.removeGlobalOnLayoutListener(this.n);
                    }
                    this.C = null;
                }
                this.t.removeOnAttachStateChangeListener(this.o);
                this.D.onDismiss();
            } else if (z2) {
                this.m.get(0).b.a(false);
            }
        }
    }

    public void a(int i2) {
        if (this.q != i2) {
            this.q = i2;
            this.r = androidx.core.h.d.a(i2, androidx.core.h.v.o(this.s));
        }
    }

    public void a(View view) {
        if (this.s != view) {
            this.s = view;
            this.r = androidx.core.h.d.a(this.q, androidx.core.h.v.o(view));
        }
    }
}
