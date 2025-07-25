package com.chileaf.fitness.b;

import android.support.v4.media.session.PlaybackStateCompat;
import android.util.SparseIntArray;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.f;
import androidx.databinding.h;
import androidx.databinding.o.c;
import com.chileaf.fitness.R$id;
import com.chileaf.fitness.viewmodel.WeightViewModel;

/* compiled from: ActivityWeightBindingImpl */
public class l0 extends k0 {
    private static final ViewDataBinding.j e0 = null;
    private static final SparseIntArray f0;
    private final CoordinatorLayout Z;
    private final ConstraintLayout a0;
    private h b0;
    private h c0;
    private long d0;

    /* compiled from: ActivityWeightBindingImpl */
    class a implements h {
        a() {
        }

        public void a() {
            String a2 = c.a(l0.this.A);
            WeightViewModel weightViewModel = l0.this.Y;
            boolean z = true;
            if (weightViewModel != null) {
                com.chileaf.fitness.model.a.a<String> c = weightViewModel.c();
                if (c == null) {
                    z = false;
                }
                if (z) {
                    c.setValue(a2);
                }
            }
        }
    }

    /* compiled from: ActivityWeightBindingImpl */
    class b implements h {
        b() {
        }

        public void a() {
            String a2 = c.a(l0.this.B);
            WeightViewModel weightViewModel = l0.this.Y;
            boolean z = true;
            if (weightViewModel != null) {
                com.chileaf.fitness.model.a.a<String> h2 = weightViewModel.h();
                if (h2 == null) {
                    z = false;
                }
                if (z) {
                    h2.setValue(a2);
                }
            }
        }
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        f0 = sparseIntArray;
        sparseIntArray.put(R$id.tv_age, 24);
        f0.put(R$id.tv_height, 25);
        f0.put(R$id.btn_sync, 26);
        f0.put(R$id.tv_sex, 27);
        f0.put(R$id.rg_sex, 28);
        f0.put(R$id.tv_unit, 29);
        f0.put(R$id.rg_unit, 30);
        f0.put(R$id.ll_weight, 31);
        f0.put(R$id.ll_fat_rate, 32);
        f0.put(R$id.ll_wet, 33);
        f0.put(R$id.ll_metabolism, 34);
        f0.put(R$id.ll_viscera, 35);
        f0.put(R$id.ll_body, 36);
        f0.put(R$id.ll_subcutaneous, 37);
    }

    public l0(f fVar, View view) {
        this(fVar, view, ViewDataBinding.a(fVar, view, 38, e0, f0));
    }

    private boolean b(com.chileaf.fitness.model.a.a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.d0 |= PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID;
        }
        return true;
    }

    private boolean c(com.chileaf.fitness.model.a.a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.d0 |= 32;
        }
        return true;
    }

    private boolean f(com.chileaf.fitness.model.a.a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.d0 |= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        }
        return true;
    }

    private boolean g(com.chileaf.fitness.model.a.a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.d0 |= PlaybackStateCompat.ACTION_PREPARE;
        }
        return true;
    }

    private boolean h(com.chileaf.fitness.model.a.a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.d0 |= PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH;
        }
        return true;
    }

    private boolean i(com.chileaf.fitness.model.a.a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.d0 |= 4;
        }
        return true;
    }

    private boolean j(com.chileaf.fitness.model.a.a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.d0 |= 16;
        }
        return true;
    }

    private boolean k(com.chileaf.fitness.model.a.a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.d0 |= 2;
        }
        return true;
    }

    private boolean l(com.chileaf.fitness.model.a.a<Integer> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.d0 |= PlaybackStateCompat.ACTION_PLAY_FROM_URI;
        }
        return true;
    }

    private boolean m(com.chileaf.fitness.model.a.a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.d0 |= 1;
        }
        return true;
    }

    private boolean n(com.chileaf.fitness.model.a.a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.d0 |= PlaybackStateCompat.ACTION_PREPARE_FROM_URI;
        }
        return true;
    }

    private boolean o(com.chileaf.fitness.model.a.a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.d0 |= 64;
        }
        return true;
    }

    private boolean p(com.chileaf.fitness.model.a.a<Integer> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.d0 |= 256;
        }
        return true;
    }

    private boolean q(com.chileaf.fitness.model.a.a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.d0 |= PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        }
        return true;
    }

    private boolean r(com.chileaf.fitness.model.a.a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.d0 |= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
        }
        return true;
    }

    public void a(WeightViewModel weightViewModel) {
        this.Y = weightViewModel;
        synchronized (this) {
            this.d0 |= PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
        }
        notifyPropertyChanged(1);
        super.f();
    }

    public boolean d() {
        synchronized (this) {
            if (this.d0 != 0) {
                return true;
            }
            return false;
        }
    }

    public void e() {
        synchronized (this) {
            this.d0 = PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED;
        }
        f();
    }

    private l0(f fVar, View view, Object[] objArr) {
        super(fVar, view, 18, objArr[26], objArr[2], objArr[3], objArr[36], objArr[32], objArr[34], objArr[37], objArr[35], objArr[31], objArr[33], objArr[5], objArr[9], objArr[6], objArr[7], objArr[4], objArr[8], objArr[28], objArr[30], objArr[24], objArr[21], objArr[18], objArr[12], objArr[13], objArr[25], objArr[17], objArr[16], objArr[20], objArr[27], objArr[23], objArr[22], objArr[10], objArr[29], objArr[19], objArr[11], objArr[14], objArr[15]);
        this.b0 = new a();
        this.c0 = new b();
        this.d0 = -1;
        this.A.setTag((Object) null);
        this.B.setTag((Object) null);
        CoordinatorLayout coordinatorLayout = objArr[0];
        this.Z = coordinatorLayout;
        coordinatorLayout.setTag((Object) null);
        ConstraintLayout constraintLayout = objArr[1];
        this.a0 = constraintLayout;
        constraintLayout.setTag((Object) null);
        this.C.setTag((Object) null);
        this.D.setTag((Object) null);
        this.E.setTag((Object) null);
        this.F.setTag((Object) null);
        this.G.setTag((Object) null);
        this.H.setTag((Object) null);
        this.K.setTag((Object) null);
        this.L.setTag((Object) null);
        this.M.setTag((Object) null);
        this.N.setTag((Object) null);
        this.O.setTag((Object) null);
        this.P.setTag((Object) null);
        this.Q.setTag((Object) null);
        this.R.setTag((Object) null);
        this.S.setTag((Object) null);
        this.T.setTag((Object) null);
        this.U.setTag((Object) null);
        this.V.setTag((Object) null);
        this.W.setTag((Object) null);
        this.X.setTag((Object) null);
        a(view);
        e();
    }

    private boolean d(com.chileaf.fitness.model.a.a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.d0 |= 8;
        }
        return true;
    }

    private boolean e(com.chileaf.fitness.model.a.a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.d0 |= 512;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean a(int i2, Object obj, int i3) {
        switch (i2) {
            case 0:
                return m((com.chileaf.fitness.model.a.a) obj, i3);
            case 1:
                return k((com.chileaf.fitness.model.a.a) obj, i3);
            case 2:
                return i((com.chileaf.fitness.model.a.a) obj, i3);
            case 3:
                return d((com.chileaf.fitness.model.a.a) obj, i3);
            case 4:
                return j((com.chileaf.fitness.model.a.a) obj, i3);
            case 5:
                return c((com.chileaf.fitness.model.a.a) obj, i3);
            case 6:
                return o((com.chileaf.fitness.model.a.a) obj, i3);
            case 7:
                return a((com.chileaf.fitness.model.a.a) obj, i3);
            case 8:
                return p((com.chileaf.fitness.model.a.a) obj, i3);
            case 9:
                return e((com.chileaf.fitness.model.a.a) obj, i3);
            case 10:
                return f((com.chileaf.fitness.model.a.a) obj, i3);
            case 11:
                return r((com.chileaf.fitness.model.a.a) obj, i3);
            case 12:
                return q((com.chileaf.fitness.model.a.a) obj, i3);
            case 13:
                return l((com.chileaf.fitness.model.a.a) obj, i3);
            case 14:
                return g((com.chileaf.fitness.model.a.a) obj, i3);
            case 15:
                return b((com.chileaf.fitness.model.a.a) obj, i3);
            case 16:
                return h((com.chileaf.fitness.model.a.a) obj, i3);
            case 17:
                return n((com.chileaf.fitness.model.a.a) obj, i3);
            default:
                return false;
        }
    }

    private boolean a(com.chileaf.fitness.model.a.a<String> aVar, int i2) {
        if (i2 != 0) {
            return false;
        }
        synchronized (this) {
            this.d0 |= 128;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x0160  */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x016f  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x0191  */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x019e  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x01b8  */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x01c6  */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x01e0  */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x01ee  */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x0208  */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x0213  */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x0246  */
    /* JADX WARNING: Removed duplicated region for block: B:165:0x0256  */
    /* JADX WARNING: Removed duplicated region for block: B:174:0x0275  */
    /* JADX WARNING: Removed duplicated region for block: B:180:0x028f  */
    /* JADX WARNING: Removed duplicated region for block: B:184:0x029d  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:190:0x02b7  */
    /* JADX WARNING: Removed duplicated region for block: B:194:0x02c5  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00b5  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00d2  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00f0  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x010c  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0128  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a() {
        /*
            r63 = this;
            r1 = r63
            monitor-enter(r63)
            long r2 = r1.d0     // Catch:{ all -> 0x0469 }
            r4 = 0
            r1.d0 = r4     // Catch:{ all -> 0x0469 }
            monitor-exit(r63)     // Catch:{ all -> 0x0469 }
            com.chileaf.fitness.viewmodel.WeightViewModel r0 = r1.Y
            r6 = 1048575(0xfffff, double:5.18065E-318)
            long r6 = r6 & r2
            r14 = 786496(0xc0040, double:3.885807E-318)
            r16 = 786464(0xc0020, double:3.88565E-318)
            r18 = 819200(0xc8000, double:4.047386E-318)
            r20 = 786448(0xc0010, double:3.88557E-318)
            r22 = 786688(0xc0100, double:3.886755E-318)
            r24 = 786440(0xc0008, double:3.88553E-318)
            r26 = 794624(0xc2000, double:3.925964E-318)
            r28 = 786436(0xc0004, double:3.88551E-318)
            r30 = 787456(0xc0400, double:3.89055E-318)
            r32 = 786434(0xc0002, double:3.8855E-318)
            r34 = 786433(0xc0001, double:3.885495E-318)
            r36 = 786560(0xc0080, double:3.886123E-318)
            r10 = 0
            int r40 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r40 == 0) goto L_0x0328
            long r6 = r2 & r34
            int r40 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r40 == 0) goto L_0x0053
            if (r0 == 0) goto L_0x0046
            com.chileaf.fitness.model.a.a r6 = r0.p()
            goto L_0x0047
        L_0x0046:
            r6 = 0
        L_0x0047:
            r1.a((int) r10, (androidx.lifecycle.LiveData<?>) r6)
            if (r6 == 0) goto L_0x0053
            java.lang.Object r6 = r6.getValue()
            java.lang.String r6 = (java.lang.String) r6
            goto L_0x0054
        L_0x0053:
            r6 = 0
        L_0x0054:
            long r40 = r2 & r32
            r7 = 1
            int r42 = (r40 > r4 ? 1 : (r40 == r4 ? 0 : -1))
            if (r42 == 0) goto L_0x0071
            if (r0 == 0) goto L_0x0064
            com.chileaf.fitness.model.a.a r40 = r0.n()
            r10 = r40
            goto L_0x0065
        L_0x0064:
            r10 = 0
        L_0x0065:
            r1.a((int) r7, (androidx.lifecycle.LiveData<?>) r10)
            if (r10 == 0) goto L_0x0071
            java.lang.Object r10 = r10.getValue()
            java.lang.String r10 = (java.lang.String) r10
            goto L_0x0072
        L_0x0071:
            r10 = 0
        L_0x0072:
            long r41 = r2 & r28
            r11 = 2
            int r43 = (r41 > r4 ? 1 : (r41 == r4 ? 0 : -1))
            if (r43 == 0) goto L_0x008f
            if (r0 == 0) goto L_0x0082
            com.chileaf.fitness.model.a.a r41 = r0.l()
            r8 = r41
            goto L_0x0083
        L_0x0082:
            r8 = 0
        L_0x0083:
            r1.a((int) r11, (androidx.lifecycle.LiveData<?>) r8)
            if (r8 == 0) goto L_0x008f
            java.lang.Object r8 = r8.getValue()
            java.lang.String r8 = (java.lang.String) r8
            goto L_0x0090
        L_0x008f:
            r8 = 0
        L_0x0090:
            long r43 = r2 & r24
            r9 = 3
            int r45 = (r43 > r4 ? 1 : (r43 == r4 ? 0 : -1))
            if (r45 == 0) goto L_0x00ad
            if (r0 == 0) goto L_0x00a0
            com.chileaf.fitness.model.a.a r43 = r0.f()
            r12 = r43
            goto L_0x00a1
        L_0x00a0:
            r12 = 0
        L_0x00a1:
            r1.a((int) r9, (androidx.lifecycle.LiveData<?>) r12)
            if (r12 == 0) goto L_0x00ad
            java.lang.Object r12 = r12.getValue()
            java.lang.String r12 = (java.lang.String) r12
            goto L_0x00ae
        L_0x00ad:
            r12 = 0
        L_0x00ae:
            long r45 = r2 & r20
            r13 = 4
            int r47 = (r45 > r4 ? 1 : (r45 == r4 ? 0 : -1))
            if (r47 == 0) goto L_0x00cb
            if (r0 == 0) goto L_0x00be
            com.chileaf.fitness.model.a.a r45 = r0.m()
            r7 = r45
            goto L_0x00bf
        L_0x00be:
            r7 = 0
        L_0x00bf:
            r1.a((int) r13, (androidx.lifecycle.LiveData<?>) r7)
            if (r7 == 0) goto L_0x00cb
            java.lang.Object r7 = r7.getValue()
            java.lang.String r7 = (java.lang.String) r7
            goto L_0x00cc
        L_0x00cb:
            r7 = 0
        L_0x00cc:
            long r46 = r2 & r16
            int r48 = (r46 > r4 ? 1 : (r46 == r4 ? 0 : -1))
            if (r48 == 0) goto L_0x00e9
            if (r0 == 0) goto L_0x00db
            com.chileaf.fitness.model.a.a r46 = r0.e()
            r13 = r46
            goto L_0x00dc
        L_0x00db:
            r13 = 0
        L_0x00dc:
            r9 = 5
            r1.a((int) r9, (androidx.lifecycle.LiveData<?>) r13)
            if (r13 == 0) goto L_0x00e9
            java.lang.Object r9 = r13.getValue()
            java.lang.String r9 = (java.lang.String) r9
            goto L_0x00ea
        L_0x00e9:
            r9 = 0
        L_0x00ea:
            long r48 = r2 & r14
            int r13 = (r48 > r4 ? 1 : (r48 == r4 ? 0 : -1))
            if (r13 == 0) goto L_0x0105
            if (r0 == 0) goto L_0x00f7
            com.chileaf.fitness.model.a.a r13 = r0.r()
            goto L_0x00f8
        L_0x00f7:
            r13 = 0
        L_0x00f8:
            r14 = 6
            r1.a((int) r14, (androidx.lifecycle.LiveData<?>) r13)
            if (r13 == 0) goto L_0x0105
            java.lang.Object r13 = r13.getValue()
            java.lang.String r13 = (java.lang.String) r13
            goto L_0x0106
        L_0x0105:
            r13 = 0
        L_0x0106:
            long r14 = r2 & r36
            int r50 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
            if (r50 == 0) goto L_0x0121
            if (r0 == 0) goto L_0x0113
            com.chileaf.fitness.model.a.a r14 = r0.c()
            goto L_0x0114
        L_0x0113:
            r14 = 0
        L_0x0114:
            r15 = 7
            r1.a((int) r15, (androidx.lifecycle.LiveData<?>) r14)
            if (r14 == 0) goto L_0x0121
            java.lang.Object r14 = r14.getValue()
            java.lang.String r14 = (java.lang.String) r14
            goto L_0x0122
        L_0x0121:
            r14 = 0
        L_0x0122:
            long r50 = r2 & r22
            int r15 = (r50 > r4 ? 1 : (r50 == r4 ? 0 : -1))
            if (r15 == 0) goto L_0x0160
            if (r0 == 0) goto L_0x012f
            com.chileaf.fitness.model.a.a r15 = r0.s()
            goto L_0x0130
        L_0x012f:
            r15 = 0
        L_0x0130:
            r4 = 8
            r1.a((int) r4, (androidx.lifecycle.LiveData<?>) r15)
            if (r15 == 0) goto L_0x013e
            java.lang.Object r4 = r15.getValue()
            java.lang.Integer r4 = (java.lang.Integer) r4
            goto L_0x013f
        L_0x013e:
            r4 = 0
        L_0x013f:
            int r4 = androidx.databinding.ViewDataBinding.a((java.lang.Integer) r4)
            if (r4 != r11) goto L_0x0147
            r5 = 1
            goto L_0x0148
        L_0x0147:
            r5 = 0
        L_0x0148:
            r15 = 3
            r11 = 4
            if (r4 != r15) goto L_0x014e
            r15 = 1
            goto L_0x014f
        L_0x014e:
            r15 = 0
        L_0x014f:
            r47 = r5
            r5 = 1
            if (r4 != r11) goto L_0x0156
            r11 = 1
            goto L_0x0157
        L_0x0156:
            r11 = 0
        L_0x0157:
            if (r4 != r5) goto L_0x015b
            r5 = 1
            goto L_0x015c
        L_0x015b:
            r5 = 0
        L_0x015c:
            r4 = r5
            r5 = r47
            goto L_0x0164
        L_0x0160:
            r4 = 0
            r5 = 0
            r11 = 0
            r15 = 0
        L_0x0164:
            r43 = 786944(0xc0200, double:3.88802E-318)
            long r52 = r2 & r43
            r50 = 0
            int r47 = (r52 > r50 ? 1 : (r52 == r50 ? 0 : -1))
            if (r47 == 0) goto L_0x0191
            if (r0 == 0) goto L_0x017e
            com.chileaf.fitness.model.a.a r47 = r0.g()
            r52 = r5
            r62 = r47
            r47 = r4
            r4 = r62
            goto L_0x0183
        L_0x017e:
            r47 = r4
            r52 = r5
            r4 = 0
        L_0x0183:
            r5 = 9
            r1.a((int) r5, (androidx.lifecycle.LiveData<?>) r4)
            if (r4 == 0) goto L_0x0195
            java.lang.Object r4 = r4.getValue()
            java.lang.String r4 = (java.lang.String) r4
            goto L_0x0196
        L_0x0191:
            r47 = r4
            r52 = r5
        L_0x0195:
            r4 = 0
        L_0x0196:
            long r53 = r2 & r30
            r50 = 0
            int r5 = (r53 > r50 ? 1 : (r53 == r50 ? 0 : -1))
            if (r5 == 0) goto L_0x01b8
            if (r0 == 0) goto L_0x01a7
            com.chileaf.fitness.model.a.a r5 = r0.h()
            r53 = r4
            goto L_0x01aa
        L_0x01a7:
            r53 = r4
            r5 = 0
        L_0x01aa:
            r4 = 10
            r1.a((int) r4, (androidx.lifecycle.LiveData<?>) r5)
            if (r5 == 0) goto L_0x01ba
            java.lang.Object r4 = r5.getValue()
            java.lang.String r4 = (java.lang.String) r4
            goto L_0x01bb
        L_0x01b8:
            r53 = r4
        L_0x01ba:
            r4 = 0
        L_0x01bb:
            r41 = 788480(0xc0800, double:3.89561E-318)
            long r54 = r2 & r41
            r50 = 0
            int r5 = (r54 > r50 ? 1 : (r54 == r50 ? 0 : -1))
            if (r5 == 0) goto L_0x01e0
            if (r0 == 0) goto L_0x01cf
            com.chileaf.fitness.model.a.a r5 = r0.u()
            r54 = r4
            goto L_0x01d2
        L_0x01cf:
            r54 = r4
            r5 = 0
        L_0x01d2:
            r4 = 11
            r1.a((int) r4, (androidx.lifecycle.LiveData<?>) r5)
            if (r5 == 0) goto L_0x01e2
            java.lang.Object r4 = r5.getValue()
            java.lang.String r4 = (java.lang.String) r4
            goto L_0x01e3
        L_0x01e0:
            r54 = r4
        L_0x01e2:
            r4 = 0
        L_0x01e3:
            r55 = 790528(0xc1000, double:3.905727E-318)
            long r55 = r2 & r55
            r50 = 0
            int r5 = (r55 > r50 ? 1 : (r55 == r50 ? 0 : -1))
            if (r5 == 0) goto L_0x0208
            if (r0 == 0) goto L_0x01f7
            com.chileaf.fitness.model.a.a r5 = r0.t()
            r55 = r4
            goto L_0x01fa
        L_0x01f7:
            r55 = r4
            r5 = 0
        L_0x01fa:
            r4 = 12
            r1.a((int) r4, (androidx.lifecycle.LiveData<?>) r5)
            if (r5 == 0) goto L_0x020a
            java.lang.Object r4 = r5.getValue()
            java.lang.String r4 = (java.lang.String) r4
            goto L_0x020b
        L_0x0208:
            r55 = r4
        L_0x020a:
            r4 = 0
        L_0x020b:
            long r56 = r2 & r26
            r50 = 0
            int r5 = (r56 > r50 ? 1 : (r56 == r50 ? 0 : -1))
            if (r5 == 0) goto L_0x0246
            if (r0 == 0) goto L_0x021c
            com.chileaf.fitness.model.a.a r5 = r0.o()
            r56 = r4
            goto L_0x021f
        L_0x021c:
            r56 = r4
            r5 = 0
        L_0x021f:
            r4 = 13
            r1.a((int) r4, (androidx.lifecycle.LiveData<?>) r5)
            if (r5 == 0) goto L_0x022d
            java.lang.Object r4 = r5.getValue()
            java.lang.Integer r4 = (java.lang.Integer) r4
            goto L_0x022e
        L_0x022d:
            r4 = 0
        L_0x022e:
            int r4 = androidx.databinding.ViewDataBinding.a((java.lang.Integer) r4)
            r5 = 1
            if (r4 != r5) goto L_0x0239
            r5 = 2
            r45 = 1
            goto L_0x023c
        L_0x0239:
            r5 = 2
            r45 = 0
        L_0x023c:
            if (r4 != r5) goto L_0x0241
            r46 = 1
            goto L_0x0243
        L_0x0241:
            r46 = 0
        L_0x0243:
            r40 = r45
            goto L_0x024c
        L_0x0246:
            r56 = r4
            r40 = 0
            r46 = 0
        L_0x024c:
            r4 = 802816(0xc4000, double:3.96644E-318)
            long r4 = r4 & r2
            r50 = 0
            int r45 = (r4 > r50 ? 1 : (r4 == r50 ? 0 : -1))
            if (r45 == 0) goto L_0x026c
            if (r0 == 0) goto L_0x025d
            com.chileaf.fitness.model.a.a r4 = r0.i()
            goto L_0x025e
        L_0x025d:
            r4 = 0
        L_0x025e:
            r5 = 14
            r1.a((int) r5, (androidx.lifecycle.LiveData<?>) r4)
            if (r4 == 0) goto L_0x026c
            java.lang.Object r4 = r4.getValue()
            java.lang.String r4 = (java.lang.String) r4
            goto L_0x026d
        L_0x026c:
            r4 = 0
        L_0x026d:
            long r57 = r2 & r18
            r50 = 0
            int r5 = (r57 > r50 ? 1 : (r57 == r50 ? 0 : -1))
            if (r5 == 0) goto L_0x028f
            if (r0 == 0) goto L_0x027e
            com.chileaf.fitness.model.a.a r5 = r0.d()
            r45 = r4
            goto L_0x0281
        L_0x027e:
            r45 = r4
            r5 = 0
        L_0x0281:
            r4 = 15
            r1.a((int) r4, (androidx.lifecycle.LiveData<?>) r5)
            if (r5 == 0) goto L_0x0291
            java.lang.Object r4 = r5.getValue()
            java.lang.String r4 = (java.lang.String) r4
            goto L_0x0292
        L_0x028f:
            r45 = r4
        L_0x0291:
            r4 = 0
        L_0x0292:
            r38 = 851968(0xd0000, double:4.20928E-318)
            long r57 = r2 & r38
            r50 = 0
            int r5 = (r57 > r50 ? 1 : (r57 == r50 ? 0 : -1))
            if (r5 == 0) goto L_0x02b7
            if (r0 == 0) goto L_0x02a6
            com.chileaf.fitness.model.a.a r5 = r0.k()
            r57 = r4
            goto L_0x02a9
        L_0x02a6:
            r57 = r4
            r5 = 0
        L_0x02a9:
            r4 = 16
            r1.a((int) r4, (androidx.lifecycle.LiveData<?>) r5)
            if (r5 == 0) goto L_0x02b9
            java.lang.Object r4 = r5.getValue()
            java.lang.String r4 = (java.lang.String) r4
            goto L_0x02ba
        L_0x02b7:
            r57 = r4
        L_0x02b9:
            r4 = 0
        L_0x02ba:
            r58 = 917504(0xe0000, double:4.53307E-318)
            long r58 = r2 & r58
            r50 = 0
            int r5 = (r58 > r50 ? 1 : (r58 == r50 ? 0 : -1))
            if (r5 == 0) goto L_0x0302
            if (r0 == 0) goto L_0x02cc
            com.chileaf.fitness.model.a.a r0 = r0.q()
            goto L_0x02cd
        L_0x02cc:
            r0 = 0
        L_0x02cd:
            r5 = 17
            r1.a((int) r5, (androidx.lifecycle.LiveData<?>) r0)
            if (r0 == 0) goto L_0x0302
            java.lang.Object r0 = r0.getValue()
            java.lang.String r0 = (java.lang.String) r0
            r60 = r10
            r61 = r13
            r13 = r14
            r10 = r40
            r40 = r45
            r45 = r55
            r5 = r57
            r14 = r9
            r9 = r11
            r11 = r15
            r15 = r46
            r46 = r56
            r62 = r47
            r47 = r0
            r0 = r52
            r52 = r6
            r6 = r53
            r53 = r7
            r7 = r4
            r4 = r54
            r54 = r8
            r8 = r62
            goto L_0x0347
        L_0x0302:
            r60 = r10
            r61 = r13
            r13 = r14
            r10 = r40
            r40 = r45
            r0 = r52
            r45 = r55
            r5 = r57
            r52 = r6
            r14 = r9
            r9 = r11
            r11 = r15
            r15 = r46
            r6 = r53
            r46 = r56
            r53 = r7
            r7 = r4
            r4 = r54
            r54 = r8
            r8 = r47
            r47 = 0
            goto L_0x0347
        L_0x0328:
            r0 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
            r40 = 0
            r45 = 0
            r46 = 0
            r47 = 0
            r52 = 0
            r53 = 0
            r54 = 0
            r60 = 0
            r61 = 0
        L_0x0347:
            long r36 = r2 & r36
            r50 = 0
            int r55 = (r36 > r50 ? 1 : (r36 == r50 ? 0 : -1))
            r36 = r7
            if (r55 == 0) goto L_0x0356
            androidx.appcompat.widget.AppCompatEditText r7 = r1.A
            androidx.databinding.o.c.a((android.widget.TextView) r7, (java.lang.CharSequence) r13)
        L_0x0356:
            r55 = 524288(0x80000, double:2.590327E-318)
            long r55 = r2 & r55
            int r7 = (r55 > r50 ? 1 : (r55 == r50 ? 0 : -1))
            if (r7 == 0) goto L_0x0371
            androidx.appcompat.widget.AppCompatEditText r7 = r1.A
            androidx.databinding.h r13 = r1.b0
            r37 = r6
            r6 = 0
            androidx.databinding.o.c.a(r7, r6, r6, r6, r13)
            androidx.appcompat.widget.AppCompatEditText r7 = r1.B
            androidx.databinding.h r13 = r1.c0
            androidx.databinding.o.c.a(r7, r6, r6, r6, r13)
            goto L_0x0373
        L_0x0371:
            r37 = r6
        L_0x0373:
            long r6 = r2 & r30
            int r13 = (r6 > r50 ? 1 : (r6 == r50 ? 0 : -1))
            if (r13 == 0) goto L_0x037e
            androidx.appcompat.widget.AppCompatEditText r6 = r1.B
            androidx.databinding.o.c.a((android.widget.TextView) r6, (java.lang.CharSequence) r4)
        L_0x037e:
            long r6 = r2 & r26
            int r4 = (r6 > r50 ? 1 : (r6 == r50 ? 0 : -1))
            if (r4 == 0) goto L_0x038e
            androidx.appcompat.widget.AppCompatRadioButton r4 = r1.C
            androidx.databinding.o.a.a(r4, r15)
            androidx.appcompat.widget.AppCompatRadioButton r4 = r1.G
            androidx.databinding.o.a.a(r4, r10)
        L_0x038e:
            long r6 = r2 & r22
            int r4 = (r6 > r50 ? 1 : (r6 == r50 ? 0 : -1))
            if (r4 == 0) goto L_0x03a8
            androidx.appcompat.widget.AppCompatRadioButton r4 = r1.D
            androidx.databinding.o.a.a(r4, r9)
            androidx.appcompat.widget.AppCompatRadioButton r4 = r1.E
            androidx.databinding.o.a.a(r4, r8)
            androidx.appcompat.widget.AppCompatRadioButton r4 = r1.F
            androidx.databinding.o.a.a(r4, r0)
            androidx.appcompat.widget.AppCompatRadioButton r0 = r1.H
            androidx.databinding.o.a.a(r0, r11)
        L_0x03a8:
            long r6 = r2 & r18
            r8 = 0
            int r0 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r0 == 0) goto L_0x03b5
            androidx.appcompat.widget.AppCompatTextView r0 = r1.K
            androidx.databinding.o.c.a((android.widget.TextView) r0, (java.lang.CharSequence) r5)
        L_0x03b5:
            long r4 = r2 & r16
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 == 0) goto L_0x03c0
            androidx.appcompat.widget.AppCompatTextView r0 = r1.L
            androidx.databinding.o.c.a((android.widget.TextView) r0, (java.lang.CharSequence) r14)
        L_0x03c0:
            long r4 = r2 & r24
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 == 0) goto L_0x03cb
            androidx.appcompat.widget.AppCompatTextView r0 = r1.M
            androidx.databinding.o.c.a((android.widget.TextView) r0, (java.lang.CharSequence) r12)
        L_0x03cb:
            r4 = 786944(0xc0200, double:3.88802E-318)
            long r4 = r4 & r2
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 == 0) goto L_0x03da
            androidx.appcompat.widget.AppCompatTextView r0 = r1.N
            r4 = r37
            androidx.databinding.o.c.a((android.widget.TextView) r0, (java.lang.CharSequence) r4)
        L_0x03da:
            r4 = 851968(0xd0000, double:4.20928E-318)
            long r4 = r4 & r2
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 == 0) goto L_0x03e9
            androidx.appcompat.widget.AppCompatTextView r0 = r1.O
            r4 = r36
            androidx.databinding.o.c.a((android.widget.TextView) r0, (java.lang.CharSequence) r4)
        L_0x03e9:
            long r4 = r2 & r28
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 == 0) goto L_0x03f6
            androidx.appcompat.widget.AppCompatTextView r0 = r1.P
            r4 = r54
            androidx.databinding.o.c.a((android.widget.TextView) r0, (java.lang.CharSequence) r4)
        L_0x03f6:
            long r4 = r2 & r20
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 == 0) goto L_0x0403
            androidx.appcompat.widget.AppCompatTextView r0 = r1.Q
            r7 = r53
            androidx.databinding.o.c.a((android.widget.TextView) r0, (java.lang.CharSequence) r7)
        L_0x0403:
            long r4 = r2 & r34
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 == 0) goto L_0x0410
            androidx.appcompat.widget.AppCompatTextView r0 = r1.R
            r6 = r52
            androidx.databinding.o.c.a((android.widget.TextView) r0, (java.lang.CharSequence) r6)
        L_0x0410:
            r4 = 917504(0xe0000, double:4.53307E-318)
            long r4 = r4 & r2
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 == 0) goto L_0x041f
            androidx.appcompat.widget.AppCompatTextView r0 = r1.S
            r4 = r47
            androidx.databinding.o.c.a((android.widget.TextView) r0, (java.lang.CharSequence) r4)
        L_0x041f:
            r4 = 786496(0xc0040, double:3.885807E-318)
            long r4 = r4 & r2
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 == 0) goto L_0x042e
            androidx.appcompat.widget.AppCompatTextView r0 = r1.T
            r13 = r61
            androidx.databinding.o.c.a((android.widget.TextView) r0, (java.lang.CharSequence) r13)
        L_0x042e:
            r4 = 790528(0xc1000, double:3.905727E-318)
            long r4 = r4 & r2
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 == 0) goto L_0x043d
            androidx.appcompat.widget.AppCompatTextView r0 = r1.U
            r4 = r46
            androidx.databinding.o.c.a((android.widget.TextView) r0, (java.lang.CharSequence) r4)
        L_0x043d:
            r4 = 788480(0xc0800, double:3.89561E-318)
            long r4 = r4 & r2
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 == 0) goto L_0x044c
            androidx.appcompat.widget.AppCompatTextView r0 = r1.V
            r4 = r45
            androidx.databinding.o.c.a((android.widget.TextView) r0, (java.lang.CharSequence) r4)
        L_0x044c:
            long r4 = r2 & r32
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 == 0) goto L_0x0459
            androidx.appcompat.widget.AppCompatTextView r0 = r1.W
            r10 = r60
            androidx.databinding.o.c.a((android.widget.TextView) r0, (java.lang.CharSequence) r10)
        L_0x0459:
            r4 = 802816(0xc4000, double:3.96644E-318)
            long r2 = r2 & r4
            int r0 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r0 == 0) goto L_0x0468
            androidx.appcompat.widget.AppCompatTextView r0 = r1.X
            r2 = r40
            androidx.databinding.o.c.a((android.widget.TextView) r0, (java.lang.CharSequence) r2)
        L_0x0468:
            return
        L_0x0469:
            r0 = move-exception
            monitor-exit(r63)     // Catch:{ all -> 0x0469 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.chileaf.fitness.b.l0.a():void");
    }
}
