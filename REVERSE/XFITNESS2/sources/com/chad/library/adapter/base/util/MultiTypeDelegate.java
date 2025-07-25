package com.chad.library.adapter.base.util;

import android.util.SparseIntArray;
import java.util.List;

public abstract class MultiTypeDelegate<T> {
    private static final int DEFAULT_VIEW_TYPE = -255;
    private boolean autoMode;
    private SparseIntArray layouts;
    private boolean selfMode;

    public MultiTypeDelegate(SparseIntArray sparseIntArray) {
        this.layouts = sparseIntArray;
    }

    private void addItemType(int i2, int i3) {
        if (this.layouts == null) {
            this.layouts = new SparseIntArray();
        }
        this.layouts.put(i2, i3);
    }

    private void checkMode(boolean z) {
        if (z) {
            throw new IllegalArgumentException("Don't mess two register mode");
        }
    }

    public final int getDefItemViewType(List<T> list, int i2) {
        T t = list.get(i2);
        return t != null ? getItemType(t) : DEFAULT_VIEW_TYPE;
    }

    /* access modifiers changed from: protected */
    public abstract int getItemType(T t);

    public final int getLayoutId(int i2) {
        return this.layouts.get(i2, -404);
    }

    public MultiTypeDelegate registerItemType(int i2, int i3) {
        this.selfMode = true;
        checkMode(this.autoMode);
        addItemType(i2, i3);
        return this;
    }

    public MultiTypeDelegate registerItemTypeAutoIncrease(int... iArr) {
        this.autoMode = true;
        checkMode(this.selfMode);
        for (int i2 = 0; i2 < iArr.length; i2++) {
            addItemType(i2, iArr[i2]);
        }
        return this;
    }

    public MultiTypeDelegate() {
    }
}
