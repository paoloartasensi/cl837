package retrofit2;

public class HttpException extends RuntimeException {
    private final int code;
    private final transient r<?> e;
    private final String message;

    public HttpException(r<?> rVar) {
        super(a(rVar));
        this.code = rVar.b();
        this.message = rVar.d();
        this.e = rVar;
    }

    private static String a(r<?> rVar) {
        d.a(rVar, "response == null");
        return "HTTP " + rVar.b() + " " + rVar.d();
    }

    public int code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public r<?> response() {
        return this.e;
    }
}
