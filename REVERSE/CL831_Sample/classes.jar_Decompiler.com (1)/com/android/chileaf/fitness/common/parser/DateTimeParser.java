package com.android.chileaf.fitness.common.parser;

import com.android.chileaf.fitness.common.DateTimeDataCallback;
import java.util.Calendar;
import java.util.Locale;
import no.nordicsemi.android.ble.data.Data;

public class DateTimeParser {
   public static String parse(final Data data) {
      return parse(data, 0);
   }

   static String parse(final Data data, final int offset) {
      Calendar calendar = DateTimeDataCallback.readDateTime(data, offset);
      return String.format(Locale.US, "%1$te %1$tb %1$tY, %1$tH:%1$tM:%1$tS", calendar);
   }
}
