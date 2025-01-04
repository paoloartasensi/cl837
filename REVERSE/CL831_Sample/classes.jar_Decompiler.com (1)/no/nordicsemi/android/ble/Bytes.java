package no.nordicsemi.android.ble;

import androidx.annotation.IntRange;
import androidx.annotation.Nullable;

final class Bytes {
   static byte[] copy(@Nullable byte[] value, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
      if (value != null && offset <= value.length) {
         int maxLength = Math.min(value.length - offset, length);
         byte[] copy = new byte[maxLength];
         System.arraycopy(value, offset, copy, 0, maxLength);
         return copy;
      } else {
         return null;
      }
   }

   static byte[] concat(@Nullable byte[] left, @Nullable byte[] right, @IntRange(from = 0L) int offset) {
      int length = offset + (right != null ? right.length : 0);
      byte[] result = new byte[length];
      if (left != null) {
         System.arraycopy(left, 0, result, 0, left.length);
      }

      if (right != null) {
         System.arraycopy(right, 0, result, offset, right.length);
      }

      return result;
   }
}
