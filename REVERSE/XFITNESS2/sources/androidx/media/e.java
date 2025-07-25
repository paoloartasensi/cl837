package androidx.media;

import android.content.Context;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.service.media.MediaBrowserService;
import android.support.v4.media.session.MediaSessionCompat;
import java.util.ArrayList;
import java.util.List;

/* compiled from: MediaBrowserServiceCompatApi21 */
class e {

    /* compiled from: MediaBrowserServiceCompatApi21 */
    static class a {
        final String a;
        final Bundle b;
    }

    /* compiled from: MediaBrowserServiceCompatApi21 */
    static class b extends MediaBrowserService {
        final d e;

        b(Context context, d dVar) {
            attachBaseContext(context);
            this.e = dVar;
        }

        public MediaBrowserService.BrowserRoot onGetRoot(String str, int i2, Bundle bundle) {
            MediaSessionCompat.ensureClassLoader(bundle);
            a a = this.e.a(str, i2, bundle == null ? null : new Bundle(bundle));
            if (a == null) {
                return null;
            }
            return new MediaBrowserService.BrowserRoot(a.a, a.b);
        }

        public void onLoadChildren(String str, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result) {
            this.e.a(str, new c(result));
        }
    }

    /* compiled from: MediaBrowserServiceCompatApi21 */
    public interface d {
        a a(String str, int i2, Bundle bundle);

        void a(String str, c<List<Parcel>> cVar);
    }

    public static Object a(Context context, d dVar) {
        return new b(context, dVar);
    }

    public static void a(Object obj) {
        ((MediaBrowserService) obj).onCreate();
    }

    public static IBinder a(Object obj, Intent intent) {
        return ((MediaBrowserService) obj).onBind(intent);
    }

    /* compiled from: MediaBrowserServiceCompatApi21 */
    static class c<T> {
        MediaBrowserService.Result a;

        c(MediaBrowserService.Result result) {
            this.a = result;
        }

        public void a(T t) {
            if (t instanceof List) {
                this.a.sendResult(a((List<Parcel>) (List) t));
            } else if (t instanceof Parcel) {
                Parcel parcel = (Parcel) t;
                parcel.setDataPosition(0);
                this.a.sendResult(MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel));
                parcel.recycle();
            } else {
                this.a.sendResult((Object) null);
            }
        }

        /* access modifiers changed from: package-private */
        public List<MediaBrowser.MediaItem> a(List<Parcel> list) {
            if (list == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Parcel next : list) {
                next.setDataPosition(0);
                arrayList.add(MediaBrowser.MediaItem.CREATOR.createFromParcel(next));
                next.recycle();
            }
            return arrayList;
        }
    }
}
