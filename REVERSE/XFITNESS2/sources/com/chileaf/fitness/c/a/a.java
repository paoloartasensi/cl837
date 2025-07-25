package com.chileaf.fitness.c.a;

import android.widget.CompoundButton;

/* compiled from: OnCheckedChangeListener */
public final class a implements CompoundButton.OnCheckedChangeListener {
    final C0063a a;
    final int b;

    /* renamed from: com.chileaf.fitness.c.a.a$a  reason: collision with other inner class name */
    /* compiled from: OnCheckedChangeListener */
    public interface C0063a {
        void a(int i2, CompoundButton compoundButton, boolean z);
    }

    public a(C0063a aVar, int i2) {
        this.a = aVar;
        this.b = i2;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.a.a(this.b, compoundButton, z);
    }
}
