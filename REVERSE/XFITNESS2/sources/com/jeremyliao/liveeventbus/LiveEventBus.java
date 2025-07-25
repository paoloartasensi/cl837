package com.jeremyliao.liveeventbus;

import com.jeremyliao.liveeventbus.core.Config;
import com.jeremyliao.liveeventbus.core.LiveEventBusCore;
import com.jeremyliao.liveeventbus.core.Observable;

public final class LiveEventBus {
    public static Config config() {
        return LiveEventBusCore.get().config();
    }

    public static <T> Observable<T> get(String str, Class<T> cls) {
        return LiveEventBusCore.get().with(str, cls);
    }

    public static Observable<Object> get(String str) {
        return get(str, Object.class);
    }
}
