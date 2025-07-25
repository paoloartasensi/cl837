package com.jeremyliao.liveeventbus.core;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.ExternalLiveData;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.jeremyliao.liveeventbus.ipc.IpcConst;
import com.jeremyliao.liveeventbus.ipc.encode.IEncoder;
import com.jeremyliao.liveeventbus.ipc.encode.ValueEncoder;
import com.jeremyliao.liveeventbus.ipc.json.GsonConverter;
import com.jeremyliao.liveeventbus.ipc.json.JsonConverter;
import com.jeremyliao.liveeventbus.ipc.receiver.LebIpcReceiver;
import com.jeremyliao.liveeventbus.logger.DefaultLogger;
import com.jeremyliao.liveeventbus.logger.Logger;
import com.jeremyliao.liveeventbus.logger.LoggerManager;
import com.jeremyliao.liveeventbus.utils.ThreadUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public final class LiveEventBusCore {
    /* access modifiers changed from: private */
    public Context appContext;
    /* access modifiers changed from: private */
    public boolean autoClear;
    /* access modifiers changed from: private */
    public final Map<String, LiveEvent<Object>> bus;
    private final Config config;
    /* access modifiers changed from: private */
    public IEncoder encoder;
    /* access modifiers changed from: private */
    public boolean lifecycleObserverAlwaysActive;
    /* access modifiers changed from: private */
    public LoggerManager logger;
    private LebIpcReceiver receiver;

    private class LiveEvent<T> implements Observable<T> {
        /* access modifiers changed from: private */
        public final String key;
        /* access modifiers changed from: private */
        public final LiveEvent<T>.LifecycleLiveData<T> liveData;
        private final Handler mainHandler = new Handler(Looper.getMainLooper());
        private final Map<Observer, ObserverWrapper<T>> observerMap = new HashMap();

        private class LifecycleLiveData<T> extends ExternalLiveData<T> {
            private LifecycleLiveData() {
            }

            /* access modifiers changed from: protected */
            public Lifecycle.State observerActiveLevel() {
                return LiveEventBusCore.this.lifecycleObserverAlwaysActive ? Lifecycle.State.CREATED : Lifecycle.State.STARTED;
            }

            public void removeObserver(Observer<? super T> observer) {
                super.removeObserver(observer);
                if (LiveEventBusCore.this.autoClear && !LiveEvent.this.liveData.hasObservers()) {
                    LiveEventBusCore.get().bus.remove(LiveEvent.this.key);
                }
                LoggerManager access$1000 = LiveEventBusCore.this.logger;
                Level level = Level.INFO;
                access$1000.log(level, "observer removed: " + observer);
            }
        }

        private class PostLifeValueTask implements Runnable {
            private Object newValue;
            private LifecycleOwner owner;

            public PostLifeValueTask(Object obj, LifecycleOwner lifecycleOwner) {
                this.newValue = obj;
                this.owner = lifecycleOwner;
            }

            public void run() {
                LifecycleOwner lifecycleOwner = this.owner;
                if (lifecycleOwner != null && lifecycleOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                    LiveEvent.this.postInternal(this.newValue);
                }
            }
        }

        private class PostValueTask implements Runnable {
            private Object newValue;

            public PostValueTask(Object obj) {
                this.newValue = obj;
            }

            public void run() {
                LiveEvent.this.postInternal(this.newValue);
            }
        }

        LiveEvent(String str) {
            this.key = str;
            this.liveData = new LifecycleLiveData<>();
        }

        /* access modifiers changed from: private */
        public void broadcastInternal(T t, boolean z) {
            LoggerManager access$1000 = LiveEventBusCore.this.logger;
            Level level = Level.INFO;
            access$1000.log(level, "broadcast: " + t + " foreground: " + z + " with key: " + this.key);
            Intent intent = new Intent(IpcConst.ACTION);
            if (z && Build.VERSION.SDK_INT >= 16) {
                intent.addFlags(268435456);
            }
            intent.putExtra(IpcConst.KEY, this.key);
            try {
                LiveEventBusCore.this.encoder.encode(intent, t);
                LiveEventBusCore.this.appContext.sendBroadcast(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /* access modifiers changed from: private */
        public void observeForeverInternal(Observer<T> observer) {
            ObserverWrapper observerWrapper = new ObserverWrapper(observer);
            boolean unused = observerWrapper.preventNextEvent = this.liveData.getVersion() > -1;
            this.observerMap.put(observer, observerWrapper);
            this.liveData.observeForever(observerWrapper);
            LoggerManager access$1000 = LiveEventBusCore.this.logger;
            Level level = Level.INFO;
            access$1000.log(level, "observe forever observer: " + observerWrapper + "(" + observer + ") with key: " + this.key);
        }

        /* access modifiers changed from: private */
        public void observeInternal(LifecycleOwner lifecycleOwner, Observer<T> observer) {
            ObserverWrapper observerWrapper = new ObserverWrapper(observer);
            boolean unused = observerWrapper.preventNextEvent = this.liveData.getVersion() > -1;
            this.liveData.observe(lifecycleOwner, observerWrapper);
            LoggerManager access$1000 = LiveEventBusCore.this.logger;
            Level level = Level.INFO;
            access$1000.log(level, "observe observer: " + observerWrapper + "(" + observer + ") on owner: " + lifecycleOwner + " with key: " + this.key);
        }

        /* access modifiers changed from: private */
        public void observeStickyForeverInternal(Observer<T> observer) {
            ObserverWrapper observerWrapper = new ObserverWrapper(observer);
            this.observerMap.put(observer, observerWrapper);
            this.liveData.observeForever(observerWrapper);
            LoggerManager access$1000 = LiveEventBusCore.this.logger;
            Level level = Level.INFO;
            access$1000.log(level, "observe sticky forever observer: " + observerWrapper + "(" + observer + ") with key: " + this.key);
        }

        /* access modifiers changed from: private */
        public void observeStickyInternal(LifecycleOwner lifecycleOwner, Observer<T> observer) {
            ObserverWrapper observerWrapper = new ObserverWrapper(observer);
            this.liveData.observe(lifecycleOwner, observerWrapper);
            LoggerManager access$1000 = LiveEventBusCore.this.logger;
            Level level = Level.INFO;
            access$1000.log(level, "observe sticky observer: " + observerWrapper + "(" + observer + ") on owner: " + lifecycleOwner + " with key: " + this.key);
        }

        /* access modifiers changed from: private */
        public void postInternal(T t) {
            LoggerManager access$1000 = LiveEventBusCore.this.logger;
            Level level = Level.INFO;
            access$1000.log(level, "post: " + t + " with key: " + this.key);
            this.liveData.setValue(t);
        }

        /* access modifiers changed from: private */
        public void removeObserverInternal(Observer<T> observer) {
            if (this.observerMap.containsKey(observer)) {
                observer = this.observerMap.remove(observer);
            }
            this.liveData.removeObserver(observer);
        }

        public void broadcast(T t) {
            broadcast(t, false);
        }

        public void observe(final LifecycleOwner lifecycleOwner, final Observer<T> observer) {
            if (ThreadUtils.isMainThread()) {
                observeInternal(lifecycleOwner, observer);
            } else {
                this.mainHandler.post(new Runnable() {
                    public void run() {
                        LiveEvent.this.observeInternal(lifecycleOwner, observer);
                    }
                });
            }
        }

        public void observeForever(final Observer<T> observer) {
            if (ThreadUtils.isMainThread()) {
                observeForeverInternal(observer);
            } else {
                this.mainHandler.post(new Runnable() {
                    public void run() {
                        LiveEvent.this.observeForeverInternal(observer);
                    }
                });
            }
        }

        public void observeSticky(final LifecycleOwner lifecycleOwner, final Observer<T> observer) {
            if (ThreadUtils.isMainThread()) {
                observeStickyInternal(lifecycleOwner, observer);
            } else {
                this.mainHandler.post(new Runnable() {
                    public void run() {
                        LiveEvent.this.observeStickyInternal(lifecycleOwner, observer);
                    }
                });
            }
        }

        public void observeStickyForever(final Observer<T> observer) {
            if (ThreadUtils.isMainThread()) {
                observeStickyForeverInternal(observer);
            } else {
                this.mainHandler.post(new Runnable() {
                    public void run() {
                        LiveEvent.this.observeStickyForeverInternal(observer);
                    }
                });
            }
        }

        public void post(T t) {
            if (ThreadUtils.isMainThread()) {
                postInternal(t);
            } else {
                this.mainHandler.post(new PostValueTask(t));
            }
        }

        public void postDelay(T t, long j2) {
            this.mainHandler.postDelayed(new PostValueTask(t), j2);
        }

        public void postOrderly(T t) {
            this.mainHandler.post(new PostValueTask(t));
        }

        public void removeObserver(final Observer<T> observer) {
            if (ThreadUtils.isMainThread()) {
                removeObserverInternal(observer);
            } else {
                this.mainHandler.post(new Runnable() {
                    public void run() {
                        LiveEvent.this.removeObserverInternal(observer);
                    }
                });
            }
        }

        public void broadcast(final T t, final boolean z) {
            if (LiveEventBusCore.this.appContext == null) {
                post(t);
            } else if (ThreadUtils.isMainThread()) {
                broadcastInternal(t, z);
            } else {
                this.mainHandler.post(new Runnable() {
                    public void run() {
                        LiveEvent.this.broadcastInternal(t, z);
                    }
                });
            }
        }

        public void postDelay(LifecycleOwner lifecycleOwner, T t, long j2) {
            this.mainHandler.postDelayed(new PostLifeValueTask(t, lifecycleOwner), j2);
        }
    }

    private class ObserverWrapper<T> implements Observer<T> {
        private final Observer<T> observer;
        /* access modifiers changed from: private */
        public boolean preventNextEvent = false;

        ObserverWrapper(Observer<T> observer2) {
            this.observer = observer2;
        }

        public void onChanged(T t) {
            if (this.preventNextEvent) {
                this.preventNextEvent = false;
                return;
            }
            LoggerManager access$1000 = LiveEventBusCore.this.logger;
            Level level = Level.INFO;
            access$1000.log(level, "message received: " + t);
            try {
                this.observer.onChanged(t);
            } catch (ClassCastException e) {
                LoggerManager access$10002 = LiveEventBusCore.this.logger;
                Level level2 = Level.WARNING;
                access$10002.log(level2, "class cast error on message received: " + t, e);
            } catch (Exception e2) {
                LoggerManager access$10003 = LiveEventBusCore.this.logger;
                Level level3 = Level.WARNING;
                access$10003.log(level3, "error on message received: " + t, e2);
            }
        }
    }

    private static class SingletonHolder {
        /* access modifiers changed from: private */
        public static final LiveEventBusCore DEFAULT_BUS = new LiveEventBusCore();

        private SingletonHolder() {
        }
    }

    public static LiveEventBusCore get() {
        return SingletonHolder.DEFAULT_BUS;
    }

    public Config config() {
        return this.config;
    }

    /* access modifiers changed from: package-private */
    public void enableLogger(boolean z) {
        this.logger.setEnable(z);
    }

    /* access modifiers changed from: package-private */
    public void registerReceiver(Context context) {
        if (context != null) {
            this.appContext = context.getApplicationContext();
        }
        if (this.appContext != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(IpcConst.ACTION);
            this.appContext.registerReceiver(this.receiver, intentFilter);
        }
    }

    /* access modifiers changed from: package-private */
    public void setAutoClear(boolean z) {
        this.autoClear = z;
    }

    /* access modifiers changed from: package-private */
    public void setJsonConverter(JsonConverter jsonConverter) {
        if (jsonConverter != null) {
            this.encoder = new ValueEncoder(jsonConverter);
            this.receiver.setJsonConverter(jsonConverter);
        }
    }

    /* access modifiers changed from: package-private */
    public void setLifecycleObserverAlwaysActive(boolean z) {
        this.lifecycleObserverAlwaysActive = z;
    }

    /* access modifiers changed from: package-private */
    public void setLogger(Logger logger2) {
        this.logger.setLogger(logger2);
    }

    public synchronized <T> Observable<T> with(String str, Class<T> cls) {
        if (!this.bus.containsKey(str)) {
            this.bus.put(str, new LiveEvent(str));
        }
        return this.bus.get(str);
    }

    private LiveEventBusCore() {
        this.config = new Config();
        this.bus = new HashMap();
        this.lifecycleObserverAlwaysActive = true;
        this.autoClear = false;
        this.logger = new LoggerManager(new DefaultLogger());
        GsonConverter gsonConverter = new GsonConverter();
        this.encoder = new ValueEncoder(gsonConverter);
        this.receiver = new LebIpcReceiver(gsonConverter);
    }
}
