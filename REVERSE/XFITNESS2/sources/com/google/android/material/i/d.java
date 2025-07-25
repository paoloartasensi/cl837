package com.google.android.material.i;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ShapePath */
public class d {
    public float a;
    public float b;
    public float c;
    public float d;
    private final List<c> e = new ArrayList();

    /* compiled from: ShapePath */
    public static class a extends c {

        /* renamed from: h  reason: collision with root package name */
        private static final RectF f1496h = new RectF();
        public float b;
        public float c;
        public float d;
        public float e;

        /* renamed from: f  reason: collision with root package name */
        public float f1497f;

        /* renamed from: g  reason: collision with root package name */
        public float f1498g;

        public a(float f2, float f3, float f4, float f5) {
            this.b = f2;
            this.c = f3;
            this.d = f4;
            this.e = f5;
        }

        public void a(Matrix matrix, Path path) {
            Matrix matrix2 = this.a;
            matrix.invert(matrix2);
            path.transform(matrix2);
            f1496h.set(this.b, this.c, this.d, this.e);
            path.arcTo(f1496h, this.f1497f, this.f1498g, false);
            path.transform(matrix);
        }
    }

    /* compiled from: ShapePath */
    public static class b extends c {
        /* access modifiers changed from: private */
        public float b;
        /* access modifiers changed from: private */
        public float c;

        public void a(Matrix matrix, Path path) {
            Matrix matrix2 = this.a;
            matrix.invert(matrix2);
            path.transform(matrix2);
            path.lineTo(this.b, this.c);
            path.transform(matrix);
        }
    }

    /* compiled from: ShapePath */
    public static abstract class c {
        protected final Matrix a = new Matrix();

        public abstract void a(Matrix matrix, Path path);
    }

    public d() {
        b(0.0f, 0.0f);
    }

    public void a(float f2, float f3) {
        b bVar = new b();
        float unused = bVar.b = f2;
        float unused2 = bVar.c = f3;
        this.e.add(bVar);
        this.c = f2;
        this.d = f3;
    }

    public void b(float f2, float f3) {
        this.a = f2;
        this.b = f3;
        this.c = f2;
        this.d = f3;
        this.e.clear();
    }

    public void a(float f2, float f3, float f4, float f5, float f6, float f7) {
        a aVar = new a(f2, f3, f4, f5);
        aVar.f1497f = f6;
        aVar.f1498g = f7;
        this.e.add(aVar);
        double d2 = (double) (f6 + f7);
        this.c = ((f2 + f4) * 0.5f) + (((f4 - f2) / 2.0f) * ((float) Math.cos(Math.toRadians(d2))));
        this.d = ((f3 + f5) * 0.5f) + (((f5 - f3) / 2.0f) * ((float) Math.sin(Math.toRadians(d2))));
    }

    public void a(Matrix matrix, Path path) {
        int size = this.e.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.e.get(i2).a(matrix, path);
        }
    }
}
