package com.jeremyliao.liveeventbus.logger;

import java.util.logging.Level;

public interface Logger {
    void log(Level level, String str);

    void log(Level level, String str, Throwable th);
}
