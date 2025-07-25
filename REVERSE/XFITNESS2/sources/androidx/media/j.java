package androidx.media;

import android.media.session.MediaSessionManager;
import androidx.core.g.c;

/* compiled from: MediaSessionManagerImplApi28 */
final class j implements i {
    final MediaSessionManager.RemoteUserInfo a;

    j(String str, int i2, int i3) {
        this.a = new MediaSessionManager.RemoteUserInfo(str, i2, i3);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof j)) {
            return false;
        }
        return this.a.equals(((j) obj).a);
    }

    public int hashCode() {
        return c.a(this.a);
    }

    j(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        this.a = remoteUserInfo;
    }
}
