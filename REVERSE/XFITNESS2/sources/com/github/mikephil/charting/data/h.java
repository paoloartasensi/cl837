package com.github.mikephil.charting.data;

import android.util.Log;
import com.github.mikephil.charting.components.YAxis;
import h.a.a.a.d.d;
import h.a.a.a.e.b.e;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ChartData */
public abstract class h<T extends e<? extends Entry>> {
    protected float a;
    protected float b;
    protected float c;
    protected float d;
    protected float e;

    /* renamed from: f  reason: collision with root package name */
    protected float f1365f;

    /* renamed from: g  reason: collision with root package name */
    protected float f1366g;

    /* renamed from: h  reason: collision with root package name */
    protected float f1367h;

    /* renamed from: i  reason: collision with root package name */
    protected List<T> f1368i;

    public h() {
        this.a = -3.4028235E38f;
        this.b = Float.MAX_VALUE;
        this.c = -3.4028235E38f;
        this.d = Float.MAX_VALUE;
        this.e = -3.4028235E38f;
        this.f1365f = Float.MAX_VALUE;
        this.f1366g = -3.4028235E38f;
        this.f1367h = Float.MAX_VALUE;
        this.f1368i = new ArrayList();
    }

    private List<T> a(T[] tArr) {
        ArrayList arrayList = new ArrayList();
        for (T add : tArr) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public int b() {
        List<T> list = this.f1368i;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public List<T> c() {
        return this.f1368i;
    }

    public int d() {
        int i2 = 0;
        for (T X : this.f1368i) {
            i2 += X.X();
        }
        return i2;
    }

    public T e() {
        List<T> list = this.f1368i;
        if (list == null || list.isEmpty()) {
            return null;
        }
        T t = (e) this.f1368i.get(0);
        for (T t2 : this.f1368i) {
            if (t2.X() > t.X()) {
                t = t2;
            }
        }
        return t;
    }

    public float f() {
        return this.c;
    }

    public float g() {
        return this.d;
    }

    public float h() {
        return this.a;
    }

    public float i() {
        return this.b;
    }

    public void j() {
        a();
    }

    public float b(YAxis.AxisDependency axisDependency) {
        if (axisDependency == YAxis.AxisDependency.LEFT) {
            float f2 = this.f1365f;
            return f2 == Float.MAX_VALUE ? this.f1367h : f2;
        }
        float f3 = this.f1367h;
        return f3 == Float.MAX_VALUE ? this.f1365f : f3;
    }

    public void a(float f2, float f3) {
        for (T b2 : this.f1368i) {
            b2.b(f2, f3);
        }
        a();
    }

    /* access modifiers changed from: protected */
    public void a() {
        List<T> list = this.f1368i;
        if (list != null) {
            this.a = -3.4028235E38f;
            this.b = Float.MAX_VALUE;
            this.c = -3.4028235E38f;
            this.d = Float.MAX_VALUE;
            for (T b2 : list) {
                b(b2);
            }
            this.e = -3.4028235E38f;
            this.f1365f = Float.MAX_VALUE;
            this.f1366g = -3.4028235E38f;
            this.f1367h = Float.MAX_VALUE;
            e a2 = a(this.f1368i);
            if (a2 != null) {
                this.e = a2.g0();
                this.f1365f = a2.A();
                for (T t : this.f1368i) {
                    if (t.S() == YAxis.AxisDependency.LEFT) {
                        if (t.A() < this.f1365f) {
                            this.f1365f = t.A();
                        }
                        if (t.g0() > this.e) {
                            this.e = t.g0();
                        }
                    }
                }
            }
            e b3 = b(this.f1368i);
            if (b3 != null) {
                this.f1366g = b3.g0();
                this.f1367h = b3.A();
                for (T t2 : this.f1368i) {
                    if (t2.S() == YAxis.AxisDependency.RIGHT) {
                        if (t2.A() < this.f1367h) {
                            this.f1367h = t2.A();
                        }
                        if (t2.g0() > this.f1366g) {
                            this.f1366g = t2.g0();
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void b(T t) {
        if (this.a < t.g0()) {
            this.a = t.g0();
        }
        if (this.b > t.A()) {
            this.b = t.A();
        }
        if (this.c < t.w()) {
            this.c = t.w();
        }
        if (this.d > t.a0()) {
            this.d = t.a0();
        }
        if (t.S() == YAxis.AxisDependency.LEFT) {
            if (this.e < t.g0()) {
                this.e = t.g0();
            }
            if (this.f1365f > t.A()) {
                this.f1365f = t.A();
                return;
            }
            return;
        }
        if (this.f1366g < t.g0()) {
            this.f1366g = t.g0();
        }
        if (this.f1367h > t.A()) {
            this.f1367h = t.A();
        }
    }

    public h(T... tArr) {
        this.a = -3.4028235E38f;
        this.b = Float.MAX_VALUE;
        this.c = -3.4028235E38f;
        this.d = Float.MAX_VALUE;
        this.e = -3.4028235E38f;
        this.f1365f = Float.MAX_VALUE;
        this.f1366g = -3.4028235E38f;
        this.f1367h = Float.MAX_VALUE;
        this.f1368i = a(tArr);
        j();
    }

    public T b(List<T> list) {
        for (T t : list) {
            if (t.S() == YAxis.AxisDependency.RIGHT) {
                return t;
            }
        }
        return null;
    }

    public float a(YAxis.AxisDependency axisDependency) {
        if (axisDependency == YAxis.AxisDependency.LEFT) {
            float f2 = this.e;
            return f2 == -3.4028235E38f ? this.f1366g : f2;
        }
        float f3 = this.f1366g;
        return f3 == -3.4028235E38f ? this.e : f3;
    }

    public Entry a(d dVar) {
        if (dVar.c() >= this.f1368i.size()) {
            return null;
        }
        return ((e) this.f1368i.get(dVar.c())).a(dVar.g(), dVar.i());
    }

    public T a(int i2) {
        List<T> list = this.f1368i;
        if (list == null || i2 < 0 || i2 >= list.size()) {
            return null;
        }
        return (e) this.f1368i.get(i2);
    }

    public void a(T t) {
        if (t != null) {
            b(t);
            this.f1368i.add(t);
        }
    }

    public void a(Entry entry, int i2) {
        if (this.f1368i.size() <= i2 || i2 < 0) {
            Log.e("addEntry", "Cannot add Entry because dataSetIndex too high or too low.");
            return;
        }
        e eVar = (e) this.f1368i.get(i2);
        if (eVar.a(entry)) {
            a(entry, eVar.S());
        }
    }

    /* access modifiers changed from: protected */
    public void a(Entry entry, YAxis.AxisDependency axisDependency) {
        if (this.a < entry.c()) {
            this.a = entry.c();
        }
        if (this.b > entry.c()) {
            this.b = entry.c();
        }
        if (this.c < entry.d()) {
            this.c = entry.d();
        }
        if (this.d > entry.d()) {
            this.d = entry.d();
        }
        if (axisDependency == YAxis.AxisDependency.LEFT) {
            if (this.e < entry.c()) {
                this.e = entry.c();
            }
            if (this.f1365f > entry.c()) {
                this.f1365f = entry.c();
                return;
            }
            return;
        }
        if (this.f1366g < entry.c()) {
            this.f1366g = entry.c();
        }
        if (this.f1367h > entry.c()) {
            this.f1367h = entry.c();
        }
    }

    /* access modifiers changed from: protected */
    public T a(List<T> list) {
        for (T t : list) {
            if (t.S() == YAxis.AxisDependency.LEFT) {
                return t;
            }
        }
        return null;
    }
}
