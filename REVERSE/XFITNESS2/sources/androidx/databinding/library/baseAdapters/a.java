package androidx.databinding.library.baseAdapters;

import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.d;
import androidx.databinding.f;
import java.util.ArrayList;
import java.util.List;

/* compiled from: DataBinderMapperImpl */
public class a extends d {
    private static final SparseIntArray a = new SparseIntArray(0);

    public ViewDataBinding a(f fVar, View view, int i2) {
        if (a.get(i2) <= 0 || view.getTag() != null) {
            return null;
        }
        throw new RuntimeException("view must have a tag");
    }

    public ViewDataBinding a(f fVar, View[] viewArr, int i2) {
        if (viewArr == null || viewArr.length == 0 || a.get(i2) <= 0 || viewArr[0].getTag() != null) {
            return null;
        }
        throw new RuntimeException("view must have a tag");
    }

    public List<d> a() {
        return new ArrayList(0);
    }
}
