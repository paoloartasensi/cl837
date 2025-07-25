package androidx.preference;

import android.os.Bundle;
import android.view.View;
import androidx.core.h.e0.d;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.s;

@Deprecated
/* compiled from: PreferenceRecyclerViewAccessibilityDelegate */
public class k extends s {

    /* renamed from: f  reason: collision with root package name */
    final RecyclerView f767f;

    /* renamed from: g  reason: collision with root package name */
    final androidx.core.h.a f768g = super.b();

    /* renamed from: h  reason: collision with root package name */
    final androidx.core.h.a f769h = new a();

    public k(RecyclerView recyclerView) {
        super(recyclerView);
        this.f767f = recyclerView;
    }

    public androidx.core.h.a b() {
        return this.f769h;
    }

    /* compiled from: PreferenceRecyclerViewAccessibilityDelegate */
    class a extends androidx.core.h.a {
        a() {
        }

        public void a(View view, d dVar) {
            Preference item;
            k.this.f768g.a(view, dVar);
            int e = k.this.f767f.e(view);
            RecyclerView.g adapter = k.this.f767f.getAdapter();
            if ((adapter instanceof h) && (item = ((h) adapter).getItem(e)) != null) {
                item.a(dVar);
            }
        }

        public boolean a(View view, int i2, Bundle bundle) {
            return k.this.f768g.a(view, i2, bundle);
        }
    }
}
