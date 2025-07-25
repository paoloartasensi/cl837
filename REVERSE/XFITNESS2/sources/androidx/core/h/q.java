package androidx.core.h;

import android.view.View;
import android.view.ViewGroup;

/* compiled from: NestedScrollingParentHelper */
public class q {
    private int a;
    private int b;

    public q(ViewGroup viewGroup) {
    }

    public void a(View view, View view2, int i2) {
        a(view, view2, i2, 0);
    }

    public void a(View view, View view2, int i2, int i3) {
        if (i3 == 1) {
            this.b = i2;
        } else {
            this.a = i2;
        }
    }

    public int a() {
        return this.a | this.b;
    }

    public void a(View view) {
        a(view, 0);
    }

    public void a(View view, int i2) {
        if (i2 == 1) {
            this.b = 0;
        } else {
            this.a = 0;
        }
    }
}
