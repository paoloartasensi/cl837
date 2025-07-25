package androidx.cardview.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.cardview.widget.g;

/* compiled from: CardViewBaseImpl */
class c implements e {
    final RectF a = new RectF();

    /* compiled from: CardViewBaseImpl */
    class a implements g.a {
        a() {
        }

        public void a(Canvas canvas, RectF rectF, float f2, Paint paint) {
            Canvas canvas2 = canvas;
            RectF rectF2 = rectF;
            float f3 = 2.0f * f2;
            float width = (rectF.width() - f3) - 1.0f;
            float height = (rectF.height() - f3) - 1.0f;
            if (f2 >= 1.0f) {
                float f4 = f2 + 0.5f;
                float f5 = -f4;
                c.this.a.set(f5, f5, f4, f4);
                int save = canvas.save();
                canvas2.translate(rectF2.left + f4, rectF2.top + f4);
                Paint paint2 = paint;
                canvas.drawArc(c.this.a, 180.0f, 90.0f, true, paint2);
                canvas2.translate(width, 0.0f);
                canvas2.rotate(90.0f);
                canvas.drawArc(c.this.a, 180.0f, 90.0f, true, paint2);
                canvas2.translate(height, 0.0f);
                canvas2.rotate(90.0f);
                canvas.drawArc(c.this.a, 180.0f, 90.0f, true, paint2);
                canvas2.translate(width, 0.0f);
                canvas2.rotate(90.0f);
                canvas.drawArc(c.this.a, 180.0f, 90.0f, true, paint2);
                canvas2.restoreToCount(save);
                float f6 = rectF2.top;
                canvas.drawRect((rectF2.left + f4) - 1.0f, f6, (rectF2.right - f4) + 1.0f, f6 + f4, paint2);
                float f7 = rectF2.bottom;
                Canvas canvas3 = canvas;
                canvas3.drawRect((rectF2.left + f4) - 1.0f, f7 - f4, (rectF2.right - f4) + 1.0f, f7, paint2);
            }
            canvas.drawRect(rectF2.left, rectF2.top + f2, rectF2.right, rectF2.bottom - f2, paint);
        }
    }

    c() {
    }

    private g j(d dVar) {
        return (g) dVar.c();
    }

    public void a() {
        g.r = new a();
    }

    public void b(d dVar, float f2) {
        j(dVar).a(f2);
        i(dVar);
    }

    public void c(d dVar, float f2) {
        j(dVar).b(f2);
        i(dVar);
    }

    public float d(d dVar) {
        return j(dVar).e();
    }

    public void e(d dVar) {
    }

    public ColorStateList f(d dVar) {
        return j(dVar).a();
    }

    public void g(d dVar) {
        j(dVar).a(dVar.d());
        i(dVar);
    }

    public float h(d dVar) {
        return j(dVar).f();
    }

    public void i(d dVar) {
        Rect rect = new Rect();
        j(dVar).a(rect);
        dVar.a((int) Math.ceil((double) d(dVar)), (int) Math.ceil((double) c(dVar)));
        dVar.a(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void a(d dVar, Context context, ColorStateList colorStateList, float f2, float f3, float f4) {
        g a2 = a(context, colorStateList, f2, f3, f4);
        a2.a(dVar.d());
        dVar.a(a2);
        i(dVar);
    }

    public float b(d dVar) {
        return j(dVar).b();
    }

    public float c(d dVar) {
        return j(dVar).d();
    }

    private g a(Context context, ColorStateList colorStateList, float f2, float f3, float f4) {
        return new g(context.getResources(), colorStateList, f2, f3, f4);
    }

    public void a(d dVar, ColorStateList colorStateList) {
        j(dVar).a(colorStateList);
    }

    public void a(d dVar, float f2) {
        j(dVar).c(f2);
    }

    public float a(d dVar) {
        return j(dVar).c();
    }
}
