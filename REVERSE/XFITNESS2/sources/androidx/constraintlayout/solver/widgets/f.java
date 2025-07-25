package androidx.constraintlayout.solver.widgets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: ConstraintWidgetGroup */
public class f {
    public List<ConstraintWidget> a;
    int b = -1;
    int c = -1;
    public boolean d = false;
    public final int[] e = {-1, -1};

    /* renamed from: f  reason: collision with root package name */
    List<ConstraintWidget> f389f = new ArrayList();

    /* renamed from: g  reason: collision with root package name */
    List<ConstraintWidget> f390g = new ArrayList();

    /* renamed from: h  reason: collision with root package name */
    HashSet<ConstraintWidget> f391h = new HashSet<>();

    /* renamed from: i  reason: collision with root package name */
    HashSet<ConstraintWidget> f392i = new HashSet<>();

    /* renamed from: j  reason: collision with root package name */
    List<ConstraintWidget> f393j = new ArrayList();
    List<ConstraintWidget> k = new ArrayList();

    f(List<ConstraintWidget> list) {
        this.a = list;
    }

    public List<ConstraintWidget> a(int i2) {
        if (i2 == 0) {
            return this.f389f;
        }
        if (i2 == 1) {
            return this.f390g;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public Set<ConstraintWidget> b(int i2) {
        if (i2 == 0) {
            return this.f391h;
        }
        if (i2 == 1) {
            return this.f392i;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void a(ConstraintWidget constraintWidget, int i2) {
        if (i2 == 0) {
            this.f391h.add(constraintWidget);
        } else if (i2 == 1) {
            this.f392i.add(constraintWidget);
        }
    }

    /* access modifiers changed from: package-private */
    public void b() {
        int size = this.k.size();
        for (int i2 = 0; i2 < size; i2++) {
            a(this.k.get(i2));
        }
    }

    /* access modifiers changed from: package-private */
    public List<ConstraintWidget> a() {
        if (!this.f393j.isEmpty()) {
            return this.f393j;
        }
        int size = this.a.size();
        for (int i2 = 0; i2 < size; i2++) {
            ConstraintWidget constraintWidget = this.a.get(i2);
            if (!constraintWidget.b0) {
                a((ArrayList<ConstraintWidget>) (ArrayList) this.f393j, constraintWidget);
            }
        }
        this.k.clear();
        this.k.addAll(this.a);
        this.k.removeAll(this.f393j);
        return this.f393j;
    }

    f(List<ConstraintWidget> list, boolean z) {
        this.a = list;
        this.d = z;
    }

    private void a(ArrayList<ConstraintWidget> arrayList, ConstraintWidget constraintWidget) {
        if (!constraintWidget.d0) {
            arrayList.add(constraintWidget);
            constraintWidget.d0 = true;
            if (!constraintWidget.y()) {
                if (constraintWidget instanceof h) {
                    h hVar = (h) constraintWidget;
                    int i2 = hVar.l0;
                    for (int i3 = 0; i3 < i2; i3++) {
                        a(arrayList, hVar.k0[i3]);
                    }
                }
                for (ConstraintAnchor constraintAnchor : constraintWidget.A) {
                    ConstraintAnchor constraintAnchor2 = constraintAnchor.d;
                    if (constraintAnchor2 != null) {
                        ConstraintWidget constraintWidget2 = constraintAnchor2.b;
                        if (!(constraintAnchor2 == null || constraintWidget2 == constraintWidget.k())) {
                            a(arrayList, constraintWidget2);
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0087  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(androidx.constraintlayout.solver.widgets.ConstraintWidget r7) {
        /*
            r6 = this;
            boolean r0 = r7.b0
            if (r0 == 0) goto L_0x00de
            boolean r0 = r7.y()
            if (r0 == 0) goto L_0x000b
            return
        L_0x000b:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r7.u
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.d
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x0015
            r0 = 1
            goto L_0x0016
        L_0x0015:
            r0 = 0
        L_0x0016:
            if (r0 == 0) goto L_0x001d
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r7.u
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.d
            goto L_0x0021
        L_0x001d:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r7.s
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.d
        L_0x0021:
            if (r3 == 0) goto L_0x0045
            androidx.constraintlayout.solver.widgets.ConstraintWidget r4 = r3.b
            boolean r5 = r4.c0
            if (r5 != 0) goto L_0x002c
            r6.a((androidx.constraintlayout.solver.widgets.ConstraintWidget) r4)
        L_0x002c:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r4 = r3.c
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r5 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT
            if (r4 != r5) goto L_0x003c
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r3.b
            int r4 = r3.I
            int r3 = r3.s()
            int r4 = r4 + r3
            goto L_0x0046
        L_0x003c:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r5 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.LEFT
            if (r4 != r5) goto L_0x0045
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r3.b
            int r4 = r3.I
            goto L_0x0046
        L_0x0045:
            r4 = 0
        L_0x0046:
            if (r0 == 0) goto L_0x0050
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r7.u
            int r0 = r0.b()
            int r4 = r4 - r0
            goto L_0x005c
        L_0x0050:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r7.s
            int r0 = r0.b()
            int r3 = r7.s()
            int r0 = r0 + r3
            int r4 = r4 + r0
        L_0x005c:
            int r0 = r7.s()
            int r0 = r4 - r0
            r7.a(r0, r4)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r7.w
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.d
            if (r0 == 0) goto L_0x0087
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r0.b
            boolean r3 = r1.c0
            if (r3 != 0) goto L_0x0074
            r6.a((androidx.constraintlayout.solver.widgets.ConstraintWidget) r1)
        L_0x0074:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r0.b
            int r1 = r0.J
            int r0 = r0.Q
            int r1 = r1 + r0
            int r0 = r7.Q
            int r1 = r1 - r0
            int r0 = r7.F
            int r0 = r0 + r1
            r7.e(r1, r0)
            r7.c0 = r2
            return
        L_0x0087:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r7.v
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.d
            if (r0 == 0) goto L_0x008e
            r1 = 1
        L_0x008e:
            if (r1 == 0) goto L_0x0095
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r7.v
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.d
            goto L_0x0099
        L_0x0095:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r7.t
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.d
        L_0x0099:
            if (r0 == 0) goto L_0x00bd
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r0.b
            boolean r5 = r3.c0
            if (r5 != 0) goto L_0x00a4
            r6.a((androidx.constraintlayout.solver.widgets.ConstraintWidget) r3)
        L_0x00a4:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r3 = r0.c
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r5 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            if (r3 != r5) goto L_0x00b5
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r0.b
            int r3 = r0.J
            int r0 = r0.i()
            int r4 = r3 + r0
            goto L_0x00bd
        L_0x00b5:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r5 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP
            if (r3 != r5) goto L_0x00bd
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r0.b
            int r4 = r0.J
        L_0x00bd:
            if (r1 == 0) goto L_0x00c7
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r7.v
            int r0 = r0.b()
            int r4 = r4 - r0
            goto L_0x00d3
        L_0x00c7:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r7.t
            int r0 = r0.b()
            int r1 = r7.i()
            int r0 = r0 + r1
            int r4 = r4 + r0
        L_0x00d3:
            int r0 = r7.i()
            int r0 = r4 - r0
            r7.e(r0, r4)
            r7.c0 = r2
        L_0x00de:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.f.a(androidx.constraintlayout.solver.widgets.ConstraintWidget):void");
    }
}
