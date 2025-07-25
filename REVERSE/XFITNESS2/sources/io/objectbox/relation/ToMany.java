package io.objectbox.relation;

import io.objectbox.BoxStore;
import io.objectbox.Cursor;
import io.objectbox.exception.DbDetachedException;
import io.objectbox.internal.ToManyGetter;
import io.objectbox.internal.ToOneGetter;
import io.objectbox.internal.c;
import io.objectbox.relation.ListFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class ToMany<TARGET> implements List<TARGET>, Serializable {

    /* renamed from: j  reason: collision with root package name */
    private static final Integer f1762j = 1;
    private static final long serialVersionUID = 2367317778240689006L;
    private transient BoxStore e;
    private List<TARGET> entities;
    private Map<TARGET, Boolean> entitiesAdded;
    private Map<TARGET, Boolean> entitiesRemoved;
    List<TARGET> entitiesToPut;
    List<TARGET> entitiesToRemoveFromDb;
    private final Object entity;
    private Map<TARGET, Integer> entityCounts;
    /* access modifiers changed from: private */

    /* renamed from: f  reason: collision with root package name */
    public transient io.objectbox.a f1763f;
    /* access modifiers changed from: private */

    /* renamed from: g  reason: collision with root package name */
    public volatile transient io.objectbox.a<TARGET> f1764g;

    /* renamed from: h  reason: collision with root package name */
    private transient boolean f1765h;

    /* renamed from: i  reason: collision with root package name */
    private transient Comparator<TARGET> f1766i;
    private ListFactory listFactory;
    /* access modifiers changed from: private */
    public final RelationInfo<Object, TARGET> relationInfo;

    class a implements Comparator<TARGET> {
        io.objectbox.internal.b<TARGET> e = ToMany.this.relationInfo.targetInfo.getIdGetter();

        a() {
        }

        public int compare(TARGET target, TARGET target2) {
            long a = this.e.a(target);
            long a2 = this.e.a(target2);
            if (a == 0) {
                a = Long.MAX_VALUE;
            }
            if (a2 == 0) {
                a2 = Long.MAX_VALUE;
            }
            long j2 = a - a2;
            if (j2 < 0) {
                return -1;
            }
            return j2 > 0 ? 1 : 0;
        }
    }

    class b implements Runnable {
        b() {
        }

        public void run() {
            ToMany.this.internalApplyToDb(io.objectbox.b.a(ToMany.this.f1763f), io.objectbox.b.a(ToMany.this.f1764g));
        }
    }

    public ToMany(Object obj, RelationInfo<? extends Object, TARGET> relationInfo2) {
        if (obj == null) {
            throw new IllegalArgumentException("No source entity given (null)");
        } else if (relationInfo2 != null) {
            this.entity = obj;
            this.relationInfo = relationInfo2;
        } else {
            throw new IllegalArgumentException("No relation info given (null)");
        }
    }

    private void a() {
        if (this.f1764g == null) {
            try {
                BoxStore boxStore = (BoxStore) c.a().a(this.entity.getClass(), "__boxStore").get(this.entity);
                this.e = boxStore;
                if (boxStore != null) {
                    this.f1763f = boxStore.a(this.relationInfo.sourceInfo.getEntityClass());
                    this.f1764g = this.e.a(this.relationInfo.targetInfo.getEntityClass());
                    return;
                }
                throw new DbDetachedException("Cannot resolve relation for detached entities, call box.attach(entity) beforehand.");
            } catch (IllegalAccessException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    private void b() {
        List<TARGET> list;
        if (this.entities == null) {
            long a2 = this.relationInfo.sourceInfo.getIdGetter().a(this.entity);
            if (a2 == 0) {
                synchronized (this) {
                    if (this.entities == null) {
                        this.entities = getListFactory().createList();
                    }
                }
                return;
            }
            a();
            RelationInfo<Object, TARGET> relationInfo2 = this.relationInfo;
            int i2 = relationInfo2.relationId;
            if (i2 != 0) {
                list = this.f1764g.a(relationInfo2.sourceInfo.getEntityId(), i2, a2, false);
            } else if (relationInfo2.targetIdProperty != null) {
                list = this.f1764g.a(this.relationInfo.targetInfo.getEntityId(), this.relationInfo.targetIdProperty, a2);
            } else {
                list = this.f1764g.a(this.relationInfo.targetInfo.getEntityId(), this.relationInfo.targetRelationId, a2, true);
            }
            Comparator<TARGET> comparator = this.f1766i;
            if (comparator != null) {
                Collections.sort(list, comparator);
            }
            synchronized (this) {
                if (this.entities == null) {
                    this.entities = list;
                }
            }
        }
    }

    private void c() {
        b();
        if (this.entitiesAdded == null) {
            synchronized (this) {
                if (this.entitiesAdded == null) {
                    this.entitiesAdded = new LinkedHashMap();
                    this.entitiesRemoved = new LinkedHashMap();
                    this.entityCounts = new HashMap();
                    for (TARGET next : this.entities) {
                        Integer put = this.entityCounts.put(next, f1762j);
                        if (put != null) {
                            this.entityCounts.put(next, Integer.valueOf(put.intValue() + 1));
                        }
                    }
                }
            }
        }
    }

    public synchronized boolean add(TARGET target) {
        a(target);
        return this.entities.add(target);
    }

    public synchronized boolean addAll(Collection<? extends TARGET> collection) {
        a(collection);
        return this.entities.addAll(collection);
    }

    public void applyChangesToDb() {
        if (this.relationInfo.sourceInfo.getIdGetter().a(this.entity) != 0) {
            try {
                a();
                if (internalCheckApplyToDbRequired()) {
                    this.e.a((Runnable) new b());
                }
            } catch (DbDetachedException unused) {
                throw new IllegalStateException("The source entity was not yet persisted, use box.put() on it instead");
            }
        } else {
            throw new IllegalStateException("The source entity was not yet persisted (no ID), use box.put() on it instead");
        }
    }

    public synchronized void clear() {
        c();
        List<TARGET> list = this.entities;
        if (list != null) {
            for (TARGET put : list) {
                this.entitiesRemoved.put(put, Boolean.TRUE);
            }
            list.clear();
        }
        Map<TARGET, Boolean> map = this.entitiesAdded;
        if (map != null) {
            map.clear();
        }
        Map<TARGET, Integer> map2 = this.entityCounts;
        if (map2 != null) {
            map2.clear();
        }
    }

    public boolean contains(Object obj) {
        b();
        return this.entities.contains(obj);
    }

    public boolean containsAll(Collection<?> collection) {
        b();
        return this.entities.containsAll(collection);
    }

    public TARGET get(int i2) {
        b();
        return this.entities.get(i2);
    }

    public int getAddCount() {
        Map<TARGET, Boolean> map = this.entitiesAdded;
        if (map != null) {
            return map.size();
        }
        return 0;
    }

    public TARGET getById(long j2) {
        b();
        TARGET[] array = this.entities.toArray();
        io.objectbox.internal.b<TARGET> idGetter = this.relationInfo.targetInfo.getIdGetter();
        for (TARGET target : array) {
            if (idGetter.a(target) == j2) {
                return target;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public Object getEntity() {
        return this.entity;
    }

    public ListFactory getListFactory() {
        if (this.listFactory == null) {
            synchronized (this) {
                if (this.listFactory == null) {
                    this.listFactory = new ListFactory.CopyOnWriteArrayListFactory();
                }
            }
        }
        return this.listFactory;
    }

    public int getRemoveCount() {
        Map<TARGET, Boolean> map = this.entitiesRemoved;
        if (map != null) {
            return map.size();
        }
        return 0;
    }

    public boolean hasA(io.objectbox.query.a<TARGET> aVar) {
        for (Object a2 : toArray()) {
            if (aVar.a(a2)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAll(io.objectbox.query.a<TARGET> aVar) {
        Object[] array = toArray();
        if (array.length == 0) {
            return false;
        }
        for (Object a2 : array) {
            if (!aVar.a(a2)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasPendingDbChanges() {
        Map<TARGET, Boolean> map = this.entitiesAdded;
        if (map != null && !map.isEmpty()) {
            return true;
        }
        Map<TARGET, Boolean> map2 = this.entitiesRemoved;
        if (map2 == null || map2.isEmpty()) {
            return false;
        }
        return true;
    }

    public int indexOf(Object obj) {
        b();
        return this.entities.indexOf(obj);
    }

    public int indexOfId(long j2) {
        b();
        Object[] array = this.entities.toArray();
        io.objectbox.internal.b<TARGET> idGetter = this.relationInfo.targetInfo.getIdGetter();
        int i2 = 0;
        for (Object a2 : array) {
            if (idGetter.a(a2) == j2) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public void internalApplyToDb(Cursor cursor, Cursor<TARGET> cursor2) {
        Object[] objArr;
        Object[] objArr2;
        ArrayList arrayList;
        Object[] array;
        Object[] objArr3;
        Cursor<TARGET> cursor3 = cursor2;
        boolean z = this.relationInfo.relationId != 0;
        io.objectbox.internal.b<TARGET> idGetter = this.relationInfo.targetInfo.getIdGetter();
        synchronized (this) {
            objArr = null;
            if (z) {
                for (TARGET next : this.entitiesAdded.keySet()) {
                    if (idGetter.a(next) == 0) {
                        this.entitiesToPut.add(next);
                    }
                }
                if (this.f1765h) {
                    this.entitiesToRemoveFromDb.addAll(this.entitiesRemoved.keySet());
                }
                if (!this.entitiesAdded.isEmpty()) {
                    objArr3 = this.entitiesAdded.keySet().toArray();
                    this.entitiesAdded.clear();
                } else {
                    objArr3 = null;
                }
                if (!this.entitiesRemoved.isEmpty()) {
                    arrayList = new ArrayList(this.entitiesRemoved.keySet());
                    this.entitiesRemoved.clear();
                } else {
                    arrayList = null;
                }
                objArr2 = objArr3;
            } else {
                arrayList = null;
                objArr2 = null;
            }
            array = this.entitiesToRemoveFromDb.isEmpty() ? null : this.entitiesToRemoveFromDb.toArray();
            this.entitiesToRemoveFromDb.clear();
            if (!this.entitiesToPut.isEmpty()) {
                objArr = this.entitiesToPut.toArray();
            }
            this.entitiesToPut.clear();
        }
        if (array != null) {
            for (Object a2 : array) {
                long a3 = idGetter.a(a2);
                if (a3 != 0) {
                    cursor3.h(a3);
                }
            }
        }
        if (objArr != null) {
            for (Object a4 : objArr) {
                cursor3.a(a4);
            }
        }
        if (z) {
            long a5 = this.relationInfo.sourceInfo.getIdGetter().a(this.entity);
            if (a5 != 0) {
                if (arrayList != null) {
                    a(cursor, a5, arrayList, idGetter);
                }
                if (objArr2 != null) {
                    a(cursor, a5, objArr2, idGetter, false);
                    return;
                }
                return;
            }
            throw new IllegalStateException("Source entity has no ID (should have been put before)");
        }
    }

    public boolean internalCheckApplyToDbRequired() {
        if (!hasPendingDbChanges()) {
            return false;
        }
        synchronized (this) {
            if (this.entitiesToPut == null) {
                this.entitiesToPut = new ArrayList();
                this.entitiesToRemoveFromDb = new ArrayList();
            }
        }
        RelationInfo<Object, TARGET> relationInfo2 = this.relationInfo;
        if (relationInfo2.relationId != 0) {
            return true;
        }
        long a2 = relationInfo2.sourceInfo.getIdGetter().a(this.entity);
        if (a2 != 0) {
            io.objectbox.internal.b<TARGET> idGetter = this.relationInfo.targetInfo.getIdGetter();
            Map<TARGET, Boolean> map = this.entitiesAdded;
            Map<TARGET, Boolean> map2 = this.entitiesRemoved;
            if (this.relationInfo.targetRelationId != 0) {
                return a(a2, idGetter, map, map2);
            }
            return b(a2, idGetter, map, map2);
        }
        throw new IllegalStateException("Source entity has no ID (should have been put before)");
    }

    public boolean isEmpty() {
        b();
        return this.entities.isEmpty();
    }

    public boolean isResolved() {
        return this.entities != null;
    }

    public Iterator<TARGET> iterator() {
        b();
        return this.entities.iterator();
    }

    public int lastIndexOf(Object obj) {
        b();
        return this.entities.lastIndexOf(obj);
    }

    public ListIterator<TARGET> listIterator() {
        b();
        return this.entities.listIterator();
    }

    public synchronized TARGET remove(int i2) {
        TARGET remove;
        c();
        remove = this.entities.remove(i2);
        b(remove);
        return remove;
    }

    public synchronized boolean removeAll(Collection<?> collection) {
        boolean z;
        z = false;
        for (Object remove : collection) {
            z |= remove((Object) remove);
        }
        return z;
    }

    public synchronized TARGET removeById(long j2) {
        b();
        int size = this.entities.size();
        io.objectbox.internal.b<TARGET> idGetter = this.relationInfo.targetInfo.getIdGetter();
        for (int i2 = 0; i2 < size; i2++) {
            TARGET target = this.entities.get(i2);
            if (idGetter.a(target) == j2) {
                TARGET remove = remove(i2);
                if (remove == target) {
                    return target;
                }
                throw new IllegalStateException("Mismatch: " + remove + " vs. " + target);
            }
        }
        return null;
    }

    public synchronized void reset() {
        this.entities = null;
        this.entitiesAdded = null;
        this.entitiesRemoved = null;
        this.entitiesToRemoveFromDb = null;
        this.entitiesToPut = null;
        this.entityCounts = null;
    }

    public synchronized boolean retainAll(Collection<?> collection) {
        boolean z;
        c();
        z = false;
        ArrayList arrayList = null;
        for (TARGET next : this.entities) {
            if (!collection.contains(next)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(next);
                z = true;
            }
        }
        if (arrayList != null) {
            removeAll(arrayList);
        }
        return z;
    }

    public synchronized TARGET set(int i2, TARGET target) {
        TARGET target2;
        c();
        target2 = this.entities.set(i2, target);
        b(target2);
        a(target);
        return target2;
    }

    public void setComparator(Comparator<TARGET> comparator) {
        this.f1766i = comparator;
    }

    public void setListFactory(ListFactory listFactory2) {
        if (listFactory2 != null) {
            this.listFactory = listFactory2;
            return;
        }
        throw new IllegalArgumentException("ListFactory is null");
    }

    public synchronized void setRemoveFromTargetBox(boolean z) {
        this.f1765h = z;
    }

    public int size() {
        b();
        return this.entities.size();
    }

    public void sortById() {
        b();
        Collections.sort(this.entities, new a());
    }

    public List<TARGET> subList(int i2, int i3) {
        b();
        return this.entities.subList(i2, i3);
    }

    public Object[] toArray() {
        b();
        return this.entities.toArray();
    }

    public synchronized void add(int i2, TARGET target) {
        a(target);
        this.entities.add(i2, target);
    }

    public synchronized boolean addAll(int i2, Collection<? extends TARGET> collection) {
        a(collection);
        return this.entities.addAll(i2, collection);
    }

    public ListIterator<TARGET> listIterator(int i2) {
        b();
        return this.entities.listIterator(i2);
    }

    public <T> T[] toArray(T[] tArr) {
        b();
        return this.entities.toArray(tArr);
    }

    public synchronized boolean remove(Object obj) {
        boolean remove;
        c();
        remove = this.entities.remove(obj);
        if (remove) {
            b(obj);
        }
        return remove;
    }

    private void a(TARGET target) {
        c();
        Integer put = this.entityCounts.put(target, f1762j);
        if (put != null) {
            this.entityCounts.put(target, Integer.valueOf(put.intValue() + 1));
        }
        this.entitiesAdded.put(target, Boolean.TRUE);
        this.entitiesRemoved.remove(target);
    }

    private void a(Collection<? extends TARGET> collection) {
        c();
        for (Object a2 : collection) {
            a(a2);
        }
    }

    private boolean a(long j2, io.objectbox.internal.b<TARGET> bVar, Map<TARGET, Boolean> map, Map<TARGET, Boolean> map2) {
        boolean z;
        ToManyGetter<SOURCE> toManyGetter = this.relationInfo.backlinkToManyGetter;
        synchronized (this) {
            if (map != null) {
                try {
                    if (!map.isEmpty()) {
                        for (TARGET next : map.keySet()) {
                            ToMany toMany = (ToMany) toManyGetter.getToMany(next);
                            if (toMany == null) {
                                throw new IllegalStateException("The ToMany property for " + this.relationInfo.targetInfo.getEntityName() + " is null");
                            } else if (toMany.getById(j2) == null) {
                                toMany.add(this.entity);
                                this.entitiesToPut.add(next);
                            } else if (bVar.a(next) == 0) {
                                this.entitiesToPut.add(next);
                            }
                        }
                        map.clear();
                    }
                } finally {
                }
            }
            if (map2 != null) {
                for (TARGET next2 : map2.keySet()) {
                    ToMany toMany2 = (ToMany) toManyGetter.getToMany(next2);
                    if (toMany2.getById(j2) != null) {
                        toMany2.removeById(j2);
                        if (bVar.a(next2) != 0) {
                            if (this.f1765h) {
                                this.entitiesToRemoveFromDb.add(next2);
                            } else {
                                this.entitiesToPut.add(next2);
                            }
                        }
                    }
                }
                map2.clear();
            }
            if (this.entitiesToPut.isEmpty()) {
                if (this.entitiesToRemoveFromDb.isEmpty()) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    private void b(TARGET target) {
        c();
        Integer remove = this.entityCounts.remove(target);
        if (remove == null) {
            return;
        }
        if (remove.intValue() == 1) {
            this.entityCounts.remove(target);
            this.entitiesAdded.remove(target);
            this.entitiesRemoved.put(target, Boolean.TRUE);
        } else if (remove.intValue() > 1) {
            this.entityCounts.put(target, Integer.valueOf(remove.intValue() - 1));
        } else {
            throw new IllegalStateException("Illegal count: " + remove);
        }
    }

    private boolean b(long j2, io.objectbox.internal.b<TARGET> bVar, Map<TARGET, Boolean> map, Map<TARGET, Boolean> map2) {
        boolean z;
        ToOneGetter<SOURCE> toOneGetter = this.relationInfo.backlinkToOneGetter;
        synchronized (this) {
            if (map != null) {
                try {
                    if (!map.isEmpty()) {
                        for (TARGET next : map.keySet()) {
                            ToOne<TARGET> toOne = toOneGetter.getToOne(next);
                            if (toOne == null) {
                                throw new IllegalStateException("The ToOne property for " + this.relationInfo.targetInfo.getEntityName() + "." + this.relationInfo.targetIdProperty.name + " is null");
                            } else if (toOne.getTargetId() != j2) {
                                toOne.setTarget(this.entity);
                                this.entitiesToPut.add(next);
                            } else if (bVar.a(next) == 0) {
                                this.entitiesToPut.add(next);
                            }
                        }
                        map.clear();
                    }
                } finally {
                }
            }
            if (map2 != null) {
                for (TARGET next2 : map2.keySet()) {
                    ToOne<TARGET> toOne2 = toOneGetter.getToOne(next2);
                    if (toOne2.getTargetId() == j2) {
                        toOne2.setTarget(null);
                        if (bVar.a(next2) != 0) {
                            if (this.f1765h) {
                                this.entitiesToRemoveFromDb.add(next2);
                            } else {
                                this.entitiesToPut.add(next2);
                            }
                        }
                    }
                }
                map2.clear();
            }
            if (this.entitiesToPut.isEmpty()) {
                if (this.entitiesToRemoveFromDb.isEmpty()) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    private void a(Cursor cursor, long j2, List<TARGET> list, io.objectbox.internal.b<TARGET> bVar) {
        Iterator<TARGET> it = list.iterator();
        while (it.hasNext()) {
            if (bVar.a(it.next()) == 0) {
                it.remove();
            }
        }
        int size = list.size();
        if (size > 0) {
            long[] jArr = new long[size];
            for (int i2 = 0; i2 < size; i2++) {
                jArr[i2] = bVar.a(list.get(i2));
            }
            cursor.a(this.relationInfo.relationId, j2, jArr, true);
        }
    }

    private void a(Cursor cursor, long j2, TARGET[] targetArr, io.objectbox.internal.b<TARGET> bVar, boolean z) {
        int length = targetArr.length;
        long[] jArr = new long[length];
        int i2 = 0;
        while (i2 < length) {
            long a2 = bVar.a(targetArr[i2]);
            if (a2 != 0) {
                jArr[i2] = a2;
                i2++;
            } else {
                throw new IllegalStateException("Target entity has no ID (should have been put before)");
            }
        }
        cursor.a(this.relationInfo.relationId, j2, jArr, z);
    }
}
