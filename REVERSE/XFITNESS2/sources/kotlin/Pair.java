package kotlin;

import java.io.Serializable;
import kotlin.jvm.internal.i;

/* compiled from: Tuples.kt */
public final class Pair<A, B> implements Serializable {
    private final A first;
    private final B second;

    public Pair(A a, B b) {
        this.first = a;
        this.second = b;
    }

    public static /* synthetic */ Pair copy$default(Pair pair, A a, B b, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            a = pair.first;
        }
        if ((i2 & 2) != 0) {
            b = pair.second;
        }
        return pair.copy(a, b);
    }

    public final A component1() {
        return this.first;
    }

    public final B component2() {
        return this.second;
    }

    public final Pair<A, B> copy(A a, B b) {
        return new Pair<>(a, b);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) obj;
        return i.a((Object) this.first, (Object) pair.first) && i.a((Object) this.second, (Object) pair.second);
    }

    public final A getFirst() {
        return this.first;
    }

    public final B getSecond() {
        return this.second;
    }

    public int hashCode() {
        A a = this.first;
        int i2 = 0;
        int hashCode = (a != null ? a.hashCode() : 0) * 31;
        B b = this.second;
        if (b != null) {
            i2 = b.hashCode();
        }
        return hashCode + i2;
    }

    public String toString() {
        return '(' + this.first + ", " + this.second + ')';
    }
}
