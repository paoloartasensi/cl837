package androidx.appcompat.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: ActivityChooserModel */
class c extends DataSetObservable {
    static final String n = c.class.getSimpleName();
    private final Object a;
    private final List<a> b;
    private final List<C0007c> c;
    final Context d;
    final String e;

    /* renamed from: f  reason: collision with root package name */
    private Intent f279f;

    /* renamed from: g  reason: collision with root package name */
    private b f280g;

    /* renamed from: h  reason: collision with root package name */
    private int f281h;

    /* renamed from: i  reason: collision with root package name */
    boolean f282i;

    /* renamed from: j  reason: collision with root package name */
    private boolean f283j;
    private boolean k;
    private boolean l;
    private d m;

    /* compiled from: ActivityChooserModel */
    public static final class a implements Comparable<a> {
        public final ResolveInfo e;

        /* renamed from: f  reason: collision with root package name */
        public float f284f;

        public a(ResolveInfo resolveInfo) {
            this.e = resolveInfo;
        }

        /* renamed from: a */
        public int compareTo(a aVar) {
            return Float.floatToIntBits(aVar.f284f) - Float.floatToIntBits(this.f284f);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && a.class == obj.getClass() && Float.floatToIntBits(this.f284f) == Float.floatToIntBits(((a) obj).f284f);
        }

        public int hashCode() {
            return Float.floatToIntBits(this.f284f) + 31;
        }

        public String toString() {
            return "[" + "resolveInfo:" + this.e.toString() + "; weight:" + new BigDecimal((double) this.f284f) + "]";
        }
    }

    /* compiled from: ActivityChooserModel */
    public interface b {
        void a(Intent intent, List<a> list, List<C0007c> list2);
    }

    /* renamed from: androidx.appcompat.widget.c$c  reason: collision with other inner class name */
    /* compiled from: ActivityChooserModel */
    public static final class C0007c {
        public final ComponentName a;
        public final long b;
        public final float c;

        public C0007c(String str, long j2, float f2) {
            this(ComponentName.unflattenFromString(str), j2, f2);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || C0007c.class != obj.getClass()) {
                return false;
            }
            C0007c cVar = (C0007c) obj;
            ComponentName componentName = this.a;
            if (componentName == null) {
                if (cVar.a != null) {
                    return false;
                }
            } else if (!componentName.equals(cVar.a)) {
                return false;
            }
            return this.b == cVar.b && Float.floatToIntBits(this.c) == Float.floatToIntBits(cVar.c);
        }

        public int hashCode() {
            ComponentName componentName = this.a;
            int hashCode = componentName == null ? 0 : componentName.hashCode();
            long j2 = this.b;
            return ((((hashCode + 31) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31) + Float.floatToIntBits(this.c);
        }

        public String toString() {
            return "[" + "; activity:" + this.a + "; time:" + this.b + "; weight:" + new BigDecimal((double) this.c) + "]";
        }

        public C0007c(ComponentName componentName, long j2, float f2) {
            this.a = componentName;
            this.b = j2;
            this.c = f2;
        }
    }

    /* compiled from: ActivityChooserModel */
    public interface d {
        boolean a(c cVar, Intent intent);
    }

    /* compiled from: ActivityChooserModel */
    private final class e extends AsyncTask<Object, Void, Void> {
        e() {
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x006d, code lost:
            if (r15 != null) goto L_0x006f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
            r15.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0092, code lost:
            if (r15 == null) goto L_0x00d5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x00b2, code lost:
            if (r15 == null) goto L_0x00d5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x00d2, code lost:
            if (r15 == null) goto L_0x00d5;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Void doInBackground(java.lang.Object... r15) {
            /*
                r14 = this;
                java.lang.String r0 = "historical-record"
                java.lang.String r1 = "historical-records"
                java.lang.String r2 = "Error writing historical record file: "
                r3 = 0
                r4 = r15[r3]
                java.util.List r4 = (java.util.List) r4
                r5 = 1
                r15 = r15[r5]
                java.lang.String r15 = (java.lang.String) r15
                r6 = 0
                androidx.appcompat.widget.c r7 = androidx.appcompat.widget.c.this     // Catch:{ FileNotFoundException -> 0x00e0 }
                android.content.Context r7 = r7.d     // Catch:{ FileNotFoundException -> 0x00e0 }
                java.io.FileOutputStream r15 = r7.openFileOutput(r15, r3)     // Catch:{ FileNotFoundException -> 0x00e0 }
                org.xmlpull.v1.XmlSerializer r7 = android.util.Xml.newSerializer()
                r7.setOutput(r15, r6)     // Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
                java.lang.String r8 = "UTF-8"
                java.lang.Boolean r9 = java.lang.Boolean.valueOf(r5)     // Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
                r7.startDocument(r8, r9)     // Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
                r7.startTag(r6, r1)     // Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
                int r8 = r4.size()     // Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
                r9 = 0
            L_0x0031:
                if (r9 >= r8) goto L_0x0063
                java.lang.Object r10 = r4.remove(r3)     // Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
                androidx.appcompat.widget.c$c r10 = (androidx.appcompat.widget.c.C0007c) r10     // Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
                r7.startTag(r6, r0)     // Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
                java.lang.String r11 = "activity"
                android.content.ComponentName r12 = r10.a     // Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
                java.lang.String r12 = r12.flattenToString()     // Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
                r7.attribute(r6, r11, r12)     // Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
                java.lang.String r11 = "time"
                long r12 = r10.b     // Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
                java.lang.String r12 = java.lang.String.valueOf(r12)     // Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
                r7.attribute(r6, r11, r12)     // Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
                java.lang.String r11 = "weight"
                float r10 = r10.c     // Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
                java.lang.String r10 = java.lang.String.valueOf(r10)     // Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
                r7.attribute(r6, r11, r10)     // Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
                r7.endTag(r6, r0)     // Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
                int r9 = r9 + 1
                goto L_0x0031
            L_0x0063:
                r7.endTag(r6, r1)     // Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
                r7.endDocument()     // Catch:{ IllegalArgumentException -> 0x00b5, IllegalStateException -> 0x0095, IOException -> 0x0075 }
                androidx.appcompat.widget.c r0 = androidx.appcompat.widget.c.this
                r0.f282i = r5
                if (r15 == 0) goto L_0x00d5
            L_0x006f:
                r15.close()     // Catch:{ IOException -> 0x00d5 }
                goto L_0x00d5
            L_0x0073:
                r0 = move-exception
                goto L_0x00d6
            L_0x0075:
                r0 = move-exception
                java.lang.String r1 = androidx.appcompat.widget.c.n     // Catch:{ all -> 0x0073 }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0073 }
                r3.<init>()     // Catch:{ all -> 0x0073 }
                r3.append(r2)     // Catch:{ all -> 0x0073 }
                androidx.appcompat.widget.c r2 = androidx.appcompat.widget.c.this     // Catch:{ all -> 0x0073 }
                java.lang.String r2 = r2.e     // Catch:{ all -> 0x0073 }
                r3.append(r2)     // Catch:{ all -> 0x0073 }
                java.lang.String r2 = r3.toString()     // Catch:{ all -> 0x0073 }
                android.util.Log.e(r1, r2, r0)     // Catch:{ all -> 0x0073 }
                androidx.appcompat.widget.c r0 = androidx.appcompat.widget.c.this
                r0.f282i = r5
                if (r15 == 0) goto L_0x00d5
                goto L_0x006f
            L_0x0095:
                r0 = move-exception
                java.lang.String r1 = androidx.appcompat.widget.c.n     // Catch:{ all -> 0x0073 }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0073 }
                r3.<init>()     // Catch:{ all -> 0x0073 }
                r3.append(r2)     // Catch:{ all -> 0x0073 }
                androidx.appcompat.widget.c r2 = androidx.appcompat.widget.c.this     // Catch:{ all -> 0x0073 }
                java.lang.String r2 = r2.e     // Catch:{ all -> 0x0073 }
                r3.append(r2)     // Catch:{ all -> 0x0073 }
                java.lang.String r2 = r3.toString()     // Catch:{ all -> 0x0073 }
                android.util.Log.e(r1, r2, r0)     // Catch:{ all -> 0x0073 }
                androidx.appcompat.widget.c r0 = androidx.appcompat.widget.c.this
                r0.f282i = r5
                if (r15 == 0) goto L_0x00d5
                goto L_0x006f
            L_0x00b5:
                r0 = move-exception
                java.lang.String r1 = androidx.appcompat.widget.c.n     // Catch:{ all -> 0x0073 }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0073 }
                r3.<init>()     // Catch:{ all -> 0x0073 }
                r3.append(r2)     // Catch:{ all -> 0x0073 }
                androidx.appcompat.widget.c r2 = androidx.appcompat.widget.c.this     // Catch:{ all -> 0x0073 }
                java.lang.String r2 = r2.e     // Catch:{ all -> 0x0073 }
                r3.append(r2)     // Catch:{ all -> 0x0073 }
                java.lang.String r2 = r3.toString()     // Catch:{ all -> 0x0073 }
                android.util.Log.e(r1, r2, r0)     // Catch:{ all -> 0x0073 }
                androidx.appcompat.widget.c r0 = androidx.appcompat.widget.c.this
                r0.f282i = r5
                if (r15 == 0) goto L_0x00d5
                goto L_0x006f
            L_0x00d5:
                return r6
            L_0x00d6:
                androidx.appcompat.widget.c r1 = androidx.appcompat.widget.c.this
                r1.f282i = r5
                if (r15 == 0) goto L_0x00df
                r15.close()     // Catch:{ IOException -> 0x00df }
            L_0x00df:
                throw r0
            L_0x00e0:
                r0 = move-exception
                java.lang.String r1 = androidx.appcompat.widget.c.n
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                r3.append(r2)
                r3.append(r15)
                java.lang.String r15 = r3.toString()
                android.util.Log.e(r1, r15, r0)
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.c.e.doInBackground(java.lang.Object[]):java.lang.Void");
        }
    }

    static {
        new HashMap();
    }

    private void d() {
        boolean e2 = e() | h();
        g();
        if (e2) {
            j();
            notifyChanged();
        }
    }

    private boolean e() {
        if (!this.l || this.f279f == null) {
            return false;
        }
        this.l = false;
        this.b.clear();
        List<ResolveInfo> queryIntentActivities = this.d.getPackageManager().queryIntentActivities(this.f279f, 0);
        int size = queryIntentActivities.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.b.add(new a(queryIntentActivities.get(i2)));
        }
        return true;
    }

    private void f() {
        if (!this.f283j) {
            throw new IllegalStateException("No preceding call to #readHistoricalData");
        } else if (this.k) {
            this.k = false;
            if (!TextUtils.isEmpty(this.e)) {
                new e().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[]{new ArrayList(this.c), this.e});
            }
        }
    }

    private void g() {
        int size = this.c.size() - this.f281h;
        if (size > 0) {
            this.k = true;
            for (int i2 = 0; i2 < size; i2++) {
                C0007c remove = this.c.remove(0);
            }
        }
    }

    private boolean h() {
        if (!this.f282i || !this.k || TextUtils.isEmpty(this.e)) {
            return false;
        }
        this.f282i = false;
        this.f283j = true;
        i();
        return true;
    }

    private void i() {
        try {
            FileInputStream openFileInput = this.d.openFileInput(this.e);
            try {
                XmlPullParser newPullParser = Xml.newPullParser();
                newPullParser.setInput(openFileInput, "UTF-8");
                int i2 = 0;
                while (i2 != 1 && i2 != 2) {
                    i2 = newPullParser.next();
                }
                if ("historical-records".equals(newPullParser.getName())) {
                    List<C0007c> list = this.c;
                    list.clear();
                    while (true) {
                        int next = newPullParser.next();
                        if (next == 1) {
                            if (openFileInput == null) {
                                return;
                            }
                        } else if (!(next == 3 || next == 4)) {
                            if ("historical-record".equals(newPullParser.getName())) {
                                list.add(new C0007c(newPullParser.getAttributeValue((String) null, "activity"), Long.parseLong(newPullParser.getAttributeValue((String) null, "time")), Float.parseFloat(newPullParser.getAttributeValue((String) null, "weight"))));
                            } else {
                                throw new XmlPullParserException("Share records file not well-formed.");
                            }
                        }
                    }
                    try {
                        openFileInput.close();
                    } catch (IOException unused) {
                    }
                } else {
                    throw new XmlPullParserException("Share records file does not start with historical-records tag.");
                }
            } catch (XmlPullParserException e2) {
                String str = n;
                Log.e(str, "Error reading historical recrod file: " + this.e, e2);
                if (openFileInput == null) {
                }
            } catch (IOException e3) {
                String str2 = n;
                Log.e(str2, "Error reading historical recrod file: " + this.e, e3);
                if (openFileInput == null) {
                }
            } catch (Throwable th) {
                if (openFileInput != null) {
                    try {
                        openFileInput.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException unused3) {
        }
    }

    private boolean j() {
        if (this.f280g == null || this.f279f == null || this.b.isEmpty() || this.c.isEmpty()) {
            return false;
        }
        this.f280g.a(this.f279f, this.b, Collections.unmodifiableList(this.c));
        return true;
    }

    public int a() {
        int size;
        synchronized (this.a) {
            d();
            size = this.b.size();
        }
        return size;
    }

    public ResolveInfo b(int i2) {
        ResolveInfo resolveInfo;
        synchronized (this.a) {
            d();
            resolveInfo = this.b.get(i2).e;
        }
        return resolveInfo;
    }

    public void c(int i2) {
        synchronized (this.a) {
            d();
            a aVar = this.b.get(i2);
            a aVar2 = this.b.get(0);
            a(new C0007c(new ComponentName(aVar.e.activityInfo.packageName, aVar.e.activityInfo.name), System.currentTimeMillis(), aVar2 != null ? (aVar2.f284f - aVar.f284f) + 5.0f : 1.0f));
        }
    }

    public void setOnChooseActivityListener(d dVar) {
        synchronized (this.a) {
            this.m = dVar;
        }
    }

    public int a(ResolveInfo resolveInfo) {
        synchronized (this.a) {
            d();
            List<a> list = this.b;
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (list.get(i2).e == resolveInfo) {
                    return i2;
                }
            }
            return -1;
        }
    }

    public ResolveInfo b() {
        synchronized (this.a) {
            d();
            if (this.b.isEmpty()) {
                return null;
            }
            ResolveInfo resolveInfo = this.b.get(0).e;
            return resolveInfo;
        }
    }

    public int c() {
        int size;
        synchronized (this.a) {
            d();
            size = this.c.size();
        }
        return size;
    }

    public Intent a(int i2) {
        synchronized (this.a) {
            if (this.f279f == null) {
                return null;
            }
            d();
            a aVar = this.b.get(i2);
            ComponentName componentName = new ComponentName(aVar.e.activityInfo.packageName, aVar.e.activityInfo.name);
            Intent intent = new Intent(this.f279f);
            intent.setComponent(componentName);
            if (this.m != null) {
                if (this.m.a(this, new Intent(intent))) {
                    return null;
                }
            }
            a(new C0007c(componentName, System.currentTimeMillis(), 1.0f));
            return intent;
        }
    }

    private boolean a(C0007c cVar) {
        boolean add = this.c.add(cVar);
        if (add) {
            this.k = true;
            g();
            f();
            j();
            notifyChanged();
        }
        return add;
    }
}
