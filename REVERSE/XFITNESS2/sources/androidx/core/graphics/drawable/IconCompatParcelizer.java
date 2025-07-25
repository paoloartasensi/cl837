package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.os.Parcelable;
import androidx.versionedparcelable.a;

public class IconCompatParcelizer {
    public static IconCompat read(a aVar) {
        IconCompat iconCompat = new IconCompat();
        iconCompat.a = aVar.a(iconCompat.a, 1);
        iconCompat.c = aVar.a(iconCompat.c, 2);
        iconCompat.d = aVar.a(iconCompat.d, 3);
        iconCompat.e = aVar.a(iconCompat.e, 4);
        iconCompat.f496f = aVar.a(iconCompat.f496f, 5);
        iconCompat.f497g = (ColorStateList) aVar.a(iconCompat.f497g, 6);
        iconCompat.f499i = aVar.a(iconCompat.f499i, 7);
        iconCompat.c();
        return iconCompat;
    }

    public static void write(IconCompat iconCompat, a aVar) {
        aVar.a(true, true);
        iconCompat.a(aVar.c());
        int i2 = iconCompat.a;
        if (-1 != i2) {
            aVar.b(i2, 1);
        }
        byte[] bArr = iconCompat.c;
        if (bArr != null) {
            aVar.b(bArr, 2);
        }
        Parcelable parcelable = iconCompat.d;
        if (parcelable != null) {
            aVar.b(parcelable, 3);
        }
        int i3 = iconCompat.e;
        if (i3 != 0) {
            aVar.b(i3, 4);
        }
        int i4 = iconCompat.f496f;
        if (i4 != 0) {
            aVar.b(i4, 5);
        }
        ColorStateList colorStateList = iconCompat.f497g;
        if (colorStateList != null) {
            aVar.b((Parcelable) colorStateList, 6);
        }
        String str = iconCompat.f499i;
        if (str != null) {
            aVar.b(str, 7);
        }
    }
}
