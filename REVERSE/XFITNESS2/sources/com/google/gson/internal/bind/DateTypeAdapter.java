package com.google.gson.internal.bind;

import com.google.gson.JsonSyntaxException;
import com.google.gson.d;
import com.google.gson.internal.c;
import com.google.gson.internal.f;
import com.google.gson.o;
import com.google.gson.p;
import com.google.gson.r.a;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class DateTypeAdapter extends o<Date> {
    public static final p b = new p() {
        public <T> o<T> a(d dVar, a<T> aVar) {
            if (aVar.a() == Date.class) {
                return new DateTypeAdapter();
            }
            return null;
        }
    };
    private final List<DateFormat> a;

    public DateTypeAdapter() {
        ArrayList arrayList = new ArrayList();
        this.a = arrayList;
        arrayList.add(DateFormat.getDateTimeInstance(2, 2, Locale.US));
        if (!Locale.getDefault().equals(Locale.US)) {
            this.a.add(DateFormat.getDateTimeInstance(2, 2));
        }
        if (c.c()) {
            this.a.add(f.a(2, 2));
        }
    }

    public Date a(JsonReader jsonReader) {
        if (jsonReader.peek() != JsonToken.NULL) {
            return a(jsonReader.nextString());
        }
        jsonReader.nextNull();
        return null;
    }

    private synchronized Date a(String str) {
        for (DateFormat parse : this.a) {
            try {
                return parse.parse(str);
            } catch (ParseException unused) {
            }
        }
        try {
            return com.google.gson.internal.bind.d.a.a(str, new ParsePosition(0));
        } catch (ParseException e) {
            throw new JsonSyntaxException(str, e);
        }
    }

    public synchronized void a(JsonWriter jsonWriter, Date date) {
        if (date == null) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(this.a.get(0).format(date));
        }
    }
}
