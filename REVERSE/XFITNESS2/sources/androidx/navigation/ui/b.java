package androidx.navigation.ui;

import android.graphics.drawable.Drawable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.a;

/* compiled from: ActionBarOnDestinationChangedListener */
class b extends a {

    /* renamed from: f  reason: collision with root package name */
    private final AppCompatActivity f751f;

    b(AppCompatActivity appCompatActivity, d dVar) {
        super(appCompatActivity.i().a(), dVar);
        this.f751f = appCompatActivity;
    }

    /* access modifiers changed from: protected */
    public void a(CharSequence charSequence) {
        this.f751f.j().a(charSequence);
    }

    /* access modifiers changed from: protected */
    public void a(Drawable drawable, int i2) {
        a j2 = this.f751f.j();
        if (drawable == null) {
            j2.d(false);
            return;
        }
        j2.d(true);
        this.f751f.i().a(drawable, i2);
    }
}
