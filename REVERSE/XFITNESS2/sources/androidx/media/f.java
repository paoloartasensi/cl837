package androidx.media;

import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.Parcel;
import android.service.media.MediaBrowserService;
import androidx.media.e;

/* compiled from: MediaBrowserServiceCompatApi23 */
class f {

    /* compiled from: MediaBrowserServiceCompatApi23 */
    static class a extends e.b {
        a(Context context, b bVar) {
            super(context, bVar);
        }

        public void onLoadItem(String str, MediaBrowserService.Result<MediaBrowser.MediaItem> result) {
            ((b) this.e).b(str, new e.c(result));
        }
    }

    /* compiled from: MediaBrowserServiceCompatApi23 */
    public interface b extends e.d {
        void b(String str, e.c<Parcel> cVar);
    }

    public static Object a(Context context, b bVar) {
        return new a(context, bVar);
    }
}
