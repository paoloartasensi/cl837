package androidx.preference;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.c;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.b;
import androidx.preference.DialogPreference;

/* compiled from: PreferenceDialogFragmentCompat */
public abstract class f extends b implements DialogInterface.OnClickListener {
    private DialogPreference p0;
    private CharSequence q0;
    private CharSequence r0;
    private CharSequence s0;
    private CharSequence t0;
    private int u0;
    private BitmapDrawable v0;
    private int w0;

    private void a(Dialog dialog) {
        dialog.getWindow().setSoftInputMode(5);
    }

    /* access modifiers changed from: protected */
    public void a(c.a aVar) {
    }

    /* access modifiers changed from: protected */
    public View b(Context context) {
        int i2 = this.u0;
        if (i2 == 0) {
            return null;
        }
        return LayoutInflater.from(context).inflate(i2, (ViewGroup) null);
    }

    public void c(Bundle bundle) {
        super.c(bundle);
        Fragment E = E();
        if (E instanceof DialogPreference.a) {
            DialogPreference.a aVar = (DialogPreference.a) E;
            String string = i().getString("key");
            if (bundle == null) {
                DialogPreference dialogPreference = (DialogPreference) aVar.a(string);
                this.p0 = dialogPreference;
                this.q0 = dialogPreference.K();
                this.r0 = this.p0.M();
                this.s0 = this.p0.L();
                this.t0 = this.p0.J();
                this.u0 = this.p0.I();
                Drawable H = this.p0.H();
                if (H == null || (H instanceof BitmapDrawable)) {
                    this.v0 = (BitmapDrawable) H;
                    return;
                }
                Bitmap createBitmap = Bitmap.createBitmap(H.getIntrinsicWidth(), H.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                H.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                H.draw(canvas);
                this.v0 = new BitmapDrawable(y(), createBitmap);
                return;
            }
            this.q0 = bundle.getCharSequence("PreferenceDialogFragment.title");
            this.r0 = bundle.getCharSequence("PreferenceDialogFragment.positiveText");
            this.s0 = bundle.getCharSequence("PreferenceDialogFragment.negativeText");
            this.t0 = bundle.getCharSequence("PreferenceDialogFragment.message");
            this.u0 = bundle.getInt("PreferenceDialogFragment.layout", 0);
            Bitmap bitmap = (Bitmap) bundle.getParcelable("PreferenceDialogFragment.icon");
            if (bitmap != null) {
                this.v0 = new BitmapDrawable(y(), bitmap);
                return;
            }
            return;
        }
        throw new IllegalStateException("Target fragment must implement TargetFragment interface");
    }

    public void e(Bundle bundle) {
        super.e(bundle);
        bundle.putCharSequence("PreferenceDialogFragment.title", this.q0);
        bundle.putCharSequence("PreferenceDialogFragment.positiveText", this.r0);
        bundle.putCharSequence("PreferenceDialogFragment.negativeText", this.s0);
        bundle.putCharSequence("PreferenceDialogFragment.message", this.t0);
        bundle.putInt("PreferenceDialogFragment.layout", this.u0);
        BitmapDrawable bitmapDrawable = this.v0;
        if (bitmapDrawable != null) {
            bundle.putParcelable("PreferenceDialogFragment.icon", bitmapDrawable.getBitmap());
        }
    }

    public abstract void k(boolean z);

    public Dialog n(Bundle bundle) {
        FragmentActivity d = d();
        this.w0 = -2;
        c.a aVar = new c.a(d);
        aVar.b(this.q0);
        aVar.a((Drawable) this.v0);
        aVar.b(this.r0, this);
        aVar.a(this.s0, (DialogInterface.OnClickListener) this);
        View b = b((Context) d);
        if (b != null) {
            b(b);
            aVar.b(b);
        } else {
            aVar.a(this.t0);
        }
        a(aVar);
        c a = aVar.a();
        if (s0()) {
            a((Dialog) a);
        }
        return a;
    }

    public void onClick(DialogInterface dialogInterface, int i2) {
        this.w0 = i2;
    }

    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        k(this.w0 == -1);
    }

    public DialogPreference r0() {
        if (this.p0 == null) {
            this.p0 = (DialogPreference) ((DialogPreference.a) E()).a(i().getString("key"));
        }
        return this.p0;
    }

    /* access modifiers changed from: protected */
    public boolean s0() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void b(View view) {
        View findViewById = view.findViewById(16908299);
        if (findViewById != null) {
            CharSequence charSequence = this.t0;
            int i2 = 8;
            if (!TextUtils.isEmpty(charSequence)) {
                if (findViewById instanceof TextView) {
                    ((TextView) findViewById).setText(charSequence);
                }
                i2 = 0;
            }
            if (findViewById.getVisibility() != i2) {
                findViewById.setVisibility(i2);
            }
        }
    }
}
