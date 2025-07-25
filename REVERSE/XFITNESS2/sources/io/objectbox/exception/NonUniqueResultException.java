package io.objectbox.exception;

public class NonUniqueResultException extends DbException {
    public NonUniqueResultException(String str) {
        super(str);
    }
}
