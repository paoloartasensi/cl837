package com.chileaf.fitness.b;

import android.support.v4.media.session.PlaybackStateCompat;
import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.f;
import com.chileaf.fitness.R$id;
import com.chileaf.fitness.model.a.a;
import com.chileaf.fitness.viewmodel.CDNViewModel;

/* compiled from: ActivityCdnBindingImpl */
public class h extends g {
    private static final ViewDataBinding.j M = null;
    private static final SparseIntArray N;
    private final CoordinatorLayout E;
    private final ConstraintLayout F;
    private final AppCompatTextView G;
    private final AppCompatTextView H;
    private final AppCompatTextView I;
    private final AppCompatTextView J;
    private final AppCompatTextView K;
    private long L;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        N = sparseIntArray;
        sparseIntArray.put(R$id.tv_speed, 11);
        N.put(R$id.tv_cadence, 12);
        N.put(R$id.tv_cadence_unit, 13);
        N.put(R$id.tv_distance, 14);
        N.put(R$id.tv_total_distance, 15);
        N.put(R$id.tv_step, 16);
    }

    public h(f fVar, View view) {
        this(fVar, view, ViewDataBinding.a(fVar, view, 17, M, N));
    }

    private boolean b(a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.L |= 256;
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

    private boolean f(a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.L |= 16;
        }
        return true;
    }

    private boolean g(a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.L |= 1;
        }
        return true;
    }

    private boolean h(a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.L |= 4;
        }
        return true;
    }

    private boolean i(a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.L |= 64;
        }
        return true;
    }

    public void a(CDNViewModel cDNViewModel) {
        this.D = cDNViewModel;
        synchronized (this) {
            this.L |= 512;
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
            this.L = PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        }
        f();
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private h(f fVar, View view, Object[] objArr) {
        super(fVar, view, 9, objArr[2], objArr[12], objArr[13], objArr[14], objArr[6], objArr[11], objArr[3], objArr[16], objArr[15], objArr[8]);
        this.L = -1;
        CoordinatorLayout coordinatorLayout = objArr[0];
        this.E = coordinatorLayout;
        coordinatorLayout.setTag((Object) null);
        ConstraintLayout constraintLayout = objArr[1];
        this.F = constraintLayout;
        constraintLayout.setTag((Object) null);
        AppCompatTextView appCompatTextView = objArr[10];
        this.G = appCompatTextView;
        appCompatTextView.setTag((Object) null);
        AppCompatTextView appCompatTextView2 = objArr[4];
        this.H = appCompatTextView2;
        appCompatTextView2.setTag((Object) null);
        AppCompatTextView appCompatTextView3 = objArr[5];
        this.I = appCompatTextView3;
        appCompatTextView3.setTag((Object) null);
        AppCompatTextView appCompatTextView4 = objArr[7];
        this.J = appCompatTextView4;
        appCompatTextView4.setTag((Object) null);
        AppCompatTextView appCompatTextView5 = objArr[9];
        this.K = appCompatTextView5;
        appCompatTextView5.setTag((Object) null);
        this.z.setTag((Object) null);
        this.A.setTag((Object) null);
        this.B.setTag((Object) null);
        this.C.setTag((Object) null);
        a(view);
        e();
    }

    private boolean d(a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.L |= 128;
        }
        return true;
    }

    private boolean e(a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.L |= 32;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean a(int i2, Object obj, int i3) {
        switch (i2) {
            case 0:
                return g((a) obj, i3);
            case 1:
                return c((a) obj, i3);
            case 2:
                return h((a) obj, i3);
            case 3:
                return a((a) obj, i3);
            case 4:
                return f((a) obj, i3);
            case 5:
                return e((a) obj, i3);
            case 6:
                return i((a) obj, i3);
            case 7:
                return d((a) obj, i3);
            case 8:
                return b((a) obj, i3);
            default:
                return false;
        }
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
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00a3  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00c1  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00df  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x00ff  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x011d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a() {
        /*
            r37 = this;
            r1 = r37
            monitor-enter(r37)
            long r2 = r1.L     // Catch:{ all -> 0x01aa }
            r4 = 0
            r1.L = r4     // Catch:{ all -> 0x01aa }
            monitor-exit(r37)     // Catch:{ all -> 0x01aa }
            com.chileaf.fitness.viewmodel.CDNViewModel r0 = r1.D
            r6 = 2047(0x7ff, double:1.0114E-320)
            long r6 = r6 & r2
            r8 = 1600(0x640, double:7.905E-321)
            r12 = 1544(0x608, double:7.63E-321)
            r14 = 1540(0x604, double:7.61E-321)
            r16 = 1792(0x700, double:8.854E-321)
            r18 = 1538(0x602, double:7.6E-321)
            r20 = 1552(0x610, double:7.67E-321)
            r22 = 1537(0x601, double:7.594E-321)
            r24 = 1568(0x620, double:7.747E-321)
            r26 = 0
            int r27 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r27 == 0) goto L_0x0138
            long r6 = r2 & r22
            int r27 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r27 == 0) goto L_0x0041
            if (r0 == 0) goto L_0x0032
            com.chileaf.fitness.model.a.a r6 = r0.j()
            goto L_0x0034
        L_0x0032:
            r6 = r26
        L_0x0034:
            r7 = 0
            r1.a((int) r7, (androidx.lifecycle.LiveData<?>) r6)
            if (r6 == 0) goto L_0x0041
            java.lang.Object r6 = r6.getValue()
            java.lang.String r6 = (java.lang.String) r6
            goto L_0x0043
        L_0x0041:
            r6 = r26
        L_0x0043:
            long r27 = r2 & r18
            int r7 = (r27 > r4 ? 1 : (r27 == r4 ? 0 : -1))
            if (r7 == 0) goto L_0x005f
            if (r0 == 0) goto L_0x0050
            com.chileaf.fitness.model.a.a r7 = r0.e()
            goto L_0x0052
        L_0x0050:
            r7 = r26
        L_0x0052:
            r10 = 1
            r1.a((int) r10, (androidx.lifecycle.LiveData<?>) r7)
            if (r7 == 0) goto L_0x005f
            java.lang.Object r7 = r7.getValue()
            java.lang.String r7 = (java.lang.String) r7
            goto L_0x0061
        L_0x005f:
            r7 = r26
        L_0x0061:
            long r10 = r2 & r14
            int r29 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r29 == 0) goto L_0x007d
            if (r0 == 0) goto L_0x006e
            com.chileaf.fitness.model.a.a r10 = r0.k()
            goto L_0x0070
        L_0x006e:
            r10 = r26
        L_0x0070:
            r11 = 2
            r1.a((int) r11, (androidx.lifecycle.LiveData<?>) r10)
            if (r10 == 0) goto L_0x007d
            java.lang.Object r10 = r10.getValue()
            java.lang.String r10 = (java.lang.String) r10
            goto L_0x007f
        L_0x007d:
            r10 = r26
        L_0x007f:
            long r29 = r2 & r12
            int r11 = (r29 > r4 ? 1 : (r29 == r4 ? 0 : -1))
            if (r11 == 0) goto L_0x009b
            if (r0 == 0) goto L_0x008c
            com.chileaf.fitness.model.a.a r11 = r0.c()
            goto L_0x008e
        L_0x008c:
            r11 = r26
        L_0x008e:
            r12 = 3
            r1.a((int) r12, (androidx.lifecycle.LiveData<?>) r11)
            if (r11 == 0) goto L_0x009b
            java.lang.Object r11 = r11.getValue()
            java.lang.String r11 = (java.lang.String) r11
            goto L_0x009d
        L_0x009b:
            r11 = r26
        L_0x009d:
            long r12 = r2 & r20
            int r31 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            if (r31 == 0) goto L_0x00b9
            if (r0 == 0) goto L_0x00aa
            com.chileaf.fitness.model.a.a r12 = r0.i()
            goto L_0x00ac
        L_0x00aa:
            r12 = r26
        L_0x00ac:
            r13 = 4
            r1.a((int) r13, (androidx.lifecycle.LiveData<?>) r12)
            if (r12 == 0) goto L_0x00b9
            java.lang.Object r12 = r12.getValue()
            java.lang.String r12 = (java.lang.String) r12
            goto L_0x00bb
        L_0x00b9:
            r12 = r26
        L_0x00bb:
            long r31 = r2 & r24
            int r13 = (r31 > r4 ? 1 : (r31 == r4 ? 0 : -1))
            if (r13 == 0) goto L_0x00d7
            if (r0 == 0) goto L_0x00c8
            com.chileaf.fitness.model.a.a r13 = r0.h()
            goto L_0x00ca
        L_0x00c8:
            r13 = r26
        L_0x00ca:
            r14 = 5
            r1.a((int) r14, (androidx.lifecycle.LiveData<?>) r13)
            if (r13 == 0) goto L_0x00d7
            java.lang.Object r13 = r13.getValue()
            java.lang.String r13 = (java.lang.String) r13
            goto L_0x00d9
        L_0x00d7:
            r13 = r26
        L_0x00d9:
            long r14 = r2 & r8
            int r33 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
            if (r33 == 0) goto L_0x00f5
            if (r0 == 0) goto L_0x00e6
            com.chileaf.fitness.model.a.a r14 = r0.l()
            goto L_0x00e8
        L_0x00e6:
            r14 = r26
        L_0x00e8:
            r15 = 6
            r1.a((int) r15, (androidx.lifecycle.LiveData<?>) r14)
            if (r14 == 0) goto L_0x00f5
            java.lang.Object r14 = r14.getValue()
            java.lang.String r14 = (java.lang.String) r14
            goto L_0x00f7
        L_0x00f5:
            r14 = r26
        L_0x00f7:
            r27 = 1664(0x680, double:8.22E-321)
            long r33 = r2 & r27
            int r15 = (r33 > r4 ? 1 : (r33 == r4 ? 0 : -1))
            if (r15 == 0) goto L_0x0115
            if (r0 == 0) goto L_0x0106
            com.chileaf.fitness.model.a.a r15 = r0.f()
            goto L_0x0108
        L_0x0106:
            r15 = r26
        L_0x0108:
            r8 = 7
            r1.a((int) r8, (androidx.lifecycle.LiveData<?>) r15)
            if (r15 == 0) goto L_0x0115
            java.lang.Object r8 = r15.getValue()
            java.lang.String r8 = (java.lang.String) r8
            goto L_0x0117
        L_0x0115:
            r8 = r26
        L_0x0117:
            long r35 = r2 & r16
            int r9 = (r35 > r4 ? 1 : (r35 == r4 ? 0 : -1))
            if (r9 == 0) goto L_0x0135
            if (r0 == 0) goto L_0x0124
            com.chileaf.fitness.model.a.a r0 = r0.d()
            goto L_0x0126
        L_0x0124:
            r0 = r26
        L_0x0126:
            r9 = 8
            r1.a((int) r9, (androidx.lifecycle.LiveData<?>) r0)
            if (r0 == 0) goto L_0x0135
            java.lang.Object r0 = r0.getValue()
            r26 = r0
            java.lang.String r26 = (java.lang.String) r26
        L_0x0135:
            r0 = r26
            goto L_0x0142
        L_0x0138:
            r0 = r26
            r6 = r0
            r7 = r6
            r8 = r7
            r10 = r8
            r11 = r10
            r12 = r11
            r13 = r12
            r14 = r13
        L_0x0142:
            long r24 = r2 & r24
            int r9 = (r24 > r4 ? 1 : (r24 == r4 ? 0 : -1))
            if (r9 == 0) goto L_0x014d
            androidx.appcompat.widget.AppCompatTextView r9 = r1.G
            androidx.databinding.o.c.a((android.widget.TextView) r9, (java.lang.CharSequence) r13)
        L_0x014d:
            long r20 = r2 & r20
            int r9 = (r20 > r4 ? 1 : (r20 == r4 ? 0 : -1))
            if (r9 == 0) goto L_0x0158
            androidx.appcompat.widget.AppCompatTextView r9 = r1.H
            androidx.databinding.o.c.a((android.widget.TextView) r9, (java.lang.CharSequence) r12)
        L_0x0158:
            long r12 = r2 & r16
            int r9 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            if (r9 == 0) goto L_0x0163
            androidx.appcompat.widget.AppCompatTextView r9 = r1.I
            androidx.databinding.o.c.a((android.widget.TextView) r9, (java.lang.CharSequence) r0)
        L_0x0163:
            long r12 = r2 & r18
            int r0 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            if (r0 == 0) goto L_0x016e
            androidx.appcompat.widget.AppCompatTextView r0 = r1.J
            androidx.databinding.o.c.a((android.widget.TextView) r0, (java.lang.CharSequence) r7)
        L_0x016e:
            r12 = 1540(0x604, double:7.61E-321)
            long r12 = r12 & r2
            int r0 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            if (r0 == 0) goto L_0x017a
            androidx.appcompat.widget.AppCompatTextView r0 = r1.K
            androidx.databinding.o.c.a((android.widget.TextView) r0, (java.lang.CharSequence) r10)
        L_0x017a:
            r9 = 1544(0x608, double:7.63E-321)
            long r9 = r9 & r2
            int r0 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r0 == 0) goto L_0x0186
            androidx.appcompat.widget.AppCompatTextView r0 = r1.z
            androidx.databinding.o.c.a((android.widget.TextView) r0, (java.lang.CharSequence) r11)
        L_0x0186:
            r9 = 1664(0x680, double:8.22E-321)
            long r9 = r9 & r2
            int r0 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r0 == 0) goto L_0x0192
            androidx.appcompat.widget.AppCompatTextView r0 = r1.A
            androidx.databinding.o.c.a((android.widget.TextView) r0, (java.lang.CharSequence) r8)
        L_0x0192:
            long r7 = r2 & r22
            int r0 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r0 == 0) goto L_0x019d
            androidx.appcompat.widget.AppCompatTextView r0 = r1.B
            androidx.databinding.o.c.a((android.widget.TextView) r0, (java.lang.CharSequence) r6)
        L_0x019d:
            r6 = 1600(0x640, double:7.905E-321)
            long r2 = r2 & r6
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 == 0) goto L_0x01a9
            androidx.appcompat.widget.AppCompatTextView r0 = r1.C
            androidx.databinding.o.c.a((android.widget.TextView) r0, (java.lang.CharSequence) r14)
        L_0x01a9:
            return
        L_0x01aa:
            r0 = move-exception
            monitor-exit(r37)     // Catch:{ all -> 0x01aa }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.chileaf.fitness.b.h.a():void");
    }
}
