package androidx.loader.b;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: Loader */
public class a<D> {
    int a;
    b<D> b;
    C0036a<D> c;
    boolean d;
    boolean e;

    /* renamed from: f  reason: collision with root package name */
    boolean f664f;

    /* renamed from: g  reason: collision with root package name */
    boolean f665g;

    /* renamed from: h  reason: collision with root package name */
    boolean f666h;

    /* renamed from: androidx.loader.b.a$a  reason: collision with other inner class name */
    /* compiled from: Loader */
    public interface C0036a<D> {
    }

    /* compiled from: Loader */
    public interface b<D> {
    }

    public void a() {
        this.e = true;
        c();
    }

    public boolean b() {
        return d();
    }

    /* access modifiers changed from: protected */
    public void c() {
    }

    /* access modifiers changed from: protected */
    public boolean d() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void e() {
    }

    /* access modifiers changed from: protected */
    public void f() {
    }

    /* access modifiers changed from: protected */
    public void g() {
    }

    public void h() {
        e();
        this.f664f = true;
        this.d = false;
        this.e = false;
        this.f665g = false;
        this.f666h = false;
    }

    public final void i() {
        this.d = true;
        this.f664f = false;
        this.e = false;
        f();
    }

    public void j() {
        this.d = false;
        g();
    }

    public void registerOnLoadCanceledListener(C0036a<D> aVar) {
        if (this.c == null) {
            this.c = aVar;
            return;
        }
        throw new IllegalStateException("There is already a listener registered");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        androidx.core.g.a.a(this, sb);
        sb.append(" id=");
        sb.append(this.a);
        sb.append("}");
        return sb.toString();
    }

    public void unregisterListener(b<D> bVar) {
        b<D> bVar2 = this.b;
        if (bVar2 == null) {
            throw new IllegalStateException("No listener register");
        } else if (bVar2 == bVar) {
            this.b = null;
        } else {
            throw new IllegalArgumentException("Attempting to unregister the wrong listener");
        }
    }

    public void unregisterOnLoadCanceledListener(C0036a<D> aVar) {
        C0036a<D> aVar2 = this.c;
        if (aVar2 == null) {
            throw new IllegalStateException("No listener register");
        } else if (aVar2 == aVar) {
            this.c = null;
        } else {
            throw new IllegalArgumentException("Attempting to unregister the wrong listener");
        }
    }

    public String a(D d2) {
        StringBuilder sb = new StringBuilder(64);
        androidx.core.g.a.a(d2, sb);
        sb.append("}");
        return sb.toString();
    }

    @Deprecated
    public void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print(str);
        printWriter.print("mId=");
        printWriter.print(this.a);
        printWriter.print(" mListener=");
        printWriter.println(this.b);
        if (this.d || this.f665g || this.f666h) {
            printWriter.print(str);
            printWriter.print("mStarted=");
            printWriter.print(this.d);
            printWriter.print(" mContentChanged=");
            printWriter.print(this.f665g);
            printWriter.print(" mProcessingChange=");
            printWriter.println(this.f666h);
        }
        if (this.e || this.f664f) {
            printWriter.print(str);
            printWriter.print("mAbandoned=");
            printWriter.print(this.e);
            printWriter.print(" mReset=");
            printWriter.println(this.f664f);
        }
    }
}
