package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.a;
import androidx.transition.l;

/* compiled from: Visibility */
public abstract class h0 extends l {
    private static final String[] O = {"android:visibility:visibility", "android:visibility:parent"};
    private int N = 3;

    /* compiled from: Visibility */
    class a extends AnimatorListenerAdapter {
        final /* synthetic */ v a;
        final /* synthetic */ View b;

        a(h0 h0Var, v vVar, View view) {
            this.a = vVar;
            this.b = view;
        }

        public void onAnimationEnd(Animator animator) {
            this.a.b(this.b);
        }
    }

    /* compiled from: Visibility */
    private static class b extends AnimatorListenerAdapter implements l.f, a.C0048a {
        private final View a;
        private final int b;
        private final ViewGroup c;
        private final boolean d;
        private boolean e;

        /* renamed from: f  reason: collision with root package name */
        boolean f895f = false;

        b(View view, int i2, boolean z) {
            this.a = view;
            this.b = i2;
            this.c = (ViewGroup) view.getParent();
            this.d = z;
            a(true);
        }

        public void a(l lVar) {
            a(false);
        }

        public void b(l lVar) {
            a(true);
        }

        public void c(l lVar) {
        }

        public void d(l lVar) {
            a();
            lVar.b((l.f) this);
        }

        public void onAnimationCancel(Animator animator) {
            this.f895f = true;
        }

        public void onAnimationEnd(Animator animator) {
            a();
        }

        public void onAnimationPause(Animator animator) {
            if (!this.f895f) {
                c0.a(this.a, this.b);
            }
        }

        public void onAnimationRepeat(Animator animator) {
        }

        public void onAnimationResume(Animator animator) {
            if (!this.f895f) {
                c0.a(this.a, 0);
            }
        }

        public void onAnimationStart(Animator animator) {
        }

        private void a() {
            if (!this.f895f) {
                c0.a(this.a, this.b);
                ViewGroup viewGroup = this.c;
                if (viewGroup != null) {
                    viewGroup.invalidate();
                }
            }
            a(false);
        }

        private void a(boolean z) {
            ViewGroup viewGroup;
            if (this.d && this.e != z && (viewGroup = this.c) != null) {
                this.e = z;
                w.a(viewGroup, z);
            }
        }
    }

    /* compiled from: Visibility */
    private static class c {
        boolean a;
        boolean b;
        int c;
        int d;
        ViewGroup e;

        /* renamed from: f  reason: collision with root package name */
        ViewGroup f896f;

        c() {
        }
    }

    private c b(r rVar, r rVar2) {
        c cVar = new c();
        cVar.a = false;
        cVar.b = false;
        if (rVar == null || !rVar.a.containsKey("android:visibility:visibility")) {
            cVar.c = -1;
            cVar.e = null;
        } else {
            cVar.c = ((Integer) rVar.a.get("android:visibility:visibility")).intValue();
            cVar.e = (ViewGroup) rVar.a.get("android:visibility:parent");
        }
        if (rVar2 == null || !rVar2.a.containsKey("android:visibility:visibility")) {
            cVar.d = -1;
            cVar.f896f = null;
        } else {
            cVar.d = ((Integer) rVar2.a.get("android:visibility:visibility")).intValue();
            cVar.f896f = (ViewGroup) rVar2.a.get("android:visibility:parent");
        }
        if (rVar == null || rVar2 == null) {
            if (rVar == null && cVar.d == 0) {
                cVar.b = true;
                cVar.a = true;
            } else if (rVar2 == null && cVar.c == 0) {
                cVar.b = false;
                cVar.a = true;
            }
        } else if (cVar.c == cVar.d && cVar.e == cVar.f896f) {
            return cVar;
        } else {
            int i2 = cVar.c;
            int i3 = cVar.d;
            if (i2 != i3) {
                if (i2 == 0) {
                    cVar.b = false;
                    cVar.a = true;
                } else if (i3 == 0) {
                    cVar.b = true;
                    cVar.a = true;
                }
            } else if (cVar.f896f == null) {
                cVar.b = false;
                cVar.a = true;
            } else if (cVar.e == null) {
                cVar.b = true;
                cVar.a = true;
            }
        }
        return cVar;
    }

    private void d(r rVar) {
        rVar.a.put("android:visibility:visibility", Integer.valueOf(rVar.b.getVisibility()));
        rVar.a.put("android:visibility:parent", rVar.b.getParent());
        int[] iArr = new int[2];
        rVar.b.getLocationOnScreen(iArr);
        rVar.a.put("android:visibility:screenLocation", iArr);
    }

    public abstract Animator a(ViewGroup viewGroup, View view, r rVar, r rVar2);

    public void a(int i2) {
        if ((i2 & -4) == 0) {
            this.N = i2;
            return;
        }
        throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
    }

    public abstract Animator b(ViewGroup viewGroup, View view, r rVar, r rVar2);

    public void c(r rVar) {
        d(rVar);
    }

    public String[] o() {
        return O;
    }

    public void a(r rVar) {
        d(rVar);
    }

    public Animator a(ViewGroup viewGroup, r rVar, r rVar2) {
        c b2 = b(rVar, rVar2);
        if (!b2.a) {
            return null;
        }
        if (b2.e == null && b2.f896f == null) {
            return null;
        }
        if (b2.b) {
            return a(viewGroup, rVar, b2.c, rVar2, b2.d);
        }
        return b(viewGroup, rVar, b2.c, rVar2, b2.d);
    }

    public Animator a(ViewGroup viewGroup, r rVar, int i2, r rVar2, int i3) {
        if ((this.N & 1) != 1 || rVar2 == null) {
            return null;
        }
        if (rVar == null) {
            View view = (View) rVar2.b.getParent();
            if (b(a(view, false), b(view, false)).a) {
                return null;
            }
        }
        return a(viewGroup, rVar2.b, rVar, rVar2);
    }

    public boolean a(r rVar, r rVar2) {
        if (rVar == null && rVar2 == null) {
            return false;
        }
        if (rVar != null && rVar2 != null && rVar2.a.containsKey("android:visibility:visibility") != rVar.a.containsKey("android:visibility:visibility")) {
            return false;
        }
        c b2 = b(rVar, rVar2);
        if (!b2.a) {
            return false;
        }
        if (b2.c == 0 || b2.d == 0) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x0087 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00ee A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.animation.Animator b(android.view.ViewGroup r7, androidx.transition.r r8, int r9, androidx.transition.r r10, int r11) {
        /*
            r6 = this;
            int r9 = r6.N
            r0 = 2
            r9 = r9 & r0
            r1 = 0
            if (r9 == r0) goto L_0x0008
            return r1
        L_0x0008:
            if (r8 == 0) goto L_0x000d
            android.view.View r9 = r8.b
            goto L_0x000e
        L_0x000d:
            r9 = r1
        L_0x000e:
            if (r10 == 0) goto L_0x0013
            android.view.View r2 = r10.b
            goto L_0x0014
        L_0x0013:
            r2 = r1
        L_0x0014:
            r3 = 1
            if (r2 == 0) goto L_0x0037
            android.view.ViewParent r4 = r2.getParent()
            if (r4 != 0) goto L_0x001e
            goto L_0x0037
        L_0x001e:
            r4 = 4
            if (r11 != r4) goto L_0x0022
            goto L_0x0024
        L_0x0022:
            if (r9 != r2) goto L_0x0027
        L_0x0024:
            r9 = r1
            goto L_0x0084
        L_0x0027:
            boolean r2 = r6.z
            if (r2 == 0) goto L_0x002c
            goto L_0x0044
        L_0x002c:
            android.view.ViewParent r2 = r9.getParent()
            android.view.View r2 = (android.view.View) r2
            android.view.View r9 = androidx.transition.q.a(r7, r9, r2)
            goto L_0x003a
        L_0x0037:
            if (r2 == 0) goto L_0x003c
            r9 = r2
        L_0x003a:
            r2 = r1
            goto L_0x0084
        L_0x003c:
            if (r9 == 0) goto L_0x0082
            android.view.ViewParent r2 = r9.getParent()
            if (r2 != 0) goto L_0x0045
        L_0x0044:
            goto L_0x003a
        L_0x0045:
            android.view.ViewParent r2 = r9.getParent()
            boolean r2 = r2 instanceof android.view.View
            if (r2 == 0) goto L_0x0082
            android.view.ViewParent r2 = r9.getParent()
            android.view.View r2 = (android.view.View) r2
            androidx.transition.r r4 = r6.b((android.view.View) r2, (boolean) r3)
            androidx.transition.r r5 = r6.a((android.view.View) r2, (boolean) r3)
            androidx.transition.h0$c r4 = r6.b(r4, r5)
            boolean r4 = r4.a
            if (r4 != 0) goto L_0x0068
            android.view.View r9 = androidx.transition.q.a(r7, r9, r2)
            goto L_0x003a
        L_0x0068:
            android.view.ViewParent r4 = r2.getParent()
            if (r4 != 0) goto L_0x0080
            int r2 = r2.getId()
            r4 = -1
            if (r2 == r4) goto L_0x0080
            android.view.View r2 = r7.findViewById(r2)
            if (r2 == 0) goto L_0x0080
            boolean r2 = r6.z
            if (r2 == 0) goto L_0x0080
            goto L_0x003a
        L_0x0080:
            r9 = r1
            goto L_0x003a
        L_0x0082:
            r9 = r1
            r2 = r9
        L_0x0084:
            r4 = 0
            if (r9 == 0) goto L_0x00cc
            if (r8 == 0) goto L_0x00cc
            java.util.Map<java.lang.String, java.lang.Object> r11 = r8.a
            java.lang.String r1 = "android:visibility:screenLocation"
            java.lang.Object r11 = r11.get(r1)
            int[] r11 = (int[]) r11
            r1 = r11[r4]
            r11 = r11[r3]
            int[] r0 = new int[r0]
            r7.getLocationOnScreen(r0)
            r2 = r0[r4]
            int r1 = r1 - r2
            int r2 = r9.getLeft()
            int r1 = r1 - r2
            r9.offsetLeftAndRight(r1)
            r0 = r0[r3]
            int r11 = r11 - r0
            int r0 = r9.getTop()
            int r11 = r11 - r0
            r9.offsetTopAndBottom(r11)
            androidx.transition.v r11 = androidx.transition.w.a(r7)
            r11.a(r9)
            android.animation.Animator r7 = r6.b(r7, r9, r8, r10)
            if (r7 != 0) goto L_0x00c3
            r11.b(r9)
            goto L_0x00cb
        L_0x00c3:
            androidx.transition.h0$a r8 = new androidx.transition.h0$a
            r8.<init>(r6, r11, r9)
            r7.addListener(r8)
        L_0x00cb:
            return r7
        L_0x00cc:
            if (r2 == 0) goto L_0x00ee
            int r9 = r2.getVisibility()
            androidx.transition.c0.a((android.view.View) r2, (int) r4)
            android.animation.Animator r7 = r6.b(r7, r2, r8, r10)
            if (r7 == 0) goto L_0x00ea
            androidx.transition.h0$b r8 = new androidx.transition.h0$b
            r8.<init>(r2, r11, r3)
            r7.addListener(r8)
            androidx.transition.a.a(r7, r8)
            r6.a((androidx.transition.l.f) r8)
            goto L_0x00ed
        L_0x00ea:
            androidx.transition.c0.a((android.view.View) r2, (int) r9)
        L_0x00ed:
            return r7
        L_0x00ee:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.transition.h0.b(android.view.ViewGroup, androidx.transition.r, int, androidx.transition.r, int):android.animation.Animator");
    }
}
