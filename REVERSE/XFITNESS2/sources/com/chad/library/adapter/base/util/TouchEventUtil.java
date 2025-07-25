package com.chad.library.adapter.base.util;

public class TouchEventUtil {
    public static String getTouchAction(int i2) {
        String str = "Unknow:id=" + i2;
        if (i2 == 0) {
            return "ACTION_DOWN";
        }
        if (i2 == 1) {
            return "ACTION_UP";
        }
        if (i2 == 2) {
            return "ACTION_MOVE";
        }
        if (i2 != 3) {
            return i2 != 4 ? str : "ACTION_OUTSIDE";
        }
        return "ACTION_CANCEL";
    }
}
