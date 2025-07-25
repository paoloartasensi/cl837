package io.objectbox.internal;

import io.objectbox.BoxStore;
import io.objectbox.Cursor;
import io.objectbox.Transaction;

/* compiled from: CursorFactory */
public interface a<T> {
    Cursor<T> a(Transaction transaction, long j2, BoxStore boxStore);
}
