package okio;

import java.nio.ByteBuffer;

/* compiled from: RealBufferedSink */
final class m implements d {
    public final c e = new c();

    /* renamed from: f  reason: collision with root package name */
    public final q f2105f;

    /* renamed from: g  reason: collision with root package name */
    boolean f2106g;

    m(q qVar) {
        if (qVar != null) {
            this.f2105f = qVar;
            return;
        }
        throw new NullPointerException("sink == null");
    }

    public void a(c cVar, long j2) {
        if (!this.f2106g) {
            this.e.a(cVar, j2);
            f();
            return;
        }
        throw new IllegalStateException("closed");
    }

    public c b() {
        return this.e;
    }

    public void close() {
        if (!this.f2106g) {
            try {
                if (this.e.f2094f > 0) {
                    this.f2105f.a(this.e, this.e.f2094f);
                }
                th = null;
            } catch (Throwable th) {
                th = th;
            }
            try {
                this.f2105f.close();
            } catch (Throwable th2) {
                if (th == null) {
                    th = th2;
                }
            }
            this.f2106g = true;
            if (th != null) {
                t.a(th);
                throw null;
            }
        }
    }

    public s d() {
        return this.f2105f.d();
    }

    public d f(long j2) {
        if (!this.f2106g) {
            this.e.f(j2);
            f();
            return this;
        }
        throw new IllegalStateException("closed");
    }

    public void flush() {
        if (!this.f2106g) {
            c cVar = this.e;
            long j2 = cVar.f2094f;
            if (j2 > 0) {
                this.f2105f.a(cVar, j2);
            }
            this.f2105f.flush();
            return;
        }
        throw new IllegalStateException("closed");
    }

    public boolean isOpen() {
        return !this.f2106g;
    }

    public String toString() {
        return "buffer(" + this.f2105f + ")";
    }

    public d write(byte[] bArr) {
        if (!this.f2106g) {
            this.e.write(bArr);
            f();
            return this;
        }
        throw new IllegalStateException("closed");
    }

    public d writeByte(int i2) {
        if (!this.f2106g) {
            this.e.writeByte(i2);
            return f();
        }
        throw new IllegalStateException("closed");
    }

    public d writeInt(int i2) {
        if (!this.f2106g) {
            this.e.writeInt(i2);
            return f();
        }
        throw new IllegalStateException("closed");
    }

    public d writeShort(int i2) {
        if (!this.f2106g) {
            this.e.writeShort(i2);
            f();
            return this;
        }
        throw new IllegalStateException("closed");
    }

    public d a(ByteString byteString) {
        if (!this.f2106g) {
            this.e.a(byteString);
            f();
            return this;
        }
        throw new IllegalStateException("closed");
    }

    public d f() {
        if (!this.f2106g) {
            long m = this.e.m();
            if (m > 0) {
                this.f2105f.a(this.e, m);
            }
            return this;
        }
        throw new IllegalStateException("closed");
    }

    public d write(byte[] bArr, int i2, int i3) {
        if (!this.f2106g) {
            this.e.write(bArr, i2, i3);
            f();
            return this;
        }
        throw new IllegalStateException("closed");
    }

    public d a(String str) {
        if (!this.f2106g) {
            this.e.a(str);
            return f();
        }
        throw new IllegalStateException("closed");
    }

    public int write(ByteBuffer byteBuffer) {
        if (!this.f2106g) {
            int write = this.e.write(byteBuffer);
            f();
            return write;
        }
        throw new IllegalStateException("closed");
    }

    public d a(long j2) {
        if (!this.f2106g) {
            this.e.a(j2);
            return f();
        }
        throw new IllegalStateException("closed");
    }
}
