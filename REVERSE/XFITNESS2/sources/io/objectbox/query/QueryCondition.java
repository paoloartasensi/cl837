package io.objectbox.query;

import io.objectbox.Property;
import io.objectbox.exception.DbException;
import java.util.Date;

public interface QueryCondition {

    public static class PropertyCondition extends a {

        public enum Operation {
            EQUALS,
            NOT_EQUALS,
            BETWEEN,
            IN,
            GREATER_THAN,
            LESS_THAN,
            IS_NULL,
            IS_NOT_NULL,
            CONTAINS,
            STARTS_WITH,
            ENDS_WITH
        }

        public PropertyCondition(Property property, Operation operation, Object obj) {
            super(a(property, obj));
        }

        private static Object a(Property property, Object obj) {
            if (obj == null || !obj.getClass().isArray()) {
                Class<?> cls = property.type;
                if (cls != Date.class) {
                    if (cls == Boolean.TYPE || cls == Boolean.class) {
                        if (obj instanceof Boolean) {
                            return Integer.valueOf(((Boolean) obj).booleanValue() ? 1 : 0);
                        }
                        if (obj instanceof Number) {
                            int intValue = ((Number) obj).intValue();
                            if (!(intValue == 0 || intValue == 1)) {
                                throw new DbException("Illegal boolean value: numbers must be 0 or 1, but was " + obj);
                            }
                        } else if (obj instanceof String) {
                            String str = (String) obj;
                            if ("TRUE".equalsIgnoreCase(str)) {
                                return 1;
                            }
                            if ("FALSE".equalsIgnoreCase(str)) {
                                return 0;
                            }
                            throw new DbException("Illegal boolean value: Strings must be \"TRUE\" or \"FALSE\" (case insensitive), but was " + obj);
                        }
                    }
                    return obj;
                } else if (obj instanceof Date) {
                    return Long.valueOf(((Date) obj).getTime());
                } else {
                    if (obj instanceof Long) {
                        return obj;
                    }
                    throw new DbException("Illegal date value: expected java.util.Date or Long for value " + obj);
                }
            } else {
                throw new DbException("Illegal value: found array, but simple object required");
            }
        }

        public PropertyCondition(Property property, Operation operation, Object[] objArr) {
            super(a(property, operation, objArr));
        }

        private static Object[] a(Property property, Operation operation, Object[] objArr) {
            if (objArr != null) {
                for (int i2 = 0; i2 < objArr.length; i2++) {
                    objArr[i2] = a(property, objArr[i2]);
                }
                return objArr;
            } else if (operation == Operation.IS_NULL || operation == Operation.IS_NOT_NULL) {
                return null;
            } else {
                throw new IllegalArgumentException("This operation requires non-null values.");
            }
        }
    }

    public static abstract class a implements QueryCondition {
        public final Object a;
        protected final Object[] b;

        a(Object obj) {
            this.a = obj;
        }

        a(Object[] objArr) {
            this.b = objArr;
        }
    }
}
