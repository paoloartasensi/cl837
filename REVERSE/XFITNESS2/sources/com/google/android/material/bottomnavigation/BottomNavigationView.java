package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.m;
import androidx.appcompat.widget.g0;
import androidx.core.h.v;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R$attr;
import com.google.android.material.R$color;
import com.google.android.material.R$dimen;
import com.google.android.material.R$style;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.k;

public class BottomNavigationView extends FrameLayout {
    private final g e;

    /* renamed from: f  reason: collision with root package name */
    private final BottomNavigationMenuView f1420f;

    /* renamed from: g  reason: collision with root package name */
    private final BottomNavigationPresenter f1421g;

    /* renamed from: h  reason: collision with root package name */
    private MenuInflater f1422h;
    /* access modifiers changed from: private */

    /* renamed from: i  reason: collision with root package name */
    public c f1423i;
    /* access modifiers changed from: private */

    /* renamed from: j  reason: collision with root package name */
    public b f1424j;

    static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();

        /* renamed from: g  reason: collision with root package name */
        Bundle f1425g;

        static class a implements Parcelable.ClassLoaderCreator<SavedState> {
            a() {
            }

            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private void a(Parcel parcel, ClassLoader classLoader) {
            this.f1425g = parcel.readBundle(classLoader);
        }

        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeBundle(this.f1425g);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            a(parcel, classLoader);
        }
    }

    class a implements g.a {
        a() {
        }

        public void a(g gVar) {
        }

        public boolean a(g gVar, MenuItem menuItem) {
            if (BottomNavigationView.this.f1424j != null && menuItem.getItemId() == BottomNavigationView.this.getSelectedItemId()) {
                BottomNavigationView.this.f1424j.a(menuItem);
                return true;
            } else if (BottomNavigationView.this.f1423i == null || BottomNavigationView.this.f1423i.a(menuItem)) {
                return false;
            } else {
                return true;
            }
        }
    }

    public interface b {
        void a(MenuItem menuItem);
    }

    public interface c {
        boolean a(MenuItem menuItem);
    }

    public BottomNavigationView(Context context) {
        this(context, (AttributeSet) null);
    }

    private MenuInflater getMenuInflater() {
        if (this.f1422h == null) {
            this.f1422h = new androidx.appcompat.d.g(getContext());
        }
        return this.f1422h;
    }

    public Drawable getItemBackground() {
        return this.f1420f.getItemBackground();
    }

    @Deprecated
    public int getItemBackgroundResource() {
        return this.f1420f.getItemBackgroundRes();
    }

    public int getItemIconSize() {
        return this.f1420f.getItemIconSize();
    }

    public ColorStateList getItemIconTintList() {
        return this.f1420f.getIconTintList();
    }

    public int getItemTextAppearanceActive() {
        return this.f1420f.getItemTextAppearanceActive();
    }

    public int getItemTextAppearanceInactive() {
        return this.f1420f.getItemTextAppearanceInactive();
    }

    public ColorStateList getItemTextColor() {
        return this.f1420f.getItemTextColor();
    }

    public int getLabelVisibilityMode() {
        return this.f1420f.getLabelVisibilityMode();
    }

    public int getMaxItemCount() {
        return 5;
    }

    public Menu getMenu() {
        return this.e;
    }

    public int getSelectedItemId() {
        return this.f1420f.getSelectedItemId();
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.a());
        this.e.b(savedState.f1425g);
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        Bundle bundle = new Bundle();
        savedState.f1425g = bundle;
        this.e.d(bundle);
        return savedState;
    }

    public void setItemBackground(Drawable drawable) {
        this.f1420f.setItemBackground(drawable);
    }

    public void setItemBackgroundResource(int i2) {
        this.f1420f.setItemBackgroundRes(i2);
    }

    public void setItemHorizontalTranslationEnabled(boolean z) {
        if (this.f1420f.b() != z) {
            this.f1420f.setItemHorizontalTranslationEnabled(z);
            this.f1421g.a(false);
        }
    }

    public void setItemIconSize(int i2) {
        this.f1420f.setItemIconSize(i2);
    }

    public void setItemIconSizeRes(int i2) {
        setItemIconSize(getResources().getDimensionPixelSize(i2));
    }

    public void setItemIconTintList(ColorStateList colorStateList) {
        this.f1420f.setIconTintList(colorStateList);
    }

    public void setItemTextAppearanceActive(int i2) {
        this.f1420f.setItemTextAppearanceActive(i2);
    }

    public void setItemTextAppearanceInactive(int i2) {
        this.f1420f.setItemTextAppearanceInactive(i2);
    }

    public void setItemTextColor(ColorStateList colorStateList) {
        this.f1420f.setItemTextColor(colorStateList);
    }

    public void setLabelVisibilityMode(int i2) {
        if (this.f1420f.getLabelVisibilityMode() != i2) {
            this.f1420f.setLabelVisibilityMode(i2);
            this.f1421g.a(false);
        }
    }

    public void setOnNavigationItemReselectedListener(b bVar) {
        this.f1424j = bVar;
    }

    public void setOnNavigationItemSelectedListener(c cVar) {
        this.f1423i = cVar;
    }

    public void setSelectedItemId(int i2) {
        MenuItem findItem = this.e.findItem(i2);
        if (findItem != null && !this.e.a(findItem, (m) this.f1421g, 0)) {
            findItem.setChecked(true);
        }
    }

    public BottomNavigationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.bottomNavigationStyle);
    }

    public void a(int i2) {
        this.f1421g.b(true);
        getMenuInflater().inflate(i2, this.e);
        this.f1421g.b(false);
        this.f1421g.a(true);
    }

    public BottomNavigationView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f1421g = new BottomNavigationPresenter();
        this.e = new a(context);
        this.f1420f = new BottomNavigationMenuView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        this.f1420f.setLayoutParams(layoutParams);
        this.f1421g.a(this.f1420f);
        this.f1421g.a(1);
        this.f1420f.setPresenter(this.f1421g);
        this.e.a((m) this.f1421g);
        this.f1421g.a(getContext(), this.e);
        g0 d = k.d(context, attributeSet, R$styleable.BottomNavigationView, i2, R$style.Widget_Design_BottomNavigationView, R$styleable.BottomNavigationView_itemTextAppearanceInactive, R$styleable.BottomNavigationView_itemTextAppearanceActive);
        if (d.g(R$styleable.BottomNavigationView_itemIconTint)) {
            this.f1420f.setIconTintList(d.a(R$styleable.BottomNavigationView_itemIconTint));
        } else {
            BottomNavigationMenuView bottomNavigationMenuView = this.f1420f;
            bottomNavigationMenuView.setIconTintList(bottomNavigationMenuView.a(16842808));
        }
        setItemIconSize(d.c(R$styleable.BottomNavigationView_itemIconSize, getResources().getDimensionPixelSize(R$dimen.design_bottom_navigation_icon_size)));
        if (d.g(R$styleable.BottomNavigationView_itemTextAppearanceInactive)) {
            setItemTextAppearanceInactive(d.g(R$styleable.BottomNavigationView_itemTextAppearanceInactive, 0));
        }
        if (d.g(R$styleable.BottomNavigationView_itemTextAppearanceActive)) {
            setItemTextAppearanceActive(d.g(R$styleable.BottomNavigationView_itemTextAppearanceActive, 0));
        }
        if (d.g(R$styleable.BottomNavigationView_itemTextColor)) {
            setItemTextColor(d.a(R$styleable.BottomNavigationView_itemTextColor));
        }
        if (d.g(R$styleable.BottomNavigationView_elevation)) {
            v.a((View) this, (float) d.c(R$styleable.BottomNavigationView_elevation, 0));
        }
        setLabelVisibilityMode(d.e(R$styleable.BottomNavigationView_labelVisibilityMode, -1));
        setItemHorizontalTranslationEnabled(d.a(R$styleable.BottomNavigationView_itemHorizontalTranslationEnabled, true));
        this.f1420f.setItemBackgroundRes(d.g(R$styleable.BottomNavigationView_itemBackground, 0));
        if (d.g(R$styleable.BottomNavigationView_menu)) {
            a(d.g(R$styleable.BottomNavigationView_menu, 0));
        }
        d.a();
        addView(this.f1420f, layoutParams);
        if (Build.VERSION.SDK_INT < 21) {
            a(context);
        }
        this.e.a((g.a) new a());
    }

    private void a(Context context) {
        View view = new View(context);
        view.setBackgroundColor(androidx.core.content.a.a(context, R$color.design_bottom_navigation_shadow_color));
        view.setLayoutParams(new FrameLayout.LayoutParams(-1, getResources().getDimensionPixelSize(R$dimen.design_bottom_navigation_shadow_height)));
        addView(view);
    }
}
