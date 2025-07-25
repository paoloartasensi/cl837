package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.solver.f;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.e;
import androidx.constraintlayout.solver.widgets.g;
import java.util.ArrayList;
import java.util.HashMap;

public class ConstraintLayout extends ViewGroup {
    SparseArray<View> e = new SparseArray<>();

    /* renamed from: f  reason: collision with root package name */
    private ArrayList<ConstraintHelper> f404f = new ArrayList<>(4);

    /* renamed from: g  reason: collision with root package name */
    private final ArrayList<ConstraintWidget> f405g = new ArrayList<>(100);

    /* renamed from: h  reason: collision with root package name */
    e f406h = new e();

    /* renamed from: i  reason: collision with root package name */
    private int f407i = 0;

    /* renamed from: j  reason: collision with root package name */
    private int f408j = 0;
    private int k = Integer.MAX_VALUE;
    private int l = Integer.MAX_VALUE;
    private boolean m = true;
    private int n = 7;
    private a o = null;
    private int p = -1;
    private HashMap<String, Integer> q = new HashMap<>();
    private int r = -1;
    private int s = -1;
    private f t;

    public ConstraintLayout(Context context) {
        super(context);
        a((AttributeSet) null);
    }

    private void b() {
        int childCount = getChildCount();
        boolean z = false;
        int i2 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            } else if (getChildAt(i2).isLayoutRequested()) {
                z = true;
                break;
            } else {
                i2++;
            }
        }
        if (z) {
            this.f405g.clear();
            a();
        }
    }

    private void c() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt instanceof Placeholder) {
                ((Placeholder) childAt).a(this);
            }
        }
        int size = this.f404f.size();
        if (size > 0) {
            for (int i3 = 0; i3 < size; i3++) {
                this.f404f.get(i3).b(this);
            }
        }
    }

    public void a(int i2, Object obj, Object obj2) {
        if (i2 == 0 && (obj instanceof String) && (obj2 instanceof Integer)) {
            if (this.q == null) {
                this.q = new HashMap<>();
            }
            String str = (String) obj;
            int indexOf = str.indexOf("/");
            if (indexOf != -1) {
                str = str.substring(indexOf + 1);
            }
            this.q.put(str, Integer.valueOf(((Integer) obj2).intValue()));
        }
    }

    public void addView(View view, int i2, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i2, layoutParams);
        if (Build.VERSION.SDK_INT < 14) {
            onViewAdded(view);
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof a;
    }

    public void dispatchDraw(Canvas canvas) {
        Object tag;
        super.dispatchDraw(canvas);
        if (isInEditMode()) {
            int childCount = getChildCount();
            float width = (float) getWidth();
            float height = (float) getHeight();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                if (!(childAt.getVisibility() == 8 || (tag = childAt.getTag()) == null || !(tag instanceof String))) {
                    String[] split = ((String) tag).split(",");
                    if (split.length == 4) {
                        int parseInt = Integer.parseInt(split[0]);
                        int parseInt2 = Integer.parseInt(split[1]);
                        int parseInt3 = Integer.parseInt(split[2]);
                        int i3 = (int) ((((float) parseInt) / 1080.0f) * width);
                        int i4 = (int) ((((float) parseInt2) / 1920.0f) * height);
                        Paint paint = new Paint();
                        paint.setColor(-65536);
                        float f2 = (float) i3;
                        float f3 = (float) (i3 + ((int) ((((float) parseInt3) / 1080.0f) * width)));
                        Canvas canvas2 = canvas;
                        float f4 = (float) i4;
                        float f5 = f2;
                        float f6 = f2;
                        float f7 = f4;
                        Paint paint2 = paint;
                        float f8 = f3;
                        Paint paint3 = paint2;
                        canvas2.drawLine(f5, f7, f8, f4, paint3);
                        float parseInt4 = (float) (i4 + ((int) ((((float) Integer.parseInt(split[3])) / 1920.0f) * height)));
                        float f9 = f3;
                        float f10 = parseInt4;
                        canvas2.drawLine(f9, f7, f8, f10, paint3);
                        float f11 = parseInt4;
                        float f12 = f6;
                        canvas2.drawLine(f9, f11, f12, f10, paint3);
                        float f13 = f6;
                        canvas2.drawLine(f13, f11, f12, f4, paint3);
                        Paint paint4 = paint2;
                        paint4.setColor(-16711936);
                        Paint paint5 = paint4;
                        float f14 = f3;
                        Paint paint6 = paint5;
                        canvas2.drawLine(f13, f4, f14, parseInt4, paint6);
                        canvas2.drawLine(f13, parseInt4, f14, f4, paint6);
                    }
                }
            }
        }
    }

    public int getMaxHeight() {
        return this.l;
    }

    public int getMaxWidth() {
        return this.k;
    }

    public int getMinHeight() {
        return this.f408j;
    }

    public int getMinWidth() {
        return this.f407i;
    }

    public int getOptimizationLevel() {
        return this.f406h.M();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        View content;
        int childCount = getChildCount();
        boolean isInEditMode = isInEditMode();
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            a aVar = (a) childAt.getLayoutParams();
            ConstraintWidget constraintWidget = aVar.k0;
            if ((childAt.getVisibility() != 8 || aVar.X || aVar.Y || isInEditMode) && !aVar.Z) {
                int g2 = constraintWidget.g();
                int h2 = constraintWidget.h();
                int s2 = constraintWidget.s() + g2;
                int i7 = constraintWidget.i() + h2;
                childAt.layout(g2, h2, s2, i7);
                if ((childAt instanceof Placeholder) && (content = ((Placeholder) childAt).getContent()) != null) {
                    content.setVisibility(0);
                    content.layout(g2, h2, s2, i7);
                }
            }
        }
        int size = this.f404f.size();
        if (size > 0) {
            for (int i8 = 0; i8 < size; i8++) {
                this.f404f.get(i8).a(this);
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:173:0x0360  */
    /* JADX WARNING: Removed duplicated region for block: B:176:0x0376  */
    /* JADX WARNING: Removed duplicated region for block: B:183:0x03af  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0124  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x013b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r24, int r25) {
        /*
            r23 = this;
            r0 = r23
            r1 = r24
            r2 = r25
            java.lang.System.currentTimeMillis()
            int r3 = android.view.View.MeasureSpec.getMode(r24)
            int r4 = android.view.View.MeasureSpec.getSize(r24)
            int r5 = android.view.View.MeasureSpec.getMode(r25)
            int r6 = android.view.View.MeasureSpec.getSize(r25)
            int r7 = r23.getPaddingLeft()
            int r8 = r23.getPaddingTop()
            androidx.constraintlayout.solver.widgets.e r9 = r0.f406h
            r9.r(r7)
            androidx.constraintlayout.solver.widgets.e r9 = r0.f406h
            r9.s(r8)
            androidx.constraintlayout.solver.widgets.e r9 = r0.f406h
            int r10 = r0.k
            r9.j(r10)
            androidx.constraintlayout.solver.widgets.e r9 = r0.f406h
            int r10 = r0.l
            r9.i(r10)
            int r9 = android.os.Build.VERSION.SDK_INT
            r10 = 0
            r11 = 1
            r12 = 17
            if (r9 < r12) goto L_0x004f
            androidx.constraintlayout.solver.widgets.e r9 = r0.f406h
            int r12 = r23.getLayoutDirection()
            if (r12 != r11) goto L_0x004b
            r12 = 1
            goto L_0x004c
        L_0x004b:
            r12 = 0
        L_0x004c:
            r9.c(r12)
        L_0x004f:
            r23.c(r24, r25)
            androidx.constraintlayout.solver.widgets.e r9 = r0.f406h
            int r9 = r9.s()
            androidx.constraintlayout.solver.widgets.e r12 = r0.f406h
            int r12 = r12.i()
            boolean r13 = r0.m
            if (r13 == 0) goto L_0x0069
            r0.m = r10
            r23.b()
            r13 = 1
            goto L_0x006a
        L_0x0069:
            r13 = 0
        L_0x006a:
            int r14 = r0.n
            r15 = 8
            r14 = r14 & r15
            if (r14 != r15) goto L_0x0073
            r14 = 1
            goto L_0x0074
        L_0x0073:
            r14 = 0
        L_0x0074:
            if (r14 == 0) goto L_0x0084
            androidx.constraintlayout.solver.widgets.e r15 = r0.f406h
            r15.T()
            androidx.constraintlayout.solver.widgets.e r15 = r0.f406h
            r15.f(r9, r12)
            r23.b(r24, r25)
            goto L_0x0087
        L_0x0084:
            r23.a((int) r24, (int) r25)
        L_0x0087:
            r23.c()
            int r15 = r23.getChildCount()
            if (r15 <= 0) goto L_0x0097
            if (r13 == 0) goto L_0x0097
            androidx.constraintlayout.solver.widgets.e r13 = r0.f406h
            androidx.constraintlayout.solver.widgets.a.a((androidx.constraintlayout.solver.widgets.e) r13)
        L_0x0097:
            androidx.constraintlayout.solver.widgets.e r13 = r0.f406h
            boolean r15 = r13.x0
            if (r15 == 0) goto L_0x00c9
            boolean r15 = r13.y0
            r11 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r15 == 0) goto L_0x00b3
            if (r3 != r11) goto L_0x00b3
            int r15 = r13.A0
            if (r15 >= r4) goto L_0x00ac
            r13.o(r15)
        L_0x00ac:
            androidx.constraintlayout.solver.widgets.e r13 = r0.f406h
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r15 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r13.a((androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour) r15)
        L_0x00b3:
            androidx.constraintlayout.solver.widgets.e r13 = r0.f406h
            boolean r15 = r13.z0
            if (r15 == 0) goto L_0x00c9
            if (r5 != r11) goto L_0x00c9
            int r11 = r13.B0
            if (r11 >= r6) goto L_0x00c2
            r13.g(r11)
        L_0x00c2:
            androidx.constraintlayout.solver.widgets.e r11 = r0.f406h
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r13 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r11.b((androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour) r13)
        L_0x00c9:
            int r11 = r0.n
            r13 = 32
            r11 = r11 & r13
            r15 = 1073741824(0x40000000, float:2.0)
            if (r11 != r13) goto L_0x011d
            androidx.constraintlayout.solver.widgets.e r11 = r0.f406h
            int r11 = r11.s()
            androidx.constraintlayout.solver.widgets.e r13 = r0.f406h
            int r13 = r13.i()
            int r10 = r0.r
            if (r10 == r11) goto L_0x00ec
            if (r3 != r15) goto L_0x00ec
            androidx.constraintlayout.solver.widgets.e r3 = r0.f406h
            java.util.List<androidx.constraintlayout.solver.widgets.f> r3 = r3.w0
            r10 = 0
            androidx.constraintlayout.solver.widgets.a.a((java.util.List<androidx.constraintlayout.solver.widgets.f>) r3, (int) r10, (int) r11)
        L_0x00ec:
            int r3 = r0.s
            if (r3 == r13) goto L_0x00fa
            if (r5 != r15) goto L_0x00fa
            androidx.constraintlayout.solver.widgets.e r3 = r0.f406h
            java.util.List<androidx.constraintlayout.solver.widgets.f> r3 = r3.w0
            r5 = 1
            androidx.constraintlayout.solver.widgets.a.a((java.util.List<androidx.constraintlayout.solver.widgets.f>) r3, (int) r5, (int) r13)
        L_0x00fa:
            androidx.constraintlayout.solver.widgets.e r3 = r0.f406h
            boolean r5 = r3.y0
            if (r5 == 0) goto L_0x010b
            int r5 = r3.A0
            if (r5 <= r4) goto L_0x010b
            java.util.List<androidx.constraintlayout.solver.widgets.f> r3 = r3.w0
            r10 = 0
            androidx.constraintlayout.solver.widgets.a.a((java.util.List<androidx.constraintlayout.solver.widgets.f>) r3, (int) r10, (int) r4)
            goto L_0x010c
        L_0x010b:
            r10 = 0
        L_0x010c:
            androidx.constraintlayout.solver.widgets.e r3 = r0.f406h
            boolean r4 = r3.z0
            if (r4 == 0) goto L_0x011d
            int r4 = r3.B0
            if (r4 <= r6) goto L_0x011d
            java.util.List<androidx.constraintlayout.solver.widgets.f> r3 = r3.w0
            r4 = 1
            androidx.constraintlayout.solver.widgets.a.a((java.util.List<androidx.constraintlayout.solver.widgets.f>) r3, (int) r4, (int) r6)
            goto L_0x011e
        L_0x011d:
            r4 = 1
        L_0x011e:
            int r3 = r23.getChildCount()
            if (r3 <= 0) goto L_0x0129
            java.lang.String r3 = "First pass"
            r0.a((java.lang.String) r3)
        L_0x0129:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r3 = r0.f405g
            int r3 = r3.size()
            int r5 = r23.getPaddingBottom()
            int r8 = r8 + r5
            int r5 = r23.getPaddingRight()
            int r7 = r7 + r5
            if (r3 <= 0) goto L_0x0360
            androidx.constraintlayout.solver.widgets.e r6 = r0.f406h
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r6 = r6.j()
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r6 != r11) goto L_0x0147
            r6 = 1
            goto L_0x0148
        L_0x0147:
            r6 = 0
        L_0x0148:
            androidx.constraintlayout.solver.widgets.e r11 = r0.f406h
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = r11.q()
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r13 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r11 != r13) goto L_0x0154
            r11 = 1
            goto L_0x0155
        L_0x0154:
            r11 = 0
        L_0x0155:
            androidx.constraintlayout.solver.widgets.e r13 = r0.f406h
            int r13 = r13.s()
            int r4 = r0.f407i
            int r4 = java.lang.Math.max(r13, r4)
            androidx.constraintlayout.solver.widgets.e r13 = r0.f406h
            int r13 = r13.i()
            int r10 = r0.f408j
            int r10 = java.lang.Math.max(r13, r10)
            r5 = r10
            r10 = 0
            r13 = 0
            r16 = 0
        L_0x0172:
            r17 = 1
            if (r10 >= r3) goto L_0x02b8
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r15 = r0.f405g
            java.lang.Object r15 = r15.get(r10)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r15 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r15
            java.lang.Object r19 = r15.e()
            r20 = r3
            r3 = r19
            android.view.View r3 = (android.view.View) r3
            if (r3 != 0) goto L_0x0190
            r19 = r9
            r21 = r12
            goto L_0x02a0
        L_0x0190:
            android.view.ViewGroup$LayoutParams r19 = r3.getLayoutParams()
            r21 = r12
            r12 = r19
            androidx.constraintlayout.widget.ConstraintLayout$a r12 = (androidx.constraintlayout.widget.ConstraintLayout.a) r12
            r19 = r9
            boolean r9 = r12.Y
            if (r9 != 0) goto L_0x02a0
            boolean r9 = r12.X
            if (r9 == 0) goto L_0x01a6
            goto L_0x02a0
        L_0x01a6:
            int r9 = r3.getVisibility()
            r22 = r13
            r13 = 8
            if (r9 != r13) goto L_0x01b2
        L_0x01b0:
            goto L_0x02a2
        L_0x01b2:
            if (r14 == 0) goto L_0x01c9
            androidx.constraintlayout.solver.widgets.l r9 = r15.m()
            boolean r9 = r9.c()
            if (r9 == 0) goto L_0x01c9
            androidx.constraintlayout.solver.widgets.l r9 = r15.l()
            boolean r9 = r9.c()
            if (r9 == 0) goto L_0x01c9
            goto L_0x01b0
        L_0x01c9:
            int r9 = r12.width
            r13 = -2
            if (r9 != r13) goto L_0x01d7
            boolean r13 = r12.U
            if (r13 == 0) goto L_0x01d7
            int r9 = android.view.ViewGroup.getChildMeasureSpec(r1, r7, r9)
            goto L_0x01e1
        L_0x01d7:
            int r9 = r15.s()
            r13 = 1073741824(0x40000000, float:2.0)
            int r9 = android.view.View.MeasureSpec.makeMeasureSpec(r9, r13)
        L_0x01e1:
            int r13 = r12.height
            r1 = -2
            if (r13 != r1) goto L_0x01ef
            boolean r1 = r12.V
            if (r1 == 0) goto L_0x01ef
            int r1 = android.view.ViewGroup.getChildMeasureSpec(r2, r8, r13)
            goto L_0x01f9
        L_0x01ef:
            int r1 = r15.i()
            r13 = 1073741824(0x40000000, float:2.0)
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r13)
        L_0x01f9:
            r3.measure(r9, r1)
            androidx.constraintlayout.solver.f r1 = r0.t
            r13 = r8
            if (r1 == 0) goto L_0x0207
            long r8 = r1.b
            long r8 = r8 + r17
            r1.b = r8
        L_0x0207:
            int r1 = r3.getMeasuredWidth()
            int r8 = r3.getMeasuredHeight()
            int r9 = r15.s()
            if (r1 == r9) goto L_0x023e
            r15.o(r1)
            if (r14 == 0) goto L_0x0221
            androidx.constraintlayout.solver.widgets.l r9 = r15.m()
            r9.a(r1)
        L_0x0221:
            if (r6 == 0) goto L_0x023c
            int r1 = r15.n()
            if (r1 <= r4) goto L_0x023c
            int r1 = r15.n()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r9 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r15.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r9)
            int r9 = r9.b()
            int r1 = r1 + r9
            int r4 = java.lang.Math.max(r4, r1)
        L_0x023c:
            r22 = 1
        L_0x023e:
            int r1 = r15.i()
            if (r8 == r1) goto L_0x026f
            r15.g(r8)
            if (r14 == 0) goto L_0x0250
            androidx.constraintlayout.solver.widgets.l r1 = r15.l()
            r1.a(r8)
        L_0x0250:
            if (r11 == 0) goto L_0x026c
            int r1 = r15.d()
            if (r1 <= r5) goto L_0x026c
            int r1 = r15.d()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r8 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r8 = r15.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r8)
            int r8 = r8.b()
            int r1 = r1 + r8
            int r1 = java.lang.Math.max(r5, r1)
            r5 = r1
        L_0x026c:
            r1 = r5
            r5 = 1
            goto L_0x0272
        L_0x026f:
            r1 = r5
            r5 = r22
        L_0x0272:
            boolean r8 = r12.W
            if (r8 == 0) goto L_0x0287
            int r8 = r3.getBaseline()
            r9 = -1
            if (r8 == r9) goto L_0x0287
            int r9 = r15.c()
            if (r8 == r9) goto L_0x0287
            r15.f(r8)
            r5 = 1
        L_0x0287:
            int r8 = android.os.Build.VERSION.SDK_INT
            r9 = 11
            if (r8 < r9) goto L_0x029a
            int r3 = r3.getMeasuredState()
            r8 = r16
            int r3 = android.view.ViewGroup.combineMeasuredStates(r8, r3)
            r16 = r3
            goto L_0x029c
        L_0x029a:
            r8 = r16
        L_0x029c:
            r22 = r5
            r5 = r1
            goto L_0x02a7
        L_0x02a0:
            r22 = r13
        L_0x02a2:
            r13 = r8
            r8 = r16
            r16 = r8
        L_0x02a7:
            int r10 = r10 + 1
            r1 = r24
            r8 = r13
            r9 = r19
            r3 = r20
            r12 = r21
            r13 = r22
            r15 = 1073741824(0x40000000, float:2.0)
            goto L_0x0172
        L_0x02b8:
            r20 = r3
            r19 = r9
            r21 = r12
            r22 = r13
            r13 = r8
            r8 = r16
            if (r22 == 0) goto L_0x0306
            androidx.constraintlayout.solver.widgets.e r1 = r0.f406h
            r3 = r19
            r1.o(r3)
            androidx.constraintlayout.solver.widgets.e r1 = r0.f406h
            r3 = r21
            r1.g(r3)
            if (r14 == 0) goto L_0x02da
            androidx.constraintlayout.solver.widgets.e r1 = r0.f406h
            r1.U()
        L_0x02da:
            java.lang.String r1 = "2nd pass"
            r0.a((java.lang.String) r1)
            androidx.constraintlayout.solver.widgets.e r1 = r0.f406h
            int r1 = r1.s()
            if (r1 >= r4) goto L_0x02ee
            androidx.constraintlayout.solver.widgets.e r1 = r0.f406h
            r1.o(r4)
            r10 = 1
            goto L_0x02ef
        L_0x02ee:
            r10 = 0
        L_0x02ef:
            androidx.constraintlayout.solver.widgets.e r1 = r0.f406h
            int r1 = r1.i()
            if (r1 >= r5) goto L_0x02fe
            androidx.constraintlayout.solver.widgets.e r1 = r0.f406h
            r1.g(r5)
            r11 = 1
            goto L_0x02ff
        L_0x02fe:
            r11 = r10
        L_0x02ff:
            if (r11 == 0) goto L_0x0306
            java.lang.String r1 = "3rd pass"
            r0.a((java.lang.String) r1)
        L_0x0306:
            r1 = r20
            r10 = 0
        L_0x0309:
            if (r10 >= r1) goto L_0x035e
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r3 = r0.f405g
            java.lang.Object r3 = r3.get(r10)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r3
            java.lang.Object r4 = r3.e()
            android.view.View r4 = (android.view.View) r4
            if (r4 != 0) goto L_0x0320
        L_0x031b:
            r6 = 8
        L_0x031d:
            r9 = 1073741824(0x40000000, float:2.0)
            goto L_0x035b
        L_0x0320:
            int r5 = r4.getMeasuredWidth()
            int r6 = r3.s()
            if (r5 != r6) goto L_0x0334
            int r5 = r4.getMeasuredHeight()
            int r6 = r3.i()
            if (r5 == r6) goto L_0x031b
        L_0x0334:
            int r5 = r3.r()
            r6 = 8
            if (r5 == r6) goto L_0x031d
            int r5 = r3.s()
            r9 = 1073741824(0x40000000, float:2.0)
            int r5 = android.view.View.MeasureSpec.makeMeasureSpec(r5, r9)
            int r3 = r3.i()
            int r3 = android.view.View.MeasureSpec.makeMeasureSpec(r3, r9)
            r4.measure(r5, r3)
            androidx.constraintlayout.solver.f r3 = r0.t
            if (r3 == 0) goto L_0x035b
            long r4 = r3.b
            long r4 = r4 + r17
            r3.b = r4
        L_0x035b:
            int r10 = r10 + 1
            goto L_0x0309
        L_0x035e:
            r10 = r8
            goto L_0x0362
        L_0x0360:
            r13 = r8
            r10 = 0
        L_0x0362:
            androidx.constraintlayout.solver.widgets.e r1 = r0.f406h
            int r1 = r1.s()
            int r1 = r1 + r7
            androidx.constraintlayout.solver.widgets.e r3 = r0.f406h
            int r3 = r3.i()
            int r3 = r3 + r13
            int r4 = android.os.Build.VERSION.SDK_INT
            r5 = 11
            if (r4 < r5) goto L_0x03af
            r4 = r24
            int r1 = android.view.ViewGroup.resolveSizeAndState(r1, r4, r10)
            int r4 = r10 << 16
            int r2 = android.view.ViewGroup.resolveSizeAndState(r3, r2, r4)
            r3 = 16777215(0xffffff, float:2.3509886E-38)
            r1 = r1 & r3
            r2 = r2 & r3
            int r3 = r0.k
            int r1 = java.lang.Math.min(r3, r1)
            int r3 = r0.l
            int r2 = java.lang.Math.min(r3, r2)
            androidx.constraintlayout.solver.widgets.e r3 = r0.f406h
            boolean r3 = r3.Q()
            r4 = 16777216(0x1000000, float:2.3509887E-38)
            if (r3 == 0) goto L_0x039e
            r1 = r1 | r4
        L_0x039e:
            androidx.constraintlayout.solver.widgets.e r3 = r0.f406h
            boolean r3 = r3.O()
            if (r3 == 0) goto L_0x03a7
            r2 = r2 | r4
        L_0x03a7:
            r0.setMeasuredDimension(r1, r2)
            r0.r = r1
            r0.s = r2
            goto L_0x03b6
        L_0x03af:
            r0.setMeasuredDimension(r1, r3)
            r0.r = r1
            r0.s = r3
        L_0x03b6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.ConstraintLayout.onMeasure(int, int):void");
    }

    public void onViewAdded(View view) {
        if (Build.VERSION.SDK_INT >= 14) {
            super.onViewAdded(view);
        }
        ConstraintWidget a2 = a(view);
        if ((view instanceof Guideline) && !(a2 instanceof g)) {
            a aVar = (a) view.getLayoutParams();
            g gVar = new g();
            aVar.k0 = gVar;
            aVar.X = true;
            gVar.v(aVar.R);
        }
        if (view instanceof ConstraintHelper) {
            ConstraintHelper constraintHelper = (ConstraintHelper) view;
            constraintHelper.a();
            ((a) view.getLayoutParams()).Y = true;
            if (!this.f404f.contains(constraintHelper)) {
                this.f404f.add(constraintHelper);
            }
        }
        this.e.put(view.getId(), view);
        this.m = true;
    }

    public void onViewRemoved(View view) {
        if (Build.VERSION.SDK_INT >= 14) {
            super.onViewRemoved(view);
        }
        this.e.remove(view.getId());
        ConstraintWidget a2 = a(view);
        this.f406h.c(a2);
        this.f404f.remove(view);
        this.f405g.remove(a2);
        this.m = true;
    }

    public void removeView(View view) {
        super.removeView(view);
        if (Build.VERSION.SDK_INT < 14) {
            onViewRemoved(view);
        }
    }

    public void requestLayout() {
        super.requestLayout();
        this.m = true;
        this.r = -1;
        this.s = -1;
    }

    public void setConstraintSet(a aVar) {
        this.o = aVar;
    }

    public void setId(int i2) {
        this.e.remove(getId());
        super.setId(i2);
        this.e.put(getId(), this);
    }

    public void setMaxHeight(int i2) {
        if (i2 != this.l) {
            this.l = i2;
            requestLayout();
        }
    }

    public void setMaxWidth(int i2) {
        if (i2 != this.k) {
            this.k = i2;
            requestLayout();
        }
    }

    public void setMinHeight(int i2) {
        if (i2 != this.f408j) {
            this.f408j = i2;
            requestLayout();
        }
    }

    public void setMinWidth(int i2) {
        if (i2 != this.f407i) {
            this.f407i = i2;
            requestLayout();
        }
    }

    public void setOptimizationLevel(int i2) {
        this.f406h.u(i2);
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    /* access modifiers changed from: protected */
    public a generateDefaultLayoutParams() {
        return new a(-2, -2);
    }

    public a generateLayoutParams(AttributeSet attributeSet) {
        return new a(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new a(layoutParams);
    }

    private final ConstraintWidget b(int i2) {
        if (i2 == 0) {
            return this.f406h;
        }
        View view = this.e.get(i2);
        if (view == null && (view = findViewById(i2)) != null && view != this && view.getParent() == this) {
            onViewAdded(view);
        }
        if (view == this) {
            return this.f406h;
        }
        if (view == null) {
            return null;
        }
        return ((a) view.getLayoutParams()).k0;
    }

    private void c(int i2, int i3) {
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        int i4;
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        int mode2 = View.MeasureSpec.getMode(i3);
        int size2 = View.MeasureSpec.getSize(i3);
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.FIXED;
        getLayoutParams();
        if (mode != Integer.MIN_VALUE) {
            if (mode == 0) {
                dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            } else if (mode != 1073741824) {
                dimensionBehaviour = dimensionBehaviour2;
            } else {
                i4 = Math.min(this.k, size) - paddingLeft;
                dimensionBehaviour = dimensionBehaviour2;
            }
            i4 = 0;
        } else {
            i4 = size;
            dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        }
        if (mode2 != Integer.MIN_VALUE) {
            if (mode2 == 0) {
                dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            } else if (mode2 == 1073741824) {
                size2 = Math.min(this.l, size2) - paddingTop;
            }
            size2 = 0;
        } else {
            dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        }
        this.f406h.l(0);
        this.f406h.k(0);
        this.f406h.a(dimensionBehaviour);
        this.f406h.o(i4);
        this.f406h.b(dimensionBehaviour2);
        this.f406h.g(size2);
        this.f406h.l((this.f407i - getPaddingLeft()) - getPaddingRight());
        this.f406h.k((this.f408j - getPaddingTop()) - getPaddingBottom());
    }

    public Object a(int i2, Object obj) {
        if (i2 != 0 || !(obj instanceof String)) {
            return null;
        }
        String str = (String) obj;
        HashMap<String, Integer> hashMap = this.q;
        if (hashMap == null || !hashMap.containsKey(str)) {
            return null;
        }
        return this.q.get(str);
    }

    private void a(AttributeSet attributeSet) {
        this.f406h.a((Object) this);
        this.e.put(getId(), this);
        this.o = null;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.ConstraintLayout_Layout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = obtainStyledAttributes.getIndex(i2);
                if (index == R$styleable.ConstraintLayout_Layout_android_minWidth) {
                    this.f407i = obtainStyledAttributes.getDimensionPixelOffset(index, this.f407i);
                } else if (index == R$styleable.ConstraintLayout_Layout_android_minHeight) {
                    this.f408j = obtainStyledAttributes.getDimensionPixelOffset(index, this.f408j);
                } else if (index == R$styleable.ConstraintLayout_Layout_android_maxWidth) {
                    this.k = obtainStyledAttributes.getDimensionPixelOffset(index, this.k);
                } else if (index == R$styleable.ConstraintLayout_Layout_android_maxHeight) {
                    this.l = obtainStyledAttributes.getDimensionPixelOffset(index, this.l);
                } else if (index == R$styleable.ConstraintLayout_Layout_layout_optimizationLevel) {
                    this.n = obtainStyledAttributes.getInt(index, this.n);
                } else if (index == R$styleable.ConstraintLayout_Layout_constraintSet) {
                    int resourceId = obtainStyledAttributes.getResourceId(index, 0);
                    try {
                        a aVar = new a();
                        this.o = aVar;
                        aVar.a(getContext(), resourceId);
                    } catch (Resources.NotFoundException unused) {
                        this.o = null;
                    }
                    this.p = resourceId;
                }
            }
            obtainStyledAttributes.recycle();
        }
        this.f406h.u(this.n);
    }

    /* JADX WARNING: Removed duplicated region for block: B:108:0x0209  */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x0242  */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x0266  */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x026f  */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x0274  */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x0276  */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x027c  */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x027e  */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x0292  */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x0297  */
    /* JADX WARNING: Removed duplicated region for block: B:144:0x029c  */
    /* JADX WARNING: Removed duplicated region for block: B:145:0x02a4  */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x02ad  */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x02b5  */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x02c2  */
    /* JADX WARNING: Removed duplicated region for block: B:154:0x02cd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(int r24, int r25) {
        /*
            r23 = this;
            r0 = r23
            r1 = r24
            r2 = r25
            int r3 = r23.getPaddingTop()
            int r4 = r23.getPaddingBottom()
            int r3 = r3 + r4
            int r4 = r23.getPaddingLeft()
            int r5 = r23.getPaddingRight()
            int r4 = r4 + r5
            int r5 = r23.getChildCount()
            r7 = 0
        L_0x001d:
            r8 = 1
            r10 = 8
            r12 = -2
            if (r7 >= r5) goto L_0x00dc
            android.view.View r14 = r0.getChildAt(r7)
            int r15 = r14.getVisibility()
            if (r15 != r10) goto L_0x0030
            goto L_0x00d4
        L_0x0030:
            android.view.ViewGroup$LayoutParams r10 = r14.getLayoutParams()
            androidx.constraintlayout.widget.ConstraintLayout$a r10 = (androidx.constraintlayout.widget.ConstraintLayout.a) r10
            androidx.constraintlayout.solver.widgets.ConstraintWidget r15 = r10.k0
            boolean r6 = r10.X
            if (r6 != 0) goto L_0x00d4
            boolean r6 = r10.Y
            if (r6 == 0) goto L_0x0042
            goto L_0x00d4
        L_0x0042:
            int r6 = r14.getVisibility()
            r15.n(r6)
            int r6 = r10.width
            int r13 = r10.height
            if (r6 == 0) goto L_0x00c4
            if (r13 != 0) goto L_0x0053
            goto L_0x00c4
        L_0x0053:
            if (r6 != r12) goto L_0x0058
            r16 = 1
            goto L_0x005a
        L_0x0058:
            r16 = 0
        L_0x005a:
            int r11 = android.view.ViewGroup.getChildMeasureSpec(r1, r4, r6)
            if (r13 != r12) goto L_0x0063
            r17 = 1
            goto L_0x0065
        L_0x0063:
            r17 = 0
        L_0x0065:
            int r12 = android.view.ViewGroup.getChildMeasureSpec(r2, r3, r13)
            r14.measure(r11, r12)
            androidx.constraintlayout.solver.f r11 = r0.t
            r12 = r3
            if (r11 == 0) goto L_0x0076
            long r2 = r11.a
            long r2 = r2 + r8
            r11.a = r2
        L_0x0076:
            r2 = -2
            if (r6 != r2) goto L_0x007b
            r3 = 1
            goto L_0x007c
        L_0x007b:
            r3 = 0
        L_0x007c:
            r15.b((boolean) r3)
            if (r13 != r2) goto L_0x0083
            r13 = 1
            goto L_0x0084
        L_0x0083:
            r13 = 0
        L_0x0084:
            r15.a((boolean) r13)
            int r2 = r14.getMeasuredWidth()
            int r3 = r14.getMeasuredHeight()
            r15.o(r2)
            r15.g(r3)
            if (r16 == 0) goto L_0x009a
            r15.q(r2)
        L_0x009a:
            if (r17 == 0) goto L_0x009f
            r15.p(r3)
        L_0x009f:
            boolean r6 = r10.W
            if (r6 == 0) goto L_0x00ad
            int r6 = r14.getBaseline()
            r8 = -1
            if (r6 == r8) goto L_0x00ad
            r15.f(r6)
        L_0x00ad:
            boolean r6 = r10.U
            if (r6 == 0) goto L_0x00d5
            boolean r6 = r10.V
            if (r6 == 0) goto L_0x00d5
            androidx.constraintlayout.solver.widgets.l r6 = r15.m()
            r6.a(r2)
            androidx.constraintlayout.solver.widgets.l r2 = r15.l()
            r2.a(r3)
            goto L_0x00d5
        L_0x00c4:
            r12 = r3
            androidx.constraintlayout.solver.widgets.l r2 = r15.m()
            r2.b()
            androidx.constraintlayout.solver.widgets.l r2 = r15.l()
            r2.b()
            goto L_0x00d5
        L_0x00d4:
            r12 = r3
        L_0x00d5:
            int r7 = r7 + 1
            r2 = r25
            r3 = r12
            goto L_0x001d
        L_0x00dc:
            r12 = r3
            androidx.constraintlayout.solver.widgets.e r2 = r0.f406h
            r2.U()
            r2 = 0
        L_0x00e3:
            if (r2 >= r5) goto L_0x02e6
            android.view.View r3 = r0.getChildAt(r2)
            int r6 = r3.getVisibility()
            if (r6 != r10) goto L_0x00f1
            goto L_0x02cf
        L_0x00f1:
            android.view.ViewGroup$LayoutParams r6 = r3.getLayoutParams()
            androidx.constraintlayout.widget.ConstraintLayout$a r6 = (androidx.constraintlayout.widget.ConstraintLayout.a) r6
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r6.k0
            boolean r11 = r6.X
            if (r11 != 0) goto L_0x02cf
            boolean r11 = r6.Y
            if (r11 == 0) goto L_0x0103
            goto L_0x02cf
        L_0x0103:
            int r11 = r3.getVisibility()
            r7.n(r11)
            int r11 = r6.width
            int r13 = r6.height
            if (r11 == 0) goto L_0x0114
            if (r13 == 0) goto L_0x0114
            goto L_0x02cf
        L_0x0114:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r14 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.LEFT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r14 = r7.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r14)
            androidx.constraintlayout.solver.widgets.k r14 = r14.d()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r15 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r15 = r7.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r15)
            androidx.constraintlayout.solver.widgets.k r15 = r15.d()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r10 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.LEFT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r7.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r10)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r10.g()
            if (r10 == 0) goto L_0x0142
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r10 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r7.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r10)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r10.g()
            if (r10 == 0) goto L_0x0142
            r10 = 1
            goto L_0x0143
        L_0x0142:
            r10 = 0
        L_0x0143:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r8 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r8 = r7.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r8)
            androidx.constraintlayout.solver.widgets.k r8 = r8.d()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r9 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r7.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r9)
            androidx.constraintlayout.solver.widgets.k r9 = r9.d()
            r17 = r5
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r5 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r7.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r5)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r5.g()
            if (r5 == 0) goto L_0x0173
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r5 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r7.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r5)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r5.g()
            if (r5 == 0) goto L_0x0173
            r5 = 1
            goto L_0x0174
        L_0x0173:
            r5 = 0
        L_0x0174:
            if (r11 != 0) goto L_0x0187
            if (r13 != 0) goto L_0x0187
            if (r10 == 0) goto L_0x0187
            if (r5 == 0) goto L_0x0187
            r5 = r25
            r6 = r0
            r20 = r2
            r2 = -1
            r8 = -2
            r18 = 1
            goto L_0x02da
        L_0x0187:
            r20 = r2
            androidx.constraintlayout.solver.widgets.e r2 = r0.f406h
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = r2.j()
            r21 = r6
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r6 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r2 == r6) goto L_0x0197
            r2 = 1
            goto L_0x0198
        L_0x0197:
            r2 = 0
        L_0x0198:
            androidx.constraintlayout.solver.widgets.e r6 = r0.f406h
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r6 = r6.q()
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r6 == r0) goto L_0x01a4
            r0 = 1
            goto L_0x01a5
        L_0x01a4:
            r0 = 0
        L_0x01a5:
            if (r2 != 0) goto L_0x01ae
            androidx.constraintlayout.solver.widgets.l r6 = r7.m()
            r6.b()
        L_0x01ae:
            if (r0 != 0) goto L_0x01b7
            androidx.constraintlayout.solver.widgets.l r6 = r7.l()
            r6.b()
        L_0x01b7:
            if (r11 != 0) goto L_0x01ee
            if (r2 == 0) goto L_0x01e5
            boolean r6 = r7.C()
            if (r6 == 0) goto L_0x01e5
            if (r10 == 0) goto L_0x01e5
            boolean r6 = r14.c()
            if (r6 == 0) goto L_0x01e5
            boolean r6 = r15.c()
            if (r6 == 0) goto L_0x01e5
            float r6 = r15.f()
            float r10 = r14.f()
            float r6 = r6 - r10
            int r11 = (int) r6
            androidx.constraintlayout.solver.widgets.l r6 = r7.m()
            r6.a(r11)
            int r6 = android.view.ViewGroup.getChildMeasureSpec(r1, r4, r11)
            goto L_0x01f7
        L_0x01e5:
            r6 = -2
            int r2 = android.view.ViewGroup.getChildMeasureSpec(r1, r4, r6)
            r6 = r2
            r2 = 0
            r10 = 1
            goto L_0x0207
        L_0x01ee:
            r6 = -2
            r10 = -1
            if (r11 != r10) goto L_0x01f9
            int r14 = android.view.ViewGroup.getChildMeasureSpec(r1, r4, r10)
            r6 = r14
        L_0x01f7:
            r10 = 0
            goto L_0x0207
        L_0x01f9:
            if (r11 != r6) goto L_0x01fd
            r6 = 1
            goto L_0x01fe
        L_0x01fd:
            r6 = 0
        L_0x01fe:
            int r10 = android.view.ViewGroup.getChildMeasureSpec(r1, r4, r11)
            r22 = r10
            r10 = r6
            r6 = r22
        L_0x0207:
            if (r13 != 0) goto L_0x0242
            if (r0 == 0) goto L_0x0237
            boolean r14 = r7.B()
            if (r14 == 0) goto L_0x0237
            if (r5 == 0) goto L_0x0237
            boolean r5 = r8.c()
            if (r5 == 0) goto L_0x0237
            boolean r5 = r9.c()
            if (r5 == 0) goto L_0x0237
            float r5 = r9.f()
            float r8 = r8.f()
            float r5 = r5 - r8
            int r13 = (int) r5
            androidx.constraintlayout.solver.widgets.l r5 = r7.l()
            r5.a(r13)
            r5 = r25
            int r8 = android.view.ViewGroup.getChildMeasureSpec(r5, r12, r13)
            goto L_0x024d
        L_0x0237:
            r5 = r25
            r8 = -2
            int r0 = android.view.ViewGroup.getChildMeasureSpec(r5, r12, r8)
            r8 = r0
            r0 = 0
            r9 = 1
            goto L_0x025d
        L_0x0242:
            r5 = r25
            r8 = -2
            r9 = -1
            if (r13 != r9) goto L_0x024f
            int r14 = android.view.ViewGroup.getChildMeasureSpec(r5, r12, r9)
            r8 = r14
        L_0x024d:
            r9 = 0
            goto L_0x025d
        L_0x024f:
            if (r13 != r8) goto L_0x0253
            r8 = 1
            goto L_0x0254
        L_0x0253:
            r8 = 0
        L_0x0254:
            int r9 = android.view.ViewGroup.getChildMeasureSpec(r5, r12, r13)
            r22 = r9
            r9 = r8
            r8 = r22
        L_0x025d:
            r3.measure(r6, r8)
            r6 = r23
            androidx.constraintlayout.solver.f r8 = r6.t
            if (r8 == 0) goto L_0x026f
            long r14 = r8.a
            r18 = 1
            long r14 = r14 + r18
            r8.a = r14
            goto L_0x0271
        L_0x026f:
            r18 = 1
        L_0x0271:
            r8 = -2
            if (r11 != r8) goto L_0x0276
            r11 = 1
            goto L_0x0277
        L_0x0276:
            r11 = 0
        L_0x0277:
            r7.b((boolean) r11)
            if (r13 != r8) goto L_0x027e
            r11 = 1
            goto L_0x027f
        L_0x027e:
            r11 = 0
        L_0x027f:
            r7.a((boolean) r11)
            int r11 = r3.getMeasuredWidth()
            int r13 = r3.getMeasuredHeight()
            r7.o(r11)
            r7.g(r13)
            if (r10 == 0) goto L_0x0295
            r7.q(r11)
        L_0x0295:
            if (r9 == 0) goto L_0x029a
            r7.p(r13)
        L_0x029a:
            if (r2 == 0) goto L_0x02a4
            androidx.constraintlayout.solver.widgets.l r2 = r7.m()
            r2.a(r11)
            goto L_0x02ab
        L_0x02a4:
            androidx.constraintlayout.solver.widgets.l r2 = r7.m()
            r2.f()
        L_0x02ab:
            if (r0 == 0) goto L_0x02b5
            androidx.constraintlayout.solver.widgets.l r0 = r7.l()
            r0.a(r13)
            goto L_0x02bc
        L_0x02b5:
            androidx.constraintlayout.solver.widgets.l r0 = r7.l()
            r0.f()
        L_0x02bc:
            r0 = r21
            boolean r0 = r0.W
            if (r0 == 0) goto L_0x02cd
            int r0 = r3.getBaseline()
            r2 = -1
            if (r0 == r2) goto L_0x02da
            r7.f(r0)
            goto L_0x02da
        L_0x02cd:
            r2 = -1
            goto L_0x02da
        L_0x02cf:
            r6 = r0
            r20 = r2
            r17 = r5
            r18 = r8
            r2 = -1
            r8 = -2
            r5 = r25
        L_0x02da:
            int r0 = r20 + 1
            r2 = r0
            r0 = r6
            r5 = r17
            r8 = r18
            r10 = 8
            goto L_0x00e3
        L_0x02e6:
            r6 = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.ConstraintLayout.b(int, int):void");
    }

    public ConstraintLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(attributeSet);
    }

    public ConstraintLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        a(attributeSet);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:118:0x01d6, code lost:
        if (r11 != -1) goto L_0x01da;
     */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x01e3  */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x01e7  */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x0203  */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x0212  */
    /* JADX WARNING: Removed duplicated region for block: B:202:0x0344  */
    /* JADX WARNING: Removed duplicated region for block: B:206:0x036c  */
    /* JADX WARNING: Removed duplicated region for block: B:209:0x037a  */
    /* JADX WARNING: Removed duplicated region for block: B:213:0x03a3  */
    /* JADX WARNING: Removed duplicated region for block: B:216:0x03b2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a() {
        /*
            r26 = this;
            r0 = r26
            boolean r1 = r26.isInEditMode()
            int r2 = r26.getChildCount()
            r3 = 0
            r4 = -1
            if (r1 == 0) goto L_0x0048
            r5 = 0
        L_0x000f:
            if (r5 >= r2) goto L_0x0048
            android.view.View r6 = r0.getChildAt(r5)
            android.content.res.Resources r7 = r26.getResources()     // Catch:{ NotFoundException -> 0x0045 }
            int r8 = r6.getId()     // Catch:{ NotFoundException -> 0x0045 }
            java.lang.String r7 = r7.getResourceName(r8)     // Catch:{ NotFoundException -> 0x0045 }
            int r8 = r6.getId()     // Catch:{ NotFoundException -> 0x0045 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ NotFoundException -> 0x0045 }
            r0.a(r3, r7, r8)     // Catch:{ NotFoundException -> 0x0045 }
            r8 = 47
            int r8 = r7.indexOf(r8)     // Catch:{ NotFoundException -> 0x0045 }
            if (r8 == r4) goto L_0x003a
            int r8 = r8 + 1
            java.lang.String r7 = r7.substring(r8)     // Catch:{ NotFoundException -> 0x0045 }
        L_0x003a:
            int r6 = r6.getId()     // Catch:{ NotFoundException -> 0x0045 }
            androidx.constraintlayout.solver.widgets.ConstraintWidget r6 = r0.b(r6)     // Catch:{ NotFoundException -> 0x0045 }
            r6.a((java.lang.String) r7)     // Catch:{ NotFoundException -> 0x0045 }
        L_0x0045:
            int r5 = r5 + 1
            goto L_0x000f
        L_0x0048:
            r5 = 0
        L_0x0049:
            if (r5 >= r2) goto L_0x005c
            android.view.View r6 = r0.getChildAt(r5)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r6 = r0.a((android.view.View) r6)
            if (r6 != 0) goto L_0x0056
            goto L_0x0059
        L_0x0056:
            r6.D()
        L_0x0059:
            int r5 = r5 + 1
            goto L_0x0049
        L_0x005c:
            int r5 = r0.p
            if (r5 == r4) goto L_0x007e
            r5 = 0
        L_0x0061:
            if (r5 >= r2) goto L_0x007e
            android.view.View r6 = r0.getChildAt(r5)
            int r7 = r6.getId()
            int r8 = r0.p
            if (r7 != r8) goto L_0x007b
            boolean r7 = r6 instanceof androidx.constraintlayout.widget.Constraints
            if (r7 == 0) goto L_0x007b
            androidx.constraintlayout.widget.Constraints r6 = (androidx.constraintlayout.widget.Constraints) r6
            androidx.constraintlayout.widget.a r6 = r6.getConstraintSet()
            r0.o = r6
        L_0x007b:
            int r5 = r5 + 1
            goto L_0x0061
        L_0x007e:
            androidx.constraintlayout.widget.a r5 = r0.o
            if (r5 == 0) goto L_0x0085
            r5.a((androidx.constraintlayout.widget.ConstraintLayout) r0)
        L_0x0085:
            androidx.constraintlayout.solver.widgets.e r5 = r0.f406h
            r5.L()
            java.util.ArrayList<androidx.constraintlayout.widget.ConstraintHelper> r5 = r0.f404f
            int r5 = r5.size()
            if (r5 <= 0) goto L_0x00a3
            r6 = 0
        L_0x0093:
            if (r6 >= r5) goto L_0x00a3
            java.util.ArrayList<androidx.constraintlayout.widget.ConstraintHelper> r7 = r0.f404f
            java.lang.Object r7 = r7.get(r6)
            androidx.constraintlayout.widget.ConstraintHelper r7 = (androidx.constraintlayout.widget.ConstraintHelper) r7
            r7.c(r0)
            int r6 = r6 + 1
            goto L_0x0093
        L_0x00a3:
            r5 = 0
        L_0x00a4:
            if (r5 >= r2) goto L_0x00b6
            android.view.View r6 = r0.getChildAt(r5)
            boolean r7 = r6 instanceof androidx.constraintlayout.widget.Placeholder
            if (r7 == 0) goto L_0x00b3
            androidx.constraintlayout.widget.Placeholder r6 = (androidx.constraintlayout.widget.Placeholder) r6
            r6.b(r0)
        L_0x00b3:
            int r5 = r5 + 1
            goto L_0x00a4
        L_0x00b6:
            r5 = 0
        L_0x00b7:
            if (r5 >= r2) goto L_0x03e3
            android.view.View r6 = r0.getChildAt(r5)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r13 = r0.a((android.view.View) r6)
            if (r13 != 0) goto L_0x00c5
            goto L_0x03df
        L_0x00c5:
            android.view.ViewGroup$LayoutParams r7 = r6.getLayoutParams()
            r14 = r7
            androidx.constraintlayout.widget.ConstraintLayout$a r14 = (androidx.constraintlayout.widget.ConstraintLayout.a) r14
            r14.a()
            boolean r7 = r14.l0
            if (r7 == 0) goto L_0x00d6
            r14.l0 = r3
            goto L_0x0108
        L_0x00d6:
            if (r1 == 0) goto L_0x0108
            android.content.res.Resources r7 = r26.getResources()     // Catch:{ NotFoundException -> 0x0107 }
            int r8 = r6.getId()     // Catch:{ NotFoundException -> 0x0107 }
            java.lang.String r7 = r7.getResourceName(r8)     // Catch:{ NotFoundException -> 0x0107 }
            int r8 = r6.getId()     // Catch:{ NotFoundException -> 0x0107 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ NotFoundException -> 0x0107 }
            r0.a(r3, r7, r8)     // Catch:{ NotFoundException -> 0x0107 }
            java.lang.String r8 = "id/"
            int r8 = r7.indexOf(r8)     // Catch:{ NotFoundException -> 0x0107 }
            int r8 = r8 + 3
            java.lang.String r7 = r7.substring(r8)     // Catch:{ NotFoundException -> 0x0107 }
            int r8 = r6.getId()     // Catch:{ NotFoundException -> 0x0107 }
            androidx.constraintlayout.solver.widgets.ConstraintWidget r8 = r0.b(r8)     // Catch:{ NotFoundException -> 0x0107 }
            r8.a((java.lang.String) r7)     // Catch:{ NotFoundException -> 0x0107 }
            goto L_0x0108
        L_0x0107:
        L_0x0108:
            int r7 = r6.getVisibility()
            r13.n(r7)
            boolean r7 = r14.Z
            if (r7 == 0) goto L_0x0118
            r7 = 8
            r13.n(r7)
        L_0x0118:
            r13.a((java.lang.Object) r6)
            androidx.constraintlayout.solver.widgets.e r6 = r0.f406h
            r6.b(r13)
            boolean r6 = r14.V
            if (r6 == 0) goto L_0x0128
            boolean r6 = r14.U
            if (r6 != 0) goto L_0x012d
        L_0x0128:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r6 = r0.f405g
            r6.add(r13)
        L_0x012d:
            boolean r6 = r14.X
            r7 = 17
            if (r6 == 0) goto L_0x015e
            androidx.constraintlayout.solver.widgets.g r13 = (androidx.constraintlayout.solver.widgets.g) r13
            int r6 = r14.h0
            int r8 = r14.i0
            float r9 = r14.j0
            int r10 = android.os.Build.VERSION.SDK_INT
            if (r10 >= r7) goto L_0x0145
            int r6 = r14.a
            int r8 = r14.b
            float r9 = r14.c
        L_0x0145:
            r7 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r7 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r7 == 0) goto L_0x0150
            r13.e(r9)
            goto L_0x03df
        L_0x0150:
            if (r6 == r4) goto L_0x0157
            r13.t(r6)
            goto L_0x03df
        L_0x0157:
            if (r8 == r4) goto L_0x03df
            r13.u(r8)
            goto L_0x03df
        L_0x015e:
            int r6 = r14.d
            if (r6 != r4) goto L_0x01a6
            int r6 = r14.e
            if (r6 != r4) goto L_0x01a6
            int r6 = r14.f409f
            if (r6 != r4) goto L_0x01a6
            int r6 = r14.f410g
            if (r6 != r4) goto L_0x01a6
            int r6 = r14.q
            if (r6 != r4) goto L_0x01a6
            int r6 = r14.p
            if (r6 != r4) goto L_0x01a6
            int r6 = r14.r
            if (r6 != r4) goto L_0x01a6
            int r6 = r14.s
            if (r6 != r4) goto L_0x01a6
            int r6 = r14.f411h
            if (r6 != r4) goto L_0x01a6
            int r6 = r14.f412i
            if (r6 != r4) goto L_0x01a6
            int r6 = r14.f413j
            if (r6 != r4) goto L_0x01a6
            int r6 = r14.k
            if (r6 != r4) goto L_0x01a6
            int r6 = r14.l
            if (r6 != r4) goto L_0x01a6
            int r6 = r14.P
            if (r6 != r4) goto L_0x01a6
            int r6 = r14.Q
            if (r6 != r4) goto L_0x01a6
            int r6 = r14.m
            if (r6 != r4) goto L_0x01a6
            int r6 = r14.width
            if (r6 == r4) goto L_0x01a6
            int r6 = r14.height
            if (r6 != r4) goto L_0x03df
        L_0x01a6:
            int r6 = r14.a0
            int r8 = r14.b0
            int r9 = r14.c0
            int r10 = r14.d0
            int r11 = r14.e0
            int r12 = r14.f0
            float r15 = r14.g0
            int r3 = android.os.Build.VERSION.SDK_INT
            if (r3 >= r7) goto L_0x01f9
            int r3 = r14.d
            int r6 = r14.e
            int r9 = r14.f409f
            int r10 = r14.f410g
            int r7 = r14.t
            int r8 = r14.v
            float r15 = r14.z
            if (r3 != r4) goto L_0x01d9
            if (r6 != r4) goto L_0x01d9
            int r11 = r14.q
            if (r11 == r4) goto L_0x01d4
            r25 = r11
            r11 = r6
            r6 = r25
            goto L_0x01db
        L_0x01d4:
            int r11 = r14.p
            if (r11 == r4) goto L_0x01d9
            goto L_0x01da
        L_0x01d9:
            r11 = r6
        L_0x01da:
            r6 = r3
        L_0x01db:
            if (r9 != r4) goto L_0x01f3
            if (r10 != r4) goto L_0x01f3
            int r3 = r14.r
            if (r3 == r4) goto L_0x01e7
            r12 = r7
            r16 = r8
            goto L_0x01f7
        L_0x01e7:
            int r3 = r14.s
            if (r3 == r4) goto L_0x01f3
            r12 = r7
            r16 = r8
            r8 = r11
            r11 = r15
            r15 = r3
            r3 = r9
            goto L_0x01ff
        L_0x01f3:
            r12 = r7
            r16 = r8
            r3 = r9
        L_0x01f7:
            r8 = r11
            goto L_0x01fd
        L_0x01f9:
            r3 = r9
            r16 = r12
            r12 = r11
        L_0x01fd:
            r11 = r15
            r15 = r10
        L_0x01ff:
            int r7 = r14.m
            if (r7 == r4) goto L_0x0212
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r0.b(r7)
            if (r3 == 0) goto L_0x032f
            float r6 = r14.o
            int r7 = r14.n
            r13.a((androidx.constraintlayout.solver.widgets.ConstraintWidget) r3, (float) r6, (int) r7)
            goto L_0x032f
        L_0x0212:
            if (r6 == r4) goto L_0x022a
            androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r0.b(r6)
            if (r9 == 0) goto L_0x0227
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r10 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.LEFT
            int r6 = r14.leftMargin
            r7 = r13
            r8 = r10
            r17 = r11
            r11 = r6
            r7.a(r8, r9, r10, r11, r12)
            goto L_0x023e
        L_0x0227:
            r17 = r11
            goto L_0x023e
        L_0x022a:
            r17 = r11
            if (r8 == r4) goto L_0x023e
            androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r0.b(r8)
            if (r9 == 0) goto L_0x023e
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r8 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.LEFT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r10 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT
            int r11 = r14.leftMargin
            r7 = r13
            r7.a(r8, r9, r10, r11, r12)
        L_0x023e:
            if (r3 == r4) goto L_0x0253
            androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r0.b(r3)
            if (r9 == 0) goto L_0x0266
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r8 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r10 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.LEFT
            int r11 = r14.rightMargin
            r7 = r13
            r12 = r16
            r7.a(r8, r9, r10, r11, r12)
            goto L_0x0266
        L_0x0253:
            if (r15 == r4) goto L_0x0266
            androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r0.b(r15)
            if (r9 == 0) goto L_0x0266
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r10 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT
            int r11 = r14.rightMargin
            r7 = r13
            r8 = r10
            r12 = r16
            r7.a(r8, r9, r10, r11, r12)
        L_0x0266:
            int r3 = r14.f411h
            if (r3 == r4) goto L_0x027c
            androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r0.b(r3)
            if (r9 == 0) goto L_0x0292
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r10 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP
            int r11 = r14.topMargin
            int r12 = r14.u
            r7 = r13
            r8 = r10
            r7.a(r8, r9, r10, r11, r12)
            goto L_0x0292
        L_0x027c:
            int r3 = r14.f412i
            if (r3 == r4) goto L_0x0292
            androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r0.b(r3)
            if (r9 == 0) goto L_0x0292
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r8 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r10 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            int r11 = r14.topMargin
            int r12 = r14.u
            r7 = r13
            r7.a(r8, r9, r10, r11, r12)
        L_0x0292:
            int r3 = r14.f413j
            if (r3 == r4) goto L_0x02a9
            androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r0.b(r3)
            if (r9 == 0) goto L_0x02be
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r8 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r10 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP
            int r11 = r14.bottomMargin
            int r12 = r14.w
            r7 = r13
            r7.a(r8, r9, r10, r11, r12)
            goto L_0x02be
        L_0x02a9:
            int r3 = r14.k
            if (r3 == r4) goto L_0x02be
            androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r0.b(r3)
            if (r9 == 0) goto L_0x02be
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r10 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            int r11 = r14.bottomMargin
            int r12 = r14.w
            r7 = r13
            r8 = r10
            r7.a(r8, r9, r10, r11, r12)
        L_0x02be:
            int r3 = r14.l
            if (r3 == r4) goto L_0x0312
            android.util.SparseArray<android.view.View> r6 = r0.e
            java.lang.Object r3 = r6.get(r3)
            android.view.View r3 = (android.view.View) r3
            int r6 = r14.l
            androidx.constraintlayout.solver.widgets.ConstraintWidget r6 = r0.b(r6)
            if (r6 == 0) goto L_0x0312
            if (r3 == 0) goto L_0x0312
            android.view.ViewGroup$LayoutParams r7 = r3.getLayoutParams()
            boolean r7 = r7 instanceof androidx.constraintlayout.widget.ConstraintLayout.a
            if (r7 == 0) goto L_0x0312
            android.view.ViewGroup$LayoutParams r3 = r3.getLayoutParams()
            androidx.constraintlayout.widget.ConstraintLayout$a r3 = (androidx.constraintlayout.widget.ConstraintLayout.a) r3
            r7 = 1
            r14.W = r7
            r3.W = r7
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BASELINE
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r18 = r13.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r3)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BASELINE
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r19 = r6.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r3)
            r20 = 0
            r21 = -1
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Strength r22 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Strength.STRONG
            r23 = 0
            r24 = 1
            r18.a(r19, r20, r21, r22, r23, r24)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r13.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r3)
            r3.j()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r13.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r3)
            r3.j()
        L_0x0312:
            r3 = 1056964608(0x3f000000, float:0.5)
            r6 = 0
            r15 = r17
            int r7 = (r15 > r6 ? 1 : (r15 == r6 ? 0 : -1))
            if (r7 < 0) goto L_0x0322
            int r7 = (r15 > r3 ? 1 : (r15 == r3 ? 0 : -1))
            if (r7 == 0) goto L_0x0322
            r13.a((float) r15)
        L_0x0322:
            float r7 = r14.A
            int r6 = (r7 > r6 ? 1 : (r7 == r6 ? 0 : -1))
            if (r6 < 0) goto L_0x032f
            int r3 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r3 == 0) goto L_0x032f
            r13.c((float) r7)
        L_0x032f:
            if (r1 == 0) goto L_0x0340
            int r3 = r14.P
            if (r3 != r4) goto L_0x0339
            int r3 = r14.Q
            if (r3 == r4) goto L_0x0340
        L_0x0339:
            int r3 = r14.P
            int r6 = r14.Q
            r13.c(r3, r6)
        L_0x0340:
            boolean r3 = r14.U
            if (r3 != 0) goto L_0x036c
            int r3 = r14.width
            if (r3 != r4) goto L_0x0362
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_PARENT
            r13.a((androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour) r3)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.LEFT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r13.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r3)
            int r6 = r14.leftMargin
            r3.e = r6
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r13.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r3)
            int r6 = r14.rightMargin
            r3.e = r6
            goto L_0x0376
        L_0x0362:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r13.a((androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour) r3)
            r3 = 0
            r13.o(r3)
            goto L_0x0376
        L_0x036c:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r13.a((androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour) r3)
            int r3 = r14.width
            r13.o(r3)
        L_0x0376:
            boolean r3 = r14.V
            if (r3 != 0) goto L_0x03a3
            int r3 = r14.height
            if (r3 != r4) goto L_0x0399
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_PARENT
            r13.b((androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour) r3)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r13.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r3)
            int r6 = r14.topMargin
            r3.e = r6
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r13.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r3)
            int r6 = r14.bottomMargin
            r3.e = r6
            r3 = 0
            goto L_0x03ae
        L_0x0399:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r13.b((androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour) r3)
            r3 = 0
            r13.g(r3)
            goto L_0x03ae
        L_0x03a3:
            r3 = 0
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r6 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r13.b((androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour) r6)
            int r6 = r14.height
            r13.g(r6)
        L_0x03ae:
            java.lang.String r6 = r14.B
            if (r6 == 0) goto L_0x03b5
            r13.b((java.lang.String) r6)
        L_0x03b5:
            float r6 = r14.D
            r13.b((float) r6)
            float r6 = r14.E
            r13.d((float) r6)
            int r6 = r14.F
            r13.h(r6)
            int r6 = r14.G
            r13.m(r6)
            int r6 = r14.H
            int r7 = r14.J
            int r8 = r14.L
            float r9 = r14.N
            r13.a((int) r6, (int) r7, (int) r8, (float) r9)
            int r6 = r14.I
            int r7 = r14.K
            int r8 = r14.M
            float r9 = r14.O
            r13.b(r6, r7, r8, r9)
        L_0x03df:
            int r5 = r5 + 1
            goto L_0x00b7
        L_0x03e3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.ConstraintLayout.a():void");
    }

    public static class a extends ViewGroup.MarginLayoutParams {
        public float A = 0.5f;
        public String B = null;
        int C = 1;
        public float D = -1.0f;
        public float E = -1.0f;
        public int F = 0;
        public int G = 0;
        public int H = 0;
        public int I = 0;
        public int J = 0;
        public int K = 0;
        public int L = 0;
        public int M = 0;
        public float N = 1.0f;
        public float O = 1.0f;
        public int P = -1;
        public int Q = -1;
        public int R = -1;
        public boolean S = false;
        public boolean T = false;
        boolean U = true;
        boolean V = true;
        boolean W = false;
        boolean X = false;
        boolean Y = false;
        boolean Z = false;
        public int a = -1;
        int a0 = -1;
        public int b = -1;
        int b0 = -1;
        public float c = -1.0f;
        int c0 = -1;
        public int d = -1;
        int d0 = -1;
        public int e = -1;
        int e0 = -1;

        /* renamed from: f  reason: collision with root package name */
        public int f409f = -1;
        int f0 = -1;

        /* renamed from: g  reason: collision with root package name */
        public int f410g = -1;
        float g0 = 0.5f;

        /* renamed from: h  reason: collision with root package name */
        public int f411h = -1;
        int h0;

        /* renamed from: i  reason: collision with root package name */
        public int f412i = -1;
        int i0;

        /* renamed from: j  reason: collision with root package name */
        public int f413j = -1;
        float j0;
        public int k = -1;
        ConstraintWidget k0 = new ConstraintWidget();
        public int l = -1;
        public boolean l0 = false;
        public int m = -1;
        public int n = 0;
        public float o = 0.0f;
        public int p = -1;
        public int q = -1;
        public int r = -1;
        public int s = -1;
        public int t = -1;
        public int u = -1;
        public int v = -1;
        public int w = -1;
        public int x = -1;
        public int y = -1;
        public float z = 0.5f;

        /* renamed from: androidx.constraintlayout.widget.ConstraintLayout$a$a  reason: collision with other inner class name */
        private static class C0010a {
            public static final SparseIntArray a;

            static {
                SparseIntArray sparseIntArray = new SparseIntArray();
                a = sparseIntArray;
                sparseIntArray.append(R$styleable.ConstraintLayout_Layout_layout_constraintLeft_toLeftOf, 8);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintLeft_toRightOf, 9);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintRight_toLeftOf, 10);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintRight_toRightOf, 11);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintTop_toTopOf, 12);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintTop_toBottomOf, 13);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintBottom_toTopOf, 14);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintBottom_toBottomOf, 15);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintBaseline_toBaselineOf, 16);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintCircle, 2);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintCircleRadius, 3);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintCircleAngle, 4);
                a.append(R$styleable.ConstraintLayout_Layout_layout_editor_absoluteX, 49);
                a.append(R$styleable.ConstraintLayout_Layout_layout_editor_absoluteY, 50);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintGuide_begin, 5);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintGuide_end, 6);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintGuide_percent, 7);
                a.append(R$styleable.ConstraintLayout_Layout_android_orientation, 1);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintStart_toEndOf, 17);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintStart_toStartOf, 18);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintEnd_toStartOf, 19);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintEnd_toEndOf, 20);
                a.append(R$styleable.ConstraintLayout_Layout_layout_goneMarginLeft, 21);
                a.append(R$styleable.ConstraintLayout_Layout_layout_goneMarginTop, 22);
                a.append(R$styleable.ConstraintLayout_Layout_layout_goneMarginRight, 23);
                a.append(R$styleable.ConstraintLayout_Layout_layout_goneMarginBottom, 24);
                a.append(R$styleable.ConstraintLayout_Layout_layout_goneMarginStart, 25);
                a.append(R$styleable.ConstraintLayout_Layout_layout_goneMarginEnd, 26);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintHorizontal_bias, 29);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintVertical_bias, 30);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintDimensionRatio, 44);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintHorizontal_weight, 45);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintVertical_weight, 46);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintHorizontal_chainStyle, 47);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintVertical_chainStyle, 48);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constrainedWidth, 27);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constrainedHeight, 28);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintWidth_default, 31);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintHeight_default, 32);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintWidth_min, 33);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintWidth_max, 34);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintWidth_percent, 35);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintHeight_min, 36);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintHeight_max, 37);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintHeight_percent, 38);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintLeft_creator, 39);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintTop_creator, 40);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintRight_creator, 41);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintBottom_creator, 42);
                a.append(R$styleable.ConstraintLayout_Layout_layout_constraintBaseline_creator, 43);
            }
        }

        public a(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            int i2;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ConstraintLayout_Layout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i3 = 0; i3 < indexCount; i3++) {
                int index = obtainStyledAttributes.getIndex(i3);
                int i4 = C0010a.a.get(index);
                switch (i4) {
                    case 1:
                        this.R = obtainStyledAttributes.getInt(index, this.R);
                        break;
                    case 2:
                        int resourceId = obtainStyledAttributes.getResourceId(index, this.m);
                        this.m = resourceId;
                        if (resourceId != -1) {
                            break;
                        } else {
                            this.m = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 3:
                        this.n = obtainStyledAttributes.getDimensionPixelSize(index, this.n);
                        break;
                    case 4:
                        float f2 = obtainStyledAttributes.getFloat(index, this.o) % 360.0f;
                        this.o = f2;
                        if (f2 >= 0.0f) {
                            break;
                        } else {
                            this.o = (360.0f - f2) % 360.0f;
                            break;
                        }
                    case 5:
                        this.a = obtainStyledAttributes.getDimensionPixelOffset(index, this.a);
                        break;
                    case 6:
                        this.b = obtainStyledAttributes.getDimensionPixelOffset(index, this.b);
                        break;
                    case 7:
                        this.c = obtainStyledAttributes.getFloat(index, this.c);
                        break;
                    case 8:
                        int resourceId2 = obtainStyledAttributes.getResourceId(index, this.d);
                        this.d = resourceId2;
                        if (resourceId2 != -1) {
                            break;
                        } else {
                            this.d = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 9:
                        int resourceId3 = obtainStyledAttributes.getResourceId(index, this.e);
                        this.e = resourceId3;
                        if (resourceId3 != -1) {
                            break;
                        } else {
                            this.e = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 10:
                        int resourceId4 = obtainStyledAttributes.getResourceId(index, this.f409f);
                        this.f409f = resourceId4;
                        if (resourceId4 != -1) {
                            break;
                        } else {
                            this.f409f = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 11:
                        int resourceId5 = obtainStyledAttributes.getResourceId(index, this.f410g);
                        this.f410g = resourceId5;
                        if (resourceId5 != -1) {
                            break;
                        } else {
                            this.f410g = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 12:
                        int resourceId6 = obtainStyledAttributes.getResourceId(index, this.f411h);
                        this.f411h = resourceId6;
                        if (resourceId6 != -1) {
                            break;
                        } else {
                            this.f411h = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 13:
                        int resourceId7 = obtainStyledAttributes.getResourceId(index, this.f412i);
                        this.f412i = resourceId7;
                        if (resourceId7 != -1) {
                            break;
                        } else {
                            this.f412i = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 14:
                        int resourceId8 = obtainStyledAttributes.getResourceId(index, this.f413j);
                        this.f413j = resourceId8;
                        if (resourceId8 != -1) {
                            break;
                        } else {
                            this.f413j = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 15:
                        int resourceId9 = obtainStyledAttributes.getResourceId(index, this.k);
                        this.k = resourceId9;
                        if (resourceId9 != -1) {
                            break;
                        } else {
                            this.k = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 16:
                        int resourceId10 = obtainStyledAttributes.getResourceId(index, this.l);
                        this.l = resourceId10;
                        if (resourceId10 != -1) {
                            break;
                        } else {
                            this.l = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 17:
                        int resourceId11 = obtainStyledAttributes.getResourceId(index, this.p);
                        this.p = resourceId11;
                        if (resourceId11 != -1) {
                            break;
                        } else {
                            this.p = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 18:
                        int resourceId12 = obtainStyledAttributes.getResourceId(index, this.q);
                        this.q = resourceId12;
                        if (resourceId12 != -1) {
                            break;
                        } else {
                            this.q = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 19:
                        int resourceId13 = obtainStyledAttributes.getResourceId(index, this.r);
                        this.r = resourceId13;
                        if (resourceId13 != -1) {
                            break;
                        } else {
                            this.r = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 20:
                        int resourceId14 = obtainStyledAttributes.getResourceId(index, this.s);
                        this.s = resourceId14;
                        if (resourceId14 != -1) {
                            break;
                        } else {
                            this.s = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 21:
                        this.t = obtainStyledAttributes.getDimensionPixelSize(index, this.t);
                        break;
                    case 22:
                        this.u = obtainStyledAttributes.getDimensionPixelSize(index, this.u);
                        break;
                    case 23:
                        this.v = obtainStyledAttributes.getDimensionPixelSize(index, this.v);
                        break;
                    case 24:
                        this.w = obtainStyledAttributes.getDimensionPixelSize(index, this.w);
                        break;
                    case 25:
                        this.x = obtainStyledAttributes.getDimensionPixelSize(index, this.x);
                        break;
                    case 26:
                        this.y = obtainStyledAttributes.getDimensionPixelSize(index, this.y);
                        break;
                    case 27:
                        this.S = obtainStyledAttributes.getBoolean(index, this.S);
                        break;
                    case 28:
                        this.T = obtainStyledAttributes.getBoolean(index, this.T);
                        break;
                    case 29:
                        this.z = obtainStyledAttributes.getFloat(index, this.z);
                        break;
                    case 30:
                        this.A = obtainStyledAttributes.getFloat(index, this.A);
                        break;
                    case 31:
                        int i5 = obtainStyledAttributes.getInt(index, 0);
                        this.H = i5;
                        if (i5 != 1) {
                            break;
                        } else {
                            Log.e("ConstraintLayout", "layout_constraintWidth_default=\"wrap\" is deprecated.\nUse layout_width=\"WRAP_CONTENT\" and layout_constrainedWidth=\"true\" instead.");
                            break;
                        }
                    case 32:
                        int i6 = obtainStyledAttributes.getInt(index, 0);
                        this.I = i6;
                        if (i6 != 1) {
                            break;
                        } else {
                            Log.e("ConstraintLayout", "layout_constraintHeight_default=\"wrap\" is deprecated.\nUse layout_height=\"WRAP_CONTENT\" and layout_constrainedHeight=\"true\" instead.");
                            break;
                        }
                    case 33:
                        try {
                            this.J = obtainStyledAttributes.getDimensionPixelSize(index, this.J);
                            break;
                        } catch (Exception unused) {
                            if (obtainStyledAttributes.getInt(index, this.J) != -2) {
                                break;
                            } else {
                                this.J = -2;
                                break;
                            }
                        }
                    case 34:
                        try {
                            this.L = obtainStyledAttributes.getDimensionPixelSize(index, this.L);
                            break;
                        } catch (Exception unused2) {
                            if (obtainStyledAttributes.getInt(index, this.L) != -2) {
                                break;
                            } else {
                                this.L = -2;
                                break;
                            }
                        }
                    case 35:
                        this.N = Math.max(0.0f, obtainStyledAttributes.getFloat(index, this.N));
                        break;
                    case 36:
                        try {
                            this.K = obtainStyledAttributes.getDimensionPixelSize(index, this.K);
                            break;
                        } catch (Exception unused3) {
                            if (obtainStyledAttributes.getInt(index, this.K) != -2) {
                                break;
                            } else {
                                this.K = -2;
                                break;
                            }
                        }
                    case 37:
                        try {
                            this.M = obtainStyledAttributes.getDimensionPixelSize(index, this.M);
                            break;
                        } catch (Exception unused4) {
                            if (obtainStyledAttributes.getInt(index, this.M) != -2) {
                                break;
                            } else {
                                this.M = -2;
                                break;
                            }
                        }
                    case 38:
                        this.O = Math.max(0.0f, obtainStyledAttributes.getFloat(index, this.O));
                        break;
                    default:
                        switch (i4) {
                            case 44:
                                String string = obtainStyledAttributes.getString(index);
                                this.B = string;
                                this.C = -1;
                                if (string == null) {
                                    break;
                                } else {
                                    int length = string.length();
                                    int indexOf = this.B.indexOf(44);
                                    if (indexOf <= 0 || indexOf >= length - 1) {
                                        i2 = 0;
                                    } else {
                                        String substring = this.B.substring(0, indexOf);
                                        if (substring.equalsIgnoreCase("W")) {
                                            this.C = 0;
                                        } else if (substring.equalsIgnoreCase("H")) {
                                            this.C = 1;
                                        }
                                        i2 = indexOf + 1;
                                    }
                                    int indexOf2 = this.B.indexOf(58);
                                    if (indexOf2 >= 0 && indexOf2 < length - 1) {
                                        String substring2 = this.B.substring(i2, indexOf2);
                                        String substring3 = this.B.substring(indexOf2 + 1);
                                        if (substring2.length() > 0 && substring3.length() > 0) {
                                            try {
                                                float parseFloat = Float.parseFloat(substring2);
                                                float parseFloat2 = Float.parseFloat(substring3);
                                                if (parseFloat > 0.0f && parseFloat2 > 0.0f) {
                                                    if (this.C != 1) {
                                                        Math.abs(parseFloat / parseFloat2);
                                                        break;
                                                    } else {
                                                        Math.abs(parseFloat2 / parseFloat);
                                                        break;
                                                    }
                                                }
                                            } catch (NumberFormatException unused5) {
                                                break;
                                            }
                                        }
                                    } else {
                                        String substring4 = this.B.substring(i2);
                                        if (substring4.length() <= 0) {
                                            break;
                                        } else {
                                            Float.parseFloat(substring4);
                                            break;
                                        }
                                    }
                                }
                                break;
                            case 45:
                                this.D = obtainStyledAttributes.getFloat(index, this.D);
                                break;
                            case 46:
                                this.E = obtainStyledAttributes.getFloat(index, this.E);
                                break;
                            case 47:
                                this.F = obtainStyledAttributes.getInt(index, 0);
                                break;
                            case 48:
                                this.G = obtainStyledAttributes.getInt(index, 0);
                                break;
                            case 49:
                                this.P = obtainStyledAttributes.getDimensionPixelOffset(index, this.P);
                                break;
                            case 50:
                                this.Q = obtainStyledAttributes.getDimensionPixelOffset(index, this.Q);
                                break;
                        }
                }
            }
            obtainStyledAttributes.recycle();
            a();
        }

        public void a() {
            this.X = false;
            this.U = true;
            this.V = true;
            if (this.width == -2 && this.S) {
                this.U = false;
                this.H = 1;
            }
            if (this.height == -2 && this.T) {
                this.V = false;
                this.I = 1;
            }
            int i2 = this.width;
            if (i2 == 0 || i2 == -1) {
                this.U = false;
                if (this.width == 0 && this.H == 1) {
                    this.width = -2;
                    this.S = true;
                }
            }
            int i3 = this.height;
            if (i3 == 0 || i3 == -1) {
                this.V = false;
                if (this.height == 0 && this.I == 1) {
                    this.height = -2;
                    this.T = true;
                }
            }
            if (this.c != -1.0f || this.a != -1 || this.b != -1) {
                this.X = true;
                this.U = true;
                this.V = true;
                if (!(this.k0 instanceof g)) {
                    this.k0 = new g();
                }
                ((g) this.k0).v(this.R);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:14:0x004c  */
        /* JADX WARNING: Removed duplicated region for block: B:17:0x0053  */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x005a  */
        /* JADX WARNING: Removed duplicated region for block: B:23:0x0060  */
        /* JADX WARNING: Removed duplicated region for block: B:26:0x0066  */
        /* JADX WARNING: Removed duplicated region for block: B:33:0x007c  */
        /* JADX WARNING: Removed duplicated region for block: B:34:0x0084  */
        @android.annotation.TargetApi(17)
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void resolveLayoutDirection(int r7) {
            /*
                r6 = this;
                int r0 = r6.leftMargin
                int r1 = r6.rightMargin
                super.resolveLayoutDirection(r7)
                r7 = -1
                r6.c0 = r7
                r6.d0 = r7
                r6.a0 = r7
                r6.b0 = r7
                r6.e0 = r7
                r6.f0 = r7
                int r2 = r6.t
                r6.e0 = r2
                int r2 = r6.v
                r6.f0 = r2
                float r2 = r6.z
                r6.g0 = r2
                int r2 = r6.a
                r6.h0 = r2
                int r2 = r6.b
                r6.i0 = r2
                float r2 = r6.c
                r6.j0 = r2
                int r2 = r6.getLayoutDirection()
                r3 = 0
                r4 = 1
                if (r4 != r2) goto L_0x0036
                r2 = 1
                goto L_0x0037
            L_0x0036:
                r2 = 0
            L_0x0037:
                if (r2 == 0) goto L_0x009a
                int r2 = r6.p
                if (r2 == r7) goto L_0x0041
                r6.c0 = r2
            L_0x003f:
                r3 = 1
                goto L_0x0048
            L_0x0041:
                int r2 = r6.q
                if (r2 == r7) goto L_0x0048
                r6.d0 = r2
                goto L_0x003f
            L_0x0048:
                int r2 = r6.r
                if (r2 == r7) goto L_0x004f
                r6.b0 = r2
                r3 = 1
            L_0x004f:
                int r2 = r6.s
                if (r2 == r7) goto L_0x0056
                r6.a0 = r2
                r3 = 1
            L_0x0056:
                int r2 = r6.x
                if (r2 == r7) goto L_0x005c
                r6.f0 = r2
            L_0x005c:
                int r2 = r6.y
                if (r2 == r7) goto L_0x0062
                r6.e0 = r2
            L_0x0062:
                r2 = 1065353216(0x3f800000, float:1.0)
                if (r3 == 0) goto L_0x006c
                float r3 = r6.z
                float r3 = r2 - r3
                r6.g0 = r3
            L_0x006c:
                boolean r3 = r6.X
                if (r3 == 0) goto L_0x00be
                int r3 = r6.R
                if (r3 != r4) goto L_0x00be
                float r3 = r6.c
                r4 = -1082130432(0xffffffffbf800000, float:-1.0)
                int r5 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
                if (r5 == 0) goto L_0x0084
                float r2 = r2 - r3
                r6.j0 = r2
                r6.h0 = r7
                r6.i0 = r7
                goto L_0x00be
            L_0x0084:
                int r2 = r6.a
                if (r2 == r7) goto L_0x008f
                r6.i0 = r2
                r6.h0 = r7
                r6.j0 = r4
                goto L_0x00be
            L_0x008f:
                int r2 = r6.b
                if (r2 == r7) goto L_0x00be
                r6.h0 = r2
                r6.i0 = r7
                r6.j0 = r4
                goto L_0x00be
            L_0x009a:
                int r2 = r6.p
                if (r2 == r7) goto L_0x00a0
                r6.b0 = r2
            L_0x00a0:
                int r2 = r6.q
                if (r2 == r7) goto L_0x00a6
                r6.a0 = r2
            L_0x00a6:
                int r2 = r6.r
                if (r2 == r7) goto L_0x00ac
                r6.c0 = r2
            L_0x00ac:
                int r2 = r6.s
                if (r2 == r7) goto L_0x00b2
                r6.d0 = r2
            L_0x00b2:
                int r2 = r6.x
                if (r2 == r7) goto L_0x00b8
                r6.e0 = r2
            L_0x00b8:
                int r2 = r6.y
                if (r2 == r7) goto L_0x00be
                r6.f0 = r2
            L_0x00be:
                int r2 = r6.r
                if (r2 != r7) goto L_0x0108
                int r2 = r6.s
                if (r2 != r7) goto L_0x0108
                int r2 = r6.q
                if (r2 != r7) goto L_0x0108
                int r2 = r6.p
                if (r2 != r7) goto L_0x0108
                int r2 = r6.f409f
                if (r2 == r7) goto L_0x00dd
                r6.c0 = r2
                int r2 = r6.rightMargin
                if (r2 > 0) goto L_0x00eb
                if (r1 <= 0) goto L_0x00eb
                r6.rightMargin = r1
                goto L_0x00eb
            L_0x00dd:
                int r2 = r6.f410g
                if (r2 == r7) goto L_0x00eb
                r6.d0 = r2
                int r2 = r6.rightMargin
                if (r2 > 0) goto L_0x00eb
                if (r1 <= 0) goto L_0x00eb
                r6.rightMargin = r1
            L_0x00eb:
                int r1 = r6.d
                if (r1 == r7) goto L_0x00fa
                r6.a0 = r1
                int r7 = r6.leftMargin
                if (r7 > 0) goto L_0x0108
                if (r0 <= 0) goto L_0x0108
                r6.leftMargin = r0
                goto L_0x0108
            L_0x00fa:
                int r1 = r6.e
                if (r1 == r7) goto L_0x0108
                r6.b0 = r1
                int r7 = r6.leftMargin
                if (r7 > 0) goto L_0x0108
                if (r0 <= 0) goto L_0x0108
                r6.leftMargin = r0
            L_0x0108:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.ConstraintLayout.a.resolveLayoutDirection(int):void");
        }

        public a(int i2, int i3) {
            super(i2, i3);
        }

        public a(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    public final ConstraintWidget a(View view) {
        if (view == this) {
            return this.f406h;
        }
        if (view == null) {
            return null;
        }
        return ((a) view.getLayoutParams()).k0;
    }

    private void a(int i2, int i3) {
        boolean z;
        boolean z2;
        int baseline;
        int i4;
        int i5;
        int i6 = i2;
        int i7 = i3;
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int childCount = getChildCount();
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                a aVar = (a) childAt.getLayoutParams();
                ConstraintWidget constraintWidget = aVar.k0;
                if (!aVar.X && !aVar.Y) {
                    constraintWidget.n(childAt.getVisibility());
                    int i9 = aVar.width;
                    int i10 = aVar.height;
                    boolean z3 = aVar.U;
                    if (z3 || aVar.V || (!z3 && aVar.H == 1) || aVar.width == -1 || (!aVar.V && (aVar.I == 1 || aVar.height == -1))) {
                        if (i9 == 0) {
                            i4 = ViewGroup.getChildMeasureSpec(i6, paddingLeft, -2);
                            z2 = true;
                        } else if (i9 == -1) {
                            i4 = ViewGroup.getChildMeasureSpec(i6, paddingLeft, -1);
                            z2 = false;
                        } else {
                            z2 = i9 == -2;
                            i4 = ViewGroup.getChildMeasureSpec(i6, paddingLeft, i9);
                        }
                        if (i10 == 0) {
                            i5 = ViewGroup.getChildMeasureSpec(i7, paddingTop, -2);
                            z = true;
                        } else if (i10 == -1) {
                            i5 = ViewGroup.getChildMeasureSpec(i7, paddingTop, -1);
                            z = false;
                        } else {
                            z = i10 == -2;
                            i5 = ViewGroup.getChildMeasureSpec(i7, paddingTop, i10);
                        }
                        childAt.measure(i4, i5);
                        f fVar = this.t;
                        if (fVar != null) {
                            fVar.a++;
                        }
                        constraintWidget.b(i9 == -2);
                        constraintWidget.a(i10 == -2);
                        i9 = childAt.getMeasuredWidth();
                        i10 = childAt.getMeasuredHeight();
                    } else {
                        z2 = false;
                        z = false;
                    }
                    constraintWidget.o(i9);
                    constraintWidget.g(i10);
                    if (z2) {
                        constraintWidget.q(i9);
                    }
                    if (z) {
                        constraintWidget.p(i10);
                    }
                    if (aVar.W && (baseline = childAt.getBaseline()) != -1) {
                        constraintWidget.f(baseline);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a(String str) {
        this.f406h.K();
        f fVar = this.t;
        if (fVar != null) {
            fVar.c++;
        }
    }

    public View a(int i2) {
        return this.e.get(i2);
    }
}
