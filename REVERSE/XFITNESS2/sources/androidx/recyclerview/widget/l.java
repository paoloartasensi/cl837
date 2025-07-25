package androidx.recyclerview.widget;

import android.graphics.Canvas;
import android.os.Build;
import android.view.View;
import androidx.core.h.v;
import androidx.recyclerview.R$id;

/* compiled from: ItemTouchUIUtilImpl */
class l implements k {
    static final k a = new l();

    l() {
    }

    private static float a(RecyclerView recyclerView, View view) {
        int childCount = recyclerView.getChildCount();
        float f2 = 0.0f;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = recyclerView.getChildAt(i2);
            if (childAt != view) {
                float k = v.k(childAt);
                if (k > f2) {
                    f2 = k;
                }
            }
        }
        return f2;
    }

    public void a(Canvas canvas, RecyclerView recyclerView, View view, float f2, float f3, int i2, boolean z) {
    }

    public void b(Canvas canvas, RecyclerView recyclerView, View view, float f2, float f3, int i2, boolean z) {
        if (Build.VERSION.SDK_INT >= 21 && z && view.getTag(R$id.item_touch_helper_previous_elevation) == null) {
            Float valueOf = Float.valueOf(v.k(view));
            v.a(view, a(recyclerView, view) + 1.0f);
            view.setTag(R$id.item_touch_helper_previous_elevation, valueOf);
        }
        view.setTranslationX(f2);
        view.setTranslationY(f3);
    }

    public void b(View view) {
    }

    public void a(View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            Object tag = view.getTag(R$id.item_touch_helper_previous_elevation);
            if (tag instanceof Float) {
                v.a(view, ((Float) tag).floatValue());
            }
            view.setTag(R$id.item_touch_helper_previous_elevation, (Object) null);
        }
        view.setTranslationX(0.0f);
        view.setTranslationY(0.0f);
    }
}
