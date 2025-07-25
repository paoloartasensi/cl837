package com.jeremyliao.liveeventbus.logger;

import java.util.logging.Level;

public class LoggerManager implements Logger {
    private boolean enable = true;
    private Logger logger;

    public LoggerManager(Logger logger2) {
        this.logger = logger2;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void log(Level level, String str) {
        if (this.enable) {
            this.logger.log(level, str);
        }
    }

    public void setEnable(boolean z) {
        this.enable = z;
    }

    public void setLogger(Logger logger2) {
        this.logger = logger2;
    }

    public void log(Level level, String str, Throwable th) {
        if (this.enable) {
            this.logger.log(level, str, th);
        }
    }
}
