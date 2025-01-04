package com.android.chileaf.fitness.common.parser;

import no.nordicsemi.android.ble.data.Data;

public class BloodPressureMeasurementParser {
   public static String parse(final Data data) {
      StringBuilder builder = new StringBuilder();
      int offset = 0;
      int offset = offset + 1;
      int flags = data.getIntValue(17, offset);
      int unitType = flags & 1;
      boolean timestampPresent = (flags & 2) > 0;
      boolean pulseRatePresent = (flags & 4) > 0;
      boolean userIdPresent = (flags & 8) > 0;
      boolean statusPresent = (flags & 16) > 0;
      float systolic = data.getFloatValue(50, offset);
      float diastolic = data.getFloatValue(50, offset + 2);
      float meanArterialPressure = data.getFloatValue(50, offset + 4);
      String unit = unitType == 0 ? " mmHg" : " kPa";
      offset += 6;
      builder.append("Systolic: ").append(systolic).append(unit);
      builder.append(" Diastolic: ").append(diastolic).append(unit);
      builder.append(" Mean AP: ").append(meanArterialPressure).append(unit);
      if (timestampPresent) {
         builder.append(" Timestamp: ").append(DateTimeParser.parse(data, offset));
         offset += 7;
      }

      if (pulseRatePresent) {
         float pulseRate = data.getFloatValue(50, offset);
         offset += 2;
         builder.append(" Pulse: ").append(pulseRate).append(" bpm");
      }

      int status;
      if (userIdPresent) {
         status = data.getIntValue(17, offset);
         ++offset;
         builder.append(" User ID: ").append(status);
      }

      if (statusPresent) {
         status = data.getIntValue(18, offset);
         if ((status & 1) > 0) {
            builder.append(" Body movement detected");
         }

         if ((status & 2) > 0) {
            builder.append(" Cuff too lose");
         }

         if ((status & 4) > 0) {
            builder.append(" Irregular pulse detected");
         }

         if ((status & 24) == 8) {
            builder.append(" Pulse rate exceeds upper limit");
         }

         if ((status & 24) == 16) {
            builder.append(" Pulse rate is less than lower limit");
         }

         if ((status & 24) == 24) {
            builder.append(" Pulse rate range: Reserved for future use ");
         }

         if ((status & 32) > 0) {
            builder.append(" Improper measurement position");
         }
      }

      return builder.toString();
   }
}
