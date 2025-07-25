package androidx.core.h;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

/* compiled from: GestureDetectorCompat */
public final class c {
    private final a a;

    /* compiled from: GestureDetectorCompat */
    interface a {
        boolean a(MotionEvent motionEvent);

        void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener);
    }

    /* compiled from: GestureDetectorCompat */
    static class b implements a {
        private static final int v = ViewConfiguration.getLongPressTimeout();
        private static final int w = ViewConfiguration.getTapTimeout();
        private static final int x = ViewConfiguration.getDoubleTapTimeout();
        private int a;
        private int b;
        private int c;
        private int d;
        private final Handler e;

        /* renamed from: f  reason: collision with root package name */
        final GestureDetector.OnGestureListener f505f;

        /* renamed from: g  reason: collision with root package name */
        GestureDetector.OnDoubleTapListener f506g;

        /* renamed from: h  reason: collision with root package name */
        boolean f507h;

        /* renamed from: i  reason: collision with root package name */
        boolean f508i;

        /* renamed from: j  reason: collision with root package name */
        private boolean f509j;
        private boolean k;
        private boolean l;
        MotionEvent m;
        private MotionEvent n;
        private boolean o;
        private float p;
        private float q;
        private float r;
        private float s;
        private boolean t;
        private VelocityTracker u;

        b(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
            if (handler != null) {
                this.e = new a(handler);
            } else {
                this.e = new a();
            }
            this.f505f = onGestureListener;
            if (onGestureListener instanceof GestureDetector.OnDoubleTapListener) {
                setOnDoubleTapListener((GestureDetector.OnDoubleTapListener) onGestureListener);
            }
            a(context);
        }

        private void a(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null");
            } else if (this.f505f != null) {
                this.t = true;
                ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
                int scaledTouchSlop = viewConfiguration.getScaledTouchSlop();
                int scaledDoubleTapSlop = viewConfiguration.getScaledDoubleTapSlop();
                this.c = viewConfiguration.getScaledMinimumFlingVelocity();
                this.d = viewConfiguration.getScaledMaximumFlingVelocity();
                this.a = scaledTouchSlop * scaledTouchSlop;
                this.b = scaledDoubleTapSlop * scaledDoubleTapSlop;
            } else {
                throw new IllegalArgumentException("OnGestureListener must not be null");
            }
        }

        private void b() {
            this.e.removeMessages(1);
            this.e.removeMessages(2);
            this.e.removeMessages(3);
            this.u.recycle();
            this.u = null;
            this.o = false;
            this.f507h = false;
            this.k = false;
            this.l = false;
            this.f508i = false;
            if (this.f509j) {
                this.f509j = false;
            }
        }

        private void c() {
            this.e.removeMessages(1);
            this.e.removeMessages(2);
            this.e.removeMessages(3);
            this.o = false;
            this.k = false;
            this.l = false;
            this.f508i = false;
            if (this.f509j) {
                this.f509j = false;
            }
        }

        public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
            this.f506g = onDoubleTapListener;
        }

        /* compiled from: GestureDetectorCompat */
        private class a extends Handler {
            a() {
            }

            public void handleMessage(Message message) {
                int i2 = message.what;
                if (i2 == 1) {
                    b bVar = b.this;
                    bVar.f505f.onShowPress(bVar.m);
                } else if (i2 == 2) {
                    b.this.a();
                } else if (i2 == 3) {
                    b bVar2 = b.this;
                    GestureDetector.OnDoubleTapListener onDoubleTapListener = bVar2.f506g;
                    if (onDoubleTapListener == null) {
                        return;
                    }
                    if (!bVar2.f507h) {
                        onDoubleTapListener.onSingleTapConfirmed(bVar2.m);
                    } else {
                        bVar2.f508i = true;
                    }
                } else {
                    throw new RuntimeException("Unknown message " + message);
                }
            }

            a(Handler handler) {
                super(handler.getLooper());
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:100:0x0208  */
        /* JADX WARNING: Removed duplicated region for block: B:103:0x021f  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean a(android.view.MotionEvent r13) {
            /*
                r12 = this;
                int r0 = r13.getAction()
                android.view.VelocityTracker r1 = r12.u
                if (r1 != 0) goto L_0x000e
                android.view.VelocityTracker r1 = android.view.VelocityTracker.obtain()
                r12.u = r1
            L_0x000e:
                android.view.VelocityTracker r1 = r12.u
                r1.addMovement(r13)
                r0 = r0 & 255(0xff, float:3.57E-43)
                r1 = 6
                r2 = 1
                r3 = 0
                if (r0 != r1) goto L_0x001c
                r4 = 1
                goto L_0x001d
            L_0x001c:
                r4 = 0
            L_0x001d:
                if (r4 == 0) goto L_0x0024
                int r5 = r13.getActionIndex()
                goto L_0x0025
            L_0x0024:
                r5 = -1
            L_0x0025:
                int r6 = r13.getPointerCount()
                r7 = 0
                r8 = 0
                r9 = 0
                r10 = 0
            L_0x002d:
                if (r8 >= r6) goto L_0x003f
                if (r5 != r8) goto L_0x0032
                goto L_0x003c
            L_0x0032:
                float r11 = r13.getX(r8)
                float r9 = r9 + r11
                float r11 = r13.getY(r8)
                float r10 = r10 + r11
            L_0x003c:
                int r8 = r8 + 1
                goto L_0x002d
            L_0x003f:
                if (r4 == 0) goto L_0x0044
                int r4 = r6 + -1
                goto L_0x0045
            L_0x0044:
                r4 = r6
            L_0x0045:
                float r4 = (float) r4
                float r9 = r9 / r4
                float r10 = r10 / r4
                r4 = 2
                r5 = 3
                if (r0 == 0) goto L_0x01bf
                r8 = 1000(0x3e8, float:1.401E-42)
                if (r0 == r2) goto L_0x0131
                if (r0 == r4) goto L_0x00ba
                if (r0 == r5) goto L_0x00b5
                r2 = 5
                if (r0 == r2) goto L_0x00a8
                if (r0 == r1) goto L_0x005b
                goto L_0x024e
            L_0x005b:
                r12.p = r9
                r12.r = r9
                r12.q = r10
                r12.s = r10
                android.view.VelocityTracker r0 = r12.u
                int r1 = r12.d
                float r1 = (float) r1
                r0.computeCurrentVelocity(r8, r1)
                int r0 = r13.getActionIndex()
                int r1 = r13.getPointerId(r0)
                android.view.VelocityTracker r2 = r12.u
                float r2 = r2.getXVelocity(r1)
                android.view.VelocityTracker r4 = r12.u
                float r1 = r4.getYVelocity(r1)
                r4 = 0
            L_0x0080:
                if (r4 >= r6) goto L_0x024e
                if (r4 != r0) goto L_0x0085
                goto L_0x00a5
            L_0x0085:
                int r5 = r13.getPointerId(r4)
                android.view.VelocityTracker r8 = r12.u
                float r8 = r8.getXVelocity(r5)
                float r8 = r8 * r2
                android.view.VelocityTracker r9 = r12.u
                float r5 = r9.getYVelocity(r5)
                float r5 = r5 * r1
                float r8 = r8 + r5
                int r5 = (r8 > r7 ? 1 : (r8 == r7 ? 0 : -1))
                if (r5 >= 0) goto L_0x00a5
                android.view.VelocityTracker r13 = r12.u
                r13.clear()
                goto L_0x024e
            L_0x00a5:
                int r4 = r4 + 1
                goto L_0x0080
            L_0x00a8:
                r12.p = r9
                r12.r = r9
                r12.q = r10
                r12.s = r10
                r12.c()
                goto L_0x024e
            L_0x00b5:
                r12.b()
                goto L_0x024e
            L_0x00ba:
                boolean r0 = r12.f509j
                if (r0 == 0) goto L_0x00c0
                goto L_0x024e
            L_0x00c0:
                float r0 = r12.p
                float r0 = r0 - r9
                float r1 = r12.q
                float r1 = r1 - r10
                boolean r6 = r12.o
                if (r6 == 0) goto L_0x00d3
                android.view.GestureDetector$OnDoubleTapListener r0 = r12.f506g
                boolean r13 = r0.onDoubleTapEvent(r13)
                r3 = r3 | r13
                goto L_0x024e
            L_0x00d3:
                boolean r6 = r12.k
                if (r6 == 0) goto L_0x0111
                float r6 = r12.r
                float r6 = r9 - r6
                int r6 = (int) r6
                float r7 = r12.s
                float r7 = r10 - r7
                int r7 = (int) r7
                int r6 = r6 * r6
                int r7 = r7 * r7
                int r6 = r6 + r7
                int r7 = r12.a
                if (r6 <= r7) goto L_0x0108
                android.view.GestureDetector$OnGestureListener r7 = r12.f505f
                android.view.MotionEvent r8 = r12.m
                boolean r13 = r7.onScroll(r8, r13, r0, r1)
                r12.p = r9
                r12.q = r10
                r12.k = r3
                android.os.Handler r0 = r12.e
                r0.removeMessages(r5)
                android.os.Handler r0 = r12.e
                r0.removeMessages(r2)
                android.os.Handler r0 = r12.e
                r0.removeMessages(r4)
                goto L_0x0109
            L_0x0108:
                r13 = 0
            L_0x0109:
                int r0 = r12.a
                if (r6 <= r0) goto L_0x01bc
                r12.l = r3
                goto L_0x01bc
            L_0x0111:
                float r2 = java.lang.Math.abs(r0)
                r4 = 1065353216(0x3f800000, float:1.0)
                int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r2 >= 0) goto L_0x0123
                float r2 = java.lang.Math.abs(r1)
                int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r2 < 0) goto L_0x024e
            L_0x0123:
                android.view.GestureDetector$OnGestureListener r2 = r12.f505f
                android.view.MotionEvent r3 = r12.m
                boolean r3 = r2.onScroll(r3, r13, r0, r1)
                r12.p = r9
                r12.q = r10
                goto L_0x024e
            L_0x0131:
                r12.f507h = r3
                android.view.MotionEvent r0 = android.view.MotionEvent.obtain(r13)
                boolean r1 = r12.o
                if (r1 == 0) goto L_0x0143
                android.view.GestureDetector$OnDoubleTapListener r1 = r12.f506g
                boolean r13 = r1.onDoubleTapEvent(r13)
                r13 = r13 | r3
                goto L_0x019b
            L_0x0143:
                boolean r1 = r12.f509j
                if (r1 == 0) goto L_0x014f
                android.os.Handler r13 = r12.e
                r13.removeMessages(r5)
                r12.f509j = r3
                goto L_0x0191
            L_0x014f:
                boolean r1 = r12.k
                if (r1 == 0) goto L_0x0166
                android.view.GestureDetector$OnGestureListener r1 = r12.f505f
                boolean r1 = r1.onSingleTapUp(r13)
                boolean r5 = r12.f508i
                if (r5 == 0) goto L_0x0164
                android.view.GestureDetector$OnDoubleTapListener r5 = r12.f506g
                if (r5 == 0) goto L_0x0164
                r5.onSingleTapConfirmed(r13)
            L_0x0164:
                r13 = r1
                goto L_0x019b
            L_0x0166:
                android.view.VelocityTracker r1 = r12.u
                int r5 = r13.getPointerId(r3)
                int r6 = r12.d
                float r6 = (float) r6
                r1.computeCurrentVelocity(r8, r6)
                float r6 = r1.getYVelocity(r5)
                float r1 = r1.getXVelocity(r5)
                float r5 = java.lang.Math.abs(r6)
                int r7 = r12.c
                float r7 = (float) r7
                int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
                if (r5 > 0) goto L_0x0193
                float r5 = java.lang.Math.abs(r1)
                int r7 = r12.c
                float r7 = (float) r7
                int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
                if (r5 <= 0) goto L_0x0191
                goto L_0x0193
            L_0x0191:
                r13 = 0
                goto L_0x019b
            L_0x0193:
                android.view.GestureDetector$OnGestureListener r5 = r12.f505f
                android.view.MotionEvent r7 = r12.m
                boolean r13 = r5.onFling(r7, r13, r1, r6)
            L_0x019b:
                android.view.MotionEvent r1 = r12.n
                if (r1 == 0) goto L_0x01a2
                r1.recycle()
            L_0x01a2:
                r12.n = r0
                android.view.VelocityTracker r0 = r12.u
                if (r0 == 0) goto L_0x01ae
                r0.recycle()
                r0 = 0
                r12.u = r0
            L_0x01ae:
                r12.o = r3
                r12.f508i = r3
                android.os.Handler r0 = r12.e
                r0.removeMessages(r2)
                android.os.Handler r0 = r12.e
                r0.removeMessages(r4)
            L_0x01bc:
                r3 = r13
                goto L_0x024e
            L_0x01bf:
                android.view.GestureDetector$OnDoubleTapListener r0 = r12.f506g
                if (r0 == 0) goto L_0x01fb
                android.os.Handler r0 = r12.e
                boolean r0 = r0.hasMessages(r5)
                if (r0 == 0) goto L_0x01d0
                android.os.Handler r1 = r12.e
                r1.removeMessages(r5)
            L_0x01d0:
                android.view.MotionEvent r1 = r12.m
                if (r1 == 0) goto L_0x01f3
                android.view.MotionEvent r6 = r12.n
                if (r6 == 0) goto L_0x01f3
                if (r0 == 0) goto L_0x01f3
                boolean r0 = r12.a(r1, r6, r13)
                if (r0 == 0) goto L_0x01f3
                r12.o = r2
                android.view.GestureDetector$OnDoubleTapListener r0 = r12.f506g
                android.view.MotionEvent r1 = r12.m
                boolean r0 = r0.onDoubleTap(r1)
                r0 = r0 | r3
                android.view.GestureDetector$OnDoubleTapListener r1 = r12.f506g
                boolean r1 = r1.onDoubleTapEvent(r13)
                r0 = r0 | r1
                goto L_0x01fc
            L_0x01f3:
                android.os.Handler r0 = r12.e
                int r1 = x
                long r6 = (long) r1
                r0.sendEmptyMessageDelayed(r5, r6)
            L_0x01fb:
                r0 = 0
            L_0x01fc:
                r12.p = r9
                r12.r = r9
                r12.q = r10
                r12.s = r10
                android.view.MotionEvent r1 = r12.m
                if (r1 == 0) goto L_0x020b
                r1.recycle()
            L_0x020b:
                android.view.MotionEvent r1 = android.view.MotionEvent.obtain(r13)
                r12.m = r1
                r12.k = r2
                r12.l = r2
                r12.f507h = r2
                r12.f509j = r3
                r12.f508i = r3
                boolean r1 = r12.t
                if (r1 == 0) goto L_0x0237
                android.os.Handler r1 = r12.e
                r1.removeMessages(r4)
                android.os.Handler r1 = r12.e
                android.view.MotionEvent r3 = r12.m
                long r5 = r3.getDownTime()
                int r3 = w
                long r7 = (long) r3
                long r5 = r5 + r7
                int r3 = v
                long r7 = (long) r3
                long r5 = r5 + r7
                r1.sendEmptyMessageAtTime(r4, r5)
            L_0x0237:
                android.os.Handler r1 = r12.e
                android.view.MotionEvent r3 = r12.m
                long r3 = r3.getDownTime()
                int r5 = w
                long r5 = (long) r5
                long r3 = r3 + r5
                r1.sendEmptyMessageAtTime(r2, r3)
                android.view.GestureDetector$OnGestureListener r1 = r12.f505f
                boolean r13 = r1.onDown(r13)
                r3 = r0 | r13
            L_0x024e:
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.h.c.b.a(android.view.MotionEvent):boolean");
        }

        private boolean a(MotionEvent motionEvent, MotionEvent motionEvent2, MotionEvent motionEvent3) {
            if (!this.l || motionEvent3.getEventTime() - motionEvent2.getEventTime() > ((long) x)) {
                return false;
            }
            int x2 = ((int) motionEvent.getX()) - ((int) motionEvent3.getX());
            int y = ((int) motionEvent.getY()) - ((int) motionEvent3.getY());
            if ((x2 * x2) + (y * y) < this.b) {
                return true;
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.e.removeMessages(3);
            this.f508i = false;
            this.f509j = true;
            this.f505f.onLongPress(this.m);
        }
    }

    /* renamed from: androidx.core.h.c$c  reason: collision with other inner class name */
    /* compiled from: GestureDetectorCompat */
    static class C0025c implements a {
        private final GestureDetector a;

        C0025c(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
            this.a = new GestureDetector(context, onGestureListener, handler);
        }

        public boolean a(MotionEvent motionEvent) {
            return this.a.onTouchEvent(motionEvent);
        }

        public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
            this.a.setOnDoubleTapListener(onDoubleTapListener);
        }
    }

    public c(Context context, GestureDetector.OnGestureListener onGestureListener) {
        this(context, onGestureListener, (Handler) null);
    }

    public boolean a(MotionEvent motionEvent) {
        return this.a.a(motionEvent);
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        this.a.setOnDoubleTapListener(onDoubleTapListener);
    }

    public c(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
        if (Build.VERSION.SDK_INT > 17) {
            this.a = new C0025c(context, onGestureListener, handler);
        } else {
            this.a = new b(context, onGestureListener, handler);
        }
    }
}
