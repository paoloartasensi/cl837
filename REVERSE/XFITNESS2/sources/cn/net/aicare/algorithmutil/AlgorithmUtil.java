package cn.net.aicare.algorithmutil;

import android.util.Log;

public class AlgorithmUtil {
    static {
        try {
            System.loadLibrary("algorithm-lib");
        } catch (Exception unused) {
            Log.e("AlgorithmUtil", "Not found algorithm lib.");
        }
    }

    public static native BodyFatData getBodyFatData(int i2, int i3, int i4, double d, int i5, int i6);
}
