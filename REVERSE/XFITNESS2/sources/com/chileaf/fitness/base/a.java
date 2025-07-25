package com.chileaf.fitness.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.g;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.j;
import androidx.lifecycle.LifecycleOwner;
import com.chileaf.fitness.R$id;
import java.util.HashMap;
import java.util.concurrent.CancellationException;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.g0;
import kotlinx.coroutines.h0;

/* compiled from: BaseFragment.kt */
public abstract class a<VB extends ViewDataBinding> extends Fragment implements g0 {
    protected VB b0;
    protected BaseActivity<?> c0;
    private final /* synthetic */ g0 d0 = h0.a();
    private HashMap e0;

    /* renamed from: com.chileaf.fitness.base.a$a  reason: collision with other inner class name */
    /* compiled from: BaseFragment.kt */
    static final class C0062a implements View.OnClickListener {
        final /* synthetic */ a e;

        C0062a(a aVar) {
            this.e = aVar;
        }

        public final void onClick(View view) {
            j p = this.e.p();
            if (p != null) {
                p.z();
            }
            j p2 = this.e.p();
            if (p2 != null && p2.o() == 1) {
                this.e.o0().onBackPressed();
            }
        }
    }

    private final void s0() {
        ImageView imageView = (ImageView) e(R$id.iv_toolbar_back);
        if (imageView != null) {
            imageView.setOnClickListener(new C0062a(this));
        }
        VB vb = this.b0;
        if (vb != null) {
            vb.a((LifecycleOwner) this);
            VB vb2 = this.b0;
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

    public /* synthetic */ void T() {
        super.T();
        n0();
    }

    public void U() {
        BaseViewModel q0 = q0();
        if (q0 != null) {
            q0.onDestroy(this);
        }
        VB vb = this.b0;
        if (vb != null) {
            vb.g();
            super.U();
            h0.a(this, (CancellationException) null, 1, (Object) null);
            return;
        }
        i.d("mBinding");
        throw null;
    }

    public void a(Context context) {
        i.b(context, "context");
        super.a(context);
        this.c0 = (BaseActivity) context;
    }

    /* access modifiers changed from: protected */
    public void b(View view) {
        i.b(view, "root");
    }

    public View e(int i2) {
        if (this.e0 == null) {
            this.e0 = new HashMap();
        }
        View view = (View) this.e0.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View F = F();
        if (F == null) {
            return null;
        }
        View findViewById = F.findViewById(i2);
        this.e0.put(Integer.valueOf(i2), findViewById);
        return findViewById;
    }

    /* access modifiers changed from: protected */
    public final String f(int i2) {
        if (i2 >= 10) {
            return String.valueOf(i2);
        }
        StringBuilder sb = new StringBuilder();
        sb.append('0');
        sb.append(i2);
        return sb.toString();
    }

    public CoroutineContext getCoroutineContext() {
        return this.d0.getCoroutineContext();
    }

    /* access modifiers changed from: protected */
    public abstract void n(Bundle bundle);

    public void n0() {
        HashMap hashMap = this.e0;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    /* access modifiers changed from: protected */
    public final BaseActivity<?> o0() {
        BaseActivity<?> baseActivity = this.c0;
        if (baseActivity != null) {
            return baseActivity;
        }
        i.d("mActivity");
        throw null;
    }

    /* access modifiers changed from: protected */
    public final VB p0() {
        VB vb = this.b0;
        if (vb != null) {
            return vb;
        }
        i.d("mBinding");
        throw null;
    }

    /* access modifiers changed from: protected */
    public abstract BaseViewModel q0();

    /* access modifiers changed from: protected */
    public abstract int r0();

    public View a(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        i.b(layoutInflater, "inflater");
        VB a = g.a(layoutInflater, r0(), viewGroup, false);
        i.a((Object) a, "DataBindingUtil.inflate(…utId(), container, false)");
        this.b0 = a;
        BaseViewModel q0 = q0();
        if (q0 != null) {
            q0.onCreate(this);
        }
        VB vb = this.b0;
        if (vb != null) {
            View c = vb.c();
            i.a((Object) c, "mBinding.root");
            b(c);
            VB vb2 = this.b0;
            if (vb2 != null) {
                return vb2.c();
            }
            i.d("mBinding");
            throw null;
        }
        i.d("mBinding");
        throw null;
    }

    public void a(View view, Bundle bundle) {
        i.b(view, "view");
        super.a(view, bundle);
        n(bundle);
        s0();
    }

    /* access modifiers changed from: protected */
    public final <T> T a(String str, T t) {
        i.b(str, "key");
        Bundle i2 = i();
        if (i2 == null) {
            return null;
        }
        i.a((Object) i2, "it");
        return a(i2, str, t);
    }

    public static /* synthetic */ Object a(a aVar, String str, Object obj, int i2, Object obj2) {
        if (obj2 == null) {
            if ((i2 & 2) != 0) {
                obj = null;
            }
            return aVar.a(str, obj);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: autoWired");
    }

    private final <T> T a(Bundle bundle, String str, T t) {
        if (bundle.get(str) == null) {
            return t;
        }
        try {
            T t2 = bundle.get(str);
            if (!(t2 instanceof Object)) {
                return null;
            }
            return t2;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }
}
