package f.a.a.a.b;

import aicare.net.cn.iweightlibrary.entity.DecimalInfo;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/* compiled from: ParseData */
public class d {
    public static double a(double d) {
        if (d > 100.0d) {
            return 0.0d;
        }
        return d;
    }

    public static int a(byte b) {
        return Integer.parseInt(c(b), 2);
    }

    public static String b(byte b) {
        String hexString = Integer.toHexString(Integer.parseInt(c(b), 2));
        StringBuilder sb = new StringBuilder();
        if (hexString.length() == 1) {
            sb.append("0");
        }
        sb.append(hexString);
        return sb.toString().toUpperCase();
    }

    public static byte[] b(int i2) {
        byte[] bArr = new byte[2];
        bArr[1] = (byte) (i2 & 255);
        bArr[0] = (byte) ((i2 >> 8) & 255);
        return bArr;
    }

    public static String c(byte b) {
        return BuildConfig.FLAVOR + ((byte) ((b >> 7) & 1)) + ((byte) ((b >> 6) & 1)) + ((byte) ((b >> 5) & 1)) + ((byte) ((b >> 4) & 1)) + ((byte) ((b >> 3) & 1)) + ((byte) ((b >> 2) & 1)) + ((byte) ((b >> 1) & 1)) + ((byte) (b & 1));
    }

    public static String d(double d, DecimalInfo decimalInfo) {
        long pow = (long) Math.pow(10.0d, (double) decimalInfo.getStDecimal());
        double pow2 = (double) ((long) Math.pow(10.0d, (double) decimalInfo.getSourceDecimal()));
        Double.isNaN(pow2);
        double d2 = ((d * 22046.0d) / 10000.0d) / pow2;
        double d3 = (double) pow;
        Double.isNaN(d3);
        long j2 = (long) ((d2 * d3) + 0.5d);
        int lbGraduation = decimalInfo.getLbGraduation();
        if (lbGraduation != 0) {
            long j3 = (long) lbGraduation;
            j2 = (j2 / j3) * j3;
        }
        float f2 = (float) pow;
        float f3 = (((float) j2) * 1.0f) / f2;
        long j4 = ((long) f3) / 14;
        float round = (((float) Math.round((f3 - ((float) (14 * j4))) * f2)) * 1.0f) / f2;
        return String.valueOf(j4) + ":" + round;
    }

    public static float c(int i2, int i3, byte[] bArr) {
        float f2 = (float) (((bArr[i2] & 255) << 8) + (bArr[i3] & 255));
        c.a((Class<?>) d.class, "data = " + f2);
        return f2;
    }

    public static int a(int i2, int i3, int i4, byte[] bArr) {
        int i5 = (bArr[i3] & 255) << 8;
        byte b = ((bArr[i2] & 255) << 16) | i5 | (bArr[i4] & 255);
        c.a((Class<?>) d.class, "data = " + b);
        return b;
    }

    public static String c(double d, DecimalInfo decimalInfo) {
        long pow = (long) Math.pow(10.0d, (double) decimalInfo.getLbDecimal());
        double pow2 = (double) ((long) Math.pow(10.0d, (double) decimalInfo.getSourceDecimal()));
        Double.isNaN(pow2);
        double d2 = ((d * 22046.0d) / 10000.0d) / pow2;
        double d3 = (double) pow;
        Double.isNaN(d3);
        long j2 = (long) ((d2 * d3) + 0.5d);
        int lbGraduation = decimalInfo.getLbGraduation();
        if (lbGraduation != 0) {
            long j3 = (long) lbGraduation;
            j2 = (j2 / j3) * j3;
        }
        return String.valueOf((((float) j2) * 1.0f) / ((float) pow));
    }

    public static int a(int i2, int i3, byte[] bArr) {
        int i4 = ((bArr[i2] & 255) << 8) + (bArr[i3] & 255);
        c.a((Class<?>) d.class, "getDataInt = " + i4);
        return i4;
    }

    public static int a(byte b, byte b2) {
        int i2 = ((b & 255) << 8) + (b2 & 255);
        c.a((Class<?>) d.class, "getDataInt = " + i2);
        return i2;
    }

    public static String a() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date(System.currentTimeMillis()));
    }

    public static float b(int i2, int i3, byte[] bArr) {
        float f2 = (float) (((bArr[i2] & 255) << 8) + (bArr[i3] & 255));
        c.a((Class<?>) d.class, "data = " + f2);
        return f2 / 10.0f;
    }

    public static String b() {
        return new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date(System.currentTimeMillis()));
    }

    public static String a(String str) {
        StringBuilder sb = new StringBuilder();
        if (str.length() != 1) {
            return str;
        }
        sb.append("0");
        sb.append(str);
        return sb.toString();
    }

    public static byte[] b(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr2 == null) {
            return null;
        }
        byte[] copyOf = Arrays.copyOf(bArr, bArr.length + bArr2.length);
        System.arraycopy(bArr2, 0, copyOf, bArr.length, bArr2.length);
        return copyOf;
    }

    public static String b(double d, DecimalInfo decimalInfo) {
        return a((d / Math.pow(10.0d, (double) decimalInfo.getSourceDecimal())) * 2.0d, decimalInfo.getKgDecimal());
    }

    public static String a(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return BuildConfig.FLAVOR;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i2 = 0; i2 < bArr.length; i2++) {
            sb.append("0x");
            sb.append(a(b(bArr[i2])));
            if (i2 < bArr.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static byte[] b(String str) {
        String[] split = str.split(":");
        byte[] bArr = new byte[split.length];
        for (int i2 = 0; i2 < split.length; i2++) {
            bArr[i2] = Integer.valueOf(split[i2], 16).byteValue();
        }
        b(bArr);
        return bArr;
    }

    public static byte[] b(byte[] bArr) {
        int length = bArr.length;
        for (int i2 = 0; i2 < length / 2; i2++) {
            byte b = bArr[i2];
            int i3 = (length - 1) - i2;
            bArr[i2] = bArr[i3];
            bArr[i3] = b;
        }
        return bArr;
    }

    public static String a(double d, int i2) {
        double doubleValue = new BigDecimal(d).setScale(i2, 4).doubleValue();
        StringBuilder sb = new StringBuilder();
        sb.append("#####0");
        if (i2 > 0) {
            for (int i3 = 0; i3 < i2; i3++) {
                if (i3 == 0) {
                    sb.append(".");
                }
                sb.append("0");
            }
        }
        return new DecimalFormat(sb.toString(), new DecimalFormatSymbols(Locale.US)).format(doubleValue);
    }

    public static String a(double d, DecimalInfo decimalInfo) {
        long pow = (long) Math.pow(10.0d, (double) decimalInfo.getKgDecimal());
        double pow2 = (double) ((long) Math.pow(10.0d, (double) decimalInfo.getSourceDecimal()));
        Double.isNaN(pow2);
        double d2 = d / pow2;
        double d3 = (double) pow;
        Double.isNaN(d3);
        long j2 = (long) ((d2 * d3) + 0.5d);
        if (decimalInfo.getKgGraduation() != 0) {
            j2 = (j2 / ((long) decimalInfo.getKgGraduation())) * ((long) decimalInfo.getKgGraduation());
        }
        return String.valueOf((((float) j2) * 1.0f) / ((float) pow));
    }

    public static String a(int i2) {
        return b(Integer.valueOf(i2).byteValue());
    }

    public static boolean a(byte[] bArr, byte[] bArr2) {
        if (bArr.length >= bArr2.length) {
            return false;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            if (bArr[i3] == bArr2[i3]) {
                i2++;
            }
        }
        if (i2 == bArr.length) {
            return true;
        }
        return false;
    }
}
