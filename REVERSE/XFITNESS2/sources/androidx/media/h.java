package androidx.media;

import android.media.session.MediaSessionManager;
import android.os.Build;

/* compiled from: MediaSessionManager */
public final class h {
    i a;

    public h(String str, int i2, int i3) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.a = new j(str, i2, i3);
        } else {
            this.a = new k(str, i2, i3);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof h)) {
            return false;
        }
        return this.a.equals(((h) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public h(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        this.a = new j(remoteUserInfo);
    }
}
