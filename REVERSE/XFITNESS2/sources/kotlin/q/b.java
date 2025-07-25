package kotlin.q;

import java.util.NoSuchElementException;
import kotlin.collections.t;

/* compiled from: ProgressionIterators.kt */
public final class b extends t {
    private final int e;

    /* renamed from: f  reason: collision with root package name */
    private boolean f1781f;

    /* renamed from: g  reason: collision with root package name */
    private int f1782g;

    /* renamed from: h  reason: collision with root package name */
    private final int f1783h;

    public b(int i2, int i3, int i4) {
        this.f1783h = i4;
        this.e = i3;
        boolean z = true;
        if (i4 <= 0 ? i2 < i3 : i2 > i3) {
            z = false;
        }
        this.f1781f = z;
        this.f1782g = !z ? this.e : i2;
    }

    public int a() {
        int i2 = this.f1782g;
        if (i2 != this.e) {
            this.f1782g = this.f1783h + i2;
        } else if (this.f1781f) {
            this.f1781f = false;
        } else {
            throw new NoSuchElementException();
        }
        return i2;
    }

    public boolean hasNext() {
        return this.f1781f;
    }
}
