package no.nordicsemi.android.ble.data;

import androidx.annotation.Nullable;

public interface PacketFilter {
  boolean filter(@Nullable byte[] paramArrayOfbyte);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\data\PacketFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */