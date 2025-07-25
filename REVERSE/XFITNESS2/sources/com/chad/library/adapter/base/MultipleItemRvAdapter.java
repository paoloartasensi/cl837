package com.chad.library.adapter.base;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.chad.library.adapter.base.util.ProviderDelegate;
import java.util.List;

public abstract class MultipleItemRvAdapter<T, V extends BaseViewHolder> extends BaseQuickAdapter<T, V> {
    /* access modifiers changed from: private */
    public SparseArray<BaseItemProvider> mItemProviders;
    private MultiTypeDelegate<T> mMultiTypeDelegate;
    protected ProviderDelegate mProviderDelegate;

    public MultipleItemRvAdapter(List<T> list) {
        super(list);
    }

    private void bindClick(final V v) {
        BaseQuickAdapter.OnItemClickListener onItemClickListener = getOnItemClickListener();
        BaseQuickAdapter.OnItemLongClickListener onItemLongClickListener = getOnItemLongClickListener();
        if (onItemClickListener == null || onItemLongClickListener == null) {
            if (onItemClickListener == null) {
                v.itemView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        int adapterPosition = v.getAdapterPosition();
                        if (adapterPosition != -1) {
                            int headerLayoutCount = adapterPosition - MultipleItemRvAdapter.this.getHeaderLayoutCount();
                            ((BaseItemProvider) MultipleItemRvAdapter.this.mItemProviders.get(v.getItemViewType())).onClick(v, MultipleItemRvAdapter.this.mData.get(headerLayoutCount), headerLayoutCount);
                        }
                    }
                });
            }
            if (onItemLongClickListener == null) {
                v.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    public boolean onLongClick(View view) {
                        int adapterPosition = v.getAdapterPosition();
                        if (adapterPosition == -1) {
                            return false;
                        }
                        int headerLayoutCount = adapterPosition - MultipleItemRvAdapter.this.getHeaderLayoutCount();
                        return ((BaseItemProvider) MultipleItemRvAdapter.this.mItemProviders.get(v.getItemViewType())).onLongClick(v, MultipleItemRvAdapter.this.mData.get(headerLayoutCount), headerLayoutCount);
                    }
                });
            }
        }
    }

    /* access modifiers changed from: protected */
    public void bindViewClickListener(V v) {
        if (v != null) {
            bindClick(v);
            super.bindViewClickListener(v);
        }
    }

    /* access modifiers changed from: protected */
    public void convert(V v, T t) {
        BaseItemProvider baseItemProvider = this.mItemProviders.get(v.getItemViewType());
        baseItemProvider.mContext = v.itemView.getContext();
        baseItemProvider.convert(v, t, v.getLayoutPosition() - getHeaderLayoutCount());
    }

    /* access modifiers changed from: protected */
    public void convertPayloads(V v, T t, List<Object> list) {
        this.mItemProviders.get(v.getItemViewType()).convertPayloads(v, t, v.getLayoutPosition() - getHeaderLayoutCount(), list);
    }

    public void finishInitialize() {
        this.mProviderDelegate = new ProviderDelegate();
        setMultiTypeDelegate(new MultiTypeDelegate<T>() {
            /* access modifiers changed from: protected */
            public int getItemType(T t) {
                return MultipleItemRvAdapter.this.getViewType(t);
            }
        });
        registerItemProvider();
        this.mItemProviders = this.mProviderDelegate.getItemProviders();
        for (int i2 = 0; i2 < this.mItemProviders.size(); i2++) {
            int keyAt = this.mItemProviders.keyAt(i2);
            BaseItemProvider baseItemProvider = this.mItemProviders.get(keyAt);
            baseItemProvider.mData = this.mData;
            getMultiTypeDelegate().registerItemType(keyAt, baseItemProvider.layout());
        }
    }

    /* access modifiers changed from: protected */
    public int getDefItemViewType(int i2) {
        if (getMultiTypeDelegate() != null) {
            return getMultiTypeDelegate().getDefItemViewType(this.mData, i2);
        }
        throw new IllegalStateException("please use setMultiTypeDelegate first!");
    }

    public MultiTypeDelegate<T> getMultiTypeDelegate() {
        return this.mMultiTypeDelegate;
    }

    /* access modifiers changed from: protected */
    public abstract int getViewType(T t);

    /* access modifiers changed from: protected */
    public V onCreateDefViewHolder(ViewGroup viewGroup, int i2) {
        if (getMultiTypeDelegate() != null) {
            return createBaseViewHolder(viewGroup, getMultiTypeDelegate().getLayoutId(i2));
        }
        throw new IllegalStateException("please use setMultiTypeDelegate first!");
    }

    public abstract void registerItemProvider();

    public void setMultiTypeDelegate(MultiTypeDelegate<T> multiTypeDelegate) {
        this.mMultiTypeDelegate = multiTypeDelegate;
    }
}
