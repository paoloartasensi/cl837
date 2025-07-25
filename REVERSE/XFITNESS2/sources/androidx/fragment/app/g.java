package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import androidx.core.g.h;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: FragmentHostCallback */
public abstract class g<E> extends d {
    private final Activity e;

    /* renamed from: f  reason: collision with root package name */
    private final Context f616f;

    /* renamed from: g  reason: collision with root package name */
    private final Handler f617g;

    /* renamed from: h  reason: collision with root package name */
    final j f618h;

    g(FragmentActivity fragmentActivity) {
        this(fragmentActivity, fragmentActivity, new Handler(), 0);
    }

    public View a(int i2) {
        return null;
    }

    /* access modifiers changed from: package-private */
    public void a(Fragment fragment) {
    }

    public void a(Fragment fragment, @SuppressLint({"UnknownNullness"}) Intent intent, int i2, Bundle bundle) {
        if (i2 == -1) {
            this.f616f.startActivity(intent);
            return;
        }
        throw new IllegalStateException("Starting activity with a requestCode requires a FragmentActivity host");
    }

    public void a(Fragment fragment, String[] strArr, int i2) {
    }

    public void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    public boolean a(String str) {
        return false;
    }

    public boolean b(Fragment fragment) {
        return true;
    }

    public boolean c() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public Activity d() {
        return this.e;
    }

    /* access modifiers changed from: package-private */
    public Context e() {
        return this.f616f;
    }

    /* access modifiers changed from: package-private */
    public Handler f() {
        return this.f617g;
    }

    public abstract E g();

    public LayoutInflater h() {
        return LayoutInflater.from(this.f616f);
    }

    public void i() {
    }

    g(Activity activity, Context context, Handler handler, int i2) {
        this.f618h = new k();
        this.e = activity;
        h.a(context, "context == null");
        this.f616f = context;
        h.a(handler, "handler == null");
        this.f617g = handler;
    }
}
