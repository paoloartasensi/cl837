package androidx.appcompat.app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.R$attr;
import androidx.appcompat.d.b;
import androidx.core.h.e;

/* compiled from: AppCompatDialog */
public class f extends Dialog implements d {
    private e e;

    /* renamed from: f  reason: collision with root package name */
    private final e.a f64f = new a();

    /* compiled from: AppCompatDialog */
    class a implements e.a {
        a() {
        }

        public boolean a(KeyEvent keyEvent) {
            return f.this.a(keyEvent);
        }
    }

    public f(Context context, int i2) {
        super(context, a(context, i2));
        e a2 = a();
        a2.d(a(context, i2));
        a2.a((Bundle) null);
    }

    public b a(b.a aVar) {
        return null;
    }

    public void a(b bVar) {
    }

    public boolean a(int i2) {
        return a().b(i2);
    }

    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        a().a(view, layoutParams);
    }

    public void b(b bVar) {
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return e.a(this.f64f, getWindow().getDecorView(), this, keyEvent);
    }

    public <T extends View> T findViewById(int i2) {
        return a().a(i2);
    }

    public void invalidateOptionsMenu() {
        a().f();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        a().e();
        super.onCreate(bundle);
        a().a(bundle);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        a().j();
    }

    public void setContentView(int i2) {
        a().c(i2);
    }

    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        a().a(charSequence);
    }

    public e a() {
        if (this.e == null) {
            this.e = e.a((Dialog) this, (d) this);
        }
        return this.e;
    }

    public void setContentView(View view) {
        a().a(view);
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        a().b(view, layoutParams);
    }

    public void setTitle(int i2) {
        super.setTitle(i2);
        a().a((CharSequence) getContext().getString(i2));
    }

    private static int a(Context context, int i2) {
        if (i2 != 0) {
            return i2;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R$attr.dialogTheme, typedValue, true);
        return typedValue.resourceId;
    }

    /* access modifiers changed from: package-private */
    public boolean a(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }
}
