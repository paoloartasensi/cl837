package com.chileaf.fitness.device.wear.cl880.callback;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.callback.profile.ProfileReadResponse;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.fitness.common.sport.a;
import com.android.chileaf.util.HexUtil;
import com.chileaf.fitness.device.wear.cl880.model.SleepHistory;
import com.chileaf.fitness.device.wear.cl880.model.SportDayHistory;
import com.chileaf.fitness.device.wear.cl880.model.SportHistory;
import java.util.ArrayList;
import java.util.List;

public abstract class CL880ReceivedDataCallback extends ProfileReadResponse implements b, c, g, a, a, e, d, f {

    /* renamed from: h  reason: collision with root package name */
    private boolean f1216h = false;

    /* renamed from: i  reason: collision with root package name */
    private List<SportHistory> f1217i;

    /* renamed from: j  reason: collision with root package name */
    private List<SleepHistory> f1218j;
    private List<SportDayHistory> k;
    private List<Data> l;
    private List<Data> m;
    private List<Data> n;
    private int o;
    private int p;
    private boolean q;
    private boolean r;
    private boolean s;
    private byte[] t = new byte[0];
    private int u = 1;

    private int a(byte b) {
        return b & 255;
    }

    private void b(BluetoothDevice bluetoothDevice) {
        if (this.f1216h) {
            j.a.a.b("onHistoryOfSleep :%s", this.m);
        }
        List<Data> list = this.m;
        if (list != null) {
            for (Data a : list) {
                byte[] a2 = a.a();
                while (a2.length >= 6) {
                    int a3 = a(a2, 0, 1);
                    int i2 = a3 + 5;
                    if (this.f1216h) {
                        j.a.a.a("onHistoryOfSleep count:%s", Integer.valueOf(a3));
                    }
                    ArrayList arrayList = new ArrayList();
                    byte[] a4 = HexUtil.a(a2, 0, i2);
                    a2 = HexUtil.a(a2, i2, a2.length);
                    long b = b(a4, 1, 4);
                    for (int i3 = 0; i3 < a4.length - 5; i3++) {
                        arrayList.add(Integer.valueOf(a(a4, i3 + 5, 1)));
                    }
                    int i4 = 0;
                    int i5 = 0;
                    int i6 = 0;
                    int i7 = 0;
                    for (int i8 = 0; i8 < arrayList.size(); i8++) {
                        Integer num = (Integer) arrayList.get(i8);
                        if (num.intValue() > 20) {
                            i4++;
                        } else {
                            if (num.intValue() == 0) {
                                i7++;
                                if (i7 == 3) {
                                    i6++;
                                    i5 -= 2;
                                } else {
                                    i5++;
                                }
                            } else {
                                i5++;
                            }
                            i7 = 0;
                        }
                    }
                    SleepHistory sleepHistory = new SleepHistory(com.android.chileaf.util.a.a(b), i4 * 5, i5 * 5, i6 * 3 * 5);
                    this.f1218j = a(this.f1218j, sleepHistory);
                    if (this.f1216h) {
                        j.a.a.a("SleepHistory :%s", sleepHistory.toString());
                    }
                }
            }
            if (!this.f1218j.isEmpty()) {
                if (this.f1216h) {
                    j.a.a.a("mSleepHistories :%s", this.f1218j.toString());
                }
                h(bluetoothDevice, this.f1218j);
                this.m.clear();
                this.f1217i.clear();
            }
        }
    }

    private void c(BluetoothDevice bluetoothDevice) {
        if (this.f1216h) {
            j.a.a.b("onHistoryOfSport :%s", this.l);
        }
        List<Data> list = this.l;
        if (list != null) {
            for (Data a : list) {
                byte[] a2 = a.a();
                while (a2.length >= 7) {
                    int a3 = a(a2, 0, 1);
                    int i2 = a3 + 5;
                    if (this.f1216h) {
                        j.a.a.a("onHistoryOfSport count:%s - total:%s", Integer.valueOf(a3), Integer.valueOf(i2));
                    }
                    byte[] a4 = HexUtil.a(a2, 0, i2);
                    a2 = HexUtil.a(a2, i2, a2.length);
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    long b = b(a4, 1, 4);
                    for (int i3 = 0; i3 < (a4.length - 5) / 2; i3++) {
                        int i4 = i3 * 2;
                        int a5 = a(a4, i4 + 5, 1);
                        int a6 = a(a4, i4 + 6, 1);
                        arrayList.add(Integer.valueOf(a5));
                        arrayList2.add(Integer.valueOf(a6));
                    }
                    SportHistory sportHistory = new SportHistory(com.android.chileaf.util.a.a(b), arrayList, arrayList2);
                    this.f1217i = a(this.f1217i, sportHistory);
                    if (this.f1216h) {
                        j.a.a.a("SportHistory :%s", sportHistory.toString());
                    }
                }
            }
            if (!this.f1217i.isEmpty()) {
                if (this.f1216h) {
                    j.a.a.a("mSports :%s", this.f1217i.toString());
                }
                d(bluetoothDevice, this.f1217i);
                this.l.clear();
                this.f1217i.clear();
            }
        }
    }

    public void a(BluetoothDevice bluetoothDevice, Data data) {
        boolean z = false;
        if (this.f1216h) {
            j.a.a.b(com.android.chileaf.bluetooth.connect.h1.a.a(data.a()), new Object[0]);
        }
        if (!(data.a(17, 2).intValue() == data.b())) {
            b(bluetoothDevice, data);
            return;
        }
        try {
            byte[] a = data.a();
            int a2 = a(a[1]);
            if (a(a, 5)) {
                a(bluetoothDevice, new byte[]{a[3], a[4]}, true);
            } else if (a(a, 1) && b(a, 1)) {
                a(bluetoothDevice, a(a, 10, 3), a(a, 13, 3), a(a, 16, 3));
            } else if (a(a, 2)) {
                a(bluetoothDevice, a);
                if (b(a, 1)) {
                    int a3 = a(a, 10, 1);
                    byte[] bArr = {a[3], a[4]};
                    if (a3 == 1) {
                        z = true;
                    }
                    b(bluetoothDevice, bArr, z);
                } else if (b(a, 2)) {
                    b(bluetoothDevice, new byte[]{a[3], a[4]}, a(a, 10, 1));
                } else if (b(a, 3)) {
                    a(bluetoothDevice, new byte[]{a[3], a[4]}, a(a, 10, 1));
                }
            } else if (a(a, 3)) {
                a(bluetoothDevice, a);
                if (b(a, 1)) {
                    if (this.f1216h) {
                        j.a.a.a("设备支持信息", new Object[0]);
                    }
                } else if (b(a, 2) && this.f1216h) {
                    j.a.a.a("上次同步的UTC", new Object[0]);
                }
            } else if (a(a, 4)) {
                a(bluetoothDevice, a);
                if (b(a, 1)) {
                    if (this.f1216h) {
                        j.a.a.b("运动细分数据", new Object[0]);
                    }
                    this.q = true;
                    if (a2 != 0) {
                        this.u = 2;
                    } else if (a.length > 11) {
                        this.l = a(this.l, new Data(a(a)));
                    }
                } else if (b(a, 5)) {
                    if (this.f1216h) {
                        j.a.a.b("睡眠数据", new Object[0]);
                    }
                    this.r = true;
                    if (a2 != 0) {
                        this.u = 4;
                    } else if (a.length > 11) {
                        this.m = a(this.m, new Data(a(a)));
                    }
                } else if (b(a, 6)) {
                    if (this.f1216h) {
                        j.a.a.b("每天总数据", new Object[0]);
                    }
                    this.s = true;
                    if (a2 != 0) {
                        this.u = 8;
                    } else if (a.length > 11) {
                        this.n = a(this.n, new Data(a(a)));
                    }
                } else if (b(a, 255)) {
                    if (this.f1216h) {
                        j.a.a.b("接受数据结束", new Object[0]);
                    }
                    if (this.q) {
                        c(bluetoothDevice);
                    }
                    if (this.r) {
                        b(bluetoothDevice);
                    }
                    if (this.s) {
                        a(bluetoothDevice);
                    }
                    b();
                }
                if (a2 == 128) {
                    this.o = 1;
                    this.p = a[9];
                    this.t = new byte[0];
                    this.t = a(a);
                }
            } else if (a(a2)) {
                this.t = HexUtil.a(this.t, HexUtil.a(a, 3, a.length - 1));
                this.o++;
            } else if (b(a2)) {
                byte[] a4 = HexUtil.a(this.t, HexUtil.a(a, 3, a.length - 1));
                int i2 = this.u;
                if (i2 == 2) {
                    this.l = a(this.l, new Data(a4));
                } else if (i2 == 4) {
                    this.m = a(this.m, new Data(a4));
                } else if (i2 == 8) {
                    this.n = a(this.n, new Data(a4));
                } else if (this.f1216h) {
                    j.a.a.b("isLast out of mState", new Object[0]);
                }
                if (this.f1216h) {
                    j.a.a.b("onDataReceived Packages:%s length:%d size:%s", com.android.chileaf.bluetooth.connect.h1.a.a(a4), Integer.valueOf(this.p), Integer.valueOf(a4.length));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long b(byte[] bArr, int i2, int i3) {
        int i4 = i3 + i2;
        long j2 = 0;
        while (i2 < i4) {
            j2 = (j2 << 8) | (((long) bArr[i2]) & 255);
            i2++;
        }
        return j2;
    }

    private boolean b(int i2) {
        int i3 = this.o;
        int i4 = i3 + 192;
        if (this.f1216h) {
            j.a.a.b("isLast count:%s pack:%02x end:%02x", Integer.valueOf(i3), Integer.valueOf(i2), Integer.valueOf(i4));
        }
        if (i2 == i4) {
            return true;
        }
        return false;
    }

    private boolean b(byte[] bArr, int i2) {
        if (bArr == null || bArr.length <= 7 || a(bArr[7]) != i2) {
            return false;
        }
        return true;
    }

    public void b() {
        this.q = false;
        this.r = false;
        this.s = false;
        this.u = 1;
    }

    private byte[] a(byte[] bArr) {
        return HexUtil.a(bArr, 10, bArr.length - 1);
    }

    private void a(BluetoothDevice bluetoothDevice) {
        if (this.f1216h) {
            j.a.a.b("onHistoryOfEveryday :%s", this.n);
        }
        List<Data> list = this.n;
        if (list != null) {
            for (Data a : list) {
                byte[] a2 = a.a();
                while (a2.length >= 12) {
                    byte[] a3 = HexUtil.a(a2, 0, 12);
                    a2 = HexUtil.a(a2, 12, a2.length);
                    long b = b(a3, 0, 4);
                    SportDayHistory sportDayHistory = new SportDayHistory(com.android.chileaf.util.a.a(b), a(a3, 4, 4), a(a3, 8, 4));
                    this.k = a(this.k, sportDayHistory);
                    if (this.f1216h) {
                        j.a.a.a("SportDayHistory :%s", sportDayHistory.toString());
                    }
                }
            }
            if (!this.k.isEmpty()) {
                if (this.f1216h) {
                    j.a.a.a("mEveryDays :%s", this.k.toString());
                }
                f(bluetoothDevice, this.k);
                this.n.clear();
                this.k.clear();
            }
        }
    }

    private synchronized <T> List<T> a(List<T> list, T t2) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(t2);
        return list;
    }

    private int a(byte[] bArr, int i2, int i3) {
        int i4 = i3 + i2;
        byte b = 0;
        while (i2 < i4) {
            b = (b << 8) | (bArr[i2] & 255);
            i2++;
        }
        return b;
    }

    private boolean a(int i2) {
        if (this.f1216h) {
            j.a.a.b("isContinuous index:%02x count:%s", Integer.valueOf(i2), Integer.valueOf(this.o));
        }
        if (i2 <= 128 || i2 >= 192) {
            return false;
        }
        return true;
    }

    private boolean a(byte[] bArr, int i2) {
        if (bArr == null || bArr.length <= 5 || a(bArr[5]) != i2) {
            return false;
        }
        return true;
    }

    private void a(BluetoothDevice bluetoothDevice, byte[] bArr) {
        if (bArr != null && bArr.length > 8 && bArr[8] == 1) {
            a(bluetoothDevice, new byte[]{bArr[3], bArr[4]}, false);
        }
    }
}
