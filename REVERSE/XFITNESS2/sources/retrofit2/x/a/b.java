package retrofit2.x.a;

import com.google.gson.d;
import com.google.gson.o;
import com.google.gson.stream.JsonWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import okhttp3.b0;
import okhttp3.g0;
import okio.c;
import retrofit2.h;

/* compiled from: GsonRequestBodyConverter */
final class b<T> implements h<T, g0> {
    private static final b0 c = b0.a("application/json; charset=UTF-8");
    private static final Charset d = Charset.forName("UTF-8");
    private final d a;
    private final o<T> b;

    b(d dVar, o<T> oVar) {
        this.a = dVar;
        this.b = oVar;
    }

    public g0 a(T t) {
        c cVar = new c();
        JsonWriter a2 = this.a.a((Writer) new OutputStreamWriter(cVar.n(), d));
        this.b.a(a2, t);
        a2.close();
        return g0.a(c, cVar.p());
    }
}
