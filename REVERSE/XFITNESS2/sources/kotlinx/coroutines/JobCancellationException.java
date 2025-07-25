package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.jvm.internal.i;

/* compiled from: Exceptions.kt */
public final class JobCancellationException extends CancellationException implements z<JobCancellationException> {
    public final k1 job;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public JobCancellationException(String str, Throwable th, k1 k1Var) {
        super(str);
        i.b(str, "message");
        i.b(k1Var, "job");
        this.job = k1Var;
        if (th != null) {
            initCause(th);
        }
    }

    public boolean equals(Object obj) {
        if (obj != this) {
            if (obj instanceof JobCancellationException) {
                JobCancellationException jobCancellationException = (JobCancellationException) obj;
                if (!i.a((Object) jobCancellationException.getMessage(), (Object) getMessage()) || !i.a((Object) jobCancellationException.job, (Object) this.job) || !i.a((Object) jobCancellationException.getCause(), (Object) getCause())) {
                    return false;
                }
            }
            return false;
        }
        return true;
    }

    public Throwable fillInStackTrace() {
        if (!j0.c()) {
            return this;
        }
        Throwable fillInStackTrace = super.fillInStackTrace();
        i.a((Object) fillInStackTrace, "super.fillInStackTrace()");
        return fillInStackTrace;
    }

    public int hashCode() {
        String message = getMessage();
        if (message != null) {
            int hashCode = ((message.hashCode() * 31) + this.job.hashCode()) * 31;
            Throwable cause = getCause();
            return hashCode + (cause != null ? cause.hashCode() : 0);
        }
        i.a();
        throw null;
    }

    public String toString() {
        return super.toString() + "; job=" + this.job;
    }

    public JobCancellationException createCopy() {
        if (!j0.c()) {
            return null;
        }
        String message = getMessage();
        if (message != null) {
            return new JobCancellationException(message, this, this.job);
        }
        i.a();
        throw null;
    }
}
