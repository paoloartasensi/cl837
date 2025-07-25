package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import androidx.constraintlayout.solver.widgets.b;

public class Barrier extends ConstraintHelper {
    private int k;
    private int l;
    private b m;

    public Barrier(Context context) {
        super(context);
        super.setVisibility(8);
    }

    /* access modifiers changed from: protected */
    public void a(AttributeSet attributeSet) {
        super.a(attributeSet);
        this.m = new b();
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.ConstraintLayout_Layout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = obtainStyledAttributes.getIndex(i2);
                if (index == R$styleable.ConstraintLayout_Layout_barrierDirection) {
                    setType(obtainStyledAttributes.getInt(index, 0));
                } else if (index == R$styleable.ConstraintLayout_Layout_barrierAllowsGoneWidgets) {
                    this.m.c(obtainStyledAttributes.getBoolean(index, true));
                }
            }
        }
        this.f401h = this.m;
        a();
    }

    public int getType() {
        return this.k;
    }

    public void setAllowsGoneWidget(boolean z) {
        this.m.c(z);
    }

    public void setType(int i2) {
        this.k = i2;
        this.l = i2;
        if (Build.VERSION.SDK_INT >= 17) {
            if (1 == getResources().getConfiguration().getLayoutDirection()) {
                int i3 = this.k;
                if (i3 == 5) {
                    this.l = 1;
                } else if (i3 == 6) {
                    this.l = 0;
                }
            } else {
                int i4 = this.k;
                if (i4 == 5) {
                    this.l = 0;
                } else if (i4 == 6) {
                    this.l = 1;
                }
            }
        } else if (i2 == 5) {
            this.l = 0;
        } else if (i2 == 6) {
            this.l = 1;
        }
        this.m.t(this.l);
    }

    public Barrier(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        super.setVisibility(8);
    }

    public Barrier(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        super.setVisibility(8);
    }
}
