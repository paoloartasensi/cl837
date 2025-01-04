package no.nordicsemi.android.ble.data;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.ByteArrayOutputStream;

public class DataStream {
   private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

   public boolean write(@Nullable byte[] data) {
      return data == null ? false : this.write(data, 0, data.length);
   }

   public boolean write(@Nullable byte[] data, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
      if (data != null && data.length >= offset) {
         int len = Math.min(data.length - offset, length);
         this.buffer.write(data, offset, len);
         return true;
      } else {
         return false;
      }
   }

   public boolean write(@Nullable Data data) {
      return data != null && this.write(data.getValue());
   }

   @IntRange(
      from = 0L
   )
   public int size() {
      return this.buffer.size();
   }

   @NonNull
   public byte[] toByteArray() {
      return this.buffer.toByteArray();
   }

   @NonNull
   public Data toData() {
      return new Data(this.buffer.toByteArray());
   }
}
