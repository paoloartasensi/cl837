package androidx.transition;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.Property;

/* compiled from: PathProperty */
class h<T> extends Property<T, Float> {
    private final Property<T, PointF> a;
    private final PathMeasure b;
    private final float c;
    private final float[] d = new float[2];
    private final PointF e = new PointF();

    /* renamed from: f  reason: collision with root package name */
    private float f894f;

    h(Property<T, PointF> property, Path path) {
        super(Float.class, property.getName());
        this.a = property;
        PathMeasure pathMeasure = new PathMeasure(path, false);
        this.b = pathMeasure;
        this.c = pathMeasure.getLength();
    }

    /* renamed from: a */
    public void set(T t, Float f2) {
        this.f894f = f2.floatValue();
        this.b.getPosTan(this.c * f2.floatValue(), this.d, (float[]) null);
        PointF pointF = this.e;
        float[] fArr = this.d;
        pointF.x = fArr[0];
        pointF.y = fArr[1];
        this.a.set(t, pointF);
    }

    public Float get(T t) {
        return Float.valueOf(this.f894f);
    }
}
