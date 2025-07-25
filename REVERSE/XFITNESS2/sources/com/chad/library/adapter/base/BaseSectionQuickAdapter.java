package com.chad.library.adapter.base;

import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.SectionEntity;
import java.util.List;

public abstract class BaseSectionQuickAdapter<T extends SectionEntity, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    protected static final int SECTION_HEADER_VIEW = 1092;
    protected int mSectionHeadResId;

    public BaseSectionQuickAdapter(int i2, int i3, List<T> list) {
        super(i2, list);
        this.mSectionHeadResId = i3;
    }

    /* access modifiers changed from: protected */
    public abstract void convertHead(K k, T t);

    /* access modifiers changed from: protected */
    public int getDefItemViewType(int i2) {
        if (((SectionEntity) this.mData.get(i2)).isHeader) {
            return SECTION_HEADER_VIEW;
        }
        return 0;
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
        return super.onCreateDefViewHolder(viewGroup, i2);
    }

    public void onBindViewHolder(K k, int i2) {
        if (k.getItemViewType() != SECTION_HEADER_VIEW) {
            super.onBindViewHolder(k, i2);
            return;
        }
        setFullSpan(k);
        convertHead(k, (SectionEntity) getItem(i2 - getHeaderLayoutCount()));
    }
}
