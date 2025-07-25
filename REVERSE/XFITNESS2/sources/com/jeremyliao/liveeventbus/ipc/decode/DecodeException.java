package com.jeremyliao.liveeventbus.ipc.decode;

public class DecodeException extends Exception {
    public DecodeException() {
    }

    public DecodeException(String str) {
        super(str);
    }

    public DecodeException(String str, Throwable th) {
        super(str, th);
    }

    public DecodeException(Throwable th) {
        super(th);
    }
}
