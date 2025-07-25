package androidx.core.e;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Handler;
import androidx.core.a.i;
import androidx.core.content.c.f;
import androidx.core.e.c;
import androidx.core.g.h;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/* compiled from: FontsContractCompat */
public class b {
    static final g.a.e<String, Typeface> a = new g.a.e<>(16);
    private static final c b = new c("fonts", 10, 10000);
    static final Object c = new Object();
    static final g.a.g<String, ArrayList<c.d<g>>> d = new g.a.g<>();
    private static final Comparator<byte[]> e = new d();

    /* compiled from: FontsContractCompat */
    static class a implements Callable<g> {
        final /* synthetic */ Context a;
        final /* synthetic */ a b;
        final /* synthetic */ int c;
        final /* synthetic */ String d;

        a(Context context, a aVar, int i2, String str) {
            this.a = context;
            this.b = aVar;
            this.c = i2;
            this.d = str;
        }

        public g call() {
            g a2 = b.a(this.a, this.b, this.c);
            Typeface typeface = a2.a;
            if (typeface != null) {
                b.a.a(this.d, typeface);
            }
            return a2;
        }
    }

    /* renamed from: androidx.core.e.b$b  reason: collision with other inner class name */
    /* compiled from: FontsContractCompat */
    static class C0018b implements c.d<g> {
        final /* synthetic */ f.a a;
        final /* synthetic */ Handler b;

        C0018b(f.a aVar, Handler handler) {
            this.a = aVar;
            this.b = handler;
        }

        public void a(g gVar) {
            if (gVar == null) {
                this.a.a(1, this.b);
                return;
            }
            int i2 = gVar.b;
            if (i2 == 0) {
                this.a.a(gVar.a, this.b);
            } else {
                this.a.a(i2, this.b);
            }
        }
    }

    /* compiled from: FontsContractCompat */
    static class c implements c.d<g> {
        final /* synthetic */ String a;

        c(String str) {
            this.a = str;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:11:0x001e, code lost:
            if (r0 >= r1.size()) goto L_0x002c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0020, code lost:
            ((androidx.core.e.c.d) r1.get(r0)).a(r5);
            r0 = r0 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x002c, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0019, code lost:
            r0 = 0;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a(androidx.core.e.b.g r5) {
            /*
                r4 = this;
                java.lang.Object r0 = androidx.core.e.b.c
                monitor-enter(r0)
                g.a.g<java.lang.String, java.util.ArrayList<androidx.core.e.c$d<androidx.core.e.b$g>>> r1 = androidx.core.e.b.d     // Catch:{ all -> 0x002d }
                java.lang.String r2 = r4.a     // Catch:{ all -> 0x002d }
                java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x002d }
                java.util.ArrayList r1 = (java.util.ArrayList) r1     // Catch:{ all -> 0x002d }
                if (r1 != 0) goto L_0x0011
                monitor-exit(r0)     // Catch:{ all -> 0x002d }
                return
            L_0x0011:
                g.a.g<java.lang.String, java.util.ArrayList<androidx.core.e.c$d<androidx.core.e.b$g>>> r2 = androidx.core.e.b.d     // Catch:{ all -> 0x002d }
                java.lang.String r3 = r4.a     // Catch:{ all -> 0x002d }
                r2.remove(r3)     // Catch:{ all -> 0x002d }
                monitor-exit(r0)     // Catch:{ all -> 0x002d }
                r0 = 0
            L_0x001a:
                int r2 = r1.size()
                if (r0 >= r2) goto L_0x002c
                java.lang.Object r2 = r1.get(r0)
                androidx.core.e.c$d r2 = (androidx.core.e.c.d) r2
                r2.a(r5)
                int r0 = r0 + 1
                goto L_0x001a
            L_0x002c:
                return
            L_0x002d:
                r5 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x002d }
                goto L_0x0031
            L_0x0030:
                throw r5
            L_0x0031:
                goto L_0x0030
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.e.b.c.a(androidx.core.e.b$g):void");
        }
    }

    /* compiled from: FontsContractCompat */
    static class d implements Comparator<byte[]> {
        d() {
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: byte} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: byte} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v5, resolved type: byte} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: byte} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: byte} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: byte} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* renamed from: a */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int compare(byte[] r5, byte[] r6) {
            /*
                r4 = this;
                int r0 = r5.length
                int r1 = r6.length
                if (r0 == r1) goto L_0x0008
                int r5 = r5.length
                int r6 = r6.length
            L_0x0006:
                int r5 = r5 - r6
                return r5
            L_0x0008:
                r0 = 0
                r1 = 0
            L_0x000a:
                int r2 = r5.length
                if (r1 >= r2) goto L_0x001b
                byte r2 = r5[r1]
                byte r3 = r6[r1]
                if (r2 == r3) goto L_0x0018
                byte r5 = r5[r1]
                byte r6 = r6[r1]
                goto L_0x0006
            L_0x0018:
                int r1 = r1 + 1
                goto L_0x000a
            L_0x001b:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.e.b.d.compare(byte[], byte[]):int");
        }
    }

    /* compiled from: FontsContractCompat */
    public static class e {
        private final int a;
        private final f[] b;

        public e(int i2, f[] fVarArr) {
            this.a = i2;
            this.b = fVarArr;
        }

        public f[] a() {
            return this.b;
        }

        public int b() {
            return this.a;
        }
    }

    /* compiled from: FontsContractCompat */
    public static class f {
        private final Uri a;
        private final int b;
        private final int c;
        private final boolean d;
        private final int e;

        public f(Uri uri, int i2, int i3, boolean z, int i4) {
            h.a(uri);
            this.a = uri;
            this.b = i2;
            this.c = i3;
            this.d = z;
            this.e = i4;
        }

        public int a() {
            return this.e;
        }

        public int b() {
            return this.b;
        }

        public Uri c() {
            return this.a;
        }

        public int d() {
            return this.c;
        }

        public boolean e() {
            return this.d;
        }
    }

    /* compiled from: FontsContractCompat */
    private static final class g {
        final Typeface a;
        final int b;

        g(Typeface typeface, int i2) {
            this.a = typeface;
            this.b = i2;
        }
    }

    static g a(Context context, a aVar, int i2) {
        try {
            e a2 = a(context, (CancellationSignal) null, aVar);
            int i3 = -3;
            if (a2.b() == 0) {
                Typeface a3 = androidx.core.a.c.a(context, (CancellationSignal) null, a2.a(), i2);
                if (a3 != null) {
                    i3 = 0;
                }
                return new g(a3, i3);
            }
            if (a2.b() == 1) {
                i3 = -2;
            }
            return new g((Typeface) null, i3);
        } catch (PackageManager.NameNotFoundException unused) {
            return new g((Typeface) null, -1);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0072, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0083, code lost:
        b.a(r1, new androidx.core.e.b.c(r0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x008d, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Typeface a(android.content.Context r2, androidx.core.e.a r3, androidx.core.content.c.f.a r4, android.os.Handler r5, boolean r6, int r7, int r8) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = r3.c()
            r0.append(r1)
            java.lang.String r1 = "-"
            r0.append(r1)
            r0.append(r8)
            java.lang.String r0 = r0.toString()
            g.a.e<java.lang.String, android.graphics.Typeface> r1 = a
            java.lang.Object r1 = r1.b(r0)
            android.graphics.Typeface r1 = (android.graphics.Typeface) r1
            if (r1 == 0) goto L_0x0028
            if (r4 == 0) goto L_0x0027
            r4.a((android.graphics.Typeface) r1)
        L_0x0027:
            return r1
        L_0x0028:
            if (r6 == 0) goto L_0x0043
            r1 = -1
            if (r7 != r1) goto L_0x0043
            androidx.core.e.b$g r2 = a((android.content.Context) r2, (androidx.core.e.a) r3, (int) r8)
            if (r4 == 0) goto L_0x0040
            int r3 = r2.b
            if (r3 != 0) goto L_0x003d
            android.graphics.Typeface r3 = r2.a
            r4.a((android.graphics.Typeface) r3, (android.os.Handler) r5)
            goto L_0x0040
        L_0x003d:
            r4.a((int) r3, (android.os.Handler) r5)
        L_0x0040:
            android.graphics.Typeface r2 = r2.a
            return r2
        L_0x0043:
            androidx.core.e.b$a r1 = new androidx.core.e.b$a
            r1.<init>(r2, r3, r8, r0)
            r2 = 0
            if (r6 == 0) goto L_0x0056
            androidx.core.e.c r3 = b     // Catch:{ InterruptedException -> 0x0055 }
            java.lang.Object r3 = r3.a(r1, (int) r7)     // Catch:{ InterruptedException -> 0x0055 }
            androidx.core.e.b$g r3 = (androidx.core.e.b.g) r3     // Catch:{ InterruptedException -> 0x0055 }
            android.graphics.Typeface r2 = r3.a     // Catch:{ InterruptedException -> 0x0055 }
        L_0x0055:
            return r2
        L_0x0056:
            if (r4 != 0) goto L_0x005a
            r3 = r2
            goto L_0x005f
        L_0x005a:
            androidx.core.e.b$b r3 = new androidx.core.e.b$b
            r3.<init>(r4, r5)
        L_0x005f:
            java.lang.Object r4 = c
            monitor-enter(r4)
            g.a.g<java.lang.String, java.util.ArrayList<androidx.core.e.c$d<androidx.core.e.b$g>>> r5 = d     // Catch:{ all -> 0x008e }
            java.lang.Object r5 = r5.get(r0)     // Catch:{ all -> 0x008e }
            java.util.ArrayList r5 = (java.util.ArrayList) r5     // Catch:{ all -> 0x008e }
            if (r5 == 0) goto L_0x0073
            if (r3 == 0) goto L_0x0071
            r5.add(r3)     // Catch:{ all -> 0x008e }
        L_0x0071:
            monitor-exit(r4)     // Catch:{ all -> 0x008e }
            return r2
        L_0x0073:
            if (r3 == 0) goto L_0x0082
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ all -> 0x008e }
            r5.<init>()     // Catch:{ all -> 0x008e }
            r5.add(r3)     // Catch:{ all -> 0x008e }
            g.a.g<java.lang.String, java.util.ArrayList<androidx.core.e.c$d<androidx.core.e.b$g>>> r3 = d     // Catch:{ all -> 0x008e }
            r3.put(r0, r5)     // Catch:{ all -> 0x008e }
        L_0x0082:
            monitor-exit(r4)     // Catch:{ all -> 0x008e }
            androidx.core.e.c r3 = b
            androidx.core.e.b$c r4 = new androidx.core.e.b$c
            r4.<init>(r0)
            r3.a(r1, r4)
            return r2
        L_0x008e:
            r2 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x008e }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.e.b.a(android.content.Context, androidx.core.e.a, androidx.core.content.c.f$a, android.os.Handler, boolean, int, int):android.graphics.Typeface");
    }

    public static Map<Uri, ByteBuffer> a(Context context, f[] fVarArr, CancellationSignal cancellationSignal) {
        HashMap hashMap = new HashMap();
        for (f fVar : fVarArr) {
            if (fVar.a() == 0) {
                Uri c2 = fVar.c();
                if (!hashMap.containsKey(c2)) {
                    hashMap.put(c2, i.a(context, cancellationSignal, c2));
                }
            }
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public static e a(Context context, CancellationSignal cancellationSignal, a aVar) {
        ProviderInfo a2 = a(context.getPackageManager(), aVar, context.getResources());
        if (a2 == null) {
            return new e(1, (f[]) null);
        }
        return new e(0, a(context, aVar, a2.authority, cancellationSignal));
    }

    public static ProviderInfo a(PackageManager packageManager, a aVar, Resources resources) {
        String d2 = aVar.d();
        ProviderInfo resolveContentProvider = packageManager.resolveContentProvider(d2, 0);
        if (resolveContentProvider == null) {
            throw new PackageManager.NameNotFoundException("No package found for authority: " + d2);
        } else if (resolveContentProvider.packageName.equals(aVar.e())) {
            List<byte[]> a2 = a(packageManager.getPackageInfo(resolveContentProvider.packageName, 64).signatures);
            Collections.sort(a2, e);
            List<List<byte[]>> a3 = a(aVar, resources);
            for (int i2 = 0; i2 < a3.size(); i2++) {
                ArrayList arrayList = new ArrayList(a3.get(i2));
                Collections.sort(arrayList, e);
                if (a(a2, (List<byte[]>) arrayList)) {
                    return resolveContentProvider;
                }
            }
            return null;
        } else {
            throw new PackageManager.NameNotFoundException("Found content provider " + d2 + ", but package was not " + aVar.e());
        }
    }

    private static List<List<byte[]>> a(a aVar, Resources resources) {
        if (aVar.a() != null) {
            return aVar.a();
        }
        return androidx.core.content.c.c.a(resources, aVar.b());
    }

    private static boolean a(List<byte[]> list, List<byte[]> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (!Arrays.equals(list.get(i2), list2.get(i2))) {
                return false;
            }
        }
        return true;
    }

    private static List<byte[]> a(Signature[] signatureArr) {
        ArrayList arrayList = new ArrayList();
        for (Signature byteArray : signatureArr) {
            arrayList.add(byteArray.toByteArray());
        }
        return arrayList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x013a  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x014b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static androidx.core.e.b.f[] a(android.content.Context r23, androidx.core.e.a r24, java.lang.String r25, android.os.CancellationSignal r26) {
        /*
            r0 = r25
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            android.net.Uri$Builder r2 = new android.net.Uri$Builder
            r2.<init>()
            java.lang.String r3 = "content"
            android.net.Uri$Builder r2 = r2.scheme(r3)
            android.net.Uri$Builder r2 = r2.authority(r0)
            android.net.Uri r2 = r2.build()
            android.net.Uri$Builder r4 = new android.net.Uri$Builder
            r4.<init>()
            android.net.Uri$Builder r3 = r4.scheme(r3)
            android.net.Uri$Builder r0 = r3.authority(r0)
            java.lang.String r3 = "file"
            android.net.Uri$Builder r0 = r0.appendPath(r3)
            android.net.Uri r0 = r0.build()
            int r4 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0147 }
            r5 = 16
            java.lang.String r9 = "font_variation_settings"
            r11 = 2
            r12 = 7
            java.lang.String r13 = "result_code"
            java.lang.String r14 = "font_italic"
            java.lang.String r15 = "font_weight"
            java.lang.String r3 = "font_ttc_index"
            java.lang.String r6 = "file_id"
            java.lang.String r7 = "_id"
            r8 = 1
            r10 = 0
            if (r4 <= r5) goto L_0x0083
            android.content.ContentResolver r4 = r23.getContentResolver()     // Catch:{ all -> 0x0147 }
            java.lang.String[] r12 = new java.lang.String[r12]     // Catch:{ all -> 0x0147 }
            r12[r10] = r7     // Catch:{ all -> 0x0147 }
            r12[r8] = r6     // Catch:{ all -> 0x0147 }
            r12[r11] = r3     // Catch:{ all -> 0x0147 }
            r5 = 3
            r12[r5] = r9     // Catch:{ all -> 0x0147 }
            r5 = 4
            r12[r5] = r15     // Catch:{ all -> 0x0147 }
            r5 = 5
            r12[r5] = r14     // Catch:{ all -> 0x0147 }
            r5 = 6
            r12[r5] = r13     // Catch:{ all -> 0x0147 }
            java.lang.String r9 = "query = ?"
            java.lang.String[] r11 = new java.lang.String[r8]     // Catch:{ all -> 0x0147 }
            java.lang.String r5 = r24.f()     // Catch:{ all -> 0x0147 }
            r11[r10] = r5     // Catch:{ all -> 0x0147 }
            r16 = 0
            r5 = r2
            r20 = r1
            r1 = r6
            r6 = r12
            r12 = r7
            r7 = r9
            r9 = 1
            r8 = r11
            r11 = 1
            r9 = r16
            r11 = 0
            r10 = r26
            android.database.Cursor r4 = r4.query(r5, r6, r7, r8, r9, r10)     // Catch:{ all -> 0x0147 }
            r10 = r12
            r11 = 1
            goto L_0x00b5
        L_0x0083:
            r20 = r1
            r1 = r6
            r10 = r7
            r4 = 2
            r11 = 0
            android.content.ContentResolver r5 = r23.getContentResolver()     // Catch:{ all -> 0x0147 }
            java.lang.String[] r6 = new java.lang.String[r12]     // Catch:{ all -> 0x0147 }
            r6[r11] = r10     // Catch:{ all -> 0x0147 }
            r6[r8] = r1     // Catch:{ all -> 0x0147 }
            r6[r4] = r3     // Catch:{ all -> 0x0147 }
            r4 = 3
            r6[r4] = r9     // Catch:{ all -> 0x0147 }
            r4 = 4
            r6[r4] = r15     // Catch:{ all -> 0x0147 }
            r4 = 5
            r6[r4] = r14     // Catch:{ all -> 0x0147 }
            r4 = 6
            r6[r4] = r13     // Catch:{ all -> 0x0147 }
            java.lang.String r7 = "query = ?"
            java.lang.String[] r9 = new java.lang.String[r8]     // Catch:{ all -> 0x0147 }
            java.lang.String r4 = r24.f()     // Catch:{ all -> 0x0147 }
            r9[r11] = r4     // Catch:{ all -> 0x0147 }
            r12 = 0
            r4 = r5
            r5 = r2
            r11 = 1
            r8 = r9
            r9 = r12
            android.database.Cursor r4 = r4.query(r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0147 }
        L_0x00b5:
            if (r4 == 0) goto L_0x0136
            int r5 = r4.getCount()     // Catch:{ all -> 0x0133 }
            if (r5 <= 0) goto L_0x0136
            int r5 = r4.getColumnIndex(r13)     // Catch:{ all -> 0x0133 }
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ all -> 0x0133 }
            r6.<init>()     // Catch:{ all -> 0x0133 }
            int r7 = r4.getColumnIndex(r10)     // Catch:{ all -> 0x0133 }
            int r1 = r4.getColumnIndex(r1)     // Catch:{ all -> 0x0133 }
            int r3 = r4.getColumnIndex(r3)     // Catch:{ all -> 0x0133 }
            int r8 = r4.getColumnIndex(r15)     // Catch:{ all -> 0x0133 }
            int r9 = r4.getColumnIndex(r14)     // Catch:{ all -> 0x0133 }
        L_0x00da:
            boolean r10 = r4.moveToNext()     // Catch:{ all -> 0x0133 }
            if (r10 == 0) goto L_0x0131
            r10 = -1
            if (r5 == r10) goto L_0x00ea
            int r12 = r4.getInt(r5)     // Catch:{ all -> 0x0133 }
            r22 = r12
            goto L_0x00ec
        L_0x00ea:
            r22 = 0
        L_0x00ec:
            if (r3 == r10) goto L_0x00f5
            int r12 = r4.getInt(r3)     // Catch:{ all -> 0x0133 }
            r19 = r12
            goto L_0x00f7
        L_0x00f5:
            r19 = 0
        L_0x00f7:
            if (r1 != r10) goto L_0x0102
            long r12 = r4.getLong(r7)     // Catch:{ all -> 0x0133 }
            android.net.Uri r12 = android.content.ContentUris.withAppendedId(r2, r12)     // Catch:{ all -> 0x0133 }
            goto L_0x010a
        L_0x0102:
            long r12 = r4.getLong(r1)     // Catch:{ all -> 0x0133 }
            android.net.Uri r12 = android.content.ContentUris.withAppendedId(r0, r12)     // Catch:{ all -> 0x0133 }
        L_0x010a:
            r18 = r12
            if (r8 == r10) goto L_0x0115
            int r12 = r4.getInt(r8)     // Catch:{ all -> 0x0133 }
            r20 = r12
            goto L_0x0119
        L_0x0115:
            r12 = 400(0x190, float:5.6E-43)
            r20 = 400(0x190, float:5.6E-43)
        L_0x0119:
            if (r9 == r10) goto L_0x0124
            int r10 = r4.getInt(r9)     // Catch:{ all -> 0x0133 }
            if (r10 != r11) goto L_0x0124
            r21 = 1
            goto L_0x0126
        L_0x0124:
            r21 = 0
        L_0x0126:
            androidx.core.e.b$f r10 = new androidx.core.e.b$f     // Catch:{ all -> 0x0133 }
            r17 = r10
            r17.<init>(r18, r19, r20, r21, r22)     // Catch:{ all -> 0x0133 }
            r6.add(r10)     // Catch:{ all -> 0x0133 }
            goto L_0x00da
        L_0x0131:
            r1 = r6
            goto L_0x0138
        L_0x0133:
            r0 = move-exception
            r3 = r4
            goto L_0x0149
        L_0x0136:
            r1 = r20
        L_0x0138:
            if (r4 == 0) goto L_0x013d
            r4.close()
        L_0x013d:
            r0 = 0
            androidx.core.e.b$f[] r0 = new androidx.core.e.b.f[r0]
            java.lang.Object[] r0 = r1.toArray(r0)
            androidx.core.e.b$f[] r0 = (androidx.core.e.b.f[]) r0
            return r0
        L_0x0147:
            r0 = move-exception
            r3 = 0
        L_0x0149:
            if (r3 == 0) goto L_0x014e
            r3.close()
        L_0x014e:
            goto L_0x0150
        L_0x014f:
            throw r0
        L_0x0150:
            goto L_0x014f
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.e.b.a(android.content.Context, androidx.core.e.a, java.lang.String, android.os.CancellationSignal):androidx.core.e.b$f[]");
    }
}
