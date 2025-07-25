package androidx.viewpager.widget;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

/* compiled from: PagerAdapter */
public abstract class a {
    private final DataSetObservable a = new DataSetObservable();

    public abstract int a();

    public int a(Object obj) {
        return -1;
    }

    public CharSequence a(int i2) {
        return null;
    }

    public Object a(ViewGroup viewGroup, int i2) {
        a((View) viewGroup, i2);
        throw null;
    }

    public void a(Parcelable parcelable, ClassLoader classLoader) {
    }

    @Deprecated
    public void a(View view) {
    }

    public abstract boolean a(View view, Object obj);

    public float b(int i2) {
        return 1.0f;
    }

    public Parcelable b() {
        return null;
    }

    @Deprecated
    public void b(View view) {
    }

    @Deprecated
    public void b(View view, int i2, Object obj) {
    }

    public void b(ViewGroup viewGroup) {
        b((View) viewGroup);
    }

    public void c(DataSetObserver dataSetObserver) {
        this.a.unregisterObserver(dataSetObserver);
    }

    public void a(ViewGroup viewGroup, int i2, Object obj) {
        a((View) viewGroup, i2, obj);
        throw null;
    }

    public void b(ViewGroup viewGroup, int i2, Object obj) {
        b((View) viewGroup, i2, obj);
    }

    public void a(ViewGroup viewGroup) {
        a((View) viewGroup);
    }

    /* access modifiers changed from: package-private */
    public void b(DataSetObserver dataSetObserver) {
        synchronized (this) {
        }
    }

    @Deprecated
    public Object a(View view, int i2) {
        throw new UnsupportedOperationException("Required method instantiateItem was not overridden");
    }

    @Deprecated
    public void a(View view, int i2, Object obj) {
        throw new UnsupportedOperationException("Required method destroyItem was not overridden");
    }

    public void a(DataSetObserver dataSetObserver) {
        this.a.registerObserver(dataSetObserver);
    }
}
