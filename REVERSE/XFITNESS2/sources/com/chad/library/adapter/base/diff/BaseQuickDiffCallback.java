package com.chad.library.adapter.base.diff;

import androidx.recyclerview.widget.f;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseQuickDiffCallback<T> extends f.b {
    private List<T> newList;
    private List<T> oldList;

    public BaseQuickDiffCallback(List<T> list) {
        this.newList = list == null ? new ArrayList<>() : list;
    }

    public boolean areContentsTheSame(int i2, int i3) {
        return areContentsTheSame(this.oldList.get(i2), this.newList.get(i3));
    }

    /* access modifiers changed from: protected */
    public abstract boolean areContentsTheSame(T t, T t2);

    public boolean areItemsTheSame(int i2, int i3) {
        return areItemsTheSame(this.oldList.get(i2), this.newList.get(i3));
    }

    /* access modifiers changed from: protected */
    public abstract boolean areItemsTheSame(T t, T t2);

    public Object getChangePayload(int i2, int i3) {
        return getChangePayload(this.oldList.get(i2), this.newList.get(i3));
    }

    /* access modifiers changed from: protected */
    public Object getChangePayload(T t, T t2) {
        return null;
    }

    public List<T> getNewList() {
        return this.newList;
    }

    public int getNewListSize() {
        return this.newList.size();
    }

    public List<T> getOldList() {
        return this.oldList;
    }

    public int getOldListSize() {
        return this.oldList.size();
    }

    public void setOldList(List<T> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.oldList = list;
    }
}
