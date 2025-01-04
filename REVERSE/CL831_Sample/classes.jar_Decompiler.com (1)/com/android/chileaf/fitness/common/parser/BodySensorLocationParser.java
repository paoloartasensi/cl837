package com.android.chileaf.fitness.common.parser;

import no.nordicsemi.android.ble.data.Data;

public class BodySensorLocationParser {
   public static String parse(final Data data) {
      int value = data.getIntValue(17, 0);
      switch(value) {
      case 0:
      default:
         return "Other";
      case 1:
         return "Chest";
      case 2:
         return "Wrist";
      case 3:
         return "Finger";
      case 4:
         return "Hand";
      case 5:
         return "Ear Lobe";
      case 6:
         return "Foot";
      }
   }
}
