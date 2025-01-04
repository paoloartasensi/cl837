package no.nordicsemi.android.ble.response;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import no.nordicsemi.android.ble.callback.ConnectionParametersUpdatedCallback;

public class ConnectionPriorityResponse implements ConnectionParametersUpdatedCallback, Parcelable {
   private BluetoothDevice device;
   @IntRange(
      from = 6L,
      to = 3200L
   )
   private int interval;
   @IntRange(
      from = 0L,
      to = 499L
   )
   private int latency;
   @IntRange(
      from = 10L,
      to = 3200L
   )
   private int supervisionTimeout;
   public static final Creator<ConnectionPriorityResponse> CREATOR = new Creator<ConnectionPriorityResponse>() {
      public ConnectionPriorityResponse createFromParcel(Parcel in) {
         return new ConnectionPriorityResponse(in);
      }

      public ConnectionPriorityResponse[] newArray(int size) {
         return new ConnectionPriorityResponse[size];
      }
   };

   public ConnectionPriorityResponse() {
   }

   public void onConnectionUpdated(@NonNull BluetoothDevice device, @IntRange(from = 6L,to = 3200L) int interval, @IntRange(from = 0L,to = 499L) int latency, @IntRange(from = 10L,to = 3200L) int timeout) {
      this.device = device;
      this.interval = interval;
      this.latency = latency;
      this.supervisionTimeout = timeout;
   }

   @Nullable
   public BluetoothDevice getBluetoothDevice() {
      return this.device;
   }

   @IntRange(
      from = 6L,
      to = 3200L
   )
   public int getConnectionInterval() {
      return this.interval;
   }

   @IntRange(
      from = 0L,
      to = 499L
   )
   public int getSlaveLatency() {
      return this.latency;
   }

   @IntRange(
      from = 10L,
      to = 3200L
   )
   public int getSupervisionTimeout() {
      return this.supervisionTimeout;
   }

   protected ConnectionPriorityResponse(Parcel in) {
      this.device = (BluetoothDevice)in.readParcelable(BluetoothDevice.class.getClassLoader());
      this.interval = in.readInt();
      this.latency = in.readInt();
      this.supervisionTimeout = in.readInt();
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeParcelable(this.device, flags);
      dest.writeInt(this.interval);
      dest.writeInt(this.latency);
      dest.writeInt(this.supervisionTimeout);
   }

   public int describeContents() {
      return 0;
   }
}
