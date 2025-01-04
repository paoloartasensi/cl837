package no.nordicsemi.android.ble.response;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import no.nordicsemi.android.ble.callback.DataReceivedCallback;
import no.nordicsemi.android.ble.data.Data;

public class ReadResponse implements DataReceivedCallback, Parcelable {
   private BluetoothDevice device;
   private Data data;
   public static final Creator<ReadResponse> CREATOR = new Creator<ReadResponse>() {
      public ReadResponse createFromParcel(Parcel in) {
         return new ReadResponse(in);
      }

      public ReadResponse[] newArray(int size) {
         return new ReadResponse[size];
      }
   };

   public ReadResponse() {
   }

   public void onDataReceived(@NonNull BluetoothDevice device, @NonNull Data data) {
      this.device = device;
      this.data = data;
   }

   @Nullable
   public BluetoothDevice getBluetoothDevice() {
      return this.device;
   }

   @Nullable
   public Data getRawData() {
      return this.data;
   }

   protected ReadResponse(Parcel in) {
      this.device = (BluetoothDevice)in.readParcelable(BluetoothDevice.class.getClassLoader());
      this.data = (Data)in.readParcelable(Data.class.getClassLoader());
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeParcelable(this.device, flags);
      dest.writeParcelable(this.data, flags);
   }

   public int describeContents() {
      return 0;
   }
}
