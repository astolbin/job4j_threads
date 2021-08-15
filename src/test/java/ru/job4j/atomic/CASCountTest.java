package ru.job4j.atomic;

import org.junit.Test;

import static org.junit.Assert.*;

public class CASCountTest {
    @Test
    public void whenCountIn2Threads() throws InterruptedException {
        CASCount counter = new CASCount();
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        counter.increment();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        counter.increment();
                    }
                }
        );

        first.start();
        second.start();

        first.join();
        second.join();

        assertEquals(10, counter.get());
    }
}