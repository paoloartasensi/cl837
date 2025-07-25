package androidx.core.a;

import android.graphics.Path;
import android.util.Log;
import java.util.ArrayList;

/* compiled from: PathParser */
public class b {

    /* compiled from: PathParser */
    private static class a {
        int a;
        boolean b;

        a() {
        }
    }

    static float[] a(float[] fArr, int i2, int i3) {
        if (i2 <= i3) {
            int length = fArr.length;
            if (i2 < 0 || i2 > length) {
                throw new ArrayIndexOutOfBoundsException();
            }
            int i4 = i3 - i2;
            int min = Math.min(i4, length - i2);
            float[] fArr2 = new float[i4];
            System.arraycopy(fArr, i2, fArr2, 0, min);
            return fArr2;
        }
        throw new IllegalArgumentException();
    }

    public static Path b(String str) {
        Path path = new Path();
        C0012b[] a2 = a(str);
        if (a2 == null) {
            return null;
        }
        try {
            C0012b.a(a2, path);
            return path;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error in parsing " + str, e);
        }
    }

    private static float[] c(String str) {
        if (str.charAt(0) == 'z' || str.charAt(0) == 'Z') {
            return new float[0];
        }
        try {
            float[] fArr = new float[str.length()];
            a aVar = new a();
            int length = str.length();
            int i2 = 1;
            int i3 = 0;
            while (i2 < length) {
                a(str, i2, aVar);
                int i4 = aVar.a;
                if (i2 < i4) {
                    fArr[i3] = Float.parseFloat(str.substring(i2, i4));
                    i3++;
                }
                i2 = aVar.b ? i4 : i4 + 1;
            }
            return a(fArr, 0, i3);
        } catch (NumberFormatException e) {
            throw new RuntimeException("error in parsing \"" + str + "\"", e);
        }
    }

    /* renamed from: androidx.core.a.b$b  reason: collision with other inner class name */
    /* compiled from: PathParser */
    public static class C0012b {
        public char a;
        public float[] b;

        C0012b(char c, float[] fArr) {
            this.a = c;
            this.b = fArr;
        }

        public static void a(C0012b[] bVarArr, Path path) {
            float[] fArr = new float[6];
            char c = 'm';
            for (int i2 = 0; i2 < bVarArr.length; i2++) {
                a(path, fArr, c, bVarArr[i2].a, bVarArr[i2].b);
                c = bVarArr[i2].a;
            }
        }

        C0012b(C0012b bVar) {
            this.a = bVar.a;
            float[] fArr = bVar.b;
            this.b = b.a(fArr, 0, fArr.length);
        }

        public void a(C0012b bVar, C0012b bVar2, float f2) {
            this.a = bVar.a;
            int i2 = 0;
            while (true) {
                float[] fArr = bVar.b;
                if (i2 < fArr.length) {
                    this.b[i2] = (fArr[i2] * (1.0f - f2)) + (bVar2.b[i2] * f2);
                    i2++;
                } else {
                    return;
                }
            }
        }

        private static void a(Path path, float[] fArr, char c, char c2, float[] fArr2) {
            int i2;
            int i3;
            float f2;
            float f3;
            float f4;
            float f5;
            float f6;
            float f7;
            float f8;
            float f9;
            float f10;
            float f11;
            Path path2 = path;
            char c3 = c2;
            float[] fArr3 = fArr2;
            float f12 = fArr[0];
            float f13 = fArr[1];
            float f14 = fArr[2];
            float f15 = fArr[3];
            float f16 = fArr[4];
            float f17 = fArr[5];
            switch (c3) {
                case 'A':
                case 'a':
                    i2 = 7;
                    break;
                case 'C':
                case 'c':
                    i2 = 6;
                    break;
                case 'H':
                case 'V':
                case 'h':
                case 'v':
                    i2 = 1;
                    break;
                case 'Q':
                case 'S':
                case 'q':
                case 's':
                    i2 = 4;
                    break;
                case 'Z':
                case 'z':
                    path.close();
                    path2.moveTo(f16, f17);
                    f12 = f16;
                    f14 = f12;
                    f13 = f17;
                    f15 = f13;
                    break;
            }
            i2 = 2;
            float f18 = f12;
            float f19 = f13;
            float f20 = f16;
            float f21 = f17;
            int i4 = 0;
            char c4 = c;
            while (i4 < fArr3.length) {
                if (c3 != 'A') {
                    if (c3 == 'C') {
                        i3 = i4;
                        int i5 = i3 + 2;
                        int i6 = i3 + 3;
                        int i7 = i3 + 4;
                        int i8 = i3 + 5;
                        path.cubicTo(fArr3[i3 + 0], fArr3[i3 + 1], fArr3[i5], fArr3[i6], fArr3[i7], fArr3[i8]);
                        f18 = fArr3[i7];
                        float f22 = fArr3[i8];
                        float f23 = fArr3[i5];
                        float f24 = fArr3[i6];
                        f19 = f22;
                        f15 = f24;
                        f14 = f23;
                    } else if (c3 == 'H') {
                        i3 = i4;
                        int i9 = i3 + 0;
                        path2.lineTo(fArr3[i9], f19);
                        f18 = fArr3[i9];
                    } else if (c3 == 'Q') {
                        i3 = i4;
                        int i10 = i3 + 0;
                        int i11 = i3 + 1;
                        int i12 = i3 + 2;
                        int i13 = i3 + 3;
                        path2.quadTo(fArr3[i10], fArr3[i11], fArr3[i12], fArr3[i13]);
                        float f25 = fArr3[i10];
                        float f26 = fArr3[i11];
                        f18 = fArr3[i12];
                        f19 = fArr3[i13];
                        f14 = f25;
                        f15 = f26;
                    } else if (c3 == 'V') {
                        i3 = i4;
                        int i14 = i3 + 0;
                        path2.lineTo(f18, fArr3[i14]);
                        f19 = fArr3[i14];
                    } else if (c3 != 'a') {
                        if (c3 == 'c') {
                            int i15 = i4 + 2;
                            int i16 = i4 + 3;
                            int i17 = i4 + 4;
                            int i18 = i4 + 5;
                            path.rCubicTo(fArr3[i4 + 0], fArr3[i4 + 1], fArr3[i15], fArr3[i16], fArr3[i17], fArr3[i18]);
                            f5 = fArr3[i15] + f18;
                            f4 = fArr3[i16] + f19;
                            f18 += fArr3[i17];
                            f6 = fArr3[i18];
                            f19 += f6;
                            f14 = f5;
                            f15 = f4;
                        } else if (c3 != 'h') {
                            if (c3 != 'q') {
                                if (c3 == 'v') {
                                    int i19 = i4 + 0;
                                    path2.rLineTo(0.0f, fArr3[i19]);
                                    f7 = fArr3[i19];
                                } else if (c3 != 'L') {
                                    if (c3 == 'M') {
                                        int i20 = i4 + 0;
                                        f18 = fArr3[i20];
                                        int i21 = i4 + 1;
                                        f19 = fArr3[i21];
                                        if (i4 > 0) {
                                            path2.lineTo(fArr3[i20], fArr3[i21]);
                                        } else {
                                            path2.moveTo(fArr3[i20], fArr3[i21]);
                                        }
                                    } else if (c3 == 'S') {
                                        if (c4 == 'c' || c4 == 's' || c4 == 'C' || c4 == 'S') {
                                            f18 = (f18 * 2.0f) - f14;
                                            f19 = (f19 * 2.0f) - f15;
                                        }
                                        float f27 = f19;
                                        int i22 = i4 + 0;
                                        int i23 = i4 + 1;
                                        int i24 = i4 + 2;
                                        int i25 = i4 + 3;
                                        path.cubicTo(f18, f27, fArr3[i22], fArr3[i23], fArr3[i24], fArr3[i25]);
                                        f5 = fArr3[i22];
                                        f4 = fArr3[i23];
                                        f18 = fArr3[i24];
                                        f19 = fArr3[i25];
                                        f14 = f5;
                                        f15 = f4;
                                    } else if (c3 == 'T') {
                                        if (c4 == 'q' || c4 == 't' || c4 == 'Q' || c4 == 'T') {
                                            f18 = (f18 * 2.0f) - f14;
                                            f19 = (f19 * 2.0f) - f15;
                                        }
                                        int i26 = i4 + 0;
                                        int i27 = i4 + 1;
                                        path2.quadTo(f18, f19, fArr3[i26], fArr3[i27]);
                                        float f28 = fArr3[i26];
                                        float f29 = fArr3[i27];
                                        i3 = i4;
                                        f15 = f19;
                                        f14 = f18;
                                        f18 = f28;
                                        f19 = f29;
                                    } else if (c3 == 'l') {
                                        int i28 = i4 + 0;
                                        int i29 = i4 + 1;
                                        path2.rLineTo(fArr3[i28], fArr3[i29]);
                                        f18 += fArr3[i28];
                                        f7 = fArr3[i29];
                                    } else if (c3 == 'm') {
                                        int i30 = i4 + 0;
                                        f18 += fArr3[i30];
                                        int i31 = i4 + 1;
                                        f19 += fArr3[i31];
                                        if (i4 > 0) {
                                            path2.rLineTo(fArr3[i30], fArr3[i31]);
                                        } else {
                                            path2.rMoveTo(fArr3[i30], fArr3[i31]);
                                        }
                                    } else if (c3 == 's') {
                                        if (c4 == 'c' || c4 == 's' || c4 == 'C' || c4 == 'S') {
                                            float f30 = f18 - f14;
                                            f8 = f19 - f15;
                                            f9 = f30;
                                        } else {
                                            f9 = 0.0f;
                                            f8 = 0.0f;
                                        }
                                        int i32 = i4 + 0;
                                        int i33 = i4 + 1;
                                        int i34 = i4 + 2;
                                        int i35 = i4 + 3;
                                        path.rCubicTo(f9, f8, fArr3[i32], fArr3[i33], fArr3[i34], fArr3[i35]);
                                        f5 = fArr3[i32] + f18;
                                        f4 = fArr3[i33] + f19;
                                        f18 += fArr3[i34];
                                        f6 = fArr3[i35];
                                    } else if (c3 == 't') {
                                        if (c4 == 'q' || c4 == 't' || c4 == 'Q' || c4 == 'T') {
                                            f10 = f18 - f14;
                                            f11 = f19 - f15;
                                        } else {
                                            f11 = 0.0f;
                                            f10 = 0;
                                        }
                                        int i36 = i4 + 0;
                                        int i37 = i4 + 1;
                                        path2.rQuadTo(f10, f11, fArr3[i36], fArr3[i37]);
                                        float f31 = f10 + f18;
                                        float f32 = f11 + f19;
                                        f18 += fArr3[i36];
                                        f19 += fArr3[i37];
                                        f15 = f32;
                                        f14 = f31;
                                    }
                                    i3 = i4;
                                    f21 = f19;
                                    f20 = f18;
                                } else {
                                    int i38 = i4 + 0;
                                    int i39 = i4 + 1;
                                    path2.lineTo(fArr3[i38], fArr3[i39]);
                                    f18 = fArr3[i38];
                                    f19 = fArr3[i39];
                                }
                                f19 += f7;
                            } else {
                                int i40 = i4 + 0;
                                int i41 = i4 + 1;
                                int i42 = i4 + 2;
                                int i43 = i4 + 3;
                                path2.rQuadTo(fArr3[i40], fArr3[i41], fArr3[i42], fArr3[i43]);
                                f5 = fArr3[i40] + f18;
                                f4 = fArr3[i41] + f19;
                                f18 += fArr3[i42];
                                f6 = fArr3[i43];
                            }
                            f19 += f6;
                            f14 = f5;
                            f15 = f4;
                        } else {
                            int i44 = i4 + 0;
                            path2.rLineTo(fArr3[i44], 0.0f);
                            f18 += fArr3[i44];
                        }
                        i3 = i4;
                    } else {
                        int i45 = i4 + 5;
                        int i46 = i4 + 6;
                        i3 = i4;
                        a(path, f18, f19, fArr3[i45] + f18, fArr3[i46] + f19, fArr3[i4 + 0], fArr3[i4 + 1], fArr3[i4 + 2], fArr3[i4 + 3] != 0.0f, fArr3[i4 + 4] != 0.0f);
                        f2 = f18 + fArr3[i45];
                        f3 = f19 + fArr3[i46];
                    }
                    i4 = i3 + i2;
                    c4 = c2;
                    c3 = c4;
                } else {
                    i3 = i4;
                    int i47 = i3 + 5;
                    int i48 = i3 + 6;
                    a(path, f18, f19, fArr3[i47], fArr3[i48], fArr3[i3 + 0], fArr3[i3 + 1], fArr3[i3 + 2], fArr3[i3 + 3] != 0.0f, fArr3[i3 + 4] != 0.0f);
                    f2 = fArr3[i47];
                    f3 = fArr3[i48];
                }
                f15 = f19;
                f14 = f18;
                i4 = i3 + i2;
                c4 = c2;
                c3 = c4;
            }
            fArr[0] = f18;
            fArr[1] = f19;
            fArr[2] = f14;
            fArr[3] = f15;
            fArr[4] = f20;
            fArr[5] = f21;
        }

        private static void a(Path path, float f2, float f3, float f4, float f5, float f6, float f7, float f8, boolean z, boolean z2) {
            double d;
            double d2;
            float f9 = f2;
            float f10 = f4;
            float f11 = f6;
            boolean z3 = z2;
            double radians = Math.toRadians((double) f8);
            double cos = Math.cos(radians);
            double sin = Math.sin(radians);
            double d3 = (double) f9;
            Double.isNaN(d3);
            double d4 = d3 * cos;
            double d5 = d3;
            double d6 = (double) f3;
            Double.isNaN(d6);
            double d7 = (double) f11;
            Double.isNaN(d7);
            double d8 = (d4 + (d6 * sin)) / d7;
            double d9 = (double) (-f9);
            Double.isNaN(d9);
            Double.isNaN(d6);
            double d10 = (d9 * sin) + (d6 * cos);
            double d11 = d6;
            double d12 = (double) f7;
            Double.isNaN(d12);
            double d13 = (double) f10;
            Double.isNaN(d13);
            double d14 = d10 / d12;
            double d15 = (double) f5;
            Double.isNaN(d15);
            Double.isNaN(d7);
            double d16 = ((d13 * cos) + (d15 * sin)) / d7;
            double d17 = d7;
            double d18 = (double) (-f10);
            Double.isNaN(d18);
            Double.isNaN(d15);
            Double.isNaN(d12);
            double d19 = ((d18 * sin) + (d15 * cos)) / d12;
            double d20 = d8 - d16;
            double d21 = d14 - d19;
            double d22 = (d8 + d16) / 2.0d;
            double d23 = (d14 + d19) / 2.0d;
            double d24 = sin;
            double d25 = (d20 * d20) + (d21 * d21);
            if (d25 == 0.0d) {
                Log.w("PathParser", " Points are coincident");
                return;
            }
            double d26 = (1.0d / d25) - 0.25d;
            if (d26 < 0.0d) {
                Log.w("PathParser", "Points are too far apart " + d25);
                float sqrt = (float) (Math.sqrt(d25) / 1.99999d);
                a(path, f2, f3, f4, f5, f11 * sqrt, f7 * sqrt, f8, z, z2);
                return;
            }
            double sqrt2 = Math.sqrt(d26);
            double d27 = d20 * sqrt2;
            double d28 = sqrt2 * d21;
            boolean z4 = z2;
            if (z == z4) {
                d2 = d22 - d28;
                d = d23 + d27;
            } else {
                d2 = d22 + d28;
                d = d23 - d27;
            }
            double atan2 = Math.atan2(d14 - d, d8 - d2);
            double atan22 = Math.atan2(d19 - d, d16 - d2) - atan2;
            if (z4 != (atan22 >= 0.0d)) {
                atan22 = atan22 > 0.0d ? atan22 - 6.283185307179586d : atan22 + 6.283185307179586d;
            }
            Double.isNaN(d17);
            double d29 = d2 * d17;
            Double.isNaN(d12);
            double d30 = d * d12;
            a(path, (d29 * cos) - (d30 * d24), (d29 * d24) + (d30 * cos), d17, d12, d5, d11, radians, atan2, atan22);
        }

        private static void a(Path path, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9) {
            double d10 = d3;
            int ceil = (int) Math.ceil(Math.abs((d9 * 4.0d) / 3.141592653589793d));
            double cos = Math.cos(d7);
            double sin = Math.sin(d7);
            double cos2 = Math.cos(d8);
            double sin2 = Math.sin(d8);
            double d11 = -d10;
            double d12 = d11 * cos;
            double d13 = d4 * sin;
            double d14 = (d12 * sin2) - (d13 * cos2);
            double d15 = d11 * sin;
            double d16 = d4 * cos;
            double d17 = (sin2 * d15) + (cos2 * d16);
            double d18 = (double) ceil;
            Double.isNaN(d18);
            double d19 = d9 / d18;
            double d20 = d8;
            double d21 = d17;
            double d22 = d14;
            int i2 = 0;
            double d23 = d5;
            double d24 = d6;
            while (i2 < ceil) {
                double d25 = d20 + d19;
                double sin3 = Math.sin(d25);
                double cos3 = Math.cos(d25);
                double d26 = (d + ((d10 * cos) * cos3)) - (d13 * sin3);
                double d27 = d2 + (d10 * sin * cos3) + (d16 * sin3);
                double d28 = (d12 * sin3) - (d13 * cos3);
                double d29 = (sin3 * d15) + (cos3 * d16);
                double d30 = d25 - d20;
                double tan = Math.tan(d30 / 2.0d);
                double sin4 = (Math.sin(d30) * (Math.sqrt(((tan * 3.0d) * tan) + 4.0d) - 1.0d)) / 3.0d;
                double d31 = d23 + (d22 * sin4);
                double d32 = cos;
                double d33 = sin;
                path.rLineTo(0.0f, 0.0f);
                float f2 = (float) (d26 - (sin4 * d28));
                float f3 = (float) (d27 - (sin4 * d29));
                path.cubicTo((float) d31, (float) (d24 + (d21 * sin4)), f2, f3, (float) d26, (float) d27);
                i2++;
                d19 = d19;
                sin = d33;
                d23 = d26;
                d15 = d15;
                cos = d32;
                d20 = d25;
                d21 = d29;
                d22 = d28;
                ceil = ceil;
                d24 = d27;
                d10 = d3;
            }
        }
    }

    public static void b(C0012b[] bVarArr, C0012b[] bVarArr2) {
        for (int i2 = 0; i2 < bVarArr2.length; i2++) {
            bVarArr[i2].a = bVarArr2[i2].a;
            for (int i3 = 0; i3 < bVarArr2[i2].b.length; i3++) {
                bVarArr[i2].b[i3] = bVarArr2[i2].b[i3];
            }
        }
    }

    public static C0012b[] a(String str) {
        if (str == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        int i3 = 0;
        while (i2 < str.length()) {
            int a2 = a(str, i2);
            String trim = str.substring(i3, a2).trim();
            if (trim.length() > 0) {
                a((ArrayList<C0012b>) arrayList, trim.charAt(0), c(trim));
            }
            i3 = a2;
            i2 = a2 + 1;
        }
        if (i2 - i3 == 1 && i3 < str.length()) {
            a((ArrayList<C0012b>) arrayList, str.charAt(i3), new float[0]);
        }
        return (C0012b[]) arrayList.toArray(new C0012b[arrayList.size()]);
    }

    public static C0012b[] a(C0012b[] bVarArr) {
        if (bVarArr == null) {
            return null;
        }
        C0012b[] bVarArr2 = new C0012b[bVarArr.length];
        for (int i2 = 0; i2 < bVarArr.length; i2++) {
            bVarArr2[i2] = new C0012b(bVarArr[i2]);
        }
        return bVarArr2;
    }

    public static boolean a(C0012b[] bVarArr, C0012b[] bVarArr2) {
        if (bVarArr == null || bVarArr2 == null || bVarArr.length != bVarArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 < bVarArr.length; i2++) {
            if (bVarArr[i2].a != bVarArr2[i2].a || bVarArr[i2].b.length != bVarArr2[i2].b.length) {
                return false;
            }
        }
        return true;
    }

    private static int a(String str, int i2) {
        while (i2 < str.length()) {
            char charAt = str.charAt(i2);
            if (((charAt - 'A') * (charAt - 'Z') <= 0 || (charAt - 'a') * (charAt - 'z') <= 0) && charAt != 'e' && charAt != 'E') {
                return i2;
            }
            i2++;
        }
        return i2;
    }

    private static void a(ArrayList<C0012b> arrayList, char c, float[] fArr) {
        arrayList.add(new C0012b(c, fArr));
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0031, code lost:
        r2 = false;
     */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x003a A[LOOP:0: B:1:0x0007->B:20:0x003a, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x003d A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(java.lang.String r8, int r9, androidx.core.a.b.a r10) {
        /*
            r0 = 0
            r10.b = r0
            r1 = r9
            r2 = 0
            r3 = 0
            r4 = 0
        L_0x0007:
            int r5 = r8.length()
            if (r1 >= r5) goto L_0x003d
            char r5 = r8.charAt(r1)
            r6 = 32
            r7 = 1
            if (r5 == r6) goto L_0x0035
            r6 = 69
            if (r5 == r6) goto L_0x0033
            r6 = 101(0x65, float:1.42E-43)
            if (r5 == r6) goto L_0x0033
            switch(r5) {
                case 44: goto L_0x0035;
                case 45: goto L_0x002a;
                case 46: goto L_0x0022;
                default: goto L_0x0021;
            }
        L_0x0021:
            goto L_0x0031
        L_0x0022:
            if (r3 != 0) goto L_0x0027
            r2 = 0
            r3 = 1
            goto L_0x0037
        L_0x0027:
            r10.b = r7
            goto L_0x0035
        L_0x002a:
            if (r1 == r9) goto L_0x0031
            if (r2 != 0) goto L_0x0031
            r10.b = r7
            goto L_0x0035
        L_0x0031:
            r2 = 0
            goto L_0x0037
        L_0x0033:
            r2 = 1
            goto L_0x0037
        L_0x0035:
            r2 = 0
            r4 = 1
        L_0x0037:
            if (r4 == 0) goto L_0x003a
            goto L_0x003d
        L_0x003a:
            int r1 = r1 + 1
            goto L_0x0007
        L_0x003d:
            r10.a = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.a.b.a(java.lang.String, int, androidx.core.a.b$a):void");
    }
}
