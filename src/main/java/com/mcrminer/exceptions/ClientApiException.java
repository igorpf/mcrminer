package com.mcrminer.exceptions;

public class ClientApiException extends RuntimeException {
    public ClientApiException() {
        super();
    }

    public ClientApiException(Throwable cause) {
        super(cause);
    }

    public ClientApiException(String message) {
        super(message);
    }

    public ClientApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
