package com.chileaf.fitness.ui.b;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.j;
import androidx.fragment.app.m;
import kotlin.jvm.internal.i;

/* compiled from: TabPagerAdapter.kt */
public final class b extends m {

    /* renamed from: g  reason: collision with root package name */
    private final String[] f1228g;

    /* renamed from: h  reason: collision with root package name */
    private final Fragment[] f1229h;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public b(String[] strArr, Fragment[] fragmentArr, j jVar) {
        super(jVar, 1);
        i.b(strArr, "tabTitles");
        i.b(fragmentArr, "tabFragments");
        i.b(jVar, "fm");
        this.f1228g = strArr;
        this.f1229h = fragmentArr;
    }

    public int a() {
        return this.f1229h.length;
    }

    public int a(Object obj) {
        i.b(obj, "any");
        return -2;
    }

    public Fragment c(int i2) {
        return this.f1229h[i2];
    }

    public CharSequence a(int i2) {
        return this.f1228g[i2];
    }
}
