package androidx.databinding;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* compiled from: DataBindingUtil */
public class g {
    private static d a = new e();
    private static f b = null;

    public static <T extends ViewDataBinding> T a(LayoutInflater layoutInflater, int i2, ViewGroup viewGroup, boolean z) {
        return a(layoutInflater, i2, viewGroup, z, b);
    }

    public static <T extends ViewDataBinding> T a(LayoutInflater layoutInflater, int i2, ViewGroup viewGroup, boolean z, f fVar) {
        int i3 = 0;
        boolean z2 = viewGroup != null && z;
        if (z2) {
            i3 = viewGroup.getChildCount();
        }
        View inflate = layoutInflater.inflate(i2, viewGroup, z);
        if (z2) {
            return a(fVar, viewGroup, i3, i2);
        }
        return a(fVar, inflate, i2);
    }

    static <T extends ViewDataBinding> T a(f fVar, View[] viewArr, int i2) {
        return a.a(fVar, viewArr, i2);
    }

    static <T extends ViewDataBinding> T a(f fVar, View view, int i2) {
        return a.a(fVar, view, i2);
    }

    public static <T extends ViewDataBinding> T a(Activity activity, int i2) {
        return a(activity, i2, b);
    }

    public static <T extends ViewDataBinding> T a(Activity activity, int i2, f fVar) {
        activity.setContentView(i2);
        return a(fVar, (ViewGroup) activity.getWindow().getDecorView().findViewById(16908290), 0, i2);
    }

    private static <T extends ViewDataBinding> T a(f fVar, ViewGroup viewGroup, int i2, int i3) {
        int childCount = viewGroup.getChildCount();
        int i4 = childCount - i2;
        if (i4 == 1) {
            return a(fVar, viewGroup.getChildAt(childCount - 1), i3);
        }
        View[] viewArr = new View[i4];
        for (int i5 = 0; i5 < i4; i5++) {
            viewArr[i5] = viewGroup.getChildAt(i5 + i2);
        }
        return a(fVar, viewArr, i3);
    }
}
