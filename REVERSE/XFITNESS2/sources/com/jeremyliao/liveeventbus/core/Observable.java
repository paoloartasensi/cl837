package com.jeremyliao.liveeventbus.core;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

public interface Observable<T> {
    void broadcast(T t);

    void broadcast(T t, boolean z);

    void observe(LifecycleOwner lifecycleOwner, Observer<T> observer);

    void observeForever(Observer<T> observer);

    void observeSticky(LifecycleOwner lifecycleOwner, Observer<T> observer);

    void observeStickyForever(Observer<T> observer);

    void post(T t);

    void postDelay(LifecycleOwner lifecycleOwner, T t, long j2);

    void postDelay(T t, long j2);

    void postOrderly(T t);

    void removeObserver(Observer<T> observer);
}
