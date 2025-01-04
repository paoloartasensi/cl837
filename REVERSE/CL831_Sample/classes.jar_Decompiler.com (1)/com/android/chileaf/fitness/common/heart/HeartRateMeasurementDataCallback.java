package com.android.chileaf.fitness.common.heart;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import no.nordicsemi.android.ble.callback.profile.ProfileReadResponse;
import no.nordicsemi.android.ble.data.Data;

public abstract class HeartRateMeasurementDataCallback extends ProfileReadResponse implements HeartRateMeasurementCallback {
   public HeartRateMeasurementDataCallback() {
   }

   protected HeartRateMeasurementDataCallback(final Parcel in) {
      super(in);
   }

   public void onDataReceived(@NonNull final BluetoothDevice device, @NonNull final Data data) {
      super.onDataReceived(device, data);
      if (data.size() < 2) {
         this.onInvalidDataReceived(device, data);
      } else {
         int offset = 0;
         int flags = data.getIntValue(17, offset);
         int hearRateType = (flags & 1) == 0 ? 17 : 18;
         int sensorContactStatus = (flags & 6) >> 1;
         boolean sensorContactSupported = sensorContactStatus == 2 || sensorContactStatus == 3;
         boolean sensorContactDetected = sensorContactStatus == 3;
         boolean energyExpandedPresent = (flags & 8) != 0;
         boolean rrIntervalsPresent = (flags & 16) != 0;
         int offset = offset + 1;
         if (data.size() < 1 + (hearRateType & 15) + (energyExpandedPresent ? 2 : 0) + (rrIntervalsPresent ? 2 : 0)) {
            this.onInvalidDataReceived(device, data);
         } else {
            Boolean sensorContact = sensorContactSupported ? sensorContactDetected : null;
            int heartRate = data.getIntValue(hearRateType, offset);
            offset += hearRateType & 15;
            Integer energyExpanded = null;
            if (energyExpandedPresent) {
               energyExpanded = data.getIntValue(18, offset);
               offset += 2;
            }

            List<Integer> rrIntervals = null;
            if (rrIntervalsPresent) {
               int count = (data.size() - offset) / 2;
               List<Integer> intervals = new ArrayList(count);

               for(int i = 0; i < count; ++i) {
                  intervals.add(data.getIntValue(18, offset));
                  offset += 2;
               }

               rrIntervals = Collections.unmodifiableList(intervals);
            }

            this.onHeartRateMeasurementReceived(device, heartRate, sensorContact, energyExpanded, rrIntervals);
         }
      }
   }
}
