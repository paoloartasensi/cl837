package io.objectbox.exception;

public class UniqueViolationException extends ConstraintViolationException {
    public UniqueViolationException(String str) {
        super(str);
    }
}
