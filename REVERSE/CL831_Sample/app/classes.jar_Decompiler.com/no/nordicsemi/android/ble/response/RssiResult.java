package no.nordicsemi.android.ble.response;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import no.nordicsemi.android.ble.callback.RssiCallback;

public class RssiResult implements RssiCallback, Parcelable {
   private BluetoothDevice device;
   @IntRange(
      from = -128L,
      to = 20L
   )
   private int rssi;
   public static final Creator<RssiResult> CREATOR = new Creator<RssiResult>() {
      public RssiResult createFromParcel(Parcel in) {
         return new RssiResult(in);
      }

      public RssiResult[] newArray(int size) {
         return new RssiResult[size];
      }
   };

   public RssiResult() {
   }

   public void onRssiRead(@NonNull BluetoothDevice device, @IntRange(from = -128L,to = 20L) int rssi) {
      this.device = device;
      this.rssi = rssi;
   }

   @Nullable
   public BluetoothDevice getBluetoothDevice() {
      return this.device;
   }

   @IntRange(
      from = -128L,
      to = 20L
   )
   public int getRssi() {
      return this.rssi;
   }

   protected RssiResult(Parcel in) {
      this.device = (BluetoothDevice)in.readParcelable(BluetoothDevice.class.getClassLoader());
      this.rssi = in.readInt();
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeParcelable(this.device, flags);
      dest.writeInt(this.rssi);
   }

   public int describeContents() {
      return 0;
   }
}
