package com.github.mikephil.charting.components;

import android.graphics.DashPathEffect;
import android.graphics.Paint;

public class LimitLine extends b {

    /* renamed from: g  reason: collision with root package name */
    private float f1335g;

    /* renamed from: h  reason: collision with root package name */
    private float f1336h;

    /* renamed from: i  reason: collision with root package name */
    private int f1337i;

    /* renamed from: j  reason: collision with root package name */
    private Paint.Style f1338j;
    private String k;
    private DashPathEffect l;
    private LimitLabelPosition m;

    public enum LimitLabelPosition {
        LEFT_TOP,
        LEFT_BOTTOM,
        RIGHT_TOP,
        RIGHT_BOTTOM
    }

    public DashPathEffect g() {
        return this.l;
    }

    public String h() {
        return this.k;
    }

    public LimitLabelPosition i() {
        return this.m;
    }

    public float j() {
        return this.f1335g;
    }

    public int k() {
        return this.f1337i;
    }

    public float l() {
        return this.f1336h;
    }

    public Paint.Style m() {
        return this.f1338j;
    }
}
