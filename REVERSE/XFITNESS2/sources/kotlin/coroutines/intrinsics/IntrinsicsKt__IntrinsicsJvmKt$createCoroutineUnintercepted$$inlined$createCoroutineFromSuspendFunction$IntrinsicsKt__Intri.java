package kotlin.coroutines.intrinsics;

import kotlin.TypeCastException;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.c;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.i;
import kotlin.jvm.b.l;
import kotlin.jvm.internal.n;

/* compiled from: IntrinsicsJvm.kt */
public final class IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$2 extends ContinuationImpl {
    final /* synthetic */ c $completion;
    final /* synthetic */ CoroutineContext $context;
    final /* synthetic */ l $this_createCoroutineUnintercepted$inlined;
    private int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$2(c cVar, CoroutineContext coroutineContext, c cVar2, CoroutineContext coroutineContext2, l lVar) {
        super(cVar2, coroutineContext2);
        this.$completion = cVar;
        this.$context = coroutineContext;
        this.$this_createCoroutineUnintercepted$inlined = lVar;
    }

    /* access modifiers changed from: protected */
    public Object invokeSuspend(Object obj) {
        int i2 = this.label;
        if (i2 == 0) {
            this.label = 1;
            i.a(obj);
            l lVar = this.$this_createCoroutineUnintercepted$inlined;
            if (lVar != null) {
                n.a((Object) lVar, 1);
                return lVar.invoke(this);
            }
            throw new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
        } else if (i2 == 1) {
            this.label = 2;
            i.a(obj);
            return obj;
        } else {
            throw new IllegalStateException("This coroutine had already completed".toString());
        }
    }
}
      } else {
            throw new IllegalStateException("This coroutine had already completed".toString());
        }
    }
}
