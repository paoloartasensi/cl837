package f.a.a.a.a;

import android.os.ParcelUuid;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

/* compiled from: BluetoothUuid */
public final class b {
    public static final ParcelUuid a = ParcelUuid.fromString("0000110B-0000-1000-8000-00805F9B34FB");
    public static final ParcelUuid b = ParcelUuid.fromString("0000110A-0000-1000-8000-00805F9B34FB");
    public static final ParcelUuid c = ParcelUuid.fromString("0000110D-0000-1000-8000-00805F9B34FB");
    public static final ParcelUuid d = ParcelUuid.fromString("00001108-0000-1000-8000-00805F9B34FB");
    public static final ParcelUuid e = ParcelUuid.fromString("0000111E-0000-1000-8000-00805F9B34FB");

    /* renamed from: f  reason: collision with root package name */
    public static final ParcelUuid f1627f = ParcelUuid.fromString("0000110E-0000-1000-8000-00805F9B34FB");

    /* renamed from: g  reason: collision with root package name */
    public static final ParcelUuid f1628g = ParcelUuid.fromString("0000110C-0000-1000-8000-00805F9B34FB");

    /* renamed from: h  reason: collision with root package name */
    public static final ParcelUuid f1629h = ParcelUuid.fromString("00001105-0000-1000-8000-00805f9b34fb");

    /* renamed from: i  reason: collision with root package name */
    public static final ParcelUuid f1630i = ParcelUuid.fromString("00001115-0000-1000-8000-00805F9B34FB");

    /* renamed from: j  reason: collision with root package name */
    public static final ParcelUuid f1631j = ParcelUuid.fromString("00001116-0000-1000-8000-00805F9B34FB");
    public static final ParcelUuid k = ParcelUuid.fromString("00001134-0000-1000-8000-00805F9B34FB");
    public static final ParcelUuid l = ParcelUuid.fromString("00001133-0000-1000-8000-00805F9B34FB");
    public static final ParcelUuid m = ParcelUuid.fromString("00001132-0000-1000-8000-00805F9B34FB");
    public static final ParcelUuid n = ParcelUuid.fromString("00000000-0000-1000-8000-00805F9B34FB");

    static {
        ParcelUuid.fromString("00001112-0000-1000-8000-00805F9B34FB");
        ParcelUuid.fromString("0000111F-0000-1000-8000-00805F9B34FB");
        ParcelUuid.fromString("00001124-0000-1000-8000-00805f9b34fb");
        ParcelUuid.fromString("00001812-0000-1000-8000-00805f9b34fb");
        ParcelUuid.fromString("0000000f-0000-1000-8000-00805F9B34FB");
        ParcelUuid.fromString("0000112e-0000-1000-8000-00805F9B34FB");
        ParcelUuid.fromString("0000112f-0000-1000-8000-00805F9B34FB");
    }

    public static ParcelUuid a(byte[] bArr) {
        long j2;
        if (bArr != null) {
            int length = bArr.length;
            if (length != 2 && length != 4 && length != 16) {
                throw new IllegalArgumentException("uuidBytes length invalid - " + length);
            } else if (length == 16) {
                ByteBuffer order = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
                return new ParcelUuid(new UUID(order.getLong(8), order.getLong(0)));
            } else {
                if (length == 2) {
                    j2 = ((long) (bArr[0] & 255)) + ((long) ((bArr[1] & 255) << 8));
                } else {
                    j2 = ((long) ((bArr[3] & 255) << 24)) + ((long) (bArr[0] & 255)) + ((long) ((bArr[1] & 255) << 8)) + ((long) ((bArr[2] & 255) << 16));
                }
                return new ParcelUuid(new UUID(n.getUuid().getMostSignificantBits() + (j2 << 32), n.getUuid().getLeastSignificantBits()));
            }
        } else {
            throw new IllegalArgumentException("uuidBytes cannot be null");
        }
    }
}
