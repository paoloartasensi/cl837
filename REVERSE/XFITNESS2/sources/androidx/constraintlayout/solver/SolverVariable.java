package androidx.constraintlayout.solver;

import com.jeremyliao.liveeventbus.BuildConfig;
import java.util.Arrays;

public class SolverVariable {
    private static int k = 1;
    private String a;
    public int b = -1;
    int c = -1;
    public int d = 0;
    public float e;

    /* renamed from: f  reason: collision with root package name */
    float[] f355f = new float[7];

    /* renamed from: g  reason: collision with root package name */
    Type f356g;

    /* renamed from: h  reason: collision with root package name */
    b[] f357h = new b[8];

    /* renamed from: i  reason: collision with root package name */
    int f358i = 0;

    /* renamed from: j  reason: collision with root package name */
    public int f359j = 0;

    public enum Type {
        UNRESTRICTED,
        CONSTANT,
        SLACK,
        ERROR,
        UNKNOWN
    }

    public SolverVariable(Type type, String str) {
        this.f356g = type;
    }

    static void b() {
        k++;
    }

    public final void a(b bVar) {
        int i2 = 0;
        while (true) {
            int i3 = this.f358i;
            if (i2 >= i3) {
                b[] bVarArr = this.f357h;
                if (i3 >= bVarArr.length) {
                    this.f357h = (b[]) Arrays.copyOf(bVarArr, bVarArr.length * 2);
                }
                b[] bVarArr2 = this.f357h;
                int i4 = this.f358i;
                bVarArr2[i4] = bVar;
                this.f358i = i4 + 1;
                return;
            } else if (this.f357h[i2] != bVar) {
                i2++;
            } else {
                return;
            }
        }
    }

    public final void c(b bVar) {
        int i2 = this.f358i;
        for (int i3 = 0; i3 < i2; i3++) {
            b[] bVarArr = this.f357h;
            bVarArr[i3].d.a(bVarArr[i3], bVar, false);
        }
        this.f358i = 0;
    }

    public String toString() {
        return BuildConfig.FLAVOR + this.a;
    }

    public final void b(b bVar) {
        int i2 = this.f358i;
        for (int i3 = 0; i3 < i2; i3++) {
            if (this.f357h[i3] == bVar) {
                for (int i4 = 0; i4 < (i2 - i3) - 1; i4++) {
                    b[] bVarArr = this.f357h;
                    int i5 = i3 + i4;
                    bVarArr[i5] = bVarArr[i5 + 1];
                }
                this.f358i--;
                return;
            }
        }
    }

    public void a() {
        this.a = null;
        this.f356g = Type.UNKNOWN;
        this.d = 0;
        this.b = -1;
        this.c = -1;
        this.e = 0.0f;
        this.f358i = 0;
        this.f359j = 0;
    }

    public void a(Type type, String str) {
        this.f356g = type;
    }
}
