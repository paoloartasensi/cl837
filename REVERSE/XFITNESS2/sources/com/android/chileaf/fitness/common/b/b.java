package com.android.chileaf.fitness.common.b;

import com.android.chileaf.bluetooth.connect.data.Data;

/* compiled from: CSCMeasurementParser */
public class b {
    public static String a(Data data) {
        int i2;
        int i3;
        int i4;
        int i5 = 0;
        byte byteValue = data.a(0).byteValue();
        int i6 = 1;
        boolean z = (byteValue & 1) > 0;
        boolean z2 = (byteValue & 2) > 0;
        if (z) {
            int intValue = data.a(20, 1).intValue();
            i2 = data.a(18, 5).intValue();
            i3 = intValue;
            i6 = 7;
        } else {
            i3 = 0;
            i2 = 0;
        }
        if (z2) {
            i5 = data.a(18, i6).intValue();
            i4 = data.a(18, i6 + 2).intValue();
        } else {
            i4 = 0;
        }
        StringBuilder sb = new StringBuilder();
        if (z) {
            sb.append("Wheel rev: ");
            sb.append(i3);
            sb.append(",");
            sb.append("Last wheel event time: ");
            sb.append(i2);
            sb.append(",");
        }
        if (z2) {
            sb.append("Crank rev: ");
            sb.append(i5);
            sb.append(",");
            sb.append("Last crank event time: ");
            sb.append(i4);
            sb.append(",");
        }
        if (!z && !z2) {
            sb.append("No wheel or crank data");
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }
}
