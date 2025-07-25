package com.android.chileaf.fitness.common.b;

import com.android.chileaf.bluetooth.connect.data.Data;
import java.util.ArrayList;
import java.util.Locale;

/* compiled from: HeartRateMeasurementParser */
public class c {
    public static String a(Data data) {
        int i2 = 17;
        int intValue = data.a(17, 0).intValue();
        boolean z = (intValue & 1) > 0;
        int i3 = (intValue & 6) >> 1;
        boolean z2 = (intValue & 8) > 0;
        boolean z3 = (intValue & 16) > 0;
        if (z) {
            i2 = 18;
        }
        int intValue2 = data.a(i2, 1).intValue();
        int i4 = z ? 3 : 2;
        int i5 = -1;
        if (z2) {
            i5 = data.a(18, i4).intValue();
        }
        ArrayList<Float> arrayList = new ArrayList<>();
        if (z3) {
            for (int i6 = i4 + 2; i6 < data.a().length; i6 += 2) {
                arrayList.add(Float.valueOf((((float) data.a(18, i6).intValue()) * 1000.0f) / 1024.0f));
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Heart Rate Measurement: ");
        sb.append(intValue2);
        sb.append(" bpm");
        if (i3 == 0 || i3 == 1) {
            sb.append(",Sensor Contact Not Supported");
        } else if (i3 == 2) {
            sb.append(",Contact is NOT Detected");
        } else if (i3 == 3) {
            sb.append(",Contact is Detected");
        }
        if (z2) {
            sb.append(",Energy Expanded: ");
            sb.append(i5);
            sb.append(" kJ");
        }
        if (z3) {
            sb.append(",RR Interval: ");
            for (Float f2 : arrayList) {
                sb.append(String.format(Locale.US, "%.02f ms, ", new Object[]{f2}));
            }
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }
}
