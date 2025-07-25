package androidx.preference;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.core.content.c.g;
import androidx.preference.j;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Preference implements Comparable<Preference> {
    private boolean A;
    private boolean B;
    private boolean C;
    private boolean D;
    private boolean E;
    private boolean F;
    private boolean G;
    private boolean H;
    private boolean I;
    private boolean J;
    private int K;
    private int L;
    private b M;
    private List<Preference> N;
    private PreferenceGroup O;
    private boolean P;
    private e Q;
    private f R;
    private final View.OnClickListener S;
    private Context e;

    /* renamed from: f  reason: collision with root package name */
    private j f752f;

    /* renamed from: g  reason: collision with root package name */
    private e f753g;

    /* renamed from: h  reason: collision with root package name */
    private long f754h;

    /* renamed from: i  reason: collision with root package name */
    private boolean f755i;

    /* renamed from: j  reason: collision with root package name */
    private c f756j;
    private d k;
    private int l;
    private int m;
    private CharSequence n;
    private CharSequence o;
    private int p;
    private Drawable q;
    private String r;
    private Intent s;
    private String t;
    private Bundle u;
    private boolean v;
    private boolean w;
    private boolean x;
    private String y;
    private Object z;

    public static class BaseSavedState extends AbsSavedState {
        public static final Parcelable.Creator<BaseSavedState> CREATOR = new a();

        static class a implements Parcelable.Creator<BaseSavedState> {
            a() {
            }

            public BaseSavedState createFromParcel(Parcel parcel) {
                return new BaseSavedState(parcel);
            }

            public BaseSavedState[] newArray(int i2) {
                return new BaseSavedState[i2];
            }
        }

        public BaseSavedState(Parcel parcel) {
            super(parcel);
        }

        public BaseSavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    class a implements View.OnClickListener {
        a() {
        }

        public void onClick(View view) {
            Preference.this.a(view);
        }
    }

    interface b {
        void a(Preference preference);

        void b(Preference preference);
    }

    public interface c {
        boolean a(Preference preference, Object obj);
    }

    public interface d {
        boolean a(Preference preference);
    }

    private static class e implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private final Preference e;

        e(Preference preference) {
            this.e = preference;
        }

        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            CharSequence o = this.e.o();
            if (this.e.t() && !TextUtils.isEmpty(o)) {
                contextMenu.setHeaderTitle(o);
                contextMenu.add(0, 0, 0, R$string.copy).setOnMenuItemClickListener(this);
            }
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            CharSequence o = this.e.o();
            ((ClipboardManager) this.e.b().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Preference", o));
            Toast.makeText(this.e.b(), this.e.b().getString(R$string.preference_copied, new Object[]{o}), 0).show();
            return true;
        }
    }

    public interface f<T extends Preference> {
        CharSequence a(T t);
    }

    public Preference(Context context, AttributeSet attributeSet, int i2, int i3) {
        this.l = Integer.MAX_VALUE;
        this.m = 0;
        this.v = true;
        this.w = true;
        this.x = true;
        this.A = true;
        this.B = true;
        this.C = true;
        this.D = true;
        this.E = true;
        this.G = true;
        this.J = true;
        this.K = R$layout.preference;
        this.S = new a();
        this.e = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Preference, i2, i3);
        this.p = g.b(obtainStyledAttributes, R$styleable.Preference_icon, R$styleable.Preference_android_icon, 0);
        this.r = g.b(obtainStyledAttributes, R$styleable.Preference_key, R$styleable.Preference_android_key);
        this.n = g.c(obtainStyledAttributes, R$styleable.Preference_title, R$styleable.Preference_android_title);
        this.o = g.c(obtainStyledAttributes, R$styleable.Preference_summary, R$styleable.Preference_android_summary);
        this.l = g.a(obtainStyledAttributes, R$styleable.Preference_order, R$styleable.Preference_android_order, Integer.MAX_VALUE);
        this.t = g.b(obtainStyledAttributes, R$styleable.Preference_fragment, R$styleable.Preference_android_fragment);
        this.K = g.b(obtainStyledAttributes, R$styleable.Preference_layout, R$styleable.Preference_android_layout, R$layout.preference);
        this.L = g.b(obtainStyledAttributes, R$styleable.Preference_widgetLayout, R$styleable.Preference_android_widgetLayout, 0);
        this.v = g.a(obtainStyledAttributes, R$styleable.Preference_enabled, R$styleable.Preference_android_enabled, true);
        this.w = g.a(obtainStyledAttributes, R$styleable.Preference_selectable, R$styleable.Preference_android_selectable, true);
        this.x = g.a(obtainStyledAttributes, R$styleable.Preference_persistent, R$styleable.Preference_android_persistent, true);
        this.y = g.b(obtainStyledAttributes, R$styleable.Preference_dependency, R$styleable.Preference_android_dependency);
        int i4 = R$styleable.Preference_allowDividerAbove;
        this.D = g.a(obtainStyledAttributes, i4, i4, this.w);
        int i5 = R$styleable.Preference_allowDividerBelow;
        this.E = g.a(obtainStyledAttributes, i5, i5, this.w);
        if (obtainStyledAttributes.hasValue(R$styleable.Preference_defaultValue)) {
            this.z = a(obtainStyledAttributes, R$styleable.Preference_defaultValue);
        } else if (obtainStyledAttributes.hasValue(R$styleable.Preference_android_defaultValue)) {
            this.z = a(obtainStyledAttributes, R$styleable.Preference_android_defaultValue);
        }
        this.J = g.a(obtainStyledAttributes, R$styleable.Preference_shouldDisableView, R$styleable.Preference_android_shouldDisableView, true);
        boolean hasValue = obtainStyledAttributes.hasValue(R$styleable.Preference_singleLineTitle);
        this.F = hasValue;
        if (hasValue) {
            this.G = g.a(obtainStyledAttributes, R$styleable.Preference_singleLineTitle, R$styleable.Preference_android_singleLineTitle, true);
        }
        this.H = g.a(obtainStyledAttributes, R$styleable.Preference_iconSpaceReserved, R$styleable.Preference_android_iconSpaceReserved, false);
        int i6 = R$styleable.Preference_isPreferenceVisible;
        this.C = g.a(obtainStyledAttributes, i6, i6, true);
        int i7 = R$styleable.Preference_enableCopying;
        this.I = g.a(obtainStyledAttributes, i7, i7, false);
        obtainStyledAttributes.recycle();
    }

    private void H() {
        if (l() != null) {
            a(true, this.z);
        } else if (!G() || !n().contains(this.r)) {
            Object obj = this.z;
            if (obj != null) {
                a(false, obj);
            }
        } else {
            a(true, (Object) null);
        }
    }

    private void I() {
        if (!TextUtils.isEmpty(this.y)) {
            Preference a2 = a(this.y);
            if (a2 != null) {
                a2.b(this);
                return;
            }
            throw new IllegalStateException("Dependency \"" + this.y + "\" not found for preference \"" + this.r + "\" (title: \"" + this.n + "\"");
        }
    }

    private void J() {
        Preference a2;
        String str = this.y;
        if (str != null && (a2 = a(str)) != null) {
            a2.c(this);
        }
    }

    public void A() {
        I();
    }

    /* access modifiers changed from: protected */
    public void B() {
    }

    public void C() {
        J();
    }

    /* access modifiers changed from: protected */
    public Parcelable D() {
        this.P = true;
        return AbsSavedState.EMPTY_STATE;
    }

    public void E() {
        j.c d2;
        if (u() && w()) {
            B();
            d dVar = this.k;
            if (dVar == null || !dVar.a(this)) {
                j m2 = m();
                if ((m2 == null || (d2 = m2.d()) == null || !d2.b(this)) && this.s != null) {
                    b().startActivity(this.s);
                }
            }
        }
    }

    public boolean F() {
        return !u();
    }

    /* access modifiers changed from: protected */
    public boolean G() {
        return this.f752f != null && v() && s();
    }

    /* access modifiers changed from: protected */
    public Object a(TypedArray typedArray, int i2) {
        return null;
    }

    /* access modifiers changed from: package-private */
    public final void a() {
    }

    public void a(Intent intent) {
        this.s = intent;
    }

    @Deprecated
    public void a(androidx.core.h.e0.d dVar) {
    }

    public void b(CharSequence charSequence) {
        if ((charSequence == null && this.n != null) || (charSequence != null && !charSequence.equals(this.n))) {
            this.n = charSequence;
            y();
        }
    }

    /* access modifiers changed from: protected */
    public void b(Object obj) {
    }

    public Bundle c() {
        if (this.u == null) {
            this.u = new Bundle();
        }
        return this.u;
    }

    public void d(int i2) {
        this.K = i2;
    }

    public String e() {
        return this.t;
    }

    public void f(int i2) {
        b((CharSequence) this.e.getString(i2));
    }

    public Intent g() {
        return this.s;
    }

    public String h() {
        return this.r;
    }

    public final int i() {
        return this.K;
    }

    public int j() {
        return this.l;
    }

    public PreferenceGroup k() {
        return this.O;
    }

    public e l() {
        e eVar = this.f753g;
        if (eVar != null) {
            return eVar;
        }
        j jVar = this.f752f;
        if (jVar != null) {
            return jVar.f();
        }
        return null;
    }

    public j m() {
        return this.f752f;
    }

    public SharedPreferences n() {
        if (this.f752f == null || l() != null) {
            return null;
        }
        return this.f752f.h();
    }

    public CharSequence o() {
        if (p() != null) {
            return p().a(this);
        }
        return this.o;
    }

    public final f p() {
        return this.R;
    }

    public CharSequence q() {
        return this.n;
    }

    public final int r() {
        return this.L;
    }

    public boolean s() {
        return !TextUtils.isEmpty(this.r);
    }

    /* access modifiers changed from: package-private */
    public final void setOnPreferenceChangeInternalListener(b bVar) {
        this.M = bVar;
    }

    public void setOnPreferenceChangeListener(c cVar) {
        this.f756j = cVar;
    }

    public void setOnPreferenceClickListener(d dVar) {
        this.k = dVar;
    }

    public boolean t() {
        return this.I;
    }

    public String toString() {
        return d().toString();
    }

    public boolean u() {
        return this.v && this.A && this.B;
    }

    public boolean v() {
        return this.x;
    }

    public boolean w() {
        return this.w;
    }

    public final boolean x() {
        return this.C;
    }

    /* access modifiers changed from: protected */
    public void y() {
        b bVar = this.M;
        if (bVar != null) {
            bVar.b(this);
        }
    }

    /* access modifiers changed from: protected */
    public void z() {
        b bVar = this.M;
        if (bVar != null) {
            bVar.a(this);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0081  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00b9  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0109  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x010c  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0043  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(androidx.preference.l r9) {
        /*
            r8 = this;
            android.view.View r0 = r9.itemView
            android.view.View$OnClickListener r1 = r8.S
            r0.setOnClickListener(r1)
            int r1 = r8.m
            r0.setId(r1)
            r1 = 16908304(0x1020010, float:2.3877274E-38)
            android.view.View r1 = r9.a((int) r1)
            android.widget.TextView r1 = (android.widget.TextView) r1
            r2 = 0
            r3 = 0
            r4 = 8
            if (r1 == 0) goto L_0x0037
            java.lang.CharSequence r5 = r8.o()
            boolean r6 = android.text.TextUtils.isEmpty(r5)
            if (r6 != 0) goto L_0x0034
            r1.setText(r5)
            r1.setVisibility(r3)
            int r1 = r1.getCurrentTextColor()
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            goto L_0x0038
        L_0x0034:
            r1.setVisibility(r4)
        L_0x0037:
            r1 = r2
        L_0x0038:
            r5 = 16908310(0x1020016, float:2.387729E-38)
            android.view.View r5 = r9.a((int) r5)
            android.widget.TextView r5 = (android.widget.TextView) r5
            if (r5 == 0) goto L_0x0075
            java.lang.CharSequence r6 = r8.q()
            boolean r7 = android.text.TextUtils.isEmpty(r6)
            if (r7 != 0) goto L_0x0072
            r5.setText(r6)
            r5.setVisibility(r3)
            boolean r6 = r8.F
            if (r6 == 0) goto L_0x005c
            boolean r6 = r8.G
            r5.setSingleLine(r6)
        L_0x005c:
            boolean r6 = r8.w()
            if (r6 != 0) goto L_0x0075
            boolean r6 = r8.u()
            if (r6 == 0) goto L_0x0075
            if (r1 == 0) goto L_0x0075
            int r1 = r1.intValue()
            r5.setTextColor(r1)
            goto L_0x0075
        L_0x0072:
            r5.setVisibility(r4)
        L_0x0075:
            r1 = 16908294(0x1020006, float:2.3877246E-38)
            android.view.View r1 = r9.a((int) r1)
            android.widget.ImageView r1 = (android.widget.ImageView) r1
            r5 = 4
            if (r1 == 0) goto L_0x00b1
            int r6 = r8.p
            if (r6 != 0) goto L_0x0089
            android.graphics.drawable.Drawable r6 = r8.q
            if (r6 == 0) goto L_0x009e
        L_0x0089:
            android.graphics.drawable.Drawable r6 = r8.q
            if (r6 != 0) goto L_0x0097
            android.content.Context r6 = r8.e
            int r7 = r8.p
            android.graphics.drawable.Drawable r6 = androidx.appcompat.a.a.a.c(r6, r7)
            r8.q = r6
        L_0x0097:
            android.graphics.drawable.Drawable r6 = r8.q
            if (r6 == 0) goto L_0x009e
            r1.setImageDrawable(r6)
        L_0x009e:
            android.graphics.drawable.Drawable r6 = r8.q
            if (r6 == 0) goto L_0x00a6
            r1.setVisibility(r3)
            goto L_0x00b1
        L_0x00a6:
            boolean r6 = r8.H
            if (r6 == 0) goto L_0x00ac
            r6 = 4
            goto L_0x00ae
        L_0x00ac:
            r6 = 8
        L_0x00ae:
            r1.setVisibility(r6)
        L_0x00b1:
            int r1 = androidx.preference.R$id.icon_frame
            android.view.View r1 = r9.a((int) r1)
            if (r1 != 0) goto L_0x00c0
            r1 = 16908350(0x102003e, float:2.3877403E-38)
            android.view.View r1 = r9.a((int) r1)
        L_0x00c0:
            if (r1 == 0) goto L_0x00d2
            android.graphics.drawable.Drawable r6 = r8.q
            if (r6 == 0) goto L_0x00ca
            r1.setVisibility(r3)
            goto L_0x00d2
        L_0x00ca:
            boolean r3 = r8.H
            if (r3 == 0) goto L_0x00cf
            r4 = 4
        L_0x00cf:
            r1.setVisibility(r4)
        L_0x00d2:
            boolean r1 = r8.J
            if (r1 == 0) goto L_0x00de
            boolean r1 = r8.u()
            r8.a((android.view.View) r0, (boolean) r1)
            goto L_0x00e2
        L_0x00de:
            r1 = 1
            r8.a((android.view.View) r0, (boolean) r1)
        L_0x00e2:
            boolean r1 = r8.w()
            r0.setFocusable(r1)
            r0.setClickable(r1)
            boolean r3 = r8.D
            r9.a((boolean) r3)
            boolean r3 = r8.E
            r9.b(r3)
            boolean r9 = r8.t()
            if (r9 == 0) goto L_0x0107
            androidx.preference.Preference$e r3 = r8.Q
            if (r3 != 0) goto L_0x0107
            androidx.preference.Preference$e r3 = new androidx.preference.Preference$e
            r3.<init>(r8)
            r8.Q = r3
        L_0x0107:
            if (r9 == 0) goto L_0x010c
            androidx.preference.Preference$e r3 = r8.Q
            goto L_0x010d
        L_0x010c:
            r3 = r2
        L_0x010d:
            r0.setOnCreateContextMenuListener(r3)
            r0.setLongClickable(r9)
            if (r9 == 0) goto L_0x011a
            if (r1 != 0) goto L_0x011a
            androidx.core.h.v.a((android.view.View) r0, (android.graphics.drawable.Drawable) r2)
        L_0x011a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.preference.Preference.a(androidx.preference.l):void");
    }

    /* access modifiers changed from: package-private */
    public StringBuilder d() {
        StringBuilder sb = new StringBuilder();
        CharSequence q2 = q();
        if (!TextUtils.isEmpty(q2)) {
            sb.append(q2);
            sb.append(' ');
        }
        CharSequence o2 = o();
        if (!TextUtils.isEmpty(o2)) {
            sb.append(o2);
            sb.append(' ');
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb;
    }

    public void e(int i2) {
        if (i2 != this.l) {
            this.l = i2;
            z();
        }
    }

    /* access modifiers changed from: package-private */
    public long f() {
        return this.f754h;
    }

    public Context b() {
        return this.e;
    }

    public void c(int i2) {
        a(androidx.appcompat.a.a.a.c(this.e, i2));
        this.p = i2;
    }

    private void b(Preference preference) {
        if (this.N == null) {
            this.N = new ArrayList();
        }
        this.N.add(preference);
        preference.a(this, F());
    }

    private void c(Preference preference) {
        List<Preference> list = this.N;
        if (list != null) {
            list.remove(preference);
        }
    }

    /* access modifiers changed from: protected */
    public boolean c(String str) {
        if (!G()) {
            return false;
        }
        if (TextUtils.equals(str, b((String) null))) {
            return true;
        }
        e l2 = l();
        if (l2 != null) {
            l2.b(this.r, str);
        } else {
            SharedPreferences.Editor a2 = this.f752f.a();
            a2.putString(this.r, str);
            a(a2);
        }
        return true;
    }

    public void b(boolean z2) {
        List<Preference> list = this.N;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                list.get(i2).a(this, z2);
            }
        }
    }

    public void d(Bundle bundle) {
        b(bundle);
    }

    public void b(Preference preference, boolean z2) {
        if (this.B == z2) {
            this.B = !z2;
            b(F());
            y();
        }
    }

    /* access modifiers changed from: protected */
    public boolean c(boolean z2) {
        if (!G()) {
            return false;
        }
        if (z2 == a(!z2)) {
            return true;
        }
        e l2 = l();
        if (l2 != null) {
            l2.b(this.r, z2);
        } else {
            SharedPreferences.Editor a2 = this.f752f.a();
            a2.putBoolean(this.r, z2);
            a(a2);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public String b(String str) {
        if (!G()) {
            return str;
        }
        e l2 = l();
        if (l2 != null) {
            return l2.a(this.r, str);
        }
        return this.f752f.h().getString(this.r, str);
    }

    public boolean b(Set<String> set) {
        if (!G()) {
            return false;
        }
        if (set.equals(a((Set<String>) null))) {
            return true;
        }
        e l2 = l();
        if (l2 != null) {
            l2.b(this.r, set);
        } else {
            SharedPreferences.Editor a2 = this.f752f.a();
            a2.putStringSet(this.r, set);
            a(a2);
        }
        return true;
    }

    public void c(Bundle bundle) {
        a(bundle);
    }

    /* access modifiers changed from: protected */
    public boolean b(int i2) {
        if (!G()) {
            return false;
        }
        if (i2 == a(i2 ^ -1)) {
            return true;
        }
        e l2 = l();
        if (l2 != null) {
            l2.b(this.r, i2);
        } else {
            SharedPreferences.Editor a2 = this.f752f.a();
            a2.putInt(this.r, i2);
            a(a2);
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void b(Bundle bundle) {
        if (s()) {
            this.P = false;
            Parcelable D2 = D();
            if (!this.P) {
                throw new IllegalStateException("Derived class did not call super.onSaveInstanceState()");
            } else if (D2 != null) {
                bundle.putParcelable(this.r, D2);
            }
        }
    }

    public Preference(Context context, AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, 0);
    }

    public Preference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, g.a(context, R$attr.preferenceStyle, 16842894));
    }

    public Preference(Context context) {
        this(context, (AttributeSet) null);
    }

    private void a(View view, boolean z2) {
        view.setEnabled(z2);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                a(viewGroup.getChildAt(childCount), z2);
            }
        }
    }

    public void a(Drawable drawable) {
        if (this.q != drawable) {
            this.q = drawable;
            this.p = 0;
            y();
        }
    }

    public void a(CharSequence charSequence) {
        if (p() != null) {
            throw new IllegalStateException("Preference already has a SummaryProvider set.");
        } else if (!TextUtils.equals(this.o, charSequence)) {
            this.o = charSequence;
            y();
        }
    }

    public final void a(f fVar) {
        this.R = fVar;
        y();
    }

    public boolean a(Object obj) {
        c cVar = this.f756j;
        return cVar == null || cVar.a(this, obj);
    }

    /* access modifiers changed from: protected */
    public void a(View view) {
        E();
    }

    /* renamed from: a */
    public int compareTo(Preference preference) {
        int i2 = this.l;
        int i3 = preference.l;
        if (i2 != i3) {
            return i2 - i3;
        }
        CharSequence charSequence = this.n;
        CharSequence charSequence2 = preference.n;
        if (charSequence == charSequence2) {
            return 0;
        }
        if (charSequence == null) {
            return 1;
        }
        if (charSequence2 == null) {
            return -1;
        }
        return charSequence.toString().compareToIgnoreCase(preference.n.toString());
    }

    /* access modifiers changed from: protected */
    public void a(j jVar) {
        this.f752f = jVar;
        if (!this.f755i) {
            this.f754h = jVar.b();
        }
        H();
    }

    /* access modifiers changed from: protected */
    public void a(j jVar, long j2) {
        this.f754h = j2;
        this.f755i = true;
        try {
            a(jVar);
        } finally {
            this.f755i = false;
        }
    }

    /* access modifiers changed from: package-private */
    public void a(PreferenceGroup preferenceGroup) {
        if (preferenceGroup == null || this.O == null) {
            this.O = preferenceGroup;
            return;
        }
        throw new IllegalStateException("This preference already has a parent. You must remove the existing parent before assigning a new one.");
    }

    /* access modifiers changed from: protected */
    public <T extends Preference> T a(String str) {
        j jVar = this.f752f;
        if (jVar == null) {
            return null;
        }
        return jVar.a((CharSequence) str);
    }

    public void a(Preference preference, boolean z2) {
        if (this.A == z2) {
            this.A = !z2;
            b(F());
            y();
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void a(boolean z2, Object obj) {
        b(obj);
    }

    private void a(SharedPreferences.Editor editor) {
        if (this.f752f.i()) {
            editor.apply();
        }
    }

    public Set<String> a(Set<String> set) {
        if (!G()) {
            return set;
        }
        e l2 = l();
        if (l2 != null) {
            return l2.a(this.r, set);
        }
        return this.f752f.h().getStringSet(this.r, set);
    }

    /* access modifiers changed from: protected */
    public int a(int i2) {
        if (!G()) {
            return i2;
        }
        e l2 = l();
        if (l2 != null) {
            return l2.a(this.r, i2);
        }
        return this.f752f.h().getInt(this.r, i2);
    }

    /* access modifiers changed from: protected */
    public boolean a(boolean z2) {
        if (!G()) {
            return z2;
        }
        e l2 = l();
        if (l2 != null) {
            return l2.a(this.r, z2);
        }
        return this.f752f.h().getBoolean(this.r, z2);
    }

    /* access modifiers changed from: package-private */
    public void a(Bundle bundle) {
        Parcelable parcelable;
        if (s() && (parcelable = bundle.getParcelable(this.r)) != null) {
            this.P = false;
            a(parcelable);
            if (!this.P) {
                throw new IllegalStateException("Derived class did not call super.onRestoreInstanceState()");
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a(Parcelable parcelable) {
        this.P = true;
        if (parcelable != AbsSavedState.EMPTY_STATE && parcelable != null) {
            throw new IllegalArgumentException("Wrong state class -- expecting Preference State");
        }
    }
}
