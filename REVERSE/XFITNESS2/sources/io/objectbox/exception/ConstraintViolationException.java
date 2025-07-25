package io.objectbox.exception;

public class ConstraintViolationException extends DbException {
    public ConstraintViolationException(String str) {
        super(str);
    }
}
