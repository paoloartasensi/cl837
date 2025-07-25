package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$id;
import androidx.appcompat.R$layout;
import androidx.appcompat.R$styleable;
import androidx.appcompat.d.b;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.m;
import androidx.core.h.v;

public class ActionBarContextView extends a {
    private CharSequence m;
    private CharSequence n;
    private View o;
    private View p;
    private LinearLayout q;
    private TextView r;
    private TextView s;
    private int t;
    private int u;
    private boolean v;
    private int w;

    class a implements View.OnClickListener {
        final /* synthetic */ b e;

        a(ActionBarContextView actionBarContextView, b bVar) {
            this.e = bVar;
        }

        public void onClick(View view) {
            this.e.a();
        }
    }

    public ActionBarContextView(Context context) {
        this(context, (AttributeSet) null);
    }

    private void e() {
        if (this.q == null) {
            LayoutInflater.from(getContext()).inflate(R$layout.abc_action_bar_title_item, this);
            LinearLayout linearLayout = (LinearLayout) getChildAt(getChildCount() - 1);
            this.q = linearLayout;
            this.r = (TextView) linearLayout.findViewById(R$id.action_bar_title);
            this.s = (TextView) this.q.findViewById(R$id.action_bar_subtitle);
            if (this.t != 0) {
                this.r.setTextAppearance(getContext(), this.t);
            }
            if (this.u != 0) {
                this.s.setTextAppearance(getContext(), this.u);
            }
        }
        this.r.setText(this.m);
        this.s.setText(this.n);
        boolean z = !TextUtils.isEmpty(this.m);
        boolean z2 = !TextUtils.isEmpty(this.n);
        int i2 = 0;
        this.s.setVisibility(z2 ? 0 : 8);
        LinearLayout linearLayout2 = this.q;
        if (!z && !z2) {
            i2 = 8;
        }
        linearLayout2.setVisibility(i2);
        if (this.q.getParent() == null) {
            addView(this.q);
        }
    }

    public boolean b() {
        return this.v;
    }

    public void c() {
        removeAllViews();
        this.p = null;
        this.f273g = null;
    }

    public boolean d() {
        ActionMenuPresenter actionMenuPresenter = this.f274h;
        if (actionMenuPresenter != null) {
            return actionMenuPresenter.k();
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.MarginLayoutParams(-1, -2);
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ViewGroup.MarginLayoutParams(getContext(), attributeSet);
    }

    public /* bridge */ /* synthetic */ int getAnimatedVisibility() {
        return super.getAnimatedVisibility();
    }

    public /* bridge */ /* synthetic */ int getContentHeight() {
        return super.getContentHeight();
    }

    public CharSequence getSubtitle() {
        return this.n;
    }

    public CharSequence getTitle() {
        return this.m;
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ActionMenuPresenter actionMenuPresenter = this.f274h;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.g();
            this.f274h.h();
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 32) {
            accessibilityEvent.setSource(this);
            accessibilityEvent.setClassName(ActionBarContextView.class.getName());
            accessibilityEvent.setPackageName(getContext().getPackageName());
            accessibilityEvent.setContentDescription(this.m);
            return;
        }
        super.onInitializeAccessibilityEvent(accessibilityEvent);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        boolean a2 = m0.a(this);
        int paddingRight = a2 ? (i4 - i2) - getPaddingRight() : getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingTop2 = ((i5 - i3) - getPaddingTop()) - getPaddingBottom();
        View view = this.o;
        if (!(view == null || view.getVisibility() == 8)) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.o.getLayoutParams();
            int i6 = a2 ? marginLayoutParams.rightMargin : marginLayoutParams.leftMargin;
            int i7 = a2 ? marginLayoutParams.leftMargin : marginLayoutParams.rightMargin;
            int a3 = a.a(paddingRight, i6, a2);
            paddingRight = a.a(a3 + a(this.o, a3, paddingTop, paddingTop2, a2), i7, a2);
        }
        int i8 = paddingRight;
        LinearLayout linearLayout = this.q;
        if (!(linearLayout == null || this.p != null || linearLayout.getVisibility() == 8)) {
            i8 += a(this.q, i8, paddingTop, paddingTop2, a2);
        }
        int i9 = i8;
        View view2 = this.p;
        if (view2 != null) {
            a(view2, i9, paddingTop, paddingTop2, a2);
        }
        int paddingLeft = a2 ? getPaddingLeft() : (i4 - i2) - getPaddingRight();
        ActionMenuView actionMenuView = this.f273g;
        if (actionMenuView != null) {
            a(actionMenuView, paddingLeft, paddingTop, paddingTop2, !a2);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int i4 = 1073741824;
        if (View.MeasureSpec.getMode(i2) != 1073741824) {
            throw new IllegalStateException(ActionBarContextView.class.getSimpleName() + " can only be used with android:layout_width=\"match_parent\" (or fill_parent)");
        } else if (View.MeasureSpec.getMode(i3) != 0) {
            int size = View.MeasureSpec.getSize(i2);
            int i5 = this.f275i;
            if (i5 <= 0) {
                i5 = View.MeasureSpec.getSize(i3);
            }
            int paddingTop = getPaddingTop() + getPaddingBottom();
            int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
            int i6 = i5 - paddingTop;
            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i6, Integer.MIN_VALUE);
            View view = this.o;
            if (view != null) {
                int a2 = a(view, paddingLeft, makeMeasureSpec, 0);
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.o.getLayoutParams();
                paddingLeft = a2 - (marginLayoutParams.leftMargin + marginLayoutParams.rightMargin);
            }
            ActionMenuView actionMenuView = this.f273g;
            if (actionMenuView != null && actionMenuView.getParent() == this) {
                paddingLeft = a(this.f273g, paddingLeft, makeMeasureSpec, 0);
            }
            LinearLayout linearLayout = this.q;
            if (linearLayout != null && this.p == null) {
                if (this.v) {
                    this.q.measure(View.MeasureSpec.makeMeasureSpec(0, 0), makeMeasureSpec);
                    int measuredWidth = this.q.getMeasuredWidth();
                    boolean z = measuredWidth <= paddingLeft;
                    if (z) {
                        paddingLeft -= measuredWidth;
                    }
                    this.q.setVisibility(z ? 0 : 8);
                } else {
                    paddingLeft = a(linearLayout, paddingLeft, makeMeasureSpec, 0);
                }
            }
            View view2 = this.p;
            if (view2 != null) {
                ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                int i7 = layoutParams.width != -2 ? 1073741824 : Integer.MIN_VALUE;
                int i8 = layoutParams.width;
                if (i8 >= 0) {
                    paddingLeft = Math.min(i8, paddingLeft);
                }
                if (layoutParams.height == -2) {
                    i4 = Integer.MIN_VALUE;
                }
                int i9 = layoutParams.height;
                if (i9 >= 0) {
                    i6 = Math.min(i9, i6);
                }
                this.p.measure(View.MeasureSpec.makeMeasureSpec(paddingLeft, i7), View.MeasureSpec.makeMeasureSpec(i6, i4));
            }
            if (this.f275i <= 0) {
                int childCount = getChildCount();
                int i10 = 0;
                for (int i11 = 0; i11 < childCount; i11++) {
                    int measuredHeight = getChildAt(i11).getMeasuredHeight() + paddingTop;
                    if (measuredHeight > i10) {
                        i10 = measuredHeight;
                    }
                }
                setMeasuredDimension(size, i10);
                return;
            }
            setMeasuredDimension(size, i5);
        } else {
            throw new IllegalStateException(ActionBarContextView.class.getSimpleName() + " can only be used with android:layout_height=\"wrap_content\"");
        }
    }

    public void setContentHeight(int i2) {
        this.f275i = i2;
    }

    public void setCustomView(View view) {
        LinearLayout linearLayout;
        View view2 = this.p;
        if (view2 != null) {
            removeView(view2);
        }
        this.p = view;
        if (!(view == null || (linearLayout = this.q) == null)) {
            removeView(linearLayout);
            this.q = null;
        }
        if (view != null) {
            addView(view);
        }
        requestLayout();
    }

    public void setSubtitle(CharSequence charSequence) {
        this.n = charSequence;
        e();
    }

    public void setTitle(CharSequence charSequence) {
        this.m = charSequence;
        e();
    }

    public void setTitleOptional(boolean z) {
        if (z != this.v) {
            requestLayout();
        }
        this.v = z;
    }

    public /* bridge */ /* synthetic */ void setVisibility(int i2) {
        super.setVisibility(i2);
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public ActionBarContextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.actionModeStyle);
    }

    public void a(b bVar) {
        View view = this.o;
        if (view == null) {
            View inflate = LayoutInflater.from(getContext()).inflate(this.w, this, false);
            this.o = inflate;
            addView(inflate);
        } else if (view.getParent() == null) {
            addView(this.o);
        }
        this.o.findViewById(R$id.action_mode_close_button).setOnClickListener(new a(this, bVar));
        g gVar = (g) bVar.c();
        ActionMenuPresenter actionMenuPresenter = this.f274h;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.c();
        }
        ActionMenuPresenter actionMenuPresenter2 = new ActionMenuPresenter(getContext());
        this.f274h = actionMenuPresenter2;
        actionMenuPresenter2.d(true);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1);
        gVar.a((m) this.f274h, this.f272f);
        ActionMenuView actionMenuView = (ActionMenuView) this.f274h.b((ViewGroup) this);
        this.f273g = actionMenuView;
        v.a((View) actionMenuView, (Drawable) null);
        addView(this.f273g, layoutParams);
    }

    public ActionBarContextView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        g0 a2 = g0.a(context, attributeSet, R$styleable.ActionMode, i2, 0);
        v.a((View) this, a2.b(R$styleable.ActionMode_background));
        this.t = a2.g(R$styleable.ActionMode_titleTextStyle, 0);
        this.u = a2.g(R$styleable.ActionMode_subtitleTextStyle, 0);
        this.f275i = a2.f(R$styleable.ActionMode_height, 0);
        this.w = a2.g(R$styleable.ActionMode_closeItemLayout, R$layout.abc_action_mode_close_item_material);
        a2.a();
    }

    public void a() {
        if (this.o == null) {
            c();
        }
    }
}
