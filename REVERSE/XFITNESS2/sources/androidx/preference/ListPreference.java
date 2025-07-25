package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import androidx.core.content.c.g;
import androidx.preference.Preference;
import com.jeremyliao.liveeventbus.BuildConfig;

public class ListPreference extends DialogPreference {
    private CharSequence[] Z;
    private CharSequence[] a0;
    private String b0;
    private String c0;
    private boolean d0;

    public static final class a implements Preference.f<ListPreference> {
        private static a a;

        private a() {
        }

        public static a a() {
            if (a == null) {
                a = new a();
            }
            return a;
        }

        public CharSequence a(ListPreference listPreference) {
            if (TextUtils.isEmpty(listPreference.O())) {
                return listPreference.b().getString(R$string.not_set);
            }
            return listPreference.O();
        }
    }

    public ListPreference(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ListPreference, i2, i3);
        this.Z = g.d(obtainStyledAttributes, R$styleable.ListPreference_entries, R$styleable.ListPreference_android_entries);
        this.a0 = g.d(obtainStyledAttributes, R$styleable.ListPreference_entryValues, R$styleable.ListPreference_android_entryValues);
        int i4 = R$styleable.ListPreference_useSimpleSummaryProvider;
        if (g.a(obtainStyledAttributes, i4, i4, false)) {
            a((Preference.f) a.a());
        }
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R$styleable.Preference, i2, i3);
        this.c0 = g.b(obtainStyledAttributes2, R$styleable.Preference_summary, R$styleable.Preference_android_summary);
        obtainStyledAttributes2.recycle();
    }

    private int R() {
        return d(this.b0);
    }

    /* access modifiers changed from: protected */
    public Parcelable D() {
        Parcelable D = super.D();
        if (v()) {
            return D;
        }
        SavedState savedState = new SavedState(D);
        savedState.e = Q();
        return savedState;
    }

    public CharSequence[] N() {
        return this.Z;
    }

    public CharSequence O() {
        CharSequence[] charSequenceArr;
        int R = R();
        if (R < 0 || (charSequenceArr = this.Z) == null) {
            return null;
        }
        return charSequenceArr[R];
    }

    public CharSequence[] P() {
        return this.a0;
    }

    public String Q() {
        return this.b0;
    }

    public void a(CharSequence charSequence) {
        super.a(charSequence);
        if (charSequence == null && this.c0 != null) {
            this.c0 = null;
        } else if (charSequence != null && !charSequence.equals(this.c0)) {
            this.c0 = charSequence.toString();
        }
    }

    /* access modifiers changed from: protected */
    public void b(Object obj) {
        e(b((String) obj));
    }

    public int d(String str) {
        CharSequence[] charSequenceArr;
        if (str == null || (charSequenceArr = this.a0) == null) {
            return -1;
        }
        for (int length = charSequenceArr.length - 1; length >= 0; length--) {
            if (this.a0[length].equals(str)) {
                return length;
            }
        }
        return -1;
    }

    public void e(String str) {
        boolean z = !TextUtils.equals(this.b0, str);
        if (z || !this.d0) {
            this.b0 = str;
            this.d0 = true;
            c(str);
            if (z) {
                y();
            }
        }
    }

    public CharSequence o() {
        if (p() != null) {
            return p().a(this);
        }
        Object O = O();
        CharSequence o = super.o();
        String str = this.c0;
        if (str == null) {
            return o;
        }
        Object[] objArr = new Object[1];
        if (O == null) {
            O = BuildConfig.FLAVOR;
        }
        objArr[0] = O;
        String format = String.format(str, objArr);
        if (TextUtils.equals(format, o)) {
            return o;
        }
        Log.w("ListPreference", "Setting a summary with a String formatting marker is no longer supported. You should use a SummaryProvider instead.");
        return format;
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
    public Object a(TypedArray typedArray, int i2) {
        return typedArray.getString(i2);
    }

    /* access modifiers changed from: protected */
    public void a(Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(SavedState.class)) {
            super.a(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.a(savedState.getSuperState());
        e(savedState.e);
    }

    public ListPreference(Context context, AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, 0);
    }

    public ListPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, g.a(context, R$attr.dialogPreferenceStyle, 16842897));
    }
}
