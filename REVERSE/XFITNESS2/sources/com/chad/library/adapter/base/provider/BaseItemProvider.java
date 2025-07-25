package com.chad.library.adapter.base.provider;

import android.content.Context;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

public abstract class BaseItemProvider<T, V extends BaseViewHolder> {
    public Context mContext;
    public List<T> mData;

    public abstract void convert(V v, T t, int i2);

    public void convertPayloads(V v, T t, int i2, List<Object> list) {
    }

    public abstract int layout();

    public void onClick(V v, T t, int i2) {
    }

    public boolean onLongClick(V v, T t, int i2) {
        return false;
    }

    public abstract int viewType();
}
