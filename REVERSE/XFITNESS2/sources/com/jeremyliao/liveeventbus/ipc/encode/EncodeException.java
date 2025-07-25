package com.jeremyliao.liveeventbus.ipc.encode;

public class EncodeException extends Exception {
    public EncodeException() {
    }

    public EncodeException(String str) {
        super(str);
    }

    public EncodeException(String str, Throwable th) {
        super(str, th);
    }

    public EncodeException(Throwable th) {
        super(th);
    }
}
