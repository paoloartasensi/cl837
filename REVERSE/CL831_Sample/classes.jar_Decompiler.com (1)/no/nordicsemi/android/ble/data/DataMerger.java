package no.nordicsemi.android.ble.data;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface DataMerger {
   boolean merge(@NonNull DataStream var1, @Nullable byte[] var2, @IntRange(from = 0L) int var3);
}
