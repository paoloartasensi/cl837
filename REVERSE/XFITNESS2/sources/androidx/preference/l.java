package androidx.preference;

import android.util.SparseArray;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: PreferenceViewHolder */
public class l extends RecyclerView.c0 {
    private final SparseArray<View> a;
    private boolean b;
    private boolean c;

    l(View view) {
        super(view);
        SparseArray<View> sparseArray = new SparseArray<>(4);
        this.a = sparseArray;
        sparseArray.put(16908310, view.findViewById(16908310));
        this.a.put(16908304, view.findViewById(16908304));
        this.a.put(16908294, view.findViewById(16908294));
        SparseArray<View> sparseArray2 = this.a;
        int i2 = R$id.icon_frame;
        sparseArray2.put(i2, view.findViewById(i2));
        this.a.put(16908350, view.findViewById(16908350));
    }

    public View a(int i2) {
        View view = this.a.get(i2);
        if (view != null) {
            return view;
        }
        View findViewById = this.itemView.findViewById(i2);
        if (findViewById != null) {
            this.a.put(i2, findViewById);
        }
        return findViewById;
    }

    public boolean b() {
        return this.c;
    }

    public void b(boolean z) {
        this.c = z;
    }

    public boolean a() {
        return this.b;
    }

    public void a(boolean z) {
        this.b = z;
    }
}
