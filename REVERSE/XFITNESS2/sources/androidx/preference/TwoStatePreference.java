package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import androidx.preference.Preference;

public abstract class TwoStatePreference extends Preference {
    protected boolean T;
    private CharSequence U;
    private CharSequence V;
    private boolean W;
    private boolean X;

    public TwoStatePreference(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
    }

    /* access modifiers changed from: protected */
    public void B() {
        super.B();
        boolean z = !H();
        if (a((Object) Boolean.valueOf(z))) {
            d(z);
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable D() {
        Parcelable D = super.D();
        if (v()) {
            return D;
        }
        SavedState savedState = new SavedState(D);
        savedState.e = H();
        return savedState;
    }

    public boolean F() {
        if ((this.X ? this.T : !this.T) || super.F()) {
            return true;
        }
        return false;
    }

    public boolean H() {
        return this.T;
    }

    /* access modifiers changed from: protected */
    public Object a(TypedArray typedArray, int i2) {
        return Boolean.valueOf(typedArray.getBoolean(i2, false));
    }

    /* access modifiers changed from: protected */
    public void b(Object obj) {
        if (obj == null) {
            obj = false;
        }
        d(a(((Boolean) obj).booleanValue()));
    }

    public void c(CharSequence charSequence) {
        this.V = charSequence;
        if (!H()) {
            y();
        }
    }

    public void d(boolean z) {
        boolean z2 = this.T != z;
        if (z2 || !this.W) {
            this.T = z;
            this.W = true;
            c(z);
            if (z2) {
                b(F());
                y();
            }
        }
    }

    public void e(boolean z) {
        this.X = z;
    }

    static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        boolean e;

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
            this.e = parcel.readInt() != 1 ? false : true;
        }

        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.e ? 1 : 0);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public TwoStatePreference(Context context, AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, 0);
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

    public TwoStatePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* access modifiers changed from: protected */
    public void b(l lVar) {
        b(lVar.a(16908304));
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(android.view.View r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof android.widget.TextView
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            android.widget.TextView r5 = (android.widget.TextView) r5
            r0 = 1
            boolean r1 = r4.T
            r2 = 0
            if (r1 == 0) goto L_0x001c
            java.lang.CharSequence r1 = r4.U
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x001c
            java.lang.CharSequence r0 = r4.U
            r5.setText(r0)
        L_0x001a:
            r0 = 0
            goto L_0x002e
        L_0x001c:
            boolean r1 = r4.T
            if (r1 != 0) goto L_0x002e
            java.lang.CharSequence r1 = r4.V
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x002e
            java.lang.CharSequence r0 = r4.V
            r5.setText(r0)
            goto L_0x001a
        L_0x002e:
            if (r0 == 0) goto L_0x003e
            java.lang.CharSequence r1 = r4.o()
            boolean r3 = android.text.TextUtils.isEmpty(r1)
            if (r3 != 0) goto L_0x003e
            r5.setText(r1)
            r0 = 0
        L_0x003e:
            r1 = 8
            if (r0 != 0) goto L_0x0043
            goto L_0x0045
        L_0x0043:
            r2 = 8
        L_0x0045:
            int r0 = r5.getVisibility()
            if (r2 == r0) goto L_0x004e
            r5.setVisibility(r2)
        L_0x004e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.preference.TwoStatePreference.b(android.view.View):void");
    }

    public void d(CharSequence charSequence) {
        this.U = charSequence;
        if (H()) {
            y();
        }
    }
}
