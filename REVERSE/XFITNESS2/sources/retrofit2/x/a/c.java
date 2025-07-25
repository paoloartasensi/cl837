package retrofit2.x.a;

import com.google.gson.JsonIOException;
import com.google.gson.d;
import com.google.gson.o;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import okhttp3.i0;
import retrofit2.h;

/* compiled from: GsonResponseBodyConverter */
final class c<T> implements h<i0, T> {
    private final d a;
    private final o<T> b;

    c(d dVar, o<T> oVar) {
        this.a = dVar;
        this.b = oVar;
    }

    public T a(i0 i0Var) {
        JsonReader a2 = this.a.a(i0Var.a());
        try {
            T a3 = this.b.a(a2);
            if (a2.peek() == JsonToken.END_DOCUMENT) {
                return a3;
            }
            throw new JsonIOException("JSON document was not fully consumed.");
        } finally {
            i0Var.close();
        }
    }
}
