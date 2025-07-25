package com.android.chileaf.adapt;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import com.android.chileaf.adapt.j.a;

public class InitProvider extends ContentProvider {
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public boolean onCreate() {
        Object applicationContext = getContext().getApplicationContext();
        if (applicationContext == null) {
            applicationContext = a.a();
        }
        d u = d.u();
        u.a(false);
        u.a((Application) applicationContext);
        u.b(false);
        return true;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
