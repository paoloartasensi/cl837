package androidx.vectordrawable.a.a;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import androidx.core.a.b;
import java.util.ArrayDeque;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: VectorDrawableCompat */
public class i extends h {
    static final PorterDuff.Mode n = PorterDuff.Mode.SRC_IN;

    /* renamed from: f  reason: collision with root package name */
    private h f915f;

    /* renamed from: g  reason: collision with root package name */
    private PorterDuffColorFilter f916g;

    /* renamed from: h  reason: collision with root package name */
    private ColorFilter f917h;

    /* renamed from: i  reason: collision with root package name */
    private boolean f918i;

    /* renamed from: j  reason: collision with root package name */
    private boolean f919j;
    private final float[] k;
    private final Matrix l;
    private final Rect m;

    /* compiled from: VectorDrawableCompat */
    private static class b extends f {
        b() {
        }

        public void a(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            if (androidx.core.content.c.g.a(xmlPullParser, "pathData")) {
                TypedArray a = androidx.core.content.c.g.a(resources, theme, attributeSet, a.d);
                a(a, xmlPullParser);
                a.recycle();
            }
        }

        public boolean b() {
            return true;
        }

        b(b bVar) {
            super(bVar);
        }

        private void a(TypedArray typedArray, XmlPullParser xmlPullParser) {
            String string = typedArray.getString(0);
            if (string != null) {
                this.b = string;
            }
            String string2 = typedArray.getString(1);
            if (string2 != null) {
                this.a = androidx.core.a.b.a(string2);
            }
            this.c = androidx.core.content.c.g.b(typedArray, xmlPullParser, "fillType", 2, 0);
        }
    }

    /* compiled from: VectorDrawableCompat */
    private static abstract class e {
        private e() {
        }

        public boolean a() {
            return false;
        }

        public boolean a(int[] iArr) {
            return false;
        }
    }

    /* compiled from: VectorDrawableCompat */
    private static class h extends Drawable.ConstantState {
        int a;
        g b;
        ColorStateList c;
        PorterDuff.Mode d;
        boolean e;

        /* renamed from: f  reason: collision with root package name */
        Bitmap f935f;

        /* renamed from: g  reason: collision with root package name */
        ColorStateList f936g;

        /* renamed from: h  reason: collision with root package name */
        PorterDuff.Mode f937h;

        /* renamed from: i  reason: collision with root package name */
        int f938i;

        /* renamed from: j  reason: collision with root package name */
        boolean f939j;
        boolean k;
        Paint l;

        public h(h hVar) {
            this.c = null;
            this.d = i.n;
            if (hVar != null) {
                this.a = hVar.a;
                g gVar = new g(hVar.b);
                this.b = gVar;
                if (hVar.b.e != null) {
                    gVar.e = new Paint(hVar.b.e);
                }
                if (hVar.b.d != null) {
                    this.b.d = new Paint(hVar.b.d);
                }
                this.c = hVar.c;
                this.d = hVar.d;
                this.e = hVar.e;
            }
        }

        public void a(Canvas canvas, ColorFilter colorFilter, Rect rect) {
            canvas.drawBitmap(this.f935f, (Rect) null, rect, a(colorFilter));
        }

        public boolean b() {
            return this.b.getRootAlpha() < 255;
        }

        public void c(int i2, int i3) {
            this.f935f.eraseColor(0);
            this.b.a(new Canvas(this.f935f), i2, i3, (ColorFilter) null);
        }

        public void d() {
            this.f936g = this.c;
            this.f937h = this.d;
            this.f938i = this.b.getRootAlpha();
            this.f939j = this.e;
            this.k = false;
        }

        public int getChangingConfigurations() {
            return this.a;
        }

        public Drawable newDrawable() {
            return new i(this);
        }

        public void b(int i2, int i3) {
            if (this.f935f == null || !a(i2, i3)) {
                this.f935f = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
                this.k = true;
            }
        }

        public Drawable newDrawable(Resources resources) {
            return new i(this);
        }

        public Paint a(ColorFilter colorFilter) {
            if (!b() && colorFilter == null) {
                return null;
            }
            if (this.l == null) {
                Paint paint = new Paint();
                this.l = paint;
                paint.setFilterBitmap(true);
            }
            this.l.setAlpha(this.b.getRootAlpha());
            this.l.setColorFilter(colorFilter);
            return this.l;
        }

        public boolean c() {
            return this.b.a();
        }

        public boolean a(int i2, int i3) {
            return i2 == this.f935f.getWidth() && i3 == this.f935f.getHeight();
        }

        public boolean a() {
            return !this.k && this.f936g == this.c && this.f937h == this.d && this.f939j == this.e && this.f938i == this.b.getRootAlpha();
        }

        public h() {
            this.c = null;
            this.d = i.n;
            this.b = new g();
        }

        public boolean a(int[] iArr) {
            boolean a2 = this.b.a(iArr);
            this.k |= a2;
            return a2;
        }
    }

    i() {
        this.f919j = true;
        this.k = new float[9];
        this.l = new Matrix();
        this.m = new Rect();
        this.f915f = new h();
    }

    public static i createFromXmlInner(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        i iVar = new i();
        iVar.inflate(resources, xmlPullParser, attributeSet, theme);
        return iVar;
    }

    /* access modifiers changed from: package-private */
    public Object a(String str) {
        return this.f915f.b.p.get(str);
    }

    public boolean canApplyTheme() {
        Drawable drawable = this.e;
        if (drawable == null) {
            return false;
        }
        androidx.core.graphics.drawable.a.a(drawable);
        return false;
    }

    public void draw(Canvas canvas) {
        Drawable drawable = this.e;
        if (drawable != null) {
            drawable.draw(canvas);
            return;
        }
        copyBounds(this.m);
        if (this.m.width() > 0 && this.m.height() > 0) {
            ColorFilter colorFilter = this.f917h;
            if (colorFilter == null) {
                colorFilter = this.f916g;
            }
            canvas.getMatrix(this.l);
            this.l.getValues(this.k);
            float abs = Math.abs(this.k[0]);
            float abs2 = Math.abs(this.k[4]);
            float abs3 = Math.abs(this.k[1]);
            float abs4 = Math.abs(this.k[3]);
            if (!(abs3 == 0.0f && abs4 == 0.0f)) {
                abs = 1.0f;
                abs2 = 1.0f;
            }
            int min = Math.min(2048, (int) (((float) this.m.width()) * abs));
            int min2 = Math.min(2048, (int) (((float) this.m.height()) * abs2));
            if (min > 0 && min2 > 0) {
                int save = canvas.save();
                Rect rect = this.m;
                canvas.translate((float) rect.left, (float) rect.top);
                if (a()) {
                    canvas.translate((float) this.m.width(), 0.0f);
                    canvas.scale(-1.0f, 1.0f);
                }
                this.m.offsetTo(0, 0);
                this.f915f.b(min, min2);
                if (!this.f919j) {
                    this.f915f.c(min, min2);
                } else if (!this.f915f.a()) {
                    this.f915f.c(min, min2);
                    this.f915f.d();
                }
                this.f915f.a(canvas, colorFilter, this.m);
                canvas.restoreToCount(save);
            }
        }
    }

    public int getAlpha() {
        Drawable drawable = this.e;
        if (drawable != null) {
            return androidx.core.graphics.drawable.a.c(drawable);
        }
        return this.f915f.b.getRootAlpha();
    }

    public int getChangingConfigurations() {
        Drawable drawable = this.e;
        if (drawable != null) {
            return drawable.getChangingConfigurations();
        }
        return super.getChangingConfigurations() | this.f915f.getChangingConfigurations();
    }

    public ColorFilter getColorFilter() {
        Drawable drawable = this.e;
        if (drawable != null) {
            return androidx.core.graphics.drawable.a.d(drawable);
        }
        return this.f917h;
    }

    public Drawable.ConstantState getConstantState() {
        if (this.e != null && Build.VERSION.SDK_INT >= 24) {
            return new C0052i(this.e.getConstantState());
        }
        this.f915f.a = getChangingConfigurations();
        return this.f915f;
    }

    public int getIntrinsicHeight() {
        Drawable drawable = this.e;
        if (drawable != null) {
            return drawable.getIntrinsicHeight();
        }
        return (int) this.f915f.b.f934j;
    }

    public int getIntrinsicWidth() {
        Drawable drawable = this.e;
        if (drawable != null) {
            return drawable.getIntrinsicWidth();
        }
        return (int) this.f915f.b.f933i;
    }

    public int getOpacity() {
        Drawable drawable = this.e;
        if (drawable != null) {
            return drawable.getOpacity();
        }
        return -3;
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) {
        Drawable drawable = this.e;
        if (drawable != null) {
            drawable.inflate(resources, xmlPullParser, attributeSet);
        } else {
            inflate(resources, xmlPullParser, attributeSet, (Resources.Theme) null);
        }
    }

    public void invalidateSelf() {
        Drawable drawable = this.e;
        if (drawable != null) {
            drawable.invalidateSelf();
        } else {
            super.invalidateSelf();
        }
    }

    public boolean isAutoMirrored() {
        Drawable drawable = this.e;
        if (drawable != null) {
            return androidx.core.graphics.drawable.a.f(drawable);
        }
        return this.f915f.e;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0019, code lost:
        r0 = r1.f915f.c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x000f, code lost:
        r0 = r1.f915f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isStateful() {
        /*
            r1 = this;
            android.graphics.drawable.Drawable r0 = r1.e
            if (r0 == 0) goto L_0x0009
            boolean r0 = r0.isStateful()
            return r0
        L_0x0009:
            boolean r0 = super.isStateful()
            if (r0 != 0) goto L_0x0028
            androidx.vectordrawable.a.a.i$h r0 = r1.f915f
            if (r0 == 0) goto L_0x0026
            boolean r0 = r0.c()
            if (r0 != 0) goto L_0x0028
            androidx.vectordrawable.a.a.i$h r0 = r1.f915f
            android.content.res.ColorStateList r0 = r0.c
            if (r0 == 0) goto L_0x0026
            boolean r0 = r0.isStateful()
            if (r0 == 0) goto L_0x0026
            goto L_0x0028
        L_0x0026:
            r0 = 0
            goto L_0x0029
        L_0x0028:
            r0 = 1
        L_0x0029:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.vectordrawable.a.a.i.isStateful():boolean");
    }

    public Drawable mutate() {
        Drawable drawable = this.e;
        if (drawable != null) {
            drawable.mutate();
            return this;
        }
        if (!this.f918i && super.mutate() == this) {
            this.f915f = new h(this.f915f);
            this.f918i = true;
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        Drawable drawable = this.e;
        if (drawable != null) {
            drawable.setBounds(rect);
        }
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        PorterDuff.Mode mode;
        Drawable drawable = this.e;
        if (drawable != null) {
            return drawable.setState(iArr);
        }
        boolean z = false;
        h hVar = this.f915f;
        ColorStateList colorStateList = hVar.c;
        if (!(colorStateList == null || (mode = hVar.d) == null)) {
            this.f916g = a(this.f916g, colorStateList, mode);
            invalidateSelf();
            z = true;
        }
        if (!hVar.c() || !hVar.a(iArr)) {
            return z;
        }
        invalidateSelf();
        return true;
    }

    public void scheduleSelf(Runnable runnable, long j2) {
        Drawable drawable = this.e;
        if (drawable != null) {
            drawable.scheduleSelf(runnable, j2);
        } else {
            super.scheduleSelf(runnable, j2);
        }
    }

    public void setAlpha(int i2) {
        Drawable drawable = this.e;
        if (drawable != null) {
            drawable.setAlpha(i2);
        } else if (this.f915f.b.getRootAlpha() != i2) {
            this.f915f.b.setRootAlpha(i2);
            invalidateSelf();
        }
    }

    public void setAutoMirrored(boolean z) {
        Drawable drawable = this.e;
        if (drawable != null) {
            androidx.core.graphics.drawable.a.a(drawable, z);
        } else {
            this.f915f.e = z;
        }
    }

    public void setTint(int i2) {
        Drawable drawable = this.e;
        if (drawable != null) {
            androidx.core.graphics.drawable.a.b(drawable, i2);
        } else {
            setTintList(ColorStateList.valueOf(i2));
        }
    }

    public void setTintList(ColorStateList colorStateList) {
        Drawable drawable = this.e;
        if (drawable != null) {
            androidx.core.graphics.drawable.a.a(drawable, colorStateList);
            return;
        }
        h hVar = this.f915f;
        if (hVar.c != colorStateList) {
            hVar.c = colorStateList;
            this.f916g = a(this.f916g, colorStateList, hVar.d);
            invalidateSelf();
        }
    }

    public void setTintMode(PorterDuff.Mode mode) {
        Drawable drawable = this.e;
        if (drawable != null) {
            androidx.core.graphics.drawable.a.a(drawable, mode);
            return;
        }
        h hVar = this.f915f;
        if (hVar.d != mode) {
            hVar.d = mode;
            this.f916g = a(this.f916g, hVar.c, mode);
            invalidateSelf();
        }
    }

    public boolean setVisible(boolean z, boolean z2) {
        Drawable drawable = this.e;
        if (drawable != null) {
            return drawable.setVisible(z, z2);
        }
        return super.setVisible(z, z2);
    }

    public void unscheduleSelf(Runnable runnable) {
        Drawable drawable = this.e;
        if (drawable != null) {
            drawable.unscheduleSelf(runnable);
        } else {
            super.unscheduleSelf(runnable);
        }
    }

    /* renamed from: androidx.vectordrawable.a.a.i$i  reason: collision with other inner class name */
    /* compiled from: VectorDrawableCompat */
    private static class C0052i extends Drawable.ConstantState {
        private final Drawable.ConstantState a;

        public C0052i(Drawable.ConstantState constantState) {
            this.a = constantState;
        }

        public boolean canApplyTheme() {
            return this.a.canApplyTheme();
        }

        public int getChangingConfigurations() {
            return this.a.getChangingConfigurations();
        }

        public Drawable newDrawable() {
            i iVar = new i();
            iVar.e = (VectorDrawable) this.a.newDrawable();
            return iVar;
        }

        public Drawable newDrawable(Resources resources) {
            i iVar = new i();
            iVar.e = (VectorDrawable) this.a.newDrawable(resources);
            return iVar;
        }

        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            i iVar = new i();
            iVar.e = (VectorDrawable) this.a.newDrawable(resources, theme);
            return iVar;
        }
    }

    /* access modifiers changed from: package-private */
    public PorterDuffColorFilter a(PorterDuffColorFilter porterDuffColorFilter, ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        Drawable drawable = this.e;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
            return;
        }
        this.f917h = colorFilter;
        invalidateSelf();
    }

    /* compiled from: VectorDrawableCompat */
    private static class c extends f {
        private int[] e;

        /* renamed from: f  reason: collision with root package name */
        androidx.core.content.c.b f920f;

        /* renamed from: g  reason: collision with root package name */
        float f921g = 0.0f;

        /* renamed from: h  reason: collision with root package name */
        androidx.core.content.c.b f922h;

        /* renamed from: i  reason: collision with root package name */
        float f923i = 1.0f;

        /* renamed from: j  reason: collision with root package name */
        float f924j = 1.0f;
        float k = 0.0f;
        float l = 1.0f;
        float m = 0.0f;
        Paint.Cap n = Paint.Cap.BUTT;
        Paint.Join o = Paint.Join.MITER;
        float p = 4.0f;

        c() {
        }

        private Paint.Cap a(int i2, Paint.Cap cap) {
            if (i2 == 0) {
                return Paint.Cap.BUTT;
            }
            if (i2 != 1) {
                return i2 != 2 ? cap : Paint.Cap.SQUARE;
            }
            return Paint.Cap.ROUND;
        }

        /* access modifiers changed from: package-private */
        public float getFillAlpha() {
            return this.f924j;
        }

        /* access modifiers changed from: package-private */
        public int getFillColor() {
            return this.f922h.a();
        }

        /* access modifiers changed from: package-private */
        public float getStrokeAlpha() {
            return this.f923i;
        }

        /* access modifiers changed from: package-private */
        public int getStrokeColor() {
            return this.f920f.a();
        }

        /* access modifiers changed from: package-private */
        public float getStrokeWidth() {
            return this.f921g;
        }

        /* access modifiers changed from: package-private */
        public float getTrimPathEnd() {
            return this.l;
        }

        /* access modifiers changed from: package-private */
        public float getTrimPathOffset() {
            return this.m;
        }

        /* access modifiers changed from: package-private */
        public float getTrimPathStart() {
            return this.k;
        }

        /* access modifiers changed from: package-private */
        public void setFillAlpha(float f2) {
            this.f924j = f2;
        }

        /* access modifiers changed from: package-private */
        public void setFillColor(int i2) {
            this.f922h.a(i2);
        }

        /* access modifiers changed from: package-private */
        public void setStrokeAlpha(float f2) {
            this.f923i = f2;
        }

        /* access modifiers changed from: package-private */
        public void setStrokeColor(int i2) {
            this.f920f.a(i2);
        }

        /* access modifiers changed from: package-private */
        public void setStrokeWidth(float f2) {
            this.f921g = f2;
        }

        /* access modifiers changed from: package-private */
        public void setTrimPathEnd(float f2) {
            this.l = f2;
        }

        /* access modifiers changed from: package-private */
        public void setTrimPathOffset(float f2) {
            this.m = f2;
        }

        /* access modifiers changed from: package-private */
        public void setTrimPathStart(float f2) {
            this.k = f2;
        }

        private Paint.Join a(int i2, Paint.Join join) {
            if (i2 == 0) {
                return Paint.Join.MITER;
            }
            if (i2 != 1) {
                return i2 != 2 ? join : Paint.Join.BEVEL;
            }
            return Paint.Join.ROUND;
        }

        public void a(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            TypedArray a = androidx.core.content.c.g.a(resources, theme, attributeSet, a.c);
            a(a, xmlPullParser, theme);
            a.recycle();
        }

        private void a(TypedArray typedArray, XmlPullParser xmlPullParser, Resources.Theme theme) {
            this.e = null;
            if (androidx.core.content.c.g.a(xmlPullParser, "pathData")) {
                String string = typedArray.getString(0);
                if (string != null) {
                    this.b = string;
                }
                String string2 = typedArray.getString(2);
                if (string2 != null) {
                    this.a = androidx.core.a.b.a(string2);
                }
                Resources.Theme theme2 = theme;
                this.f922h = androidx.core.content.c.g.a(typedArray, xmlPullParser, theme2, "fillColor", 1, 0);
                this.f924j = androidx.core.content.c.g.a(typedArray, xmlPullParser, "fillAlpha", 12, this.f924j);
                this.n = a(androidx.core.content.c.g.b(typedArray, xmlPullParser, "strokeLineCap", 8, -1), this.n);
                this.o = a(androidx.core.content.c.g.b(typedArray, xmlPullParser, "strokeLineJoin", 9, -1), this.o);
                this.p = androidx.core.content.c.g.a(typedArray, xmlPullParser, "strokeMiterLimit", 10, this.p);
                this.f920f = androidx.core.content.c.g.a(typedArray, xmlPullParser, theme2, "strokeColor", 3, 0);
                this.f923i = androidx.core.content.c.g.a(typedArray, xmlPullParser, "strokeAlpha", 11, this.f923i);
                this.f921g = androidx.core.content.c.g.a(typedArray, xmlPullParser, "strokeWidth", 4, this.f921g);
                this.l = androidx.core.content.c.g.a(typedArray, xmlPullParser, "trimPathEnd", 6, this.l);
                this.m = androidx.core.content.c.g.a(typedArray, xmlPullParser, "trimPathOffset", 7, this.m);
                this.k = androidx.core.content.c.g.a(typedArray, xmlPullParser, "trimPathStart", 5, this.k);
                this.c = androidx.core.content.c.g.b(typedArray, xmlPullParser, "fillType", 13, this.c);
            }
        }

        c(c cVar) {
            super(cVar);
            this.e = cVar.e;
            this.f920f = cVar.f920f;
            this.f921g = cVar.f921g;
            this.f923i = cVar.f923i;
            this.f922h = cVar.f922h;
            this.c = cVar.c;
            this.f924j = cVar.f924j;
            this.k = cVar.k;
            this.l = cVar.l;
            this.m = cVar.m;
            this.n = cVar.n;
            this.o = cVar.o;
            this.p = cVar.p;
        }

        public boolean a() {
            return this.f922h.d() || this.f920f.d();
        }

        public boolean a(int[] iArr) {
            return this.f920f.a(iArr) | this.f922h.a(iArr);
        }
    }

    /* compiled from: VectorDrawableCompat */
    private static class d extends e {
        final Matrix a = new Matrix();
        final ArrayList<e> b = new ArrayList<>();
        float c = 0.0f;
        private float d = 0.0f;
        private float e = 0.0f;

        /* renamed from: f  reason: collision with root package name */
        private float f925f = 1.0f;

        /* renamed from: g  reason: collision with root package name */
        private float f926g = 1.0f;

        /* renamed from: h  reason: collision with root package name */
        private float f927h = 0.0f;

        /* renamed from: i  reason: collision with root package name */
        private float f928i = 0.0f;

        /* renamed from: j  reason: collision with root package name */
        final Matrix f929j = new Matrix();
        int k;
        private int[] l;
        private String m = null;

        public d(d dVar, g.a.a<String, Object> aVar) {
            super();
            f fVar;
            this.c = dVar.c;
            this.d = dVar.d;
            this.e = dVar.e;
            this.f925f = dVar.f925f;
            this.f926g = dVar.f926g;
            this.f927h = dVar.f927h;
            this.f928i = dVar.f928i;
            this.l = dVar.l;
            String str = dVar.m;
            this.m = str;
            this.k = dVar.k;
            if (str != null) {
                aVar.put(str, this);
            }
            this.f929j.set(dVar.f929j);
            ArrayList<e> arrayList = dVar.b;
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                e eVar = arrayList.get(i2);
                if (eVar instanceof d) {
                    this.b.add(new d((d) eVar, aVar));
                } else {
                    if (eVar instanceof c) {
                        fVar = new c((c) eVar);
                    } else if (eVar instanceof b) {
                        fVar = new b((b) eVar);
                    } else {
                        throw new IllegalStateException("Unknown object in the tree!");
                    }
                    this.b.add(fVar);
                    String str2 = fVar.b;
                    if (str2 != null) {
                        aVar.put(str2, fVar);
                    }
                }
            }
        }

        private void b() {
            this.f929j.reset();
            this.f929j.postTranslate(-this.d, -this.e);
            this.f929j.postScale(this.f925f, this.f926g);
            this.f929j.postRotate(this.c, 0.0f, 0.0f);
            this.f929j.postTranslate(this.f927h + this.d, this.f928i + this.e);
        }

        public void a(Resources resources, AttributeSet attributeSet, Resources.Theme theme, XmlPullParser xmlPullParser) {
            TypedArray a2 = androidx.core.content.c.g.a(resources, theme, attributeSet, a.b);
            a(a2, xmlPullParser);
            a2.recycle();
        }

        public String getGroupName() {
            return this.m;
        }

        public Matrix getLocalMatrix() {
            return this.f929j;
        }

        public float getPivotX() {
            return this.d;
        }

        public float getPivotY() {
            return this.e;
        }

        public float getRotation() {
            return this.c;
        }

        public float getScaleX() {
            return this.f925f;
        }

        public float getScaleY() {
            return this.f926g;
        }

        public float getTranslateX() {
            return this.f927h;
        }

        public float getTranslateY() {
            return this.f928i;
        }

        public void setPivotX(float f2) {
            if (f2 != this.d) {
                this.d = f2;
                b();
            }
        }

        public void setPivotY(float f2) {
            if (f2 != this.e) {
                this.e = f2;
                b();
            }
        }

        public void setRotation(float f2) {
            if (f2 != this.c) {
                this.c = f2;
                b();
            }
        }

        public void setScaleX(float f2) {
            if (f2 != this.f925f) {
                this.f925f = f2;
                b();
            }
        }

        public void setScaleY(float f2) {
            if (f2 != this.f926g) {
                this.f926g = f2;
                b();
            }
        }

        public void setTranslateX(float f2) {
            if (f2 != this.f927h) {
                this.f927h = f2;
                b();
            }
        }

        public void setTranslateY(float f2) {
            if (f2 != this.f928i) {
                this.f928i = f2;
                b();
            }
        }

        private void a(TypedArray typedArray, XmlPullParser xmlPullParser) {
            this.l = null;
            this.c = androidx.core.content.c.g.a(typedArray, xmlPullParser, "rotation", 5, this.c);
            this.d = typedArray.getFloat(1, this.d);
            this.e = typedArray.getFloat(2, this.e);
            this.f925f = androidx.core.content.c.g.a(typedArray, xmlPullParser, "scaleX", 3, this.f925f);
            this.f926g = androidx.core.content.c.g.a(typedArray, xmlPullParser, "scaleY", 4, this.f926g);
            this.f927h = androidx.core.content.c.g.a(typedArray, xmlPullParser, "translateX", 6, this.f927h);
            this.f928i = androidx.core.content.c.g.a(typedArray, xmlPullParser, "translateY", 7, this.f928i);
            String string = typedArray.getString(0);
            if (string != null) {
                this.m = string;
            }
            b();
        }

        public boolean a() {
            for (int i2 = 0; i2 < this.b.size(); i2++) {
                if (this.b.get(i2).a()) {
                    return true;
                }
            }
            return false;
        }

        public boolean a(int[] iArr) {
            boolean z = false;
            for (int i2 = 0; i2 < this.b.size(); i2++) {
                z |= this.b.get(i2).a(iArr);
            }
            return z;
        }

        public d() {
            super();
        }
    }

    /* compiled from: VectorDrawableCompat */
    private static abstract class f extends e {
        protected b.C0012b[] a = null;
        String b;
        int c = 0;
        int d;

        public f() {
            super();
        }

        public void a(Path path) {
            path.reset();
            b.C0012b[] bVarArr = this.a;
            if (bVarArr != null) {
                b.C0012b.a(bVarArr, path);
            }
        }

        public boolean b() {
            return false;
        }

        public b.C0012b[] getPathData() {
            return this.a;
        }

        public String getPathName() {
            return this.b;
        }

        public void setPathData(b.C0012b[] bVarArr) {
            if (!androidx.core.a.b.a(this.a, bVarArr)) {
                this.a = androidx.core.a.b.a(bVarArr);
            } else {
                androidx.core.a.b.b(this.a, bVarArr);
            }
        }

        public f(f fVar) {
            super();
            this.b = fVar.b;
            this.d = fVar.d;
            this.a = androidx.core.a.b.a(fVar.a);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0036 A[Catch:{ XmlPullParserException -> 0x0048, IOException -> 0x0043 }] */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x003b A[Catch:{ XmlPullParserException -> 0x0048, IOException -> 0x0043 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static androidx.vectordrawable.a.a.i a(android.content.res.Resources r6, int r7, android.content.res.Resources.Theme r8) {
        /*
            java.lang.String r0 = "parser error"
            java.lang.String r1 = "VectorDrawableCompat"
            int r2 = android.os.Build.VERSION.SDK_INT
            r3 = 24
            if (r2 < r3) goto L_0x0021
            androidx.vectordrawable.a.a.i r0 = new androidx.vectordrawable.a.a.i
            r0.<init>()
            android.graphics.drawable.Drawable r6 = androidx.core.content.c.f.a(r6, r7, r8)
            r0.e = r6
            androidx.vectordrawable.a.a.i$i r6 = new androidx.vectordrawable.a.a.i$i
            android.graphics.drawable.Drawable r7 = r0.e
            android.graphics.drawable.Drawable$ConstantState r7 = r7.getConstantState()
            r6.<init>(r7)
            return r0
        L_0x0021:
            android.content.res.XmlResourceParser r7 = r6.getXml(r7)     // Catch:{ XmlPullParserException -> 0x0048, IOException -> 0x0043 }
            android.util.AttributeSet r2 = android.util.Xml.asAttributeSet(r7)     // Catch:{ XmlPullParserException -> 0x0048, IOException -> 0x0043 }
        L_0x0029:
            int r3 = r7.next()     // Catch:{ XmlPullParserException -> 0x0048, IOException -> 0x0043 }
            r4 = 2
            if (r3 == r4) goto L_0x0034
            r5 = 1
            if (r3 == r5) goto L_0x0034
            goto L_0x0029
        L_0x0034:
            if (r3 != r4) goto L_0x003b
            androidx.vectordrawable.a.a.i r6 = createFromXmlInner(r6, r7, r2, r8)     // Catch:{ XmlPullParserException -> 0x0048, IOException -> 0x0043 }
            return r6
        L_0x003b:
            org.xmlpull.v1.XmlPullParserException r6 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ XmlPullParserException -> 0x0048, IOException -> 0x0043 }
            java.lang.String r7 = "No start tag found"
            r6.<init>(r7)     // Catch:{ XmlPullParserException -> 0x0048, IOException -> 0x0043 }
            throw r6     // Catch:{ XmlPullParserException -> 0x0048, IOException -> 0x0043 }
        L_0x0043:
            r6 = move-exception
            android.util.Log.e(r1, r0, r6)
            goto L_0x004c
        L_0x0048:
            r6 = move-exception
            android.util.Log.e(r1, r0, r6)
        L_0x004c:
            r6 = 0
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.vectordrawable.a.a.i.a(android.content.res.Resources, int, android.content.res.Resources$Theme):androidx.vectordrawable.a.a.i");
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        Drawable drawable = this.e;
        if (drawable != null) {
            androidx.core.graphics.drawable.a.a(drawable, resources, xmlPullParser, attributeSet, theme);
            return;
        }
        h hVar = this.f915f;
        hVar.b = new g();
        TypedArray a2 = androidx.core.content.c.g.a(resources, theme, attributeSet, a.a);
        a(a2, xmlPullParser, theme);
        a2.recycle();
        hVar.a = getChangingConfigurations();
        hVar.k = true;
        a(resources, xmlPullParser, attributeSet, theme);
        this.f916g = a(this.f916g, hVar.c, hVar.d);
    }

    i(h hVar) {
        this.f919j = true;
        this.k = new float[9];
        this.l = new Matrix();
        this.m = new Rect();
        this.f915f = hVar;
        this.f916g = a(this.f916g, hVar.c, hVar.d);
    }

    /* compiled from: VectorDrawableCompat */
    private static class g {
        private static final Matrix q = new Matrix();
        private final Path a;
        private final Path b;
        private final Matrix c;
        Paint d;
        Paint e;

        /* renamed from: f  reason: collision with root package name */
        private PathMeasure f930f;

        /* renamed from: g  reason: collision with root package name */
        private int f931g;

        /* renamed from: h  reason: collision with root package name */
        final d f932h;

        /* renamed from: i  reason: collision with root package name */
        float f933i;

        /* renamed from: j  reason: collision with root package name */
        float f934j;
        float k;
        float l;
        int m;
        String n;
        Boolean o;
        final g.a.a<String, Object> p;

        public g() {
            this.c = new Matrix();
            this.f933i = 0.0f;
            this.f934j = 0.0f;
            this.k = 0.0f;
            this.l = 0.0f;
            this.m = 255;
            this.n = null;
            this.o = null;
            this.p = new g.a.a<>();
            this.f932h = new d();
            this.a = new Path();
            this.b = new Path();
        }

        private static float a(float f2, float f3, float f4, float f5) {
            return (f2 * f5) - (f3 * f4);
        }

        private void a(d dVar, Matrix matrix, Canvas canvas, int i2, int i3, ColorFilter colorFilter) {
            dVar.a.set(matrix);
            dVar.a.preConcat(dVar.f929j);
            canvas.save();
            for (int i4 = 0; i4 < dVar.b.size(); i4++) {
                e eVar = dVar.b.get(i4);
                if (eVar instanceof d) {
                    a((d) eVar, dVar.a, canvas, i2, i3, colorFilter);
                } else if (eVar instanceof f) {
                    a(dVar, (f) eVar, canvas, i2, i3, colorFilter);
                }
            }
            canvas.restore();
        }

        public float getAlpha() {
            return ((float) getRootAlpha()) / 255.0f;
        }

        public int getRootAlpha() {
            return this.m;
        }

        public void setAlpha(float f2) {
            setRootAlpha((int) (f2 * 255.0f));
        }

        public void setRootAlpha(int i2) {
            this.m = i2;
        }

        public void a(Canvas canvas, int i2, int i3, ColorFilter colorFilter) {
            a(this.f932h, q, canvas, i2, i3, colorFilter);
        }

        public g(g gVar) {
            this.c = new Matrix();
            this.f933i = 0.0f;
            this.f934j = 0.0f;
            this.k = 0.0f;
            this.l = 0.0f;
            this.m = 255;
            this.n = null;
            this.o = null;
            g.a.a<String, Object> aVar = new g.a.a<>();
            this.p = aVar;
            this.f932h = new d(gVar.f932h, aVar);
            this.a = new Path(gVar.a);
            this.b = new Path(gVar.b);
            this.f933i = gVar.f933i;
            this.f934j = gVar.f934j;
            this.k = gVar.k;
            this.l = gVar.l;
            this.f931g = gVar.f931g;
            this.m = gVar.m;
            this.n = gVar.n;
            String str = gVar.n;
            if (str != null) {
                this.p.put(str, this);
            }
            this.o = gVar.o;
        }

        private void a(d dVar, f fVar, Canvas canvas, int i2, int i3, ColorFilter colorFilter) {
            float f2 = ((float) i2) / this.k;
            float f3 = ((float) i3) / this.l;
            float min = Math.min(f2, f3);
            Matrix matrix = dVar.a;
            this.c.set(matrix);
            this.c.postScale(f2, f3);
            float a2 = a(matrix);
            if (a2 != 0.0f) {
                fVar.a(this.a);
                Path path = this.a;
                this.b.reset();
                if (fVar.b()) {
                    this.b.setFillType(fVar.c == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                    this.b.addPath(path, this.c);
                    canvas.clipPath(this.b);
                    return;
                }
                c cVar = (c) fVar;
                if (!(cVar.k == 0.0f && cVar.l == 1.0f)) {
                    float f4 = cVar.k;
                    float f5 = cVar.m;
                    float f6 = (f4 + f5) % 1.0f;
                    float f7 = (cVar.l + f5) % 1.0f;
                    if (this.f930f == null) {
                        this.f930f = new PathMeasure();
                    }
                    this.f930f.setPath(this.a, false);
                    float length = this.f930f.getLength();
                    float f8 = f6 * length;
                    float f9 = f7 * length;
                    path.reset();
                    if (f8 > f9) {
                        this.f930f.getSegment(f8, length, path, true);
                        this.f930f.getSegment(0.0f, f9, path, true);
                    } else {
                        this.f930f.getSegment(f8, f9, path, true);
                    }
                    path.rLineTo(0.0f, 0.0f);
                }
                this.b.addPath(path, this.c);
                if (cVar.f922h.e()) {
                    androidx.core.content.c.b bVar = cVar.f922h;
                    if (this.e == null) {
                        Paint paint = new Paint(1);
                        this.e = paint;
                        paint.setStyle(Paint.Style.FILL);
                    }
                    Paint paint2 = this.e;
                    if (bVar.c()) {
                        Shader b2 = bVar.b();
                        b2.setLocalMatrix(this.c);
                        paint2.setShader(b2);
                        paint2.setAlpha(Math.round(cVar.f924j * 255.0f));
                    } else {
                        paint2.setShader((Shader) null);
                        paint2.setAlpha(255);
                        paint2.setColor(i.a(bVar.a(), cVar.f924j));
                    }
                    paint2.setColorFilter(colorFilter);
                    this.b.setFillType(cVar.c == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                    canvas.drawPath(this.b, paint2);
                }
                if (cVar.f920f.e()) {
                    androidx.core.content.c.b bVar2 = cVar.f920f;
                    if (this.d == null) {
                        Paint paint3 = new Paint(1);
                        this.d = paint3;
                        paint3.setStyle(Paint.Style.STROKE);
                    }
                    Paint paint4 = this.d;
                    Paint.Join join = cVar.o;
                    if (join != null) {
                        paint4.setStrokeJoin(join);
                    }
                    Paint.Cap cap = cVar.n;
                    if (cap != null) {
                        paint4.setStrokeCap(cap);
                    }
                    paint4.setStrokeMiter(cVar.p);
                    if (bVar2.c()) {
                        Shader b3 = bVar2.b();
                        b3.setLocalMatrix(this.c);
                        paint4.setShader(b3);
                        paint4.setAlpha(Math.round(cVar.f923i * 255.0f));
                    } else {
                        paint4.setShader((Shader) null);
                        paint4.setAlpha(255);
                        paint4.setColor(i.a(bVar2.a(), cVar.f923i));
                    }
                    paint4.setColorFilter(colorFilter);
                    paint4.setStrokeWidth(cVar.f921g * min * a2);
                    canvas.drawPath(this.b, paint4);
                }
            }
        }

        private float a(Matrix matrix) {
            float[] fArr = {0.0f, 1.0f, 1.0f, 0.0f};
            matrix.mapVectors(fArr);
            float a2 = a(fArr[0], fArr[1], fArr[2], fArr[3]);
            float max = Math.max((float) Math.hypot((double) fArr[0], (double) fArr[1]), (float) Math.hypot((double) fArr[2], (double) fArr[3]));
            if (max > 0.0f) {
                return Math.abs(a2) / max;
            }
            return 0.0f;
        }

        public boolean a() {
            if (this.o == null) {
                this.o = Boolean.valueOf(this.f932h.a());
            }
            return this.o.booleanValue();
        }

        public boolean a(int[] iArr) {
            return this.f932h.a(iArr);
        }
    }

    static int a(int i2, float f2) {
        return (i2 & 16777215) | (((int) (((float) Color.alpha(i2)) * f2)) << 24);
    }

    private static PorterDuff.Mode a(int i2, PorterDuff.Mode mode) {
        if (i2 == 3) {
            return PorterDuff.Mode.SRC_OVER;
        }
        if (i2 == 5) {
            return PorterDuff.Mode.SRC_IN;
        }
        if (i2 == 9) {
            return PorterDuff.Mode.SRC_ATOP;
        }
        switch (i2) {
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return mode;
        }
    }

    private void a(TypedArray typedArray, XmlPullParser xmlPullParser, Resources.Theme theme) {
        h hVar = this.f915f;
        g gVar = hVar.b;
        hVar.d = a(androidx.core.content.c.g.b(typedArray, xmlPullParser, "tintMode", 6, -1), PorterDuff.Mode.SRC_IN);
        ColorStateList a2 = androidx.core.content.c.g.a(typedArray, xmlPullParser, theme, "tint", 1);
        if (a2 != null) {
            hVar.c = a2;
        }
        hVar.e = androidx.core.content.c.g.a(typedArray, xmlPullParser, "autoMirrored", 5, hVar.e);
        gVar.k = androidx.core.content.c.g.a(typedArray, xmlPullParser, "viewportWidth", 7, gVar.k);
        float a3 = androidx.core.content.c.g.a(typedArray, xmlPullParser, "viewportHeight", 8, gVar.l);
        gVar.l = a3;
        if (gVar.k <= 0.0f) {
            throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires viewportWidth > 0");
        } else if (a3 > 0.0f) {
            gVar.f933i = typedArray.getDimension(3, gVar.f933i);
            float dimension = typedArray.getDimension(2, gVar.f934j);
            gVar.f934j = dimension;
            if (gVar.f933i <= 0.0f) {
                throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires width > 0");
            } else if (dimension > 0.0f) {
                gVar.setAlpha(androidx.core.content.c.g.a(typedArray, xmlPullParser, "alpha", 4, gVar.getAlpha()));
                String string = typedArray.getString(0);
                if (string != null) {
                    gVar.n = string;
                    gVar.p.put(string, gVar);
                }
            } else {
                throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires height > 0");
            }
        } else {
            throw new XmlPullParserException(typedArray.getPositionDescription() + "<vector> tag requires viewportHeight > 0");
        }
    }

    private void a(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        h hVar = this.f915f;
        g gVar = hVar.b;
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.push(gVar.f932h);
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth() + 1;
        boolean z = true;
        while (eventType != 1 && (xmlPullParser.getDepth() >= depth || eventType != 3)) {
            if (eventType == 2) {
                String name = xmlPullParser.getName();
                d dVar = (d) arrayDeque.peek();
                if ("path".equals(name)) {
                    c cVar = new c();
                    cVar.a(resources, attributeSet, theme, xmlPullParser);
                    dVar.b.add(cVar);
                    if (cVar.getPathName() != null) {
                        gVar.p.put(cVar.getPathName(), cVar);
                    }
                    z = false;
                    hVar.a = cVar.d | hVar.a;
                } else if ("clip-path".equals(name)) {
                    b bVar = new b();
                    bVar.a(resources, attributeSet, theme, xmlPullParser);
                    dVar.b.add(bVar);
                    if (bVar.getPathName() != null) {
                        gVar.p.put(bVar.getPathName(), bVar);
                    }
                    hVar.a = bVar.d | hVar.a;
                } else if ("group".equals(name)) {
                    d dVar2 = new d();
                    dVar2.a(resources, attributeSet, theme, xmlPullParser);
                    dVar.b.add(dVar2);
                    arrayDeque.push(dVar2);
                    if (dVar2.getGroupName() != null) {
                        gVar.p.put(dVar2.getGroupName(), dVar2);
                    }
                    hVar.a = dVar2.k | hVar.a;
                }
            } else if (eventType == 3 && "group".equals(xmlPullParser.getName())) {
                arrayDeque.pop();
            }
            eventType = xmlPullParser.next();
        }
        if (z) {
            throw new XmlPullParserException("no path defined");
        }
    }

    /* access modifiers changed from: package-private */
    public void a(boolean z) {
        this.f919j = z;
    }

    private boolean a() {
        if (Build.VERSION.SDK_INT < 17 || !isAutoMirrored() || androidx.core.graphics.drawable.a.e(this) != 1) {
            return false;
        }
        return true;
    }
}
