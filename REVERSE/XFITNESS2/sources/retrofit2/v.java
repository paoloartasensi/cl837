package retrofit2;

import java.lang.annotation.Annotation;

/* compiled from: SkipCallbackExecutorImpl */
final class v implements u {
    private static final u a = new v();

    v() {
    }

    static Annotation[] a(Annotation[] annotationArr) {
        if (w.a(annotationArr, (Class<? extends Annotation>) u.class)) {
            return annotationArr;
        }
        Annotation[] annotationArr2 = new Annotation[(annotationArr.length + 1)];
        annotationArr2[0] = a;
        System.arraycopy(annotationArr, 0, annotationArr2, 1, annotationArr.length);
        return annotationArr2;
    }

    public Class<? extends Annotation> annotationType() {
        return u.class;
    }

    public boolean equals(Object obj) {
        return obj instanceof u;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "@" + u.class.getName() + "()";
    }
}
