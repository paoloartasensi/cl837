package com.chad.library.adapter.base.listener;

import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;

public abstract class OnItemLongClickListener extends SimpleClickListener {
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
    }

    public void onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
    }

    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
    }

    public void onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        onSimpleItemLongClick(baseQuickAdapter, view, i2);
    }

    public abstract void onSimpleItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i2);
}
