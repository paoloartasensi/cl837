package com.chad.library.adapter.base.listener;

import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;

public abstract class OnItemChildLongClickListener extends SimpleClickListener {
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
    }

    public void onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        onSimpleItemChildLongClick(baseQuickAdapter, view, i2);
    }

    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
    }

    public void onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
    }

    public abstract void onSimpleItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i2);
}
