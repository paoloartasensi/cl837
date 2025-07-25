package g.b.a;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import g.b.a.b;

/* compiled from: CursorAdapter */
public abstract class a extends BaseAdapter implements Filterable, b.a {
    protected boolean e;

    /* renamed from: f  reason: collision with root package name */
    protected boolean f1665f;

    /* renamed from: g  reason: collision with root package name */
    protected Cursor f1666g;

    /* renamed from: h  reason: collision with root package name */
    protected Context f1667h;

    /* renamed from: i  reason: collision with root package name */
    protected int f1668i;

    /* renamed from: j  reason: collision with root package name */
    protected C0087a f1669j;
    protected DataSetObserver k;
    protected b l;

    /* renamed from: g.b.a.a$a  reason: collision with other inner class name */
    /* compiled from: CursorAdapter */
    private class C0087a extends ContentObserver {
        C0087a() {
            super(new Handler());
        }

        public boolean deliverSelfNotifications() {
            return true;
        }

        public void onChange(boolean z) {
            a.this.b();
        }
    }

    /* compiled from: CursorAdapter */
    private class b extends DataSetObserver {
        b() {
        }

        public void onChanged() {
            a aVar = a.this;
            aVar.e = true;
            aVar.notifyDataSetChanged();
        }

        public void onInvalidated() {
            a aVar = a.this;
            aVar.e = false;
            aVar.notifyDataSetInvalidated();
        }
    }

    public a(Context context, Cursor cursor, boolean z) {
        a(context, cursor, z ? 1 : 2);
    }

    public abstract View a(Context context, Cursor cursor, ViewGroup viewGroup);

    public abstract CharSequence a(Cursor cursor);

    /* access modifiers changed from: package-private */
    public void a(Context context, Cursor cursor, int i2) {
        boolean z = false;
        if ((i2 & 1) == 1) {
            i2 |= 2;
            this.f1665f = true;
        } else {
            this.f1665f = false;
        }
        if (cursor != null) {
            z = true;
        }
        this.f1666g = cursor;
        this.e = z;
        this.f1667h = context;
        this.f1668i = z ? cursor.getColumnIndexOrThrow("_id") : -1;
        if ((i2 & 2) == 2) {
            this.f1669j = new C0087a();
            this.k = new b();
        } else {
            this.f1669j = null;
            this.k = null;
        }
        if (z) {
            C0087a aVar = this.f1669j;
            if (aVar != null) {
                cursor.registerContentObserver(aVar);
            }
            DataSetObserver dataSetObserver = this.k;
            if (dataSetObserver != null) {
                cursor.registerDataSetObserver(dataSetObserver);
            }
        }
    }

    public abstract void a(View view, Context context, Cursor cursor);

    public abstract View b(Context context, Cursor cursor, ViewGroup viewGroup);

    public void b(Cursor cursor) {
        Cursor c = c(cursor);
        if (c != null) {
            c.close();
        }
    }

    public Cursor c(Cursor cursor) {
        Cursor cursor2 = this.f1666g;
        if (cursor == cursor2) {
            return null;
        }
        if (cursor2 != null) {
            C0087a aVar = this.f1669j;
            if (aVar != null) {
                cursor2.unregisterContentObserver(aVar);
            }
            DataSetObserver dataSetObserver = this.k;
            if (dataSetObserver != null) {
                cursor2.unregisterDataSetObserver(dataSetObserver);
            }
        }
        this.f1666g = cursor;
        if (cursor != null) {
            C0087a aVar2 = this.f1669j;
            if (aVar2 != null) {
                cursor.registerContentObserver(aVar2);
            }
            DataSetObserver dataSetObserver2 = this.k;
            if (dataSetObserver2 != null) {
                cursor.registerDataSetObserver(dataSetObserver2);
            }
            this.f1668i = cursor.getColumnIndexOrThrow("_id");
            this.e = true;
            notifyDataSetChanged();
        } else {
            this.f1668i = -1;
            this.e = false;
            notifyDataSetInvalidated();
        }
        return cursor2;
    }

    public int getCount() {
        Cursor cursor;
        if (!this.e || (cursor = this.f1666g) == null) {
            return 0;
        }
        return cursor.getCount();
    }

    public View getDropDownView(int i2, View view, ViewGroup viewGroup) {
        if (!this.e) {
            return null;
        }
        this.f1666g.moveToPosition(i2);
        if (view == null) {
            view = a(this.f1667h, this.f1666g, viewGroup);
        }
        a(view, this.f1667h, this.f1666g);
        return view;
    }

    public Filter getFilter() {
        if (this.l == null) {
            this.l = new b(this);
        }
        return this.l;
    }

    public Object getItem(int i2) {
        Cursor cursor;
        if (!this.e || (cursor = this.f1666g) == null) {
            return null;
        }
        cursor.moveToPosition(i2);
        return this.f1666g;
    }

    public long getItemId(int i2) {
        Cursor cursor;
        if (!this.e || (cursor = this.f1666g) == null || !cursor.moveToPosition(i2)) {
            return 0;
        }
        return this.f1666g.getLong(this.f1668i);
    }

    public View getView(int i2, View view, ViewGroup viewGroup) {
        if (!this.e) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        } else if (this.f1666g.moveToPosition(i2)) {
            if (view == null) {
                view = b(this.f1667h, this.f1666g, viewGroup);
            }
            a(view, this.f1667h, this.f1666g);
            return view;
        } else {
            throw new IllegalStateException("couldn't move cursor to position " + i2);
        }
    }

    /* access modifiers changed from: protected */
    public void b() {
        Cursor cursor;
        if (this.f1665f && (cursor = this.f1666g) != null && !cursor.isClosed()) {
            this.e = this.f1666g.requery();
        }
    }

    public Cursor a() {
        return this.f1666g;
    }
}
