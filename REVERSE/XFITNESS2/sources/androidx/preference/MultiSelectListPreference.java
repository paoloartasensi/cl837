package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import androidx.core.content.c.g;
import androidx.preference.Preference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MultiSelectListPreference extends DialogPreference {
    private CharSequence[] Z;
    private CharSequence[] a0;
    private Set<String> b0;

    public MultiSelectListPreference(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        this.b0 = new HashSet();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.MultiSelectListPreference, i2, i3);
        this.Z = g.d(obtainStyledAttributes, R$styleable.MultiSelectListPreference_entries, R$styleable.MultiSelectListPreference_android_entries);
        this.a0 = g.d(obtainStyledAttributes, R$styleable.MultiSelectListPreference_entryValues, R$styleable.MultiSelectListPreference_android_entryValues);
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public Parcelable D() {
        Parcelable D = super.D();
        if (v()) {
            return D;
        }
        SavedState savedState = new SavedState(D);
        savedState.e = P();
        return savedState;
    }

    public CharSequence[] N() {
        return this.Z;
    }

    public CharSequence[] O() {
        return this.a0;
    }

    public Set<String> P() {
        return this.b0;
    }

    /* access modifiers changed from: protected */
    public Object a(TypedArray typedArray, int i2) {
        CharSequence[] textArray = typedArray.getTextArray(i2);
        HashSet hashSet = new HashSet();
        for (CharSequence charSequence : textArray) {
            hashSet.add(charSequence.toString());
        }
        return hashSet;
    }

    /* access modifiers changed from: protected */
    public void b(Object obj) {
        c(a((Set<String>) (Set) obj));
    }

    public void c(Set<String> set) {
        this.b0.clear();
        this.b0.addAll(set);
        b(set);
        y();
    }

    /* access modifiers changed from: protected */
    public void a(Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(SavedState.class)) {
            super.a(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.a(savedState.getSuperState());
        c(savedState.e);
    }

    private static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        Set<String> e;

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
            int readInt = parcel.readInt();
            this.e = new HashSet();
            String[] strArr = new String[readInt];
            parcel.readStringArray(strArr);
            Collections.addAll(this.e, strArr);
        }

        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.e.size());
            Set<String> set = this.e;
            parcel.writeStringArray((String[]) set.toArray(new String[set.size()]));
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public MultiSelectListPreference(Context context, AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, 0);
    }

    public MultiSelectListPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, g.a(context, R$attr.dialogPreferenceStyle, 16842897));
    }
}
