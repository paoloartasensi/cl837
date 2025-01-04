package com.android.chileaf.fitness.common.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import no.nordicsemi.android.ble.data.Data;

public class HeartRateMeasurementParser {
   private static final byte HEART_RATE_VALUE_FORMAT = 1;
   private static final byte SENSOR_CONTACT_STATUS = 6;
   private static final byte ENERGY_EXPANDED_STATUS = 8;
   private static final byte RR_INTERVAL = 16;

   public static String parse(final Data data) {
      int offset = 0;
      int offset = offset + 1;
      int flags = data.getIntValue(17, offset);
      boolean value16bit = (flags & 1) > 0;
      int sensorContactStatus = (flags & 6) >> 1;
      boolean energyExpandedStatus = (flags & 8) > 0;
      boolean rrIntervalStatus = (flags & 16) > 0;
      int heartRateValue = data.getIntValue(value16bit ? 18 : 17, offset++);
      if (value16bit) {
         ++offset;
      }

      int energyExpanded = -1;
      if (energyExpandedStatus) {
         energyExpanded = data.getIntValue(18, offset);
      }

      offset += 2;
      List<Float> rrIntervals = new ArrayList();
      if (rrIntervalStatus) {
         for(int o = offset; o < data.getValue().length; o += 2) {
            int units = data.getIntValue(18, o);
            rrIntervals.add((float)units * 1000.0F / 1024.0F);
         }
      }

      StringBuilder builder = new StringBuilder();
      builder.append("Heart Rate Measurement: ").append(heartRateValue).append(" bpm");
      switch(sensorContactStatus) {
      case 0:
      case 1:
         builder.append(",Sensor Contact Not Supported");
         break;
      case 2:
         builder.append(",Contact is NOT Detected");
         break;
      case 3:
         builder.append(",Contact is Detected");
      }

      if (energyExpandedStatus) {
         builder.append(",Energy Expanded: ").append(energyExpanded).append(" kJ");
      }

      if (rrIntervalStatus) {
         builder.append(",RR Interval: ");
         Iterator var15 = rrIntervals.iterator();

         while(var15.hasNext()) {
            Float interval = (Float)var15.next();
            builder.append(String.format(Locale.US, "%.02f ms, ", interval));
         }

         builder.setLength(builder.length() - 2);
      }

      return builder.toString();
   }
}
