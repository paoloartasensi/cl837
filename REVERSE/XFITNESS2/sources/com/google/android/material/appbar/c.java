package com.google.android.material.appbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/* compiled from: ViewOffsetBehavior */
class c<V extends View> extends CoordinatorLayout.c<V> {
    private d a;
    private int b = 0;
    private int c = 0;

    public c() {
    }

    public boolean a(CoordinatorLayout coordinatorLayout, V v, int i2) {
        b(coordinatorLayout, v, i2);
        if (this.a == null) {
            this.a = new d(v);
        }
        this.a.c();
        int i3 = this.b;
        if (i3 != 0) {
            this.a.b(i3);
            this.b = 0;
        }
        int i4 = this.c;
        if (i4 == 0) {
            return true;
        }
        this.a.a(i4);
        this.c = 0;
        return true;
    }

    /* access modifiers changed from: protected */
    public void b(CoordinatorLayout coordinatorLayout, V v, int i2) {
        coordinatorLayout.c((View) v, i2);
    }

    public int b() {
        d dVar = this.a;
        if (dVar != null) {
            return dVar.b();
        }
        return 0;
    }

    public c(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean a(int i2) {
        d dVar = this.a;
        if (dVar != null) {
            return dVar.b(i2);
        }
        this.b = i2;
        return false;
    }
}
