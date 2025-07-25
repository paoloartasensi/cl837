package com.google.android.material.circularreveal;

import android.animation.TypeEvaluator;
import android.graphics.drawable.Drawable;
import android.util.Property;
import com.google.android.material.circularreveal.b;

/* compiled from: CircularRevealWidget */
public interface c extends b.a {

    /* compiled from: CircularRevealWidget */
    public static class b implements TypeEvaluator<e> {
        public static final TypeEvaluator<e> b = new b();
        private final e a = new e();

        /* renamed from: a */
        public e evaluate(float f2, e eVar, e eVar2) {
            this.a.a(com.google.android.material.e.a.b(eVar.a, eVar2.a, f2), com.google.android.material.e.a.b(eVar.b, eVar2.b, f2), com.google.android.material.e.a.b(eVar.c, eVar2.c, f2));
            return this.a;
        }
    }

    /* renamed from: com.google.android.material.circularreveal.c$c  reason: collision with other inner class name */
    /* compiled from: CircularRevealWidget */
    public static class C0078c extends Property<c, e> {
        public static final Property<c, e> a = new C0078c("circularReveal");

        private C0078c(String str) {
            super(e.class, str);
        }

        /* renamed from: a */
        public e get(c cVar) {
            return cVar.getRevealInfo();
        }

        /* renamed from: a */
        public void set(c cVar, e eVar) {
            cVar.setRevealInfo(eVar);
        }
    }

    /* compiled from: CircularRevealWidget */
    public static class d extends Property<c, Integer> {
        public static final Property<c, Integer> a = new d("circularRevealScrimColor");

        private d(String str) {
            super(Integer.class, str);
        }

        /* renamed from: a */
        public Integer get(c cVar) {
            return Integer.valueOf(cVar.getCircularRevealScrimColor());
        }

        /* renamed from: a */
        public void set(c cVar, Integer num) {
            cVar.setCircularRevealScrimColor(num.intValue());
        }
    }

    /* compiled from: CircularRevealWidget */
    public static class e {
        public float a;
        public float b;
        public float c;

        public void a(float f2, float f3, float f4) {
            this.a = f2;
            this.b = f3;
            this.c = f4;
        }

        private e() {
        }

        public e(float f2, float f3, float f4) {
            this.a = f2;
            this.b = f3;
            this.c = f4;
        }

        public void a(e eVar) {
            a(eVar.a, eVar.b, eVar.c);
        }

        public boolean a() {
            return this.c == Float.MAX_VALUE;
        }

        public e(e eVar) {
            this(eVar.a, eVar.b, eVar.c);
        }
    }

    void a();

    void b();

    int getCircularRevealScrimColor();

    e getRevealInfo();

    void setCircularRevealOverlayDrawable(Drawable drawable);

    void setCircularRevealScrimColor(int i2);

    void setRevealInfo(e eVar);
}
