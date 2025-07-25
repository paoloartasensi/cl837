package androidx.viewpager2.adapter;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.h.v;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: FragmentViewHolder */
public final class a extends RecyclerView.c0 {
    private a(FrameLayout frameLayout) {
        super(frameLayout);
    }

    static a a(ViewGroup viewGroup) {
        FrameLayout frameLayout = new FrameLayout(viewGroup.getContext());
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        frameLayout.setId(v.b());
        frameLayout.setSaveEnabled(false);
        return new a(frameLayout);
    }

    /* access modifiers changed from: package-private */
    public FrameLayout a() {
        return (FrameLayout) this.itemView;
    }
}
