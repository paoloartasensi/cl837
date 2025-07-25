package com.google.android.material.snackbar;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;

/* compiled from: SnackbarManager */
class d {
    private static d e;
    private final Object a = new Object();
    private final Handler b = new Handler(Looper.getMainLooper(), new a());
    private c c;
    private c d;

    /* compiled from: SnackbarManager */
    class a implements Handler.Callback {
        a() {
        }

        public boolean handleMessage(Message message) {
            if (message.what != 0) {
                return false;
            }
            d.this.a((c) message.obj);
            return true;
        }
    }

    /* compiled from: SnackbarManager */
    interface b {
        void a(int i2);
    }

    /* compiled from: SnackbarManager */
    private static class c {
        final WeakReference<b> a;
        int b;
        boolean c;

        /* access modifiers changed from: package-private */
        public boolean a(b bVar) {
            return bVar != null && this.a.get() == bVar;
        }
    }

    private d() {
    }

    static d a() {
        if (e == null) {
            e = new d();
        }
        return e;
    }

    private boolean c(b bVar) {
        c cVar = this.c;
        return cVar != null && cVar.a(bVar);
    }

    public void b(b bVar) {
        synchronized (this.a) {
            if (c(bVar) && this.c.c) {
                this.c.c = false;
                b(this.c);
            }
        }
    }

    public void a(b bVar) {
        synchronized (this.a) {
            if (c(bVar) && !this.c.c) {
                this.c.c = true;
                this.b.removeCallbacksAndMessages(this.c);
            }
        }
    }

    private void b(c cVar) {
        int i2 = cVar.b;
        if (i2 != -2) {
            if (i2 <= 0) {
                i2 = i2 == -1 ? 1500 : 2750;
            }
            this.b.removeCallbacksAndMessages(cVar);
            Handler handler = this.b;
            handler.sendMessageDelayed(Message.obtain(handler, 0, cVar), (long) i2);
        }
    }

    private boolean a(c cVar, int i2) {
        b bVar = (b) cVar.a.get();
        if (bVar == null) {
            return false;
        }
        this.b.removeCallbacksAndMessages(cVar);
        bVar.a(i2);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void a(c cVar) {
        synchronized (this.a) {
            if (this.c == cVar || this.d == cVar) {
                a(cVar, 2);
            }
        }
    }
}
