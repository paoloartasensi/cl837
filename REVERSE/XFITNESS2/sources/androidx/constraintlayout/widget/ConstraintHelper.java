package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.solver.widgets.h;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Arrays;

public abstract class ConstraintHelper extends View {
    protected int[] e = new int[32];

    /* renamed from: f  reason: collision with root package name */
    protected int f399f;

    /* renamed from: g  reason: collision with root package name */
    protected Context f400g;

    /* renamed from: h  reason: collision with root package name */
    protected h f401h;

    /* renamed from: i  reason: collision with root package name */
    protected boolean f402i = false;

    /* renamed from: j  reason: collision with root package name */
    private String f403j;

    public ConstraintHelper(Context context) {
        super(context);
        this.f400g = context;
        a((AttributeSet) null);
    }

    private void setIds(String str) {
        if (str != null) {
            int i2 = 0;
            while (true) {
                int indexOf = str.indexOf(44, i2);
                if (indexOf == -1) {
                    a(str.substring(i2));
                    return;
                } else {
                    a(str.substring(i2, indexOf));
                    i2 = indexOf + 1;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a(AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.ConstraintLayout_Layout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = obtainStyledAttributes.getIndex(i2);
                if (index == R$styleable.ConstraintLayout_Layout_constraint_referenced_ids) {
                    String string = obtainStyledAttributes.getString(index);
                    this.f403j = string;
                    setIds(string);
                }
            }
        }
    }

    public void a(ConstraintLayout constraintLayout) {
    }

    public void b(ConstraintLayout constraintLayout) {
    }

    public void c(ConstraintLayout constraintLayout) {
        if (isInEditMode()) {
            setIds(this.f403j);
        }
        h hVar = this.f401h;
        if (hVar != null) {
            hVar.J();
            for (int i2 = 0; i2 < this.f399f; i2++) {
                View a = constraintLayout.a(this.e[i2]);
                if (a != null) {
                    this.f401h.b(constraintLayout.a(a));
                }
            }
        }
    }

    public int[] getReferencedIds() {
        return Arrays.copyOf(this.e, this.f399f);
    }

    public void onDraw(Canvas canvas) {
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        if (this.f402i) {
            super.onMeasure(i2, i3);
        } else {
            setMeasuredDimension(0, 0);
        }
    }

    public void setReferencedIds(int[] iArr) {
        this.f399f = 0;
        for (int tag : iArr) {
            setTag(tag, (Object) null);
        }
    }

    public void setTag(int i2, Object obj) {
        int i3 = this.f399f + 1;
        int[] iArr = this.e;
        if (i3 > iArr.length) {
            this.e = Arrays.copyOf(iArr, iArr.length * 2);
        }
        int[] iArr2 = this.e;
        int i4 = this.f399f;
        iArr2[i4] = i2;
        this.f399f = i4 + 1;
    }

    public ConstraintHelper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f400g = context;
        a(attributeSet);
    }

    public void a() {
        if (this.f401h != null) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            if (layoutParams instanceof ConstraintLayout.a) {
                ((ConstraintLayout.a) layoutParams).k0 = this.f401h;
            }
        }
    }

    public ConstraintHelper(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f400g = context;
        a(attributeSet);
    }

    private void a(String str) {
        int i2;
        Object a;
        if (str != null && this.f400g != null) {
            String trim = str.trim();
            try {
                i2 = R$id.class.getField(trim).getInt((Object) null);
            } catch (Exception unused) {
                i2 = 0;
            }
            if (i2 == 0) {
                i2 = this.f400g.getResources().getIdentifier(trim, "id", this.f400g.getPackageName());
            }
            if (i2 == 0 && isInEditMode() && (getParent() instanceof ConstraintLayout) && (a = ((ConstraintLayout) getParent()).a(0, (Object) trim)) != null && (a instanceof Integer)) {
                i2 = ((Integer) a).intValue();
            }
            if (i2 != 0) {
                setTag(i2, (Object) null);
                return;
            }
            Log.w("ConstraintHelper", "Could not find id of \"" + trim + "\"");
        }
    }
}
