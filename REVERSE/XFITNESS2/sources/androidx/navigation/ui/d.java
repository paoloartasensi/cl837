package androidx.navigation.ui;

import android.annotation.SuppressLint;
import androidx.drawerlayout.widget.DrawerLayout;
import java.util.HashSet;
import java.util.Set;

/* compiled from: AppBarConfiguration */
public final class d {
    private final Set<Integer> a;
    private final DrawerLayout b;
    private final c c;

    /* compiled from: AppBarConfiguration */
    public static final class b {
        private final Set<Integer> a;
        private DrawerLayout b;
        private c c;

        public b(Set<Integer> set) {
            HashSet hashSet = new HashSet();
            this.a = hashSet;
            hashSet.addAll(set);
        }

        public b a(DrawerLayout drawerLayout) {
            this.b = drawerLayout;
            return this;
        }

        public b a(c cVar) {
            this.c = cVar;
            return this;
        }

        @SuppressLint({"SyntheticAccessor"})
        public d a() {
            return new d(this.a, this.b, this.c);
        }
    }

    /* compiled from: AppBarConfiguration */
    public interface c {
        boolean a();
    }

    public DrawerLayout a() {
        return this.b;
    }

    public c b() {
        return this.c;
    }

    public Set<Integer> c() {
        return this.a;
    }

    private d(Set<Integer> set, DrawerLayout drawerLayout, c cVar) {
        this.a = set;
        this.b = drawerLayout;
        this.c = cVar;
    }
}
