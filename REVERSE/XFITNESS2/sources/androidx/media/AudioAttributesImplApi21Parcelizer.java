package androidx.media;

import android.media.AudioAttributes;
import android.os.Parcelable;
import androidx.versionedparcelable.a;

public final class AudioAttributesImplApi21Parcelizer {
    public static b read(a aVar) {
        b bVar = new b();
        bVar.a = (AudioAttributes) aVar.a(bVar.a, 1);
        bVar.b = aVar.a(bVar.b, 2);
        return bVar;
    }

    public static void write(b bVar, a aVar) {
        aVar.a(false, false);
        aVar.b((Parcelable) bVar.a, 1);
        aVar.b(bVar.b, 2);
    }
}
