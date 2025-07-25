package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import java.lang.ref.WeakReference;

/* compiled from: TintResources */
class f0 extends y {
    private final WeakReference<Context> b;

    public f0(Context context, Resources resources) {
        super(resources);
        this.b = new WeakReference<>(context);
    }

    public Drawable getDrawable(int i2) {
        Drawable drawable = super.getDrawable(i2);
        Context context = (Context) this.b.get();
        if (!(drawable == null || context == null)) {
            x.a().a(context, i2, drawable);
        }
        return drawable;
    }
}
