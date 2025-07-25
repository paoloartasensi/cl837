package com.chileaf.fitness.device.wear.cl880.external;

import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: NotificationSpec */
public class a {

    /* renamed from: g  reason: collision with root package name */
    private static final AtomicInteger f1221g = new AtomicInteger((int) (System.currentTimeMillis() / 1000));
    private int a = f1221g.incrementAndGet();
    public long b;
    public String c;
    public String d;
    public String e;

    /* renamed from: f  reason: collision with root package name */
    public NotificationType f1222f;

    public String toString() {
        return "NotificationSpec{id=" + this.a + ", stamp=" + this.b + ", title='" + this.c + '\'' + ", content='" + this.d + '\'' + ", sourceAppId='" + this.e + '\'' + ", type=" + this.f1222f + '}';
    }
}
