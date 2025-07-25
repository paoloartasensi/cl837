package androidx.databinding;

import androidx.databinding.j;

/* compiled from: BaseObservableField */
abstract class b extends a {

    /* compiled from: BaseObservableField */
    class a extends j.a {
        a() {
        }

        public void a(j jVar, int i2) {
            b.this.notifyChange();
        }
    }

    public b() {
    }

    public b(j... jVarArr) {
        if (jVarArr != null && jVarArr.length != 0) {
            a aVar = new a();
            for (j addOnPropertyChangedCallback : jVarArr) {
                addOnPropertyChangedCallback.addOnPropertyChangedCallback(aVar);
            }
        }
    }
}
