package okio;

import android.support.v4.media.session.PlaybackStateCompat;

/* compiled from: SegmentPool */
final class p {
    static o a;
    static long b;

    private p() {
    }

    static o a() {
        synchronized (p.class) {
            if (a == null) {
                return new o();
            }
            o oVar = a;
            a = oVar.f2109f;
            oVar.f2109f = null;
            b -= PlaybackStateCompat.ACTION_PLAY_FROM_URI;
            return oVar;
        }
    }

    static void a(o oVar) {
        if (oVar.f2109f != null || oVar.f2110g != null) {
            throw new IllegalArgumentException();
        } else if (!oVar.d) {
            synchronized (p.class) {
                if (b + PlaybackStateCompat.ACTION_PLAY_FROM_URI <= PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH) {
                    b += PlaybackStateCompat.ACTION_PLAY_FROM_URI;
                    oVar.f2109f = a;
                    oVar.c = 0;
                    oVar.b = 0;
                    a = oVar;
                }
            }
        }
    }
}
