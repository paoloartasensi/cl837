package androidx.appcompat.view.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.widget.g0;

public final class ExpandedMenuView extends ListView implements g.b, n, AdapterView.OnItemClickListener {

    /* renamed from: g  reason: collision with root package name */
    private static final int[] f114g = {16842964, 16843049};
    private g e;

    /* renamed from: f  reason: collision with root package name */
    private int f115f;

    public ExpandedMenuView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842868);
    }

    public void a(g gVar) {
        this.e = gVar;
    }

    public int getWindowAnimations() {
        return this.f115f;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setChildrenDrawingCacheEnabled(false);
    }

    public void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
        a((i) getAdapter().getItem(i2));
    }

    public ExpandedMenuView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet);
        setOnItemClickListener(this);
        g0 a = g0.a(context, attributeSet, f114g, i2, 0);
        if (a.g(0)) {
            setBackgroundDrawable(a.b(0));
        }
        if (a.g(1)) {
            setDivider(a.b(1));
        }
        a.a();
    }

    public boolean a(i iVar) {
        return this.e.a((MenuItem) iVar, 0);
    }
}
