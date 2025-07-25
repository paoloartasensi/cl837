package com.google.gson.internal;

/* compiled from: JavaVersion */
public final class c {
    private static final int a = a();

    private static int a() {
        return b(System.getProperty("java.version"));
    }

    static int b(String str) {
        int c = c(str);
        if (c == -1) {
            c = a(str);
        }
        if (c == -1) {
            return 6;
        }
        return c;
    }

    private static int c(String str) {
        try {
            String[] split = str.split("[._]");
            int parseInt = Integer.parseInt(split[0]);
            return (parseInt != 1 || split.length <= 1) ? parseInt : Integer.parseInt(split[1]);
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    private static int a(String str) {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < str.length(); i2++) {
                char charAt = str.charAt(i2);
                if (!Character.isDigit(charAt)) {
                    break;
                }
                sb.append(charAt);
            }
            return Integer.parseInt(sb.toString());
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    public static int b() {
        return a;
    }

    public static boolean c() {
        return a >= 9;
    }
}
