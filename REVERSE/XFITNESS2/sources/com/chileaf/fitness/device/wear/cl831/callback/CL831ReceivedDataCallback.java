package com.chileaf.fitness.device.wear.cl831.callback;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.callback.profile.ProfileReadResponse;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.fitness.common.sport.a;
import com.android.chileaf.fitness.model.HistoryOfHeartRate;
import com.android.chileaf.fitness.model.HistoryOfRecord;
import com.android.chileaf.fitness.x.b;
import com.android.chileaf.fitness.x.c;
import com.android.chileaf.util.HexUtil;
import com.chileaf.fitness.device.wear.cl831.model.HistoryOfSport;
import com.chileaf.fitness.device.wear.cl831.model.IntervalStep;
import java.util.ArrayList;
import java.util.List;

public abstract class CL831ReceivedDataCallback extends ProfileReadResponse implements c, a, a, b, b, com.android.chileaf.fitness.x.a, c, d {

    /* renamed from: h  reason: collision with root package name */
    private long f1188h = 0;

    /* renamed from: i  reason: collision with root package name */
    private boolean f1189i = false;

    /* renamed from: j  reason: collision with root package name */
    private boolean f1190j = false;
    private List<Data> k = new ArrayList();
    private List<HistoryOfSport> l;
    private List<HistoryOfRecord> m;
    private List<HistoryOfHeartRate> n;
    private List<IntervalStep> o;
    private List<HistoryOfRecord> p;

    private synchronized byte[] b(byte[] bArr) {
        return HexUtil.a(bArr, 3, bArr.length - 1);
    }

    public void a(BluetoothDevice bluetoothDevice, Data data) {
        BluetoothDevice bluetoothDevice2 = bluetoothDevice;
        Data data2 = data;
        super.a(bluetoothDevice, data);
        boolean z = true;
        if (!(data2.a(17, 1).intValue() == data.b())) {
            b(bluetoothDevice, data);
            com.android.chileaf.util.b.b("onDataReceived:length:%s", Integer.valueOf(data.b()));
            return;
        }
        byte[] a = data.a();
        int intValue = data2.a(17, 2).intValue();
        if (intValue == 3) {
            try {
                a(bluetoothDevice, a(a, 5, 1), a(a, 6, 1), a(a, 7, 1), a(a, 8, 1), b(a, 9, 5));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (intValue == 21) {
            a(bluetoothDevice2, a(a, 3, 3), a(a, 6, 3), a(a, 9, 3));
        } else if (intValue == 63) {
            if (a(a, 3, 1) != 1) {
                z = false;
            }
            a(bluetoothDevice2, z);
        } else if (intValue == 22) {
            if (this.l == null) {
                this.l = new ArrayList();
            }
            long b = b(a, 3, 4);
            if (this.f1189i) {
                a(b(a));
                b(bluetoothDevice2, this.l);
                this.l.clear();
            } else if (b != 4294967295L) {
                this.k.add(data2);
            } else {
                for (int i2 = 0; i2 < this.k.size(); i2++) {
                    byte[] b2 = b(this.k.get(i2).a());
                    a(b2);
                    com.android.chileaf.util.b.a("HistoryOfSport index:%d values:%s", Integer.valueOf(i2), com.android.chileaf.bluetooth.connect.h1.a.a(b2));
                }
                b(bluetoothDevice2, this.l);
                this.l.clear();
                this.k.clear();
            }
        } else if (intValue == 33) {
            if (this.m == null) {
                this.m = new ArrayList();
            }
            if (b(a, 3, 4) != 4294967295L) {
                this.k.add(data2);
                return;
            }
            for (int i3 = 0; i3 < this.k.size(); i3++) {
                byte[] b3 = b(this.k.get(i3).a());
                com.android.chileaf.util.b.a("mHistoryOfRecords index:%d values:%s", Integer.valueOf(i3), com.android.chileaf.bluetooth.connect.h1.a.a(b3));
                for (int i4 = 0; i4 < b3.length / 4; i4++) {
                    long b4 = b(b3, i4 * 4, 4);
                    long a2 = com.android.chileaf.util.a.a(b4);
                    this.m.add(new HistoryOfRecord(b4, a2));
                    com.android.chileaf.util.b.a("mHistoryOfRecords index:%d stamp:%s record:%s", Integer.valueOf(i4), Long.valueOf(b4), Long.valueOf(a2));
                }
            }
            a(bluetoothDevice2, this.m);
            this.m.clear();
            this.k.clear();
        } else {
            if (intValue != 34) {
                if (intValue != 35) {
                    if (intValue != 64) {
                        if (intValue != 65) {
                            if (intValue == 66 || intValue == 67) {
                                if (this.p == null) {
                                    this.p = new ArrayList();
                                }
                                if (intValue == 66) {
                                    this.k.add(data2);
                                }
                                if (intValue == 67) {
                                    for (int i5 = 0; i5 < this.k.size(); i5++) {
                                        byte[] b5 = b(this.k.get(i5).a());
                                        com.android.chileaf.util.b.a("mSingleTapRecords index:%d values:%s", Integer.valueOf(i5), com.android.chileaf.bluetooth.connect.h1.a.a(b5));
                                        for (int i6 = 0; i6 < b5.length / 4; i6++) {
                                            long b6 = b(b5, i6 * 4, 4);
                                            this.p.add(new HistoryOfRecord(b6, com.android.chileaf.util.a.a(b6)));
                                        }
                                    }
                                    i(bluetoothDevice2, this.p);
                                    this.p.clear();
                                    this.k.clear();
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                    }
                    if (this.o == null) {
                        this.o = new ArrayList();
                    }
                    if (intValue == 64) {
                        this.k.add(data2);
                    }
                    if (intValue == 65) {
                        for (int i7 = 0; i7 < this.k.size(); i7++) {
                            byte[] b7 = b(this.k.get(i7).a());
                            com.android.chileaf.util.b.a("mIntervalSteps index:%d values:%s", Integer.valueOf(i7), com.android.chileaf.bluetooth.connect.h1.a.a(b7));
                            for (int i8 = 0; i8 < b7.length / 8; i8++) {
                                int i9 = i8 * 8;
                                this.o.add(new IntervalStep(com.android.chileaf.util.a.a(b(b7, i9, 4)), a(b7, i9 + 4, 4)));
                            }
                        }
                        e(bluetoothDevice2, this.o);
                        this.o.clear();
                        this.k.clear();
                        return;
                    }
                    return;
                }
            }
            if (this.n == null) {
                this.n = new ArrayList();
            }
            if (intValue == 34) {
                if (!this.f1190j) {
                    this.f1188h = b(a, 3, 4);
                    this.f1190j = true;
                }
                this.k.add(data2);
            }
            if (intValue == 35) {
                for (int i10 = 0; i10 < this.k.size(); i10++) {
                    byte[] b8 = b(this.k.get(i10).a());
                    com.android.chileaf.util.b.a("mHistoryOfHeartRates index:%d values:%s", Integer.valueOf(i10), com.android.chileaf.bluetooth.connect.h1.a.a(b8));
                    for (int i11 = 4; i11 < b8.length; i11++) {
                        this.n.add(new HistoryOfHeartRate(com.android.chileaf.util.a.a(this.f1188h), a(b8, i11, 1)));
                        this.f1188h++;
                    }
                }
                c(bluetoothDevice2, this.n);
                this.n.clear();
                this.k.clear();
                this.f1190j = false;
                this.f1188h = 0;
            }
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

    public void a(boolean z) {
        this.f1189i = z;
    }

    public void a(int i2) {
        List<HistoryOfRecord> list;
        if (i2 == 2) {
            List<HistoryOfSport> list2 = this.l;
            if (list2 != null) {
                list2.clear();
            }
        } else if (i2 == 4) {
            List<HistoryOfRecord> list3 = this.m;
            if (list3 != null) {
                list3.clear();
            }
        } else if (i2 == 6) {
            List<HistoryOfHeartRate> list4 = this.n;
            if (list4 != null) {
                list4.clear();
            }
            this.f1190j = false;
            this.f1188h = 0;
        } else if (i2 == 8) {
            List<IntervalStep> list5 = this.o;
            if (list5 != null) {
                list5.clear();
            }
        } else if (i2 == 16 && (list = this.p) != null) {
            list.clear();
        }
        this.k.clear();
    }

    private void a(byte[] bArr) {
        for (int i2 = 0; i2 < bArr.length / 10; i2++) {
            int i3 = i2 * 10;
            long b = b(bArr, i3, 4);
            long b2 = b(bArr, i3 + 4, 3);
            long b3 = b(bArr, i3 + 7, 3);
            this.l.add(new HistoryOfSport(com.android.chileaf.util.a.a(b), b2, b3));
        }
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
