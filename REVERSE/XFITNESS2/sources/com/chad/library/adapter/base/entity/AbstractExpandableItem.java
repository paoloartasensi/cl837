package com.chad.library.adapter.base.entity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractExpandableItem<T> implements IExpandable<T> {
    protected boolean mExpandable = false;
    protected List<T> mSubItems;

    public void addSubItem(T t) {
        if (this.mSubItems == null) {
            this.mSubItems = new ArrayList();
        }
        this.mSubItems.add(t);
    }

    public boolean contains(T t) {
        List<T> list = this.mSubItems;
        return list != null && list.contains(t);
    }

    public T getSubItem(int i2) {
        if (!hasSubItem() || i2 >= this.mSubItems.size()) {
            return null;
        }
        return this.mSubItems.get(i2);
    }

    public int getSubItemPosition(T t) {
        List<T> list = this.mSubItems;
        if (list != null) {
            return list.indexOf(t);
        }
        return -1;
    }

    public List<T> getSubItems() {
        return this.mSubItems;
    }

    public boolean hasSubItem() {
        List<T> list = this.mSubItems;
        return list != null && list.size() > 0;
    }

    public boolean isExpanded() {
        return this.mExpandable;
    }

    public boolean removeSubItem(T t) {
        List<T> list = this.mSubItems;
        return list != null && list.remove(t);
    }

    public void setExpanded(boolean z) {
        this.mExpandable = z;
    }

    public void setSubItems(List<T> list) {
        this.mSubItems = list;
    }

    public boolean removeSubItem(int i2) {
        List<T> list = this.mSubItems;
        if (list == null || i2 < 0 || i2 >= list.size()) {
            return false;
        }
        this.mSubItems.remove(i2);
        return true;
    }

    public void addSubItem(int i2, T t) {
        List<T> list = this.mSubItems;
        if (list == null || i2 < 0 || i2 >= list.size()) {
            addSubItem(t);
        } else {
            this.mSubItems.add(i2, t);
        }
    }
}
