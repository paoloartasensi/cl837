package io.objectbox.exception;

public class DbFullException extends DbException {
    public DbFullException(String str) {
        super(str);
    }

    public DbFullException(String str, int i2) {
        super(str, i2);
    }
}
