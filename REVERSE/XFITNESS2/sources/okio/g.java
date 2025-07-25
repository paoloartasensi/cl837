package okio;

/* compiled from: ForwardingSource */
public abstract class g implements r {
    private final r e;

    public g(r rVar) {
        if (rVar != null) {
            this.e = rVar;
            return;
        }
        throw new IllegalArgumentException("delegate == null");
    }

    public final r a() {
        return this.e;
    }

    public long b(c cVar, long j2) {
        return this.e.b(cVar, j2);
    }

    public void close() {
        this.e.close();
    }

    public s d() {
        return this.e.d();
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.e.toString() + ")";
    }
}
