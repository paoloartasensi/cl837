package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.appcompat.a.a.a;
import androidx.core.content.c.f;

/* compiled from: TintTypedArray */
public class g0 {
    private final Context a;
    private final TypedArray b;
    private TypedValue c;

    private g0(Context context, TypedArray typedArray) {
        this.a = context;
        this.b = typedArray;
    }

    public static g0 a(Context context, AttributeSet attributeSet, int[] iArr) {
        return new g0(context, context.obtainStyledAttributes(attributeSet, iArr));
    }

    public Drawable b(int i2) {
        int resourceId;
        if (!this.b.hasValue(i2) || (resourceId = this.b.getResourceId(i2, 0)) == 0) {
            return this.b.getDrawable(i2);
        }
        return a.c(this.a, resourceId);
    }

    public Drawable c(int i2) {
        int resourceId;
        if (!this.b.hasValue(i2) || (resourceId = this.b.getResourceId(i2, 0)) == 0) {
            return null;
        }
        return f.b().a(this.a, resourceId, true);
    }

    public String d(int i2) {
        return this.b.getString(i2);
    }

    public CharSequence e(int i2) {
        return this.b.getText(i2);
    }

    public int f(int i2, int i3) {
        return this.b.getLayoutDimension(i2, i3);
    }

    public int g(int i2, int i3) {
        return this.b.getResourceId(i2, i3);
    }

    public static g0 a(Context context, AttributeSet attributeSet, int[] iArr, int i2, int i3) {
        return new g0(context, context.obtainStyledAttributes(attributeSet, iArr, i2, i3));
    }

    public int d(int i2, int i3) {
        return this.b.getInt(i2, i3);
    }

    public int e(int i2, int i3) {
        return this.b.getInteger(i2, i3);
    }

    public CharSequence[] f(int i2) {
        return this.b.getTextArray(i2);
    }

    public boolean g(int i2) {
        return this.b.hasValue(i2);
    }

    public static g0 a(Context context, int i2, int[] iArr) {
        return new g0(context, context.obtainStyledAttributes(i2, iArr));
    }

    public int c(int i2, int i3) {
        return this.b.getDimensionPixelSize(i2, i3);
    }

    public Typeface a(int i2, int i3, f.a aVar) {
        int resourceId = this.b.getResourceId(i2, 0);
        if (resourceId == 0) {
            return null;
        }
        if (this.c == null) {
            this.c = new TypedValue();
        }
        return f.a(this.a, resourceId, this.c, i3, aVar);
    }

    public float b(int i2, float f2) {
        return this.b.getFloat(i2, f2);
    }

    public int b(int i2, int i3) {
        return this.b.getDimensionPixelOffset(i2, i3);
    }

    public boolean a(int i2, boolean z) {
        return this.b.getBoolean(i2, z);
    }

    public int a(int i2, int i3) {
        return this.b.getColor(i2, i3);
    }

    public ColorStateList a(int i2) {
        int resourceId;
        ColorStateList b2;
        if (!this.b.hasValue(i2) || (resourceId = this.b.getResourceId(i2, 0)) == 0 || (b2 = a.b(this.a, resourceId)) == null) {
            return this.b.getColorStateList(i2);
        }
        return b2;
    }

    public float a(int i2, float f2) {
        return this.b.getDimension(i2, f2);
    }

    public void a() {
        this.b.recycle();
    }
}
