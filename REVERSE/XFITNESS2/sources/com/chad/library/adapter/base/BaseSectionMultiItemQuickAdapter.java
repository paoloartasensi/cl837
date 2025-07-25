package com.chad.library.adapter.base;

import android.util.SparseIntArray;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.SectionMultiEntity;
import java.util.List;

public abstract class BaseSectionMultiItemQuickAdapter<T extends SectionMultiEntity, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    private static final int DEFAULT_VIEW_TYPE = -255;
    protected static final int SECTION_HEADER_VIEW = 1092;
    public static final int TYPE_NOT_FOUND = -404;
    private SparseIntArray layouts;
    protected int mSectionHeadResId;

    public BaseSectionMultiItemQuickAdapter(int i2, List<T> list) {
        super(list);
        this.mSectionHeadResId = i2;
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
    public abstract void convertHead(K k, T t);

    /* access modifiers changed from: protected */
    public int getDefItemViewType(int i2) {
        SectionMultiEntity sectionMultiEntity = (SectionMultiEntity) this.mData.get(i2);
        if (sectionMultiEntity != null) {
            return sectionMultiEntity.isHeader ? SECTION_HEADER_VIEW : sectionMultiEntity.getItemType();
        }
        return DEFAULT_VIEW_TYPE;
    }

    /* access modifiers changed from: protected */
    public boolean isFixedViewType(int i2) {
        return super.isFixedViewType(i2) || i2 == SECTION_HEADER_VIEW;
    }

    /* access modifiers changed from: protected */
    public K onCreateDefViewHolder(ViewGroup viewGroup, int i2) {
        if (i2 == SECTION_HEADER_VIEW) {
            return createBaseViewHolder(getItemView(this.mSectionHeadResId, viewGroup));
        }
        return createBaseViewHolder(viewGroup, getLayoutId(i2));
    }

    public void remove(int i2) {
        List<T> list = this.mData;
        if (list != null && i2 >= 0 && i2 < list.size()) {
            SectionMultiEntity sectionMultiEntity = (SectionMultiEntity) this.mData.get(i2);
            if (sectionMultiEntity instanceof IExpandable) {
                removeAllChild((IExpandable) sectionMultiEntity, i2);
            }
            removeDataFromParent(sectionMultiEntity);
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
        int parentPosition = getParentPosition(t);
        if (parentPosition >= 0) {
            ((IExpandable) this.mData.get(parentPosition)).getSubItems().remove(t);
        }
    }

    /* access modifiers changed from: protected */
    public void setDefaultViewTypeLayout(int i2) {
        addItemType(DEFAULT_VIEW_TYPE, i2);
    }

    public void onBindViewHolder(K k, int i2) {
        if (k.getItemViewType() != SECTION_HEADER_VIEW) {
            super.onBindViewHolder(k, i2);
            return;
        }
        setFullSpan(k);
        convertHead(k, (SectionMultiEntity) getItem(i2 - getHeaderLayoutCount()));
    }
}
