package com.chileaf.fitness.b;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.f;

/* compiled from: InfoNoDevicesBindingImpl */
public class r1 extends q1 {
    private static final ViewDataBinding.j B = null;
    private static final SparseIntArray C = null;
    private long A;
    private final LinearLayout z;

    public r1(f fVar, View view) {
        this(fVar, view, ViewDataBinding.a(fVar, view, 1, B, C));
    }

    /* access modifiers changed from: protected */
    public void a() {
        synchronized (this) {
            this.A = 0;
        }
    }

    /* access modifiers changed from: protected */
    public boolean a(int i2, Object obj, int i3) {
        return false;
    }

    public boolean d() {
        synchronized (this) {
            if (this.A != 0) {
                return true;
            }
            return false;
        }
    }

    public void e() {
        synchronized (this) {
            this.A = 1;
        }
        f();
    }

    private r1(f fVar, View view, Object[] objArr) {
        super(fVar, view, 0);
        this.A = -1;
        LinearLayout linearLayout = objArr[0];
        this.z = linearLayout;
        linearLayout.setTag((Object) null);
        a(view);
        e();
    }
}
