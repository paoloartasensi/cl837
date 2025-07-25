package com.chileaf.fitness.ui.c;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import com.chileaf.fitness.R$style;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* compiled from: LoadingDialog */
public class f extends Dialog {
    /* access modifiers changed from: private */
    public b e;
    /* access modifiers changed from: private */

    /* renamed from: f  reason: collision with root package name */
    public e f1233f;
    /* access modifiers changed from: private */

    /* renamed from: g  reason: collision with root package name */
    public int f1234g;
    /* access modifiers changed from: private */

    /* renamed from: h  reason: collision with root package name */
    public Handler f1235h;
    /* access modifiers changed from: private */

    /* renamed from: i  reason: collision with root package name */
    public Runnable f1236i;
    /* access modifiers changed from: private */

    /* renamed from: j  reason: collision with root package name */
    public c f1237j;

    /* compiled from: LoadingDialog */
    class a implements Runnable {
        a() {
        }

        public void run() {
            int a = f.this.f1234g % f.this.e.f1241i;
            if (f.this.e.n == 100) {
                f.this.f1233f.a(a);
            } else {
                f.this.f1233f.a((f.this.e.f1241i - 1) - a);
            }
            if (a == 0) {
                int unused = f.this.f1234g = 1;
            } else {
                f.b(f.this);
            }
            f.this.f1235h.postDelayed(this, (long) (1000.0f / f.this.e.o));
        }
    }

    /* compiled from: LoadingDialog */
    public static final class b {
        /* access modifiers changed from: private */
        public Context a;
        /* access modifiers changed from: private */
        public int b;
        /* access modifiers changed from: private */
        public float c;
        /* access modifiers changed from: private */
        public float d;
        /* access modifiers changed from: private */
        public float e;
        /* access modifiers changed from: private */

        /* renamed from: f  reason: collision with root package name */
        public int f1238f;
        /* access modifiers changed from: private */

        /* renamed from: g  reason: collision with root package name */
        public int f1239g;
        /* access modifiers changed from: private */

        /* renamed from: h  reason: collision with root package name */
        public int f1240h;
        /* access modifiers changed from: private */

        /* renamed from: i  reason: collision with root package name */
        public int f1241i;
        /* access modifiers changed from: private */

        /* renamed from: j  reason: collision with root package name */
        public int f1242j;
        /* access modifiers changed from: private */
        public float k;
        /* access modifiers changed from: private */
        public float l;
        /* access modifiers changed from: private */
        public float m;
        /* access modifiers changed from: private */
        public int n;
        /* access modifiers changed from: private */
        public float o;
        /* access modifiers changed from: private */
        public String p;
        /* access modifiers changed from: private */
        public int q;
        /* access modifiers changed from: private */
        public float r;
        /* access modifiers changed from: private */
        public float s;
        /* access modifiers changed from: private */
        public int t;
        /* access modifiers changed from: private */
        public boolean u;
        /* access modifiers changed from: private */
        public boolean v;

        public b(Context context) {
            this(context, R$style.dialog_progress);
        }

        private b(Context context, int i2) {
            this.c = 0.18f;
            this.d = 0.55f;
            this.e = 0.27f;
            this.f1238f = -16777216;
            this.f1239g = -16711681;
            this.f1240h = -1;
            this.f1241i = 12;
            this.f1242j = 8;
            this.k = 0.5f;
            this.l = 20.0f;
            this.m = 0.5f;
            this.n = 100;
            this.o = 9.0f;
            this.p = null;
            this.q = -1;
            this.r = 0.5f;
            this.t = 40;
            this.u = true;
            this.v = false;
            this.a = context;
            this.b = i2;
            this.s = (float) a(16.0f);
        }

        public f a() {
            return new f(this, (a) null);
        }

        private int a(float f2) {
            return Math.round(TypedValue.applyDimension(1, f2, Resources.getSystem().getDisplayMetrics()));
        }
    }

    /* compiled from: LoadingDialog */
    private static class c implements DialogInterface.OnDismissListener {
        private c() {
        }

        public void onDismiss(DialogInterface dialogInterface) {
            f fVar = (f) dialogInterface;
            if (fVar.f1235h != null) {
                fVar.f1235h.removeCallbacks(fVar.f1236i);
            }
            int unused = fVar.f1234g = 0;
            e unused2 = fVar.f1233f = null;
            c unused3 = fVar.f1237j = null;
        }

        /* synthetic */ c(a aVar) {
            this();
        }
    }

    /* compiled from: LoadingDialog */
    private static final class d {
        private double[] a;
        private double[] b;

        /* synthetic */ d(int i2, a aVar) {
            this(i2);
        }

        private d(int i2) {
            this.a = new double[i2];
            this.b = new double[i2];
            double d = (double) i2;
            Double.isNaN(d);
            double d2 = 6.283185307179586d / d;
            for (int i3 = 0; i3 < i2; i3++) {
                double d3 = (double) i3;
                Double.isNaN(d3);
                double d4 = d3 * d2;
                this.a[i3] = Math.cos(d4);
                this.b[i3] = Math.sin(d4);
            }
        }

        /* access modifiers changed from: private */
        public List<C0070f> a(int i2, int i3, int i4, int i5, int i6) {
            int i7 = i2;
            int i8 = i5;
            ArrayList arrayList = new ArrayList(i8);
            double d = (double) i7;
            Double.isNaN(d);
            double d2 = d / 2.0d;
            double d3 = (double) i6;
            Double.isNaN(d3);
            double d4 = d3 / 2.0d;
            double d5 = (double) (i7 - i3);
            Double.isNaN(d5);
            double d6 = d5 / 2.0d;
            double d7 = (double) i4;
            Double.isNaN(d7);
            double d8 = d7 / 2.0d;
            int i9 = 0;
            while (i9 < i8) {
                double[] dArr = this.a;
                double[] dArr2 = this.b;
                arrayList.add(new C0070f((int) (d4 - (dArr[i9] * d6)), (int) (d2 + (dArr2[i9] * d6)), (int) (d4 - (dArr[i9] * d8)), (int) ((dArr2[i9] * d8) + d2), (a) null));
                i9++;
                d6 = d6;
                d8 = d8;
            }
            return arrayList;
        }

        /* access modifiers changed from: private */
        public int[] a(int i2, int i3, int i4, int i5) {
            int i6 = i4;
            int[] iArr = new int[i6];
            int red = Color.red(i2);
            int green = Color.green(i2);
            int blue = Color.blue(i2);
            int red2 = Color.red(i3);
            int green2 = Color.green(i3);
            int blue2 = Color.blue(i3);
            double d = (double) (red2 - red);
            double d2 = (double) (i6 - 1);
            Double.isNaN(d);
            Double.isNaN(d2);
            double d3 = d / d2;
            double d4 = (double) (green2 - green);
            Double.isNaN(d4);
            Double.isNaN(d2);
            double d5 = d4 / d2;
            double d6 = (double) (blue2 - blue);
            Double.isNaN(d6);
            Double.isNaN(d2);
            double d7 = d6 / d2;
            int i7 = 0;
            while (i7 < i6) {
                double d8 = (double) red;
                double d9 = (double) i7;
                Double.isNaN(d9);
                Double.isNaN(d8);
                double d10 = d3;
                double d11 = (double) green;
                Double.isNaN(d9);
                Double.isNaN(d11);
                int i8 = red;
                int i9 = green;
                double d12 = (double) blue;
                Double.isNaN(d9);
                Double.isNaN(d12);
                iArr[i7] = Color.argb(i5, (int) (d8 + (d3 * d9)), (int) (d11 + (d5 * d9)), (int) (d12 + (d9 * d7)));
                i7++;
                red = i8;
                green = i9;
                d3 = d10;
            }
            return iArr;
        }
    }

    /* compiled from: LoadingDialog */
    private static final class e extends View {
        private int e;

        /* renamed from: f  reason: collision with root package name */
        private int f1243f;

        /* renamed from: g  reason: collision with root package name */
        private int f1244g;

        /* renamed from: h  reason: collision with root package name */
        private float f1245h;

        /* renamed from: i  reason: collision with root package name */
        private RectF f1246i;

        /* renamed from: j  reason: collision with root package name */
        private Paint f1247j;
        private Paint k;
        private Paint l;
        private List<C0070f> m;
        private int[] n;
        private Handler o;
        private int p;
        private String q;
        private int r;
        private int s;
        private int t;
        private boolean u;

        /* compiled from: LoadingDialog */
        private static class a extends Handler {
            WeakReference<e> a;

            /* synthetic */ a(e eVar, a aVar) {
                this(eVar);
            }

            public void handleMessage(Message message) {
                e eVar = (e) this.a.get();
                if (eVar != null) {
                    eVar.invalidate();
                }
            }

            private a(e eVar) {
                this.a = new WeakReference<>(eVar);
            }
        }

        /* synthetic */ e(Context context, int i2, int i3, float f2, float f3, int i4, int i5, float f4, float f5, float f6, int i6, int i7, String str, float f7, int i8, float f8, int i9, boolean z, a aVar) {
            this(context, i2, i3, f2, f3, i4, i5, f4, f5, f6, i6, i7, str, f7, i8, f8, i9, z);
        }

        /* access modifiers changed from: protected */
        public void onDraw(Canvas canvas) {
            RectF rectF = this.f1246i;
            float f2 = this.f1245h;
            canvas.drawRoundRect(rectF, f2, f2, this.f1247j);
            for (int i2 = 0; i2 < this.f1244g; i2++) {
                C0070f fVar = this.m.get(i2);
                this.k.setColor(this.n[(this.p + i2) % this.f1244g]);
                canvas.drawLine((float) fVar.c(), (float) fVar.d(), (float) fVar.a(), (float) fVar.b(), this.k);
            }
            if (this.q != null) {
                canvas.drawText(this.q, (float) ((this.f1243f / 2) - (this.s / 2)), ((float) this.e) + (((-this.l.ascent()) + this.l.descent()) / 2.0f), this.l);
            }
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int i2, int i3) {
            if (this.u) {
                setMeasuredDimension((int) this.f1246i.width(), this.e + this.r + this.t);
                return;
            }
            int i4 = this.e;
            setMeasuredDimension(i4, this.r + i4 + this.t);
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        private e(Context context, int i2, int i3, float f2, float f3, int i4, int i5, float f4, float f5, float f6, int i6, int i7, String str, float f7, int i8, float f8, int i9, boolean z) {
            super(context);
            int i10 = i2;
            int i11 = i3;
            this.o = new a(this, (a) null);
            this.t = i9;
            a(i2, i3, f2, f3, i4, i5, f4, f5, f6, i6, i7, str, f7, i8, f8, z);
        }

        private void a(int i2, int i3, float f2, float f3, int i4, int i5, float f4, float f5, float f6, int i6, int i7, String str, float f7, int i8, float f8, boolean z) {
            int i9 = i5;
            String str2 = str;
            this.u = (str2 == null || str.length() == 0 || !z) ? false : true;
            this.e = i2;
            this.f1244g = i9;
            this.f1245h = f3;
            Paint paint = new Paint();
            this.f1247j = paint;
            paint.setAntiAlias(true);
            int i10 = i3;
            this.f1247j.setColor(i3);
            this.f1247j.setAlpha((int) (f2 * 255.0f));
            Paint paint2 = new Paint();
            this.k = paint2;
            paint2.setAntiAlias(true);
            this.k.setStrokeWidth((float) i4);
            this.k.setStrokeCap(Paint.Cap.ROUND);
            if (str2 == null || str.length() == 0) {
                this.t = 0;
            } else {
                this.q = str2;
                Paint paint3 = new Paint();
                this.l = paint3;
                paint3.setAntiAlias(true);
                this.l.setColor(i8);
                this.l.setAlpha((int) (f8 * 255.0f));
                this.l.setTextSize(f7);
                Rect rect = new Rect();
                this.l.getTextBounds(str2, 0, str.length(), rect);
                this.r = rect.bottom - rect.top;
                this.s = rect.right - rect.left;
            }
            if (this.u) {
                int i11 = this.s;
                int i12 = this.t;
                RectF rectF = new RectF(0.0f, 0.0f, (float) Math.max(i11 + (i12 * 2), this.e + this.r + i12), (float) (this.e + this.r + this.t));
                this.f1246i = rectF;
                this.f1243f = (int) rectF.width();
            } else {
                int i13 = this.e;
                this.f1246i = new RectF(0.0f, 0.0f, (float) i13, (float) (i13 + this.r + this.t));
                this.f1243f = this.e;
            }
            d dVar = new d(i9, (a) null);
            int i14 = this.e;
            this.m = dVar.a(i14, (int) (((float) i14) * f5), (int) (((float) i14) * f6), i5, this.f1243f);
            this.n = dVar.a(i6, i7, i9, (int) (f4 * 255.0f));
        }

        /* access modifiers changed from: private */
        public void a(int i2) {
            this.p = i2;
            this.o.sendEmptyMessage(0);
        }
    }

    /* renamed from: com.chileaf.fitness.ui.c.f$f  reason: collision with other inner class name */
    /* compiled from: LoadingDialog */
    private static final class C0070f {
        private int a;
        private int b;
        private int c;
        private int d;

        /* synthetic */ C0070f(int i2, int i3, int i4, int i5, a aVar) {
            this(i2, i3, i4, i5);
        }

        private C0070f(int i2, int i3, int i4, int i5) {
            this.a = i2;
            this.b = i3;
            this.c = i4;
            this.d = i5;
        }

        /* access modifiers changed from: private */
        public int a() {
            return this.c;
        }

        /* access modifiers changed from: private */
        public int b() {
            return this.d;
        }

        /* access modifiers changed from: private */
        public int c() {
            return this.a;
        }

        /* access modifiers changed from: private */
        public int d() {
            return this.b;
        }
    }

    /* synthetic */ f(b bVar, a aVar) {
        this(bVar);
    }

    static /* synthetic */ int b(f fVar) {
        int i2 = fVar.f1234g;
        fVar.f1234g = i2 + 1;
        return i2;
    }

    public void show() {
        if (this.f1233f == null) {
            e eVar = r2;
            e eVar2 = new e(this.e.a, (int) (((float) a(this.e.a)) * this.e.c), this.e.f1238f, this.e.m, this.e.l, this.e.f1242j, this.e.f1241i, this.e.k, this.e.d, this.e.e, this.e.f1239g, this.e.f1240h, this.e.p, this.e.s, this.e.q, this.e.r, this.e.t, this.e.u, (a) null);
            this.f1233f = eVar;
        }
        super.setContentView(this.f1233f);
        super.show();
        this.f1235h.postDelayed(this.f1236i, (long) (1000.0f / this.e.o));
    }

    private f(b bVar) {
        super(bVar.a, bVar.b);
        this.f1234g = 0;
        this.f1235h = new Handler();
        this.f1236i = new a();
        this.e = bVar;
        setCanceledOnTouchOutside(false);
        setCancelable(this.e.v);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        c cVar = new c((a) null);
        this.f1237j = cVar;
        setOnDismissListener(cVar);
    }

    private int a(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return Math.min(point.x, point.y);
    }
}
