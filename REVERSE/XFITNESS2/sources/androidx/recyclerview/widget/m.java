package androidx.recyclerview.widget;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: LayoutState */
class m {
    boolean a = true;
    int b;
    int c;
    int d;
    int e;

    /* renamed from: f  reason: collision with root package name */
    int f849f = 0;

    /* renamed from: g  reason: collision with root package name */
    int f850g = 0;

    /* renamed from: h  reason: collision with root package name */
    boolean f851h;

    /* renamed from: i  reason: collision with root package name */
    boolean f852i;

    m() {
    }

    /* access modifiers changed from: package-private */
    public boolean a(RecyclerView.z zVar) {
        int i2 = this.c;
        return i2 >= 0 && i2 < zVar.a();
    }

    public String toString() {
        return "LayoutState{mAvailable=" + this.b + ", mCurrentPosition=" + this.c + ", mItemDirection=" + this.d + ", mLayoutDirection=" + this.e + ", mStartLine=" + this.f849f + ", mEndLine=" + this.f850g + '}';
    }

    /* access modifiers changed from: package-private */
    public View a(RecyclerView.v vVar) {
        View d2 = vVar.d(this.c);
        this.c += this.d;
        return d2;
    }
}
