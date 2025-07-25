package com.chileaf.fitness.widget;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    private final /* synthetic */ WheelView e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ int f1320f;

    public /* synthetic */ a(WheelView wheelView, int i2) {
        this.e = wheelView;
        this.f1320f = i2;
    }

    public final void run() {
        this.e.a(this.f1320f);
    }
}
