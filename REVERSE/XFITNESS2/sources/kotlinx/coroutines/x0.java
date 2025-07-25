package kotlinx.coroutines;

/* compiled from: JobSupport.kt */
final class x0 implements f1 {
    private final boolean e;

    public x0(boolean z) {
        this.e = z;
    }

    public u1 b() {
        return null;
    }

    public boolean isActive() {
        return this.e;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Empty{");
        sb.append(isActive() ? "Active" : "New");
        sb.append('}');
        return sb.toString();
    }
}
