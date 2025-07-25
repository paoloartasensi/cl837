package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;
import androidx.core.content.c.g;
import androidx.preference.Preference;

public class EditTextPreference extends DialogPreference {
    private String Z;
    private a a0;

    public interface a {
        void a(EditText editText);
    }

    public static final class b implements Preference.f<EditTextPreference> {
        private static b a;

        private b() {
        }

        public static b a() {
            if (a == null) {
                a = new b();
            }
            return a;
        }

        public CharSequence a(EditTextPreference editTextPreference) {
            if (TextUtils.isEmpty(editTextPreference.O())) {
                return editTextPreference.b().getString(R$string.not_set);
            }
            return editTextPreference.O();
        }
    }

    public EditTextPreference(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.EditTextPreference, i2, i3);
        int i4 = R$styleable.EditTextPreference_useSimpleSummaryProvider;
        if (g.a(obtainStyledAttributes, i4, i4, false)) {
            a((Preference.f) b.a());
        }
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public Parcelable D() {
        Parcelable D = super.D();
        if (v()) {
            return D;
        }
        SavedState savedState = new SavedState(D);
        savedState.e = O();
        return savedState;
    }

    public boolean F() {
        return TextUtils.isEmpty(this.Z) || super.F();
    }

    /* access modifiers changed from: package-private */
    public a N() {
        return this.a0;
    }

    public String O() {
        return this.Z;
    }

    /* access modifiers changed from: protected */
    public Object a(TypedArray typedArray, int i2) {
        return typedArray.getString(i2);
    }

    /* access modifiers changed from: protected */
    public void b(Object obj) {
        d(b((String) obj));
    }

    public void d(String str) {
        boolean F = F();
        this.Z = str;
        c(str);
        boolean F2 = F();
        if (F2 != F) {
            b(F2);
        }
        y();
    }

    public void setOnBindEditTextListener(a aVar) {
        this.a0 = aVar;
    }

    private static class SavedState extends Preference.BaseSavedState {
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

        SavedState(Parcel parcel) {
            super(parcel);
            this.e = parcel.readString();
        }

        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeString(this.e);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    /* access modifiers changed from: protected */
    public void a(Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(SavedState.class)) {
            super.a(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.a(savedState.getSuperState());
        d(savedState.e);
    }

    public EditTextPreference(Context context, AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, 0);
    }

    public EditTextPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, g.a(context, R$attr.editTextPreferenceStyle, 16842898));
    }
}
