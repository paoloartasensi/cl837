package kotlin.s;

import kotlin.jvm.internal.i;
import kotlin.q.a;
import kotlin.q.c;

/* compiled from: Strings.kt */
class m extends l {
    public static /* synthetic */ String a(String str, String str2, String str3, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            str3 = str;
        }
        return a(str, str2, str3);
    }

    public static final c b(CharSequence charSequence) {
        i.b(charSequence, "$this$indices");
        return new c(0, charSequence.length() - 1);
    }

    public static final int c(CharSequence charSequence) {
        i.b(charSequence, "$this$lastIndex");
        return charSequence.length() - 1;
    }

    public static CharSequence d(CharSequence charSequence) {
        i.b(charSequence, "$this$trim");
        int length = charSequence.length() - 1;
        int i2 = 0;
        boolean z = false;
        while (i2 <= length) {
            boolean a = a.a(charSequence.charAt(!z ? i2 : length));
            if (!z) {
                if (!a) {
                    z = true;
                } else {
                    i2++;
                }
            } else if (!a) {
                break;
            } else {
                length--;
            }
        }
        return charSequence.subSequence(i2, length + 1);
    }

    public static final String a(String str, String str2, String str3) {
        i.b(str, "$this$substringAfter");
        i.b(str2, "delimiter");
        i.b(str3, "missingDelimiterValue");
        int a = a((CharSequence) str, str2, 0, false, 6, (Object) null);
        if (a == -1) {
            return str3;
        }
        String substring = str.substring(a + str2.length(), str.length());
        i.a((Object) substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String b(String str, char c, String str2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            str2 = str;
        }
        return b(str, c, str2);
    }

    public static final String b(String str, char c, String str2) {
        i.b(str, "$this$substringBefore");
        i.b(str2, "missingDelimiterValue");
        int a = a((CharSequence) str, c, 0, false, 6, (Object) null);
        if (a == -1) {
            return str2;
        }
        String substring = str.substring(0, a);
        i.a((Object) substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String a(String str, char c, String str2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            str2 = str;
        }
        return a(str, c, str2);
    }

    public static final String a(String str, char c, String str2) {
        i.b(str, "$this$substringAfterLast");
        i.b(str2, "missingDelimiterValue");
        int b = b((CharSequence) str, c, 0, false, 6, (Object) null);
        if (b == -1) {
            return str2;
        }
        String substring = str.substring(b + 1, str.length());
        i.a((Object) substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String b(String str, String str2, String str3, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            str3 = str;
        }
        return b(str, str2, str3);
    }

    public static final String b(String str, String str2, String str3) {
        i.b(str, "$this$substringBefore");
        i.b(str2, "delimiter");
        i.b(str3, "missingDelimiterValue");
        int a = a((CharSequence) str, str2, 0, false, 6, (Object) null);
        if (a == -1) {
            return str3;
        }
        String substring = str.substring(0, a);
        i.a((Object) substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    public static final boolean a(CharSequence charSequence, int i2, CharSequence charSequence2, int i3, int i4, boolean z) {
        i.b(charSequence, "$this$regionMatchesImpl");
        i.b(charSequence2, "other");
        if (i3 < 0 || i2 < 0 || i2 > charSequence.length() - i4 || i3 > charSequence2.length() - i4) {
            return false;
        }
        for (int i5 = 0; i5 < i4; i5++) {
            if (!b.a(charSequence.charAt(i2 + i5), charSequence2.charAt(i3 + i5), z)) {
                return false;
            }
        }
        return true;
    }

    public static final int b(CharSequence charSequence, char[] cArr, int i2, boolean z) {
        i.b(charSequence, "$this$lastIndexOfAny");
        i.b(cArr, "chars");
        if (z || cArr.length != 1 || !(charSequence instanceof String)) {
            for (int b = f.b(i2, c(charSequence)); b >= 0; b--) {
                char charAt = charSequence.charAt(b);
                int length = cArr.length;
                boolean z2 = false;
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
                        break;
                    } else if (b.a(cArr[i3], charAt, z)) {
                        z2 = true;
                        break;
                    } else {
                        i3++;
                    }
                }
                if (z2) {
                    return b;
                }
            }
            return -1;
        }
        return ((String) charSequence).lastIndexOf(f.a(cArr), i2);
    }

    public static final int a(CharSequence charSequence, char[] cArr, int i2, boolean z) {
        boolean z2;
        i.b(charSequence, "$this$indexOfAny");
        i.b(cArr, "chars");
        if (z || cArr.length != 1 || !(charSequence instanceof String)) {
            int a = f.a(i2, 0);
            int c = c(charSequence);
            if (a > c) {
                return -1;
            }
            while (true) {
                char charAt = charSequence.charAt(a);
                int length = cArr.length;
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
                        z2 = false;
                        break;
                    } else if (b.a(cArr[i3], charAt, z)) {
                        z2 = true;
                        break;
                    } else {
                        i3++;
                    }
                }
                if (z2) {
                    return a;
                }
                if (a == c) {
                    return -1;
                }
                a++;
            }
        } else {
            return ((String) charSequence).indexOf(f.a(cArr), i2);
        }
    }

    public static /* synthetic */ int b(CharSequence charSequence, char c, int i2, boolean z, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = c(charSequence);
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        return b(charSequence, c, i2, z);
    }

    static /* synthetic */ int a(CharSequence charSequence, CharSequence charSequence2, int i2, int i3, boolean z, boolean z2, int i4, Object obj) {
        return a(charSequence, charSequence2, i2, i3, z, (i4 & 16) != 0 ? false : z2);
    }

    public static final int b(CharSequence charSequence, char c, int i2, boolean z) {
        i.b(charSequence, "$this$lastIndexOf");
        if (!z && (charSequence instanceof String)) {
            return ((String) charSequence).lastIndexOf(c, i2);
        }
        return b(charSequence, new char[]{c}, i2, z);
    }

    private static final int a(CharSequence charSequence, CharSequence charSequence2, int i2, int i3, boolean z, boolean z2) {
        a aVar;
        if (!z2) {
            aVar = new c(f.a(i2, 0), f.b(i3, charSequence.length()));
        } else {
            aVar = f.c(f.b(i2, c(charSequence)), f.a(i3, 0));
        }
        if (!(charSequence instanceof String) || !(charSequence2 instanceof String)) {
            int a = aVar.a();
            int b = aVar.b();
            int c = aVar.c();
            if (c >= 0) {
                if (a > b) {
                    return -1;
                }
            } else if (a < b) {
                return -1;
            }
            while (true) {
                if (a(charSequence2, 0, charSequence, a, charSequence2.length(), z)) {
                    return a;
                }
                if (a == b) {
                    return -1;
                }
                a += c;
            }
        } else {
            int a2 = aVar.a();
            int b2 = aVar.b();
            int c2 = aVar.c();
            if (c2 >= 0) {
                if (a2 > b2) {
                    return -1;
                }
            } else if (a2 < b2) {
                return -1;
            }
            while (true) {
                if (l.a((String) charSequence2, 0, (String) charSequence, a2, charSequence2.length(), z)) {
                    return a2;
                }
                if (a2 == b2) {
                    return -1;
                }
                a2 += c2;
            }
        }
    }

    public static /* synthetic */ int b(CharSequence charSequence, String str, int i2, boolean z, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = c(charSequence);
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        return b(charSequence, str, i2, z);
    }

    public static final int b(CharSequence charSequence, String str, int i2, boolean z) {
        i.b(charSequence, "$this$lastIndexOf");
        i.b(str, "string");
        if (z || !(charSequence instanceof String)) {
            return a(charSequence, (CharSequence) str, i2, 0, z, true);
        }
        return ((String) charSequence).lastIndexOf(str, i2);
    }

    public static /* synthetic */ int a(CharSequence charSequence, char c, int i2, boolean z, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        return a(charSequence, c, i2, z);
    }

    public static final int a(CharSequence charSequence, char c, int i2, boolean z) {
        i.b(charSequence, "$this$indexOf");
        if (!z && (charSequence instanceof String)) {
            return ((String) charSequence).indexOf(c, i2);
        }
        return a(charSequence, new char[]{c}, i2, z);
    }

    public static /* synthetic */ int a(CharSequence charSequence, String str, int i2, boolean z, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        return a(charSequence, str, i2, z);
    }

    public static final int a(CharSequence charSequence, String str, int i2, boolean z) {
        i.b(charSequence, "$this$indexOf");
        i.b(str, "string");
        if (!z && (charSequence instanceof String)) {
            return ((String) charSequence).indexOf(str, i2);
        }
        return a(charSequence, str, i2, charSequence.length(), z, false, 16, (Object) null);
    }

    public static /* synthetic */ boolean a(CharSequence charSequence, CharSequence charSequence2, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        return a(charSequence, charSequence2, z);
    }

    public static final boolean a(CharSequence charSequence, CharSequence charSequence2, boolean z) {
        i.b(charSequence, "$this$contains");
        i.b(charSequence2, "other");
        if (charSequence2 instanceof String) {
            if (a(charSequence, (String) charSequence2, 0, z, 2, (Object) null) >= 0) {
                return true;
            }
        } else {
            if (a(charSequence, charSequence2, 0, charSequence.length(), z, false, 16, (Object) null) >= 0) {
                return true;
            }
        }
        return false;
    }
}
