package com.android.chileaf.fitness.common.b;

import com.android.chileaf.bluetooth.connect.data.Data;

/* compiled from: BodySensorLocationParser */
public class a {
    public static String a(Data data) {
        switch (data.a(17, 0).intValue()) {
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
            default:
                return "Other";
        }
    }
}
