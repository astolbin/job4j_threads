package ru.job4j.wait;

import org.junit.Test;

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
}