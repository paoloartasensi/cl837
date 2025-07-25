package androidx.core.app;

import android.app.RemoteInput;
import android.os.Bundle;
import java.util.Set;

/* compiled from: RemoteInput */
public final class k {
    private final String a;
    private final CharSequence b;
    private final CharSequence[] c;
    private final boolean d;
    private final Bundle e;

    /* renamed from: f  reason: collision with root package name */
    private final Set<String> f470f;

    k(String str, CharSequence charSequence, CharSequence[] charSequenceArr, boolean z, Bundle bundle, Set<String> set) {
        this.a = str;
        this.b = charSequence;
        this.c = charSequenceArr;
        this.d = z;
        this.e = bundle;
        this.f470f = set;
    }

    public boolean a() {
        return this.d;
    }

    public Set<String> b() {
        return this.f470f;
    }

    public CharSequence[] c() {
        return this.c;
    }

    public Bundle d() {
        return this.e;
    }

    public CharSequence e() {
        return this.b;
    }

    public String f() {
        return this.a;
    }

    static RemoteInput[] a(k[] kVarArr) {
        if (kVarArr == null) {
            return null;
        }
        RemoteInput[] remoteInputArr = new RemoteInput[kVarArr.length];
        for (int i2 = 0; i2 < kVarArr.length; i2++) {
            remoteInputArr[i2] = a(kVarArr[i2]);
        }
        return remoteInputArr;
    }

    static RemoteInput a(k kVar) {
        return new RemoteInput.Builder(kVar.f()).setLabel(kVar.e()).setChoices(kVar.c()).setAllowFreeFormInput(kVar.a()).addExtras(kVar.d()).build();
    }
}
