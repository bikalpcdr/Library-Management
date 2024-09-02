package com.bikalp.library.Exception;

public class ApiException extends Throwable {
    public ApiException(String message) {
        super(message);
    }

    public ApiException() {
        super();
    }
}
