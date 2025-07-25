package com.google.gson;

import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.internal.a;
import java.math.BigInteger;

/* compiled from: JsonPrimitive */
public final class l extends i {
    private final Object a;

    public l(Boolean bool) {
        a.a(bool);
        this.a = bool;
    }

    private static boolean a(l lVar) {
        Object obj = lVar.a;
        if (!(obj instanceof Number)) {
            return false;
        }
        Number number = (Number) obj;
        if ((number instanceof BigInteger) || (number instanceof Long) || (number instanceof Integer) || (number instanceof Short) || (number instanceof Byte)) {
            return true;
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || l.class != obj.getClass()) {
            return false;
        }
        l lVar = (l) obj;
        if (this.a == null) {
            if (lVar.a == null) {
                return true;
            }
            return false;
        } else if (!a(this) || !a(lVar)) {
            if (!(this.a instanceof Number) || !(lVar.a instanceof Number)) {
                return this.a.equals(lVar.a);
            }
            double doubleValue = l().doubleValue();
            double doubleValue2 = lVar.l().doubleValue();
            if (doubleValue == doubleValue2) {
                return true;
            }
            if (!Double.isNaN(doubleValue) || !Double.isNaN(doubleValue2)) {
                return false;
            }
            return true;
        } else if (l().longValue() == lVar.l().longValue()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean h() {
        if (n()) {
            return ((Boolean) this.a).booleanValue();
        }
        return Boolean.parseBoolean(m());
    }

    public int hashCode() {
        long doubleToLongBits;
        if (this.a == null) {
            return 31;
        }
        if (a(this)) {
            doubleToLongBits = l().longValue();
        } else {
            Object obj = this.a;
            if (!(obj instanceof Number)) {
                return obj.hashCode();
            }
            doubleToLongBits = Double.doubleToLongBits(l().doubleValue());
        }
        return (int) ((doubleToLongBits >>> 32) ^ doubleToLongBits);
    }

    public double i() {
        return o() ? l().doubleValue() : Double.parseDouble(m());
    }

    public int j() {
        return o() ? l().intValue() : Integer.parseInt(m());
    }

    public long k() {
        return o() ? l().longValue() : Long.parseLong(m());
    }

    public Number l() {
        Object obj = this.a;
        return obj instanceof String ? new LazilyParsedNumber((String) this.a) : (Number) obj;
    }

    public String m() {
        if (o()) {
            return l().toString();
        }
        if (n()) {
            return ((Boolean) this.a).toString();
        }
        return (String) this.a;
    }

    public boolean n() {
        return this.a instanceof Boolean;
    }

    public boolean o() {
        return this.a instanceof Number;
    }

    public boolean p() {
        return this.a instanceof String;
    }

    public l(Number number) {
        a.a(number);
        this.a = number;
    }

    public l(String str) {
        a.a(str);
        this.a = str;
    }
}
