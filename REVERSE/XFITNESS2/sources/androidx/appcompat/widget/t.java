package androidx.appcompat.widget;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import androidx.appcompat.view.menu.p;

/* compiled from: ForwardingListener */
public abstract class t implements View.OnTouchListener, View.OnAttachStateChangeListener {
    private final float e;

    /* renamed from: f  reason: collision with root package name */
    private final int f322f;

    /* renamed from: g  reason: collision with root package name */
    private final int f323g;

    /* renamed from: h  reason: collision with root package name */
    final View f324h;

    /* renamed from: i  reason: collision with root package name */
    private Runnable f325i;

    /* renamed from: j  reason: collision with root package name */
    private Runnable f326j;
    private boolean k;
    private int l;
    private final int[] m = new int[2];

    /* compiled from: ForwardingListener */
    private class a implements Runnable {
        a() {
        }

        public void run() {
            ViewParent parent = t.this.f324h.getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }
    }

    /* compiled from: ForwardingListener */
    private class b implements Runnable {
        b() {
        }

        public void run() {
            t.this.d();
        }
    }

    public t(View view) {
        this.f324h = view;
        view.setLongClickable(true);
        view.addOnAttachStateChangeListener(this);
        this.e = (float) ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
        int tapTimeout = ViewConfiguration.getTapTimeout();
        this.f322f = tapTimeout;
        this.f323g = (tapTimeout + ViewConfiguration.getLongPressTimeout()) / 2;
    }

    private boolean a(MotionEvent motionEvent) {
        r rVar;
        View view = this.f324h;
        p a2 = a();
        if (a2 == null || !a2.a() || (rVar = (r) a2.g()) == null || !rVar.isShown()) {
            return false;
        }
        MotionEvent obtainNoHistory = MotionEvent.obtainNoHistory(motionEvent);
        a(view, obtainNoHistory);
        b(rVar, obtainNoHistory);
        boolean a3 = rVar.a(obtainNoHistory, this.l);
        obtainNoHistory.recycle();
        int actionMasked = motionEvent.getActionMasked();
        boolean z = (actionMasked == 1 || actionMasked == 3) ? false : true;
        if (!a3 || !z) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0017, code lost:
        if (r1 != 3) goto L_0x006d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean b(android.view.MotionEvent r6) {
        /*
            r5 = this;
            android.view.View r0 = r5.f324h
            boolean r1 = r0.isEnabled()
            r2 = 0
            if (r1 != 0) goto L_0x000a
            return r2
        L_0x000a:
            int r1 = r6.getActionMasked()
            if (r1 == 0) goto L_0x0041
            r3 = 1
            if (r1 == r3) goto L_0x003d
            r4 = 2
            if (r1 == r4) goto L_0x001a
            r6 = 3
            if (r1 == r6) goto L_0x003d
            goto L_0x006d
        L_0x001a:
            int r1 = r5.l
            int r1 = r6.findPointerIndex(r1)
            if (r1 < 0) goto L_0x006d
            float r4 = r6.getX(r1)
            float r6 = r6.getY(r1)
            float r1 = r5.e
            boolean r6 = a(r0, r4, r6, r1)
            if (r6 != 0) goto L_0x006d
            r5.e()
            android.view.ViewParent r6 = r0.getParent()
            r6.requestDisallowInterceptTouchEvent(r3)
            return r3
        L_0x003d:
            r5.e()
            goto L_0x006d
        L_0x0041:
            int r6 = r6.getPointerId(r2)
            r5.l = r6
            java.lang.Runnable r6 = r5.f325i
            if (r6 != 0) goto L_0x0052
            androidx.appcompat.widget.t$a r6 = new androidx.appcompat.widget.t$a
            r6.<init>()
            r5.f325i = r6
        L_0x0052:
            java.lang.Runnable r6 = r5.f325i
            int r1 = r5.f322f
            long r3 = (long) r1
            r0.postDelayed(r6, r3)
            java.lang.Runnable r6 = r5.f326j
            if (r6 != 0) goto L_0x0065
            androidx.appcompat.widget.t$b r6 = new androidx.appcompat.widget.t$b
            r6.<init>()
            r5.f326j = r6
        L_0x0065:
            java.lang.Runnable r6 = r5.f326j
            int r1 = r5.f323g
            long r3 = (long) r1
            r0.postDelayed(r6, r3)
        L_0x006d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.t.b(android.view.MotionEvent):boolean");
    }

    private void e() {
        Runnable runnable = this.f326j;
        if (runnable != null) {
            this.f324h.removeCallbacks(runnable);
        }
        Runnable runnable2 = this.f325i;
        if (runnable2 != null) {
            this.f324h.removeCallbacks(runnable2);
        }
    }

    public abstract p a();

    /* access modifiers changed from: protected */
    public abstract boolean b();

    /* access modifiers changed from: protected */
    public boolean c() {
        p a2 = a();
        if (a2 == null || !a2.a()) {
            return true;
        }
        a2.dismiss();
        return true;
    }

    /* access modifiers changed from: package-private */
    public void d() {
        e();
        View view = this.f324h;
        if (view.isEnabled() && !view.isLongClickable() && b()) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            long uptimeMillis = SystemClock.uptimeMillis();
            MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
            view.onTouchEvent(obtain);
            obtain.recycle();
            this.k = true;
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean z;
        boolean z2 = this.k;
        if (z2) {
            z = a(motionEvent) || !c();
        } else {
            z = b(motionEvent) && b();
            if (z) {
                long uptimeMillis = SystemClock.uptimeMillis();
                MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
                this.f324h.onTouchEvent(obtain);
                obtain.recycle();
            }
        }
        this.k = z;
        if (z || z2) {
            return true;
        }
        return false;
    }

    public void onViewAttachedToWindow(View view) {
    }

    public void onViewDetachedFromWindow(View view) {
        this.k = false;
        this.l = -1;
        Runnable runnable = this.f325i;
        if (runnable != null) {
            this.f324h.removeCallbacks(runnable);
        }
    }

    private static boolean a(View view, float f2, float f3, float f4) {
        float f5 = -f4;
        return f2 >= f5 && f3 >= f5 && f2 < ((float) (view.getRight() - view.getLeft())) + f4 && f3 < ((float) (view.getBottom() - view.getTop())) + f4;
    }

    private boolean a(View view, MotionEvent motionEvent) {
        int[] iArr = this.m;
        view.getLocationOnScreen(iArr);
        motionEvent.offsetLocation((float) iArr[0], (float) iArr[1]);
        return true;
    }

    private boolean b(View view, MotionEvent motionEvent) {
        int[] iArr = this.m;
        view.getLocationOnScreen(iArr);
        motionEvent.offsetLocation((float) (-iArr[0]), (float) (-iArr[1]));
        return true;
    }
}
