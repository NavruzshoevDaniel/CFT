package queue;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class PriorityHeapTest {

    @Test
    public void insert() {
        var heapString = new PriorityHeap<String>(Comparator.naturalOrder());
        heapString.insert("b");
        heapString.insert("c");
        heapString.insert("a");

        assertEquals("a",heapString.poll());
        assertEquals("b",heapString.poll());
        assertEquals("c",heapString.poll());

        var heapInteger = new PriorityHeap<Integer>(Comparator.naturalOrder());
        heapInteger.insert(2);
        heapInteger.insert(6);
        heapInteger.insert(0);
        heapInteger.insert(1);

        assertEquals(Integer.valueOf(0),heapInteger.poll());
        assertEquals(Integer.valueOf(1),heapInteger.poll());
        assertEquals(Integer.valueOf(2),heapInteger.poll());
        assertEquals(Integer.valueOf(6),heapInteger.poll());



    }
}