package com.chileaf.fitness.device.wear.cl820.callback;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.callback.profile.ProfileReadResponse;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.fitness.common.sport.a;
import com.android.chileaf.fitness.model.HistoryOfHeartRate;
import com.android.chileaf.fitness.model.HistoryOfRecord;
import com.android.chileaf.util.HexUtil;
import com.chileaf.fitness.device.wear.cl820.model.HistoryOfRespiratoryRate;
import com.chileaf.fitness.device.wear.cl820.model.HistoryOfSleep;
import com.chileaf.fitness.device.wear.cl820.model.HistoryOfSport;
import java.util.ArrayList;
import java.util.List;

public abstract class CL820ReceivedDataCallback extends ProfileReadResponse implements a, f, b, a, d, c, e {

    /* renamed from: h  reason: collision with root package name */
    private byte[] f1166h = null;

    /* renamed from: i  reason: collision with root package name */
    private int f1167i = 0;

    /* renamed from: j  reason: collision with root package name */
    private long f1168j = 0;
    private boolean k = false;
    private List<HistoryOfSport> l;
    private List<HistoryOfRecord> m;
    private List<HistoryOfRecord> n;
    private List<HistoryOfHeartRate> o;
    private List<HistoryOfRespiratoryRate> p;
    private List<HistoryOfSleep> q;
    private List<Data> r;

    private long b(byte[] bArr, int i2, int i3) {
        int i4 = i3 + i2;
        long j2 = 0;
        while (i2 < i4) {
            j2 = (j2 << 8) | (((long) bArr[i2]) & 255);
            i2++;
        }
        return j2;
    }

    public void a(BluetoothDevice bluetoothDevice, Data data) {
        BluetoothDevice bluetoothDevice2 = bluetoothDevice;
        Data data2 = data;
        super.a(bluetoothDevice, data);
        int i2 = 1;
        if (!(data2.a(17, 1).intValue() == data.b())) {
            b(bluetoothDevice, data);
            return;
        }
        byte[] a = data.a();
        int i3 = 2;
        int intValue = data2.a(17, 2).intValue();
        if (intValue == 21) {
            try {
                a(bluetoothDevice2, a(a, 3, 3), a(a, 6, 3), a(a, 9, 3));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            int i4 = 4;
            if (intValue == 22) {
                if (this.l == null) {
                    this.l = new ArrayList();
                }
                if (b(a, 3, 4) != 4294967295L) {
                    a(a);
                    this.f1167i++;
                    return;
                }
                while (this.f1167i > 0) {
                    this.f1167i--;
                    byte[] a2 = a(14);
                    j.a.a.b("HistoryOfSport mValues:%s mIndex:%d", com.android.chileaf.bluetooth.connect.h1.a.a(a2), Integer.valueOf(this.f1167i));
                    for (int i5 = 0; i5 < (a2.length - 4) / 10; i5++) {
                        int i6 = i5 * 10;
                        this.l.add(new HistoryOfSport(com.android.chileaf.util.a.a(b(a2, i6 + 3, 4)), b(a2, i6 + 7, 3), b(a2, i6 + 10, 3)));
                    }
                }
                b(bluetoothDevice2, this.l);
                j.a.a.b("onHistoryOfSportReceived size:%d", Integer.valueOf(this.l.size()));
                this.l.clear();
                this.f1166h = null;
                this.f1167i = 0;
            } else if (intValue == 33) {
                if (this.m == null) {
                    this.m = new ArrayList();
                }
                if (b(a, 3, 4) != 4294967295L) {
                    a(a);
                    this.f1167i++;
                    return;
                }
                while (this.f1167i > 0) {
                    this.f1167i--;
                    byte[] a3 = a(20);
                    Object[] objArr = new Object[i3];
                    objArr[0] = com.android.chileaf.bluetooth.connect.h1.a.a(a3);
                    objArr[1] = Integer.valueOf(this.f1167i);
                    j.a.a.b("HeartRatesRecords mValues:%s mIndex:%d", objArr);
                    int i7 = 0;
                    while (i7 < (a3.length - 4) / 4) {
                        long b = b(a3, (i7 * 4) + 3, 4);
                        this.m.add(new HistoryOfRecord(b, com.android.chileaf.util.a.a(b)));
                        i7++;
                        i3 = 2;
                    }
                }
                a(bluetoothDevice2, this.m);
                j.a.a.b("onHistoryOfHRRecordReceived size:%d", Integer.valueOf(this.m.size()));
                this.m.clear();
                this.f1166h = null;
                this.f1167i = 0;
            } else {
                if (intValue != 34) {
                    if (intValue != 35) {
                        if (intValue == 36) {
                            if (this.n == null) {
                                this.n = new ArrayList();
                            }
                            if (b(a, 3, 4) != 4294967295L) {
                                a(a);
                                this.f1167i++;
                                return;
                            }
                            while (this.f1167i > 0) {
                                this.f1167i--;
                                byte[] a4 = a(20);
                                j.a.a.b("RespiratoryRatesRecords mValues:%s mIndex:%d", com.android.chileaf.bluetooth.connect.h1.a.a(a4), Integer.valueOf(this.f1167i));
                                for (int i8 = 0; i8 < (a4.length - 4) / 4; i8++) {
                                    long b2 = b(a4, (i8 * 4) + 3, 4);
                                    this.n.add(new HistoryOfRecord(b2, com.android.chileaf.util.a.a(b2)));
                                }
                            }
                            k(bluetoothDevice2, this.n);
                            j.a.a.b("onHistoryOfRRRecordReceived size:%d", Integer.valueOf(this.n.size()));
                            this.n.clear();
                            this.f1166h = null;
                            this.f1167i = 0;
                            return;
                        }
                        if (intValue != 37) {
                            if (intValue != 38) {
                                if (intValue == 49 || intValue == 50) {
                                    if (this.q == null) {
                                        this.q = new ArrayList();
                                    }
                                    if (this.r == null) {
                                        this.r = new ArrayList();
                                    }
                                    if (intValue == 49) {
                                        a(a);
                                        if (b(a, 3, 4) > 1000 && this.f1166h != null) {
                                            this.r.add(new Data(this.f1166h));
                                            j.a.a.b("mHistoryOfSleepData :%s ", this.r.toString());
                                            this.f1166h = null;
                                        }
                                    }
                                    if (intValue == 50) {
                                        int i9 = 0;
                                        while (i9 < this.r.size()) {
                                            ArrayList arrayList = new ArrayList();
                                            Data data3 = this.r.get(i9);
                                            int b3 = data3.b();
                                            this.f1167i = b3 % 20 == 0 ? b3 / 20 : (b3 / 20) + i2;
                                            byte[] a5 = data3.a();
                                            this.f1166h = a5;
                                            long b4 = b(a5, 3, i4);
                                            while (this.f1167i > 0) {
                                                this.f1167i -= i2;
                                                byte[] a6 = a(20);
                                                Object[] objArr2 = new Object[2];
                                                objArr2[0] = com.android.chileaf.bluetooth.connect.h1.a.a(a6);
                                                objArr2[i2] = Integer.valueOf(this.f1167i);
                                                j.a.a.b("HistoryOfSleeps mValues:%s mIndex:%d ", objArr2);
                                                for (int i10 = 7; i10 < a6.length - i2; i10++) {
                                                    arrayList.add(Integer.valueOf(a(a6, i10, i2)));
                                                }
                                            }
                                            int i11 = 0;
                                            int i12 = 0;
                                            int i13 = 0;
                                            int i14 = 0;
                                            for (int i15 = 0; i15 < arrayList.size(); i15++) {
                                                Integer num = (Integer) arrayList.get(i15);
                                                if (num.intValue() > 20) {
                                                    i11++;
                                                } else if (num.intValue() == 0) {
                                                    i14++;
                                                    if (i14 == 3) {
                                                        i13++;
                                                        i12 -= 2;
                                                    } else {
                                                        i12++;
                                                    }
                                                }
                                            }
                                            HistoryOfSleep historyOfSleep = new HistoryOfSleep(com.android.chileaf.util.a.a(b4), com.android.chileaf.util.a.a(((long) (arrayList.size() * 5 * 60)) + b4), i11 * 5, i12 * 5, i13 * 3 * 5);
                                            j.a.a.b("HistoryOfSleep :%s mIndex:%d ", historyOfSleep.toString(), Integer.valueOf(i9));
                                            this.q.add(historyOfSleep);
                                            i9++;
                                            i2 = 1;
                                            i4 = 4;
                                        }
                                        j(bluetoothDevice2, this.q);
                                        j.a.a.b("onHistoryOfSleepReceived size:%d", Integer.valueOf(this.q.size()));
                                        this.r.clear();
                                        this.q.clear();
                                        this.k = false;
                                        this.f1166h = null;
                                        this.f1168j = 0;
                                        this.f1167i = 0;
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                        }
                        if (this.p == null) {
                            this.p = new ArrayList();
                        }
                        if (intValue == 37) {
                            if (!this.k) {
                                this.f1168j = b(a, 3, 4);
                                this.k = true;
                            }
                            a(a);
                            this.f1167i++;
                        }
                        if (intValue != 38) {
                            return;
                        }
                        while (this.f1167i > 0) {
                            this.f1167i--;
                            byte[] a7 = a(20);
                            j.a.a.b("HistoryOfRespiratoryRate mValues:%s mIndex:%d", com.android.chileaf.bluetooth.connect.h1.a.a(a7), Integer.valueOf(this.f1167i));
                            for (int i16 = 0; i16 < (a7.length - 8) / 4; i16++) {
                                int i17 = i16 * 4;
                                this.p.add(new HistoryOfRespiratoryRate(com.android.chileaf.util.a.a(this.f1168j), a(a7, i17 + 7, 2), a(a7, i17 + 9, 2)));
                                this.f1168j++;
                            }
                        }
                        g(bluetoothDevice2, this.p);
                        j.a.a.b("onHistoryOfRRDataReceived size:%d", Integer.valueOf(this.p.size()));
                        this.p.clear();
                        this.k = false;
                        this.f1166h = null;
                        this.f1168j = 0;
                        this.f1167i = 0;
                        return;
                    }
                }
                if (this.o == null) {
                    this.o = new ArrayList();
                }
                if (intValue == 34) {
                    if (!this.k) {
                        this.f1168j = b(a, 3, 4);
                        this.k = true;
                    }
                    a(a);
                    this.f1167i++;
                }
                if (intValue != 35) {
                    return;
                }
                while (this.f1167i > 0) {
                    this.f1167i--;
                    byte[] a8 = a(20);
                    j.a.a.b("HistoryOfHeartRates mValues:%s mIndex:%d", com.android.chileaf.bluetooth.connect.h1.a.a(a8), Integer.valueOf(this.f1167i));
                    int i18 = 7;
                    for (int i19 = 1; i18 < a8.length - i19; i19 = 1) {
                        this.o.add(new HistoryOfHeartRate(com.android.chileaf.util.a.a(this.f1168j), a(a8, i18, i19)));
                        this.f1168j++;
                        i18++;
                    }
                }
                c(bluetoothDevice2, this.o);
                j.a.a.b("onHistoryOfHRDataReceived size:%d", Integer.valueOf(this.o.size()));
                this.o.clear();
                this.k = false;
                this.f1166h = null;
                this.f1168j = 0;
                this.f1167i = 0;
            }
        }
    }

    private synchronized void a(byte[] bArr) {
        if (this.f1166h == null) {
            this.f1166h = bArr;
        } else {
            this.f1166h = HexUtil.a(this.f1166h, bArr);
        }
    }

    private synchronized byte[] a(int i2) {
        byte[] bArr;
        if (this.f1166h.length >= i2) {
            bArr = HexUtil.a(this.f1166h, 0, i2);
            this.f1166h = HexUtil.a(this.f1166h, i2, this.f1166h.length);
        } else {
            bArr = this.f1166h;
        }
        return bArr;
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
}
