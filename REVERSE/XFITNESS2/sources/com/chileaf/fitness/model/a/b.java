package com.chileaf.fitness.model.a;

import androidx.lifecycle.LiveData;

/* compiled from: DeviceStateLiveData */
public class b extends LiveData<b> {
    private boolean a = false;
    private boolean b;
    private boolean c;

    public b(boolean z, boolean z2) {
        this.c = z;
        postValue(this);
    }

    public synchronized void a() {
        this.c = false;
        this.b = false;
        postValue(this);
    }

    public void b() {
        this.c = true;
        postValue(this);
    }

    public void c() {
        this.b = false;
        postValue(this);
    }

    public boolean d() {
        return this.b;
    }

    public boolean e() {
        return this.c;
    }

    public boolean f() {
        return this.a;
    }

    public void g() {
        this.b = true;
        postValue(this);
    }

    public void h() {
        postValue(this);
    }

    public void i() {
        this.a = true;
        postValue(this);
    }

    public void j() {
        this.a = false;
        postValue(this);
    }

    public void a(boolean z) {
        postValue(this);
    }
}
