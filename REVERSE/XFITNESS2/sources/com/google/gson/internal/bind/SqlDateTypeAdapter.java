package com.google.gson.internal.bind;

import com.google.gson.JsonSyntaxException;
import com.google.gson.d;
import com.google.gson.o;
import com.google.gson.p;
import com.google.gson.r.a;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class SqlDateTypeAdapter extends o<Date> {
    public static final p b = new p() {
        public <T> o<T> a(d dVar, a<T> aVar) {
            if (aVar.a() == Date.class) {
                return new SqlDateTypeAdapter();
            }
            return null;
        }
    };
    private final DateFormat a = new SimpleDateFormat("MMM d, yyyy");

    public synchronized Date a(JsonReader jsonReader) {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        try {
            return new Date(this.a.parse(jsonReader.nextString()).getTime());
        } catch (ParseException e) {
            throw new JsonSyntaxException((Throwable) e);
        }
    }

    public synchronized void a(JsonWriter jsonWriter, Date date) {
        jsonWriter.value(date == null ? null : this.a.format(date));
    }
}
