package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.m;
import androidx.core.h.z;

/* compiled from: DecorToolbar */
public interface p {
    z a(int i2, long j2);

    void a(int i2);

    void a(Drawable drawable);

    void a(Menu menu, m.a aVar);

    void a(m.a aVar, g.a aVar2);

    void a(ScrollingTabContainerView scrollingTabContainerView);

    void a(boolean z);

    boolean a();

    void b(int i2);

    void b(boolean z);

    boolean b();

    void c(int i2);

    boolean c();

    void collapseActionView();

    void d(int i2);

    boolean d();

    void e();

    boolean f();

    void g();

    Context getContext();

    CharSequence getTitle();

    int h();

    Menu i();

    ViewGroup j();

    int k();

    void l();

    boolean m();

    void n();

    void setIcon(int i2);

    void setIcon(Drawable drawable);

    void setTitle(CharSequence charSequence);

    void setWindowCallback(Window.Callback callback);

    void setWindowTitle(CharSequence charSequence);
}
