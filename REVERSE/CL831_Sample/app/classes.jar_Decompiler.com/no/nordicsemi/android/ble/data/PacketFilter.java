package no.nordicsemi.android.ble.data;

import androidx.annotation.Nullable;

public interface PacketFilter {
   boolean filter(@Nullable byte[] var1);
}
