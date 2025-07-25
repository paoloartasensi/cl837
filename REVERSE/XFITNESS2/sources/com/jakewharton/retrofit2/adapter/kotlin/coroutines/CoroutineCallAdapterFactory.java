package com.jakewharton.retrofit2.adapter.kotlin.coroutines;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import kotlin.jvm.b.l;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.k1;
import kotlinx.coroutines.n0;
import kotlinx.coroutines.q;
import kotlinx.coroutines.s;
import retrofit2.HttpException;
import retrofit2.d;
import retrofit2.e;
import retrofit2.f;
import retrofit2.r;

/* compiled from: CoroutineCallAdapterFactory.kt */
public final class CoroutineCallAdapterFactory extends e.a {
    public static final a a = new a((f) null);

    /* compiled from: CoroutineCallAdapterFactory.kt */
    private static final class BodyCallAdapter<T> implements e<T, n0<? extends T>> {
        private final Type a;

        /* compiled from: CoroutineCallAdapterFactory.kt */
        public static final class a implements f<T> {
            final /* synthetic */ q a;

            a(q qVar) {
                this.a = qVar;
            }

            public void a(d<T> dVar, Throwable th) {
                i.b(dVar, "call");
                i.b(th, "t");
                this.a.a(th);
            }

            public void a(d<T> dVar, r<T> rVar) {
                i.b(dVar, "call");
                i.b(rVar, "response");
                if (rVar.c()) {
                    q qVar = this.a;
                    T a2 = rVar.a();
                    if (a2 != null) {
                        qVar.a(a2);
                    } else {
                        i.a();
                        throw null;
                    }
                } else {
                    this.a.a((Throwable) new HttpException(rVar));
                }
            }
        }

        public BodyCallAdapter(Type type) {
            i.b(type, "responseType");
            this.a = type;
        }

        public Type a() {
            return this.a;
        }

        public n0<T> a(d<T> dVar) {
            i.b(dVar, "call");
            q a2 = s.a((k1) null, 1, (Object) null);
            a2.a((l<? super Throwable, kotlin.l>) new CoroutineCallAdapterFactory$BodyCallAdapter$adapt$1(a2, dVar));
            dVar.a(new a(a2));
            return a2;
        }
    }

    /* compiled from: CoroutineCallAdapterFactory.kt */
    private static final class ResponseCallAdapter<T> implements e<T, n0<? extends r<T>>> {
        private final Type a;

        /* compiled from: CoroutineCallAdapterFactory.kt */
        public static final class a implements f<T> {
            final /* synthetic */ q a;

            a(q qVar) {
                this.a = qVar;
            }

            public void a(d<T> dVar, Throwable th) {
                i.b(dVar, "call");
                i.b(th, "t");
                this.a.a(th);
            }

            public void a(d<T> dVar, r<T> rVar) {
                i.b(dVar, "call");
                i.b(rVar, "response");
                this.a.a(rVar);
            }
        }

        public ResponseCallAdapter(Type type) {
            i.b(type, "responseType");
            this.a = type;
        }

        public Type a() {
            return this.a;
        }

        public n0<r<T>> a(d<T> dVar) {
            i.b(dVar, "call");
            q a2 = s.a((k1) null, 1, (Object) null);
            a2.a((l<? super Throwable, kotlin.l>) new CoroutineCallAdapterFactory$ResponseCallAdapter$adapt$1(a2, dVar));
            dVar.a(new a(a2));
            return a2;
        }
    }

    /* compiled from: CoroutineCallAdapterFactory.kt */
    public static final class a {
        private a() {
        }

        public final CoroutineCallAdapterFactory a() {
            return new CoroutineCallAdapterFactory((f) null);
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    private CoroutineCallAdapterFactory() {
    }

    public e<?, ?> a(Type type, Annotation[] annotationArr, retrofit2.s sVar) {
        i.b(type, "returnType");
        i.b(annotationArr, "annotations");
        i.b(sVar, "retrofit");
        if (!i.a((Object) n0.class, (Object) e.a.a(type))) {
            return null;
        }
        if (type instanceof ParameterizedType) {
            Type b = e.a.a(0, (ParameterizedType) type);
            if (!i.a((Object) e.a.a(b), (Object) r.class)) {
                i.a((Object) b, "responseType");
                return new BodyCallAdapter(b);
            } else if (b instanceof ParameterizedType) {
                Type b2 = e.a.a(0, (ParameterizedType) b);
                i.a((Object) b2, "getParameterUpperBound(0, responseType)");
                return new ResponseCallAdapter(b2);
            } else {
                throw new IllegalStateException("Response must be parameterized as Response<Foo> or Response<out Foo>");
            }
        } else {
            throw new IllegalStateException("Deferred return type must be parameterized as Deferred<Foo> or Deferred<out Foo>");
        }
    }

    public /* synthetic */ CoroutineCallAdapterFactory(f fVar) {
        this();
    }
}
