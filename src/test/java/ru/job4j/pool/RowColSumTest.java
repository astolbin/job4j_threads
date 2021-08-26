package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class RowColSumTest {
    @Test
    public void whenSyncSum() {
        int[][] inputMatrix = {
                {1, 3, 5},
                {2, 4, 6},
                {7, 8, 9}
        };

        RowColSum.Sums[] expectedSums = new RowColSum.Sums[inputMatrix.length];

        IntStream.range(0, inputMatrix.length)
                .forEach(i -> expectedSums[i] = new RowColSum.Sums());

        expectedSums[0].setRowSum(9);
        expectedSums[1].setRowSum(12);
        expectedSums[2].setRowSum(24);
        expectedSums[0].setColSum(10);
        expectedSums[1].setColSum(15);
        expectedSums[2].setColSum(20);

        assertEquals(expectedSums, RowColSum.sum(inputMatrix));
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] inputMatrix = {
                {1, 3, 5},
                {2, 4, 6},
                {7, 8, 9}
        };

        RowColSum.Sums[] expectedSums = new RowColSum.Sums[inputMatrix.length];

        IntStream.range(0, inputMatrix.length)
                .forEach(i -> expectedSums[i] = new RowColSum.Sums());

        expectedSums[0].setRowSum(9);
        expectedSums[1].setRowSum(12);
        expectedSums[2].setRowSum(24);
        expectedSums[0].setColSum(10);
        expectedSums[1].setColSum(15);
        expectedSums[2].setColSum(20);

        assertEquals(expectedSums, RowColSum.asyncSum(inputMatrix));
    }
}