package androidx.viewpager2.widget;

import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

/* compiled from: CompositeOnPageChangeCallback */
final class b extends ViewPager2.i {
    private final List<ViewPager2.i> a;

    b(int i2) {
        this.a = new ArrayList(i2);
    }

    /* access modifiers changed from: package-private */
    public void a(ViewPager2.i iVar) {
        this.a.add(iVar);
    }

    /* access modifiers changed from: package-private */
    public void b(ViewPager2.i iVar) {
        this.a.remove(iVar);
    }

    public void a(int i2, float f2, int i3) {
        try {
            for (ViewPager2.i a2 : this.a) {
                a2.a(i2, f2, i3);
            }
        } catch (ConcurrentModificationException e) {
            a(e);
            throw null;
        }
    }

    public void b(int i2) {
        try {
            for (ViewPager2.i b : this.a) {
                b.b(i2);
            }
        } catch (ConcurrentModificationException e) {
            a(e);
            throw null;
        }
    }

    public void a(int i2) {
        try {
            for (ViewPager2.i a2 : this.a) {
                a2.a(i2);
            }
        } catch (ConcurrentModificationException e) {
            a(e);
            throw null;
        }
    }

    private void a(ConcurrentModificationException concurrentModificationException) {
        throw new IllegalStateException("Adding and removing callbacks during dispatch to callbacks is not supported", concurrentModificationException);
    }
}
