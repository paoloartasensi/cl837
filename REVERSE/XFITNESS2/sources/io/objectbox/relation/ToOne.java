package io.objectbox.relation;

import io.objectbox.BoxStore;
import io.objectbox.Cursor;
import io.objectbox.exception.DbDetachedException;
import io.objectbox.internal.c;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.Field;

public class ToOne<TARGET> implements Serializable {
    private static final long serialVersionUID = 5092547044335989281L;
    private boolean checkIdOfTargetForPut;
    private boolean debugRelations;
    private transient BoxStore e;
    /* access modifiers changed from: private */
    public final Object entity;
    /* access modifiers changed from: private */

    /* renamed from: f  reason: collision with root package name */
    public transient io.objectbox.a f1768f;
    /* access modifiers changed from: private */

    /* renamed from: g  reason: collision with root package name */
    public volatile transient io.objectbox.a<TARGET> f1769g;

    /* renamed from: h  reason: collision with root package name */
    private transient Field f1770h;
    private final RelationInfo relationInfo;
    private volatile long resolvedTargetId;
    private TARGET target;
    private long targetId;
    private final boolean virtualProperty;

    class a implements Runnable {
        final /* synthetic */ Object e;

        a(Object obj) {
            this.e = obj;
        }

        public void run() {
            ToOne.this.a(this.e, ToOne.this.f1769g.b(this.e));
            ToOne.this.f1768f.b(ToOne.this.entity);
        }
    }

    public ToOne(Object obj, RelationInfo relationInfo2) {
        if (obj == null) {
            throw new IllegalArgumentException("No source entity given (null)");
        } else if (relationInfo2 != null) {
            this.entity = obj;
            this.relationInfo = relationInfo2;
            this.virtualProperty = relationInfo2.targetIdProperty.isVirtual;
        } else {
            throw new IllegalArgumentException("No relation info given (null)");
        }
    }

    private void a(TARGET target2) {
        if (this.f1769g == null) {
            try {
                BoxStore boxStore = (BoxStore) c.a().a(this.entity.getClass(), "__boxStore").get(this.entity);
                this.e = boxStore;
                if (boxStore == null) {
                    if (target2 != null) {
                        this.e = (BoxStore) c.a().a(target2.getClass(), "__boxStore").get(target2);
                    }
                    if (this.e == null) {
                        throw new DbDetachedException("Cannot resolve relation for detached entities, call box.attach(entity) beforehand.");
                    }
                }
                this.debugRelations = this.e.m();
                this.f1768f = this.e.a(this.relationInfo.sourceInfo.getEntityClass());
                this.f1769g = this.e.a(this.relationInfo.targetInfo.getEntityClass());
            } catch (IllegalAccessException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    private Field b() {
        if (this.f1770h == null) {
            this.f1770h = c.a().a(this.entity.getClass(), this.relationInfo.targetIdProperty.name);
        }
        return this.f1770h;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ToOne)) {
            return false;
        }
        ToOne toOne = (ToOne) obj;
        if (this.relationInfo == toOne.relationInfo && getTargetId() == toOne.getTargetId()) {
            return true;
        }
        return false;
    }

    public TARGET getCachedTarget() {
        return this.target;
    }

    /* access modifiers changed from: package-private */
    public Object getEntity() {
        return this.entity;
    }

    public TARGET getTarget() {
        return getTarget(getTargetId());
    }

    public long getTargetId() {
        if (this.virtualProperty) {
            return this.targetId;
        }
        Field b = b();
        try {
            Long l = (Long) b.get(this.entity);
            if (l != null) {
                return l.longValue();
            }
            return 0;
        } catch (IllegalAccessException unused) {
            throw new RuntimeException("Could not access field " + b);
        }
    }

    public int hashCode() {
        long targetId2 = getTargetId();
        return (int) (targetId2 ^ (targetId2 >>> 32));
    }

    public void internalPutTarget(Cursor<TARGET> cursor) {
        this.checkIdOfTargetForPut = false;
        long a2 = cursor.a(this.target);
        setTargetId(a2);
        a(this.target, a2);
    }

    public boolean internalRequiresPutTarget() {
        return this.checkIdOfTargetForPut && this.target != null && getTargetId() == 0;
    }

    public boolean isNull() {
        return getTargetId() == 0 && this.target == null;
    }

    public boolean isResolved() {
        return this.resolvedTargetId == getTargetId();
    }

    public boolean isResolvedAndNotNull() {
        return this.resolvedTargetId != 0 && this.resolvedTargetId == getTargetId();
    }

    public void setAndPutTarget(TARGET target2) {
        a(target2);
        if (target2 != null) {
            long a2 = this.f1769g.a(target2);
            if (a2 == 0) {
                setAndPutTargetAlways(target2);
                return;
            }
            setTargetId(a2);
            a(target2, a2);
            this.f1768f.b(this.entity);
            return;
        }
        setTargetId(0);
        a();
        this.f1768f.b(this.entity);
    }

    public void setAndPutTargetAlways(TARGET target2) {
        a(target2);
        if (target2 != null) {
            this.e.a((Runnable) new a(target2));
            return;
        }
        setTargetId(0);
        a();
        this.f1768f.b(this.entity);
    }

    /* access modifiers changed from: package-private */
    public void setAndUpdateTargetId(long j2) {
        setTargetId(j2);
        a((Object) null);
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setTarget(TARGET target2) {
        if (target2 != null) {
            long a2 = this.relationInfo.targetInfo.getIdGetter().a(target2);
            this.checkIdOfTargetForPut = a2 == 0;
            setTargetId(a2);
            a(target2, a2);
            return;
        }
        setTargetId(0);
        a();
    }

    public void setTargetId(long j2) {
        if (this.virtualProperty) {
            this.targetId = j2;
        } else {
            try {
                b().set(this.entity, Long.valueOf(j2));
            } catch (IllegalAccessException e2) {
                throw new RuntimeException("Could not update to-one ID in entity", e2);
            }
        }
        if (j2 != 0) {
            this.checkIdOfTargetForPut = false;
        }
    }

    public TARGET getTarget(long j2) {
        synchronized (this) {
            if (this.resolvedTargetId == j2) {
                TARGET target2 = this.target;
                return target2;
            }
            a((Object) null);
            TARGET a2 = this.f1769g.a(j2);
            a(a2, j2);
            return a2;
        }
    }

    /* access modifiers changed from: private */
    public synchronized void a(TARGET target2, long j2) {
        if (this.debugRelations) {
            PrintStream printStream = System.out;
            StringBuilder sb = new StringBuilder();
            sb.append("Setting resolved ToOne target to ");
            sb.append(target2 == null ? "null" : "non-null");
            sb.append(" for ID ");
            sb.append(j2);
            printStream.println(sb.toString());
        }
        this.resolvedTargetId = j2;
        this.target = target2;
    }

    private synchronized void a() {
        this.resolvedTargetId = 0;
        this.target = null;
    }
}
