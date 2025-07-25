package retrofit2;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;
import okhttp3.f0;
import retrofit2.e;

/* compiled from: DefaultCallAdapterFactory */
final class i extends e.a {
    private final Executor a;

    /* compiled from: DefaultCallAdapterFactory */
    class a implements e<Object, d<?>> {
        final /* synthetic */ Type a;
        final /* synthetic */ Executor b;

        a(i iVar, Type type, Executor executor) {
            this.a = type;
            this.b = executor;
        }

        public Type a() {
            return this.a;
        }

        public d<Object> a(d<Object> dVar) {
            Executor executor = this.b;
            return executor == null ? dVar : new b(executor, dVar);
        }
    }

    /* compiled from: DefaultCallAdapterFactory */
    static final class b<T> implements d<T> {
        final Executor e;

        /* renamed from: f  reason: collision with root package name */
        final d<T> f2119f;

        /* compiled from: DefaultCallAdapterFactory */
        class a implements f<T> {
            final /* synthetic */ f a;

            a(f fVar) {
                this.a = fVar;
            }

            public void a(d<T> dVar, r<T> rVar) {
                b.this.e.execute(new a(this, this.a, rVar));
            }

            public /* synthetic */ void a(f fVar, r rVar) {
                if (b.this.f2119f.c()) {
                    fVar.a(b.this, (Throwable) new IOException("Canceled"));
                } else {
                    fVar.a(b.this, rVar);
                }
            }

            public /* synthetic */ void a(f fVar, Throwable th) {
                fVar.a(b.this, th);
            }

            public void a(d<T> dVar, Throwable th) {
                b.this.e.execute(new b(this, this.a, th));
            }
        }

        b(Executor executor, d<T> dVar) {
            this.e = executor;
            this.f2119f = dVar;
        }

        public void a(f<T> fVar) {
            d.a(fVar, "callback == null");
            this.f2119f.a(new a(fVar));
        }

        public boolean c() {
            return this.f2119f.c();
        }

        public void cancel() {
            this.f2119f.cancel();
        }

        public d<T> clone() {
            return new b(this.e, this.f2119f.clone());
        }

        public f0 a() {
            return this.f2119f.a();
        }
    }

    i(Executor executor) {
        this.a = executor;
    }

    public e<?, ?> a(Type type, Annotation[] annotationArr, s sVar) {
        Executor executor = null;
        if (e.a.a(type) != d.class) {
            return null;
        }
        if (type instanceof ParameterizedType) {
            Type b2 = w.b(0, (ParameterizedType) type);
            if (!w.a(annotationArr, (Class<? extends Annotation>) u.class)) {
                executor = this.a;
            }
            return new a(this, b2, executor);
        }
        throw new IllegalArgumentException("Call return type must be parameterized as Call<Foo> or Call<? extends Foo>");
    }
}
