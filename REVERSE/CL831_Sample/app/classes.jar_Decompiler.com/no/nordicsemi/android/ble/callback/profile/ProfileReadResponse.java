package no.nordicsemi.android.ble.callback.profile;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import androidx.annotation.NonNull;
import no.nordicsemi.android.ble.data.Data;
import no.nordicsemi.android.ble.response.ReadResponse;

public class ProfileReadResponse extends ReadResponse implements ProfileDataCallback, Parcelable {
   private boolean valid = true;
   public static final Creator<ProfileReadResponse> CREATOR = new Creator<ProfileReadResponse>() {
      public ProfileReadResponse createFromParcel(Parcel in) {
         return new ProfileReadResponse(in);
      }

      public ProfileReadResponse[] newArray(int size) {
         return new ProfileReadResponse[size];
      }
   };

   public ProfileReadResponse() {
   }

   public void onInvalidDataReceived(@NonNull BluetoothDevice device, @NonNull Data data) {
      this.valid = false;
   }

   public boolean isValid() {
      return this.valid;
   }

   protected ProfileReadResponse(Parcel in) {
      super(in);
      this.valid = in.readByte() != 0;
   }

   public void writeToParcel(Parcel dest, int flags) {
      super.writeToParcel(dest, flags);
      dest.writeByte((byte)(this.valid ? 1 : 0));
   }
}
