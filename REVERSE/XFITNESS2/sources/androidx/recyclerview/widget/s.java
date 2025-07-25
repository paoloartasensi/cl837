package androidx.recyclerview.widget;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.h.e0.d;
import androidx.core.h.e0.e;
import androidx.core.h.v;
import java.util.Map;
import java.util.WeakHashMap;

/* compiled from: RecyclerViewAccessibilityDelegate */
public class s extends androidx.core.h.a {
    final RecyclerView d;
    private final a e;

    /* compiled from: RecyclerViewAccessibilityDelegate */
    public static class a extends androidx.core.h.a {
        final s d;
        private Map<View, androidx.core.h.a> e = new WeakHashMap();

        public a(s sVar) {
            this.d = sVar;
        }

        public void a(View view, d dVar) {
            if (this.d.c() || this.d.d.getLayoutManager() == null) {
                super.a(view, dVar);
                return;
            }
            this.d.d.getLayoutManager().a(view, dVar);
            androidx.core.h.a aVar = this.e.get(view);
            if (aVar != null) {
                aVar.a(view, dVar);
            } else {
                super.a(view, dVar);
            }
        }

        public void b(View view, AccessibilityEvent accessibilityEvent) {
            androidx.core.h.a aVar = this.e.get(view);
            if (aVar != null) {
                aVar.b(view, accessibilityEvent);
            } else {
                super.b(view, accessibilityEvent);
            }
        }

        /* access modifiers changed from: package-private */
        public androidx.core.h.a c(View view) {
            return this.e.remove(view);
        }

        /* access modifiers changed from: package-private */
        public void d(View view) {
            androidx.core.h.a b = v.b(view);
            if (b != null && b != this) {
                this.e.put(view, b);
            }
        }

        public void c(View view, AccessibilityEvent accessibilityEvent) {
            androidx.core.h.a aVar = this.e.get(view);
            if (aVar != null) {
                aVar.c(view, accessibilityEvent);
            } else {
                super.c(view, accessibilityEvent);
            }
        }

        public void d(View view, AccessibilityEvent accessibilityEvent) {
            androidx.core.h.a aVar = this.e.get(view);
            if (aVar != null) {
                aVar.d(view, accessibilityEvent);
            } else {
                super.d(view, accessibilityEvent);
            }
        }

        public boolean a(View view, int i2, Bundle bundle) {
            if (this.d.c() || this.d.d.getLayoutManager() == null) {
                return super.a(view, i2, bundle);
            }
            androidx.core.h.a aVar = this.e.get(view);
            if (aVar != null) {
                if (aVar.a(view, i2, bundle)) {
                    return true;
                }
            } else if (super.a(view, i2, bundle)) {
                return true;
            }
            return this.d.d.getLayoutManager().a(view, i2, bundle);
        }

        public void a(View view, int i2) {
            androidx.core.h.a aVar = this.e.get(view);
            if (aVar != null) {
                aVar.a(view, i2);
            } else {
                super.a(view, i2);
            }
        }

        public boolean a(View view, AccessibilityEvent accessibilityEvent) {
            androidx.core.h.a aVar = this.e.get(view);
            if (aVar != null) {
                return aVar.a(view, accessibilityEvent);
            }
            return super.a(view, accessibilityEvent);
        }

        public boolean a(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            androidx.core.h.a aVar = this.e.get(viewGroup);
            if (aVar != null) {
                return aVar.a(viewGroup, view, accessibilityEvent);
            }
            return super.a(viewGroup, view, accessibilityEvent);
        }

        public e a(View view) {
            androidx.core.h.a aVar = this.e.get(view);
            if (aVar != null) {
                return aVar.a(view);
            }
            return super.a(view);
        }
    }

    public s(RecyclerView recyclerView) {
        this.d = recyclerView;
        androidx.core.h.a b = b();
        if (b == null || !(b instanceof a)) {
            this.e = new a(this);
        } else {
            this.e = (a) b;
        }
    }

    public boolean a(View view, int i2, Bundle bundle) {
        if (super.a(view, i2, bundle)) {
            return true;
        }
        if (c() || this.d.getLayoutManager() == null) {
            return false;
        }
        return this.d.getLayoutManager().a(i2, bundle);
    }

    public void b(View view, AccessibilityEvent accessibilityEvent) {
        super.b(view, accessibilityEvent);
        if ((view instanceof RecyclerView) && !c()) {
            RecyclerView recyclerView = (RecyclerView) view;
            if (recyclerView.getLayoutManager() != null) {
                recyclerView.getLayoutManager().a(accessibilityEvent);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean c() {
        return this.d.j();
    }

    public void a(View view, d dVar) {
        super.a(view, dVar);
        if (!c() && this.d.getLayoutManager() != null) {
            this.d.getLayoutManager().a(dVar);
        }
    }

    public androidx.core.h.a b() {
        return this.e;
    }
}
