package ru.job4j.wait;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {
    @Test
    public void whenQueueBlockTest() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(
                () -> {
                    try {
                        queue.offer(1);
                        queue.offer(2);
                        queue.offer(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    try {
                        queue.poll();
                        queue.poll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        assertEquals(3, (int)queue.poll());
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> IntStream.range(0, 5).forEach(
                        (n) -> {
                            try {
                                queue.offer(n);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Thread.currentThread().interrupt();
                            }
                        }
                )
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();

        producer.join();
        consumer.interrupt();
        consumer.join();

        assertEquals(Arrays.asList(0, 1, 2, 3, 4), buffer);
    }
}