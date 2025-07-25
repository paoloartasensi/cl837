package androidx.viewpager2.widget;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;
import java.util.Locale;

/* compiled from: PageTransformerAdapter */
final class d extends ViewPager2.i {
    private final LinearLayoutManager a;
    private ViewPager2.k b;

    d(LinearLayoutManager linearLayoutManager) {
        this.a = linearLayoutManager;
    }

    /* access modifiers changed from: package-private */
    public ViewPager2.k a() {
        return this.b;
    }

    public void a(int i2) {
    }

    public void b(int i2) {
    }

    /* access modifiers changed from: package-private */
    public void a(ViewPager2.k kVar) {
        this.b = kVar;
    }

    public void a(int i2, float f2, int i3) {
        if (this.b != null) {
            float f3 = -f2;
            int i4 = 0;
            while (i4 < this.a.e()) {
                View d = this.a.d(i4);
                if (d != null) {
                    this.b.a(d, ((float) (this.a.l(d) - i2)) + f3);
                    i4++;
                } else {
                    throw new IllegalStateException(String.format(Locale.US, "LayoutManager returned a null child at pos %d/%d while transforming pages", new Object[]{Integer.valueOf(i4), Integer.valueOf(this.a.e())}));
                }
            }
        }
    }
}
