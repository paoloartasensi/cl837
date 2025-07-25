package androidx.viewpager.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class PagerTabStrip extends PagerTitleStrip {
    private final Paint A;
    private final Rect B;
    private int C;
    private boolean D;
    private boolean E;
    private int F;
    private boolean G;
    private float H;
    private float I;
    private int J;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;
    private int z;

    class a implements View.OnClickListener {
        a() {
        }

        public void onClick(View view) {
            ViewPager viewPager = PagerTabStrip.this.e;
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    class b implements View.OnClickListener {
        b() {
        }

        public void onClick(View view) {
            ViewPager viewPager = PagerTabStrip.this.e;
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    }

    public PagerTabStrip(Context context) {
        this(context, (AttributeSet) null);
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, float f2, boolean z2) {
        Rect rect = this.B;
        int height = getHeight();
        int left = this.f946g.getLeft() - this.z;
        int right = this.f946g.getRight() + this.z;
        int i3 = height - this.v;
        rect.set(left, i3, right, height);
        super.a(i2, f2, z2);
        this.C = (int) (Math.abs(f2 - 0.5f) * 2.0f * 255.0f);
        rect.union(this.f946g.getLeft() - this.z, i3, this.f946g.getRight() + this.z, height);
        invalidate(rect);
    }

    public boolean getDrawFullUnderline() {
        return this.D;
    }

    /* access modifiers changed from: package-private */
    public int getMinHeight() {
        return Math.max(super.getMinHeight(), this.y);
    }

    public int getTabIndicatorColor() {
        return this.u;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int left = this.f946g.getLeft() - this.z;
        int right = this.f946g.getRight() + this.z;
        this.A.setColor((this.C << 24) | (this.u & 16777215));
        float f2 = (float) height;
        canvas.drawRect((float) left, (float) (height - this.v), (float) right, f2, this.A);
        if (this.D) {
            this.A.setColor(-16777216 | (this.u & 16777215));
            canvas.drawRect((float) getPaddingLeft(), (float) (height - this.F), (float) (getWidth() - getPaddingRight()), f2, this.A);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != 0 && this.G) {
            return false;
        }
        float x2 = motionEvent.getX();
        float y2 = motionEvent.getY();
        if (action == 0) {
            this.H = x2;
            this.I = y2;
            this.G = false;
        } else if (action != 1) {
            if (action == 2 && (Math.abs(x2 - this.H) > ((float) this.J) || Math.abs(y2 - this.I) > ((float) this.J))) {
                this.G = true;
            }
        } else if (x2 < ((float) (this.f946g.getLeft() - this.z))) {
            ViewPager viewPager = this.e;
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        } else if (x2 > ((float) (this.f946g.getRight() + this.z))) {
            ViewPager viewPager2 = this.e;
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
        return true;
    }

    public void setBackgroundColor(int i2) {
        super.setBackgroundColor(i2);
        if (!this.E) {
            this.D = (i2 & -16777216) == 0;
        }
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (!this.E) {
            this.D = drawable == null;
        }
    }

    public void setBackgroundResource(int i2) {
        super.setBackgroundResource(i2);
        if (!this.E) {
            this.D = i2 == 0;
        }
    }

    public void setDrawFullUnderline(boolean z2) {
        this.D = z2;
        this.E = true;
        invalidate();
    }

    public void setPadding(int i2, int i3, int i4, int i5) {
        int i6 = this.w;
        if (i5 < i6) {
            i5 = i6;
        }
        super.setPadding(i2, i3, i4, i5);
    }

    public void setTabIndicatorColor(int i2) {
        this.u = i2;
        this.A.setColor(i2);
        invalidate();
    }

    public void setTabIndicatorColorResource(int i2) {
        setTabIndicatorColor(androidx.core.content.a.a(getContext(), i2));
    }

    public void setTextSpacing(int i2) {
        int i3 = this.x;
        if (i2 < i3) {
            i2 = i3;
        }
        super.setTextSpacing(i2);
    }

    public PagerTabStrip(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.A = new Paint();
        this.B = new Rect();
        this.C = 255;
        this.D = false;
        this.E = false;
        int i2 = this.r;
        this.u = i2;
        this.A.setColor(i2);
        float f2 = context.getResources().getDisplayMetrics().density;
        this.v = (int) ((3.0f * f2) + 0.5f);
        this.w = (int) ((6.0f * f2) + 0.5f);
        this.x = (int) (64.0f * f2);
        this.z = (int) ((16.0f * f2) + 0.5f);
        this.F = (int) ((1.0f * f2) + 0.5f);
        this.y = (int) ((f2 * 32.0f) + 0.5f);
        this.J = ViewConfiguration.get(context).getScaledTouchSlop();
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        setTextSpacing(getTextSpacing());
        setWillNotDraw(false);
        this.f945f.setFocusable(true);
        this.f945f.setOnClickListener(new a());
        this.f947h.setFocusable(true);
        this.f947h.setOnClickListener(new b());
        if (getBackground() == null) {
            this.D = true;
        }
    }
}
