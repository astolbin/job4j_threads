package ru.job4j.pool;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.*;

public class ArraySearcherTest {
    private ForkJoinPool pool;

    @Before
    public void setUp() {
        pool = new ForkJoinPool();
    }

    @Test
    public void whenOneItem() {
        Integer[] array = new Integer[] {2, 3, 1, 0};
        ArraySearcher<Integer> searcher = new ArraySearcher<>(array, 3);
        assertEquals(1, (int)pool.invoke(searcher));
    }

    @Test
    public void whenNoItem() {
        Integer[] array = new Integer[] {2, 3, 1, 0};
        ArraySearcher<Integer> searcher = new ArraySearcher<>(array, 4);
        assertEquals(-1, (int)pool.invoke(searcher));
    }

    @Test
    public void whenManyItemThenFirstIndex() {
        Integer[] array = new Integer[] {
                0, 1, 2, 3, 24, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 4, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 5, 25, 26, 27, 28, 29, 30
        };
        ArraySearcher<Integer> searcher = new ArraySearcher<>(array, 5);
        assertEquals(5, (int)pool.invoke(searcher));
    }
}