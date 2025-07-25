package h.a.a.a.h;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import h.a.a.a.d.d;
import h.a.a.a.e.a.g;
import h.a.a.a.e.b.e;
import h.a.a.a.e.b.f;
import h.a.a.a.h.c;
import h.a.a.a.i.i;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

/* compiled from: LineChartRenderer */
public class j extends k {

    /* renamed from: h  reason: collision with root package name */
    protected g f1700h;

    /* renamed from: i  reason: collision with root package name */
    protected Paint f1701i;

    /* renamed from: j  reason: collision with root package name */
    protected WeakReference<Bitmap> f1702j;
    protected Canvas k;
    protected Bitmap.Config l = Bitmap.Config.ARGB_8888;
    protected Path m = new Path();
    protected Path n = new Path();
    private float[] o = new float[4];
    protected Path p = new Path();
    private HashMap<e, b> q = new HashMap<>();
    private float[] r = new float[2];

    /* compiled from: LineChartRenderer */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        static {
            /*
                com.github.mikephil.charting.data.LineDataSet$Mode[] r0 = com.github.mikephil.charting.data.LineDataSet.Mode.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                com.github.mikephil.charting.data.LineDataSet$Mode r1 = com.github.mikephil.charting.data.LineDataSet.Mode.LINEAR     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001d }
                com.github.mikephil.charting.data.LineDataSet$Mode r1 = com.github.mikephil.charting.data.LineDataSet.Mode.STEPPED     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.github.mikephil.charting.data.LineDataSet$Mode r1 = com.github.mikephil.charting.data.LineDataSet.Mode.CUBIC_BEZIER     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.github.mikephil.charting.data.LineDataSet$Mode r1 = com.github.mikephil.charting.data.LineDataSet.Mode.HORIZONTAL_BEZIER     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: h.a.a.a.h.j.a.<clinit>():void");
        }
    }

    public j(g gVar, h.a.a.a.a.a aVar, h.a.a.a.i.j jVar) {
        super(aVar, jVar);
        this.f1700h = gVar;
        Paint paint = new Paint(1);
        this.f1701i = paint;
        paint.setStyle(Paint.Style.FILL);
        this.f1701i.setColor(-1);
    }

    public void a() {
    }

    public void a(Canvas canvas) {
        int l2 = (int) this.a.l();
        int k2 = (int) this.a.k();
        WeakReference<Bitmap> weakReference = this.f1702j;
        Bitmap bitmap = weakReference == null ? null : (Bitmap) weakReference.get();
        if (!(bitmap != null && bitmap.getWidth() == l2 && bitmap.getHeight() == k2)) {
            if (l2 > 0 && k2 > 0) {
                bitmap = Bitmap.createBitmap(l2, k2, this.l);
                this.f1702j = new WeakReference<>(bitmap);
                this.k = new Canvas(bitmap);
            } else {
                return;
            }
        }
        bitmap.eraseColor(0);
        for (f fVar : this.f1700h.getLineData().c()) {
            if (fVar.isVisible()) {
                a(canvas, fVar);
            }
        }
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.c);
    }

    /* access modifiers changed from: protected */
    public void b(f fVar) {
        float b2 = this.b.b();
        h.a.a.a.i.g b3 = this.f1700h.b(fVar.S());
        this.f1687f.a(this.f1700h, fVar);
        this.m.reset();
        c.a aVar = this.f1687f;
        if (aVar.c >= 1) {
            Entry c = fVar.c(aVar.a);
            this.m.moveTo(c.d(), c.c() * b2);
            int i2 = this.f1687f.a + 1;
            while (true) {
                c.a aVar2 = this.f1687f;
                if (i2 > aVar2.c + aVar2.a) {
                    break;
                }
                Entry c2 = fVar.c(i2);
                float d = c.d() + ((c2.d() - c.d()) / 2.0f);
                this.m.cubicTo(d, c.c() * b2, d, c2.c() * b2, c2.d(), c2.c() * b2);
                i2++;
                c = c2;
            }
        }
        if (fVar.s0()) {
            this.n.reset();
            this.n.addPath(this.m);
            a(this.k, fVar, this.n, b3, this.f1687f);
        }
        this.c.setColor(fVar.b0());
        this.c.setStyle(Paint.Style.STROKE);
        b3.a(this.m);
        this.k.drawPath(this.m, this.c);
        this.c.setPathEffect((PathEffect) null);
    }

    public void c(Canvas canvas) {
        int i2;
        f fVar;
        Entry entry;
        if (a((h.a.a.a.e.a.e) this.f1700h)) {
            List c = this.f1700h.getLineData().c();
            for (int i3 = 0; i3 < c.size(); i3++) {
                f fVar2 = (f) c.get(i3);
                if (b(fVar2) && fVar2.X() >= 1) {
                    a((e) fVar2);
                    h.a.a.a.i.g b2 = this.f1700h.b(fVar2.S());
                    int r0 = (int) (fVar2.r0() * 1.75f);
                    if (!fVar2.d0()) {
                        r0 /= 2;
                    }
                    int i4 = r0;
                    this.f1687f.a(this.f1700h, fVar2);
                    float a2 = this.b.a();
                    float b3 = this.b.b();
                    c.a aVar = this.f1687f;
                    float[] a3 = b2.a(fVar2, a2, b3, aVar.a, aVar.b);
                    h.a.a.a.c.e W = fVar2.W();
                    h.a.a.a.i.e a4 = h.a.a.a.i.e.a(fVar2.Y());
                    a4.f1727g = i.a(a4.f1727g);
                    a4.f1728h = i.a(a4.f1728h);
                    int i5 = 0;
                    while (i5 < a3.length) {
                        float f2 = a3[i5];
                        float f3 = a3[i5 + 1];
                        if (!this.a.c(f2)) {
                            break;
                        }
                        if (!this.a.b(f2) || !this.a.f(f3)) {
                            i2 = i4;
                            fVar = fVar2;
                        } else {
                            int i6 = i5 / 2;
                            Entry c2 = fVar2.c(this.f1687f.a + i6);
                            if (fVar2.E()) {
                                entry = c2;
                                i2 = i4;
                                float f4 = f3 - ((float) i4);
                                fVar = fVar2;
                                a(canvas, W.a(c2), f2, f4, fVar2.b(i6));
                            } else {
                                entry = c2;
                                i2 = i4;
                                fVar = fVar2;
                            }
                            if (entry.b() != null && fVar.G0()) {
                                Drawable b4 = entry.b();
                                i.a(canvas, b4, (int) (f2 + a4.f1727g), (int) (f3 + a4.f1728h), b4.getIntrinsicWidth(), b4.getIntrinsicHeight());
                            }
                        }
                        i5 += 2;
                        fVar2 = fVar;
                        i4 = i2;
                    }
                    h.a.a.a.i.e.b(a4);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void d(Canvas canvas) {
        b bVar;
        Bitmap a2;
        this.c.setStyle(Paint.Style.FILL);
        float b2 = this.b.b();
        float[] fArr = this.r;
        float f2 = 0.0f;
        char c = 0;
        fArr[0] = 0.0f;
        fArr[1] = 0.0f;
        List c2 = this.f1700h.getLineData().c();
        int i2 = 0;
        while (i2 < c2.size()) {
            f fVar = (f) c2.get(i2);
            if (fVar.isVisible() && fVar.d0() && fVar.X() != 0) {
                this.f1701i.setColor(fVar.n());
                h.a.a.a.i.g b3 = this.f1700h.b(fVar.S());
                this.f1687f.a(this.f1700h, fVar);
                float r0 = fVar.r0();
                float m0 = fVar.m0();
                boolean z = fVar.F0() && m0 < r0 && m0 > f2;
                boolean z2 = z && fVar.n() == 1122867;
                if (this.q.containsKey(fVar)) {
                    bVar = this.q.get(fVar);
                } else {
                    bVar = new b(this, (a) null);
                    this.q.put(fVar, bVar);
                }
                if (bVar.a(fVar)) {
                    bVar.a(fVar, z, z2);
                }
                c.a aVar = this.f1687f;
                int i3 = aVar.c;
                int i4 = aVar.a;
                int i5 = i3 + i4;
                while (i4 <= i5) {
                    Entry c3 = fVar.c(i4);
                    if (c3 == null) {
                        break;
                    }
                    this.r[c] = c3.d();
                    this.r[1] = c3.c() * b2;
                    b3.b(this.r);
                    if (!this.a.c(this.r[c])) {
                        break;
                    }
                    if (!this.a.b(this.r[c]) || !this.a.f(this.r[1]) || (a2 = bVar.a(i4)) == null) {
                        Canvas canvas2 = canvas;
                    } else {
                        float[] fArr2 = this.r;
                        canvas.drawBitmap(a2, fArr2[c] - r0, fArr2[1] - r0, (Paint) null);
                    }
                    i4++;
                    c = 0;
                }
            }
            Canvas canvas3 = canvas;
            i2++;
            f2 = 0.0f;
            c = 0;
        }
    }

    /* compiled from: LineChartRenderer */
    private class b {
        private Path a;
        private Bitmap[] b;

        private b() {
            this.a = new Path();
        }

        /* access modifiers changed from: protected */
        public boolean a(f fVar) {
            int u = fVar.u();
            Bitmap[] bitmapArr = this.b;
            if (bitmapArr == null) {
                this.b = new Bitmap[u];
                return true;
            } else if (bitmapArr.length == u) {
                return false;
            } else {
                this.b = new Bitmap[u];
                return true;
            }
        }

        /* synthetic */ b(j jVar, a aVar) {
            this();
        }

        /* access modifiers changed from: protected */
        public void a(f fVar, boolean z, boolean z2) {
            int u = fVar.u();
            float r0 = fVar.r0();
            float m0 = fVar.m0();
            for (int i2 = 0; i2 < u; i2++) {
                Bitmap.Config config = Bitmap.Config.ARGB_4444;
                double d = (double) r0;
                Double.isNaN(d);
                int i3 = (int) (d * 2.1d);
                Bitmap createBitmap = Bitmap.createBitmap(i3, i3, config);
                Canvas canvas = new Canvas(createBitmap);
                this.b[i2] = createBitmap;
                j.this.c.setColor(fVar.a(i2));
                if (z2) {
                    this.a.reset();
                    this.a.addCircle(r0, r0, r0, Path.Direction.CW);
                    this.a.addCircle(r0, r0, m0, Path.Direction.CCW);
                    canvas.drawPath(this.a, j.this.c);
                } else {
                    canvas.drawCircle(r0, r0, r0, j.this.c);
                    if (z) {
                        canvas.drawCircle(r0, r0, m0, j.this.f1701i);
                    }
                }
            }
        }

        /* access modifiers changed from: protected */
        public Bitmap a(int i2) {
            Bitmap[] bitmapArr = this.b;
            return bitmapArr[i2 % bitmapArr.length];
        }
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, f fVar) {
        if (fVar.X() >= 1) {
            this.c.setStrokeWidth(fVar.u0());
            this.c.setPathEffect(fVar.M());
            int i2 = a.a[fVar.C0().ordinal()];
            if (i2 == 3) {
                a(fVar);
            } else if (i2 != 4) {
                b(canvas, fVar);
            } else {
                b(fVar);
            }
            this.c.setPathEffect((PathEffect) null);
        }
    }

    /* access modifiers changed from: protected */
    public void a(f fVar) {
        f fVar2 = fVar;
        float b2 = this.b.b();
        h.a.a.a.i.g b3 = this.f1700h.b(fVar.S());
        this.f1687f.a(this.f1700h, fVar2);
        float I = fVar.I();
        this.m.reset();
        c.a aVar = this.f1687f;
        if (aVar.c >= 1) {
            int i2 = aVar.a + 1;
            Entry c = fVar2.c(Math.max(i2 - 2, 0));
            Entry c2 = fVar2.c(Math.max(i2 - 1, 0));
            int i3 = -1;
            if (c2 != null) {
                this.m.moveTo(c2.d(), c2.c() * b2);
                int i4 = this.f1687f.a + 1;
                Entry entry = c2;
                while (true) {
                    c.a aVar2 = this.f1687f;
                    if (i4 > aVar2.c + aVar2.a) {
                        break;
                    }
                    if (i3 != i4) {
                        c2 = fVar2.c(i4);
                    }
                    int i5 = i4 + 1;
                    if (i5 < fVar.X()) {
                        i4 = i5;
                    }
                    Entry c3 = fVar2.c(i4);
                    this.m.cubicTo(entry.d() + ((c2.d() - c.d()) * I), (entry.c() + ((c2.c() - c.c()) * I)) * b2, c2.d() - ((c3.d() - entry.d()) * I), (c2.c() - ((c3.c() - entry.c()) * I)) * b2, c2.d(), c2.c() * b2);
                    c = entry;
                    entry = c2;
                    c2 = c3;
                    int i6 = i4;
                    i4 = i5;
                    i3 = i6;
                }
            } else {
                return;
            }
        }
        if (fVar.s0()) {
            this.n.reset();
            this.n.addPath(this.m);
            a(this.k, fVar, this.n, b3, this.f1687f);
        }
        this.c.setColor(fVar.b0());
        this.c.setStyle(Paint.Style.STROKE);
        b3.a(this.m);
        this.k.drawPath(this.m, this.c);
        this.c.setPathEffect((PathEffect) null);
    }

    /* access modifiers changed from: protected */
    public void b(Canvas canvas, f fVar) {
        f fVar2 = fVar;
        int X = fVar.X();
        boolean z = fVar.C0() == LineDataSet.Mode.STEPPED;
        int i2 = z ? 4 : 2;
        h.a.a.a.i.g b2 = this.f1700h.b(fVar.S());
        float b3 = this.b.b();
        this.c.setStyle(Paint.Style.STROKE);
        Canvas canvas2 = fVar.E0() ? this.k : canvas;
        this.f1687f.a(this.f1700h, fVar2);
        if (fVar.s0() && X > 0) {
            a(canvas, fVar2, b2, this.f1687f);
        }
        if (fVar.p0().size() > 1) {
            int i3 = i2 * 2;
            if (this.o.length <= i3) {
                this.o = new float[(i2 * 4)];
            }
            int i4 = this.f1687f.a;
            while (true) {
                c.a aVar = this.f1687f;
                if (i4 > aVar.c + aVar.a) {
                    break;
                }
                Entry c = fVar2.c(i4);
                if (c != null) {
                    this.o[0] = c.d();
                    this.o[1] = c.c() * b3;
                    if (i4 < this.f1687f.b) {
                        Entry c2 = fVar2.c(i4 + 1);
                        if (c2 == null) {
                            break;
                        } else if (z) {
                            this.o[2] = c2.d();
                            float[] fArr = this.o;
                            fArr[3] = fArr[1];
                            fArr[4] = fArr[2];
                            fArr[5] = fArr[3];
                            fArr[6] = c2.d();
                            this.o[7] = c2.c() * b3;
                        } else {
                            this.o[2] = c2.d();
                            this.o[3] = c2.c() * b3;
                        }
                    } else {
                        float[] fArr2 = this.o;
                        fArr2[2] = fArr2[0];
                        fArr2[3] = fArr2[1];
                    }
                    b2.b(this.o);
                    if (!this.a.c(this.o[0])) {
                        break;
                    } else if (this.a.b(this.o[2]) && (this.a.d(this.o[1]) || this.a.a(this.o[3]))) {
                        this.c.setColor(fVar2.e(i4));
                        canvas2.drawLines(this.o, 0, i3, this.c);
                    }
                }
                i4++;
            }
        } else {
            int i5 = X * i2;
            if (this.o.length < Math.max(i5, i2) * 2) {
                this.o = new float[(Math.max(i5, i2) * 4)];
            }
            if (fVar2.c(this.f1687f.a) != null) {
                int i6 = this.f1687f.a;
                int i7 = 0;
                while (true) {
                    c.a aVar2 = this.f1687f;
                    if (i6 > aVar2.c + aVar2.a) {
                        break;
                    }
                    Entry c3 = fVar2.c(i6 == 0 ? 0 : i6 - 1);
                    Entry c4 = fVar2.c(i6);
                    if (!(c3 == null || c4 == null)) {
                        int i8 = i7 + 1;
                        this.o[i7] = c3.d();
                        int i9 = i8 + 1;
                        this.o[i8] = c3.c() * b3;
                        if (z) {
                            int i10 = i9 + 1;
                            this.o[i9] = c4.d();
                            int i11 = i10 + 1;
                            this.o[i10] = c3.c() * b3;
                            int i12 = i11 + 1;
                            this.o[i11] = c4.d();
                            i9 = i12 + 1;
                            this.o[i12] = c3.c() * b3;
                        }
                        int i13 = i9 + 1;
                        this.o[i9] = c4.d();
                        this.o[i13] = c4.c() * b3;
                        i7 = i13 + 1;
                    }
                    i6++;
                }
                if (i7 > 0) {
                    b2.b(this.o);
                    this.c.setColor(fVar.b0());
                    canvas2.drawLines(this.o, 0, Math.max((this.f1687f.c + 1) * i2, i2) * 2, this.c);
                }
            }
        }
        this.c.setPathEffect((PathEffect) null);
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, f fVar, Path path, h.a.a.a.i.g gVar, c.a aVar) {
        float a2 = fVar.c0().a(fVar, this.f1700h);
        path.lineTo(fVar.c(aVar.a + aVar.c).d(), a2);
        path.lineTo(fVar.c(aVar.a).d(), a2);
        path.close();
        gVar.a(path);
        Drawable P = fVar.P();
        if (P != null) {
            a(canvas, path, P);
        } else {
            a(canvas, path, fVar.y(), fVar.Q());
        }
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, f fVar, h.a.a.a.i.g gVar, c.a aVar) {
        int i2;
        int i3;
        Path path = this.p;
        int i4 = aVar.a;
        int i5 = aVar.c + i4;
        int i6 = 0;
        do {
            i2 = (i6 * 128) + i4;
            i3 = i2 + 128;
            if (i3 > i5) {
                i3 = i5;
            }
            if (i2 <= i3) {
                a(fVar, i2, i3, path);
                gVar.a(path);
                Drawable P = fVar.P();
                if (P != null) {
                    a(canvas, path, P);
                } else {
                    a(canvas, path, fVar.y(), fVar.Q());
                }
            }
            i6++;
        } while (i2 <= i3);
    }

    private void a(f fVar, int i2, int i3, Path path) {
        float a2 = fVar.c0().a(fVar, this.f1700h);
        float b2 = this.b.b();
        boolean z = fVar.C0() == LineDataSet.Mode.STEPPED;
        path.reset();
        Entry c = fVar.c(i2);
        path.moveTo(c.d(), a2);
        path.lineTo(c.d(), c.c() * b2);
        Entry entry = null;
        int i4 = i2 + 1;
        while (i4 <= i3) {
            entry = fVar.c(i4);
            if (z) {
                path.lineTo(entry.d(), c.c() * b2);
            }
            path.lineTo(entry.d(), entry.c() * b2);
            i4++;
            c = entry;
        }
        if (entry != null) {
            path.lineTo(entry.d(), a2);
        }
        path.close();
    }

    public void b(Canvas canvas) {
        d(canvas);
    }

    public void b() {
        Canvas canvas = this.k;
        if (canvas != null) {
            canvas.setBitmap((Bitmap) null);
            this.k = null;
        }
        WeakReference<Bitmap> weakReference = this.f1702j;
        if (weakReference != null) {
            Bitmap bitmap = (Bitmap) weakReference.get();
            if (bitmap != null) {
                bitmap.recycle();
            }
            this.f1702j.clear();
            this.f1702j = null;
        }
    }

    public void a(Canvas canvas, String str, float f2, float f3, int i2) {
        this.e.setColor(i2);
        canvas.drawText(str, f2, f3, this.e);
    }

    public void a(Canvas canvas, d[] dVarArr) {
        com.github.mikephil.charting.data.j lineData = this.f1700h.getLineData();
        for (d dVar : dVarArr) {
            f fVar = (f) lineData.a(dVar.c());
            if (fVar != null && fVar.e0()) {
                Entry a2 = fVar.a(dVar.g(), dVar.i());
                if (a(a2, fVar)) {
                    h.a.a.a.i.d a3 = this.f1700h.b(fVar.S()).a(a2.d(), a2.c() * this.b.b());
                    dVar.a((float) a3.f1724g, (float) a3.f1725h);
                    a(canvas, (float) a3.f1724g, (float) a3.f1725h, fVar);
                }
            }
        }
    }
}
