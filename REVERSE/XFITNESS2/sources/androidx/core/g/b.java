package androidx.core.g;

import android.util.Log;
import java.io.Writer;

/* compiled from: LogWriter */
public class b extends Writer {
    private final String e;

    /* renamed from: f  reason: collision with root package name */
    private StringBuilder f494f = new StringBuilder(128);

    public b(String str) {
        this.e = str;
    }

    private void a() {
        if (this.f494f.length() > 0) {
            Log.d(this.e, this.f494f.toString());
            StringBuilder sb = this.f494f;
            sb.delete(0, sb.length());
        }
    }

    public void close() {
        a();
    }

    public void flush() {
        a();
    }

    public void write(char[] cArr, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            char c = cArr[i2 + i4];
            if (c == 10) {
                a();
            } else {
                this.f494f.append(c);
            }
        }
    }
}
