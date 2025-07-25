package androidx.navigation.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.j;
import androidx.navigation.r;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@r.b("fragment")
/* compiled from: FragmentNavigator */
public class a extends r<C0040a> {
    private final Context a;
    private final j b;
    private final int c;
    private ArrayDeque<Integer> d = new ArrayDeque<>();

    /* renamed from: androidx.navigation.fragment.a$a  reason: collision with other inner class name */
    /* compiled from: FragmentNavigator */
    public static class C0040a extends androidx.navigation.j {
        private String m;

        public C0040a(r<? extends C0040a> rVar) {
            super((r<? extends androidx.navigation.j>) rVar);
        }

        public void a(Context context, AttributeSet attributeSet) {
            super.a(context, attributeSet);
            TypedArray obtainAttributes = context.getResources().obtainAttributes(attributeSet, R$styleable.FragmentNavigator);
            String string = obtainAttributes.getString(R$styleable.FragmentNavigator_android_name);
            if (string != null) {
                b(string);
            }
            obtainAttributes.recycle();
        }

        public final C0040a b(String str) {
            this.m = str;
            return this;
        }

        public final String i() {
            String str = this.m;
            if (str != null) {
                return str;
            }
            throw new IllegalStateException("Fragment class was not set");
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(super.toString());
            sb.append(" class=");
            String str = this.m;
            if (str == null) {
                sb.append("null");
            } else {
                sb.append(str);
            }
            return sb.toString();
        }
    }

    /* compiled from: FragmentNavigator */
    public static final class b implements r.a {
        private final LinkedHashMap<View, String> a;

        public Map<View, String> a() {
            return Collections.unmodifiableMap(this.a);
        }
    }

    public a(Context context, j jVar, int i2) {
        this.a = context;
        this.b = jVar;
        this.c = i2;
    }

    public Bundle b() {
        Bundle bundle = new Bundle();
        int[] iArr = new int[this.d.size()];
        Iterator<Integer> it = this.d.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            iArr[i2] = it.next().intValue();
            i2++;
        }
        bundle.putIntArray("androidx-nav-fragment:navigator:backStackIds", iArr);
        return bundle;
    }

    public boolean c() {
        if (this.d.isEmpty()) {
            return false;
        }
        if (this.b.x()) {
            Log.i("FragmentNavigator", "Ignoring popBackStack() call: FragmentManager has already saved its state");
            return false;
        }
        this.b.a(a(this.d.size(), this.d.peekLast().intValue()), 1);
        this.d.removeLast();
        return true;
    }

    public C0040a a() {
        return new C0040a(this);
    }

    @Deprecated
    public Fragment a(Context context, j jVar, String str, Bundle bundle) {
        return jVar.p().a(context.getClassLoader(), str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:51:0x00f8  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x012a  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0134 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public androidx.navigation.j a(androidx.navigation.fragment.a.C0040a r9, android.os.Bundle r10, androidx.navigation.o r11, androidx.navigation.r.a r12) {
        /*
            r8 = this;
            androidx.fragment.app.j r0 = r8.b
            boolean r0 = r0.x()
            r1 = 0
            if (r0 == 0) goto L_0x0011
            java.lang.String r9 = "FragmentNavigator"
            java.lang.String r10 = "Ignoring navigate() call: FragmentManager has already saved its state"
            android.util.Log.i(r9, r10)
            return r1
        L_0x0011:
            java.lang.String r0 = r9.i()
            r2 = 0
            char r3 = r0.charAt(r2)
            r4 = 46
            if (r3 != r4) goto L_0x0033
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            android.content.Context r4 = r8.a
            java.lang.String r4 = r4.getPackageName()
            r3.append(r4)
            r3.append(r0)
            java.lang.String r0 = r3.toString()
        L_0x0033:
            android.content.Context r3 = r8.a
            androidx.fragment.app.j r4 = r8.b
            androidx.fragment.app.Fragment r0 = r8.a((android.content.Context) r3, (androidx.fragment.app.j) r4, (java.lang.String) r0, (android.os.Bundle) r10)
            r0.m(r10)
            androidx.fragment.app.j r10 = r8.b
            androidx.fragment.app.p r10 = r10.b()
            r3 = -1
            if (r11 == 0) goto L_0x004c
            int r4 = r11.a()
            goto L_0x004d
        L_0x004c:
            r4 = -1
        L_0x004d:
            if (r11 == 0) goto L_0x0054
            int r5 = r11.b()
            goto L_0x0055
        L_0x0054:
            r5 = -1
        L_0x0055:
            if (r11 == 0) goto L_0x005c
            int r6 = r11.c()
            goto L_0x005d
        L_0x005c:
            r6 = -1
        L_0x005d:
            if (r11 == 0) goto L_0x0064
            int r7 = r11.d()
            goto L_0x0065
        L_0x0064:
            r7 = -1
        L_0x0065:
            if (r4 != r3) goto L_0x006d
            if (r5 != r3) goto L_0x006d
            if (r6 != r3) goto L_0x006d
            if (r7 == r3) goto L_0x0080
        L_0x006d:
            if (r4 == r3) goto L_0x0070
            goto L_0x0071
        L_0x0070:
            r4 = 0
        L_0x0071:
            if (r5 == r3) goto L_0x0074
            goto L_0x0075
        L_0x0074:
            r5 = 0
        L_0x0075:
            if (r6 == r3) goto L_0x0078
            goto L_0x0079
        L_0x0078:
            r6 = 0
        L_0x0079:
            if (r7 == r3) goto L_0x007c
            goto L_0x007d
        L_0x007c:
            r7 = 0
        L_0x007d:
            r10.a((int) r4, (int) r5, (int) r6, (int) r7)
        L_0x0080:
            int r3 = r8.c
            r10.a((int) r3, (androidx.fragment.app.Fragment) r0)
            r10.d(r0)
            int r0 = r9.d()
            java.util.ArrayDeque<java.lang.Integer> r3 = r8.d
            boolean r3 = r3.isEmpty()
            r4 = 1
            if (r11 == 0) goto L_0x00ad
            if (r3 != 0) goto L_0x00ad
            boolean r11 = r11.g()
            if (r11 == 0) goto L_0x00ad
            java.util.ArrayDeque<java.lang.Integer> r11 = r8.d
            java.lang.Object r11 = r11.peekLast()
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r11 = r11.intValue()
            if (r11 != r0) goto L_0x00ad
            r11 = 1
            goto L_0x00ae
        L_0x00ad:
            r11 = 0
        L_0x00ae:
            if (r3 == 0) goto L_0x00b2
        L_0x00b0:
            r2 = 1
            goto L_0x00f4
        L_0x00b2:
            if (r11 == 0) goto L_0x00e5
            java.util.ArrayDeque<java.lang.Integer> r11 = r8.d
            int r11 = r11.size()
            if (r11 <= r4) goto L_0x00f4
            androidx.fragment.app.j r11 = r8.b
            java.util.ArrayDeque<java.lang.Integer> r3 = r8.d
            int r3 = r3.size()
            java.util.ArrayDeque<java.lang.Integer> r5 = r8.d
            java.lang.Object r5 = r5.peekLast()
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            java.lang.String r3 = r8.a(r3, r5)
            r11.a((java.lang.String) r3, (int) r4)
            java.util.ArrayDeque<java.lang.Integer> r11 = r8.d
            int r11 = r11.size()
            java.lang.String r11 = r8.a(r11, r0)
            r10.a((java.lang.String) r11)
            goto L_0x00f4
        L_0x00e5:
            java.util.ArrayDeque<java.lang.Integer> r11 = r8.d
            int r11 = r11.size()
            int r11 = r11 + r4
            java.lang.String r11 = r8.a(r11, r0)
            r10.a((java.lang.String) r11)
            goto L_0x00b0
        L_0x00f4:
            boolean r11 = r12 instanceof androidx.navigation.fragment.a.b
            if (r11 == 0) goto L_0x0122
            androidx.navigation.fragment.a$b r12 = (androidx.navigation.fragment.a.b) r12
            java.util.Map r11 = r12.a()
            java.util.Set r11 = r11.entrySet()
            java.util.Iterator r11 = r11.iterator()
        L_0x0106:
            boolean r12 = r11.hasNext()
            if (r12 == 0) goto L_0x0122
            java.lang.Object r12 = r11.next()
            java.util.Map$Entry r12 = (java.util.Map.Entry) r12
            java.lang.Object r3 = r12.getKey()
            android.view.View r3 = (android.view.View) r3
            java.lang.Object r12 = r12.getValue()
            java.lang.String r12 = (java.lang.String) r12
            r10.a((android.view.View) r3, (java.lang.String) r12)
            goto L_0x0106
        L_0x0122:
            r10.a((boolean) r4)
            r10.a()
            if (r2 == 0) goto L_0x0134
            java.util.ArrayDeque<java.lang.Integer> r10 = r8.d
            java.lang.Integer r11 = java.lang.Integer.valueOf(r0)
            r10.add(r11)
            return r9
        L_0x0134:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.fragment.a.a(androidx.navigation.fragment.a$a, android.os.Bundle, androidx.navigation.o, androidx.navigation.r$a):androidx.navigation.j");
    }

    public void a(Bundle bundle) {
        int[] intArray;
        if (bundle != null && (intArray = bundle.getIntArray("androidx-nav-fragment:navigator:backStackIds")) != null) {
            this.d.clear();
            for (int valueOf : intArray) {
                this.d.add(Integer.valueOf(valueOf));
            }
        }
    }

    private String a(int i2, int i3) {
        return i2 + "-" + i3;
    }
}
