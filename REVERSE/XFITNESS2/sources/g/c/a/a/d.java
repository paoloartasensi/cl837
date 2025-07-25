package g.c.a.a;

import android.view.animation.Interpolator;

/* compiled from: LookupTableInterpolator */
abstract class d implements Interpolator {
    private final float[] a;
    private final float b;

    protected d(float[] fArr) {
        this.a = fArr;
        this.b = 1.0f / ((float) (fArr.length - 1));
    }

    public float getInterpolation(float f2) {
        if (f2 >= 1.0f) {
            return 1.0f;
        }
        if (f2 <= 0.0f) {
            return 0.0f;
        }
        float[] fArr = this.a;
        int min = Math.min((int) (((float) (fArr.length - 1)) * f2), fArr.length - 2);
        float f3 = this.b;
        float f4 = (f2 - (((float) min) * f3)) / f3;
        float[] fArr2 = this.a;
        return fArr2[min] + (f4 * (fArr2[min + 1] - fArr2[min]));
    }
}
