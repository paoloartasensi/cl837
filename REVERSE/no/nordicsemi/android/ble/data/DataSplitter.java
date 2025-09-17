package no.nordicsemi.android.ble.data;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface DataSplitter {
  @Nullable
  byte[] chunk(@NonNull byte[] paramArrayOfbyte, @IntRange(from = 0L) int paramInt1, @IntRange(from = 20L) int paramInt2);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\data\DataSplitter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */