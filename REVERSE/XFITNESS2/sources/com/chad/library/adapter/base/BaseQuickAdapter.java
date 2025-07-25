package com.chad.library.adapter.base;

import android.animation.Animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.recyclerview.widget.f;
import androidx.recyclerview.widget.o;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.animation.AlphaInAnimation;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.chad.library.adapter.base.animation.ScaleInAnimation;
import com.chad.library.adapter.base.animation.SlideInBottomAnimation;
import com.chad.library.adapter.base.animation.SlideInLeftAnimation;
import com.chad.library.adapter.base.animation.SlideInRightAnimation;
import com.chad.library.adapter.base.diff.BaseQuickAdapterListUpdateCallback;
import com.chad.library.adapter.base.diff.BaseQuickDiffCallback;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseQuickAdapter<T, K extends BaseViewHolder> extends RecyclerView.g<K> {
    public static final int ALPHAIN = 1;
    public static final int EMPTY_VIEW = 1365;
    public static final int FOOTER_VIEW = 819;
    public static final int HEADER_VIEW = 273;
    public static final int LOADING_VIEW = 546;
    public static final int SCALEIN = 2;
    public static final int SLIDEIN_BOTTOM = 3;
    public static final int SLIDEIN_LEFT = 4;
    public static final int SLIDEIN_RIGHT = 5;
    protected static final String TAG = "BaseQuickAdapter";
    private boolean footerViewAsFlow;
    private boolean headerViewAsFlow;
    protected Context mContext;
    private BaseAnimation mCustomAnimation;
    protected List<T> mData;
    private int mDuration;
    private FrameLayout mEmptyLayout;
    /* access modifiers changed from: private */
    public boolean mEnableLoadMoreEndClick;
    private boolean mFirstOnlyEnable;
    private boolean mFootAndEmptyEnable;
    private LinearLayout mFooterLayout;
    private boolean mHeadAndEmptyEnable;
    private LinearLayout mHeaderLayout;
    private Interpolator mInterpolator;
    private boolean mIsUseEmpty;
    private int mLastPosition;
    protected LayoutInflater mLayoutInflater;
    protected int mLayoutResId;
    private boolean mLoadMoreEnable;
    /* access modifiers changed from: private */
    public LoadMoreView mLoadMoreView;
    private boolean mLoading;
    private boolean mNextLoadEnable;
    private OnItemChildClickListener mOnItemChildClickListener;
    private OnItemChildLongClickListener mOnItemChildLongClickListener;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private boolean mOpenAnimationEnable;
    private int mPreLoadNumber;
    private RecyclerView mRecyclerView;
    /* access modifiers changed from: private */
    public RequestLoadMoreListener mRequestLoadMoreListener;
    private BaseAnimation mSelectAnimation;
    /* access modifiers changed from: private */
    public SpanSizeLookup mSpanSizeLookup;
    private int mStartUpFetchPosition;
    private boolean mUpFetchEnable;
    private UpFetchListener mUpFetchListener;
    private boolean mUpFetching;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }

    public interface OnItemChildClickListener {
        void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i2);
    }

    public interface OnItemChildLongClickListener {
        boolean onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i2);
    }

    public interface OnItemClickListener {
        void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i2);
    }

    public interface RequestLoadMoreListener {
        void onLoadMoreRequested();
    }

    public interface SpanSizeLookup {
        int getSpanSize(GridLayoutManager gridLayoutManager, int i2);
    }

    public interface UpFetchListener {
        void onUpFetch();
    }

    public BaseQuickAdapter(int i2, List<T> list) {
        this.mNextLoadEnable = false;
        this.mLoadMoreEnable = false;
        this.mLoading = false;
        this.mLoadMoreView = new SimpleLoadMoreView();
        this.mEnableLoadMoreEndClick = false;
        this.mFirstOnlyEnable = true;
        this.mOpenAnimationEnable = false;
        this.mInterpolator = new LinearInterpolator();
        this.mDuration = 300;
        this.mLastPosition = -1;
        this.mSelectAnimation = new AlphaInAnimation();
        this.mIsUseEmpty = true;
        this.mPreLoadNumber = 1;
        this.mStartUpFetchPosition = 1;
        this.mData = list == null ? new ArrayList<>() : list;
        if (i2 != 0) {
            this.mLayoutResId = i2;
        }
    }

    private void addAnimation(RecyclerView.c0 c0Var) {
        if (!this.mOpenAnimationEnable) {
            return;
        }
        if (!this.mFirstOnlyEnable || c0Var.getLayoutPosition() > this.mLastPosition) {
            BaseAnimation baseAnimation = this.mCustomAnimation;
            if (baseAnimation == null) {
                baseAnimation = this.mSelectAnimation;
            }
            for (Animator startAnim : baseAnimation.getAnimators(c0Var.itemView)) {
                startAnim(startAnim, c0Var.getLayoutPosition());
            }
            this.mLastPosition = c0Var.getLayoutPosition();
        }
    }

    private void autoLoadMore(int i2) {
        if (getLoadMoreViewCount() != 0 && i2 >= getItemCount() - this.mPreLoadNumber && this.mLoadMoreView.getLoadMoreStatus() == 1) {
            this.mLoadMoreView.setLoadMoreStatus(2);
            if (!this.mLoading) {
                this.mLoading = true;
                if (getRecyclerView() != null) {
                    getRecyclerView().post(new Runnable() {
                        public void run() {
                            BaseQuickAdapter.this.mRequestLoadMoreListener.onLoadMoreRequested();
                        }
                    });
                } else {
                    this.mRequestLoadMoreListener.onLoadMoreRequested();
                }
            }
        }
    }

    private void autoUpFetch(int i2) {
        UpFetchListener upFetchListener;
        if (isUpFetchEnable() && !isUpFetching() && i2 <= this.mStartUpFetchPosition && (upFetchListener = this.mUpFetchListener) != null) {
            upFetchListener.onUpFetch();
        }
    }

    private void checkNotNull() {
        if (getRecyclerView() == null) {
            throw new IllegalStateException("please bind recyclerView first!");
        }
    }

    private void compatibilityDataSizeChanged(int i2) {
        List<T> list = this.mData;
        if ((list == null ? 0 : list.size()) == i2) {
            notifyDataSetChanged();
        }
    }

    private K createGenericKInstance(Class cls, View view) {
        try {
            if (!cls.isMemberClass() || Modifier.isStatic(cls.getModifiers())) {
                Constructor declaredConstructor = cls.getDeclaredConstructor(new Class[]{View.class});
                declaredConstructor.setAccessible(true);
                return (BaseViewHolder) declaredConstructor.newInstance(new Object[]{view});
            }
            Constructor declaredConstructor2 = cls.getDeclaredConstructor(new Class[]{getClass(), View.class});
            declaredConstructor2.setAccessible(true);
            return (BaseViewHolder) declaredConstructor2.newInstance(new Object[]{this, view});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return null;
        } catch (InstantiationException e3) {
            e3.printStackTrace();
            return null;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    private IExpandable getExpandableItem(int i2) {
        Object item = getItem(i2);
        if (isExpandable(item)) {
            return (IExpandable) item;
        }
        return null;
    }

    private int getFooterViewPosition() {
        int i2 = 1;
        if (getEmptyViewCount() != 1) {
            return getHeaderLayoutCount() + this.mData.size();
        }
        if (this.mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
            i2 = 2;
        }
        if (this.mFootAndEmptyEnable) {
            return i2;
        }
        return -1;
    }

    private int getHeaderViewPosition() {
        if (getEmptyViewCount() != 1 || this.mHeadAndEmptyEnable) {
            return 0;
        }
        return -1;
    }

    private Class getInstancedGenericKClass(Class cls) {
        Class<BaseViewHolder> cls2 = BaseViewHolder.class;
        Type genericSuperclass = cls.getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            return null;
        }
        for (Type type : ((ParameterizedType) genericSuperclass).getActualTypeArguments()) {
            if (type instanceof Class) {
                Class cls3 = (Class) type;
                if (cls2.isAssignableFrom(cls3)) {
                    return cls3;
                }
            } else if (type instanceof ParameterizedType) {
                Type rawType = ((ParameterizedType) type).getRawType();
                if (rawType instanceof Class) {
                    Class cls4 = (Class) rawType;
                    if (cls2.isAssignableFrom(cls4)) {
                        return cls4;
                    }
                } else {
                    continue;
                }
            } else {
                continue;
            }
        }
        return null;
    }

    private int getItemPosition(T t) {
        List<T> list;
        if (t == null || (list = this.mData) == null || list.isEmpty()) {
            return -1;
        }
        return this.mData.indexOf(t);
    }

    private K getLoadingView(ViewGroup viewGroup) {
        K createBaseViewHolder = createBaseViewHolder(getItemView(this.mLoadMoreView.getLayoutId(), viewGroup));
        createBaseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (BaseQuickAdapter.this.mLoadMoreView.getLoadMoreStatus() == 3) {
                    BaseQuickAdapter.this.notifyLoadMoreToLoading();
                }
                if (BaseQuickAdapter.this.mEnableLoadMoreEndClick && BaseQuickAdapter.this.mLoadMoreView.getLoadMoreStatus() == 4) {
                    BaseQuickAdapter.this.notifyLoadMoreToLoading();
                }
            }
        });
        return createBaseViewHolder;
    }

    /* access modifiers changed from: private */
    public int getTheBiggestNumber(int[] iArr) {
        int i2 = -1;
        if (!(iArr == null || iArr.length == 0)) {
            for (int i3 : iArr) {
                if (i3 > i2) {
                    i2 = i3;
                }
            }
        }
        return i2;
    }

    /* access modifiers changed from: private */
    public boolean isFullScreen(LinearLayoutManager linearLayoutManager) {
        if (linearLayoutManager.I() + 1 == getItemCount() && linearLayoutManager.G() == 0) {
            return false;
        }
        return true;
    }

    private void openLoadMore(RequestLoadMoreListener requestLoadMoreListener) {
        this.mRequestLoadMoreListener = requestLoadMoreListener;
        this.mNextLoadEnable = true;
        this.mLoadMoreEnable = true;
        this.mLoading = false;
    }

    private int recursiveCollapse(int i2) {
        Object item = getItem(i2);
        if (item == null || !isExpandable(item)) {
            return 0;
        }
        IExpandable iExpandable = (IExpandable) item;
        if (!iExpandable.isExpanded()) {
            return 0;
        }
        ArrayList arrayList = new ArrayList();
        int level = iExpandable.getLevel();
        int size = this.mData.size();
        for (int i3 = i2 + 1; i3 < size; i3++) {
            T t = this.mData.get(i3);
            if ((t instanceof IExpandable) && ((IExpandable) t).getLevel() <= level) {
                break;
            }
            arrayList.add(t);
        }
        this.mData.removeAll(arrayList);
        return arrayList.size();
    }

    private int recursiveExpand(int i2, List list) {
        int size = list.size();
        int size2 = (i2 + list.size()) - 1;
        int size3 = list.size() - 1;
        while (size3 >= 0) {
            if (list.get(size3) instanceof IExpandable) {
                IExpandable iExpandable = (IExpandable) list.get(size3);
                if (iExpandable.isExpanded() && hasSubItems(iExpandable)) {
                    List subItems = iExpandable.getSubItems();
                    int i3 = size2 + 1;
                    this.mData.addAll(i3, subItems);
                    size += recursiveExpand(i3, subItems);
                }
            }
            size3--;
            size2--;
        }
        return size;
    }

    private void setRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    @Deprecated
    public void add(int i2, T t) {
        addData(i2, t);
    }

    public void addData(int i2, T t) {
        this.mData.add(i2, t);
        notifyItemInserted(i2 + getHeaderLayoutCount());
        compatibilityDataSizeChanged(1);
    }

    public int addFooterView(View view) {
        return addFooterView(view, -1, 1);
    }

    public int addHeaderView(View view) {
        return addHeaderView(view, -1);
    }

    public void bindToRecyclerView(RecyclerView recyclerView) {
        if (getRecyclerView() != recyclerView) {
            setRecyclerView(recyclerView);
            getRecyclerView().setAdapter(this);
            return;
        }
        throw new IllegalStateException("Don't bind twice");
    }

    /* access modifiers changed from: protected */
    public void bindViewClickListener(final K k) {
        if (k != null) {
            View view = k.itemView;
            if (getOnItemClickListener() != null) {
                view.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        int adapterPosition = k.getAdapterPosition();
                        if (adapterPosition != -1) {
                            BaseQuickAdapter.this.setOnItemClick(view, adapterPosition - BaseQuickAdapter.this.getHeaderLayoutCount());
                        }
                    }
                });
            }
            if (getOnItemLongClickListener() != null) {
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    public boolean onLongClick(View view) {
                        int adapterPosition = k.getAdapterPosition();
                        if (adapterPosition == -1) {
                            return false;
                        }
                        return BaseQuickAdapter.this.setOnItemLongClick(view, adapterPosition - BaseQuickAdapter.this.getHeaderLayoutCount());
                    }
                });
            }
        }
    }

    public void closeLoadAnimation() {
        this.mOpenAnimationEnable = false;
    }

    public int collapse(int i2, boolean z, boolean z2) {
        int headerLayoutCount = i2 - getHeaderLayoutCount();
        IExpandable expandableItem = getExpandableItem(headerLayoutCount);
        if (expandableItem == null) {
            return 0;
        }
        int recursiveCollapse = recursiveCollapse(headerLayoutCount);
        expandableItem.setExpanded(false);
        int headerLayoutCount2 = headerLayoutCount + getHeaderLayoutCount();
        if (z2) {
            if (z) {
                notifyItemChanged(headerLayoutCount2);
                notifyItemRangeRemoved(headerLayoutCount2 + 1, recursiveCollapse);
            } else {
                notifyDataSetChanged();
            }
        }
        return recursiveCollapse;
    }

    /* access modifiers changed from: protected */
    public abstract void convert(K k, T t);

    /* access modifiers changed from: protected */
    public void convertPayloads(K k, T t, List<Object> list) {
    }

    /* access modifiers changed from: protected */
    public K createBaseViewHolder(ViewGroup viewGroup, int i2) {
        return createBaseViewHolder(getItemView(i2, viewGroup));
    }

    public void disableLoadMoreIfNotFullPage() {
        checkNotNull();
        disableLoadMoreIfNotFullPage(getRecyclerView());
    }

    public void enableLoadMoreEndClick(boolean z) {
        this.mEnableLoadMoreEndClick = z;
    }

    public int expand(int i2, boolean z, boolean z2) {
        int headerLayoutCount = i2 - getHeaderLayoutCount();
        IExpandable expandableItem = getExpandableItem(headerLayoutCount);
        int i3 = 0;
        if (expandableItem == null) {
            return 0;
        }
        if (!hasSubItems(expandableItem)) {
            expandableItem.setExpanded(true);
            notifyItemChanged(headerLayoutCount);
            return 0;
        }
        if (!expandableItem.isExpanded()) {
            List subItems = expandableItem.getSubItems();
            int i4 = headerLayoutCount + 1;
            this.mData.addAll(i4, subItems);
            i3 = 0 + recursiveExpand(i4, subItems);
            expandableItem.setExpanded(true);
        }
        int headerLayoutCount2 = headerLayoutCount + getHeaderLayoutCount();
        if (z2) {
            if (z) {
                notifyItemChanged(headerLayoutCount2);
                notifyItemRangeInserted(headerLayoutCount2 + 1, i3);
            } else {
                notifyDataSetChanged();
            }
        }
        return i3;
    }

    public int expandAll(int i2, boolean z, boolean z2) {
        Object item;
        int headerLayoutCount = i2 - getHeaderLayoutCount();
        int i3 = headerLayoutCount + 1;
        Object item2 = i3 < this.mData.size() ? getItem(i3) : null;
        IExpandable expandableItem = getExpandableItem(headerLayoutCount);
        if (expandableItem == null) {
            return 0;
        }
        if (!hasSubItems(expandableItem)) {
            expandableItem.setExpanded(true);
            notifyItemChanged(headerLayoutCount);
            return 0;
        }
        int expand = expand(getHeaderLayoutCount() + headerLayoutCount, false, false);
        while (i3 < this.mData.size() && ((item = getItem(i3)) == null || !item.equals(item2))) {
            if (isExpandable(item)) {
                expand += expand(getHeaderLayoutCount() + i3, false, false);
            }
            i3++;
        }
        if (z2) {
            if (z) {
                notifyItemRangeInserted(headerLayoutCount + getHeaderLayoutCount() + 1, expand);
            } else {
                notifyDataSetChanged();
            }
        }
        return expand;
    }

    public List<T> getData() {
        return this.mData;
    }

    /* access modifiers changed from: protected */
    public int getDefItemViewType(int i2) {
        return super.getItemViewType(i2);
    }

    public View getEmptyView() {
        return this.mEmptyLayout;
    }

    public int getEmptyViewCount() {
        FrameLayout frameLayout = this.mEmptyLayout;
        if (frameLayout == null || frameLayout.getChildCount() == 0 || !this.mIsUseEmpty || this.mData.size() != 0) {
            return 0;
        }
        return 1;
    }

    public LinearLayout getFooterLayout() {
        return this.mFooterLayout;
    }

    public int getFooterLayoutCount() {
        LinearLayout linearLayout = this.mFooterLayout;
        return (linearLayout == null || linearLayout.getChildCount() == 0) ? 0 : 1;
    }

    @Deprecated
    public int getFooterViewsCount() {
        return getFooterLayoutCount();
    }

    public LinearLayout getHeaderLayout() {
        return this.mHeaderLayout;
    }

    public int getHeaderLayoutCount() {
        LinearLayout linearLayout = this.mHeaderLayout;
        return (linearLayout == null || linearLayout.getChildCount() == 0) ? 0 : 1;
    }

    @Deprecated
    public int getHeaderViewsCount() {
        return getHeaderLayoutCount();
    }

    public T getItem(int i2) {
        if (i2 < 0 || i2 >= this.mData.size()) {
            return null;
        }
        return this.mData.get(i2);
    }

    public int getItemCount() {
        int i2 = 1;
        if (1 == getEmptyViewCount()) {
            if (this.mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
                i2 = 2;
            }
            if (!this.mFootAndEmptyEnable || getFooterLayoutCount() == 0) {
                return i2;
            }
            return i2 + 1;
        }
        return getLoadMoreViewCount() + getHeaderLayoutCount() + this.mData.size() + getFooterLayoutCount();
    }

    public long getItemId(int i2) {
        return (long) i2;
    }

    /* access modifiers changed from: protected */
    public View getItemView(int i2, ViewGroup viewGroup) {
        return this.mLayoutInflater.inflate(i2, viewGroup, false);
    }

    public int getItemViewType(int i2) {
        if (getEmptyViewCount() == 1) {
            boolean z = this.mHeadAndEmptyEnable && getHeaderLayoutCount() != 0;
            if (i2 != 0) {
                if (i2 != 1) {
                    if (i2 != 2) {
                        return EMPTY_VIEW;
                    }
                    return FOOTER_VIEW;
                } else if (z) {
                    return EMPTY_VIEW;
                } else {
                    return FOOTER_VIEW;
                }
            } else if (z) {
                return HEADER_VIEW;
            } else {
                return EMPTY_VIEW;
            }
        } else {
            int headerLayoutCount = getHeaderLayoutCount();
            if (i2 < headerLayoutCount) {
                return HEADER_VIEW;
            }
            int i3 = i2 - headerLayoutCount;
            int size = this.mData.size();
            if (i3 < size) {
                return getDefItemViewType(i3);
            }
            if (i3 - size < getFooterLayoutCount()) {
                return FOOTER_VIEW;
            }
            return LOADING_VIEW;
        }
    }

    public int getLoadMoreViewCount() {
        if (this.mRequestLoadMoreListener == null || !this.mLoadMoreEnable) {
            return 0;
        }
        if ((this.mNextLoadEnable || !this.mLoadMoreView.isLoadEndMoreGone()) && this.mData.size() != 0) {
            return 1;
        }
        return 0;
    }

    public int getLoadMoreViewPosition() {
        return getHeaderLayoutCount() + this.mData.size() + getFooterLayoutCount();
    }

    public final OnItemChildClickListener getOnItemChildClickListener() {
        return this.mOnItemChildClickListener;
    }

    public final OnItemChildLongClickListener getOnItemChildLongClickListener() {
        return this.mOnItemChildLongClickListener;
    }

    public final OnItemClickListener getOnItemClickListener() {
        return this.mOnItemClickListener;
    }

    public final OnItemLongClickListener getOnItemLongClickListener() {
        return this.mOnItemLongClickListener;
    }

    public int getParentPosition(T t) {
        int itemPosition = getItemPosition(t);
        if (itemPosition == -1) {
            return -1;
        }
        int level = t instanceof IExpandable ? ((IExpandable) t).getLevel() : Integer.MAX_VALUE;
        if (level == 0) {
            return itemPosition;
        }
        if (level == -1) {
            return -1;
        }
        while (itemPosition >= 0) {
            T t2 = this.mData.get(itemPosition);
            if (t2 instanceof IExpandable) {
                IExpandable iExpandable = (IExpandable) t2;
                if (iExpandable.getLevel() >= 0 && iExpandable.getLevel() < level) {
                    return itemPosition;
                }
            }
            itemPosition--;
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    public View getViewByPosition(int i2, int i3) {
        checkNotNull();
        return getViewByPosition(getRecyclerView(), i2, i3);
    }

    public boolean hasSubItems(IExpandable iExpandable) {
        List subItems;
        if (iExpandable == null || (subItems = iExpandable.getSubItems()) == null || subItems.size() <= 0) {
            return false;
        }
        return true;
    }

    public boolean isExpandable(T t) {
        return t != null && (t instanceof IExpandable);
    }

    public void isFirstOnly(boolean z) {
        this.mFirstOnlyEnable = z;
    }

    /* access modifiers changed from: protected */
    public boolean isFixedViewType(int i2) {
        return i2 == 1365 || i2 == 273 || i2 == 819 || i2 == 546;
    }

    public boolean isFooterViewAsFlow() {
        return this.footerViewAsFlow;
    }

    public boolean isHeaderViewAsFlow() {
        return this.headerViewAsFlow;
    }

    public boolean isLoadMoreEnable() {
        return this.mLoadMoreEnable;
    }

    public boolean isLoading() {
        return this.mLoading;
    }

    public boolean isUpFetchEnable() {
        return this.mUpFetchEnable;
    }

    public boolean isUpFetching() {
        return this.mUpFetching;
    }

    public void isUseEmpty(boolean z) {
        this.mIsUseEmpty = z;
    }

    public void loadMoreComplete() {
        if (getLoadMoreViewCount() != 0) {
            this.mLoading = false;
            this.mNextLoadEnable = true;
            this.mLoadMoreView.setLoadMoreStatus(1);
            notifyItemChanged(getLoadMoreViewPosition());
        }
    }

    public void loadMoreEnd() {
        loadMoreEnd(false);
    }

    public void loadMoreFail() {
        if (getLoadMoreViewCount() != 0) {
            this.mLoading = false;
            this.mLoadMoreView.setLoadMoreStatus(3);
            notifyItemChanged(getLoadMoreViewPosition());
        }
    }

    public void notifyLoadMoreToLoading() {
        if (this.mLoadMoreView.getLoadMoreStatus() != 2) {
            this.mLoadMoreView.setLoadMoreStatus(1);
            notifyItemChanged(getLoadMoreViewPosition());
        }
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.o layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.c P = gridLayoutManager.P();
            gridLayoutManager.a((GridLayoutManager.c) new GridLayoutManager.c() {
                public int getSpanSize(int i2) {
                    int itemViewType = BaseQuickAdapter.this.getItemViewType(i2);
                    if (itemViewType == 273 && BaseQuickAdapter.this.isHeaderViewAsFlow()) {
                        return 1;
                    }
                    if (itemViewType == 819 && BaseQuickAdapter.this.isFooterViewAsFlow()) {
                        return 1;
                    }
                    if (BaseQuickAdapter.this.mSpanSizeLookup == null) {
                        return BaseQuickAdapter.this.isFixedViewType(itemViewType) ? gridLayoutManager.O() : P.getSpanSize(i2);
                    }
                    if (BaseQuickAdapter.this.isFixedViewType(itemViewType)) {
                        return gridLayoutManager.O();
                    }
                    return BaseQuickAdapter.this.mSpanSizeLookup.getSpanSize(gridLayoutManager, i2 - BaseQuickAdapter.this.getHeaderLayoutCount());
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public K onCreateDefViewHolder(ViewGroup viewGroup, int i2) {
        return createBaseViewHolder(viewGroup, this.mLayoutResId);
    }

    public void openLoadAnimation(int i2) {
        this.mOpenAnimationEnable = true;
        this.mCustomAnimation = null;
        if (i2 == 1) {
            this.mSelectAnimation = new AlphaInAnimation();
        } else if (i2 == 2) {
            this.mSelectAnimation = new ScaleInAnimation();
        } else if (i2 == 3) {
            this.mSelectAnimation = new SlideInBottomAnimation();
        } else if (i2 == 4) {
            this.mSelectAnimation = new SlideInLeftAnimation();
        } else if (i2 == 5) {
            this.mSelectAnimation = new SlideInRightAnimation();
        }
    }

    public final void refreshNotifyItemChanged(int i2) {
        notifyItemChanged(i2 + getHeaderLayoutCount());
    }

    public void remove(int i2) {
        this.mData.remove(i2);
        int headerLayoutCount = i2 + getHeaderLayoutCount();
        notifyItemRemoved(headerLayoutCount);
        compatibilityDataSizeChanged(0);
        notifyItemRangeChanged(headerLayoutCount, this.mData.size() - headerLayoutCount);
    }

    public void removeAllFooterView() {
        if (getFooterLayoutCount() != 0) {
            this.mFooterLayout.removeAllViews();
            int footerViewPosition = getFooterViewPosition();
            if (footerViewPosition != -1) {
                notifyItemRemoved(footerViewPosition);
            }
        }
    }

    public void removeAllHeaderView() {
        if (getHeaderLayoutCount() != 0) {
            this.mHeaderLayout.removeAllViews();
            int headerViewPosition = getHeaderViewPosition();
            if (headerViewPosition != -1) {
                notifyItemRemoved(headerViewPosition);
            }
        }
    }

    public void removeFooterView(View view) {
        int footerViewPosition;
        if (getFooterLayoutCount() != 0) {
            this.mFooterLayout.removeView(view);
            if (this.mFooterLayout.getChildCount() == 0 && (footerViewPosition = getFooterViewPosition()) != -1) {
                notifyItemRemoved(footerViewPosition);
            }
        }
    }

    public void removeHeaderView(View view) {
        int headerViewPosition;
        if (getHeaderLayoutCount() != 0) {
            this.mHeaderLayout.removeView(view);
            if (this.mHeaderLayout.getChildCount() == 0 && (headerViewPosition = getHeaderViewPosition()) != -1) {
                notifyItemRemoved(headerViewPosition);
            }
        }
    }

    public void replaceData(Collection<? extends T> collection) {
        List<T> list = this.mData;
        if (collection != list) {
            list.clear();
            this.mData.addAll(collection);
        }
        notifyDataSetChanged();
    }

    @Deprecated
    public void setAutoLoadMoreSize(int i2) {
        setPreLoadNumber(i2);
    }

    public void setData(int i2, T t) {
        this.mData.set(i2, t);
        notifyItemChanged(i2 + getHeaderLayoutCount());
    }

    public void setDuration(int i2) {
        this.mDuration = i2;
    }

    public void setEmptyView(int i2, ViewGroup viewGroup) {
        setEmptyView(LayoutInflater.from(viewGroup.getContext()).inflate(i2, viewGroup, false));
    }

    public void setEnableLoadMore(boolean z) {
        int loadMoreViewCount = getLoadMoreViewCount();
        this.mLoadMoreEnable = z;
        int loadMoreViewCount2 = getLoadMoreViewCount();
        if (loadMoreViewCount == 1) {
            if (loadMoreViewCount2 == 0) {
                notifyItemRemoved(getLoadMoreViewPosition());
            }
        } else if (loadMoreViewCount2 == 1) {
            this.mLoadMoreView.setLoadMoreStatus(1);
            notifyItemInserted(getLoadMoreViewPosition());
        }
    }

    public int setFooterView(View view) {
        return setFooterView(view, 0, 1);
    }

    public void setFooterViewAsFlow(boolean z) {
        this.footerViewAsFlow = z;
    }

    /* access modifiers changed from: protected */
    public void setFullSpan(RecyclerView.c0 c0Var) {
        if (c0Var.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.c) {
            ((StaggeredGridLayoutManager.c) c0Var.itemView.getLayoutParams()).a(true);
        }
    }

    public void setHeaderAndEmpty(boolean z) {
        setHeaderFooterEmpty(z, false);
    }

    public void setHeaderFooterEmpty(boolean z, boolean z2) {
        this.mHeadAndEmptyEnable = z;
        this.mFootAndEmptyEnable = z2;
    }

    public int setHeaderView(View view) {
        return setHeaderView(view, 0, 1);
    }

    public void setHeaderViewAsFlow(boolean z) {
        this.headerViewAsFlow = z;
    }

    public void setLoadMoreView(LoadMoreView loadMoreView) {
        this.mLoadMoreView = loadMoreView;
    }

    public void setNewData(List<T> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.mData = list;
        if (this.mRequestLoadMoreListener != null) {
            this.mNextLoadEnable = true;
            this.mLoadMoreEnable = true;
            this.mLoading = false;
            this.mLoadMoreView.setLoadMoreStatus(1);
        }
        this.mLastPosition = -1;
        notifyDataSetChanged();
    }

    public void setNewDiffData(BaseQuickDiffCallback<T> baseQuickDiffCallback) {
        setNewDiffData(baseQuickDiffCallback, false);
    }

    public void setNotDoAnimationCount(int i2) {
        this.mLastPosition = i2;
    }

    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        this.mOnItemChildClickListener = onItemChildClickListener;
    }

    public void setOnItemChildLongClickListener(OnItemChildLongClickListener onItemChildLongClickListener) {
        this.mOnItemChildLongClickListener = onItemChildLongClickListener;
    }

    public void setOnItemClick(View view, int i2) {
        getOnItemClickListener().onItemClick(this, view, i2);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public boolean setOnItemLongClick(View view, int i2) {
        return getOnItemLongClickListener().onItemLongClick(this, view, i2);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    @Deprecated
    public void setOnLoadMoreListener(RequestLoadMoreListener requestLoadMoreListener) {
        openLoadMore(requestLoadMoreListener);
    }

    public void setPreLoadNumber(int i2) {
        if (i2 > 1) {
            this.mPreLoadNumber = i2;
        }
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    public void setStartUpFetchPosition(int i2) {
        this.mStartUpFetchPosition = i2;
    }

    public void setUpFetchEnable(boolean z) {
        this.mUpFetchEnable = z;
    }

    public void setUpFetchListener(UpFetchListener upFetchListener) {
        this.mUpFetchListener = upFetchListener;
    }

    public void setUpFetching(boolean z) {
        this.mUpFetching = z;
    }

    /* access modifiers changed from: protected */
    public void startAnim(Animator animator, int i2) {
        animator.setDuration((long) this.mDuration).start();
        animator.setInterpolator(this.mInterpolator);
    }

    public int addFooterView(View view, int i2) {
        return addFooterView(view, i2, 1);
    }

    public int addHeaderView(View view, int i2) {
        return addHeaderView(view, i2, 1);
    }

    /* access modifiers changed from: protected */
    public K createBaseViewHolder(View view) {
        K k;
        Class cls = getClass();
        Class cls2 = null;
        while (cls2 == null && cls != null) {
            cls2 = getInstancedGenericKClass(cls);
            cls = cls.getSuperclass();
        }
        if (cls2 == null) {
            k = new BaseViewHolder(view);
        } else {
            k = createGenericKInstance(cls2, view);
        }
        return k != null ? k : new BaseViewHolder(view);
    }

    public void loadMoreEnd(boolean z) {
        if (getLoadMoreViewCount() != 0) {
            this.mLoading = false;
            this.mNextLoadEnable = false;
            this.mLoadMoreView.setLoadMoreEndGone(z);
            if (z) {
                notifyItemRemoved(getLoadMoreViewPosition());
                return;
            }
            this.mLoadMoreView.setLoadMoreStatus(4);
            notifyItemChanged(getLoadMoreViewPosition());
        }
    }

    public K onCreateViewHolder(ViewGroup viewGroup, int i2) {
        K k;
        Context context = viewGroup.getContext();
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        if (i2 == 273) {
            ViewParent parent = this.mHeaderLayout.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.mHeaderLayout);
            }
            k = createBaseViewHolder(this.mHeaderLayout);
        } else if (i2 == 546) {
            k = getLoadingView(viewGroup);
        } else if (i2 == 819) {
            ViewParent parent2 = this.mFooterLayout.getParent();
            if (parent2 instanceof ViewGroup) {
                ((ViewGroup) parent2).removeView(this.mFooterLayout);
            }
            k = createBaseViewHolder(this.mFooterLayout);
        } else if (i2 != 1365) {
            k = onCreateDefViewHolder(viewGroup, i2);
            bindViewClickListener(k);
        } else {
            ViewParent parent3 = this.mEmptyLayout.getParent();
            if (parent3 instanceof ViewGroup) {
                ((ViewGroup) parent3).removeView(this.mEmptyLayout);
            }
            k = createBaseViewHolder(this.mEmptyLayout);
        }
        k.setAdapter(this);
        return k;
    }

    public void onViewAttachedToWindow(K k) {
        super.onViewAttachedToWindow(k);
        int itemViewType = k.getItemViewType();
        if (itemViewType == 1365 || itemViewType == 273 || itemViewType == 819 || itemViewType == 546) {
            setFullSpan(k);
        } else {
            addAnimation(k);
        }
    }

    public final void refreshNotifyItemChanged(int i2, Object obj) {
        notifyItemChanged(i2 + getHeaderLayoutCount(), obj);
    }

    public int setFooterView(View view, int i2) {
        return setFooterView(view, i2, 1);
    }

    public int setHeaderView(View view, int i2) {
        return setHeaderView(view, i2, 1);
    }

    public void setNewDiffData(BaseQuickDiffCallback<T> baseQuickDiffCallback, boolean z) {
        if (getEmptyViewCount() == 1) {
            setNewData(baseQuickDiffCallback.getNewList());
            return;
        }
        baseQuickDiffCallback.setOldList(getData());
        f.a(baseQuickDiffCallback, z).a((o) new BaseQuickAdapterListUpdateCallback(this));
        this.mData = baseQuickDiffCallback.getNewList();
    }

    public void setOnLoadMoreListener(RequestLoadMoreListener requestLoadMoreListener, RecyclerView recyclerView) {
        openLoadMore(requestLoadMoreListener);
        if (getRecyclerView() == null) {
            setRecyclerView(recyclerView);
        }
    }

    public int addFooterView(View view, int i2, int i3) {
        int footerViewPosition;
        if (this.mFooterLayout == null) {
            LinearLayout linearLayout = new LinearLayout(view.getContext());
            this.mFooterLayout = linearLayout;
            if (i3 == 1) {
                linearLayout.setOrientation(1);
                this.mFooterLayout.setLayoutParams(new RecyclerView.p(-1, -2));
            } else {
                linearLayout.setOrientation(0);
                this.mFooterLayout.setLayoutParams(new RecyclerView.p(-2, -1));
            }
        }
        int childCount = this.mFooterLayout.getChildCount();
        if (i2 < 0 || i2 > childCount) {
            i2 = childCount;
        }
        this.mFooterLayout.addView(view, i2);
        if (this.mFooterLayout.getChildCount() == 1 && (footerViewPosition = getFooterViewPosition()) != -1) {
            notifyItemInserted(footerViewPosition);
        }
        return i2;
    }

    public int addHeaderView(View view, int i2, int i3) {
        int headerViewPosition;
        if (this.mHeaderLayout == null) {
            LinearLayout linearLayout = new LinearLayout(view.getContext());
            this.mHeaderLayout = linearLayout;
            if (i3 == 1) {
                linearLayout.setOrientation(1);
                this.mHeaderLayout.setLayoutParams(new RecyclerView.p(-1, -2));
            } else {
                linearLayout.setOrientation(0);
                this.mHeaderLayout.setLayoutParams(new RecyclerView.p(-2, -1));
            }
        }
        int childCount = this.mHeaderLayout.getChildCount();
        if (i2 < 0 || i2 > childCount) {
            i2 = childCount;
        }
        this.mHeaderLayout.addView(view, i2);
        if (this.mHeaderLayout.getChildCount() == 1 && (headerViewPosition = getHeaderViewPosition()) != -1) {
            notifyItemInserted(headerViewPosition);
        }
        return i2;
    }

    public void disableLoadMoreIfNotFullPage(RecyclerView recyclerView) {
        RecyclerView.o layoutManager;
        setEnableLoadMore(false);
        if (recyclerView != null && (layoutManager = recyclerView.getLayoutManager()) != null) {
            if (layoutManager instanceof LinearLayoutManager) {
                final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                recyclerView.postDelayed(new Runnable() {
                    public void run() {
                        if (BaseQuickAdapter.this.isFullScreen(linearLayoutManager)) {
                            BaseQuickAdapter.this.setEnableLoadMore(true);
                        }
                    }
                }, 50);
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                final StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                recyclerView.postDelayed(new Runnable() {
                    public void run() {
                        int[] iArr = new int[staggeredGridLayoutManager.K()];
                        staggeredGridLayoutManager.a(iArr);
                        if (BaseQuickAdapter.this.getTheBiggestNumber(iArr) + 1 != BaseQuickAdapter.this.getItemCount()) {
                            BaseQuickAdapter.this.setEnableLoadMore(true);
                        }
                    }
                }, 50);
            }
        }
    }

    public View getViewByPosition(RecyclerView recyclerView, int i2, int i3) {
        BaseViewHolder baseViewHolder;
        if (recyclerView == null || (baseViewHolder = (BaseViewHolder) recyclerView.d(i2)) == null) {
            return null;
        }
        return baseViewHolder.getView(i3);
    }

    public void onBindViewHolder(K k, int i2) {
        autoUpFetch(i2);
        autoLoadMore(i2);
        int itemViewType = k.getItemViewType();
        if (itemViewType == 0) {
            convert(k, getItem(i2 - getHeaderLayoutCount()));
        } else if (itemViewType == 273) {
        } else {
            if (itemViewType == 546) {
                this.mLoadMoreView.convert(k);
            } else if (itemViewType != 819 && itemViewType != 1365) {
                convert(k, getItem(i2 - getHeaderLayoutCount()));
            }
        }
    }

    @Deprecated
    public void setEmptyView(int i2) {
        checkNotNull();
        setEmptyView(i2, getRecyclerView());
    }

    public int setFooterView(View view, int i2, int i3) {
        LinearLayout linearLayout = this.mFooterLayout;
        if (linearLayout == null || linearLayout.getChildCount() <= i2) {
            return addFooterView(view, i2, i3);
        }
        this.mFooterLayout.removeViewAt(i2);
        this.mFooterLayout.addView(view, i2);
        return i2;
    }

    public int setHeaderView(View view, int i2, int i3) {
        LinearLayout linearLayout = this.mHeaderLayout;
        if (linearLayout == null || linearLayout.getChildCount() <= i2) {
            return addHeaderView(view, i2, i3);
        }
        this.mHeaderLayout.removeViewAt(i2);
        this.mHeaderLayout.addView(view, i2);
        return i2;
    }

    public void addData(T t) {
        this.mData.add(t);
        notifyItemInserted(this.mData.size() + getHeaderLayoutCount());
        compatibilityDataSizeChanged(1);
    }

    public void setEmptyView(View view) {
        boolean z;
        int itemCount = getItemCount();
        int i2 = 0;
        if (this.mEmptyLayout == null) {
            this.mEmptyLayout = new FrameLayout(view.getContext());
            RecyclerView.p pVar = new RecyclerView.p(-1, -1);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams != null) {
                pVar.width = layoutParams.width;
                pVar.height = layoutParams.height;
            }
            this.mEmptyLayout.setLayoutParams(pVar);
            z = true;
        } else {
            z = false;
        }
        this.mEmptyLayout.removeAllViews();
        this.mEmptyLayout.addView(view);
        this.mIsUseEmpty = true;
        if (z && getEmptyViewCount() == 1) {
            if (this.mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
                i2 = 1;
            }
            if (getItemCount() > itemCount) {
                notifyItemInserted(i2);
            } else {
                notifyDataSetChanged();
            }
        }
    }

    public void addData(int i2, Collection<? extends T> collection) {
        this.mData.addAll(i2, collection);
        notifyItemRangeInserted(i2 + getHeaderLayoutCount(), collection.size());
        compatibilityDataSizeChanged(collection.size());
    }

    public void openLoadAnimation(BaseAnimation baseAnimation) {
        this.mOpenAnimationEnable = true;
        this.mCustomAnimation = baseAnimation;
    }

    public void setNewDiffData(f.c cVar, List<T> list) {
        if (getEmptyViewCount() == 1) {
            setNewData(list);
            return;
        }
        cVar.a((o) new BaseQuickAdapterListUpdateCallback(this));
        this.mData = list;
    }

    public int collapse(int i2) {
        return collapse(i2, true, true);
    }

    public void onBindViewHolder(K k, int i2, List<Object> list) {
        if (list.isEmpty()) {
            onBindViewHolder(k, i2);
            return;
        }
        autoUpFetch(i2);
        autoLoadMore(i2);
        int itemViewType = k.getItemViewType();
        if (itemViewType == 0) {
            convertPayloads(k, getItem(i2 - getHeaderLayoutCount()), list);
        } else if (itemViewType == 273) {
        } else {
            if (itemViewType == 546) {
                this.mLoadMoreView.convert(k);
            } else if (itemViewType != 819 && itemViewType != 1365) {
                convertPayloads(k, getItem(i2 - getHeaderLayoutCount()), list);
            }
        }
    }

    public void addData(Collection<? extends T> collection) {
        this.mData.addAll(collection);
        notifyItemRangeInserted((this.mData.size() - collection.size()) + getHeaderLayoutCount(), collection.size());
        compatibilityDataSizeChanged(collection.size());
    }

    public int collapse(int i2, boolean z) {
        return collapse(i2, z, true);
    }

    public void openLoadAnimation() {
        this.mOpenAnimationEnable = true;
    }

    public int expand(int i2, boolean z) {
        return expand(i2, z, true);
    }

    public int expand(int i2) {
        return expand(i2, true, true);
    }

    public int expandAll(int i2, boolean z) {
        return expandAll(i2, true, !z);
    }

    public void expandAll() {
        for (int size = (this.mData.size() - 1) + getHeaderLayoutCount(); size >= getHeaderLayoutCount(); size--) {
            expandAll(size, false, false);
        }
    }

    public BaseQuickAdapter(List<T> list) {
        this(0, list);
    }

    public BaseQuickAdapter(int i2) {
        this(i2, (List) null);
    }
}
