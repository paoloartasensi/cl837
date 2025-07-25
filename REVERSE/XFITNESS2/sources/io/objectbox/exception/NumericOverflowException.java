package io.objectbox.exception;

public class NumericOverflowException extends DbException {
    public NumericOverflowException(String str) {
        super(str);
    }
}
