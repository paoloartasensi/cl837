package com.github.mikephil.charting.data;

import android.graphics.drawable.Drawable;

/* compiled from: BaseEntry */
public abstract class e {
    private float e = 0.0f;

    /* renamed from: f  reason: collision with root package name */
    private Object f1363f = null;

    /* renamed from: g  reason: collision with root package name */
    private Drawable f1364g = null;

    public e() {
    }

    public void a(float f2) {
        this.e = f2;
    }

    public Drawable b() {
        return this.f1364g;
    }

    public float c() {
        return this.e;
    }

    public Object a() {
        return this.f1363f;
    }

    public void a(Object obj) {
        this.f1363f = obj;
    }

    public e(float f2) {
        this.e = f2;
    }
}
