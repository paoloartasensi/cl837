package com.google.gson.internal.bind;

import com.google.gson.JsonSyntaxException;
import com.google.gson.d;
import com.google.gson.o;
import com.google.gson.p;
import com.google.gson.r.a;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class TimeTypeAdapter extends o<Time> {
    public static final p b = new p() {
        public <T> o<T> a(d dVar, a<T> aVar) {
            if (aVar.a() == Time.class) {
                return new TimeTypeAdapter();
            }
            return null;
        }
    };
    private final DateFormat a = new SimpleDateFormat("hh:mm:ss a");

    public synchronized Time a(JsonReader jsonReader) {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        try {
            return new Time(this.a.parse(jsonReader.nextString()).getTime());
        } catch (ParseException e) {
            throw new JsonSyntaxException((Throwable) e);
        }
    }

    public synchronized void a(JsonWriter jsonWriter, Time time) {
        jsonWriter.value(time == null ? null : this.a.format(time));
    }
}
