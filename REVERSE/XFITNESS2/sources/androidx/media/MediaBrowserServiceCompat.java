package androidx.media;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import androidx.media.e;
import androidx.media.f;
import androidx.media.g;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public abstract class MediaBrowserServiceCompat extends Service {

    /* renamed from: j  reason: collision with root package name */
    static final boolean f667j = Log.isLoggable("MBServiceCompat", 3);
    private g e;

    /* renamed from: f  reason: collision with root package name */
    final g.a.a<IBinder, f> f668f = new g.a.a<>();

    /* renamed from: g  reason: collision with root package name */
    f f669g;

    /* renamed from: h  reason: collision with root package name */
    final q f670h = new q();

    /* renamed from: i  reason: collision with root package name */
    MediaSessionCompat.Token f671i;

    class a extends m<List<MediaBrowserCompat.MediaItem>> {

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ f f672f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ String f673g;

        /* renamed from: h  reason: collision with root package name */
        final /* synthetic */ Bundle f674h;

        /* renamed from: i  reason: collision with root package name */
        final /* synthetic */ Bundle f675i;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(Object obj, f fVar, String str, Bundle bundle, Bundle bundle2) {
            super(obj);
            this.f672f = fVar;
            this.f673g = str;
            this.f674h = bundle;
            this.f675i = bundle2;
        }

        /* access modifiers changed from: package-private */
        public void a(List<MediaBrowserCompat.MediaItem> list) {
            if (MediaBrowserServiceCompat.this.f668f.get(this.f672f.b.asBinder()) == this.f672f) {
                if ((a() & 1) != 0) {
                    list = MediaBrowserServiceCompat.this.a(list, this.f674h);
                }
                try {
                    this.f672f.b.a(this.f673g, list, this.f674h, this.f675i);
                } catch (RemoteException unused) {
                    Log.w("MBServiceCompat", "Calling onLoadChildren() failed for id=" + this.f673g + " package=" + this.f672f.a);
                }
            } else if (MediaBrowserServiceCompat.f667j) {
                Log.d("MBServiceCompat", "Not sending onLoadChildren result for connection that has been disconnected. pkg=" + this.f672f.a + " id=" + this.f673g);
            }
        }
    }

    class b extends m<MediaBrowserCompat.MediaItem> {

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ ResultReceiver f677f;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(MediaBrowserServiceCompat mediaBrowserServiceCompat, Object obj, ResultReceiver resultReceiver) {
            super(obj);
            this.f677f = resultReceiver;
        }

        /* access modifiers changed from: package-private */
        public void a(MediaBrowserCompat.MediaItem mediaItem) {
            if ((a() & 2) != 0) {
                this.f677f.send(-1, (Bundle) null);
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable("media_item", mediaItem);
            this.f677f.send(0, bundle);
        }
    }

    class c extends m<List<MediaBrowserCompat.MediaItem>> {

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ ResultReceiver f678f;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(MediaBrowserServiceCompat mediaBrowserServiceCompat, Object obj, ResultReceiver resultReceiver) {
            super(obj);
            this.f678f = resultReceiver;
        }

        /* access modifiers changed from: package-private */
        public void a(List<MediaBrowserCompat.MediaItem> list) {
            if ((a() & 4) != 0 || list == null) {
                this.f678f.send(-1, (Bundle) null);
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArray("search_results", (Parcelable[]) list.toArray(new MediaBrowserCompat.MediaItem[0]));
            this.f678f.send(0, bundle);
        }
    }

    class d extends m<Bundle> {

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ ResultReceiver f679f;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(MediaBrowserServiceCompat mediaBrowserServiceCompat, Object obj, ResultReceiver resultReceiver) {
            super(obj);
            this.f679f = resultReceiver;
        }

        /* access modifiers changed from: package-private */
        /* renamed from: c */
        public void a(Bundle bundle) {
            this.f679f.send(0, bundle);
        }

        /* access modifiers changed from: package-private */
        public void a(Bundle bundle) {
            this.f679f.send(-1, bundle);
        }
    }

    public static final class e {
        public Bundle a() {
            throw null;
        }

        public String b() {
            throw null;
        }
    }

    private class f implements IBinder.DeathRecipient {
        public final String a;
        public final o b;
        public final HashMap<String, List<androidx.core.g.d<IBinder, Bundle>>> c = new HashMap<>();
        public e d;

        class a implements Runnable {
            a() {
            }

            public void run() {
                f fVar = f.this;
                MediaBrowserServiceCompat.this.f668f.remove(fVar.b.asBinder());
            }
        }

        f(String str, int i2, int i3, Bundle bundle, o oVar) {
            this.a = str;
            new h(str, i2, i3);
            this.b = oVar;
        }

        public void binderDied() {
            MediaBrowserServiceCompat.this.f670h.post(new a());
        }
    }

    interface g {
        IBinder a(Intent intent);

        void onCreate();
    }

    class h implements g, e.d {
        final List<Bundle> a = new ArrayList();
        Object b;
        Messenger c;

        class a extends m<List<MediaBrowserCompat.MediaItem>> {

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ e.c f680f;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(h hVar, Object obj, e.c cVar) {
                super(obj);
                this.f680f = cVar;
            }

            /* access modifiers changed from: package-private */
            public void a(List<MediaBrowserCompat.MediaItem> list) {
                ArrayList arrayList;
                if (list != null) {
                    arrayList = new ArrayList();
                    for (MediaBrowserCompat.MediaItem writeToParcel : list) {
                        Parcel obtain = Parcel.obtain();
                        writeToParcel.writeToParcel(obtain, 0);
                        arrayList.add(obtain);
                    }
                } else {
                    arrayList = null;
                }
                this.f680f.a(arrayList);
            }
        }

        h() {
        }

        public IBinder a(Intent intent) {
            return e.a(this.b, intent);
        }

        public void onCreate() {
            Object a2 = e.a((Context) MediaBrowserServiceCompat.this, (e.d) this);
            this.b = a2;
            e.a(a2);
        }

        public e.a a(String str, int i2, Bundle bundle) {
            Bundle bundle2;
            IBinder iBinder;
            if (bundle == null || bundle.getInt("extra_client_version", 0) == 0) {
                bundle2 = null;
            } else {
                bundle.remove("extra_client_version");
                this.c = new Messenger(MediaBrowserServiceCompat.this.f670h);
                bundle2 = new Bundle();
                bundle2.putInt("extra_service_version", 2);
                androidx.core.app.e.a(bundle2, "extra_messenger", this.c.getBinder());
                MediaSessionCompat.Token token = MediaBrowserServiceCompat.this.f671i;
                if (token != null) {
                    IMediaSession extraBinder = token.getExtraBinder();
                    if (extraBinder == null) {
                        iBinder = null;
                    } else {
                        iBinder = extraBinder.asBinder();
                    }
                    androidx.core.app.e.a(bundle2, "extra_session_binder", iBinder);
                } else {
                    this.a.add(bundle2);
                }
            }
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat.f669g = new f(str, -1, i2, bundle, (o) null);
            e a2 = MediaBrowserServiceCompat.this.a(str, i2, bundle);
            MediaBrowserServiceCompat.this.f669g = null;
            if (a2 == null) {
                return null;
            }
            if (bundle2 == null) {
                a2.a();
                throw null;
            }
            a2.a();
            throw null;
        }

        public void a(String str, e.c<List<Parcel>> cVar) {
            MediaBrowserServiceCompat.this.a(str, (m<List<MediaBrowserCompat.MediaItem>>) new a(this, str, cVar));
        }
    }

    class i extends h implements f.b {

        class a extends m<MediaBrowserCompat.MediaItem> {

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ e.c f681f;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(i iVar, Object obj, e.c cVar) {
                super(obj);
                this.f681f = cVar;
            }

            /* access modifiers changed from: package-private */
            public void a(MediaBrowserCompat.MediaItem mediaItem) {
                if (mediaItem == null) {
                    this.f681f.a(null);
                    return;
                }
                Parcel obtain = Parcel.obtain();
                mediaItem.writeToParcel(obtain, 0);
                this.f681f.a(obtain);
            }
        }

        i() {
            super();
        }

        public void b(String str, e.c<Parcel> cVar) {
            MediaBrowserServiceCompat.this.b(str, new a(this, str, cVar));
        }

        public void onCreate() {
            Object a2 = f.a(MediaBrowserServiceCompat.this, this);
            this.b = a2;
            e.a(a2);
        }
    }

    class j extends i implements g.c {

        class a extends m<List<MediaBrowserCompat.MediaItem>> {

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ g.b f683f;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(j jVar, Object obj, g.b bVar) {
                super(obj);
                this.f683f = bVar;
            }

            /* access modifiers changed from: package-private */
            public void a(List<MediaBrowserCompat.MediaItem> list) {
                ArrayList arrayList;
                if (list != null) {
                    arrayList = new ArrayList();
                    for (MediaBrowserCompat.MediaItem writeToParcel : list) {
                        Parcel obtain = Parcel.obtain();
                        writeToParcel.writeToParcel(obtain, 0);
                        arrayList.add(obtain);
                    }
                } else {
                    arrayList = null;
                }
                this.f683f.a(arrayList, a());
            }
        }

        j() {
            super();
        }

        public void a(String str, g.b bVar, Bundle bundle) {
            MediaBrowserServiceCompat.this.a(str, (m<List<MediaBrowserCompat.MediaItem>>) new a(this, str, bVar), bundle);
        }

        public void onCreate() {
            Object a2 = g.a(MediaBrowserServiceCompat.this, this);
            this.b = a2;
            e.a(a2);
        }
    }

    class k extends j {
        k(MediaBrowserServiceCompat mediaBrowserServiceCompat) {
            super();
        }
    }

    class l implements g {
        private Messenger a;

        l() {
        }

        public IBinder a(Intent intent) {
            if ("android.media.browse.MediaBrowserService".equals(intent.getAction())) {
                return this.a.getBinder();
            }
            return null;
        }

        public void onCreate() {
            this.a = new Messenger(MediaBrowserServiceCompat.this.f670h);
        }
    }

    public static class m<T> {
        private final Object a;
        private boolean b;
        private boolean c;
        private boolean d;
        private int e;

        m(Object obj) {
            this.a = obj;
        }

        /* access modifiers changed from: package-private */
        public void a(int i2) {
            this.e = i2;
        }

        /* access modifiers changed from: package-private */
        public void a(T t) {
            throw null;
        }

        public void b(T t) {
            if (this.c || this.d) {
                throw new IllegalStateException("sendResult() called when either sendResult() or sendError() had already been called for: " + this.a);
            }
            this.c = true;
            a(t);
        }

        /* access modifiers changed from: package-private */
        public int a() {
            return this.e;
        }

        /* access modifiers changed from: package-private */
        public void a(Bundle bundle) {
            throw new UnsupportedOperationException("It is not supported to send an error for " + this.a);
        }

        public void b(Bundle bundle) {
            if (this.c || this.d) {
                throw new IllegalStateException("sendError() called when either sendResult() or sendError() had already been called for: " + this.a);
            }
            this.d = true;
            a(bundle);
        }

        /* access modifiers changed from: package-private */
        public boolean b() {
            return this.b || this.c || this.d;
        }
    }

    private class n {

        class a implements Runnable {
            final /* synthetic */ o e;

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ String f684f;

            /* renamed from: g  reason: collision with root package name */
            final /* synthetic */ int f685g;

            /* renamed from: h  reason: collision with root package name */
            final /* synthetic */ int f686h;

            /* renamed from: i  reason: collision with root package name */
            final /* synthetic */ Bundle f687i;

            a(o oVar, String str, int i2, int i3, Bundle bundle) {
                this.e = oVar;
                this.f684f = str;
                this.f685g = i2;
                this.f686h = i3;
                this.f687i = bundle;
            }

            public void run() {
                IBinder asBinder = this.e.asBinder();
                MediaBrowserServiceCompat.this.f668f.remove(asBinder);
                f fVar = new f(this.f684f, this.f685g, this.f686h, this.f687i, this.e);
                MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
                mediaBrowserServiceCompat.f669g = fVar;
                e a = mediaBrowserServiceCompat.a(this.f684f, this.f686h, this.f687i);
                fVar.d = a;
                MediaBrowserServiceCompat mediaBrowserServiceCompat2 = MediaBrowserServiceCompat.this;
                mediaBrowserServiceCompat2.f669g = null;
                if (a == null) {
                    Log.i("MBServiceCompat", "No root for client " + this.f684f + " from service " + a.class.getName());
                    try {
                        this.e.a();
                    } catch (RemoteException unused) {
                        Log.w("MBServiceCompat", "Calling onConnectFailed() failed. Ignoring. pkg=" + this.f684f);
                    }
                } else {
                    try {
                        mediaBrowserServiceCompat2.f668f.put(asBinder, fVar);
                        asBinder.linkToDeath(fVar, 0);
                        if (MediaBrowserServiceCompat.this.f671i != null) {
                            fVar.d.b();
                            throw null;
                        }
                    } catch (RemoteException unused2) {
                        Log.w("MBServiceCompat", "Calling onConnect() failed. Dropping client. pkg=" + this.f684f);
                        MediaBrowserServiceCompat.this.f668f.remove(asBinder);
                    }
                }
            }
        }

        class b implements Runnable {
            final /* synthetic */ o e;

            b(o oVar) {
                this.e = oVar;
            }

            public void run() {
                f remove = MediaBrowserServiceCompat.this.f668f.remove(this.e.asBinder());
                if (remove != null) {
                    remove.b.asBinder().unlinkToDeath(remove, 0);
                }
            }
        }

        class c implements Runnable {
            final /* synthetic */ o e;

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ String f690f;

            /* renamed from: g  reason: collision with root package name */
            final /* synthetic */ IBinder f691g;

            /* renamed from: h  reason: collision with root package name */
            final /* synthetic */ Bundle f692h;

            c(o oVar, String str, IBinder iBinder, Bundle bundle) {
                this.e = oVar;
                this.f690f = str;
                this.f691g = iBinder;
                this.f692h = bundle;
            }

            public void run() {
                f fVar = MediaBrowserServiceCompat.this.f668f.get(this.e.asBinder());
                if (fVar == null) {
                    Log.w("MBServiceCompat", "addSubscription for callback that isn't registered id=" + this.f690f);
                    return;
                }
                MediaBrowserServiceCompat.this.a(this.f690f, fVar, this.f691g, this.f692h);
            }
        }

        class d implements Runnable {
            final /* synthetic */ o e;

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ String f694f;

            /* renamed from: g  reason: collision with root package name */
            final /* synthetic */ IBinder f695g;

            d(o oVar, String str, IBinder iBinder) {
                this.e = oVar;
                this.f694f = str;
                this.f695g = iBinder;
            }

            public void run() {
                f fVar = MediaBrowserServiceCompat.this.f668f.get(this.e.asBinder());
                if (fVar == null) {
                    Log.w("MBServiceCompat", "removeSubscription for callback that isn't registered id=" + this.f694f);
                } else if (!MediaBrowserServiceCompat.this.a(this.f694f, fVar, this.f695g)) {
                    Log.w("MBServiceCompat", "removeSubscription called for " + this.f694f + " which is not subscribed");
                }
            }
        }

        class e implements Runnable {
            final /* synthetic */ o e;

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ String f697f;

            /* renamed from: g  reason: collision with root package name */
            final /* synthetic */ ResultReceiver f698g;

            e(o oVar, String str, ResultReceiver resultReceiver) {
                this.e = oVar;
                this.f697f = str;
                this.f698g = resultReceiver;
            }

            public void run() {
                f fVar = MediaBrowserServiceCompat.this.f668f.get(this.e.asBinder());
                if (fVar == null) {
                    Log.w("MBServiceCompat", "getMediaItem for callback that isn't registered id=" + this.f697f);
                    return;
                }
                MediaBrowserServiceCompat.this.a(this.f697f, fVar, this.f698g);
            }
        }

        class f implements Runnable {
            final /* synthetic */ o e;

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ String f700f;

            /* renamed from: g  reason: collision with root package name */
            final /* synthetic */ int f701g;

            /* renamed from: h  reason: collision with root package name */
            final /* synthetic */ int f702h;

            /* renamed from: i  reason: collision with root package name */
            final /* synthetic */ Bundle f703i;

            f(o oVar, String str, int i2, int i3, Bundle bundle) {
                this.e = oVar;
                this.f700f = str;
                this.f701g = i2;
                this.f702h = i3;
                this.f703i = bundle;
            }

            public void run() {
                IBinder asBinder = this.e.asBinder();
                MediaBrowserServiceCompat.this.f668f.remove(asBinder);
                f fVar = new f(this.f700f, this.f701g, this.f702h, this.f703i, this.e);
                MediaBrowserServiceCompat.this.f668f.put(asBinder, fVar);
                try {
                    asBinder.linkToDeath(fVar, 0);
                } catch (RemoteException unused) {
                    Log.w("MBServiceCompat", "IBinder is already dead.");
                }
            }
        }

        class g implements Runnable {
            final /* synthetic */ o e;

            g(o oVar) {
                this.e = oVar;
            }

            public void run() {
                IBinder asBinder = this.e.asBinder();
                f remove = MediaBrowserServiceCompat.this.f668f.remove(asBinder);
                if (remove != null) {
                    asBinder.unlinkToDeath(remove, 0);
                }
            }
        }

        class h implements Runnable {
            final /* synthetic */ o e;

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ String f706f;

            /* renamed from: g  reason: collision with root package name */
            final /* synthetic */ Bundle f707g;

            /* renamed from: h  reason: collision with root package name */
            final /* synthetic */ ResultReceiver f708h;

            h(o oVar, String str, Bundle bundle, ResultReceiver resultReceiver) {
                this.e = oVar;
                this.f706f = str;
                this.f707g = bundle;
                this.f708h = resultReceiver;
            }

            public void run() {
                f fVar = MediaBrowserServiceCompat.this.f668f.get(this.e.asBinder());
                if (fVar == null) {
                    Log.w("MBServiceCompat", "search for callback that isn't registered query=" + this.f706f);
                    return;
                }
                MediaBrowserServiceCompat.this.b(this.f706f, this.f707g, fVar, this.f708h);
            }
        }

        class i implements Runnable {
            final /* synthetic */ o e;

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ String f710f;

            /* renamed from: g  reason: collision with root package name */
            final /* synthetic */ Bundle f711g;

            /* renamed from: h  reason: collision with root package name */
            final /* synthetic */ ResultReceiver f712h;

            i(o oVar, String str, Bundle bundle, ResultReceiver resultReceiver) {
                this.e = oVar;
                this.f710f = str;
                this.f711g = bundle;
                this.f712h = resultReceiver;
            }

            public void run() {
                f fVar = MediaBrowserServiceCompat.this.f668f.get(this.e.asBinder());
                if (fVar == null) {
                    Log.w("MBServiceCompat", "sendCustomAction for callback that isn't registered action=" + this.f710f + ", extras=" + this.f711g);
                    return;
                }
                MediaBrowserServiceCompat.this.a(this.f710f, this.f711g, fVar, this.f712h);
            }
        }

        n() {
        }

        public void a(String str, int i2, int i3, Bundle bundle, o oVar) {
            if (MediaBrowserServiceCompat.this.a(str, i3)) {
                MediaBrowserServiceCompat.this.f670h.a(new a(oVar, str, i2, i3, bundle));
                return;
            }
            throw new IllegalArgumentException("Package/uid mismatch: uid=" + i3 + " package=" + str);
        }

        public void b(o oVar) {
            MediaBrowserServiceCompat.this.f670h.a(new g(oVar));
        }

        public void b(String str, Bundle bundle, ResultReceiver resultReceiver, o oVar) {
            if (!TextUtils.isEmpty(str) && resultReceiver != null) {
                MediaBrowserServiceCompat.this.f670h.a(new i(oVar, str, bundle, resultReceiver));
            }
        }

        public void a(o oVar) {
            MediaBrowserServiceCompat.this.f670h.a(new b(oVar));
        }

        public void a(String str, IBinder iBinder, Bundle bundle, o oVar) {
            MediaBrowserServiceCompat.this.f670h.a(new c(oVar, str, iBinder, bundle));
        }

        public void a(String str, IBinder iBinder, o oVar) {
            MediaBrowserServiceCompat.this.f670h.a(new d(oVar, str, iBinder));
        }

        public void a(String str, ResultReceiver resultReceiver, o oVar) {
            if (!TextUtils.isEmpty(str) && resultReceiver != null) {
                MediaBrowserServiceCompat.this.f670h.a(new e(oVar, str, resultReceiver));
            }
        }

        public void a(o oVar, String str, int i2, int i3, Bundle bundle) {
            MediaBrowserServiceCompat.this.f670h.a(new f(oVar, str, i2, i3, bundle));
        }

        public void a(String str, Bundle bundle, ResultReceiver resultReceiver, o oVar) {
            if (!TextUtils.isEmpty(str) && resultReceiver != null) {
                MediaBrowserServiceCompat.this.f670h.a(new h(oVar, str, bundle, resultReceiver));
            }
        }
    }

    private interface o {
        void a();

        void a(String str, List<MediaBrowserCompat.MediaItem> list, Bundle bundle, Bundle bundle2);

        IBinder asBinder();
    }

    private static class p implements o {
        final Messenger a;

        p(Messenger messenger) {
            this.a = messenger;
        }

        public void a() {
            a(2, (Bundle) null);
        }

        public IBinder asBinder() {
            return this.a.getBinder();
        }

        public void a(String str, List<MediaBrowserCompat.MediaItem> list, Bundle bundle, Bundle bundle2) {
            Bundle bundle3 = new Bundle();
            bundle3.putString("data_media_item_id", str);
            bundle3.putBundle("data_options", bundle);
            bundle3.putBundle("data_notify_children_changed_options", bundle2);
            if (list != null) {
                bundle3.putParcelableArrayList("data_media_item_list", list instanceof ArrayList ? (ArrayList) list : new ArrayList(list));
            }
            a(3, bundle3);
        }

        private void a(int i2, Bundle bundle) {
            Message obtain = Message.obtain();
            obtain.what = i2;
            obtain.arg1 = 2;
            obtain.setData(bundle);
            this.a.send(obtain);
        }
    }

    private final class q extends Handler {
        private final n a = new n();

        q() {
        }

        public void a(Runnable runnable) {
            if (Thread.currentThread() == getLooper().getThread()) {
                runnable.run();
            } else {
                post(runnable);
            }
        }

        public void handleMessage(Message message) {
            Bundle data = message.getData();
            switch (message.what) {
                case 1:
                    Bundle bundle = data.getBundle("data_root_hints");
                    MediaSessionCompat.ensureClassLoader(bundle);
                    this.a.a(data.getString("data_package_name"), data.getInt("data_calling_pid"), data.getInt("data_calling_uid"), bundle, (o) new p(message.replyTo));
                    return;
                case 2:
                    this.a.a(new p(message.replyTo));
                    return;
                case 3:
                    Bundle bundle2 = data.getBundle("data_options");
                    MediaSessionCompat.ensureClassLoader(bundle2);
                    this.a.a(data.getString("data_media_item_id"), androidx.core.app.e.a(data, "data_callback_token"), bundle2, (o) new p(message.replyTo));
                    return;
                case 4:
                    this.a.a(data.getString("data_media_item_id"), androidx.core.app.e.a(data, "data_callback_token"), (o) new p(message.replyTo));
                    return;
                case 5:
                    this.a.a(data.getString("data_media_item_id"), (ResultReceiver) data.getParcelable("data_result_receiver"), (o) new p(message.replyTo));
                    return;
                case 6:
                    Bundle bundle3 = data.getBundle("data_root_hints");
                    MediaSessionCompat.ensureClassLoader(bundle3);
                    n nVar = this.a;
                    p pVar = new p(message.replyTo);
                    nVar.a((o) pVar, data.getString("data_package_name"), data.getInt("data_calling_pid"), data.getInt("data_calling_uid"), bundle3);
                    return;
                case 7:
                    this.a.b(new p(message.replyTo));
                    return;
                case 8:
                    Bundle bundle4 = data.getBundle("data_search_extras");
                    MediaSessionCompat.ensureClassLoader(bundle4);
                    this.a.a(data.getString("data_search_query"), bundle4, (ResultReceiver) data.getParcelable("data_result_receiver"), (o) new p(message.replyTo));
                    return;
                case 9:
                    Bundle bundle5 = data.getBundle("data_custom_action_extras");
                    MediaSessionCompat.ensureClassLoader(bundle5);
                    this.a.b(data.getString("data_custom_action"), bundle5, (ResultReceiver) data.getParcelable("data_result_receiver"), new p(message.replyTo));
                    return;
                default:
                    Log.w("MBServiceCompat", "Unhandled message: " + message + "\n  Service version: " + 2 + "\n  Client version: " + message.arg1);
                    return;
            }
        }

        public boolean sendMessageAtTime(Message message, long j2) {
            Bundle data = message.getData();
            data.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            data.putInt("data_calling_uid", Binder.getCallingUid());
            data.putInt("data_calling_pid", Binder.getCallingPid());
            return super.sendMessageAtTime(message, j2);
        }
    }

    public abstract e a(String str, int i2, Bundle bundle);

    public void a(String str) {
    }

    public void a(String str, Bundle bundle) {
    }

    public abstract void a(String str, m<List<MediaBrowserCompat.MediaItem>> mVar);

    public void a(String str, m<List<MediaBrowserCompat.MediaItem>> mVar, Bundle bundle) {
        mVar.a(1);
        a(str, mVar);
    }

    public void b(String str, m<MediaBrowserCompat.MediaItem> mVar) {
        mVar.a(2);
        mVar.b(null);
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    public IBinder onBind(Intent intent) {
        return this.e.a(intent);
    }

    public void onCreate() {
        super.onCreate();
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 28) {
            this.e = new k(this);
        } else if (i2 >= 26) {
            this.e = new j();
        } else if (i2 >= 23) {
            this.e = new i();
        } else if (i2 >= 21) {
            this.e = new h();
        } else {
            this.e = new l();
        }
        this.e.onCreate();
    }

    public void a(String str, Bundle bundle, m<Bundle> mVar) {
        mVar.b((Bundle) null);
    }

    public void b(String str, Bundle bundle, m<List<MediaBrowserCompat.MediaItem>> mVar) {
        mVar.a(4);
        mVar.b(null);
    }

    /* access modifiers changed from: package-private */
    public boolean a(String str, int i2) {
        if (str == null) {
            return false;
        }
        for (String equals : getPackageManager().getPackagesForUid(i2)) {
            if (equals.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void b(String str, Bundle bundle, f fVar, ResultReceiver resultReceiver) {
        c cVar = new c(this, str, resultReceiver);
        b(str, bundle, cVar);
        if (!cVar.b()) {
            throw new IllegalStateException("onSearch must call detach() or sendResult() before returning for query=" + str);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(String str, f fVar, IBinder iBinder, Bundle bundle) {
        List<androidx.core.g.d> list = fVar.c.get(str);
        if (list == null) {
            list = new ArrayList<>();
        }
        for (androidx.core.g.d dVar : list) {
            if (iBinder == dVar.a && d.a(bundle, (Bundle) dVar.b)) {
                return;
            }
        }
        list.add(new androidx.core.g.d(iBinder, bundle));
        fVar.c.put(str, list);
        a(str, fVar, bundle, (Bundle) null);
        a(str, bundle);
    }

    /* access modifiers changed from: package-private */
    public boolean a(String str, f fVar, IBinder iBinder) {
        boolean z = true;
        boolean z2 = false;
        if (iBinder == null) {
            try {
                if (fVar.c.remove(str) == null) {
                    z = false;
                }
                return z;
            } finally {
                a(str);
            }
        } else {
            List list = fVar.c.get(str);
            if (list != null) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (iBinder == ((androidx.core.g.d) it.next()).a) {
                        it.remove();
                        z2 = true;
                    }
                }
                if (list.size() == 0) {
                    fVar.c.remove(str);
                }
            }
            a(str);
            return z2;
        }
    }

    /* access modifiers changed from: package-private */
    public void a(String str, f fVar, Bundle bundle, Bundle bundle2) {
        a aVar = new a(str, fVar, str, bundle, bundle2);
        if (bundle == null) {
            a(str, (m<List<MediaBrowserCompat.MediaItem>>) aVar);
        } else {
            a(str, (m<List<MediaBrowserCompat.MediaItem>>) aVar, bundle);
        }
        if (!aVar.b()) {
            throw new IllegalStateException("onLoadChildren must call detach() or sendResult() before returning for package=" + fVar.a + " id=" + str);
        }
    }

    /* access modifiers changed from: package-private */
    public List<MediaBrowserCompat.MediaItem> a(List<MediaBrowserCompat.MediaItem> list, Bundle bundle) {
        if (list == null) {
            return null;
        }
        int i2 = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
        int i3 = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        if (i2 == -1 && i3 == -1) {
            return list;
        }
        int i4 = i3 * i2;
        int i5 = i4 + i3;
        if (i2 < 0 || i3 < 1 || i4 >= list.size()) {
            return Collections.emptyList();
        }
        if (i5 > list.size()) {
            i5 = list.size();
        }
        return list.subList(i4, i5);
    }

    /* access modifiers changed from: package-private */
    public void a(String str, f fVar, ResultReceiver resultReceiver) {
        b bVar = new b(this, str, resultReceiver);
        b(str, bVar);
        if (!bVar.b()) {
            throw new IllegalStateException("onLoadItem must call detach() or sendResult() before returning for id=" + str);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(String str, Bundle bundle, f fVar, ResultReceiver resultReceiver) {
        d dVar = new d(this, str, resultReceiver);
        a(str, bundle, (m<Bundle>) dVar);
        if (!dVar.b()) {
            throw new IllegalStateException("onCustomAction must call detach() or sendResult() or sendError() before returning for action=" + str + " extras=" + bundle);
        }
    }
}
