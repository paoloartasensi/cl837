package androidx.appcompat.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.R$styleable;
import androidx.appcompat.d.b;

/* compiled from: ActionBar */
public abstract class a {

    /* compiled from: ActionBar */
    public interface b {
        void a(boolean z);
    }

    @Deprecated
    /* compiled from: ActionBar */
    public static abstract class c {
        public abstract CharSequence a();

        public abstract View b();

        public abstract Drawable c();

        public abstract CharSequence d();

        public abstract void e();
    }

    public androidx.appcompat.d.b a(b.a aVar) {
        return null;
    }

    public void a(Configuration configuration) {
    }

    public void a(Drawable drawable) {
    }

    public abstract void a(CharSequence charSequence);

    public boolean a(int i2, KeyEvent keyEvent) {
        return false;
    }

    public boolean a(KeyEvent keyEvent) {
        return false;
    }

    public abstract void addOnMenuVisibilityListener(b bVar);

    public void b(int i2) {
    }

    public void b(CharSequence charSequence) {
    }

    public void b(boolean z) {
    }

    public void c(boolean z) {
    }

    public abstract void d(boolean z);

    public void e(boolean z) {
    }

    public boolean e() {
        return false;
    }

    public boolean f() {
        return false;
    }

    public abstract int g();

    public Context h() {
        return null;
    }

    public boolean i() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void j() {
    }

    public boolean k() {
        return false;
    }

    public abstract void removeOnMenuVisibilityListener(b bVar);

    /* renamed from: androidx.appcompat.app.a$a  reason: collision with other inner class name */
    /* compiled from: ActionBar */
    public static class C0002a extends ViewGroup.MarginLayoutParams {
        public int a;

        public C0002a(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.a = 0;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ActionBarLayout);
            this.a = obtainStyledAttributes.getInt(R$styleable.ActionBarLayout_android_layout_gravity, 0);
            obtainStyledAttributes.recycle();
        }

        public C0002a(int i2, int i3) {
            super(i2, i3);
            this.a = 0;
            this.a = 8388627;
        }

        public C0002a(C0002a aVar) {
            super(aVar);
            this.a = 0;
            this.a = aVar.a;
        }

        public C0002a(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.a = 0;
        }
    }
}
