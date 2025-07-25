package com.github.mikephil.charting.components;

import android.graphics.DashPathEffect;
import com.github.mikephil.charting.components.Legend;

/* compiled from: LegendEntry */
public class e {
    public String a;
    public Legend.LegendForm b = Legend.LegendForm.DEFAULT;
    public float c = Float.NaN;
    public float d = Float.NaN;
    public DashPathEffect e = null;

    /* renamed from: f  reason: collision with root package name */
    public int f1349f = 1122867;

    public e() {
    }

    public e(String str, Legend.LegendForm legendForm, float f2, float f3, DashPathEffect dashPathEffect, int i2) {
        this.a = str;
        this.b = legendForm;
        this.c = f2;
        this.d = f3;
        this.e = dashPathEffect;
        this.f1349f = i2;
    }
}
