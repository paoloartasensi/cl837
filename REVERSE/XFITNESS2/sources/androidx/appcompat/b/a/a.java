package androidx.appcompat.b.a;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.StateSet;
import androidx.appcompat.b.a.b;
import androidx.appcompat.b.a.e;
import androidx.appcompat.resources.R$styleable;
import androidx.appcompat.widget.x;
import androidx.vectordrawable.a.a.i;
import g.a.h;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@SuppressLint({"RestrictedAPI"})
/* compiled from: AnimatedStateListDrawableCompat */
public class a extends e implements androidx.core.graphics.drawable.b {
    private c s;
    private g t;
    private int u;
    private int v;
    private boolean w;

    /* compiled from: AnimatedStateListDrawableCompat */
    private static class b extends g {
        private final Animatable a;

        b(Animatable animatable) {
            super();
            this.a = animatable;
        }

        public void c() {
            this.a.start();
        }

        public void d() {
            this.a.stop();
        }
    }

    /* compiled from: AnimatedStateListDrawableCompat */
    static class c extends e.a {
        g.a.d<Long> K;
        h<Integer> L;

        c(c cVar, a aVar, Resources resources) {
            super(cVar, aVar, resources);
            if (cVar != null) {
                this.K = cVar.K;
                this.L = cVar.L;
                return;
            }
            this.K = new g.a.d<>();
            this.L = new h<>();
        }

        private static long f(int i2, int i3) {
            return ((long) i3) | (((long) i2) << 32);
        }

        /* access modifiers changed from: package-private */
        public int a(int i2, int i3, Drawable drawable, boolean z) {
            int a = super.a(drawable);
            long f2 = f(i2, i3);
            long j2 = z ? 8589934592L : 0;
            long j3 = (long) a;
            this.K.a(f2, Long.valueOf(j3 | j2));
            if (z) {
                this.K.a(f(i3, i2), Long.valueOf(4294967296L | j3 | j2));
            }
            return a;
        }

        /* access modifiers changed from: package-private */
        public int b(int[] iArr) {
            int a = super.a(iArr);
            if (a >= 0) {
                return a;
            }
            return super.a(StateSet.WILD_CARD);
        }

        /* access modifiers changed from: package-private */
        public int c(int i2, int i3) {
            return (int) this.K.b(f(i2, i3), -1L).longValue();
        }

        /* access modifiers changed from: package-private */
        public int d(int i2) {
            if (i2 < 0) {
                return 0;
            }
            return this.L.b(i2, 0).intValue();
        }

        /* access modifiers changed from: package-private */
        public boolean e(int i2, int i3) {
            return (this.K.b(f(i2, i3), -1L).longValue() & 8589934592L) != 0;
        }

        /* access modifiers changed from: package-private */
        public void m() {
            this.K = this.K.clone();
            this.L = this.L.clone();
        }

        public Drawable newDrawable() {
            return new a(this, (Resources) null);
        }

        /* access modifiers changed from: package-private */
        public boolean d(int i2, int i3) {
            return (this.K.b(f(i2, i3), -1L).longValue() & 4294967296L) != 0;
        }

        public Drawable newDrawable(Resources resources) {
            return new a(this, resources);
        }

        /* access modifiers changed from: package-private */
        public int a(int[] iArr, Drawable drawable, int i2) {
            int a = super.a(iArr, drawable);
            this.L.c(a, Integer.valueOf(i2));
            return a;
        }
    }

    /* compiled from: AnimatedStateListDrawableCompat */
    private static class d extends g {
        private final androidx.vectordrawable.a.a.c a;

        d(androidx.vectordrawable.a.a.c cVar) {
            super();
            this.a = cVar;
        }

        public void c() {
            this.a.start();
        }

        public void d() {
            this.a.stop();
        }
    }

    /* compiled from: AnimatedStateListDrawableCompat */
    private static class e extends g {
        private final ObjectAnimator a;
        private final boolean b;

        e(AnimationDrawable animationDrawable, boolean z, boolean z2) {
            super();
            int numberOfFrames = animationDrawable.getNumberOfFrames();
            int i2 = z ? numberOfFrames - 1 : 0;
            int i3 = z ? 0 : numberOfFrames - 1;
            f fVar = new f(animationDrawable, z);
            ObjectAnimator ofInt = ObjectAnimator.ofInt(animationDrawable, "currentIndex", new int[]{i2, i3});
            if (Build.VERSION.SDK_INT >= 18) {
                ofInt.setAutoCancel(true);
            }
            ofInt.setDuration((long) fVar.a());
            ofInt.setInterpolator(fVar);
            this.b = z2;
            this.a = ofInt;
        }

        public boolean a() {
            return this.b;
        }

        public void b() {
            this.a.reverse();
        }

        public void c() {
            this.a.start();
        }

        public void d() {
            this.a.cancel();
        }
    }

    /* compiled from: AnimatedStateListDrawableCompat */
    private static abstract class g {
        private g() {
        }

        public boolean a() {
            return false;
        }

        public void b() {
        }

        public abstract void c();

        public abstract void d();
    }

    static {
        Class<a> cls = a.class;
    }

    public a() {
        this((c) null, (Resources) null);
    }

    public static a b(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        String name = xmlPullParser.getName();
        if (name.equals("animated-selector")) {
            a aVar = new a();
            aVar.a(context, resources, xmlPullParser, attributeSet, theme);
            return aVar;
        }
        throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": invalid animated-selector tag " + name);
    }

    private void c() {
        onStateChange(getState());
    }

    private int d(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        int next;
        TypedArray a = androidx.core.content.c.g.a(resources, theme, attributeSet, R$styleable.AnimatedStateListDrawableItem);
        int resourceId = a.getResourceId(R$styleable.AnimatedStateListDrawableItem_android_id, 0);
        int resourceId2 = a.getResourceId(R$styleable.AnimatedStateListDrawableItem_android_drawable, -1);
        Drawable a2 = resourceId2 > 0 ? x.a().a(context, resourceId2) : null;
        a.recycle();
        int[] a3 = a(attributeSet);
        if (a2 == null) {
            do {
                next = xmlPullParser.next();
            } while (next == 4);
            if (next != 2) {
                throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
            } else if (xmlPullParser.getName().equals("vector")) {
                a2 = i.createFromXmlInner(resources, xmlPullParser, attributeSet, theme);
            } else if (Build.VERSION.SDK_INT >= 21) {
                a2 = Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme);
            } else {
                a2 = Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet);
            }
        }
        if (a2 != null) {
            return this.s.a(a3, a2, resourceId);
        }
        throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
    }

    private int e(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        int next;
        TypedArray a = androidx.core.content.c.g.a(resources, theme, attributeSet, R$styleable.AnimatedStateListDrawableTransition);
        int resourceId = a.getResourceId(R$styleable.AnimatedStateListDrawableTransition_android_fromId, -1);
        int resourceId2 = a.getResourceId(R$styleable.AnimatedStateListDrawableTransition_android_toId, -1);
        int resourceId3 = a.getResourceId(R$styleable.AnimatedStateListDrawableTransition_android_drawable, -1);
        Drawable a2 = resourceId3 > 0 ? x.a().a(context, resourceId3) : null;
        boolean z = a.getBoolean(R$styleable.AnimatedStateListDrawableTransition_android_reversible, false);
        a.recycle();
        if (a2 == null) {
            do {
                next = xmlPullParser.next();
            } while (next == 4);
            if (next != 2) {
                throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <transition> tag requires a 'drawable' attribute or child tag defining a drawable");
            } else if (xmlPullParser.getName().equals("animated-vector")) {
                a2 = androidx.vectordrawable.a.a.c.a(context, resources, xmlPullParser, attributeSet, theme);
            } else if (Build.VERSION.SDK_INT >= 21) {
                a2 = Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme);
            } else {
                a2 = Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet);
            }
        }
        if (a2 == null) {
            throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <transition> tag requires a 'drawable' attribute or child tag defining a drawable");
        } else if (resourceId != -1 && resourceId2 != -1) {
            return this.s.a(resourceId, resourceId2, a2, z);
        } else {
            throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <transition> tag requires 'fromId' & 'toId' attributes");
        }
    }

    public boolean isStateful() {
        return true;
    }

    public void jumpToCurrentState() {
        super.jumpToCurrentState();
        g gVar = this.t;
        if (gVar != null) {
            gVar.d();
            this.t = null;
            a(this.u);
            this.u = -1;
            this.v = -1;
        }
    }

    public Drawable mutate() {
        if (!this.w) {
            super.mutate();
            if (this == this) {
                this.s.m();
                this.w = true;
            }
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        int b2 = this.s.b(iArr);
        boolean z = b2 != b() && (b(b2) || a(b2));
        Drawable current = getCurrent();
        return current != null ? z | current.setState(iArr) : z;
    }

    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (this.t != null && (visible || z2)) {
            if (z) {
                this.t.c();
            } else {
                jumpToCurrentState();
            }
        }
        return visible;
    }

    a(c cVar, Resources resources) {
        super((e.a) null);
        this.u = -1;
        this.v = -1;
        a((b.c) new c(cVar, this, resources));
        onStateChange(getState());
        jumpToCurrentState();
    }

    private void c(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        int depth = xmlPullParser.getDepth() + 1;
        while (true) {
            int next = xmlPullParser.next();
            if (next != 1) {
                int depth2 = xmlPullParser.getDepth();
                if (depth2 < depth && next == 3) {
                    return;
                }
                if (next == 2 && depth2 <= depth) {
                    if (xmlPullParser.getName().equals("item")) {
                        d(context, resources, xmlPullParser, attributeSet, theme);
                    } else if (xmlPullParser.getName().equals("transition")) {
                        e(context, resources, xmlPullParser, attributeSet, theme);
                    }
                }
            } else {
                return;
            }
        }
    }

    public void a(Context context, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        TypedArray a = androidx.core.content.c.g.a(resources, theme, attributeSet, R$styleable.AnimatedStateListDrawableCompat);
        setVisible(a.getBoolean(R$styleable.AnimatedStateListDrawableCompat_android_visible, true), true);
        a(a);
        a(resources);
        a.recycle();
        c(context, resources, xmlPullParser, attributeSet, theme);
        c();
    }

    private boolean b(int i2) {
        int i3;
        int c2;
        g gVar;
        g gVar2 = this.t;
        if (gVar2 == null) {
            i3 = b();
        } else if (i2 == this.u) {
            return true;
        } else {
            if (i2 != this.v || !gVar2.a()) {
                i3 = this.u;
                gVar2.d();
            } else {
                gVar2.b();
                this.u = this.v;
                this.v = i2;
                return true;
            }
        }
        this.t = null;
        this.v = -1;
        this.u = -1;
        c cVar = this.s;
        int d2 = cVar.d(i3);
        int d3 = cVar.d(i2);
        if (d3 == 0 || d2 == 0 || (c2 = cVar.c(d2, d3)) < 0) {
            return false;
        }
        boolean e2 = cVar.e(d2, d3);
        a(c2);
        Drawable current = getCurrent();
        if (current instanceof AnimationDrawable) {
            gVar = new e((AnimationDrawable) current, cVar.d(d2, d3), e2);
        } else if (current instanceof androidx.vectordrawable.a.a.c) {
            gVar = new d((androidx.vectordrawable.a.a.c) current);
        } else {
            if (current instanceof Animatable) {
                gVar = new b((Animatable) current);
            }
            return false;
        }
        gVar.c();
        this.t = gVar;
        this.v = i3;
        this.u = i2;
        return true;
    }

    /* compiled from: AnimatedStateListDrawableCompat */
    private static class f implements TimeInterpolator {
        private int[] a;
        private int b;
        private int c;

        f(AnimationDrawable animationDrawable, boolean z) {
            a(animationDrawable, z);
        }

        /* access modifiers changed from: package-private */
        public int a(AnimationDrawable animationDrawable, boolean z) {
            int numberOfFrames = animationDrawable.getNumberOfFrames();
            this.b = numberOfFrames;
            int[] iArr = this.a;
            if (iArr == null || iArr.length < numberOfFrames) {
                this.a = new int[numberOfFrames];
            }
            int[] iArr2 = this.a;
            int i2 = 0;
            for (int i3 = 0; i3 < numberOfFrames; i3++) {
                int duration = animationDrawable.getDuration(z ? (numberOfFrames - i3) - 1 : i3);
                iArr2[i3] = duration;
                i2 += duration;
            }
            this.c = i2;
            return i2;
        }

        public float getInterpolation(float f2) {
            int i2 = (int) ((f2 * ((float) this.c)) + 0.5f);
            int i3 = this.b;
            int[] iArr = this.a;
            int i4 = 0;
            while (i4 < i3 && i2 >= iArr[i4]) {
                i2 -= iArr[i4];
                i4++;
            }
            return (((float) i4) / ((float) i3)) + (i4 < i3 ? ((float) i2) / ((float) this.c) : 0.0f);
        }

        /* access modifiers changed from: package-private */
        public int a() {
            return this.c;
        }
    }

    private void a(TypedArray typedArray) {
        c cVar = this.s;
        if (Build.VERSION.SDK_INT >= 21) {
            cVar.d |= typedArray.getChangingConfigurations();
        }
        cVar.b(typedArray.getBoolean(R$styleable.AnimatedStateListDrawableCompat_android_variablePadding, cVar.f91i));
        cVar.a(typedArray.getBoolean(R$styleable.AnimatedStateListDrawableCompat_android_constantSize, cVar.l));
        cVar.b(typedArray.getInt(R$styleable.AnimatedStateListDrawableCompat_android_enterFadeDuration, cVar.A));
        cVar.c(typedArray.getInt(R$styleable.AnimatedStateListDrawableCompat_android_exitFadeDuration, cVar.B));
        setDither(typedArray.getBoolean(R$styleable.AnimatedStateListDrawableCompat_android_dither, cVar.x));
    }

    /* access modifiers changed from: package-private */
    public c a() {
        return new c(this.s, this, (Resources) null);
    }

    /* access modifiers changed from: package-private */
    public void a(b.c cVar) {
        super.a(cVar);
        if (cVar instanceof c) {
            this.s = (c) cVar;
        }
    }
}
