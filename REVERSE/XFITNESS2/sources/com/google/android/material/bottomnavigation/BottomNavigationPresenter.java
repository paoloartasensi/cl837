package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.view.menu.m;
import androidx.appcompat.view.menu.r;

public class BottomNavigationPresenter implements m {
    private g e;

    /* renamed from: f  reason: collision with root package name */
    private BottomNavigationMenuView f1417f;

    /* renamed from: g  reason: collision with root package name */
    private boolean f1418g = false;

    /* renamed from: h  reason: collision with root package name */
    private int f1419h;

    static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        int e;

        static class a implements Parcelable.Creator<SavedState> {
            a() {
            }

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }
        }

        SavedState() {
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeInt(this.e);
        }

        SavedState(Parcel parcel) {
            this.e = parcel.readInt();
        }
    }

    public void a(g gVar, boolean z) {
    }

    public void a(m.a aVar) {
    }

    public void a(BottomNavigationMenuView bottomNavigationMenuView) {
        this.f1417f = bottomNavigationMenuView;
    }

    public boolean a(g gVar, i iVar) {
        return false;
    }

    public boolean a(r rVar) {
        return false;
    }

    public int b() {
        return this.f1419h;
    }

    public boolean b(g gVar, i iVar) {
        return false;
    }

    public boolean d() {
        return false;
    }

    public Parcelable e() {
        SavedState savedState = new SavedState();
        savedState.e = this.f1417f.getSelectedItemId();
        return savedState;
    }

    public void a(Context context, g gVar) {
        this.e = gVar;
        this.f1417f.a(gVar);
    }

    public void b(boolean z) {
        this.f1418g = z;
    }

    public void a(boolean z) {
        if (!this.f1418g) {
            if (z) {
                this.f1417f.a();
            } else {
                this.f1417f.c();
            }
        }
    }

    public void a(int i2) {
        this.f1419h = i2;
    }

    public void a(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.f1417f.b(((SavedState) parcelable).e);
        }
    }
}
