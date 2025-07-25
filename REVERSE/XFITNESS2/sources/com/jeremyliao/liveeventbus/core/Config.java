package com.jeremyliao.liveeventbus.core;

import android.content.Context;
import com.jeremyliao.liveeventbus.ipc.json.JsonConverter;
import com.jeremyliao.liveeventbus.logger.Logger;

public class Config {
    public Config autoClear(boolean z) {
        LiveEventBusCore.get().setAutoClear(z);
        return this;
    }

    public Config enableLogger(boolean z) {
        LiveEventBusCore.get().enableLogger(z);
        return this;
    }

    public Config lifecycleObserverAlwaysActive(boolean z) {
        LiveEventBusCore.get().setLifecycleObserverAlwaysActive(z);
        return this;
    }

    public Config setJsonConverter(JsonConverter jsonConverter) {
        LiveEventBusCore.get().setJsonConverter(jsonConverter);
        return this;
    }

    public Config setLogger(Logger logger) {
        LiveEventBusCore.get().setLogger(logger);
        return this;
    }

    public Config supportBroadcast(Context context) {
        LiveEventBusCore.get().registerReceiver(context);
        return this;
    }
}
