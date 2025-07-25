package okhttp3.internal.connection;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import okhttp3.e;
import okhttp3.j;
import okhttp3.j0;
import okhttp3.v;
import okhttp3.z;

/* compiled from: RouteSelector */
final class i {
    private final e a;
    private final h b;
    private final j c;
    private final v d;
    private List<Proxy> e = Collections.emptyList();

    /* renamed from: f  reason: collision with root package name */
    private int f1927f;

    /* renamed from: g  reason: collision with root package name */
    private List<InetSocketAddress> f1928g = Collections.emptyList();

    /* renamed from: h  reason: collision with root package name */
    private final List<j0> f1929h = new ArrayList();

    /* compiled from: RouteSelector */
    public static final class a {
        private final List<j0> a;
        private int b = 0;

        a(List<j0> list) {
            this.a = list;
        }

        public List<j0> a() {
            return new ArrayList(this.a);
        }

        public boolean b() {
            return this.b < this.a.size();
        }

        public j0 c() {
            if (b()) {
                List<j0> list = this.a;
                int i2 = this.b;
                this.b = i2 + 1;
                return list.get(i2);
            }
            throw new NoSuchElementException();
        }
    }

    i(e eVar, h hVar, j jVar, v vVar) {
        this.a = eVar;
        this.b = hVar;
        this.c = jVar;
        this.d = vVar;
        a(eVar.k(), eVar.f());
    }

    private boolean c() {
        return this.f1927f < this.e.size();
    }

    private Proxy d() {
        if (c()) {
            List<Proxy> list = this.e;
            int i2 = this.f1927f;
            this.f1927f = i2 + 1;
            Proxy proxy = list.get(i2);
            a(proxy);
            return proxy;
        }
        throw new SocketException("No route to " + this.a.k().g() + "; exhausted proxy configurations: " + this.e);
    }

    public boolean a() {
        return c() || !this.f1929h.isEmpty();
    }

    public a b() {
        if (a()) {
            ArrayList arrayList = new ArrayList();
            while (c()) {
                Proxy d2 = d();
                int size = this.f1928g.size();
                for (int i2 = 0; i2 < size; i2++) {
                    j0 j0Var = new j0(this.a, d2, this.f1928g.get(i2));
                    if (this.b.c(j0Var)) {
                        this.f1929h.add(j0Var);
                    } else {
                        arrayList.add(j0Var);
                    }
                }
                if (!arrayList.isEmpty()) {
                    break;
                }
            }
            if (arrayList.isEmpty()) {
                arrayList.addAll(this.f1929h);
                this.f1929h.clear();
            }
            return new a(arrayList);
        }
        throw new NoSuchElementException();
    }

    private void a(z zVar, Proxy proxy) {
        List<Proxy> list;
        if (proxy != null) {
            this.e = Collections.singletonList(proxy);
        } else {
            List<Proxy> select = this.a.h().select(zVar.o());
            if (select == null || select.isEmpty()) {
                list = okhttp3.k0.e.a((T[]) new Proxy[]{Proxy.NO_PROXY});
            } else {
                list = okhttp3.k0.e.a(select);
            }
            this.e = list;
        }
        this.f1927f = 0;
    }

    private void a(Proxy proxy) {
        String str;
        int i2;
        this.f1928g = new ArrayList();
        if (proxy.type() == Proxy.Type.DIRECT || proxy.type() == Proxy.Type.SOCKS) {
            str = this.a.k().g();
            i2 = this.a.k().k();
        } else {
            SocketAddress address = proxy.address();
            if (address instanceof InetSocketAddress) {
                InetSocketAddress inetSocketAddress = (InetSocketAddress) address;
                str = a(inetSocketAddress);
                i2 = inetSocketAddress.getPort();
            } else {
                throw new IllegalArgumentException("Proxy.address() is not an InetSocketAddress: " + address.getClass());
            }
        }
        if (i2 < 1 || i2 > 65535) {
            throw new SocketException("No route to " + str + ":" + i2 + "; port is out of range");
        } else if (proxy.type() == Proxy.Type.SOCKS) {
            this.f1928g.add(InetSocketAddress.createUnresolved(str, i2));
        } else {
            this.d.a(this.c, str);
            List<InetAddress> a2 = this.a.c().a(str);
            if (!a2.isEmpty()) {
                this.d.a(this.c, str, a2);
                int size = a2.size();
                for (int i3 = 0; i3 < size; i3++) {
                    this.f1928g.add(new InetSocketAddress(a2.get(i3), i2));
                }
                return;
            }
            throw new UnknownHostException(this.a.c() + " returned no addresses for " + str);
        }
    }

    static String a(InetSocketAddress inetSocketAddress) {
        InetAddress address = inetSocketAddress.getAddress();
        if (address == null) {
            return inetSocketAddress.getHostName();
        }
        return address.getHostAddress();
    }
}
