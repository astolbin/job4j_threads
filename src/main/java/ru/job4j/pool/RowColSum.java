package ru.job4j.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class RowColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] rsl = new Sums[matrix.length];
        IntStream.range(0, matrix.length)
                .forEach(i -> rsl[i] = new Sums());

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                rsl[row].setRowSum(rsl[row].getRowSum() + matrix[row][col]);
                rsl[col].setColSum(rsl[col].getColSum() + matrix[row][col]);
            }
        }

        return rsl;
    }

    /**
     * @param matrix nested array of int
     * @return array of Sums
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] rsl = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();

        for (int i = 0; i < matrix.length; i++) {
            futures.put(i, getTask(matrix, i));
        }
        for (Integer key : futures.keySet()) {
            rsl[key] = futures.get(key).get();
        }

        return rsl;
    }

    /**
     *
     * @param data input matrix
     * @param current currents row and column
     * @return CompletableFuture
     */
    public static CompletableFuture<Sums> getTask(int[][] data, int current) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sum = new Sums();
            for (int col = 0; col < data.length; col++) {
                sum.setRowSum(sum.getRowSum() + data[current][col]);
            }
            for (int row = 0; row < data[current].length; row++) {
                sum.setColSum(sum.getColSum() + data[row][current]);
            }
            return sum;
        });
    }
}
