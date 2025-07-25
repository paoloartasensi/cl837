package okio;

/* compiled from: ForwardingSink */
public abstract class f implements q {
    private final q e;

    public f(q qVar) {
        if (qVar != null) {
            this.e = qVar;
            return;
        }
        throw new IllegalArgumentException("delegate == null");
    }

    public void a(c cVar, long j2) {
        this.e.a(cVar, j2);
    }

    public void close() {
        this.e.close();
    }

    public s d() {
        return this.e.d();
    }

    public void flush() {
        this.e.flush();
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.e.toString() + ")";
    }
}
