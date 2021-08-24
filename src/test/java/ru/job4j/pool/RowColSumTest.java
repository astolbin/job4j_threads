package ru.job4j.pool;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class RowColSumTest {
    private final int[][] matrix = {
            {1, 3, 5},
            {2, 4, 6},
            {7, 8, 9}
    };
    private final RowColSum.Sums[] expect = new RowColSum.Sums[matrix.length];

    @Before
    public void setUp() {
        IntStream.range(0, matrix.length)
                .forEach(i -> expect[i] = new RowColSum.Sums());

        expect[0].setRowSum(9);
        expect[1].setRowSum(12);
        expect[2].setRowSum(24);
        expect[0].setColSum(10);
        expect[1].setColSum(15);
        expect[2].setColSum(20);
    }

    @Test
    public void whenSyncSum() {
        assertEquals(expect, RowColSum.sum(matrix));
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        assertEquals(expect, RowColSum.asyncSum(matrix));
    }
}