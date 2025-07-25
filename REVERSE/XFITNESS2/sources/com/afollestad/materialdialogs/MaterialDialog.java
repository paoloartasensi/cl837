package com.afollestad.materialdialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import androidx.recyclerview.widget.RecyclerView;
import com.afollestad.materialdialogs.internal.button.DialogActionButton;
import com.afollestad.materialdialogs.internal.main.DialogLayout;
import com.afollestad.materialdialogs.j.b;
import com.afollestad.materialdialogs.j.d;
import com.afollestad.materialdialogs.j.e;
import com.afollestad.materialdialogs.j.f;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.b.l;
import kotlin.jvm.internal.i;

/* compiled from: MaterialDialog.kt */
public final class MaterialDialog extends Dialog {
    private static a s = c.a;
    private final Map<String, Object> e;

    /* renamed from: f  reason: collision with root package name */
    private boolean f978f;

    /* renamed from: g  reason: collision with root package name */
    private Typeface f979g;

    /* renamed from: h  reason: collision with root package name */
    private Typeface f980h;

    /* renamed from: i  reason: collision with root package name */
    private Typeface f981i;

    /* renamed from: j  reason: collision with root package name */
    private Float f982j;
    private Integer k;
    private final DialogLayout l;
    private final List<l<MaterialDialog, kotlin.l>> m;
    private final List<l<MaterialDialog, kotlin.l>> n;
    private final List<l<MaterialDialog, kotlin.l>> o;
    private final List<l<MaterialDialog, kotlin.l>> p;
    private final Context q;
    private final a r;

    /* compiled from: MaterialDialog.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    static {
        new a((f) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ MaterialDialog(Context context, a aVar, int i2, f fVar) {
        this(context, (i2 & 2) != 0 ? s : aVar);
    }

    private final void g() {
        int a2 = com.afollestad.materialdialogs.j.a.a(this, (Integer) null, Integer.valueOf(R$attr.md_background_color), new MaterialDialog$invalidateBackgroundColorAndRadius$backgroundColor$1(this), 1, (Object) null);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(0));
        }
        a aVar = this.r;
        DialogLayout dialogLayout = this.l;
        Float f2 = this.f982j;
        aVar.a(dialogLayout, a2, f2 != null ? f2.floatValue() : e.a.a(this.q, R$attr.md_corner_radius, (kotlin.jvm.b.a<Float>) new MaterialDialog$invalidateBackgroundColorAndRadius$1(this)));
    }

    private final void h() {
        a aVar = this.r;
        Context context = this.q;
        Integer num = this.k;
        Window window = getWindow();
        if (window != null) {
            i.a((Object) window, "window!!");
            aVar.a(context, window, this.l, num);
            return;
        }
        i.a();
        throw null;
    }

    public final boolean a() {
        return this.f978f;
    }

    public final Typeface b() {
        return this.f980h;
    }

    public final Map<String, Object> c() {
        return this.e;
    }

    public final List<l<MaterialDialog, kotlin.l>> d() {
        return this.m;
    }

    public void dismiss() {
        if (!this.r.onDismiss()) {
            b.a(this);
            super.dismiss();
        }
    }

    public final DialogLayout e() {
        return this.l;
    }

    public final Context f() {
        return this.q;
    }

    public void setCancelable(boolean z) {
        super.setCancelable(z);
    }

    public void setCanceledOnTouchOutside(boolean z) {
        super.setCanceledOnTouchOutside(z);
    }

    public void show() {
        h();
        b.b(this);
        this.r.a(this);
        super.show();
        this.r.b(this);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MaterialDialog(Context context, a aVar) {
        super(context, d.a(context, aVar));
        i.b(context, "windowContext");
        i.b(aVar, "dialogBehavior");
        this.q = context;
        this.r = aVar;
        this.e = new LinkedHashMap();
        this.f978f = true;
        this.m = new ArrayList();
        new ArrayList();
        new ArrayList();
        new ArrayList();
        this.n = new ArrayList();
        this.o = new ArrayList();
        this.p = new ArrayList();
        LayoutInflater from = LayoutInflater.from(this.q);
        a aVar2 = this.r;
        Context context2 = this.q;
        Window window = getWindow();
        if (window != null) {
            i.a((Object) window, "window!!");
            i.a((Object) from, "layoutInflater");
            ViewGroup a2 = aVar2.a(context2, window, from, this);
            setContentView(a2);
            DialogLayout a3 = this.r.a(a2);
            a3.a(this);
            this.l = a3;
            this.f979g = d.a(this, (Integer) null, Integer.valueOf(R$attr.md_font_title), 1, (Object) null);
            this.f980h = d.a(this, (Integer) null, Integer.valueOf(R$attr.md_font_body), 1, (Object) null);
            this.f981i = d.a(this, (Integer) null, Integer.valueOf(R$attr.md_font_button), 1, (Object) null);
            g();
            return;
        }
        i.a();
        throw null;
    }

    public static /* synthetic */ MaterialDialog a(MaterialDialog materialDialog, Integer num, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            num = null;
        }
        if ((i2 & 2) != 0) {
            str = null;
        }
        materialDialog.a(num, str);
        return materialDialog;
    }

    public static /* synthetic */ MaterialDialog b(MaterialDialog materialDialog, Integer num, CharSequence charSequence, l lVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            num = null;
        }
        if ((i2 & 2) != 0) {
            charSequence = null;
        }
        if ((i2 & 4) != 0) {
            lVar = null;
        }
        materialDialog.b(num, charSequence, lVar);
        return materialDialog;
    }

    public static /* synthetic */ MaterialDialog c(MaterialDialog materialDialog, Integer num, CharSequence charSequence, l lVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            num = null;
        }
        if ((i2 & 2) != 0) {
            charSequence = null;
        }
        if ((i2 & 4) != 0) {
            lVar = null;
        }
        materialDialog.c(num, charSequence, lVar);
        return materialDialog;
    }

    public final MaterialDialog a(Integer num, String str) {
        e.a.a("title", (Object) str, num);
        b.a(this, this.l.getTitleLayout().getTitleView$core(), num, str, 0, this.f979g, Integer.valueOf(R$attr.md_color_title), 8, (Object) null);
        return this;
    }

    public final MaterialDialog b(Integer num, CharSequence charSequence, l<? super MaterialDialog, kotlin.l> lVar) {
        if (lVar != null) {
            this.o.add(lVar);
        }
        DialogActionButton a2 = com.afollestad.materialdialogs.e.a.a(this, WhichButton.NEGATIVE);
        if (!(num == null && charSequence == null && f.c(a2))) {
            b.a(this, a2, num, charSequence, 17039360, this.f981i, (Integer) null, 32, (Object) null);
        }
        return this;
    }

    public final MaterialDialog c(Integer num, CharSequence charSequence, l<? super MaterialDialog, kotlin.l> lVar) {
        if (lVar != null) {
            this.n.add(lVar);
        }
        DialogActionButton a2 = com.afollestad.materialdialogs.e.a.a(this, WhichButton.POSITIVE);
        if (num == null && charSequence == null && f.c(a2)) {
            return this;
        }
        b.a(this, a2, num, charSequence, 17039370, this.f981i, (Integer) null, 32, (Object) null);
        return this;
    }

    public static /* synthetic */ MaterialDialog a(MaterialDialog materialDialog, Integer num, CharSequence charSequence, l lVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            num = null;
        }
        if ((i2 & 2) != 0) {
            charSequence = null;
        }
        if ((i2 & 4) != 0) {
            lVar = null;
        }
        materialDialog.a(num, charSequence, lVar);
        return materialDialog;
    }

    public final MaterialDialog a(Integer num, CharSequence charSequence, l<? super com.afollestad.materialdialogs.i.a, kotlin.l> lVar) {
        e.a.a("message", (Object) charSequence, num);
        this.l.getContentLayout().a(this, num, charSequence, this.f980h, lVar);
        return this;
    }

    public static /* synthetic */ MaterialDialog a(MaterialDialog materialDialog, Float f2, Integer num, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            f2 = null;
        }
        if ((i2 & 2) != 0) {
            num = null;
        }
        materialDialog.a(f2, num);
        return materialDialog;
    }

    public final MaterialDialog a(Float f2, Integer num) {
        Float f3;
        e.a.a("cornerRadius", (Object) f2, num);
        if (num != null) {
            f3 = Float.valueOf(this.q.getResources().getDimension(num.intValue()));
        } else {
            Resources resources = this.q.getResources();
            i.a((Object) resources, "windowContext.resources");
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            if (f2 != null) {
                f3 = Float.valueOf(TypedValue.applyDimension(1, f2.floatValue(), displayMetrics));
            } else {
                i.a();
                throw null;
            }
        }
        this.f982j = f3;
        g();
        return this;
    }

    public final void a(WhichButton whichButton) {
        i.b(whichButton, "which");
        int i2 = b.a[whichButton.ordinal()];
        if (i2 == 1) {
            com.afollestad.materialdialogs.f.a.a(this.n, this);
            RecyclerView.g<?> b = com.afollestad.materialdialogs.h.a.b(this);
            if (!(b instanceof com.afollestad.materialdialogs.internal.list.a)) {
                b = null;
            }
            com.afollestad.materialdialogs.internal.list.a aVar = (com.afollestad.materialdialogs.internal.list.a) b;
            if (aVar != null) {
                aVar.a();
            }
        } else if (i2 == 2) {
            com.afollestad.materialdialogs.f.a.a(this.o, this);
        } else if (i2 == 3) {
            com.afollestad.materialdialogs.f.a.a(this.p, this);
        }
        if (this.f978f) {
            dismiss();
        }
    }
}
