package com.google.android.material.navigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.R$attr;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.view.menu.m;
import androidx.appcompat.widget.g0;
import androidx.core.h.d0;
import androidx.core.h.v;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R$style;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.ScrimInsetsFrameLayout;
import com.google.android.material.internal.f;
import com.google.android.material.internal.g;
import com.google.android.material.internal.k;

public class NavigationView extends ScrimInsetsFrameLayout {
    private static final int[] m = {16842912};
    private static final int[] n = {-16842910};

    /* renamed from: h  reason: collision with root package name */
    private final f f1525h;

    /* renamed from: i  reason: collision with root package name */
    private final g f1526i;

    /* renamed from: j  reason: collision with root package name */
    b f1527j;
    private final int k;
    private MenuInflater l;

    class a implements g.a {
        a() {
        }

        public void a(androidx.appcompat.view.menu.g gVar) {
        }

        public boolean a(androidx.appcompat.view.menu.g gVar, MenuItem menuItem) {
            b bVar = NavigationView.this.f1527j;
            return bVar != null && bVar.a(menuItem);
        }
    }

    public interface b {
        boolean a(MenuItem menuItem);
    }

    public NavigationView(Context context) {
        this(context, (AttributeSet) null);
    }

    private ColorStateList c(int i2) {
        TypedValue typedValue = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(i2, typedValue, true)) {
            return null;
        }
        ColorStateList b2 = androidx.appcompat.a.a.a.b(getContext(), typedValue.resourceId);
        if (!getContext().getTheme().resolveAttribute(R$attr.colorPrimary, typedValue, true)) {
            return null;
        }
        int i3 = typedValue.data;
        int defaultColor = b2.getDefaultColor();
        return new ColorStateList(new int[][]{n, m, FrameLayout.EMPTY_STATE_SET}, new int[]{b2.getColorForState(n, defaultColor), i3, defaultColor});
    }

    private MenuInflater getMenuInflater() {
        if (this.l == null) {
            this.l = new androidx.appcompat.d.g(getContext());
        }
        return this.l;
    }

    /* access modifiers changed from: protected */
    public void a(d0 d0Var) {
        this.f1526i.a(d0Var);
    }

    public void b(int i2) {
        this.f1526i.b(true);
        getMenuInflater().inflate(i2, this.f1525h);
        this.f1526i.b(false);
        this.f1526i.a(false);
    }

    public MenuItem getCheckedItem() {
        return this.f1526i.a();
    }

    public int getHeaderCount() {
        return this.f1526i.c();
    }

    public Drawable getItemBackground() {
        return this.f1526i.f();
    }

    public int getItemHorizontalPadding() {
        return this.f1526i.g();
    }

    public int getItemIconPadding() {
        return this.f1526i.h();
    }

    public ColorStateList getItemIconTintList() {
        return this.f1526i.j();
    }

    public ColorStateList getItemTextColor() {
        return this.f1526i.i();
    }

    public Menu getMenu() {
        return this.f1525h;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i2);
        if (mode == Integer.MIN_VALUE) {
            i2 = View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(i2), this.k), 1073741824);
        } else if (mode == 0) {
            i2 = View.MeasureSpec.makeMeasureSpec(this.k, 1073741824);
        }
        super.onMeasure(i2, i3);
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.a());
        this.f1525h.b(savedState.f1528g);
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        Bundle bundle = new Bundle();
        savedState.f1528g = bundle;
        this.f1525h.d(bundle);
        return savedState;
    }

    public void setCheckedItem(int i2) {
        MenuItem findItem = this.f1525h.findItem(i2);
        if (findItem != null) {
            this.f1526i.a((i) findItem);
        }
    }

    public void setItemBackground(Drawable drawable) {
        this.f1526i.a(drawable);
    }

    public void setItemBackgroundResource(int i2) {
        setItemBackground(androidx.core.content.a.c(getContext(), i2));
    }

    public void setItemHorizontalPadding(int i2) {
        this.f1526i.c(i2);
    }

    public void setItemHorizontalPaddingResource(int i2) {
        this.f1526i.c(getResources().getDimensionPixelSize(i2));
    }

    public void setItemIconPadding(int i2) {
        this.f1526i.d(i2);
    }

    public void setItemIconPaddingResource(int i2) {
        this.f1526i.d(getResources().getDimensionPixelSize(i2));
    }

    public void setItemIconTintList(ColorStateList colorStateList) {
        this.f1526i.a(colorStateList);
    }

    public void setItemTextAppearance(int i2) {
        this.f1526i.e(i2);
    }

    public void setItemTextColor(ColorStateList colorStateList) {
        this.f1526i.b(colorStateList);
    }

    public void setNavigationItemSelectedListener(b bVar) {
        this.f1527j = bVar;
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();

        /* renamed from: g  reason: collision with root package name */
        public Bundle f1528g;

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

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f1528g = parcel.readBundle(classLoader);
        }

        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeBundle(this.f1528g);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public NavigationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.google.android.material.R$attr.navigationViewStyle);
    }

    public View a(int i2) {
        return this.f1526i.a(i2);
    }

    public NavigationView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        ColorStateList colorStateList;
        boolean z;
        int i3;
        this.f1526i = new com.google.android.material.internal.g();
        this.f1525h = new f(context);
        g0 d = k.d(context, attributeSet, R$styleable.NavigationView, i2, R$style.Widget_Design_NavigationView, new int[0]);
        v.a((View) this, d.b(R$styleable.NavigationView_android_background));
        if (d.g(R$styleable.NavigationView_elevation)) {
            v.a((View) this, (float) d.c(R$styleable.NavigationView_elevation, 0));
        }
        v.a((View) this, d.a(R$styleable.NavigationView_android_fitsSystemWindows, false));
        this.k = d.c(R$styleable.NavigationView_android_maxWidth, 0);
        if (d.g(R$styleable.NavigationView_itemIconTint)) {
            colorStateList = d.a(R$styleable.NavigationView_itemIconTint);
        } else {
            colorStateList = c(16842808);
        }
        if (d.g(R$styleable.NavigationView_itemTextAppearance)) {
            i3 = d.g(R$styleable.NavigationView_itemTextAppearance, 0);
            z = true;
        } else {
            i3 = 0;
            z = false;
        }
        ColorStateList a2 = d.g(R$styleable.NavigationView_itemTextColor) ? d.a(R$styleable.NavigationView_itemTextColor) : null;
        if (!z && a2 == null) {
            a2 = c(16842806);
        }
        Drawable b2 = d.b(R$styleable.NavigationView_itemBackground);
        if (d.g(R$styleable.NavigationView_itemHorizontalPadding)) {
            this.f1526i.c(d.c(R$styleable.NavigationView_itemHorizontalPadding, 0));
        }
        int c = d.c(R$styleable.NavigationView_itemIconPadding, 0);
        this.f1525h.a((g.a) new a());
        this.f1526i.b(1);
        this.f1526i.a(context, (androidx.appcompat.view.menu.g) this.f1525h);
        this.f1526i.a(colorStateList);
        if (z) {
            this.f1526i.e(i3);
        }
        this.f1526i.b(a2);
        this.f1526i.a(b2);
        this.f1526i.d(c);
        this.f1525h.a((m) this.f1526i);
        addView((View) this.f1526i.a((ViewGroup) this));
        if (d.g(R$styleable.NavigationView_menu)) {
            b(d.g(R$styleable.NavigationView_menu, 0));
        }
        if (d.g(R$styleable.NavigationView_headerLayout)) {
            a(d.g(R$styleable.NavigationView_headerLayout, 0));
        }
        d.a();
    }

    public void setCheckedItem(MenuItem menuItem) {
        MenuItem findItem = this.f1525h.findItem(menuItem.getItemId());
        if (findItem != null) {
            this.f1526i.a((i) findItem);
            return;
        }
        throw new IllegalArgumentException("Called setCheckedItem(MenuItem) with an item that is not in the current menu.");
    }
}
