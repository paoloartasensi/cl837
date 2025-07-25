package com.chileaf.fitness.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.g;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.chileaf.fitness.R$id;
import com.chileaf.fitness.b.y1;
import com.chileaf.fitness.ui.activity.DeviceSettingsActivity;
import com.chileaf.fitness.ui.c.f;
import com.jeremyliao.liveeventbus.LiveEventBus;
import java.util.HashMap;
import java.util.concurrent.CancellationException;
import kotlin.TypeCastException;
import kotlin.coroutines.CoroutineContext;
import kotlin.d;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.g0;
import kotlinx.coroutines.h0;
import kotlinx.coroutines.k1;
import kotlinx.coroutines.u0;

/* compiled from: BaseActivity.kt */
public abstract class BaseActivity<VB extends ViewDataBinding> extends AppCompatActivity implements g0 {
    private final d A = g.a(new BaseActivity$mToastBinding$2(this));
    private final d B = g.a(new BaseActivity$mLoading$2(this));
    private final /* synthetic */ g0 C = h0.a();
    private HashMap D;
    protected VB y;
    private final d z = g.a(new BaseActivity$mToast$2(this));

    /* compiled from: BaseActivity.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    /* compiled from: BaseActivity.kt */
    static final class b implements View.OnClickListener {
        final /* synthetic */ BaseActivity e;

        b(BaseActivity baseActivity) {
            this.e = baseActivity;
        }

        public final void onClick(View view) {
            this.e.onBackPressed();
        }
    }

    /* compiled from: BaseActivity.kt */
    static final class c<T> implements Observer<String> {
        final /* synthetic */ BaseActivity a;

        c(BaseActivity baseActivity) {
            this.a = baseActivity;
        }

        /* renamed from: a */
        public final void onChanged(String str) {
            if (str != null) {
                BaseActivity.a(this.a, (CharSequence) str, 0, 2, (Object) null);
            }
        }
    }

    static {
        new a((f) null);
    }

    /* access modifiers changed from: private */
    public final f q() {
        return (f) this.B.getValue();
    }

    /* access modifiers changed from: private */
    public final Toast r() {
        return (Toast) this.z.getValue();
    }

    private final y1 s() {
        return (y1) this.A.getValue();
    }

    private final void t() {
        ImageView imageView = (ImageView) d(R$id.iv_toolbar_back);
        if (imageView != null) {
            imageView.setOnClickListener(new b(this));
        }
        LiveEventBus.get("event_toast", String.class).observe(this, new c(this));
        VB vb = this.y;
        if (vb != null) {
            vb.a((LifecycleOwner) this);
            VB vb2 = this.y;
            if (vb2 != null) {
                vb2.b();
            } else {
                i.d("mBinding");
                throw null;
            }
        } else {
            i.d("mBinding");
            throw null;
        }
    }

    /* access modifiers changed from: protected */
    public abstract void a(Bundle bundle);

    /* access modifiers changed from: protected */
    public void a(View view) {
        i.b(view, "root");
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(com.chileaf.fitness.config.b.b.a(context));
    }

    public View d(int i2) {
        if (this.D == null) {
            this.D = new HashMap();
        }
        View view = (View) this.D.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i2);
        this.D.put(Integer.valueOf(i2), findViewById);
        return findViewById;
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        View currentFocus;
        i.b(motionEvent, "event");
        if (motionEvent.getAction() != 0 || (currentFocus = getCurrentFocus()) == null) {
            return onTouchEvent(motionEvent) | getWindow().superDispatchTouchEvent(motionEvent);
        }
        int[] iArr = {0, 0};
        currentFocus.getLocationInWindow(iArr);
        int i2 = iArr[0];
        boolean z2 = true;
        int i3 = iArr[1];
        int height = currentFocus.getHeight() + i3;
        int width = currentFocus.getWidth() + i2;
        if (motionEvent.getX() > ((float) i2) && motionEvent.getX() < ((float) width) && motionEvent.getY() > ((float) i3) && motionEvent.getY() < ((float) height)) {
            z2 = false;
        }
        if ((currentFocus instanceof EditText) && z2) {
            Object systemService = getSystemService("input_method");
            if (systemService != null) {
                ((InputMethodManager) systemService).hideSoftInputFromWindow(((EditText) currentFocus).getWindowToken(), 0);
                currentFocus.clearFocus();
            } else {
                throw new TypeCastException("null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public CoroutineContext getCoroutineContext() {
        return this.C.getCoroutineContext();
    }

    /* access modifiers changed from: protected */
    public final VB m() {
        VB vb = this.y;
        if (vb != null) {
            return vb;
        }
        i.d("mBinding");
        throw null;
    }

    public final void n() {
        k1 unused = e.a(this, u0.b(), (CoroutineStart) null, new BaseActivity$hideLoading$1(this, (kotlin.coroutines.c) null), 2, (Object) null);
    }

    /* access modifiers changed from: protected */
    public abstract BaseViewModel o();

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(17432576, 17432577);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        VB a2 = g.a(this, p());
        i.a((Object) a2, "DataBindingUtil.setContentView(this, layoutId())");
        this.y = a2;
        BaseViewModel o = o();
        if (o != null) {
            o.onCreate(this);
        }
        VB vb = this.y;
        if (vb != null) {
            View c2 = vb.c();
            i.a((Object) c2, "mBinding.root");
            a(c2);
            a(bundle);
            t();
            return;
        }
        i.d("mBinding");
        throw null;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        BaseViewModel o = o();
        if (o != null) {
            o.onDestroy(this);
        }
        VB vb = this.y;
        if (vb != null) {
            vb.g();
            super.onDestroy();
            h0.a(this, (CancellationException) null, 1, (Object) null);
            return;
        }
        i.d("mBinding");
        throw null;
    }

    /* access modifiers changed from: protected */
    public abstract int p();

    /* access modifiers changed from: private */
    public final void b(CharSequence charSequence, int i2) {
        TextView textView = s().z;
        i.a((Object) textView, "mToastBinding.tvToastDesc");
        textView.setText(charSequence);
        Toast r = r();
        y1 s = s();
        i.a((Object) s, "mToastBinding");
        r.setView(s.c());
        r().setDuration(i2);
        r().setGravity(17, 0, 0);
        r().show();
    }

    /* access modifiers changed from: protected */
    public final void a(String str) {
        TextView textView;
        i.b(str, "title");
        if (((Toolbar) d(R$id.toolbar)) != null && !TextUtils.isEmpty(str) && (textView = (TextView) d(R$id.tv_toolbar_title)) != null) {
            textView.setText(str);
        }
    }

    /* access modifiers changed from: protected */
    public final void a(String str, View.OnClickListener onClickListener) {
        i.b(str, "target");
        i.b(onClickListener, "listener");
        TextView textView = (TextView) d(R$id.tv_toolbar_target);
        if (textView != null) {
            textView.setVisibility(0);
            textView.setText(str);
            textView.setOnClickListener(onClickListener);
        }
    }

    public final void a(long j2) {
        k1 unused = e.a(this, u0.b(), (CoroutineStart) null, new BaseActivity$showLoading$2(this, j2, (kotlin.coroutines.c) null), 2, (Object) null);
    }

    public static /* synthetic */ void a(BaseActivity baseActivity, Class cls, boolean z2, int i2, Object obj) {
        if (obj == null) {
            if ((i2 & 2) != 0) {
                z2 = false;
            }
            baseActivity.a((Class<?>) cls, z2);
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: launchActivity");
    }

    public final void a(Class<?> cls, boolean z2) {
        if (cls != null) {
            startActivity(new Intent(this, cls));
            overridePendingTransition(17432576, 17432577);
            if (z2) {
                finish();
            }
        }
    }

    public static /* synthetic */ void a(BaseActivity baseActivity, Intent intent, boolean z2, int i2, Object obj) {
        if (obj == null) {
            if ((i2 & 2) != 0) {
                z2 = false;
            }
            baseActivity.a(intent, z2);
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: launchActivity");
    }

    public final void a(Intent intent, boolean z2) {
        if (intent != null) {
            startActivity(intent);
            overridePendingTransition(17432576, 17432577);
            if (z2) {
                finish();
            }
        }
    }

    public final void a(String str, String str2) {
        i.b(str, "title");
        i.b(str2, "fragment");
        Intent intent = new Intent(this, DeviceSettingsActivity.class);
        intent.putExtra("extra_title", str);
        intent.putExtra("extra_fragment", str2);
        startActivity(intent);
    }

    public static /* synthetic */ void a(BaseActivity baseActivity, CharSequence charSequence, int i2, int i3, Object obj) {
        if (obj == null) {
            if ((i3 & 2) != 0) {
                i2 = 0;
            }
            baseActivity.a(charSequence, i2);
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toast");
    }

    public final void a(CharSequence charSequence, int i2) {
        k1 unused = e.a(this, u0.b(), (CoroutineStart) null, new BaseActivity$toast$1(this, charSequence, i2, (kotlin.coroutines.c) null), 2, (Object) null);
    }
}
