package okhttp3.internal.http2;

import com.jeremyliao.liveeventbus.BuildConfig;
import java.io.IOException;
import okhttp3.k0.e;
import okio.ByteString;

/* compiled from: Http2 */
public final class c {
    static final ByteString a = ByteString.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
    private static final String[] b = {"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};
    static final String[] c = new String[64];
    static final String[] d = new String[256];

    static {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            String[] strArr = d;
            if (i3 >= strArr.length) {
                break;
            }
            strArr[i3] = e.a("%8s", Integer.toBinaryString(i3)).replace(' ', '0');
            i3++;
        }
        String[] strArr2 = c;
        strArr2[0] = BuildConfig.FLAVOR;
        strArr2[1] = "END_STREAM";
        int[] iArr = {1};
        strArr2[8] = "PADDED";
        for (int i4 = 0; i4 < 1; i4++) {
            int i5 = iArr[i4];
            c[i5 | 8] = c[i5] + "|PADDED";
        }
        String[] strArr3 = c;
        strArr3[4] = "END_HEADERS";
        strArr3[32] = "PRIORITY";
        strArr3[36] = "END_HEADERS|PRIORITY";
        int[] iArr2 = {4, 32, 36};
        for (int i6 = 0; i6 < 3; i6++) {
            int i7 = iArr2[i6];
            for (int i8 = 0; i8 < 1; i8++) {
                int i9 = iArr[i8];
                String[] strArr4 = c;
                int i10 = i9 | i7;
                strArr4[i10] = c[i9] + '|' + c[i7];
                c[i10 | 8] = c[i9] + '|' + c[i7] + "|PADDED";
            }
        }
        while (true) {
            String[] strArr5 = c;
            if (i2 < strArr5.length) {
                if (strArr5[i2] == null) {
                    strArr5[i2] = d[i2];
                }
                i2++;
            } else {
                return;
            }
        }
    }

    private c() {
    }

    static IllegalArgumentException a(String str, Object... objArr) {
        throw new IllegalArgumentException(e.a(str, objArr));
    }

    static IOException b(String str, Object... objArr) {
        throw new IOException(e.a(str, objArr));
    }

    static String a(boolean z, int i2, int i3, byte b2, byte b3) {
        String[] strArr = b;
        String a2 = b2 < strArr.length ? strArr[b2] : e.a("0x%02x", Byte.valueOf(b2));
        String a3 = a(b2, b3);
        Object[] objArr = new Object[5];
        objArr[0] = z ? "<<" : ">>";
        objArr[1] = Integer.valueOf(i2);
        objArr[2] = Integer.valueOf(i3);
        objArr[3] = a2;
        objArr[4] = a3;
        return e.a("%s 0x%08x %5d %-13s %s", objArr);
    }

    static String a(byte b2, byte b3) {
        if (b3 == 0) {
            return BuildConfig.FLAVOR;
        }
        if (!(b2 == 2 || b2 == 3)) {
            if (b2 == 4 || b2 == 6) {
                if (b3 == 1) {
                    return "ACK";
                }
                return d[b3];
            } else if (!(b2 == 7 || b2 == 8)) {
                String[] strArr = c;
                String str = b3 < strArr.length ? strArr[b3] : d[b3];
                if (b2 != 5 || (b3 & 4) == 0) {
                    return (b2 != 0 || (b3 & 32) == 0) ? str : str.replace("PRIORITY", "COMPRESSED");
                }
                return str.replace("HEADERS", "PUSH_PROMISE");
            }
        }
        return d[b3];
    }
}
