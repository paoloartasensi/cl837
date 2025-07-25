package androidx.transition;

import android.animation.TimeInterpolator;
import android.util.AndroidRuntimeException;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.l;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: TransitionSet */
public class p extends l {
    private ArrayList<l> N = new ArrayList<>();
    private boolean O = true;
    int P;
    boolean Q = false;
    private int R = 0;

    /* compiled from: TransitionSet */
    class a extends m {
        final /* synthetic */ l a;

        a(p pVar, l lVar) {
            this.a = lVar;
        }

        public void d(l lVar) {
            this.a.p();
            lVar.b((l.f) this);
        }
    }

    /* compiled from: TransitionSet */
    static class b extends m {
        p a;

        b(p pVar) {
            this.a = pVar;
        }

        public void c(l lVar) {
            p pVar = this.a;
            if (!pVar.Q) {
                pVar.q();
                this.a.Q = true;
            }
        }

        public void d(l lVar) {
            p pVar = this.a;
            int i2 = pVar.P - 1;
            pVar.P = i2;
            if (i2 == 0) {
                pVar.Q = false;
                pVar.b();
            }
            lVar.b((l.f) this);
        }
    }

    private void s() {
        b bVar = new b(this);
        Iterator<l> it = this.N.iterator();
        while (it.hasNext()) {
            it.next().a((l.f) bVar);
        }
        this.P = this.N.size();
    }

    public void c(r rVar) {
        if (b(rVar.b)) {
            Iterator<l> it = this.N.iterator();
            while (it.hasNext()) {
                l next = it.next();
                if (next.b(rVar.b)) {
                    next.c(rVar);
                    rVar.c.add(next);
                }
            }
        }
    }

    public void e(View view) {
        super.e(view);
        int size = this.N.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.N.get(i2).e(view);
        }
    }

    /* access modifiers changed from: protected */
    public void p() {
        if (this.N.isEmpty()) {
            q();
            b();
            return;
        }
        s();
        if (!this.O) {
            for (int i2 = 1; i2 < this.N.size(); i2++) {
                this.N.get(i2 - 1).a((l.f) new a(this, this.N.get(i2)));
            }
            l lVar = this.N.get(0);
            if (lVar != null) {
                lVar.p();
                return;
            }
            return;
        }
        Iterator<l> it = this.N.iterator();
        while (it.hasNext()) {
            it.next().p();
        }
    }

    public int r() {
        return this.N.size();
    }

    public l clone() {
        p pVar = (p) super.clone();
        pVar.N = new ArrayList<>();
        int size = this.N.size();
        for (int i2 = 0; i2 < size; i2++) {
            pVar.a(this.N.get(i2).clone());
        }
        return pVar;
    }

    public p d(View view) {
        for (int i2 = 0; i2 < this.N.size(); i2++) {
            this.N.get(i2).d(view);
        }
        super.d(view);
        return this;
    }

    public p b(int i2) {
        if (i2 == 0) {
            this.O = true;
        } else if (i2 == 1) {
            this.O = false;
        } else {
            throw new AndroidRuntimeException("Invalid parameter for TransitionSet ordering: " + i2);
        }
        return this;
    }

    public p a(l lVar) {
        this.N.add(lVar);
        lVar.v = this;
        long j2 = this.f898g;
        if (j2 >= 0) {
            lVar.a(j2);
        }
        if ((this.R & 1) != 0) {
            lVar.a(f());
        }
        if ((this.R & 2) != 0) {
            lVar.a(i());
        }
        if ((this.R & 4) != 0) {
            lVar.a(h());
        }
        if ((this.R & 8) != 0) {
            lVar.a(e());
        }
        return this;
    }

    public p b(long j2) {
        super.b(j2);
        return this;
    }

    public void c(View view) {
        super.c(view);
        int size = this.N.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.N.get(i2).c(view);
        }
    }

    public p b(l.f fVar) {
        super.b(fVar);
        return this;
    }

    /* access modifiers changed from: package-private */
    public void b(r rVar) {
        super.b(rVar);
        int size = this.N.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.N.get(i2).b(rVar);
        }
    }

    public l a(int i2) {
        if (i2 < 0 || i2 >= this.N.size()) {
            return null;
        }
        return this.N.get(i2);
    }

    public p a(long j2) {
        super.a(j2);
        if (this.f898g >= 0) {
            int size = this.N.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.N.get(i2).a(j2);
            }
        }
        return this;
    }

    public p a(TimeInterpolator timeInterpolator) {
        this.R |= 1;
        ArrayList<l> arrayList = this.N;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.N.get(i2).a(timeInterpolator);
            }
        }
        super.a(timeInterpolator);
        return this;
    }

    public p a(View view) {
        for (int i2 = 0; i2 < this.N.size(); i2++) {
            this.N.get(i2).a(view);
        }
        super.a(view);
        return this;
    }

    public p a(l.f fVar) {
        super.a(fVar);
        return this;
    }

    public void a(g gVar) {
        super.a(gVar);
        this.R |= 4;
        for (int i2 = 0; i2 < this.N.size(); i2++) {
            this.N.get(i2).a(gVar);
        }
    }

    /* access modifiers changed from: protected */
    public void a(ViewGroup viewGroup, s sVar, s sVar2, ArrayList<r> arrayList, ArrayList<r> arrayList2) {
        long j2 = j();
        int size = this.N.size();
        for (int i2 = 0; i2 < size; i2++) {
            l lVar = this.N.get(i2);
            if (j2 > 0 && (this.O || i2 == 0)) {
                long j3 = lVar.j();
                if (j3 > 0) {
                    lVar.b(j3 + j2);
                } else {
                    lVar.b(j2);
                }
            }
            lVar.a(viewGroup, sVar, sVar2, arrayList, arrayList2);
        }
    }

    public void a(r rVar) {
        if (b(rVar.b)) {
            Iterator<l> it = this.N.iterator();
            while (it.hasNext()) {
                l next = it.next();
                if (next.b(rVar.b)) {
                    next.a(rVar);
                    rVar.c.add(next);
                }
            }
        }
    }

    public void a(o oVar) {
        super.a(oVar);
        this.R |= 2;
        int size = this.N.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.N.get(i2).a(oVar);
        }
    }

    public void a(l.e eVar) {
        super.a(eVar);
        this.R |= 8;
        int size = this.N.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.N.get(i2).a(eVar);
        }
    }

    /* access modifiers changed from: package-private */
    public String a(String str) {
        String a2 = super.a(str);
        for (int i2 = 0; i2 < this.N.size(); i2++) {
            StringBuilder sb = new StringBuilder();
            sb.append(a2);
            sb.append("\n");
            sb.append(this.N.get(i2).a(str + "  "));
            a2 = sb.toString();
        }
        return a2;
    }
}
