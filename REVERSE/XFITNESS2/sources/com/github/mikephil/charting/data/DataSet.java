package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.Entry;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.util.ArrayList;
import java.util.List;

public abstract class DataSet<T extends Entry> extends d<T> {
    protected List<T> s = null;
    protected float t = -3.4028235E38f;
    protected float u = Float.MAX_VALUE;
    protected float v = -3.4028235E38f;
    protected float w = Float.MAX_VALUE;

    public enum Rounding {
        UP,
        DOWN,
        CLOSEST
    }

    public DataSet(List<T> list, String str) {
        super(str);
        this.s = list;
        if (list == null) {
            this.s = new ArrayList();
        }
        I0();
    }

    public float A() {
        return this.u;
    }

    public void I0() {
        List<T> list = this.s;
        if (list != null && !list.isEmpty()) {
            this.t = -3.4028235E38f;
            this.u = Float.MAX_VALUE;
            this.v = -3.4028235E38f;
            this.w = Float.MAX_VALUE;
            for (T c : this.s) {
                c(c);
            }
        }
    }

    public List<T> J0() {
        return this.s;
    }

    public String K0() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder sb = new StringBuilder();
        sb.append("DataSet, label: ");
        sb.append(q() == null ? BuildConfig.FLAVOR : q());
        sb.append(", entries: ");
        sb.append(this.s.size());
        sb.append("\n");
        stringBuffer.append(sb.toString());
        return stringBuffer.toString();
    }

    public int X() {
        return this.s.size();
    }

    public boolean a(T t2) {
        if (t2 == null) {
            return false;
        }
        List J0 = J0();
        if (J0 == null) {
            J0 = new ArrayList();
        }
        c(t2);
        return J0.add(t2);
    }

    public float a0() {
        return this.w;
    }

    public void b(float f2, float f3) {
        List<T> list = this.s;
        if (list != null && !list.isEmpty()) {
            this.t = -3.4028235E38f;
            this.u = Float.MAX_VALUE;
            int b = b(f3, Float.NaN, Rounding.UP);
            for (int b2 = b(f2, Float.NaN, Rounding.DOWN); b2 <= b; b2++) {
                e((Entry) this.s.get(b2));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void c(T t2) {
        if (t2 != null) {
            d(t2);
            e(t2);
        }
    }

    /* access modifiers changed from: protected */
    public void d(T t2) {
        if (t2.d() < this.w) {
            this.w = t2.d();
        }
        if (t2.d() > this.v) {
            this.v = t2.d();
        }
    }

    /* access modifiers changed from: protected */
    public void e(T t2) {
        if (t2.c() < this.u) {
            this.u = t2.c();
        }
        if (t2.c() > this.t) {
            this.t = t2.c();
        }
    }

    public float g0() {
        return this.t;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(K0());
        for (int i2 = 0; i2 < this.s.size(); i2++) {
            stringBuffer.append(((Entry) this.s.get(i2)).toString() + " ");
        }
        return stringBuffer.toString();
    }

    public float w() {
        return this.v;
    }

    public T c(int i2) {
        return (Entry) this.s.get(i2);
    }

    public T a(float f2, float f3, Rounding rounding) {
        int b = b(f2, f3, rounding);
        if (b > -1) {
            return (Entry) this.s.get(b);
        }
        return null;
    }

    public T a(float f2, float f3) {
        return a(f2, f3, Rounding.CLOSEST);
    }

    public int b(Entry entry) {
        return this.s.indexOf(entry);
    }

    public List<T> a(float f2) {
        ArrayList arrayList = new ArrayList();
        int size = this.s.size() - 1;
        int i2 = 0;
        while (true) {
            if (i2 > size) {
                break;
            }
            int i3 = (size + i2) / 2;
            Entry entry = (Entry) this.s.get(i3);
            if (f2 == entry.d()) {
                while (i3 > 0 && ((Entry) this.s.get(i3 - 1)).d() == f2) {
                    i3--;
                }
                int size2 = this.s.size();
                while (i3 < size2) {
                    Entry entry2 = (Entry) this.s.get(i3);
                    if (entry2.d() != f2) {
                        break;
                    }
                    arrayList.add(entry2);
                    i3++;
                }
            } else if (f2 > entry.d()) {
                i2 = i3 + 1;
            } else {
                size = i3 - 1;
            }
        }
        return arrayList;
    }

    public int b(float f2, float f3, Rounding rounding) {
        int i2;
        Entry entry;
        List<T> list = this.s;
        if (list == null || list.isEmpty()) {
            return -1;
        }
        int i3 = 0;
        int size = this.s.size() - 1;
        while (i3 < size) {
            int i4 = (i3 + size) / 2;
            float d = ((Entry) this.s.get(i4)).d() - f2;
            int i5 = i4 + 1;
            float abs = Math.abs(d);
            float abs2 = Math.abs(((Entry) this.s.get(i5)).d() - f2);
            if (abs2 >= abs) {
                if (abs >= abs2) {
                    double d2 = (double) d;
                    if (d2 < 0.0d) {
                        if (d2 >= 0.0d) {
                        }
                    }
                }
                size = i4;
            }
            i3 = i5;
        }
        if (size == -1) {
            return size;
        }
        float d3 = ((Entry) this.s.get(size)).d();
        if (rounding == Rounding.UP) {
            if (d3 < f2 && size < this.s.size() - 1) {
                size++;
            }
        } else if (rounding == Rounding.DOWN && d3 > f2 && size > 0) {
            size--;
        }
        if (Float.isNaN(f3)) {
            return size;
        }
        while (size > 0 && ((Entry) this.s.get(size - 1)).d() == d3) {
            size--;
        }
        float c = ((Entry) this.s.get(size)).c();
        loop2:
        while (true) {
            i2 = size;
            do {
                size++;
                if (size >= this.s.size()) {
                    break loop2;
                }
                entry = (Entry) this.s.get(size);
                if (entry.d() != d3) {
                    break loop2;
                }
            } while (Math.abs(entry.c() - f3) >= Math.abs(c - f3));
            c = f3;
        }
        return i2;
    }
}
