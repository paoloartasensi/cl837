package com.chileaf.fitness.b;

import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.f;
import com.chileaf.fitness.R$id;

/* compiled from: ActivityMainBindingImpl */
public class f0 extends e0 {
    private static final ViewDataBinding.j D = null;
    private static final SparseIntArray E;
    private long C;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        E = sparseIntArray;
        sparseIntArray.put(R$id.appbar, 1);
        E.put(R$id.toolbar, 2);
        E.put(R$id.nav_main, 3);
    }

    public f0(f fVar, View view) {
        this(fVar, view, ViewDataBinding.a(fVar, view, 4, D, E));
    }

    /* access modifiers changed from: protected */
    public void a() {
        synchronized (this) {
            this.C = 0;
        }
    }

    /* access modifiers changed from: protected */
    public boolean a(int i2, Object obj, int i3) {
        return false;
    }

    public boolean d() {
        synchronized (this) {
            if (this.C != 0) {
                return true;
            }
            return false;
        }
    }

    public void e() {
        synchronized (this) {
            this.C = 1;
        }
        f();
    }

    private f0(f fVar, View view, Object[] objArr) {
        super(fVar, view, 0, objArr[1], objArr[0], objArr[3], objArr[2]);
        this.C = -1;
        this.z.setTag((Object) null);
        a(view);
        e();
    }
}
