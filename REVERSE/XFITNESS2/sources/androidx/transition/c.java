package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.h.v;
import androidx.transition.l;
import java.util.Map;

/* compiled from: ChangeBounds */
public class c extends l {
    private static final String[] Q = {"android:changeBounds:bounds", "android:changeBounds:clip", "android:changeBounds:parent", "android:changeBounds:windowX", "android:changeBounds:windowY"};
    private static final Property<Drawable, PointF> R = new b(PointF.class, "boundsOrigin");
    private static final Property<k, PointF> S = new C0049c(PointF.class, "topLeft");
    private static final Property<k, PointF> T = new d(PointF.class, "bottomRight");
    private static final Property<View, PointF> U = new e(PointF.class, "bottomRight");
    private static final Property<View, PointF> V = new f(PointF.class, "topLeft");
    private static final Property<View, PointF> W = new g(PointF.class, "position");
    private static j X = new j();
    private int[] N = new int[2];
    private boolean O = false;
    private boolean P = false;

    /* compiled from: ChangeBounds */
    class a extends AnimatorListenerAdapter {
        final /* synthetic */ ViewGroup a;
        final /* synthetic */ BitmapDrawable b;
        final /* synthetic */ View c;
        final /* synthetic */ float d;

        a(c cVar, ViewGroup viewGroup, BitmapDrawable bitmapDrawable, View view, float f2) {
            this.a = viewGroup;
            this.b = bitmapDrawable;
            this.c = view;
            this.d = f2;
        }

        public void onAnimationEnd(Animator animator) {
            c0.b(this.a).b(this.b);
            c0.a(this.c, this.d);
        }
    }

    /* renamed from: androidx.transition.c$c  reason: collision with other inner class name */
    /* compiled from: ChangeBounds */
    static class C0049c extends Property<k, PointF> {
        C0049c(Class cls, String str) {
            super(cls, str);
        }

        /* renamed from: a */
        public PointF get(k kVar) {
            return null;
        }

        /* renamed from: a */
        public void set(k kVar, PointF pointF) {
            kVar.b(pointF);
        }
    }

    /* compiled from: ChangeBounds */
    static class d extends Property<k, PointF> {
        d(Class cls, String str) {
            super(cls, str);
        }

        /* renamed from: a */
        public PointF get(k kVar) {
            return null;
        }

        /* renamed from: a */
        public void set(k kVar, PointF pointF) {
            kVar.a(pointF);
        }
    }

    /* compiled from: ChangeBounds */
    static class e extends Property<View, PointF> {
        e(Class cls, String str) {
            super(cls, str);
        }

        /* renamed from: a */
        public PointF get(View view) {
            return null;
        }

        /* renamed from: a */
        public void set(View view, PointF pointF) {
            c0.a(view, view.getLeft(), view.getTop(), Math.round(pointF.x), Math.round(pointF.y));
        }
    }

    /* compiled from: ChangeBounds */
    static class f extends Property<View, PointF> {
        f(Class cls, String str) {
            super(cls, str);
        }

        /* renamed from: a */
        public PointF get(View view) {
            return null;
        }

        /* renamed from: a */
        public void set(View view, PointF pointF) {
            c0.a(view, Math.round(pointF.x), Math.round(pointF.y), view.getRight(), view.getBottom());
        }
    }

    /* compiled from: ChangeBounds */
    static class g extends Property<View, PointF> {
        g(Class cls, String str) {
            super(cls, str);
        }

        /* renamed from: a */
        public PointF get(View view) {
            return null;
        }

        /* renamed from: a */
        public void set(View view, PointF pointF) {
            int round = Math.round(pointF.x);
            int round2 = Math.round(pointF.y);
            c0.a(view, round, round2, view.getWidth() + round, view.getHeight() + round2);
        }
    }

    /* compiled from: ChangeBounds */
    class h extends AnimatorListenerAdapter {
        final /* synthetic */ k a;
        private k mViewBounds = this.a;

        h(c cVar, k kVar) {
            this.a = kVar;
        }
    }

    /* compiled from: ChangeBounds */
    class i extends AnimatorListenerAdapter {
        private boolean a;
        final /* synthetic */ View b;
        final /* synthetic */ Rect c;
        final /* synthetic */ int d;
        final /* synthetic */ int e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ int f883f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ int f884g;

        i(c cVar, View view, Rect rect, int i2, int i3, int i4, int i5) {
            this.b = view;
            this.c = rect;
            this.d = i2;
            this.e = i3;
            this.f883f = i4;
            this.f884g = i5;
        }

        public void onAnimationCancel(Animator animator) {
            this.a = true;
        }

        public void onAnimationEnd(Animator animator) {
            if (!this.a) {
                v.a(this.b, this.c);
                c0.a(this.b, this.d, this.e, this.f883f, this.f884g);
            }
        }
    }

    /* compiled from: ChangeBounds */
    class j extends m {
        boolean a = false;
        final /* synthetic */ ViewGroup b;

        j(c cVar, ViewGroup viewGroup) {
            this.b = viewGroup;
        }

        public void a(l lVar) {
            w.a(this.b, false);
        }

        public void b(l lVar) {
            w.a(this.b, true);
        }

        public void d(l lVar) {
            if (!this.a) {
                w.a(this.b, false);
            }
            lVar.b((l.f) this);
        }
    }

    private void d(r rVar) {
        View view = rVar.b;
        if (v.D(view) || view.getWidth() != 0 || view.getHeight() != 0) {
            rVar.a.put("android:changeBounds:bounds", new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
            rVar.a.put("android:changeBounds:parent", rVar.b.getParent());
            if (this.P) {
                rVar.b.getLocationInWindow(this.N);
                rVar.a.put("android:changeBounds:windowX", Integer.valueOf(this.N[0]));
                rVar.a.put("android:changeBounds:windowY", Integer.valueOf(this.N[1]));
            }
            if (this.O) {
                rVar.a.put("android:changeBounds:clip", v.i(view));
            }
        }
    }

    public void a(r rVar) {
        d(rVar);
    }

    public void c(r rVar) {
        d(rVar);
    }

    public String[] o() {
        return Q;
    }

    private boolean a(View view, View view2) {
        if (!this.P) {
            return true;
        }
        r a2 = a(view, true);
        if (a2 == null) {
            if (view == view2) {
                return true;
            }
        } else if (view2 == a2.b) {
            return true;
        }
        return false;
    }

    /* compiled from: ChangeBounds */
    static class b extends Property<Drawable, PointF> {
        private Rect a = new Rect();

        b(Class cls, String str) {
            super(cls, str);
        }

        /* renamed from: a */
        public void set(Drawable drawable, PointF pointF) {
            drawable.copyBounds(this.a);
            this.a.offsetTo(Math.round(pointF.x), Math.round(pointF.y));
            drawable.setBounds(this.a);
        }

        /* renamed from: a */
        public PointF get(Drawable drawable) {
            drawable.copyBounds(this.a);
            Rect rect = this.a;
            return new PointF((float) rect.left, (float) rect.top);
        }
    }

    /* compiled from: ChangeBounds */
    private static class k {
        private int a;
        private int b;
        private int c;
        private int d;
        private View e;

        /* renamed from: f  reason: collision with root package name */
        private int f885f;

        /* renamed from: g  reason: collision with root package name */
        private int f886g;

        k(View view) {
            this.e = view;
        }

        /* access modifiers changed from: package-private */
        public void a(PointF pointF) {
            this.c = Math.round(pointF.x);
            this.d = Math.round(pointF.y);
            int i2 = this.f886g + 1;
            this.f886g = i2;
            if (this.f885f == i2) {
                a();
            }
        }

        /* access modifiers changed from: package-private */
        public void b(PointF pointF) {
            this.a = Math.round(pointF.x);
            this.b = Math.round(pointF.y);
            int i2 = this.f885f + 1;
            this.f885f = i2;
            if (i2 == this.f886g) {
                a();
            }
        }

        private void a() {
            c0.a(this.e, this.a, this.b, this.c, this.d);
            this.f885f = 0;
            this.f886g = 0;
        }
    }

    public Animator a(ViewGroup viewGroup, r rVar, r rVar2) {
        int i2;
        View view;
        Animator animator;
        ObjectAnimator objectAnimator;
        int i3;
        Rect rect;
        ObjectAnimator objectAnimator2;
        r rVar3 = rVar;
        r rVar4 = rVar2;
        if (rVar3 == null || rVar4 == null) {
            return null;
        }
        Map<String, Object> map = rVar3.a;
        Map<String, Object> map2 = rVar4.a;
        ViewGroup viewGroup2 = (ViewGroup) map.get("android:changeBounds:parent");
        ViewGroup viewGroup3 = (ViewGroup) map2.get("android:changeBounds:parent");
        if (viewGroup2 == null || viewGroup3 == null) {
            return null;
        }
        View view2 = rVar4.b;
        if (a(viewGroup2, viewGroup3)) {
            Rect rect2 = (Rect) rVar3.a.get("android:changeBounds:bounds");
            Rect rect3 = (Rect) rVar4.a.get("android:changeBounds:bounds");
            int i4 = rect2.left;
            int i5 = rect3.left;
            int i6 = rect2.top;
            int i7 = rect3.top;
            int i8 = rect2.right;
            int i9 = rect3.right;
            int i10 = rect2.bottom;
            int i11 = rect3.bottom;
            int i12 = i8 - i4;
            int i13 = i10 - i6;
            int i14 = i9 - i5;
            int i15 = i11 - i7;
            View view3 = view2;
            Rect rect4 = (Rect) rVar3.a.get("android:changeBounds:clip");
            Rect rect5 = (Rect) rVar4.a.get("android:changeBounds:clip");
            if ((i12 == 0 || i13 == 0) && (i14 == 0 || i15 == 0)) {
                i2 = 0;
            } else {
                i2 = (i4 == i5 && i6 == i7) ? 0 : 1;
                if (!(i8 == i9 && i10 == i11)) {
                    i2++;
                }
            }
            if ((rect4 != null && !rect4.equals(rect5)) || (rect4 == null && rect5 != null)) {
                i2++;
            }
            if (i2 <= 0) {
                return null;
            }
            Rect rect6 = rect5;
            Rect rect7 = rect4;
            if (!this.O) {
                view = view3;
                c0.a(view, i4, i6, i8, i10);
                if (i2 == 2) {
                    if (i12 == i14 && i13 == i15) {
                        animator = f.a(view, W, h().a((float) i4, (float) i6, (float) i5, (float) i7));
                    } else {
                        k kVar = new k(view);
                        ObjectAnimator a2 = f.a(kVar, S, h().a((float) i4, (float) i6, (float) i5, (float) i7));
                        ObjectAnimator a3 = f.a(kVar, T, h().a((float) i8, (float) i10, (float) i9, (float) i11));
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(new Animator[]{a2, a3});
                        animatorSet.addListener(new h(this, kVar));
                        animator = animatorSet;
                    }
                } else if (i4 == i5 && i6 == i7) {
                    animator = f.a(view, U, h().a((float) i8, (float) i10, (float) i9, (float) i11));
                } else {
                    animator = f.a(view, V, h().a((float) i4, (float) i6, (float) i5, (float) i7));
                }
            } else {
                view = view3;
                c0.a(view, i4, i6, Math.max(i12, i14) + i4, Math.max(i13, i15) + i6);
                if (i4 == i5 && i6 == i7) {
                    objectAnimator = null;
                } else {
                    objectAnimator = f.a(view, W, h().a((float) i4, (float) i6, (float) i5, (float) i7));
                }
                if (rect7 == null) {
                    i3 = 0;
                    rect = new Rect(0, 0, i12, i13);
                } else {
                    i3 = 0;
                    rect = rect7;
                }
                Rect rect8 = rect6 == null ? new Rect(i3, i3, i14, i15) : rect6;
                if (!rect.equals(rect8)) {
                    v.a(view, rect);
                    j jVar = X;
                    Object[] objArr = new Object[2];
                    objArr[i3] = rect;
                    objArr[1] = rect8;
                    ObjectAnimator ofObject = ObjectAnimator.ofObject(view, "clipBounds", jVar, objArr);
                    ofObject.addListener(new i(this, view, rect6, i5, i7, i9, i11));
                    objectAnimator2 = ofObject;
                } else {
                    objectAnimator2 = null;
                }
                animator = q.a(objectAnimator, objectAnimator2);
            }
            if (view.getParent() instanceof ViewGroup) {
                ViewGroup viewGroup4 = (ViewGroup) view.getParent();
                w.a(viewGroup4, true);
                a((l.f) new j(this, viewGroup4));
            }
            return animator;
        }
        int intValue = ((Integer) rVar3.a.get("android:changeBounds:windowX")).intValue();
        int intValue2 = ((Integer) rVar3.a.get("android:changeBounds:windowY")).intValue();
        int intValue3 = ((Integer) rVar4.a.get("android:changeBounds:windowX")).intValue();
        int intValue4 = ((Integer) rVar4.a.get("android:changeBounds:windowY")).intValue();
        if (intValue == intValue3 && intValue2 == intValue4) {
            return null;
        }
        viewGroup.getLocationInWindow(this.N);
        Bitmap createBitmap = Bitmap.createBitmap(view2.getWidth(), view2.getHeight(), Bitmap.Config.ARGB_8888);
        view2.draw(new Canvas(createBitmap));
        BitmapDrawable bitmapDrawable = new BitmapDrawable(createBitmap);
        float c = c0.c(view2);
        c0.a(view2, 0.0f);
        c0.b(viewGroup).a(bitmapDrawable);
        g h2 = h();
        int[] iArr = this.N;
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(bitmapDrawable, new PropertyValuesHolder[]{i.a(R, h2.a((float) (intValue - iArr[0]), (float) (intValue2 - iArr[1]), (float) (intValue3 - iArr[0]), (float) (intValue4 - iArr[1])))});
        ofPropertyValuesHolder.addListener(new a(this, viewGroup, bitmapDrawable, view2, c));
        return ofPropertyValuesHolder;
    }
}
