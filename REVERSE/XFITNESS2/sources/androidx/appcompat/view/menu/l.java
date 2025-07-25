package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.PopupWindow;
import androidx.appcompat.view.menu.m;
import androidx.core.h.d;
import androidx.core.h.v;

/* compiled from: MenuPopupHelper */
public class l {
    private final Context a;
    private final g b;
    private final boolean c;
    private final int d;
    private final int e;

    /* renamed from: f  reason: collision with root package name */
    private View f164f;

    /* renamed from: g  reason: collision with root package name */
    private int f165g;

    /* renamed from: h  reason: collision with root package name */
    private boolean f166h;

    /* renamed from: i  reason: collision with root package name */
    private m.a f167i;

    /* renamed from: j  reason: collision with root package name */
    private k f168j;
    private PopupWindow.OnDismissListener k;
    private final PopupWindow.OnDismissListener l;

    /* compiled from: MenuPopupHelper */
    class a implements PopupWindow.OnDismissListener {
        a() {
        }

        public void onDismiss() {
            l.this.d();
        }
    }

    public l(Context context, g gVar, View view, boolean z, int i2) {
        this(context, gVar, view, z, i2, 0);
    }

    /* JADX WARNING: type inference failed for: r0v7, types: [androidx.appcompat.view.menu.m, androidx.appcompat.view.menu.k] */
    /* JADX WARNING: type inference failed for: r7v1, types: [androidx.appcompat.view.menu.q] */
    /* JADX WARNING: type inference failed for: r1v13, types: [androidx.appcompat.view.menu.d] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private androidx.appcompat.view.menu.k g() {
        /*
            r14 = this;
            android.content.Context r0 = r14.a
            java.lang.String r1 = "window"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.view.WindowManager r0 = (android.view.WindowManager) r0
            android.view.Display r0 = r0.getDefaultDisplay()
            android.graphics.Point r1 = new android.graphics.Point
            r1.<init>()
            int r2 = android.os.Build.VERSION.SDK_INT
            r3 = 17
            if (r2 < r3) goto L_0x001d
            r0.getRealSize(r1)
            goto L_0x0020
        L_0x001d:
            r0.getSize(r1)
        L_0x0020:
            int r0 = r1.x
            int r1 = r1.y
            int r0 = java.lang.Math.min(r0, r1)
            android.content.Context r1 = r14.a
            android.content.res.Resources r1 = r1.getResources()
            int r2 = androidx.appcompat.R$dimen.abc_cascading_menus_min_smallest_width
            int r1 = r1.getDimensionPixelSize(r2)
            if (r0 < r1) goto L_0x0038
            r0 = 1
            goto L_0x0039
        L_0x0038:
            r0 = 0
        L_0x0039:
            if (r0 == 0) goto L_0x004c
            androidx.appcompat.view.menu.d r0 = new androidx.appcompat.view.menu.d
            android.content.Context r2 = r14.a
            android.view.View r3 = r14.f164f
            int r4 = r14.d
            int r5 = r14.e
            boolean r6 = r14.c
            r1 = r0
            r1.<init>(r2, r3, r4, r5, r6)
            goto L_0x005e
        L_0x004c:
            androidx.appcompat.view.menu.q r0 = new androidx.appcompat.view.menu.q
            android.content.Context r8 = r14.a
            androidx.appcompat.view.menu.g r9 = r14.b
            android.view.View r10 = r14.f164f
            int r11 = r14.d
            int r12 = r14.e
            boolean r13 = r14.c
            r7 = r0
            r7.<init>(r8, r9, r10, r11, r12, r13)
        L_0x005e:
            androidx.appcompat.view.menu.g r1 = r14.b
            r0.a((androidx.appcompat.view.menu.g) r1)
            android.widget.PopupWindow$OnDismissListener r1 = r14.l
            r0.setOnDismissListener(r1)
            android.view.View r1 = r14.f164f
            r0.a((android.view.View) r1)
            androidx.appcompat.view.menu.m$a r1 = r14.f167i
            r0.a((androidx.appcompat.view.menu.m.a) r1)
            boolean r1 = r14.f166h
            r0.b((boolean) r1)
            int r1 = r14.f165g
            r0.a((int) r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.view.menu.l.g():androidx.appcompat.view.menu.k");
    }

    public void a(View view) {
        this.f164f = view;
    }

    public k b() {
        if (this.f168j == null) {
            this.f168j = g();
        }
        return this.f168j;
    }

    public boolean c() {
        k kVar = this.f168j;
        return kVar != null && kVar.a();
    }

    /* access modifiers changed from: protected */
    public void d() {
        this.f168j = null;
        PopupWindow.OnDismissListener onDismissListener = this.k;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public void e() {
        if (!f()) {
            throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    public boolean f() {
        if (c()) {
            return true;
        }
        if (this.f164f == null) {
            return false;
        }
        a(0, 0, false, false);
        return true;
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.k = onDismissListener;
    }

    public l(Context context, g gVar, View view, boolean z, int i2, int i3) {
        this.f165g = 8388611;
        this.l = new a();
        this.a = context;
        this.b = gVar;
        this.f164f = view;
        this.c = z;
        this.d = i2;
        this.e = i3;
    }

    public void a(boolean z) {
        this.f166h = z;
        k kVar = this.f168j;
        if (kVar != null) {
            kVar.b(z);
        }
    }

    public void a(int i2) {
        this.f165g = i2;
    }

    public boolean a(int i2, int i3) {
        if (c()) {
            return true;
        }
        if (this.f164f == null) {
            return false;
        }
        a(i2, i3, true, true);
        return true;
    }

    private void a(int i2, int i3, boolean z, boolean z2) {
        k b2 = b();
        b2.c(z2);
        if (z) {
            if ((d.a(this.f165g, v.o(this.f164f)) & 7) == 5) {
                i2 -= this.f164f.getWidth();
            }
            b2.b(i2);
            b2.c(i3);
            int i4 = (int) ((this.a.getResources().getDisplayMetrics().density * 48.0f) / 2.0f);
            b2.a(new Rect(i2 - i4, i3 - i4, i2 + i4, i3 + i4));
        }
        b2.c();
    }

    public void a() {
        if (c()) {
            this.f168j.dismiss();
        }
    }

    public void a(m.a aVar) {
        this.f167i = aVar;
        k kVar = this.f168j;
        if (kVar != null) {
            kVar.a(aVar);
        }
    }
}
