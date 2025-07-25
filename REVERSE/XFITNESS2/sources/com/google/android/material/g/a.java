package com.google.android.material.g;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.StateSet;

/* compiled from: RippleUtils */
public class a {
    public static final boolean a = (Build.VERSION.SDK_INT >= 21);
    private static final int[] b = {16842919};
    private static final int[] c = {16843623, 16842908};
    private static final int[] d = {16842908};
    private static final int[] e = {16843623};

    /* renamed from: f  reason: collision with root package name */
    private static final int[] f1481f = {16842913, 16842919};

    /* renamed from: g  reason: collision with root package name */
    private static final int[] f1482g = {16842913, 16843623, 16842908};

    /* renamed from: h  reason: collision with root package name */
    private static final int[] f1483h = {16842913, 16842908};

    /* renamed from: i  reason: collision with root package name */
    private static final int[] f1484i = {16842913, 16843623};

    /* renamed from: j  reason: collision with root package name */
    private static final int[] f1485j = {16842913};

    public static ColorStateList a(ColorStateList colorStateList) {
        if (a) {
            return new ColorStateList(new int[][]{f1485j, StateSet.NOTHING}, new int[]{a(colorStateList, f1481f), a(colorStateList, b)});
        }
        int[] iArr = f1481f;
        int[] iArr2 = f1482g;
        int[] iArr3 = f1483h;
        int[] iArr4 = f1484i;
        int[] iArr5 = b;
        int[] iArr6 = c;
        int[] iArr7 = d;
        int[] iArr8 = e;
        return new ColorStateList(new int[][]{iArr, iArr2, iArr3, iArr4, f1485j, iArr5, iArr6, iArr7, iArr8, StateSet.NOTHING}, new int[]{a(colorStateList, iArr), a(colorStateList, iArr2), a(colorStateList, iArr3), a(colorStateList, iArr4), 0, a(colorStateList, iArr5), a(colorStateList, iArr6), a(colorStateList, iArr7), a(colorStateList, iArr8), 0});
    }

    private static int a(ColorStateList colorStateList, int[] iArr) {
        int colorForState = colorStateList != null ? colorStateList.getColorForState(iArr, colorStateList.getDefaultColor()) : 0;
        return a ? a(colorForState) : colorForState;
    }

    @TargetApi(21)
    private static int a(int i2) {
        return androidx.core.a.a.c(i2, Math.min(Color.alpha(i2) * 2, 255));
    }
}
