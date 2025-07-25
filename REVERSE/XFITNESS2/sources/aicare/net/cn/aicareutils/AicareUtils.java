package aicare.net.cn.aicareutils;

import java.util.Random;

public class AicareUtils {
    private static final byte[] a = {-83, 1};

    public static byte[] a() {
        byte[] bArr = new byte[18];
        byte[] bArr2 = a;
        bArr[0] = bArr2[0];
        bArr[1] = bArr2[1];
        Random random = new Random();
        for (int i2 = 2; i2 < 18; i2++) {
            bArr[i2] = Integer.valueOf(random.nextInt(256)).byteValue();
        }
        return bArr;
    }

    public static native boolean compareAddress(String str);

    public static native boolean compareBytes(byte[] bArr, byte[] bArr2);

    public static native boolean compareVersion(String str, String str2);

    public static native byte[] decrypt(byte[] bArr, boolean z);

    public static native byte[] encrypt(byte[] bArr, boolean z);
}
