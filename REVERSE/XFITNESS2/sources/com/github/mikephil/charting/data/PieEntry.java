package com.github.mikephil.charting.data;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint({"ParcelCreator"})
public class PieEntry extends Entry {

    /* renamed from: i  reason: collision with root package name */
    private String f1356i;

    @Deprecated
    public float d() {
        Log.i("DEPRECATED", "Pie entries do not have x values");
        return super.d();
    }

    public String e() {
        return this.f1356i;
    }
}
