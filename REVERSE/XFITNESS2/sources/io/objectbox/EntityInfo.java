package io.objectbox;

import io.objectbox.internal.a;
import io.objectbox.internal.b;
import java.io.Serializable;

public interface EntityInfo<T> extends Serializable {
    a<T> getCursorFactory();

    String getDbName();

    Class<T> getEntityClass();

    int getEntityId();

    String getEntityName();

    b<T> getIdGetter();
}
