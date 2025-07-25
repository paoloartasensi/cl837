package io.objectbox.exception;

public class DbMaxReadersExceededException extends DbException {
    public DbMaxReadersExceededException(String str) {
        super(str);
    }

    public DbMaxReadersExceededException(String str, int i2) {
        super(str, i2);
    }
}
