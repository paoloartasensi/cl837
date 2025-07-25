package kotlin.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.i;
import kotlin.l;

/* compiled from: CoroutineContextImpl.kt */
final class CombinedContext$writeReplace$1 extends Lambda implements p<l, CoroutineContext.a, l> {
    final /* synthetic */ CoroutineContext[] $elements;
    final /* synthetic */ Ref$IntRef $index;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CombinedContext$writeReplace$1(CoroutineContext[] coroutineContextArr, Ref$IntRef ref$IntRef) {
        super(2);
        this.$elements = coroutineContextArr;
        this.$index = ref$IntRef;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        invoke((l) obj, (CoroutineContext.a) obj2);
        return l.a;
    }

    public final void invoke(l lVar, CoroutineContext.a aVar) {
        i.b(lVar, "<anonymous parameter 0>");
        i.b(aVar, "element");
        CoroutineContext[] coroutineContextArr = this.$elements;
        Ref$IntRef ref$IntRef = this.$index;
        int i2 = ref$IntRef.element;
        ref$IntRef.element = i2 + 1;
        coroutineContextArr[i2] = aVar;
    }
}
