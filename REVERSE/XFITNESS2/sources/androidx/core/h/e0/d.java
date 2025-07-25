package androidx.core.h.e0;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.SparseArray;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.R$id;
import androidx.core.h.e0.g;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* compiled from: AccessibilityNodeInfoCompat */
public class d {
    private static int d;
    private final AccessibilityNodeInfo a;
    public int b = -1;
    private int c = -1;

    /* compiled from: AccessibilityNodeInfoCompat */
    public static class a {
        public static final a e = new a(1, (CharSequence) null);

        /* renamed from: f  reason: collision with root package name */
        public static final a f512f = new a(2, (CharSequence) null);

        /* renamed from: g  reason: collision with root package name */
        public static final a f513g = new a(16, (CharSequence) null);

        /* renamed from: h  reason: collision with root package name */
        public static final a f514h = new a(4096, (CharSequence) null);

        /* renamed from: i  reason: collision with root package name */
        public static final a f515i = new a(8192, (CharSequence) null);
        final Object a;
        private final int b;
        private final Class<? extends g.a> c;
        protected final g d;

        static {
            Class<g.c> cls = g.c.class;
            Class<g.b> cls2 = g.b.class;
            AccessibilityNodeInfo.AccessibilityAction accessibilityAction = null;
            new a(4, (CharSequence) null);
            new a(8, (CharSequence) null);
            new a(32, (CharSequence) null);
            new a(64, (CharSequence) null);
            new a(128, (CharSequence) null);
            new a(256, (CharSequence) null, cls2);
            new a(512, (CharSequence) null, cls2);
            new a(1024, (CharSequence) null, cls);
            new a(2048, (CharSequence) null, cls);
            new a(16384, (CharSequence) null);
            new a(32768, (CharSequence) null);
            new a(65536, (CharSequence) null);
            new a(131072, (CharSequence) null, g.C0026g.class);
            new a(262144, (CharSequence) null);
            new a(524288, (CharSequence) null);
            new a(1048576, (CharSequence) null);
            new a(2097152, (CharSequence) null, g.h.class);
            new a(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN : null, 16908342, (CharSequence) null, (g) null, (Class<? extends g.a>) null);
            new a(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION : null, 16908343, (CharSequence) null, (g) null, g.e.class);
            new a(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP : null, 16908344, (CharSequence) null, (g) null, (Class<? extends g.a>) null);
            new a(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT : null, 16908345, (CharSequence) null, (g) null, (Class<? extends g.a>) null);
            new a(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN : null, 16908346, (CharSequence) null, (g) null, (Class<? extends g.a>) null);
            new a(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT : null, 16908347, (CharSequence) null, (g) null, (Class<? extends g.a>) null);
            new a(Build.VERSION.SDK_INT >= 23 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK : null, 16908348, (CharSequence) null, (g) null, (Class<? extends g.a>) null);
            new a(Build.VERSION.SDK_INT >= 24 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS : null, 16908349, (CharSequence) null, (g) null, g.f.class);
            new a(Build.VERSION.SDK_INT >= 26 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_MOVE_WINDOW : null, 16908354, (CharSequence) null, (g) null, g.d.class);
            new a(Build.VERSION.SDK_INT >= 28 ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TOOLTIP : null, 16908356, (CharSequence) null, (g) null, (Class<? extends g.a>) null);
            if (Build.VERSION.SDK_INT >= 28) {
                accessibilityAction = AccessibilityNodeInfo.AccessibilityAction.ACTION_HIDE_TOOLTIP;
            }
            new a(accessibilityAction, 16908357, (CharSequence) null, (g) null, (Class<? extends g.a>) null);
        }

        public a(int i2, CharSequence charSequence) {
            this((Object) null, i2, charSequence, (g) null, (Class<? extends g.a>) null);
        }

        public int a() {
            if (Build.VERSION.SDK_INT >= 21) {
                return ((AccessibilityNodeInfo.AccessibilityAction) this.a).getId();
            }
            return 0;
        }

        private a(int i2, CharSequence charSequence, Class<? extends g.a> cls) {
            this((Object) null, i2, charSequence, (g) null, cls);
        }

        a(Object obj, int i2, CharSequence charSequence, g gVar, Class<? extends g.a> cls) {
            this.b = i2;
            this.d = gVar;
            if (Build.VERSION.SDK_INT < 21 || obj != null) {
                this.a = obj;
            } else {
                this.a = new AccessibilityNodeInfo.AccessibilityAction(i2, charSequence);
            }
            this.c = cls;
        }

        /* JADX WARNING: Removed duplicated region for block: B:14:0x0025  */
        /* JADX WARNING: Removed duplicated region for block: B:15:0x0028  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean a(android.view.View r5, android.os.Bundle r6) {
            /*
                r4 = this;
                androidx.core.h.e0.g r0 = r4.d
                r1 = 0
                if (r0 == 0) goto L_0x0049
                r0 = 0
                java.lang.Class<? extends androidx.core.h.e0.g$a> r2 = r4.c
                if (r2 == 0) goto L_0x0042
                java.lang.Class[] r3 = new java.lang.Class[r1]     // Catch:{ Exception -> 0x0020 }
                java.lang.reflect.Constructor r2 = r2.getDeclaredConstructor(r3)     // Catch:{ Exception -> 0x0020 }
                java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Exception -> 0x0020 }
                java.lang.Object r1 = r2.newInstance(r1)     // Catch:{ Exception -> 0x0020 }
                androidx.core.h.e0.g$a r1 = (androidx.core.h.e0.g.a) r1     // Catch:{ Exception -> 0x0020 }
                r1.a(r6)     // Catch:{ Exception -> 0x001d }
                r0 = r1
                goto L_0x0042
            L_0x001d:
                r6 = move-exception
                r0 = r1
                goto L_0x0021
            L_0x0020:
                r6 = move-exception
            L_0x0021:
                java.lang.Class<? extends androidx.core.h.e0.g$a> r1 = r4.c
                if (r1 != 0) goto L_0x0028
                java.lang.String r1 = "null"
                goto L_0x002c
            L_0x0028:
                java.lang.String r1 = r1.getName()
            L_0x002c:
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "Failed to execute command with argument class ViewCommandArgument: "
                r2.append(r3)
                r2.append(r1)
                java.lang.String r1 = r2.toString()
                java.lang.String r2 = "A11yActionCompat"
                android.util.Log.e(r2, r1, r6)
            L_0x0042:
                androidx.core.h.e0.g r6 = r4.d
                boolean r5 = r6.a(r5, r0)
                return r5
            L_0x0049:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.h.e0.d.a.a(android.view.View, android.os.Bundle):boolean");
        }

        public a a(CharSequence charSequence, g gVar) {
            return new a((Object) null, this.b, charSequence, gVar, this.c);
        }
    }

    /* compiled from: AccessibilityNodeInfoCompat */
    public static class b {
        final Object a;

        b(Object obj) {
            this.a = obj;
        }

        public static b a(int i2, int i3, boolean z, int i4) {
            int i5 = Build.VERSION.SDK_INT;
            if (i5 >= 21) {
                return new b(AccessibilityNodeInfo.CollectionInfo.obtain(i2, i3, z, i4));
            }
            if (i5 >= 19) {
                return new b(AccessibilityNodeInfo.CollectionInfo.obtain(i2, i3, z));
            }
            return new b((Object) null);
        }
    }

    private d(AccessibilityNodeInfo accessibilityNodeInfo) {
        this.a = accessibilityNodeInfo;
    }

    public static d A() {
        return a(AccessibilityNodeInfo.obtain());
    }

    public static d a(AccessibilityNodeInfo accessibilityNodeInfo) {
        return new d(accessibilityNodeInfo);
    }

    private static String c(int i2) {
        if (i2 == 1) {
            return "ACTION_FOCUS";
        }
        if (i2 == 2) {
            return "ACTION_CLEAR_FOCUS";
        }
        switch (i2) {
            case 4:
                return "ACTION_SELECT";
            case 8:
                return "ACTION_CLEAR_SELECTION";
            case 16:
                return "ACTION_CLICK";
            case 32:
                return "ACTION_LONG_CLICK";
            case 64:
                return "ACTION_ACCESSIBILITY_FOCUS";
            case 128:
                return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
            case 256:
                return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
            case 512:
                return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
            case 1024:
                return "ACTION_NEXT_HTML_ELEMENT";
            case 2048:
                return "ACTION_PREVIOUS_HTML_ELEMENT";
            case 4096:
                return "ACTION_SCROLL_FORWARD";
            case 8192:
                return "ACTION_SCROLL_BACKWARD";
            case 16384:
                return "ACTION_COPY";
            case 32768:
                return "ACTION_PASTE";
            case 65536:
                return "ACTION_CUT";
            case 131072:
                return "ACTION_SET_SELECTION";
            default:
                return "ACTION_UNKNOWN";
        }
    }

    public static d f(View view) {
        return a(AccessibilityNodeInfo.obtain(view));
    }

    private void y() {
        if (Build.VERSION.SDK_INT >= 19) {
            this.a.getExtras().remove("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY");
            this.a.getExtras().remove("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_END_KEY");
            this.a.getExtras().remove("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_FLAGS_KEY");
            this.a.getExtras().remove("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ID_KEY");
        }
    }

    private boolean z() {
        return !a("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY").isEmpty();
    }

    public int b() {
        return this.a.getChildCount();
    }

    public void c(View view) {
        this.c = -1;
        this.a.setSource(view);
    }

    public void d(Rect rect) {
        this.a.setBoundsInScreen(rect);
    }

    public void e(boolean z) {
        this.a.setClickable(z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || d.class != obj.getClass()) {
            return false;
        }
        d dVar = (d) obj;
        AccessibilityNodeInfo accessibilityNodeInfo = this.a;
        if (accessibilityNodeInfo == null) {
            if (dVar.a != null) {
                return false;
            }
        } else if (!accessibilityNodeInfo.equals(dVar.a)) {
            return false;
        }
        return this.c == dVar.c && this.b == dVar.b;
    }

    public int g() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.a.getMovementGranularities();
        }
        return 0;
    }

    public void h(boolean z) {
        this.a.setEnabled(z);
    }

    public int hashCode() {
        AccessibilityNodeInfo accessibilityNodeInfo = this.a;
        if (accessibilityNodeInfo == null) {
            return 0;
        }
        return accessibilityNodeInfo.hashCode();
    }

    public void i(boolean z) {
        this.a.setFocusable(z);
    }

    public void j(boolean z) {
        this.a.setFocused(z);
    }

    public boolean k() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.a.isAccessibilityFocused();
        }
        return false;
    }

    public boolean l() {
        return this.a.isCheckable();
    }

    public boolean m() {
        return this.a.isChecked();
    }

    public boolean n() {
        return this.a.isClickable();
    }

    public void o(boolean z) {
        this.a.setSelected(z);
    }

    public boolean p() {
        return this.a.isFocusable();
    }

    public boolean q() {
        return this.a.isFocused();
    }

    public boolean r() {
        return this.a.isLongClickable();
    }

    public boolean s() {
        return this.a.isPassword();
    }

    public boolean t() {
        return this.a.isScrollable();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        Rect rect = new Rect();
        a(rect);
        sb.append("; boundsInParent: " + rect);
        b(rect);
        sb.append("; boundsInScreen: " + rect);
        sb.append("; packageName: ");
        sb.append(h());
        sb.append("; className: ");
        sb.append(c());
        sb.append("; text: ");
        sb.append(i());
        sb.append("; contentDescription: ");
        sb.append(e());
        sb.append("; viewId: ");
        sb.append(j());
        sb.append("; checkable: ");
        sb.append(l());
        sb.append("; checked: ");
        sb.append(m());
        sb.append("; focusable: ");
        sb.append(p());
        sb.append("; focused: ");
        sb.append(q());
        sb.append("; selected: ");
        sb.append(u());
        sb.append("; clickable: ");
        sb.append(n());
        sb.append("; longClickable: ");
        sb.append(r());
        sb.append("; enabled: ");
        sb.append(o());
        sb.append("; password: ");
        sb.append(s());
        sb.append("; scrollable: " + t());
        sb.append("; [");
        int a2 = a();
        while (a2 != 0) {
            int numberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(a2);
            a2 &= numberOfTrailingZeros ^ -1;
            sb.append(c(numberOfTrailingZeros));
            if (a2 != 0) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public boolean u() {
        return this.a.isSelected();
    }

    public boolean v() {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.a.isVisibleToUser();
        }
        return false;
    }

    public void w() {
        this.a.recycle();
    }

    public AccessibilityNodeInfo x() {
        return this.a;
    }

    public static d a(d dVar) {
        return a(AccessibilityNodeInfo.obtain(dVar.a));
    }

    public boolean b(a aVar) {
        if (Build.VERSION.SDK_INT >= 21) {
            return this.a.removeAction((AccessibilityNodeInfo.AccessibilityAction) aVar.a);
        }
        return false;
    }

    public void d(boolean z) {
        this.a.setChecked(z);
    }

    public void e(CharSequence charSequence) {
        this.a.setPackageName(charSequence);
    }

    public void f(boolean z) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.a.setContentInvalid(z);
        }
    }

    public CharSequence h() {
        return this.a.getPackageName();
    }

    public CharSequence i() {
        if (!z()) {
            return this.a.getText();
        }
        List<Integer> a2 = a("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY");
        List<Integer> a3 = a("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_END_KEY");
        List<Integer> a4 = a("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_FLAGS_KEY");
        List<Integer> a5 = a("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ID_KEY");
        SpannableString spannableString = new SpannableString(TextUtils.substring(this.a.getText(), 0, this.a.getText().length()));
        for (int i2 = 0; i2 < a2.size(); i2++) {
            spannableString.setSpan(new a(a5.get(i2).intValue(), this, f().getInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ACTION_ID_KEY")), a2.get(i2).intValue(), a3.get(i2).intValue(), a4.get(i2).intValue());
        }
        return spannableString;
    }

    public String j() {
        if (Build.VERSION.SDK_INT >= 18) {
            return this.a.getViewIdResourceName();
        }
        return null;
    }

    public void l(boolean z) {
        this.a.setLongClickable(z);
    }

    public void m(boolean z) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.a.setScreenReaderFocusable(z);
        } else {
            a(1, z);
        }
    }

    public void n(boolean z) {
        this.a.setScrollable(z);
    }

    public boolean o() {
        return this.a.isEnabled();
    }

    public void p(boolean z) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.a.setShowingHintText(z);
        } else {
            a(4, z);
        }
    }

    public void q(boolean z) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.a.setVisibleToUser(z);
        }
    }

    private SparseArray<WeakReference<ClickableSpan>> d(View view) {
        SparseArray<WeakReference<ClickableSpan>> e = e(view);
        if (e != null) {
            return e;
        }
        SparseArray<WeakReference<ClickableSpan>> sparseArray = new SparseArray<>();
        view.setTag(R$id.tag_accessibility_clickable_spans, sparseArray);
        return sparseArray;
    }

    private SparseArray<WeakReference<ClickableSpan>> e(View view) {
        return (SparseArray) view.getTag(R$id.tag_accessibility_clickable_spans);
    }

    public static ClickableSpan[] h(CharSequence charSequence) {
        if (charSequence instanceof Spanned) {
            return (ClickableSpan[]) ((Spanned) charSequence).getSpans(0, charSequence.length(), ClickableSpan.class);
        }
        return null;
    }

    public void a(View view) {
        this.a.addChild(view);
    }

    public void c(View view, int i2) {
        this.c = i2;
        if (Build.VERSION.SDK_INT >= 16) {
            this.a.setSource(view, i2);
        }
    }

    public void g(CharSequence charSequence) {
        this.a.setText(charSequence);
    }

    public void k(boolean z) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.a.setHeading(z);
        } else {
            a(2, z);
        }
    }

    /* compiled from: AccessibilityNodeInfoCompat */
    public static class c {
        final Object a;

        c(Object obj) {
            this.a = obj;
        }

        public static c a(int i2, int i3, int i4, int i5, boolean z, boolean z2) {
            int i6 = Build.VERSION.SDK_INT;
            if (i6 >= 21) {
                return new c(AccessibilityNodeInfo.CollectionItemInfo.obtain(i2, i3, i4, i5, z, z2));
            }
            if (i6 >= 19) {
                return new c(AccessibilityNodeInfo.CollectionItemInfo.obtain(i2, i3, i4, i5, z));
            }
            return new c((Object) null);
        }

        public int b() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.CollectionItemInfo) this.a).getColumnSpan();
            }
            return 0;
        }

        public int c() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.CollectionItemInfo) this.a).getRowIndex();
            }
            return 0;
        }

        public int d() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.CollectionItemInfo) this.a).getRowSpan();
            }
            return 0;
        }

        public boolean e() {
            if (Build.VERSION.SDK_INT >= 21) {
                return ((AccessibilityNodeInfo.CollectionItemInfo) this.a).isSelected();
            }
            return false;
        }

        public int a() {
            if (Build.VERSION.SDK_INT >= 19) {
                return ((AccessibilityNodeInfo.CollectionItemInfo) this.a).getColumnIndex();
            }
            return 0;
        }
    }

    private void g(View view) {
        SparseArray<WeakReference<ClickableSpan>> e = e(view);
        if (e != null) {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < e.size(); i2++) {
                if (e.valueAt(i2).get() == null) {
                    arrayList.add(Integer.valueOf(i2));
                }
            }
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                e.remove(((Integer) arrayList.get(i3)).intValue());
            }
        }
    }

    public void a(View view, int i2) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.a.addChild(view, i2);
        }
    }

    public void b(int i2) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.a.setMovementGranularities(i2);
        }
    }

    public CharSequence e() {
        return this.a.getContentDescription();
    }

    public Bundle f() {
        if (Build.VERSION.SDK_INT >= 19) {
            return this.a.getExtras();
        }
        return new Bundle();
    }

    public int a() {
        return this.a.getActions();
    }

    public void b(View view) {
        this.b = -1;
        this.a.setParent(view);
    }

    public void c(Rect rect) {
        this.a.setBoundsInParent(rect);
    }

    public c d() {
        AccessibilityNodeInfo.CollectionItemInfo collectionItemInfo;
        if (Build.VERSION.SDK_INT < 19 || (collectionItemInfo = this.a.getCollectionItemInfo()) == null) {
            return null;
        }
        return new c(collectionItemInfo);
    }

    public void a(int i2) {
        this.a.addAction(i2);
    }

    public void c(boolean z) {
        this.a.setCheckable(z);
    }

    public void f(CharSequence charSequence) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 28) {
            this.a.setPaneTitle(charSequence);
        } else if (i2 >= 19) {
            this.a.getExtras().putCharSequence("androidx.view.accessibility.AccessibilityNodeInfoCompat.PANE_TITLE_KEY", charSequence);
        }
    }

    private List<Integer> a(String str) {
        if (Build.VERSION.SDK_INT < 19) {
            return new ArrayList();
        }
        ArrayList<Integer> integerArrayList = this.a.getExtras().getIntegerArrayList(str);
        if (integerArrayList != null) {
            return integerArrayList;
        }
        ArrayList arrayList = new ArrayList();
        this.a.getExtras().putIntegerArrayList(str, arrayList);
        return arrayList;
    }

    public void b(View view, int i2) {
        this.b = i2;
        if (Build.VERSION.SDK_INT >= 16) {
            this.a.setParent(view, i2);
        }
    }

    public CharSequence c() {
        return this.a.getClassName();
    }

    public void c(CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.a.setError(charSequence);
        }
    }

    public void d(CharSequence charSequence) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 26) {
            this.a.setHintText(charSequence);
        } else if (i2 >= 19) {
            this.a.getExtras().putCharSequence("androidx.view.accessibility.AccessibilityNodeInfoCompat.HINT_TEXT_KEY", charSequence);
        }
    }

    public void b(Rect rect) {
        this.a.getBoundsInScreen(rect);
    }

    public void g(boolean z) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.a.setDismissable(z);
        }
    }

    public void b(CharSequence charSequence) {
        this.a.setContentDescription(charSequence);
    }

    public void b(Object obj) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.a.setCollectionItemInfo(obj == null ? null : (AccessibilityNodeInfo.CollectionItemInfo) ((c) obj).a);
        }
    }

    public void a(a aVar) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.a.addAction((AccessibilityNodeInfo.AccessibilityAction) aVar.a);
        }
    }

    public void b(boolean z) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.a.setCanOpenPopup(z);
        }
    }

    public boolean a(int i2, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            return this.a.performAction(i2, bundle);
        }
        return false;
    }

    public void a(Rect rect) {
        this.a.getBoundsInParent(rect);
    }

    public void a(boolean z) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.a.setAccessibilityFocused(z);
        }
    }

    public void a(CharSequence charSequence) {
        this.a.setClassName(charSequence);
    }

    public void a(CharSequence charSequence, View view) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 19 && i2 < 26) {
            y();
            g(view);
            ClickableSpan[] h2 = h(charSequence);
            if (h2 != null && h2.length > 0) {
                f().putInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ACTION_ID_KEY", R$id.accessibility_action_clickable_span);
                SparseArray<WeakReference<ClickableSpan>> d2 = d(view);
                int i3 = 0;
                while (h2 != null && i3 < h2.length) {
                    int a2 = a(h2[i3], d2);
                    d2.put(a2, new WeakReference(h2[i3]));
                    a(h2[i3], (Spanned) charSequence, a2);
                    i3++;
                }
            }
        }
    }

    private int a(ClickableSpan clickableSpan, SparseArray<WeakReference<ClickableSpan>> sparseArray) {
        if (sparseArray != null) {
            for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                if (clickableSpan.equals((ClickableSpan) sparseArray.valueAt(i2).get())) {
                    return sparseArray.keyAt(i2);
                }
            }
        }
        int i3 = d;
        d = i3 + 1;
        return i3;
    }

    private void a(ClickableSpan clickableSpan, Spanned spanned, int i2) {
        a("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_START_KEY").add(Integer.valueOf(spanned.getSpanStart(clickableSpan)));
        a("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_END_KEY").add(Integer.valueOf(spanned.getSpanEnd(clickableSpan)));
        a("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_FLAGS_KEY").add(Integer.valueOf(spanned.getSpanFlags(clickableSpan)));
        a("androidx.view.accessibility.AccessibilityNodeInfoCompat.SPANS_ID_KEY").add(Integer.valueOf(i2));
    }

    public void a(Object obj) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.a.setCollectionInfo(obj == null ? null : (AccessibilityNodeInfo.CollectionInfo) ((b) obj).a);
        }
    }

    private void a(int i2, boolean z) {
        Bundle f2 = f();
        if (f2 != null) {
            int i3 = f2.getInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.BOOLEAN_PROPERTY_KEY", 0) & (i2 ^ -1);
            if (!z) {
                i2 = 0;
            }
            f2.putInt("androidx.view.accessibility.AccessibilityNodeInfoCompat.BOOLEAN_PROPERTY_KEY", i2 | i3);
        }
    }
}
