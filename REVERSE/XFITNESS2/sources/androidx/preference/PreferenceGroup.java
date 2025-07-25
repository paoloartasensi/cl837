package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import androidx.preference.Preference;
import g.a.g;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class PreferenceGroup extends Preference {
    final g<String, Long> T;
    private List<Preference> U;
    private boolean V;
    private int W;
    private boolean X;
    private int Y;
    private b Z;

    class a implements Runnable {
        a() {
        }

        public void run() {
            synchronized (this) {
                PreferenceGroup.this.T.clear();
            }
        }
    }

    public interface b {
        void a();
    }

    public PreferenceGroup(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        this.T = new g<>();
        new Handler();
        this.V = true;
        this.W = 0;
        this.X = false;
        this.Y = Integer.MAX_VALUE;
        this.Z = null;
        new a();
        this.U = new ArrayList();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.PreferenceGroup, i2, i3);
        int i4 = R$styleable.PreferenceGroup_orderingFromXml;
        this.V = androidx.core.content.c.g.a(obtainStyledAttributes, i4, i4, true);
        if (obtainStyledAttributes.hasValue(R$styleable.PreferenceGroup_initialExpandedChildrenCount)) {
            int i5 = R$styleable.PreferenceGroup_initialExpandedChildrenCount;
            h(androidx.core.content.c.g.a(obtainStyledAttributes, i5, i5, Integer.MAX_VALUE));
        }
        obtainStyledAttributes.recycle();
    }

    public void A() {
        super.A();
        this.X = true;
        int J = J();
        for (int i2 = 0; i2 < J; i2++) {
            g(i2).A();
        }
    }

    public void C() {
        super.C();
        this.X = false;
        int J = J();
        for (int i2 = 0; i2 < J; i2++) {
            g(i2).C();
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable D() {
        return new SavedState(super.D(), this.Y);
    }

    public int H() {
        return this.Y;
    }

    public b I() {
        return this.Z;
    }

    public int J() {
        return this.U.size();
    }

    /* access modifiers changed from: protected */
    public boolean K() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void L() {
        synchronized (this) {
            Collections.sort(this.U);
        }
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle) {
        super.a(bundle);
        int J = J();
        for (int i2 = 0; i2 < J; i2++) {
            g(i2).a(bundle);
        }
    }

    public void b(Preference preference) {
        c(preference);
    }

    public boolean c(Preference preference) {
        long j2;
        if (this.U.contains(preference)) {
            return true;
        }
        if (preference.h() != null) {
            PreferenceGroup preferenceGroup = this;
            while (preferenceGroup.k() != null) {
                preferenceGroup = preferenceGroup.k();
            }
            String h2 = preference.h();
            if (preferenceGroup.c((CharSequence) h2) != null) {
                Log.e("PreferenceGroup", "Found duplicated key: \"" + h2 + "\". This can cause unintended behaviour, please use unique keys for every preference.");
            }
        }
        if (preference.j() == Integer.MAX_VALUE) {
            if (this.V) {
                int i2 = this.W;
                this.W = i2 + 1;
                preference.e(i2);
            }
            if (preference instanceof PreferenceGroup) {
                ((PreferenceGroup) preference).d(this.V);
            }
        }
        int binarySearch = Collections.binarySearch(this.U, preference);
        if (binarySearch < 0) {
            binarySearch = (binarySearch * -1) - 1;
        }
        if (!d(preference)) {
            return false;
        }
        synchronized (this) {
            this.U.add(binarySearch, preference);
        }
        j m = m();
        String h3 = preference.h();
        if (h3 == null || !this.T.containsKey(h3)) {
            j2 = m.b();
        } else {
            j2 = this.T.get(h3).longValue();
            this.T.remove(h3);
        }
        preference.a(m, j2);
        preference.a(this);
        if (this.X) {
            preference.A();
        }
        z();
        return true;
    }

    public void d(boolean z) {
        this.V = z;
    }

    public Preference g(int i2) {
        return this.U.get(i2);
    }

    public void h(int i2) {
        if (i2 != Integer.MAX_VALUE && !s()) {
            Log.e("PreferenceGroup", getClass().getSimpleName() + " should have a key defined if it contains an expandable preference");
        }
        this.Y = i2;
    }

    public void setOnExpandButtonClickListener(b bVar) {
        this.Z = bVar;
    }

    static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        int e;

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

        SavedState(Parcel parcel) {
            super(parcel);
            this.e = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.e);
        }

        SavedState(Parcelable parcelable, int i2) {
            super(parcelable);
            this.e = i2;
        }
    }

    public void b(boolean z) {
        super.b(z);
        int J = J();
        for (int i2 = 0; i2 < J; i2++) {
            g(i2).b(this, z);
        }
    }

    /* access modifiers changed from: protected */
    public boolean d(Preference preference) {
        preference.b(this, F());
        return true;
    }

    /* access modifiers changed from: protected */
    public void a(Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(SavedState.class)) {
            super.a(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        this.Y = savedState.e;
        super.a(savedState.getSuperState());
    }

    /* access modifiers changed from: protected */
    public void b(Bundle bundle) {
        super.b(bundle);
        int J = J();
        for (int i2 = 0; i2 < J; i2++) {
            g(i2).b(bundle);
        }
    }

    public PreferenceGroup(Context context, AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, 0);
    }

    public PreferenceGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public <T extends Preference> T c(CharSequence charSequence) {
        T c;
        if (charSequence == null) {
            throw new IllegalArgumentException("Key cannot be null");
        } else if (TextUtils.equals(h(), charSequence)) {
            return this;
        } else {
            int J = J();
            for (int i2 = 0; i2 < J; i2++) {
                T g2 = g(i2);
                if (TextUtils.equals(g2.h(), charSequence)) {
                    return g2;
                }
                if ((g2 instanceof PreferenceGroup) && (c = ((PreferenceGroup) g2).c(charSequence)) != null) {
                    return c;
                }
            }
            return null;
        }
    }
}
