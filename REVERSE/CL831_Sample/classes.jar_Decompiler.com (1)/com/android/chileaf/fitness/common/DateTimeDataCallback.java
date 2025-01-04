package com.android.chileaf.fitness.common;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Calendar;
import no.nordicsemi.android.ble.callback.profile.ProfileReadResponse;
import no.nordicsemi.android.ble.data.Data;

public abstract class DateTimeDataCallback extends ProfileReadResponse implements DateTimeCallback {
   public DateTimeDataCallback() {
   }

   protected DateTimeDataCallback(final Parcel in) {
      super(in);
   }

   public void onDataReceived(@NonNull final BluetoothDevice device, @NonNull final Data data) {
      super.onDataReceived(device, data);
      Calendar calendar = readDateTime(data, 0);
      if (calendar == null) {
         this.onInvalidDataReceived(device, data);
      } else {
         this.onDateTimeReceived(device, calendar);
      }
   }

   @Nullable
   public static Calendar readDateTime(@NonNull final Data data, final int offset) {
      if (data.size() < offset + 7) {
         return null;
      } else {
         Calendar calendar = Calendar.getInstance();
         int year = data.getIntValue(18, offset);
         int month = data.getIntValue(17, offset + 2);
         int day = data.getIntValue(17, offset + 3);
         if (year > 0) {
            calendar.set(1, year);
         } else {
            calendar.clear(1);
         }

         if (month > 0) {
            calendar.set(2, month - 1);
         } else {
            calendar.clear(2);
         }

         if (day > 0) {
            calendar.set(5, day);
         } else {
            calendar.clear(5);
         }

         calendar.set(11, data.getIntValue(17, offset + 4));
         calendar.set(12, data.getIntValue(17, offset + 5));
         calendar.set(13, data.getIntValue(17, offset + 6));
         calendar.set(14, 0);
         return calendar;
      }
   }
}
