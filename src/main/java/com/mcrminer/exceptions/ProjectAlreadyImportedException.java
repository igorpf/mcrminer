package com.mcrminer.exceptions;

public class ProjectAlreadyImportedException extends RuntimeException {
    public ProjectAlreadyImportedException() {
    }

    public ProjectAlreadyImportedException(String message) {
        super(message);
    }

    public ProjectAlreadyImportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectAlreadyImportedException(Throwable cause) {
        super(cause);
    }
}
