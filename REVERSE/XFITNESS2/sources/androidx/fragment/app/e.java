package androidx.fragment.app;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.core.g.h;
import androidx.lifecycle.ViewModelStoreOwner;

/* compiled from: FragmentController */
public class e {
    private final g<?> a;

    private e(g<?> gVar) {
        this.a = gVar;
    }

    public static e a(g<?> gVar) {
        h.a(gVar, "callbacks == null");
        return new e(gVar);
    }

    public void b() {
        this.a.f618h.e();
    }

    public void c() {
        this.a.f618h.f();
    }

    public void d() {
        this.a.f618h.h();
    }

    public void e() {
        this.a.f618h.i();
    }

    public void f() {
        this.a.f618h.k();
    }

    public void g() {
        this.a.f618h.l();
    }

    public void h() {
        this.a.f618h.m();
    }

    public boolean i() {
        return this.a.f618h.c(true);
    }

    public j j() {
        return this.a.f618h;
    }

    public void k() {
        this.a.f618h.y();
    }

    public Parcelable l() {
        return this.a.f618h.B();
    }

    public Fragment a(String str) {
        return this.a.f618h.c(str);
    }

    public void b(boolean z) {
        this.a.f618h.b(z);
    }

    public void a(Fragment fragment) {
        g<?> gVar = this.a;
        gVar.f618h.a(gVar, (d) gVar, fragment);
    }

    public boolean b(Menu menu) {
        return this.a.f618h.b(menu);
    }

    public View a(View view, String str, Context context, AttributeSet attributeSet) {
        return this.a.f618h.r().onCreateView(view, str, context, attributeSet);
    }

    public boolean b(MenuItem menuItem) {
        return this.a.f618h.b(menuItem);
    }

    public void a(Parcelable parcelable) {
        g<?> gVar = this.a;
        if (gVar instanceof ViewModelStoreOwner) {
            gVar.f618h.a(parcelable);
            return;
        }
        throw new IllegalStateException("Your FragmentHostCallback must implement ViewModelStoreOwner to call restoreSaveState(). Call restoreAllState()  if you're still using retainNestedNonConfig().");
    }

    public void a() {
        this.a.f618h.d();
    }

    public void a(boolean z) {
        this.a.f618h.a(z);
    }

    public void a(Configuration configuration) {
        this.a.f618h.a(configuration);
    }

    public boolean a(Menu menu, MenuInflater menuInflater) {
        return this.a.f618h.a(menu, menuInflater);
    }

    public boolean a(MenuItem menuItem) {
        return this.a.f618h.a(menuItem);
    }

    public void a(Menu menu) {
        this.a.f618h.a(menu);
    }
}
