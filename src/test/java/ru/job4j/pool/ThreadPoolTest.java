package ru.job4j.pool;

import org.junit.Test;

import static org.junit.Assert.*;

public class ThreadPoolTest {
    private int sum = 0;

    @Test
    public void whenRun2Workers() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        threadPool.work(() -> {
            for (int i = 0; i < 5; i++) {
                sum++;
            }
        });
        threadPool.work(() -> {
            for (int i = 0; i < 5; i++) {
                sum++;
            }
        });

        Thread.sleep(300);

        assertEquals(10, sum);
    }
}