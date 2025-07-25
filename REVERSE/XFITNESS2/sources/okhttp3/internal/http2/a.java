package okhttp3.internal.http2;

import okhttp3.k0.e;
import okio.ByteString;

/* compiled from: Header */
public final class a {
    public static final ByteString d = ByteString.encodeUtf8(":");
    public static final ByteString e = ByteString.encodeUtf8(":status");

    /* renamed from: f  reason: collision with root package name */
    public static final ByteString f1935f = ByteString.encodeUtf8(":method");

    /* renamed from: g  reason: collision with root package name */
    public static final ByteString f1936g = ByteString.encodeUtf8(":path");

    /* renamed from: h  reason: collision with root package name */
    public static final ByteString f1937h = ByteString.encodeUtf8(":scheme");

    /* renamed from: i  reason: collision with root package name */
    public static final ByteString f1938i = ByteString.encodeUtf8(":authority");
    public final ByteString a;
    public final ByteString b;
    final int c;

    public a(String str, String str2) {
        this(ByteString.encodeUtf8(str), ByteString.encodeUtf8(str2));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        if (!this.a.equals(aVar.a) || !this.b.equals(aVar.b)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return ((527 + this.a.hashCode()) * 31) + this.b.hashCode();
    }

    public String toString() {
        return e.a("%s: %s", this.a.utf8(), this.b.utf8());
    }

    public a(ByteString byteString, String str) {
        this(byteString, ByteString.encodeUtf8(str));
    }

    public a(ByteString byteString, ByteString byteString2) {
        this.a = byteString;
        this.b = byteString2;
        this.c = byteString.size() + 32 + byteString2.size();
    }
}
