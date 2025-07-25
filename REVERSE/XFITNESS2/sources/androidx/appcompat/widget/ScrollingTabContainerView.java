package androidx.appcompat.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.R$attr;
import androidx.appcompat.app.a;
import androidx.appcompat.widget.LinearLayoutCompat;

public class ScrollingTabContainerView extends HorizontalScrollView implements AdapterView.OnItemSelectedListener {
    Runnable e;

    /* renamed from: f  reason: collision with root package name */
    private c f238f;

    /* renamed from: g  reason: collision with root package name */
    LinearLayoutCompat f239g;

    /* renamed from: h  reason: collision with root package name */
    private Spinner f240h;

    /* renamed from: i  reason: collision with root package name */
    private boolean f241i;

    /* renamed from: j  reason: collision with root package name */
    int f242j;
    int k;
    private int l;
    private int m;
    protected ViewPropertyAnimator n;

    class a implements Runnable {
        final /* synthetic */ View e;

        a(View view) {
            this.e = view;
        }

        public void run() {
            ScrollingTabContainerView.this.smoothScrollTo(this.e.getLeft() - ((ScrollingTabContainerView.this.getWidth() - this.e.getWidth()) / 2), 0);
            ScrollingTabContainerView.this.e = null;
        }
    }

    private class b extends BaseAdapter {
        b() {
        }

        public int getCount() {
            return ScrollingTabContainerView.this.f239g.getChildCount();
        }

        public Object getItem(int i2) {
            return ((d) ScrollingTabContainerView.this.f239g.getChildAt(i2)).a();
        }

        public long getItemId(int i2) {
            return (long) i2;
        }

        public View getView(int i2, View view, ViewGroup viewGroup) {
            if (view == null) {
                return ScrollingTabContainerView.this.a((a.c) getItem(i2), true);
            }
            ((d) view).a((a.c) getItem(i2));
            return view;
        }
    }

    private class c implements View.OnClickListener {
        c() {
        }

        public void onClick(View view) {
            ((d) view).a().e();
            int childCount = ScrollingTabContainerView.this.f239g.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = ScrollingTabContainerView.this.f239g.getChildAt(i2);
                childAt.setSelected(childAt == view);
            }
        }
    }

    protected class e extends AnimatorListenerAdapter {
        private boolean a = false;
        private int b;

        protected e() {
        }

        public void onAnimationCancel(Animator animator) {
            this.a = true;
        }

        public void onAnimationEnd(Animator animator) {
            if (!this.a) {
                ScrollingTabContainerView scrollingTabContainerView = ScrollingTabContainerView.this;
                scrollingTabContainerView.n = null;
                scrollingTabContainerView.setVisibility(this.b);
            }
        }

        public void onAnimationStart(Animator animator) {
            ScrollingTabContainerView.this.setVisibility(0);
            this.a = false;
        }
    }

    static {
        new DecelerateInterpolator();
    }

    public ScrollingTabContainerView(Context context) {
        super(context);
        new e();
        setHorizontalScrollBarEnabled(false);
        androidx.appcompat.d.a a2 = androidx.appcompat.d.a.a(context);
        setContentHeight(a2.e());
        this.k = a2.d();
        LinearLayoutCompat b2 = b();
        this.f239g = b2;
        addView(b2, new ViewGroup.LayoutParams(-2, -1));
    }

    private Spinner a() {
        AppCompatSpinner appCompatSpinner = new AppCompatSpinner(getContext(), (AttributeSet) null, R$attr.actionDropDownStyle);
        appCompatSpinner.setLayoutParams(new LinearLayoutCompat.a(-2, -1));
        appCompatSpinner.setOnItemSelectedListener(this);
        return appCompatSpinner;
    }

    private LinearLayoutCompat b() {
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(getContext(), (AttributeSet) null, R$attr.actionBarTabBarStyle);
        linearLayoutCompat.setMeasureWithLargestChildEnabled(true);
        linearLayoutCompat.setGravity(17);
        linearLayoutCompat.setLayoutParams(new LinearLayoutCompat.a(-2, -1));
        return linearLayoutCompat;
    }

    private boolean c() {
        Spinner spinner = this.f240h;
        return spinner != null && spinner.getParent() == this;
    }

    private void d() {
        if (!c()) {
            if (this.f240h == null) {
                this.f240h = a();
            }
            removeView(this.f239g);
            addView(this.f240h, new ViewGroup.LayoutParams(-2, -1));
            if (this.f240h.getAdapter() == null) {
                this.f240h.setAdapter(new b());
            }
            Runnable runnable = this.e;
            if (runnable != null) {
                removeCallbacks(runnable);
                this.e = null;
            }
            this.f240h.setSelection(this.m);
        }
    }

    private boolean e() {
        if (!c()) {
            return false;
        }
        removeView(this.f240h);
        addView(this.f239g, new ViewGroup.LayoutParams(-2, -1));
        setTabSelected(this.f240h.getSelectedItemPosition());
        return false;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Runnable runnable = this.e;
        if (runnable != null) {
            post(runnable);
        }
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        androidx.appcompat.d.a a2 = androidx.appcompat.d.a.a(getContext());
        setContentHeight(a2.e());
        this.k = a2.d();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Runnable runnable = this.e;
        if (runnable != null) {
            removeCallbacks(runnable);
        }
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i2, long j2) {
        ((d) view).a().e();
    }

    public void onMeasure(int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i2);
        boolean z = true;
        boolean z2 = mode == 1073741824;
        setFillViewport(z2);
        int childCount = this.f239g.getChildCount();
        if (childCount <= 1 || !(mode == 1073741824 || mode == Integer.MIN_VALUE)) {
            this.f242j = -1;
        } else {
            if (childCount > 2) {
                this.f242j = (int) (((float) View.MeasureSpec.getSize(i2)) * 0.4f);
            } else {
                this.f242j = View.MeasureSpec.getSize(i2) / 2;
            }
            this.f242j = Math.min(this.f242j, this.k);
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.l, 1073741824);
        if (z2 || !this.f241i) {
            z = false;
        }
        if (z) {
            this.f239g.measure(0, makeMeasureSpec);
            if (this.f239g.getMeasuredWidth() > View.MeasureSpec.getSize(i2)) {
                d();
            } else {
                e();
            }
        } else {
            e();
        }
        int measuredWidth = getMeasuredWidth();
        super.onMeasure(i2, makeMeasureSpec);
        int measuredWidth2 = getMeasuredWidth();
        if (z2 && measuredWidth != measuredWidth2) {
            setTabSelected(this.m);
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void setAllowCollapse(boolean z) {
        this.f241i = z;
    }

    public void setContentHeight(int i2) {
        this.l = i2;
        requestLayout();
    }

    public void setTabSelected(int i2) {
        this.m = i2;
        int childCount = this.f239g.getChildCount();
        int i3 = 0;
        while (i3 < childCount) {
            View childAt = this.f239g.getChildAt(i3);
            boolean z = i3 == i2;
            childAt.setSelected(z);
            if (z) {
                a(i2);
            }
            i3++;
        }
        Spinner spinner = this.f240h;
        if (spinner != null && i2 >= 0) {
            spinner.setSelection(i2);
        }
    }

    private class d extends LinearLayout {
        private final int[] e;

        /* renamed from: f  reason: collision with root package name */
        private a.c f244f;

        /* renamed from: g  reason: collision with root package name */
        private TextView f245g;

        /* renamed from: h  reason: collision with root package name */
        private ImageView f246h;

        /* renamed from: i  reason: collision with root package name */
        private View f247i;

        public d(Context context, a.c cVar, boolean z) {
            super(context, (AttributeSet) null, R$attr.actionBarTabStyle);
            int[] iArr = {16842964};
            this.e = iArr;
            this.f244f = cVar;
            g0 a = g0.a(context, (AttributeSet) null, iArr, R$attr.actionBarTabStyle, 0);
            if (a.g(0)) {
                setBackgroundDrawable(a.b(0));
            }
            a.a();
            if (z) {
                setGravity(8388627);
            }
            b();
        }

        public void a(a.c cVar) {
            this.f244f = cVar;
            b();
        }

        public void b() {
            a.c cVar = this.f244f;
            View b = cVar.b();
            CharSequence charSequence = null;
            if (b != null) {
                ViewParent parent = b.getParent();
                if (parent != this) {
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(b);
                    }
                    addView(b);
                }
                this.f247i = b;
                TextView textView = this.f245g;
                if (textView != null) {
                    textView.setVisibility(8);
                }
                ImageView imageView = this.f246h;
                if (imageView != null) {
                    imageView.setVisibility(8);
                    this.f246h.setImageDrawable((Drawable) null);
                    return;
                }
                return;
            }
            View view = this.f247i;
            if (view != null) {
                removeView(view);
                this.f247i = null;
            }
            Drawable c = cVar.c();
            CharSequence d = cVar.d();
            if (c != null) {
                if (this.f246h == null) {
                    AppCompatImageView appCompatImageView = new AppCompatImageView(getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                    layoutParams.gravity = 16;
                    appCompatImageView.setLayoutParams(layoutParams);
                    addView(appCompatImageView, 0);
                    this.f246h = appCompatImageView;
                }
                this.f246h.setImageDrawable(c);
                this.f246h.setVisibility(0);
            } else {
                ImageView imageView2 = this.f246h;
                if (imageView2 != null) {
                    imageView2.setVisibility(8);
                    this.f246h.setImageDrawable((Drawable) null);
                }
            }
            boolean z = !TextUtils.isEmpty(d);
            if (z) {
                if (this.f245g == null) {
                    AppCompatTextView appCompatTextView = new AppCompatTextView(getContext(), (AttributeSet) null, R$attr.actionBarTabTextStyle);
                    appCompatTextView.setEllipsize(TextUtils.TruncateAt.END);
                    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
                    layoutParams2.gravity = 16;
                    appCompatTextView.setLayoutParams(layoutParams2);
                    addView(appCompatTextView);
                    this.f245g = appCompatTextView;
                }
                this.f245g.setText(d);
                this.f245g.setVisibility(0);
            } else {
                TextView textView2 = this.f245g;
                if (textView2 != null) {
                    textView2.setVisibility(8);
                    this.f245g.setText((CharSequence) null);
                }
            }
            ImageView imageView3 = this.f246h;
            if (imageView3 != null) {
                imageView3.setContentDescription(cVar.a());
            }
            if (!z) {
                charSequence = cVar.a();
            }
            i0.a(this, charSequence);
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName("androidx.appcompat.app.ActionBar$Tab");
        }

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName("androidx.appcompat.app.ActionBar$Tab");
        }

        public void onMeasure(int i2, int i3) {
            int i4;
            super.onMeasure(i2, i3);
            if (ScrollingTabContainerView.this.f242j > 0 && getMeasuredWidth() > (i4 = ScrollingTabContainerView.this.f242j)) {
                super.onMeasure(View.MeasureSpec.makeMeasureSpec(i4, 1073741824), i3);
            }
        }

        public void setSelected(boolean z) {
            boolean z2 = isSelected() != z;
            super.setSelected(z);
            if (z2 && z) {
                sendAccessibilityEvent(4);
            }
        }

        public a.c a() {
            return this.f244f;
        }
    }

    public void a(int i2) {
        View childAt = this.f239g.getChildAt(i2);
        Runnable runnable = this.e;
        if (runnable != null) {
            removeCallbacks(runnable);
        }
        a aVar = new a(childAt);
        this.e = aVar;
        post(aVar);
    }

    /* access modifiers changed from: package-private */
    public d a(a.c cVar, boolean z) {
        d dVar = new d(getContext(), cVar, z);
        if (z) {
            dVar.setBackgroundDrawable((Drawable) null);
            dVar.setLayoutParams(new AbsListView.LayoutParams(-1, this.l));
        } else {
            dVar.setFocusable(true);
            if (this.f238f == null) {
                this.f238f = new c();
            }
            dVar.setOnClickListener(this.f238f);
        }
        return dVar;
    }
}
