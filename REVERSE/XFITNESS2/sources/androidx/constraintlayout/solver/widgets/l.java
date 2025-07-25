package androidx.constraintlayout.solver.widgets;

/* compiled from: ResolutionDimension */
public class l extends m {
    float c = 0.0f;

    public void a(int i2) {
        if (this.b == 0 || this.c != ((float) i2)) {
            this.c = (float) i2;
            if (this.b == 1) {
                b();
            }
            a();
        }
    }

    public void d() {
        super.d();
        this.c = 0.0f;
    }

    public void f() {
        this.b = 2;
    }
}
