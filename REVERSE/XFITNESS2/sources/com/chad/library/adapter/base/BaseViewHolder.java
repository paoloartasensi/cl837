package com.chad.library.adapter.base;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class BaseViewHolder extends RecyclerView.c0 {
    /* access modifiers changed from: private */
    public BaseQuickAdapter adapter;
    private Object associatedObject;
    private final LinkedHashSet<Integer> childClickViewIds = new LinkedHashSet<>();
    @Deprecated
    public View convertView;
    private final LinkedHashSet<Integer> itemChildLongClickViewIds = new LinkedHashSet<>();
    private final HashSet<Integer> nestViews = new HashSet<>();
    private final SparseArray<View> views = new SparseArray<>();

    public BaseViewHolder(View view) {
        super(view);
        this.convertView = view;
    }

    public BaseViewHolder addOnClickListener(int... iArr) {
        for (int i2 : iArr) {
            this.childClickViewIds.add(Integer.valueOf(i2));
            View view = getView(i2);
            if (view != null) {
                if (!view.isClickable()) {
                    view.setClickable(true);
                }
                view.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        int adapterPosition;
                        if (BaseViewHolder.this.adapter.getOnItemChildClickListener() != null && (adapterPosition = BaseViewHolder.this.getAdapterPosition()) != -1) {
                            BaseViewHolder.this.adapter.getOnItemChildClickListener().onItemChildClick(BaseViewHolder.this.adapter, view, adapterPosition - BaseViewHolder.this.adapter.getHeaderLayoutCount());
                        }
                    }
                });
            }
        }
        return this;
    }

    public BaseViewHolder addOnLongClickListener(int... iArr) {
        for (int i2 : iArr) {
            this.itemChildLongClickViewIds.add(Integer.valueOf(i2));
            View view = getView(i2);
            if (view != null) {
                if (!view.isLongClickable()) {
                    view.setLongClickable(true);
                }
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    public boolean onLongClick(View view) {
                        int adapterPosition;
                        if (BaseViewHolder.this.adapter.getOnItemChildLongClickListener() == null || (adapterPosition = BaseViewHolder.this.getAdapterPosition()) == -1) {
                            return false;
                        }
                        return BaseViewHolder.this.adapter.getOnItemChildLongClickListener().onItemChildLongClick(BaseViewHolder.this.adapter, view, adapterPosition - BaseViewHolder.this.adapter.getHeaderLayoutCount());
                    }
                });
            }
        }
        return this;
    }

    public Object getAssociatedObject() {
        return this.associatedObject;
    }

    public HashSet<Integer> getChildClickViewIds() {
        return this.childClickViewIds;
    }

    @Deprecated
    public View getConvertView() {
        return this.convertView;
    }

    public HashSet<Integer> getItemChildLongClickViewIds() {
        return this.itemChildLongClickViewIds;
    }

    public Set<Integer> getNestViews() {
        return this.nestViews;
    }

    public <T extends View> T getView(int i2) {
        T t = (View) this.views.get(i2);
        if (t != null) {
            return t;
        }
        T findViewById = this.itemView.findViewById(i2);
        this.views.put(i2, findViewById);
        return findViewById;
    }

    public BaseViewHolder linkify(int i2) {
        Linkify.addLinks((TextView) getView(i2), 15);
        return this;
    }

    public BaseViewHolder setAdapter(int i2, Adapter adapter2) {
        ((AdapterView) getView(i2)).setAdapter(adapter2);
        return this;
    }

    public BaseViewHolder setAlpha(int i2, float f2) {
        if (Build.VERSION.SDK_INT >= 11) {
            getView(i2).setAlpha(f2);
        } else {
            AlphaAnimation alphaAnimation = new AlphaAnimation(f2, f2);
            alphaAnimation.setDuration(0);
            alphaAnimation.setFillAfter(true);
            getView(i2).startAnimation(alphaAnimation);
        }
        return this;
    }

    public void setAssociatedObject(Object obj) {
        this.associatedObject = obj;
    }

    public BaseViewHolder setBackgroundColor(int i2, int i3) {
        getView(i2).setBackgroundColor(i3);
        return this;
    }

    public BaseViewHolder setBackgroundRes(int i2, int i3) {
        getView(i2).setBackgroundResource(i3);
        return this;
    }

    public BaseViewHolder setChecked(int i2, boolean z) {
        View view = getView(i2);
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(z);
        }
        return this;
    }

    public BaseViewHolder setEnabled(int i2, boolean z) {
        getView(i2).setEnabled(z);
        return this;
    }

    public BaseViewHolder setGone(int i2, boolean z) {
        getView(i2).setVisibility(z ? 0 : 8);
        return this;
    }

    public BaseViewHolder setImageBitmap(int i2, Bitmap bitmap) {
        ((ImageView) getView(i2)).setImageBitmap(bitmap);
        return this;
    }

    public BaseViewHolder setImageDrawable(int i2, Drawable drawable) {
        ((ImageView) getView(i2)).setImageDrawable(drawable);
        return this;
    }

    public BaseViewHolder setImageResource(int i2, int i3) {
        ((ImageView) getView(i2)).setImageResource(i3);
        return this;
    }

    public BaseViewHolder setMax(int i2, int i3) {
        ((ProgressBar) getView(i2)).setMax(i3);
        return this;
    }

    public BaseViewHolder setNestView(int... iArr) {
        for (int valueOf : iArr) {
            this.nestViews.add(Integer.valueOf(valueOf));
        }
        addOnClickListener(iArr);
        addOnLongClickListener(iArr);
        return this;
    }

    public BaseViewHolder setOnCheckedChangeListener(int i2, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        ((CompoundButton) getView(i2)).setOnCheckedChangeListener(onCheckedChangeListener);
        return this;
    }

    @Deprecated
    public BaseViewHolder setOnClickListener(int i2, View.OnClickListener onClickListener) {
        getView(i2).setOnClickListener(onClickListener);
        return this;
    }

    @Deprecated
    public BaseViewHolder setOnItemClickListener(int i2, AdapterView.OnItemClickListener onItemClickListener) {
        ((AdapterView) getView(i2)).setOnItemClickListener(onItemClickListener);
        return this;
    }

    public BaseViewHolder setOnItemLongClickListener(int i2, AdapterView.OnItemLongClickListener onItemLongClickListener) {
        ((AdapterView) getView(i2)).setOnItemLongClickListener(onItemLongClickListener);
        return this;
    }

    public BaseViewHolder setOnItemSelectedClickListener(int i2, AdapterView.OnItemSelectedListener onItemSelectedListener) {
        ((AdapterView) getView(i2)).setOnItemSelectedListener(onItemSelectedListener);
        return this;
    }

    @Deprecated
    public BaseViewHolder setOnLongClickListener(int i2, View.OnLongClickListener onLongClickListener) {
        getView(i2).setOnLongClickListener(onLongClickListener);
        return this;
    }

    @Deprecated
    public BaseViewHolder setOnTouchListener(int i2, View.OnTouchListener onTouchListener) {
        getView(i2).setOnTouchListener(onTouchListener);
        return this;
    }

    public BaseViewHolder setProgress(int i2, int i3) {
        ((ProgressBar) getView(i2)).setProgress(i3);
        return this;
    }

    public BaseViewHolder setRating(int i2, float f2) {
        ((RatingBar) getView(i2)).setRating(f2);
        return this;
    }

    public BaseViewHolder setTag(int i2, Object obj) {
        getView(i2).setTag(obj);
        return this;
    }

    public BaseViewHolder setText(int i2, CharSequence charSequence) {
        ((TextView) getView(i2)).setText(charSequence);
        return this;
    }

    public BaseViewHolder setTextColor(int i2, int i3) {
        ((TextView) getView(i2)).setTextColor(i3);
        return this;
    }

    public BaseViewHolder setTypeface(int i2, Typeface typeface) {
        TextView textView = (TextView) getView(i2);
        textView.setTypeface(typeface);
        textView.setPaintFlags(textView.getPaintFlags() | 128);
        return this;
    }

    public BaseViewHolder setVisible(int i2, boolean z) {
        getView(i2).setVisibility(z ? 0 : 4);
        return this;
    }

    /* access modifiers changed from: protected */
    public BaseViewHolder setAdapter(BaseQuickAdapter baseQuickAdapter) {
        this.adapter = baseQuickAdapter;
        return this;
    }

    public BaseViewHolder setProgress(int i2, int i3, int i4) {
        ProgressBar progressBar = (ProgressBar) getView(i2);
        progressBar.setMax(i4);
        progressBar.setProgress(i3);
        return this;
    }

    public BaseViewHolder setRating(int i2, float f2, int i3) {
        RatingBar ratingBar = (RatingBar) getView(i2);
        ratingBar.setMax(i3);
        ratingBar.setRating(f2);
        return this;
    }

    public BaseViewHolder setTag(int i2, int i3, Object obj) {
        getView(i2).setTag(i3, obj);
        return this;
    }

    public BaseViewHolder setText(int i2, int i3) {
        ((TextView) getView(i2)).setText(i3);
        return this;
    }

    public BaseViewHolder setTypeface(Typeface typeface, int... iArr) {
        for (int view : iArr) {
            TextView textView = (TextView) getView(view);
            textView.setTypeface(typeface);
            textView.setPaintFlags(textView.getPaintFlags() | 128);
        }
        return this;
    }
}
