package androidx.core.app;

import android.app.Notification;
import android.app.RemoteInput;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.widget.RemoteViews;
import androidx.core.app.h;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: NotificationCompatBuilder */
class i implements g {
    private final Notification.Builder a;
    private final h.b b;
    private RemoteViews c;
    private RemoteViews d;
    private final List<Bundle> e = new ArrayList();

    /* renamed from: f  reason: collision with root package name */
    private final Bundle f467f = new Bundle();

    /* renamed from: g  reason: collision with root package name */
    private int f468g;

    /* renamed from: h  reason: collision with root package name */
    private RemoteViews f469h;

    i(h.b bVar) {
        ArrayList<String> arrayList;
        this.b = bVar;
        if (Build.VERSION.SDK_INT >= 26) {
            this.a = new Notification.Builder(bVar.a, bVar.I);
        } else {
            this.a = new Notification.Builder(bVar.a);
        }
        Notification notification = bVar.N;
        this.a.setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, bVar.f459h).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(bVar.d).setContentText(bVar.e).setContentInfo(bVar.f461j).setContentIntent(bVar.f457f).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(bVar.f458g, (notification.flags & 128) != 0).setLargeIcon(bVar.f460i).setNumber(bVar.k).setProgress(bVar.r, bVar.s, bVar.t);
        if (Build.VERSION.SDK_INT < 21) {
            this.a.setSound(notification.sound, notification.audioStreamType);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            this.a.setSubText(bVar.p).setUsesChronometer(bVar.n).setPriority(bVar.l);
            Iterator<h.a> it = bVar.b.iterator();
            while (it.hasNext()) {
                a(it.next());
            }
            Bundle bundle = bVar.B;
            if (bundle != null) {
                this.f467f.putAll(bundle);
            }
            if (Build.VERSION.SDK_INT < 20) {
                if (bVar.x) {
                    this.f467f.putBoolean("android.support.localOnly", true);
                }
                String str = bVar.u;
                if (str != null) {
                    this.f467f.putString("android.support.groupKey", str);
                    if (bVar.v) {
                        this.f467f.putBoolean("android.support.isGroupSummary", true);
                    } else {
                        this.f467f.putBoolean("android.support.useSideChannel", true);
                    }
                }
                String str2 = bVar.w;
                if (str2 != null) {
                    this.f467f.putString("android.support.sortKey", str2);
                }
            }
            this.c = bVar.F;
            this.d = bVar.G;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            this.a.setShowWhen(bVar.m);
            if (Build.VERSION.SDK_INT < 21 && (arrayList = bVar.O) != null && !arrayList.isEmpty()) {
                Bundle bundle2 = this.f467f;
                ArrayList<String> arrayList2 = bVar.O;
                bundle2.putStringArray("android.people", (String[]) arrayList2.toArray(new String[arrayList2.size()]));
            }
        }
        if (Build.VERSION.SDK_INT >= 20) {
            this.a.setLocalOnly(bVar.x).setGroup(bVar.u).setGroupSummary(bVar.v).setSortKey(bVar.w);
            this.f468g = bVar.M;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.a.setCategory(bVar.A).setColor(bVar.C).setVisibility(bVar.D).setPublicVersion(bVar.E).setSound(notification.sound, notification.audioAttributes);
            Iterator<String> it2 = bVar.O.iterator();
            while (it2.hasNext()) {
                this.a.addPerson(it2.next());
            }
            this.f469h = bVar.H;
            if (bVar.c.size() > 0) {
                Bundle bundle3 = bVar.b().getBundle("android.car.EXTENSIONS");
                bundle3 = bundle3 == null ? new Bundle() : bundle3;
                Bundle bundle4 = new Bundle();
                for (int i2 = 0; i2 < bVar.c.size(); i2++) {
                    bundle4.putBundle(Integer.toString(i2), j.a(bVar.c.get(i2)));
                }
                bundle3.putBundle("invisible_actions", bundle4);
                bVar.b().putBundle("android.car.EXTENSIONS", bundle3);
                this.f467f.putBundle("android.car.EXTENSIONS", bundle3);
            }
        }
        if (Build.VERSION.SDK_INT >= 24) {
            this.a.setExtras(bVar.B).setRemoteInputHistory(bVar.q);
            RemoteViews remoteViews = bVar.F;
            if (remoteViews != null) {
                this.a.setCustomContentView(remoteViews);
            }
            RemoteViews remoteViews2 = bVar.G;
            if (remoteViews2 != null) {
                this.a.setCustomBigContentView(remoteViews2);
            }
            RemoteViews remoteViews3 = bVar.H;
            if (remoteViews3 != null) {
                this.a.setCustomHeadsUpContentView(remoteViews3);
            }
        }
        if (Build.VERSION.SDK_INT >= 26) {
            this.a.setBadgeIconType(bVar.J).setShortcutId(bVar.K).setTimeoutAfter(bVar.L).setGroupAlertBehavior(bVar.M);
            if (bVar.z) {
                this.a.setColorized(bVar.y);
            }
            if (!TextUtils.isEmpty(bVar.I)) {
                this.a.setSound((Uri) null).setDefaults(0).setLights(0, 0, 0).setVibrate((long[]) null);
            }
        }
    }

    public Notification a() {
        Bundle a2;
        RemoteViews d2;
        RemoteViews b2;
        h.c cVar = this.b.o;
        if (cVar != null) {
            cVar.a((g) this);
        }
        RemoteViews c2 = cVar != null ? cVar.c(this) : null;
        Notification b3 = b();
        if (c2 != null) {
            b3.contentView = c2;
        } else {
            RemoteViews remoteViews = this.b.F;
            if (remoteViews != null) {
                b3.contentView = remoteViews;
            }
        }
        if (!(Build.VERSION.SDK_INT < 16 || cVar == null || (b2 = cVar.b(this)) == null)) {
            b3.bigContentView = b2;
        }
        if (!(Build.VERSION.SDK_INT < 21 || cVar == null || (d2 = this.b.o.d(this)) == null)) {
            b3.headsUpContentView = d2;
        }
        if (!(Build.VERSION.SDK_INT < 16 || cVar == null || (a2 = h.a(b3)) == null)) {
            cVar.a(a2);
        }
        return b3;
    }

    /* access modifiers changed from: protected */
    public Notification b() {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 26) {
            return this.a.build();
        }
        if (i2 >= 24) {
            Notification build = this.a.build();
            if (this.f468g != 0) {
                if (!(build.getGroup() == null || (build.flags & 512) == 0 || this.f468g != 2)) {
                    a(build);
                }
                if (build.getGroup() != null && (build.flags & 512) == 0 && this.f468g == 1) {
                    a(build);
                }
            }
            return build;
        } else if (i2 >= 21) {
            this.a.setExtras(this.f467f);
            Notification build2 = this.a.build();
            RemoteViews remoteViews = this.c;
            if (remoteViews != null) {
                build2.contentView = remoteViews;
            }
            RemoteViews remoteViews2 = this.d;
            if (remoteViews2 != null) {
                build2.bigContentView = remoteViews2;
            }
            RemoteViews remoteViews3 = this.f469h;
            if (remoteViews3 != null) {
                build2.headsUpContentView = remoteViews3;
            }
            if (this.f468g != 0) {
                if (!(build2.getGroup() == null || (build2.flags & 512) == 0 || this.f468g != 2)) {
                    a(build2);
                }
                if (build2.getGroup() != null && (build2.flags & 512) == 0 && this.f468g == 1) {
                    a(build2);
                }
            }
            return build2;
        } else if (i2 >= 20) {
            this.a.setExtras(this.f467f);
            Notification build3 = this.a.build();
            RemoteViews remoteViews4 = this.c;
            if (remoteViews4 != null) {
                build3.contentView = remoteViews4;
            }
            RemoteViews remoteViews5 = this.d;
            if (remoteViews5 != null) {
                build3.bigContentView = remoteViews5;
            }
            if (this.f468g != 0) {
                if (!(build3.getGroup() == null || (build3.flags & 512) == 0 || this.f468g != 2)) {
                    a(build3);
                }
                if (build3.getGroup() != null && (build3.flags & 512) == 0 && this.f468g == 1) {
                    a(build3);
                }
            }
            return build3;
        } else if (i2 >= 19) {
            SparseArray<Bundle> a2 = j.a(this.e);
            if (a2 != null) {
                this.f467f.putSparseParcelableArray("android.support.actionExtras", a2);
            }
            this.a.setExtras(this.f467f);
            Notification build4 = this.a.build();
            RemoteViews remoteViews6 = this.c;
            if (remoteViews6 != null) {
                build4.contentView = remoteViews6;
            }
            RemoteViews remoteViews7 = this.d;
            if (remoteViews7 != null) {
                build4.bigContentView = remoteViews7;
            }
            return build4;
        } else if (i2 < 16) {
            return this.a.getNotification();
        } else {
            Notification build5 = this.a.build();
            Bundle a3 = h.a(build5);
            Bundle bundle = new Bundle(this.f467f);
            for (String str : this.f467f.keySet()) {
                if (a3.containsKey(str)) {
                    bundle.remove(str);
                }
            }
            a3.putAll(bundle);
            SparseArray<Bundle> a4 = j.a(this.e);
            if (a4 != null) {
                h.a(build5).putSparseParcelableArray("android.support.actionExtras", a4);
            }
            RemoteViews remoteViews8 = this.c;
            if (remoteViews8 != null) {
                build5.contentView = remoteViews8;
            }
            RemoteViews remoteViews9 = this.d;
            if (remoteViews9 != null) {
                build5.bigContentView = remoteViews9;
            }
            return build5;
        }
    }

    private void a(h.a aVar) {
        Bundle bundle;
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 20) {
            Notification.Action.Builder builder = new Notification.Action.Builder(aVar.e(), aVar.i(), aVar.a());
            if (aVar.f() != null) {
                for (RemoteInput addRemoteInput : k.a(aVar.f())) {
                    builder.addRemoteInput(addRemoteInput);
                }
            }
            if (aVar.d() != null) {
                bundle = new Bundle(aVar.d());
            } else {
                bundle = new Bundle();
            }
            bundle.putBoolean("android.support.allowGeneratedReplies", aVar.b());
            if (Build.VERSION.SDK_INT >= 24) {
                builder.setAllowGeneratedReplies(aVar.b());
            }
            bundle.putInt("android.support.action.semanticAction", aVar.g());
            if (Build.VERSION.SDK_INT >= 28) {
                builder.setSemanticAction(aVar.g());
            }
            bundle.putBoolean("android.support.action.showsUserInterface", aVar.h());
            builder.addExtras(bundle);
            this.a.addAction(builder.build());
        } else if (i2 >= 16) {
            this.e.add(j.a(this.a, aVar));
        }
    }

    private void a(Notification notification) {
        notification.sound = null;
        notification.vibrate = null;
        int i2 = notification.defaults & -2;
        notification.defaults = i2;
        notification.defaults = i2 & -3;
    }
}
