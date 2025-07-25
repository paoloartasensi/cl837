package kotlin.q;

import kotlin.collections.t;
import kotlin.n.c;

/* compiled from: Progressions.kt */
public class a implements Iterable<Integer> {

    /* renamed from: h  reason: collision with root package name */
    public static final C0091a f1778h = new C0091a((f) null);
    private final int e;

    /* renamed from: f  reason: collision with root package name */
    private final int f1779f;

    /* renamed from: g  reason: collision with root package name */
    private final int f1780g;

    /* renamed from: kotlin.q.a$a  reason: collision with other inner class name */
    /* compiled from: Progressions.kt */
    public static final class C0091a {
        private C0091a() {
        }

        public final a a(int i2, int i3, int i4) {
            return new a(i2, i3, i4);
        }

        public /* synthetic */ C0091a(f fVar) {
            this();
        }
    }

    public a(int i2, int i3, int i4) {
        if (i4 == 0) {
            throw new IllegalArgumentException("Step must be non-zero.");
        } else if (i4 != Integer.MIN_VALUE) {
            this.e = i2;
            this.f1779f = c.b(i2, i3, i4);
            this.f1780g = i4;
        } else {
            throw new IllegalArgumentException("Step must be greater than Int.MIN_VALUE to avoid overflow on negation.");
        }
    }

    public final int a() {
        return this.e;
    }

    public final int b() {
        return this.f1779f;
    }

    public final int c() {
        return this.f1780g;
    }

    public boolean equals(Object obj) {
        if (obj instanceof a) {
            if (!isEmpty() || !((a) obj).isEmpty()) {
                a aVar = (a) obj;
                if (!(this.e == aVar.e && this.f1779f == aVar.f1779f && this.f1780g == aVar.f1780g)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (((this.e * 31) + this.f1779f) * 31) + this.f1780g;
    }

    public boolean isEmpty() {
        if (this.f1780g > 0) {
            if (this.e > this.f1779f) {
                return true;
            }
        } else if (this.e < this.f1779f) {
            return true;
        }
        return false;
    }

    public String toString() {
        int i2;
        StringBuilder sb;
        if (this.f1780g > 0) {
            sb = new StringBuilder();
            sb.append(this.e);
            sb.append("..");
            sb.append(this.f1779f);
            sb.append(" step ");
            i2 = this.f1780g;
        } else {
            sb = new StringBuilder();
            sb.append(this.e);
            sb.append(" downTo ");
            sb.append(this.f1779f);
            sb.append(" step ");
            i2 = -this.f1780g;
        }
        sb.append(i2);
        return sb.toString();
    }

    public t iterator() {
        return new b(this.e, this.f1779f, this.f1780g);
    }
}
