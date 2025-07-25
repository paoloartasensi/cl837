package com.chileaf.fitness.b;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.f;
import com.chileaf.fitness.R$id;
import com.chileaf.fitness.model.a.a;
import com.chileaf.fitness.viewmodel.CL820ViewModel;

/* compiled from: ActivityCl820BindingImpl */
public class l extends k {
    private static final ViewDataBinding.j M = null;
    private static final SparseIntArray N;
    private final CoordinatorLayout H;
    private final ConstraintLayout I;
    private final AppCompatTextView J;
    private final AppCompatTextView K;
    private long L;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        N = sparseIntArray;
        sparseIntArray.put(R$id.tv_calorie, 7);
        N.put(R$id.tv_heart_rate, 8);
        N.put(R$id.tv_distance, 9);
        N.put(R$id.constraint_step, 10);
        N.put(R$id.tv_week_history, 11);
        N.put(R$id.tv_hr_history, 12);
        N.put(R$id.tv_rr_history, 13);
        N.put(R$id.tv_hrv_screenshot, 14);
    }

    public l(f fVar, View view) {
        this(fVar, view, ViewDataBinding.a(fVar, view, 15, M, N));
    }

    private boolean b(a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.L |= 16;
        }
        return true;
    }

    private boolean c(a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.L |= 2;
        }
        return true;
    }

    public void a(CL820ViewModel cL820ViewModel) {
        this.G = cL820ViewModel;
        synchronized (this) {
            this.L |= 32;
        }
        notifyPropertyChanged(1);
        super.f();
    }

    public boolean d() {
        synchronized (this) {
            if (this.L != 0) {
                return true;
            }
            return false;
        }
    }

    public void e() {
        synchronized (this) {
            this.L = 64;
        }
        f();
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private l(f fVar, View view, Object[] objArr) {
        super(fVar, view, 5, objArr[10], objArr[2], objArr[7], objArr[3], objArr[9], objArr[8], objArr[12], objArr[14], objArr[13], objArr[6], objArr[11]);
        this.L = -1;
        CoordinatorLayout coordinatorLayout = objArr[0];
        this.H = coordinatorLayout;
        coordinatorLayout.setTag((Object) null);
        ConstraintLayout constraintLayout = objArr[1];
        this.I = constraintLayout;
        constraintLayout.setTag((Object) null);
        AppCompatTextView appCompatTextView = objArr[4];
        this.J = appCompatTextView;
        appCompatTextView.setTag((Object) null);
        AppCompatTextView appCompatTextView2 = objArr[5];
        this.K = appCompatTextView2;
        appCompatTextView2.setTag((Object) null);
        this.z.setTag((Object) null);
        this.A.setTag((Object) null);
        this.E.setTag((Object) null);
        a(view);
        e();
    }

    private boolean d(a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.L |= 1;
        }
        return true;
    }

    private boolean e(a<Integer> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.L |= 4;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean a(int i2, Object obj, int i3) {
        if (i2 == 0) {
            return d((a) obj, i3);
        }
        if (i2 == 1) {
            return c((a) obj, i3);
        }
        if (i2 == 2) {
            return e((a) obj, i3);
        }
        if (i2 == 3) {
            return a((a) obj, i3);
        }
        if (i2 != 4) {
            return false;
        }
        return b((a) obj, i3);
    }

    private boolean a(a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.L |= 8;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0082  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x008a  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00a8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a() {
        /*
            r25 = this;
            r1 = r25
            monitor-enter(r25)
            long r2 = r1.L     // Catch:{ all -> 0x0102 }
            r4 = 0
            r1.L = r4     // Catch:{ all -> 0x0102 }
            monitor-exit(r25)     // Catch:{ all -> 0x0102 }
            com.chileaf.fitness.viewmodel.CL820ViewModel r0 = r1.G
            r6 = 127(0x7f, double:6.27E-322)
            long r6 = r6 & r2
            r8 = 112(0x70, double:5.53E-322)
            r10 = 100
            r12 = 104(0x68, double:5.14E-322)
            r14 = 98
            r16 = 97
            r18 = 0
            int r19 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r19 == 0) goto L_0x00c2
            long r6 = r2 & r16
            int r19 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r19 == 0) goto L_0x003b
            if (r0 == 0) goto L_0x002c
            com.chileaf.fitness.model.a.a r6 = r0.f()
            goto L_0x002e
        L_0x002c:
            r6 = r18
        L_0x002e:
            r7 = 0
            r1.a((int) r7, (androidx.lifecycle.LiveData<?>) r6)
            if (r6 == 0) goto L_0x003b
            java.lang.Object r6 = r6.getValue()
            java.lang.String r6 = (java.lang.String) r6
            goto L_0x003d
        L_0x003b:
            r6 = r18
        L_0x003d:
            long r19 = r2 & r14
            int r7 = (r19 > r4 ? 1 : (r19 == r4 ? 0 : -1))
            if (r7 == 0) goto L_0x0059
            if (r0 == 0) goto L_0x004a
            com.chileaf.fitness.model.a.a r7 = r0.e()
            goto L_0x004c
        L_0x004a:
            r7 = r18
        L_0x004c:
            r14 = 1
            r1.a((int) r14, (androidx.lifecycle.LiveData<?>) r7)
            if (r7 == 0) goto L_0x0059
            java.lang.Object r7 = r7.getValue()
            java.lang.String r7 = (java.lang.String) r7
            goto L_0x005b
        L_0x0059:
            r7 = r18
        L_0x005b:
            long r14 = r2 & r10
            int r21 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
            if (r21 == 0) goto L_0x0082
            if (r0 == 0) goto L_0x0068
            com.chileaf.fitness.model.a.a r14 = r0.h()
            goto L_0x006a
        L_0x0068:
            r14 = r18
        L_0x006a:
            r15 = 2
            r1.a((int) r15, (androidx.lifecycle.LiveData<?>) r14)
            if (r14 == 0) goto L_0x0077
            java.lang.Object r14 = r14.getValue()
            java.lang.Integer r14 = (java.lang.Integer) r14
            goto L_0x0079
        L_0x0077:
            r14 = r18
        L_0x0079:
            int r14 = androidx.databinding.ViewDataBinding.a((java.lang.Integer) r14)
            java.lang.String r14 = java.lang.String.valueOf(r14)
            goto L_0x0084
        L_0x0082:
            r14 = r18
        L_0x0084:
            long r21 = r2 & r12
            int r15 = (r21 > r4 ? 1 : (r21 == r4 ? 0 : -1))
            if (r15 == 0) goto L_0x00a0
            if (r0 == 0) goto L_0x0091
            com.chileaf.fitness.model.a.a r15 = r0.c()
            goto L_0x0093
        L_0x0091:
            r15 = r18
        L_0x0093:
            r10 = 3
            r1.a((int) r10, (androidx.lifecycle.LiveData<?>) r15)
            if (r15 == 0) goto L_0x00a0
            java.lang.Object r10 = r15.getValue()
            java.lang.String r10 = (java.lang.String) r10
            goto L_0x00a2
        L_0x00a0:
            r10 = r18
        L_0x00a2:
            long r23 = r2 & r8
            int r11 = (r23 > r4 ? 1 : (r23 == r4 ? 0 : -1))
            if (r11 == 0) goto L_0x00bf
            if (r0 == 0) goto L_0x00af
            com.chileaf.fitness.model.a.a r0 = r0.d()
            goto L_0x00b1
        L_0x00af:
            r0 = r18
        L_0x00b1:
            r11 = 4
            r1.a((int) r11, (androidx.lifecycle.LiveData<?>) r0)
            if (r0 == 0) goto L_0x00bf
            java.lang.Object r0 = r0.getValue()
            r18 = r0
            java.lang.String r18 = (java.lang.String) r18
        L_0x00bf:
            r0 = r18
            goto L_0x00c8
        L_0x00c2:
            r0 = r18
            r6 = r0
            r7 = r6
            r10 = r7
            r14 = r10
        L_0x00c8:
            long r16 = r2 & r16
            int r11 = (r16 > r4 ? 1 : (r16 == r4 ? 0 : -1))
            if (r11 == 0) goto L_0x00d3
            androidx.appcompat.widget.AppCompatTextView r11 = r1.J
            androidx.databinding.o.c.a((android.widget.TextView) r11, (java.lang.CharSequence) r6)
        L_0x00d3:
            r15 = 98
            long r15 = r15 & r2
            int r6 = (r15 > r4 ? 1 : (r15 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x00df
            androidx.appcompat.widget.AppCompatTextView r6 = r1.K
            androidx.databinding.o.c.a((android.widget.TextView) r6, (java.lang.CharSequence) r7)
        L_0x00df:
            long r6 = r2 & r12
            int r11 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r11 == 0) goto L_0x00ea
            androidx.appcompat.widget.AppCompatTextView r6 = r1.z
            androidx.databinding.o.c.a((android.widget.TextView) r6, (java.lang.CharSequence) r10)
        L_0x00ea:
            long r6 = r2 & r8
            int r8 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r8 == 0) goto L_0x00f5
            androidx.appcompat.widget.AppCompatTextView r6 = r1.A
            androidx.databinding.o.c.a((android.widget.TextView) r6, (java.lang.CharSequence) r0)
        L_0x00f5:
            r6 = 100
            long r2 = r2 & r6
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 == 0) goto L_0x0101
            androidx.appcompat.widget.AppCompatTextView r0 = r1.E
            androidx.databinding.o.c.a((android.widget.TextView) r0, (java.lang.CharSequence) r14)
        L_0x0101:
            return
        L_0x0102:
            r0 = move-exception
            monitor-exit(r25)     // Catch:{ all -> 0x0102 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.chileaf.fitness.b.l.a():void");
    }
}
