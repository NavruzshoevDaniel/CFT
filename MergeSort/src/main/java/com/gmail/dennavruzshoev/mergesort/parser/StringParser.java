package com.gmail.dennavruzshoev.mergesort.parser;

@FunctionalInterface
public interface StringParser<T> {
    T parse(String line);
}
