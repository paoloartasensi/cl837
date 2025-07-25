package com.chileaf.fitness.b;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.o.c;
import androidx.lifecycle.LiveData;
import com.chileaf.fitness.model.a.a;
import com.chileaf.fitness.viewmodel.BoxingViewModel;

/* compiled from: ActivityBoxingBindingImpl */
public class f extends e {
    private static final ViewDataBinding.j E = null;
    private static final SparseIntArray F = null;
    private final CoordinatorLayout B;
    private final ConstraintLayout C;
    private long D;

    public f(androidx.databinding.f fVar, View view) {
        this(fVar, view, ViewDataBinding.a(fVar, view, 3, E, F));
    }

    public void a(BoxingViewModel boxingViewModel) {
        this.A = boxingViewModel;
        synchronized (this) {
            this.D |= 2;
        }
        notifyPropertyChanged(1);
        super.f();
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
            this.D = 4;
        }
        f();
    }

    private f(androidx.databinding.f fVar, View view, Object[] objArr) {
        super(fVar, view, 1, objArr[2]);
        this.D = -1;
        CoordinatorLayout coordinatorLayout = objArr[0];
        this.B = coordinatorLayout;
        coordinatorLayout.setTag((Object) null);
        ConstraintLayout constraintLayout = objArr[1];
        this.C = constraintLayout;
        constraintLayout.setTag((Object) null);
        this.z.setTag((Object) null);
        a(view);
        e();
    }

    /* access modifiers changed from: protected */
    public boolean a(int i2, Object obj, int i3) {
        if (i2 != 0) {
            return false;
        }
        return a((a) obj, i3);
    }

    private boolean a(a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.D |= 1;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void a() {
        long j2;
        synchronized (this) {
            j2 = this.D;
            this.D = 0;
        }
        BoxingViewModel boxingViewModel = this.A;
        long j3 = j2 & 7;
        String str = null;
        if (j3 != 0) {
            a<String> c = boxingViewModel != null ? boxingViewModel.c() : null;
            a(0, (LiveData<?>) c);
            if (c != null) {
                str = c.getValue();
            }
        }
        if (j3 != 0) {
            c.a((TextView) this.z, (CharSequence) str);
        }
    }
}
