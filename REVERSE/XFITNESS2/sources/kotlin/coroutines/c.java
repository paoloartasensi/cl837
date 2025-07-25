package kotlin.coroutines;

/* compiled from: Continuation.kt */
public interface c<T> {
    CoroutineContext getContext();

    void resumeWith(Object obj);
}
