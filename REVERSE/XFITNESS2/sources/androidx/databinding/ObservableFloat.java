package androidx.databinding;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

public class ObservableFloat extends b implements Parcelable, Serializable {
    public static final Parcelable.Creator<ObservableFloat> CREATOR = new a();
    static final long serialVersionUID = 1;
    private float mValue;

    static class a implements Parcelable.Creator<ObservableFloat> {
        a() {
        }

        public ObservableFloat createFromParcel(Parcel parcel) {
            return new ObservableFloat(parcel.readFloat());
        }

        public ObservableFloat[] newArray(int i2) {
            return new ObservableFloat[i2];
        }
    }

    public ObservableFloat(float f2) {
        this.mValue = f2;
    }

    public int describeContents() {
        return 0;
    }

    public float get() {
        return this.mValue;
    }

    public void set(float f2) {
        if (f2 != this.mValue) {
            this.mValue = f2;
            notifyChange();
        }
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeFloat(this.mValue);
    }

    public ObservableFloat() {
    }

    public ObservableFloat(j... jVarArr) {
        super(jVarArr);
    }
}
