package h.a.a.a.i;

/* compiled from: TransformerHorizontalBarChart */
public class h extends g {
    public h(j jVar) {
        super(jVar);
    }

    public void a(boolean z) {
        this.b.reset();
        if (!z) {
            this.b.postTranslate(this.c.y(), this.c.k() - this.c.x());
            return;
        }
        this.b.setTranslate(-(this.c.l() - this.c.z()), this.c.k() - this.c.x());
        this.b.postScale(-1.0f, 1.0f);
    }
}
