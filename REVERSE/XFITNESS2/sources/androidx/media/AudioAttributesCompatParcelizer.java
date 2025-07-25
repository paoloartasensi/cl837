package androidx.media;

import androidx.versionedparcelable.a;
import androidx.versionedparcelable.c;

public final class AudioAttributesCompatParcelizer {
    public static AudioAttributesCompat read(a aVar) {
        AudioAttributesCompat audioAttributesCompat = new AudioAttributesCompat();
        audioAttributesCompat.a = (a) aVar.a(audioAttributesCompat.a, 1);
        return audioAttributesCompat;
    }

    public static void write(AudioAttributesCompat audioAttributesCompat, a aVar) {
        aVar.a(false, false);
        aVar.b((c) audioAttributesCompat.a, 1);
    }
}
