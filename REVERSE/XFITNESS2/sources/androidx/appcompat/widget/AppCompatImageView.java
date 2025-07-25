package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.core.h.u;
import androidx.core.widget.l;

public class AppCompatImageView extends ImageView implements u, l {
    private final d e;

    /* renamed from: f  reason: collision with root package name */
    private final h f208f;

    public AppCompatImageView(Context context) {
        this(context, (AttributeSet) null);
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        d dVar = this.e;
        if (dVar != null) {
            dVar.a();
        }
        h hVar = this.f208f;
        if (hVar != null) {
            hVar.a();
        }
    }

    public ColorStateList getSupportBackgroundTintList() {
        d dVar = this.e;
        if (dVar != null) {
            return dVar.b();
        }
        return null;
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        d dVar = this.e;
        if (dVar != null) {
            return dVar.c();
        }
        return null;
    }

    public ColorStateList getSupportImageTintList() {
        h hVar = this.f208f;
        if (hVar != null) {
            return hVar.b();
        }
        return null;
    }

    public PorterDuff.Mode getSupportImageTintMode() {
        h hVar = this.f208f;
        if (hVar != null) {
            return hVar.c();
        }
        return null;
    }

    public boolean hasOverlappingRendering() {
        return this.f208f.d() && super.hasOverlappingRendering();
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        d dVar = this.e;
        if (dVar != null) {
            dVar.a(drawable);
        }
    }

    public void setBackgroundResource(int i2) {
        super.setBackgroundResource(i2);
        d dVar = this.e;
        if (dVar != null) {
            dVar.a(i2);
        }
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        h hVar = this.f208f;
        if (hVar != null) {
            hVar.a();
        }
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        h hVar = this.f208f;
        if (hVar != null) {
            hVar.a();
        }
    }

    public void setImageResource(int i2) {
        h hVar = this.f208f;
        if (hVar != null) {
            hVar.a(i2);
        }
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        h hVar = this.f208f;
        if (hVar != null) {
            hVar.a();
        }
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        d dVar = this.e;
        if (dVar != null) {
            dVar.b(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        d dVar = this.e;
        if (dVar != null) {
            dVar.a(mode);
        }
    }

    public void setSupportImageTintList(ColorStateList colorStateList) {
        h hVar = this.f208f;
        if (hVar != null) {
            hVar.a(colorStateList);
        }
    }

    public void setSupportImageTintMode(PorterDuff.Mode mode) {
        h hVar = this.f208f;
        if (hVar != null) {
            hVar.a(mode);
        }
    }

    public AppCompatImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AppCompatImageView(Context context, AttributeSet attributeSet, int i2) {
        super(d0.b(context), attributeSet, i2);
        d dVar = new d(this);
        this.e = dVar;
        dVar.a(attributeSet, i2);
        h hVar = new h(this);
        this.f208f = hVar;
        hVar.a(attributeSet, i2);
    }
}
