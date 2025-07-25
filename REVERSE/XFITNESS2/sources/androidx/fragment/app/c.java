package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import androidx.core.d.a;
import androidx.core.h.s;
import androidx.fragment.R$anim;
import androidx.fragment.R$id;
import androidx.fragment.app.q;

/* compiled from: FragmentAnim */
class c {

    /* compiled from: FragmentAnim */
    static class a implements a.C0017a {
        final /* synthetic */ Fragment a;

        a(Fragment fragment) {
            this.a = fragment;
        }

        public void a() {
            if (this.a.g() != null) {
                View g2 = this.a.g();
                this.a.a((View) null);
                g2.clearAnimation();
            }
            this.a.a((Animator) null);
        }
    }

    /* compiled from: FragmentAnim */
    static class b implements Animation.AnimationListener {
        final /* synthetic */ ViewGroup a;
        final /* synthetic */ Fragment b;
        final /* synthetic */ q.g c;
        final /* synthetic */ androidx.core.d.a d;

        /* compiled from: FragmentAnim */
        class a implements Runnable {
            a() {
            }

            public void run() {
                if (b.this.b.g() != null) {
                    b.this.b.a((View) null);
                    b bVar = b.this;
                    bVar.c.a(bVar.b, bVar.d);
                }
            }
        }

        b(ViewGroup viewGroup, Fragment fragment, q.g gVar, androidx.core.d.a aVar) {
            this.a = viewGroup;
            this.b = fragment;
            this.c = gVar;
            this.d = aVar;
        }

        public void onAnimationEnd(Animation animation) {
            this.a.post(new a());
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    /* renamed from: androidx.fragment.app.c$c  reason: collision with other inner class name */
    /* compiled from: FragmentAnim */
    static class C0033c extends AnimatorListenerAdapter {
        final /* synthetic */ ViewGroup a;
        final /* synthetic */ View b;
        final /* synthetic */ Fragment c;
        final /* synthetic */ q.g d;
        final /* synthetic */ androidx.core.d.a e;

        C0033c(ViewGroup viewGroup, View view, Fragment fragment, q.g gVar, androidx.core.d.a aVar) {
            this.a = viewGroup;
            this.b = view;
            this.c = fragment;
            this.d = gVar;
            this.e = aVar;
        }

        public void onAnimationEnd(Animator animator) {
            this.a.endViewTransition(this.b);
            Animator h2 = this.c.h();
            this.c.a((Animator) null);
            if (h2 != null && this.a.indexOfChild(this.b) < 0) {
                this.d.a(this.c, this.e);
            }
        }
    }

    static d a(Context context, d dVar, Fragment fragment, boolean z) {
        int a2;
        int u = fragment.u();
        int t = fragment.t();
        boolean z2 = false;
        fragment.b(0);
        View a3 = dVar.a(fragment.A);
        if (!(a3 == null || a3.getTag(R$id.visible_removing_fragment_view_tag) == null)) {
            a3.setTag(R$id.visible_removing_fragment_view_tag, (Object) null);
        }
        ViewGroup viewGroup = fragment.J;
        if (viewGroup != null && viewGroup.getLayoutTransition() != null) {
            return null;
        }
        Animation a4 = fragment.a(u, z, t);
        if (a4 != null) {
            return new d(a4);
        }
        Animator b2 = fragment.b(u, z, t);
        if (b2 != null) {
            return new d(b2);
        }
        if (t != 0) {
            boolean equals = "anim".equals(context.getResources().getResourceTypeName(t));
            if (equals) {
                try {
                    Animation loadAnimation = AnimationUtils.loadAnimation(context, t);
                    if (loadAnimation != null) {
                        return new d(loadAnimation);
                    }
                    z2 = true;
                } catch (Resources.NotFoundException e2) {
                    throw e2;
                } catch (RuntimeException unused) {
                }
            }
            if (!z2) {
                try {
                    Animator loadAnimator = AnimatorInflater.loadAnimator(context, t);
                    if (loadAnimator != null) {
                        return new d(loadAnimator);
                    }
                } catch (RuntimeException e3) {
                    if (!equals) {
                        Animation loadAnimation2 = AnimationUtils.loadAnimation(context, t);
                        if (loadAnimation2 != null) {
                            return new d(loadAnimation2);
                        }
                    } else {
                        throw e3;
                    }
                }
            }
        }
        if (u != 0 && (a2 = a(u, z)) >= 0) {
            return new d(AnimationUtils.loadAnimation(context, a2));
        }
        return null;
    }

    /* compiled from: FragmentAnim */
    static class d {
        public final Animation a;
        public final Animator b;

        d(Animation animation) {
            this.a = animation;
            this.b = null;
            if (animation == null) {
                throw new IllegalStateException("Animation cannot be null");
            }
        }

        d(Animator animator) {
            this.a = null;
            this.b = animator;
            if (animator == null) {
                throw new IllegalStateException("Animator cannot be null");
            }
        }
    }

    /* compiled from: FragmentAnim */
    private static class e extends AnimationSet implements Runnable {
        private final ViewGroup e;

        /* renamed from: f  reason: collision with root package name */
        private final View f612f;

        /* renamed from: g  reason: collision with root package name */
        private boolean f613g;

        /* renamed from: h  reason: collision with root package name */
        private boolean f614h;

        /* renamed from: i  reason: collision with root package name */
        private boolean f615i = true;

        e(Animation animation, ViewGroup viewGroup, View view) {
            super(false);
            this.e = viewGroup;
            this.f612f = view;
            addAnimation(animation);
            this.e.post(this);
        }

        public boolean getTransformation(long j2, Transformation transformation) {
            this.f615i = true;
            if (this.f613g) {
                return !this.f614h;
            }
            if (!super.getTransformation(j2, transformation)) {
                this.f613g = true;
                s.a(this.e, this);
            }
            return true;
        }

        public void run() {
            if (this.f613g || !this.f615i) {
                this.e.endViewTransition(this.f612f);
                this.f614h = true;
                return;
            }
            this.f615i = false;
            this.e.post(this);
        }

        public boolean getTransformation(long j2, Transformation transformation, float f2) {
            this.f615i = true;
            if (this.f613g) {
                return !this.f614h;
            }
            if (!super.getTransformation(j2, transformation, f2)) {
                this.f613g = true;
                s.a(this.e, this);
            }
            return true;
        }
    }

    static void a(Fragment fragment, d dVar, q.g gVar) {
        View view = fragment.K;
        ViewGroup viewGroup = fragment.J;
        viewGroup.startViewTransition(view);
        androidx.core.d.a aVar = new androidx.core.d.a();
        aVar.setOnCancelListener(new a(fragment));
        gVar.b(fragment, aVar);
        if (dVar.a != null) {
            e eVar = new e(dVar.a, viewGroup, view);
            fragment.a(fragment.K);
            eVar.setAnimationListener(new b(viewGroup, fragment, gVar, aVar));
            fragment.K.startAnimation(eVar);
            return;
        }
        Animator animator = dVar.b;
        fragment.a(animator);
        animator.addListener(new C0033c(viewGroup, view, fragment, gVar, aVar));
        animator.setTarget(fragment.K);
        animator.start();
    }

    private static int a(int i2, boolean z) {
        if (i2 == 4097) {
            return z ? R$anim.fragment_open_enter : R$anim.fragment_open_exit;
        }
        if (i2 == 4099) {
            return z ? R$anim.fragment_fade_enter : R$anim.fragment_fade_exit;
        }
        if (i2 != 8194) {
            return -1;
        }
        return z ? R$anim.fragment_close_enter : R$anim.fragment_close_exit;
    }
}
