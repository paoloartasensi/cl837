package androidx.core.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/* compiled from: NotificationCompat */
public class h {

    /* compiled from: NotificationCompat */
    public static class a {
        final Bundle a;
        private final k[] b;
        private final k[] c;
        private boolean d;
        boolean e = true;

        /* renamed from: f  reason: collision with root package name */
        private final int f453f;

        /* renamed from: g  reason: collision with root package name */
        public int f454g;

        /* renamed from: h  reason: collision with root package name */
        public CharSequence f455h;

        /* renamed from: i  reason: collision with root package name */
        public PendingIntent f456i;

        a(int i2, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, k[] kVarArr, k[] kVarArr2, boolean z, int i3, boolean z2) {
            this.f454g = i2;
            this.f455h = b.a(charSequence);
            this.f456i = pendingIntent;
            this.a = bundle == null ? new Bundle() : bundle;
            this.b = kVarArr;
            this.c = kVarArr2;
            this.d = z;
            this.f453f = i3;
            this.e = z2;
        }

        public PendingIntent a() {
            return this.f456i;
        }

        public boolean b() {
            return this.d;
        }

        public k[] c() {
            return this.c;
        }

        public Bundle d() {
            return this.a;
        }

        public int e() {
            return this.f454g;
        }

        public k[] f() {
            return this.b;
        }

        public int g() {
            return this.f453f;
        }

        public boolean h() {
            return this.e;
        }

        public CharSequence i() {
            return this.f455h;
        }
    }

    /* compiled from: NotificationCompat */
    public static class b {
        String A;
        Bundle B;
        int C = 0;
        int D = 0;
        Notification E;
        RemoteViews F;
        RemoteViews G;
        RemoteViews H;
        String I;
        int J = 0;
        String K;
        long L;
        int M = 0;
        Notification N;
        @Deprecated
        public ArrayList<String> O;
        public Context a;
        public ArrayList<a> b = new ArrayList<>();
        ArrayList<a> c = new ArrayList<>();
        CharSequence d;
        CharSequence e;

        /* renamed from: f  reason: collision with root package name */
        PendingIntent f457f;

        /* renamed from: g  reason: collision with root package name */
        PendingIntent f458g;

        /* renamed from: h  reason: collision with root package name */
        RemoteViews f459h;

        /* renamed from: i  reason: collision with root package name */
        Bitmap f460i;

        /* renamed from: j  reason: collision with root package name */
        CharSequence f461j;
        int k;
        int l;
        boolean m = true;
        boolean n;
        c o;
        CharSequence p;
        CharSequence[] q;
        int r;
        int s;
        boolean t;
        String u;
        boolean v;
        String w;
        boolean x = false;
        boolean y;
        boolean z;

        public b(Context context, String str) {
            Notification notification = new Notification();
            this.N = notification;
            this.a = context;
            this.I = str;
            notification.when = System.currentTimeMillis();
            this.N.audioStreamType = -1;
            this.l = 0;
            this.O = new ArrayList<>();
        }

        public Notification a() {
            return new i(this).a();
        }

        public Bundle b() {
            if (this.B == null) {
                this.B = new Bundle();
            }
            return this.B;
        }

        protected static CharSequence a(CharSequence charSequence) {
            return (charSequence != null && charSequence.length() > 5120) ? charSequence.subSequence(0, 5120) : charSequence;
        }
    }

    /* compiled from: NotificationCompat */
    public static abstract class c {
        public abstract void a(Bundle bundle);

        public abstract void a(g gVar);

        public abstract RemoteViews b(g gVar);

        public abstract RemoteViews c(g gVar);

        public abstract RemoteViews d(g gVar);
    }

    /* compiled from: NotificationCompat */
    public static final class d {
        private ArrayList<a> a = new ArrayList<>();
        private int b = 1;
        private PendingIntent c;
        private ArrayList<Notification> d = new ArrayList<>();
        private Bitmap e;

        /* renamed from: f  reason: collision with root package name */
        private int f462f;

        /* renamed from: g  reason: collision with root package name */
        private int f463g = 8388613;

        /* renamed from: h  reason: collision with root package name */
        private int f464h = -1;

        /* renamed from: i  reason: collision with root package name */
        private int f465i = 0;

        /* renamed from: j  reason: collision with root package name */
        private int f466j;
        private int k = 80;
        private int l;
        private String m;
        private String n;

        public d() {
        }

        public List<a> a() {
            return this.a;
        }

        public d clone() {
            d dVar = new d();
            dVar.a = new ArrayList<>(this.a);
            dVar.b = this.b;
            dVar.c = this.c;
            dVar.d = new ArrayList<>(this.d);
            dVar.e = this.e;
            dVar.f462f = this.f462f;
            dVar.f463g = this.f463g;
            dVar.f464h = this.f464h;
            dVar.f465i = this.f465i;
            dVar.f466j = this.f466j;
            dVar.k = this.k;
            dVar.l = this.l;
            dVar.m = this.m;
            dVar.n = this.n;
            return dVar;
        }

        public d(Notification notification) {
            Bundle a2 = h.a(notification);
            Bundle bundle = a2 != null ? a2.getBundle("android.wearable.EXTENSIONS") : null;
            if (bundle != null) {
                ArrayList parcelableArrayList = bundle.getParcelableArrayList("actions");
                if (Build.VERSION.SDK_INT >= 16 && parcelableArrayList != null) {
                    int size = parcelableArrayList.size();
                    a[] aVarArr = new a[size];
                    for (int i2 = 0; i2 < size; i2++) {
                        int i3 = Build.VERSION.SDK_INT;
                        if (i3 >= 20) {
                            aVarArr[i2] = h.a((Notification.Action) parcelableArrayList.get(i2));
                        } else if (i3 >= 16) {
                            aVarArr[i2] = j.b((Bundle) parcelableArrayList.get(i2));
                        }
                    }
                    Collections.addAll(this.a, aVarArr);
                }
                this.b = bundle.getInt("flags", 1);
                this.c = (PendingIntent) bundle.getParcelable("displayIntent");
                Notification[] a3 = h.a(bundle, "pages");
                if (a3 != null) {
                    Collections.addAll(this.d, a3);
                }
                this.e = (Bitmap) bundle.getParcelable("background");
                this.f462f = bundle.getInt("contentIcon");
                this.f463g = bundle.getInt("contentIconGravity", 8388613);
                this.f464h = bundle.getInt("contentActionIndex", -1);
                this.f465i = bundle.getInt("customSizePreset", 0);
                this.f466j = bundle.getInt("customContentHeight");
                this.k = bundle.getInt("gravity", 80);
                this.l = bundle.getInt("hintScreenTimeout");
                this.m = bundle.getString("dismissalId");
                this.n = bundle.getString("bridgeTag");
            }
        }
    }

    static Notification[] a(Bundle bundle, String str) {
        Parcelable[] parcelableArray = bundle.getParcelableArray(str);
        if ((parcelableArray instanceof Notification[]) || parcelableArray == null) {
            return (Notification[]) parcelableArray;
        }
        Notification[] notificationArr = new Notification[parcelableArray.length];
        for (int i2 = 0; i2 < parcelableArray.length; i2++) {
            notificationArr[i2] = (Notification) parcelableArray[i2];
        }
        bundle.putParcelableArray(str, notificationArr);
        return notificationArr;
    }

    public static boolean b(Notification notification) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 20) {
            if ((notification.flags & 512) != 0) {
                return true;
            }
            return false;
        } else if (i2 >= 19) {
            return notification.extras.getBoolean("android.support.isGroupSummary");
        } else {
            if (i2 >= 16) {
                return j.a(notification).getBoolean("android.support.isGroupSummary");
            }
            return false;
        }
    }

    public static Bundle a(Notification notification) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 19) {
            return notification.extras;
        }
        if (i2 >= 16) {
            return j.a(notification);
        }
        return null;
    }

    static a a(Notification.Action action) {
        k[] kVarArr;
        boolean z;
        int i2;
        RemoteInput[] remoteInputs = action.getRemoteInputs();
        if (remoteInputs == null) {
            kVarArr = null;
        } else {
            k[] kVarArr2 = new k[remoteInputs.length];
            for (int i3 = 0; i3 < remoteInputs.length; i3++) {
                RemoteInput remoteInput = remoteInputs[i3];
                kVarArr2[i3] = new k(remoteInput.getResultKey(), remoteInput.getLabel(), remoteInput.getChoices(), remoteInput.getAllowFreeFormInput(), remoteInput.getExtras(), (Set<String>) null);
            }
            kVarArr = kVarArr2;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            z = action.getExtras().getBoolean("android.support.allowGeneratedReplies") || action.getAllowGeneratedReplies();
        } else {
            z = action.getExtras().getBoolean("android.support.allowGeneratedReplies");
        }
        boolean z2 = z;
        boolean z3 = action.getExtras().getBoolean("android.support.action.showsUserInterface", true);
        if (Build.VERSION.SDK_INT >= 28) {
            i2 = action.getSemanticAction();
        } else {
            i2 = action.getExtras().getInt("android.support.action.semanticAction", 0);
        }
        return new a(action.icon, action.title, action.actionIntent, action.getExtras(), kVarArr, (k[]) null, z2, i2, z3);
    }
}
