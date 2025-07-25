package androidx.appcompat.d;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.d.b;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.widget.ActionBarContextView;
import java.lang.ref.WeakReference;

/* compiled from: StandaloneActionMode */
public class e extends b implements g.a {

    /* renamed from: g  reason: collision with root package name */
    private Context f99g;

    /* renamed from: h  reason: collision with root package name */
    private ActionBarContextView f100h;

    /* renamed from: i  reason: collision with root package name */
    private b.a f101i;

    /* renamed from: j  reason: collision with root package name */
    private WeakReference<View> f102j;
    private boolean k;
    private g l;

    public e(Context context, ActionBarContextView actionBarContextView, b.a aVar, boolean z) {
        this.f99g = context;
        this.f100h = actionBarContextView;
        this.f101i = aVar;
        g gVar = new g(actionBarContextView.getContext());
        gVar.c(1);
        this.l = gVar;
        gVar.a((g.a) this);
    }

    public void a(CharSequence charSequence) {
        this.f100h.setSubtitle(charSequence);
    }

    public void b(CharSequence charSequence) {
        this.f100h.setTitle(charSequence);
    }

    public Menu c() {
        return this.l;
    }

    public MenuInflater d() {
        return new g(this.f100h.getContext());
    }

    public CharSequence e() {
        return this.f100h.getSubtitle();
    }

    public CharSequence g() {
        return this.f100h.getTitle();
    }

    public void i() {
        this.f101i.a((b) this, (Menu) this.l);
    }

    public boolean j() {
        return this.f100h.b();
    }

    public void a(int i2) {
        a((CharSequence) this.f99g.getString(i2));
    }

    public void b(int i2) {
        b((CharSequence) this.f99g.getString(i2));
    }

    public void a(boolean z) {
        super.a(z);
        this.f100h.setTitleOptional(z);
    }

    public View b() {
        WeakReference<View> weakReference = this.f102j;
        if (weakReference != null) {
            return (View) weakReference.get();
        }
        return null;
    }

    public void a(View view) {
        this.f100h.setCustomView(view);
        this.f102j = view != null ? new WeakReference<>(view) : null;
    }

    public void a() {
        if (!this.k) {
            this.k = true;
            this.f100h.sendAccessibilityEvent(32);
            this.f101i.a(this);
        }
    }

    public boolean a(g gVar, MenuItem menuItem) {
        return this.f101i.a((b) this, menuItem);
    }

    public void a(g gVar) {
        i();
        this.f100h.d();
    }
}
