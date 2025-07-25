package com.android.chileaf.util;

import com.jeremyliao.liveeventbus.BuildConfig;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HexUtil {
    private static final Map<Character, byte[]> a = new HashMap<Character, byte[]>() {
        {
            put(243, new byte[]{111});
            put(211, new byte[]{79});
            put(237, new byte[]{105});
            put(205, new byte[]{73});
            put(250, new byte[]{117});
            put(218, new byte[]{85});
            put(199, new byte[]{Byte.MIN_VALUE});
            put(252, new byte[]{-127});
            put(233, new byte[]{-126});
            put(226, new byte[]{-125});
            put(228, new byte[]{-124});
            put(224, new byte[]{-123});
            put(227, new byte[]{-122});
            put(231, new byte[]{-121});
            put(234, new byte[]{-120});
            put(235, new byte[]{-119});
            put(207, new byte[]{-117});
            put(232, new byte[]{-118});
            put(206, new byte[]{-116});
            put(204, new byte[]{-115});
            put(195, new byte[]{-114});
            put(196, new byte[]{-113});
            put(201, new byte[]{-112});
            put(230, new byte[]{-111});
            put(198, new byte[]{-110});
            put(244, new byte[]{-109});
            put(246, new byte[]{-108});
            put(242, new byte[]{-107});
            put(251, new byte[]{-106});
            put(249, new byte[]{-105});
            put(255, new byte[]{-104});
            put(214, new byte[]{-103});
            put(220, new byte[]{-102});
            put(162, new byte[]{-101});
            put(163, new byte[]{-100});
            put(165, new byte[]{-99});
            put(402, new byte[]{-97});
            put(225, new byte[]{-96});
            put(241, new byte[]{-92});
            put(209, new byte[]{-91});
            put(170, new byte[]{-90});
            put(186, new byte[]{-89});
            put(191, new byte[]{-88});
            put(172, new byte[]{-86});
            put(189, new byte[]{-85});
            put(188, new byte[]{-84});
            put(161, new byte[]{-83});
            put(171, new byte[]{-82});
            put(187, new byte[]{-81});
            put(176, new byte[]{-95, -29});
        }
    };

    public static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            char c = (char) b;
            if (c != 0) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static byte[] a(long j2) {
        return new byte[]{(byte) ((int) ((j2 >> 24) & 255)), (byte) ((int) ((j2 >> 16) & 255)), (byte) ((int) ((j2 >> 8) & 255)), (byte) ((int) (j2 & 255))};
    }

    public static byte[] b(String str) {
        if (str == null || BuildConfig.FLAVOR.equals(str.trim())) {
            return new byte[0];
        }
        byte[] bArr = new byte[(str.length() / 2)];
        for (int i2 = 0; i2 < str.length() / 2; i2++) {
            int i3 = i2 * 2;
            bArr[i2] = (byte) Integer.parseInt(str.substring(i3, i3 + 2), 16);
        }
        return bArr;
    }

    public static String a(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        String hexString = Integer.toHexString(Integer.parseInt(str, 2));
        if (hexString.length() < 2) {
            stringBuffer.append(0);
        }
        stringBuffer.append(hexString);
        return stringBuffer.toString();
    }

    public static String b(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() < 2) {
                stringBuffer.append(0);
            }
            stringBuffer.append(hexString);
        }
        return stringBuffer.toString();
    }

    public static byte[] a(int... iArr) {
        byte[] bArr = new byte[iArr.length];
        for (int i2 = 0; i2 < iArr.length; i2++) {
            bArr[i2] = (byte) (iArr[i2] & 255);
        }
        return bArr;
    }

    public static byte[] a(byte[] bArr, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, 0, bArr2, 0, i2);
        return bArr2;
    }

    public static byte[] a(byte[] bArr, int i2, int i3) {
        int i4 = i3 - i2;
        byte[] bArr2 = new byte[i4];
        System.arraycopy(bArr, i2, bArr2, 0, i4);
        return bArr2;
    }

    public static int[] a(int i2, int[] iArr) {
        int[] iArr2 = new int[(iArr.length + 1)];
        iArr2[0] = i2;
        System.arraycopy(iArr, 0, iArr2, 1, iArr.length);
        return iArr2;
    }

    public static byte[] a(byte[] bArr, byte b) {
        int length = bArr.length + 1;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        System.arraycopy(new byte[]{b}, 0, bArr2, length - 1, 1);
        return bArr2;
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        int length = bArr.length + bArr2.length;
        byte[] bArr3 = new byte[length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, length - bArr2.length, bArr2.length);
        return bArr3;
    }

    public static byte[] a(Byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        if (bArr.length == 0) {
            return new byte[0];
        }
        byte[] bArr2 = new byte[bArr.length];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr2[i2] = bArr[i2].byteValue();
        }
        return bArr2;
    }

    public static byte[] a(String str, boolean z) {
        byte[] bArr;
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (i2 < str.length()) {
            char charAt = str.charAt(i2);
            if (a.containsKey(Character.valueOf(charAt))) {
                bArr = a.get(Character.valueOf(charAt));
            } else if (z) {
                try {
                    bArr = Character.toString(charAt).getBytes("Unicode");
                } catch (UnsupportedEncodingException unused) {
                    bArr = Character.toString(charAt).getBytes();
                }
            } else {
                bArr = Character.toString(charAt).getBytes("UTF-8");
            }
            for (int i3 = (!z || i2 == 0) ? 0 : 2; i3 < bArr.length; i3++) {
                arrayList.add(Byte.valueOf(bArr[i3]));
            }
            i2++;
        }
        return a((Byte[]) arrayList.toArray(new Byte[0]));
    }
}
