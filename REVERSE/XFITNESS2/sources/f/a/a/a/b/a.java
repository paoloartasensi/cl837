package f.a.a.a.b;

import aicare.net.cn.aicareutils.AicareUtils;
import aicare.net.cn.iweightlibrary.entity.AlgorithmInfo;
import aicare.net.cn.iweightlibrary.entity.BM09Data;
import aicare.net.cn.iweightlibrary.entity.BM15Data;
import aicare.net.cn.iweightlibrary.entity.BodyFatData;
import aicare.net.cn.iweightlibrary.entity.BroadData;
import aicare.net.cn.iweightlibrary.entity.DecimalInfo;
import aicare.net.cn.iweightlibrary.entity.User;
import aicare.net.cn.iweightlibrary.entity.WeightData;
import android.bluetooth.BluetoothDevice;
import android.os.ParcelUuid;
import android.util.SparseArray;
import cn.net.aicare.algorithmutil.AlgorithmUtil;
import f.a.a.a.a.c;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: AicareBleConfig */
public class a {
    private static final byte[] a = {1, 1, 27};
    private static final byte[] b = {1, 1, 26};
    private static final byte[] c = {1, 1, 29};
    private static boolean d = false;
    private static byte e = 2;

    /* renamed from: f  reason: collision with root package name */
    private static BodyFatData f1633f = null;

    /* renamed from: g  reason: collision with root package name */
    private static AlgorithmInfo f1634g = null;

    /* renamed from: h  reason: collision with root package name */
    private static byte[] f1635h = null;

    /* renamed from: i  reason: collision with root package name */
    private static byte[] f1636i = null;

    static {
        try {
            System.loadLibrary("aicare-lib");
            c.b("AicareBleConfig", "load libs!");
        } catch (Exception unused) {
            c.b("AicareBleConfig", "not found libs!");
        }
    }

    public static byte[] a(byte b2, User user, byte b3) {
        byte[] bArr = new byte[8];
        bArr[0] = -84;
        bArr[1] = e;
        if (user != null) {
            c.b("AicareBleConfig", "syncUser: " + user.toString());
        }
        if (b2 == -9) {
            bArr[2] = -9;
            bArr[6] = -52;
        } else if (b2 == -1) {
            bArr[2] = -1;
            bArr[6] = -49;
        } else if (b2 == 2) {
            bArr[2] = -3;
            bArr[3] = 2;
            bArr[6] = -49;
        } else if (b2 == 6) {
            bArr[2] = -2;
            bArr[3] = 6;
            bArr[4] = b3;
            bArr[6] = -52;
        } else if (b2 == -6) {
            bArr[2] = -6;
            bArr[3] = (byte) user.getId();
            bArr[6] = -52;
        } else if (b2 == -5) {
            bArr[2] = -5;
            bArr[3] = (byte) user.getSex();
            bArr[4] = Integer.valueOf(user.getAge()).byteValue();
            bArr[5] = Integer.valueOf(user.getHeight()).byteValue();
            bArr[6] = -52;
        } else if (b2 == -4) {
            String[] split = d.b().split(":");
            bArr[2] = -4;
            bArr[3] = Integer.valueOf(split[0]).byteValue();
            bArr[4] = Integer.valueOf(split[1]).byteValue();
            bArr[5] = Integer.valueOf(split[2]).byteValue();
            bArr[6] = -52;
        } else if (b2 == -3) {
            String[] split2 = d.a().split("-");
            bArr[2] = -3;
            bArr[3] = Integer.valueOf(split2[0].substring(2, 4)).byteValue();
            bArr[4] = Integer.valueOf(split2[1]).byteValue();
            bArr[5] = Integer.valueOf(split2[2]).byteValue();
            bArr[6] = -52;
        }
        bArr[7] = a(bArr, 2, 7);
        c.c("AicareBleConfig", "initCmd: " + d.a(bArr));
        return bArr;
    }

    private static boolean b(byte[] bArr, byte[] bArr2) {
        if (bArr.length == 0 || bArr2.length == 0) {
            return false;
        }
        for (int i2 = 0; i2 < bArr2.length; i2++) {
            if (bArr2[i2] != bArr[i2]) {
                return false;
            }
        }
        return true;
    }

    private static byte[] c(String str, byte[] bArr) {
        byte[] copyOf = Arrays.copyOf(d.b(str), 10);
        copyOf[6] = 11;
        copyOf[7] = -1;
        copyOf[8] = 38;
        copyOf[9] = 8;
        if (d.a(copyOf, bArr)) {
            return Arrays.copyOfRange(bArr, copyOf.length, bArr.length);
        }
        return null;
    }

    private static boolean d(List<ParcelUuid> list) {
        return !e(list) && list.contains(ParcelUuid.fromString("0000d618-0000-1000-8000-00805f9b34fb")) && list.contains(ParcelUuid.fromString("0000ffb0-0000-1000-8000-00805f9b34fb"));
    }

    private static <T> boolean e(List<T> list) {
        return list == null || list.size() == 0;
    }

    private static byte[] f(byte[] bArr) {
        byte[] bArr2 = f1636i;
        if (bArr2 != null) {
            char c2 = bArr2[0] == -84 ? (char) 3 : 65535;
            if (f1636i[0] == -82) {
                c2 = 4;
            }
            if (c2 == 65535) {
                return bArr;
            }
            byte b2 = bArr[0];
            byte[] bArr3 = f1636i;
            if (b2 - bArr3[c2] == 1) {
                byte[] b3 = d.b(bArr3, bArr);
                f1636i = null;
                return b3;
            }
            f1636i = null;
        } else {
            f1636i = bArr;
        }
        return bArr;
    }

    private static SparseArray<Object> g(byte[] bArr) {
        c.b((Class<?>) a.class, "getData: " + d.a(bArr));
        SparseArray<Object> sparseArray = new SparseArray<>();
        byte b2 = bArr[2];
        if (b2 == -6) {
            String a2 = a((byte) -6, bArr, 3);
            if (f1633f == null) {
                f1633f = new BodyFatData();
            }
            f1633f.setTime(a2);
            sparseArray.put(5, a2);
            return sparseArray;
        } else if (b2 == -5) {
            String a3 = a((byte) -5, bArr, 3);
            if (f1633f == null) {
                f1633f = new BodyFatData();
            }
            f1633f.setDate(a3);
            sparseArray.put(4, a3);
            return sparseArray;
        } else if (b2 == -4) {
            return q(bArr);
        } else {
            if (b2 == -3) {
                return b(bArr);
            }
            if (b2 != -2) {
                return sparseArray;
            }
            return d(bArr);
        }
    }

    public static SparseArray<Object> h(byte[] bArr) {
        c.b((Class<?>) a.class, "getDatas: " + d.a(bArr));
        SparseArray<Object> sparseArray = new SparseArray<>();
        if (a(bArr)) {
            switch (bArr[6]) {
                case -54:
                case -50:
                    return r(bArr);
                case -53:
                    return g(bArr);
                case -52:
                    return j(bArr);
                case -49:
                    if (bArr[2] == -2) {
                        return n(bArr);
                    }
                    if (bArr[2] == -4) {
                        return p(bArr);
                    }
                    return sparseArray;
                default:
                    return sparseArray;
            }
        } else if (w(bArr)) {
            return c(bArr);
        } else {
            if (u(bArr)) {
                return m(f(bArr));
            }
            byte[] y = y(bArr);
            if (y == null) {
                return sparseArray;
            }
            if (y[0] != -84) {
                return y[0] == -82 ? i(y) : sparseArray;
            }
            if (y[2] == -9) {
                String b2 = b(y, 3, 2015);
                c.b("AicareBleConfig", "version: " + b2);
                sparseArray.put(2, b2);
                return sparseArray;
            } else if (y[2] == -2 && y[6] == -49) {
                return n(y);
            } else {
                return sparseArray;
            }
        }
    }

    private static SparseArray<Object> i(byte[] bArr) {
        c.b((Class<?>) a.class, "getDecimalInfo: " + d.a(bArr));
        SparseArray<Object> sparseArray = new SparseArray<>();
        sparseArray.put(10, a(bArr, 4));
        return sparseArray;
    }

    private static SparseArray<Object> j(byte[] bArr) {
        c.b((Class<?>) a.class, "getDeviceStatus: " + d.a(bArr));
        SparseArray<Object> sparseArray = new SparseArray<>();
        if (bArr[2] == -9) {
            sparseArray.put(2, b(bArr, 3, 2015));
            return sparseArray;
        }
        byte b2 = bArr[3];
        if (b2 == 16) {
            c.b("AicareBleConfig", "DATA_SEND_END");
            BodyFatData bodyFatData = f1633f;
            if (bodyFatData == null || bodyFatData.getWeight() <= 0.0d) {
                return sparseArray;
            }
            sparseArray.put(8, f1633f);
            f1633f = null;
            return sparseArray;
        } else if (b2 == 27) {
            sparseArray.put(1, 22);
            return sparseArray;
        } else if (b2 == 29) {
            return k(bArr);
        } else {
            if (b2 == 30) {
                return k(bArr);
            }
            switch (b2) {
                case 0:
                    sparseArray.put(1, 0);
                    return sparseArray;
                case 1:
                    sparseArray.put(1, 1);
                    return sparseArray;
                case 2:
                    sparseArray.put(1, 2);
                    return sparseArray;
                case 3:
                    sparseArray.put(1, 3);
                    return sparseArray;
                case 4:
                    sparseArray.put(1, 4);
                    return sparseArray;
                case 5:
                    sparseArray.put(1, 5);
                    return sparseArray;
                case 6:
                    return e(bArr);
                case 7:
                    sparseArray.put(1, 8);
                    return sparseArray;
                case 8:
                    sparseArray.put(1, 9);
                    return sparseArray;
                case 9:
                    sparseArray.put(1, 10);
                    return sparseArray;
                case 10:
                    sparseArray.put(1, 11);
                    return sparseArray;
                default:
                    return sparseArray;
            }
        }
    }

    private static SparseArray<Object> k(byte[] bArr) {
        c.b((Class<?>) a.class, "getDid: " + d.a(bArr));
        SparseArray<Object> sparseArray = new SparseArray<>();
        byte b2 = bArr[3];
        if (b2 == 29) {
            byte b3 = bArr[4];
            if (b3 == 0) {
                sparseArray.put(1, 24);
            } else if (b3 == 1) {
                sparseArray.put(1, 23);
            }
        } else if (b2 == 30) {
            sparseArray.put(9, Integer.valueOf(d.a(4, 5, bArr)));
        }
        return sparseArray;
    }

    private static int l(byte[] bArr) {
        if (bArr.length >= 7 && bArr[6] <= 1 && bArr[6] >= 0) {
            return bArr[6];
        }
        return 0;
    }

    private static SparseArray<Object> m(byte[] bArr) {
        double d2;
        byte[] bArr2 = bArr;
        Class<a> cls = a.class;
        c.b((Class<?>) cls, "getHistoryData: " + d.a(bArr));
        c.b((Class<?>) cls, "b.length = " + bArr2.length);
        SparseArray<Object> sparseArray = new SparseArray<>();
        if (bArr2.length > 20) {
            int i2 = bArr2[0] == -84 ? 6 : -1;
            if (bArr2[0] == -82) {
                i2 = 5;
            }
            if (i2 == -1) {
                return sparseArray;
            }
            String a2 = a((byte) -5, bArr2, i2);
            int i3 = i2 + 3;
            String a3 = a((byte) -6, bArr2, i3);
            int i4 = i3 + 3;
            if (bArr2[0] == -84) {
                int i5 = i4 + 1;
                d2 = (double) d.a(i4, i5, bArr2);
                i4 = i5;
            } else {
                d2 = -1.0d;
            }
            if (bArr2[0] == -82) {
                int i6 = i4 + 1;
                int i7 = i6 + 1;
                d2 = (double) d.a(i4, i6, i7, bArr2);
                i4 = i7;
            }
            if (d2 == -1.0d) {
                return sparseArray;
            }
            int i8 = i4 + 1;
            int i9 = i8 + 1;
            double a4 = (double) d.a(i8, i9, bArr2);
            Double.isNaN(a4);
            double d3 = a4 / 10.0d;
            int i10 = i9 + 1;
            int i11 = i10 + 1;
            double a5 = (double) d.a(i10, i11, bArr2);
            Double.isNaN(a5);
            double a6 = d.a(a5 / 10.0d);
            int i12 = i11 + 1;
            int i13 = i12 + 1;
            double a7 = (double) d.a(i12, i13, bArr2);
            Double.isNaN(a7);
            double d4 = a7 / 10.0d;
            int i14 = i13 + 1 + 1;
            int i15 = i14 + 1;
            int a8 = d.a(i14, i15, bArr2);
            int i16 = i15 + 1;
            int i17 = i16 + 1;
            double a9 = (double) d.a(i16, i17, bArr2);
            Double.isNaN(a9);
            double a10 = d.a(a9 / 10.0d);
            int i18 = i17 + 1;
            int i19 = i18 + 1;
            double a11 = (double) d.a(i18, i19, bArr2);
            int i20 = i19 + 1;
            int i21 = i20 + 1;
            double a12 = (double) d.a(i20, i21, bArr2);
            Double.isNaN(a12);
            double d5 = a12 / 10.0d;
            int i22 = i21 + 1;
            int i23 = i22 + 1;
            double a13 = (double) d.a(i22, i23, bArr2);
            Double.isNaN(a13);
            double a14 = d.a(a13 / 10.0d);
            int i24 = i23 + 1;
            int a15 = d.a(bArr2[i24]);
            int i25 = i24 + 1;
            int i26 = i25 + 1;
            double a16 = (double) d.a(i25, i26, bArr2);
            Double.isNaN(a16);
            int i27 = i26 + 1;
            int i28 = i27 + 1;
            int i29 = i28 + 1;
            int i30 = i29 + 1;
            int i31 = i30 + 1;
            BodyFatData bodyFatData = new BodyFatData(a2, a3, d2, d3, a6, d4, a8, a10, a11, d5, a14, a15, a16 / 10.0d, d.a(bArr2[i27]), d.a(bArr2[i28]), d.a(bArr2[i29]), d.a(bArr2[i30]), d.a(i31, i31 + 1, bArr2), (DecimalInfo) null);
            if (d) {
                sparseArray.put(7, bodyFatData);
            }
        }
        return sparseArray;
    }

    private static SparseArray<Object> n(byte[] bArr) {
        c.b((Class<?>) a.class, "getHistoryStatus: " + d.a(bArr));
        SparseArray<Object> sparseArray = new SparseArray<>();
        byte b2 = bArr[3];
        if (b2 == 0) {
            sparseArray.put(1, 16);
        } else if (b2 == 1) {
            d = true;
            sparseArray.put(1, 17);
        } else if (b2 == 2) {
            d = false;
            sparseArray.put(1, 18);
        }
        return sparseArray;
    }

    private static List<Integer> o(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < bArr.length; i2++) {
            if (bArr[i2] == -84 || bArr[i2] == -82) {
                arrayList.add(Integer.valueOf(i2));
            }
        }
        return arrayList;
    }

    private static SparseArray<Object> p(byte[] bArr) {
        c.b((Class<?>) a.class, "getSyncUserStatus: " + d.a(bArr));
        SparseArray<Object> sparseArray = new SparseArray<>();
        byte b2 = bArr[3];
        if (b2 == 0) {
            sparseArray.put(1, 12);
        } else if (b2 == 1) {
            sparseArray.put(1, 15);
        } else if (b2 == 2) {
            sparseArray.put(1, 14);
        } else if (b2 == 3) {
            sparseArray.put(1, 15);
        }
        return sparseArray;
    }

    private static SparseArray<Object> q(byte[] bArr) {
        c.b((Class<?>) a.class, "getUserId: " + d.a(bArr));
        SparseArray<Object> sparseArray = new SparseArray<>();
        if (bArr[3] == Byte.MAX_VALUE) {
            sparseArray.put(1, 19);
        } else {
            sparseArray.put(3, String.valueOf(d.a(bArr[3])));
        }
        return sparseArray;
    }

    private static SparseArray<Object> r(byte[] bArr) {
        c.b((Class<?>) a.class, "getWeiData: " + d.a(bArr));
        SparseArray<Object> sparseArray = new SparseArray<>();
        sparseArray.put(0, s(bArr));
        return sparseArray;
    }

    public static WeightData s(byte[] bArr) {
        c.b((Class<?>) a.class, "getWeightData: " + d.a(bArr));
        if (!a(bArr)) {
            return null;
        }
        if (bArr[6] != -50 && bArr[6] != -54) {
            return null;
        }
        double a2 = (double) d.a(2, 3, bArr);
        double d2 = Double.MAX_VALUE;
        int i2 = 1;
        if (bArr[1] == 3 || bArr[1] == 1) {
            d2 = a(4, 5, bArr);
        }
        double d3 = d2;
        byte b2 = bArr[6];
        if (bArr[6] == -54) {
            if (f1633f == null) {
                f1633f = new BodyFatData();
            }
            f1633f.setWeight(a2);
            i2 = 2;
        }
        return new WeightData(i2, a2, d3, (DecimalInfo) null);
    }

    private static boolean t(byte[] bArr) {
        return bArr == null || bArr.length == 0;
    }

    private static boolean u(byte[] bArr) {
        c.b((Class<?>) a.class, "isHistoryData");
        if (bArr.length != 20 || (!v(bArr) && bArr[0] != 1)) {
            byte[] bArr2 = f1635h;
            if (bArr2 != null && v(bArr2)) {
                byte[] bArr3 = f1635h;
                if ((bArr3[3] == 0 || bArr3[4] == 0) && bArr[0] == 1) {
                    f1635h = null;
                    return true;
                }
            }
            f1635h = null;
            return false;
        }
        f1635h = bArr;
        return true;
    }

    private static boolean v(byte[] bArr) {
        if (!(bArr[0] == -84 && ((bArr[1] == 2 || bArr[1] == 3) && bArr[2] == -1))) {
            if (bArr[0] == -82) {
                return (bArr[2] == 2 || bArr[2] == 3) && bArr[3] == 5;
            }
            return false;
        }
    }

    private static boolean w(byte[] bArr) {
        if (bArr[0] != -82) {
            return false;
        }
        if ((bArr[2] == 2 || bArr[2] == 3) && bArr[1] + 2 < bArr.length && a(bArr, 2, bArr[1] + 2) == bArr[bArr.length - 1]) {
            return true;
        }
        return false;
    }

    private static void x(byte[] bArr) {
        if (b(bArr, a)) {
            e = 2;
        } else if (b(bArr, c) || b(bArr, b)) {
            e = 2;
        } else {
            e = 2;
        }
    }

    private static byte[] y(byte[] bArr) {
        List<Integer> o = o(bArr);
        if (o.isEmpty()) {
            return null;
        }
        for (int i2 = 0; i2 < o.size(); i2++) {
            int intValue = o.get(i2).intValue();
            int i3 = intValue + 8;
            if (bArr.length >= i3 && bArr[intValue] == -84) {
                int i4 = intValue + 2;
                if (bArr[i4] == -9) {
                    byte[] bArr2 = new byte[8];
                    System.arraycopy(bArr, intValue, bArr2, 0, 8);
                    return bArr2;
                } else if (bArr[i4] == -2 && bArr[intValue + 6] == -49) {
                    byte[] bArr3 = new byte[8];
                    System.arraycopy(bArr, intValue, bArr3, 0, 8);
                    return bArr3;
                }
            } else if (bArr[intValue] == -82 && bArr[intValue + 3] == 4) {
                int i5 = intValue + 1;
                if (bArr[i5] == 5) {
                    if (bArr.length >= i3) {
                        byte[] bArr4 = new byte[8];
                        System.arraycopy(bArr, intValue, bArr4, 0, 8);
                        return bArr4;
                    }
                } else if (bArr[i5] == 6 && bArr.length >= intValue + 9) {
                    byte[] bArr5 = new byte[9];
                    System.arraycopy(bArr, intValue, bArr5, 0, 9);
                    return bArr5;
                }
            }
        }
        return null;
    }

    private static SparseArray<Object> d(byte[] bArr) {
        c.b((Class<?>) a.class, "getBodyFatData: " + d.a(bArr));
        SparseArray<Object> sparseArray = new SparseArray<>();
        if (f1633f == null) {
            f1633f = new BodyFatData();
        }
        byte b2 = bArr[3];
        if (b2 != -4) {
            switch (b2) {
                case 0:
                    f1633f.setWeight((double) d.a(4, 5, bArr));
                    break;
                case 1:
                    double a2 = (double) d.a(4, 5, bArr);
                    Double.isNaN(a2);
                    f1633f.setBmi(a2 / 10.0d);
                    break;
                case 2:
                    double a3 = (double) d.a(4, 5, bArr);
                    Double.isNaN(a3);
                    f1633f.setBfr(a3 / 10.0d);
                    break;
                case 3:
                    double a4 = (double) d.a(4, 5, bArr);
                    Double.isNaN(a4);
                    f1633f.setSfr(a4 / 10.0d);
                    break;
                case 4:
                    f1633f.setUvi(d.a(4, 5, bArr));
                    break;
                case 5:
                    double a5 = (double) d.a(4, 5, bArr);
                    Double.isNaN(a5);
                    f1633f.setRom(a5 / 10.0d);
                    break;
                case 6:
                    f1633f.setBmr((double) d.a(4, 5, bArr));
                    break;
                case 7:
                    double a6 = (double) d.a(4, 5, bArr);
                    Double.isNaN(a6);
                    f1633f.setBm(a6 / 10.0d);
                    break;
                case 8:
                    double a7 = (double) d.a(4, 5, bArr);
                    Double.isNaN(a7);
                    f1633f.setVwc(a7 / 10.0d);
                    break;
                case 9:
                    f1633f.setBodyAge(d.a(4, 5, bArr));
                    break;
                case 10:
                    double a8 = (double) d.a(4, 5, bArr);
                    Double.isNaN(a8);
                    f1633f.setPp(a8 / 10.0d);
                    break;
            }
        } else {
            c.b("AicareBleConfig", "DATA_SEND_OVER");
        }
        return sparseArray;
    }

    private static SparseArray<Object> e(byte[] bArr) {
        c.b((Class<?>) a.class, "getChangeUnitStatus: " + d.a(bArr));
        SparseArray<Object> sparseArray = new SparseArray<>();
        byte b2 = bArr[4];
        if (b2 == -2) {
            sparseArray.put(1, 6);
        } else if (b2 == -1) {
            sparseArray.put(1, 7);
        }
        return sparseArray;
    }

    private static boolean b(List<ParcelUuid> list) {
        return !e(list) && list.contains(ParcelUuid.fromString("0000feb3-0000-1000-8000-00805f9b34fb")) && list.contains(ParcelUuid.fromString("0000ffb0-0000-1000-8000-00805f9b34fb"));
    }

    private static SparseArray<Object> b(byte[] bArr) {
        c.b((Class<?>) a.class, "getADC: " + d.a(bArr));
        SparseArray<Object> sparseArray = new SparseArray<>();
        byte b2 = bArr[3];
        if (b2 == -1) {
            sparseArray.put(1, 21);
        } else if (b2 == 0) {
            sparseArray.put(1, 20);
            int a2 = d.a(4, 5, bArr);
            if (a2 > 0 && f1634g == null) {
                AlgorithmInfo algorithmInfo = new AlgorithmInfo();
                f1634g = algorithmInfo;
                algorithmInfo.setAlgorithmId(a2);
            }
        } else if (b2 == 1) {
            int a3 = d.a(4, 5, bArr);
            AlgorithmInfo algorithmInfo2 = f1634g;
            if (algorithmInfo2 != null) {
                algorithmInfo2.setAdc(a3);
                BodyFatData bodyFatData = f1633f;
                if (bodyFatData != null) {
                    f1634g.setWeight(bodyFatData.getWeight());
                    f1633f = null;
                }
                sparseArray.put(11, f1634g);
                f1634g = null;
            } else {
                if (f1633f == null) {
                    f1633f = new BodyFatData();
                }
                f1633f.setAdc(a3);
                sparseArray.put(6, Integer.valueOf(a3));
            }
        }
        return sparseArray;
    }

    private static boolean c(List<ParcelUuid> list) {
        return !e(list) && list.contains(ParcelUuid.fromString("0000ffb0-0000-1000-8000-00805f9b34fb"));
    }

    private static SparseArray<Object> c(byte[] bArr) {
        c.b((Class<?>) a.class, "getBleData: " + d.a(bArr));
        SparseArray<Object> sparseArray = new SparseArray<>();
        byte b2 = bArr[3];
        if (b2 == 1) {
            return b(bArr, 1);
        }
        if (b2 == 2) {
            return b(bArr, 2);
        }
        if (b2 != 4) {
            return sparseArray;
        }
        return i(bArr);
    }

    public static byte[] a(byte b2) {
        byte[] bArr = new byte[6];
        bArr[0] = -82;
        bArr[1] = 3;
        bArr[2] = e;
        bArr[3] = b2;
        bArr[4] = 1;
        bArr[5] = a(bArr, 2, 5);
        return bArr;
    }

    private static byte a(byte[] bArr, int i2, int i3) {
        int i4 = 0;
        while (i2 < i3) {
            i4 += bArr[i2];
            i2++;
        }
        return (byte) (i4 & 255);
    }

    private static boolean a(byte[] bArr) {
        if (bArr == null || bArr.length == 0 || bArr.length != 8 || bArr[0] != -84) {
            return false;
        }
        if (bArr[1] != 2 && bArr[1] != 3 && bArr[1] != 1 && bArr[1] != 0) {
            return false;
        }
        byte a2 = a(bArr, 2, 7);
        c.b("AicareBleConfig", "result = " + a2);
        c.b("AicareBleConfig", "b[SUM_END] = " + bArr[7]);
        if (a2 == bArr[7]) {
            return true;
        }
        return false;
    }

    public static BroadData a(BluetoothDevice bluetoothDevice, int i2, byte[] bArr) {
        c a2 = c.a(bArr);
        if (a2 == null) {
            return null;
        }
        SparseArray<byte[]> a3 = a2.a();
        List<ParcelUuid> b2 = a2.b();
        boolean z = false;
        if (!e(b2)) {
            for (int i3 = 0; i3 < b2.size(); i3++) {
                c.b("AicareBleConfig", "uuid: " + b2.get(i3).getUuid().toString());
            }
        }
        if (a3 == null) {
            return null;
        }
        BroadData broadData = new BroadData();
        broadData.setAddress(bluetoothDevice.getAddress());
        broadData.setName(bluetoothDevice.getName());
        broadData.setRssi(i2);
        int keyAt = a3.keyAt(0);
        byte[] bArr2 = a3.get(keyAt);
        if (e(b2)) {
            byte[] b3 = d.b(keyAt);
            d.b(b3);
            if (b3[0] != -68) {
                return null;
            }
            broadData.setSpecificData(d.b(b3, bArr2));
            broadData.setBright(true);
            broadData.setDeviceType(15);
            return broadData;
        } else if (d(b2)) {
            c.b("AicareBleConfig", "JD manufacturerId: " + keyAt);
            if (keyAt != 684 && keyAt != 940 && keyAt != 65452) {
                return null;
            }
            c.b("AicareBleConfig", "specificData: " + d.a(bArr2));
            a(keyAt);
            if (l(bArr2) != 0) {
                z = true;
            }
            broadData.setBright(z);
            broadData.setDeviceType(e);
            return broadData;
        } else if (b(b2)) {
            c.b("AicareBleConfig", "ALI manufacturerId: " + keyAt);
            if (keyAt != 424) {
                return null;
            }
            c.b("AicareBleConfig", "specificData: " + d.a(bArr2));
            if (t(bArr2)) {
                return null;
            }
            if (!b(bArr2, a) && !b(bArr2, b) && !b(bArr2, c)) {
                return null;
            }
            x(bArr2);
            if (l(bArr2) != 0) {
                z = true;
            }
            broadData.setBright(z);
            broadData.setDeviceType(e);
            return broadData;
        } else if (c(b2)) {
            c.b("AicareBleConfig", "Aicare manufacturerId: " + keyAt);
            if (keyAt == 684 || keyAt == 940 || keyAt == 65452) {
                c.b("AicareBleConfig", "specificData: " + d.a(bArr2));
                a(keyAt);
                if (l(bArr2) != 0) {
                    z = true;
                }
                broadData.setBright(z);
                broadData.setDeviceType(e);
                return broadData;
            } else if (keyAt != 172 && keyAt != 428) {
                return null;
            } else {
                a(keyAt);
                broadData.setBright(true);
                broadData.setSpecificData(c(bluetoothDevice.getAddress(), bArr2));
                broadData.setDeviceType(e);
                return broadData;
            }
        } else if (keyAt == 2086) {
            broadData.setSpecificData(bArr2);
            broadData.setBright(true);
            broadData.setDeviceType(0);
            return broadData;
        } else if (!a(b2)) {
            return null;
        } else {
            byte[] b4 = d.b(keyAt);
            d.b(b4);
            broadData.setSpecificData(d.b(b4, bArr2));
            broadData.setBright(true);
            broadData.setDeviceType(9);
            return broadData;
        }
    }

    private static SparseArray<Object> b(byte[] bArr, int i2) {
        c.b((Class<?>) a.class, "getWeiData: " + d.a(bArr));
        SparseArray<Object> sparseArray = new SparseArray<>();
        double a2 = (double) d.a(4, 5, 6, bArr);
        double a3 = bArr[2] == 3 ? a(7, 8, bArr) : Double.MAX_VALUE;
        if (i2 == 2) {
            if (f1633f == null) {
                f1633f = new BodyFatData();
            }
            f1633f.setWeight(a2);
        }
        sparseArray.put(0, new WeightData(i2, a2, a3, (DecimalInfo) null));
        return sparseArray;
    }

    private static String b(byte[] bArr, int i2, int i3) {
        c.b((Class<?>) a.class, "getVersion: " + d.a(bArr));
        int a2 = (d.a(bArr[i2]) / 16) + i3;
        int i4 = i2 + 1;
        int a3 = d.a(bArr[i4]);
        float floatValue = new BigDecimal((double) (((float) d.a(bArr[i4 + 1])) / 10.0f)).setScale(1, 4).floatValue();
        return a2 + d.a(String.valueOf(d.a(bArr[i2]) % 16)) + d.a(String.valueOf(a3)) + "_" + floatValue;
    }

    public static byte[] b(byte[] bArr, boolean z) {
        return AicareUtils.encrypt(bArr, z);
    }

    public static BM15Data b(String str, byte[] bArr) {
        BM15Data bM15Data = new BM15Data();
        if (bArr != null && bArr.length > 11 && bArr[0] == -68) {
            bM15Data.setAddress(str);
            byte[] a2 = a(Arrays.copyOfRange(bArr, 3, 11), true);
            System.arraycopy(a2, 0, bArr, 3, a2.length);
            double a3 = (double) d.a(bArr[1]);
            Double.isNaN(a3);
            bM15Data.setVersion(String.valueOf(d.a(a3 / 10.0d, 1)));
            bM15Data.setAgreementType(bArr[2] >> 3);
            bM15Data.setUnitType(bArr[2] & 7);
            bM15Data.setWeight((double) d.c(3, 4, bArr));
            bM15Data.setAdc(d.a(5, 6, bArr));
            bM15Data.setTemp((double) d.b(7, 8, bArr));
            bM15Data.setAlgorithmId(d.a(bArr[9]));
            bM15Data.setDid(d.a(bArr[10]));
            bM15Data.setBleType(d.a((byte) 15));
        }
        return bM15Data;
    }

    private static void a(int i2) {
        if (i2 == 172) {
            e = 0;
        } else if (i2 == 428) {
            e = 1;
        } else if (i2 == 684) {
            e = 2;
        } else if (i2 == 940) {
            e = 3;
        }
    }

    private static boolean a(List<ParcelUuid> list) {
        return !e(list) && list.contains(ParcelUuid.fromString("0000ffa0-0000-1000-8000-00805f9b34fb"));
    }

    private static String a(byte b2, byte[] bArr, int i2) {
        Class<a> cls = a.class;
        c.b((Class<?>) cls, "getDateOrTime: " + d.a(bArr));
        StringBuilder sb = new StringBuilder();
        String valueOf = String.valueOf(d.a(bArr[i2]));
        int i3 = i2 + 1;
        String valueOf2 = String.valueOf(d.a(bArr[i3]));
        String valueOf3 = String.valueOf(d.a(bArr[i3 + 1]));
        if (b2 == -6) {
            sb.append(d.a(valueOf));
            sb.append(":");
            sb.append(d.a(valueOf2));
            sb.append(":");
            sb.append(d.a(valueOf3));
            c.b((Class<?>) cls, "MCU_TIME = " + sb.toString());
        } else if (b2 == -5) {
            sb.append("20");
            sb.append(d.a(valueOf));
            sb.append("-");
            sb.append(d.a(valueOf2));
            sb.append("-");
            sb.append(d.a(valueOf3));
            c.b((Class<?>) cls, "MCU_DATE = " + sb.toString());
        }
        return sb.toString();
    }

    private static DecimalInfo a(byte[] bArr, int i2) {
        byte b2;
        int i3;
        int i4 = i2 + 1;
        int i5 = bArr[i4] >> 4;
        byte b3 = bArr[i4] & 15;
        int i6 = i4 + 1;
        int i7 = bArr[i6] >> 4;
        byte b4 = bArr[i6] & 15;
        if (bArr[1] == 6) {
            int i8 = i6 + 1;
            b2 = bArr[i8] & 15;
            i3 = bArr[i8] >> 4;
        } else {
            i3 = 0;
            b2 = 0;
        }
        return new DecimalInfo(i5, b3, i7, b4, i3, b2);
    }

    private static double a(int i2, int i3, byte[] bArr) {
        c.b((Class<?>) a.class, "getTemp: " + d.a(bArr));
        byte byteValue = Integer.valueOf(bArr[i2] >> 4).byteValue();
        double a2 = (double) d.a(0, 1, new byte[]{Integer.valueOf(bArr[i2] & 15).byteValue(), bArr[i3]});
        Double.isNaN(a2);
        double d2 = a2 / 10.0d;
        return byteValue == 15 ? -d2 : d2;
    }

    public static byte[] a() {
        return AicareUtils.a();
    }

    public static byte[] a(byte[] bArr, boolean z) {
        return AicareUtils.decrypt(bArr, z);
    }

    public static boolean a(byte[] bArr, byte[] bArr2) {
        return AicareUtils.compareBytes(bArr, bArr2);
    }

    public static boolean a(String str, String str2) {
        return AicareUtils.compareVersion(str, str2);
    }

    public static boolean a(String str) {
        return AicareUtils.compareAddress(str);
    }

    public static String a(double d2, byte b2, DecimalInfo decimalInfo) {
        if (decimalInfo == null) {
            decimalInfo = new DecimalInfo(1, 1, 1, 1, 1, 1);
        }
        if (b2 == 1) {
            return d.c(d2, decimalInfo);
        }
        if (b2 == 2) {
            return d.d(d2, decimalInfo);
        }
        if (b2 != 3) {
            return d.a(d2, decimalInfo);
        }
        return d.b(d2, decimalInfo);
    }

    public static BM09Data a(String str, byte[] bArr) {
        BM09Data bM09Data = new BM09Data();
        if (bArr != null && bArr.length >= 18) {
            boolean z = false;
            byte[] b2 = d.b(a(Arrays.copyOfRange(bArr, 0, 16), false), Arrays.copyOfRange(bArr, 16, bArr.length));
            if (b2 == null) {
                return bM09Data;
            }
            bM09Data.setAddress(str);
            bM09Data.setAgreementType(b2[0] >> 4);
            bM09Data.setUnitType(b2[0] & 15);
            bM09Data.setDecimalInfo(a(b2, 0));
            bM09Data.setWeight((double) ((float) d.a(3, 4, 5, b2)));
            bM09Data.setAdc(d.a(6, 7, b2));
            bM09Data.setTemp((double) (((float) d.a(8, 9, b2)) / 10.0f));
            if ((b2[10] >> 6) == 1) {
                z = true;
            }
            bM09Data.setStable(z);
            bM09Data.setAlgorithmId(d.a((byte) (b2[10] & 63), b2[11]));
            bM09Data.setDid(d.a(12, 13, b2));
            bM09Data.setBleVersion(b(b2, 15, 2018));
            bM09Data.setBleType(d.a(b2[14]));
            bM09Data.setTimeMillis(System.currentTimeMillis());
        }
        return bM09Data;
    }

    public static BodyFatData a(WeightData weightData, int i2, int i3, int i4) {
        if (weightData.getAdc() <= 0) {
            BodyFatData bodyFatData = new BodyFatData();
            bodyFatData.setDate(d.a());
            bodyFatData.setTime(d.b());
            bodyFatData.setAdc(weightData.getAdc());
            bodyFatData.setWeight(weightData.getWeight());
            bodyFatData.setHeight(i4);
            bodyFatData.setSex(i2);
            bodyFatData.setAge(i3);
            bodyFatData.setDecimalInfo(weightData.getDecimalInfo());
            return bodyFatData;
        }
        int i5 = i2;
        int i6 = i3;
        int i7 = i4;
        cn.net.aicare.algorithmutil.BodyFatData bodyFatData2 = AlgorithmUtil.getBodyFatData(0, i2, i3, Double.valueOf(d.a(weightData.getWeight(), weightData.getDecimalInfo())).doubleValue(), i4, weightData.getAdc());
        return new BodyFatData(d.a(), d.b(), weightData.getWeight(), bodyFatData2.getBmi(), bodyFatData2.getBfr(), bodyFatData2.getSfr(), bodyFatData2.getUvi(), bodyFatData2.getRom(), (double) bodyFatData2.getBmr(), bodyFatData2.getBm(), bodyFatData2.getVwc(), bodyFatData2.getBodyAge(), bodyFatData2.getPp(), 0, i2, i3, i4, weightData.getAdc(), weightData.getDecimalInfo());
    }
}
