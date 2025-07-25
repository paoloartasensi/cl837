package com.github.mikephil.charting.components;

import android.graphics.DashPathEffect;
import com.jeremyliao.liveeventbus.BuildConfig;
import h.a.a.a.c.e;
import h.a.a.a.i.i;
import java.util.ArrayList;
import java.util.List;

/* compiled from: AxisBase */
public abstract class a extends b {
    protected boolean A = false;
    protected boolean B = true;
    protected float C = 0.0f;
    protected float D = 0.0f;
    protected boolean E = false;
    protected boolean F = false;
    public float G = 0.0f;
    public float H = 0.0f;
    public float I = 0.0f;

    /* renamed from: g  reason: collision with root package name */
    protected e f1341g;

    /* renamed from: h  reason: collision with root package name */
    private int f1342h = -7829368;

    /* renamed from: i  reason: collision with root package name */
    private float f1343i = 1.0f;

    /* renamed from: j  reason: collision with root package name */
    private int f1344j = -7829368;
    private float k = 1.0f;
    public float[] l = new float[0];
    public float[] m = new float[0];
    public int n;
    public int o;
    private int p = 6;
    protected float q = 1.0f;
    protected boolean r = false;
    protected boolean s = false;
    protected boolean t = true;
    protected boolean u = true;
    protected boolean v = true;
    protected boolean w = false;
    private DashPathEffect x = null;
    private DashPathEffect y = null;
    protected List<LimitLine> z;

    public a() {
        this.e = i.a(10.0f);
        this.b = i.a(5.0f);
        this.c = i.a(5.0f);
        this.z = new ArrayList();
    }

    public String a(int i2) {
        return (i2 < 0 || i2 >= this.l.length) ? BuildConfig.FLAVOR : q().a(this.l[i2], this);
    }

    public void b(boolean z2) {
        this.u = z2;
    }

    public void c(boolean z2) {
        this.t = z2;
    }

    public void d(float f2) {
        this.D = f2;
    }

    public void e(float f2) {
        this.C = f2;
    }

    public int g() {
        return this.f1344j;
    }

    public DashPathEffect h() {
        return this.x;
    }

    public float i() {
        return this.k;
    }

    public float j() {
        return this.q;
    }

    public int k() {
        return this.f1342h;
    }

    public DashPathEffect l() {
        return this.y;
    }

    public float m() {
        return this.f1343i;
    }

    public int n() {
        return this.p;
    }

    public List<LimitLine> o() {
        return this.z;
    }

    public String p() {
        String str = BuildConfig.FLAVOR;
        for (int i2 = 0; i2 < this.l.length; i2++) {
            String a = a(i2);
            if (a != null && str.length() < a.length()) {
                str = a;
            }
        }
        return str;
    }

    public e q() {
        e eVar = this.f1341g;
        if (eVar == null || ((eVar instanceof h.a.a.a.c.a) && ((h.a.a.a.c.a) eVar).a() != this.o)) {
            this.f1341g = new h.a.a.a.c.a(this.o);
        }
        return this.f1341g;
    }

    public boolean r() {
        return this.w && this.n > 0;
    }

    public boolean s() {
        return this.u;
    }

    public boolean t() {
        return this.B;
    }

    public boolean u() {
        return this.t;
    }

    public boolean v() {
        return this.v;
    }

    public boolean w() {
        return this.A;
    }

    public boolean x() {
        return this.s;
    }

    public boolean y() {
        return this.r;
    }

    public void b(float f2) {
        this.E = true;
        this.H = f2;
        this.I = Math.abs(this.G - f2);
    }

    public void c(float f2) {
        this.q = f2;
        this.r = true;
    }

    public void a(e eVar) {
        if (eVar == null) {
            this.f1341g = new h.a.a.a.c.a(this.o);
        } else {
            this.f1341g = eVar;
        }
    }

    public void a(float f2, float f3) {
        float f4 = this.E ? this.H : f2 - this.C;
        float f5 = this.F ? this.G : f3 + this.D;
        if (Math.abs(f5 - f4) == 0.0f) {
            f5 += 1.0f;
            f4 -= 1.0f;
        }
        this.H = f4;
        this.G = f5;
        this.I = Math.abs(f5 - f4);
    }
}
