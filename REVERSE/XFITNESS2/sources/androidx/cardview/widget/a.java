package androidx.cardview.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.cardview.widget.g;

/* compiled from: CardViewApi17Impl */
class a extends c {

    /* renamed from: androidx.cardview.widget.a$a  reason: collision with other inner class name */
    /* compiled from: CardViewApi17Impl */
    class C0009a implements g.a {
        C0009a(a aVar) {
        }

        public void a(Canvas canvas, RectF rectF, float f2, Paint paint) {
            canvas.drawRoundRect(rectF, f2, f2, paint);
        }
    }

    a() {
    }

    public void a() {
        g.r = new C0009a(this);
    }
}
