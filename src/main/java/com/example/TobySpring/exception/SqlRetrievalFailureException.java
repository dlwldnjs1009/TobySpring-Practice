package com.example.TobySpring.exception;

public class SqlRetrievalFailureException extends RuntimeException {
    public SqlRetrievalFailureException(String message) {
        super(message);
    }

    public SqlRetrievalFailureException(Throwable cause) {
        super(cause);
    }

}
