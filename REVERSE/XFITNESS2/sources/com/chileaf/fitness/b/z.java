package com.chileaf.fitness.b;

import android.util.SparseIntArray;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.f;
import androidx.lifecycle.LifecycleOwner;
import com.chileaf.fitness.R$id;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.model.a.a;
import com.chileaf.fitness.viewmodel.DevicesViewModel;

/* compiled from: ActivityDevicesBindingImpl */
public class z extends y {
    private static final ViewDataBinding.j K;
    private static final SparseIntArray L;
    private final ConstraintLayout I;
    private long J;

    static {
        ViewDataBinding.j jVar = new ViewDataBinding.j(9);
        K = jVar;
        jVar.a(0, new String[]{"info_no_devices", "info_no_bluetooth", "info_no_permission"}, new int[]{3, 4, 5}, new int[]{R$layout.info_no_devices, R$layout.info_no_bluetooth, R$layout.info_no_permission});
        SparseIntArray sparseIntArray = new SparseIntArray();
        L = sparseIntArray;
        sparseIntArray.put(R$id.sb_signal, 6);
        L.put(R$id.tv_signal, 7);
        L.put(R$id.swipe_refresh, 8);
    }

    public z(f fVar, View view) {
        this(fVar, view, ViewDataBinding.a(fVar, view, 9, K, L));
    }

    private boolean b(a<Boolean> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.J |= 4;
        }
        return true;
    }

    private boolean c(a<Boolean> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.J |= 1;
        }
        return true;
    }

    public void a(DevicesViewModel devicesViewModel) {
        this.H = devicesViewModel;
        synchronized (this) {
            this.J |= 256;
        }
        notifyPropertyChanged(1);
        super.f();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001c, code lost:
        if (r6.z.d() == false) goto L_0x001f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001e, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0025, code lost:
        if (r6.B.d() == false) goto L_0x0028;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0027, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0028, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0013, code lost:
        if (r6.A.d() == false) goto L_0x0016;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0015, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean d() {
        /*
            r6 = this;
            monitor-enter(r6)
            long r0 = r6.J     // Catch:{ all -> 0x002a }
            r2 = 0
            r4 = 1
            int r5 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r5 == 0) goto L_0x000c
            monitor-exit(r6)     // Catch:{ all -> 0x002a }
            return r4
        L_0x000c:
            monitor-exit(r6)     // Catch:{ all -> 0x002a }
            com.chileaf.fitness.b.q1 r0 = r6.A
            boolean r0 = r0.d()
            if (r0 == 0) goto L_0x0016
            return r4
        L_0x0016:
            com.chileaf.fitness.b.o1 r0 = r6.z
            boolean r0 = r0.d()
            if (r0 == 0) goto L_0x001f
            return r4
        L_0x001f:
            com.chileaf.fitness.b.s1 r0 = r6.B
            boolean r0 = r0.d()
            if (r0 == 0) goto L_0x0028
            return r4
        L_0x0028:
            r0 = 0
            return r0
        L_0x002a:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x002a }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.chileaf.fitness.b.z.d():boolean");
    }

    public void e() {
        synchronized (this) {
            this.J = 512;
        }
        this.A.e();
        this.z.e();
        this.B.e();
        f();
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private z(f fVar, View view, Object[] objArr) {
        super(fVar, view, 8, objArr[4], objArr[3], objArr[5], objArr[1], objArr[2], objArr[6], objArr[8], objArr[7]);
        this.J = -1;
        ConstraintLayout constraintLayout = objArr[0];
        this.I = constraintLayout;
        constraintLayout.setTag((Object) null);
        this.C.setTag((Object) null);
        this.D.setTag((Object) null);
        View view2 = view;
        a(view);
        e();
    }

    public void a(LifecycleOwner lifecycleOwner) {
        super.a(lifecycleOwner);
        this.A.a(lifecycleOwner);
        this.z.a(lifecycleOwner);
        this.B.a(lifecycleOwner);
    }

    private boolean d(a<Boolean> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.J |= 2;
        }
        return true;
    }

    private boolean e(a<Boolean> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.J |= 32;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean a(int i2, Object obj, int i3) {
        switch (i2) {
            case 0:
                return c((a) obj, i3);
            case 1:
                return d((a) obj, i3);
            case 2:
                return b((a) obj, i3);
            case 3:
                return a((q1) obj, i3);
            case 4:
                return a((o1) obj, i3);
            case 5:
                return e((a) obj, i3);
            case 6:
                return a((s1) obj, i3);
            case 7:
                return a((a<Boolean>) (a) obj, i3);
            default:
                return false;
        }
    }

    private boolean a(q1 q1Var, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.J |= 8;
        }
        return true;
    }

    private boolean a(o1 o1Var, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.J |= 16;
        }
        return true;
    }

    private boolean a(s1 s1Var, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.J |= 64;
        }
        return true;
    }

    private boolean a(a<Boolean> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.J |= 128;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00ca  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0100  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x0132  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a() {
        /*
            r24 = this;
            r1 = r24
            monitor-enter(r24)
            long r2 = r1.J     // Catch:{ all -> 0x0190 }
            r4 = 0
            r1.J = r4     // Catch:{ all -> 0x0190 }
            monitor-exit(r24)     // Catch:{ all -> 0x0190 }
            com.chileaf.fitness.viewmodel.DevicesViewModel r0 = r1.H
            r6 = 935(0x3a7, double:4.62E-321)
            long r6 = r6 & r2
            r10 = 772(0x304, double:3.814E-321)
            r12 = 770(0x302, double:3.804E-321)
            r14 = 769(0x301, double:3.8E-321)
            r16 = 896(0x380, double:4.427E-321)
            r8 = 0
            int r9 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r9 == 0) goto L_0x0135
            long r6 = r2 & r14
            r20 = 0
            int r21 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r21 == 0) goto L_0x0055
            if (r0 == 0) goto L_0x002d
            com.chileaf.fitness.model.a.a r21 = r0.g()
            r9 = r21
            goto L_0x002f
        L_0x002d:
            r9 = r20
        L_0x002f:
            r1.a((int) r8, (androidx.lifecycle.LiveData<?>) r9)
            if (r9 == 0) goto L_0x003b
            java.lang.Object r9 = r9.getValue()
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            goto L_0x003d
        L_0x003b:
            r9 = r20
        L_0x003d:
            boolean r9 = androidx.databinding.ViewDataBinding.a((java.lang.Boolean) r9)
            int r22 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r22 == 0) goto L_0x004f
            if (r9 == 0) goto L_0x004b
            r6 = 524288(0x80000, double:2.590327E-318)
            goto L_0x004e
        L_0x004b:
            r6 = 262144(0x40000, double:1.295163E-318)
        L_0x004e:
            long r2 = r2 | r6
        L_0x004f:
            if (r9 == 0) goto L_0x0052
            goto L_0x0055
        L_0x0052:
            r6 = 8
            goto L_0x0056
        L_0x0055:
            r6 = 0
        L_0x0056:
            long r22 = r2 & r12
            int r7 = (r22 > r4 ? 1 : (r22 == r4 ? 0 : -1))
            if (r7 == 0) goto L_0x008b
            if (r0 == 0) goto L_0x0063
            com.chileaf.fitness.model.a.a r7 = r0.h()
            goto L_0x0065
        L_0x0063:
            r7 = r20
        L_0x0065:
            r9 = 1
            r1.a((int) r9, (androidx.lifecycle.LiveData<?>) r7)
            if (r7 == 0) goto L_0x0072
            java.lang.Object r7 = r7.getValue()
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            goto L_0x0074
        L_0x0072:
            r7 = r20
        L_0x0074:
            boolean r7 = androidx.databinding.ViewDataBinding.a((java.lang.Boolean) r7)
            int r9 = (r22 > r4 ? 1 : (r22 == r4 ? 0 : -1))
            if (r9 == 0) goto L_0x0086
            if (r7 == 0) goto L_0x0082
            r22 = 32768(0x8000, double:1.61895E-319)
            goto L_0x0084
        L_0x0082:
            r22 = 16384(0x4000, double:8.0948E-320)
        L_0x0084:
            long r2 = r2 | r22
        L_0x0086:
            if (r7 == 0) goto L_0x008b
            r7 = 8
            goto L_0x008c
        L_0x008b:
            r7 = 0
        L_0x008c:
            long r22 = r2 & r10
            int r9 = (r22 > r4 ? 1 : (r22 == r4 ? 0 : -1))
            if (r9 == 0) goto L_0x00c1
            if (r0 == 0) goto L_0x0099
            com.chileaf.fitness.model.a.a r9 = r0.f()
            goto L_0x009b
        L_0x0099:
            r9 = r20
        L_0x009b:
            r8 = 2
            r1.a((int) r8, (androidx.lifecycle.LiveData<?>) r9)
            if (r9 == 0) goto L_0x00a8
            java.lang.Object r8 = r9.getValue()
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            goto L_0x00aa
        L_0x00a8:
            r8 = r20
        L_0x00aa:
            boolean r8 = androidx.databinding.ViewDataBinding.a((java.lang.Boolean) r8)
            int r9 = (r22 > r4 ? 1 : (r22 == r4 ? 0 : -1))
            if (r9 == 0) goto L_0x00bb
            if (r8 == 0) goto L_0x00b7
            r22 = 2048(0x800, double:1.0118E-320)
            goto L_0x00b9
        L_0x00b7:
            r22 = 1024(0x400, double:5.06E-321)
        L_0x00b9:
            long r2 = r2 | r22
        L_0x00bb:
            if (r8 == 0) goto L_0x00be
            goto L_0x00c1
        L_0x00be:
            r8 = 8
            goto L_0x00c2
        L_0x00c1:
            r8 = 0
        L_0x00c2:
            r18 = 800(0x320, double:3.953E-321)
            long r22 = r2 & r18
            int r9 = (r22 > r4 ? 1 : (r22 == r4 ? 0 : -1))
            if (r9 == 0) goto L_0x00f9
            if (r0 == 0) goto L_0x00d1
            com.chileaf.fitness.model.a.a r9 = r0.i()
            goto L_0x00d3
        L_0x00d1:
            r9 = r20
        L_0x00d3:
            r10 = 5
            r1.a((int) r10, (androidx.lifecycle.LiveData<?>) r9)
            if (r9 == 0) goto L_0x00e0
            java.lang.Object r9 = r9.getValue()
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            goto L_0x00e2
        L_0x00e0:
            r9 = r20
        L_0x00e2:
            boolean r9 = androidx.databinding.ViewDataBinding.a((java.lang.Boolean) r9)
            int r10 = (r22 > r4 ? 1 : (r22 == r4 ? 0 : -1))
            if (r10 == 0) goto L_0x00f4
            if (r9 == 0) goto L_0x00f0
            r10 = 131072(0x20000, double:6.47582E-319)
            goto L_0x00f3
        L_0x00f0:
            r10 = 65536(0x10000, double:3.2379E-319)
        L_0x00f3:
            long r2 = r2 | r10
        L_0x00f4:
            if (r9 == 0) goto L_0x00f7
            goto L_0x00f9
        L_0x00f7:
            r9 = 4
            goto L_0x00fa
        L_0x00f9:
            r9 = 0
        L_0x00fa:
            long r10 = r2 & r16
            int r22 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r22 == 0) goto L_0x0132
            if (r0 == 0) goto L_0x0107
            com.chileaf.fitness.model.a.a r0 = r0.e()
            goto L_0x0109
        L_0x0107:
            r0 = r20
        L_0x0109:
            r12 = 7
            r1.a((int) r12, (androidx.lifecycle.LiveData<?>) r0)
            if (r0 == 0) goto L_0x0117
            java.lang.Object r0 = r0.getValue()
            r20 = r0
            java.lang.Boolean r20 = (java.lang.Boolean) r20
        L_0x0117:
            boolean r0 = androidx.databinding.ViewDataBinding.a((java.lang.Boolean) r20)
            int r12 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r12 == 0) goto L_0x0127
            if (r0 == 0) goto L_0x0124
            r10 = 8192(0x2000, double:4.0474E-320)
            goto L_0x0126
        L_0x0124:
            r10 = 4096(0x1000, double:2.0237E-320)
        L_0x0126:
            long r2 = r2 | r10
        L_0x0127:
            if (r0 == 0) goto L_0x012c
            r21 = 8
            goto L_0x012e
        L_0x012c:
            r21 = 0
        L_0x012e:
            r0 = r8
            r8 = r21
            goto L_0x013a
        L_0x0132:
            r0 = r8
            r8 = 0
            goto L_0x013a
        L_0x0135:
            r0 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
        L_0x013a:
            long r10 = r2 & r16
            int r12 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r12 == 0) goto L_0x0149
            com.chileaf.fitness.b.o1 r10 = r1.z
            android.view.View r10 = r10.c()
            r10.setVisibility(r8)
        L_0x0149:
            long r10 = r2 & r14
            int r8 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r8 == 0) goto L_0x0158
            com.chileaf.fitness.b.q1 r8 = r1.A
            android.view.View r8 = r8.c()
            r8.setVisibility(r6)
        L_0x0158:
            r10 = 770(0x302, double:3.804E-321)
            long r10 = r10 & r2
            int r6 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x0168
            com.chileaf.fitness.b.s1 r6 = r1.B
            android.view.View r6 = r6.c()
            r6.setVisibility(r7)
        L_0x0168:
            r6 = 800(0x320, double:3.953E-321)
            long r6 = r6 & r2
            int r8 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r8 == 0) goto L_0x0174
            android.widget.ProgressBar r6 = r1.C
            r6.setVisibility(r9)
        L_0x0174:
            r6 = 772(0x304, double:3.814E-321)
            long r2 = r2 & r6
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x0180
            androidx.recyclerview.widget.RecyclerView r2 = r1.D
            r2.setVisibility(r0)
        L_0x0180:
            com.chileaf.fitness.b.q1 r0 = r1.A
            androidx.databinding.ViewDataBinding.d(r0)
            com.chileaf.fitness.b.o1 r0 = r1.z
            androidx.databinding.ViewDataBinding.d(r0)
            com.chileaf.fitness.b.s1 r0 = r1.B
            androidx.databinding.ViewDataBinding.d(r0)
            return
        L_0x0190:
            r0 = move-exception
            monitor-exit(r24)     // Catch:{ all -> 0x0190 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.chileaf.fitness.b.z.a():void");
    }
}
