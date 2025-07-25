package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.R$id;
import androidx.appcompat.R$styleable;
import androidx.core.h.v;

public class ActionBarContainer extends FrameLayout {
    private boolean e;

    /* renamed from: f  reason: collision with root package name */
    private View f174f;

    /* renamed from: g  reason: collision with root package name */
    private View f175g;

    /* renamed from: h  reason: collision with root package name */
    private View f176h;

    /* renamed from: i  reason: collision with root package name */
    Drawable f177i;

    /* renamed from: j  reason: collision with root package name */
    Drawable f178j;
    Drawable k;
    boolean l;
    boolean m;
    private int n;

    public ActionBarContainer(Context context) {
        this(context, (AttributeSet) null);
    }

    private int a(View view) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        return view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
    }

    private boolean b(View view) {
        return view == null || view.getVisibility() == 8 || view.getMeasuredHeight() == 0;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.f177i;
        if (drawable != null && drawable.isStateful()) {
            this.f177i.setState(getDrawableState());
        }
        Drawable drawable2 = this.f178j;
        if (drawable2 != null && drawable2.isStateful()) {
            this.f178j.setState(getDrawableState());
        }
        Drawable drawable3 = this.k;
        if (drawable3 != null && drawable3.isStateful()) {
            this.k.setState(getDrawableState());
        }
    }

    public View getTabContainer() {
        return this.f174f;
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.f177i;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
        Drawable drawable2 = this.f178j;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
        Drawable drawable3 = this.k;
        if (drawable3 != null) {
            drawable3.jumpToCurrentState();
        }
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        this.f175g = findViewById(R$id.action_bar);
        this.f176h = findViewById(R$id.action_context_bar);
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        super.onHoverEvent(motionEvent);
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.e || super.onInterceptTouchEvent(motionEvent);
    }

    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        Drawable drawable;
        super.onLayout(z, i2, i3, i4, i5);
        View view = this.f174f;
        boolean z2 = true;
        boolean z3 = false;
        boolean z4 = (view == null || view.getVisibility() == 8) ? false : true;
        if (!(view == null || view.getVisibility() == 8)) {
            int measuredHeight = getMeasuredHeight();
            int i6 = ((FrameLayout.LayoutParams) view.getLayoutParams()).bottomMargin;
            view.layout(i2, (measuredHeight - view.getMeasuredHeight()) - i6, i4, measuredHeight - i6);
        }
        if (this.l) {
            Drawable drawable2 = this.k;
            if (drawable2 != null) {
                drawable2.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            } else {
                z2 = false;
            }
        } else {
            if (this.f177i != null) {
                if (this.f175g.getVisibility() == 0) {
                    this.f177i.setBounds(this.f175g.getLeft(), this.f175g.getTop(), this.f175g.getRight(), this.f175g.getBottom());
                } else {
                    View view2 = this.f176h;
                    if (view2 == null || view2.getVisibility() != 0) {
                        this.f177i.setBounds(0, 0, 0, 0);
                    } else {
                        this.f177i.setBounds(this.f176h.getLeft(), this.f176h.getTop(), this.f176h.getRight(), this.f176h.getBottom());
                    }
                }
                z3 = true;
            }
            this.m = z4;
            if (!z4 || (drawable = this.f178j) == null) {
                z2 = z3;
            } else {
                drawable.setBounds(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            }
        }
        if (z2) {
            invalidate();
        }
    }

    public void onMeasure(int i2, int i3) {
        int i4;
        int i5;
        if (this.f175g == null && View.MeasureSpec.getMode(i3) == Integer.MIN_VALUE && (i5 = this.n) >= 0) {
            i3 = View.MeasureSpec.makeMeasureSpec(Math.min(i5, View.MeasureSpec.getSize(i3)), Integer.MIN_VALUE);
        }
        super.onMeasure(i2, i3);
        if (this.f175g != null) {
            int mode = View.MeasureSpec.getMode(i3);
            View view = this.f174f;
            if (view != null && view.getVisibility() != 8 && mode != 1073741824) {
                if (!b(this.f175g)) {
                    i4 = a(this.f175g);
                } else {
                    i4 = !b(this.f176h) ? a(this.f176h) : 0;
                }
                setMeasuredDimension(getMeasuredWidth(), Math.min(i4 + a(this.f174f), mode == Integer.MIN_VALUE ? View.MeasureSpec.getSize(i3) : Integer.MAX_VALUE));
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    public void setPrimaryBackground(Drawable drawable) {
        Drawable drawable2 = this.f177i;
        if (drawable2 != null) {
            drawable2.setCallback((Drawable.Callback) null);
            unscheduleDrawable(this.f177i);
        }
        this.f177i = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            View view = this.f175g;
            if (view != null) {
                this.f177i.setBounds(view.getLeft(), this.f175g.getTop(), this.f175g.getRight(), this.f175g.getBottom());
            }
        }
        boolean z = true;
        if (!this.l ? !(this.f177i == null && this.f178j == null) : this.k != null) {
            z = false;
        }
        setWillNotDraw(z);
        invalidate();
        if (Build.VERSION.SDK_INT >= 21) {
            invalidateOutline();
        }
    }

    public void setSplitBackground(Drawable drawable) {
        Drawable drawable2;
        Drawable drawable3 = this.k;
        if (drawable3 != null) {
            drawable3.setCallback((Drawable.Callback) null);
            unscheduleDrawable(this.k);
        }
        this.k = drawable;
        boolean z = false;
        if (drawable != null) {
            drawable.setCallback(this);
            if (this.l && (drawable2 = this.k) != null) {
                drawable2.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            }
        }
        if (!this.l ? this.f177i == null && this.f178j == null : this.k == null) {
            z = true;
        }
        setWillNotDraw(z);
        invalidate();
        if (Build.VERSION.SDK_INT >= 21) {
            invalidateOutline();
        }
    }

    public void setStackedBackground(Drawable drawable) {
        Drawable drawable2;
        Drawable drawable3 = this.f178j;
        if (drawable3 != null) {
            drawable3.setCallback((Drawable.Callback) null);
            unscheduleDrawable(this.f178j);
        }
        this.f178j = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            if (this.m && (drawable2 = this.f178j) != null) {
                drawable2.setBounds(this.f174f.getLeft(), this.f174f.getTop(), this.f174f.getRight(), this.f174f.getBottom());
            }
        }
        boolean z = true;
        if (!this.l ? !(this.f177i == null && this.f178j == null) : this.k != null) {
            z = false;
        }
        setWillNotDraw(z);
        invalidate();
        if (Build.VERSION.SDK_INT >= 21) {
            invalidateOutline();
        }
    }

    public void setTabContainer(ScrollingTabContainerView scrollingTabContainerView) {
        View view = this.f174f;
        if (view != null) {
            removeView(view);
        }
        this.f174f = scrollingTabContainerView;
        if (scrollingTabContainerView != null) {
            addView(scrollingTabContainerView);
            ViewGroup.LayoutParams layoutParams = scrollingTabContainerView.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -2;
            scrollingTabContainerView.setAllowCollapse(false);
        }
    }

    public void setTransitioning(boolean z) {
        this.e = z;
        setDescendantFocusability(z ? 393216 : 262144);
    }

    public void setVisibility(int i2) {
        super.setVisibility(i2);
        boolean z = i2 == 0;
        Drawable drawable = this.f177i;
        if (drawable != null) {
            drawable.setVisible(z, false);
        }
        Drawable drawable2 = this.f178j;
        if (drawable2 != null) {
            drawable2.setVisible(z, false);
        }
        Drawable drawable3 = this.k;
        if (drawable3 != null) {
            drawable3.setVisible(z, false);
        }
    }

    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback) {
        return null;
    }

    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback, int i2) {
        if (i2 != 0) {
            return super.startActionModeForChild(view, callback, i2);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        return (drawable == this.f177i && !this.l) || (drawable == this.f178j && this.m) || ((drawable == this.k && this.l) || super.verifyDrawable(drawable));
    }

    public ActionBarContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        v.a((View) this, (Drawable) new b(this));
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ActionBar);
        this.f177i = obtainStyledAttributes.getDrawable(R$styleable.ActionBar_background);
        this.f178j = obtainStyledAttributes.getDrawable(R$styleable.ActionBar_backgroundStacked);
        this.n = obtainStyledAttributes.getDimensionPixelSize(R$styleable.ActionBar_height, -1);
        boolean z = true;
        if (getId() == R$id.split_action_bar) {
            this.l = true;
            this.k = obtainStyledAttributes.getDrawable(R$styleable.ActionBar_backgroundSplit);
        }
        obtainStyledAttributes.recycle();
        if (!this.l ? !(this.f177i == null && this.f178j == null) : this.k != null) {
            z = false;
        }
        setWillNotDraw(z);
    }
}
