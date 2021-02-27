package com.gmail.dennavruzshoev.mergesort;

import java.util.Comparator;

public class SortedChecker<T> {
    private final Comparator<T> comparator;
    private T previousElement;

    public SortedChecker(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public SortedChecker(Comparator<T> comparator, T initFirstElement) {
        this.comparator = comparator;
        this.previousElement = initFirstElement;
    }

    public boolean isSorted(T nextElement) {
        if (previousElement == null) {
            previousElement = nextElement;
            return true;
        }
        try {
            return comparator.compare(previousElement, nextElement) <= 0;
        } finally {
            previousElement = nextElement;
        }
    }

}
