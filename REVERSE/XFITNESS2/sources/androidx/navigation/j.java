package androidx.navigation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import androidx.navigation.common.R$styleable;
import g.a.h;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: NavDestination */
public class j {
    private final String e;

    /* renamed from: f  reason: collision with root package name */
    private k f733f;

    /* renamed from: g  reason: collision with root package name */
    private int f734g;

    /* renamed from: h  reason: collision with root package name */
    private String f735h;

    /* renamed from: i  reason: collision with root package name */
    private CharSequence f736i;

    /* renamed from: j  reason: collision with root package name */
    private ArrayList<h> f737j;
    private h<d> k;
    private HashMap<String, e> l;

    /* compiled from: NavDestination */
    static class a implements Comparable<a> {
        private final j e;

        /* renamed from: f  reason: collision with root package name */
        private final Bundle f738f;

        /* renamed from: g  reason: collision with root package name */
        private final boolean f739g;

        a(j jVar, Bundle bundle, boolean z) {
            this.e = jVar;
            this.f738f = bundle;
            this.f739g = z;
        }

        /* access modifiers changed from: package-private */
        public j a() {
            return this.e;
        }

        /* access modifiers changed from: package-private */
        public Bundle b() {
            return this.f738f;
        }

        /* renamed from: a */
        public int compareTo(a aVar) {
            if (this.f739g && !aVar.f739g) {
                return 1;
            }
            if (this.f739g || !aVar.f739g) {
                return this.f738f.size() - aVar.f738f.size();
            }
            return -1;
        }
    }

    static {
        new HashMap();
    }

    public j(r<? extends j> rVar) {
        this(s.a((Class<? extends r>) rVar.getClass()));
    }

    static String a(Context context, int i2) {
        if (i2 <= 16777215) {
            return Integer.toString(i2);
        }
        try {
            return context.getResources().getResourceName(i2);
        } catch (Resources.NotFoundException unused) {
            return Integer.toString(i2);
        }
    }

    public final Map<String, e> b() {
        HashMap<String, e> hashMap = this.l;
        if (hashMap == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(hashMap);
    }

    /* access modifiers changed from: package-private */
    public String c() {
        if (this.f735h == null) {
            this.f735h = Integer.toString(this.f734g);
        }
        return this.f735h;
    }

    public final int d() {
        return this.f734g;
    }

    public final CharSequence e() {
        return this.f736i;
    }

    public final String f() {
        return this.e;
    }

    public final k g() {
        return this.f733f;
    }

    /* access modifiers changed from: package-private */
    public boolean h() {
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("(");
        String str = this.f735h;
        if (str == null) {
            sb.append("0x");
            sb.append(Integer.toHexString(this.f734g));
        } else {
            sb.append(str);
        }
        sb.append(")");
        if (this.f736i != null) {
            sb.append(" label=");
            sb.append(this.f736i);
        }
        return sb.toString();
    }

    public j(String str) {
        this.e = str;
    }

    public final void b(int i2) {
        this.f734g = i2;
        this.f735h = null;
    }

    public void a(Context context, AttributeSet attributeSet) {
        TypedArray obtainAttributes = context.getResources().obtainAttributes(attributeSet, R$styleable.Navigator);
        b(obtainAttributes.getResourceId(R$styleable.Navigator_android_id, 0));
        this.f735h = a(context, this.f734g);
        a(obtainAttributes.getText(R$styleable.Navigator_android_label));
        obtainAttributes.recycle();
    }

    /* access modifiers changed from: package-private */
    public final void a(k kVar) {
        this.f733f = kVar;
    }

    public final void a(CharSequence charSequence) {
        this.f736i = charSequence;
    }

    public final void a(String str) {
        if (this.f737j == null) {
            this.f737j = new ArrayList<>();
        }
        this.f737j.add(new h(str));
    }

    /* access modifiers changed from: package-private */
    public a a(Uri uri) {
        ArrayList<h> arrayList = this.f737j;
        a aVar = null;
        if (arrayList == null) {
            return null;
        }
        Iterator<h> it = arrayList.iterator();
        while (it.hasNext()) {
            h next = it.next();
            Bundle a2 = next.a(uri, b());
            if (a2 != null) {
                a aVar2 = new a(this, a2, next.a());
                if (aVar == null || aVar2.compareTo(aVar) > 0) {
                    aVar = aVar2;
                }
            }
        }
        return aVar;
    }

    /* access modifiers changed from: package-private */
    public int[] a() {
        ArrayDeque arrayDeque = new ArrayDeque();
        k kVar = this;
        while (true) {
            k g2 = kVar.g();
            if (g2 == null || g2.j() != kVar.d()) {
                arrayDeque.addFirst(kVar);
            }
            if (g2 == null) {
                break;
            }
            kVar = g2;
        }
        int[] iArr = new int[arrayDeque.size()];
        int i2 = 0;
        Iterator it = arrayDeque.iterator();
        while (it.hasNext()) {
            iArr[i2] = ((j) it.next()).d();
            i2++;
        }
        return iArr;
    }

    public final d a(int i2) {
        h<d> hVar = this.k;
        d a2 = hVar == null ? null : hVar.a(i2);
        if (a2 != null) {
            return a2;
        }
        if (g() != null) {
            return g().a(i2);
        }
        return null;
    }

    public final void a(int i2, d dVar) {
        if (!h()) {
            throw new UnsupportedOperationException("Cannot add action " + i2 + " to " + this + " as it does not support actions, indicating that it is a terminal destination in your navigation graph and will never trigger actions.");
        } else if (i2 != 0) {
            if (this.k == null) {
                this.k = new h<>();
            }
            this.k.c(i2, dVar);
        } else {
            throw new IllegalArgumentException("Cannot have an action with actionId 0");
        }
    }

    public final void a(String str, e eVar) {
        if (this.l == null) {
            this.l = new HashMap<>();
        }
        this.l.put(str, eVar);
    }

    /* access modifiers changed from: package-private */
    public Bundle a(Bundle bundle) {
        HashMap<String, e> hashMap;
        if (bundle == null && ((hashMap = this.l) == null || hashMap.isEmpty())) {
            return null;
        }
        Bundle bundle2 = new Bundle();
        HashMap<String, e> hashMap2 = this.l;
        if (hashMap2 != null) {
            for (Map.Entry next : hashMap2.entrySet()) {
                ((e) next.getValue()).a((String) next.getKey(), bundle2);
            }
        }
        if (bundle != null) {
            bundle2.putAll(bundle);
            HashMap<String, e> hashMap3 = this.l;
            if (hashMap3 != null) {
                for (Map.Entry next2 : hashMap3.entrySet()) {
                    if (!((e) next2.getValue()).b((String) next2.getKey(), bundle)) {
                        throw new IllegalArgumentException("Wrong argument type for '" + ((String) next2.getKey()) + "' in argument bundle. " + ((e) next2.getValue()).b().a() + " expected.");
                    }
                }
            }
        }
        return bundle2;
    }
}
