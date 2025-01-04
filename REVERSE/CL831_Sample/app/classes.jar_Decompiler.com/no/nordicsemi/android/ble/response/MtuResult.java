package no.nordicsemi.android.ble.response;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import no.nordicsemi.android.ble.callback.MtuCallback;

public class MtuResult implements MtuCallback, Parcelable {
   private BluetoothDevice device;
   @IntRange(
      from = 23L,
      to = 517L
   )
   private int mtu;
   public static final Creator<MtuResult> CREATOR = new Creator<MtuResult>() {
      public MtuResult createFromParcel(Parcel in) {
         return new MtuResult(in);
      }

      public MtuResult[] newArray(int size) {
         return new MtuResult[size];
      }
   };

   public MtuResult() {
   }

   public void onMtuChanged(@NonNull BluetoothDevice device, @IntRange(from = 23L,to = 517L) int mtu) {
      this.device = device;
      this.mtu = mtu;
   }

   @Nullable
   public BluetoothDevice getBluetoothDevice() {
      return this.device;
   }

   @IntRange(
      from = 23L,
      to = 517L
   )
   public int getMtu() {
      return this.mtu;
   }

   protected MtuResult(Parcel in) {
      this.device = (BluetoothDevice)in.readParcelable(BluetoothDevice.class.getClassLoader());
      this.mtu = in.readInt();
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeParcelable(this.device, flags);
      dest.writeInt(this.mtu);
   }

   public int describeContents() {
      return 0;
   }
}
