package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TabHost;
import java.util.ArrayList;

@Deprecated
public class FragmentTabHost extends TabHost implements TabHost.OnTabChangeListener {
    private final ArrayList<a> e = new ArrayList<>();

    /* renamed from: f  reason: collision with root package name */
    private Context f607f;

    /* renamed from: g  reason: collision with root package name */
    private j f608g;

    /* renamed from: h  reason: collision with root package name */
    private int f609h;

    /* renamed from: i  reason: collision with root package name */
    private TabHost.OnTabChangeListener f610i;

    /* renamed from: j  reason: collision with root package name */
    private a f611j;
    private boolean k;

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        String e;

        static class a implements Parcelable.Creator<SavedState> {
            a() {
            }

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "FragmentTabHost.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " curTab=" + this.e + "}";
        }

        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeString(this.e);
        }

        SavedState(Parcel parcel) {
            super(parcel);
            this.e = parcel.readString();
        }
    }

    static final class a {
        final String a;
        final Class<?> b;
        final Bundle c;
        Fragment d;
    }

    @Deprecated
    public FragmentTabHost(Context context) {
        super(context, (AttributeSet) null);
        a(context, (AttributeSet) null);
    }

    private void a(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{16842995}, 0, 0);
        this.f609h = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
        super.setOnTabChangedListener(this);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        String currentTabTag = getCurrentTabTag();
        int size = this.e.size();
        p pVar = null;
        for (int i2 = 0; i2 < size; i2++) {
            a aVar = this.e.get(i2);
            Fragment b = this.f608g.b(aVar.a);
            aVar.d = b;
            if (b != null && !b.I()) {
                if (aVar.a.equals(currentTabTag)) {
                    this.f611j = aVar;
                } else {
                    if (pVar == null) {
                        pVar = this.f608g.b();
                    }
                    pVar.b(aVar.d);
                }
            }
        }
        this.k = true;
        p a2 = a(currentTabTag, pVar);
        if (a2 != null) {
            a2.a();
            this.f608g.n();
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.k = false;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void onRestoreInstanceState(@SuppressLint({"UnknownNullness"}) Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setCurrentTabByTag(savedState.e);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.e = getCurrentTabTag();
        return savedState;
    }

    @Deprecated
    public void onTabChanged(String str) {
        p a2;
        if (this.k && (a2 = a(str, (p) null)) != null) {
            a2.a();
        }
        TabHost.OnTabChangeListener onTabChangeListener = this.f610i;
        if (onTabChangeListener != null) {
            onTabChangeListener.onTabChanged(str);
        }
    }

    @Deprecated
    public void setOnTabChangedListener(TabHost.OnTabChangeListener onTabChangeListener) {
        this.f610i = onTabChangeListener;
    }

    @Deprecated
    public void setup() {
        throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
    }

    @Deprecated
    public FragmentTabHost(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, attributeSet);
    }

    private p a(String str, p pVar) {
        Fragment fragment;
        a a2 = a(str);
        if (this.f611j != a2) {
            if (pVar == null) {
                pVar = this.f608g.b();
            }
            a aVar = this.f611j;
            if (!(aVar == null || (fragment = aVar.d) == null)) {
                pVar.b(fragment);
            }
            if (a2 != null) {
                Fragment fragment2 = a2.d;
                if (fragment2 == null) {
                    Fragment a3 = this.f608g.p().a(this.f607f.getClassLoader(), a2.b.getName());
                    a2.d = a3;
                    a3.m(a2.c);
                    pVar.a(this.f609h, a2.d, a2.a);
                } else {
                    pVar.a(fragment2);
                }
            }
            this.f611j = a2;
        }
        return pVar;
    }

    private a a(String str) {
        int size = this.e.size();
        for (int i2 = 0; i2 < size; i2++) {
            a aVar = this.e.get(i2);
            if (aVar.a.equals(str)) {
                return aVar;
            }
        }
        return null;
    }
}
