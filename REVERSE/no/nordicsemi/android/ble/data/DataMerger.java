package no.nordicsemi.android.ble.data;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface DataMerger {
  boolean merge(@NonNull DataStream paramDataStream, @Nullable byte[] paramArrayOfbyte, @IntRange(from = 0L) int paramInt);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\data\DataMerger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */