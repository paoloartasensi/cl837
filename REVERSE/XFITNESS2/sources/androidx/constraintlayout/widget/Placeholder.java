package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Placeholder extends View {
    private int e = -1;

    /* renamed from: f  reason: collision with root package name */
    private View f414f = null;

    /* renamed from: g  reason: collision with root package name */
    private int f415g = 4;

    public Placeholder(Context context) {
        super(context);
        a((AttributeSet) null);
    }

    private void a(AttributeSet attributeSet) {
        super.setVisibility(this.f415g);
        this.e = -1;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.ConstraintLayout_placeholder);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = obtainStyledAttributes.getIndex(i2);
                if (index == R$styleable.ConstraintLayout_placeholder_content) {
                    this.e = obtainStyledAttributes.getResourceId(index, this.e);
                } else if (index == R$styleable.ConstraintLayout_placeholder_emptyVisibility) {
                    this.f415g = obtainStyledAttributes.getInt(index, this.f415g);
                }
            }
        }
    }

    public void b(ConstraintLayout constraintLayout) {
        if (this.e == -1 && !isInEditMode()) {
            setVisibility(this.f415g);
        }
        View findViewById = constraintLayout.findViewById(this.e);
        this.f414f = findViewById;
        if (findViewById != null) {
            ((ConstraintLayout.a) findViewById.getLayoutParams()).Z = true;
            this.f414f.setVisibility(0);
            setVisibility(0);
        }
    }

    public View getContent() {
        return this.f414f;
    }

    public int getEmptyVisibility() {
        return this.f415g;
    }

    public void onDraw(Canvas canvas) {
        if (isInEditMode()) {
            canvas.drawRGB(223, 223, 223);
            Paint paint = new Paint();
            paint.setARGB(255, 210, 210, 210);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, 0));
            Rect rect = new Rect();
            canvas.getClipBounds(rect);
            paint.setTextSize((float) rect.height());
            int height = rect.height();
            int width = rect.width();
            paint.setTextAlign(Paint.Align.LEFT);
            paint.getTextBounds("?", 0, 1, rect);
            canvas.drawText("?", ((((float) width) / 2.0f) - (((float) rect.width()) / 2.0f)) - ((float) rect.left), ((((float) height) / 2.0f) + (((float) rect.height()) / 2.0f)) - ((float) rect.bottom), paint);
        }
    }

    public void setContentId(int i2) {
        View findViewById;
        if (this.e != i2) {
            View view = this.f414f;
            if (view != null) {
                view.setVisibility(0);
                ((ConstraintLayout.a) this.f414f.getLayoutParams()).Z = false;
                this.f414f = null;
            }
            this.e = i2;
            if (i2 != -1 && (findViewById = ((View) getParent()).findViewById(i2)) != null) {
                findViewById.setVisibility(8);
            }
        }
    }

    public void setEmptyVisibility(int i2) {
        this.f415g = i2;
    }

    public Placeholder(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(attributeSet);
    }

    public void a(ConstraintLayout constraintLayout) {
        if (this.f414f != null) {
            ConstraintLayout.a aVar = (ConstraintLayout.a) getLayoutParams();
            ConstraintLayout.a aVar2 = (ConstraintLayout.a) this.f414f.getLayoutParams();
            aVar2.k0.n(0);
            aVar.k0.o(aVar2.k0.s());
            aVar.k0.g(aVar2.k0.i());
            aVar2.k0.n(8);
        }
    }

    public Placeholder(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        a(attributeSet);
    }
}
