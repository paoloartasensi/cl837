package com.google.android.material.appbar;

import android.view.View;
import androidx.core.h.v;

/* compiled from: ViewOffsetHelper */
class d {
    private final View a;
    private int b;
    private int c;
    private int d;
    private int e;

    public d(View view) {
        this.a = view;
    }

    private void d() {
        View view = this.a;
        v.e(view, this.d - (view.getTop() - this.b));
        View view2 = this.a;
        v.d(view2, this.e - (view2.getLeft() - this.c));
    }

    public boolean a(int i2) {
        if (this.e == i2) {
            return false;
        }
        this.e = i2;
        d();
        return true;
    }

    public boolean b(int i2) {
        if (this.d == i2) {
            return false;
        }
        this.d = i2;
        d();
        return true;
    }

    public void c() {
        this.b = this.a.getTop();
        this.c = this.a.getLeft();
        d();
    }

    public int a() {
        return this.b;
    }

    public int b() {
        return this.d;
    }
}
