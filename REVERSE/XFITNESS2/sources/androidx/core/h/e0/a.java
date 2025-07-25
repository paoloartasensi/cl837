package androidx.core.h.e0;

import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.view.View;

/* compiled from: AccessibilityClickableSpanCompat */
public final class a extends ClickableSpan {
    private final int e;

    /* renamed from: f  reason: collision with root package name */
    private final d f510f;

    /* renamed from: g  reason: collision with root package name */
    private final int f511g;

    public a(int i2, d dVar, int i3) {
        this.e = i2;
        this.f510f = dVar;
        this.f511g = i3;
    }

    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("ACCESSIBILITY_CLICKABLE_SPAN_ID", this.e);
        this.f510f.a(this.f511g, bundle);
    }
}
