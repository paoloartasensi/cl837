package com.github.mikephil.charting.data;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import h.a.a.a.e.b.e;
import h.a.a.a.g.a;
import h.a.a.a.i.i;
import java.util.ArrayList;
import java.util.List;

/* compiled from: BaseDataSet */
public abstract class d<T extends Entry> implements e<T> {
    protected List<Integer> a;
    protected a b;
    protected List<a> c;
    protected List<Integer> d;
    private String e;

    /* renamed from: f  reason: collision with root package name */
    protected YAxis.AxisDependency f1358f;

    /* renamed from: g  reason: collision with root package name */
    protected boolean f1359g;

    /* renamed from: h  reason: collision with root package name */
    protected transient h.a.a.a.c.e f1360h;

    /* renamed from: i  reason: collision with root package name */
    protected Typeface f1361i;

    /* renamed from: j  reason: collision with root package name */
    private Legend.LegendForm f1362j;
    private float k;
    private float l;
    private DashPathEffect m;
    protected boolean n;
    protected boolean o;
    protected h.a.a.a.i.e p;
    protected float q;
    protected boolean r;

    public d() {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = "DataSet";
        this.f1358f = YAxis.AxisDependency.LEFT;
        this.f1359g = true;
        this.f1362j = Legend.LegendForm.DEFAULT;
        this.k = Float.NaN;
        this.l = Float.NaN;
        this.m = null;
        this.n = true;
        this.o = true;
        this.p = new h.a.a.a.i.e();
        this.q = 17.0f;
        this.r = true;
        this.a = new ArrayList();
        this.d = new ArrayList();
        this.a.add(Integer.valueOf(Color.rgb(140, 234, 255)));
        this.d.add(-16777216);
    }

    public boolean E() {
        return this.n;
    }

    public boolean G0() {
        return this.o;
    }

    public void H0() {
        if (this.a == null) {
            this.a = new ArrayList();
        }
        this.a.clear();
    }

    public a L() {
        return this.b;
    }

    public YAxis.AxisDependency S() {
        return this.f1358f;
    }

    public float T() {
        return this.q;
    }

    public h.a.a.a.c.e W() {
        if (p()) {
            return i.b();
        }
        return this.f1360h;
    }

    public h.a.a.a.i.e Y() {
        return this.p;
    }

    public void a(h.a.a.a.c.e eVar) {
        if (eVar != null) {
            this.f1360h = eVar;
        }
    }

    public void b(boolean z) {
        this.f1359g = z;
    }

    public int b0() {
        return this.a.get(0).intValue();
    }

    public Legend.LegendForm c() {
        return this.f1362j;
    }

    public a d(int i2) {
        List<a> list = this.c;
        return list.get(i2 % list.size());
    }

    public int e(int i2) {
        List<Integer> list = this.a;
        return list.get(i2 % list.size()).intValue();
    }

    public boolean e0() {
        return this.f1359g;
    }

    public void f(int i2) {
        H0();
        this.a.add(Integer.valueOf(i2));
    }

    public void g(int i2) {
        this.d.clear();
        this.d.add(Integer.valueOf(i2));
    }

    public boolean isVisible() {
        return this.r;
    }

    public List<a> j() {
        return this.c;
    }

    public Typeface l() {
        return this.f1361i;
    }

    public float l0() {
        return this.l;
    }

    public boolean p() {
        return this.f1360h == null;
    }

    public List<Integer> p0() {
        return this.a;
    }

    public String q() {
        return this.e;
    }

    public float y0() {
        return this.k;
    }

    public DashPathEffect z0() {
        return this.m;
    }

    public void a(boolean z) {
        this.n = z;
    }

    public void b(float f2) {
        this.q = i.a(f2);
    }

    public void a(YAxis.AxisDependency axisDependency) {
        this.f1358f = axisDependency;
    }

    public int b(int i2) {
        List<Integer> list = this.d;
        return list.get(i2 % list.size()).intValue();
    }

    public d(String str) {
        this();
        this.e = str;
    }
}
