package io.objectbox.relation;

import io.objectbox.EntityInfo;
import io.objectbox.Property;
import io.objectbox.internal.ToManyGetter;
import io.objectbox.internal.ToOneGetter;
import java.io.Serializable;

public class RelationInfo<SOURCE, TARGET> implements Serializable {
    private static final long serialVersionUID = 7412962174183812632L;
    public final ToManyGetter<SOURCE> backlinkToManyGetter;
    public final ToOneGetter<SOURCE> backlinkToOneGetter;
    public final int relationId;
    public final EntityInfo<SOURCE> sourceInfo;
    public final Property targetIdProperty;
    public final EntityInfo<TARGET> targetInfo;
    public final int targetRelationId;
    public final ToManyGetter<TARGET> toManyGetter;
    public final ToOneGetter<TARGET> toOneGetter;

    public RelationInfo(EntityInfo<SOURCE> entityInfo, EntityInfo<TARGET> entityInfo2, Property property, ToOneGetter toOneGetter2) {
        this.sourceInfo = entityInfo;
        this.targetInfo = entityInfo2;
        this.targetIdProperty = property;
        this.toOneGetter = toOneGetter2;
        this.targetRelationId = 0;
        this.backlinkToOneGetter = null;
        this.backlinkToManyGetter = null;
        this.toManyGetter = null;
        this.relationId = 0;
    }

    public boolean isBacklink() {
        return (this.backlinkToManyGetter == null && this.backlinkToOneGetter == null) ? false : true;
    }

    public String toString() {
        return "RelationInfo from " + this.sourceInfo.getEntityClass() + " to " + this.targetInfo.getEntityClass();
    }

    public RelationInfo(EntityInfo<SOURCE> entityInfo, EntityInfo<TARGET> entityInfo2, ToManyGetter toManyGetter2, Property property, ToOneGetter toOneGetter2) {
        this.sourceInfo = entityInfo;
        this.targetInfo = entityInfo2;
        this.targetIdProperty = property;
        this.toManyGetter = toManyGetter2;
        this.backlinkToOneGetter = toOneGetter2;
        this.targetRelationId = 0;
        this.toOneGetter = null;
        this.backlinkToManyGetter = null;
        this.relationId = 0;
    }

    public RelationInfo(EntityInfo<SOURCE> entityInfo, EntityInfo<TARGET> entityInfo2, ToManyGetter toManyGetter2, ToManyGetter toManyGetter3, int i2) {
        this.sourceInfo = entityInfo;
        this.targetInfo = entityInfo2;
        this.toManyGetter = toManyGetter2;
        this.targetRelationId = i2;
        this.backlinkToManyGetter = toManyGetter3;
        this.targetIdProperty = null;
        this.toOneGetter = null;
        this.backlinkToOneGetter = null;
        this.relationId = 0;
    }

    public RelationInfo(EntityInfo<SOURCE> entityInfo, EntityInfo<TARGET> entityInfo2, ToManyGetter toManyGetter2, int i2) {
        this.sourceInfo = entityInfo;
        this.targetInfo = entityInfo2;
        this.toManyGetter = toManyGetter2;
        this.relationId = i2;
        this.targetRelationId = 0;
        this.targetIdProperty = null;
        this.toOneGetter = null;
        this.backlinkToOneGetter = null;
        this.backlinkToManyGetter = null;
    }
}
