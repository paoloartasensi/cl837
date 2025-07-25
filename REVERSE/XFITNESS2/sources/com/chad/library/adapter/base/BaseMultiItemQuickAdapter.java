package com.chad.library.adapter.base;

import android.util.SparseIntArray;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import java.util.List;

public abstract class BaseMultiItemQuickAdapter<T extends MultiItemEntity, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    private static final int DEFAULT_VIEW_TYPE = -255;
    public static final int TYPE_NOT_FOUND = -404;
    private SparseIntArray layouts;

    public BaseMultiItemQuickAdapter(List<T> list) {
        super(list);
    }

    private int getLayoutId(int i2) {
        return this.layouts.get(i2, -404);
    }

    /* access modifiers changed from: protected */
    public void addItemType(int i2, int i3) {
        if (this.layouts == null) {
            this.layouts = new SparseIntArray();
        }
        this.layouts.put(i2, i3);
    }

    /* access modifiers changed from: protected */
    public int getDefItemViewType(int i2) {
        MultiItemEntity multiItemEntity = (MultiItemEntity) this.mData.get(i2);
        return multiItemEntity != null ? multiItemEntity.getItemType() : DEFAULT_VIEW_TYPE;
    }

    public int getParentPositionInAll(int i2) {
        List data = getData();
        MultiItemEntity multiItemEntity = (MultiItemEntity) getItem(i2);
        if (isExpandable(multiItemEntity)) {
            IExpandable iExpandable = (IExpandable) multiItemEntity;
            for (int i3 = i2 - 1; i3 >= 0; i3--) {
                MultiItemEntity multiItemEntity2 = (MultiItemEntity) data.get(i3);
                if (isExpandable(multiItemEntity2) && iExpandable.getLevel() > ((IExpandable) multiItemEntity2).getLevel()) {
                    return i3;
                }
            }
            return -1;
        }
        for (int i4 = i2 - 1; i4 >= 0; i4--) {
            if (isExpandable((MultiItemEntity) data.get(i4))) {
                return i4;
            }
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public K onCreateDefViewHolder(ViewGroup viewGroup, int i2) {
        return createBaseViewHolder(viewGroup, getLayoutId(i2));
    }

    public void remove(int i2) {
        List<T> list = this.mData;
        if (list != null && i2 >= 0 && i2 < list.size()) {
            MultiItemEntity multiItemEntity = (MultiItemEntity) this.mData.get(i2);
            if (multiItemEntity instanceof IExpandable) {
                removeAllChild((IExpandable) multiItemEntity, i2);
            }
            removeDataFromParent(multiItemEntity);
            super.remove(i2);
        }
    }

    /* access modifiers changed from: protected */
    public void removeAllChild(IExpandable iExpandable, int i2) {
        List subItems;
        if (iExpandable.isExpanded() && (subItems = iExpandable.getSubItems()) != null && subItems.size() != 0) {
            int size = subItems.size();
            for (int i3 = 0; i3 < size; i3++) {
                remove(i2 + 1);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void removeDataFromParent(T t) {
        T t2;
        int parentPosition = getParentPosition(t);
        if (parentPosition >= 0 && (t2 = (IExpandable) this.mData.get(parentPosition)) != t) {
            t2.getSubItems().remove(t);
        }
    }

    /* access modifiers changed from: protected */
    public void setDefaultViewTypeLayout(int i2) {
        addItemType(DEFAULT_VIEW_TYPE, i2);
    }

    public boolean isExpandable(MultiItemEntity multiItemEntity) {
        return multiItemEntity != null && (multiItemEntity instanceof IExpandable);
    }
}
