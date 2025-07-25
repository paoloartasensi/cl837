package androidx.recyclerview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: DividerItemDecoration */
public class g extends RecyclerView.n {
    private static final int[] d = {16843284};
    private Drawable a;
    private int b;
    private final Rect c = new Rect();

    public g(Context context, int i2) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(d);
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        this.a = drawable;
        if (drawable == null) {
            Log.w("DividerItem", "@android:attr/listDivider was not set in the theme used for this DividerItemDecoration. Please set that attribute all call setDrawable()");
        }
        obtainStyledAttributes.recycle();
        a(i2);
    }

    private void c(Canvas canvas, RecyclerView recyclerView) {
        int i2;
        int i3;
        canvas.save();
        if (recyclerView.getClipToPadding()) {
            i3 = recyclerView.getPaddingTop();
            i2 = recyclerView.getHeight() - recyclerView.getPaddingBottom();
            canvas.clipRect(recyclerView.getPaddingLeft(), i3, recyclerView.getWidth() - recyclerView.getPaddingRight(), i2);
        } else {
            i2 = recyclerView.getHeight();
            i3 = 0;
        }
        int childCount = recyclerView.getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = recyclerView.getChildAt(i4);
            recyclerView.getLayoutManager().b(childAt, this.c);
            int round = this.c.right + Math.round(childAt.getTranslationX());
            this.a.setBounds(round - this.a.getIntrinsicWidth(), i3, round, i2);
            this.a.draw(canvas);
        }
        canvas.restore();
    }

    private void d(Canvas canvas, RecyclerView recyclerView) {
        int i2;
        int i3;
        canvas.save();
        if (recyclerView.getClipToPadding()) {
            i3 = recyclerView.getPaddingLeft();
            i2 = recyclerView.getWidth() - recyclerView.getPaddingRight();
            canvas.clipRect(i3, recyclerView.getPaddingTop(), i2, recyclerView.getHeight() - recyclerView.getPaddingBottom());
        } else {
            i2 = recyclerView.getWidth();
            i3 = 0;
        }
        int childCount = recyclerView.getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = recyclerView.getChildAt(i4);
            recyclerView.a(childAt, this.c);
            int round = this.c.bottom + Math.round(childAt.getTranslationY());
            this.a.setBounds(i3, round - this.a.getIntrinsicHeight(), i2, round);
            this.a.draw(canvas);
        }
        canvas.restore();
    }

    public void a(int i2) {
        if (i2 == 0 || i2 == 1) {
            this.b = i2;
            return;
        }
        throw new IllegalArgumentException("Invalid orientation. It should be either HORIZONTAL or VERTICAL");
    }

    public void a(Canvas canvas, RecyclerView recyclerView, RecyclerView.z zVar) {
        if (recyclerView.getLayoutManager() != null && this.a != null) {
            if (this.b == 1) {
                d(canvas, recyclerView);
            } else {
                c(canvas, recyclerView);
            }
        }
    }

    public void a(Rect rect, View view, RecyclerView recyclerView, RecyclerView.z zVar) {
        Drawable drawable = this.a;
        if (drawable == null) {
            rect.set(0, 0, 0, 0);
        } else if (this.b == 1) {
            rect.set(0, 0, 0, drawable.getIntrinsicHeight());
        } else {
            rect.set(0, 0, drawable.getIntrinsicWidth(), 0);
        }
    }
}
