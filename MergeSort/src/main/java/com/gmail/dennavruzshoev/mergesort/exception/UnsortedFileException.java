package com.gmail.dennavruzshoev.mergesort.exception;

public class UnsortedFileException extends RuntimeException {
    public UnsortedFileException() {
    }

    public UnsortedFileException(String message) {
        super(message);
    }

    public UnsortedFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsortedFileException(Throwable cause) {
        super(cause);
    }
}
