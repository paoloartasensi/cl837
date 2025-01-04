package no.nordicsemi.android.support.v18.scanner;

import android.os.ParcelUuid;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

final class BluetoothUuid {
   private static final ParcelUuid BASE_UUID = ParcelUuid.fromString("00000000-0000-1000-8000-00805F9B34FB");
   static final int UUID_BYTES_16_BIT = 2;
   static final int UUID_BYTES_32_BIT = 4;
   static final int UUID_BYTES_128_BIT = 16;

   static ParcelUuid parseUuidFrom(byte[] uuidBytes) {
      if (uuidBytes == null) {
         throw new IllegalArgumentException("uuidBytes cannot be null");
      } else {
         int length = uuidBytes.length;
         if (length != 2 && length != 4 && length != 16) {
            throw new IllegalArgumentException("uuidBytes length invalid - " + length);
         } else if (length == 16) {
            ByteBuffer buf = ByteBuffer.wrap(uuidBytes).order(ByteOrder.LITTLE_ENDIAN);
            long msb = buf.getLong(8);
            long lsb = buf.getLong(0);
            return new ParcelUuid(new UUID(msb, lsb));
         } else {
            long shortUuid;
            if (length == 2) {
               shortUuid = (long)(uuidBytes[0] & 255);
               shortUuid += (long)((uuidBytes[1] & 255) << 8);
            } else {
               shortUuid = (long)(uuidBytes[0] & 255);
               shortUuid += (long)((uuidBytes[1] & 255) << 8);
               shortUuid += (long)((uuidBytes[2] & 255) << 16);
               shortUuid += (long)((uuidBytes[3] & 255) << 24);
            }

            long msb = BASE_UUID.getUuid().getMostSignificantBits() + (shortUuid << 32);
            long lsb = BASE_UUID.getUuid().getLeastSignificantBits();
            return new ParcelUuid(new UUID(msb, lsb));
         }
      }
   }
}
