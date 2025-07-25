package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

/* compiled from: DialogFragment */
public class b extends Fragment implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {
    private Handler b0;
    private Runnable c0 = new a();
    private DialogInterface.OnCancelListener d0 = new C0032b();
    /* access modifiers changed from: private */
    public DialogInterface.OnDismissListener e0 = new c();
    private int f0 = 0;
    private int g0 = 0;
    private boolean h0 = true;
    private boolean i0 = true;
    private int j0 = -1;
    private boolean k0;
    /* access modifiers changed from: private */
    public Dialog l0;
    private boolean m0;
    private boolean n0;
    private boolean o0;

    /* compiled from: DialogFragment */
    class a implements Runnable {
        a() {
        }

        @SuppressLint({"SyntheticAccessor"})
        public void run() {
            b.this.e0.onDismiss(b.this.l0);
        }
    }

    /* renamed from: androidx.fragment.app.b$b  reason: collision with other inner class name */
    /* compiled from: DialogFragment */
    class C0032b implements DialogInterface.OnCancelListener {
        C0032b() {
        }

        @SuppressLint({"SyntheticAccessor"})
        public void onCancel(DialogInterface dialogInterface) {
            if (b.this.l0 != null) {
                b bVar = b.this;
                bVar.onCancel(bVar.l0);
            }
        }
    }

    /* compiled from: DialogFragment */
    class c implements DialogInterface.OnDismissListener {
        c() {
        }

        @SuppressLint({"SyntheticAccessor"})
        public void onDismiss(DialogInterface dialogInterface) {
            if (b.this.l0 != null) {
                b bVar = b.this;
                bVar.onDismiss(bVar.l0);
            }
        }
    }

    public void T() {
        super.T();
        Dialog dialog = this.l0;
        if (dialog != null) {
            this.m0 = true;
            dialog.setOnDismissListener((DialogInterface.OnDismissListener) null);
            this.l0.dismiss();
            if (!this.n0) {
                onDismiss(this.l0);
            }
            this.l0 = null;
        }
    }

    public void U() {
        super.U();
        if (!this.o0 && !this.n0) {
            this.n0 = true;
        }
    }

    public void X() {
        super.X();
        Dialog dialog = this.l0;
        if (dialog != null) {
            this.m0 = false;
            dialog.show();
        }
    }

    public void Y() {
        super.Y();
        Dialog dialog = this.l0;
        if (dialog != null) {
            dialog.hide();
        }
    }

    public void c(Bundle bundle) {
        super.c(bundle);
        this.b0 = new Handler();
        this.i0 = this.A == 0;
        if (bundle != null) {
            this.f0 = bundle.getInt("android:style", 0);
            this.g0 = bundle.getInt("android:theme", 0);
            this.h0 = bundle.getBoolean("android:cancelable", true);
            this.i0 = bundle.getBoolean("android:showsDialog", this.i0);
            this.j0 = bundle.getInt("android:backStackId", -1);
        }
    }

    /* JADX INFO: finally extract failed */
    public LayoutInflater d(Bundle bundle) {
        LayoutInflater d = super.d(bundle);
        if (!this.i0 || this.k0) {
            return d;
        }
        try {
            this.k0 = true;
            Dialog n = n(bundle);
            this.l0 = n;
            a(n, this.f0);
            this.k0 = false;
            return d.cloneInContext(q0().getContext());
        } catch (Throwable th) {
            this.k0 = false;
            throw th;
        }
    }

    public void e(Bundle bundle) {
        super.e(bundle);
        Dialog dialog = this.l0;
        if (dialog != null) {
            bundle.putBundle("android:savedDialogState", dialog.onSaveInstanceState());
        }
        int i2 = this.f0;
        if (i2 != 0) {
            bundle.putInt("android:style", i2);
        }
        int i3 = this.g0;
        if (i3 != 0) {
            bundle.putInt("android:theme", i3);
        }
        boolean z = this.h0;
        if (!z) {
            bundle.putBoolean("android:cancelable", z);
        }
        boolean z2 = this.i0;
        if (!z2) {
            bundle.putBoolean("android:showsDialog", z2);
        }
        int i4 = this.j0;
        if (i4 != -1) {
            bundle.putInt("android:backStackId", i4);
        }
    }

    public Dialog n(Bundle bundle) {
        return new Dialog(k0(), p0());
    }

    public void n0() {
        a(false, false);
    }

    public Dialog o0() {
        return this.l0;
    }

    public void onCancel(DialogInterface dialogInterface) {
    }

    public void onDismiss(DialogInterface dialogInterface) {
        if (!this.m0) {
            a(true, true);
        }
    }

    public int p0() {
        return this.g0;
    }

    public final Dialog q0() {
        Dialog o02 = o0();
        if (o02 != null) {
            return o02;
        }
        throw new IllegalStateException("DialogFragment " + this + " does not have a Dialog.");
    }

    public void a(j jVar, String str) {
        this.n0 = false;
        this.o0 = true;
        p b = jVar.b();
        b.a((Fragment) this, str);
        b.a();
    }

    public void b(Bundle bundle) {
        Bundle bundle2;
        super.b(bundle);
        if (this.i0) {
            View F = F();
            if (this.l0 != null) {
                if (F != null) {
                    if (F.getParent() == null) {
                        this.l0.setContentView(F);
                    } else {
                        throw new IllegalStateException("DialogFragment can not be attached to a container view");
                    }
                }
                FragmentActivity d = d();
                if (d != null) {
                    this.l0.setOwnerActivity(d);
                }
                this.l0.setCancelable(this.h0);
                this.l0.setOnCancelListener(this.d0);
                this.l0.setOnDismissListener(this.e0);
                if (bundle != null && (bundle2 = bundle.getBundle("android:savedDialogState")) != null) {
                    this.l0.onRestoreInstanceState(bundle2);
                }
            }
        }
    }

    private void a(boolean z, boolean z2) {
        if (!this.n0) {
            this.n0 = true;
            this.o0 = false;
            Dialog dialog = this.l0;
            if (dialog != null) {
                dialog.setOnDismissListener((DialogInterface.OnDismissListener) null);
                this.l0.dismiss();
                if (!z2) {
                    if (Looper.myLooper() == this.b0.getLooper()) {
                        onDismiss(this.l0);
                    } else {
                        this.b0.post(this.c0);
                    }
                }
            }
            this.m0 = true;
            if (this.j0 >= 0) {
                w().a(this.j0, 1);
                this.j0 = -1;
                return;
            }
            p b = w().b();
            b.c(this);
            if (z) {
                b.b();
            } else {
                b.a();
            }
        }
    }

    public void a(Context context) {
        super.a(context);
        if (!this.o0) {
            this.n0 = false;
        }
    }

    public void a(Dialog dialog, int i2) {
        if (!(i2 == 1 || i2 == 2)) {
            if (i2 == 3) {
                Window window = dialog.getWindow();
                if (window != null) {
                    window.addFlags(24);
                }
            } else {
                return;
            }
        }
        dialog.requestWindowFeature(1);
    }
}
