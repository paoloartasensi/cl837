package okhttp3.internal.http2;

import java.util.List;
import okio.e;

/* compiled from: PushObserver */
public interface j {
    public static final j a = new a();

    /* compiled from: PushObserver */
    class a implements j {
        a() {
        }

        public void a(int i2, ErrorCode errorCode) {
        }

        public boolean a(int i2, List<a> list) {
            return true;
        }

        public boolean a(int i2, List<a> list, boolean z) {
            return true;
        }

        public boolean a(int i2, e eVar, int i3, boolean z) {
            eVar.skip((long) i3);
            return true;
        }
    }

    void a(int i2, ErrorCode errorCode);

    boolean a(int i2, List<a> list);

    boolean a(int i2, List<a> list, boolean z);

    boolean a(int i2, e eVar, int i3, boolean z);
}
