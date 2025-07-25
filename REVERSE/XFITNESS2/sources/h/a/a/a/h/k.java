package h.a.a.a.h;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import h.a.a.a.a.a;
import h.a.a.a.i.i;
import h.a.a.a.i.j;

/* compiled from: LineRadarRenderer */
public abstract class k extends l {
    public k(a aVar, j jVar) {
        super(aVar, jVar);
    }

    private boolean b() {
        return i.e() >= 18;
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, Path path, Drawable drawable) {
        if (b()) {
            int save = canvas.save();
            canvas.clipPath(path);
            drawable.setBounds((int) this.a.g(), (int) this.a.i(), (int) this.a.h(), (int) this.a.e());
            drawable.draw(canvas);
            canvas.restoreToCount(save);
            return;
        }
        throw new RuntimeException("Fill-drawables not (yet) supported below API level 18, this code was run on API level " + i.e() + ".");
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, Path path, int i2, int i3) {
        int i4 = (i2 & 16777215) | (i3 << 24);
        if (b()) {
            int save = canvas.save();
            canvas.clipPath(path);
            canvas.drawColor(i4);
            canvas.restoreToCount(save);
            return;
        }
        Paint.Style style = this.c.getStyle();
        int color = this.c.getColor();
        this.c.setStyle(Paint.Style.FILL);
        this.c.setColor(i4);
        canvas.drawPath(path, this.c);
        this.c.setColor(color);
        this.c.setStyle(style);
    }
}
