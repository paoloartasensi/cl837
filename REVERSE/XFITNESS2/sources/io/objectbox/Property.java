package io.objectbox;

import io.objectbox.converter.PropertyConverter;
import io.objectbox.exception.DbException;
import io.objectbox.query.QueryCondition;
import java.io.Serializable;
import java.util.Collection;

public class Property<ENTITY> implements Serializable {
    private static final long serialVersionUID = 8613291105982758093L;
    public final Class<? extends PropertyConverter> converterClass;
    public final Class customType;
    public final String dbName;
    public final EntityInfo<ENTITY> entity;
    public final int id;
    private boolean idVerified;
    public final boolean isId;
    public final boolean isVirtual;
    public final String name;
    public final int ordinal;
    public final Class<?> type;

    public Property(EntityInfo<ENTITY> entityInfo, int i2, int i3, Class<?> cls, String str) {
        this(entityInfo, i2, i3, cls, str, false, str, (Class<? extends PropertyConverter>) null, (Class) null);
    }

    public QueryCondition between(Object obj, Object obj2) {
        return new QueryCondition.PropertyCondition(this, QueryCondition.PropertyCondition.Operation.BETWEEN, new Object[]{obj, obj2});
    }

    public QueryCondition contains(String str) {
        return new QueryCondition.PropertyCondition(this, QueryCondition.PropertyCondition.Operation.CONTAINS, (Object) str);
    }

    public QueryCondition endsWith(String str) {
        return new QueryCondition.PropertyCondition(this, QueryCondition.PropertyCondition.Operation.ENDS_WITH, (Object) str);
    }

    public QueryCondition eq(Object obj) {
        return new QueryCondition.PropertyCondition(this, QueryCondition.PropertyCondition.Operation.EQUALS, obj);
    }

    public int getEntityId() {
        return this.entity.getEntityId();
    }

    public int getId() {
        int i2 = this.id;
        if (i2 > 0) {
            return i2;
        }
        throw new IllegalStateException("Illegal property ID " + this.id + " for " + toString());
    }

    public QueryCondition gt(Object obj) {
        return new QueryCondition.PropertyCondition(this, QueryCondition.PropertyCondition.Operation.GREATER_THAN, obj);
    }

    public QueryCondition in(Object... objArr) {
        return new QueryCondition.PropertyCondition(this, QueryCondition.PropertyCondition.Operation.IN, objArr);
    }

    /* access modifiers changed from: package-private */
    public boolean isIdVerified() {
        return this.idVerified;
    }

    public QueryCondition isNotNull() {
        return new QueryCondition.PropertyCondition(this, QueryCondition.PropertyCondition.Operation.IS_NOT_NULL, (Object[]) null);
    }

    public QueryCondition isNull() {
        return new QueryCondition.PropertyCondition(this, QueryCondition.PropertyCondition.Operation.IS_NULL, (Object[]) null);
    }

    public QueryCondition lt(Object obj) {
        return new QueryCondition.PropertyCondition(this, QueryCondition.PropertyCondition.Operation.LESS_THAN, obj);
    }

    public QueryCondition notEq(Object obj) {
        return new QueryCondition.PropertyCondition(this, QueryCondition.PropertyCondition.Operation.NOT_EQUALS, obj);
    }

    public QueryCondition startsWith(String str) {
        return new QueryCondition.PropertyCondition(this, QueryCondition.PropertyCondition.Operation.STARTS_WITH, (Object) str);
    }

    public String toString() {
        return "Property \"" + this.name + "\" (ID: " + this.id + ")";
    }

    /* access modifiers changed from: package-private */
    public void verifyId(int i2) {
        int i3 = this.id;
        if (i3 <= 0) {
            throw new IllegalStateException("Illegal property ID " + this.id + " for " + toString());
        } else if (i3 == i2) {
            this.idVerified = true;
        } else {
            throw new DbException(toString() + " does not match ID in DB: " + i2);
        }
    }

    public Property(EntityInfo<ENTITY> entityInfo, int i2, int i3, Class<?> cls, String str, boolean z) {
        this(entityInfo, i2, i3, cls, str, false, z, str, (Class<? extends PropertyConverter>) null, (Class) null);
    }

    public QueryCondition in(Collection<?> collection) {
        return in(collection.toArray());
    }

    public Property(EntityInfo<ENTITY> entityInfo, int i2, int i3, Class<?> cls, String str, boolean z, String str2) {
        this(entityInfo, i2, i3, cls, str, z, str2, (Class<? extends PropertyConverter>) null, (Class) null);
    }

    public Property(EntityInfo<ENTITY> entityInfo, int i2, int i3, Class<?> cls, String str, boolean z, String str2, Class<? extends PropertyConverter> cls2, Class cls3) {
        this(entityInfo, i2, i3, cls, str, z, false, str2, cls2, cls3);
    }

    public Property(EntityInfo<ENTITY> entityInfo, int i2, int i3, Class<?> cls, String str, boolean z, boolean z2, String str2, Class<? extends PropertyConverter> cls2, Class cls3) {
        this.entity = entityInfo;
        this.ordinal = i2;
        this.id = i3;
        this.type = cls;
        this.name = str;
        this.isId = z;
        this.isVirtual = z2;
        this.dbName = str2;
        this.converterClass = cls2;
        this.customType = cls3;
    }
}
