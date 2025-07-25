package com.github.mikephil.charting.components;

import android.graphics.Typeface;
import h.a.a.a.i.i;

/* compiled from: ComponentBase */
public abstract class b {
    protected boolean a = true;
    protected float b = 5.0f;
    protected float c = 5.0f;
    protected Typeface d = null;
    protected float e = i.a(10.0f);

    /* renamed from: f  reason: collision with root package name */
    protected int f1345f = -16777216;

    public void a(float f2) {
        if (f2 > 24.0f) {
            f2 = 24.0f;
        }
        if (f2 < 6.0f) {
            f2 = 6.0f;
        }
        this.e = i.a(f2);
    }

    public float b() {
        return this.e;
    }

    public Typeface c() {
        return this.d;
    }

    public float d() {
        return this.b;
    }

    public float e() {
        return this.c;
    }

    public boolean f() {
        return this.a;
    }

    public int a() {
        return this.f1345f;
    }

    public void a(boolean z) {
        this.a = z;
    }
}
