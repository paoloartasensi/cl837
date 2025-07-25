package androidx.recyclerview.widget;

import androidx.recyclerview.widget.RecyclerView;

/* compiled from: AdapterListUpdateCallback */
public final class b implements o {
    private final RecyclerView.g a;

    public b(RecyclerView.g gVar) {
        this.a = gVar;
    }

    public void onChanged(int i2, int i3, Object obj) {
        this.a.notifyItemRangeChanged(i2, i3, obj);
    }

    public void onInserted(int i2, int i3) {
        this.a.notifyItemRangeInserted(i2, i3);
    }

    public void onMoved(int i2, int i3) {
        this.a.notifyItemMoved(i2, i3);
    }

    public void onRemoved(int i2, int i3) {
        this.a.notifyItemRangeRemoved(i2, i3);
    }
}
