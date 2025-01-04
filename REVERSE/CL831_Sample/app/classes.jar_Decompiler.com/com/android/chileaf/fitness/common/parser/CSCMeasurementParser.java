package com.android.chileaf.fitness.common.parser;

import no.nordicsemi.android.ble.data.Data;

public class CSCMeasurementParser {
   private static final byte WHEEL_REV_DATA_PRESENT = 1;
   private static final byte CRANK_REV_DATA_PRESENT = 2;

   public static String parse(final Data data) {
      int offset = 0;
      int flags = data.getByte(offset);
      int offset = offset + 1;
      boolean wheelRevPresent = (flags & 1) > 0;
      boolean crankRevPreset = (flags & 2) > 0;
      int wheelRevolutions = 0;
      int lastWheelEventTime = 0;
      if (wheelRevPresent) {
         wheelRevolutions = data.getIntValue(20, offset);
         offset += 4;
         lastWheelEventTime = data.getIntValue(18, offset);
         offset += 2;
      }

      int crankRevolutions = 0;
      int lastCrankEventTime = 0;
      if (crankRevPreset) {
         crankRevolutions = data.getIntValue(18, offset);
         offset += 2;
         lastCrankEventTime = data.getIntValue(18, offset);
      }

      StringBuilder builder = new StringBuilder();
      if (wheelRevPresent) {
         builder.append("Wheel rev: ").append(wheelRevolutions).append(",");
         builder.append("Last wheel event time: ").append(lastWheelEventTime).append(",");
      }

      if (crankRevPreset) {
         builder.append("Crank rev: ").append(crankRevolutions).append(",");
         builder.append("Last crank event time: ").append(lastCrankEventTime).append(",");
      }

      if (!wheelRevPresent && !crankRevPreset) {
         builder.append("No wheel or crank data");
      }

      builder.setLength(builder.length() - 2);
      return builder.toString();
   }
}
