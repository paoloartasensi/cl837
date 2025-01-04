package no.nordicsemi.android.ble.data;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class DefaultMtuSplitter implements DataSplitter {
   @Nullable
   public byte[] chunk(@NonNull byte[] message, @IntRange(from = 0L) int index, @IntRange(from = 20L) int maxLength) {
      int offset = index * maxLength;
      int length = Math.min(maxLength, message.length - offset);
      if (length <= 0) {
         return null;
      } else {
         byte[] data = new byte[length];
         System.arraycopy(message, offset, data, 0, length);
         return data;
      }
   }
}
