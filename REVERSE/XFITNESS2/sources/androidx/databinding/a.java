package androidx.databinding;

import androidx.databinding.j;

/* compiled from: BaseObservable */
public class a implements j {
    private transient n e;

    public void addOnPropertyChangedCallback(j.a aVar) {
        synchronized (this) {
            if (this.e == null) {
                this.e = new n();
            }
        }
        this.e.a(aVar);
    }

    public void notifyChange() {
        synchronized (this) {
            if (this.e != null) {
                this.e.a(this, 0, null);
            }
        }
    }

    public void notifyPropertyChanged(int i2) {
        synchronized (this) {
            if (this.e != null) {
                this.e.a(this, i2, null);
            }
        }
    }

    public void removeOnPropertyChangedCallback(j.a aVar) {
        synchronized (this) {
            if (this.e != null) {
                this.e.b(aVar);
            }
        }
    }
}
