package com.chad.library.adapter.base.diff;

import androidx.recyclerview.widget.o;
import com.chad.library.adapter.base.BaseQuickAdapter;

public final class BaseQuickAdapterListUpdateCallback implements o {
    private final BaseQuickAdapter mAdapter;

    public BaseQuickAdapterListUpdateCallback(BaseQuickAdapter baseQuickAdapter) {
        this.mAdapter = baseQuickAdapter;
    }

    public void onChanged(int i2, int i3, Object obj) {
        BaseQuickAdapter baseQuickAdapter = this.mAdapter;
        baseQuickAdapter.notifyItemRangeChanged(i2 + baseQuickAdapter.getHeaderLayoutCount(), i3, obj);
    }

    public void onInserted(int i2, int i3) {
        BaseQuickAdapter baseQuickAdapter = this.mAdapter;
        baseQuickAdapter.notifyItemRangeInserted(i2 + baseQuickAdapter.getHeaderLayoutCount(), i3);
    }

    public void onMoved(int i2, int i3) {
        BaseQuickAdapter baseQuickAdapter = this.mAdapter;
        baseQuickAdapter.notifyItemMoved(i2 + baseQuickAdapter.getHeaderLayoutCount(), i3 + this.mAdapter.getHeaderLayoutCount());
    }

    public void onRemoved(int i2, int i3) {
        BaseQuickAdapter baseQuickAdapter = this.mAdapter;
        baseQuickAdapter.notifyItemRangeRemoved(i2 + baseQuickAdapter.getHeaderLayoutCount(), i3);
    }
}
