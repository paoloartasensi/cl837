package com.google.android.material.transformation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.h.v;
import com.google.android.material.R$id;
import com.google.android.material.a.h;
import com.google.android.material.a.i;
import com.google.android.material.a.j;
import com.google.android.material.circularreveal.c;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public abstract class FabTransformationBehavior extends ExpandableTransformationBehavior {
    private final Rect c = new Rect();
    private final RectF d = new RectF();
    private final RectF e = new RectF();

    /* renamed from: f  reason: collision with root package name */
    private final int[] f1570f = new int[2];

    class a extends AnimatorListenerAdapter {
        final /* synthetic */ boolean a;
        final /* synthetic */ View b;
        final /* synthetic */ View c;

        a(FabTransformationBehavior fabTransformationBehavior, boolean z, View view, View view2) {
            this.a = z;
            this.b = view;
            this.c = view2;
        }

        public void onAnimationEnd(Animator animator) {
            if (!this.a) {
                this.b.setVisibility(4);
                this.c.setAlpha(1.0f);
                this.c.setVisibility(0);
            }
        }

        public void onAnimationStart(Animator animator) {
            if (this.a) {
                this.b.setVisibility(0);
                this.c.setAlpha(0.0f);
                this.c.setVisibility(4);
            }
        }
    }

    class b implements ValueAnimator.AnimatorUpdateListener {
        final /* synthetic */ View a;

        b(FabTransformationBehavior fabTransformationBehavior, View view) {
            this.a = view;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            this.a.invalidate();
        }
    }

    class c extends AnimatorListenerAdapter {
        final /* synthetic */ com.google.android.material.circularreveal.c a;
        final /* synthetic */ Drawable b;

        c(FabTransformationBehavior fabTransformationBehavior, com.google.android.material.circularreveal.c cVar, Drawable drawable) {
            this.a = cVar;
            this.b = drawable;
        }

        public void onAnimationEnd(Animator animator) {
            this.a.setCircularRevealOverlayDrawable((Drawable) null);
        }

        public void onAnimationStart(Animator animator) {
            this.a.setCircularRevealOverlayDrawable(this.b);
        }
    }

    class d extends AnimatorListenerAdapter {
        final /* synthetic */ com.google.android.material.circularreveal.c a;

        d(FabTransformationBehavior fabTransformationBehavior, com.google.android.material.circularreveal.c cVar) {
            this.a = cVar;
        }

        public void onAnimationEnd(Animator animator) {
            c.e revealInfo = this.a.getRevealInfo();
            revealInfo.c = Float.MAX_VALUE;
            this.a.setRevealInfo(revealInfo);
        }
    }

    protected static class e {
        public h a;
        public j b;

        protected e() {
        }
    }

    public FabTransformationBehavior() {
    }

    @TargetApi(21)
    private void c(View view, View view2, boolean z, boolean z2, e eVar, List<Animator> list, List<Animator.AnimatorListener> list2) {
        ObjectAnimator objectAnimator;
        float k = v.k(view2) - v.k(view);
        if (z) {
            if (!z2) {
                view2.setTranslationZ(-k);
            }
            objectAnimator = ObjectAnimator.ofFloat(view2, View.TRANSLATION_Z, new float[]{0.0f});
        } else {
            objectAnimator = ObjectAnimator.ofFloat(view2, View.TRANSLATION_Z, new float[]{-k});
        }
        eVar.a.a("elevation").a((Animator) objectAnimator);
        list.add(objectAnimator);
    }

    private void d(View view, View view2, boolean z, boolean z2, e eVar, List<Animator> list, List<Animator.AnimatorListener> list2) {
        ObjectAnimator objectAnimator;
        if ((view2 instanceof com.google.android.material.circularreveal.c) && (view instanceof ImageView)) {
            com.google.android.material.circularreveal.c cVar = (com.google.android.material.circularreveal.c) view2;
            Drawable drawable = ((ImageView) view).getDrawable();
            if (drawable != null) {
                drawable.mutate();
                if (z) {
                    if (!z2) {
                        drawable.setAlpha(255);
                    }
                    objectAnimator = ObjectAnimator.ofInt(drawable, com.google.android.material.a.e.b, new int[]{0});
                } else {
                    objectAnimator = ObjectAnimator.ofInt(drawable, com.google.android.material.a.e.b, new int[]{255});
                }
                objectAnimator.addUpdateListener(new b(this, view2));
                eVar.a.a("iconFade").a((Animator) objectAnimator);
                list.add(objectAnimator);
                list2.add(new c(this, cVar, drawable));
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract e a(Context context, boolean z);

    public boolean a(CoordinatorLayout coordinatorLayout, View view, View view2) {
        if (view.getVisibility() == 8) {
            throw new IllegalStateException("This behavior cannot be attached to a GONE view. Set the view to INVISIBLE instead.");
        } else if (!(view2 instanceof FloatingActionButton)) {
            return false;
        } else {
            int expandedComponentIdHint = ((FloatingActionButton) view2).getExpandedComponentIdHint();
            if (expandedComponentIdHint == 0 || expandedComponentIdHint == view.getId()) {
                return true;
            }
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public AnimatorSet b(View view, View view2, boolean z, boolean z2) {
        boolean z3 = z;
        e a2 = a(view2.getContext(), z3);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (Build.VERSION.SDK_INT >= 21) {
            c(view, view2, z, z2, a2, arrayList, arrayList2);
        }
        RectF rectF = this.d;
        View view3 = view;
        View view4 = view2;
        boolean z4 = z;
        boolean z5 = z2;
        e eVar = a2;
        ArrayList arrayList3 = arrayList;
        ArrayList arrayList4 = arrayList2;
        a(view3, view4, z4, z5, eVar, (List<Animator>) arrayList3, (List<Animator.AnimatorListener>) arrayList4, rectF);
        float width = rectF.width();
        float height = rectF.height();
        d(view3, view4, z4, z5, eVar, arrayList3, arrayList4);
        a(view3, view4, z4, z5, eVar, width, height, (List<Animator>) arrayList, (List<Animator.AnimatorListener>) arrayList2);
        ArrayList arrayList5 = arrayList;
        ArrayList arrayList6 = arrayList2;
        b(view3, view4, z4, z5, eVar, arrayList5, arrayList6);
        a(view3, view4, z4, z5, eVar, arrayList5, arrayList6);
        AnimatorSet animatorSet = new AnimatorSet();
        com.google.android.material.a.b.a(animatorSet, arrayList);
        animatorSet.addListener(new a(this, z3, view2, view));
        int size = arrayList2.size();
        for (int i2 = 0; i2 < size; i2++) {
            animatorSet.addListener((Animator.AnimatorListener) arrayList2.get(i2));
        }
        return animatorSet;
    }

    public FabTransformationBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void a(CoordinatorLayout.f fVar) {
        if (fVar.f429h == 0) {
            fVar.f429h = 80;
        }
    }

    private float c(View view, View view2, j jVar) {
        float f2;
        float f3;
        float f4;
        RectF rectF = this.d;
        RectF rectF2 = this.e;
        a(view, rectF);
        a(view2, rectF2);
        int i2 = jVar.a & 7;
        if (i2 == 1) {
            f4 = rectF2.centerX();
            f3 = rectF.centerX();
        } else if (i2 == 3) {
            f4 = rectF2.left;
            f3 = rectF.left;
        } else if (i2 != 5) {
            f2 = 0.0f;
            return f2 + jVar.b;
        } else {
            f4 = rectF2.right;
            f3 = rectF.right;
        }
        f2 = f4 - f3;
        return f2 + jVar.b;
    }

    private void a(View view, View view2, boolean z, boolean z2, e eVar, List<Animator> list, List<Animator.AnimatorListener> list2, RectF rectF) {
        i iVar;
        i iVar2;
        ObjectAnimator objectAnimator;
        ObjectAnimator objectAnimator2;
        View view3 = view;
        View view4 = view2;
        e eVar2 = eVar;
        List<Animator> list3 = list;
        float c2 = c(view3, view4, eVar2.b);
        float d2 = d(view3, view4, eVar2.b);
        if (c2 == 0.0f || d2 == 0.0f) {
            iVar2 = eVar2.a.a("translationXLinear");
            iVar = eVar2.a.a("translationYLinear");
        } else if ((!z || d2 >= 0.0f) && (z || d2 <= 0.0f)) {
            iVar2 = eVar2.a.a("translationXCurveDownwards");
            iVar = eVar2.a.a("translationYCurveDownwards");
        } else {
            iVar2 = eVar2.a.a("translationXCurveUpwards");
            iVar = eVar2.a.a("translationYCurveUpwards");
        }
        i iVar3 = iVar2;
        i iVar4 = iVar;
        if (z) {
            if (!z2) {
                view4.setTranslationX(-c2);
                view4.setTranslationY(-d2);
            }
            objectAnimator2 = ObjectAnimator.ofFloat(view4, View.TRANSLATION_X, new float[]{0.0f});
            objectAnimator = ObjectAnimator.ofFloat(view4, View.TRANSLATION_Y, new float[]{0.0f});
            a(view2, eVar, iVar3, iVar4, -c2, -d2, 0.0f, 0.0f, rectF);
        } else {
            objectAnimator2 = ObjectAnimator.ofFloat(view4, View.TRANSLATION_X, new float[]{-c2});
            objectAnimator = ObjectAnimator.ofFloat(view4, View.TRANSLATION_Y, new float[]{-d2});
        }
        iVar3.a((Animator) objectAnimator2);
        iVar4.a((Animator) objectAnimator);
        list3.add(objectAnimator2);
        list3.add(objectAnimator);
    }

    private float d(View view, View view2, j jVar) {
        float f2;
        float f3;
        float f4;
        RectF rectF = this.d;
        RectF rectF2 = this.e;
        a(view, rectF);
        a(view2, rectF2);
        int i2 = jVar.a & 112;
        if (i2 == 16) {
            f4 = rectF2.centerY();
            f3 = rectF.centerY();
        } else if (i2 == 48) {
            f4 = rectF2.top;
            f3 = rectF.top;
        } else if (i2 != 80) {
            f2 = 0.0f;
            return f2 + jVar.c;
        } else {
            f4 = rectF2.bottom;
            f3 = rectF.bottom;
        }
        f2 = f4 - f3;
        return f2 + jVar.c;
    }

    private ViewGroup c(View view) {
        if (view instanceof ViewGroup) {
            return (ViewGroup) view;
        }
        return null;
    }

    private void b(View view, View view2, boolean z, boolean z2, e eVar, List<Animator> list, List<Animator.AnimatorListener> list2) {
        ObjectAnimator objectAnimator;
        if (view2 instanceof com.google.android.material.circularreveal.c) {
            com.google.android.material.circularreveal.c cVar = (com.google.android.material.circularreveal.c) view2;
            int b2 = b(view);
            int i2 = 16777215 & b2;
            if (z) {
                if (!z2) {
                    cVar.setCircularRevealScrimColor(b2);
                }
                objectAnimator = ObjectAnimator.ofInt(cVar, c.d.a, new int[]{i2});
            } else {
                objectAnimator = ObjectAnimator.ofInt(cVar, c.d.a, new int[]{b2});
            }
            objectAnimator.setEvaluator(com.google.android.material.a.c.a());
            eVar.a.a("color").a((Animator) objectAnimator);
            list.add(objectAnimator);
        }
    }

    private void a(View view, View view2, boolean z, boolean z2, e eVar, float f2, float f3, List<Animator> list, List<Animator.AnimatorListener> list2) {
        Animator animator;
        View view3 = view;
        View view4 = view2;
        e eVar2 = eVar;
        if (view4 instanceof com.google.android.material.circularreveal.c) {
            com.google.android.material.circularreveal.c cVar = (com.google.android.material.circularreveal.c) view4;
            float a2 = a(view3, view4, eVar2.b);
            float b2 = b(view3, view4, eVar2.b);
            ((FloatingActionButton) view3).a(this.c);
            float width = ((float) this.c.width()) / 2.0f;
            i a3 = eVar2.a.a("expansion");
            if (z) {
                if (!z2) {
                    cVar.setRevealInfo(new c.e(a2, b2, width));
                }
                if (z2) {
                    width = cVar.getRevealInfo().c;
                }
                animator = com.google.android.material.circularreveal.a.a(cVar, a2, b2, com.google.android.material.e.a.a(a2, b2, 0.0f, 0.0f, f2, f3));
                animator.addListener(new d(this, cVar));
                a(view2, a3.a(), (int) a2, (int) b2, width, list);
            } else {
                float f4 = cVar.getRevealInfo().c;
                Animator a4 = com.google.android.material.circularreveal.a.a(cVar, a2, b2, width);
                int i2 = (int) a2;
                int i3 = (int) b2;
                View view5 = view2;
                a(view5, a3.a(), i2, i3, f4, list);
                long a5 = a3.a();
                long b3 = a3.b();
                long a6 = eVar2.a.a();
                a(view5, a5, b3, a6, i2, i3, width, list);
                animator = a4;
            }
            a3.a(animator);
            list.add(animator);
            list2.add(com.google.android.material.circularreveal.a.a(cVar));
        }
    }

    private float b(View view, View view2, j jVar) {
        RectF rectF = this.d;
        RectF rectF2 = this.e;
        a(view, rectF);
        a(view2, rectF2);
        rectF2.offset(0.0f, -d(view, view2, jVar));
        return rectF.centerY() - rectF2.top;
    }

    private int b(View view) {
        ColorStateList g2 = v.g(view);
        if (g2 != null) {
            return g2.getColorForState(view.getDrawableState(), g2.getDefaultColor());
        }
        return 0;
    }

    private void a(View view, View view2, boolean z, boolean z2, e eVar, List<Animator> list, List<Animator.AnimatorListener> list2) {
        ViewGroup a2;
        ObjectAnimator objectAnimator;
        if (view2 instanceof ViewGroup) {
            if ((!(view2 instanceof com.google.android.material.circularreveal.c) || com.google.android.material.circularreveal.b.f1461j != 0) && (a2 = a(view2)) != null) {
                if (z) {
                    if (!z2) {
                        com.google.android.material.a.d.a.set(a2, Float.valueOf(0.0f));
                    }
                    objectAnimator = ObjectAnimator.ofFloat(a2, com.google.android.material.a.d.a, new float[]{1.0f});
                } else {
                    objectAnimator = ObjectAnimator.ofFloat(a2, com.google.android.material.a.d.a, new float[]{0.0f});
                }
                eVar.a.a("contentFade").a((Animator) objectAnimator);
                list.add(objectAnimator);
            }
        }
    }

    private void a(View view, RectF rectF) {
        rectF.set(0.0f, 0.0f, (float) view.getWidth(), (float) view.getHeight());
        int[] iArr = this.f1570f;
        view.getLocationInWindow(iArr);
        rectF.offsetTo((float) iArr[0], (float) iArr[1]);
        rectF.offset((float) ((int) (-view.getTranslationX())), (float) ((int) (-view.getTranslationY())));
    }

    private float a(View view, View view2, j jVar) {
        RectF rectF = this.d;
        RectF rectF2 = this.e;
        a(view, rectF);
        a(view2, rectF2);
        rectF2.offset(-c(view, view2, jVar), 0.0f);
        return rectF.centerX() - rectF2.left;
    }

    private void a(View view, e eVar, i iVar, i iVar2, float f2, float f3, float f4, float f5, RectF rectF) {
        float a2 = a(eVar, iVar, f2, f4);
        float a3 = a(eVar, iVar2, f3, f5);
        Rect rect = this.c;
        view.getWindowVisibleDisplayFrame(rect);
        RectF rectF2 = this.d;
        rectF2.set(rect);
        RectF rectF3 = this.e;
        a(view, rectF3);
        rectF3.offset(a2, a3);
        rectF3.intersect(rectF2);
        rectF.set(rectF3);
    }

    private float a(e eVar, i iVar, float f2, float f3) {
        long a2 = iVar.a();
        long b2 = iVar.b();
        i a3 = eVar.a.a("expansion");
        return com.google.android.material.a.a.a(f2, f3, iVar.c().getInterpolation(((float) (((a3.a() + a3.b()) + 17) - a2)) / ((float) b2)));
    }

    private ViewGroup a(View view) {
        View findViewById = view.findViewById(R$id.mtrl_child_content_container);
        if (findViewById != null) {
            return c(findViewById);
        }
        if ((view instanceof TransformationChildLayout) || (view instanceof TransformationChildCard)) {
            return c(((ViewGroup) view).getChildAt(0));
        }
        return c(view);
    }

    private void a(View view, long j2, int i2, int i3, float f2, List<Animator> list) {
        if (Build.VERSION.SDK_INT >= 21 && j2 > 0) {
            Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(view, i2, i3, f2, f2);
            createCircularReveal.setStartDelay(0);
            createCircularReveal.setDuration(j2);
            list.add(createCircularReveal);
        }
    }

    private void a(View view, long j2, long j3, long j4, int i2, int i3, float f2, List<Animator> list) {
        if (Build.VERSION.SDK_INT >= 21) {
            long j5 = j2 + j3;
            if (j5 < j4) {
                Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(view, i2, i3, f2, f2);
                createCircularReveal.setStartDelay(j5);
                createCircularReveal.setDuration(j4 - j5);
                list.add(createCircularReveal);
            }
        }
    }
}
