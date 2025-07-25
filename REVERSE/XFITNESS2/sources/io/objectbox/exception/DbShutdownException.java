package io.objectbox.exception;

public class DbShutdownException extends DbException {
    public DbShutdownException(String str) {
        super(str);
    }

    public DbShutdownException(String str, int i2) {
        super(str, i2);
    }
}
