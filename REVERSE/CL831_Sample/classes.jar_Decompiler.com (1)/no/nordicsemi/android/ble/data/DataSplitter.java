package no.nordicsemi.android.ble.data;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface DataSplitter {
   @Nullable
   byte[] chunk(@NonNull byte[] var1, @IntRange(from = 0L) int var2, @IntRange(from = 20L) int var3);
}
