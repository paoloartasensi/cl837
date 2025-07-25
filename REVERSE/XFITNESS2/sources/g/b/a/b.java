package g.b.a;

import android.database.Cursor;
import android.widget.Filter;

/* compiled from: CursorFilter */
class b extends Filter {
    a a;

    /* compiled from: CursorFilter */
    interface a {
        Cursor a();

        Cursor a(CharSequence charSequence);

        CharSequence a(Cursor cursor);

        void b(Cursor cursor);
    }

    b(a aVar) {
        this.a = aVar;
    }

    public CharSequence convertResultToString(Object obj) {
        return this.a.a((Cursor) obj);
    }

    /* access modifiers changed from: protected */
    public Filter.FilterResults performFiltering(CharSequence charSequence) {
        Cursor a2 = this.a.a(charSequence);
        Filter.FilterResults filterResults = new Filter.FilterResults();
        if (a2 != null) {
            filterResults.count = a2.getCount();
            filterResults.values = a2;
        } else {
            filterResults.count = 0;
            filterResults.values = null;
        }
        return filterResults;
    }

    /* access modifiers changed from: protected */
    public void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
        Cursor a2 = this.a.a();
        Object obj = filterResults.values;
        if (obj != null && obj != a2) {
            this.a.b((Cursor) obj);
        }
    }
}
