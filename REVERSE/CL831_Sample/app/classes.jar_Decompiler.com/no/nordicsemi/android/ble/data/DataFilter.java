package no.nordicsemi.android.ble.data;

import androidx.annotation.Nullable;

public interface DataFilter {
   boolean filter(@Nullable byte[] var1);
}
