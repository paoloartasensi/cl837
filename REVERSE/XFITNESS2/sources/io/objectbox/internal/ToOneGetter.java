package io.objectbox.internal;

import io.objectbox.relation.ToOne;
import java.io.Serializable;

public interface ToOneGetter<SOURCE> extends Serializable {
    <TARGET> ToOne<TARGET> getToOne(SOURCE source);
}
