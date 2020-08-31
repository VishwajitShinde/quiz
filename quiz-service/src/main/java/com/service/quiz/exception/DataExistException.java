package com.service.quiz.exception;

public class DataExistException extends RuntimeException {
    public DataExistException() {
        super();
    }

    public DataExistException(String message) {
        super(message);
    }

    public DataExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataExistException(Throwable cause) {
        super(cause);
    }

    protected DataExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
