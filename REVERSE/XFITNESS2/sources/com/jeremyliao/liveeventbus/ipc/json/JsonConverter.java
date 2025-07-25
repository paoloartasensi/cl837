package com.jeremyliao.liveeventbus.ipc.json;

public interface JsonConverter {
    <T> T fromJson(String str, Class<T> cls);

    String toJson(Object obj);
}
