package io.objectbox.internal;

import java.io.Serializable;
import java.util.List;

public interface ToManyGetter<SOURCE> extends Serializable {
    <TARGET> List<TARGET> getToMany(SOURCE source);
}
