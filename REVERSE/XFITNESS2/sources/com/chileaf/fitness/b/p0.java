package com.chileaf.fitness.b;

import android.util.SparseIntArray;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.f;
import com.chileaf.fitness.R$id;

/* compiled from: FragmentAboutBindingImpl */
public class p0 extends o0 {
    private static final ViewDataBinding.j E = null;
    private static final SparseIntArray F;
    private final ConstraintLayout C;
    private long D;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        F = sparseIntArray;
        sparseIntArray.put(R$id.constraint_logo, 1);
        F.put(R$id.card_logo, 2);
        F.put(R$id.tv_version, 3);
        F.put(R$id.constraint_about, 4);
        F.put(R$id.constraint_product, 5);
        F.put(R$id.constraint_phone, 6);
        F.put(R$id.constraint_mobile, 7);
        F.put(R$id.constraint_email, 8);
    }

    public p0(f fVar, View view) {
        this(fVar, view, ViewDataBinding.a(fVar, view, 9, E, F));
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

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private p0(f fVar, View view, Object[] objArr) {
        super(fVar, view, 0, objArr[2], objArr[4], objArr[8], objArr[1], objArr[7], objArr[6], objArr[5], objArr[3]);
        this.D = -1;
        ConstraintLayout constraintLayout = objArr[0];
        this.C = constraintLayout;
        constraintLayout.setTag((Object) null);
        View view2 = view;
        a(view);
        e();
    }
}
