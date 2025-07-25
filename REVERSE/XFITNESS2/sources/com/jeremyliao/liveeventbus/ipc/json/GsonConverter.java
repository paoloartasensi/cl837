package com.jeremyliao.liveeventbus.ipc.json;

import com.google.gson.d;

public class GsonConverter implements JsonConverter {
    private d gson = new d();

    public <T> T fromJson(String str, Class<T> cls) {
        return this.gson.a(str, cls);
    }

    public String toJson(Object obj) {
        return this.gson.a(obj);
    }
}
