package com.android.chileaf.bluetooth.connect.h1;

import com.jeremyliao.liveeventbus.BuildConfig;

/* compiled from: ParserUtils */
public class a {
    protected static final char[] a = "0123456789ABCDEF".toCharArray();

    public static String a(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return BuildConfig.FLAVOR;
        }
        char[] cArr = new char[((bArr.length * 3) - 1)];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            byte b = bArr[i2] & 255;
            int i3 = i2 * 3;
            char[] cArr2 = a;
            cArr[i3] = cArr2[b >>> 4];
            cArr[i3 + 1] = cArr2[b & 15];
            if (i2 != bArr.length - 1) {
                cArr[i3 + 2] = '-';
            }
        }
        return "(0x) " + new String(cArr);
    }

    public static String b(int i2) {
        switch (i2) {
            case 0:
                return "PAIRING_VARIANT_PIN";
            case 1:
                return "PAIRING_VARIANT_PASSKEY";
            case 2:
                return "PAIRING_VARIANT_PASSKEY_CONFIRMATION";
            case 3:
                return "PAIRING_VARIANT_CONSENT";
            case 4:
                return "PAIRING_VARIANT_DISPLAY_PASSKEY";
            case 5:
                return "PAIRING_VARIANT_DISPLAY_PIN";
            case 6:
                return "PAIRING_VARIANT_OOB_CONSENT";
            default:
                return "UNKNOWN (" + i2 + ")";
        }
    }

    public static String c(int i2) {
        if (i2 == 0) {
            return "No preferred";
        }
        if (i2 == 1) {
            return "S2";
        }
        if (i2 == 2) {
            return "S8";
        }
        return "UNKNOWN (" + i2 + ")";
    }

    public static String d(int i2) {
        switch (i2) {
            case 1:
                return "LE 1M";
            case 2:
                return "LE 2M";
            case 3:
                return "LE 1M or LE 2M";
            case 4:
                return "LE Coded";
            case 5:
                return "LE 1M or LE Coded";
            case 6:
                return "LE 2M or LE Coded";
            case 7:
                return "LE 1M, LE 2M or LE Coded";
            default:
                return "UNKNOWN (" + i2 + ")";
        }
    }

    public static String e(int i2) {
        if (i2 == 1) {
            return "LE 1M";
        }
        if (i2 == 2) {
            return "LE 2M";
        }
        if (i2 == 3) {
            return "LE Coded";
        }
        return "UNKNOWN (" + i2 + ")";
    }

    public static String f(int i2) {
        if (i2 == 0) {
            return "DISCONNECTED";
        }
        if (i2 == 1) {
            return "CONNECTING";
        }
        if (i2 == 2) {
            return "CONNECTED";
        }
        if (i2 == 3) {
            return "DISCONNECTING";
        }
        return "UNKNOWN (" + i2 + ")";
    }

    public static String g(int i2) {
        if (i2 == 1) {
            return "WRITE COMMAND";
        }
        if (i2 == 2) {
            return "WRITE REQUEST";
        }
        if (i2 == 4) {
            return "WRITE SIGNED";
        }
        return "UNKNOWN (" + i2 + ")";
    }

    public static String a(int i2) {
        switch (i2) {
            case 10:
                return "BOND_NONE";
            case 11:
                return "BOND_BONDING";
            case 12:
                return "BOND_BONDED";
            default:
                return "UNKNOWN (" + i2 + ")";
        }
    }
}
