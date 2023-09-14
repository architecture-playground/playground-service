package com.playground.collections;

import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * The name deque is short for "double ended queue" and is usually pronounced "deck"
 */
public class DequeueTest {

    @Test
    void testLinkedList() {
        Deque<Function<Integer, String>> dequeue = new LinkedList<>();

        dequeue.add(id -> "first, id = " + id);
        dequeue.add(id -> "second, id = " + id);
        dequeue.add(id -> "third, id = " + id);

        for (int i = 0; i < 8; i++) {
            Function<Integer, String> function = dequeue.pollFirst();
            System.out.println(requireNonNull(function).apply(i));
            dequeue.addLast(function);
        }

        System.out.println("Dequeue size: " + dequeue.size());
    }
}
