package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;

/* compiled from: WrappedDrawableApi14 */
class d extends Drawable implements Drawable.Callback, c, b {
    static final PorterDuff.Mode k = PorterDuff.Mode.SRC_IN;
    private int e;

    /* renamed from: f  reason: collision with root package name */
    private PorterDuff.Mode f500f;

    /* renamed from: g  reason: collision with root package name */
    private boolean f501g;

    /* renamed from: h  reason: collision with root package name */
    f f502h;

    /* renamed from: i  reason: collision with root package name */
    private boolean f503i;

    /* renamed from: j  reason: collision with root package name */
    Drawable f504j;

    d(f fVar, Resources resources) {
        this.f502h = fVar;
        a(resources);
    }

    private void a(Resources resources) {
        Drawable.ConstantState constantState;
        f fVar = this.f502h;
        if (fVar != null && (constantState = fVar.b) != null) {
            a(constantState.newDrawable(resources));
        }
    }

    private f c() {
        return new f(this.f502h);
    }

    /* access modifiers changed from: protected */
    public boolean b() {
        return true;
    }

    public void draw(Canvas canvas) {
        this.f504j.draw(canvas);
    }

    public int getChangingConfigurations() {
        int changingConfigurations = super.getChangingConfigurations();
        f fVar = this.f502h;
        return changingConfigurations | (fVar != null ? fVar.getChangingConfigurations() : 0) | this.f504j.getChangingConfigurations();
    }

    public Drawable.ConstantState getConstantState() {
        f fVar = this.f502h;
        if (fVar == null || !fVar.a()) {
            return null;
        }
        this.f502h.a = getChangingConfigurations();
        return this.f502h;
    }

    public Drawable getCurrent() {
        return this.f504j.getCurrent();
    }

    public int getIntrinsicHeight() {
        return this.f504j.getIntrinsicHeight();
    }

    public int getIntrinsicWidth() {
        return this.f504j.getIntrinsicWidth();
    }

    public int getMinimumHeight() {
        return this.f504j.getMinimumHeight();
    }

    public int getMinimumWidth() {
        return this.f504j.getMinimumWidth();
    }

    public int getOpacity() {
        return this.f504j.getOpacity();
    }

    public boolean getPadding(Rect rect) {
        return this.f504j.getPadding(rect);
    }

    public int[] getState() {
        return this.f504j.getState();
    }

    public Region getTransparentRegion() {
        return this.f504j.getTransparentRegion();
    }

    public void invalidateDrawable(Drawable drawable) {
        invalidateSelf();
    }

    public boolean isAutoMirrored() {
        return this.f504j.isAutoMirrored();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0006, code lost:
        r0 = r1.f502h;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isStateful() {
        /*
            r1 = this;
            boolean r0 = r1.b()
            if (r0 == 0) goto L_0x000d
            androidx.core.graphics.drawable.f r0 = r1.f502h
            if (r0 == 0) goto L_0x000d
            android.content.res.ColorStateList r0 = r0.c
            goto L_0x000e
        L_0x000d:
            r0 = 0
        L_0x000e:
            if (r0 == 0) goto L_0x0016
            boolean r0 = r0.isStateful()
            if (r0 != 0) goto L_0x001e
        L_0x0016:
            android.graphics.drawable.Drawable r0 = r1.f504j
            boolean r0 = r0.isStateful()
            if (r0 == 0) goto L_0x0020
        L_0x001e:
            r0 = 1
            goto L_0x0021
        L_0x0020:
            r0 = 0
        L_0x0021:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.drawable.d.isStateful():boolean");
    }

    public void jumpToCurrentState() {
        this.f504j.jumpToCurrentState();
    }

    public Drawable mutate() {
        if (!this.f503i && super.mutate() == this) {
            this.f502h = c();
            Drawable drawable = this.f504j;
            if (drawable != null) {
                drawable.mutate();
            }
            f fVar = this.f502h;
            if (fVar != null) {
                Drawable drawable2 = this.f504j;
                fVar.b = drawable2 != null ? drawable2.getConstantState() : null;
            }
            this.f503i = true;
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        Drawable drawable = this.f504j;
        if (drawable != null) {
            drawable.setBounds(rect);
        }
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int i2) {
        return this.f504j.setLevel(i2);
    }

    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j2) {
        scheduleSelf(runnable, j2);
    }

    public void setAlpha(int i2) {
        this.f504j.setAlpha(i2);
    }

    public void setAutoMirrored(boolean z) {
        this.f504j.setAutoMirrored(z);
    }

    public void setChangingConfigurations(int i2) {
        this.f504j.setChangingConfigurations(i2);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.f504j.setColorFilter(colorFilter);
    }

    public void setDither(boolean z) {
        this.f504j.setDither(z);
    }

    public void setFilterBitmap(boolean z) {
        this.f504j.setFilterBitmap(z);
    }

    public boolean setState(int[] iArr) {
        return a(iArr) || this.f504j.setState(iArr);
    }

    public void setTint(int i2) {
        setTintList(ColorStateList.valueOf(i2));
    }

    public void setTintList(ColorStateList colorStateList) {
        this.f502h.c = colorStateList;
        a(getState());
    }

    public void setTintMode(PorterDuff.Mode mode) {
        this.f502h.d = mode;
        a(getState());
    }

    public boolean setVisible(boolean z, boolean z2) {
        return super.setVisible(z, z2) || this.f504j.setVisible(z, z2);
    }

    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }

    private boolean a(int[] iArr) {
        if (!b()) {
            return false;
        }
        f fVar = this.f502h;
        ColorStateList colorStateList = fVar.c;
        PorterDuff.Mode mode = fVar.d;
        if (colorStateList == null || mode == null) {
            this.f501g = false;
            clearColorFilter();
        } else {
            int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
            if (!(this.f501g && colorForState == this.e && mode == this.f500f)) {
                setColorFilter(colorForState, mode);
                this.e = colorForState;
                this.f500f = mode;
                this.f501g = true;
                return true;
            }
        }
        return false;
    }

    d(Drawable drawable) {
        this.f502h = c();
        a(drawable);
    }

    public final Drawable a() {
        return this.f504j;
    }

    public final void a(Drawable drawable) {
        Drawable drawable2 = this.f504j;
        if (drawable2 != null) {
            drawable2.setCallback((Drawable.Callback) null);
        }
        this.f504j = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            setVisible(drawable.isVisible(), true);
            setState(drawable.getState());
            setLevel(drawable.getLevel());
            setBounds(drawable.getBounds());
            f fVar = this.f502h;
            if (fVar != null) {
                fVar.b = drawable.getConstantState();
            }
        }
        invalidateSelf();
    }
}
