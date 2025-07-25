package retrofit2;

import java.lang.reflect.Method;
import kotlin.KotlinNullPointerException;
import kotlin.Result;
import kotlin.coroutines.jvm.internal.e;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.h;

/* compiled from: KotlinExtensions.kt */
public final class KotlinExtensions {

    /* compiled from: KotlinExtensions.kt */
    public static final class c implements f<T> {
        final /* synthetic */ h a;

        c(h hVar) {
            this.a = hVar;
        }

        public void a(d<T> dVar, r<T> rVar) {
            i.b(dVar, "call");
            i.b(rVar, "response");
            h hVar = this.a;
            Result.a aVar = Result.Companion;
            hVar.resumeWith(Result.m1constructorimpl(rVar));
        }

        public void a(d<T> dVar, Throwable th) {
            i.b(dVar, "call");
            i.b(th, "t");
            h hVar = this.a;
            Result.a aVar = Result.Companion;
            hVar.resumeWith(Result.m1constructorimpl(kotlin.i.a(th)));
        }
    }

    /* compiled from: KotlinExtensions.kt */
    static final class d implements Runnable {
        final /* synthetic */ kotlin.coroutines.c e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ Exception f2114f;

        d(kotlin.coroutines.c cVar, Exception exc) {
            this.e = cVar;
            this.f2114f = exc;
        }

        public final void run() {
            kotlin.coroutines.c a = IntrinsicsKt__IntrinsicsJvmKt.a(this.e);
            Exception exc = this.f2114f;
            Result.a aVar = Result.Companion;
            a.resumeWith(Result.m1constructorimpl(kotlin.i.a((Throwable) exc)));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object a(java.lang.Exception r4, kotlin.coroutines.c<?> r5) {
        /*
            boolean r0 = r5 instanceof retrofit2.KotlinExtensions$suspendAndThrow$1
            if (r0 == 0) goto L_0x0013
            r0 = r5
            retrofit2.KotlinExtensions$suspendAndThrow$1 r0 = (retrofit2.KotlinExtensions$suspendAndThrow$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            retrofit2.KotlinExtensions$suspendAndThrow$1 r0 = new retrofit2.KotlinExtensions$suspendAndThrow$1
            r0.<init>(r5)
        L_0x0018:
            java.lang.Object r5 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.b.a()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L_0x0035
            if (r2 != r3) goto L_0x002d
            java.lang.Object r4 = r0.L$0
            java.lang.Exception r4 = (java.lang.Exception) r4
            kotlin.i.a((java.lang.Object) r5)
            goto L_0x005c
        L_0x002d:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L_0x0035:
            kotlin.i.a((java.lang.Object) r5)
            r0.L$0 = r4
            r0.label = r3
            kotlinx.coroutines.b0 r5 = kotlinx.coroutines.u0.a()
            kotlin.coroutines.CoroutineContext r2 = r0.getContext()
            retrofit2.KotlinExtensions$d r3 = new retrofit2.KotlinExtensions$d
            r3.<init>(r0, r4)
            r5.dispatch(r2, r3)
            java.lang.Object r4 = kotlin.coroutines.intrinsics.b.a()
            java.lang.Object r5 = kotlin.coroutines.intrinsics.b.a()
            if (r4 != r5) goto L_0x0059
            kotlin.coroutines.jvm.internal.e.c(r0)
        L_0x0059:
            if (r4 != r1) goto L_0x005c
            return r1
        L_0x005c:
            kotlin.l r4 = kotlin.l.a
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: retrofit2.KotlinExtensions.a(java.lang.Exception, kotlin.coroutines.c):java.lang.Object");
    }

    public static final <T> Object b(d<T> dVar, kotlin.coroutines.c<? super T> cVar) {
        kotlinx.coroutines.i iVar = new kotlinx.coroutines.i(IntrinsicsKt__IntrinsicsJvmKt.a(cVar), 1);
        iVar.b(new KotlinExtensions$await$$inlined$suspendCancellableCoroutine$lambda$2(dVar));
        dVar.a(new b(iVar));
        Object d2 = iVar.d();
        if (d2 == b.a()) {
            e.c(cVar);
        }
        return d2;
    }

    public static final <T> Object c(d<T> dVar, kotlin.coroutines.c<? super r<T>> cVar) {
        kotlinx.coroutines.i iVar = new kotlinx.coroutines.i(IntrinsicsKt__IntrinsicsJvmKt.a(cVar), 1);
        iVar.b(new KotlinExtensions$awaitResponse$$inlined$suspendCancellableCoroutine$lambda$1(dVar));
        dVar.a(new c(iVar));
        Object d2 = iVar.d();
        if (d2 == b.a()) {
            e.c(cVar);
        }
        return d2;
    }

    /* compiled from: KotlinExtensions.kt */
    public static final class b implements f<T> {
        final /* synthetic */ h a;

        b(h hVar) {
            this.a = hVar;
        }

        public void a(d<T> dVar, r<T> rVar) {
            i.b(dVar, "call");
            i.b(rVar, "response");
            if (rVar.c()) {
                h hVar = this.a;
                T a2 = rVar.a();
                Result.a aVar = Result.Companion;
                hVar.resumeWith(Result.m1constructorimpl(a2));
                return;
            }
            h hVar2 = this.a;
            HttpException httpException = new HttpException(rVar);
            Result.a aVar2 = Result.Companion;
            hVar2.resumeWith(Result.m1constructorimpl(kotlin.i.a((Throwable) httpException)));
        }

        public void a(d<T> dVar, Throwable th) {
            i.b(dVar, "call");
            i.b(th, "t");
            h hVar = this.a;
            Result.a aVar = Result.Companion;
            hVar.resumeWith(Result.m1constructorimpl(kotlin.i.a(th)));
        }
    }

    public static final <T> Object a(d<T> dVar, kotlin.coroutines.c<? super T> cVar) {
        kotlinx.coroutines.i iVar = new kotlinx.coroutines.i(IntrinsicsKt__IntrinsicsJvmKt.a(cVar), 1);
        iVar.b(new KotlinExtensions$await$$inlined$suspendCancellableCoroutine$lambda$1(dVar));
        dVar.a(new a(iVar));
        Object d2 = iVar.d();
        if (d2 == b.a()) {
            e.c(cVar);
        }
        return d2;
    }

    /* compiled from: KotlinExtensions.kt */
    public static final class a implements f<T> {
        final /* synthetic */ h a;

        a(h hVar) {
            this.a = hVar;
        }

        public void a(d<T> dVar, r<T> rVar) {
            i.b(dVar, "call");
            i.b(rVar, "response");
            if (rVar.c()) {
                T a2 = rVar.a();
                if (a2 == null) {
                    Object a3 = dVar.a().a(k.class);
                    if (a3 != null) {
                        i.a(a3, "call.request().tag(Invocation::class.java)!!");
                        Method a4 = ((k) a3).a();
                        StringBuilder sb = new StringBuilder();
                        sb.append("Response from ");
                        i.a((Object) a4, "method");
                        Class<?> declaringClass = a4.getDeclaringClass();
                        i.a((Object) declaringClass, "method.declaringClass");
                        sb.append(declaringClass.getName());
                        sb.append('.');
                        sb.append(a4.getName());
                        sb.append(" was null but response body type was declared as non-null");
                        KotlinNullPointerException kotlinNullPointerException = new KotlinNullPointerException(sb.toString());
                        h hVar = this.a;
                        Result.a aVar = Result.Companion;
                        hVar.resumeWith(Result.m1constructorimpl(kotlin.i.a((Throwable) kotlinNullPointerException)));
                        return;
                    }
                    i.a();
                    throw null;
                }
                h hVar2 = this.a;
                Result.a aVar2 = Result.Companion;
                hVar2.resumeWith(Result.m1constructorimpl(a2));
                return;
            }
            h hVar3 = this.a;
            HttpException httpException = new HttpException(rVar);
            Result.a aVar3 = Result.Companion;
            hVar3.resumeWith(Result.m1constructorimpl(kotlin.i.a((Throwable) httpException)));
        }

        public void a(d<T> dVar, Throwable th) {
            i.b(dVar, "call");
            i.b(th, "t");
            h hVar = this.a;
            Result.a aVar = Result.Companion;
            hVar.resumeWith(Result.m1constructorimpl(kotlin.i.a(th)));
        }
    }
}
