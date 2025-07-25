package h.a.a.a.i;

import android.graphics.Color;
import com.jeremyliao.liveeventbus.BuildConfig;

/* compiled from: ColorTemplate */
public class a {
    static {
        Color.rgb(207, 248, 246);
        Color.rgb(148, 212, 212);
        Color.rgb(136, 180, 187);
        Color.rgb(118, 174, 175);
        Color.rgb(42, 109, 130);
        Color.rgb(217, 80, 138);
        Color.rgb(254, 149, 7);
        Color.rgb(254, 247, 120);
        Color.rgb(106, 167, 134);
        Color.rgb(53, 194, 209);
        Color.rgb(64, 89, 128);
        Color.rgb(149, 165, 124);
        Color.rgb(217, 184, 162);
        Color.rgb(191, 134, 134);
        Color.rgb(179, 48, 80);
        Color.rgb(193, 37, 82);
        Color.rgb(255, 102, 0);
        Color.rgb(245, 199, 0);
        Color.rgb(106, 150, 31);
        Color.rgb(179, 100, 53);
        Color.rgb(192, 255, 140);
        Color.rgb(255, 247, 140);
        Color.rgb(255, 208, 140);
        Color.rgb(140, 234, 255);
        Color.rgb(255, 140, 157);
        a("#2ecc71");
        a("#f1c40f");
        a("#e74c3c");
        a("#3498db");
    }

    public static int a(int i2, int i3) {
        return (i2 & 16777215) | ((i3 & 255) << 24);
    }

    public static int a(String str) {
        int parseLong = (int) Long.parseLong(str.replace("#", BuildConfig.FLAVOR), 16);
        return Color.rgb((parseLong >> 16) & 255, (parseLong >> 8) & 255, (parseLong >> 0) & 255);
    }
}
