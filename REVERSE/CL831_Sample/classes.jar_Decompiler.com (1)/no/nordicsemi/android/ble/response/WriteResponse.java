package no.nordicsemi.android.ble.response;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import no.nordicsemi.android.ble.callback.DataSentCallback;
import no.nordicsemi.android.ble.data.Data;

public class WriteResponse implements DataSentCallback, Parcelable {
   private BluetoothDevice device;
   private Data data;
   public static final Creator<WriteResponse> CREATOR = new Creator<WriteResponse>() {
      public WriteResponse createFromParcel(Parcel in) {
         return new WriteResponse(in);
      }

      public WriteResponse[] newArray(int size) {
         return new WriteResponse[size];
      }
   };

   public WriteResponse() {
   }

   public void onDataSent(@NonNull BluetoothDevice device, @NonNull Data data) {
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

   protected WriteResponse(Parcel in) {
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
