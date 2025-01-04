package no.nordicsemi.android.ble.response;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import no.nordicsemi.android.ble.callback.PhyCallback;

public class PhyResult implements PhyCallback, Parcelable {
   private BluetoothDevice device;
   private int txPhy;
   private int rxPhy;
   public static final Creator<PhyResult> CREATOR = new Creator<PhyResult>() {
      public PhyResult createFromParcel(Parcel in) {
         return new PhyResult(in);
      }

      public PhyResult[] newArray(int size) {
         return new PhyResult[size];
      }
   };

   public PhyResult() {
   }

   public void onPhyChanged(@NonNull BluetoothDevice device, int txPhy, int rxPhy) {
      this.device = device;
      this.txPhy = txPhy;
      this.rxPhy = rxPhy;
   }

   @Nullable
   public BluetoothDevice getBluetoothDevice() {
      return this.device;
   }

   public int getTxPhy() {
      return this.txPhy;
   }

   public int getRxPhy() {
      return this.rxPhy;
   }

   protected PhyResult(Parcel in) {
      this.device = (BluetoothDevice)in.readParcelable(BluetoothDevice.class.getClassLoader());
      this.txPhy = in.readInt();
      this.rxPhy = in.readInt();
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeParcelable(this.device, flags);
      dest.writeInt(this.txPhy);
      dest.writeInt(this.rxPhy);
   }

   public int describeContents() {
      return 0;
   }
}
