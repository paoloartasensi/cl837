package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import retrofit2.e;

@IgnoreJRERequirement
/* compiled from: CompletableFutureCallAdapterFactory */
final class g extends e.a {
    static final e.a a = new g();

    @IgnoreJRERequirement
    /* compiled from: CompletableFutureCallAdapterFactory */
    private static final class a<R> implements e<R, CompletableFuture<R>> {
        private final Type a;

        a(Type type) {
            this.a = type;
        }

        public Type a() {
            return this.a;
        }

        /* renamed from: retrofit2.g$a$a  reason: collision with other inner class name */
        /* compiled from: CompletableFutureCallAdapterFactory */
        class C0101a implements f<R> {
            final /* synthetic */ CompletableFuture a;

            C0101a(a aVar, CompletableFuture completableFuture) {
                this.a = completableFuture;
            }

            public void a(d<R> dVar, r<R> rVar) {
                if (rVar.c()) {
                    this.a.complete(rVar.a());
                } else {
                    this.a.completeExceptionally(new HttpException(rVar));
                }
            }

            public void a(d<R> dVar, Throwable th) {
                this.a.completeExceptionally(th);
            }
        }

        public CompletableFuture<R> a(d<R> dVar) {
            b bVar = new b(dVar);
            dVar.a(new C0101a(this, bVar));
            return bVar;
        }
    }

    /* compiled from: CompletableFutureCallAdapterFactory */
    private static final class b<T> extends CompletableFuture<T> {
        private final d<?> e;

        b(d<?> dVar) {
            this.e = dVar;
        }

        public boolean cancel(boolean z) {
            if (z) {
                this.e.cancel();
            }
            return super.cancel(z);
        }
    }

    @IgnoreJRERequirement
    /* compiled from: CompletableFutureCallAdapterFactory */
    private static final class c<R> implements e<R, CompletableFuture<r<R>>> {
        private final Type a;

        /* compiled from: CompletableFutureCallAdapterFactory */
        class a implements f<R> {
            final /* synthetic */ CompletableFuture a;

            a(c cVar, CompletableFuture completableFuture) {
                this.a = completableFuture;
            }

            public void a(d<R> dVar, r<R> rVar) {
                this.a.complete(rVar);
            }

            public void a(d<R> dVar, Throwable th) {
                this.a.completeExceptionally(th);
            }
        }

        c(Type type) {
            this.a = type;
        }

        public Type a() {
            return this.a;
        }

        public CompletableFuture<r<R>> a(d<R> dVar) {
            b bVar = new b(dVar);
            dVar.a(new a(this, bVar));
            return bVar;
        }
    }

    g() {
    }

    public e<?, ?> a(Type type, Annotation[] annotationArr, s sVar) {
        if (e.a.a(type) != CompletableFuture.class) {
            return null;
        }
        if (type instanceof ParameterizedType) {
            Type a2 = e.a.a(0, (ParameterizedType) type);
            if (e.a.a(a2) != r.class) {
                return new a(a2);
            }
            if (a2 instanceof ParameterizedType) {
                return new c(e.a.a(0, (ParameterizedType) a2));
            }
            throw new IllegalStateException("Response must be parameterized as Response<Foo> or Response<? extends Foo>");
        }
        throw new IllegalStateException("CompletableFuture return type must be parameterized as CompletableFuture<Foo> or CompletableFuture<? extends Foo>");
    }
}
