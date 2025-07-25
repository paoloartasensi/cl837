package androidx.loader.a;

import android.os.Bundle;
import android.util.Log;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.loader.b.a;
import g.a.h;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: LoaderManagerImpl */
class b extends a {
    static boolean c = false;
    private final LifecycleOwner a;
    private final c b;

    /* compiled from: LoaderManagerImpl */
    public static class a<D> extends MutableLiveData<D> implements a.b<D> {
        private final int a;
        private final Bundle b;
        private final androidx.loader.b.a<D> c;
        private LifecycleOwner d;
        private C0035b<D> e;

        /* renamed from: f  reason: collision with root package name */
        private androidx.loader.b.a<D> f663f;

        /* access modifiers changed from: package-private */
        public androidx.loader.b.a<D> a() {
            return this.c;
        }

        /* access modifiers changed from: package-private */
        public void b() {
            LifecycleOwner lifecycleOwner = this.d;
            C0035b<D> bVar = this.e;
            if (lifecycleOwner != null && bVar != null) {
                super.removeObserver(bVar);
                observe(lifecycleOwner, bVar);
            }
        }

        /* access modifiers changed from: protected */
        public void onActive() {
            if (b.c) {
                Log.v("LoaderManager", "  Starting: " + this);
            }
            this.c.i();
        }

        /* access modifiers changed from: protected */
        public void onInactive() {
            if (b.c) {
                Log.v("LoaderManager", "  Stopping: " + this);
            }
            this.c.j();
        }

        public void removeObserver(Observer<? super D> observer) {
            super.removeObserver(observer);
            this.d = null;
            this.e = null;
        }

        public void setValue(D d2) {
            super.setValue(d2);
            androidx.loader.b.a<D> aVar = this.f663f;
            if (aVar != null) {
                aVar.h();
                this.f663f = null;
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(64);
            sb.append("LoaderInfo{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" #");
            sb.append(this.a);
            sb.append(" : ");
            androidx.core.g.a.a(this.c, sb);
            sb.append("}}");
            return sb.toString();
        }

        /* access modifiers changed from: package-private */
        public androidx.loader.b.a<D> a(boolean z) {
            if (b.c) {
                Log.v("LoaderManager", "  Destroying: " + this);
            }
            this.c.b();
            this.c.a();
            C0035b<D> bVar = this.e;
            if (bVar != null) {
                removeObserver(bVar);
                if (z) {
                    bVar.b();
                    throw null;
                }
            }
            this.c.unregisterListener(this);
            if (bVar != null) {
                bVar.a();
                throw null;
            } else if (!z) {
                return this.c;
            } else {
                this.c.h();
                return this.f663f;
            }
        }

        public void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            printWriter.print(str);
            printWriter.print("mId=");
            printWriter.print(this.a);
            printWriter.print(" mArgs=");
            printWriter.println(this.b);
            printWriter.print(str);
            printWriter.print("mLoader=");
            printWriter.println(this.c);
            androidx.loader.b.a<D> aVar = this.c;
            aVar.a(str + "  ", fileDescriptor, printWriter, strArr);
            if (this.e == null) {
                printWriter.print(str);
                printWriter.print("mData=");
                printWriter.println(a().a(getValue()));
                printWriter.print(str);
                printWriter.print("mStarted=");
                printWriter.println(hasActiveObservers());
                return;
            }
            printWriter.print(str);
            printWriter.print("mCallbacks=");
            printWriter.println(this.e);
            C0035b<D> bVar = this.e;
            bVar.a(str + "  ", printWriter);
            throw null;
        }
    }

    /* renamed from: androidx.loader.a.b$b  reason: collision with other inner class name */
    /* compiled from: LoaderManagerImpl */
    static class C0035b<D> implements Observer<D> {
        public void a(String str, PrintWriter printWriter) {
            throw null;
        }

        /* access modifiers changed from: package-private */
        public boolean a() {
            throw null;
        }

        /* access modifiers changed from: package-private */
        public void b() {
            throw null;
        }
    }

    /* compiled from: LoaderManagerImpl */
    static class c extends ViewModel {
        private static final ViewModelProvider.Factory b = new a();
        private h<a> a = new h<>();

        /* compiled from: LoaderManagerImpl */
        static class a implements ViewModelProvider.Factory {
            a() {
            }

            public <T extends ViewModel> T create(Class<T> cls) {
                return new c();
            }
        }

        c() {
        }

        static c a(ViewModelStore viewModelStore) {
            return (c) new ViewModelProvider(viewModelStore, b).get(c.class);
        }

        /* access modifiers changed from: package-private */
        public void b() {
            int d = this.a.d();
            for (int i2 = 0; i2 < d; i2++) {
                this.a.f(i2).b();
            }
        }

        /* access modifiers changed from: protected */
        public void onCleared() {
            super.onCleared();
            int d = this.a.d();
            for (int i2 = 0; i2 < d; i2++) {
                this.a.f(i2).a(true);
            }
            this.a.b();
        }

        public void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (this.a.d() > 0) {
                printWriter.print(str);
                printWriter.println("Loaders:");
                String str2 = str + "    ";
                for (int i2 = 0; i2 < this.a.d(); i2++) {
                    a f2 = this.a.f(i2);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(this.a.c(i2));
                    printWriter.print(": ");
                    printWriter.println(f2.toString());
                    f2.a(str2, fileDescriptor, printWriter, strArr);
                }
            }
        }
    }

    b(LifecycleOwner lifecycleOwner, ViewModelStore viewModelStore) {
        this.a = lifecycleOwner;
        this.b = c.a(viewModelStore);
    }

    public void a() {
        this.b.b();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("LoaderManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        androidx.core.g.a.a(this.a, sb);
        sb.append("}}");
        return sb.toString();
    }

    @Deprecated
    public void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.b.a(str, fileDescriptor, printWriter, strArr);
    }
}
