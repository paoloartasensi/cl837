package com.chileaf.fitness.b;

import android.util.SparseIntArray;
import android.view.View;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.f;
import com.chileaf.fitness.R$id;

/* compiled from: FragmentWearProductBindingImpl */
public class l1 extends k1 {
    private static final ViewDataBinding.j E = null;
    private static final SparseIntArray F;
    private final NestedScrollView C;
    private long D;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        F = sparseIntArray;
        sparseIntArray.put(R$id.iv_image, 1);
        F.put(R$id.tv_description, 2);
        F.put(R$id.btn_scan, 3);
    }

    public l1(f fVar, View view) {
        this(fVar, view, ViewDataBinding.a(fVar, view, 4, E, F));
    }

    /* access modifiers changed from: protected */
    public void a() {
        synchronized (this) {
            this.D = 0;
        }
    }

    /* access modifiers changed from: protected */
    public boolean a(int i2, Object obj, int i3) {
        return false;
    }

    public boolean d() {
        synchronized (this) {
            if (this.D != 0) {
                return true;
            }
            return false;
        }
    }

    public void e() {
        synchronized (this) {
            this.D = 1;
        }
        f();
    }

    private l1(f fVar, View view, Object[] objArr) {
        super(fVar, view, 0, objArr[3], objArr[1], objArr[2]);
        this.D = -1;
        NestedScrollView nestedScrollView = objArr[0];
        this.C = nestedScrollView;
        nestedScrollView.setTag((Object) null);
        a(view);
        e();
    }
}
