package com.chad.library.adapter.base.listener;

import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;

public abstract class OnItemChildClickListener extends SimpleClickListener {
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        onSimpleItemChildClick(baseQuickAdapter, view, i2);
    }

    public void onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
    }

    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
    }

    public void onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
    }

    public abstract void onSimpleItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i2);
}
