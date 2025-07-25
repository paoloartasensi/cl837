package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$styleable;
import androidx.core.h.a0;
import androidx.core.h.v;
import androidx.core.h.z;

/* compiled from: AbsActionBarView */
abstract class a extends ViewGroup {
    protected final C0006a e;

    /* renamed from: f  reason: collision with root package name */
    protected final Context f272f;

    /* renamed from: g  reason: collision with root package name */
    protected ActionMenuView f273g;

    /* renamed from: h  reason: collision with root package name */
    protected ActionMenuPresenter f274h;

    /* renamed from: i  reason: collision with root package name */
    protected int f275i;

    /* renamed from: j  reason: collision with root package name */
    protected z f276j;
    private boolean k;
    private boolean l;

    a(Context context) {
        this(context, (AttributeSet) null);
    }

    protected static int a(int i2, int i3, boolean z) {
        return z ? i2 - i3 : i2 + i3;
    }

    public int getAnimatedVisibility() {
        if (this.f276j != null) {
            return this.e.b;
        }
        return getVisibility();
    }

    public int getContentHeight() {
        return this.f275i;
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes((AttributeSet) null, R$styleable.ActionBar, R$attr.actionBarStyle, 0);
        setContentHeight(obtainStyledAttributes.getLayoutDimension(R$styleable.ActionBar_height, 0));
        obtainStyledAttributes.recycle();
        ActionMenuPresenter actionMenuPresenter = this.f274h;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.a(configuration);
        }
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9) {
            this.l = false;
        }
        if (!this.l) {
            boolean onHoverEvent = super.onHoverEvent(motionEvent);
            if (actionMasked == 9 && !onHoverEvent) {
                this.l = true;
            }
        }
        if (actionMasked == 10 || actionMasked == 3) {
            this.l = false;
        }
        return true;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.k = false;
        }
        if (!this.k) {
            boolean onTouchEvent = super.onTouchEvent(motionEvent);
            if (actionMasked == 0 && !onTouchEvent) {
                this.k = true;
            }
        }
        if (actionMasked == 1 || actionMasked == 3) {
            this.k = false;
        }
        return true;
    }

    public abstract void setContentHeight(int i2);

    public void setVisibility(int i2) {
        if (i2 != getVisibility()) {
            z zVar = this.f276j;
            if (zVar != null) {
                zVar.a();
            }
            super.setVisibility(i2);
        }
    }

    /* renamed from: androidx.appcompat.widget.a$a  reason: collision with other inner class name */
    /* compiled from: AbsActionBarView */
    protected class C0006a implements a0 {
        private boolean a = false;
        int b;

        protected C0006a() {
        }

        public C0006a a(z zVar, int i2) {
            a.this.f276j = zVar;
            this.b = i2;
            return this;
        }

        public void b(View view) {
            a.super.setVisibility(0);
            this.a = false;
        }

        public void c(View view) {
            this.a = true;
        }

        public void a(View view) {
            if (!this.a) {
                a aVar = a.this;
                aVar.f276j = null;
                a.super.setVisibility(this.b);
            }
        }
    }

    a(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public z a(int i2, long j2) {
        z zVar = this.f276j;
        if (zVar != null) {
            zVar.a();
        }
        if (i2 == 0) {
            if (getVisibility() != 0) {
                setAlpha(0.0f);
            }
            z a = v.a(this);
            a.a(1.0f);
            a.a(j2);
            C0006a aVar = this.e;
            aVar.a(a, i2);
            a.a((a0) aVar);
            return a;
        }
        z a2 = v.a(this);
        a2.a(0.0f);
        a2.a(j2);
        C0006a aVar2 = this.e;
        aVar2.a(a2, i2);
        a2.a((a0) aVar2);
        return a2;
    }

    a(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.e = new C0006a();
        TypedValue typedValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(R$attr.actionBarPopupTheme, typedValue, true) || typedValue.resourceId == 0) {
            this.f272f = context;
        } else {
            this.f272f = new ContextThemeWrapper(context, typedValue.resourceId);
        }
    }

    /* access modifiers changed from: protected */
    public int a(View view, int i2, int i3, int i4) {
        view.measure(View.MeasureSpec.makeMeasureSpec(i2, Integer.MIN_VALUE), i3);
        return Math.max(0, (i2 - view.getMeasuredWidth()) - i4);
    }

    /* access modifiers changed from: protected */
    public int a(View view, int i2, int i3, int i4, boolean z) {
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int i5 = i3 + ((i4 - measuredHeight) / 2);
        if (z) {
            view.layout(i2 - measuredWidth, i5, i2, measuredHeight + i5);
        } else {
            view.layout(i2, i5, i2 + measuredWidth, measuredHeight + i5);
        }
        return z ? -measuredWidth : measuredWidth;
    }
}
