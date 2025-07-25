package com.chileaf.fitness.b;

import android.util.SparseIntArray;
import android.view.View;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.f;
import com.chileaf.fitness.R$id;

/* compiled from: FragmentTrainSettingBindingImpl */
public class h1 extends g1 {
    private static final ViewDataBinding.j F = null;
    private static final SparseIntArray G;
    private final NestedScrollView D;
    private long E;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        G = sparseIntArray;
        sparseIntArray.put(R$id.constraint_warning, 1);
        G.put(R$id.tv_warning, 2);
        G.put(R$id.constraint_sound, 3);
        G.put(R$id.sw_sound, 4);
    }

    public h1(f fVar, View view) {
        this(fVar, view, ViewDataBinding.a(fVar, view, 5, F, G));
    }

    /* access modifiers changed from: protected */
    public void a() {
        synchronized (this) {
            this.E = 0;
        }
    }

    /* access modifiers changed from: protected */
    public boolean a(int i2, Object obj, int i3) {
        return false;
    }

    public boolean d() {
        synchronized (this) {
            if (this.E != 0) {
                return true;
            }
            return false;
        }
    }

    public void e() {
        synchronized (this) {
            this.E = 1;
        }
        f();
    }

    private h1(f fVar, View view, Object[] objArr) {
        super(fVar, view, 0, objArr[3], objArr[1], objArr[4], objArr[2]);
        this.E = -1;
        NestedScrollView nestedScrollView = objArr[0];
        this.D = nestedScrollView;
        nestedScrollView.setTag((Object) null);
        a(view);
        e();
    }
}
