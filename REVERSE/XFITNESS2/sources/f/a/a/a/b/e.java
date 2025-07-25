package f.a.a.a.b;

import android.os.AsyncTask;

/* compiled from: PostInfoTask */
public class e extends AsyncTask<Void, Void, Void> {
    private String a;

    public e(String str) {
        this.a = str;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Void doInBackground(Void... voidArr) {
        return a(this.a);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0077, code lost:
        if (r1 == null) goto L_0x00b2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x009a, code lost:
        if (r1 == null) goto L_0x00b2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00ad, code lost:
        if (r1 == null) goto L_0x00b2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00af, code lost:
        r1.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00b2, code lost:
        return null;
     */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0092 A[SYNTHETIC, Splitter:B:34:0x0092] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00a5 A[SYNTHETIC, Splitter:B:44:0x00a5] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00b6 A[SYNTHETIC, Splitter:B:53:0x00b6] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00c0  */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:41:0x00a0=Splitter:B:41:0x00a0, B:31:0x008d=Splitter:B:31:0x008d} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.Void a(java.lang.String r6) {
        /*
            r5 = this;
            r0 = 0
            java.net.URL r1 = new java.net.URL     // Catch:{ MalformedURLException -> 0x009d, IOException -> 0x008a, all -> 0x0085 }
            java.lang.String r2 = "http://aicare.net.cn/register/weightuser/pushBTInfo"
            r1.<init>(r2)     // Catch:{ MalformedURLException -> 0x009d, IOException -> 0x008a, all -> 0x0085 }
            java.net.URLConnection r1 = r1.openConnection()     // Catch:{ MalformedURLException -> 0x009d, IOException -> 0x008a, all -> 0x0085 }
            java.net.HttpURLConnection r1 = (java.net.HttpURLConnection) r1     // Catch:{ MalformedURLException -> 0x009d, IOException -> 0x008a, all -> 0x0085 }
            java.lang.String r2 = "POST"
            r1.setRequestMethod(r2)     // Catch:{ MalformedURLException -> 0x0082, IOException -> 0x007f, all -> 0x007a }
            r2 = 1
            r1.setDoOutput(r2)     // Catch:{ MalformedURLException -> 0x0082, IOException -> 0x007f, all -> 0x007a }
            r1.setDoInput(r2)     // Catch:{ MalformedURLException -> 0x0082, IOException -> 0x007f, all -> 0x007a }
            java.lang.String r2 = "Content-Type"
            java.lang.String r3 = "application/x-www-form-urlencoded; charset=UTF-8"
            r1.setRequestProperty(r2, r3)     // Catch:{ MalformedURLException -> 0x0082, IOException -> 0x007f, all -> 0x007a }
            r2 = 0
            r1.setUseCaches(r2)     // Catch:{ MalformedURLException -> 0x0082, IOException -> 0x007f, all -> 0x007a }
            r2 = 10000(0x2710, float:1.4013E-41)
            r1.setConnectTimeout(r2)     // Catch:{ MalformedURLException -> 0x0082, IOException -> 0x007f, all -> 0x007a }
            r1.setReadTimeout(r2)     // Catch:{ MalformedURLException -> 0x0082, IOException -> 0x007f, all -> 0x007a }
            r1.connect()     // Catch:{ MalformedURLException -> 0x0082, IOException -> 0x007f, all -> 0x007a }
            java.io.DataOutputStream r2 = new java.io.DataOutputStream     // Catch:{ MalformedURLException -> 0x0082, IOException -> 0x007f, all -> 0x007a }
            java.io.OutputStream r3 = r1.getOutputStream()     // Catch:{ MalformedURLException -> 0x0082, IOException -> 0x007f, all -> 0x007a }
            r2.<init>(r3)     // Catch:{ MalformedURLException -> 0x0082, IOException -> 0x007f, all -> 0x007a }
            r2.writeBytes(r6)     // Catch:{ MalformedURLException -> 0x0082, IOException -> 0x007f, all -> 0x007a }
            r2.flush()     // Catch:{ MalformedURLException -> 0x0082, IOException -> 0x007f, all -> 0x007a }
            r2.close()     // Catch:{ MalformedURLException -> 0x0082, IOException -> 0x007f, all -> 0x007a }
            int r6 = r1.getResponseCode()     // Catch:{ MalformedURLException -> 0x0082, IOException -> 0x007f, all -> 0x007a }
            r2 = 200(0xc8, float:2.8E-43)
            if (r6 != r2) goto L_0x006c
            java.io.BufferedInputStream r6 = new java.io.BufferedInputStream     // Catch:{ MalformedURLException -> 0x0082, IOException -> 0x007f, all -> 0x007a }
            java.io.InputStream r2 = r1.getInputStream()     // Catch:{ MalformedURLException -> 0x0082, IOException -> 0x007f, all -> 0x007a }
            r6.<init>(r2)     // Catch:{ MalformedURLException -> 0x0082, IOException -> 0x007f, all -> 0x007a }
            java.util.Scanner r2 = new java.util.Scanner     // Catch:{ MalformedURLException -> 0x006a, IOException -> 0x0068 }
            r2.<init>(r6)     // Catch:{ MalformedURLException -> 0x006a, IOException -> 0x0068 }
            java.lang.String r3 = "\\A"
            java.util.Scanner r2 = r2.useDelimiter(r3)     // Catch:{ MalformedURLException -> 0x006a, IOException -> 0x0068 }
            boolean r3 = r2.hasNext()     // Catch:{ MalformedURLException -> 0x006a, IOException -> 0x0068 }
            if (r3 == 0) goto L_0x006d
            r2.next()     // Catch:{ MalformedURLException -> 0x006a, IOException -> 0x0068 }
            goto L_0x006d
        L_0x0068:
            r2 = move-exception
            goto L_0x008d
        L_0x006a:
            r2 = move-exception
            goto L_0x00a0
        L_0x006c:
            r6 = r0
        L_0x006d:
            if (r6 == 0) goto L_0x0077
            r6.close()     // Catch:{ IOException -> 0x0073 }
            goto L_0x0077
        L_0x0073:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0077:
            if (r1 == 0) goto L_0x00b2
            goto L_0x00af
        L_0x007a:
            r6 = move-exception
            r4 = r0
            r0 = r6
            r6 = r4
            goto L_0x00b4
        L_0x007f:
            r2 = move-exception
            r6 = r0
            goto L_0x008d
        L_0x0082:
            r2 = move-exception
            r6 = r0
            goto L_0x00a0
        L_0x0085:
            r6 = move-exception
            r1 = r0
            r0 = r6
            r6 = r1
            goto L_0x00b4
        L_0x008a:
            r2 = move-exception
            r6 = r0
            r1 = r6
        L_0x008d:
            r2.printStackTrace()     // Catch:{ all -> 0x00b3 }
            if (r6 == 0) goto L_0x009a
            r6.close()     // Catch:{ IOException -> 0x0096 }
            goto L_0x009a
        L_0x0096:
            r6 = move-exception
            r6.printStackTrace()
        L_0x009a:
            if (r1 == 0) goto L_0x00b2
            goto L_0x00af
        L_0x009d:
            r2 = move-exception
            r6 = r0
            r1 = r6
        L_0x00a0:
            r2.printStackTrace()     // Catch:{ all -> 0x00b3 }
            if (r6 == 0) goto L_0x00ad
            r6.close()     // Catch:{ IOException -> 0x00a9 }
            goto L_0x00ad
        L_0x00a9:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00ad:
            if (r1 == 0) goto L_0x00b2
        L_0x00af:
            r1.disconnect()
        L_0x00b2:
            return r0
        L_0x00b3:
            r0 = move-exception
        L_0x00b4:
            if (r6 == 0) goto L_0x00be
            r6.close()     // Catch:{ IOException -> 0x00ba }
            goto L_0x00be
        L_0x00ba:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00be:
            if (r1 == 0) goto L_0x00c3
            r1.disconnect()
        L_0x00c3:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: f.a.a.a.b.e.a(java.lang.String):java.lang.Void");
    }
}
