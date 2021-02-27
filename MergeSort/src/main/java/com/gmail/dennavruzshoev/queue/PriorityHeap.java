package com.gmail.dennavruzshoev.queue;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PriorityHeap<T> {
    private final Comparator<T> comparator;
    private final List<T> list = new ArrayList<>();

    public PriorityHeap(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    private int getParent(int index) {
        return (index - 1) / 2;
    }

    private int getLeftChild(int index) {
        return (2 * index) + 1;
    }

    private int getRightChild(int index) {
        return (2 * index) + 2;
    }

    private int getLastIndex() {
        return list.size() - 1;
    }

    private void heapifyElements(int index) {
        int smallestIndex = index;
        int leftIndex = getLeftChild(index);
        int rightIndex = getRightChild(index);
        if (leftIndex <= getLastIndex()
                && comparator.compare(list.get(leftIndex), list.get(smallestIndex)) < 0) {
            smallestIndex = leftIndex;
        }
        if (rightIndex <= getLastIndex()
                && comparator.compare(list.get(rightIndex), list.get(smallestIndex)) < 0) {
            smallestIndex = rightIndex;
        }
        if (smallestIndex != index) {
            Collections.swap(list, index, smallestIndex);
            heapifyElements(smallestIndex);
        }
    }

    public void insert(T element) {
        list.add(element);
        int currentIndexElement = getLastIndex();
        int parentElementIndex = getParent(currentIndexElement);

        while (comparator.compare(list.get(currentIndexElement), list.get(parentElementIndex)) < 0) {
            Collections.swap(list, currentIndexElement, parentElementIndex);
            currentIndexElement = getParent(currentIndexElement);
            parentElementIndex = getParent(currentIndexElement);
        }
    }

    public T poll() {
        Collections.swap(list, 0, getLastIndex());
        try {
            return list.remove(getLastIndex());
        } finally {
            heapifyElements(0);
        }
    }
}
