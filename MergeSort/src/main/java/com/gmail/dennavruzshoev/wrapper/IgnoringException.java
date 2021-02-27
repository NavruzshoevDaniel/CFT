package com.gmail.dennavruzshoev.wrapper;

public interface IgnoringException<T extends Exception> {

    void run() throws T;

    static void ignore(IgnoringException<Exception> runnable) {
        try {
            runnable.run();
        } catch (Exception ignored) {
        }
    }
}
