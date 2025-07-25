package io.objectbox.exception;

public interface DbExceptionListener {
    void onDbException(Exception exc);
}
